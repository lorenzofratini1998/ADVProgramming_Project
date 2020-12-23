package it.univpm.advprog.blog.controllers;

import it.univpm.advprog.blog.model.entities.*;
import it.univpm.advprog.blog.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Controller
public class AdminController {
    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private TagService tagService;
    private ArchiveService archiveService;
    private PostService postService;
    private CommentService commentService;
    private AttachmentService attachmentService;
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
     * Setter per la proprietà riferita al Service dell'entità Archive.
     *
     * @param archiveService Service dell'entità Archive da settare
     */
    @Autowired
    public void setArchiveService(ArchiveService archiveService) {
        this.archiveService = archiveService;
    }

    /**
     * Setter per la proprietà riferita al Service dell'entità User.
     *
     * @param userService Service dell'entità User da settare
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Setter per la proprietà riferita al Service dell'entità Attachment.
     *
     * @param attachmentService Service dell'entità Attachment da settare
     */
    @Autowired
    public void setAttachmentService(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    /**
     * Setter per la proprietà riferita al Service dell'entità Comment.
     *
     * @param commentService Service dell'entità Comment da settare
     */
    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Setter per la proprietà riferita al Service dell'entità Post.
     *
     * @param postService Service dell'entità Post da settare
     */
    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    /**
     * Metodo per la richiesta GET per la creazione di un nuovo tag.
     *
     * @param uiModel modello associato alla vista
     * @return nome della vista da visualizzare
     */
    @GetMapping(value = "/tags/new")
    public String newTag(Model uiModel) {
        logger.info("Creating a new tag...");

        uiModel.addAttribute("tag", new Tag());

        return "tags.new";
    }

    /**
     * Metodo per la richiesta POST per il salvataggio di un nuovo tag.
     *
     * @param tag           tag restituito dalla richiesta
     * @param bindingResult eventuali errori di validazione
     * @param uiModel       modello associato alla vista
     * @return nome della vista da visualizzare
     */
    @PostMapping(value = "/tags/new/save")
    public String saveTag(@ModelAttribute("tag") Tag tag, BindingResult bindingResult, Model uiModel) {
        logger.info("Saving a new tag...");

        try {
            //this.sanitizeId(tag.getName());

            this.tagService.update(tag);

            String strMessage = "Il tag \"" + tag.getName() + "\" è stato salvato correttamente!";
//			uiModel.addAttribute("message", strMessage);

            return "redirect:/tags/?message=" + strMessage;

        } catch (RuntimeException e) {

            return "redirect:/tags/?message=" + e.getMessage();
        }
    }

    /**
     * Metodo per la richiesta GET per la cancellazione di un tag VUOTO.
     *
     * @param tagName nome del tag da cancellare
     * @return nome della vista da visualizzare
     */
    @GetMapping(value = "/tags/delete/{tag_name}")
    public String deleteTag(@PathVariable("tag_name") String tagName) {
        logger.info("Deleting the tag \"" + tagName + "\"...");

        Tag selectedTag = this.tagService.getByName(tagName);
        String strMessage;

        if (selectedTag.getPosts().size() == 0) {
            this.tagService.delete(selectedTag);
            strMessage = "Il tag \"" + selectedTag.getName() + "\" è stato cancellato correttamente!";
        } else {
            strMessage = "Il tag \"" + selectedTag.getName() + "\" contiene dei post... " +
                    "Non può essere cancellato!";
        }

        return "redirect:/tags/?message=" + strMessage;
    }

    /**
     * Metodo per la richiesta GET per la cancellazione di un archivio VUOTO.
     *
     * @param archiveName nome dell'archivio da cancellare
     * @return nome della vista da visualizzare
     */
    @GetMapping(value = "/archives/delete/{archive_name}")
    public String deleteArchive(@PathVariable("archive_name") String archiveName) {
        logger.info("Deleting the archive \"" + archiveName + "\"...");

        Archive selectedArchive = this.archiveService.getByName(archiveName);
        String strMessage;

        if (selectedArchive.getPosts().size() == 0) {
            this.archiveService.delete(selectedArchive);
            strMessage = "L'archivio \"" + selectedArchive.getName() + "\" è stato cancellato correttamente!";
        } else {
            strMessage = "L'archivio \"" + selectedArchive.getName() + "\" contiene dei post... " +
                    "Non può essere cancellato!";
        }

        return "redirect:/archives/?message=" + strMessage;
    }

    /**
     * Metodo per la richiesta GET per la visualizzazione della lista di allegati.
     *
     * @param message eventuale messaggio da mostrare
     * @param uiModel modello associato alla vista
     * @return nome della vista da visualizzare
     */
    @GetMapping(value = "/attachments")
    public String showAttachments(@RequestParam(value = "message", required = false) String message, Model uiModel) {
        logger.info("Listing all the attachments...");

        List<Attachment> allAttachments = this.attachmentService.getAll();

        uiModel.addAttribute("attachments", allAttachments);
//		uiModel.addAttribute("numberOfAttachments", allAttachments.size());

        uiModel.addAttribute("message", message);

        return "attachments.list";
    }

    /**
     * Metodo per la richiesta GET per nascondere un allegato.
     *
     * @param attachment_id ID dell'allegato da nascondere
     * @return nome della vista da visualizzare
     */
    @GetMapping(value = "/attachments/hide/{attachment_id}")
    public String hideAttachment(@PathVariable("attachment_id") String attachment_id) {
        Attachment selectedAttachment = attachmentService.getById(Long.parseLong(attachment_id));
        logger.info("Hiding the attachment \"" + selectedAttachment.getDescription() + "\"...");

        selectedAttachment.setHide(true);
        this.attachmentService.update(selectedAttachment); //TODO: serve?
        String strMessage = "L'allegato \"" + selectedAttachment.getDescription() + "\" è stato nascosto correttamente!";

        return "redirect:/attachments/?message=" + strMessage;
    }
    
    /**
     * Metodo per la richiesta GET per mostrare un allegato.
     *
     * @param attachment_id ID dell'allegato da nascondere
     * @return nome della vista da visualizzare
     */
    @GetMapping(value = "/attachments/show/{attachment_id}")
    public String showAttachment(@PathVariable("attachment_id") String attachment_id) {
        Attachment selectedAttachment = attachmentService.getById(Long.parseLong(attachment_id));
        logger.info("Showing the attachment \"" + selectedAttachment.getDescription() + "\"...");

        selectedAttachment.setHide(false);
        this.attachmentService.update(selectedAttachment); //TODO: serve?
        String strMessage = "L'allegato \"" + selectedAttachment.getDescription() + "\" è stato nascosto correttamente!";

        return "redirect:/attachments/?message=" + strMessage;
    }

    /**
     * Metodo per la richiesta GET per la visualizzazione della lista di tutti gli utenti.
     *
     * @param message eventuale messaggio da mostrare
     * @param uiModel modello associato alla vista
     * @return nome della vista da visualizzare
     */
    @GetMapping(value = "/users")
    public String showUsers(@RequestParam(value = "message", required = false) String message, Model uiModel) {
        logger.info("Listing all the users...");

        List<User> allUsers = this.userService.findAll();
        for(User user: allUsers) {
        	if(user.isAdmin()) {
        		allUsers.remove(user);
        	}
        };

        uiModel.addAttribute("users", allUsers);

        uiModel.addAttribute("message", "prova");

        return "users.list";
    }

    /**
     * Metodo per la richiesta GET per disabilitare un utente.
     *
     * @param username username dell'utente da disabilitare
     * @return nome della vista da visualizzare
     */
    @GetMapping(value = "/users/disable/{username}")
    public String disableUser(@PathVariable("username") String username) {
        User selectedUser = userService.findUserByUsername(username);
        logger.info("Disabling the user \"" + selectedUser.getUsername() + "\"...");

        selectedUser.setDisabled(true);
        this.userService.update(selectedUser); //TODO: serve?
        String strMessage = "L'utente \"" + selectedUser.getUsername() + "\" è stato disabilitato correttamente!";
        return "redirect:/users/?message=" + strMessage;
    }
    
    /**
     * Metodo per la richiesta GET per abilitare un utente.
     *
     * @param username username dell'utente da abilitare
     * @return nome della vista da visualizzare
     */
    @GetMapping(value = "/users/enable/{username}")
    public String enableUser(@PathVariable("username") String username) {
        User selectedUser = userService.findUserByUsername(username);
        logger.info("Enabling the user \"" + selectedUser.getUsername() + "\"...");

        selectedUser.setDisabled(false);
        this.userService.update(selectedUser); //TODO: serve?
        String strMessage = "L'utente \"" + selectedUser.getUsername() + "\" è stato abilitato correttamente!";

        return "redirect:/users/?message=" + strMessage;
    }
    
    /**
	 * Metodo per la richiesta GET di visualizzazione dei commenti di tutti gli autori
	 *
	 * @param uiModel	porzione di modello da passare alla vista
	 * @return			nome della vista da renderizzare
	 */
	@GetMapping(value = "/comments/manage")
	public String showComments(Authentication authentication, Model uiModel) {
		logger.info("Showing all comments...");

		List<Comment> allComments = new ArrayList<>();
		List<User> allAuthor= this.userService.findAll();
		
		for(User author: allAuthor) {
			if(!author.isAdmin()) {
				allComments.addAll(author.getComments());
			}
		}
		uiModel.addAttribute("comments", allComments);
	
		
		return "comments.listforAdmin";
	}

    /**
     * Metodo per la richiesta GET per nascondere un commento.
     *
     * @param comment_id ID del commento da nascondere
     * @return nome della vista da visualizzare
     */
    @GetMapping(value = "/comments/manage/hide/{comment_id}")
    public String hideComment(@PathVariable("comment_id") String comment_id) {
        Comment selectedComment = commentService.findCommentById(Long.parseLong(comment_id));
        logger.info("Hiding the comment \"" + selectedComment.getDescription() + "\"...");

        selectedComment.setHide(true);
        this.commentService.update(selectedComment); //TODO: serve?
        String strMessage = "Il commento \"" + selectedComment.getDescription() + "\" è stato nascosto correttamente!";

        return "redirect:/comments/manage";
    }
    
    /**
     * Metodo per la richiesta GET per mostrare un commento.
     *
     * @param comment_id ID del commento da nascondere
     * @return nome della vista da visualizzare
     */
    @GetMapping(value = "/comments/manage/show/{comment_id}")
    public String showComment(@PathVariable("comment_id") String comment_id, Model uiModel) {
        Comment selectedComment = commentService.findCommentById(Long.parseLong(comment_id));
        logger.info("Hiding the comment \"" + selectedComment.getDescription() + "\"...");

        selectedComment.setHide(false);
        this.commentService.update(selectedComment); //TODO: serve?
        String strMessage = "Il commento \"" + selectedComment.getDescription() + "\" è stato mostrato correttamente!";
        
        uiModel.addAttribute("showMessage", strMessage);

        return "redirect:/comments/manage";
    }

    /**
	 * Metodo per la richiesta GET di mosrare tutti i post scritti dall'utente
	 *
	 * @param uiModel	porzione di modello da passare alla vista
	 * @return			nome della vista da renderizzare
	 */
	@GetMapping(value = "/posts/manage")
	public String showMyPosts(Authentication authentication, Model uiModel) {
		logger.info("Showing your posts...");

		List<Post> allPosts = new ArrayList<>();
		List<User> allAuthor= this.userService.findAll();
		
		for(User author: allAuthor) {
			if(!author.isAdmin()) {
				allPosts.addAll(author.getPosts());
			}
		}
		
		if(allPosts.isEmpty()) {
			String strMessage = "Non ci sono post scritti dagli utenti!";
			return "redirect:/?message=" + strMessage ;
		}
			
		uiModel.addAttribute("posts", allPosts);
		uiModel.addAttribute("numPosts", allPosts.size());

		return "posts.listForAdmin";

	}
    
    /**
     * Metodo per la richiesta GET per nascondere un post.
     *
     * @param post_id ID del post da nascondere
     * @return nome della vista da visualizzare
     */
    @GetMapping(value = "/posts/manage/hide/{post_id}")
    public String hidePost(@PathVariable("post_id") String post_id) {
        Post selectedPost = postService.getById(Long.parseLong(post_id));
        logger.info("Hiding the post \"" + selectedPost.getTitle() + "\"...");

        selectedPost.setHide(true);
        this.postService.update(selectedPost); //TODO: serve?
        String strMessage = "Il post \"" + selectedPost.getTitle() + "\" è stato nascosto correttamente!";

        return "redirect:/posts/manage?message=" + strMessage;
    }
    
    /**
     * Metodo per la richiesta GET per mostrare un post.
     *
     * @param post_id ID del post da mostrare
     * @return nome della vista da visualizzare
     */
    @GetMapping(value = "/posts/manage/show/{post_id}")
    public String showPost(@PathVariable("post_id") String post_id) {
        Post selectedPost = postService.getById(Long.parseLong(post_id));
        logger.info("Hiding the post \"" + selectedPost.getTitle() + "\"...");

        selectedPost.setHide(false);
        this.postService.update(selectedPost); //TODO: serve?
        String strMessage = "Il post \"" + selectedPost.getTitle() + "\" è stato mostrato correttamente!";

        return "redirect:/posts/manage?message=" + strMessage;
    }
}
