package com.cmcnally.udacity.project.cloudstorage.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void clickLogout() {
        logoutButton.click();
    }
}
