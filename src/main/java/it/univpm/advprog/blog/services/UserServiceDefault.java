package it.univpm.advprog.blog.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univpm.advprog.blog.model.dao.PostDao;
import it.univpm.advprog.blog.model.dao.UserDao;
import it.univpm.advprog.blog.model.entities.Post;
import it.univpm.advprog.blog.model.entities.User;

@Transactional
@Service("userService")
public class UserServiceDefault implements UserService{
	
	private UserDao userRepository;
	
	 
	
	//Dipendenze con UserDao e PostDao
	
	@Autowired
	public void setUserRepository(UserDao userDao) {
		this.userRepository = userDao;
	}
	
	
	
	//Funzione per trovare un utente in base al suo username
	
	@Transactional(readOnly = true)
	@Override
	public User findUserByUsername (String username) {
		
		return this.userRepository.findUserByUsername(username);
	}
	
	//Funzione per creare un utente
	
	@Transactional
	@Override
	public User create(String username, String password, String firstName, String lastName) {
		
		return this.userRepository.create(username, password, firstName, lastName);
	}
	
	//Funzione per creare un utente fornendo anche il path che rimanda all'immagine di profilo
	
	
	@Transactional
	@Override
	public User create(String username, String password, String firstName, String lastName, String imgProfile) {
		
		User nu = this.userRepository.create(username, password, firstName, lastName);
		nu.setImageProfile(imgProfile);
		return nu;
	}
	
	//Funzione per aggiornare un utente
	
	@Transactional(readOnly = true)
	@Override
	public User update(User user) {
		return this.userRepository.update(user);
	}
	
	
	//Funzione per eliminare un utente
	
	@Transactional(readOnly = true)
	@Override
	public void delete(User user) {
		this.userRepository.delete(user);
	}
	
	
	//Funzione per trovare tutti gli utenti
	
	@Transactional(readOnly = true)
	@Override
	public List<User> findAll() {
		
		return this.userRepository.findAll();
	}
	
	
	//Funzione per trovare tutti i post scritti da un utente, conoscendo l'username
	
	@Override
	public List<Post> findPosts(String username) {
		
		
		User author = this.userRepository.findUserByUsername(username);
		if (author != null) {
			return this.userRepository.getSession().getNamedQuery("User.findPostsOfUser").setParameter(":username", username).getResultList();
		}
		
		else return Collections.emptyList(); 
		
	}

}
