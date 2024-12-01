package org.mql.cloud.smart_hire.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.mql.cloud.smart_hire.model.Resume;
import org.mql.cloud.smart_hire.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class HomeController {
	@Autowired
	private ApplicationService applicationService;
	
    @GetMapping("/")
    public String home() {
        return "index"; 
    }
    
    @GetMapping({"resumes", "/resumes/{post}"})
    public String getResumesByPost(@PathVariable(required = false) String post, Model model) {
        List<Resume> resumes = applicationService.getApplicationsByPost(post);
        System.out.println("post :::: "+ post);
        List<String> uniquePosts = applicationService.getPosts().stream()
        	    .map(map -> {
        	    	return map.getPost();
        	    })
        	    .distinct()
        	    .collect(Collectors.toList());
        
        
        model.addAttribute("distinctPosts", uniquePosts);
        model.addAttribute("resumes", resumes);
        model.addAttribute("post", post);
        
        return "resume-list";
    }
}
