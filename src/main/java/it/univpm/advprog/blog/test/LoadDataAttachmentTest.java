package it.univpm.advprog.blog.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import it.univpm.advprog.blog.model.dao.AttachmentDao;
import it.univpm.advprog.blog.model.dao.FileDao;
import it.univpm.advprog.blog.model.dao.LinkDao;

public class LoadDataAttachmentTest {

	public static void main(String ...args) {
		
		try(AnnotationConfigApplicationContext ctx= new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			
			AttachmentDao attachmentDao=ctx.getBean("attachmentDao",AttachmentDao.class);
			FileDao fileDao=ctx.getBean("fileDao",FileDao.class);
			LinkDao linkDao=ctx.getBean("linkDao",LinkDao.class);
			
			try (Session session=sf.openSession()) {
				
				attachmentDao.setSession(session);
				fileDao.setSession(session);
				linkDao.setSession(session);
				
				//fase 1: aggiunta al DB
				session.beginTransaction();
				attachmentDao.create(100, "Allegato di prova 1", true);
				session.getTransaction().commit();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
