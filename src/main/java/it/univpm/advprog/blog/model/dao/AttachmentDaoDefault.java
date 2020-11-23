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
	                createQuery("from Attachment p", Attachment.class).
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
	 * Metodo per cercare tutti gli allegati di un certo post
	 * @param post: post specificato
	 * @return lista degli allegati associati ad un certo post
	 */
	@Override
	public List<Attachment> getByPost(Post post) {
		return (List<Attachment>) getSession().createNamedQuery("getAttachmentByPost", Attachment.class);
	}

	@Override
	public Attachment create(long id, String description, Set<Post> posts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attachment update(Attachment attachment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Attachment attachment) {
		// TODO Auto-generated method stub

	}


}
