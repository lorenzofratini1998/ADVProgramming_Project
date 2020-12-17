package it.univpm.advprog.blog.model.dao;

import it.univpm.advprog.blog.model.entities.*;
import org.hibernate.Session;

import java.util.List;
import java.util.Set;

public interface PostDao {
    Session getSession();

    void setSession(Session session);

    List<Post> getAll();

    List<Post> getAllByHide(boolean hide);

    Post getById(long id);

    Post getByTitle(String title);

    Post create(String title, User author, String shortDescription, String longDescription, Tag tag,
                Archive archive);

    Post create(String title, User author, String shortDescription, String longDescription, Set<Tag> tags,
                Archive archive);

    Post create(String title, User author, boolean hide, String shortDescription, String longDescription, Set<Tag> tags,
                Archive archive, Set<Attachment> attachments, Set<Comment> comments);

    Post update(Post post);

    void delete(Post post);
}