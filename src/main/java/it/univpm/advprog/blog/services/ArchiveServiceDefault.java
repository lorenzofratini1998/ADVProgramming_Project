package it.univpm.advprog.blog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univpm.advprog.blog.model.dao.ArchiveDao;
import it.univpm.advprog.blog.model.entities.Archive;
import it.univpm.advprog.blog.model.entities.Post;

@Service("archiveService")
public class ArchiveServiceDefault implements ArchiveService {
	
	private ArchiveDao archiveRepository;
	
	/**
	 * Funzione per restituire la lista di tutti gli archivi
	 * 
	 * @return lista con tutti gli archivi
	 */

	@Transactional(readOnly=true)
	@Override
	public List<Archive> getAll() {
		return this.archiveRepository.getAll();
	}

	/**
	 * Funzione per trovare un archivio dal nome
	 * 
	 * @param name dell'archivio
	 * @return archivio con il nome cercato
	 */
	@Transactional(readOnly=true)
	@Override
	public Archive getByName(String name) {
		return this.archiveRepository.getByName(name);
	}
	
	/**
	 * Funzione per creare un archivio
	 * 
	 * @param name dell'archivio da creare
	 */

	@Transactional
	@Override
	public Archive create(String name) {
		return this.archiveRepository.create(name);
	}

	/**
	 * Funzione per fare update di uno specifico archivio
	 * 
	 * @param archive    archivio da aggiornare
	 */
	@Transactional
	@Override
	public Archive update(Archive archive) {
		return this.archiveRepository.update(archive);
	}

	/**
	 * Funzione per elimiare l'archivio specificato
	 * 
	 * @param archive archivio da eliminare
	 */
	@Transactional
	@Override
	public void delete(Archive archive) {
		 this.archiveRepository.delete(archive);
	}
	
    /**
     * Funzione per eliminare un'archivio specificando il nome.
     *
     * @param name nome dell'archivio
     */

	@Transactional
	@Override
	public void delete(String name) {
		Archive archive= this.getByName(name);
        this.archiveRepository.delete(archive);
	}
	
	 /**
     * Setter per la proprietà riferità al DAO dell'entità archive
     *
     * @param archiveRepository DAO dell'entità archive da settare
     */
    @Autowired
    public void setArchiveRepository(ArchiveDao archiveRepository) {
        this.archiveRepository = archiveRepository;
    }


}
