package br.all;

import com.github.javafaker.Faker;
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

    private final Faker faker = new Faker();

    private WebDriver driver;

    private void captureAndFillForm(String nome, String ano, String potencia, String preco, String fabricante) {

        driver.get("https://carros-crud.vercel.app/");

        WebElement nomeInput = driver.findElement(By.name("nome"));
        WebElement anoInput = driver.findElement(By.name("ano"));
        WebElement potenciaInput = driver.findElement(By.name("potencia"));
        WebElement precoInput = driver.findElement(By.name("preco"));
        WebElement fabricanteInput = driver.findElement(By.name("fabricante"));
        WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(),'Enviar')]"));

        nomeInput.sendKeys(nome);
        anoInput.sendKeys(ano);
        potenciaInput.sendKeys(potencia);
        precoInput.sendKeys(preco);
        fabricanteInput.sendKeys(fabricante);

        submitButton.click();
    }

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
    @DisplayName("Find Elements")
    class FindElements {
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
    }

    @Nested
    @DisplayName("Successful Actions")
    class validActions{

        @Test
        @DisplayName("Name field accepts special characters")
        public void testNameFieldDoesNotAcceptSpecialCharacters() {
            driver.get("https://carros-crud.vercel.app/");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            captureAndFillForm(
                    "Carro #1",
                    faker.number().digits(6),
                    faker.number().digits(5),
                    faker.number().digits(5),
                    faker.name().name());

            WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(),'Editar')]"));
            submitButton.click();

            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            assertEquals("Carro cadastrado com sucesso!", alertText);

            alert.accept();

        }

        @Test
        @DisplayName("Test to verify the functionality of the submit button")
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
        @DisplayName("Should make a successful registration")
        public void testCompleteFormSubmission() {
            driver.get("https://carros-crud.vercel.app/");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            captureAndFillForm(
                    faker.rickAndMorty().character(),
                    faker.number().digits(4),
                    faker.number().digits(3),
                    faker.number().digits(6),
                    faker.aquaTeenHungerForce().character()
            );

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
        @Test
        @DisplayName("Preço field accepts float values")
        public void testPrecoFieldAcceptsFloatValues() {
            driver.get("https://carros-crud.vercel.app/");

            WebElement precoField = driver.findElement(By.name("preco"));

            precoField.sendKeys("123.45");

            String enteredText = precoField.getAttribute("value");

            assertTrue(enteredText.matches("^[0-9]*\\.?[0-9]+$"));
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

            captureAndFillForm(
                    faker.rickAndMorty().character().repeat(1000),
                    faker.number().digits(4),
                    faker.number().digits(3),
                    faker.number().digits(6),
                    faker.name().firstName()
            );

            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            assertEquals("Erro ao cadastrar o carro.", alertText);

            alert.accept();
        }

        @Test
        @DisplayName("Should fail due a producer with too many characters")
        public void failDueTooManyCharactersInProducer() {
            driver.get("https://carros-crud.vercel.app/");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            captureAndFillForm(
                    faker.name().firstName(),
                    faker.number().digits(4),
                    faker.number().digits(3),
                    faker.number().digits(6),
                    faker.gameOfThrones().character().repeat(1000)
            );

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

            captureAndFillForm(
                    faker.rickAndMorty().character(),
                    "2147483648",
                    faker.number().digits(3),
                    faker.number().digits(6),
                    faker.rickAndMorty().character()
            );

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

            captureAndFillForm(
                    faker.leagueOfLegends().champion(),
                    faker.number().digits(4),
                    "2147483648",
                    faker.number().digits(6),
                    faker.leagueOfLegends().champion()
            );

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

            captureAndFillForm(
                faker.backToTheFuture().character(),
                faker.number().digits(4),
                faker.number().digits(3),
                faker.number().digits(100),
                faker.backToTheFuture().character()
            );

            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            assertEquals("Erro ao cadastrar o carro.", alertText);

            alert.accept();
        }
    }
}





