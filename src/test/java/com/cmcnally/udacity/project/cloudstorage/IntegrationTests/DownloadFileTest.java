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
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DownloadFileTest {

    @LocalServerPort
    private Integer port;

    // Initialise web driver and page object
    private static WebDriver driver;
    private SignupPage signupPage;
    private LoginPage loginPage;
    private HomePage homePage;

    // Initialise common test variables
    private String testFirstName = "Jackie";
    private String testLastName = "James";
    private String testUsername = "jjames";
    private String testPassword = "JackiesPassw0rd";
    private String localFilePath = "/Users/ciaran/Documents/WebProjects/UdacityBlogProject/index.html";
    private String localFileName = "index.html";
    private String localDownloadsPath = "/Users/ciaran/Downloads";

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
    public void testDownloadFile() throws InterruptedException {
        /*
            Preconditions:
            The user must be signed up, logged in and have a file uploaded before they can download a file.
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

        // Verify file is uploaded
        assertEquals(localFileName, homePage.getDisplayedFileName());

        /*
            Test downloading a file:
                - Click view/download file button
                - VERIFY: File is downloaded
         */

        // Click download file button
        homePage.clickDownloadFile();

        // Wait for file to download
        Thread.sleep(1000);

        // Verify the file has been downloaded to the downloads folder
        assertTrue(homePage.isFileDownloaded(localDownloadsPath, localFileName));

    }
}