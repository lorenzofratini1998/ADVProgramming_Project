package it.univpm.advprog.blog.services;

import java.util.List;
import java.util.Set;

import it.univpm.advprog.blog.model.entities.Attachment;
import it.univpm.advprog.blog.model.entities.Link;
import it.univpm.advprog.blog.model.entities.Post;

public interface LinkService {
	List<Link> getAll();
	
	Link getById(long id);
	
	List<Link> getLinkByPost(Post post); //TODO: lasciare (prendo solo la lista dei link e non di tutti gli allegati)... magari spostarlo sul DAO

	Link create(String description, boolean hide, Post post, String link);
	
	Link create(String description, Post post, String link);
	
	Link update(Link link);
	
	void delete(Link link);
}
