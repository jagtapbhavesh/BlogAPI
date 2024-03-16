package com.bjproject.sb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjproject.sb.model.User;
import com.bjproject.sb.projection.UserWithoutPassword;
import com.bjproject.sb.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
//	public Iterable<User> getAll() {
//		return userRepository.findAll();
//	}
	
	public List<UserWithoutPassword> getAll() {
		return userRepository.findAllProjectedBy();
	}
	
	public User add(User user) {
		return userRepository.save(user);
	}
	
	public String delete(Integer id) {
		UserWithoutPassword foundUser =this.find(id);
		if(foundUser == null) {
			return null;
		} else {
			userRepository.deleteById(id);
			return "Deleted";
		}
		
	}
	
//	public User find(Integer id) {
//		return userRepository.findById(id).orElse(null);
//	}
	
	public UserWithoutPassword find(Integer id) {
		return userRepository.findProjectedById(id).orElse(null);
	}
	
	
	public User update(Integer id, User user) {
		UserWithoutPassword foundUser =this.find(id);
		if(foundUser == null) {
			return null;
		} else {
			user.setId(id);
			return userRepository.save(user);
		}
	}

	public List<UserWithoutPassword> getByName(String name) {
		return this.userRepository.findByName(name);
	}
	public List<UserWithoutPassword> getByEmail(String email) {
		return this.userRepository.findByEmailContaining(email);
	}
	
}
