package it.univpm.advprog.blog.security;

import it.univpm.advprog.blog.services.UserServiceDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserServiceDefault();
    }

    /**
     * Configurazione delle autenticazioni.
     *
     * @param auth AuthenticationManagerBuilder
     * @param passwordEncoder PasswordEncoder
     * @throws Exception eventuale eccezione generata
     */
    @Autowired
    public void configure(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {

        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder);
    }

    /**
     * Configurazione delle autorizzazioni.
     *
     * @param http HttpSecurity
     * @throws Exception eventuale eccezione generata
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().
                antMatchers("/login").permitAll().
                antMatchers("/").permitAll().
                antMatchers("/tags").permitAll().
                antMatchers("/archives").permitAll().
                antMatchers("/tags/?**").hasAnyRole("admin").
                antMatchers("/profile/**").hasAnyRole("user","admin").
                antMatchers("/attachments/**").hasAnyRole("admin").
                antMatchers("/posts/manage/**").hasAnyRole("admin").
                antMatchers("/posts/**").hasAnyRole("user", "admin").
                antMatchers("/comments/manage/**").hasAnyRole("admin").
                antMatchers("/comments/**").hasAnyRole("user", "admin").
                and().formLogin().loginPage("/login").defaultSuccessUrl("/")
                .failureUrl("/login?error=true").permitAll().
                and().logout().logoutSuccessUrl("/")
                .invalidateHttpSession(true).permitAll().
                and().csrf().disable();
    }
}