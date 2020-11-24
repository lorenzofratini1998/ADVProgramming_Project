package it.univpm.advprog.blog.model.dao;

import java.util.List;
import java.util.Set;

import it.univpm.advprog.blog.model.entities.Attachment;
import it.univpm.advprog.blog.model.entities.File;
import it.univpm.advprog.blog.model.entities.Post;

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
		return (File) getSession().createNamedQuery("getFileByName", File.class);
	}

	/**
	 * Metodo per ricercare la lista dei file che sono scaricabili o meno
	 * @param downloadble: parametro per indicare se si è interessati a file scaricabili o meno
	 * @return lista dei file scaricabili o meno
	 */
	@Override
	public List<File> getByDownloadble(boolean downloadble) {
		return (List<File>) getSession().createNamedQuery("getFileByDownloadble", File.class);
	}

	/**
	 * Metodo per creare un nuovo file
	 * @param id: id del file da creare
	 * @param name: nome del file 
	 * @param downloadble: flag per indicare se il file è scaricabile o meno
	 * @return file creato
	 */
	@Override
	public File create(long id, String name, boolean downloadble) {
		return this.create(id, name, downloadble);
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
