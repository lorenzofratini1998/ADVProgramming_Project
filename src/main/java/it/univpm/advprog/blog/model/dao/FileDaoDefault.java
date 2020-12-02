package it.univpm.advprog.blog.model.dao;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import it.univpm.advprog.blog.model.entities.Attachment;
import it.univpm.advprog.blog.model.entities.File;
import it.univpm.advprog.blog.model.entities.Post;

@Repository("fileDao")
public class FileDaoDefault extends DefaultDao implements FileDao  {

	/**
	 * Metodo per ritornare tutti i file
	 * @return lista di tutti i file
	 */
	@Override
	public List<File> getAll() {
		 return getSession().
	                createQuery("from File f", File.class).
	                getResultList();
	}
	
	/**
	 * Metodo per ottenere un file dato un id
	 * @param id: id del file da cercare
	 * @return file ricercato
	 */
	@Override
	public File getById(long id) {
		return getSession().find(File.class, id);
	}
	
	/**
	 * Metodo per ottenere un file dal nome
	 * @param name: nome del file da cercare
	 * @return file con nome ricercato
	 */
	@Override
	public File getByName(String name) {
		return getSession().createNamedQuery("getFileByName", File.class).setParameter("name", name).getSingleResult();
	}

	/**
	 * Metodo per ricercare la lista dei file che sono scaricabili o meno
	 * @param downloadable: parametro per indicare se si è interessati a file scaricabili o meno
	 * @return lista dei file scaricabili o meno
	 */
	@Override
	public List<File> getByDownloadable(boolean downloadable) {
		return getSession().createNamedQuery("getFileByDownloadable", File.class).getResultList();
	}

	/**
	 * Metodo per creare un nuovo file
	 * @param description: descrizione del file da creare
	 * @param hide: visibilità del file
	 * @param post: post a cui il file è associato
	 * @param name: nome del file
	 * @param downloadable: flag per indicare se il file è scaricabile o meno
	 * @return file creato
	 */
	@Override
	public File create(String description, boolean hide, Post post, String name, boolean downloadable) {
		File file=new File();
		file.setDescription(description);
		file.setHide(hide);
		file.setPost(post);
		file.setName(name);
		file.setNoDownloadable(downloadable);
		this.getSession().save(file);
		return file;
	}
	
	/**
	 * Metodo per creare un nuovo file
	 * @param description: descrizione del file da creare
	 * @param post: post a cui il file è associato
	 * @param name: nome del file
	 * @return file creato
	 */
	@Override
	public File create(String description, Post post, String name) {
		File file=new File();
		file.setDescription(description);
		file.setPost(post);
		file.setName(name);
		this.getSession().save(file);
		return file;
	}

	/**
	 * Metodo per creare un nuovo file
	 * @param description: descrizione del file da creare
	 * @param hide: visibilità del file
	 * @param post: post a cui il file è associato
	 * @param name: nome del file
	 * @return file creato
	 */
	@Override
	public File create(String description, boolean hide, Post post, String name) {
		File file=new File();
		file.setDescription(description);
		file.setHide(hide);
		file.setPost(post);
		file.setName(name);
		this.getSession().save(file);
		return file;
	}

	/**
	 * Metodo per creare un nuovo file
	 * @param description: descrizione del file da creare
	 * @param hide: visibilità del file
	 * @param name: nome del file
	 * @param downloadable: flag per indicare se il file è scaricabile o meno
	 * @return file creato
	 */
	@Override
	public File create(String description, Post post, String name, boolean downloadable) {
		File file=new File();
		file.setDescription(description);
		file.setPost(post);
		file.setName(name);
		file.setNoDownloadable(downloadable);
		this.getSession().save(file);
		return file;
	}

	/**
	 * Metodo per aggiornare un file
	 * @param file: file da aggiornare
	 * @return file aggiornato
	 */
	@Override
	public File update(File file) {
		return (File) this.getSession().merge(file);
	}

	/**
	 * Metodo per eliminare un file
	 * @param file: file da eliminare
	 */
	@Override
	public void delete(File file) {
		this.getSession().delete(file);
	}

}
