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

/*
    NOTE: In test variables section, please provide the path to a file to upload
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeleteFileTest {

    @LocalServerPort
    private Integer port;

    // Initialise web driver and page object
    private static WebDriver driver;
    private SignupPage signupPage;
    private LoginPage loginPage;
    private HomePage homePage;

    // Initialise common test variables
    private String testFirstName = "Jimmy";
    private String testLastName = "Jonny";
    private String testUsername = "jjonny";
    private String testPassword = "JimmysPassw0rd";
    private String localFilePath = "/Users/ciaran/Documents/WebProjects/UdacityBlogProject/index.html";
    private String localFileName = "index.html";

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
    public void testDeleteFile() throws InterruptedException {
        /*
            Preconditions:
            The user must be signed up, logged in and have a file uploaded before they can delete a file.
            Therefore, user being tested is signed up, logged and upload a file in first.
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

        // Click the upload button and choose local file to upload
        homePage.uploadLocalFile(localFilePath);

        // Verify success alert box is shown
        assertEquals("File uploaded successfully", driver.switchTo().alert().getText());

        // Click ok on alert box
        driver.switchTo().alert().accept();

        // Verify file is uploaded
        assertEquals(localFileName, homePage.getDisplayedFileName());

        /*
            Test Deleting a file:
            - Click delete file button
            - VERIFY: File is deleted and nothing is displayed.
         */

        // Click the delete file button
        homePage.clickDeleteFile();

        // Wait for deletion to take place
        Thread.sleep(500);

        // Verify success alert box is shown
        assertEquals("File deleted successfully", driver.switchTo().alert().getText());

        // Click ok on alert box
        driver.switchTo().alert().accept();

        // Verify that no files are displayed
        assertEquals(false, homePage.isFileRowsDisplayed());
    }
}