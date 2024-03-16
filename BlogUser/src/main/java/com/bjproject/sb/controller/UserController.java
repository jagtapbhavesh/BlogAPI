package com.bjproject.sb.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bjproject.sb.model.User;
import com.bjproject.sb.projection.UserWithoutPassword;
import com.bjproject.sb.services.UserService;
import com.bjproject.sb.util.ResponseWrapper;

import jakarta.validation.Valid;


@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers() {
		ResponseWrapper wrapper = new ResponseWrapper();
		wrapper.setSubject("All Users List");
		wrapper.setBody(userService.getAll());
		return new ResponseEntity<>(wrapper,HttpStatus.OK);
	}
	
	@PostMapping("/users")
	public ResponseEntity<?> addUser(@RequestBody @Valid User user, BindingResult bindingResult) {
		ResponseWrapper wrapper = new ResponseWrapper();
		if(bindingResult.hasErrors()) { 
			wrapper.setSubject("Validation error");
			List<String> errors = bindingResult.getFieldErrors()
											   .stream()
											   .map(error ->  {
												   return error.getField() + " - " + 
														  error.getDefaultMessage();
											   }).collect(Collectors.toList());
			wrapper.setBody(errors);
			return new ResponseEntity<>(wrapper,HttpStatus.BAD_REQUEST);
		} else {
			wrapper.setSubject("User created");
			wrapper.setBody(this.userService.add(user));
			return new ResponseEntity<>(wrapper,HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/users/{id}") 
	public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
		ResponseWrapper wrapper = new ResponseWrapper();
		String message = userService.delete(id);
		if(message == null) {
			wrapper.setSubject("User Not Found");
			wrapper.setBody(null);
			return new ResponseEntity<>(wrapper,HttpStatus.NOT_FOUND);
		} else {
			wrapper.setSubject(message);
			wrapper.setBody(null);
			return new ResponseEntity<>(wrapper,HttpStatus.OK);
		}
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<?> findUser(@PathVariable Integer id) {
		UserWithoutPassword foundUser = userService.find(id);
		ResponseWrapper wrapper = new ResponseWrapper();
		// System.out.println(foundUser);
		if(foundUser == null) {
			wrapper.setSubject("User Not Found");
			wrapper.setBody(null);
			return new ResponseEntity<>(wrapper,HttpStatus.NOT_FOUND);
		} else {
			wrapper.setSubject("User Found : " + id);
			wrapper.setBody(foundUser);
			return new ResponseEntity<>(wrapper,HttpStatus.OK);
		}
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody @Valid User user, BindingResult bindingResult) {
		User updatedUser = userService.update(id, user);
		ResponseWrapper wrapper = new ResponseWrapper();
		if(updatedUser == null) {
			wrapper.setSubject("User Not Found");
			wrapper.setBody(null);
			return new ResponseEntity<>(wrapper,HttpStatus.NOT_FOUND);
		} else {
			wrapper.setSubject("User Updated : " + id);
			wrapper.setBody(updatedUser);
			return new ResponseEntity<>(wrapper,HttpStatus.OK);
		}
	}
	
	@GetMapping("/users/findby") 
	public ResponseEntity<?> getByName(@RequestParam String name)	{
		ResponseWrapper wrapper = new ResponseWrapper();
		wrapper.setSubject("Find By Name");
		wrapper.setBody(this.userService.getByName(name));
		return new ResponseEntity<>(wrapper, HttpStatus.OK);
	}
	
	@GetMapping("/users/findbyemail")
	public ResponseEntity<?> getByEmail(@RequestParam String email)	{
		ResponseWrapper wrapper = new ResponseWrapper();
		wrapper.setSubject("Find By Email");
		wrapper.setBody(this.userService.getByEmail(email));
		return new ResponseEntity<>(wrapper, HttpStatus.OK);
	}
	
}