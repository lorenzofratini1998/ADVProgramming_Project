package it.univpm.advprog.blog.model.dao;

import it.univpm.advprog.blog.model.entities.*;
import org.hibernate.Session;

import java.util.List;
import java.util.Set;

public interface PostDao {
    Session getSession();

    public void setSession(Session session);

    List<Post> getAll();

    Post getById(long id);

    Post getByTitle(String title);

    List<Post> getByArchive(Archive archive);

    List<Post> getByAuthor(User author);

    Post create(String title, User author, String shortDescription, String longDescription, Set<Tag> tags,
                Archive archive);

    Post create(String title, User author, String shortDescription, String longDescription, Set<Tag> tags,
                Archive archive, String image, Set<Attachment> attachments);

    Post update(Post post);

    void delete(Post post);
}