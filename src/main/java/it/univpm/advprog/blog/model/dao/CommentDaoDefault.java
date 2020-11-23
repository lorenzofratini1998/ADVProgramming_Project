package it.univpm.advprog.blog.model.dao;

import java.util.List;

import it.univpm.advprog.blog.model.entities.Comment;
import it.univpm.advprog.blog.model.entities.Post;
import it.univpm.advprog.blog.model.entities.User;

public class CommentDaoDefault extends DefaultDao implements CommentDao {

	@Override
	public Comment findCommentById(long id) {
		
		return this.getSession().get(Comment.class, id);
		
	}
	
	@Override
	public Comment create(long id, User author, Post post, String title, String description) {
		
		Comment newComment = new Comment();
		newComment.setId(id);
		newComment.setAuthor(author);
		newComment.setPost(post);
		newComment.setTitle(title);
		newComment.setDescription(description);
		
		this.getSession().save(newComment);
		return newComment;
	}
	
	@Override
	public Comment update(Comment comment) {
		
		return (Comment)this.getSession().merge(comment);
	}
	
	
	@Override
	public void delete(Comment comment) {
		
		this.getSession().delete(comment);
	}
	
	
	/*
	 * Funzione per trovare tutti i commenti relativi ad un certo post
	 */
	
	@Override
	public List<Comment> getCommentsFromPost (Post post) {
		
		return this.getSession().getNamedQuery("Comment.getCommentsFromPost").setParameter("post", post).getResultList();
	}
	
	
}
