package it.univpm.advprog.blog.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    /**
     * Metodo per gestire l'eccezione in caso di caricamento di un file di dimensioni maggiori del consentito.
     *
     * @return viene restituita la home con un messaggio
     */
    @ExceptionHandler(org.springframework.web.multipart.MaxUploadSizeExceededException.class)
    public String databaseError() {
        String message = "ERRORE, la dimensione massima del file deve essere di 1MB (MB non MiB!!).";
        return "redirect:/?errorMessage=" + message;
    }
}
