package com.udacity.jwdnd.course1.cloudstorage.integration;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
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
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteIT {

    @Autowired
    private NoteMapper noteMapper;
    @LocalServerPort
    private int port;
    private WebDriver driver;
    private final String serverUrl = "http://localhost:" + this.port;
    private final String SIGNUP_URL = serverUrl + "/signup";

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();

        String userFirstName = "Elias";
        String userLastName = "Phiri";
        final String USER_USERNAME = "elias_phiri@note_test.com";
        final String USER_PASSWORD = "password";

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
}
