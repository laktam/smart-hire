package org.mql.cloud.smart_hire.repository;

import java.util.Optional;

import org.mql.cloud.smart_hire.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
	public Optional<Post> findByPostName(String postName);
}