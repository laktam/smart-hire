package org.mql.cloud.smart_hire.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.mql.cloud.smart_hire.model.Post;
import org.mql.cloud.smart_hire.service.ApplicationService;
import org.mql.cloud.smart_hire.service.BlobStorageService;
import org.mql.cloud.smart_hire.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/apply")
public class ApplicationsController {
	

	    @Autowired
	    private BlobStorageService blobStorageService;
	    @Autowired
	    private ApplicationService applicationService;
	    @Autowired
	    private PostService postService;

	    @PostMapping("/{post}/upload")
	    public ResponseEntity<Map<String, String>> uploadCV(@PathVariable("post") String postName, @RequestParam("cv") MultipartFile file) {
	        try {
	            String fileUrl = blobStorageService.uploadFile(file);
	            Post post = postService.getPostByName(postName);
	            applicationService.addApplication(post, fileUrl);
	            
	            return ResponseEntity.ok(Collections.singletonMap("url", fileUrl));
	        } catch (IOException e) {
	            return ResponseEntity.status(500).body(Collections.singletonMap("message", "Erreur lors du téléchargement du fichier."));
	        }
	    }
}
