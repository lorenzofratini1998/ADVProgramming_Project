package it.univpm.advprog.blog.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="files")
@NamedQueries({
	@NamedQuery(
			name="getFileByName",
			query="SELECT f FROM File f WHERE f.name= :name"
			),
	@NamedQuery(
			name="getByDownloadable",
			query="SELECT f FROM File f WHERE f.noDownloadable= :noDownloadable"
			)
})
@PrimaryKeyJoinColumn(name="attachment_id")
public class File extends Attachment implements Serializable{
	
	private String name;
	private boolean noDownloadable=false;
	
	/**
	 * Getter per il nome del file
	 * @return nome del file
	 */
	@Column(nullable=false, unique=true, length=100)
	public String getName() {
		return name;
	}
	
	/**
	 * Setter per il nome del file
	 * @param name: nome del file da settare
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter per la proprietà noDownloadable
	 * @return file scaricabile o meno
	 */
	@Column(nullable=false)
	public boolean isNoDownloadable() {
		return noDownloadable;
	}
	
	/**
	 * Setter per la proprietà noDownloadable
	 * @param noDownloadable: attributo da settare
	 */
	public void setNoDownloadable(boolean noDownloadable) {
		this.noDownloadable = noDownloadable;
	}
	
	
}
