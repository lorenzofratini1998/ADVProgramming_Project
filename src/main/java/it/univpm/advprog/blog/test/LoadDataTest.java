package it.univpm.advprog.blog.test;

import it.univpm.advprog.blog.model.dao.*;
import it.univpm.advprog.blog.model.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.hibernate.resource.transaction.spi.TransactionStatus.NOT_ACTIVE;

public class LoadDataTest {
    private final static String SHORTDESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras " +
            "tempus magna vel posuere cursus. Sed ultricies nunc purus, et maximus eros accumsan sit amet. Donec " +
            "diam nisl, consectetur non nisl vel, condimentum finibus est.";
    private final static String LONGDESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras " +
            "tempus magna vel posuere cursus. Sed ultricies nunc purus, et maximus eros accumsan sit amet. Donec " +
            "diam nisl, consectetur non nisl vel, condimentum finibus est. Duis venenatis lectus et hendrerit " +
            "sollicitudin. Donec nisl felis, bibendum vitae placerat nec, dapibus iaculis enim. Integer rutrum " +
            "pulvinar nibh et dignissim. Vivamus porttitor dui ut ante iaculis interdum. Fusce vel purus libero. " +
            "Nunc luctus metus at tristique lobortis. Nulla egestas dictum bibendum. Suspendisse iaculis quis lorem " +
            "id scelerisque. Cras ac imperdiet metus. Donec lobortis vestibulum velit, feugiat interdum tellus " +
            "consectetur ac.";
    private final static String TITLE = "Lorem ipsum dolor sit amet.";
    private final static String DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras " +
            "tempus magna vel posuere cursus.";

    public static void main(String... args) {

        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
                DataServiceConfigTest.class)) {

            SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);

            UserDao userDao = ctx.getBean("userDao", UserDao.class);
            PostDao postDao = ctx.getBean("postDao", PostDao.class);
            ArchiveDao archiveDao = ctx.getBean("archiveDao", ArchiveDao.class);
            TagDao tagDao = ctx.getBean("tagDao", TagDao.class);
            CommentDao commentDao = ctx.getBean("commentDao", CommentDao.class);
            FileDao fileDao = ctx.getBean("fileDao", FileDao.class);
            LinkDao linkDao = ctx.getBean("linkDao", LinkDao.class);
            AttachmentDao attachmentDao = ctx.getBean("attachmentDao", AttachmentDao.class);

