package com.diegovp.mongodb.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diegovp.mongodb.models.dto.PostDTO;
import com.diegovp.mongodb.models.entities.Post;
import com.diegovp.mongodb.repositories.PostRepository;
import com.diegovp.mongodb.services.exceptions.ResourceNotFoundException;

@Service
public class PostService {

	@Autowired
	private PostRepository repository;

	public PostDTO findById(String id) {
		Post entity = getEntityById(id);
		return new PostDTO(entity);
	}

	private Post getEntityById(String id) {
		Optional<Post> result = repository.findById(id);
		return result.orElseThrow(() -> new ResourceNotFoundException("Resource not found!"));
	}

}
