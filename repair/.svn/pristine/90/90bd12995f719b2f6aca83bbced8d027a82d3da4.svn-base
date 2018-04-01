package cn.sunline.repair.service.impl;



import java.io.Serializable;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sunline.core.common.service.impl.CommonServiceImpl;
import cn.sunline.repair.entity.TSSmsTemplateEntity;
import cn.sunline.repair.service.TSSmsTemplateServiceI;


@Service("tSSmsTemplateService")
@Transactional
public class TSSmsTemplateServiceImpl extends CommonServiceImpl implements TSSmsTemplateServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TSSmsTemplateEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TSSmsTemplateEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TSSmsTemplateEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TSSmsTemplateEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TSSmsTemplateEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TSSmsTemplateEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TSSmsTemplateEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCrtName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCrtCde()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCrtTm()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdCde()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdTm()));
 		sql  = sql.replace("#{template_type}",String.valueOf(t.getTemplateType()));
 		sql  = sql.replace("#{template_name}",String.valueOf(t.getTemplateName()));
 		sql  = sql.replace("#{template_content}",String.valueOf(t.getTemplateContent()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}