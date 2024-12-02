package org.mql.cloud.smart_hire.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "posts")
public class Post {
	private String postName;
	private String postDescription;
		
	public Post() {
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getPostDescription() {
		return postDescription;
	}

	public void setPostDescription(String postDescription) {
		this.postDescription = postDescription;
	}
	
	
}
