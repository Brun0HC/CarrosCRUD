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



    @Nested
    @DisplayName("Successful Actions")
    class validActions{

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
        @DisplayName("'The <h1> element contains 'Formulário de Carros'")
        public void testFormTitlePresence() {

            System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
            driver = new ChromeDriver();

            driver.get("https://carros-crud.vercel.app/");

            WebElement h1Element = driver.findElement(By.tagName("h1"));

            assertFalse(h1Element.getText().equals("Formulário de Carros"));

            driver.quit();
        }

        @Test
        @DisplayName("Table is present on the page")
        public void testTableIsPresent() {
            driver.get("https://carros-crud.vercel.app/");
            boolean isTablePresent = driver.findElements(By.className("styled-table")).size() > 0;
            assertTrue(isTablePresent);
        }


        @Test
        @DisplayName("Form is present on the page")
        public void testFormIsPresent() {
            driver.get("https://carros-crud.vercel.app/");
            WebElement form = driver.findElement(By.tagName("form"));
            assertTrue(form.isDisplayed());
        }
        @Test
        @DisplayName("Edit buttons are present on the page")
        public void testEditButtonsArePresent() {
            driver.get("https://carros-crud.vercel.app/");
            List<WebElement> editButtons = driver.findElements(By.xpath("//button[contains(text(),'Editar')]"));
            assertFalse(editButtons.size() > 0);
        }

        @Test
        @DisplayName("O botão 'Enviar' está presente")
        public void testElementIsPresent() {
            driver.get("https://carros-crud.vercel.app/");

            WebElement addButton = driver.findElement(By.xpath("//button[contains(text(),'Enviar')]"));
            assertTrue(addButton.isDisplayed());
        }

        @Test
        @DisplayName("O título da página está correto")
        public void testHomePageLoads() {
            driver.get("https://carros-crud.vercel.app/");

            String pageTitle = driver.getTitle();
            System.out.println("O título da página é: " + pageTitle);

            assertTrue(pageTitle.equalsIgnoreCase("vite + react"));
        }

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

        @Test
        @DisplayName("Nome field accepts only strings")
        public void testNomeFieldAcceptsOnlyStrings() {
            driver.get("https://carros-crud.vercel.app/");

            WebElement nomeField = driver.findElement(By.name("nome"));
            nomeField.sendKeys("123");

            String enteredText = nomeField.getAttribute("value");

            assertFalse(!enteredText.matches(".*\\d.*"));
        }


        @Test
        @DisplayName("Fabricante field accepts only strings")
        public void testFabricanteFieldAcceptsOnlyStrings() {

            driver.get("https://carros-crud.vercel.app/");

            WebElement fabricanteField = driver.findElement(By.name("fabricante"));
            fabricanteField.sendKeys("123");

            String enteredText = fabricanteField.getAttribute("value");

            assertFalse(!enteredText.matches(".*\\d.*"));
        }

        @Test
        @DisplayName("Potência field accepts only numbers")
        public void testPotenciaFieldAcceptsOnlyNumbers() {
            driver.get("https://carros-crud.vercel.app/");

            WebElement potenciaField = driver.findElement(By.name("potencia"));

            potenciaField.sendKeys("abc123");

            String enteredText = potenciaField.getAttribute("value");

            assertFalse(enteredText.matches("^[0-9]*$"));
        }

        @Test
        @DisplayName("Preço field accepts only numbers")
        public void testPrecoFieldAcceptsOnlyNumbers() {
            driver.get("https://carros-crud.vercel.app/");

            WebElement precoField = driver.findElement(By.name("preco"));

            precoField.sendKeys("abc123");

            String enteredText = precoField.getAttribute("value");

            assertTrue(enteredText.matches("^[0-9]*$"));
        }

        @Test
        @DisplayName("Ano field accepts only numbers")
        public void testAnoFieldAcceptsOnlyNumbers() {
            driver.get("https://carros-crud.vercel.app/");

            WebElement anoField = driver.findElement(By.name("ano"));

            anoField.sendKeys("abc123");

            String enteredText = anoField.getAttribute("value");

            assertTrue(enteredText.matches("^[0-9]*$"));
        }



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

        @Test
        @DisplayName("Should fail due a year too big")
        public void failDueYearTooBig() {
            driver.get("https://carros-crud.vercel.app/");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement nomeInput = driver.findElement(By.name("nome"));
            WebElement anoInput = driver.findElement(By.name("ano"));
            WebElement potenciaInput = driver.findElement(By.name("potencia"));
            WebElement precoInput = driver.findElement(By.name("preco"));
            WebElement fabricanteInput = driver.findElement(By.name("fabricante"));
            WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(),'Enviar')]"));

            nomeInput.sendKeys("Teste Carro");
            anoInput.sendKeys("2147483648");
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
        @DisplayName("Should fail due a potency value too big")
        public void failDuePotencyValueTooBig() {
            driver.get("https://carros-crud.vercel.app/");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement nomeInput = driver.findElement(By.name("nome"));
            WebElement anoInput = driver.findElement(By.name("ano"));
            WebElement potenciaInput = driver.findElement(By.name("potencia"));
            WebElement precoInput = driver.findElement(By.name("preco"));
            WebElement fabricanteInput = driver.findElement(By.name("fabricante"));
            WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(),'Enviar')]"));

            nomeInput.sendKeys("Teste Carro");
            anoInput.sendKeys("2000");
            potenciaInput.sendKeys("2147483648");
            precoInput.sendKeys("50000");
            fabricanteInput.sendKeys("Fabricante Teste");

            submitButton.click();

            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            assertEquals("Erro ao cadastrar o carro.", alertText);

            alert.accept();
        }

        @Test
        @DisplayName("Should fail due a price value too big")
        public void failDuePriceValueTooBig() {
            driver.get("https://carros-crud.vercel.app/");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement nomeInput = driver.findElement(By.name("nome"));
            WebElement anoInput = driver.findElement(By.name("ano"));
            WebElement potenciaInput = driver.findElement(By.name("potencia"));
            WebElement precoInput = driver.findElement(By.name("preco"));
            WebElement fabricanteInput = driver.findElement(By.name("fabricante"));
            WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(),'Enviar')]"));

            String longString = ("4").repeat(100);
            nomeInput.sendKeys("Teste Carro");
            anoInput.sendKeys("2000");
            potenciaInput.sendKeys("200");
            precoInput.sendKeys(longString);
            fabricanteInput.sendKeys("Fabricante Teste");

            submitButton.click();

            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            assertEquals("Erro ao cadastrar o carro.", alertText);

            alert.accept();
        }

    }
}


