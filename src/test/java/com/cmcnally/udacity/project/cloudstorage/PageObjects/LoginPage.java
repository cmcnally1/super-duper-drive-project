package com.cmcnally.udacity.project.cloudstorage.PageObjects;

import org.apache.juli.logging.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/*
    Login page object to hold to page elements and methods used in tests
 */
public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id =  "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "submitButton")
    private WebElement submitButton;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void clickSubmit() {
        submitButton.click();
    }

    public void setUsername(String username) {
        usernameField.sendKeys(username);
    }

    public void setPassword(String password) {
        passwordField.sendKeys(password);
    }
}
