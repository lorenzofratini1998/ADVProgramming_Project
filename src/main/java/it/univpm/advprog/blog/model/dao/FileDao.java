package it.univpm.advprog.blog.model.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import it.univpm.advprog.blog.model.entities.Attachment;
import it.univpm.advprog.blog.model.entities.File;
import it.univpm.advprog.blog.model.entities.Post;

public interface FileDao {
	Session getSession();
	
	public void setSession(Session session);
	
	List<File> getAll();
	
	File getById(long id);
	
	File getByName(String name);
	
	List<File> getByDownloadble(boolean downloadable);
	
	File create(long id, String name, boolean downloadable);
	
	File update(File file);
	
	void delete(File file);
	
}
