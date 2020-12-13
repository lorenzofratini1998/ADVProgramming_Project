package it.univpm.advprog.blog.model.dao;

import java.util.List;

import it.univpm.advprog.blog.model.entities.*;
import org.hibernate.Session;

public interface TagDao {

	Session getSession();

	public void setSession(Session session);

	List<Tag> getAll();
	
	Tag getByName(String name);
	
	Tag create(String name);
	
	Tag update(Tag tag);
	
	void delete(Tag tag);
	
	void delete(String name);
	
	List<Tag> getTagsFromPost(Post post);

}
