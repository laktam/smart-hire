package org.mql.cloud.smart_hire.controller;

import java.util.Collections;
import java.util.Map;

import org.mql.cloud.smart_hire.model.Post;
import org.mql.cloud.smart_hire.repository.PostRepository;
import org.mql.cloud.smart_hire.service.GoogleGeminiService;
import org.mql.cloud.smart_hire.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {
	@Autowired
	private PostService postService;
	@Autowired
	private GoogleGeminiService geminiService;

	@PostMapping("/posts/add")
    public ResponseEntity<Map<String, String>> addPost(@RequestParam("postName") String postName, 
                                          @RequestParam("postDescription") String postDescription) {

        // Create and save the new post
        Post post = new Post();
        post.setPostName(postName);
        String formatedDescription = geminiService.intelligentFormat(postDescription);
        post.setPostDescription(formatedDescription);

        postService.addPost(post);

        return ResponseEntity.ok(Collections.singletonMap("message", "Post added successfully!"));
    }
}
