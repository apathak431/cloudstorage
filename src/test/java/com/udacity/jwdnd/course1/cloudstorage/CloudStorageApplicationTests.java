package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
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
class CloudStorageApplicationTests {
    @Autowired
    private NoteMapper noteMapper;
    @Autowired
    private CredentialMapper credentialMapper;
    @LocalServerPort
    private int port;
    private final String serverUrl = "http://localhost:" + this.port;
    private final String LOGIN_URL = serverUrl + "/login";
    private final String SIGNUP_URL = serverUrl + "/signup";
    private final String HOME_URL = serverUrl + "/home";
    private final String USER_USERNAME = "apathak";
    private final String USER_PASSWORD = "123456";
    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();

        String userFirstName = "Aditya";
        String userLastName = "Pathak";
        final String USER_USERNAME = "apathak";
        final String USER_PASSWORD = "123456";

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

        driver.findElement(By.id("nav-notes-tab")).click();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    //Authentication section
    @Test
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
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
        final String USER_FIRST_NAME = "Aditya";
        final String USER_LAST_NAME = "Pathak";
        final String USER_USERNAME = "apathak";
        final String USER_PASSWORD = "123456";

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
    //Notes section
    @Test
    public void readWriteNote() {
        String noteTitle = "Test Note Title";
        String noteDescription = "Test note description";

        driver.findElement(By.id("note-noteTitle")).sendKeys("value", noteTitle);
        driver.findElement(By.id("note-noteDescription")).sendKeys("value", noteDescription);
        driver.findElement(By.id("note-noteDescription")).submit();

        driver.findElement(By.id("nav-notes-tab")).click();

        List<Note> savedNotes = noteMapper.findAll();
        Note lastSavedNote = savedNotes.get(savedNotes.size() - 1);

        WebElement tableHeader = driver.findElement(By.id("note-" + lastSavedNote.getId())).findElement(By.tagName("th"));
        List<WebElement> tableData = driver.findElement(By.id("note-" + lastSavedNote.getId())).findElements(By.tagName("td"));

        Assertions.assertEquals(tableHeader.getText(), noteTitle);
        Assertions.assertTrue(
                tableData
                        .stream()
                        .map(WebElement::getText)
                        .collect(Collectors.toList())
                        .contains(noteDescription)
        );
    }

    @Test
    public void updateNote() {
        String updatedNoteTitle = "Test Note Title (updated)";
        String updatedNoteDescription = "Test note description (updated)";

        List<Note> savedNotes = noteMapper.findAll();
        Note lastSavedNote = savedNotes.get(savedNotes.size() - 1);

        driver.findElement(By.id("note-edit-" + lastSavedNote.getId())).click();
        driver.findElement(By.id("note-edit-id")).sendKeys("value", String.valueOf(lastSavedNote.getId()) );
        driver.findElement(By.id("note-edit-noteTitle")).sendKeys("value", updatedNoteTitle);
        driver.findElement(By.id("note-edit-noteDescription")).sendKeys("value", updatedNoteDescription);
        driver.findElement(By.id("note-edit-noteDescription")).submit();

        driver.findElement(By.id("nav-notes-tab")).click();

        WebElement tableHeader = driver.findElement(By.id("note-" + lastSavedNote.getId())).findElement(By.tagName("th"));
        List<WebElement> tableData = driver.findElement(By.id("note-" + lastSavedNote.getId())).findElements(By.tagName("td"));

        Assertions.assertEquals(tableHeader.getText(), updatedNoteTitle);
        Assertions.assertTrue(
                tableData
                        .stream()
                        .map(WebElement::getText)
                        .collect(Collectors.toList())
                        .contains(updatedNoteDescription)
        );
    }

    @Test
    public void deleteNote() {
        List<Note> savedNotes = noteMapper.findAll();
        Note lastSavedNote = savedNotes.get(savedNotes.size() - 1);

        driver.findElement(By.id("note-delete-" + lastSavedNote.getId())).click();
        List<WebElement> tableRow = driver.findElements(By.id("note-" + lastSavedNote.getId()));

        Assertions.assertEquals(tableRow.size(), 0);
    }
    //Credentials section
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
        String updatedUsername = "apathak431@test.com";
        String updatedPassword = "updated_password";

        String updatedUrl = "testing.com";
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