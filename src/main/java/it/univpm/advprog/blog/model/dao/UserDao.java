package it.univpm.advprog.blog.model.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import it.univpm.advprog.blog.model.entities.Post;
import it.univpm.advprog.blog.model.entities.User;



public interface UserDao {

	Session getSession();
	
	public void setSession(Session session);
	
	List<User> findAll();
	
    User findUserByUsername(String username);
	
	User create(String username, String password, String firstName, String lastName);
	
	User update(User user);
	
	void delete(User user);
	
	
	
	
	
	
	
}
