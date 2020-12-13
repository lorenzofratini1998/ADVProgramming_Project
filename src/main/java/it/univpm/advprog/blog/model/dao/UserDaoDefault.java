package it.univpm.advprog.blog.model.dao;


import java.util.List;

import org.springframework.stereotype.Repository;
import it.univpm.advprog.blog.model.entities.User;

@Repository("userDao")
public class UserDaoDefault extends DefaultDao implements UserDao {

    @Override
    public List<User> findAll() {

        return getSession().
                createQuery("from User u", User.class).
                getResultList();
    }

    @Override
    public User findUserByUsername(String username) {

        return this.getSession().find(User.class, username);
    }

    @Override
    public User create(String username, String password, String firstName, String lastName) {

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);

        this.getSession().save(newUser);
        return newUser;
    }

    @Override
    public User update(User user) {
        return (User) this.getSession().merge(user);
    }

    @Override
    public void delete(User user) {
        this.getSession().delete(user);
    }


}