//            userDao.setPasswordEncoder(new BCryptPasswordEncoder()); // non mi serve, uso @Autowired

            try (Session session = sf.openSession()) {

                userDao.setSession(session);
                postDao.setSession(session);
                archiveDao.setSession(session);
                userDao.setSession(session);
                tagDao.setSession(session);
                commentDao.setSession(session);
                fileDao.setSession(session);
                linkDao.setSession(session);
                attachmentDao.setSession(session);

//**********************************************************************************************************************
                // CREAZIONE UTENTI
                session.beginTransaction();

                User user1 = userDao.create("mario98", "12345678", "Mario", "Rossi");
                User user2 = userDao.create("luca78", "12345678", "Luca", "Rossini");
                User user3 = userDao.create("matteoVerdi", "12345678", "Matteo", "Verdi");
                User user4 = userDao.create("giov_bian", "12345678", "Giovanni", "Bianchi");
                User user5 = userDao.create("anto88", "12345678", "Antonio", "Bianchini");

                user1.setAdmin(true);

                session.getTransaction().commit();

                assert userDao.findAll().size() == 5;

//**********************************************************************************************************************
                // INSERIMENTO ARCHIVI
                session.beginTransaction();

                Archive archive1 = archiveDao.create("settembre 2020");
                Archive archive2 = archiveDao.create("ottobre 2020");
                Archive archive3 = archiveDao.create("novembre 2020");
                Archive archive4 = archiveDao.create("dicembre 2020");
                Archive archive5 = archiveDao.create("gennaio 2021");

                session.getTransaction().commit();

                assert archiveDao.getAll().size() == 5;

//**********************************************************************************************************************
                // INSERIMENTO TAG
                session.beginTransaction();

                Tag tag1 = tagDao.create("Office 2021");
                Tag tag2 = tagDao.create("Teams");
                Tag tag3 = tagDao.create("GitHub");
                Tag tag4 = tagDao.create("Google Calendar");
                Tag tag5 = tagDao.create("Google Photos");

                session.getTransaction().commit();

                assert tagDao.getAll().size() == 5;

//**********************************************************************************************************************
                // INSERIMENTO POST
                session.beginTransaction();

                Post post1 = postDao.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION,
                        tag1, archive1);
                Post post2 = postDao.create("titoloPost2", user2, SHORTDESCRIPTION, LONGDESCRIPTION,
                        tag2, archive2);
                Post post3 = postDao.create("titoloPost3", user3, SHORTDESCRIPTION, LONGDESCRIPTION,
                        tag3, archive3);
                Post post4 = postDao.create("titoloPost4", user4, SHORTDESCRIPTION, LONGDESCRIPTION,
                        tag4, archive4);
                Post post5 = postDao.create("titoloPost5", user5, SHORTDESCRIPTION, LONGDESCRIPTION,
                        tag5, archive5);
                Post post6 = postDao.create("titoloPost6", user1, SHORTDESCRIPTION, LONGDESCRIPTION,
                        tag1, archive1);
                Post post7 = postDao.create("titoloPost7", user2, SHORTDESCRIPTION, LONGDESCRIPTION,
                        tag2, archive2);
                Post post8 = postDao.create("titoloPost8", user3, SHORTDESCRIPTION, LONGDESCRIPTION,
                        tag3, archive3);
                Post post9 = postDao.create("titoloPost9", user4, SHORTDESCRIPTION, LONGDESCRIPTION,
                        tag4, archive4);
                Post post10 = postDao.create("titoloPost10", user5, SHORTDESCRIPTION, LONGDESCRIPTION,
                        tag5, archive5);

                session.getTransaction().commit();

                assert postDao.getAll().size() == 10;

//**********************************************************************************************************************
                // INSERIMENTO COMMENTI
                session.beginTransaction();

                Comment comment1 = commentDao.create(user1, post5, TITLE, DESCRIPTION);
                Comment comment2 = commentDao.create(user2, post4, TITLE, DESCRIPTION);
                Comment comment3 = commentDao.create(user3, post3, TITLE, DESCRIPTION);
                Comment comment4 = commentDao.create(user4, post2, TITLE, DESCRIPTION);
                Comment comment5 = commentDao.create(user5, post1, TITLE, DESCRIPTION);
                Comment comment6 = commentDao.create(user1, post3, TITLE, DESCRIPTION);
                Comment comment7 = commentDao.create(user2, post1, TITLE, DESCRIPTION);
                Comment comment8 = commentDao.create(user3, post2, TITLE, DESCRIPTION);
                Comment comment9 = commentDao.create(user4, post5, TITLE, DESCRIPTION);
                Comment comment10 = commentDao.create(user5, post4, TITLE, DESCRIPTION);

                session.getTransaction().commit();

                assert commentDao.getAll().size() == 10;

//**********************************************************************************************************************
                // INSERIMENTO ALLEGATI

                session.beginTransaction();

                File file1 = fileDao.create(DESCRIPTION, post1, "file1.jpg", true);
                File file2 = fileDao.create(DESCRIPTION, post1, "file2.jpg");
                File file3 = fileDao.create(DESCRIPTION, post2, "file1.jpg", true);
                File file4 = fileDao.create(DESCRIPTION, post2, "file2.jpg");
                File file5 = fileDao.create(DESCRIPTION, true, post1, "file3.jpg", true);

                Link link1 = linkDao.create(DESCRIPTION, post1, "https://www.univpm.it");
                Link link2 = linkDao.create(DESCRIPTION, true, post2, "https://www.univpm.it");
                Link link3 = linkDao.create(DESCRIPTION, post3, "https://www.univpm.it");
                Link link4 = linkDao.create(DESCRIPTION, true, post1, "https://www.univpm.it");
                Link link5 = linkDao.create(DESCRIPTION, post2, "https://www.univpm.it");

                session.getTransaction().commit();

                assert attachmentDao.getAll().size() == 10;

