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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddNoteTest {

    @LocalServerPort
    private Integer port;

    // Initialise web driver and page object
    private static WebDriver driver;
    private SignupPage signupPage;
    private LoginPage loginPage;
    private HomePage homePage;

    // Initialise common test variables
    private String testFirstName = "John";
    private String testLastName = "Doe";
    private String testUsername = "johnD";
    private String testPassword = "JohnsPassw0rd";
    private String testNoteTitle = "Test Note!";
    private String testNoteDesc = "This is a test note.\n Testing new line.";

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
    public void testAddNote() {
        /*
            Preconditions:
            The user must be signed up and logged in before they can add notes.
            Therefore, user being tested is signed up and logged in first.
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

        /*
            Test Adding Note:
            - Click Note tab
            - Click add new note button
            - Enter new note title and description
            - Click submit note button
            - VERIFY: New note appears in list
         */

        // Set up web driver and page objects for home page
        driver.get("http://localhost:" + port +"/home");
        homePage = new HomePage(driver);

        // Click Note tab
        homePage.clickNoteTab();

        // Click add new note button
        homePage.clickAddNote();

        // Enter new note title and description and click submit
        homePage.addNewNote(testNoteTitle, testNoteDesc);

        // Verify note title and description are displayed correctly
        assertEquals(testNoteTitle, homePage.getDisplayedTitle());
        assertEquals(testNoteDesc, homePage.getDisplayedDescription());
    }


}
