package com.vivebest.mall.admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.entity.Admin;
import com.vivebest.mall.entity.Changes;
import com.vivebest.mall.entity.Order;
import com.vivebest.mall.entity.Order.OrderStatus;
import com.vivebest.mall.entity.OrderItem;
import com.vivebest.mall.entity.Returns;
import com.vivebest.mall.entity.Returns.ReturnType;
import com.vivebest.mall.service.AdminService;
import com.vivebest.mall.service.ChangesItemService;
import com.vivebest.mall.service.ChangesService;
import com.vivebest.mall.service.MemberService;
import com.vivebest.mall.service.MessageService;
import com.vivebest.mall.service.OrderItemService;
import com.vivebest.mall.service.OrderService;
import com.vivebest.mall.service.ReturnsService;

@Controller("adminMemberChangesController")
@RequestMapping("/admin/changes")
public class ChangesController extends BaseController {
	private static Logger logger = Logger.getLogger(ChangesController.class);
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "orderItemServiceImpl")
	private OrderItemService orderItemService;
	@Resource(name = "changesServiceImpl")
	private ChangesService changesService;
	@Resource(name = "changesItemServiceImpl")
	private ChangesItemService changesItemService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "returnsServiceImpl")
	private ReturnsService returnsService;
	@Resource(name = "messageServiceImpl")
	private MessageService messageService;

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", changesService.findPage(pageable));
		return "/admin/changes/list";

	}

	/**
	 * 查看审核信息
	 */
	@RequestMapping(value = "/audite", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		Changes changes = changesService.find(id);
		model.addAttribute("changes", changes);
		if (com.vivebest.mall.entity.Changes.Status.pending.equals(changes.getStatus()) && (!changes.isExpired())) {
			return "/admin/changes/audite";
		}
		return "/admin/changes/view";
	}

	/**
	 * 审核操作
	 */
	@RequestMapping(value = "/audite", method = RequestMethod.POST)
	@ResponseBody
	public Message update(Long id, boolean value, String memo, String auditDesc,
			RedirectAttributes redirectAttributes) {
		Changes changes = changesService.find(id);
		if (changes == null) {
			return Message.error("change.content.entry");
		} else {
			if (auditDesc != null) {

				if (auditDesc.length() > 200 || (memo != null && memo.length() > 200)) {
					return Message.error("change.auditDesc.tooLong");
				}
				changes.setAuditDesc(auditDesc);
				changes.setMemo(memo);
				String username = adminService.getCurrentUsername();
				Admin admin = adminService.findByUsername(username);
				if (adminService.exists(admin.getId())) {
					changes.setAuditApply(admin);
					changes.setOperator(admin);
				} else {
					return Message.error("change.audite.admin.empty");
				}
				if (value) {
					changes.setStatus(com.vivebest.mall.entity.Changes.Status.approve);
					message(changes,"pass");
				} else {
					changes.setStatus(com.vivebest.mall.entity.Changes.Status.unapprove);
					message(changes,"pass");
				}
				changes.setAuditDate(new Date());
				changesService.update(changes);

			} else {
				return Message.error("change.auditDesc.entry");
			}
			return Message.success("change.audite.handle");
		}
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids) {
		if (ids != null) {
			// Admin admin = adminService.getCurrent();
			for (Long id : ids) {
				Changes changes = changesService.find(id);
				if (changes != null && changes.getSn() != null) {
					return Message.error("admin.order.deleteLockedNotAllowed", changes.getSn());
				}
			}
			changesService.delete(ids);
		}
		return SUCCESS_MESSAGE;
	}

	/**
	 * 确认收货
	 * 
	 * @param id
	 *            换货单id
	 * @return
	 */
	@RequestMapping(value = "/sureAccept", method = RequestMethod.POST)
	@ResponseBody
	Map<String, Object> sureAccept(Long id) {
		Message message = new Message();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Changes changes = changesService.find(id);
		Admin admin = adminService.getCurrent();
		changes.setShipper(admin.getUsername());
		if (changes.getStatus() == com.vivebest.mall.entity.Changes.Status.delivered) {
			changes.setStatus(com.vivebest.mall.entity.Changes.Status.completeRep);
			try {
				// 根据换货单生成订单
				orderService.createChangesOrder(changes);
				// 跟新换货单
				changesService.update(changes);
				message = Message.success("changes.handle.success");
			} catch (Exception e) {
				message = Message.error("shop.network.error");
				e.printStackTrace();
			}
		} else {
			message = ERROR_MESSAGE;
		}
		Order order = changes.getOrders();
		if (order != null && !order.getOrderItems().isEmpty()) {
			int count = 0;
			for (OrderItem orderItem : order.getOrderItems()) {
				Changes change = changesService.findByOrderItem(orderItem.getId());
				List<Returns> returns2 = returnsService.findByOrderItem(orderItem.getId());
				List<Returns> returnss=new ArrayList<Returns>();
				Returns returns=null;
				if (returns2 != null) {
					returnss.addAll(returns2);
					for (Returns ret : returnss) {
						if (ReturnType.bookingRefund.equals(ret.getReturnType())) {
							returns2.remove(ret);
						} else {
							returns = ret;
							break;
						}
					}
				}
				if (change != null && (com.vivebest.mall.entity.Changes.Status.completeRep.equals(change.getStatus())
						|| com.vivebest.mall.entity.Changes.Status.delivered.equals(change.getStatus()))) {
					count++;
				} else if (returns != null) {
					if (ReturnType.directlyRefund.equals(returns.getReturnType())) {
						if (com.vivebest.mall.entity.Returns.Status.approved.equals(returns.getStatus())) {
							count++;
						}
					} else if (ReturnType.returnToRefund.equals(returns.getReturnType())) {
						if (com.vivebest.mall.entity.Returns.Status.dealwith.equals(returns.getStatus())
								|| com.vivebest.mall.entity.Returns.Status.received.equals(returns.getStatus())) {
							count++;
						}
					}
				} else {
					break;
				}
			}
			if (count == order.getOrderItems().size()) {
				order.setOrderStatus(OrderStatus.closed);
				try {
					orderService.update(order);
				} catch (Exception e) {
					logger.info(e);
				}
			}
		}
		resultMap.put("changes", changes);
		resultMap.put("message", message);
		return resultMap;

	}
	
	public void message(Changes changes, String type){
		com.vivebest.mall.entity.Message message = new com.vivebest.mall.entity.Message();
		message.setIp("127.0.0.1");
		message.setIsDraft(false);
		message.setSenderRead(true);
		message.setReceiverRead(false);
		message.setSenderDelete(false);
		message.setReceiverDelete(false);
		message.setReceiver(changes.getMember());
		if("pass".equals(type)){
			message.setTitle("系统提示");
			message.setContent("订单编号为"+changes.getSn()+"的商品换货成功");
		}else{
			message.setTitle("系统提示");
			message.setContent("订单编号为"+changes.getSn()+"的商品换货失败");
		}
		messageService.save(message);
	}
}
