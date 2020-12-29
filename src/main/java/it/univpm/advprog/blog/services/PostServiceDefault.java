package it.univpm.advprog.blog.services;

import it.univpm.advprog.blog.model.dao.ArchiveDao;
import it.univpm.advprog.blog.model.dao.PostDao;
import it.univpm.advprog.blog.model.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("postService")
public class PostServiceDefault implements PostService {

    private PostDao postRepository;
    private ArchiveDao archiveRepository;

    /**
     * Funzione per restituire la lista di tutti i post.
     *
     * @return lista contenente tutti i post
     */
    @Transactional(readOnly = true)
    @Override
    public List<Post> getAll() {
        return this.postRepository.getAll();
    }

    /**
     * Funzione per restituire la lista di tutti i post nascosti/non nascosti.
     *
     * @return lista contenente tutti i post nascosti/non nascosti
     */
    @Transactional(readOnly = true)
    @Override
    public List<Post> getAllByHide(boolean hide) {
        return this.postRepository.getAllByHide(hide);
    }

    /**
     * Funzione per cercare un particolare post specificandone l'ID.
     *
     * @param id id del post da cercare
     * @return eventuale post trovato
     */
    @Transactional(readOnly = true)
    @Override
    public Post getById(long id) {
        return this.postRepository.getById(id);
    }

    /**
     * Funzione per cercare un particolare post specificandone il titolo.
     *
     * @param title titolo del post da cercare
     * @return eventuale post trovato
     */
    @Transactional(readOnly = true)
    @Override
    public Post getByTitle(String title) {
        return this.postRepository.getByTitle(title);
    }

    /**
     * Funzione per cercare tutti i post appartenenti ad un insieme di tag specifico.
     *
     * @param tags insieme di tag specificato
     * @return lista contenente i post che soddisfano i criteri di ricerca
     */
    @Transactional(readOnly = true)
    @Override
    public List<Post> getByTags(Set<Tag> tags) { //TODO: spostare sul DAO
        List<Post> posts = this.postRepository.getAll();
        for (Post post : posts) {
            Set<Tag> tagsPost = post.getTags();
            if (!tagsPost.containsAll(tags)) {
                posts.remove(post);
            }
        }
        return posts;
    }

    /**
     * Funzione per cercare tutti i post che utilizzano un insieme di allegati specifico.
     *
     * @param attachments insieme di allegati specificato
     * @return lista contenente i post che soddisfano i criteri di ricerca
     */
    @Transactional(readOnly = true)
    @Override
    public List<Post> getByAttachments(Set<Attachment> attachments) {//TODO: spostare sul DAO
        List<Post> posts = this.postRepository.getAll();
        for (Post post : posts) {
            Set<Attachment> attachmentsPost = post.getAttachments();
            if (!attachmentsPost.containsAll(attachments)) {
                posts.remove(post);
            }
        }
        return posts;
    }

    /**
     * Funzione per creare un nuovo post.
     *
     * @param title            titolo del post
     * @param author           autore del post
     * @param shortDescription descrizione breve del post
     * @param longDescription  descrizione estesa del post
     * @param tag              singolo tag del post
     * @return nuovo post creato
     */
    @Transactional
    @Override
    public Post create(String title, User author, String shortDescription, String longDescription, Tag tag) {
        Set<Tag> tags = new HashSet<>();
        tags.add(tag);

        return this.create(title, author, false, shortDescription, longDescription, tags);
    }

    /**
     * Funzione per creare un nuovo post.
     *
     * @param title            titolo del post
     * @param author           autore del post
     * @param shortDescription descrizione breve del post
     * @param longDescription  descrizione estesa del post
     * @param tags             tag del post
     * @return nuovo post creato
     */
    @Transactional
    @Override
    public Post create(String title, User author, String shortDescription, String longDescription, Set<Tag> tags) {
        return this.create(title, author, false, shortDescription, longDescription, tags);
    }

    /**
     * Funzione per creare un nuovo post (specificando anche i parametri opzionali).
     *
     * @param title            titolo del post
     * @param author           autore del post
     * @param hide             se il post è nascosto
     * @param shortDescription descrizione breve del post
     * @param longDescription  descrizione estesa del post
     * @param tags             tag del post
     * @return nuovo post creato
     */
    @Transactional
    @Override
    public Post create(String title, User author, boolean hide, String shortDescription, String longDescription,
                       Set<Tag> tags) {
        Archive archive = createCurrentArchive();

        return this.postRepository.create(title, author, hide, shortDescription, longDescription, tags, archive);
    }

    /**
     * Funzione per aggiornare il post specificato.
     *
     * @param post post da aggiornare
     * @return post aggiornato
     */
    @Transactional
    @Override
    public Post update(Post post) {
        return this.postRepository.update(post);
    }

    /**
     * Funzione per eliminare il post specificato.
     *
     * @param post post da eliminare
     */
    @Transactional
    @Override
    public void delete(Post post) {
        this.postRepository.delete(post);
    }

    /**
     * Funzione per eliminare un post specificandone l'ID.
     *
     * @param id id del post
     */
    @Transactional
    @Override
    public void delete(long id) {
        Post post = this.getById(id);
        this.postRepository.delete(post);
    }

    /**
     * Funzione per eliminare un post specificandone il titolo.
     *
     * @param title titolo del post
     */
    @Transactional
    @Override
    public void delete(String title) {
        Post post = this.getByTitle(title);
        this.postRepository.delete(post);
    }

    @Transactional
    @Override
    public Archive createCurrentArchive() {
        Calendar calendar = Calendar.getInstance();
        String archiveName = new SimpleDateFormat("MMMMM yyyy").format(calendar.getTime());
        // try to get the archive by generated name
        Archive archive = this.archiveRepository.getByName(archiveName);
        if (archive == null) {
            // archive does not exist, create it
            archive = this.archiveRepository.create(archiveName);
        }
        return archive;
    }

    /**
     * Setter per la proprietà che si riferisce al DAO dell'entità Post.
     *
     * @param postRepository DAO dell'entità Post da settare
     */
    @Autowired
    public void setPostRepository(PostDao postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * Setter per la proprietà che si riferisce al DAO dell'entità Archive.
     *
     * @param archiveRepository DAO dell'entità Archive da settare
     */
    @Autowired
    public void setArchiveRepository(ArchiveDao archiveRepository) {
        this.archiveRepository = archiveRepository;
    }
}
