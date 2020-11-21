package it.univpm.advprog.blog.model.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="attachments")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Attachment {
    
	private long id;
	private String description;
	private boolean hide=false;
	private Set<Post> posts=new HashSet<>();
	
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
	 * Getter per la proprietà posts
	 * @return post associati agli allegati
	 */
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JoinTable(name="posts_attachments",
			joinColumns=@JoinColumn(name="attachment_id",nullable=false),
			inverseJoinColumns=@JoinColumn(name="post_id",nullable=false)
	)
	public Set<Post> getPosts() {
		return this.posts;
	}
	
	/**
	 * Setter per la proprietà posts
	 * @param posts: posts associati agli allegati da settare
	 */
	public void setPosts(Set<Post> posts) {
		this.posts=posts;
	}
	
	/**
	 * Metodo per aggiungere un post all'allegato
	 * @param post: post da aggiungere
	 */
	public void addPost(Post post) {
		post.addTag(this);
		this.posts.add(post);
    }
}
