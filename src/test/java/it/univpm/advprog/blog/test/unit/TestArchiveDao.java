package it.univpm.advprog.blog.test.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import it.univpm.advprog.blog.model.dao.ArchiveDao;
import it.univpm.advprog.blog.model.entities.Archive;
import it.univpm.advprog.blog.test.DataServiceConfigTest;

public class TestArchiveDao {
	
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
   	public void noArchivesAtBeginning() {
   		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao",ArchiveDao.class);
			Session s  = sf.openSession();
			archiveDao.setSession(s);
			assertEquals(archiveDao.getAll().size(), 0);
   			}
   	}
   	
//   	//penso vada tolto perchè quando si modifica un archivio?
//   	@Test //TODO: sì, non ha senso... è da togliere il metodo update in ArchiveDao, ArchiveService e anche in TagDao, TagService
//   	public void createAndUpdate() {
//		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
//			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
//			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
//
//			Session s=sf.openSession();
//			archiveDao.setSession(s);
//
//			s.beginTransaction();
//			Archive archive1 = archiveDao.create("dicembre 2020");
//			s.getTransaction().commit();
//
//			assertEquals(archiveDao.getAll().size(),1);
//			assertEquals(archiveDao.getByName("dicembre 2020").getName(),"dicembre 2020");
//
//			s.beginTransaction();
//			archive1.setName("luglio 2021");
//			archiveDao.update(archive1);
//			s.getTransaction().commit();
//
//			assertEquals(archiveDao.getAll().size(),1);
//			assertEquals(archiveDao.getByName("luglio 2021").getName(),"luglio 2021");
//		}
//   	}
   	
   	@Test
   	public void createAndDeleteByArchive() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
			
			Session s=sf.openSession();
			archiveDao.setSession(s);
			
			s.beginTransaction();
			Archive archive1 = archiveDao.create("dicembre 2020");
			s.getTransaction().commit();
			
			assertEquals(archiveDao.getAll().size(),1);
			assertEquals(archiveDao.getByName("dicembre 2020").getName(),"dicembre 2020");
			
			s.beginTransaction();
			archiveDao.delete(archive1);
			s.getTransaction().commit();
			
			assertEquals(archiveDao.getAll().size(),0);
			assertNull(archiveDao.getByName("dicembre 2020"));
		}
   	}
   	
   	@Test
   	public void createAndDeleteByString() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			ArchiveDao archiveDao=ctx.getBean("archiveDao", ArchiveDao.class);
			
			Session s=sf.openSession();
			archiveDao.setSession(s);
			
			s.beginTransaction();
			Archive archive1 = archiveDao.create("dicembre 2020");
			s.getTransaction().commit();
			
			assertEquals(archiveDao.getAll().size(),1);
			assertEquals(archiveDao.getByName("dicembre 2020").getName(),"dicembre 2020");
			
			s.beginTransaction();
			archiveDao.delete("dicembre 2020");
			s.getTransaction().commit();
			
			assertEquals(archiveDao.getAll().size(),0);
			assertNull(archiveDao.getByName("dicembre 2020"));
		}
   	}

   	//TODO: inserire un test che mostri che NON è possibile inserire due archivi con lo stesso nome

	//TODO: inserire un test che mostri che NON è possibile cancellare un archivio che contiene un post
}
