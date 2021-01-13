package it.univpm.advprog.blog.test.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import it.univpm.advprog.blog.model.dao.ArchiveDao;
import it.univpm.advprog.blog.model.dao.PostDao;
import it.univpm.advprog.blog.model.dao.TagDao;
import it.univpm.advprog.blog.model.dao.UserDao;
import it.univpm.advprog.blog.model.entities.Archive;
import it.univpm.advprog.blog.model.entities.Post;
import it.univpm.advprog.blog.model.entities.User;
import it.univpm.advprog.blog.model.entities.Tag;
import it.univpm.advprog.blog.test.DataServiceConfigTest;

public class TestArchiveDao {
	
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
    private final static String DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras " +
        "tempus magna vel posuere cursus.";
	
    @BeforeEach
   	void setUp() throws Exception {
   	}

   	@AfterEach
   	void tearDown() throws Exception {
   	}
   	
   	@Test
   	public void noArchivesAtBeginning() {
   		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao",ArchiveDao.class);
			Session s  = sf.openSession();
			archiveDao.setSession(s);
			assertEquals(archiveDao.getAll().size(), 0);
   			}
   	}  	
   	
   	@Test
   	public void createAndDeleteByArchive() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
			
			Session s=sf.openSession();
			archiveDao.setSession(s);
			
			s.beginTransaction();
			Archive archive1 = archiveDao.create("dicembre 2020");
			s.getTransaction().commit();
			
			assertEquals(archiveDao.getAll().size(),1);
			assertEquals(archiveDao.getByName("dicembre 2020").getName(),"dicembre 2020");
			
			s.beginTransaction();
			archiveDao.delete(archive1);
			s.getTransaction().commit();
			
			assertEquals(archiveDao.getAll().size(),0);
			assertNull(archiveDao.getByName("dicembre 2020"));
		}
   	}
   	
   	@Test
   	public void createAndDeleteByString() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
			
			Session s=sf.openSession();
			archiveDao.setSession(s);
			
			s.beginTransaction();
			Archive archive1 = archiveDao.create("dicembre 2020");
			s.getTransaction().commit();
			
			assertEquals(archiveDao.getAll().size(),1);
			assertEquals(archiveDao.getByName("dicembre 2020").getName(),"dicembre 2020");
			
			s.beginTransaction();
			archiveDao.delete("dicembre 2020");
			s.getTransaction().commit();
			
			assertEquals(archiveDao.getAll().size(),0);
			assertNull(archiveDao.getByName("dicembre 2020"));
		}
   	}
   	
   	@Test
   	public void cannotInsertArchiveWithSameName() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
			
			Session s=sf.openSession();
			archiveDao.setSession(s);
			
			s.beginTransaction();
			Archive archive1 = archiveDao.create("dicembre 2020");
			s.getTransaction().commit();
			
			assertEquals(archiveDao.getAll().size(),1);
			
			try {
				Archive archive2 = archiveDao.create("dicembre 2020");
				fail("Exception expected when creating two archives with same name");
			} catch(Exception e) {
				assertTrue(true);
			}
		}
   	}
   	
   	@Test
   	public void cannotDeleteArchiveWithPost() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
			UserDao userDao=ctx.getBean("userDao",UserDao.class);
			PostDao postDao = ctx.getBean("postDao",PostDao.class);
			TagDao tagDao = ctx.getBean("tagDao",TagDao.class);
			
			Session s=sf.openSession();
			archiveDao.setSession(s);
			userDao.setSession(s);
			tagDao.setSession(s);
			postDao.setSession(s);

			
			s.beginTransaction();
			User user1 = userDao.create("alessandro02", "12345678", "Alessandro", "Bianchi");
			Archive archive1 = archiveDao.create("dicembre 2020");
			Tag tag1 = tagDao.create("Tag del test");
			Post post1 = postDao.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1, archive1);
			s.getTransaction().commit();
			
			assertEquals(archiveDao.getAll().size(),1);
			assertEquals(postDao.getAll().size(),1);
			assertEquals(postDao.getById(1).getArchive().getName(),"dicembre 2020");
			
			try {
				s.beginTransaction();
				archiveDao.delete(archive1);
				s.getTransaction().commit();
				fail("Exception expected when deleting archive related with posts");
			} catch(Exception e) {
				assertTrue(true);
			}
		}
   	}
}
