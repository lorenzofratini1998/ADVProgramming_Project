package it.univpm.advprog.blog.services;

import java.util.List;

import it.univpm.advprog.blog.model.entities.*;

public interface TagService {

	List<Tag> getAll();
	
	Tag getByName(String name);
	
	Tag create(String name);
	
	Tag update(Tag tag);
	
	void delete(Tag tag);
	
	void delete(String name);
}
