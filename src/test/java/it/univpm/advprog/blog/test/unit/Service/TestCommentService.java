package it.univpm.advprog.blog.test.unit.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import it.univpm.advprog.blog.model.dao.CommentDao;
import it.univpm.advprog.blog.model.entities.Archive;
import it.univpm.advprog.blog.model.entities.Comment;
import it.univpm.advprog.blog.model.entities.Post;
import it.univpm.advprog.blog.model.entities.Tag;
import it.univpm.advprog.blog.model.entities.User;
import it.univpm.advprog.blog.services.ArchiveService;
import it.univpm.advprog.blog.services.AttachmentService;
import it.univpm.advprog.blog.services.CommentService;
import it.univpm.advprog.blog.services.FileService;
import it.univpm.advprog.blog.services.LinkService;
import it.univpm.advprog.blog.services.PostService;
import it.univpm.advprog.blog.services.TagService;
import it.univpm.advprog.blog.services.UserService;
import it.univpm.advprog.blog.test.DataServiceConfigTest;

public class TestCommentService {

	

	
	private AnnotationConfigApplicationContext ctx;
	private UserService userService;
	private TagService tagService;
	private PostService postService;
	private ArchiveService archiveService;
	private CommentService commentService;
	private AttachmentService attachmentService;
	private FileService fileService;
	private LinkService linkService;
	
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
	
   	@BeforeEach
  	 void openContext() {
   	
   	ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class);
   	userService = ctx.getBean("userService", UserService.class);
    tagService = ctx.getBean("tagService", TagService.class);
    postService = ctx.getBean("postService", PostService.class);
    archiveService = ctx.getBean("archiveService", ArchiveService.class);
    commentService = ctx.getBean("commentService", CommentService.class);
    fileService = ctx.getBean("fileService", FileService.class);
    linkService = ctx.getBean("linkService", LinkService.class);
    attachmentService = ctx.getBean("attachmentService", AttachmentService.class);
   	
   	
   	}
   	
   	@AfterEach
   	void closeContext() {
   		
   		ctx.close();
   	}
   	
   	@Test
  	 void noCommentsAtBeginning() {
  		
			
			
			assertEquals(commentService.getAll().size(), 0);
  			
  	}
  	
  	@Test
  	 void findCommentById() {
  		
			
			User user1 = userService.create("mario98", "12345678", "Mario", "Rossi");
			Tag tag1 = tagService.create("Office 2021");
			Post post1 = postService.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1);
			Comment comment1 = commentService.create(user1, post1, "Commento prova", "Lorem Ipsum");
			
			
			try {
				
				assertEquals(commentService.getAll().size(),1);
			} catch(Exception e) {
				fail("Exception not excepted: "+e.getMessage());
			}
  		}
  	
  	
  	@Test
  	 void createAndFind() {
  		
			
			
			User user1 = userService.create("mario98", "12345678", "Mario", "Rossi");
			Tag tag1 = tagService.create("Office 2021");
			Post post1 = postService.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1);
			Comment comment1 = commentService.create(user1, post1, "Commento Prova", "Lorem Ipsum");
			
			
			try {
				commentService.findCommentById(comment1.getId());
			} catch(Exception e) {
				fail("Exception not expected: " + e.getMessage());
			}
			try {
				Comment notFound=commentService.findCommentById(999);
				assertNull(notFound);
			} catch(Exception e) {
				assertTrue(true);
			}
			List<Comment> allcomments=commentService.getAll();
			assertEquals(allcomments.size(), 1);
			}
  	
  	
  	@Test
  	 void updateComment() {
  		
			
			
			User user1 = userService.create("mario98", "12345678", "Mario", "Rossi");
			User user2 = userService.create("mario97", "12345678", "Mario", "Verdi");
			
			Tag tag1 = tagService.create("Office 2021");
			Post post1 = postService.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1);
			Comment comment1 = commentService.create(user1, post1, "Commento Prova", "Lorem Ipsum");

			
			try {
				assertEquals(commentService.getAll().size(),1);
			} catch(Exception e) {
				fail("Exception not excepted: "+e.getMessage());
			}
			
			
			comment1.setTitle("Commento Modificato");
			comment1.setAuthor(user2);
			comment1.setDescription("Ipsum lorem");
			commentService.update(comment1);
			
			try {
				assertEquals(commentService.getAll().size(),1);
				assertEquals(commentService.findCommentById(1).getTitle(), "Commento Modificato");
			} catch(Exception e) {
				fail("Exception not excepted: "+e.getMessage());
			}
  		}
  	
  	
  	
  	

	
  	
  	@Test
  	 void testDuplicateCommentsDoNotCauseError() {
  		
  	
  		
  		
  		User user1 = userService.create("mario98", "12345678", "Mario", "Rossi");
		Tag tag1 = tagService.create("Office 2021");
		Post post1 = postService.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1);
		Comment comment1 = commentService.create(user1, post1, "Commento Prova", "Lorem Ipsum");
		
		try {
			assertEquals(1,commentService.getAll().size());
		}
		catch(Exception e) {
			fail("Exception not excepted: "+e.getMessage());
		}
		
		
		Comment comment2 = commentService.create(user1, post1, "Commento Prova", "Lorem Ipsum");
		
		try {
			assertEquals(2,commentService.getAll().size());
			assertEquals(comment1.getTitle(),comment2.getTitle());
			assertSame(comment1.getPost(),comment2.getPost());
			assertSame(comment1.getAuthor(), comment2.getAuthor());
			assertFalse(comment1.getId() == comment2.getId());
			
			
		}
		catch(Exception e) {
			fail("Exception not excepted: "+e.getMessage());
		}
  		
  		
  	 }
	
}
