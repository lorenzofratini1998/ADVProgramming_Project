package it.univpm.advprog.blog.app;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {

	//Livello di configurazione principale
	@Override
	protected Class<?>[] getRootConfigClasses() {
		
		return new Class<?>[] {
			//WebSecurityConfig.class,
			DataServiceConfig.class
		};
	}

	//Livello di configurazione di singola servlet
	@Override
	protected Class<?>[] getServletConfigClasses() {
		
		return new Class<?>[] { WebConfig.class };
			
		
	}

	@Override
	protected String[] getServletMappings() {
		// as the <servlet-mapping>...</servlet-mapping> element in web.xml
		return new String[] {"/"};
	}
	
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter cef = new CharacterEncodingFilter();
		cef.setEncoding("UTF-8");
		cef.setForceEncoding(true);
		return new Filter[] { new HiddenHttpMethodFilter(), cef };
	}

}
