package it.univpm.advprog.blog.model.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import it.univpm.advprog.blog.model.entities.Attachment;
import it.univpm.advprog.blog.model.entities.Post;

@Repository("attachmentDao")
public class AttachmentDaoDefault extends DefaultDao implements AttachmentDao {

	/**
	 * Metodo per restituire la lista di tutti gli allegati
	 * @return lista di tutti gli allegati
	 */
	@Override
	public List<Attachment> getAll() {
		 return getSession().
	                createQuery("from Attachment a order by a.id desc", Attachment.class).
	                getResultList();
	}

	/**
	 * Metodo per restituire un allegato da un id
	 * @param id: id dell'allegato da cercare 
	 * @return allegato con id specificato
	 */
	@Override
	public Attachment getById(long id) {
		return getSession().find(Attachment.class, id);
	}
	
	/**
	 * Metodo per aggiornare un post
	 * @param attachment: allegato da aggiornare
	 * 
	 * @return allegato aggiornato
	 */
	@Override
	public Attachment update(Attachment attachment) {
		return (Attachment)this.getSession().merge(attachment);
	}

	/**
	 * Metodo per eliminare un allegato
	 * @param attachment: allegato da eliminare
	 * 
	 */
	@Override
	public void delete(Attachment attachment) {
		this.getSession().delete(attachment);

	}

}
