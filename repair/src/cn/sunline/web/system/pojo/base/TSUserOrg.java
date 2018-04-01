package cn.sunline.web.system.pojo.base;



import javax.persistence.*;

import cn.sunline.core.common.entity.IdEntity;

/**
 * 用户-组织机构 实体
 * <p/>
 * <p><b>User:</b> zhanggm <a href="mailto:guomingzhang2008@gmail.com">guomingzhang2008@gmail.com</a></p>
 * <p><b>Date:</b> 2014-08-22 15:39</p>
 *
 * @author 张国明
 */
@Entity
@Table(name = "t_s_user_org")
public class TSUserOrg extends IdEntity implements java.io.Serializable {
    private TSUser tsUser;
    private TSDepart tsDepart;
    private TSRole tsRole;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    public TSUser getTsUser() {
        return tsUser;
    }

    public void setTsUser(TSUser tsDepart) {
        this.tsUser = tsDepart;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "org_id")
    public TSDepart getTsDepart() {
        return tsDepart;
    }

    public void setTsDepart(TSDepart tsDepart) {
        this.tsDepart = tsDepart;
    }
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
	public TSRole getTsRole() {
		return tsRole;
	}

	public void setTsRole(TSRole tsRole) {
		this.tsRole = tsRole;
	}
    
}
