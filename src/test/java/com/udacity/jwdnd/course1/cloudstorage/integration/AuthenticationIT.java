package com.udacity.jwdnd.course1.cloudstorage.integration;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationIT {

    @LocalServerPort
    private int port;
    private final String serverUrl = "http://localhost:" + this.port;
    private final String LOGIN_URL = serverUrl + "/login";
    private final String SIGNUP_URL = serverUrl + "/signup";
    private final String HOME_URL = serverUrl + "/home";
    private WebDriver driver;

    @BeforeAll
    static void beforeAll() { WebDriverManager.chromedriver().setup(); }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void visitLoginPageWithoutAuth() {
        driver.get(HOME_URL);
        String actualResult = driver.getCurrentUrl();

        Assertions.assertEquals(actualResult, LOGIN_URL);
    }

    @Test
    public void visitSignupPageWithoutAuth() {
        driver.get(SIGNUP_URL);
        String actualResult = driver.getCurrentUrl();

        Assertions.assertEquals(actualResult, SIGNUP_URL);
    }

    @Test
    public void visitHomePageWithoutAuth() {
        driver.get(HOME_URL);
        String actualResult = driver.getCurrentUrl();

        Assertions.assertEquals(actualResult, LOGIN_URL);
    }

    @Test
    public void visitHomePageWithAuth() {
        // User Signup Info
        final String USER_FIRST_NAME = "Elias";
        final String USER_LAST_NAME = "Phiri";
        final String USER_USERNAME = "elias_phiri@test.com";
        final String USER_PASSWORD = "password";

        // Signup
        driver.get(SIGNUP_URL);
        driver.findElement(By.id("inputFirstName")).sendKeys(USER_FIRST_NAME);
        driver.findElement(By.id("inputLastName")).sendKeys(USER_LAST_NAME);
        driver.findElement(By.id("inputUsername")).sendKeys(USER_USERNAME);
        driver.findElement(By.id("inputPassword")).sendKeys(USER_PASSWORD);
        driver.findElement(By.id("inputPassword")).submit();

        // Login (page redirects to login after signup)
        driver.findElement(By.id("inputUsername")).sendKeys(USER_USERNAME);
        driver.findElement(By.id("inputPassword")).sendKeys(USER_PASSWORD);
        driver.findElement(By.id("inputPassword")).submit();

        String actualResult = driver.getCurrentUrl();

        Assertions.assertEquals(actualResult, HOME_URL);
    }
}
