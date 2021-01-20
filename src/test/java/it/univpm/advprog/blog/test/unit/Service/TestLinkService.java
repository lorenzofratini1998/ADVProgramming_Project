package it.univpm.advprog.blog.test.unit.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import it.univpm.advprog.blog.model.entities.Attachment;
import it.univpm.advprog.blog.model.entities.File;
import it.univpm.advprog.blog.model.entities.Link;
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

public class TestLinkService {

	
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
	void createAndFindWithoutHide() {
		
			User user1 = userService.create("mario98", "12345678", "Mario", "Rossi");
			Tag tag1 = tagService.create("Office 2021");
			Post post1 = postService.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1);
			Link link1=linkService.create(DESCRIPTION, post1, "https://www.univpm.it");
			
			try {
				linkService.getById(link1.getId());
				assertTrue(!linkService.getById(link1.getId()).isHide());
			} catch(Exception e) {
				fail("Exception not expected: " + e.getMessage());
			}
			try {
				Link notFound=linkService.getById(999);
				assertEquals(notFound,null);
			} catch(Exception e) {
				assertTrue(true);
			}
			List<Link> allFiles=linkService.getAll();
			assertEquals(allFiles.size(), 1);
			}
		
	
	@Test
	void createAndFind() {
		User user1 = userService.create("mario98", "12345678", "Mario", "Rossi");
		Tag tag1 = tagService.create("Office 2021");
		Post post1 = postService.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1);
		Link link1=linkService.create(DESCRIPTION, post1, "https://www.univpm.it");
		
			try {
				linkService.getById(link1.getId());
				assertFalse(linkService.getById(link1.getId()).isHide());
			} catch(Exception e) {
				fail("Exception not expected: " + e.getMessage());
			}
			try {
				Link notFound=linkService.getById(999);
				assertEquals(notFound,null);
			} catch(Exception e) {
				assertTrue(true);
			}
			List<Link> allFiles=linkService.getAll();
			assertEquals(allFiles.size(), 1);
			}
		
	
	@Test
	void createAndUpdate() {
		User user1 = userService.create("mario98", "12345678", "Mario", "Rossi");
		Tag tag1 = tagService.create("Office 2021");
		Post post1 = postService.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1);
		Link link1=linkService.create(DESCRIPTION, post1, "https://www.univpm.it");
		
			
			assertEquals(linkService.getAll().size(),1);
			
			link1.setLink("https://www.google.com");
			linkService.update(link1);
			
			assertEquals(linkService.getAll().size(),1);
			assertEquals(linkService.getById(1).getLink(),"https://www.google.com");
		}
	
	
	@Test
	void createAndDelete() {
		User user1 = userService.create("mario98", "12345678", "Mario", "Rossi");
		Tag tag1 = tagService.create("Office 2021");
		Post post1 = postService.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1);
		Link link1=linkService.create(DESCRIPTION, post1, "https://www.univpm.it");
		
			
			assertEquals(linkService.getAll().size(),1);
			
			linkService.delete(link1);
			
			assertEquals(linkService.getAll().size(),0);
		}
	
	@Test
	void testGetLinkByPost() {
		
		User user1 = userService.create("mario98", "12345678", "Mario", "Rossi");
		Tag tag1 = tagService.create("Office 2021");
		Post post1 = postService.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1);
		Link link1=linkService.create(DESCRIPTION, post1, "link1.jpg");
		Set<Attachment> links = new HashSet<Attachment>();
		assertTrue(links.add(link1));
		post1.setAttachments(links);
		post1 = postService.update(post1);
		
		Set<Attachment> allegati = post1.getAttachments();
		assertTrue(allegati.contains(link1));
		assertEquals(allegati.size(),1);
		
		
	}
   	
   	
   	
	
	
}
