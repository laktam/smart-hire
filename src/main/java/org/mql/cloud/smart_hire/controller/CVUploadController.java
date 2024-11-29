package org.mql.cloud.smart_hire.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.mql.cloud.smart_hire.storageblob.BlobStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class CVUploadController {
	

	    @Autowired
	    private BlobStorageService blobStorageService;

	    @PostMapping("/cv")
	    public ResponseEntity<Map<String, String>> uploadCV(@RequestParam("cv") MultipartFile file) {
	        try {
	            String fileUrl = blobStorageService.uploadFile(file);
	            return ResponseEntity.ok(Collections.singletonMap("url", fileUrl));
	        } catch (IOException e) {
	            return ResponseEntity.status(500).body(Collections.singletonMap("message", "Erreur lors du téléchargement du fichier."));
	        }
	    }
}
