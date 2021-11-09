package com.cmcnally.udacity.project.cloudstorage.IntegrationTests;

import com.cmcnally.udacity.project.cloudstorage.PageObjects.HomePage;
import com.cmcnally.udacity.project.cloudstorage.PageObjects.LoginPage;
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
public class UnauthorisedUserTest {

    @LocalServerPort
    private Integer port;

    // Initialise driver and page objects
    private static WebDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
    }

    // Test to verify a user not signed up cannot login
    @Test
    public void testUnauthorisedUserLogin() {

        // Set up web driver and page object for test
        driver.get("http://localhost:" + port +"/login");
        loginPage = new LoginPage(driver);

        // Try to log in with credentials that have not been signed up
        loginPage.setUsername("NotAUser");
        loginPage.setPassword("NotAPassword");

        // Click submit
        loginPage.clickSubmit();

        // Verify that the home page is not loaded
        assertNotEquals("Home", driver.getTitle());
    }

    // Test to verify an unauthorised user cannot access home with URL
    @Test
    public void testUnauthorisedUserHomeAccess() {

        // Set up driver to try and access home page
        driver.get("http://localhost:" + port +"/home");
        homePage = new HomePage(driver);

        // Verify that the home page is not loaded
        assertNotEquals("Home", driver.getTitle());

    }
}
