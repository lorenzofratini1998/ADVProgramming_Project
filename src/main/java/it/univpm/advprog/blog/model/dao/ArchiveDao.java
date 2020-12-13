package it.univpm.advprog.blog.model.dao;

import org.hibernate.Session;

import java.util.List;
import it.univpm.advprog.blog.model.entities.*;

public interface ArchiveDao {
	Session getSession();

    void setSession(Session session);
    
    List<Archive> getAll();
    
    Archive getByName(String name);
    
    Archive create(String name);
    
    Archive update(Archive archive);
    
    void delete(Archive archive);
    
    void delete(String name);
    

}
