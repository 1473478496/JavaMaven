package com.vivebest.mall.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vivebest.mall.core.entity.BaseEntity;

/**
 * Entity - 首页猜你喜欢
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Entity
@Table(name = "gbm_guess_you_like")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_guess_you_like_sequence")
public class GuessYouLike extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	public enum GuessType{
		/**首页*/
		homeType,
		
		/**购物车*/
		cartType,
		
		/**详情*/
		detailType
	}
	
	/**商品*/
	private Product product;
	
	/**排序*/
	private Long sort;
	
	/**描述*/
	private String description;
	
	/**猜你喜欢类型*/
	private GuessType guessType;

	/**
	 * 获取商品
	 * @return
	 */
	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	public Product getProduct() {
		return product;
	}

	/**
	 * 设置商品
	 * @param product 商品
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * 获取排序
	 * @return
	 */
	public Long getSort() {
		return sort;
	}

	/**
	 * 设置排序
	 * @param sort 排序
	 */
	public void setSort(Long sort) {
		this.sort = sort;
	}

	/**
	 * 获取描述
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置描述
	 * @param description 描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取猜你喜欢类型
	 * @return
	 */
	public GuessType getGuessType() {
		return guessType;
	}

	/**
	 * 设置猜你喜欢类型
	 * @param guessType
	 */
	public void setGuessType(GuessType guessType) {
		this.guessType = guessType;
	}
	
}
