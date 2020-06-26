package com.udacity.jwdnd.course1.cloudstorage.integration;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialIT {

    @Autowired
    private CredentialMapper credentialMapper;

    @LocalServerPort
    private int port;
    private WebDriver driver;
    private final String serverUrl = "http://localhost:" + this.port;
    private final String SIGNUP_URL = serverUrl + "/signup";
    private final String USER_USERNAME = "elias_phiri@credential_test.com";
    private final String USER_PASSWORD = "password";

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();

    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();

        String userFirstName = "Elias";
        String userLastName = "Phiri";

        // Signup
        driver.get(SIGNUP_URL);

        // User Signup Info
        driver.findElement(By.id("inputFirstName")).sendKeys("value", userFirstName);
        driver.findElement(By.id("inputLastName")).sendKeys("value", userLastName);
        driver.findElement(By.id("inputUsername")).sendKeys("value", USER_USERNAME);
        driver.findElement(By.id("inputPassword")).sendKeys("value", USER_PASSWORD);
        driver.findElement(By.id("inputPassword")).submit();

        // Login (page redirects to login after signup)
        driver.findElement(By.id("inputUsername")).sendKeys("value", USER_USERNAME);
        driver.findElement(By.id("inputPassword")).sendKeys("value", USER_PASSWORD);
        driver.findElement(By.id("inputPassword")).submit();

        driver.findElement(By.id("nav-credentials-tab")).click();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void readWriteCredential() {
        String url = "test_url.com";

        driver.findElement(By.id("credential-create")).click();
        driver.findElement(By.id("credential-url")).sendKeys("value", url);
        driver.findElement(By.id("credential-username")).sendKeys("value", USER_USERNAME);
        driver.findElement(By.id("credential-password")).sendKeys("value", USER_PASSWORD);
        driver.findElement(By.id("credential-password")).submit();
        driver.findElement(By.id("nav-credentials-tab")).click();

        List<Credential> savedCredentials = credentialMapper.findAll();
        Credential lastSavedCredential = savedCredentials.get(savedCredentials.size() - 1);

        WebElement tableHeader = driver.findElement(By.id("credential-" + lastSavedCredential.getId())).findElement(By.tagName("th"));
        List<WebElement> tableData = driver.findElement(By.id("credential-" + lastSavedCredential.getId())).findElements(By.tagName("td"));

        Assertions.assertEquals(tableHeader.getText(), url);
        Assertions.assertTrue(
                tableData
                        .stream()
                        .map(WebElement::getText)
                        .collect(Collectors.toList())
                        .containsAll(Arrays.asList(USER_USERNAME, USER_PASSWORD))
        );
    }

    @Test
    public void updateCredential() {
        String updatedUsername = "elias_phiri_updated@credential_test.com";
        String updatedPassword = "updated_password";

        String updatedUrl = "test_url_updated.com";
        List<Credential> savedCredentials = credentialMapper.findAll();
        Credential lastSavedCredential = savedCredentials.get(savedCredentials.size() - 1);

        driver.findElement(By.id("credential-edit-" + lastSavedCredential.getId())).click();
        driver.findElement(By.id("credential-edit-id")).sendKeys("value", String.valueOf(lastSavedCredential.getId()) );
        driver.findElement(By.id("credential-edit-url")).sendKeys("value", updatedUrl);
        driver.findElement(By.id("credential-edit-username")).sendKeys("value", updatedUsername);
        driver.findElement(By.id("credential-edit-password")).sendKeys("value", updatedPassword);
        driver.findElement(By.id("credential-edit-password")).submit();
        driver.findElement(By.id("nav-credentials-tab")).click();

        WebElement tableHeader = driver.findElement(By.id("credential-" + lastSavedCredential.getId())).findElement(By.tagName("th"));
        List<WebElement> tableData = driver.findElement(By.id("credential-" + lastSavedCredential.getId())).findElements(By.tagName("td"));

        Assertions.assertEquals(tableHeader.getText(), updatedUrl);
        Assertions.assertTrue(
                tableData
                        .stream()
                        .map(WebElement::getText)
                        .collect(Collectors.toList())
                        .containsAll(Arrays.asList(updatedUsername, updatedPassword))
        );
    }

    @Test
    public void deleteCredential() {
        List<Credential> savedCredentials = credentialMapper.findAll();
        Credential lastSavedCredential = savedCredentials.get(savedCredentials.size() - 1);

        driver.findElement(By.id("credential-delete-" + lastSavedCredential.getId())).click();
        List<WebElement> tableRow = driver.findElements(By.id("credential-" + lastSavedCredential.getId()));

        Assertions.assertEquals(tableRow.size(), 0);
    }
}
