package it.univpm.advprog.blog.services;

import java.util.List;

import it.univpm.advprog.blog.model.entities.Comment;
import it.univpm.advprog.blog.model.entities.Post;
import it.univpm.advprog.blog.model.entities.User;

public interface CommentService {

	List<Comment> getAll();

    Comment findCommentById(long id);
	
	Comment create(User author, Post post, String title, String description);
	
	Comment update(Comment comment);
	
	void delete(Comment comment);

//	List<Comment> findCommentsByPostAndAuthor(Post post, User author);
}
