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
	
	 
	
	/**
	 * Setter per la dipendenza verso l'UserDao
	 * 
	 * @param userDao bean userDao
	 */
	
	@Autowired
	public void setUserRepository(UserDao userDao) {
		this.userRepository = userDao;
	}
	
	
	
	/**
	 * Metodo per trovare un utente dato il suo username
	 * 
	 * @param username nome dell'utente da ricercare
	 * @return  utente corrispondente al nome passato
	 */
	
	@Transactional(readOnly = true)
	@Override
	public User findUserByUsername (String username) {
		
		return this.userRepository.findUserByUsername(username);
	}
	
	/**
	 * Metodo per creare un nuovo utente
	 * 
	 * @param username username dell'utente da creare
	 * @param password password dell'utente da creare
	 * @param firstName nome dell'utente da creare
	 * @param lastName cognome dell'utente da creare
	 * @return utente creato
	 * 
	 */
	@Transactional
	@Override
	public User create(String username, String password, String firstName, String lastName) {
		
		return this.userRepository.create(username, password, firstName, lastName);
	}
	
	/**
	 * Metodo per creare un utente fornendo anche il nome dell'immagine profilo
	 * 
	 * @param username username dell'utente da creare
	 * @param password password dell'utente da creare
	 * @param firstName nome dell'utente da creare
	 * @param lastName cognome dell'utente da creare
	 * @param imgProfile nome del file contente l'immagine profilo dell'utente da creare
	 * @return utente creato
	 */
	@Transactional
	@Override
	public User create(String username, String password, String firstName, String lastName, String imgProfile) {
		
		User nu = this.userRepository.create(username, password, firstName, lastName);
		nu.setImageProfile(imgProfile);
		return nu;
	}
	
	/**
	 * Metodo per aggiornare un utente 
	 * 
	 * @param user utente da modificare
	 * @return utente modificato
	 */
	@Transactional
	@Override
	public User update(User user) {
		return this.userRepository.update(user);
	}
	
	
	/**
	 * Metodo per eliminare un utente
	 * 
	 * @param user utente da eliminare
	 * 
	 */	
	@Transactional
	@Override
	public void delete(User user) {
		this.userRepository.delete(user);
	}
	
	
	/**
	 * Metodo per trovare tutti gli utenti registrati
	 * 
	 * @return lista degli utenti registrati
	 */
	@Transactional(readOnly = true)
	@Override
	public List<User> findAll() {
		
		return this.userRepository.findAll();
	}
	
	
	/**
	 * Metodo per trovare tutti i post scritti da un utente, conoscendo l'username
	 * 
	 * @param username username dell'utente di cui cercare i post
	 * @return lista di post associati all'utente specificato
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Post> findPosts(String username) {
		
		
		User author = this.userRepository.findUserByUsername(username);
		if (author != null) {
			return this.userRepository.getSession().getNamedQuery("User.findPostsOfUser").setParameter(":username", username).getResultList();
		}
		
		else return Collections.emptyList(); 
		
	}
	
	


}
