package com.vivebest.mall.web.merchant.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.entity.Order;
import com.vivebest.mall.entity.Order.OrderStatus;
import com.vivebest.mall.entity.OrderItem;
import com.vivebest.mall.merchant.entity.Balance;
import com.vivebest.mall.merchant.entity.Balance.Status;
import com.vivebest.mall.merchant.entity.BalanceItem;
import com.vivebest.mall.merchant.entity.CommissionRate;
import com.vivebest.mall.merchant.entity.Trade;
import com.vivebest.mall.merchant.entity.TradeBanks;
import com.vivebest.mall.merchant.service.BalanceItemService;
import com.vivebest.mall.merchant.service.BalanceService;
import com.vivebest.mall.merchant.service.CommissionRateService;
import com.vivebest.mall.merchant.service.OrderService;
import com.vivebest.mall.merchant.service.TradeBanksService;
import com.vivebest.mall.merchant.service.TradeService;

/**
 * controller 结算管理
 * 
 * @author vnb Team
 * @version 1.0
 *
 */
@Controller("settleController")
@RequestMapping("/settle")
public class SettleController extends BaseController {

    @Resource(name = "orderServiceImpl2")
    private OrderService orderService;

    @Resource(name = "tradeBanksServiceImpl")
    private TradeBanksService tradeBanksService;

    @Resource(name = "commissionRateServiceImpl")
    private CommissionRateService commissionRateService;

    @Resource(name = "balanceServiceImpl")
    private BalanceService balanceService;

    @Resource(name = "balanceItemServiceImpl")
    private BalanceItemService balanceItemService;

    @Resource(name = "tradeServiceImpl")
    private TradeService tradeService;

