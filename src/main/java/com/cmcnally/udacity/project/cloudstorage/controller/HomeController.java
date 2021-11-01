package com.cmcnally.udacity.project.cloudstorage.controller;

import com.cmcnally.udacity.project.cloudstorage.model.File;
import com.cmcnally.udacity.project.cloudstorage.model.Note;
import com.cmcnally.udacity.project.cloudstorage.model.NoteForm;
import com.cmcnally.udacity.project.cloudstorage.services.AuthenticationService;
import com.cmcnally.udacity.project.cloudstorage.services.FileService;
import com.cmcnally.udacity.project.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

@Controller
@RequestMapping("/home")
public class HomeController {

    private NoteService noteService;
    private AuthenticationService authenticationService;
    private FileService fileService;

    public HomeController(NoteService noteService, AuthenticationService authenticationService, FileService fileService) {
        this.noteService = noteService;
        this.authenticationService = authenticationService;
        this.fileService = fileService;
    }

    // General GET Method for the home page
    @GetMapping
    public String getHomeView(@ModelAttribute("newNote") NoteForm noteForm, Model model){
        model.addAttribute("storedNotes", this.noteService.getStoredNotes());
        model.addAttribute("storedFiles", this.fileService.getStoredFiles());
        return "home";
    }

    /*
        Note methods
     */

    // POST Method to handle a user adding a new note or editing an existing one
    @PostMapping("/postNote")
    public String postNewNote(@ModelAttribute("newNote") NoteForm noteForm, Model model) {
        // if note id doesn't exist, then this is a new note request
        // else note already exists, then this is an edit note request
        if(noteForm.getFormNoteId() == null){
            // Create new note
            noteService.addNote(new Note(null, noteForm.getFormNoteTitle(), noteForm.getFormNoteDescription(), authenticationService.getUserId()));
        } else {
            // Update existing note
            noteService.editNote(noteForm.getFormNoteTitle(), noteForm.getFormNoteDescription(), noteForm.getFormNoteId());
        }
        model.addAttribute("storedNotes", this.noteService.getStoredNotes());
        noteForm.setFormNoteTitle("");
        noteForm.setFormNoteDescription("");
        return "redirect:/home";
    }

    // Get Method to handle a user deleting a note
    // Receives the id of the note to be deleted
    @GetMapping("/deleteNote")
    public String deleteNote(@RequestParam Integer noteid) {
        noteService.deleteNote(noteid);
        return "redirect:/home";
    }

    /*
        File methods
     */

    // POST method to handle a user uploading a file
    @PostMapping("/uploadFile")
    public String fileUpload(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        // Add new file via file service
        fileService.addFile(new File(null, file.getOriginalFilename(), file.getContentType(), String.valueOf(file.getSize()), authenticationService.getUserId(), file.getBytes()));
        model.addAttribute("storedFiles", this.fileService.getStoredFiles());
        return "redirect:/home";
    }

    // Get method to handle the user deleting a file
    // Receives the id of the file to be deleted
    @GetMapping("deleteFile")
    public String deleteFile(@RequestParam Integer fileId) {
        // Delete file via file service and redirect to home
        fileService.deleteFile(fileId);
        return "redirect:/home";
    }
}
