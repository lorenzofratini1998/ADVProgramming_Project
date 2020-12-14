package it.univpm.advprog.blog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univpm.advprog.blog.model.dao.LinkDao;
import it.univpm.advprog.blog.model.entities.Link;
import it.univpm.advprog.blog.model.entities.Post;

@Service("linkService")
public class LinkServiceDefault implements LinkService {

	private LinkDao linkRepository;
	/**
	 * Metodo per ottenere tutti i link
	 * @return lista di tutti i link
	 */
	@Transactional(readOnly=true)
	@Override
	public List<Link> getAll() {
		return this.linkRepository.getAll();
	}
	
	/**
	 * Metodo per ricavare il link dall'id
	 * @param id: id del link da ricavare
	 * @return link ricercato
	 */
	@Transactional(readOnly=true)
	@Override
	public Link getById(long id) {
		return this.linkRepository.getById(id);
	}

	/**
	 * Metodo per ottenere i link associati ad un certo post
	 * @param post: post da ricercare
	 * @return lista dei link associati al post
	 */
	@Transactional(readOnly=true)
	@Override
	public List<Link> getLinkByPost(Post post) {
		List<Link> links=this.linkRepository.getAll();
		links.removeIf(link -> !link.getPost().equals(post));
		return links;
	}

	/**
	 * Metodo per creare un link
	 * @param description: descrizione del link
	 * @param hide: visibilità del link
	 * @param post: post associato al link
	 * @param link: link del link da creare
	 * @return link creato
	 */
	@Override
	@Transactional
	public Link create(String description, boolean hide, Post post, String link) {
		return this.linkRepository.create(description, hide, post, link);
	}
	
	/**
	 * Metodo per creare un link
	 * @param description: descrizione del link
	 * @param post: post associato al link
	 * @param link: link del link da creare
	 * @return link creato
	 */
	@Override
	@Transactional
	public Link create(String description, Post post, String link) {
		return this.linkRepository.create(description, post, link);
	}

	/**
	 * Metodo per aggiornare un link
	 * @param link: link da aggiornare
	 * @return link aggiornato
	 */
	@Override
	@Transactional
	public Link update(Link link) {
		return this.linkRepository.update(link);
	}

	/**
	 * Metodo per eliminare un link
	 * @param link: link da eliminare
	 */
	@Override
	@Transactional
	public void delete(Link link) {
		this.linkRepository.delete(link);

	}

	/**
	 * Setter per la proprietà che si riferisce al DAO dell'entità Link.
	 *
	 * @param linkRepository DAO dell'entità Link da settare
	 */
	@Autowired
	public void setLinkRepository(LinkDao linkRepository) {
		this.linkRepository = linkRepository;
	}
}
