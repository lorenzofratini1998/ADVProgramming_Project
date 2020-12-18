package it.univpm.advprog.blog.services;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import it.univpm.advprog.blog.model.entities.Post;
import it.univpm.advprog.blog.model.entities.User;

public interface UserService {
	
	List<User> findAll();
	
    User findUserByUsername(String username);
	
	User create(String username, String password, String firstName, String lastName);
		
	User create(String username, String password, String firstName, String lastName, String imgProfile);
	
	void update(User user);
	
	void delete(User user);
	
}