//**********************************************************************************************************************
                // CONTROLLI
                assert post1.getComments().size() == 0;
                assert user1.getComments().size() == 0;

                assert user1.getPosts().size() == 0;
                assert user2.getPosts().size() == 0;

                assert tag1.getPosts().size() == 0;
                assert tag2.getPosts().size() == 0;

                assert archive1.getPosts().size() == 0;
                assert archive2.getPosts().size() == 0;

                assert post1.getAttachments().size() == 0;

                session.refresh(user1);
                session.refresh(user2);
                session.refresh(user3);
                session.refresh(user4);
                session.refresh(user5);

                session.refresh(tag1);
                session.refresh(tag2);
                session.refresh(tag3);
                session.refresh(tag4);
                session.refresh(tag5);

                session.refresh(archive1);
                session.refresh(archive2);
                session.refresh(archive3);
                session.refresh(archive4);
                session.refresh(archive5);

                session.refresh(post1);
                session.refresh(post2);
                session.refresh(post3);
                session.refresh(post4);
                session.refresh(post5);
                session.refresh(post6);
                session.refresh(post7);
                session.refresh(post8);
                session.refresh(post9);
                session.refresh(post10);

                assert post1.getAttachments().size() == 5;

                assert post1.getComments().size() == 2;
                assert user1.getComments().size() == 2;

                assert user1.getPosts().size() == 2;
                assert user2.getPosts().size() == 2;

                assert post1.getTags().size() == 1;
                assert post2.getTags().size() == 1;

                assert tag1.getPosts().size() == 2;
                assert tag2.getPosts().size() == 2;

                assert archive1.getPosts().size() == 2;
                assert archive2.getPosts().size() == 2;

//**********************************************************************************************************************
                // AGGIORNAMENTO DATI POST
                assert archive1.getPosts().size() == 2;
                assert archive3.getPosts().size() == 2;

                assert tag1.getPosts().size() == 2;
                assert tag3.getPosts().size() == 2;

                session.beginTransaction();

                // cambio l'archivio
                post3.setArchive(archive1);
                // aggiungo un altro tag
                post3.addTag(tag1);
                // faccio l'update del post
                post3 = postDao.update(post3);

                session.getTransaction().commit();

                session.refresh(archive1);
                session.refresh(archive3);
                session.refresh(tag1);
                session.refresh(tag3);

                assert archive1.getPosts().size() == 3;
                assert archive3.getPosts().size() == 1;

                assert tag1.getPosts().size() == 3;
                assert tag3.getPosts().size() == 2;

//**********************************************************************************************************************
                // AGGIORNAMENTO DATI ALLEGATI
                session.beginTransaction();

                Attachment attachment = attachmentDao.getById(8);
                attachment.setDescription("Descrizione link aggiornata");
                attachmentDao.update(attachment);

                file3.setNoDownloadable(false);
                fileDao.update(file3);

                session.getTransaction().commit();
//**********************************************************************************************************************
                // ELIMINAZIONE UTENTE (implica -> eliminazione di tutti i SUOI POST comprese le associazioni dei
                // POST [cioè associazioni con TAG, ALLEGATI, COMMENTI] e l'eliminazione di tutti i SUOI COMMENTI)
                session.refresh(user2);
                session.refresh(archive2);

                assert user2.getPosts().size() == 2;
                assert archive2.getPosts().size() == 2;

                session.beginTransaction();

                // NON SERVONO, viene gestito automaticamente da Hibernate (tramite i orphanRemoval)
                //user2.getPosts().clear();
                //user2.getComments().clear();
                //user2 = userDao.update(user2);
                userDao.delete(user2);

                session.getTransaction().commit();

                session.refresh(archive2);

                assert archive2.getPosts().size() == 0;

                User userFound = userDao.findUserByUsername(user2.getUsername());

                assert userFound == null; // userFound is NULL... OK!

