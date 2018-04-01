package com.vivebest.mall.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.MemberBirthdayRigthsDao;
import com.vivebest.mall.entity.MemberBirthdayRigths;
import com.vivebest.mall.entity.Message;
import com.vivebest.mall.service.MailService;
import com.vivebest.mall.service.MemberBirthdayRigthsService;
import com.vivebest.mall.service.MessageService;
import com.vivebest.mall.service.SmsService;

/**
 * ServiceImpl - 生日权益
 * @author ding.hy
 * @version 3.0
 *
 */
@Service("memberBirthdayRigthsServiceImpl")
public class MemberBirthdayRigthsServiceImpl extends BaseServiceImpl<MemberBirthdayRigths, Long>
		implements MemberBirthdayRigthsService {
	
	@Resource(name = "memberBirthdayRigthsDaoImpl")
	private MemberBirthdayRigthsDao memberBirthdayRigthsDao;

	@Resource(name = "memberBirthdayRigthsDaoImpl")
	public void setBaseDao(MemberBirthdayRigthsDao memberBirthdayRigthsDao) {
		super.setBaseDao(memberBirthdayRigthsDao);
	}
	
	@Resource(name = "messageServiceImpl")
	MessageService messageService;
	
	@Resource(name = "mailServiceImpl")
	MailService mailService;
	@Resource(name = "smsServiceImpl")
	SmsService smsService;
	
	public int send(Long[] ids, HttpServletRequest request) {
		List<MemberBirthdayRigths> memberBirthdayRigths = findList(ids);
		for (MemberBirthdayRigths mbr : memberBirthdayRigths) {
			//邮件
			if (mbr.getMember().getEmail() != null) {
				mailService.sendMemberBirthdayRightsMail(mbr);
			}
			//短信
			if (mbr.getMember().getMobile() != null) {
				smsService.sendMemberBirthdayRightsSms(mbr);
			}
			//站内信
			Message message = new Message();
			message.setTitle("生日权益通知");
			message.setContent("生日快乐");
			message.setIp(request.getRemoteAddr());
			message.setIsDraft(false);
			message.setSenderRead(true);
			message.setReceiverRead(false);
			message.setSenderDelete(false);
			message.setReceiverDelete(false);
			message.setSender(null);
			message.setReceiver(mbr.getMember());
			message.setForMessage(null);
			message.setReplyMessages(null);
			messageService.save(message);
			
			mbr.setRightsStatus(true);
			memberBirthdayRigthsDao.merge(mbr);
		}
		return memberBirthdayRigths.size();
	}
	
	@Transactional(readOnly = true)
	public Page<MemberBirthdayRigths> findPage(Date beginDate, Date endDate, Pageable pageable){
		return memberBirthdayRigthsDao.findPage(beginDate, endDate, pageable);
	}

}
