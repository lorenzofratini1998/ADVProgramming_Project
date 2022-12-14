package it.univpm.advprog.blog.model.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.univpm.advprog.blog.model.entities.Comment;
import it.univpm.advprog.blog.model.entities.Post;
import it.univpm.advprog.blog.model.entities.User;

@Repository("commentDao")
public class CommentDaoDefault extends DefaultDao implements CommentDao {

	@Override
	public Comment findCommentById(long id) {
		
		return this.getSession().get(Comment.class, id);
		
	}

	@Override
	public List<Comment> getAll() {
		return getSession().
				createQuery("from Comment c order by c.id desc", Comment.class).

				getResultList();
	}

	@Override
	public Comment create(User author, Post post, String title, String description) {
		
		Comment newComment = new Comment();
		
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
	
}
