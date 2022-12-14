package it.univpm.advprog.blog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univpm.advprog.blog.model.dao.FileDao;
import it.univpm.advprog.blog.model.entities.File;
import it.univpm.advprog.blog.model.entities.Post;

@Service("fileService")
public class FileServiceDefault implements FileService {

	FileDao fileRepository;
	
	/**
	 * Metodo per ricavare tutti i file
	 */
	@Transactional(readOnly=true)
	@Override
	public List<File> getAll() {
		return this.fileRepository.getAll();
	}

	/**
	 * Metodo per ricavare i file da un id
	 * @param id: id del file da ricercare
	 * @return file ricercato
	 */
	@Transactional(readOnly=true)
	@Override
	public File getById(long id) {
		return this.fileRepository.getById(id);
	}

	/**
	 * Metodo per ricavare i file dal nome
	 * @param name: nome del file da ricavare
	 * @return file ricercato
	 */
	@Transactional(readOnly=true)
	@Override
	public File getByName(String name) {
		return this.fileRepository.getByName(name);
	}

	/**
	 * Metodo per ricavare la lista dei file scaribili o non scaricabili
	 * @param noDownloadable: flag per indicare se si √® interessati a file scaricabili o no
	 * @return lista dei file che soddisfano il parametro
	 */
	@Transactional(readOnly=true)
	@Override
	public List<File> getByNoDownloadable(boolean noDownloadable) {
		return this.fileRepository.getByNoDownloadable(noDownloadable);
	}
	
	/**
	 * Metodo per restituire i file associati ad un certo post
	 * @param post: post ricercato
	 * @return lista dei file associati al post
	 */
	@Transactional(readOnly=true)
	@Override
	public List<File> getFileByPost(Post post) {
		List<File> files=this.fileRepository.getAll();
		files.removeIf(file -> !file.getPost().equals(post));
		return files;
		
	}

	/**
	 * Metodo per creare un file
	 * @param description: descrizione del file 
	 * @param hide: visibilit√† del file
	 * @param post: post a cui il file √® associato
	 * @param name: nome del file
	 * @param noDownloadable: flag per indicare se il file √® scaricabile o meno
	 */
	@Override
	@Transactional
	public File create(String description, boolean hide, Post post,String name, boolean noDownloadable) {
		return this.fileRepository.create(description, hide, post, name, noDownloadable);
	}
	
	/**
	 * Metodo per creare un file
	 * @param description: descrizione del file 
	 * @param post: post a cui il file √® associato
	 * @param name: nome del file
	 */
	@Override
	@Transactional
	public File create(String description, Post post, String name) {
		return this.fileRepository.create(description, post, name);
	}

	/**
	 * Metodo per creare un file
	 * @param description: descrizione del file 
	 * @param hide: visibilit√† del file
	 * @param post: post a cui il file √® associato
	 * @param name: nome del file
	 */
	@Override
	@Transactional
	public File create(String description, boolean hide, Post post, String name) {
		return this.fileRepository.create(description, hide, post, name);
	}

	/**
	 * Metodo per creare un file
	 * @param description: descrizione del file 
	 * @param post: post a cui il file √® associato
	 * @param name: nome del file
	 * @param noDownloadable: flag per indicare se il file √® scaricabile o meno
	 */
	@Override
	@Transactional
	public File create(String description, Post post, String name, boolean noDownloadable) {
		return this.fileRepository.create(description, post, name, noDownloadable);
	}

	/**
	 * Metodo per aggiornare un file
	 * @param file: file da aggiornare
	 * @return file aggiornato	
	 */
	@Transactional
	@Override
	public File update(File file) {
		return this.fileRepository.update(file);
	}

	/**
	 * Metodo per cancellare un file
	 * @param file: file da eliminare
	 */
	@Transactional
	@Override
	public void delete(File file) {
		this.fileRepository.delete(file);
	}

	/**
	 * Setter per la propriet√† che si riferisce al DAO dell'entit√† File.
	 *
	 * @param fileRepository DAO dell'entit√† File da settare
	 */
	@Autowired
	public void setFileRepository(FileDao fileRepository) {
		this.fileRepository = fileRepository;
	}
}
