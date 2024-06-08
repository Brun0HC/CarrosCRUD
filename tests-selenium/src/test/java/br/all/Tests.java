package br.all;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Tests {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");

        driver = new ChromeDriver();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testHomePageLoads() {
        driver.get("https://carros-crud.vercel.app/");

        assertTrue(driver.getTitle().contains("Carros"), "O título da página está correto");
    }

    @Test
    public void testElementIsPresent() {
        driver.get("https://carros-crud.vercel.app/");

        WebElement addButton = driver.findElement(By.xpath("//button[contains(text(),'Enviar')]"));
        assertTrue(addButton.isDisplayed(), "O botão 'Enviar' está presente");
    }

}
