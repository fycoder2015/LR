package com.job.lr.control.bounty;

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
import com.job.lr.service.bounty.BountyTaskService;

@RestController
@RequestMapping(value = "/rest/bounty")
public class RestBountyController {
	
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(RestBountyController.class);

	@Autowired
	BountyTaskService bountyService;

	@SuppressWarnings("unused")
	@Autowired
	private Validator validator;
	
	
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

}
