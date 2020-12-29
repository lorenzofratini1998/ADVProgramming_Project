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
                createQuery("from Post p order by p.id desc", Post.class).
                getResultList();
    }

    /**
     * Funzione per restituire la lista di tutti i post nascosti/non nascosti.
     *
     * @return lista contenente tutti i post nascosti/non nascosti
     */
    @Override
    public List<Post> getAllByHide(boolean hide) {
        return getSession().
                getNamedQuery("Post.getPostsByHide").setParameter("hide", hide).getResultList();
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
        return this.create(title, author, false, shortDescription, longDescription, tags, archive);
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
        return this.create(title, author, false, shortDescription, longDescription, tags, archive);
                
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
     * @return nuovo post creato
     */
    @Override
    public Post create(String title, User author, boolean hide, String shortDescription, String longDescription,
                       Set<Tag> tags, Archive archive) {
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
    	
    	 
         this.getSession().update(post);
         Post updated_post = this.getById(post.getId());
         return updated_post;
         
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
