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

    public void addNote(Note note) {
        noteMapper.insert(note);
    }

    public List<Note> getStoredNotes() {
        return new ArrayList<>(noteMapper.getNotes(authenticationService.getUserId()));
    }

    // Method to delete a note using note id
    public void deleteNote(Integer noteid) {
        noteMapper.delete(noteid);
    }

}
