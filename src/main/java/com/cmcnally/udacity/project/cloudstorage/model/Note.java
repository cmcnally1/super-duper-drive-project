package com.cmcnally.udacity.project.cloudstorage.model;

/*
    Model class for Note
 */

public class Note {

    // Instance variables for a note
    private int noteid;
    private String notetitle;
    private String notedescription;
    private int userid;

    // Note constructor
    public Note(String notetitle, String notedescription, int userid) {
        this.notetitle = notetitle;
        this.notedescription = notedescription;
        this.userid = userid;
    }

    /*
        Getters and Setters
     */

    public int getNoteid() {
        return noteid;
    }

    public void setNoteid(int noteid) {
        this.noteid = noteid;
    }

    public String getNotetitle() {
        return notetitle;
    }

    public void setNotetitle(String notetitle) {
        this.notetitle = notetitle;
    }

    public String getNotedescription() {
        return notedescription;
    }

    public void setNotedescription(String notedescription) {
        this.notedescription = notedescription;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
