package it.univpm.advprog.blog.services;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univpm.advprog.blog.model.dao.AttachmentDao;
import it.univpm.advprog.blog.model.dao.LinkDao;
import it.univpm.advprog.blog.model.entities.Attachment;
import it.univpm.advprog.blog.model.entities.Link;
import it.univpm.advprog.blog.model.entities.Post;

@Transactional
@Service("linkService")
public class LinkServiceDefault implements LinkService {

	private LinkDao linkRepository;
	private AttachmentDao attachmentRepository;
	
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
	 * Metodo per ricavare un link dal suo link
	 * @param link: link da ricercare
	 * @retun link ricercato
	 */
	@Transactional(readOnly=true)
	@Override
	public Link getByLink(String link) {
		return this.linkRepository.getByLink(link);
	}

	/**
	 * Metodo per ottenere le caratteristiche dell'allegato associato al link
	 * @param link: link ricercato
	 * @return allegato se il link esiste
	 * @return null se il link non esiste
	 */
	@Transactional(readOnly=true)
	@Override
	public Attachment getAttachmentByLink(Link link) {
		List<Attachment> attachments=this.attachmentRepository.getAll();
		for(Attachment attachment : attachments) {
			if(attachment.getId()==link.getId()) 
				return attachment;
			}
		return null;
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
		for(Link link: links) {
			Attachment attachment=this.getAttachmentByLink(link);
			Set<Post> postAttachments=attachment.getPosts();
			if(!postAttachments.contains(post)) {
				links.remove(link);
			}
		}
		return links;
	}

	/**
	 * Metodo per creare un link
	 * @param id: id del link da creare
	 * @param link: link del link da creare
	 * @return link creato
	 */
	@Override
	public Link create(long id, String link) {
		return this.linkRepository.create(id, link);
	}

	/**
	 * Metodo per aggiornare un link
	 * @param link: link da aggiornare
	 * @return link aggiornato
	 */
	@Override
	public Link update(Link link) {
		return this.linkRepository.update(link);
	}

	/**
	 * Metodo per eliminare un link
	 * @param link: link da eliminare
	 */
	@Override
	public void delete(Link link) {
		this.linkRepository.delete(link);

	}

}
