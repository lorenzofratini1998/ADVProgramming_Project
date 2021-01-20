package it.univpm.advprog.blog.test.unit.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import it.univpm.advprog.blog.services.ArchiveService;
import it.univpm.advprog.blog.services.AttachmentService;
import it.univpm.advprog.blog.services.CommentService;
import it.univpm.advprog.blog.services.FileService;
import it.univpm.advprog.blog.services.LinkService;
import it.univpm.advprog.blog.services.PostService;
import it.univpm.advprog.blog.services.TagService;
import it.univpm.advprog.blog.services.UserService;
import it.univpm.advprog.blog.test.DataServiceConfigTest;

public class TestFileService {

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
	void createAndFindWithoutHideAndDownloadable() {
		
			User user1 = userService.create("mario98", "12345678", "Mario", "Rossi");
			Tag tag1 = tagService.create("Office 2021");
			Post post1 = postService.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1);
			File a=fileService.create(DESCRIPTION, post1, "file1.jpg");
			
			
			try {
				fileService.getById(a.getId());
				assertEquals(fileService.getById(a.getId()).isHide(),false);
				assertEquals(fileService.getById(a.getId()).isNoDownloadable(),false);
			} catch(Exception e) {
				fail("Exception not expected: " + e.getMessage());
			}
			try {
				File notFound=fileService.getById(999);
				assertEquals(notFound,null);
			} catch(Exception e) {
				assertTrue(true);
			}
			List<File> allFiles=fileService.getAll();
			assertEquals(allFiles.size(), 1);
			}
		
		
	@Test
	void createAndFindWithoutHide() {
		
			User user1 = userService.create("mario98", "12345678", "Mario", "Rossi");
			Tag tag1 = tagService.create("Office 2021");
			Post post1 = postService.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1);
			File a=fileService.create(DESCRIPTION, post1, "file1.jpg", true);
			
			try {
				fileService.getById(a.getId());
				assertEquals(fileService.getById(a.getId()).isHide(),false);
				assertEquals(fileService.getById(a.getId()).isNoDownloadable(),true);
			} catch(Exception e) {
				fail("Exception not expected: " + e.getMessage());
			}
			List<File> allFiles=fileService.getAll();
			assertEquals(allFiles.size(), 1);
			}
		
	
	@Test
	void createAndFindWithoutDownloadble() {
		
			User user1 = userService.create("mario98", "12345678", "Mario", "Rossi");
			Tag tag1 = tagService.create("Office 2021");
			Post post1 = postService.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1);
			File a=fileService.create(DESCRIPTION, true, post1, "file1.jpg");
			
			try {
				fileService.getById(a.getId());
				assertEquals(fileService.getById(a.getId()).isHide(),true);
				assertEquals(fileService.getById(a.getId()).isNoDownloadable(),false);
			} catch(Exception e) {
				fail("Exception not expected: " + e.getMessage());
			}
			List<File> allFiles=fileService.getAll();
			assertEquals(allFiles.size(), 1);
			}
		
	
	@Test
	void createAndFind() {
		
			User user1 = userService.create("mario98", "12345678", "Mario", "Rossi");
			Tag tag1 = tagService.create("Office 2021");
			Post post1 = postService.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1);
			File a=fileService.create(DESCRIPTION, true, post1, "file1.jpg", true);
			
			try {
				fileService.getById(a.getId());
				assertEquals(fileService.getById(a.getId()).isHide(),true);
				assertEquals(fileService.getById(a.getId()).isNoDownloadable(),true);
			} catch(Exception e) {
				fail("Exception not expected: " + e.getMessage());
			}
			List<File> allFiles=fileService.getAll();
			assertEquals(allFiles.size(), 1);
			}
		
	
	@Test
	void createAndUpdate() {
		
		User user1 = userService.create("mario98", "12345678", "Mario", "Rossi");
		Tag tag1 = tagService.create("Office 2021");
		Post post1 = postService.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1);
		File file1=fileService.create(DESCRIPTION, true, post1, "file1.jpg", true);
		
			
			assertEquals(fileService.getAll().size(),1);
			assertEquals(fileService.getByName("post1_file1.jpg").getName(),"post1_file1.jpg");
			
			file1.setHide(true);
			file1.setName("post1_modifica.jpg");
			fileService.update(file1);
			
			assertEquals(fileService.getAll().size(),1);
			assertEquals(fileService.getByName("post1_modifica.jpg").getName(),"post1_modifica.jpg");
		}
	
	
	@Test
	void createAndDelete() {
		User user1 = userService.create("mario98", "12345678", "Mario", "Rossi");
		Tag tag1 = tagService.create("Office 2021");
		Post post1 = postService.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1);
		File file1=fileService.create(DESCRIPTION, true, post1, "file1.jpg", true);
		
			
			assertEquals(fileService.getAll().size(),1);
			
			fileService.delete(file1);
			
			assertEquals(fileService.getAll().size(),0);
			try {
				File notFound=fileService.getById(1);
				assertEquals(notFound,null);
			} catch(Exception e) {
				assertTrue(true);
			}
			
		}
	
	@Test
	void testGetFileByPost() {
		
		User user1 = userService.create("mario98", "12345678", "Mario", "Rossi");
		Tag tag1 = tagService.create("Office 2021");
		Post post1 = postService.create("Installazione Office 2021", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1);
		File file1=fileService.create(DESCRIPTION, true, post1, "file1.jpg", true);
		Set<Attachment> files = new HashSet<Attachment>();
		assertTrue(files.add(file1));
		post1.setAttachments(files);
		post1 = postService.update(post1);
		
		Set<Attachment> allegati = post1.getAttachments();
		assertTrue(allegati.contains(file1));
		assertEquals(allegati.size(),1);
		
		
	}
	}
   	
   	
   	
	
	

