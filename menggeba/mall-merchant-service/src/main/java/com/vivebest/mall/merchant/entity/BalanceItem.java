package com.vivebest.mall.merchant.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.search.annotations.DocumentId;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="gbm_trade_balance_item")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_balance_item_sequence")
public class BalanceItem implements Serializable{

    private static final long serialVersionUID = 1730985868023182238L;
    
    /** ID */
    private Long id;
    /** 结算ID */
    private Long balanceId;
    /** 订单id */
    private Long orderId;
    /** 订单总额 */
    private BigDecimal totalAmt;
    /** 订单佣金*/
    private BigDecimal commission;
    /** 结算给商户金额*/
    private BigDecimal pay_amt;
    /** 备注 */
    private String remark;

    @JsonProperty
    @DocumentId
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequenceGenerator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Column(name="balance_id", nullable=false)
    public Long getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
    }
    @Column(name="order_id",nullable=false)
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    @Column(name="total_amt")
    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }
    @Column(name="commission")
    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }
    @Column(name="pay_amt")
    public BigDecimal getPay_amt() {
        return pay_amt;
    }

    public void setPay_amt(BigDecimal pay_amt) {
        this.pay_amt = pay_amt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    

}
