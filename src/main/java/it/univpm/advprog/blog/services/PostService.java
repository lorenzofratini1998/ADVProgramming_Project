package it.univpm.advprog.blog.services;

import it.univpm.advprog.blog.model.entities.*;

import java.util.List;
import java.util.Set;

public interface PostService {
    List<Post> getAll();

    Post getById(long id);

    Post getByTitle(String title);

    List<Post> getByArchive(Archive archive);

    List<Post> getByAuthor(User author);

    List<Post> getByTag(Tag tag);

    List<Post> getByTags(Set<Tag> tags);

    List<Post> getByAttachment(Attachment attachment);

    List<Post> getByAttachments(Set<Attachment> attachments);

    Post create(String title, User author, String shortDescription, String longDescription, Tag tag);

    Post create(String title, User author, String shortDescription, String longDescription, Set<Tag> tags);

    Post create(String title, User author, boolean hide, String shortDescription, String longDescription,
                Set<Tag> tags, Set<Attachment> attachments, Set<Comment> comments);

    Post update(Post post);

    void delete(Post post);

    void delete(long id);

    void delete(String title);

}
