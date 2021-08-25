package com.noctturne.api.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.noctturne.api.models.Category;
import com.noctturne.api.models.User;
import com.noctturne.api.services.UserServiceInterface;

// Angular - podemos decidir tambien los métodos a los que puede acceder
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class userController {
	
	@Autowired
	private UserServiceInterface userService;
	
	// List
	@GetMapping("/users")
	public List<User> index(){
		return userService.findAll();
	}
	
	// Lista paginada
	@GetMapping("/users/page/{page}")
	public Page<User> index(@PathVariable Integer page){
		Pageable pag = PageRequest.of(page, 4);
		return userService.findAll(pag);
	}
	
	// Show
	@GetMapping("/users/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		User user = null;
		Map<String, Object> res = new HashMap<>();
		
		try {
			user = userService.findById(id);
		}catch(DataAccessException e) {
			res.put("msg", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.NOT_FOUND);
		}
		
		if(user == null) {
			res.put("msg", "User ".concat(id.toString().concat(" not found")));
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	// Create
	@PostMapping("/users")
	public ResponseEntity<?> create(@RequestBody User user){
		User newUser = null;
		Map<String, Object> res = new HashMap<>();
		
		try {
			newUser =  userService.save(user);
		}catch(DataAccessException e) {
			res.put("msg", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}
	
	// Update
	@PutMapping("/users/{id}")
	public ResponseEntity<?> update(@RequestBody User user, @PathVariable Long id) {
		User actualUser = this.userService.findById(id);
		User userUpdated = null;
		Map<String, Object> res = new HashMap<>();
		
		if(actualUser == null) {
			res.put("msg", "User ".concat(id.toString().concat(" not found")));
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.NOT_FOUND);
		}
		
		try {
			actualUser.setUsername(user.getUsername());
			actualUser.setEmail(user.getEmail());
			actualUser.setCreateAt(user.getCreateAt());
			actualUser.setCategory(user.getCategory());
			userUpdated = userService.save(actualUser);
			
		}catch(DataAccessException e) {
			res.put("msg", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		res.put("msg", "User Updated!");
		res.put("user", userUpdated);
		return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
	}
	
	// Delete
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> res = new HashMap<>();
		try {
			userService.delete(id);
		}catch(DataAccessException e) {
			res.put("msg", "Error on delete");
			res.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		res.put("msg", "User deleted!");
		
		return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
	}
	
	// Show Categories
	@GetMapping("/users/categories")
	public List<Category> categoryList(){
		return userService.findAllCategories();
	}
}







