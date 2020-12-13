package it.univpm.advprog.blog.services;

import java.util.List;

import it.univpm.advprog.blog.model.entities.*;

public interface ArchiveService {
	
		List<Archive> getAll();
	    
	    Archive getByName(String name);
	    
	    Archive create(String name);
	    
	    Archive update(Archive archive);
	    
	    void delete(Archive archive);
	    
	    void delete(String name);

	    List<Archive> getArchivesFromPost(Post post);
}
