package org.mql.cloud.smart_hire.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	
	public ApplicationServiceDefault() {
	}
	
	@Override
	public String addApplication(String post, MultipartFile file, String fileUrl) {
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
        resume.setEducation(extractField(result, "Education"));
        resume.setExperience(extractField(result, "Experience"));
        resume.setSkills(extractField(result, "Skills"));
        resume.setLanguages(extractField(result, "Languages"));
        resume.setSoftSkills(extractField(result, "Soft skills"));
        resume.setProjects(extractField(result, "Projects"));
        resume.setUrl(extractField(result, "URL"));
        resume.setPost(post);
        resume.setPdfLink(fileUrl);

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

}
