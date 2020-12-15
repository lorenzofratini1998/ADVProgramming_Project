package it.univpm.advprog.blog.test;

import it.univpm.advprog.blog.model.entities.*;
import it.univpm.advprog.blog.services.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LoadDataServicesTest_v2 {
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

            UserService userService = ctx.getBean("userService", UserService.class);
            TagService tagService = ctx.getBean("tagService", TagService.class);
            PostService postService = ctx.getBean("postService", PostService.class);
            ArchiveService archiveService = ctx.getBean("archiveService", ArchiveService.class);
            CommentService commentService = ctx.getBean("commentService", CommentService.class);
            FileService fileService = ctx.getBean("fileService", FileService.class);
            LinkService linkService = ctx.getBean("linkService", LinkService.class);
            AttachmentService attachmentService = ctx.getBean("attachmentService", AttachmentService.class);

//**********************************************************************************************************************
            // CREAZIONE UTENTI
            User user1 = userService.create("mario98", "12345678", "Mario", "Rossi");
            User user2 = userService.create("luca78", "12345678", "Luca", "Rossini");
            User user3 = userService.create("matteoVerdi", "12345678", "Matteo", "Verdi");
            User user4 = userService.create("giov_bian", "12345678", "Giovanni", "Bianchi");
            User user5 = userService.create("anto88", "12345678", "Antonio", "Bianchini");

            assert userService.findAll().size() == 5;
//**********************************************************************************************************************
            // INSERIMENTO TAG
            Tag tag1 = tagService.create("Office 2021");
            Tag tag2 = tagService.create("Teams");
            Tag tag3 = tagService.create("GitHub");
            Tag tag4 = tagService.create("Google Calendar");
            Tag tag5 = tagService.create("Google Photos");

            assert tagService.getAll().size() == 5;
//**********************************************************************************************************************
            // INSERIMENTO POST
            Post post1 = postService.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION,
                    tag1);
            Post post2 = postService.create("titoloPost2", user2, SHORTDESCRIPTION, LONGDESCRIPTION,
                    tag2);
            Post post3 = postService.create("titoloPost3", user3, SHORTDESCRIPTION, LONGDESCRIPTION,
                    tag3);
            Post post4 = postService.create("titoloPost4", user4, SHORTDESCRIPTION, LONGDESCRIPTION,
                    tag4);
            Post post5 = postService.create("titoloPost5", user5, SHORTDESCRIPTION, LONGDESCRIPTION,
                    tag5);
            Post post6 = postService.create("titoloPost6", user1, SHORTDESCRIPTION, LONGDESCRIPTION,
                    tag1);
            Post post7 = postService.create("titoloPost7", user2, SHORTDESCRIPTION, LONGDESCRIPTION,
                    tag2);
            Post post8 = postService.create("titoloPost8", user3, SHORTDESCRIPTION, LONGDESCRIPTION,
                    tag3);
            Post post9 = postService.create("titoloPost9", user4, SHORTDESCRIPTION, LONGDESCRIPTION,
                    tag4);
            Post post10 = postService.create("titoloPost10", user5, SHORTDESCRIPTION, LONGDESCRIPTION,
                    tag5);

            assert postService.getAll().size() == 10;

            assert archiveService.getAll().size() == 1; // l'archivio è stato creato automaticamente

            Archive archive1 = archiveService.getByName(new SimpleDateFormat("MMMMM yyyy").format(Calendar.getInstance().getTime()));

            assert archive1.getPosts().size() == 10;
//**********************************************************************************************************************
            // INSERIMENTO COMMENTI
            Comment comment1 = commentService.create(user1, post5, TITLE, DESCRIPTION);
            Comment comment2 = commentService.create(user2, post4, TITLE, DESCRIPTION);
            Comment comment3 = commentService.create(user3, post3, TITLE, DESCRIPTION);
            Comment comment4 = commentService.create(user4, post2, TITLE, DESCRIPTION);
            Comment comment5 = commentService.create(user5, post1, TITLE, DESCRIPTION);
            Comment comment6 = commentService.create(user1, post3, TITLE, DESCRIPTION);
            Comment comment7 = commentService.create(user2, post1, TITLE, DESCRIPTION);
            Comment comment8 = commentService.create(user3, post2, TITLE, DESCRIPTION);
            Comment comment9 = commentService.create(user4, post5, TITLE, DESCRIPTION);
            Comment comment10 = commentService.create(user5, post4, TITLE, DESCRIPTION);

            assert commentService.getAll().size() == 10;
//**********************************************************************************************************************
            // INSERIMENTO ALLEGATI
            File file1 = fileService.create(DESCRIPTION, post1, "file1.jpg", true);
            File file2 = fileService.create(DESCRIPTION, post1, "file2.jpg");
            File file3 = fileService.create(DESCRIPTION, post2, "file1.jpg", true);
            File file4 = fileService.create(DESCRIPTION, post2, "file2.jpg");
            File file5 = fileService.create(DESCRIPTION, true, post1, "file3.jpg", true);

            Link link1 = linkService.create(DESCRIPTION, post1, "https://www.univpm.it");
            Link link2 = linkService.create(DESCRIPTION, true, post2, "https://www.univpm.it");
            Link link3 = linkService.create(DESCRIPTION, post3, "https://www.univpm.it");
            Link link4 = linkService.create(DESCRIPTION, true, post1, "https://www.univpm.it");
            Link link5 = linkService.create(DESCRIPTION, post2, "https://www.univpm.it");

            assert attachmentService.getAll().size() == 10;
