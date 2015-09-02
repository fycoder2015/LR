package com.job.lr.web.task;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.job.lr.entity.Task;
import com.job.lr.entity.TaskComment;

@Controller
@RequestMapping(value = "/comment")
public class CommentController {
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("comment", new TaskComment());
		model.addAttribute("action", "create");
		return "comment/commentForm";
	}

}
