package com.vivebest.mall.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vivebest.mall.core.entity.BaseEntity;

@Entity
@Table(name = "gbm_order_pay")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "order_pay_sequence")
public class OrderPay extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	public enum OrderStatus {
		/** 已完成 */
		completed,

		/** 已取消 */
		cancelled,

		/** 支付中 */
		payment,

		/** 已支付 */
		paymented,

		/** 待支付 */
		unpayment,

		/** 已关闭*/
		closed,
		
		/** 已删除*/
		deleted
	}
	
	public enum PaymentStatus {
		/** 未支付 */
		unpaid,

		/** 部分支付 */
		partialPayment,

		/** 已支付 */
		paid,

	}
	
	/**总金额*/
	private BigDecimal total_amount;
	
	/**已支付金额*/
	private BigDecimal amount_paid;
	
	/**总订单状态*/
	private OrderStatus order_status;
	
	/**支付方式名称*/
	private String payment_method_name;
	
	/**总订单支付状态*/
	private PaymentStatus payment_status;
	
	/**订单*/
	private Set<Order> orders = new HashSet<Order>();

	/**获取总金额
	 * @return
	 */
	public BigDecimal getTotal_amount() {
		return total_amount;
	}

	/**设置总金额
	 * @param total_amount
	 */
	public void setTotal_amount(BigDecimal total_amount) {
		this.total_amount = total_amount;
	}

	/**获取已支付金额
	 * @return
	 */
	public BigDecimal getAmount_paid() {
		return amount_paid;
	}

	/**设置已支付金额
	 * @param amount_paid
	 */
	public void setAmount_paid(BigDecimal amount_paid) {
		this.amount_paid = amount_paid;
	}

	/**获取总订单状态
	 * @return
	 */
	public OrderStatus getOrder_status() {
		return order_status;
	}

	/**设置总订单状态
	 * @param order_status
	 */
	public void setOrder_status(OrderStatus order_status) {
		this.order_status = order_status;
	}

	/**获取支付方式名称
	 * @return
	 */
	public String getPayment_method_name() {
		return payment_method_name;
	}

	/**设置支付方式名称
	 * @param payment_method_name
	 */
	public void setPayment_method_name(String payment_method_name) {
		this.payment_method_name = payment_method_name;
	}

	/**获取总订单支付状态
	 * @return
	 */
	public PaymentStatus getPayment_status() {
		return payment_status;
	}

	/**设置总订单支付状态
	 * @param payment_status
	 */
	public void setPayment_status(PaymentStatus payment_status) {
		this.payment_status = payment_status;
	}

	/**获取订单
	 * @return
	 */
	@OneToMany(mappedBy="orderPay", fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
	public Set<Order> getOrders() {
		return orders;
	}

	/**设置订单
	 * @param orders
	 */
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

}
