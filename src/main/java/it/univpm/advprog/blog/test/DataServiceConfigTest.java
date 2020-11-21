package it.univpm.advprog.blog.test;

import it.univpm.advprog.blog.app.DataServiceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {"it.univpm.advprog.blog.model"})
@EnableTransactionManagement
public class DataServiceConfigTest extends DataServiceConfig {

    @Bean
    @Override
    protected Properties hibernateProperties() {
        Properties hibernateProp = super.hibernateProperties();
        hibernateProp.put("javax.persistence.schema-generation.database.action", "drop-and-create");
        return hibernateProp;
    }
}
