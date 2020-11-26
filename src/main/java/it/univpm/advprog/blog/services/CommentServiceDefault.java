package it.univpm.advprog.blog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univpm.advprog.blog.model.dao.CommentDao;
import it.univpm.advprog.blog.model.dao.PostDao;
import it.univpm.advprog.blog.model.entities.Comment;
import it.univpm.advprog.blog.model.entities.Post;
import it.univpm.advprog.blog.model.entities.User;

@Transactional
@Service("commentService")
public class CommentServiceDefault implements CommentService {

	private CommentDao commentRepository;
	private PostDao postRepository;
	
	
	@Autowired
	public void setCommentRepository(CommentDao commentDao) {
		this.commentRepository = commentDao;
	}
	
	@Autowired
	public void setPostRepository(PostDao postDao) {
		this.postRepository = postDao;
	}
	
	//Funzione per trovare un commento dall'id
	
	@Transactional
	@Override
	public Comment findCommentById(long id) {
		return this.commentRepository.findCommentById(id);
	}
	
	
	//Funzione per creare un commento
	
	@Transactional
	@Override
	public Comment create(long id, User author, Post post, String title, String description) {
		return this.commentRepository.create(id, author, post, title, description);
	}
	
	//Funzione per aggiornare un commento
	
	@Transactional
	@Override
	public Comment update(Comment comment) {
		return this.commentRepository.update(comment);
	}
	
	
	//Funzione per rimuovere un commento
	
	@Transactional
	@Override
	public void delete(Comment comment) {
		this.commentRepository.delete(comment);
	}
	
	
	//Funzione per ottenere i commenti relativi ad un post
	
	@Transactional
	@Override
	public List<Comment> getCommentsFromPost(Post post) {
		
		return this.commentRepository.getCommentsFromPost(post);
	
	}
	
	//Funzione per ottenere i commenti di un utente specificato sotto un post specificato
	
//	@Override
//	public List<Comment> findCommentsByPostAndAuthor(Post post, User author) {
//		
//		return this.commentRepository.getSession().createQuery("SELECT c FROM Comment c JOIN User u ON c.author = u.username WHERE c.post = :post AND u.username = :username", Post.class)
//		
//		
//		
//		
//	}
	
}
