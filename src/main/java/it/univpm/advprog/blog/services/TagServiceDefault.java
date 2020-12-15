package it.univpm.advprog.blog.services;

import it.univpm.advprog.blog.model.dao.PostDao;
import it.univpm.advprog.blog.model.dao.TagDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import it.univpm.advprog.blog.model.entities.Archive;
import it.univpm.advprog.blog.model.entities.Tag;

@Service("tagService")
public class TagServiceDefault implements TagService {
	
	private TagDao tagRepository;

	/**
	 * Funzione per ritornare la lista di tutti i tag
	 */
	@Transactional(readOnly=true)
	@Override
	public List<Tag> getAll() {
		return this.tagRepository.getAll();
	}

	/**
	 * Funzione per trovare un tag dal name
	 * 
	 * @param name nome del tag
	 */
	@Transactional(readOnly=true)
	@Override
	public Tag getByName(String name) {
		return this.tagRepository.getByName(name);
	}

	/**
	 * Funzione per creare un tag con un nome specifico
	 * 
	 * @param name nome del tag da creare
	 */
	@Transactional
	@Override
	public Tag create(String name) {
		return this.tagRepository.create(name);
	}

	/**
	 * Funzione per aggiornare un tag specificato
	 * 
	 * @param tag tag specificato
	 */
	@Transactional 
	@Override
	public Tag update(Tag tag) {
		return this.tagRepository.update(tag);
	}

	/**
	 * Funzione per cancellare un tag specifico
	 * 
	 * @param tag tag da cancellare
	 */
	@Transactional
	@Override
	public void delete(Tag tag) {
		this.tagRepository.delete(tag);
		
	}
	
	/**
	 * Funzione per cancellare un tag specificando il nome
	 * 
	 * @param name nome del tag da cancellare
	 */
	@Transactional
	@Override
	public void delete(String name) {
		Tag tag= this.getByName(name);
        this.tagRepository.delete(tag);
	}
	
    /**
     * Setter per la proprietà riferita al DAO dell'entità Tag.
     *
     * @param tagRepository DAO dell'entità Tag da settare
     */
	@Autowired
	public void setTagRepository(TagDao tagRepository) {
	    this.tagRepository = tagRepository;
	}

}
