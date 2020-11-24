package it.univpm.advprog.blog.services;

import java.util.List;

import it.univpm.advprog.blog.model.entities.Attachment;
import it.univpm.advprog.blog.model.entities.File;
import it.univpm.advprog.blog.model.entities.Post;

public interface FileService {

	List<File> getAll();
	
	File getById(long id);
	
	File getByName(String name);
	
	Attachment getAttachmentByFile(File file);
	
	List<File> getByDownloadble(boolean downloadble);
	
	List<File> getFileByPost(Post post);
	
	File create(long id, String name, boolean downloadble);
	
	File update(File file);
	
	void delete(File file);
}
