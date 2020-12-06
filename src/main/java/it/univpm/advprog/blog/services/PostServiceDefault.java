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

@Transactional
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
     * Funzione per cercare tutti i post contenuti in un particolare archivio.
     *
     * @param archive archivio specificato
     * @return lista contenente i post che soddisfano i criteri di ricerca
     */
    @Transactional(readOnly = true)
    @Override
    public List<Post> getByArchive(Archive archive) {
        return this.postRepository.getByArchive(archive);
    }

    /**
     * Funzione per cercare tutti i post scritti da un particolare utente.
     *
     * @param author autore specificato
     * @return lista contenente i post che soddisfano i criteri di ricerca
     */
    @Transactional(readOnly = true)
    @Override
    public List<Post> getByAuthor(User author) {
        return this.postRepository.getByAuthor(author);
    }

    /**
     * Funzione per cercare tutti i post appartenenti ad un tag specifico.
     *
     * @param tag tag specificato
     * @return lista contenente i post che soddisfano i criteri di ricerca
     */
    @Transactional(readOnly = true)
    @Override
    public List<Post> getByTag(Tag tag) {
        Set<Tag> tags = new HashSet<>();
        tags.add(tag);

        return this.getByTags(tags);
    }

    /**
     * Funzione per cercare tutti i post appartenenti ad un insieme di tag specifico.
     *
     * @param tags insieme di tag specificato
     * @return lista contenente i post che soddisfano i criteri di ricerca
     */
    @Transactional(readOnly = true)
    @Override
    public List<Post> getByTags(Set<Tag> tags) {
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
     * Funzione per cercare tutti i post che utilizzano un allegato specifico.
     *
     * @param attachment allegato specificato
     * @return lista contenente i post che soddisfano i criteri di ricerca
     */
    @Transactional(readOnly = true)
    @Override
    public List<Post> getByAttachment(Attachment attachment) {
        Set<Attachment> attachments = new HashSet<>();
        attachments.add(attachment);

        return this.getByAttachments(attachments);
    }

    /**
     * Funzione per cercare tutti i post che utilizzano un insieme di allegati specifico.
     *
     * @param attachments insieme di allegati specificato
     * @return lista contenente i post che soddisfano i criteri di ricerca
     */
    @Transactional(readOnly = true)
    @Override
    public List<Post> getByAttachments(Set<Attachment> attachments) {
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
    @Override
    public Post create(String title, User author, String shortDescription, String longDescription, Tag tag) {
        Set<Tag> tags = new HashSet<>();
        tags.add(tag);

        return this.create(title, author, false, shortDescription, longDescription, tags, null,
                null);
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
        return this.create(title, author, false, shortDescription, longDescription, tags, null,
                null);
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
     * @param attachments      allegati del post
     * @param comments         commenti del post
     * @return nuovo post creato
     */
    @Transactional
    @Override
    public Post create(String title, User author, boolean hide, String shortDescription, String longDescription,
                       Set<Tag> tags, Set<Attachment> attachments, Set<Comment> comments) {
        Calendar calendar = Calendar.getInstance();
        String archiveName = new SimpleDateFormat("MMMMM yyyy").format(calendar.getTime());
        // try to get the archive by generated name
        Archive archive = this.archiveRepository.getByName(archiveName);
        if (archive == null) {
            // archive does not exist, create it
            archive = this.archiveRepository.create(archiveName);
        }

        return this.postRepository.create(title, author, hide, shortDescription, longDescription, tags, archive,
                attachments, comments);
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

    /**
     * Setter per la proprietà che si riferisce al DAO dell'entità Post.
     *
     * @param postRepository DAO dell'entità Post da settare
     */
    @Autowired
    public void setPostRepository(PostDao postRepository) {
        this.postRepository = postRepository;
    }

    @Autowired
    public void setArchiveRepository(ArchiveDao archiveRepository) {
        this.archiveRepository = archiveRepository;
    }
}
