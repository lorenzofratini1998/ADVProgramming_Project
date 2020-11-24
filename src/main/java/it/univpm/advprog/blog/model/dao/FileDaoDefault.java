package it.univpm.advprog.blog.model.dao;

import java.util.List;

import it.univpm.advprog.blog.model.entities.Attachment;
import it.univpm.advprog.blog.model.entities.File;

public class FileDaoDefault extends DefaultDao implements FileDao  {

	/**
	 * Metodo per ritornare tutti i file
	 * @return lista di tutti i file
	 */
	@Override
	public List<File> getAll() {
		 return getSession().
	                createQuery("from File p", File.class).
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

	
	@Override
	public File create(String name, boolean downloadle) {
		// TODO Auto-generated method stub
		return null;
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
