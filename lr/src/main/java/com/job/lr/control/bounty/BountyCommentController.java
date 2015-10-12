package com.job.lr.control.bounty;

import java.util.Map;

import javax.servlet.ServletRequest;

import com.job.lr.entity.BountyApply;
import com.job.lr.entity.BountyComment;
import com.job.lr.service.bounty.BountyCommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/bountyComment")
@SuppressWarnings("unused")
public class BountyCommentController {

	private static final String PAGE_SIZE = "20";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("title", "标题");
	}
	
	@Autowired
	private BountyCommentService commentSerivce;
	
	@RequestMapping(value="create/{applyId}", method=RequestMethod.GET)
	public String commentForm(@PathVariable("applyId") Long applyId,
			Model model, ServletRequest request)
	{
		model.addAttribute("applyId", applyId);
		model.addAttribute("action","create");
		return "bounty/bountyCommentForm";
	}
	
	@RequestMapping(value="listByApply/{applyId}_{pageNum}", method=RequestMethod.GET)
	public String listComment(@PathVariable("applyId") Long applyId,
			@PathVariable("pageNum") int pageNum,
			Model model, ServletRequest request) {
		
		Page<BountyComment> commentList = commentSerivce.pageByApplyId(applyId, pageNum);
		
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		
		model.addAttribute("commentList", commentList);
		model.addAttribute("sortTypes", sortTypes);

		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "bounty/commentList";
	}
}
