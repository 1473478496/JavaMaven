package cn.sunline.web.system.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.sunline.core.common.hibernate.qbc.CriteriaQuery;
import cn.sunline.core.common.service.impl.CommonServiceImpl;
import cn.sunline.core.util.BrowserUtils;
import cn.sunline.core.util.ContextHolderUtils;
import cn.sunline.core.util.DateUtils;
import cn.sunline.core.util.MutiLangUtil;
import cn.sunline.core.util.ResourceUtil;
import cn.sunline.core.util.StringUtil;
import cn.sunline.core.util.oConvertUtils;
import cn.sunline.web.system.dao.JeecgDictDao;
import cn.sunline.web.system.pojo.base.DictEntity;
import cn.sunline.web.system.pojo.base.TSDatalogEntity;
import cn.sunline.web.system.pojo.base.TSFunction;
import cn.sunline.web.system.pojo.base.TSIcon;
import cn.sunline.web.system.pojo.base.TSLog;
import cn.sunline.web.system.pojo.base.TSRole;
import cn.sunline.web.system.pojo.base.TSRoleFunction;
import cn.sunline.web.system.pojo.base.TSRoleUser;
import cn.sunline.web.system.pojo.base.TSType;
import cn.sunline.web.system.pojo.base.TSTypegroup;
import cn.sunline.web.system.pojo.base.TSUser;
import cn.sunline.web.system.pojo.base.TSUserOrg;
import cn.sunline.web.system.service.SystemService;

@Service("systemService")
@Transactional
public class SystemServiceImpl extends CommonServiceImpl implements SystemService {
	@Autowired
	private JeecgDictDao jeecgDictDao;

	public TSUser checkUserExits(TSUser user) throws Exception {
		return this.commonDao.getUserByUserIdAndUserNameExits(user);
	}

	public List<DictEntity> queryDict(String dicTable, String dicCode,String dicText){
		List<DictEntity> dictList = null;
		//step.1 如果没有字典表则使用系统字典表
		if(StringUtil.isEmpty(dicTable)){
			dictList = jeecgDictDao.querySystemDict(dicCode);
			for(DictEntity t:dictList){
				t.setTypename(MutiLangUtil.getMutiLangInstance().getLang(t.getTypename()));
			}
		}else {
			dicText = StringUtil.isEmpty(dicText, dicCode);
			dictList = jeecgDictDao.queryCustomDict(dicTable, dicCode, dicText);
		}
		return dictList;
	}