    private static Logger logger = Logger.getLogger(SettleController.class);
    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        return "settle/index";
    }
    /**
     * 结算申请列表
     * 
     * @param order
     * @param orderStatus
     * @param sn
     * @param memberUsername
     * @param memberName
     * @param model
     * @param bDate
     * @param eDate
     * @param applyNum
     * @param tradeId
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Order order, OrderStatus orderStatus, String sn, String memberUsername,
            String memberName, Model model, String bDate, String eDate, String applyNum, Long tradeId,
            Pageable pageable) {
        tradeId = null;

        // 回显
        model.addAttribute("bDate", bDate);
        model.addAttribute("eDate", eDate);
        model.addAttribute("memberUsername", memberUsername);
        model.addAttribute("memberName", memberName);

        Integer os = null;
        if (orderStatus != null) {
            os = orderStatus.ordinal();
        }

        // 分页查找订单
        model.addAttribute("page",
                orderService.findPage(order, 3, memberUsername, memberName, bDate, eDate, tradeId, pageable));

        return "settle/settle";
    }

    /**
     * 结算确认
     * 
     * @param ids
     * @param model
     * @return
     */
    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String confirm(Long[] ids, Model model) {

        List<Order> orderList = orderService.findList(ids);

        List<TradeBanks> tradeBanks = tradeBanksService.findAll();

        CommissionRate commissionRate = commissionRateService.findCommissionRate((short) 1, 0);

        // 结算总额
        BigDecimal sum = new BigDecimal(0);
        for (Order order : orderList) {
            BigDecimal amountPaid = order.getAmountPaid();
            sum = sum.add(amountPaid);
        }
        // 佣金计算
        BigDecimal commissionSum = new BigDecimal(0);

        if (commissionRate.getMethod().ordinal() == 0) {
            // 按笔数收
            BigDecimal commission = commissionRate.getCommission();
            commissionSum = new BigDecimal(ids.length).multiply(commission);
        } else {
            // 按比例收
            BigDecimal commission = commissionRate.getCommission();
            commissionSum = sum.multiply(commission);
        }

        model.addAttribute("orderList", orderList);
        model.addAttribute("num", ids.length);
        model.addAttribute("sum", sum);
        model.addAttribute("tradeBanks", tradeBanks);
        model.addAttribute("commissionSum", commissionSum);
        model.addAttribute("ids", ids);
        return "settle/confirm";

    }
    /**
     * 结算提交
     * @param model
     * @param balance
     * @param tradeId
     * @return
     */
    @RequestMapping(value = "/settleSave", method = RequestMethod.POST)
    public String settleSave(Model model,Balance balance,Long tradeId,Long[] ids) {
        tradeId =8L;
        Trade trade = tradeService.find(tradeId);
        Balance balance1=new Balance();
        balance1.setCreateDate(new Date());
        balance1.setModifyDate(new Date());
        balance1.setApplyDate(balance1.getCreateDate());
        balance1.setTradeId(tradeId);
        balance1.setApplicant(trade.getName());
        TradeBanks tradeBanks=tradeBanksService.findByCardNo(balance.getCardNo());
        balance1.setBankName(tradeBanks.getBank_name());
//        balance1.setBalanceNo(balance.getBalanceNo());
        balance1.setCardName(tradeBanks.getCard_name());
        balance1.setCardNo(balance.getCardNo());
        balance1.setCommission(balance.getCommission());
        balance1.setTotalAmt(balance.getTotalAmt());
        balance1.setOperating(balance.getOperating());
        BigDecimal payAmt = balance.getTotalAmt().subtract(balance.getCommission());
        balance1.setPayAmt(payAmt);
        balance1.setStatus(Status.uncheck);
        balanceService.save(balance1);
//        List<Order> orderList = orderService.findList(ids);
//        for(Order order:orderList){
//            BalanceItem balanceItem=new BalanceItem();
////            balanceItem.setBalanceId(balanceId);
////            balanceItem.setCommission(commission);
//            balanceItem.setOrderId(order.getId());
//            balanceItem.setTotalAmt(order.getAmountPaid());
//            balanceItemService.save(balanceItem);
//        }
        return "redirect:/settle/list.do";
    }

    /**
     * 结算单查询
     * 
     * @param balance
     * @param model
     * @param status
     * @param bDate
     * @param eDate
     * @param pageable
     * @param tradeId
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public String query(Balance balance, Model model, Status status, String bDate, String eDate,
            Pageable pageable, Long tradeId,String operating) {
        tradeId = null;
        Integer os = null;
        if (status != null) {
            os = status.ordinal();
        }
        // 回显
        model.addAttribute("bDate", bDate);
        model.addAttribute("eDate", eDate);
        
        model.addAttribute("status", status);
        model.addAttribute("operating", operating);
        
        model.addAttribute("balanceList", balanceService.findAll());
        
        model.addAttribute("page", balanceService.findPage(balance, os, bDate, eDate, pageable, tradeId));
        return "settle/query";
    }

    /**
     * 订单详情
     * 
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/orderDetail", method = RequestMethod.GET)
    public String orderDetail(Long id, Model model, Long tradeId) {
        tradeId = 8L;
        // 根据id查找订单对象
        Order order = orderService.find(id);
        List<OrderItem> orderItems = order.getOrderItems();

        BalanceItem balanceItem = balanceItemService.findByOrderId(id);

        Balance balance = balanceService.find(balanceItem.getBalanceId());

        Trade trade = tradeService.find(tradeId);

        model.addAttribute("trade", trade);
        model.addAttribute("balance", balance);
        model.addAttribute("balanceItem", balanceItem);
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("order", order);
        return "order/detail";
    }

    /**
     * 结算单详情
     * 
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/settleDetail", method = RequestMethod.GET)
    public String settleDetail(Long id, Model model, Long tradeId) {
        tradeId = 8L;
        List<BalanceItem> balanceItemList = balanceItemService.findByBalanceId(id);
        Balance balance = balanceService.find(id);
        Trade trade = tradeService.find(tradeId);
        // 订单id数组
        List list = new ArrayList();
        for (BalanceItem balanceItem : balanceItemList) {
            Long orderId = balanceItem.getOrderId();
            list.add(orderId);
        }
        Long[] ids = new Long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ids[i] = (Long) list.get(i);
        }
        model.addAttribute("orderList", orderService.findList(ids));
        model.addAttribute("balance", balance);
        model.addAttribute("trade", trade);

        return "settle/detail";
    }

}
