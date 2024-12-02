package org.mql.cloud.smart_hire.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.mql.cloud.smart_hire.model.Post;
import org.mql.cloud.smart_hire.model.Resume;
import org.mql.cloud.smart_hire.repository.PostRepository;
import org.mql.cloud.smart_hire.service.ApplicationService;
import org.mql.cloud.smart_hire.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private PostService postService;

	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/offers")
	public String offers(Model model) {
		List<Post> uniquePosts = postService.getDistinctPosts();
		model.addAttribute("distinctPosts", uniquePosts);
		return "offers";
	}

	@GetMapping("/apply")
	public String apply(Model model) {
		List<String> uniquePosts = postService.getDistinctPostsNames();
		model.addAttribute("distinctPosts", uniquePosts);
		return "apply";
	}

	@GetMapping({ "resumes", "/resumes/{post}" })
	public String getResumesByPost(@PathVariable(required = false) String post, Model model) {
		List<Resume> resumes = applicationService.getApplicationsByPost(post);
		List<String> uniquePosts = postService.getDistinctPostsNames();

		for (Resume resume : resumes) {
			colorMatchingScore(resume);
		}

		model.addAttribute("distinctPosts", uniquePosts);
		model.addAttribute("resumes", resumes);

		model.addAttribute("post", post);

		return "resume-list";
	}

	@GetMapping("/resume/details/{id}")
	public String getResumeDetails(@PathVariable String id, Model model) {
		Resume resume = applicationService.getResumeById(id);
		colorMatchingScore(resume);
		model.addAttribute("resume", resume);
		return "resume-details";
	}

	public void colorMatchingScore(Resume resume) {
		String color = "black";
		if (resume.getMatchingScore() != null) {
			Integer percentage = extractPercentage(resume.getMatchingScore());
			if (percentage != null) {
				color = percentage.intValue() >= 50 ? "green" : "red";
				// change matching score to html to color only the percentage

				String coloredText = " <span style=\"color: " + color + ";\"><b>" + percentage + "% Match. </b></span><span> "
						+ resume.getMatchingScore().substring(11) + "</span>";
				resume.setMatchingScore(coloredText);
			}
		}
	}

	public Integer extractPercentage(String text) {
		Pattern pattern = Pattern.compile("(\\d+)%");
		Matcher matcher = pattern.matcher(text);

		if (matcher.find()) {
			return Integer.parseInt(matcher.group(1));
		} else {
			return null;
		}
	}
}
