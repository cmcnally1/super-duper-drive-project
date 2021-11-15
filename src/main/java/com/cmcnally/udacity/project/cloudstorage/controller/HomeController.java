package com.cmcnally.udacity.project.cloudstorage.controller;

import com.cmcnally.udacity.project.cloudstorage.model.*;
import com.cmcnally.udacity.project.cloudstorage.services.AuthenticationService;
import com.cmcnally.udacity.project.cloudstorage.services.CredentialService;
import com.cmcnally.udacity.project.cloudstorage.services.FileService;
import com.cmcnally.udacity.project.cloudstorage.services.NoteService;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController {

    private NoteService noteService;
    private AuthenticationService authenticationService;
    private FileService fileService;
    private CredentialService credentialService;

    private String messageToShow = "";

    public HomeController(NoteService noteService, AuthenticationService authenticationService, FileService fileService, CredentialService credentialService) {
        this.noteService = noteService;
        this.authenticationService = authenticationService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    // General GET Method for the home page
    @GetMapping
    public String getHomeView(@ModelAttribute("newNote") NoteForm noteForm, @ModelAttribute("newCredential") CredentialForm credentialForm, Model model){

        // Check if there is an alert box to show to the user
        switch (messageToShow) {
            case "NoteAdd":
                model.addAttribute("noteAddSuccess", true);
                messageToShow = "";
                break;
            case "NoteUpdate":
                model.addAttribute("noteUpdateSuccess", true);
                messageToShow = "";
                break;
            case "NoteDelete":
                model.addAttribute("noteDeleteSuccess", true);
                messageToShow = "";
                break;
            case "CredAdd":
                model.addAttribute("credAddSuccess", true);
                messageToShow = "";
                break;
            case "CredUpdate":
                model.addAttribute("credUpdateSuccess", true);
                messageToShow = "";
                break;
            case "CredDelete":
                model.addAttribute("credDeleteSuccess", true);
                messageToShow = "";
                break;
            case "FileAdd":
                model.addAttribute("fileAddSuccess", true);
                messageToShow = "";
                break;
            case "FileDelete":
                model.addAttribute("fileDeleteSuccess", true);
                messageToShow = "";
                break;
            case "FileEmpty":
                model.addAttribute("fileEmpty", true);
                messageToShow = "";
                break;
            case "NoteError":
                model.addAttribute("noteError", true);
                messageToShow = "";
                break;
            case "WrongUserNote":
                model.addAttribute("wrongUserNote", true);
                messageToShow = "";
                break;
            case "CredError":
                model.addAttribute("credError", true);
                messageToShow = "";
                break;
            case "WrongUserCred":
                model.addAttribute("wrongUserCred", true);
                messageToShow = "";
                break;
            case "FileError":
                model.addAttribute("fileError", true);
                messageToShow = "";
                break;
            case "WrongUserFile":
                model.addAttribute("wrongUserFile", true);
                messageToShow = "";
                break;
            case "FileLarge":
                model.addAttribute("fileTooLarge", true);
                messageToShow = "";
                break;
            case "NoteLarge":
                model.addAttribute("noteTooLarge", true);
                messageToShow = "";
                break;
        }

        // Try retrieve data for user. Catch if there is no authorised user (null) and return login page
        try {
            // Add data to display to the user in the view
            model.addAttribute("storedNotes", this.noteService.getStoredNotes());
            model.addAttribute("storedFiles", this.fileService.getStoredFiles());
            model.addAttribute("storedCredentials", this.credentialService.getStoredCredentials());
            model.addAttribute("decryptedPasswords", this.credentialService.getDecryptedPasswords());
        } catch (NullPointerException e) {
            return "login";
        }
        return "home";
    }

    /*
        Note methods
     */

    // POST Method to handle a user adding a new note or editing an existing one
    @PostMapping("/postNote")
    public String postNewNote(@ModelAttribute("newNote") NoteForm noteForm, Model model) {
        // Variable to hold the number of rows update/created in database
        int rowsUpdate = 0;
        // if note id doesn't exist, then this is a new note request
        // else note already exists, then this is an edit note request
        if(noteForm.getFormNoteId() == null){
            // Create new note and return number of rows update in database
            rowsUpdate = noteService.addNote(new Note(null, noteForm.getFormNoteTitle(), noteForm.getFormNoteDescription(), authenticationService.getUserId()));
            // If rows have been update/created, then show updated notes to user
            if (rowsUpdate > 0){
                // Set message to show add note success
                messageToShow = "NoteAdd";
            } else if(rowsUpdate == -1) {
                // Set message to show if note too large
                messageToShow = "NoteLarge";
            } else {
                messageToShow = "NoteError";
            }

        } else {
            // Update existing note and return number of rows update in database
            rowsUpdate = noteService.editNote(noteForm.getFormNoteTitle(), noteForm.getFormNoteDescription(), noteForm.getFormNoteId());
            // If rows have been update/created, then show updated notes to user
            if (rowsUpdate > 0){
                // Set message to show update note success
                messageToShow = "NoteUpdate";
            } else {
                messageToShow = "NoteError";
            }
        }
        // Reset Note form
        noteForm.setFormNoteTitle("");
        noteForm.setFormNoteDescription("");
        return "redirect:/home";
    }

    // Get Method to handle a user deleting a note
    // Receives the id of the note to be deleted
    @GetMapping("/deleteNote")
    public String deleteNote(@RequestParam Integer noteid) {
        // Delete the user note and return rows updated.
        int rowUpdate = noteService.deleteNote(noteid);
        // Set message to show to user
        if (rowUpdate > 0) {
            // Delete note success
            messageToShow = "NoteDelete";
        } else if (rowUpdate < 0) {
            // Trying to delete other user's note
            messageToShow = "WrongUserNote";
        } else {
            // Note error
            messageToShow = "NoteError";
        }

        return "redirect:/home";
    }

    /*
        File methods
     */

    // POST method to handle a user uploading a file
    @PostMapping("/uploadFile")
    public String fileUpload(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        // Variable to hold number of database rows added
        int rowsUpdate = 0;
        // Check if file uploaded is empty
        if(file.isEmpty()){
            // if file is empty, display an alert to user
            messageToShow = "FileEmpty";
        } else if (file.getSize() > 31457280) {
            // If file is greater than 30 MB, display file too large message
            messageToShow = "FileLarge";
        } else {

            file.getSize();
            // if file is not empty, proceed to store file in database.
            // Add new file via file service
            rowsUpdate = fileService.addFile(new File(null, file.getOriginalFilename(), file.getContentType(), String.valueOf(file.getSize()), authenticationService.getUserId(), file.getBytes()));
            // If rows have been update/created, then show updated notes to user
            if (rowsUpdate > 0){
                // Set message to show upload file success
                messageToShow = "FileAdd";
            } else {
                // Show file error message to user
                messageToShow = "FileError";
            }
        }
        return "redirect:/home";
    }

    // Get method to handle the user deleting a file
    // Receives the id of the file to be deleted
    @GetMapping("deleteFile")
    public String deleteFile(@RequestParam Integer fileId) {
        // Delete file via file service and redirect to home
        int rowUpdate = fileService.deleteFile(fileId);
        // Set message to show to user
        if (rowUpdate > 0) {
            // Set message to show delete file success
            messageToShow = "FileDelete";
        } else if (rowUpdate < 0) {
            // Trying to delete other user's file
            messageToShow = "WrongUserFile";
        } else {
            // File error
            messageToShow = "FileError";
        }
        return "redirect:/home";
    }

    // Get method to handle the user downloading a file
    // Receives the id of the file to be downloaded
    @GetMapping("downloadFile")
    public ResponseEntity downloadFile(@RequestParam Integer fileId) {

        try {
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
        } catch (Exception e) {
            // Return forbidden message to user
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ACCESS DENIED: You do not have access to this file.");

        }
    }

    /*
        Credential Methods
     */

    // Post method to handle the user adding a new credential
    @PostMapping("postCredential")
    public String postNewCredential(@ModelAttribute("newCredential") CredentialForm credentialForm, Model model) {

        // Variable to hold number of database rows affected by action
        int rowsUpdate = 0;

        // if credential id doesn't exist, then this is a new credential request
        // else credential already exists, then this is an edit credential request
        if(credentialForm.getFormCredentialId() == null){
            // Add new credential to database
            rowsUpdate = credentialService.addCredential(new Credential(null, credentialForm.getFormCredentialUrl(), credentialForm.getFormCredentialUsername(), null, credentialForm.getFormCredentialPassword(), authenticationService.getUserId()));
            // If rows have been update/created, otherwise show error
            if (rowsUpdate > 0){
                // Set message to show add credential success
                messageToShow = "CredAdd";
            } else {
                messageToShow = "CredError";
            }
        } else {
            // Update existing credential
            rowsUpdate = credentialService.editCredential(credentialForm.getFormCredentialUrl(), credentialForm.getFormCredentialUsername(), credentialForm.getFormCredentialPassword(), credentialForm.getFormCredentialId());
            // If rows have been update/created, otherwise show error
            if (rowsUpdate > 0){
                // Set message to show update credential success
                messageToShow = "CredUpdate";
            } else {
                messageToShow = "CredError";
            }
        }
        // Clear the credential form
        credentialForm.setFormCredentialUrl("");
        credentialForm.setFormCredentialUsername("");
        credentialForm.setFormCredentialPassword("");
        return "redirect:/home";
    }

    // Get method to handle the user deleting a credential
    // Receives the id of the file to be deleted
    @GetMapping("deleteCredential")
    public String deleteCredential(@RequestParam Integer credentialid) {
        // Delete the file that matches the id via the credential service
        int rowUpdate = credentialService.deleteCredential(credentialid);

        // Set message to show to user
        if (rowUpdate > 0) {
            // Set message to show delete credential success
            messageToShow = "CredDelete";
        } else if (rowUpdate < 0) {
            // Trying to delete other user's credential
            messageToShow = "WrongUserCred";
        } else {
            // Credential error
            messageToShow = "CredError";
        }

        return "redirect:/home";
    }
}
