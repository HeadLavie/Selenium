package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CallbackTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:7777");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestV1() throws InterruptedException {
        driver.get("http://localhost:7777/");
        Thread.sleep(2000);
        WebElement form = driver.findElement(By.className("form_theme_alfa-on-white"));
        List<WebElement> elements = driver.findElements(By.className("input_control"));
        elements.get(0).sendKeys("Надежда");
        elements.get(1).sendKeys("+11122233345");
        driver.findElement(By.className("checkbox_box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }
    @Test
    void shouldTestV2() throws InterruptedException {
        driver.get("http://localhost:7777/");
        Thread.sleep(2000);
        WebElement form = driver.findElement(By.className("form_theme_alfa-on-white"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Надежда Карпенко");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+11122233345");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }
    @Test
    void shouldTestV3() {
        driver.get("http://localhost:7777/");
        WebElement form = driver.findElement(By.className("form_theme_alfa-on-white"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Nick");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+11122233345");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void shouldTestV4() {
        driver.get("http://localhost:7777/");
        WebElement form = driver.findElement(By.className("form_theme_alfa-on-white"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Надежда");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("0894448773");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void shouldTestV5() {
        driver.get("http://localhost:7777/");
        WebElement form = driver.findElement(By.className("form_theme_alfa-on-white"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Надежда");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+11122233345");

        WebElement labelElement = driver.findElement(By.cssSelector("[data-test-id=agreement]"));
        String initialClass = labelElement.getAttribute("class");


        form.findElement(By.className("button_theme_alfa-on-white")).click();

        // Wait for the class to change
        Duration timeout = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(driver, timeout);

        // Get the updated class attribute
        String updatedClass = labelElement.getAttribute("class");

        // Assert that the class has changed as expected
        assertTrue(updatedClass.contains("input_invalid"), "Class has not changed as expected");



    }

}
