package org.mql.cloud.smart_hire.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mql.cloud.smart_hire.model.Post;
import org.mql.cloud.smart_hire.model.Resume;
import org.mql.cloud.smart_hire.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azure.ai.formrecognizer.documentanalysis.models.AnalyzeResult;
import com.azure.ai.formrecognizer.documentanalysis.models.AnalyzedDocument;

@Service
public class ApplicationServiceDefault implements ApplicationService{

	@Autowired
	private AzureDocumentIntelligenceService azureDocIntelligentService;
	@Autowired
	private ResumeRepository resumeRepository;
	@Autowired
	private GoogleGeminiService googleGeminiService;
	
	public ApplicationServiceDefault() {
	}
	
	@Override
	public String addApplication(Post post, MultipartFile file, String fileUrl) {
		AnalyzeResult result;
		try {
			result = azureDocIntelligentService.analyzeResume(file);
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
			return "Error analyzing resume";
		}
		
		// Map the extracted data to a Resume object
        Resume resume = new Resume();
        resume.setFullName(extractField(result, "Full Name"));
        resume.setEmail(extractField(result, "Mail"));
        resume.setPhoneNumber(extractField(result, "Number"));
        resume.setProfile(extractField(result, "Profile"));
        resume.setLocation(extractField(result, "Location"));
        
        String education = extractField(result, "Education");
        resume.setEducation(googleGeminiService.intelligentFormat(education));
        
        String experience = extractField(result, "Experience");
        resume.setExperience(googleGeminiService.intelligentFormat(experience));
        
        String skills = extractField(result, "Skills");
        resume.setSkills(googleGeminiService.intelligentFormat(skills));
        
        String projects = extractField(result, "Projects");
        resume.setProjects(googleGeminiService.intelligentFormat(projects));

        resume.setLanguages(googleGeminiService.intelligentFormat(extractField(result, "Languages")));
        resume.setSoftSkills(googleGeminiService.intelligentFormat(extractField(result, "Soft skills")));
        resume.setUrl(extractField(result, "URL"));
        resume.setPost(post.getPostName());
        resume.setPdfLink(fileUrl);
        
        String applicationString = "Education : " + education + " , experience : " + experience + " , skills : "
        		+ skills + " , projects : " + projects;
        
        
        String matchingScore = googleGeminiService.matchApplicationToPost(applicationString, post);
        resume.setMatchingScore(matchingScore);
        
        resumeRepository.save(resume);
		return "Resume analyzed and added to MongoDB";
	}
	
	private String extractField(AnalyzeResult result, String fieldName) {
        for (AnalyzedDocument document : result.getDocuments()) {
            var fields = document.getFields();
            if (fields.containsKey(fieldName)) {
                return fields.get(fieldName).getValueAsString();
            }
        }
        return null;  // If the field is not found
    }

	@Override
	public List<Resume> getApplicationsByPost(String post) {
		return resumeRepository.findByPost(post);
	}

	@Override
	public List<Resume> getPosts() {
		return resumeRepository.findDistinctPosts();
	}

	@Override
	public Resume getResumeById(String id) {
		return resumeRepository.findById(id).get();
	}

}
