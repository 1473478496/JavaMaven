package com.vivebest.mall.web.merchant.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.common.Setting;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.core.util.ExportUtil;
import com.vivebest.mall.core.util.SettingUtils;
import com.vivebest.mall.core.util.SpringUtils;
import com.vivebest.mall.entity.Order;
import com.vivebest.mall.entity.Order.OrderStatus;
import com.vivebest.mall.entity.Order.PaymentStatus;
import com.vivebest.mall.entity.Order.ShippingStatus;
import com.vivebest.mall.entity.OrderItem;
import com.vivebest.mall.merchant.entity.Balance;
import com.vivebest.mall.merchant.entity.BalanceItem;
import com.vivebest.mall.merchant.entity.Trade;
import com.vivebest.mall.merchant.service.BalanceItemService;
import com.vivebest.mall.merchant.service.BalanceService;
import com.vivebest.mall.merchant.service.OrderService;
import com.vivebest.mall.merchant.service.TradeService;

/**
 * Controller - 订单
 * 
 * @author vnb shop Team
 *
 * @version 1.0
 */
@Controller("orderController")
@RequestMapping("/orderItem")
public class OrderController extends BaseController {

    private static Logger logger = Logger.getLogger(OrderController.class);

    @Resource(name = "orderServiceImpl2")
    private OrderService orderService;
    
    @Resource(name = "balanceServiceImpl")
    private BalanceService balanceService;

    @Resource(name = "balanceItemServiceImpl")
    private BalanceItemService balanceItemService;

    @Resource(name = "tradeServiceImpl")
    private TradeService tradeService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String queryOrderList(Model model, Order order, OrderStatus orderStatus,
            PaymentStatus paymentStatus, ShippingStatus shippingStatus, Boolean hasExpired,
            Pageable pageable, String bDate, String eDate, String shippingSn, String pname, String psn,
            String memberName,Long tradeId) {
//        Long tradeId=1L;

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        if (bDate != null) {
            try {
                beginDate = format.parse(bDate);
            } catch (ParseException e) {
                logger.error("日期格式化失败", e);
                return ERROR_VIEW;
            }
            Calendar calendar = DateUtils.toCalendar(beginDate);
            calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
            beginDate = calendar.getTime();
        }
        Date endDate = null;
        if (eDate != null) {
            try {
                endDate = format.parse(eDate);
            } catch (ParseException e) {
                logger.error("日期格式化失败", e);
                return ERROR_VIEW;
            }
            Calendar calendar = DateUtils.toCalendar(endDate);
            calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
            endDate = calendar.getTime();
        }

        // 返回起始和结束时间
        model.addAttribute("bDate", bDate);
        model.addAttribute("eDate", eDate);

        model.addAttribute("orderStatus", orderStatus);
        model.addAttribute("paymentStatus", paymentStatus);
        model.addAttribute("shippingStatus", shippingStatus);
        model.addAttribute("hasExpired", hasExpired);

        model.addAttribute("memberName", memberName);
        model.addAttribute("shippingSn", shippingSn);

        Integer os = null;
        Integer ss = null;
        Integer ps = null;
        if (orderStatus != null) {
            os = orderStatus.ordinal();
        }
        if (shippingStatus != null) {
            ss = shippingStatus.ordinal();
        }
        if (paymentStatus != null) {
            ps = paymentStatus.ordinal();
        }

        model.addAttribute("page", orderService.findPage(order, shippingSn, psn, pname, memberName, bDate,
                eDate, os, ss, ps, pageable, hasExpired,tradeId));

        // List<Order> orderList = orderService.findList(Page, row, null, null);
        // model.addAttribute("orderList",orderList);

        return "/order/control";
    }
    
