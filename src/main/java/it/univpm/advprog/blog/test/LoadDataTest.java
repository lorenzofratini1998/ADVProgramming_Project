package it.univpm.advprog.blog.test;

import it.univpm.advprog.blog.model.dao.*;
import it.univpm.advprog.blog.model.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashSet;
import java.util.Set;

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
                // INSERIMENTO POST - UTENTI - TAG - ARCHIVI
                session.beginTransaction();

//                Calendar cal = Calendar.getInstance();
//                Archive archive1 = archiveDao.create(new SimpleDateFormat("MMMMM yyyy").format(cal.getTime()));

                Archive archive1 = archiveDao.create("novembre 2020");
                Archive archive2 = archiveDao.create("dicembre 2020");

                Tag tag1 = tagDao.create("Office 2013");
                Tag tag2 = tagDao.create("Office 2020");

                User user1 = userDao.create("marco98", "123456", "Marco", "Rossi");
                User user2 = userDao.create("mario88", "123456", "Mario", "Bianchi");

                Post post1 = postDao.create("Installazione Office 2013", user1, SHORTDESCRIPTION, LONGDESCRIPTION,
                        tag1, archive1);
                Post post2 = postDao.create("Installazione Office 2020", user2, SHORTDESCRIPTION, LONGDESCRIPTION,
                        tag2, archive2);


                assert user1.getPosts().size() == 0;
                assert user2.getPosts().size() == 0;

                assert tag1.getPosts().size() == 0;
                assert tag2.getPosts().size() == 0;

                assert archive1.getPosts().size() == 0;
                assert archive2.getPosts().size() == 0;

                session.refresh(user1);
                session.refresh(user2);

                session.refresh(tag1);
                session.refresh(tag2);

                session.refresh(archive1);
                session.refresh(archive2);

                assert user1.getPosts().size() == 1;
                assert user2.getPosts().size() == 1;

                assert post1.getTags().size() == 1;
                assert post2.getTags().size() == 1;

                session.getTransaction().commit(); //TODO, perché serve il commit() + beginTransaction() ???
                session.beginTransaction();

                assert tag1.getPosts().size() == 1;
                assert tag2.getPosts().size() == 1;

                assert archive1.getPosts().size() == 1;
                assert archive2.getPosts().size() == 1;

                Post post3 = postDao.create("Installazione Office", user2, SHORTDESCRIPTION, LONGDESCRIPTION,
                        tag2, archive2);

                assert user2.getPosts().size() == 1;

                assert tag2.getPosts().size() == 1;

                assert archive2.getPosts().size() == 1;

                session.refresh(user2);

                session.refresh(tag2);

                session.refresh(archive2);

                assert user2.getPosts().size() == 2;

                session.getTransaction().commit(); //TODO, perché serve il commit() + beginTransaction() ???
                session.beginTransaction();

                assert tag2.getPosts().size() == 2;

                assert archive2.getPosts().size() == 2;

                // cambio l'archivio
                post3.setArchive(archive1);

                // aggiungo un altro tag
                post3.addTag(tag1);

                // faccio l'update del post
                post3 = postDao.update(post3);

                assert archive1.getPosts().size() == 1;

                assert tag1.getPosts().size() == 1;

                session.refresh(archive1);

                session.refresh(tag1);

                session.getTransaction().commit(); //TODO, perché serve il commit() + beginTransaction() ???
                session.beginTransaction();

                assert archive1.getPosts().size() == 2;

                assert tag1.getPosts().size() == 2;

                session.getTransaction().commit();


//**********************************************************************************************************************
                // ELIMINAZIONE UTENTE (implica: eliminazione di tutti i SUOI POST comprese le associazioni fra i POST
                // e i TAG e l'eliminazione di tutti i SUOI COMMENTI)
                session.beginTransaction();

                session.refresh(user2);
                session.refresh(archive2);

                assert user2.getPosts().size() == 2;
                assert archive1.getPosts().size() == 2;
                assert archive2.getPosts().size() == 1;

                // NON SERVONO, viene gestito automaticamente da Hibernate (tramite i CascadeType)
                //user2.getPosts().clear();
                //user2.getComments().clear();
                //user2 = userDao.update(user2);

                userDao.delete(user2);

                session.getTransaction().commit(); //TODO, perché serve il commit() + beginTransaction() ???
                session.beginTransaction();

                session.refresh(archive1);
                session.refresh(archive2);

                assert archive1.getPosts().size() == 1;
                assert archive2.getPosts().size() == 0;

