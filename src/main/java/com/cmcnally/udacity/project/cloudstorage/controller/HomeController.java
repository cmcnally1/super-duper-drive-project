package com.cmcnally.udacity.project.cloudstorage.controller;

import com.cmcnally.udacity.project.cloudstorage.model.*;
import com.cmcnally.udacity.project.cloudstorage.services.AuthenticationService;
import com.cmcnally.udacity.project.cloudstorage.services.CredentialService;
import com.cmcnally.udacity.project.cloudstorage.services.FileService;
import com.cmcnally.udacity.project.cloudstorage.services.NoteService;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    private CredentialService credentialService;

    public HomeController(NoteService noteService, AuthenticationService authenticationService, FileService fileService, CredentialService credentialService) {
        this.noteService = noteService;
        this.authenticationService = authenticationService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    // General GET Method for the home page
    @GetMapping
    public String getHomeView(@ModelAttribute("newNote") NoteForm noteForm, @ModelAttribute("newCredential") CredentialForm credentialForm, Model model){
        model.addAttribute("storedNotes", this.noteService.getStoredNotes());
        model.addAttribute("storedFiles", this.fileService.getStoredFiles());
        model.addAttribute("storedCredentials", this.credentialService.getStoredCredentials());
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

    // Get method to handle the user downloading a file
    // Receives the id of the file to be downloaded
    @GetMapping("downloadFile")
    public ResponseEntity<byte[]> downloadFile(@RequestParam Integer fileId) {
        // Get the file from the database
        File fileToDownload = fileService.getFileById(fileId);

        // Set up HTTP headers for the response to the user
        HttpHeaders httpHeaders = new HttpHeaders();
        // Set the content type to be a stream of bytes
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        // Set the Content Disposition to indicate to the browser that this is a download with a specific file name
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.builder("attachment").filename(fileToDownload.getFilename()).build().toString());
        // Return the response to the user's browser with the file data
        return ResponseEntity.ok().headers(httpHeaders).body(fileToDownload.getFiledata());
    }

    /*
        Credential Methods
     */

    // Post method to handle the user adding a new credential
    @PostMapping("postCredential")
    public String postNewCredential(@ModelAttribute("newCredential") CredentialForm credentialForm, Model model) {
        // Add new credential to database and to model to be displayed to user
        credentialService.addCredential(new Credential(null, credentialForm.getFormCredentialUrl(), credentialForm.getFormCredentialUsername(), null, credentialForm.getFormCredentialPassword(), authenticationService.getUserId()));
        model.addAttribute("storedCredentials", this.credentialService.getStoredCredentials());
        // Clear the credential form
        credentialForm.setFormCredentialUrl("");
        credentialForm.setFormCredentialUsername("");
        credentialForm.setFormCredentialPassword("");
        return "redirect:/home";
    }
}
