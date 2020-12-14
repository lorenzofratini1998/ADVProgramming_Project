package it.univpm.advprog.blog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univpm.advprog.blog.model.dao.UserDao;
import it.univpm.advprog.blog.model.entities.User;

@Service("userService")
public class UserServiceDefault implements UserService, UserDetailsService {

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

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByUsername(username);
		org.springframework.security.core.userdetails.User.UserBuilder builder;

		if (user != null) {
			builder = org.springframework.security.core.userdetails.User.withUsername(username);
			builder.disabled(user.isDisabled());
			builder.password(user.getPassword());
			String role = "user";
			if (user.isAdmin()) {
				role = "admin";
			}
			builder.roles(role);
		} else {
			throw new UsernameNotFoundException("User not found.");
		}
		return builder.build();
	}
}
