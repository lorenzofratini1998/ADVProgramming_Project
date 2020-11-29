package it.univpm.advprog.blog.model.entities;
	
import javax.persistence.*;
	
import antlr.collections.List;
	
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

	
	@Entity
	@Table(name="tags")
	public class Tag implements Serializable {
		
		private String name;
		private Set<Post> posts = new HashSet<Post>();
		
		/**
		 * Getter per la proprieta' name del tag
		 * 
		 * return name del tag
		 */
	@Id
	@Column(name="name",nullable=false, length=20)
	public String getName() {
		return name;
	}
	
	/**
	* Setter per la proprieta' name del tag
	* @param name del name del tag
	*/
	
	public void setName(String name) {
		this.name=name;
	}
	
	/**
	 * Vista dall'esempio di spegni tra singer e instrument
	 * @return
	 */
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy="tags")
	public Set<Post> getPosts(){
		return posts;
	}
	
	public void setPosts(Set<Post> posts) {
		this.posts=posts;
	}
	
	void addPost(Post post) {
	}
}
