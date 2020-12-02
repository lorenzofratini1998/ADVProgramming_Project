package it.univpm.advprog.blog.model.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@NamedQueries({
	@NamedQuery(
			name = "findAllUsers",
			query = "SELECT u FROM User u"
			),
	@NamedQuery(
			name = "findPostsOfUser",
			query = "SELECT u FROM User u JOIN Post p ON u.username = p.author WHERE u.username = :username"
			)
	
})
public class User {

	
	private String username;
	private String firstname;
	private String lastname;
	private String password;
	private String imageProfile;
	private boolean disabled = false;
	private Set<Comment> comments = new HashSet<Comment>();
	private Set<Post> posts = new HashSet<Post>();
	
	
	//Getter per la proprietà username dell'User

	@Id
	@Column(name = "username", nullable = false, length = 20)
	public String getUsername() {
		return this.username;
	}
	
	//Setter per la proprietà username dell'User
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	//Getter per la proprietà firstname dell'User
	
	@Column(name = "firstname" , nullable = false, length = 100)
	public String getFirstName() {
		return this.firstname;
	}
	
	
	//Setter per la proprietà firstname dell'User
	
	public void setFirstName(String firstname) {
		this.firstname = firstname;
	}
	
	
	//Getter per la proprietà lastname dell'User
	
	
	@Column(name = "lastname" , nullable = false, length = 100)
	public String getLastName() {
		return this.lastname;
	}
	
	
	//Setter per la proprietà lastname dell'User
	
	public void setLastName(String lastname) {
		this.lastname = lastname;
	}
	
	
	//Getter per la proprietà password dell'User
	
	@Column(name = "password" , nullable = false, length = 100)
	public String getPassword() {
		return this.password;
	}
	
	
	//Setter per la proprietà password dell'User
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	//Getter per la proprietà imageProfile dell'User
	
	@Column(name = "imageProfile" , nullable = true, length = 100)
	public String getImageProfile() {
		return this.imageProfile;
	}
	
	
	//Setter per la proprietà imageProfile dell'User
	
	public void setImageProfile(String imgprofile) {
		//TODO: impostare il caricamento di un file immagine da qualche parte nel server ed il solo salvataggio del nome
        // del file in questo campo "image"
		this.imageProfile = imgprofile;
	}
	
	
	
	//Mapping della relazione con l'entità Comment (getter della proprietà comments)
	
	@OneToMany(mappedBy = "author", orphanRemoval = true)
	public Set<Comment> getComments() {
		return this.comments;
	}
	
	
	//Setter per la proprietà comments dell'User
	
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	
	//Metodo per aggiungere un commento al set di commenti.
	
	public void addComment(Comment comment) {
		comment.setAuthor(this); 
		this.comments.add(comment);
	}
	
	
	//Mapping della relazione con l'entità Post (Getter per la proprietà posts)
	
	
	@OneToMany(mappedBy = "author", orphanRemoval = true)
	public Set<Post> getPosts() {
		return this.posts;
	}
	
	
	//Setter per la proprietà posts dell'User
	
	public void setPosts(Set<Post> posts) {
		
		this.posts = posts;
	}
	
	
	//Metodo per aggiungere un post al set di post.
	
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
}
