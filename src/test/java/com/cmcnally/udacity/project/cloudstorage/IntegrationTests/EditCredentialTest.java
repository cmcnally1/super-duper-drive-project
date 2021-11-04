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
public class EditCredentialTest {

    @LocalServerPort
    private Integer port;

    // Initialise web driver and page object
    private static WebDriver driver;
    private SignupPage signupPage;
    private LoginPage loginPage;
    private HomePage homePage;

    // Initialise common test variables
    private String testFirstName = "Janey";
    private String testLastName = "Mac";
    private String testUsername = "janeyM";
    private String testPassword = "JaneysPassw0rd";
    private String testCredURL= "https://www.bebo.com/login";
    private String testCredUsername = "janeyDOther";
    private String testCredPassword = "JaneysOtherPassw0rd";
    private String altUrl = "https://www.myspace.com/login";
    private String altUsername = "JaneyD";
    private String altPassword = "JD123";

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
    public void testEditCredential() throws InterruptedException {
        /*
            Preconditions:
            The user must be signed up, logged in and have added credentials before viewing and editing.
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
            Test Editing Credential
            - Click View credentials button
            - VERIFY: Correct credentials are shown
            - Edit credentials
            - Click submit credentials button
            - VERIFY: Credentials updated and displayed correctly
         */

        // Click delete credential button
        homePage.clickEditCredential();

        // Wait for delete to take place
        Thread.sleep(500);

        // Verify credentials are displayed correctly
        assertEquals(testCredURL, homePage.getViewedUrl());
        assertEquals(testCredUsername, homePage.getViewedUsername());
        // Verify decrypted password is shown to user
        assertNotEquals(testCredPassword, homePage.getViewedPassword());

        // Enter new credentials in the field
        homePage.editCredential(altUrl, altUsername, altPassword);

        // Wait for element to load correctly
        Thread.sleep(500);

        // Click Credentials tab again
        homePage.clickCredentialsTab();

        // Wait for element to load correctly
        Thread.sleep(500);

        // Verify entered credential is displayed
        assertEquals(altUrl, homePage.getDisplayedURL());
        assertEquals(altUsername, homePage.getDisplayedUsername());
        // Verify entered password and displayed password are not the same (i.e. password is encrypted)
        assertNotEquals(altPassword, homePage.getDisplayedPassword());
    }
}