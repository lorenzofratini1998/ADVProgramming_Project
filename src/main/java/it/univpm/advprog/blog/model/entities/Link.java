package it.univpm.advprog.blog.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="links")
@NamedQueries({
	@NamedQuery(
			name="getLinkByLink",
			query="SELECT l FROM Link l WHERE l.link= :link"
			)
})
@PrimaryKeyJoinColumn(name="attachment_id")
public class Link extends Attachment implements Serializable {
	
	private String link;

	/**
	 * Getter per la proprietà link
	 * @return link 
	 */
	@Column(nullable=false, length = 300)
	public String getLink() {
		return link;
	}

	/**
	 * Setter per la proprietà link
	 * @param link: link da settare
	 */
	public void setLink(String link) {
		this.link = link;
	}
	
	
}