//**********************************************************************************************************************
                // ELIMINAZIONE POST (implica: cancellazione dei suoi COMMENTI, ALLEGATI e associazioni con i tag)
                session.refresh(post1);

                assert user1.getPosts().size() == 2;
                assert archive1.getPosts().size() == 3;

                session.beginTransaction();

                postDao.delete(post1);

                session.getTransaction().commit();

                session.refresh(user1);
                session.refresh(archive1);

                assert user1.getPosts().size() == 1;
                assert archive1.getPosts().size() == 2;


//**********************************************************************************************************************
                // ELIMINAZIONE TAG (implica: 1) TAG VUOTO     -> viene ELIMINATO
                //                            2) TAG NON VUOTO -> ERRORE (NON permettiamo la cancellazione di tag a cui
                //                                                sono associati dei post.
                session.refresh(tag2);
                session.refresh(tag4);

                assert tag2.getPosts().size() == 0;
                assert tag4.getPosts().size() == 2;

                session.beginTransaction();
                tagDao.delete(tag2);
                session.getTransaction().commit();

                try {
                    session.beginTransaction();
                    tagDao.delete(tag4);
                    session.getTransaction().commit();
                } catch (Exception e) {
                    // ConstraintViolationException... OK!!
                } finally {
                    if (session.getTransaction().getStatus() != NOT_ACTIVE) {
                        session.getTransaction().commit();
                    }
                }

                Tag tagFound = tagDao.getByName(tag2.getName());
                assert tagFound == null; // tagFound is NULL... OK!

                tag4 = tagDao.getByName(tag4.getName());
                assert tag4 != null; // tag4 NON è stato eliminato... OK!

//**********************************************************************************************************************
                // ELIMINAZIONE ARCHIVIO (implica: 1) ARCHIVIO VUOTO     -> viene ELIMINATO
                //                                 2) ARCHIVIO NON VUOTO -> ERRORE (NON permettiamo la cancellazione di
                //                                 archivi a cui sono associati dei post.
                session.refresh(archive2);
                session.refresh(archive4);

                assert archive2.getPosts().size() == 0;
                assert archive4.getPosts().size() == 2;

                session.beginTransaction();
                archiveDao.delete(archive2);
                session.getTransaction().commit();

                try {
                    session.beginTransaction();
                    archiveDao.delete(archive4);
                    session.getTransaction().commit();
                } catch (Exception e) {
                    // ConstraintViolationException... OK!!
                } finally {
                    if (session.getTransaction().getStatus() != NOT_ACTIVE) {
                        session.getTransaction().commit();
                    }
                }

                Tag archiveFound = tagDao.getByName(archive2.getName());
                assert archiveFound == null; // archiveFound is NULL... OK!

                archive4 = archiveDao.getByName(archive4.getName());
                assert archive4 != null; // archive4 NON è stato eliminato... OK!

//**********************************************************************************************************************
                // ELIMINAZIONE DI UN COMMENTO
                session.refresh(post4);
                session.refresh(comment10);

                assert post4.getComments().size() == 1;

                session.beginTransaction();

                commentDao.delete(comment10);

                session.getTransaction().commit();

                session.refresh(post4);

                assert post4.getComments().size() == 0;

                Comment commentFound = commentDao.findCommentById(comment10.getId());

                assert commentFound == null; // commentFound is NULL... OK!

//**********************************************************************************************************************
                // ELIMINAZIONE di un ALLEGATO (implica: cancellazione dello stesso nella tabella padre
                // dell'ereditarietà)
                session.refresh(post3);
                session.refresh(link3);

                assert post3.getAttachments().size() == 1;
                assert attachmentDao.getAll().size() == 1;

                session.beginTransaction();
                linkDao.delete(link3);
                session.getTransaction().commit();

                session.refresh(post3);
                assert post3.getAttachments().size() == 0;
                assert attachmentDao.getAll().size() == 0;

            }

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
