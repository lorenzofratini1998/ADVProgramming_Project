package it.univpm.advprog.blog.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
	private LinkService linkService;
	private FileService fileService;
	private AttachmentService attachmentService;
	@Autowired
	private HttpServletRequest request;

	@Autowired
	public void setLogger(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	/**
	 * Setter per la proprietà riferita al Service dell'entità Link.
	 *
	 * @param linkService Service dell'entità Link da settare
	 */
	@Autowired
	public void setLinkService(LinkService linkService) {
		this.linkService = linkService;
	}

	/**
	 * Setter per la proprietà riferita al Service dell'entità File.
	 *
	 * @param fileService Service dell'entità File da settare
	 */
	@Autowired
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
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
	 * @param postService Service dell'entità Post da settare
	 */
	@Autowired
	public void setPostService(PostService postService) {
		this.postService = postService;
	}

	/**
	 * Setter per la proprietà commentService, relativa alla classe di servizio dei Commenti
	 *
	 * @param commentService Service dell'entità Comment da settare
	 */
	@Autowired
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	/**
	 * Setter per la proprietà userService, relativa alla classe di servizio degli Utenti
	 *
	 * @param userService Service dell'entità User da settare
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
	public String showMyPosts(Authentication authentication, Model uiModel,
							  @RequestParam(value="message", required = false) String message) {
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
		uiModel.addAttribute("message", message);

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
		uiModel.addAttribute("titlePageForm", "Inserisci un nuovo post");

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

			Post updated_post = this.postService.update(post);

			String strMessage = "Post \"" + updated_post.getTitle() + "\" salvato correttamente";
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
			List<String> postTags = new ArrayList<>();
//			//Da passare alla vista per rendere consistente la modifica
//	    	List<Comment> post_comments = (List<Comment>) post.getComments();
//	    	Set<Attachment> post_attachments = post.getAttachments();
	    	
			for (Tag tag:post.getTags()) {
				postTags.add(tag.getName());
			}
			
		
			uiModel.addAttribute("post", post);
			uiModel.addAttribute("allTags", tags);
			uiModel.addAttribute("postTags", postTags);
			uiModel.addAttribute("titlePageForm", "Modifica il post \"" + post.getTitle() + "\"");
			return "posts.edit";

		} else {

			String strMessage = "Non sei abilitato a modificare il post specificato.";
			return "redirect:/posts/?message=" + strMessage;

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
	 * Metodo per la richiesta GET della gestione degli allegati di un post.
	 *
	 * @param id ID del post
	 * @param uiModel modello associato alla vista
	 * @return nome della vista da visualizzare
	 */
	@GetMapping(value="/posts/{postId}/attachments")
	public String postAttachments(@PathVariable("postId") long id, Model uiModel) {
		Post post = postService.getById(id);

		// get all file attachments
		List<it.univpm.advprog.blog.model.entities.File> filesPost = new ArrayList<>();
		for (Attachment attachment:post.getAttachments()) {
			it.univpm.advprog.blog.model.entities.File file = fileService.getById(attachment.getId());
			if( file != null) {
				filesPost.add(file);
			}
		}

		// get all link attachments
		List<Link> linksPost = new ArrayList<>();
		for (Attachment attachment:post.getAttachments()) {
			Link link = linkService.getById(attachment.getId());
			if( link != null) {
				linksPost.add(link);
			}
		}

		uiModel.addAttribute("filesPost", filesPost);
		uiModel.addAttribute("linksPost", linksPost);
		uiModel.addAttribute("postID", id);

		return "post.attachments";
	}

	/**
	 * Metodo per la richiesta GET di creazione di un nuovo allegato di tipo link.
	 *
	 * @param postId ID del post
	 * @param uiModel modello associato alla vista
	 * @return nome della vista da visualizzare
	 */
	@GetMapping(value="/posts/edit/{postID}/attachments/link/new")
	public String newLink(@PathVariable("postID") long postId, Model uiModel) {

		uiModel.addAttribute("link", new Link());
		uiModel.addAttribute("postID", postId);

		return "link.new";
	}

	/**
	 * Metodo per la richiesta GET di creazione di un nuovo allegato di tipo file.
	 *
	 * @param postId ID del post
	 * @param uiModel modello associato alla vista
	 * @return nome della vista da visualizzare
	 */
	@GetMapping(value="/posts/edit/{postID}/attachments/file/new")
	public String newFile(@PathVariable("postID") long postId, Model uiModel) {

		uiModel.addAttribute("file", new it.univpm.advprog.blog.model.entities.File());
		uiModel.addAttribute("postID", postId);

		return "file.new";
	}

	/**
	 * Metodo per la richiesta GET di modifica di un link.
	 *
	 * @param postId ID del post
	 * @param linkId ID del link da modificare
	 * @param uiModel modello associato alla vista
	 * @return nome della vista da visualizzare
	 */
	@GetMapping(value="/posts/edit/{postID}/attachments/link/{linkID}/edit")
	public String editLink(@PathVariable("postID") long postId, @PathVariable("linkID") long linkId, Model uiModel) {

		uiModel.addAttribute("link", linkService.getById(linkId));
		uiModel.addAttribute("postID", postId);

		return "link.edit";
	}

	/**
	 * Metodo per la richiesta GET di modifica di un file.
	 *
	 * @param postId ID del post
	 * @param fileId ID del file da modificare
	 * @param uiModel modello associato alla vista
	 * @return nome della vista da visualizzare
	 */
	@GetMapping(value="/posts/edit/{postID}/attachments/file/{fileID}/edit")
	public String editFile(@PathVariable("postID") long postId, @PathVariable("fileID") long fileId, Model uiModel) {

		uiModel.addAttribute("file", fileService.getById(fileId));
		uiModel.addAttribute("postID", postId);

		return "file.edit";
	}

	/**
	 * Metodo per la richiesta POST di salvataggio di un link.
	 *
	 * @param link link da salvare
	 * @param postId ID del post
	 * @return nome della vista da visualizzare
	 */
	@PostMapping(value = "/posts/edit/{postID}/attachments/link/save")
	public String saveLink (@ModelAttribute("link") Link link, @PathVariable("postID") long postId) {
		logger.info("Saving a link attachment...");

		try {
			link.setPost(this.postService.getById(postId));
			this.linkService.update(link);
			String message = "Il link \"" + link.getDescription() + "\" è stato salvato correttamente!";

			return "redirect:" + "/posts/?message=" + message;

		} catch (RuntimeException e) {
			return "redirect:" + "/posts/?message=" + e.getMessage();
		}
	}

	/**
	 * Metodo per la richiesta POST di salvataggio di un nuovo file.
	 *
	 * @param newFile nuovo file
	 * @param postId ID del post
	 * @param uploadedFile file caricato nel form
	 * @return nome della vista da visualizzare
	 */
	@PostMapping(value = "/posts/edit/{postID}/attachments/file/new/save", consumes = "multipart/form-data")
	public String saveNewFile (@ModelAttribute("file") it.univpm.advprog.blog.model.entities.File newFile,
							   @PathVariable("postID") long postId, @RequestParam("fileAttachment") MultipartFile
									   uploadedFile) {
		logger.info("Saving the new file...");

		if (!uploadedFile.isEmpty()) {
			String nameOfFile = null;
			try {
				String uploadsDir = "/WEB-INF/files/post_attachments/";
				String realPathtoUploads = request.getServletContext().getRealPath(uploadsDir);
				if (!new java.io.File(realPathtoUploads).exists()) {
					logger.info("creating the directory...");
					if (!new java.io.File(realPathtoUploads).mkdir()) {
						String strMessage = "ERRORE, impossibile creare la cartella nel server!";
						return "redirect:/posts?message=" + strMessage;
					}
				}

				logger.info("realPathtoUploads = {}", realPathtoUploads);
				// rename uploaded file with the username
//					String fileExtension = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
				nameOfFile = "post" + postId + "_" + uploadedFile.getOriginalFilename();
				String filePath = realPathtoUploads + nameOfFile;
				java.io.File dest = new File(filePath);
				// sposto il file sulla cartella destinazione
				uploadedFile.transferTo(dest);
			} catch (Exception e) {
				e.printStackTrace();
			}

			newFile.setName(nameOfFile);
		} else {
			String message = "ERRORE, non hai caricato alcun file!";
			return "redirect:/posts?message=" + message;
		}

		try {
			newFile.setPost(postService.getById(postId));
			this.fileService.update(newFile);
			String strMessage = "Il file e' stato caricato correttamente!";
			return "redirect:/posts?message=" + strMessage;
		} catch (RuntimeException e) {

			return "redirect:/posts?message=" + e.getMessage();

		}
	}

	/**
	 * Metodo per la richiesta POST di salvataggio di un file modificato.
	 *
	 * @param file file da salvare
	 * @param postId ID del post
	 * @return nome della vista da visualizzare
	 */
	@PostMapping(value = "/posts/edit/{postID}/attachments/file/save")
	public String saveEditedFile (@ModelAttribute("file") it.univpm.advprog.blog.model.entities.File file,
								  @PathVariable("postID") long postId) {
		logger.info("Saving the edited file...");

		try {
			file.setPost(this.postService.getById(postId));
			this.fileService.update(file);
			String message = "Il file \"" + file.getDescription() + "\" è stato salvato correttamente!";

			return "redirect:" + "/posts/?message=" + message;

		} catch (RuntimeException e) {
			return "redirect:" + "/posts/?message=" + e.getMessage();
		}
	}

	/**
	 * Metodo per la richiesta GET di eliminazione di un allegato.
	 *
	 * @param id ID dell'allegato da eliminare
	 * @return nome della vista da visualizzare
	 */
	@GetMapping(value = "/attachments/{attachmentId}/delete")
	public String deleteAttachment (@PathVariable("attachmentId") long id) {
		logger.info("Deleting an attachment...");

		Attachment attachment = attachmentService.getById(id);
		this.attachmentService.delete(attachment);
		String message = "L'allegato \"" + attachment.getDescription() + "\" è stato eliminato correttamente!";
		return "redirect:" + "/posts/?message=" + message;
	}

	/**
	 * Metodo per la richiesta GET di visualizzazione dei commenti
	 *
	 * @param uiModel	porzione di modello da passare alla vista
	 * @return			nome della vista da renderizzare
	 */
	@GetMapping(value = "/comments")
	public String showComments(Authentication authentication, Model uiModel,
							   @RequestParam(value="message", required = false) String message) {
		logger.info("Showing your comments...");

		List<Comment> allComments = new ArrayList<>();

		if(authentication != null) {
			User currentLoggedInUser = userService.findUserByUsername(authentication.getName());
			allComments.addAll(currentLoggedInUser.getComments());
		}

		uiModel.addAttribute("comments", allComments);
		uiModel.addAttribute("numComments",allComments.size());
		uiModel.addAttribute("message", message);


		return "comments.list";
	}



	/**
	 * Metodo per la richiesta POST di salvataggio nuovo commento 
	 *
	 * @param comment	commento da rendere persistente
	 * @param authentication informazioni dell'autenticazione corrente
	 * @return			nome della vista da visualizzare
	 */
	@PostMapping(value = "/blog/post/{postID}/comment/new/save")
	public String saveComment (@ModelAttribute("comment") Comment comment, Authentication authentication,
							   @PathVariable("postID") long postId) {
		logger.info("Saving a new comment...");

		try {
			User author = userService.findUserByUsername(authentication.getName());
			comment.setAuthor(author);
			Post post = postService.getById(postId);
			comment.setPost(post);
			this.commentService.update(comment);
			String strMessage = "Il commento è stato salvato correttamente!";

			return "redirect:/blog/post/" + postId + "?message=" + strMessage;

		} catch (RuntimeException e) {

			return "redirect:/blog/post/" + postId + "?message=" + e.getMessage();
		}
	}


	/**
	 * Metodo per la richiesta GET di modifica ad un commento
	 *
	 * @param commId	id del commento da modificare
	 * @param uiModel	porzione del modello da passare alla vista
	 * @return			nome della vista da ritornare
	 */
	@GetMapping(value="/comments/edit/{commentId}")
	public String editComment(@PathVariable("commentId") long commId, Authentication auth, Model uiModel) {
		logger.info(auth.getName() + " :Editing a comment...");

		Comment c = this.commentService.findCommentById(commId);
		User u = this.userService.findUserByUsername(auth.getName());

		//TODO verificare se il controllo seguente può essere evolto con le funzioni di sicurezza

		if (c.getAuthor().getUsername().equals(u.getUsername())) {
			uiModel.addAttribute("comment", c);

			return "comments.editComment";}

		else {
			String strMessage = "Non abilitato";
			return "redirect:/" + u + "/comments/?message=" + strMessage;
		}}


	/**
	 * Metodo per la richiesta POST di salvataggio modifiche ad un commento
	 *
	 * @param auth informazioni dell'autenticazione
	 * @param commentAuthor username dell'autore del commento
	 * @param commentPostId ID del post a cui si riferisce il commento
	 * @param comment	commento modificato da rendere persistente
	 * @return			redirect all'indirizzo cui fare richiesta
	 */
	@PostMapping(value = "/comments/edit/{commentId}/save")
	public String saveEditComment (@ModelAttribute("comment") Comment comment, Authentication auth,
								   @RequestParam("post.id") long commentPostId,
								   @RequestParam("author.username") String commentAuthor) {
		logger.info(auth.getName() + "Saving the edited comment...");

		try {
			comment.setAuthor(this.userService.findUserByUsername(commentAuthor));
			comment.setPost(this.postService.getById(commentPostId));
			this.commentService.update(comment);
			String strMessage = "Commento: " + comment.getTitle() + " salvato correttamente!";

			return "redirect:/comments/?message=" + strMessage;

		} catch (RuntimeException e) {

			return "redirect:/comments/?message=" + e.getMessage();
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
	public String showUserProfile (Authentication auth, Model uiModel, @RequestParam(value = "message", required=false)  String message ) {
		logger.info("Showing Profile...");

		if (auth != null) {
			User userLoggedIn = this.userService.findUserByUsername(auth.getName());
			uiModel.addAttribute("user", userLoggedIn);
			uiModel.addAttribute("message", message);
			return "users.profile";
		}

		else {
			String noAuthMessage = "Nessun utente autenticato";
			return "redirect:/?message=" + noAuthMessage;
		}
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
			uiModel.addAttribute("userToEdit", userLoggedIn);
			return "users.editProfile";
		}

		else {
			String message = "Nessun utente autenticato";
			return "redirect:/?message=" + message;
		}

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
	public String saveProfile(@ModelAttribute("userToEdit") User profile, BindingResult br, Model uiModel,
							  @RequestParam("imageProfile") MultipartFile file) {
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
			this.userService.update(profile);
			String strMessage = "Il tuo profilo utente e' stato salvato correttamente!";
			return "redirect:/profile?message=" + strMessage;
		} catch (RuntimeException e) {

			return "redirect:/profile?message=" + e.getMessage();

		}
	}
}
