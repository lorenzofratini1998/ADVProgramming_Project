package it.univpm.advprog.blog.test.unit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import it.univpm.advprog.blog.model.dao.ArchiveDao;
import it.univpm.advprog.blog.model.dao.AttachmentDao;
import it.univpm.advprog.blog.model.dao.FileDao;
import it.univpm.advprog.blog.model.dao.PostDao;
import it.univpm.advprog.blog.model.dao.TagDao;
import it.univpm.advprog.blog.model.dao.UserDao;
import it.univpm.advprog.blog.model.entities.Archive;
import it.univpm.advprog.blog.model.entities.Attachment;
import it.univpm.advprog.blog.model.entities.File;
import it.univpm.advprog.blog.model.entities.Post;
import it.univpm.advprog.blog.model.entities.Tag;
import it.univpm.advprog.blog.model.entities.User;
import it.univpm.advprog.blog.test.DataServiceConfigTest;

public class TestFileDao {
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
	void noFilesAtBeginning() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			FileDao fileDao=ctx.getBean("fileDao",FileDao.class);
			Session s  = sf.openSession();
			fileDao.setSession(s);
			assertEquals(fileDao.getAll().size(), 0);
		}
	}
		
	@Test
	void createAndFindWithoutHideAndDownloadable() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			FileDao fileDao=ctx.getBean("fileDao",FileDao.class);
			PostDao postDao=ctx.getBean("postDao",PostDao.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
			TagDao tagDao=ctx.getBean("tagDao",TagDao.class);
			UserDao userDao=ctx.getBean("userDao",UserDao.class);
			
			Session s=sf.openSession();
			fileDao.setSession(s);
			postDao.setSession(s);
			archiveDao.setSession(s);
			tagDao.setSession(s);
			userDao.setSession(s);
			
			s.beginTransaction();
			User user1 = userDao.create("mario98", "12345678", "Mario", "Rossi");
			Archive archive1 = archiveDao.create("settembre 2020");
			Tag tag1 = tagDao.create("Office 2021");
			Post post1 = postDao.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1, archive1);
			File a=fileDao.create(DESCRIPTION, post1, "file1.jpg");
			s.getTransaction().commit();
			
			try {
				fileDao.getById(a.getId());
				assertEquals(fileDao.getById(a.getId()).isHide(),false);
				assertEquals(fileDao.getById(a.getId()).isNoDownloadable(),false);
			} catch(Exception e) {
				fail("Exception not expected: " + e.getMessage());
			}
			try {
				File notFound=fileDao.getById(999);
				assertEquals(notFound,null);
			} catch(Exception e) {
				assertTrue(true);
			}
			List<File> allFiles=fileDao.getAll();
			assertEquals(allFiles.size(), 1);
			}
		}
		
	@Test
	void createAndFindWithoutHide() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			FileDao fileDao=ctx.getBean("fileDao",FileDao.class);
			PostDao postDao=ctx.getBean("postDao",PostDao.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
			TagDao tagDao=ctx.getBean("tagDao",TagDao.class);
			UserDao userDao=ctx.getBean("userDao",UserDao.class);
			
			Session s=sf.openSession();
			fileDao.setSession(s);
			postDao.setSession(s);
			archiveDao.setSession(s);
			tagDao.setSession(s);
			userDao.setSession(s);
			
			s.beginTransaction();
			User user1 = userDao.create("mario98", "12345678", "Mario", "Rossi");
			Archive archive1 = archiveDao.create("settembre 2020");
			Tag tag1 = tagDao.create("Office 2021");
			Post post1 = postDao.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1, archive1);
			File a=fileDao.create(DESCRIPTION, post1, "file1.jpg", true);
			s.getTransaction().commit();
			
			try {
				fileDao.getById(a.getId());
				assertEquals(fileDao.getById(a.getId()).isHide(),false);
				assertEquals(fileDao.getById(a.getId()).isNoDownloadable(),true);
			} catch(Exception e) {
				fail("Exception not expected: " + e.getMessage());
			}
			List<File> allFiles=fileDao.getAll();
			assertEquals(allFiles.size(), 1);
			}
		}
	
	@Test
	void createAndFindWithoutDownloadble() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			FileDao fileDao=ctx.getBean("fileDao",FileDao.class);
			PostDao postDao=ctx.getBean("postDao",PostDao.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
			TagDao tagDao=ctx.getBean("tagDao",TagDao.class);
			UserDao userDao=ctx.getBean("userDao",UserDao.class);
			
			Session s=sf.openSession();
			fileDao.setSession(s);
			postDao.setSession(s);
			archiveDao.setSession(s);
			tagDao.setSession(s);
			userDao.setSession(s);
			
			s.beginTransaction();
			User user1 = userDao.create("mario98", "12345678", "Mario", "Rossi");
			Archive archive1 = archiveDao.create("settembre 2020");
			Tag tag1 = tagDao.create("Office 2021");
			Post post1 = postDao.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1, archive1);
			File a=fileDao.create(DESCRIPTION, true, post1, "file1.jpg");
			s.getTransaction().commit();
			
			try {
				fileDao.getById(a.getId());
				assertEquals(fileDao.getById(a.getId()).isHide(),true);
				assertEquals(fileDao.getById(a.getId()).isNoDownloadable(),false);
			} catch(Exception e) {
				fail("Exception not expected: " + e.getMessage());
			}
			List<File> allFiles=fileDao.getAll();
			assertEquals(allFiles.size(), 1);
			}
		}
	
	@Test
	void createAndFind() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			FileDao fileDao=ctx.getBean("fileDao",FileDao.class);
			PostDao postDao=ctx.getBean("postDao",PostDao.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
			TagDao tagDao=ctx.getBean("tagDao",TagDao.class);
			UserDao userDao=ctx.getBean("userDao",UserDao.class);
			
			Session s=sf.openSession();
			fileDao.setSession(s);
			postDao.setSession(s);
			archiveDao.setSession(s);
			tagDao.setSession(s);
			userDao.setSession(s);
			
			s.beginTransaction();
			User user1 = userDao.create("mario98", "12345678", "Mario", "Rossi");
			Archive archive1 = archiveDao.create("settembre 2020");
			Tag tag1 = tagDao.create("Office 2021");
			Post post1 = postDao.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1, archive1);
			File a=fileDao.create(DESCRIPTION, true, post1, "file1.jpg", true);
			s.getTransaction().commit();
			
			try {
				fileDao.getById(a.getId());
				assertEquals(fileDao.getById(a.getId()).isHide(),true);
				assertEquals(fileDao.getById(a.getId()).isNoDownloadable(),true);
			} catch(Exception e) {
				fail("Exception not expected: " + e.getMessage());
			}
			List<File> allFiles=fileDao.getAll();
			assertEquals(allFiles.size(), 1);
			}
		}
	
	@Test
	void createAndUpdate() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			FileDao fileDao=ctx.getBean("fileDao",FileDao.class);
			PostDao postDao=ctx.getBean("postDao",PostDao.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
			TagDao tagDao=ctx.getBean("tagDao",TagDao.class);
			UserDao userDao=ctx.getBean("userDao",UserDao.class);
			
			Session s=sf.openSession();
			fileDao.setSession(s);
			postDao.setSession(s);
			archiveDao.setSession(s);
			tagDao.setSession(s);
			userDao.setSession(s);
			
			s.beginTransaction();
			User user1 = userDao.create("mario98", "12345678", "Mario", "Rossi");
			Archive archive1 = archiveDao.create("settembre 2020");
			Tag tag1 = tagDao.create("Office 2021");
			Post post1 = postDao.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1, archive1);
			File file1=fileDao.create(DESCRIPTION, false, post1, "file1.jpg", true);
			s.getTransaction().commit();
			
			assertEquals(fileDao.getAll().size(),1);
			assertEquals(fileDao.getByName("post1_file1.jpg").getName(),"post1_file1.jpg");
			
			s.beginTransaction();
			file1.setHide(true);
			file1.setName("post1_modifica.jpg");
			fileDao.update(file1);
			s.getTransaction().commit();
			
			assertEquals(fileDao.getAll().size(),1);
			assertEquals(fileDao.getByName("post1_modifica.jpg").getName(),"post1_modifica.jpg");
		}
	}
	
	@Test
	void createAndDelete() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			FileDao fileDao=ctx.getBean("fileDao",FileDao.class);
			PostDao postDao=ctx.getBean("postDao",PostDao.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
			TagDao tagDao=ctx.getBean("tagDao",TagDao.class);
			UserDao userDao=ctx.getBean("userDao",UserDao.class);
			
			Session s=sf.openSession();
			fileDao.setSession(s);
			postDao.setSession(s);
			archiveDao.setSession(s);
			tagDao.setSession(s);
			userDao.setSession(s);
			
			s.beginTransaction();
			User user1 = userDao.create("mario98", "12345678", "Mario", "Rossi");
			Archive archive1 = archiveDao.create("settembre 2020");
			Tag tag1 = tagDao.create("Office 2021");
			Post post1 = postDao.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1, archive1);
			File file1=fileDao.create(DESCRIPTION, false, post1, "file1.jpg", true);
			s.getTransaction().commit();
			
			assertEquals(fileDao.getAll().size(),1);
			assertEquals(fileDao.getById(1),file1);
			
			s.beginTransaction();
			fileDao.delete(file1);
			s.getTransaction().commit();
			
			assertEquals(fileDao.getAll().size(),0);
			try {
				File notFound=fileDao.getById(1);
				assertEquals(notFound,null);
			} catch(Exception e) {
				assertTrue(true);
			}
			
		}
	}
	
}

