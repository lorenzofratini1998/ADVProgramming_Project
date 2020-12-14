package it.univpm.advprog.blog.model.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import it.univpm.advprog.blog.model.entities.Post;
import it.univpm.advprog.blog.model.entities.User;

@Repository("userDao")
public class UserDaoDefault extends DefaultDao implements UserDao {

	@Autowired
    private PasswordEncoder passwordEncoder;

    /**
	 * Metodo per trovare tutti gli utenti registrati
	 * 
	 * @return lista di utenti registrati
	 */
    @Override
    public List<User> findAll() {

        return getSession().
                createQuery("from User u", User.class).
                getResultList();
    }

    /**
     * Metodo per trovare un utente conoscendo il suo username
     * 
     * @param username username dell'utente da ricercare
     * @return utente corrispondente all'username specificato
     */
    @Override
    public User findUserByUsername(String username) {

        return this.getSession().find(User.class, username);
    }

    /**
     * Metodo per creare un nuovo utente
     * 
     * @param username username dell'utente da creare
	 * @param password password dell'utente da creare
	 * @param firstName nome dell'utente da creare
	 * @param lastName cognome dell'utente da creare
	 * 
     */
    @Override
    public User create(String username, String password, String firstName, String lastName) {

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);

        this.getSession().save(newUser);
        return newUser;
    }

    /**
     * Meetodo per aggiornare un utente 
     * 
     * @param user utente da modificare
     * @return utente modificato
     */
    @Override
    public User update(User user) {
        return (User) this.getSession().merge(user);
    }

    /**
     * Metodo per rimuovere un utente
     * 
     * @param utente da rimuovere
     * 
     */
    @Override
    public void delete(User user) {
        this.getSession().delete(user);
    }

    @Override
    public String encryptPassword(String password) {
        return this.passwordEncoder.encode(password);
    }

    @Override
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public PasswordEncoder getpasswordEncoder() {
        return this.passwordEncoder;
    }

}
