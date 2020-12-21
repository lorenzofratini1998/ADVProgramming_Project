package it.univpm.advprog.blog.model.dao;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import it.univpm.advprog.blog.model.entities.Link;
import it.univpm.advprog.blog.model.entities.Post;

@Repository("linkDao")
public class LinkDaoDefault extends DefaultDao implements LinkDao {

	/**
	 * Metodo per ritornare la lista dei link
	 * @return lista dei links
	 */
	@Override
	public List<Link> getAll() {
		return getSession().
                createQuery("from Link p", Link.class).
                getResultList();
	}

	/**
	 * Metodo per cercare un link dal suo id
	 * @param id: id del link da ricercare
	 * @return link ricercato
	 */
	@Override
	public Link getById(long id) {
		return this.getSession().find(Link.class, id);
	}

	/**
	 * Metodo per creare un nuovo link
	 * @param description: descrizione del link
	 * @param hide: visibilità del link
	 * @param post: post a cui il link è associato
	 * @param link: nome del link
	 * @return link creato 
	 */
	@Override
	public Link create(String description, boolean hide, Post post, String link) {
		Link newLink =new Link();
		newLink.setDescription(description);
		newLink.setHide(hide);
		newLink.setPost(post);
		newLink.setLink(link);
		this.getSession().save(newLink);
		return newLink;	
	}
	
	/**
	 * Metodo per creare un nuovo link
	 * @param description: descrizione del link
	 * @param post: post a cui il link è associato
	 * @param link: nome del link
	 * @return link creato 
	 */
	@Override
	public Link create(String description, Post post, String link) {
		return this.create(description, false, post, link);
	}

	/**
	 * Metodo per aggiornare un link
	 * @param link: link da aggiornare
	 * @return link aggiornato
	 */
	@Override
	public Link update(Link link) {
		return (Link) this.getSession().merge(link);
	}

	/**
	 * Metodo per eliminare un link
	 * @param link: link da eliminare
	 */
	@Override
	public void delete(Link link) {
		this.getSession().delete(link);
	}

}
