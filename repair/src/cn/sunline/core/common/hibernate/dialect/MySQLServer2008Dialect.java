/**  
 * @Title: MySQLServer2008Dialect.java
 * @Package cn.sunline.core.common.hibernate.dialect
 * @Description: TODO
 * @author yokoboy
 * @date 2016-6-7
 */
package cn.sunline.core.common.hibernate.dialect;

import java.sql.Types;

import org.hibernate.dialect.SQLServer2008Dialect;
import org.hibernate.type.StringType;
 

/**
 * ClassName: MySQLServer2008Dialect 
 * @Description: TODO
 * @author yokoboy
 * @date 2016-6-7
 */
public class MySQLServer2008Dialect extends SQLServer2008Dialect {
    public  MySQLServer2008Dialect() {
		super();
		registerHibernateType(Types.NVARCHAR, StringType.INSTANCE.getName());
	}
}
