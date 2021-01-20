package it.univpm.advprog.blog.test.unit.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import it.univpm.advprog.blog.model.dao.UserDao;
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

public class TestUserService {

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
	public void noUsersAtBeginning() {
		
			
			assertEquals(userService.findAll().size(), 0);
		}
	
   	
   	
   	
   	@Test
   	public void createAndUpdate() {
   		
			User user1 = userService.create("mario98", "12345678", "Mario", "Rossi");
			
			
			assertEquals(userService.findAll().size(),1);
			
			
			user1.setFirstName("Roberto");
			userService.update(user1);
			
			
			assertEquals(userService.findAll().size(),1);
			assertEquals(user1.getFirstName(),"Roberto");
   		}
   	
   	
   	@Test 
   	 void testFindAll() {
   		
   		assertEquals(userService.findAll().size(),0);
   		int i;
   		for (i=0; i < 10; i++) {
   			
   			userService.create("user"+i, "1234", "Utente"+i, "Prova"+i);
   		}
   		
   		assertEquals(userService.findAll().size(),i);
   		
   		
   	}
   	
   	@Test
   	public void createAndDelete() {
   		
			User user1 = userService.create("mario98", "12345678", "Mario", "Rossi");
			
			
			assertEquals(userService.findAll().size(),1);
			
			
			userService.delete(user1);
			
			
			assertEquals(userService.findAll().size(),0);
   		}
   	}
   	
	
	

