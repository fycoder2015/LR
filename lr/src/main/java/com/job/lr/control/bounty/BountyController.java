
package com.job.lr.control.bounty;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
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
import com.job.lr.entity.BountyTask;
import com.job.lr.entity.User;
import com.job.lr.service.account.ShiroDbRealm.ShiroUser;
import com.job.lr.service.bounty.BountyTaskService;


@Controller
@RequestMapping(value = "/bounty")
public class BountyController {

	@SuppressWarnings("unused")
	private static final String PAGE_SIZE = "20";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("title", "标题");
	}

	@Autowired
	private BountyTaskService bountyTaskService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			Model model, ServletRequest request) {
		
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		
		Page<BountyTask> bountyList = bountyTaskService.getPagedBountyAll(pageNumber);
		
		model.addAttribute("bountyList", bountyList);
		
		model.addAttribute("sortTypes", sortTypes);

		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "bounty/bountyList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("bountyTask", new BountyTask());
		model.addAttribute("action", "create");
		return "bounty/bountyForm";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("bountyTask", bountyTaskService.getBountTask(id));
		model.addAttribute("action", "update");
		
		return "bounty/bountyForm";
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid BountyTask bountyTask, RedirectAttributes redirectAttributes) {

		User user = new User(getCurrentUserId());
		bountyTask.setUser(user);
		
		bountyTaskService.createBountyTask(bountyTask);
		redirectAttributes.addFlashAttribute("message", "赏金创建任务成功");
		return "redirect:/bounty/";
	}
	
	
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("ent") BountyTask bounty, RedirectAttributes redirectAttributes) {
		bountyTaskService.saveBountyTask(bounty);
		redirectAttributes.addFlashAttribute("message", "更新赏金任务成功");
		return "redirect:/bounty/";
	}

	
	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {		
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if (user== null){
			return 0L;
		}else{
			return user.id;
		}	
	}
	

	
	
}
