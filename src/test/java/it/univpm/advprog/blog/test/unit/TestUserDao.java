package it.univpm.advprog.blog.test.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import it.univpm.advprog.blog.model.dao.UserDao;
import it.univpm.advprog.blog.model.entities.User;
import it.univpm.advprog.blog.test.DataServiceConfigTest;

public class TestUserDao {

	private final static String SHORTDESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras " +
            "tempus magna vel posuere cursus. Sed ultricies nunc purus, et maximus eros accumsan sit amet. Donec " +
            "diam nisl, consectetur non nisl vel, condimentum finibus est.";
    private final static String LONGDESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras " +
            "tempus magna vel posuere cursus. Sed ultricies nunc purus, et maximus eros accumsan sit amet. Donec " +
            "diam nisl, consectetur non nisl vel, condimentum finibus est. Duis venenatis lectus et hendrerit " +
            "sollicitudin. Donec nisl felis, bibendum vitae placerat nec, dapibus iaculis enim. Integer rutrum " +
            "pulvinar nibh et dignissim. Vivamus porttitor dui ut ante iaculis interdum. Fusce vel purus libero. " +
            "Nunc luctus metus at tristique lobortis. Nulla egestas dictum bibendum. Suspendisse iaculis quis lorem " +
            "id scelerisque. Cras ac imperdiet metus. Donec lobortis vestibulum velit, feugiat interdum tellus " +
            "consectetur ac.";
    private final static String DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras " +
        "tempus magna vel posuere cursus.";
    
    @BeforeEach
   	void setUp() throws Exception {
   	}

   	@AfterEach
   	void tearDown() throws Exception {
   	}
   	
   	@Test
	public void noUsersAtBeginning() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			UserDao userDao=ctx.getBean("userDao",UserDao.class);
			Session s  = sf.openSession();
			userDao.setSession(s);
			assertEquals(userDao.findAll().size(), 0);
		}
	}
   	
   	@Test
   	public void testFindUserByUsername() {
   		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
   			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			UserDao userDao=ctx.getBean("userDao",UserDao.class);
			
			Session s=sf.openSession();
			userDao.setSession(s);
			
			s.beginTransaction();
			User user1 = userDao.create("mario98", "12345678", "Mario", "Rossi");
			User user2 = userDao.create("paolo97", "12345678", "Paolo", "Baggio");
			s.getTransaction().commit();
			
			try {
				assertEquals(userDao.findUserByUsername("mario98"),user1);
				assertEquals(userDao.findUserByUsername("paolo97"),user2);
			} catch(Exception e) {
				fail("Exception not excepted: "+e.getMessage());
			}
   		}
   	}
   	
   	@Test
   	public void createAndUpdate() {
   		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
   			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			UserDao userDao=ctx.getBean("userDao",UserDao.class);
			
			Session s=sf.openSession();
			userDao.setSession(s);

			
			s.beginTransaction();
			User user1 = userDao.create("mario98", "12345678", "Mario", "Rossi");
			s.getTransaction().commit();
			
			assertEquals(userDao.findAll().size(),1);
			
			s.beginTransaction();
			user1.setFirstName("Roberto");
			userDao.update(user1);
			s.getTransaction().commit();
			
			assertEquals(userDao.findAll().size(),1);
			assertEquals(user1.getFirstName(),"Roberto");
   		}
   	}
   	
   	@Test
   	public void createAndDelete() {
   		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
   			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			UserDao userDao=ctx.getBean("userDao",UserDao.class);
			
			Session s=sf.openSession();
			userDao.setSession(s);

			
			s.beginTransaction();
			User user1 = userDao.create("mario98", "12345678", "Mario", "Rossi");
			s.getTransaction().commit();
			
			assertEquals(userDao.findAll().size(),1);
			
			s.beginTransaction();
			userDao.delete(user1);
			s.getTransaction().commit();
			
			assertEquals(userDao.findAll().size(),0);
   		}
   	}
   	
}
