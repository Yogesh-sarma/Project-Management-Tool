package com.ppm.kanbantool.KanbanTool2.repositories;

import com.ppm.kanbantool.KanbanTool2.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

	User findByUsername(String username);
	User getById(Long id);
	Iterable<User> findAll();
}
