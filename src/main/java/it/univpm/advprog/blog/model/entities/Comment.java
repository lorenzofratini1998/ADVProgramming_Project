package it.univpm.advprog.blog.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@NamedQueries({
	@NamedQuery(
			name = "getCommentsFromPost",
			query = "SELECT c FROM Comment c WHERE c.post = :post"
			)
})
public class Comment {

	private long id;
	private User author;
	private Post post;
	private String title;
	private String description;
	private boolean hide = false;
	
	
	//Proprietà id 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id" , nullable = false)
	public long getId() {
		return this.id;
	}
	
	
	public void setId(long id) {
		this.id = id;
	}
	
	
	//Mapping della relazione con l'entità User 
	
	@ManyToOne //Di default fetch di tipo EAGER
	@JoinColumn(name = "author" , nullable = false)
	public User getAuthor() {
		return this.author;
	}
	
	
	public void setAuthor(User author) {
		this.author = author;
	}
	
	
	//Mapping della relazione con l'entità Post
	
	@ManyToOne //Di default fetch di tipo EAGER
	@JoinColumn(name = "post_id" , nullable = false)
	public Post getPost() {
		return this.post;
	}
	
	
	public void setPost(Post post) {
		this.post = post;
	}
	
	
	//Proprietà title
	
	@Column(name = "title" , nullable = false, length = 100)
	public String getTitle() {
		return this.title;
	}
	
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	//Proprietà description
	
	@Column(name = "description" , nullable = false, length = 300)
	public String getDescription() {
		return this.description;
	}

	
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
