package com.vivebest.mall.merchant.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.log4j.Logger;

import com.vivebest.mall.core.entity.BaseEntity;
@Entity
@Table(name="gbm_trade_balance")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_balance_sequence")
public class Balance extends BaseEntity  implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = -6915720488787161298L;
    private static Logger logger = Logger.getLogger(Balance.class);
    
    public enum Status{
        /**待审核*/
        uncheck,
        /**审核通过*/
        approve,
        /**审核拒绝*/
        unapprove,
        /**结算成功*/
        settlesuccess,
        /**结算失败*/
        settlefailures
    }
    /**状态*/
    private Status status;
    /** 结算编号 */
    private String sn;
    /** 结算单号 */
    private String balanceNo;
    /** 结算流水号 */
    private String serialNo;
    /** 商户ID */
    private Long tradeId;
    /** 申请日期 */
    private Date applyDate;
    /** 申请人 */
    private String applicant;
    /** 申请说明 */
    private String applyDesc;
    /** 审核日期 */
    private Date auditDate;
    /** 审核人 */
    private String approving;
    /**审核意见*/
    private String auditDesc;
    /**结算银行*/
    private String bankName;
    /**银行开户名*/
    private String cardName;
    /**银行卡号*/
    private String cardNo;
    /**结算人*/
    private String operating;
    /**备注*/
    private String remark;
    /**订单总额*/
    private BigDecimal totalAmt;
    /**佣金总额*/
    private BigDecimal commission;
    /**结算给商户的金额*/
    private BigDecimal payAmt;
    
    
    
    @Column(name = "balance_no",updatable = false, unique = true, length = 10)
    public String getBalanceNo() {
        return balanceNo;
    }
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }
    @Column(name = "trade_id")
    public Long getTradeId() {
        return tradeId;
    }
    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }
    @Column(name = "apply_date")
    public Date getApplyDate() {
        return applyDate;
    }
    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }
    public String getApplicant() {
        return applicant;
    }
    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }
    @Column(name = "apply_desc")
    public String getApplyDesc() {
        return applyDesc;
    }
    public void setApplyDesc(String applyDesc) {
        this.applyDesc = applyDesc;
    }
    @Column(name = "audit_date")
    public Date getAuditDate() {
        return auditDate;
    }
    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }
    public String getApproving() {
        return approving;
    }
    public void setApproving(String approving) {
        this.approving = approving;
    }
    @Column(name = "audit_desc")
    public String getAuditDesc() {
        return auditDesc;
    }
    public void setAuditDesc(String auditDesc) {
        this.auditDesc = auditDesc;
    }
    @Column(name = "bank_name")
    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    @Column(name = "card_name")
    public String getCardName() {
        return cardName;
    }
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
    @Column(name = "card_no")
    public String getCardNo() {
        return cardNo;
    }
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    public String getOperating() {
        return operating;
    }
    public void setOperating(String operating) {
        this.operating = operating;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    @Column(name = "total_amt")
    public BigDecimal getTotalAmt() {
        return totalAmt;
    }
    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }
    @Column(name = "commission")
    public BigDecimal getCommission() {
        return commission;
    }
    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }
    @Column(name = "pay_amt")
    public BigDecimal getPayAmt() {
        return payAmt;
    }
    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }
    @Column(name = "serial_no")
    public String getSerialNo() {
        return serialNo;
    }
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
    public String getSn() {
        return sn;
    }
    public void setSn(String sn) {
        this.sn = sn;
    }
    
    
    
    
    

}
