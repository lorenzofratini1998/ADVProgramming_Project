package it.univpm.advprog.blog.model.dao;

import java.util.List;

import org.hibernate.Session;

import it.univpm.advprog.blog.model.entities.Comment;
import it.univpm.advprog.blog.model.entities.Post;
import it.univpm.advprog.blog.model.entities.User;

public interface CommentDao {

	Session getSession();
	
	public void setSession(Session session);
	
	Comment findCommentById(long id);
	
	Comment create(long id, User author, Post post, String title, String description);
	
	Comment update(Comment comment);
	
	void delete(Comment comment);
	
	List<Comment> getCommentsFromPost(Post post);
	
	
}
