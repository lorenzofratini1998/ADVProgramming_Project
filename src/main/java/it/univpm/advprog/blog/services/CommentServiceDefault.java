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

@Transactional
@Service("commentService")
public class CommentServiceDefault implements CommentService {

	private CommentDao commentRepository;
	private PostDao postRepository;
	private UserDao userRepository;
	
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
	 * Setter della proprietà postRepository
	 * 
	 * @param postDao bean della classe Dao relativa ai post
	 */
	@Autowired
	public void setPostRepository(PostDao postDao) {
		this.postRepository = postDao;
	}
	
	/**
	 * Setter della proprietà userRepository
	 * 
	 * @param userDao bean della classe Dao relativa agli utenti
	 */
	@Autowired
	public void setUserRepository(UserDao userDao) {
		this.userRepository = userDao;
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
	 * @return null
	 */
	@Transactional
	@Override
	public void delete(Comment comment) {
		this.commentRepository.delete(comment);
	}
	
	
	/**
	 * Metodo per ottenere i commenti relativi ad un certo post
	 * 
	 * @return lista di tutti i commenti di un post specifico
	 */
	@Transactional
	@Override
	public List<Comment> getCommentsFromPost(Post post) {
		
		return this.commentRepository.getCommentsFromPost(post);
	
	}
	
	
	/**
	 * Metodo per ottenere i commenti di un certo utente
	 * 
	 * @return lista di commenti relativi ad un utente specifico
	 */
	@Override
	public List<Comment> getCommentsFromAuthor(String username) {
		
		User u = this.userRepository.findUserByUsername(username);
		
		return this.commentRepository.getCommentsFromAuthor(u);
		
		
		
	}
	
}
