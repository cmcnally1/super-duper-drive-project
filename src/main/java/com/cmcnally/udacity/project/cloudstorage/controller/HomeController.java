package com.cmcnally.udacity.project.cloudstorage.controller;

import com.cmcnally.udacity.project.cloudstorage.model.Note;
import com.cmcnally.udacity.project.cloudstorage.model.NoteForm;
import com.cmcnally.udacity.project.cloudstorage.services.AuthenticationService;
import com.cmcnally.udacity.project.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private NoteService noteService;
    private AuthenticationService authenticationService;

    public HomeController(NoteService noteService, AuthenticationService authenticationService) {
        this.noteService = noteService;
        this.authenticationService = authenticationService;
    }

    // General GET Method for the home page
    @GetMapping
    public String getHomeView(@ModelAttribute("newNote") NoteForm noteForm, Model model){
        model.addAttribute("storedNotes", this.noteService.getStoredNotes());
        return "home";
    }

    // Get method for notes tab which gathers stored notes and displays them
//    @GetMapping("/home#nav-notes")
//    public String getHomeNotesView(@ModelAttribute("newNote") NoteForm noteForm, Model model) {
//        model.addAttribute("storedNotes", this.noteService.getStoredNotes());
//        return "home#nav-notes";
//    }

    // POST Method to handle a user adding a new note
    @PostMapping
    public String postNewNote(@ModelAttribute("newNote") NoteForm noteForm, Model model) {
        noteService.addNote(new Note(null, noteForm.getNewNoteTitle(), noteForm.getNewNoteDescription(), authenticationService.getUserId()));
        model.addAttribute("storedNotes", this.noteService.getStoredNotes());
        noteForm.setNewNoteTitle("");
        noteForm.setNewNoteDescription("");
        return "home";
    }
}
