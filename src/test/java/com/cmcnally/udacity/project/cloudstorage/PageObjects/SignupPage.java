package com.cmcnally.udacity.project.cloudstorage.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    @FindBy(id = "inputFirstName")
    private WebElement firstNameField;

    @FindBy(id = "inputLastName")
    private WebElement lastNameField;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "submitButton")
    private WebElement submitButton;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void clickSubmit() {
        submitButton.click();
    }

    public void setFirstName(String firstName) {
        firstNameField.sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        lastNameField.sendKeys(lastName);
    }

    public void setUsername(String username) {
        usernameField.sendKeys(username);
    }

    public void setPassword(String password) {
        passwordField.sendKeys(password);
    }
}
