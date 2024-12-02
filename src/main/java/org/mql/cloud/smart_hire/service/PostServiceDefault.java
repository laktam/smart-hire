package org.mql.cloud.smart_hire.service;

import java.util.List;
import java.util.stream.Collectors;

import org.mql.cloud.smart_hire.model.Post;
import org.mql.cloud.smart_hire.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceDefault implements PostService{
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public List<Post> getDistinctPosts() {
		return  postRepository.findAll().stream()
        	    .distinct()
        	    .collect(Collectors.toList());
	}

	@Override
	public void addPost(Post post) {
		postRepository.save(post);
	}

	@Override
	public Post getPostByName(String postName) {
		return postRepository.findByPostName(postName).get();
	}

	@Override
	public List<String> getDistinctPostsNames() {
		
		return postRepository.findAll().stream()
	    .map(p -> p.getPostName())
	    .distinct()
	    .collect(Collectors.toList());
	}

}
