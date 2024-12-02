package org.mql.cloud.smart_hire.service;

import java.util.List;

import org.mql.cloud.smart_hire.model.Post;

public interface PostService {
	public List<Post> getDistinctPosts();
	public void addPost(Post post);
	public Post getPostByName(String postName);
	public List<String> getDistinctPostsNames();
}
