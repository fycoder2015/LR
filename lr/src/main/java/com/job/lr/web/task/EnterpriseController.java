package com.job.lr.web.task;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.job.lr.entity.Enterprise;
import com.job.lr.service.task.EnterpriseService;

@Controller
@RequestMapping(value = "/enterprise")
public class EnterpriseController {
	
	@SuppressWarnings("unused")
	private static final String PAGE_SIZE = "20";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("title", "标题");
	}
	
	@Autowired
	private EnterpriseService entService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			Model model, ServletRequest request) {
		
		
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		
		Page<Enterprise> enterpriseList = entService.getPagedEnterpriseAll(pageNumber);
		

		model.addAttribute("entList", enterpriseList);
		
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "enterprise/entList";
	}
	
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("enterprise", new Enterprise());
		model.addAttribute("action", "create");
		return "enterprise/enterpriseForm";
	}
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ent", entService.getEnterpriseById(id));
		model.addAttribute("action", "update");
		
		return "enterprise/enterpriseForm";
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Enterprise enterprise, RedirectAttributes redirectAttributes) {
	
		entService.createEnterprise(enterprise);
		redirectAttributes.addFlashAttribute("message", "创建企业成功");
		return "redirect:/enterprise/";
	}
	
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("ent") Enterprise ent, RedirectAttributes redirectAttributes) {
		entService.saveEnterprise(ent);
		redirectAttributes.addFlashAttribute("message", "更新企业信息成功");
		return "redirect:/enterprise/";
	}

}
