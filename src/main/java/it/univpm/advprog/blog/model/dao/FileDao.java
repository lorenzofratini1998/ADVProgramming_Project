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
	
	List<File> getByDownloadable(boolean downloadable);

	//TODO: Inserire altri 3 metodi create:
	// uno che permetta di creare il file senza gli attributi "hide" e "downloadable" (perché tanto di default sono false);
	// uno che permetta la creazione di un file con l'attributo "hide" e senza "downloadable";
	// uno che permetta la creazione di un file con l'attributo "downloadable" e senza "hide".
	// ---
	// (stessa cosa per LinkDao, in quel caso solo per l'attributo "hide")
	
	File create(String description, boolean hide, Post post, String name, boolean downloadable);
	
	File update(File file);
	
	void delete(File file);
	
}
