package it.univpm.advprog.blog.test.unit.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import it.univpm.advprog.blog.model.entities.Archive;
import it.univpm.advprog.blog.model.entities.Attachment;
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

public class TestPostService {

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
   	void createArchiveWhenWritingFirstPost() {
   	
   	
   	assertEquals(archiveService.getAll().size(),0);
   	User u = userService.create("user", "pass", "Utente", "Prova");
   	Tag t = tagService.create("tag_prova");
   	Post p = postService.create("titolo", u, SHORTDESCRIPTION, LONGDESCRIPTION, t);
   	assertEquals(archiveService.getAll().size(),1);
   	assertTrue(p.getArchive() != null);
   	Archive created = archiveService.getAll().get(0);
   	assertEquals(created.getName(), p.getArchive().getName());
   	
   	
   		
   	}
   	
   	
   	@Test
   	 void testCreateCurrentArchive() {
   		assertEquals(archiveService.getAll().size(),0);
   		Archive currentArchive = postService.createCurrentArchive();
   		assertEquals(archiveService.getAll().size(),1);
   		User u = userService.create("user", "pass", "Utente", "Prova");
   	   	Tag t = tagService.create("tag_prova");
   	   	Post p = postService.create("titolo", u, SHORTDESCRIPTION, LONGDESCRIPTION, t);
   	   	assertEquals(archiveService.getAll().size(),1);
   	   	assertEquals(p.getArchive().getName(),currentArchive.getName());
   	   	
 		
   	}
   	
   	/* 
   	 * Nello unit test seguente si verifica che se un post non contiene TUTTI i tags 
   	 * passati alla funzione di servizio getByTags allora il post non viene 
   	 * ritornato da quest'ultima.
   	 */
   	@Test
   	 void findPostFromTagsSet() {
   		
   		
   		Tag tag1 = tagService.create("tag1");
   		Tag tag2 = tagService.create("tag2");
   		Set<Tag> tags = new HashSet<Tag>();
   		tags.add(tag1);
   		tags.add(tag2);
   		//Tag tag3 = tagService.create("tag3");
   		User u = userService.create("user", "pass", "Utente", "Prova");
   	    Post post1 = postService.create("title1", u, SHORTDESCRIPTION, LONGDESCRIPTION, tags);
   		Post post2 = postService.create("title2", u, SHORTDESCRIPTION, LONGDESCRIPTION, tag1);
   	    //List<Post> allPosts = postService.getAll();
   		List<Post> list_found = postService.getByTags(tags);
   		assertEquals(list_found.size(),1);
   		Post found = list_found.get(0);
   		assertEquals(found.getId(),post1.getId());
   		assertFalse(found.getId() == post2.getId());
   	}
   	
   	
   	/* 
   	 * Nello unit test seguente si verifica che se un post non contiene TUTTI gli allegati 
   	 * passati alla funzione di servizio getByAttachments allora il post non viene 
   	 * ritornato da quest'ultima.
   	 */
   	@Test
   	 void findPostFromAttachmentsSet() {
   		
   		
   		Tag tag1 = tagService.create("tag1");
   		//Tag tag3 = tagService.create("tag3");
   		User u = userService.create("user", "pass", "Utente", "Prova");
   	    Post post1 = postService.create("title1", u, SHORTDESCRIPTION, LONGDESCRIPTION, tag1);
   		Post post2 = postService.create("title2", u, SHORTDESCRIPTION, LONGDESCRIPTION, tag1);
   		Attachment att1 = fileService.create(DESCRIPTION, post1, "file1");
   		Attachment att2 = fileService.create(DESCRIPTION, post1, "file2");
   		Attachment att3 = fileService.create(DESCRIPTION, post2, "file3");
   		Set<Attachment> attachments = new HashSet<Attachment>();
   		attachments.add(att1);
   		attachments.add(att2);
   	    //List<Post> allPosts = postService.getAll();
   		List<Post> list_found = postService.getByAttachments(attachments);
   		assertEquals(list_found.size(),1);
   		Post found = list_found.get(0);
   		assertEquals(found.getId(),post1.getId());
   		assertFalse(found.getId() == post2.getId());
   	}
   	
	
}
