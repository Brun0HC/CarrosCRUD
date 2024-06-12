package br.all;

import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

        String pageTitle = driver.getTitle();
        System.out.println("O título da página é: " + pageTitle);

        assertTrue(pageTitle.equalsIgnoreCase("vite + react"), "O título da página está correto");
    }

    @Test
    public void testElementIsPresent() {
        driver.get("https://carros-crud.vercel.app/");

        WebElement addButton = driver.findElement(By.xpath("//button[contains(text(),'Enviar')]"));
        assertTrue(addButton.isDisplayed(), "O botão 'Enviar' está presente");
    }


    @Test
    public void testCarFormSubmission() {
        driver.get("https://carros-crud.vercel.app/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement nomeField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("nome")));
        nomeField.sendKeys("Teste Carro");

        WebElement anoField = driver.findElement(By.name("ano"));
        anoField.sendKeys("2023");

        WebElement potenciaField = driver.findElement(By.name("potencia"));
        potenciaField.sendKeys("150");

        WebElement precoField = driver.findElement(By.name("preco"));
        precoField.sendKeys("50000");

        WebElement fabricanteField = driver.findElement(By.name("fabricante"));
        fabricanteField.sendKeys("Teste Fabricante");

        WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(),'Enviar')]"));
        submitButton.click();

    }

    @Test
    public void testEditButtonFunctionality() {
        driver.get("https://carros-crud.vercel.app/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


        WebElement editButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Editar')]")));
        editButton.click();

        WebElement nomeField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("nome")));
        WebElement anoField = driver.findElement(By.name("ano"));
        WebElement potenciaField = driver.findElement(By.name("potencia"));
        WebElement precoField = driver.findElement(By.name("preco"));
        WebElement fabricanteField = driver.findElement(By.name("fabricante"));

        // Editamos os campos com novos valores
        nomeField.clear();
        nomeField.sendKeys("Carro Editado");
        anoField.clear();
        anoField.sendKeys("2024");
        potenciaField.clear();
        potenciaField.sendKeys("180");
        precoField.clear();
        precoField.sendKeys("60000");
        fabricanteField.clear();
        fabricanteField.sendKeys("Fabricante Editado");

        WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(),'Editar')]"));
        submitButton.click();


    }

    @Test
    public void testNomeFieldAcceptsOnlyStrings() {
        driver.get("https://carros-crud.vercel.app/");

        WebElement nomeField = driver.findElement(By.name("nome"));
        nomeField.sendKeys("123");

        String enteredText = nomeField.getAttribute("value");

        assertFalse(!enteredText.matches(".*\\d.*"), "Nome field accepts only strings");
    }

    @Test
    public void testFabricanteFieldAcceptsOnlyStrings() {

        driver.get("https://carros-crud.vercel.app/");

        WebElement fabricanteField = driver.findElement(By.name("fabricante"));
        fabricanteField.sendKeys("123");

        String enteredText = fabricanteField.getAttribute("value");

        assertFalse(!enteredText.matches(".*\\d.*"), "Fabricante field accepts only strings");
    }

    @Test
    public void testPotenciaFieldAcceptsOnlyNumbers() {
        driver.get("https://carros-crud.vercel.app/");

        WebElement potenciaField = driver.findElement(By.name("potencia"));

        potenciaField.sendKeys("abc123");

        String enteredText = potenciaField.getAttribute("value");

        assertFalse(enteredText.matches("^[0-9]*$"), "Potência field accepts only numbers");
    }

    @Test
    public void testFormTitlePresence() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();

        driver.get("https://carros-crud.vercel.app/");

        WebElement h1Element = driver.findElement(By.tagName("h1"));

        assertFalse(h1Element.getText().equals("Formulário de Carros"), "The <h1> element contains 'Formulário de Carros'");

        driver.quit();
    }

    @Test
    public void testTableIsPresent() {
        driver.get("https://carros-crud.vercel.app/");
        boolean isTablePresent = driver.findElements(By.className("styled-table")).size() > 0;
        assertTrue(isTablePresent, "Table is present on the page");
    }


    @Test
    public void testFormIsPresent() {
        driver.get("https://carros-crud.vercel.app/");
        WebElement form = driver.findElement(By.tagName("form"));
        assertTrue(form.isDisplayed(), "Form is present on the page");
    }
    @Test
    public void testEditButtonsArePresent() {
        driver.get("https://carros-crud.vercel.app/");
        List<WebElement> editButtons = driver.findElements(By.xpath("//button[contains(text(),'Editar')]"));
        assertFalse(editButtons.size() > 0, "Edit buttons are present on the page");
    }

    @Nested
    @DisplayName("Successful Actions")
    class validActions{

        @Test
        @DisplayName("Should make a successful registration")
        public void testCompleteFormSubmission() {
            driver.get("https://carros-crud.vercel.app/");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement nomeInput = driver.findElement(By.name("nome"));
            WebElement anoInput = driver.findElement(By.name("ano"));
            WebElement potenciaInput = driver.findElement(By.name("potencia"));
            WebElement precoInput = driver.findElement(By.name("preco"));
            WebElement fabricanteInput = driver.findElement(By.name("fabricante"));
            WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(),'Enviar')]"));

            nomeInput.sendKeys("Carro Teste");
            anoInput.sendKeys("2023");
            potenciaInput.sendKeys("200");
            precoInput.sendKeys("50000");
            fabricanteInput.sendKeys("Fabricante Teste");

            submitButton.click();

            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            assertEquals("Carro cadastrado com sucesso!", alertText);

            alert.accept();
        }

    }

    @Nested
    @DisplayName("Fail Actions")
    class InvalidActions{

    }

    @Nested
    @DisplayName("Overload Tests")
    class OverloadTests{

        @Test
        @DisplayName("Should fail due a name with too many characters")
        public void failDueTooManyCharactersInName() {
            driver.get("https://carros-crud.vercel.app/");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement nomeInput = driver.findElement(By.name("nome"));
            WebElement anoInput = driver.findElement(By.name("ano"));
            WebElement potenciaInput = driver.findElement(By.name("potencia"));
            WebElement precoInput = driver.findElement(By.name("preco"));
            WebElement fabricanteInput = driver.findElement(By.name("fabricante"));
            WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(),'Enviar')]"));

            String longstring = ("a").repeat(10000);
            nomeInput.sendKeys(longstring);
            anoInput.sendKeys("2023");
            potenciaInput.sendKeys("200");
            precoInput.sendKeys("50000");
            fabricanteInput.sendKeys("Fabricante Teste");

            submitButton.click();

            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            assertEquals("Erro ao cadastrar o carro.", alertText);

            alert.accept();

        }

        @Test
        @DisplayName("Should fail due a producer with too many characters")
        public void failDueTooManyCharactersInYear() {
            driver.get("https://carros-crud.vercel.app/");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement nomeInput = driver.findElement(By.name("nome"));
            WebElement anoInput = driver.findElement(By.name("ano"));
            WebElement potenciaInput = driver.findElement(By.name("potencia"));
            WebElement precoInput = driver.findElement(By.name("preco"));
            WebElement fabricanteInput = driver.findElement(By.name("fabricante"));
            WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(),'Enviar')]"));

            String longstring = ("a").repeat(10000);
            nomeInput.sendKeys("Teste Carro");
            anoInput.sendKeys("2023");
            potenciaInput.sendKeys("200");
            precoInput.sendKeys("50000");
            fabricanteInput.sendKeys(longstring);

            submitButton.click();

            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            assertEquals("Erro ao cadastrar o carro.", alertText);

            alert.accept();

        }
    }
}


