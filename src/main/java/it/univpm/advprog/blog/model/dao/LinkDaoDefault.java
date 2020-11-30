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
	 * Metodo per ricercare un link dalla stringa che lo caratterizza
	 * @param link: stringa che descrive il link
	 * @return link ricercato
	 */
	@Override
	public Link getByLink(String link) {
		return (Link) this.getSession().createNamedQuery("getLinkByLink",Link.class);
	}

	/**
	 * Metodo per creare un nuovo link
	 * @param id: id del link da creare
	 * @param link: nome del link
	 * @return link creato 
	 */
	@Override
	public Link create(String description, boolean hide, Set<Post> posts, String link) {
		Link newLink =new Link();
		newLink.setDescription(description);
		newLink.setHide(hide);
		newLink.setPosts(posts);
		newLink.setLink(link);
		this.getSession().save(newLink);
		return newLink;
		
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
