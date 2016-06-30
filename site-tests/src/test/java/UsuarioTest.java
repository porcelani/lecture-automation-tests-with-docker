import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class UsuarioTest {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private WebDriverWait wait;

    @Before
    public void setUp() throws Exception {
//        driver = new FirefoxDriver();
//        baseUrl = "http://192.168.99.100:8080/";

        baseUrl = "http://wildfly-container:8080/";

        DesiredCapabilities dr = DesiredCapabilities.chrome();
        dr.setPlatform(Platform.LINUX);

        driver = new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub"), dr);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void testUsuario() throws Exception {
        driver.get(baseUrl + "/usuarios/listarUsuarios.xhtml");
        driver.findElement(By.id("novoUsuario")).click();
        By input = By.id("formCadastro:nome");
        wait.until(ExpectedConditions.visibilityOfElementLocated(input));
        driver.findElement(input).clear();
        driver.findElement(input).sendKeys("Joao da Silva");
        driver.findElement(By.id("formCadastro:inserirUsuario")).click();
        assertTrue(closeAlertAndGetItsText().matches("^Confirma a informação[\\s\\S]$"));
        assertEquals("Joao da Silva", driver.findElement(By.cssSelector("td")).getText());
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