package com.cmcnally.udacity.project.cloudstorage.services;

import com.cmcnally.udacity.project.cloudstorage.mapper.NoteMapper;
import com.cmcnally.udacity.project.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

    private List<Note> storedNotes;
    private NoteMapper noteMapper;
    private AuthenticationService authenticationService;

    public NoteService(List<Note> storedNotes, NoteMapper noteMapper, AuthenticationService authenticationService) {
        this.storedNotes = storedNotes;
        this.noteMapper = noteMapper;
        this.authenticationService = authenticationService;
    }

    public int addNote(Note note) {
        // Check size of the note description and title.
        // If it does not exceed max length, add note.
        // else return error.
        if (note.getNotedescription().length() <= 1000 && note.getNotetitle().length() <= 20) {
            return noteMapper.insert(note);
        } else if (note.getNotetitle().length() > 20){
            // return title error
            return -2;
        } else {
            // return description error
            return -1;
        }
    }

    public List<Note> getStoredNotes() {
        return new ArrayList<>(noteMapper.getNotes(authenticationService.getUserId()));
    }

    // Method to delete a note using note id
    public int deleteNote(Integer noteid) {
        // Get logged in user's notes
        List<Note> authUserNotes = this.getStoredNotes();

        // Search the user's notes to ensure the requested note to be deleted
        // belongs to the logged in user. If not, do nothing and return -1 to inform user.
        for(int i = 0; i < authUserNotes.size(); i++) {
            if(authUserNotes.get(i).getNoteid() == noteid) {
                return noteMapper.delete(noteid);
            }
        }

        // User in home controller to return specific error message to user
        return -1;
    }

    // Method to update a note using the user inputted edits and the note id
    public int editNote(String notetitle, String notedescription, Integer noteid) {
        // Check size of the note description and title.
        // If it does not exceed max length, add note.
        // else return error.
        if (notedescription.length() <= 1000 && notetitle.length() <= 20) {
            return noteMapper.update(notetitle, notedescription, noteid);
        } else if (notetitle.length() > 20){
            // return title error
            return -2;
        } else {
            // return description error
            return -1;
        }

    }

}
