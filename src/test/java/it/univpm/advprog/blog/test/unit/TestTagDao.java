package it.univpm.advprog.blog.test.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import it.univpm.advprog.blog.model.dao.ArchiveDao;
import it.univpm.advprog.blog.model.dao.TagDao;
import it.univpm.advprog.blog.model.entities.Archive;
import it.univpm.advprog.blog.model.entities.Tag;
import it.univpm.advprog.blog.test.DataServiceConfigTest;

public class TestTagDao {
	
    @BeforeEach
   	void setUp() throws Exception {
   	}

   	@AfterEach
   	void tearDown() throws Exception {
   	}
   	
   	@Test
   	public void noTagsAtBeginning() {
   		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			TagDao tagDao=ctx.getBean("tagDao",TagDao.class);
			Session s  = sf.openSession();
			tagDao.setSession(s);
			assertEquals(tagDao.getAll().size(), 0);
   			}
   	} 
   	
   	@Test
   	public void testGetByName() {
   		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
   			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			TagDao tagDao=ctx.getBean("tagDao",TagDao.class);

			
			Session s=sf.openSession();
			tagDao.setSession(s);
			
			s.beginTransaction();
			Tag tag1 = tagDao.create("Office 2021");
			s.getTransaction().commit();
			
			try {
				assertEquals(tagDao.getAll().size(),1);
				assertEquals(tagDao.getByName("Office 2021"),tag1);
			} catch(Exception e) {
				fail("Exception not excepted: "+e.getMessage());
			}
   		}
   	}
   	
   	@Test
   	public void createAndDeleteByTag() {
   		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			TagDao tagDao=ctx.getBean("tagDao", TagDao.class);
			
			Session s=sf.openSession();
			tagDao.setSession(s);
			
			s.beginTransaction();
			Tag tag1 = tagDao.create("Office 2021");
			s.getTransaction().commit();
			
			assertEquals(tagDao.getAll().size(),1);
			assertEquals(tagDao.getByName("Office 2021").getName(),"Office 2021");
			
			s.beginTransaction();
			tagDao.delete(tag1);
			s.getTransaction().commit();
			
			assertEquals(tagDao.getAll().size(),0);
			assertNull(tagDao.getByName("Office 2021"));
		}
   	}
   	
   	@Test
   	public void createAndDeleteByString() {
   		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			TagDao tagDao=ctx.getBean("tagDao", TagDao.class);
			
			Session s=sf.openSession();
			tagDao.setSession(s);
			
			s.beginTransaction();
			Tag tag1 = tagDao.create("Office 2021");
			s.getTransaction().commit();
			
			assertEquals(tagDao.getAll().size(),1);
			assertEquals(tagDao.getByName("Office 2021").getName(),"Office 2021");
			
			s.beginTransaction();
			tagDao.delete("Office 2021");
			s.getTransaction().commit();
			
			assertEquals(tagDao.getAll().size(),0);
			assertNull(tagDao.getByName("Office 2021"));
		}
   	}

}
