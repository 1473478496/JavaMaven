package com.vivebest.mall.merchant.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vivebest.mall.core.entity.BaseEntity;

@Entity
@Table(name="gbm_commission_rate")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_commission_rate_sequence")
public class CommissionRate extends BaseEntity implements Serializable{

    
    private static final long serialVersionUID = -3665275713942094916L;

    public enum Type{
        /**边民佣金*/
        marginal,
        /**商户佣金*/
        trade
    }
    public enum Method{
        /**按笔数收*/
        count,
        /**按比例收*/
        Proportion
    }
    
    public enum Status{
        /** 启用 */
        start,
        /** 停用 */
        stop
    }
    
    /**佣金值*/
    private BigDecimal commission;
    /**备注*/
    private String remark;
    /**佣金类型*/
    private Type type;
    /**计算方式*/
    private Method method;
    /**状态*/
    private Status status;
    public BigDecimal getCommission() {
        return commission;
    }
    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Column(nullable = false)
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public Method getMethod() {
        return method;
    }
    public void setMethod(Method method) {
        this.method = method;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    
    
    

}