//                userDao.findUserByUsername(user2.getUsername()); // EntityNotFoundException... OK!!

                session.getTransaction().commit();


//**********************************************************************************************************************
                // ELIMINAZIONE POST (implica: cancellazione COMMENTI, ALLEGATI e associazioni con i tag)
                session.beginTransaction();

                assert user1.getPosts().size() == 1;
                assert archive1.getPosts().size() == 1;

                //postDao.delete(post1); //TODO, per ora commentato, poi togliere commento

                session.getTransaction().commit();


//**********************************************************************************************************************
                // ELIMINAZIONE TAG (implica: ERRORE, NON permettiamo la cancellazione di tag a cui sono associati
                // POSTS. Posso eliminare TAG VUOTI.)
                session.beginTransaction();

                postDao.create("Installazione Office", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag2, archive2);

                session.getTransaction().commit();

                session.beginTransaction();

                session.refresh(tag2);
                assert tag2.getPosts().size() == 1;

//                tagDao.delete(tag2); // ConstraintViolationException... OK!!

                session.getTransaction().commit();


//**********************************************************************************************************************
                // ELIMINAZIONE ARCHIVIO (implica: ERRORE, NON permettiamo la cancellazione di archivi a cui sono
                // associati POSTS. Posso eliminare ARCHIVI VUOTI.)
                session.beginTransaction();

                session.refresh(archive2);
                assert archive2.getPosts().size() == 1;

//                archiveDao.delete(archive2); // ConstraintViolationException... OK!!

                session.getTransaction().commit();

//**********************************************************************************************************************
                // INSERIMENTO COMMENTI
                session.beginTransaction();
                Comment comment1 = commentDao.create(user1, post1, "Stesso problema su Windows 7!",
                        "Ciao, ho lo stesso problema su Windows 7, all'apertura esce errore 128383.");

                Comment comment2 = commentDao.create(user1, post1, "Stesso problema su macOS Catalina!",
                        "Ciao, ho lo stesso problema...");

                assert post1.getComments().size() == 0;

                session.refresh(post1);

                assert post1.getComments().size() == 2;

                session.getTransaction().commit();

//**********************************************************************************************************************
                // ELIMINAZIONE DI UN COMMENTO

                session.beginTransaction();

                commentDao.delete(comment2);

                session.refresh(post1);

                session.getTransaction().commit(); //TODO, perché serve il commit() + beginTransaction() ???
                session.beginTransaction();

                assert post1.getComments().size() == 1;

                session.getTransaction().commit();

//**********************************************************************************************************************
                // INSERIMENTO ALLEGATI

                session.beginTransaction();

                File file1 = fileDao.create("Screenshot Office 2020", post1, "file1.jpg", true);
                File file2 = fileDao.create("Screenshot Eclipse 2020-09", post1, "file2.jpg");
                Link link1 = linkDao.create("Link al sito UNIVPM",true, post1, "https://www.univpm.it");

                session.getTransaction().commit();

                session.refresh(post1);
                assert post1.getAttachments().size() == 2;
                assert attachmentDao.getAll().size() == 2;

//**********************************************************************************************************************
                // ELIMINAZIONE di un ALLEGATO (implica: cancellazione dello stesso nella tabella padre dell'ereditarietà)

                session.beginTransaction();
                fileDao.delete(file1);
                session.getTransaction().commit();

                session.refresh(post1);
                assert post1.getAttachments().size() == 1;
                assert attachmentDao.getAll().size() == 1;

//**********************************************************************************************************************
                // MODIFICA di un ALLEGATO

                session.beginTransaction();

                Attachment attachment = attachmentDao.getById(2);
                attachment.setDescription("Descrizione Link Updated");
                attachmentDao.update(attachment);

                session.getTransaction().commit();
//**********************************************************************************************************************
                // ELIMINAZIONE di un POST (implica: cancellazione COMMENTI, ALLEGATI e associazioni con i tag)

                session.beginTransaction();
                postDao.delete(post1);
                session.getTransaction().commit();

                assert attachmentDao.getAll().size() == 0;

            }

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
