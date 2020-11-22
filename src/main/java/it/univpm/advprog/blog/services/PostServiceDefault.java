package it.univpm.advprog.blog.services;

import it.univpm.advprog.blog.model.dao.PostDao;
import it.univpm.advprog.blog.model.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service("postService")
public class PostServiceDefault implements PostService {

    private PostDao postRepository;

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
     * Funzione per cercare tutti i post appartenenti ad un tag specificato.
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
     * Funzione per cercare tutti i post appartenenti ad un insieme di tag specificato.
     *
     * @param tags insieme di tag specificato
     * @return lista contenente i post che soddisfano i criteri di ricerca
     */
    @Transactional(readOnly = true)
    @Override
    public List<Post> getByTags(Set<Tag> tags) {
        return null; //TODO
    }

    /**
     * Funzione per creare un nuovo post.
     *
     * @param title            titolo del post
     * @param author           autore del post
     * @param shortDescription descrizione breve del post
     * @param longDescription  descrizione estesa del post
     * @param tags             tag del post
     * @param archive          archivio del post
     * @return nuovo post creato
     */
    @Transactional
    @Override
    public Post create(String title, User author, String shortDescription, String longDescription, Set<Tag> tags,
                       Archive archive) {
        return this.postRepository.create(title, author, shortDescription, longDescription, tags, archive,
                null, null);
    }

    /**
     * Funzione per creare un nuovo post (specificando anche i parametri opzionali).
     *
     * @param title            titolo del post
     * @param author           autore del post
     * @param shortDescription descrizione breve del post
     * @param longDescription  descrizione estesa del post
     * @param tags             tag del post
     * @param archive          archivio del post
     * @param image            immagine di copertina del post
     * @param attachments      allegati del post
     * @return nuovo post creato
     */
    @Transactional
    @Override
    public Post create(String title, User author, String shortDescription, String longDescription, Set<Tag> tags,
                       Archive archive, String image, Set<Attachment> attachments) {
        return this.postRepository.create(title, author, shortDescription, longDescription, tags, archive, image,
                attachments);
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
}