	/**
	 * 添加日志
	 */
	public void addLog(String logcontent, Short loglevel, Short operatetype) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String broswer = BrowserUtils.checkBrowse(request);
		TSLog log = new TSLog();
		log.setLogcontent(logcontent);
		log.setLoglevel(loglevel);
		log.setOperatetype(operatetype);
		log.setNote(oConvertUtils.getIp());
		log.setBroswer(broswer);
		log.setOperatetime(DateUtils.gettimestamp());
		log.setTSUser(ResourceUtil.getSessionUserName());
		commonDao.save(log);
	}

	/**
	 * 根据类型编码和类型名称获取Type,如果为空则创建一个
	 *
	 * @param typecode
	 * @param typename
	 * @return
	 */
	public TSType getType(String typecode, String typename, TSTypegroup tsTypegroup) {
		//TSType actType = commonDao.findUniqueByProperty(TSType.class, "typecode", typecode,tsTypegroup.getId());
		List<TSType> ls = commonDao.findHql("from TSType where typecode = ? and typegroupid = ?",typecode,tsTypegroup.getId());
		TSType actType = null;
		if (ls == null || ls.size()==0) {
			actType = new TSType();
			actType.setTypecode(typecode);
			actType.setTypename(typename);
			actType.setTSTypegroup(tsTypegroup);
			commonDao.save(actType);
		}else{
			actType = ls.get(0);
		}
		return actType;

	}

	/**
	 * 根据类型分组编码和名称获取TypeGroup,如果为空则创建一个
	 *
	 * @param typecode
	 * @param typename
	 * @return
	 */
	public TSTypegroup getTypeGroup(String typegroupcode, String typgroupename) {
		TSTypegroup tsTypegroup = commonDao.findUniqueByProperty(TSTypegroup.class, "typegroupcode", typegroupcode);
		if (tsTypegroup == null) {
			tsTypegroup = new TSTypegroup();
			tsTypegroup.setTypegroupcode(typegroupcode);
			tsTypegroup.setTypegroupname(typgroupename);
			commonDao.save(tsTypegroup);
		}
		return tsTypegroup;
	}


	public TSTypegroup getTypeGroupByCode(String typegroupCode) {
		TSTypegroup tsTypegroup = commonDao.findUniqueByProperty(TSTypegroup.class, "typegroupcode", typegroupCode);
		return tsTypegroup;
	}


	public void initAllTypeGroups() {
		List<TSTypegroup> typeGroups = this.commonDao.loadAll(TSTypegroup.class);
		for (TSTypegroup tsTypegroup : typeGroups) {
			ResourceUtil.allTypeGroups.put(tsTypegroup.getTypegroupcode().toLowerCase(), tsTypegroup);
			List<TSType> types = this.commonDao.findByProperty(TSType.class, "TSTypegroup.id", tsTypegroup.getId());
			ResourceUtil.allTypes.put(tsTypegroup.getTypegroupcode().toLowerCase(), types);
		}
	}


	public void refleshTypesCach(TSType type) {
		TSTypegroup tsTypegroup = type.getTSTypegroup();
		TSTypegroup typeGroupEntity = this.commonDao.get(TSTypegroup.class, tsTypegroup.getId());
		List<TSType> types = this.commonDao.findByProperty(TSType.class, "TSTypegroup.id", tsTypegroup.getId());
		ResourceUtil.allTypes.put(typeGroupEntity.getTypegroupcode().toLowerCase(), types);
	}


	public void refleshTypeGroupCach() {
		ResourceUtil.allTypeGroups.clear();
		List<TSTypegroup> typeGroups = this.commonDao.loadAll(TSTypegroup.class);
		for (TSTypegroup tsTypegroup : typeGroups) {
			ResourceUtil.allTypeGroups.put(tsTypegroup.getTypegroupcode().toLowerCase(), tsTypegroup);
		}
	}

	/**
	 * 根据角色ID 和 菜单Id 获取 具有操作权限的按钮Codes
	 * @param roleId
	 * @param functionId
	 * @return
	 */
	public Set<String> getOperationCodesByRoleIdAndFunctionId(String roleId, String functionId) {
		Set<String> operationCodes = new HashSet<String>();
		TSRole role = commonDao.get(TSRole.class, roleId);
		CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
		cq1.eq("TSRole.id", role.getId());
		cq1.eq("TSFunction.id", functionId);
		cq1.add();
		List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
		if (null != rFunctions && rFunctions.size() > 0) {
			TSRoleFunction tsRoleFunction = rFunctions.get(0);
			if (null != tsRoleFunction.getOperation()) {
				String[] operationArry = tsRoleFunction.getOperation().split(",");
				for (int i = 0; i < operationArry.length; i++) {
					operationCodes.add(operationArry[i]);
				}
			}
		}
		return operationCodes;
	}

	/**
	 * 根据用户ID 和 菜单Id 获取 具有操作权限的按钮Codes
	 * @param roleId
	 * @param functionId
	 * @return
	 */
	public Set<String> getOperationCodesByUserIdAndFunctionId(String departId,String userId, String functionId) {
		Set<String> operationCodes = new HashSet<String>();
		//List<TSRoleUser> rUsers = findByProperty(TSRoleUser.class, "TSUser.id", userId);
//		for (TSRoleUser ru : rUsers) {
//			TSRole role = ru.getTSRole();
//			CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
//			cq1.eq("TSRole.id", role.getId());
//			cq1.eq("TSFunction.id", functionId);
//			cq1.add();
//			List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
//			if (null != rFunctions && rFunctions.size() > 0) {
//				TSRoleFunction tsRoleFunction = rFunctions.get(0);
//				if (null != tsRoleFunction.getOperation()) {
//					String[] operationArry = tsRoleFunction.getOperation().split(",");
//					for (int i = 0; i < operationArry.length; i++) {
//						operationCodes.add(operationArry[i]);
//					}
//				}
//			}
//		}
		//List<TSUserOrg> rUsers = findByProperty(TSUserOrg.class, "tsUser.id", userId);
		String hql="from TSUserOrg where tsUser.id='"+userId+"' and tsDepart.id='"+departId+"'";
		List<TSUserOrg> uorList=findByQueryString(hql);
		for (TSUserOrg ru : uorList) {
			TSRole role = ru.getTsRole();
			CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
			if(role==null){
				cq1.eq("TSRole.id", " ");
			}else{
				cq1.eq("TSRole.id", role.getId());
			}
			cq1.eq("TSFunction.id", functionId);
			cq1.add();
			List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
			if (null != rFunctions && rFunctions.size() > 0) {
				TSRoleFunction tsRoleFunction = rFunctions.get(0);
				if (null != tsRoleFunction.getOperation()) {
					String[] operationArry = tsRoleFunction.getOperation().split(",");
					for (int i = 0; i < operationArry.length; i++) {
						operationCodes.add(operationArry[i]);
					}
				}
			}
		}
		return operationCodes;
	}

	public void flushRoleFunciton(String departId,String id, TSFunction newFunction) {
		TSFunction functionEntity = this.getEntity(TSFunction.class, id);
		if (functionEntity.getTSIcon() == null || !StringUtil.isNotEmpty(functionEntity.getTSIcon().getId())) {
			return;
		}
		TSIcon oldIcon = this.getEntity(TSIcon.class, functionEntity.getTSIcon().getId());
		if (!oldIcon.getIconClas().equals(newFunction.getTSIcon().getIconClas())) {
			// 刷新缓存
			HttpSession session = ContextHolderUtils.getSession();
			TSUser user = ResourceUtil.getSessionUserName();
//			List<TSRoleUser> rUsers = this.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
//			for (TSRoleUser ru : rUsers) {
//				TSRole role = ru.getTSRole();
//				session.removeAttribute(role.getId());
//			}
			String hql="from TSUserOrg where tsUser.id='"+user.getId()+"' and tsDepart.id='"+departId+"'";
			List<TSUserOrg> rUsers = this.findByQueryString(hql);
			for (TSUserOrg ru : rUsers) {
				TSRole role = ru.getTsRole();
				session.removeAttribute(role.getId());
			}
		}
	}

    public String generateOrgCode(String id, String pid) {

        int orgCodeLength = 2; // 默认编码长度
        if ("3".equals(ResourceUtil.getOrgCodeLengthType())) { // 类型2-编码长度为3，如001
            orgCodeLength = 3;
        }


        String  newOrgCode = "";
        if(!StringUtils.hasText(pid)) { // 第一级编码
            String sql = "select max(t.org_code) orgCode from t_s_depart t where t.parentdepartid is null";
            Map<String, Object> pOrgCodeMap = commonDao.findOneForJdbc(sql);
            if(pOrgCodeMap.get("orgCode") != null) {
                String curOrgCode = pOrgCodeMap.get("orgCode").toString();
                newOrgCode = String.format("%0" + orgCodeLength + "d", Integer.valueOf(curOrgCode) + 1);
            } else {
                newOrgCode = String.format("%0" + orgCodeLength + "d", 1);
            }
        } else { // 下级编码
            String sql = "select max(t.org_code) orgCode from t_s_depart t where t.parentdepartid = ?";
            Map<String, Object> orgCodeMap = commonDao.findOneForJdbc(sql, pid);
            if(orgCodeMap.get("orgCode") != null) { // 当前基本有编码时
                String curOrgCode = orgCodeMap.get("orgCode").toString();
                String pOrgCode = curOrgCode.substring(0, curOrgCode.length() - orgCodeLength);
                String subOrgCode = curOrgCode.substring(curOrgCode.length() - orgCodeLength, curOrgCode.length());
                newOrgCode = pOrgCode + String.format("%0" + orgCodeLength + "d", Integer.valueOf(subOrgCode) + 1);
            } else { // 当前级别没有编码时
                String pOrgCodeSql = "select max(t.org_code) orgCode from t_s_depart t where t.id = ?";
                Map<String, Object> pOrgCodeMap = commonDao.findOneForJdbc(pOrgCodeSql, pid);
                String curOrgCode = pOrgCodeMap.get("orgCode").toString();
                newOrgCode = curOrgCode + String.format("%0" + orgCodeLength + "d", 1);
            }
        }

        return newOrgCode;
    }

	public Set<String> getOperationCodesByRoleIdAndruleDataId(String roleId,
			String functionId) {
		Set<String> operationCodes = new HashSet<String>();
		TSRole role = commonDao.get(TSRole.class, roleId);
		CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
		cq1.eq("TSRole.id", role.getId());
		cq1.eq("TSFunction.id", functionId);
		cq1.add();
		List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
		if (null != rFunctions && rFunctions.size() > 0) {
			TSRoleFunction tsRoleFunction = rFunctions.get(0);
			if (null != tsRoleFunction.getDataRule()) {
				String[] operationArry = tsRoleFunction.getDataRule().split(",");
				for (int i = 0; i < operationArry.length; i++) {
					operationCodes.add(operationArry[i]);
				}
			}
		}
		return operationCodes;
	}

	public Set<String> getOperationCodesByUserIdAndDataId(String departId,String userId,
			String functionId) {
		// TODO Auto-generated method stub
		Set<String> dataRulecodes = new HashSet<String>();
//		List<TSRoleUser> rUsers = findByProperty(TSRoleUser.class, "TSUser.id", userId);
//		for (TSRoleUser ru : rUsers) {
//			TSRole role = ru.getTSRole();
//			CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
//			cq1.eq("TSRole.id", role.getId());
//			cq1.eq("TSFunction.id", functionId);
//			cq1.add();
//			List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
//			if (null != rFunctions && rFunctions.size() > 0) {
//				TSRoleFunction tsRoleFunction = rFunctions.get(0);
//				if (null != tsRoleFunction.getDataRule()) {
//					String[] operationArry = tsRoleFunction.getDataRule().split(",");
//					for (int i = 0; i < operationArry.length; i++) {
//						dataRulecodes.add(operationArry[i]);
//					}
//				}
//			}
//		}
		
		//List<TSUserOrg> rUsers = findByProperty(TSUserOrg.class, "tsUser.id", userId);
		String hql="from TSUserOrg where tsUser.id='"+userId+"' and tsDepart.id='"+departId+"'";
		List<TSUserOrg> uorList=findByQueryString(hql);
		for (TSUserOrg ru : uorList) {
			TSRole role = ru.getTsRole();
			CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
			if(role==null){
				cq1.eq("TSRole.id", " ");
			}else{				
				cq1.eq("TSRole.id", role.getId());
			}
			cq1.eq("TSFunction.id", functionId);
			cq1.add();
			List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
			if (null != rFunctions && rFunctions.size() > 0) {
				TSRoleFunction tsRoleFunction = rFunctions.get(0);
				if (null != tsRoleFunction.getDataRule()) {
					String[] operationArry = tsRoleFunction.getDataRule().split(",");
					for (int i = 0; i < operationArry.length; i++) {
						dataRulecodes.add(operationArry[i]);
					}
				}
			}
		}
		return dataRulecodes;
	}
	/**
	 * 加载所有图标
	 * @return
	 */
	public  void initAllTSIcons() {
		List<TSIcon> list = this.loadAll(TSIcon.class);
		for (TSIcon tsIcon : list) {
			ResourceUtil.allTSIcons.put(tsIcon.getId(), tsIcon);
		}
	}
	/**
	 * 更新图标
	 * @param icon
	 */
	public  void upTSIcons(TSIcon icon) {
		ResourceUtil.allTSIcons.put(icon.getId(), icon);
	}
	/**
	 * 更新图标
	 * @param icon
	 */
	public  void delTSIcons(TSIcon icon) {
		ResourceUtil.allTSIcons.remove(icon.getId());
	}

	@Override
	public void addDataLog(String tableName, String dataId, String dataContent) {

		int versionNumber = 0;

		Integer integer = commonDao.singleResult("select max(versionNumber) from TSDatalogEntity where tableName = '" + tableName + "' and dataId = '" + dataId + "'");
		if (integer != null) {
			versionNumber = integer.intValue();
		}

		TSDatalogEntity tsDatalogEntity = new TSDatalogEntity();
		tsDatalogEntity.setTableName(tableName);
		tsDatalogEntity.setDataId(dataId);
		tsDatalogEntity.setDataContent(dataContent);
		tsDatalogEntity.setVersionNumber(versionNumber + 1);
		commonDao.save(tsDatalogEntity);
	}

}
