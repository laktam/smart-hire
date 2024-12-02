package org.mql.cloud.smart_hire.repository;

import java.util.List;
import java.util.Map;

import org.mql.cloud.smart_hire.model.Resume;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends MongoRepository<Resume, String>{
	List<Resume> findByPost(String post);
	@Query(value = "{}", fields = "{'_id' : 0, 'post' : 1}")
    List<Resume> findDistinctPosts();
}
