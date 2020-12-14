package it.univpm.advprog.blog.services;

import java.util.List;
import java.util.Set;

import it.univpm.advprog.blog.model.entities.Attachment;
import it.univpm.advprog.blog.model.entities.Post;

public interface AttachmentService {
	
	List<Attachment> getAll();
	
	Attachment getById(long id);

	Attachment update(Attachment attachment);
	
	void delete(Attachment attachment);

}
