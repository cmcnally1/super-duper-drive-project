package com.cmcnally.udacity.project.cloudstorage.IntegrationTests;

import com.cmcnally.udacity.project.cloudstorage.PageObjects.HomePage;
import com.cmcnally.udacity.project.cloudstorage.PageObjects.LoginPage;
import com.cmcnally.udacity.project.cloudstorage.PageObjects.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeleteCredentialTest {

    @LocalServerPort
    private Integer port;

    // Initialise web driver and page object
    private static WebDriver driver;
    private SignupPage signupPage;
    private LoginPage loginPage;
    private HomePage homePage;

    // Initialise common test variables
    private String testFirstName = "Jakey";
    private String testLastName = "Donald";
    private String testUsername = "jakeyD";
    private String testPassword = "JakeysPassw0rd";
    private String testCredURL= "https://www.bebo.com/login";
    private String testCredUsername = "jakeyDOther";
    private String testCredPassword = "JakeysOtherPassw0rd";

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
    }

    @Test
    public void testDeleteCredential() throws InterruptedException {
        /*
            Preconditions:
            The user must be signed up, logged in and have added credentials before deleting.
            Therefore, user being tested is signed up, logged in first and add credentials first.
         */

        // Set up web driver and page objects for the test
        driver.get("http://localhost:" + port + "/signup");
        signupPage = new SignupPage(driver);

        // Fill out the signup page text fields
        signupPage.setFirstName(testFirstName);
        signupPage.setLastName(testLastName);
        signupPage.setUsername(testUsername);
        signupPage.setPassword(testPassword);

        // Click the submit button
        signupPage.clickSubmit();

        // Set up web driver and page objects for login
        driver.get("http://localhost:" + port + "/login");
        loginPage = new LoginPage(driver);

        // Fill out the login text fields
        loginPage.setUsername(testUsername);
        loginPage.setPassword(testPassword);

        // Click the submit button
        loginPage.clickSubmit();

        // Set up web driver and page objects for home page
        driver.get("http://localhost:" + port + "/home");
        homePage = new HomePage(driver);

        // Click Credentials tab
        homePage.clickCredentialsTab();

        // Wait for element to load correctly
        Thread.sleep(500);

        // Click add new credential
        homePage.clickAddCredential();

        // Wait for element to load correctly
        Thread.sleep(500);

        // Enter URL, username and password and submit
        homePage.addNewCredential(testCredURL, testCredUsername, testPassword);

        // Wait for element to load correctly
        Thread.sleep(500);

        // Verify success alert box is shown
        assertEquals("Credential added successfully", driver.switchTo().alert().getText());

        // Click ok on alert box
        driver.switchTo().alert().accept();

        // Click Credentials tab again
        homePage.clickCredentialsTab();

        // Wait for element to load correctly
        Thread.sleep(500);

        // Verify entered credential is displayed
        assertEquals(testCredURL, homePage.getDisplayedURL());
        assertEquals(testCredUsername, homePage.getDisplayedUsername());
        // Verify entered password and displayed password are not the same (i.e. password is encrypted)
        assertNotEquals(testCredPassword, homePage.getDisplayedPassword());

        /*
            Test Deleting Credential
            - Click delete credentials button
            - VERIFY: Credential is no longer displayed
         */

        // Click delete credential button
        homePage.clickDeleteCredential();

        // Wait for delete to take place
        Thread.sleep(500);

        // Verify success alert box is shown
        assertEquals("Credential deleted successfully", driver.switchTo().alert().getText());

        // Click ok on alert box
        driver.switchTo().alert().accept();

        // Verify that no credentials are displayed
        assertEquals(false, homePage.isCredentialDisplayed());
    }
}
