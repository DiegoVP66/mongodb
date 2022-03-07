package com.diegovp.mongodb.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.diegovp.mongodb.models.dto.UserDTO;
import com.diegovp.mongodb.models.entities.User;
import com.diegovp.mongodb.repositories.UserRepository;
import com.diegovp.mongodb.services.exceptions.DatabaseException;
import com.diegovp.mongodb.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public List<UserDTO> findAll() {
		List<User> list = repository.findAll();
		return list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
	}

	public UserDTO findById(String id) {
		User entity = getEntityById(id);
		return new UserDTO(entity);
	}

	public UserDTO insert(UserDTO dto) {
		User entity = new User();
		copyToDTO(entity, dto);
		entity = repository.save(entity);
		return new UserDTO(entity);
	}

	public UserDTO update(String id, UserDTO dto) {
		User entity = getEntityById(id);
		copyToDTO(entity, dto);
		entity = repository.save(entity);
		return new UserDTO(entity);

	}

	public void delete(String id) {
		try {
			getEntityById(id);
			repository.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation!");
		}
	}

	private User getEntityById(String id) {
		Optional<User> result = repository.findById(id);
		return result.orElseThrow(() -> new ResourceNotFoundException("Resource not found!"));
	}

	private void copyToDTO(User entity, UserDTO dto) {
		entity.setName(dto.getName());
		entity.setEmail(dto.getEmail());
	}
}
