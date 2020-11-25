package it.univpm.advprog.blog.model.dao;

import java.util.List;

import org.hibernate.Session;

import it.univpm.advprog.blog.model.entities.Link;

public interface LinkDao {
	Session getSession();
	
	public void setSession(Session session);
	
	List<Link> getAll();
	
	Link getById(long id);
	
	Link getByLink(String link);
	
	Link create(long id, String link);
	
	Link update(Link link);
	
	void delete(Link link);
}
