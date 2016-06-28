import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class GoogleSearchTest {

    @Test
    public void should() throws MalformedURLException {
        DesiredCapabilities dr = DesiredCapabilities.firefox();
        dr.setBrowserName("firefox");
        dr.setPlatform(Platform.LINUX);
        RemoteWebDriver driver = new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub"), dr);

        driver.navigate().to("http://gmail.com");
        WebElement email = driver.findElement(By.id("Email"));
        WebElement next = driver.findElement(By.id("next"));
        driver.close();
    }
}