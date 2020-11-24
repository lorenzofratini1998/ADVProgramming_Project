package it.univpm.advprog.blog.model.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import it.univpm.advprog.blog.model.entities.Attachment;
import it.univpm.advprog.blog.model.entities.Post;

public interface AttachmentDao {
	Session getSession();
	
	public void setSession(Session session);
	
	List<Attachment> getAll();
	
	Attachment getById(long id);
	
	List<Attachment> getByPost(Post post);
	
	Attachment create(long id, String description, boolean hide, Set<Post> posts);
	
	Attachment update(Attachment attachment);
	
	void delete(Attachment attachment);
}
