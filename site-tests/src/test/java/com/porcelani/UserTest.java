package com.porcelani;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class UserTest {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private WebDriverWait wait;

    @Before
    public void setUp() throws Exception {
//        localConfiguration();
        remoteConfiguration();

        wait = new WebDriverWait(driver, 10);
    }

    private void localConfiguration() {
        baseUrl = "http://192.168.99.100:8080/";
        driver = new FirefoxDriver();
    }

    private void remoteConfiguration() throws MalformedURLException {
        baseUrl = "http://wildfly-container:8080/";
        DesiredCapabilities dr = DesiredCapabilities.firefox();
        dr.setPlatform(Platform.LINUX);
        driver = new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub"), dr);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @Test
    public void should_create_library_user() throws Exception {
        driver.get(baseUrl + "/usuarios/listarUsuarios.xhtml");
        By novoUsuario = By.id("novoUsuario");
        driver.findElement(novoUsuario).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(novoUsuario));
        By input = By.id("formCadastro:nome");
        wait.until(ExpectedConditions.visibilityOfElementLocated(input));
        driver.findElement(input).clear();
        driver.findElement(input).sendKeys("Joao da Silva");
        driver.findElement(By.id("formCadastro:inserirUsuario")).click();
        assertTrue(closeAlertAndGetItsText().matches("^Confirma a informação[\\s\\S]$"));
        assertEquals("Joao da Silva", driver.findElement(By.xpath("//tr[1]/td[1]")).getText());
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
