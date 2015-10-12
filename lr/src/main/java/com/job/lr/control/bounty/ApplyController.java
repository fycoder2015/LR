package com.job.lr.control.bounty;

import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.job.lr.entity.BountyApply;
import com.job.lr.service.bounty.BountyApplyService;

@Controller
@SuppressWarnings("unused")
@RequestMapping(value = "/bountyApply")
public class ApplyController {
	
	@Autowired
	private BountyApplyService applyService;
	
	private static final String PAGE_SIZE = "20";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("title", "标题");
	}
	
	@RequestMapping(value = "listByBountyId/{bountyId}_{pageNum}" ,method = RequestMethod.GET)
	public String list(@PathVariable("pageNum") int pageNumber,
			@PathVariable("bountyId") Long bountyId,
			Model model, ServletRequest request) {
		
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		
		Page<BountyApply> applyList = applyService.pageByBountyId(bountyId, pageNumber);
		
		model.addAttribute("applyList", applyList);
		
		model.addAttribute("sortTypes", sortTypes);

		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		
		return "bounty/applyList";
	}
	
	


}
