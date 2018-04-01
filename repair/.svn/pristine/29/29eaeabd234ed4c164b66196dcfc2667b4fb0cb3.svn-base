package cn.sunline.ws.foreignInterface.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RP_USER_CONFIG")
public class RepairServiceUser {
	 private String  CPkId;
	 private String user ;
	 private String password;
	 
	 @Id
	 @Column(name = "C_PK_ID", unique = true, nullable = false, length = 200)
	 public String getCPkId() {
			return this.CPkId;
		}
	public void setCPkId(String CPkId) {
			this.CPkId = CPkId;
		}
	@Column(name = "C_USER", length = 30)
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	@Column(name = "C_PASSWORD", length = 30)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
