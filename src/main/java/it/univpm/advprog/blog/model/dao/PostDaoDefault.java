package it.univpm.advprog.blog.model.dao;

import it.univpm.advprog.blog.model.entities.*;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository("postDao")
public class PostDaoDefault extends DefaultDao implements PostDao {

    /**
     * Funzione per restituire la lista di tutti i post.
     *
     * @return lista contenente tutti i post
     */
    @Override
    public List<Post> getAll() {
        return getSession().
                createQuery("from Post p", Post.class).
                getResultList();
    }

    /**
     * Funzione per cercare un particolare post specificandone l'ID.
     *
     * @param id ID del post da cercare
     * @return eventuale post trovato
     */
    @Override
    public Post getById(long id) {
        return getSession().find(Post.class, id);
    }

    /**
     * Funzione per cercare un particolare post specificandone il titolo.
     *
     * @param title titolo del post da cercare
     * @return eventuale post trovato
     */
    @Override
    public Post getByTitle(String title) {
        return (Post) getSession().getNamedQuery("Post.getPostByTitle").setParameter("title", title).
                uniqueResult();
    }

    /**
     * Funzione per cercare tutti i post contenuti in un particolare archivio.
     *
     * @param archive archivio specificato
     * @return lista contenente i post che soddisfano i criteri di ricerca
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Post> getByArchive(Archive archive) {
        return (List<Post>) getSession().getNamedQuery("Post.getPostsByArchive").
                setParameter("archive", archive.getName()).
                getResultList();
    }

    /**
     * Funzione per cercare tutti i post scritti da un particolare utente.
     *
     * @param author autore specificato
     * @return lista contenente i post che soddisfano i criteri di ricerca
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Post> getByAuthor(String username) {
        return (List<Post>) getSession().getNamedQuery("Post.getPostsByAuthor").
                setParameter("authorName", username).
                getResultList();
    }

    /**
     * Funzione per creare un nuovo post.
     *
     * @param title            titolo del post
     * @param author           autore del post
     * @param shortDescription descrizione breve del post
     * @param longDescription  descrizione estesa del post
     * @param tag              tag del post
     * @param archive          archivio del post
     * @return nuovo post creato
     */
    @Override
    public Post create(String title, User author, String shortDescription, String longDescription, Tag tag,
                       Archive archive) {
        Set<Tag> tags = new HashSet<>();
        tags.add(tag);
        return this.create(title, author, false, shortDescription, longDescription, tags, archive, null,
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
     * @param archive          archivio del post
     * @return nuovo post creato
     */
    @Override
    public Post create(String title, User author, String shortDescription, String longDescription, Set<Tag> tags,
                       Archive archive) {
        return this.create(title, author, false, shortDescription, longDescription, tags, archive, null,
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
     * @param archive          archivio del post
     * @param attachments      allegati del post
     * @param comments         commenti del post
     * @return nuovo post creato
     */
    @Override
    public Post create(String title, User author, boolean hide, String shortDescription, String longDescription,
                       Set<Tag> tags, Archive archive, Set<Attachment> attachments, Set<Comment> comments) {
        // create a new post
        Post post = new Post();
        // set params
        post.setTitle(title);
        post.setAuthor(author);
        post.setHide(hide);
        post.setShortDescription(shortDescription);
        post.setLongDescription(longDescription);
        post.setTags(tags);
        post.setArchive(archive);
        // faccio i controlli sul valore null almeno posso fare getComments().size() anche quando non ho alcun commento,
        // altrimenti se era null non potevo fare .size()
        if (comments != null) post.setComments(comments);
        if (attachments != null) post.setAttachments(attachments);
        // save the new post
        this.getSession().save(post);
        return post;
    }

    /**
     * Funzione per aggiornare il post specificato.
     *
     * @param post post da aggiornare
     * @return post aggiornato
     */
    @Override
    public Post update(Post post) {
        return (Post) this.getSession().merge(post);
    }

    /**
     * Funzione per eliminare il post specificato.
     *
     * @param post post da eliminare
     */
    @Override
    public void delete(Post post) {
        this.getSession().delete(post);
    }
}
