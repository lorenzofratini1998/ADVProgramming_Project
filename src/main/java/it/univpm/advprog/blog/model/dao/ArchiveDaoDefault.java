package it.univpm.advprog.blog.model.dao;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.univpm.advprog.blog.model.entities.*;

@Repository("archiveDao")
public class ArchiveDaoDefault extends DefaultDao implements ArchiveDao {
	
	/**
	 * Funzione per resitutire la lista di tutti gli archivi
	 * 
	 * @return lista contenente tutti gli archivi
	 */
	@Override
	public List<Archive> getAll() {
	     return getSession().
	             createQuery("from Archive a", Archive.class).
	             getResultList();
	    }

	/**
	 * Funzione per trovare un archivio specificando il nome
	 * 
	 * @param name dell'archivio da trovare
	 * @return archivio eventualmente trovato
	 */
	@Override
	public Archive getByName(String name) {
        return getSession().find(Archive.class, name);
    }
	
	/**
	 * Funzione per creare un nuovo archivio specificando il nome
	 * @param name        nome dell'archivio
	 */

	@Override
	public Archive create(String name) {
		Archive archive = new Archive();
	    archive.setName(name);
	    this.getSession().save(archive);
	    return archive;
	}

	/**
	 * Funzione per cancellare un archivio specificato
	 * 
	 * @param archive archivio specificato
	 */
	
	@Override
	public void delete(Archive archive) {
		this.getSession().delete(archive);
		
	}
	
	/**
	 * Funzione per cancellare un archivio specificando il nome
	 * 
	 * @param name nome dell'archivio da cancellare
	 */
	@Override
	public void delete(String name) {
		Archive archive = this.getByName(name);
		this.delete(archive);
	}
	
}
