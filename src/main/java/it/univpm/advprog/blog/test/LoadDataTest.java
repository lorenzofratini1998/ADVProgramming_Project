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

                Tag tag1 = tagDao.create("Microsoft Teams");
                Tag tag2 = tagDao.create("Microsoft Office");
                Tag tag3 = tagDao.create("Windows");
                Tag tag4 = tagDao.create("Microsoft Edge");
                Tag tag5 = tagDao.create("Outlook");

                session.getTransaction().commit();

                assert tagDao.getAll().size() == 5;

//**********************************************************************************************************************
                // INSERIMENTO POST
                session.beginTransaction();

                Post post1 = postDao.create("File su Teams", user1, "Non trovo i file su Teams", "dopo aver preso la versione di prova di 365 business basics adesso non vedo pi?? tutti i file di lavoro che avevo su Teams",
                        tag1, archive1);
                Post post2 = postDao.create("Account microsoft universit??", user2, "Sono uno studente universitario, mi sono iscritto ad un master in una nuova universit?? ma ho ancora l'account microsoft collegato alla precedente",
                		"Buongiorno, \r\n"
                		+ "\r\n"
                		+ "sono uno studente universitario, mi sono iscritto ad un master in una nuova universit?? ma ho ancora l'account microsoft collegato alla precedente. In tal modo non riesco ad accedere al team del master (mi fa accedere automaticamente a teams con la mail dell'universit?? precedente) e non so se devo creare un nuovo account o sostituire il precedente. Non voglio assolutamente perdere i documenti e le app che ho utilizzato questi tre anni. Mi pu?? aiutare?",
                        tag2, archive2);
                Post post3 = postDao.create("Mouse e tastiera USB non funzionano", user3, "Mouse e tastiera USB non funzionano dopo reinstallazione Windows 10 in pulito", 
                		"Nel mio vecchio PC ho fatto una reinstallazione WIN10 pulito.\r\n"
                		+ "Terminato l'aggiornamento, la tastiera e il maus collegati a USB non funzionano.\r\n"
                		+ "Cordiali saluti",
                        tag3, archive3);
                Post post4 = postDao.create("Avviso di Microsoft Edge: Sito non sicuro", user4, "Accedendo a certi siti, anche molto comuni e noti, appare la scritta Sito non sicuro", 
                		"Buongiorno a tutti. Volevo chiedere come mai accedendo a certi siti, anche molto comuni e noti, appare la scritta Sito non sicuro.\r\n"
                		+ "\r\n"
                		+ "E' un problema legato all'antivirus o ?? effettivamente un problema di sicurezza? Nonostante  il sito abbia in indirizzo  https ://ecc.\r\n"
                		+ "\r\n"
                		+ "Il mio antivirus e' windows defender con windows 10.",
                        tag4, archive4);
                Post post5 = postDao.create("Hanno clonato il mio indirizzo", user5, "Ricevo mail da un indirizzo clonato", 
                		"Ho problemi perche ricevo mail da mio indirizzo clonato\r\n"
                		+ "\r\n"
                		+ "posso ricevere vostro supporto?\r\n"
                		+ "\r\n"
                		+ "grazie",
                        tag5, archive5);
                Post post6 = postDao.create("Teams non salva registrazione", user1, "Registrazione non salvata dopo averla avviata durante una riunione a cui ho partecipato", 
                		"Buongiorno, ieri ho partecipato ad una riunione ed ho fatto partire la registrazione.  Poi a fine riunione ho interrotto la registrazione e mi ?? apparsa una scritta che diceva \"la registrazione ?? stata salvata, sar?? disponibile sulla cronologia della chat\". per?? nella cronologia della chat non vedo assolutamente niente.\r\n"
                		+ "\r\n"
                		+ "ad oggi ( dopo quasi 24 ore)  della registrazione nessuna traccia. come pu?? essere possibile? dove trovo la mia registrazione? grazie",
                        tag1, archive1);
                Post post7 = postDao.create("File danneggiati su OneDrive", user2, "Molti file salvati nei mesi passati risultano illeggibili da qualsiasi dispositivo si tenta di aprirli", 
                		"Ciao a tutti, ho un grosso problema!\r\n"
                		+ "\r\n"
                		+ "ci siamo accorti da alcuni giorni che molti file salvati nei mesi passati risultano illeggibili da qualsiasi dispositivo si tenta di aprirli.\r\n"
                		+ "\r\n"
                		+ "Mio figlio prima di natale a fatto backup delle foto del suo telefono e ora risultano tutte illeggibili.\r\n"
                		+ "\r\n"
                		+ "Stessa cosa per parecchi documenti word di scuola....che posso fare?",
                        tag2, archive2);
                Post post8 = postDao.create("Sfarfallio schermo Windows 10", user3, "Quando vado in Impostazioni inizia lo sfarfallio dello schermo appena chiudo la finestra impostazioni lo sfarfallo termina", 
                		"Salve,\r\n"
                		+ "\r\n"
                		+ "ho un computer da 2GB di ram a 32 bit e sono passato da win 7 a win 10 l'aggiornamento ?? andato bene. Poi per?? mi sono accorto che quando vado in Impostazioni inizia lo sfarfallio dello schermo appena chiudo la finestra impostazioni lo sfarfallo termina. Ho capito che a causare questo problema ?? la scheda grafica infatti appena disabilito il driver grafico tutto torna a posto ma con una risoluzione pessima. La scheda grafica ?? la Nvidia il driver ??: Nvdia Gforce 6100 Nforce 430  Win 10 ha installato: Nvidia Geforce 6150 Se nforce 430.\r\n"
                		+ "\r\n"
                		+ "Chiedo, cortesemente, se avete qualche suggerimento da darmi per risolvere questo sfarfallio che si manifesta solo quando apro la finestra impostazioni.\r\n"
                		+ "\r\n"
                		+ "Grazie n anticipo e saluti.",
                        tag3, archive3);
                Post post9 = postDao.create("Microfono non funziona con Edge", user4, "Non riesco a far funzionare il microfono per la ricerca vocale", 
                		"Buongiorno, con l'aggiornamento di edge, non riesco a far funzionare il microfono per la ricerca vocale in google.\r\n"
                		+ "\r\n"
                		+ "Funziona invece con chrome.\r\n"
                		+ "\r\n"
                		+ "Grazie",
                        tag4, archive4);
                Post post10 = postDao.create("Invio Virus da Mail", user5, "Alcuni miei contatti ricevono delle mail con virus", 
                		"Buongiorno ho una mail con outlook che non utilizzo mai.\r\n"
                		+ "Purtroppo alcuni miei contatti ricevono delle mail (con virus) dal mio indirizzo senza che io le abbia mai mandate.\r\n"
                		+ "\r\n"
                		+ "Come posso risolvere il problema?",
                        tag5, archive5);

                session.getTransaction().commit();

                assert postDao.getAll().size() == 10;

