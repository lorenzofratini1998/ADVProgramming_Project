package it.univpm.advprog.blog.model.dao;

import java.util.List;

import it.univpm.advprog.blog.model.entities.*;
import org.springframework.stereotype.Repository;

@Repository("tagDao")
public class TagDaoDefault extends DefaultDao implements TagDao {

    /**
     * Funzione per restituire la lista di tutti i tag
     *
     * @return lista dei tag
     */

    public List<Tag> getAll() {
        return getSession().
                createQuery("from Tag t", Tag.class).
                getResultList();
    }

    /**
     * Funzione per trovare un tag specificando il nome
     *
     * @param name del tag da trovare
     * @return tag eventualmente trovato
     */

    public Tag getByName(String name) {
        return getSession().find(Tag.class, name);
    }

    /**
     * Funzione per la creazione di un tag specificando il nome
     *
     * @param name del tag
     * @return tag creato
     */

    public Tag create(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        this.getSession().save(tag);
        return tag;
    }

    /**
     * Funzione per aggiornare un tag specifico
     */
    public Tag update(Tag tag) {
        return (Tag) this.getSession().merge(tag);
    }

    /**
     * Funzione per cancellare un tag specifico
     */
    public void delete(Tag tag) {
        this.getSession().delete(tag);

    }

    /**
     * Funzione per cancellare un tag specificando il nome
     *
     * @param name nome del tag da cancellare
     */
    public void delete(String name) {
        Tag tag = this.getByName(name);
        this.delete(tag);
    }


}