//**********************************************************************************************************************
            // CONTROLLI
            assert post1.getAttachments().size() == 5;
            assert post1.getComments().size() == 2;
            assert post1.getTags().size() == 1;

            assert user1.getComments().size() == 2;
            assert user1.getPosts().size() == 2;

            assert user2.getPosts().size() == 2;

            assert post2.getTags().size() == 1;

            assert tag1.getPosts().size() == 2;

            assert tag2.getPosts().size() == 2;

            assert archive1.getPosts().size() == 10;
//**********************************************************************************************************************
            // AGGIORNAMENTO DATI POST
            assert tag1.getPosts().size() == 2;
            assert tag3.getPosts().size() == 2;

            // aggiungo un altro tag
            post3.addTag(tag1);

            // faccio l'update del post
            post3 = postService.update(post3);

            assert tag1.getPosts().size() == 3;
            assert tag3.getPosts().size() == 2;
//**********************************************************************************************************************
            // AGGIORNAMENTO DATI ALLEGATI
            Attachment attachment = attachmentService.getById(8);
            attachment.setDescription("Descrizione link aggiornata");
            attachmentService.update(attachment);
            assert attachmentService.getById(8).getDescription().equals("Descrizione link aggiornata");

            file3.setNoDownloadable(false);
            attachmentService.update(file3);
            assert !fileService.getById(3).isNoDownloadable();
//**********************************************************************************************************************
            // ELIMINAZIONE UTENTE (implica -> eliminazione di tutti i SUOI POST comprese le associazioni dei
            // POST [cioè associazioni con TAG, ALLEGATI, COMMENTI] e l'eliminazione di tutti i SUOI COMMENTI)

            assert user2.getPosts().size() == 2;

            userService.delete(user2);

            User userFound = userService.findUserByUsername(user2.getUsername());

            assert userFound == null; // userFound is NULL... OK!
//**********************************************************************************************************************
            // ELIMINAZIONE POST (implica: cancellazione dei suoi COMMENTI, ALLEGATI e associazioni con i tag)
            assert user1.getPosts().size() == 2;
            assert archive1.getPosts().size() == 8;

            postService.delete(post1);

            assert user1.getPosts().size() == 1;
            assert archive1.getPosts().size() == 7;
//**********************************************************************************************************************
            // ELIMINAZIONE TAG (implica: 1) TAG VUOTO     -> viene ELIMINATO
            //                            2) TAG NON VUOTO -> ERRORE (NON permettiamo la cancellazione di tag a cui
            //                                                sono associati dei post.
            assert tag2.getPosts().size() == 0;
            assert tag4.getPosts().size() == 2;

            tagService.delete(tag2);

            try {
                tagService.delete(tag4);
            } catch (Exception e) {
                // ConstraintViolationException... OK!!
            }

            Tag tagFound = tagService.getByName(tag2.getName());
            assert tagFound == null; // tagFound is NULL... OK!

            tag4 = tagService.getByName(tag4.getName());
            assert tag4 != null; // tag4 NON è stato eliminato... OK!

//**********************************************************************************************************************
            // ELIMINAZIONE ARCHIVIO (implica: 1) ARCHIVIO VUOTO     -> viene ELIMINATO
            //                                 2) ARCHIVIO NON VUOTO -> ERRORE (NON permettiamo la cancellazione di
            //                                 archivi a cui sono associati dei post.
            assert archive1.getPosts().size() == 7;

            // create an empty archive
            Archive archive = archiveService.create("archivio-prova");

            assert archiveService.getByName("archivio-prova").getPosts().size() == 0;

            archiveService.delete("archivio-prova");

            try {
                archiveService.delete(archive1);
            } catch (Exception e) {
                // ConstraintViolationException... OK!!
            }

            Tag archiveFound = tagService.getByName(archive.getName());
            assert archiveFound == null; // archiveFound is NULL... OK!

            archive1 = archiveService.getByName(archive1.getName());
            assert archive1 != null; // archive4 NON è stato eliminato... OK!

//**********************************************************************************************************************
            // ELIMINAZIONE DI UN COMMENTO
            assert post4.getComments().size() == 1;

            comment10 = commentService.findCommentById(10);
            commentService.delete(comment10);

            assert post4.getComments().size() == 0;

            Comment commentFound = commentService.findCommentById(comment10.getId());

            assert commentFound == null; // commentFound is NULL... OK!

//**********************************************************************************************************************
            // ELIMINAZIONE di un ALLEGATO (implica: cancellazione dello stesso nella tabella padre
            // dell'ereditarietà)
            assert post3.getAttachments().size() == 1;

            assert attachmentService.getAll().size() == 1;

            link3 = linkService.getById(8);
            linkService.delete(link3);

            assert post3.getAttachments().size() == 0;

            assert attachmentService.getAll().size() == 0;

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

    }
}