package it.univpm.advprog.blog.model.entities;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@NamedQueries({
	@NamedQuery(
			name = "getCommentsFromPost",
			query = "SELECT c FROM Comment c WHERE c.post = :post"
			)
})
public class Comment implements Serializable {

	private long id;
	private User author;
	private Post post;
	private String title;
	private String description;
	private boolean hide = false;
	
	
	/** 
	 * Getter per la proprietà id del commento
	 * 
	 * @return username dell'utente 
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id" , nullable = false)
	public long getId() {
		return this.id;
	}
	
	/** 
	 * Setter per la proprietà id del commento
	 * 
	 * @param id: id del commento da settare
	 */
	
	public void setId(long id) {
		this.id = id;
	}
	
	
	//Mapping della relazione con l'entità User 
	
	/** 
	 * Getter per la proprietà author del commento
	 * 
	 * @return autore del commento
	 */
	
	@ManyToOne //Di default fetch di tipo EAGER
	@JoinColumn(name = "author" , nullable = false)
	public User getAuthor() {
		return this.author;
	}
	
	
	/** 
	 * Setter per la proprietà author del commento
	 * 
	 * @param author: autore del commento da settare
	 */
	
	public void setAuthor(User author) {
		this.author = author;
	}
	
	
	//Mapping della relazione con l'entità Post
	
	/** 
	 * Getter per la proprietà post del commento
	 * 
	 * @return post associato al commento
	 */
	
	@ManyToOne //Di default fetch di tipo EAGER
	@JoinColumn(name = "post_id" , nullable = false)
	public Post getPost() {
		return this.post;
	}
	
	
	/** 
	 * Setter per la proprietà post del commento
	 * 
	 * @param post: post associato al commento da settare
	 */
	
	public void setPost(Post post) {
		this.post = post;
	}
	
	
	/** 
	 * Getter per la proprietà title del commento
	 * 
	 * @return titolo del commento
	 */	
	@Column(name = "title" , nullable = false, length = 100)
	public String getTitle() {
		return this.title;
	}
	
	
	/** 
	 * Setter per la proprietà title del commento
	 * 
	 * @param title: titolo da settare per il commento 
	 */
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	/** 
	 * Getter per la proprietà description del commento
	 * 
	 * @return descrizione del commento
	 */	
	
	@Column(name = "description" , nullable = false, length = 300)
	public String getDescription() {
		return this.description;
	}

	
	/** 
	 * Setter per la proprietà description del commento
	 * 
	 * @param description: descrizione da settare per il commento 
	 */
	
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter per la proprietà hide.
	 *
	 * @return se il commento è nascosto o meno
	 */
	@Column(nullable=false)
	public boolean isHide() {
		return hide;
	}

	/**
	 * Setter per la proprietà hide.
	 *
	 * @param hide specifico se il commento è nascosto o meno
	 */
	public void setHide(boolean hide) {
		this.hide = hide;
	}
}
