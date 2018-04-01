package cn.sunline.core.util;




import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import cn.sunline.web.system.listener.OnlineListener;

//import cn.sunline.web.cgform.controller.autolist.CgAutoListController;

/**
 * 
 * @author  张代浩
 *
 */
public class DBTypeUtil {
	private static Logger log = Logger.getLogger(DBTypeUtil.class);
	/**
	 * 获取数据库类型
	 * @return
	 */
	public static String getDBType(){
		String retStr="";
		ApplicationContext ctx = OnlineListener.getCtx();
		if (ctx==null) {
			 return retStr;//如果ctx为空，则服务器异常了
		}else{
			org.springframework.orm.hibernate4.LocalSessionFactoryBean sf = (org.springframework.orm.hibernate4.LocalSessionFactoryBean)ctx.getBean("&sessionFactory");
			String dbdialect = sf.getHibernateProperties().getProperty("hibernate.dialect");
			log.debug(dbdialect);
			if (dbdialect.equals("org.hibernate.dialect.MySQLDialect")) {
				retStr="mysql";
			}else if (dbdialect.contains("Oracle")) {//oracle有多个版本的方言
				retStr = "oracle";
			}else if (dbdialect.equals("org.hibernate.dialect.SQLServerDialect")) {
				retStr = "sqlserver";
			}else if (dbdialect.equals("org.hibernate.dialect.PostgreSQLDialect")) {
				retStr = "postgres";
			}
			else if (dbdialect.equals("cn.sunline.core.common.hibernate.SQLServer2008Dialect")) {
				retStr = "sqlserver";
			}
			return retStr;
		}
	}
}
