package it.univpm.advprog.blog.test.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import it.univpm.advprog.blog.model.dao.ArchiveDao;
import it.univpm.advprog.blog.model.dao.FileDao;
import it.univpm.advprog.blog.model.dao.PostDao;
import it.univpm.advprog.blog.model.dao.TagDao;
import it.univpm.advprog.blog.model.dao.UserDao;
import it.univpm.advprog.blog.model.entities.Archive;
import it.univpm.advprog.blog.model.entities.Post;
import it.univpm.advprog.blog.model.entities.Tag;
import it.univpm.advprog.blog.model.entities.User;
import it.univpm.advprog.blog.test.DataServiceConfigTest;

public class TestPostDao {
	
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
   	public void noPostsAtBeginning() {
   		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			PostDao postDao=ctx.getBean("postDao",PostDao.class);
			Session s  = sf.openSession();
			postDao.setSession(s);
			assertEquals(postDao.getAll().size(), 0);
   			}
   	}
   	
   	@Test
   	public void createAndFind() {
   		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			PostDao postDao=ctx.getBean("postDao",PostDao.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
			TagDao tagDao=ctx.getBean("tagDao",TagDao.class);
			UserDao userDao=ctx.getBean("userDao",UserDao.class);
			
			Session s=sf.openSession();
			postDao.setSession(s);
			archiveDao.setSession(s);
			tagDao.setSession(s);
			userDao.setSession(s);
			
			s.beginTransaction();
			User user1 = userDao.create("mario98", "12345678", "Mario", "Rossi");
			Archive archive1 = archiveDao.create("settembre 2020");
			Tag tag1 = tagDao.create("Office 2021");
			Post post1 = postDao.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1, archive1);
			s.getTransaction().commit();
			
			try {
				postDao.getById(post1.getId());
				assertEquals(postDao.getById(post1.getId()).isHide(),false);
			} catch(Exception e) {
				fail("Exception not expected: " + e.getMessage());
			}
			try {
				Post notFound=postDao.getById(999);
				assertEquals(notFound,null);
			} catch(Exception e) {
				assertTrue(true);
			}
			List<Post> allPosts=postDao.getAll();
			assertEquals(allPosts.size(), 1);
			}
   	}
   	
   	@Test
   	public void createAndFindWithTags() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			PostDao postDao=ctx.getBean("postDao",PostDao.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
			TagDao tagDao=ctx.getBean("tagDao",TagDao.class);
			UserDao userDao=ctx.getBean("userDao",UserDao.class);
			
			Session s=sf.openSession();
			postDao.setSession(s);
			archiveDao.setSession(s);
			tagDao.setSession(s);
			userDao.setSession(s);
			
			s.beginTransaction();
			User user1 = userDao.create("mario98", "12345678", "Mario", "Rossi");
			Archive archive1 = archiveDao.create("settembre 2020");
			Tag tag1 = tagDao.create("Office 2021");
			Tag tag2 = tagDao.create("Office 2020");
			Set<Tag> tags = new HashSet<>();
		    tags.add(tag1);
		    tags.add(tag2);
			Post post1 = postDao.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tags, archive1);
			s.getTransaction().commit();
			
			try {
				postDao.getById(post1.getId());
				assertEquals(post1.getTags().size(),2);
			} catch(Exception e) {
				fail("Exception not expected: " + e.getMessage());
			}
		}
   	}
   	
   	@Test
   	public void createAndFindWithHide() {
   		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			PostDao postDao=ctx.getBean("postDao",PostDao.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
			TagDao tagDao=ctx.getBean("tagDao",TagDao.class);
			UserDao userDao=ctx.getBean("userDao",UserDao.class);
			
			Session s=sf.openSession();
			postDao.setSession(s);
			archiveDao.setSession(s);
			tagDao.setSession(s);
			userDao.setSession(s);
			
			s.beginTransaction();
			User user1 = userDao.create("mario98", "12345678", "Mario", "Rossi");
			Archive archive1 = archiveDao.create("settembre 2020");
			Tag tag1 = tagDao.create("Office 2021");
			Tag tag2 = tagDao.create("Office 2020");
			Set<Tag> tags = new HashSet<>();
		    tags.add(tag1);
		    tags.add(tag2);
			Post post1 = postDao.create("Post1", user1, false, SHORTDESCRIPTION, LONGDESCRIPTION, tags, archive1);
			s.getTransaction().commit();
			
			try {
				postDao.getById(post1.getId());
				assertEquals(postDao.getById(post1.getId()).isHide(),false);
				assertEquals(postDao.getAllByHide(false).size(),1);
			} catch(Exception e) {
				fail("Exception not expected: " + e.getMessage());
			}
   		}
   	}
   	
	@Test
	public void createAndUpdate() {
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
			User user2 = userDao.create("mario97", "12345678", "Mario", "Verdi");
			Archive archive1 = archiveDao.create("settembre 2020");
			Tag tag1 = tagDao.create("Office 2021");
			Post post1 = postDao.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1, archive1);
			s.getTransaction().commit();
			
			assertEquals(postDao.getAll().size(),1);
			
			
			s.beginTransaction();
			post1.setAuthor(user2);
			postDao.update(post1);
			s.getTransaction().commit();
			
			assertEquals(post1.getAuthor(),user2);
		}
	}
	
	@Test
	public void createAndDelete() {
   		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
   			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			PostDao postDao=ctx.getBean("postDao",PostDao.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
			TagDao tagDao=ctx.getBean("tagDao",TagDao.class);
			UserDao userDao=ctx.getBean("userDao",UserDao.class);
			
			Session s=sf.openSession();

			postDao.setSession(s);
			archiveDao.setSession(s);
			tagDao.setSession(s);
			userDao.setSession(s);

			
			s.beginTransaction();
			User user1 = userDao.create("mario98", "12345678", "Mario", "Rossi");
			Archive archive1 = archiveDao.create("settembre 2020");
			Tag tag1 = tagDao.create("Office 2021");
			Post post1 = postDao.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1, archive1);
			s.getTransaction().commit();
			
			try {
				assertEquals(postDao.getAll().size(),1);
			} catch(Exception e) {
				fail("Exception not excepted: "+e.getMessage());
			}
			
			s.beginTransaction();
			postDao.delete(post1);
			s.getTransaction().commit();
			
			try {
				assertEquals(postDao.getAll().size(),0);
				assertEquals(postDao.getById(1),null);
			} catch(Exception e) {
				fail("Exception not excepted: "+e.getMessage());
			}
   		}
	}
}
