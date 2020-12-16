package it.univpm.advprog.blog.model.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.*;


@Entity
@Table(name = "users")
public class User implements Serializable {

	
	private String username;
	private String firstname;
	private String lastname;
	private String password;
	private String imageProfile;
	private boolean disabled = false;
	private boolean admin = false;
	private Set<Comment> comments = new HashSet<Comment>();
	private Set<Post> posts = new HashSet<Post>();
	
	
	/** 
	 * Getter per la proprietà username dell'User 
	 * 
	 * @return username dell'utente 
	 */

	@Id
	@Column(name = "username", nullable = false, length = 20)
	public String getUsername() {
		return this.username;
	}
	
	/** 
	 * Setter per la proprietà username dell'User 
	 * 
	 * @param username: username da settare per l'utente 
	 */
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	/** 
	 * Getter per la proprietà firstname dell'User 
	 * 
	 * @return il nome dell'utente 
	 */
	
	@Column(name = "firstname" , nullable = false, length = 100)
	public String getFirstName() {
		return this.firstname;
	}
	
	
	/** 
	 * Setter per la proprietà firstname dell'User 
	 * 
	 * @param firstname: nome dell'utente da settare
	 */
	
	public void setFirstName(String firstname) {
		this.firstname = firstname;
	}
	
	
	/** 
	 * Getter per la proprietà lastname dell'User 
	 * 
	 * @return cognome dell'utente 
	 */
	
	
	@Column(name = "lastname" , nullable = false, length = 100)
	public String getLastName() {
		return this.lastname;
	}
	
	
	/** 
	 * Setter per la proprietà lastname dell'User 
	 * 
	 * @param lastname: cognome dell'utente da settare
	 */
	
	public void setLastName(String lastname) {
		this.lastname = lastname;
	}
	
	
	/** 
	 * Getter per la proprietà password dell'User 
	 * 
	 * @return password dell'utente 
	 */
	
	@Column(name = "password" , nullable = false, length = 100)
	public String getPassword() {
		return this.password;
	}
	
	
	/** 
	 * Setter per la proprietà firstname dell'User 
	 * 
	 * @param password: password dell'utente da settare
	 */
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	/** 
	 * Getter per la proprietà imageProfile dell'User 
	 * 
	 * @return stringa relativa al nome del file che contiene l'immagine profilo
	 */
	
	@Column(name = "imageProfile" , nullable = true, length = 100)
	public String getImageProfile() {
		return this.imageProfile;
	}
	
	
	/** 
	 * Setter per la proprietà imageProfile dell'User 
	 * 
	 * @param imgprofile: nome del file contenente l'immagine profilo dell'utente da settare
	 */
	
	public void setImageProfile(String imgprofile) {
		//TODO: impostare il caricamento di un file immagine da qualche parte nel server ed il solo salvataggio del nome
        // del file in questo campo "image"
		this.imageProfile = imgprofile;
	}
	
	
	
	/** 
	 * Getter per la proprietà comments dell'User 
	 * 
	 * @return commenti dell'utente
	 */
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "author", orphanRemoval = true)
	public Set<Comment> getComments() {
		return this.comments;
	}
	
	
	/** 
	 * Setter per la proprietà comments dell'User 
	 * 
	 * @param comments: commenti da associare all'utente 
	 */
	
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	
	/** 
	 * Metodo per aggiungere commenti 
	 * 
	 * @param comment: commento da aggiugnere al Set 
	 */
	
	public void addComment(Comment comment) {
		comment.setAuthor(this); 
		this.comments.add(comment);
	}
	
	
	/** 
	 * Getter per la proprietà posts dell'User
	 *  
	 * @return posts dell'utente
	 */	
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "author", orphanRemoval = true)
	public Set<Post> getPosts() {
		return this.posts;
	}
	
	
	/** 
	 * Setter per la proprietà posts dell'User 
	 * 
	 * @param posts: post da associare all'utente 
	 */
	
	public void setPosts(Set<Post> posts) {
		
		this.posts = posts;
	}
	
	
	/** 
	 * Metodo per aggiungere post 
	 * 
	 * @param post: post da aggiugnere al Set 
	 */
	
	public void addPost(Post post) {
		post.setAuthor(this);
		this.posts.add(post);
	}

	/**
	 * Getter della proprietà disabled.
	 *
	 * @return se l'utente è disabilitato o meno.
	 */
	@Column(nullable=false)
	public boolean isDisabled() {
		return disabled;
	}

	/**
	 * Setter della proprietà disabled.
	 *
	 * @param disabled specifico se l'utente è disabilitato o meno
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	/**
	 * Getter della proprietà admin.
	 *
	 * @return se l'utente è un admin o meno
	 */
	@Column(nullable=false)
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * Setter della proprietà admin.
	 *
	 * @param admin specifico se l'utente è un admin o meno
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}
