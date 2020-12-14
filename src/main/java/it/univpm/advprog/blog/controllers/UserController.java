package it.univpm.advprog.blog.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.univpm.advprog.blog.model.entities.Comment;
import it.univpm.advprog.blog.model.entities.Post;
import it.univpm.advprog.blog.model.entities.User;
import it.univpm.advprog.blog.services.CommentService;
import it.univpm.advprog.blog.services.PostService;
import it.univpm.advprog.blog.services.UserService;

@Controller
public class UserController {
	
	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	private PostService postService;
	private CommentService commentService;
	private UserService userService;
	
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

		if(allPosts.isEmpty()) {
			String strMessage = "Non hai scritto alcun post!";
			return "redirect:/?message=" + strMessage ;
		}

		else {
			uiModel.addAttribute("posts", allPosts);
			uiModel.addAttribute("numPosts", allPosts.size());

			return "posts.list";}

	}
	
	
	/**
	 * Metodo per la richiesta GET di creazione nuovo post
	 * 
	 * @param username	nome dell'utente che crea il post
	 * @param uiModel	porzione di modello da passare alla vista
	 * @return			nome della vista da renderizzare
	 */
	@GetMapping(value = "/posts/new")
	public String newPost(@PathVariable("username") String username, Model uiModel) {
		logger.info(username + ":Creating a new post");

		uiModel.addAttribute("post", new Post());
		
		return "posts/form";
	}
	
	
	/**
	 * Metodo per la richiesta POST di salvataggio di un nuovo post
	 * 
	 * @param post		post da salvare ottenutod alla form
	 * @param username	username dell'utente che sta richiedendo l'operazione
	 * @param br		eventuali errori di validazione
	 * @param uiModel	porzione di modello da passare alla vista
	 * @return			redirect all'indirizzo cui fare richiesta
	 */
	@PostMapping(value="/posts/new/save")
	public String saveNewPost(@ModelAttribute("post") Post post, @PathVariable("username") String username,
			BindingResult br, Model uiModel) {
		logger.info(username + ":Saving a new post");

		
		try {
			
			this.postService.update(post);
			
			String strMessage = "Post (" + post.getTitle() + ") salvato correttamente";
			//uiModel.addAttribute("message", strMessage);
			
			return "redirect:/" + username + "/posts?message=" + strMessage;}
		
		catch (RuntimeException e) {
			
			return "redirect:/" + username + "/posts?message=" + e.getMessage();
		}
		
	}
	
	
	/**
	 * Metodo per la richiesta GET di modifica di un post
	 * 
	 * @param username	nome dell'utente che modifica il post
	 * @param postId	id del post da modificare
	 * @param uiModel	porzione di modello da passare alla vista
	 * @return			nome della vista da renderizzare
	 */
	@GetMapping(value="/posts/edit/{postId}")
	public String editPost(@PathVariable("username") String username, @PathVariable("postId") long postId, Model uiModel) {
		logger.info(username + ":Modifying a post");
	
		Post p = this.postService.getById(postId);
		User u = this.userService.findUserByUsername(username);
		
		//TODO verificare se il controllo seguente può essere evolto con le funzioni di sicurezza
		if (p.getAuthor() == u) {
		uiModel.addAttribute("post", p);
		
		return "posts/form";}
		
		else {
			String strMessage = "Non abilitato";
			return "redirect:/" + username + "/posts/list?message=" + strMessage;
	}}
	
	
	/**
	 * Metodo per la richiesta POST di salvataggio di un post modificato
	 * 
	 * @param post		post modificato da rendere persistente
	 * @param username	username dell'utente che sta richiedendo l'operazione
	 * @param br		eventuali errori di validazione
	 * @param uiModel	porzione di modello da passare alla vista
	 * @return			redirect all'indirizzo cui fare richiesta
	 */
	@PostMapping(value="/posts/edit/{postId}/save")
	public String saveEditPost(@ModelAttribute("post") Post post, @PathVariable("username") String username,
			BindingResult br, Model uiModel) {
		
			logger.info(username + ":Saving a modified post");

		
			this.postService.update(post);
			
			String strMessage = "Post (" + post.getTitle() + ") salvato correttamente";
			
			
			return "redirect:/" + username + "/posts?message=" + strMessage;
		
	}
	
	
	/**
	 * Metodo per la richiesta GET di eliminazione post
	 * 
	 * @param username 	username dell'utente che ha richiesto l'operazione
	 * @param id		id del commento da rimuovere
	 * @param uiModel	porzione del modello da passare alla vista
	 * @return			redirect all'indirizzo cui fare richiesta
	 */
	@GetMapping(value = "/posts/delete/{postId}")
	public String deletePost (@PathVariable("username") String username, @PathVariable("postId") long id, Model uiModel) {
		logger.info(username + "Deleting a post...");

		Post p = this.postService.getById(id);
		this.postService.delete(p);
		String message = "Post" + p.getTitle() + "eliminato correttamente!";
		return "redirect:" + username + "/comments/?message=" + message; 
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
	
		
		return "comments/list";
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
	 * @param 			username username dell'utente che ha richiesto l'operazione
	 * @param id		id del commento da rimuovere
	 * @param uiModel	porzione del modello da passare alla vista
	 * @return			redirect all'indirizzo cui fare richiesta
	 */
	@GetMapping(value = "/comments/delete/{commentId}")
	public String deleteComment (@PathVariable("username") String username, @PathVariable("commentId") long id, Model uiModel) {
		logger.info(username + "Deleting a comment...");

		Comment c = this.commentService.findCommentById(id);
		this.commentService.delete(c);
		String message = "Commento" + c.getTitle() + "eliminato correttamente!";
		return "redirect:/" + username + "/comments/?message=" + message; 
	}
	
	
	/**
	 * Metodo per la richiesta GET di visualizzazione dettagli profilo
	 * 
	 * @param username	username dell'utente che sta richiedendo l'operazione
	 * @param uiModel	porzione del modello da passare alla vista
	 * @return			nome della vista da renderizzare
	 */
	@GetMapping(value = "/profile")
	public String showUserProfile (@PathVariable("username") String username, Model uiModel) {
		logger.info(username + ": Showing Profile...");

		User u = this.userService.findUserByUsername(username);
		uiModel.addAttribute("user", u);
		
		return "profile";
	}
	
	/**
	 * Metodo per la richiesta GET di modifica profilo utente
	 * 
	 * @param username	username dell'utente che sta richiedendo l'operazione
	 * @param uiModel	porzione del modello da passare alla vista
	 * @return			nome della vista da renderizzare
	 */
	@GetMapping(value = "/profile/edit")
	public String editProfile (@PathVariable("username") String username, Model uiModel) {
		
		User u = this.userService.findUserByUsername(username);
		
		uiModel.addAttribute("username", u);
		
		return "/profile/form";
	}
	
	
	/**
	 * Metodo per al richiesta POST di salvataggio modifiche al profilo utente
	 * 
	 * @param profile	profilo utente modificato
	 * @param br		eventuali errori di validazione
	 * @param uiModel	porzione del modello da passare alla vista
	 * @return			redirect all'indirizzo cui fare richiesta
	 */
	@PostMapping(value = "/profile/edit/save")
	public String saveProfile(@ModelAttribute("userProfile") User profile, BindingResult br, Model uiModel) {
		logger.info("Saving the edited profile...");
		String username = profile.getUsername();
		
		try {
			this.userService.update(profile);
			String strMessage = "Il tuo profilo utente è stato salvato correttamente!";
			
			return "redirect:" + username + "/profile?message=" + strMessage;
		}
		catch (RuntimeException e) {
			
			return "redirect:" + username + "/profile?message=" + e.getMessage();
			
		}
		
	}
	
	
	
	
		
		
	
	
	
}
	
	


