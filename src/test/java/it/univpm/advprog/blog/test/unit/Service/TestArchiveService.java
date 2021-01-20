package it.univpm.advprog.blog.test.unit.Service;

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

public class TestArchiveService {

	
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
   	public void createAndDeleteByArchive() {
		
			Archive archive1 = archiveService.create("dicembre 2020");
			
			
			assertEquals(archiveService.getAll().size(),1);
			assertEquals(archiveService.getByName("dicembre 2020").getName(),"dicembre 2020");
			
			
			archiveService.delete(archive1);
			
			
			assertEquals(archiveService.getAll().size(),0);
			assertNull(archiveService.getByName("dicembre 2020"));
		}
   	
   	
   	@Test
   	public void createAndDeleteByString() {
		
			Archive archive1 = archiveService.create("dicembre 2020");
			
			assertEquals(archiveService.getAll().size(),1);
			assertEquals(archiveService.getByName("dicembre 2020").getName(),"dicembre 2020");
			
			
			archiveService.delete("dicembre 2020");
			
			assertEquals(archiveService.getAll().size(),0);
			assertNull(archiveService.getByName("dicembre 2020"));
		}
   	
   	
   	@Test
   	public void cannotInsertArchiveWithSameName() {
		
			Archive archive1 = archiveService.create("dicembre 2020");
			
			assertEquals(archiveService.getAll().size(),1);
			
			try {
				Archive archive2 = archiveService.create("dicembre 2020");
				fail("Exception expected when creating two archives with same name");
			} catch(Exception e) {
				assertTrue(true);
			}
		}
   	
   	
   	@Test
   	public void cannotDeleteArchiveWithPost() {
		
			User user1 = userService.create("alessandro02", "12345678", "Alessandro", "Bianchi");
			
			Tag tag1 = tagService.create("Tag del test");
			Post post1 = postService.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1);
			
			
			assertEquals(archiveService.getAll().size(),1);
			assertEquals(postService.getAll().size(),1);
			
			try {
				archiveService.delete(post1.getArchive());
				
				fail("Exception expected when deleting archive related with posts");
			} catch(Exception e) {
				assertTrue(true);
			}
		}
   	}
	
	

