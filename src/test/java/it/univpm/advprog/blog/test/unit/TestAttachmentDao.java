package it.univpm.advprog.blog.test.unit;

import static org.junit.jupiter.api.Assertions.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import it.univpm.advprog.blog.model.dao.ArchiveDao;
import it.univpm.advprog.blog.model.dao.AttachmentDao;
import it.univpm.advprog.blog.model.dao.FileDao;
import it.univpm.advprog.blog.model.dao.LinkDao;
import it.univpm.advprog.blog.model.dao.PostDao;
import it.univpm.advprog.blog.model.dao.TagDao;
import it.univpm.advprog.blog.model.dao.UserDao;
import it.univpm.advprog.blog.model.entities.Archive;
import it.univpm.advprog.blog.model.entities.File;
import it.univpm.advprog.blog.model.entities.Link;
import it.univpm.advprog.blog.model.entities.Post;
import it.univpm.advprog.blog.model.entities.Tag;
import it.univpm.advprog.blog.model.entities.User;
import it.univpm.advprog.blog.test.DataServiceConfigTest;

public class TestAttachmentDao {
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
	void noAttachmentsAtBeginning() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			AttachmentDao attachmentDao=ctx.getBean("attachmentDao",AttachmentDao.class);
			Session s  = sf.openSession();
			attachmentDao.setSession(s);
			assertEquals(attachmentDao.getAll().size(), 0);
		}
	}
   	
   	@Test
   	void findAttachmentById() {
   		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
   			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			LinkDao linkDao=ctx.getBean("linkDao",LinkDao.class);
			PostDao postDao=ctx.getBean("postDao",PostDao.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
			FileDao fileDao=ctx.getBean("fileDao",FileDao.class);
			TagDao tagDao=ctx.getBean("tagDao",TagDao.class);
			UserDao userDao=ctx.getBean("userDao",UserDao.class);
			AttachmentDao attachmentDao=ctx.getBean("attachmentDao",AttachmentDao.class);
			
			Session s=sf.openSession();
			linkDao.setSession(s);
			postDao.setSession(s);
			archiveDao.setSession(s);
			fileDao.setSession(s);
			tagDao.setSession(s);
			userDao.setSession(s);
			attachmentDao.setSession(s);
			
			s.beginTransaction();
			User user1 = userDao.create("mario98", "12345678", "Mario", "Rossi");
			Archive archive1 = archiveDao.create("settembre 2020");
			Tag tag1 = tagDao.create("Office 2021");
			Post post1 = postDao.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1, archive1);
			Link link1=linkDao.create(DESCRIPTION, post1, "https://www.univpm.it");
			File file1=fileDao.create(DESCRIPTION, false, post1, "file1.jpg", true);
			s.getTransaction().commit();
			
			try {
				assertEquals(attachmentDao.getAll().size(),2);
				assertEquals(attachmentDao.getById(1),link1);
				assertEquals(attachmentDao.getById(2),file1);
			} catch(Exception e) {
				fail("Exception not excepted: "+e.getMessage());
			}
   		}
   	}
   	
   	@Test
   	void updateAttachment() {
   		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
   			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			LinkDao linkDao=ctx.getBean("linkDao",LinkDao.class);
			PostDao postDao=ctx.getBean("postDao",PostDao.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
			FileDao fileDao=ctx.getBean("fileDao",FileDao.class);
			TagDao tagDao=ctx.getBean("tagDao",TagDao.class);
			UserDao userDao=ctx.getBean("userDao",UserDao.class);
			AttachmentDao attachmentDao=ctx.getBean("attachmentDao",AttachmentDao.class);
			
			Session s=sf.openSession();
			linkDao.setSession(s);
			postDao.setSession(s);
			archiveDao.setSession(s);
			fileDao.setSession(s);
			tagDao.setSession(s);
			userDao.setSession(s);
			attachmentDao.setSession(s);
			
			s.beginTransaction();
			User user1 = userDao.create("mario98", "12345678", "Mario", "Rossi");
			Archive archive1 = archiveDao.create("settembre 2020");
			Tag tag1 = tagDao.create("Office 2021");
			Post post1 = postDao.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1, archive1);
			Link link1=linkDao.create(DESCRIPTION, post1, "https://www.univpm.it");
			File file1=fileDao.create(DESCRIPTION, false, post1, "file1.jpg", true);
			s.getTransaction().commit();
			
			try {
				assertEquals(attachmentDao.getAll().size(),2);
				assertEquals(attachmentDao.getById(1),link1);
				assertEquals(attachmentDao.getById(2),file1);
			} catch(Exception e) {
				fail("Exception not excepted: "+e.getMessage());
			}
			
			s.beginTransaction();
			file1.setName("file2.jpg");
			attachmentDao.update(file1);
			s.getTransaction().commit();
			
			try {
				assertEquals(attachmentDao.getAll().size(),2);
				assertEquals(attachmentDao.getById(1),link1);
				assertEquals(attachmentDao.getById(2),file1);
			} catch(Exception e) {
				fail("Exception not excepted: "+e.getMessage());
			}
   		}
   	}
   	
   	@Test
   	void deleteAttachment() {
   		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
   			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			LinkDao linkDao=ctx.getBean("linkDao",LinkDao.class);
			PostDao postDao=ctx.getBean("postDao",PostDao.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
			FileDao fileDao=ctx.getBean("fileDao",FileDao.class);
			TagDao tagDao=ctx.getBean("tagDao",TagDao.class);
			UserDao userDao=ctx.getBean("userDao",UserDao.class);
			AttachmentDao attachmentDao=ctx.getBean("attachmentDao",AttachmentDao.class);
			
			Session s=sf.openSession();
			linkDao.setSession(s);
			postDao.setSession(s);
			archiveDao.setSession(s);
			fileDao.setSession(s);
			tagDao.setSession(s);
			userDao.setSession(s);
			attachmentDao.setSession(s);
			
			s.beginTransaction();
			User user1 = userDao.create("mario98", "12345678", "Mario", "Rossi");
			Archive archive1 = archiveDao.create("settembre 2020");
			Tag tag1 = tagDao.create("Office 2021");
			Post post1 = postDao.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1, archive1);
			Link link1=linkDao.create(DESCRIPTION, post1, "https://www.univpm.it");
			File file1=fileDao.create(DESCRIPTION, false, post1, "file1.jpg", true);
			s.getTransaction().commit();
			
			try {
				assertEquals(attachmentDao.getAll().size(),2);
				assertEquals(attachmentDao.getById(1),link1);
				assertEquals(attachmentDao.getById(2),file1);
			} catch(Exception e) {
				fail("Exception not excepted: "+e.getMessage());
			}
			
			s.beginTransaction();
			attachmentDao.delete(file1);
			attachmentDao.delete(link1);
			s.getTransaction().commit();
			
			try {
				assertEquals(attachmentDao.getAll().size(),0);
				assertEquals(attachmentDao.getById(1),null);
				assertEquals(attachmentDao.getById(2),null);
			} catch(Exception e) {
				fail("Exception not excepted: "+e.getMessage());
			}
   		}
   	}
}