//**********************************************************************************************************************
                // INSERIMENTO COMMENTI
                session.beginTransaction();

                commentDao.create(user1, post5, "Finto mittente", "Ciao, potrebbe essere qualcuno che finge di usare il tuo stesso account, ma il vero mittente ?? un altro. Fammi sapere.");
                commentDao.create(user2, post4, "Falso allarme!", "Se sono siti che conosci, si pu?? accedere tranquillamente, ?? solo un avviso.");
                commentDao.create(user3, post3, "Installazione in modalit?? avanzata", "Devi reinstallare pulito in modalit?? avanzata se vuoi uscire da questo problema.");
                commentDao.create(user4, post2, "Procedura guidata", "Potresti selezionare l???immagine del tuo account nell???applicazione di Teams > selezionare Esci > accedere nuovamente a Teams tramite le credenziali del nuovo account. In qualsiasi momento potresti connettere l???altro account a Teams e accedere ai tuoi documenti.");
                commentDao.create(user5, post1, "Da vecchio a nuovo account", "In precedenza quale versione avevi? Microsoft 365 Business associa un nuovo tenant all'utente con dominio onmicrosoft.com Se in precedenza usavi un altro account per accedere a Teams, dovresti accedere con tale account e copiare i dati sul nuovo");
                commentDao.create(user1, post3, "Stesso problema", "Nel mio vechio PC ho fatto un riinstallazione WIN10 pulito. Terminato l'aggiornamento, la tastiera e il maus collegati a USB non funzionano. Cordiali saluti");
                commentDao.create(user2, post1, "Risoluzione problema", "Potresti avere la versione precedente con un altro username");
                commentDao.create(user3, post2, "Consiglio legato alla procedura guidata", "?? importante sapere che se non frequenti pi?? un istituto d???istruzione, la tua licenza pu?? essere disabilitata in qualsiasi momento. Per questo motivo suggerirei di eseguire un backup dei tuoi documenti archiviati con il tuo account precedente.");
                commentDao.create(user4, post5, "Segnalazione assistenza", "Prova a segnalare quanto avvenuto all'assistenza");
                commentDao.create(user5, post4, "Rischio esposizione attacco hacker", "Se appare in sfondo tutto rosso il blocco di Edge ?? un problema di sicurezza del sito, che magari ha subito un attacco hacker.");

                session.getTransaction().commit();

                assert commentDao.getAll().size() == 10;

