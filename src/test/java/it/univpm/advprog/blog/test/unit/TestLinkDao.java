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
import it.univpm.advprog.blog.model.dao.LinkDao;
import it.univpm.advprog.blog.model.dao.PostDao;
import it.univpm.advprog.blog.model.dao.TagDao;
import it.univpm.advprog.blog.model.dao.UserDao;
import it.univpm.advprog.blog.model.entities.Archive;
import it.univpm.advprog.blog.model.entities.Link;
import it.univpm.advprog.blog.model.entities.Post;
import it.univpm.advprog.blog.model.entities.Tag;
import it.univpm.advprog.blog.model.entities.User;
import it.univpm.advprog.blog.test.DataServiceConfigTest;

public class TestLinkDao {
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
	void noLinksAtBeginning() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			LinkDao linkDao=ctx.getBean("linkDao",LinkDao.class);
			Session s  = sf.openSession();
			linkDao.setSession(s);
			assertEquals(linkDao.getAll().size(), 0);
		}
	}
	
	@Test
	void createAndFindWithoutHide() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			LinkDao linkDao=ctx.getBean("linkDao",LinkDao.class);
			PostDao postDao=ctx.getBean("postDao",PostDao.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
			TagDao tagDao=ctx.getBean("tagDao",TagDao.class);
			UserDao userDao=ctx.getBean("userDao",UserDao.class);
			
			Session s=sf.openSession();
			linkDao.setSession(s);
			postDao.setSession(s);
			archiveDao.setSession(s);
			tagDao.setSession(s);
			userDao.setSession(s);
			
			s.beginTransaction();
			User user1 = userDao.create("mario98", "12345678", "Mario", "Rossi");
			Archive archive1 = archiveDao.create("settembre 2020");
			Tag tag1 = tagDao.create("Office 2021");
			Post post1 = postDao.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1, archive1);
			Link link1=linkDao.create(DESCRIPTION, post1, "https://www.univpm.it");
			s.getTransaction().commit();
			
			try {
				linkDao.getById(link1.getId());
				assertTrue(!linkDao.getById(link1.getId()).isHide());
			} catch(Exception e) {
				fail("Exception not expected: " + e.getMessage());
			}
			try {
				Link notFound=linkDao.getById(999);
				assertEquals(notFound,null);
			} catch(Exception e) {
				assertTrue(true);
			}
			List<Link> allFiles=linkDao.getAll();
			assertEquals(allFiles.size(), 1);
			}
		}
	
	@Test
	void createAndFind() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			LinkDao linkDao=ctx.getBean("linkDao",LinkDao.class);
			PostDao postDao=ctx.getBean("postDao",PostDao.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
			TagDao tagDao=ctx.getBean("tagDao",TagDao.class);
			UserDao userDao=ctx.getBean("userDao",UserDao.class);
			
			Session s=sf.openSession();
			linkDao.setSession(s);
			postDao.setSession(s);
			archiveDao.setSession(s);
			tagDao.setSession(s);
			userDao.setSession(s);
			
			s.beginTransaction();
			User user1 = userDao.create("mario98", "12345678", "Mario", "Rossi");
			Archive archive1 = archiveDao.create("settembre 2020");
			Tag tag1 = tagDao.create("Office 2021");
			Post post1 = postDao.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1, archive1);
			Link link1 = linkDao.create(DESCRIPTION, true, post1, "https://www.univpm.it");
			s.getTransaction().commit();
			
			try {
				linkDao.getById(link1.getId());
				assertTrue(linkDao.getById(link1.getId()).isHide());
			} catch(Exception e) {
				fail("Exception not expected: " + e.getMessage());
			}
			try {
				Link notFound=linkDao.getById(999);
				assertEquals(notFound,null);
			} catch(Exception e) {
				assertTrue(true);
			}
			List<Link> allFiles=linkDao.getAll();
			assertEquals(allFiles.size(), 1);
			}
		}
	
	@Test
	void createAndUpdate() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			LinkDao linkDao=ctx.getBean("linkDao",LinkDao.class);
			PostDao postDao=ctx.getBean("postDao",PostDao.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
			TagDao tagDao=ctx.getBean("tagDao",TagDao.class);
			UserDao userDao=ctx.getBean("userDao",UserDao.class);
			
			Session s=sf.openSession();
			linkDao.setSession(s);
			postDao.setSession(s);
			archiveDao.setSession(s);
			tagDao.setSession(s);
			userDao.setSession(s);
			
			s.beginTransaction();
			User user1 = userDao.create("mario98", "12345678", "Mario", "Rossi");
			Archive archive1 = archiveDao.create("settembre 2020");
			Tag tag1 = tagDao.create("Office 2021");
			Post post1 = postDao.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1, archive1);
			Link link1 = linkDao.create(DESCRIPTION, true, post1, "https://www.univpm.it");
			s.getTransaction().commit();
			
			assertEquals(linkDao.getAll().size(),1);
			
			s.beginTransaction();
			link1.setLink("https://www.google.com");
			linkDao.update(link1);
			s.getTransaction().commit();
			
			assertEquals(linkDao.getAll().size(),1);
			assertEquals(linkDao.getById(1).getLink(),"https://www.google.com");
			assertEquals(linkDao.getById(1),link1);
		}
	}
	
	@Test
	void createAndDelete() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			LinkDao linkDao=ctx.getBean("linkDao",LinkDao.class);
			PostDao postDao=ctx.getBean("postDao",PostDao.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
			TagDao tagDao=ctx.getBean("tagDao",TagDao.class);
			UserDao userDao=ctx.getBean("userDao",UserDao.class);
			
			Session s=sf.openSession();
			linkDao.setSession(s);
			postDao.setSession(s);
			archiveDao.setSession(s);
			tagDao.setSession(s);
			userDao.setSession(s);
			
			s.beginTransaction();
			User user1 = userDao.create("mario98", "12345678", "Mario", "Rossi");
			Archive archive1 = archiveDao.create("settembre 2020");
			Tag tag1 = tagDao.create("Office 2021");
			Post post1 = postDao.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1, archive1);
			Link link1 = linkDao.create(DESCRIPTION, true, post1, "https://www.univpm.it");
			s.getTransaction().commit();
			
			assertEquals(linkDao.getAll().size(),1);
			
			s.beginTransaction();
			linkDao.delete(link1);
			s.getTransaction().commit();
			
			assertEquals(linkDao.getAll().size(),0);
		}
	}
	
}
