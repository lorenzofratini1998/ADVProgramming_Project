package it.univpm.advprog.blog.model.dao;

import java.util.List;

import org.hibernate.Session;

import it.univpm.advprog.blog.model.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;


public interface UserDao {

	Session getSession();

	void setSession(Session session);

	List<User> findAll();
	
    User findUserByUsername(String username);
	
	User create(String username, String password, String firstName, String lastName);
	
	User update(User user);
	
	void delete(User user);

	String encryptPassword(String password);

	void setPasswordEncoder(PasswordEncoder passwordEncoder);

	PasswordEncoder getpasswordEncoder();

}
