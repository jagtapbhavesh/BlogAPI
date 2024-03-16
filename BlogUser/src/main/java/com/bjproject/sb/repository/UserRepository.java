package com.bjproject.sb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bjproject.sb.model.User;
import com.bjproject.sb.projection.UserWithoutPassword;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
	List<UserWithoutPassword> findAllProjectedBy();
	List<UserWithoutPassword> findByName(String name);
	List<UserWithoutPassword> findByEmailContaining(String email);
	
	Optional<UserWithoutPassword> findProjectedById(Integer id);
	
	
}
