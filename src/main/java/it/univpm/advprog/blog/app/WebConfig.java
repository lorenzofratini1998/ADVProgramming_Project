package it.univpm.advprog.blog.app;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.theme.CookieThemeResolver;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

import it.univpm.advprog.blog.test.DataServiceConfigTest;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "it.univpm.advprog.blog" },
		excludeFilters  = {@ComponentScan.Filter(
		type = FilterType.ASSIGNABLE_TYPE, classes = {it.univpm.advprog.blog.test.DataServiceConfigTest.class})})

public class WebConfig implements WebMvcConfigurer {

	
	@Bean
	public String appName() {
		return "Blog App";
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/css/")
				.setCachePeriod(31556926);
		registry.addResourceHandler("/media/**").addResourceLocations("/WEB-INF/media/")
		.setCachePeriod(31556926);
		registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/js/")
		.setCachePeriod(31556926);
	}
	
	@Bean 
	StandardServletMultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
	
	//Bean che carica la definizione delle viste sotto forma di tiles
	@Bean
	UrlBasedViewResolver tilesViewResolver() {
		UrlBasedViewResolver tilesViewResolver = new UrlBasedViewResolver(); 
		tilesViewResolver.setViewClass(TilesView.class);
		return tilesViewResolver;
	}
	
	//TODO decommentare quando avremo definito il layout
	
	//Configuratore dei tiles, necessario per il precedente
	@Bean
	TilesConfigurer tilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();

		//COnfigurazione dei file.xml che contengono la deinizione delle viste
		tilesConfigurer.setDefinitions(
				"/WEB-INF/layouts/layouts.xml",
				"/WEB-INF/views/views.xml",
				"/WEB-INF/views/**/views.xml" );

		tilesConfigurer.setCheckRefresh(true);
		return tilesConfigurer;
	}
	
	//Formattatore per le date
	@Bean
	public DateFormatter dateFormatter() {
		return new DateFormatter("dd/MM/YYYY");
	}
	
	

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
		registry.addInterceptor(webChangeInterceptor());
	}
	
	@Bean
	LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("lang");
		return interceptor;
	}

	
	//Componente che va a settare il Locale di default
			@Bean
			CookieLocaleResolver localeResolver() {
				CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
				cookieLocaleResolver.setDefaultLocale(Locale.ITALIAN);
				cookieLocaleResolver.setCookieMaxAge(3600);
				cookieLocaleResolver.setCookieName("locale"); 
				return cookieLocaleResolver;
			}
			
	
	//TODO da completare eventualmente la parte sulla risoluzione dei temi

	@Bean ResourceBundleThemeSource themeSource() {
		return new ResourceBundleThemeSource();
	}

	
		
		 
		@Bean
		WebContentInterceptor webChangeInterceptor() {
			// allow/disallow handling of http methods; prepare the request
			WebContentInterceptor webContentInterceptor = new WebContentInterceptor();
			webContentInterceptor.setCacheSeconds(0);
			webContentInterceptor.setSupportedMethods("GET", "POST", "PUT", "DELETE");
			return webContentInterceptor;
		}

		

		
		//TODO da decidere se inserirli all'occorrenza quando verranno fatte le viste

//		// <=> <mvc:default-servlet-handler/>
//		@Override
//		public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//			configurer.enable();
//		}

		// <=> <mvc:view-controller .../>
		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
			registry.addRedirectViewController("/", "/blog");
		}
		}


	

