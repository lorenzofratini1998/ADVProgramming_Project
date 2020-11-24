package it.univpm.advprog.blog.model.dao;

import java.util.List;

import org.hibernate.Session;

import it.univpm.advprog.blog.model.entities.File;

public interface FileDao {
	
	List<File> getAll();
	
	File getById(long id);
	
	File getByName(String name);
	
	List<File> getByDownloadble(boolean downloadble);
	
	File create(String name, boolean downloadle);
	
	File update(File file);
	
	void delete(File file);
	
}
