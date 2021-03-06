package com.job.lr.control.bounty;

import javax.validation.Valid;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springside.modules.web.MediaTypes;

import com.job.lr.entity.BountyTask;
import com.job.lr.entity.Category;
import com.job.lr.entity.GeneralResponse;
import com.job.lr.service.admin.CategoryService;
import com.job.lr.service.bounty.BountyTaskService;

@RestController
@RequestMapping(value = "/rest/bounty")
public class RestBountyController {
	
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(RestBountyController.class);

	@Autowired
	BountyTaskService bountyService;
	
	@Autowired
	CategoryService cateService;

	@SuppressWarnings("unused")
	@Autowired
	private Validator validator;
	
	
	
	@RequestMapping(value = "/create",method = RequestMethod.POST,produces = MediaTypes.JSON_UTF_8) 
	public GeneralResponse create(@Valid BountyTask bountyTask) {
		
		GeneralResponse resp = new GeneralResponse();
		try {
			bountyService.createBountyTask(bountyTask);
		}
		catch(Exception e) {
			resp.setRetCode(-1);
			resp.setRetInfo(e.getMessage());
		}
		return resp;
	}
	
	
	/**
	 * 分页列出所有赏金任务
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/getPageBounty/{pageNum}",method = RequestMethod.GET,produces = MediaTypes.JSON_UTF_8) 
	public Page<BountyTask> pageBounty(@PathVariable("pageNum") int pageNum) {
		
		return bountyService.getPagedBountyAll(pageNum);
	}
	
	
	/**
	 * 根据酬谢方式查询赏金任务
	 * @param paymentType
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/getPageByPaymentType/{paymentType}_{pageNum}",method = RequestMethod.GET,
			produces = MediaTypes.JSON_UTF_8) 
	public Page<BountyTask> pageByPaymentType (@PathVariable("paymentType") String paymentType,
			@PathVariable("pageNum") int pageNum){
		return bountyService.getBountyByPaymentWay(paymentType, pageNum);
	}
	
	/**
	 * 根据性别要求查询赏金任务
	 * @param gender
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/getPageByGender/{gender}_{pageNum}",method = RequestMethod.GET,
			produces = MediaTypes.JSON_UTF_8) 
	public Page<BountyTask> pageByGender (@PathVariable("gender") String gender,
			@PathVariable("pageNum") int pageNum){
		return bountyService.getBountyByGender(gender, pageNum);
	}
	
	/**
	 * 根据用户ID分页查询该用户发布的赏金任务
	 * @param userId
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/getPageByUser/{userId}_{pageNum}",method = RequestMethod.GET,
			produces = MediaTypes.JSON_UTF_8) 
	public Page<BountyTask> pageByUser (@PathVariable("userId") Long userId,
			@PathVariable("pageNum") int pageNum){
		return bountyService.getUserBountyTask(userId, pageNum);
	}
	
	/**
	 * 获取赏金任务的分类列表
	 * @return
	 */
	@RequestMapping(value = "/getBountyCategory",method = RequestMethod.GET,
			produces = MediaTypes.JSON_UTF_8) 
	public Page<Category> getBountyCategory(){
		return this.cateService.pageAllBountyCate(1, 500);
	}
	
	/**
	 * 获取所有所有已经通过审核的赏金任务分页
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/getAuditedBounty/{pageNum}_{pageSize}",method = RequestMethod.GET,
			produces = MediaTypes.JSON_UTF_8) 
	public Page<BountyTask> getAuditedBounty(@PathVariable("pageNum") int pageNum,
			@PathVariable("pageSize") int pageSize){
		return this.bountyService.getAuditedBounty(pageNum, pageSize);
	}
}
