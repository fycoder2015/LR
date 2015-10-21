package com.job.lr.control.bounty;

import javax.validation.Validator;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springside.modules.web.MediaTypes;

import com.job.lr.entity.BountyApply;
import com.job.lr.entity.GeneralResponse;
import com.job.lr.service.account.ShiroDbRealm.ShiroUser;
import com.job.lr.service.bounty.BountyApplyService;

@RestController
@RequestMapping(value = "/rest/bountyApply")
public class RestApplyController {
	
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(RestApplyController.class);

	@SuppressWarnings("unused")
	@Autowired
	private Validator validator;
	
	@Autowired
	BountyApplyService applyService;
	
	/**
	 * 分页查询所有申请记录
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/list/{pageNum}",method = RequestMethod.GET,produces = MediaTypes.JSON_UTF_8)
	public Page<BountyApply> pageListAll(@PathVariable("pageNum") int pageNum) {
		return applyService.pageAllApply(pageNum);
	}
	
	
	/**
	 * 按照揭榜申请用户ID分页查询揭榜申请记录
	 * @param userId
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/pageByApplyUser/{userId}_{pageNum}",method = RequestMethod.GET,
			produces = MediaTypes.JSON_UTF_8)
	public Page<BountyApply> pageByApplyUser(@PathVariable("userId") Long userId,
			@PathVariable("pageNum") int pageNum) {
		return applyService.pageByApplyUser(userId, pageNum);
		
	}
	
	/**
	 * 按照赏金任务发布用户Id分页查询揭榜申请记录
	 * @param userId
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/pageByBountyUser/{userId}_{pageNum}",method = RequestMethod.GET,
			produces = MediaTypes.JSON_UTF_8)
	public Page<BountyApply> pageByBountyUser(@PathVariable("userId") Long userId,
			@PathVariable("pageNum") int pageNum) {
		return applyService.pageByBountyUser(userId, pageNum);
		
	}
	
	/**
	 * 提交揭榜请求
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value = "/apply/{taskId}",method = RequestMethod.GET,
			produces = MediaTypes.JSON_UTF_8)
	public GeneralResponse applyBounty(@PathVariable("taskId") Long taskId) {
		
		return applyService.createApply(taskId, getCurrentUserId());
	}
	
	/**
	 * 赏金任务发布者确认用户提交的揭榜请求
	 * @param applyId
	 * @return
	 */
	@RequestMapping(value = "/confirm/{applyId}",method = RequestMethod.GET,
			produces = MediaTypes.JSON_UTF_8)
	public GeneralResponse confirmApply(@PathVariable("applyId") Long applyId) {
		
		return applyService.confirmApply(applyId);
	}
	
	/**
	 * 用户取消赏金任务揭榜请求
	 * @param applyId
	 * @return
	 */
	@RequestMapping(value = "/cancel/{applyId}",method = RequestMethod.GET,
			produces = MediaTypes.JSON_UTF_8)
	public GeneralResponse cancelApply(@PathVariable("applyId") Long applyId) {
		
		return applyService.cancelApply(applyId);
	}
	
	/**
	 * 赏金任务发布者拒绝揭榜请求
	 * @param applyId
	 * @return
	 */
	@RequestMapping(value = "/refuse/{applyId}",method = RequestMethod.GET,
			produces = MediaTypes.JSON_UTF_8)
	public GeneralResponse refuseApply(@PathVariable("applyId") Long applyId) {
		
		return applyService.refuseApply(applyId);
	}
	
	
	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {		
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if (user== null){
			return 0L;
		} else{
			return user.id;
		}
	}
	
}
