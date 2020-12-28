package it.univpm.advprog.blog.test.unit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import it.univpm.advprog.blog.model.dao.ArchiveDao;
import it.univpm.advprog.blog.model.dao.CommentDao;
import it.univpm.advprog.blog.model.dao.FileDao;
import it.univpm.advprog.blog.model.dao.PostDao;
import it.univpm.advprog.blog.model.dao.TagDao;
import it.univpm.advprog.blog.model.dao.UserDao;
import it.univpm.advprog.blog.model.entities.Archive;
import it.univpm.advprog.blog.model.entities.Comment;
import it.univpm.advprog.blog.model.entities.Post;
import it.univpm.advprog.blog.model.entities.Tag;
import it.univpm.advprog.blog.model.entities.User;
import it.univpm.advprog.blog.test.DataServiceConfigTest;

public class TestCommentDao {
	
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
   	public void noCommentsAtBeginning() {
   		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			CommentDao commentDao=ctx.getBean("commentDao",CommentDao.class);
			Session s  = sf.openSession();
			commentDao.setSession(s);
			assertEquals(commentDao.getAll().size(), 0);
   			}
   	}
   	
   	@Test
   	public void findCommentById() {
   		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
   			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			PostDao postDao=ctx.getBean("postDao",PostDao.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
			TagDao tagDao=ctx.getBean("tagDao",TagDao.class);
			UserDao userDao=ctx.getBean("userDao",UserDao.class);
			CommentDao commentDao=ctx.getBean("commentDao",CommentDao.class);
			
			Session s=sf.openSession();
			postDao.setSession(s);
			archiveDao.setSession(s);
			tagDao.setSession(s);
			userDao.setSession(s);
			commentDao.setSession(s);
			
			s.beginTransaction();
			User user1 = userDao.create("mario98", "12345678", "Mario", "Rossi");
			Archive archive1 = archiveDao.create("settembre 2020");
			Tag tag1 = tagDao.create("Office 2021");
			Post post1 = postDao.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1, archive1);
			Comment comment1 = commentDao.create(user1, post1, "Commento prova", "Lorem Ipsum");
			s.getTransaction().commit();
			
			try {
				assertEquals(commentDao.getAll().size(),1);
				assertEquals(commentDao.findCommentById(1),comment1);
			} catch(Exception e) {
				fail("Exception not excepted: "+e.getMessage());
			}
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
			CommentDao commentDao = ctx.getBean("commentDao",CommentDao.class);
			
			Session s=sf.openSession();
			postDao.setSession(s);
			archiveDao.setSession(s);
			tagDao.setSession(s);
			userDao.setSession(s);
			commentDao.setSession(s);
			
			s.beginTransaction();
			User user1 = userDao.create("mario98", "12345678", "Mario", "Rossi");
			Archive archive1 = archiveDao.create("settembre 2020");
			Tag tag1 = tagDao.create("Office 2021");
			Post post1 = postDao.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1, archive1);
			Comment comment1 = commentDao.create(user1, post1, "Commento Prova", "Lorem Ipsum");
			s.getTransaction().commit();
			
			try {
				commentDao.findCommentById(comment1.getId());
			} catch(Exception e) {
				fail("Exception not expected: " + e.getMessage());
			}
			try {
				Comment notFound=commentDao.findCommentById(999);
				assertEquals(notFound,null);
			} catch(Exception e) {
				assertTrue(true);
			}
			List<Comment> allcomments=commentDao.getAll();
			assertEquals(allcomments.size(), 1);
			}
   	}
   	
   	@Test
   	public void updateComment() {
   		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
   			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
			FileDao fileDao=ctx.getBean("fileDao",FileDao.class);
			TagDao tagDao=ctx.getBean("tagDao",TagDao.class);
			UserDao userDao=ctx.getBean("userDao",UserDao.class);
			CommentDao commentDao = ctx.getBean("commentDao",CommentDao.class);
			PostDao postDao=ctx.getBean("postDao",PostDao.class);
			
			Session s=sf.openSession();
			archiveDao.setSession(s);
			fileDao.setSession(s);
			tagDao.setSession(s);
			userDao.setSession(s);
			commentDao.setSession(s);
			postDao.setSession(s);
			
			s.beginTransaction();
			User user1 = userDao.create("mario98", "12345678", "Mario", "Rossi");
			User user2 = userDao.create("mario97", "12345678", "Mario", "Verdi");
			Archive archive1 = archiveDao.create("settembre 2020");
			Tag tag1 = tagDao.create("Office 2021");
			Post post1 = postDao.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1, archive1);
			Comment comment1 = commentDao.create(user1, post1, "Commento Prova", "Lorem Ipsum");

			s.getTransaction().commit();
			
			try {
				assertEquals(commentDao.getAll().size(),1);
				assertEquals(commentDao.findCommentById(1), comment1);
			} catch(Exception e) {
				fail("Exception not excepted: "+e.getMessage());
			}
			
			s.beginTransaction();
			comment1.setTitle("Commento Modificato");
			comment1.setAuthor(user2);
			comment1.setDescription("Ipsum lorem");
			commentDao.update(comment1);
			s.getTransaction().commit();
			
			try {
				assertEquals(commentDao.getAll().size(),1);
				assertEquals(commentDao.findCommentById(1), comment1);
			} catch(Exception e) {
				fail("Exception not excepted: "+e.getMessage());
			}
   		}
   	}
   	
   	@Test
   	public void deleteComment() {
   		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
   			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			PostDao postDao=ctx.getBean("postDao",PostDao.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
			TagDao tagDao=ctx.getBean("tagDao",TagDao.class);
			UserDao userDao=ctx.getBean("userDao",UserDao.class);
			CommentDao commentDao = ctx.getBean("commentDao",CommentDao.class);
			
			Session s=sf.openSession();
			postDao.setSession(s);
			archiveDao.setSession(s);;
			tagDao.setSession(s);
			userDao.setSession(s);
			commentDao.setSession(s);
			
			s.beginTransaction();
			User user1 = userDao.create("mario98", "12345678", "Mario", "Rossi");
			Archive archive1 = archiveDao.create("settembre 2020");
			Tag tag1 = tagDao.create("Office 2021");
			Post post1 = postDao.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1, archive1);
			Comment comment1 = commentDao.create(user1, post1, "Commento Prova", "Lorem Ipsum");
			s.getTransaction().commit();
			
			try {
				assertEquals(commentDao.getAll().size(),1);
				assertEquals(commentDao.findCommentById(1),comment1);
			} catch(Exception e) {
				fail("Exception not excepted: "+e.getMessage());
			}
			
			s.beginTransaction();
			commentDao.delete(comment1);;
			s.getTransaction().commit();
			
			try {
				assertEquals(commentDao.getAll().size(),0);
				assertEquals(commentDao.findCommentById(1),null);
			} catch(Exception e) {
				fail("Exception not excepted: "+e.getMessage());
			}
   		}
   	}

}