//**********************************************************************************************************************
                // INSERIMENTO ALLEGATI

                session.beginTransaction();

                fileDao.create(DESCRIPTION, post1, "file1.jpg", true);
                fileDao.create(DESCRIPTION, post1, "file2.jpg");
                fileDao.create(DESCRIPTION, post2, "file1.jpg", true);
                fileDao.create(DESCRIPTION, post2, "file2.jpg");
                fileDao.create(DESCRIPTION, true, post1, "file3.jpg", true);

                linkDao.create(DESCRIPTION, post1, "https://www.univpm.it");
                linkDao.create(DESCRIPTION, true, post2, "https://www.univpm.it");
                linkDao.create(DESCRIPTION, post3, "https://www.univpm.it");
                linkDao.create(DESCRIPTION, true, post1, "https://www.univpm.it");
                linkDao.create(DESCRIPTION, post2, "https://www.univpm.it");

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

                File file3 = fileDao.getById(3);
                file3.setNoDownloadable(false);
                fileDao.update(file3);

                session.getTransaction().commit();
//**********************************************************************************************************************
                // ELIMINAZIONE UTENTE (implica -> eliminazione di tutti i SUOI POST comprese le associazioni dei
                // POST [cio?? associazioni con TAG, ALLEGATI, COMMENTI] e l'eliminazione di tutti i SUOI COMMENTI)
                session.refresh(user2);
                session.refresh(archive2);

                assert user2.getPosts().size() == 2;
                assert archive2.getPosts().size() == 2;

                session.beginTransaction();

                // NON SERVONO, viene gestito automaticamente da Hibernate (tramite i orphanRemoval)
//                user2.getPosts().clear();
//                user2.getComments().clear();
//                user2 = userDao.update(user2);
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
                assert tag4 != null; // tag4 NON ?? stato eliminato... OK!

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
                assert archive4 != null; // archive4 NON ?? stato eliminato... OK!

//**********************************************************************************************************************
                // ELIMINAZIONE DI UN COMMENTO
//                session.refresh(post4);
                post4 = postDao.getById(4);

                assert post4.getComments().size() == 1;

                session.beginTransaction();

                commentDao.delete(commentDao.findCommentById(10));

                session.getTransaction().commit();

                session.refresh(post4);

                assert post4.getComments().size() == 0;

                Comment commentFound = commentDao.findCommentById(10);

                assert commentFound == null; // commentFound is NULL... OK!

//**********************************************************************************************************************
                // ELIMINAZIONE di un ALLEGATO (implica: cancellazione dello stesso nella tabella padre
                // dell'ereditariet??)
                post3 = postDao.getById(3);

                assert post3.getAttachments().size() == 1;
                assert attachmentDao.getAll().size() == 1;

                session.beginTransaction();
                linkDao.delete(linkDao.getById(8));
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
