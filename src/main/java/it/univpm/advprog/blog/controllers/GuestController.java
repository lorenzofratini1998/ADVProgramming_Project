package it.univpm.advprog.blog.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class GuestController {
    private final Logger logger = LoggerFactory.getLogger(GuestController.class);

    @GetMapping(value = "/blog")
    public String home() {

        return "home";
    }
}
