package it.univpm.advprog.blog.services;

import java.util.List;

import it.univpm.advprog.blog.model.entities.Attachment;
import it.univpm.advprog.blog.model.entities.Link;
import it.univpm.advprog.blog.model.entities.Post;

public interface LinkService {
	List<Link> getAll();
	
	Link getById(long id);
	
	Link getByLink(String link);
	
	Attachment getAttachmentByLink(Link link);
	
	List<Link> getLinkByPost(Post post);
	
	Link create(long id, String link);
	
	Link update(Link link);
	
	void delete(Link link);
}