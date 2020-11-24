package it.univpm.advprog.blog.services;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univpm.advprog.blog.model.dao.AttachmentDao;
import it.univpm.advprog.blog.model.dao.FileDao;
import it.univpm.advprog.blog.model.entities.Attachment;
import it.univpm.advprog.blog.model.entities.File;
import it.univpm.advprog.blog.model.entities.Post;

@Transactional()
@Service("fileService")
public class FileServiceDefault implements FileService {

	FileDao fileRepository;
	AttachmentDao attachmentRepository;
	
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
	 * @param downloadble: flag per indicare se si è interessati a file scaricabili o no
	 * @return lista dei file che soddisfano il parametro
	 */
	@Transactional(readOnly=true)
	@Override
	public List<File> getByDownloadble(boolean downloadble) {
		return this.fileRepository.getByDownloadble(downloadble);
	}
	
	/**
	 * Metodo per ottenere l'allegato generico del file
	 * @param file: file d'interesse
	 * @return allegato se il file esiste
	 * @return null se il file non esiste
	 */
	@Transactional(readOnly=true)
	@Override
	public Attachment getAttachmentByFile(File file) {
		List<Attachment> attachments=this.attachmentRepository.getAll();
		for(Attachment attachment : attachments) {
			if(attachment.getId()==file.getId()) 
				return attachment;
			}
		return null;
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
		for(File file: files) {
			Attachment attachment=this.getAttachmentByFile(file);
			Set<Post> postAttachments=attachment.getPosts();
			if(!postAttachments.contains(post)) {
				files.remove(attachment);
			}
		}
		return files;
		
	}

	/**
	 * Metodo per creare un file
	 * @param id: id del file
	 * @param name: nome del file
	 * @param downloadble: flag per indicare se il file è scaricabile o meno
	 */
	@Transactional(readOnly=true)
	@Override
	public File create(long id, String name, boolean downloadble) {
		return this.fileRepository.create(id, name, downloadble);
		
	}

	/**
	 * Metodo per aggiornare un file
	 * @param file: file da aggiornare
	 * @return file aggiornato	
	 */
	@Transactional(readOnly=true)
	@Override
	public File update(File file) {
		return this.fileRepository.update(file);
	}

	/**
	 * Metodo per cancellare un file
	 * @param file: file da eliminare
	 */
	@Transactional(readOnly=true)
	@Override
	public void delete(File file) {
		this.fileRepository.delete(file);

	}

}
