package it.univpm.advprog.blog.services;

import java.util.List;
import java.util.Set;

import it.univpm.advprog.blog.model.entities.Attachment;
import it.univpm.advprog.blog.model.entities.File;
import it.univpm.advprog.blog.model.entities.Post;

public interface FileService {

	List<File> getAll();
	
	File getById(long id);
	
	File getByName(String name);
	
	List<File> getByNoDownloadable(boolean noDownloadable);

	List<File> getFileByPost(Post post);

	File create(String description, boolean hide, Post post, String name, boolean noDownloadable);
	
	File create(String description, Post post, String name);
	
	File create(String description, boolean hide, Post post, String name);
	
	File create(String description, Post post, String name, boolean noDownloadable);
	
	File update(File file);
	
	void delete(File file);
}
