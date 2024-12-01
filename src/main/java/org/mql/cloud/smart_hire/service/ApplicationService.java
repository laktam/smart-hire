package org.mql.cloud.smart_hire.service;

import java.util.List;
import java.util.Map;

import org.mql.cloud.smart_hire.model.Resume;
import org.springframework.web.multipart.MultipartFile;

public interface ApplicationService {
	public String addApplication(String post, MultipartFile file, String fileUrl);
	public List<Resume> getApplicationsByPost(String post);
	public List<Resume> getPosts();
	
}
