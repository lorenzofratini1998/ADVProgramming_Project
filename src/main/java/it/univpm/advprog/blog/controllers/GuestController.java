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

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
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
    @Autowired
    private HttpServletRequest request;

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
     * @param userService Service dell'entità User da settare
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
    	
	     List<Post> allPosts = this.postService.getAllByHide(false);
	     List<Tag>allTags=this.tagService.getAll();
	     List<Archive> allArchives=this.archiveService.getAll();

	     postsPagination(page, uiModel, allPosts);

	     uiModel.addAttribute("archives", allArchives);
	     uiModel.addAttribute("tags",allTags);
	     uiModel.addAttribute("numPosts",allPosts.size());
	     uiModel.addAttribute("postsTitle","Tutti i post del blog");
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
    @GetMapping(value="/blog/archive/{archive_name}")
    public String showPostByArchive(@RequestParam(required = false) Integer page, @PathVariable("archive_name")
            String archiveName, Model uiModel) {
    	logger.info("Listing all the posts searched by archive...");
    	Archive selectedArchive = archiveService.getByName(archiveName);

        List<Post> allPostsByArchive = new ArrayList<>();

        for (Post post:selectedArchive.getPosts()) {
            if(!post.isHide()) {
                allPostsByArchive.add(post);
            }
        }

        postsPagination(page, uiModel, allPostsByArchive);

        uiModel.addAttribute("archives", this.archiveService.getAll());
        uiModel.addAttribute("tags",this.tagService.getAll());
        uiModel.addAttribute("postsTitle","Tutti i post dell'archivio \"" + archiveName + "\"");
        uiModel.addAttribute("numPosts",allPostsByArchive.size());
    	
    	return "home";
    
    }

    /**
     * Metodo per la visualizzazione di tutti i post appartenenti ad un particolare tag
     * 
     * @param tagName nome del tag selezionato
     * @return nome della vista da visualizzare
     */
    @GetMapping(value="/blog/tag/{tag_name}")
    public String showPostByTag(@RequestParam(required = false) Integer page, @PathVariable("tag_name") String tagName,
                                Model uiModel) {
    	logger.info("Listing all the posts searched by tag...");
    	Tag selectedTag = tagService.getByName(tagName);
    	
    	List<Post> allPostsByTag = new ArrayList<>();

        for (Post post:selectedTag.getPosts()) {
            if(!post.isHide()) {
                allPostsByTag.add(post);
            }
        }

        postsPagination(page, uiModel, allPostsByTag);

        uiModel.addAttribute("archives", this.archiveService.getAll());
        uiModel.addAttribute("tags", this.tagService.getAll());
        uiModel.addAttribute("postsTitle","Tutti i post con tag \"" + tagName + "\"");
        uiModel.addAttribute("numPosts",allPostsByTag.size());
    	
    	return "home";
    
    }
    
    /**
     * Metodo per la visualizzazione di tutti i post appartenenti ad un particolare author
     * 
     * @param userName nome dell'author selezionato
     * @return nome della vista da visualizzare
     */
    @GetMapping(value="/blog/author/{username}")
    public String showPostByAuthor(@RequestParam(required = false) Integer page, @PathVariable("username") String
            userName, Model uiModel) {
    	logger.info("Listing all the posts searched by author...");
    	User selectedUser = userService.findUserByUsername(userName);
    	
    	List<Post> allPostsByAuthor = new ArrayList<>();

        for (Post post:selectedUser.getPosts()) {
            if(!post.isHide()) {
                allPostsByAuthor.add(post);
            }
        }

        postsPagination(page, uiModel, allPostsByAuthor);

        uiModel.addAttribute("archives", this.archiveService.getAll());
        uiModel.addAttribute("tags", this.tagService.getAll());
        uiModel.addAttribute("postsTitle","Tutti i post dell'autore \"" + userName + "\"");
        uiModel.addAttribute("numPosts",allPostsByAuthor.size());
    	
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

    	if(selectedPost.isHide()) {

            String strMessage = "Il post specificato non può essere visualizzato.";
            return "redirect:/?message=" + strMessage;

        } else {

            uiModel.addAttribute(selectedPost);
            return "post.details";
        }
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
    @GetMapping(value="/disclaimer")
    public String showPrivacyPolicy() {
    	
    	return "disclaimer";
    }
    /**
     * Metodo per la visualizzazione della pagina statica contact_us
     * 
     */
    @GetMapping(value="/contacts")
    public String showContatcUs() {
    	
    	return "contact_us";
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
    	public String newUser(Model uiModel, @RequestParam(value="message", required = false) String message) {
    	logger.info("Creating a new user...");
    	
    	uiModel.addAttribute("user", new User());
    	uiModel.addAttribute("message", message);
    		
    	return "sign_up";
    	}
    
    /**
     * Metodo per la richiesta POST di salvataggio nuovo utente
     * 
     * @param newUser utente registrato
     * @return redirect alla vista con la lista di tutti i posts
     */
    @PostMapping(value="/sign_up/save", consumes = "multipart/form-data")
    public String newUserSave(@ModelAttribute("newUser") User newUser, @RequestParam("image") MultipartFile file) {
    	logger.info("Saving a new user");

    	User u = this.userService.findUserByUsername(newUser.getUsername());

    	if( u != null) {
    		String notAvailable = "Username non disponibile, scegline un altro!";
    		return "redirect:/sign_up?message=" + notAvailable;
    	}
    	
    	
        if (file.isEmpty()) {
    	this.userService.create(newUser.getUsername(), newUser.getPassword(), newUser.getFirstName(), newUser.getLastName());   	
    	} else {
            String nameOfFile = null;
            try {
                String uploadsDir = "/WEB-INF/files/profile_pictures/";
                String realPathtoUploads = request.getServletContext().getRealPath(uploadsDir);
                if (!new File(realPathtoUploads).exists()) {
                    logger.info("creating the directory...");
                    if(!new File(realPathtoUploads).mkdir()){
                        String strMessage = "ERRORE, impossibile creare la cartella nel server!";
                        return "redirect:/sign_up?message=" + strMessage;
                    }
                }

                logger.info("realPathtoUploads = {}", realPathtoUploads);
                // rename uploaded file with the username
                String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
                nameOfFile = newUser.getUsername() + "." + fileExtension;
                String filePath = realPathtoUploads + nameOfFile;
                File dest = new File(filePath);
                // controllo che sia un file immagine
                String mimetype= new MimetypesFileTypeMap().getContentType(dest);
                String type = mimetype.split("/")[0];
                if(!type.equals("image")) {
                    String strMessage = "ERRORE, il file specificato non è un'immagine!";
                    return "redirect:/sign_up?message=" + strMessage;
                }
                // sposto il file sulla cartella destinazione
                file.transferTo(dest);
            } catch (Exception e){
                e.printStackTrace();
            }

            this.userService.create(newUser.getUsername(), newUser.getPassword(), newUser.getFirstName(), newUser.getLastName(), nameOfFile);
        }

    	return "redirect:/login";
    }

    /**
     * Metodo utilizzato per sfruttare la paginazione nella home.
     *
     * @param page numero della pagina
     * @param uiModel modello associato alla vista
     * @param posts lista di post da paginare
     */
    private void postsPagination(@RequestParam(required = false) Integer page, Model uiModel, List<Post>
            posts) {
        PagedListHolder<Post> pagedListHolder = new PagedListHolder<>(posts);
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
    }
}