    /**
     * 利用正则表达式判断textValue是否全部由数字组成
     * 
     * @param workbook
     * @param cell
     * @param textValue
     */
    private void setCellValues(Workbook workbook, Row row, CellStyle style2, String textValue, int sn) {
            // 利用正则表达式判断textValue是否全部由数字组成
            Cell cell = row.createCell(sn);
            cell.setCellStyle(style2);
            if (textValue != null) {
                    Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                    Matcher matcher = p.matcher(textValue);
                    if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                    } else {
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
                            Font font3 = workbook.createFont();
                            font3.setColor(HSSFColor.BLUE.index);
                            richString.applyFont(font3);
                            cell.setCellValue(richString);
                    }
            }
    }
    
    
    /**
     * 导出数据
     */
    @RequestMapping(value = "/exportData", method = RequestMethod.POST)
    public void exportData(OrderStatus orderStatus, PaymentStatus paymentStatus, ShippingStatus shippingStatus,
                    Boolean hasExpired, Pageable pageable, String exportbDate, String exporteDate, HttpServletRequest request,
                    HttpServletResponse response) {
            Locale locale = Locale.CANADA;
            SimpleDateFormat frm1 = new SimpleDateFormat("yyyy-MM-dd", locale);
            Date beginDate = null;
            if (exportbDate != null) {
                    try {
                            beginDate = frm1.parse(exportbDate);
                    } catch (ParseException e) {
                            logger.error("日期格式化失败", e);
                    }
                    Calendar calendar = DateUtils.toCalendar(beginDate);
                    calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
                    calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
                    calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
                    beginDate = calendar.getTime();
            }
            Date endDate = null;
            if (exporteDate != null) {
                    try {
                            endDate = frm1.parse(exporteDate);
                    } catch (ParseException e) {
                            logger.error("日期格式化失败", e);
                    }
                    Calendar calendar = DateUtils.toCalendar(endDate);
                    calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
                    calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
                    calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
                    endDate = calendar.getTime();
            }
            try {
                    List<Order> orders = orderService.findList(orderStatus, paymentStatus, shippingStatus, hasExpired, pageable,
                                    beginDate, endDate);
                    Setting setting = SettingUtils.get();
                    String[] headers = { SpringUtils.getMessage("Order.sn", setting.getSiteName()),
                                    SpringUtils.getMessage("Order.member", setting.getSiteName()),
                                    SpringUtils.getMessage("Order.createDate", setting.getSiteName()),
                                    SpringUtils.getMessage("Order.Item.productName", setting.getSiteName()),
                                    SpringUtils.getMessage("Order.Item.productQuantity", setting.getSiteName()),
                                    SpringUtils.getMessage("Order.Item.productSpec", setting.getSiteName()),
                                    SpringUtils.getMessage("Order.amount", setting.getSiteName()),
                                    //导出优惠券 及优惠码
                            
                                    //SpringUtils.getMessage("Order.member.name", setting.getSiteName()),
                                    SpringUtils.getMessage("Order.member.link", setting.getSiteName()),
                                    SpringUtils.getMessage("Order.member.area", setting.getSiteName()),
                                    SpringUtils.getMessage("Order.member.addr", setting.getSiteName()),
                                    SpringUtils.getMessage("Order.consignee", setting.getSiteName()),
                                    SpringUtils.getMessage("Order.consignee.tel", setting.getSiteName()),
                                    SpringUtils.getMessage("Order.consignee.addr", setting.getSiteName()),
                                    SpringUtils.getMessage("Order.orderStatus", setting.getSiteName()),
                                    SpringUtils.getMessage("Order.paymentStatus", setting.getSiteName()),
                                    SpringUtils.getMessage("Order.shippingStatus", setting.getSiteName()),
                                    SpringUtils.getMessage("Order.couponCode", setting.getSiteName()),
                                    SpringUtils.getMessage("Order.coupons", setting.getSiteName()),
                                    SpringUtils.getMessage("商户订单号", "")
                    };
            
                            

                    // 订单编号 会员 下单时间 产品名称 产品数量 型号 订单金额 会员姓名 出生年月日 联系方式 所属省 所属市 详细地址 收货人
                    // 收货人电话 收货人地址 订单状态 支付状态 配送状态

                    // String[] values = { "getSn", "getAmount",
                    // "getMember.getUsername", "getConsignee", "getPaymentMethodName",
                    // "getShippingMethodName", "getOrderStatus", "getPaymentStatus",
                    // "getShippingStatus",
                    // "getCreateDate", "getOrderItems" };
                    // Map<String, String> enumMap = new HashMap<String, String>();
                    // enumMap.put(Order.OrderStatus.class.toString(),
                    // "Order.OrderStatus.");
                    // enumMap.put(Order.PaymentStatus.class.toString(),
                    // "Order.PaymentStatus.");
                    // enumMap.put(Order.ShippingStatus.class.toString(),
                    // "Order.ShippingStatus.");

                    ExportUtil<Order> ex = new ExportUtil<Order>();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CANADA);
                    String filedisplay = SpringUtils.getMessage("Order.shippingExportName", setting.getSiteName())
                                    + sdf.format(new Date()) + ".xls";
                    String title = SpringUtils.getMessage("Order.shippingExportName", setting.getSiteName());
                    // ex.exportExcel(request, response, filedisplay, title, headers,
                    // values, orders, "yyyy-MM-dd", enumMap);
                    orderExportExcel(request, response, filedisplay, title, headers, orders, "yyyy-MM-dd HH:mm:ss");
            } catch (Exception e) {
                    logger.error(e);
            }
    }
    
    /**
     * 导出订单到excel
     * @param request
     * @param response
     * @param filedisplay
     * @param title
     * @param headers
     * @param dataset
     * @param pattern
     * @throws Exception
     */
    public void orderExportExcel(HttpServletRequest request, HttpServletResponse response, String filedisplay,
                    String title, String[] headers, Collection<Order> dataset, String pattern) throws Exception {
            // 声明一个工作薄
            Workbook workbook = new HSSFWorkbook();

            // 生成一个表格
            Sheet sheet = workbook.createSheet(title);
            // 设置表格默认列宽度为15个字节
            sheet.setDefaultColumnWidth((short) 15);
            // 生成一个样式
            CellStyle style = workbook.createCellStyle();
            // 设置这些样式
            style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            // 生成一个字体
            Font font = workbook.createFont();
            font.setColor(HSSFColor.VIOLET.index);
            font.setFontHeightInPoints((short) 12);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            // 把字体应用到当前的样式
            style.setFont(font);
            // 生成并设置另一个样式
            CellStyle style2 = workbook.createCellStyle();
            style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            // 生成另一个字体
            Font font2 = workbook.createFont();
            font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            // 把字体应用到当前的样式
            style2.setFont(font2);

            // 产生表格标题行
            Row row = sheet.createRow(0);
            for (short i = 0; i < headers.length; i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellStyle(style);
                    HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                    cell.setCellValue(text);
            }

            // 遍历集合数据，产生数据行
            Iterator<Order> it = dataset.iterator();
            int index = 0;
            DecimalFormat df = new DecimalFormat("0.00");
            Setting setting = SettingUtils.get();
            while (it.hasNext()) {
                    Order t = it.next();
                    if (t != null) {
                            Iterator<OrderItem> iOrderItem = t.getOrderItems().iterator();
                            while (iOrderItem.hasNext()) {
                                    // 订单编号 会员 下单时间 产品名称 产品数量 型号 订单金额 会员姓名 出生年月日 联系方式 所属省 所属市
                                    // 详细地址 收货人
                                    // 收货人电话 收货人地址 订单状态 支付状态 配送状态6
                                    try {
                                            index++;
                                            OrderItem orderItem = iOrderItem.next();
                                            row = sheet.createRow(index);

                                            setCellValues(workbook, row, style2, t.getSn(), 0);

                                            if (t.getMember() != null)
                                                    setCellValues(workbook, row, style2, t.getMember().getUsername(), 1);
                                            else
                                                    setCellValues(workbook, row, style2, "", 1);

                                            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                                            setCellValues(workbook, row, style2, sdf.format(t.getCreateDate()), 2);
                                            setCellValues(workbook, row, style2, orderItem.getName(), 3);
                                            setCellValues(workbook, row, style2, orderItem.getQuantity().toString(), 4);
                                            if (orderItem.getProduct() != null) 
                                                    setCellValues(workbook, row, style2, orderItem.getProduct().getSn(), 5);
                                            else
                                                    setCellValues(workbook, row, style2, "", 5);
                                            if (t.getAmount() != null)
                                                    setCellValues(workbook, row, style2, "￥" + df.format(t.getAmount()), 6);
                                                                                                                                    
                                            if (t.getMember() != null) {
                                                    /*if (t.getMember().getName() != null
                                                                    && !StringUtils.isNotEmpty(t.getMember().getName()))
                                                            setCellValues(workbook, row, style2, t.getMember().getName(), 7);
                                                    else
                                                            setCellValues(workbook, row, style2, "", 7);*/
                                                    /*if (t.getMember().getBirth() != null
                                                                    && !StringUtils.isNotEmpty(t.getMember().getBirth().toString()))
                                                            setCellValues(workbook, row, style2, sdf.format(t.getMember().getBirth()), 8);
                                                    else
                                                            setCellValues(workbook, row, style2, "", 8);*/
                                                    setCellValues(workbook, row, style2, t.getMember().getMobile(), 7);
                                                    if (t.getAreaName() != null)
                                                            setCellValues(workbook, row, style2, t.getAreaName(), 8);
                                                    else
                                                            setCellValues(workbook, row, style2, "", 8);
                                                    if(t.getAddress()!=null)
                                                    {
                                                    setCellValues(workbook, row, style2, ascii2native(t.getAddress()), 9);
                                                    }
                                                    setCellValues(workbook, row, style2, ascii2native(t.getConsignee()), 10);
                                                    setCellValues(workbook, row, style2, t.getPhone(), 11);
                                                    if(t.getAddress()!=null)
                                                    {
                                                    setCellValues(workbook, row, style2, ascii2native(t.getAreaName() + t.getAddress()), 12);
                                                    }
                                                    setCellValues(workbook, row, style2, SpringUtils.getMessage(
                                                                    "Order.OrderStatus." + t.getOrderStatus().toString(), setting.getSiteName()), 13);
                                                    setCellValues(workbook, row, style2, t.getPaymentMethodName().toString(), 14);
                                                    setCellValues(workbook, row, style2, t.getShippingMethodName().toString(), 15);
                                                    
                                            }
                                            if (t.getCouponCode() != null)
                                                    setCellValues(workbook, row, style2, t.getCouponCode().getCode().toString(), 16);
                                            else
                                                    setCellValues(workbook, row, style2, "", 16);
                                            
                                            if (t.getCouponCode() != null&&t.getCouponCode().getCoupon()!=null)
                                                    setCellValues(workbook, row, style2, t.getCouponCode().getCoupon().getName(), 17);
                                            else
                                                    setCellValues(workbook, row, style2, "", 17);
                                            if(t.getPayMerOrderNo()!=null){
                                                    setCellValues(workbook, row, style2, t.getPayMerOrderNo().toString(), 18);
                                            }else {
                                                    setCellValues(workbook, row, style2, "", 18);
                                            }
                                    } catch (SecurityException e) {
                                            logger.error(e);
                                    } catch (Exception e) {
                                            logger.error(e);
                                    }
                            }
                    }
            }

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workbook.write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/x-msdownload");
            // filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
            response.addHeader("Content-Disposition",
                            "attachment;filename=" + new String(filedisplay.getBytes(), "iso-8859-1"));
            OutputStream out = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                    bis = new BufferedInputStream(is);
                    bos = new BufferedOutputStream(out);
                    byte[] buff = new byte[2048];
                    int bytesRead;
                    // Simple read/write loop.
                    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                            bos.write(buff, 0, bytesRead);
                    }
            } catch (final IOException e) {
                    throw e;
            } finally {
                    if (bis != null) {
                            bis.close();
                    }
                    if (bos != null) {
                            bos.close();
                    }
            }
    }
    

    /***
     * unicode转中文
     * 
     * @param theString
     * @return
     */

    public static String ascii2native(String ascii) {

            Pattern pattern = Pattern.compile("\\&\\#(\\d+)");
            ascii = ascii.replaceAll("&gt;", ">");
            ascii = ascii.replaceAll("&#", ";&#");
            StringBuilder sb = new StringBuilder();
            String[] childs = ascii.split(";");
            for (String child : childs) {
                    if (child.contains("&#")) {
                            Matcher m = pattern.matcher(child);
                            while (m.find())
                                    sb.append((char) Integer.valueOf(m.group(1)).intValue());

                    } else
                            sb.append(child);
            }

            ascii = sb.toString();

            return ascii;

    }
    /**
     * 订单修改
     * @param id
     * @param model
     * @param tradeId
     * @return
     */
    @RequestMapping(value = "/orderAlter", method = RequestMethod.GET)
    public String orderAlter(Long id, Model model, Long tradeId) {
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
        return "order/alter";
        
    }
    /**
     * 修改提交
     * @param id
     * @param model
     * @param amountPaid
     * @return
     */
    @RequestMapping(value="/alter",method=RequestMethod.GET)
    public String alter(Long id ,Model model,BigDecimal amountPaid){
        Order order = orderService.find(id);
        order.setAmountPaid(amountPaid);
        orderService.update(order);
        return "redirect:/orderItem/list.do";
    }

}
