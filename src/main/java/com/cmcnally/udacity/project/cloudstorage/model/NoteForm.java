package com.cmcnally.udacity.project.cloudstorage.model;

/*
    Model for the note creation form
 */
public class NoteForm {

    private Integer formNoteId;
    private String formNoteTitle;
    private String formNoteDescription;

    public Integer getFormNoteId() {
        return formNoteId;
    }

    public void setFormNoteId(Integer formNoteId) {
        this.formNoteId = formNoteId;
    }

    public String getFormNoteTitle() {
        return formNoteTitle;
    }

    public void setFormNoteTitle(String formNoteTitle) {
        this.formNoteTitle = formNoteTitle;
    }

    public String getFormNoteDescription() {
        return formNoteDescription;
    }

    public void setFormNoteDescription(String formNoteDescription) {
        this.formNoteDescription = formNoteDescription;
    }
}
