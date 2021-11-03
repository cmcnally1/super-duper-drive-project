package com.cmcnally.udacity.project.cloudstorage.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.File;

public class HomePage {

    // Misc page elements

    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    /*
        Note tab page elements
    */

    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "new-note-button")
    private WebElement newNoteSubmitButton;

    @FindBy(id = "note-entry-rows")
    private WebElement storedNoteRows;

    @FindBy(id = "note-entry-title")
    private WebElement storedNoteTitle;

    @FindBy(id = "note-entry-desc")
    private WebElement storedNoteDescription;

    @FindBy(id = "note-title")
    private WebElement noteTitleField;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionField;

    @FindBy(id = "noteSubmitButton")
    private WebElement noteSubmitButton;

    @FindBy(id = "deleteNoteButton")
    private WebElement deleteNoteButton;

    @FindBy(id = "editNoteButton")
    private WebElement editNoteButton;

    /*
        File page elements
     */

    @FindBy(id = "nav-files-tab")
    private WebElement filesTab;

    @FindBy(id = "fileUpload")
    private WebElement fileUploadButton;

    @FindBy(id = "submitUpload")
    private WebElement submitUploadButton;

    @FindBy(id = "file-name")
    private WebElement storedFileName;

    @FindBy(id = "fileDeleteButton")
    private WebElement fileDeleteButton;

    @FindBy(id = "file-entry-rows")
    private WebElement storedFileRows;

    @FindBy(id = "fileViewButton")
    private WebElement fileDownloadButton;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /*
        Credential page elements
     */

    @FindBy(id = "newCredButton")
    private WebElement newCredentialButton;

    @FindBy(id = "cred-entry-rows")
    private WebElement storedCredentialRows;

    @FindBy(id = "credURL")
    private WebElement storedCredentialURL;

    @FindBy(id = "credUsername")
    private WebElement storedCredentialUsername;

    @FindBy(id = "credPassword")
    private WebElement storedCredentialPassword;

    // Misc page methods

    public void clickLogout() {
        logoutButton.click();
    }

    /*
        Note methods
     */

    public void clickNoteTab() {
        noteTab.click();
    }

    public void clickAddNote() {
        newNoteSubmitButton.click();
    }

    public void clickDeleteNote() {
        deleteNoteButton.click();
    }

    public void clickEditNote() {
        editNoteButton.click();
    }

    public void addNewNote(String title, String description) {
        noteTitleField.sendKeys(title);
        noteDescriptionField.sendKeys(description);
        noteSubmitButton.click();
    }

    public String getDisplayedTitle() {
        return storedNoteTitle.getText();
    }

    public String getDisplayedDescription() {
        return storedNoteDescription.getText();
    }

    public Boolean isNoteRowsDisplayed(){
        return storedNoteRows.isDisplayed();
    }

    public void editNote(String altTitle, String altDescription) {
        noteTitleField.clear();
        noteTitleField.sendKeys(altTitle);
        noteDescriptionField.clear();
        noteDescriptionField.sendKeys(altDescription);
        noteSubmitButton.click();
    }

    /*
        File methods
     */

    public void uploadLocalFile(String filePath) throws InterruptedException {
        // Click upload file and choose path of file to upload
        fileUploadButton.sendKeys(filePath);

        // Wait for file to upload to show
        Thread.sleep(500);

        // Click the upload submit button
        submitUploadButton.click();
    }

    public String getDisplayedFileName() {
        return storedFileName.getText();
    }

    public void clickDeleteFile() {
        fileDeleteButton.click();
    }

    public void clickDownloadFile() {
        fileDownloadButton.click();
    }

    public Boolean isFileRowsDisplayed() {
        return storedFileRows.getText() != "";
    }

    // Method to check if file has been downloaded and appears in local downloads directory
    public Boolean isFileDownloaded(String downloadFolderPath, String fileName) {
        File directory = new File(downloadFolderPath);
        File[] dirContents = directory.listFiles();

        for(int i = 0; i < dirContents.length; i++) {
            if (dirContents[i].getName().equals(fileName)) {
                // Delete file as no longer needed for test
                dirContents[i].delete();
                return true;
            }
        }
        return false;
    }

    /*
        Credential methods
     */

    public void clickAddCredential() {
        newCredentialButton.click();
    }

    public String getDisplayedURL() {
        return storedCredentialURL.getText();
    }

    public String getDisplayedUsername() {
        return storedCredentialUsername.getText();
    }

    public String getDisplayedPassword() {
        return storedCredentialPassword.getText();
    }


}
