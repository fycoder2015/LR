package com.job.lr.control.bounty;

import java.io.File;
import java.text.SimpleDateFormat;

import javax.validation.Valid;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.utils.Clock;
import org.springside.modules.web.MediaTypes;

import com.job.lr.entity.BountyApply;
import com.job.lr.entity.BountyComment;
import com.job.lr.entity.GeneralResponse;
import com.job.lr.entity.User;
import com.job.lr.service.bounty.BountyApplyService;
import com.job.lr.service.bounty.BountyCommentService;

@RestController
@RequestMapping(value = "/rest/comment")
@SuppressWarnings("unused")
public class RestCommentController {

	private static Logger logger = LoggerFactory.getLogger(RestBountyController.class);

	@Autowired
	private Validator validator;

	@Autowired
	private BountyCommentService commentService;
	
	@Autowired
	private BountyApplyService applyService;
	
	@RequestMapping(value = "/getPageComment/{pageNum}",method = RequestMethod.GET,produces = MediaTypes.JSON_UTF_8) 
	public Page<BountyComment> pageBounty(@PathVariable("pageNum") int pageNum) {
		return commentService.pageAll(pageNum);
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public GeneralResponse create(@Valid BountyComment comment,RedirectAttributes redirectAttributes,
			@RequestParam(value = "imageFile1", required = false) MultipartFile imageFile1,
			@RequestParam(value = "imageFile2", required = false) MultipartFile imageFile2,
			@RequestParam(value = "imageFile3", required = false) MultipartFile imageFile3
			) {
		
		GeneralResponse resp = new GeneralResponse();
		
		/*
		 * 根据提交的请求内容查询订单的具体内容，针对订单的状态、申请人和任务提交人进行校验；
		 */
		BountyApply apply = this.applyService.getOne(comment.getApply().getId());
		
		Long applyUserId = apply.getApplyUser().getId();
		
		Long currentUserId = commentService.getCurrentUserId();
		
		Long bountyUserId = apply.getTaskUserId();
		
		if (!apply.getSts().equals("D")) {
			resp.setRetCode(-1);
			resp.setRetInfo("订单状态未进行确认，不可进行评论");
			return resp;
		}
		
		/*
		 * 业务逻辑如下：只有任务发起人和经过确认后的订单申请人具备评论的权限；
		 * 如果评论的提交者为订单申请人（猎人），则被评论人即为赏金任务发起人；
		 * 评论的提交者为赏金任务的发起人，则被评论人即为订单申请人（猎人）；
		 */
		if (currentUserId.equals(applyUserId) || currentUserId.equals(currentUserId)) {
			User user = new User();
			user.setId(currentUserId);
			comment.setCommentUser(user);
			comment.setCommentDate(Clock.DEFAULT.getCurrentDate());
			
			if (currentUserId.equals(applyUserId)) {
				comment.setByCommentUserId(bountyUserId);
				apply.setHunterCommentSts("D");
			}
			else if (currentUserId.equals(bountyUserId)) {
				comment.setByCommentUserId(applyUserId);
				apply.setHunterCommentSts("D");
			}
			
			/*
			 * 检测是否有图片上传，如果有则保存图片
			 */
			if (imageFile1!=null && imageFile1.getContentType().contains("image")) {

				String newFileName = this.saveImageFile(imageFile1, currentUserId, apply.getId());
				if (newFileName.startsWith("OK:")) {
					comment.setImageFileName1(newFileName.substring(3));
				}
				else {
					resp.setRetCode(-1);
					resp.setRetInfo(newFileName);
					return resp;
				}
			}
			
			if (imageFile2!=null && imageFile2.getContentType().contains("image")) {

				String newFileName = this.saveImageFile(imageFile2, currentUserId, apply.getId());
				if (newFileName.startsWith("OK:")) {
					comment.setImageFileName2(newFileName.substring(3));
				}
				else {
					resp.setRetCode(-1);
					resp.setRetInfo(newFileName);
					return resp;
				}
			}
			
			if (imageFile3!=null && imageFile3.getContentType().contains("image")) {

				String newFileName = this.saveImageFile(imageFile3, currentUserId, apply.getId());
				if (newFileName.startsWith("OK:")) {
					comment.setImageFileName3(newFileName.substring(3));
				}
				else {
					resp.setRetCode(-1);
					resp.setRetInfo(newFileName);
					return resp;
				}
			}
			
			//创建评论
			resp = commentService.createComment(comment);
			if (resp.getRetCode()<0) {
				return resp;
			}
			
			//目前业务需求尚未明确，暂定评论完成后，订单即为‘完成’状态
/*			apply.setSts("F");
			resp = applyService.saveApply(apply);*/
		}
		else {
			resp.setRetCode(-1);
			resp.setRetInfo("当前用户与赏金猎人订单中用户不一致");
		}
		
		return resp;
	}
	
	private String saveImageFile(MultipartFile imageFile,Long userId, Long applyId) {
		
		String fileName = imageFile.getOriginalFilename();

        String suffix = fileName.substring(fileName.indexOf("."));
        
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		
		String newFileName = userId+"_comment_"+applyId+"_"+format.format(Clock.DEFAULT.getCurrentDate())+suffix;
		System.out.println(newFileName);
	   
		String destDir = System.getenv("IMAGE_DIR");

		if (destDir == null|| destDir.equals("")) {
			return "未能获取文件保存位置，请联系系统管理员。";
		}
		
		File destFile = new File(destDir+"/"+newFileName);
		
		try {
			imageFile.transferTo(destFile);
		}
		catch(Exception e) {
			return e.getMessage();
		}
		return "OK:"+newFileName;
	}
	
}
