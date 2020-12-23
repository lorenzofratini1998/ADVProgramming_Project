package it.univpm.advprog.blog.model.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "posts")
@NamedQueries({
        @NamedQuery(
                name = "Post.getPostByTitle",
                query = "SELECT p FROM Post p WHERE p.title = :title"
        ), @NamedQuery(
        name = "Post.getPostsByHide",
        query = "SELECT p FROM Post p WHERE p.hide = :hide ORDER BY p.id DESC"
)
})

public class Post implements Serializable {

    private long id;
    private String title;
    private User author;
    private boolean hide = false;
    private String shortDescription;
    private String longDescription;
    private Set<Tag> tags = new HashSet<>();
    private Set<Attachment> attachments = new HashSet<>();
    private Set<Comment> comments = new HashSet<>();
    private Archive archive;

    /**
     * Getter per la proprietà id del post.
     *
     * @return id del post
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    public long getId() {
        return id;
    }

    /**
     * Getter per la proprietà title del post.
     *
     * @return titolo del post
     */
    @Column(nullable = false, unique = true, length = 100)
    public String getTitle() {
        return title;
    }

    /**
     * Getter per la proprietà shortDescription del post.
     *
     * @return descrizione breve del post
     */
    @Column(nullable = false, length = 300)
    public String getShortDescription() {

        return shortDescription;
    }

    /**
     * Getter per la proprietà longDescription del post.
     *
     * @return descrizione lunga del post
     */
    @Column(nullable = false, length = 2500)
    public String getLongDescription() {

        return longDescription;
    }

    /**
     * Getter per la proprietà hide del post.
     *
     * @return se il post è nascosto o meno
     */
    @Column(nullable = false)
    public boolean isHide() {
        return hide;
    }

    /**
     * Setter per la proprietà id.
     *
     * @param id id del post da settare
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Setter per la proprietà title.
     *
     * @param title titolo del post da settare
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Setter per la proprietà shortDescription.
     *
     * @param shortDescription descrizione breve del post da settare
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     * Setter per la proprietà longDescription.
     *
     * @param longDescription descrizione lunga del post da settare
     */
    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    /**
     * Setter per la proprietà hide.
     *
     * @param hide specifico se il post non è visibile o meno
     */
    public void setHide(boolean hide) {
        this.hide = hide;
    }

    /**
     * Getter per la proprietà author.
     *
     * @return autore del post
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author", nullable = false)
    public User getAuthor() {
        return author;
    }

    /**
     * Setter per la proprietà author.
     *
     * @param author autore del post da settare
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     * Getter per la proprietà archive.
     *
     * @return archivio di cui il post fa parte
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "archive_name", nullable = false)
    public Archive getArchive() {
        return archive;
    }

    /**
     * Setter per la proprietà archive.
     *
     * @param archive archivio del post da settare
     */
    public void setArchive(Archive archive) {
        this.archive = archive;
    }

    /**
     * Getter per la proprietà tags.
     *
     * @return tags del post
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "posts_tags",
            joinColumns = @JoinColumn(name = "post_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "tag_name", nullable = false)
    )
    public Set<Tag> getTags() {
        return tags;
    }

    /**
     * Setter per la proprietà tags.
     *
     * @param tags tags del post da settare
     */
    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    /**
     * Metodo per aggiungere un tag al post.
     *
     * @param tag tag da aggiungere
     */
    public void addTag(Tag tag) {
        this.tags.add(tag);
        tag.getPosts().add(this);
    }

    /**
     * Getter per la proprietà attachments.
     *
     * @return allegati del post
     */
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "post")
    public Set<Attachment> getAttachments() {
        return attachments;
    }

    /**
     * Setter per la proprietà attachments.
     *
     * @param attachments allegati del post da settare
     */
    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }

    /**
     * Metodo per aggiungere un allegato al post.
     *
     * @param attachment allegato da aggiungere
     */
    public void addAttachment(Attachment attachment) {
        attachment.setPost(this);
        this.attachments.add(attachment);
    }

    /**
     * Getter per la proprietà comments.
     *
     * @return commenti del post
     */
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "post")
    public Set<Comment> getComments() {
        return comments;
    }

    /**
     * Setter per la proprietà comments.
     *
     * @param comments commenti del post da settare
     */
    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    /**
     * Metodo per aggiungere un commento al post.
     *
     * @param comment commento da aggiungere
     */
    public void addComment(Comment comment) {
        comment.setPost(this);
        this.comments.add(comment);
    }
}
