package it.univpm.advprog.blog.model.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


@Entity
@Table(name="attachments")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Attachment {
    
	private long id;
	private String description;
	private boolean hide=false;
	private Post post;
	
	/**
	 * Getter per la proprietà id dell'allegato
	 * @return id dell'allegato
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="attachment_id")
	public long getId() {
		return this.id;
	}
	
	/**
	 * Getter per la proprietà description dell'allegato
	 * @return descrizione dell'allegato
	 */
	@Column(nullable=false, length=100) 
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Getter per l'attributo hide
	 * @return visibilità o meno dell'allegato
	 */
	@Column(nullable=false) 
	public boolean isHide() {
		return this.hide;
	}
	
	/**
	 * Setter per l'attributo id
	 * @param id: id dell'allegato da settare
	 */
	public void setId(long id) {
		this.id=id;
	}
	
	/**
	 * Setter per l'attributo description
	 * @param description: descrizione dell'allegato da settare
	 */
	public void setDescription(String description) {
		this.description=description;
	}
	
	
	/**
	 * Setter per l'attributo hide
	 * @param hide: visibilità dell'allegato da settare
	 */
	public void setHide(boolean hide) {
		this.hide=hide;
	}
	
	/**
	 * Getter per la proprietà post
	 * @return post associato all'allegato
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "post_id", nullable = false)
	public Post getPost() {
		return this.post;
	}
	
	/**
	 * Setter per la proprietà post
	 * @param post: post associato all'allegato da settare
	 */
	public void setPost(Post post) {
		this.post=post;
	}

}
