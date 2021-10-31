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
public class EditNoteTest {

    @LocalServerPort
    private Integer port;

    // Initialise web driver and page object
    private static WebDriver driver;
    private SignupPage signupPage;
    private LoginPage loginPage;
    private HomePage homePage;

    // Initialise common test variables
    private String testFirstName = "Jack";
    private String testLastName = "Donald";
    private String testUsername = "jackD";
    private String testPassword = "JacksPassw0rd";
    private String testNoteTitle = "Test Note!";
    private String testNoteDesc = "This is a test note.";
    private String altNoteTitle = "Edited Note";
    private String altNoteDesc = "I have edited this note";

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
    public void testDeleteNote() throws InterruptedException {
        /*
            Preconditions:
            The user must be signed up, logged in and notes added before they can edit notes.
            Therefore, user being tested is signed up, logged in and a note created first.
         */

        // Set up web driver and page objects for the test
        driver.get("http://localhost:" + port +"/signup");
        signupPage = new SignupPage(driver);

        // Fill out the signup page text fields
        signupPage.setFirstName(testFirstName);
        signupPage.setLastName(testLastName);
        signupPage.setUsername(testUsername);
        signupPage.setPassword(testPassword);

        // Click the submit button
        signupPage.clickSubmit();

        // Set up web driver and page objects for login
        driver.get("http://localhost:" + port +"/login");
        loginPage = new LoginPage(driver);

        // Fill out the login text fields
        loginPage.setUsername(testUsername);
        loginPage.setPassword(testPassword);

        // Click the submit button
        loginPage.clickSubmit();

        // Set up web driver and page objects for home page
        driver.get("http://localhost:" + port +"/home");
        homePage = new HomePage(driver);

        // Click Note tab
        homePage.clickNoteTab();

        // Wait for element to load correctly
        Thread.sleep(500);

        // Click add new note button
        homePage.clickAddNote();

        // Wait for element to load correctly
        Thread.sleep(500);

        // Enter new note title and description and click submit
        homePage.addNewNote(testNoteTitle, testNoteDesc);

        // Wait for element to load correctly
        Thread.sleep(500);

        // Click Note tab again
        homePage.clickNoteTab();

        // Wait for element to load correctly
        Thread.sleep(500);

        // Verify note title and description are displayed correctly before editing
        assertEquals(testNoteTitle, homePage.getDisplayedTitle());
        assertEquals(testNoteDesc, homePage.getDisplayedDescription());

        /*
            Test Editing Note:
            - Click edit note button
            - Edit the contents of the note
            - VERIFY: Note contents have been updated with new title and description
         */

        // Click the delete note button
        homePage.clickEditNote();

        // Wait for element to load correctly
        Thread.sleep(500);

        // Edit the contents of the note tile and description
        homePage.editNote(altNoteTitle, altNoteDesc);

        // Wait for element to load correctly
        Thread.sleep(500);

        // Click Note tab again
        homePage.clickNoteTab();

        // Wait for element to load correctly
        Thread.sleep(500);

        // Verify note title and description are displayed correctly
        assertEquals(altNoteTitle, homePage.getDisplayedTitle());
        assertEquals(altNoteDesc, homePage.getDisplayedDescription());
    }


}
