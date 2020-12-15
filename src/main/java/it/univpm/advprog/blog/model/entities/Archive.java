package it.univpm.advprog.blog.model.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

	@Entity
	@Table (name="archives")
	public class Archive implements Serializable {
	    
		private String name;
		private Set<Post> posts = new HashSet<Post>();
	
	
	/** Getter per la proprieta' name dell'archivio
	 * 
	 * return name dell'archivio
	 */
	
	@Id
	@Column(name="name",nullable=false, length= 20)
	public String getName() {
		return name;
	}
	
	/** Setter per la proprieta' name dell'archivio
	 * 
	 * @param name del name dell'archivio
	 * 
	 */
	public void setName(String name) {
		this.name=name;
	}
	
	/**
	 * relazione uno a molti tra archives e posts
	 * 
	 */
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="archive")
	public Set<Post> getPosts(){
		return posts;
	}
	
	public void setPosts(Set<Post> posts) {
		this.posts=posts;
	}
	
	}
