package it.univpm.advprog.blog.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univpm.advprog.blog.model.dao.AttachmentDao;
import it.univpm.advprog.blog.model.entities.Attachment;
import it.univpm.advprog.blog.model.entities.Post;

@Transactional
@Service("attachmentService")
public class AttachmentServiceDefault implements AttachmentService {

	AttachmentDao attachmentRepository;
	
	/**
	 * Metodo per restituire tutti gli allegati
	 * @return lista contenente gli allegati
	 */
	@Transactional(readOnly = true)
	@Override
	public List<Attachment> getAll() {
		return this.attachmentRepository.getAll();
	}

	/**
	 * Metodo per restituire un allegato dato un certo id
	 * @param id: id dell'allegato da cercare
	 * @return allegato ricercato
	 */
	@Transactional(readOnly = true)
	@Override
	public Attachment getById(long id) {
		return this.attachmentRepository.getById(id);
	}

	/**
	 * Metodo per restituire gli allegati associati ad un certo post
	 * @param post: post specificato
	 * @return lista degli allegati associati al post
	 */
	@Transactional(readOnly = true)
	@Override
	public List<Attachment> getByPost(Post post) {
		return new ArrayList<>(post.getAttachments());
	}

	/**
	 * Metodo per creare un nuovo allegato
	 * @param id: id dell'allegato da creare
	 * @param description: descrizione dell'allegato
	 * @param hide: visibilità dell'allegato
	 * @return allegato creato
	 */
	/*@Transactional(readOnly = true)
	@Override
	public Attachment create(long id, String description, boolean hide, Set<Post> posts) {
		return this.attachmentRepository.create(id, description, hide, posts);
	}*/

	/**
	 * Metodo per aggiornare un allegato
	 * @param attachment: allegato da aggiornare
	 * @return allegato aggiornato
	 */
	@Transactional
	@Override
	public Attachment update(Attachment attachment) {
		return this.attachmentRepository.update(attachment);
	}

	/**
	 * Metodo per cancellare un allegato
	 * @param attachment: allegato da cancellare
	 */
	@Transactional
	@Override
	public void delete(Attachment attachment) {
		this.attachmentRepository.delete(attachment);
	}

	/**
	 * Setter per la proprietà che si riferisce al DAO dell'entità Attachment.
	 *
	 * @param attachmentRepository DAO dell'entità Attachment da settare
	 */
	@Autowired
	public void setLinkRepository(AttachmentDao attachmentRepository) {
		this.attachmentRepository = attachmentRepository;
	}
}
