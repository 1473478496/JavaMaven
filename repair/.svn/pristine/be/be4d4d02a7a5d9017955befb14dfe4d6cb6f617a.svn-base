package cn.sunline.repair.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 1
 * @author zhangdaihao
 * @date 2016-10-21 19:29:52
 * @version V1.0   
 *
 */
@Entity
@Table(name = "rp_question")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class QuestionEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	private java.lang.String questionName;
	private java.lang.String questionContent;
	/**
	 *方法: 取得java.lang.Long
	 *@return: java.lang.Long  id
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=200)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	
	@Column(name ="questionName")
	public java.lang.String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(java.lang.String questionName) {
		this.questionName = questionName;
	}

	@Column(name ="questioncontent")
	public java.lang.String getQuestionContent() {
		return questionContent;
	}

	public void setQuestionContent(java.lang.String questionContent) {
		this.questionContent = questionContent;
	}

}
