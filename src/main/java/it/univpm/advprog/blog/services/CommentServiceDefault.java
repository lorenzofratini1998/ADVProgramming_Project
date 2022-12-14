package it.univpm.advprog.blog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univpm.advprog.blog.model.dao.CommentDao;
import it.univpm.advprog.blog.model.dao.PostDao;
import it.univpm.advprog.blog.model.dao.UserDao;
import it.univpm.advprog.blog.model.entities.Comment;
import it.univpm.advprog.blog.model.entities.Post;
import it.univpm.advprog.blog.model.entities.User;

@Service("commentService")
public class CommentServiceDefault implements CommentService {

	private CommentDao commentRepository;
	
	/**
	 * Setter della proprietà commentRepository
	 * 
	 * @param commentDao bean della classe Dao relativa ai commenti
	 */
	@Autowired
	public void setCommentRepository(CommentDao commentDao) {
		this.commentRepository = commentDao;
	}

	/**
	 * Metodo che restituisce tutti i commenti.
	 *
	 * @return lista di commenti restituita
	 */
	@Transactional(readOnly = true)
	@Override
	public List<Comment> getAll() {
		return this.commentRepository.getAll();
	}

	/**
	 * Metodo che restituisce il commento conoscendo il suo id
	 * 
	 * @return commento corrispondente all'id
	 */
	@Transactional
	@Override
	public Comment findCommentById(long id) {
		return this.commentRepository.findCommentById(id);
	}
	
	
	/**
	 * Metodo che crea un nuovo commento
	 * 
	 * @return commento creato da un certo utente
	 */
	@Transactional
	@Override
	public Comment create(User author, Post post, String title, String description) {
		return this.commentRepository.create(author, post, title, description);
	}
	
	/**
	 * Metodo per aggiornare un commento
	 * 
	 * @return commento con eventuali modifiche apportate
	 */
	@Transactional
	@Override
	public Comment update(Comment comment) {
		return this.commentRepository.update(comment);
	}
	
	
	/**
	 * Metodo per rimuovere un commento
	 *
	 * @param comment commento da eliminare
	 */
	@Transactional
	@Override
	public void delete(Comment comment) {
		this.commentRepository.delete(comment);
	}
	
}
