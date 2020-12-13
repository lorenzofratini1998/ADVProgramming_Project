package it.univpm.advprog.blog.controllers;

import it.univpm.advprog.blog.model.entities.Tag;
import it.univpm.advprog.blog.services.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class GuestController {
    private final Logger logger = LoggerFactory.getLogger(GuestController.class);
    private TagService tagService;

    @GetMapping(value = "/blog")
    public String home() {

        return "home";
    }

    /**
     * Setter per la proprietà riferita al Service dell'entità Tag.
     *
     * @param tagService Service dell'entità Tag da settare
     */
    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Metodo per la richiesta GET per la visualizzazione della lista di tags.
     *
     * @param message eventuale messaggio da mostrare
     * @param uiModel modello associato alla vista
     * @return nome della vista da visualizzare
     */
    @GetMapping(value = "/tags")
    public String showTags(@RequestParam(value = "message", required = false) String message, Model uiModel) {
        logger.info("Listing all the tags...");

        List<Tag> allTags = this.tagService.getAll();

        uiModel.addAttribute("tags", allTags);

        uiModel.addAttribute("message", message);

        return "tags.list";
    }
}
