package it.univpm.advprog.blog.model.dao;

import java.util.List;

import it.univpm.advprog.blog.model.entities.Link;

public interface LinkDao {
	
	List<Link> getAll();
	
	Link getById(long id);
	
	Link getByLink(String link);
	
	Link create(String name, boolean downloadle);
	
	Link update(Link link);
	
	void delete(Link link);
}
