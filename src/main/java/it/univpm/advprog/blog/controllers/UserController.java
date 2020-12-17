package it.univpm.advprog.blog.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import it.univpm.advprog.blog.model.entities.*;
import it.univpm.advprog.blog.services.*;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
	
	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	private PostService postService;
	private CommentService commentService;
	private UserService userService;
	private TagService tagService;
	private ArchiveService archiveService;
	@Autowired
	private HttpServletRequest request;

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
	 * Setter per la proprietà riferita al Service dell'entità Tag.
	 *
	 * @param tagService Service dell'entità Tag da settare
	 */
	@Autowired
	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}

	/**
	 * Setter per la proprietà postService, relativa alla classe di servizio dei Post
	 * 
	 * @param postService
	 */
	@Autowired
	public void setPostService(PostService postService) {
		this.postService = postService;
	}
	
	/**
	 * Setter per la proprietà commentService, relativa alla classe di servizio dei Commenti
	 * 
	 * @param commentService
	 */
	@Autowired
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
	
	/**
	 * Setter per la proprietà userService, relativa alla classe di servizio degli Utenti
	 * 
	 * @param userService
	 */
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
	
	/**
	 * Metodo per la richiesta GET di mosrare tutti i post scritti dall'utente
	 *
	 * @param uiModel	porzione di modello da passare alla vista
	 * @return			nome della vista da renderizzare
	 */
	@GetMapping(value = "/posts")
	public String showMyPosts(Authentication authentication, Model uiModel) {
		logger.info("Showing your posts...");

		List<Post> allPosts = new ArrayList<>();

		if(authentication != null) {
			User currentLoggedInUser = userService.findUserByUsername(authentication.getName());
			allPosts.addAll(currentLoggedInUser.getPosts());
		}

		/*if(allPosts.isEmpty()) {
			String strMessage = "Non hai scritto alcun post!";
			return "redirect:/?message=" + strMessage ;
		}*/
			
		uiModel.addAttribute("posts", allPosts);
		uiModel.addAttribute("numPosts", allPosts.size());

		return "posts.list";

	}
	
	
	/**
	 * Metodo per la richiesta GET di creazione nuovo post
	 * 
	 * @param uiModel	porzione di modello da passare alla vista
	 * @return			nome della vista da renderizzare
	 */
	@GetMapping(value = "/posts/new")
	public String newPost(Model uiModel) {
		logger.info("Creating a new post");

		List<Tag> tags = tagService.getAll();

		uiModel.addAttribute("post", new Post());
		uiModel.addAttribute("allTags", tags);

		return "posts.new";
	}
	
	
	/**
	 * Metodo per la richiesta POST di salvataggio di un nuovo post
	 *
	 * @param currentArchive eventuale archivio corrente
	 * @param post		post da salvare ottenutod alla form
	 * @param tags      array contenente i nomi dei tag selezionati
	 * @param authentication informazioni per l'autenticazione corrente
	 * @return			redirect all'indirizzo cui fare richiesta
	 */
	@PostMapping(value="/posts/save")
	public String saveNewPost(@RequestParam("archive.name") String currentArchive, @RequestParam(value = "tagsSelected", required = false) String[] tags,
							  Authentication authentication, @ModelAttribute("post") Post post) {
		logger.info("Saving the post");
		try {
			Archive archive;
			if (currentArchive.equals("")) {
				// sto CREANDO il POST, quindi devo IMPOSTARE l'ARCHIVIO
				archive = postService.createCurrentArchive();
			} else {
				// sto EDITANDO il POST, quindi lascio il VECCHIO ARCHIVIO
				archive = archiveService.getByName(currentArchive);
			}
			post.setArchive(archive);

			User author = userService.findUserByUsername(authentication.getName());
			post.setAuthor(author);

			if(tags == null || tags.length == 0) {
				String strMessage = "ERRORE, il post deve contenere almeno un tag.";
				return "redirect:/?message=" + strMessage;
			}

			for (String tagName : tags) {
				post.addTag(tagService.getByName(tagName));
			}

			this.postService.update(post);

			String strMessage = "Post \"" + post.getTitle() + "\" salvato correttamente";
			return "redirect:/?message=" + strMessage;

		} catch (RuntimeException e) {

			String strMessage = "ERRORE: " + e.getMessage();
			return "redirect:/?message=" + strMessage;
		}

	}
	
	
	/**
	 * Metodo per la richiesta GET di modifica di un post
	 *
	 * @param authentication informazioni per 'autenticazione corrente
	 * @param postId	id del post da modificare
	 * @param uiModel	porzione di modello da passare alla vista
	 * @return			nome della vista da renderizzare
	 */
	@GetMapping(value="/posts/edit/{postId}")
	public String editPost(Authentication authentication, @PathVariable("postId") long postId, Model uiModel) {
		logger.info("Modifying a post");

		Post post = this.postService.getById(postId);
		String authorUsername = authentication.getName();

		if (post.getAuthor().getUsername().equals(authorUsername)) {
			List<Tag> tags = tagService.getAll();

			uiModel.addAttribute("post", post);
			uiModel.addAttribute("allTags", tags);
			return "posts.new";

		} else {

			String strMessage = "Non sei abilitato a modificare il post specificato.";
			return "redirect:/?message=" + strMessage;

		}
	}
	
	/**
	 * Metodo per la richiesta GET di eliminazione post
	 * 
	 * @param id		id del commento da rimuovere
	 * @param uiModel	porzione del modello da passare alla vista
	 * @return			redirect all'indirizzo cui fare richiesta
	 */
	@GetMapping(value = "/posts/delete/{postId}")
	public String deletePost (@PathVariable("postId") long id, Model uiModel) {
		logger.info("Deleting a post...");

		Post p = this.postService.getById(id);
		this.postService.delete(p);
		String message = "Post" + p.getTitle() + "eliminato correttamente!";
		return "redirect:" + "/posts/?message=" + message; 
	}
	
	
	
	
	
	/**
	 * Metodo per la richiesta GET di visualizzazione dei commenti
	 *
	 * @param uiModel	porzione di modello da passare alla vista
	 * @return			nome della vista da renderizzare
	 */
	@GetMapping(value = "/comments")
	public String showComments(Authentication authentication, Model uiModel) {
		logger.info("Showing your comments...");

		List<Comment> allComments = new ArrayList<>();

		if(authentication != null) {
			User currentLoggedInUser = userService.findUserByUsername(authentication.getName());
			allComments.addAll(currentLoggedInUser.getComments());
		}

		uiModel.addAttribute("comments", allComments);
	
		
		return "comments.list";
	}

	
	
	/**
	 * Metodo per la richiesta POST di salvataggio nuovo commento 
	 * 
	 * @param comment	commento da rendere persistente
	 * @param username	username dell'utente che ah richiesto l'operazione
	 * @param br 		eventuali errori di validazione
	 * @param uiModel	modello associato alla vista
	 * @return			nome della vista da visualizzare
	 */
	@PostMapping(value = "/posts/{postId}/newcomment/save")
	public String saveComment (@ModelAttribute("comment") Comment comment, @PathVariable("username") String username,
			BindingResult br, Model uiModel) {
		logger.info("Saving a new comment...");
		
		try {
			this.commentService.update(comment);
			String strMessage = "Commento: " + comment.getTitle() + " salvato correttamente!";
			
			return "redirect:/" + username + "/posts/{postId}?message=" + strMessage;
			
		} catch (RuntimeException e) {
			
			return "redirect:/" + username + "/posts/{postId}?message=" + e.getMessage();
		}
	}
	
	
	/**
	 * Metodo per la richiesta GET di modifica ad un commento
	 * 
	 * @param commId	id del commento da modificare
	 * @param username	username dell'utente che sta richiedendo l'operazione
	 * @param uiModel	porzione del modello da passare alla vista
	 * @return			nome della vista da ritornare
	 */
	@GetMapping(value="/comments/edit/{commentId}")
	public String editComment(@PathVariable("commentId") long commId, @PathVariable("username") String username, Model uiModel) {
		logger.info(username + " :Editing a comment...");

		Comment c = this.commentService.findCommentById(commId);
		User u = this.userService.findUserByUsername(username);
		
		//TODO verificare se il controllo seguente può essere evolto con le funzioni di sicurezza
		
		if (c.getAuthor() == u) {
		uiModel.addAttribute("comment", c);
		
		return "comments/form";}
		
		else {
			String strMessage = "Non abilitato";
			return "redirect:/" + username + "/comments/?message=" + strMessage;
	}}	
	
	
	/**
	 * Metodo per la richiesta POST di salvataggio modifiche ad un commento
	 * 
	 * @param comment	commento modificato da rendere persistente
	 * @param username 	username dell'utente che ha richiesto l'operazione
	 * @param br		eventuali errori di validazione
	 * @param uiModel	porzione di modello da passare alla vista
	 * @return			redirect all'indirizzo cui fare richiesta
	 */
	@PostMapping(value = "/comments/edit/{commentId}/save")
	public String saveEditComment (@ModelAttribute("comment") Comment comment, @PathVariable("username") String username,
			BindingResult br, Model uiModel) {
		logger.info(username + "Saving the edited comment...");
		
		try {
			this.commentService.update(comment);
			String strMessage = "Commento: " + comment.getTitle() + " salvato correttamente!";
			
			return "redirect:/" + username + "/comments/?message=" + strMessage;
			
		} catch (RuntimeException e) {
			
			return "redirect:/" + username + "/comments/?message=" + e.getMessage();
		}
	}
	
	/**
	 * Metodo per la richiesta GET di eliminazione commento
	 * 
	 * @param id		id del commento da rimuovere
	 * @param uiModel	porzione del modello da passare alla vista
	 * @return			redirect all'indirizzo cui fare richiesta
	 */
	@GetMapping(value = "/comments/delete/{commentId}")
	public String deleteComment (/*@PathVariable("username") String username,*/ @PathVariable("commentId") long id, Model uiModel) {
		//logger.info(username + "Deleting a comment...");

		Comment c = this.commentService.findCommentById(id);
		this.commentService.delete(c);
		String message = "Commento: " + c.getTitle() + "eliminato correttamente!";
		return "redirect:/" + "comments?message=" + message;
	}
	
	
	/**
	 * Metodo per la richiesta GET di visualizzazione dettagli profilo
	 * 
	 * @param uiModel	porzione del modello da passare alla vista
	 * @return			nome della vista da renderizzare
	 */
	@GetMapping(value = "/profile")
	public String showUserProfile (Authentication auth, Model uiModel) {
		logger.info("Showing Profile...");
		
		if (auth != null) {
			User userLoggedIn = this.userService.findUserByUsername(auth.getName());
			uiModel.addAttribute("user", userLoggedIn);
		}
		
		else {
			String message = "Nessun utente autenticato";
			uiModel.addAttribute("message", message);
		}
		
		return "users.profile";
	}
	
	/**
	 * Metodo per la richiesta GET di modifica profilo utente
	 * 
	 * @param uiModel	porzione del modello da passare alla vista
	 * @return			nome della vista da renderizzare
	 */
	@GetMapping(value = "/profile/edit")
	public String editProfile (Authentication auth, Model uiModel) {
		
		
		if (auth != null) {
			User userLoggedIn = this.userService.findUserByUsername(auth.getName());
			uiModel.addAttribute("user", userLoggedIn);
		}
		
		else {
			String message = "Nessun utente autenticato";
			uiModel.addAttribute("message", message);
		}
		
		
		return "users.editProfile";
	}
	
	
	/**
	 * Metodo per al richiesta POST di salvataggio modifiche al profilo utente
	 * 
	 * @param profile	profilo utente modificato
	 * @param br		eventuali errori di validazione
	 * @param uiModel	porzione del modello da passare alla vista
	 * @return			redirect all'indirizzo cui fare richiesta
	 */
	@PostMapping(value = "/profile/edit/save", consumes = "multipart/form-data")
	public String saveProfile(@ModelAttribute("user") User profile, BindingResult br, Model uiModel,
							  @RequestParam("image") MultipartFile file) {
		logger.info("Saving the edited profile...");
		if (!file.isEmpty()) {
			String nameOfFile = null;
			try {
				String uploadsDir = "/WEB-INF/files/profile_pictures/";
				String realPathtoUploads = request.getServletContext().getRealPath(uploadsDir);
				if (!new java.io.File(realPathtoUploads).exists()) {
					logger.info("creating the directory...");
					if (!new java.io.File(realPathtoUploads).mkdir()) {
						String strMessage = "ERRORE, impossibile creare la cartella nel server!";
						return "redirect:/profile?message=" + strMessage;
					}
				}

				logger.info("realPathtoUploads = {}", realPathtoUploads);
				// rename uploaded file with the username
				String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
				nameOfFile = profile.getUsername() + "." + fileExtension;
				String filePath = realPathtoUploads + nameOfFile;
				java.io.File dest = new File(filePath);
				// controllo che sia un file immagine
				String mimetype = new MimetypesFileTypeMap().getContentType(dest);
				String type = mimetype.split("/")[0];
				if (!type.equals("image")) {
					String strMessage = "ERRORE, il file specificato non è un'immagine!";
					return "redirect:/profile?message=" + strMessage;
				}
				// sposto il file sulla cartella destinazione
				file.transferTo(dest);
			} catch (Exception e) {
				e.printStackTrace();
			}

			profile.setImageProfile(nameOfFile);
		}
			try {
				this.userService.update(profile); //todo verificare se rimane la vecchia immagine profilo
				String strMessage = "Il tuo profilo utente è stato salvato correttamente!";
				uiModel.addAttribute("message", strMessage);
				return "redirect:/profile?message=" + strMessage;
			} catch (RuntimeException e) {

				return "redirect:/profile?message=" + e.getMessage();

			}
		}
	}

	
	


