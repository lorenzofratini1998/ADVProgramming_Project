package it.univpm.advprog.blog.model.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import it.univpm.advprog.blog.model.entities.Link;
import it.univpm.advprog.blog.model.entities.Post;

public interface LinkDao {
	Session getSession();
	
	void setSession(Session session);
	
	List<Link> getAll();
	
	Link getById(long id);

	Link create(String description, boolean hide, Post post, String link);
	
	Link create(String description, Post post, String link);
	
	Link update(Link link);
	
	void delete(Link link);
}
