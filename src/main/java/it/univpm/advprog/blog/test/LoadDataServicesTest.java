package it.univpm.advprog.blog.test;

import it.univpm.advprog.blog.model.entities.*;
import it.univpm.advprog.blog.services.PostService;
import it.univpm.advprog.blog.services.TagService;
import it.univpm.advprog.blog.services.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LoadDataServicesTest {
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

    public static void main(String... args) {

        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
                DataServiceConfigTest.class)) {

            UserService userService = ctx.getBean("userService", UserService.class);
            TagService tagService = ctx.getBean("tagService", TagService.class);
            PostService postService = ctx.getBean("postService", PostService.class);

//**********************************************************************************************************************
            // CREAZIONE UTENTI
            User user1 = userService.create("mario98", "12345678", "Mario", "Rossi");

//**********************************************************************************************************************
            // CREAZIONE TAG
            Tag tag1 = tagService.create("Office 2021");

//**********************************************************************************************************************
            // CREAZIONE POST (implica: creazione automatica dell'archivio dal mese ed anno corrente)
            Post post1 = postService.create("titoloPost1", user1, SHORTDESCRIPTION, LONGDESCRIPTION, tag1);

        }
    }
}
