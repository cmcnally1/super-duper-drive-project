package com.cmcnally.udacity.project.cloudstorage.model;

/*
    Model for the note creation form
 */
public class NoteForm {

    private String newNoteTitle;
    private String newNoteDescription;

    public String getNewNoteTitle() {
        return newNoteTitle;
    }

    public void setNewNoteTitle(String newNoteTitle) {
        this.newNoteTitle = newNoteTitle;
    }

    public String getNewNoteDescription() {
        return newNoteDescription;
    }

    public void setNewNoteDescription(String newNoteDescription) {
        this.newNoteDescription = newNoteDescription;
    }
}
