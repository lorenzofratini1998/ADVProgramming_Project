package it.univpm.advprog.blog.controllers;

import it.univpm.advprog.blog.model.entities.Tag;
import it.univpm.advprog.blog.model.entities.Archive;
import it.univpm.advprog.blog.model.entities.Post;
import it.univpm.advprog.blog.model.entities.User;
import it.univpm.advprog.blog.model.entities.Comment;
import it.univpm.advprog.blog.model.entities.Attachment;
import it.univpm.advprog.blog.services.ArchiveService;
import it.univpm.advprog.blog.services.AttachmentService;
import it.univpm.advprog.blog.services.CommentService;
import it.univpm.advprog.blog.services.PostService;
import it.univpm.advprog.blog.services.TagService;
import it.univpm.advprog.blog.services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Controller
public class GuestController {
    private final Logger logger = LoggerFactory.getLogger(GuestController.class);
    private TagService tagService;
    private ArchiveService archiveService;
    private PostService postService;
    private UserService userService;

    /**
     * Setter per la proprietà riferita al Service dell'entità Tag.
     *
     * @param tagService Service dell'entità Tag da settare
     */
    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }
    
    /**
     * Setter per la proprietà riferita al Service dell'entità Archive
     * 
     * @param archiveService Service dell'entità Archive da settare
     */
    
    @Autowired
    public void setArchiveService(ArchiveService archiveService) {
    	this.archiveService = archiveService;
    }
    
    /**
     * Setter per la proprietà riferita al Service dell'entità Post
     * 
     * @param postService Service dell'entità Post da settare
     */
    
    @Autowired
    public void setPostService(PostService postService) {
    	this.postService = postService;
    }
    
    /**
     * Setter per la proprietà riferita al Service dell'entità User
     * 
     * @param userSrvice Service dell'entità User da settare
     */
    
    @Autowired
    public void setUserService(UserService userService) {
    	this.userService = userService;
    }
    
    /**
     * Metodo per la richiesta GET per la visualizzazione della lista di tutti i post
     * 
     * @param message eventuale messaggio da mostrare
     * @param uiModel modello associato alla vista
     * @return nome della vista da ritornare
     */
    
    @GetMapping(value="/")
    public String showPost(	@RequestParam(value="message", required = false) String message, 
    						@RequestParam(required = false) Integer page, 
    						Model uiModel) {
    	 logger.info("Listing all the posts...");
    	
	     List<Post> allPosts = this.postService.getAll();
	     List<Tag>allTags=this.tagService.getAll();
	     List<Archive> allArchives=this.archiveService.getAll();
	     int numPosts=allPosts.size();
	     
	     PagedListHolder<Post> pagedListHolder = new PagedListHolder<>(allPosts);
	     pagedListHolder.setPageSize(4);
	     uiModel.addAttribute("maxPages",pagedListHolder.getPageCount());
	     
	     if(page==null || page < 1 || page > pagedListHolder.getPageCount())page=1;
	     
	     uiModel.addAttribute("page",page);
	     if(page == null || page < 1 || page > pagedListHolder.getPageCount()){
	            pagedListHolder.setPage(0);
	            uiModel.addAttribute("posts", pagedListHolder.getPageList());
	        }
	       else if(page <= pagedListHolder.getPageCount()) {
	            pagedListHolder.setPage(page-1);
	            uiModel.addAttribute("posts", pagedListHolder.getPageList());
	        }
	     
	     
	     
	     uiModel.addAttribute("archives", allArchives);
	     uiModel.addAttribute("tags",allTags);
	     //uiModel.addAttribute("posts", allPosts);
	     uiModel.addAttribute("numPosts",numPosts);
	     uiModel.addAttribute("message", message);
	
	     return "home";
    }

    /**
     * Metodo per la richiesta GET per la visualizzazione della lista di tags.
     *
     * @param message eventuale messaggio da mostrare
     * @param uiModel modello associato alla vista
     * @return nome della vista da visualizzare
     */
    @GetMapping(value = "/tags")
    public String showTags(@RequestParam(value = "message", required = false) String message, Model uiModel) {
        logger.info("Listing all the tags...");

        List<Tag> allTags = this.tagService.getAll();

        uiModel.addAttribute("tags", allTags);

        uiModel.addAttribute("message", message);

        return "tags.list";
    }
    
    /**
     * Metodo per la richiesta GET per la visualizzazione degli archivi
     * 
     * @param message  eventuale messaggio da mostrare
     * @param uiModel  modello associato alla vista
     * @return nome nome della vista da visualizzare
     */
    
    @GetMapping(value="/archives")
    public String showArchives(@RequestParam(value="message", required = false) String message, Model uiModel) {
    	logger.info("Listing all the archives...");
    	
	     List<Archive> allArchives = this.archiveService.getAll();
	
	     uiModel.addAttribute("archives", allArchives);
	
	     uiModel.addAttribute("message", message);
	
	     return "archives.list";
    }
    
    /**
     * Metodo per la visualizzazione di tutti i post appartenenti ad un particolare archivio
     * 
     * @param archiveName nome dell'archivio selezionato
     * @return nome della vista da visualizzare
     */
    @GetMapping(value="/post/archive/{archive_name}")
    public String showPostByArchive(@PathVariable("archive_name") String archiveName, Model uiModel) {
    	logger.info("Listing all the posts searched by archive...");
    	Archive selectedArchive = archiveService.getByName(archiveName);
    	
    	List<Post> allPostsByArchive = new ArrayList<>(selectedArchive.getPosts());
    	
    	uiModel.addAttribute("postByArchive",allPostsByArchive);
    	
    	return "home";
    
    }
    
    /**
     * Metodo per la visualizzazione di tutti i post appartenenti ad un particolare tag
     * 
     * @param tagName nome del tag selezionato
     * @return nome della vista da visualizzare
     */
    @GetMapping(value="/blog/tag/{tag_name}")
    public String showPostByTag(@PathVariable("tag_name") String tagName, Model uiModel) {
    	logger.info("Listing all the posts searched by tag...");
    	Tag selectedTag = tagService.getByName(tagName);
    	
    	List<Post> allPostsByTag = new ArrayList<>(selectedTag.getPosts());
    	
    	uiModel.addAttribute("postByTag",allPostsByTag);
    	
    	return "home";
    
    }
    
    /**
     * Metodo per la visualizzazione di tutti i post appartenenti ad un particolare author
     * 
     * @param userName nome dell'author selezionato
     * @return nome della vista da visualizzare
     */
    @GetMapping(value="/blog/author/{username}")
    public String showPostByAuthor(@PathVariable("username") String userName, Model uiModel) {
    	logger.info("Listing all the posts searched by author...");
    	User selectedUser = userService.findUserByUsername(userName);
    	
    	List<Post> allPostsByAuthor = new ArrayList<>(selectedUser.getPosts());
    	
    	uiModel.addAttribute("postByAuthor", allPostsByAuthor);
    	
    	return "post/author";
   
    }
    
    /**
     * Metodo per la visualizzazione dei dettagli di uno specifico post
     * 
     */
    @GetMapping(value="/blog/post/{post_id}")
    public String showPostDetails(@PathVariable("post_id") String post_id, Model uiModel) {
    	logger.info("Show details of a specific post...");
    	Post selectedPost = postService.getById(Long.parseLong(post_id));
    	
    	List<Comment> allCommentsOfPost = new ArrayList<>(selectedPost.getComments());
    	List<Tag> allTagOfPost = new ArrayList<>(selectedPost.getTags());
    	List<Attachment> attachmentOfPost = new ArrayList<>(selectedPost.getAttachments());
    	Archive archiveOfPost = selectedPost.getArchive();
    	User authorOfPost = selectedPost.getAuthor();
    	String shortDescriptionOfPost = selectedPost.getShortDescription();
    	String longDescriptionOfPost = selectedPost.getLongDescription();
    	
    	uiModel.addAttribute(selectedPost);
    	
    	return "post/details";
 
    }
    
    /**
     * Metodo per la visualizzazione della pagina statica about_us
     * 
     */
    @GetMapping(value="/about")
    public String showAboutUs() {
    	
    	return "about_us";
    }
    
    /**
     * Metodo per la visualizzazione della pagina statica privacy_policy
     * 
     */
    @GetMapping(value="/privacy_policy")
    public String showPrivacyPolicy() {
    	
    	return "privacy_policy";
    }
    /**
     * Metodo per la visualizzazione della pagina statica contact_us
     * 
     */
    @GetMapping(value="/contact_us")
    public String showContatcUs() {
    	
    	return "contatc_us";
    }
    
    /**
     * Metodo per il login (copiato da spegni)
     */
    @GetMapping(value = "/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, 
                            Model model) {
        String errorMessage = null;
        if(error != null) {
        	errorMessage = "Username o Password errati !!";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }
    
    /**
     * Metodo per la registrazione
     * 
     * @param uiModel modello associato alla vista
     * @return nome della vista da visualizzare
     */
    @GetMapping(value="/sign_up")
    	public String newUser(Model uiModel) {
    	logger.info("Creating a new user...");
    	
    	uiModel.addAttribute("user", new User());
    		
    	return "sign_up";
    	}
    
    /**
     * Metodo per la richiesta POST di salvataggio nuovo utente
     * 
     * @param newUser utente registrato
     * @return redirect alla vista con la lista di tutti i posts
     */
    @PostMapping(value="/sign_up/save")
    public String newUserSave(@ModelAttribute("newUser") User newUser) {
    	logger.info("Saving a new user");
    	
    	if(newUser.getImageProfile() == null) {
    	this.userService.create(newUser.getUsername(), newUser.getPassword(), newUser.getFirstName(), newUser.getLastName());   	
    	}
    	
    	else this.userService.create(newUser.getUsername(), newUser.getPassword(), newUser.getFirstName(), newUser.getLastName(), newUser.getImageProfile());
    	
    	return "redirect:/login";
    }


}
