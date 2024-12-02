package org.mql.cloud.smart_hire.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "resumes")
public class Resume {

    @Id
    private String id; 
    private String fullName;
    private String email;
    private String phoneNumber;
    private String profile;
    private String location;
    private String education;
    private String experience;
    private String skills;
    private String languages;
    private String softSkills;
    private String projects;
    private String url;
    private String pdfLink;  
    private String post; // post de candidature
    private String matchingScore; // this is how much this application match the post

    public Resume() {
	}
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getSoftSkills() {
        return softSkills;
    }

    public void setSoftSkills(String softSkills) {
        this.softSkills = softSkills;
    }

    public String getProjects() {
        return projects;
    }

    public void setProjects(String projects) {
        this.projects = projects;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPdfLink() {
		return pdfLink;
	}
    
    public void setPdfLink(String pdfLink) {
		this.pdfLink = pdfLink;
	}
    
    public void setPost(String post) {
		this.post = post;
	}
    
    public String getPost() {
		return post;
	}
    
    public String getMatchingScore() {
		return matchingScore;
	}
    
    public void setMatchingScore(String matchingScore) {
		this.matchingScore = matchingScore;
	}
}
