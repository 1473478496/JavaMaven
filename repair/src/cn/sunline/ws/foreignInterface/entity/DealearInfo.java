package cn.sunline.ws.foreignInterface.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "RP_DEALER_INFO")
public class DealearInfo {
		private String id;
		
		private String policyNo;//保单号

		private String reportNo;//报案号
	
		private String repairSerialNo;//送修码
		
		private String frmNo;//车架号
		
		private String nuclearDamageCode; // 核损员代码
		
		private String nuclearDamageName; // 核损员姓名
		
		private String dealerNo; // 车商编码
		
		private String dealerName; // 车商名称
		
		private String dealerPlace; // 车商地址（省+市+区县+街道的汉语名称）
		
		private String provinceCode;//车商地址（省代码）
		
		private String landCode;//车商地址（地市代码）

		private String countyCode;//车商地址（区县代码）
		
		private String  street;//车商地址（街道）
		
		private String  dealerTelphone;//车商电话
		
		private String  dealerPeople;//车商联系人
		
		private String  crtCde;
		
		private Date crtTm;
		
		private String  updCde;
		
		private Date updTm;
		
		@Id
		@Column(name = "ID", unique = true, nullable = false, length = 50)
		@GenericGenerator(name="idGenerator", strategy="uuid")
		@GeneratedValue(generator="idGenerator")
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
		@Column(name = "POLICY_NO", length = 50)
		public String getPolicyNo() {
			return policyNo;
		}
		
		public void setPolicyNo(String policyNo) {
			this.policyNo = policyNo;
		}
		
		@Column(name = "REPORT_NO", nullable=false,length = 50)
		public String getReportNo() {
			return reportNo;
		}

		public void setReportNo(String reportNo) {
			this.reportNo = reportNo;
		}
		@Column(name = "REPAIR_SERIAL_NO", length = 50)
		public String getRepairSerialNo() {
			return repairSerialNo;
		}

		public void setRepairSerialNo(String repairSerialNo) {
			this.repairSerialNo = repairSerialNo;
		}
		@Column(name = "FRM_NO",nullable=false, length = 50)
		public String getFrmNo() {
			return frmNo;
		}
		
		public void setFrmNo(String frmNo) {
			this.frmNo = frmNo;
		}
		@Column(name = "NUCLEAR_DAMAGE_CODE", length = 100)
		public String getNuclearDamageCode() {
			return nuclearDamageCode;
		}

		public void setNuclearDamageCode(String nuclearDamageCode) {
			this.nuclearDamageCode = nuclearDamageCode;
		}
		@Column(name = "NUCLEAR_DAMAGE_NAME", length = 100)
		public String getNuclearDamageName() {
			return nuclearDamageName;
		}

		public void setNuclearDamageName(String nuclearDamageName) {
			this.nuclearDamageName = nuclearDamageName;
		}
		@Column(name = "DEALER_NO", length = 50)
		public String getDealerNo() {
			return dealerNo;
		}

		public void setDealerNo(String dealerNo) {
			this.dealerNo = dealerNo;
		}
		@Column(name = "DEALER_NAME", length = 50)
		public String getDealerName() {
			return dealerName;
		}

		public void setDealerName(String dealerName) {
			this.dealerName = dealerName;
		}
		@Column(name = "DEALER_PLACE", length = 50)
		public String getDealerPlace() {
			return dealerPlace;
		}
		
		public void setDealerPlace(String dealerPlace) {
			this.dealerPlace = dealerPlace;
		}
		@Column(name = "PROVINCE_CODE",length = 50)
		public String getProvinceCode() {
			return provinceCode;
		}

		public void setProvinceCode(String provinceCode) {
			this.provinceCode = provinceCode;
		}

		@Column(name = "LAND_CODE",length = 50)
		public String getLandCode() {
			return landCode;
		}

		public void setLandCode(String landCode) {
			this.landCode = landCode;
		}
		@Column(name = "COUNTY_CODE",length = 50)
		public String getCountyCode() {
			return countyCode;
		}

		public void setCountyCode(String countyCode) {
			this.countyCode = countyCode;
		}
		@Column(name = "STREET",length = 50)
		public String getStreet() {
			return street;
		}

		public void setStreet(String street) {
			this.street = street;
		}
		@Column(name = "DEALER_TELPHONE",length = 50)
		public String getDealerTelphone() {
			return dealerTelphone;
		}

		public void setDealerTelphone(String dealerTelphone) {
			this.dealerTelphone = dealerTelphone;
		}
		@Column(name = "DEALER_PEOPLE",length = 50)
		public String getDealerPeople() {
			return dealerPeople;
		}

		public void setDealerPeople(String dealerPeople) {
			this.dealerPeople = dealerPeople;
		}
		@Column(name = "CRT_TM",length = 50)
		public Date getCrtTm() {
			return crtTm;
		}

		public void setCrtTm(Date crtTm) {
			this.crtTm = crtTm;
		}
		@Column(name = "UPD_TM",length = 50)
		public Date getUpdTm() {
			return updTm;
		}
		
		public void setUpdTm(Date updTm) {
			this.updTm = updTm;
		}
		
		@Column(name = "CRT_CDE",length = 50)
		public String getCrtCde() {
			return crtCde;
		}

		public void setCrtCde(String crtCde) {
			this.crtCde = crtCde;
		}
		@Column(name = "UPD_CDE",length = 50)
		public String getUpdCde() {
			return updCde;
		}

		public void setUpdCde(String updCde) {
			this.updCde = updCde;
		}

}
