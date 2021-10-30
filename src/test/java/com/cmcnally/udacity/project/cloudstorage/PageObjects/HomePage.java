package com.cmcnally.udacity.project.cloudstorage.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    // Misc page elements

    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    // Note tab page elements

    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "new-note-button")
    private WebElement newNoteButton;

    @FindBy(id = "note-entry-rows")
    private WebElement storedNoteRows;

    @FindBy(id = "note-entry-title")
    private WebElement storedNoteTitle;

    @FindBy(id = "note-entry-desc")
    private WebElement storedNoteDescription;

    @FindBy(id = "note-title")
    private WebElement newNoteTitleField;

    @FindBy(id = "note-description")
    private WebElement newNoteDescriptionField;

    @FindBy(id = "noteSubmitButton")
    private WebElement newNoteSubmitButton;

    @FindBy(id = "deleteNoteButton")
    private WebElement deleteNoteButton;

    @FindBy(id = "editNoteButton")
    private WebElement editNoteButton;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // Misc page methods

    public void clickLogout() {
        logoutButton.click();
    }

    // Note methods

    public void clickNoteTab() {
        noteTab.click();
    }

    public void clickAddNote() {
        newNoteButton.click();
    }

    public void clickDeleteNote() {
        deleteNoteButton.click();
    }

    public void clickEditNote() {
        editNoteButton.click();
    }

    public void addNewNote(String title, String description) {
        newNoteTitleField.sendKeys(title);
        newNoteDescriptionField.sendKeys(description);
        newNoteSubmitButton.click();
    }

    public String getDisplayedTitle() {
        return storedNoteTitle.getText();
    }

    public String getDisplayedDescription() {
        return storedNoteDescription.getText();
    }

}
