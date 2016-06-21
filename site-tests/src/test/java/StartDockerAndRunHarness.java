import com.shekhargulati.reactivex.docker.client.DockerClient;
import com.shekhargulati.reactivex.docker.client.http_client.HttpStatus;
import com.shekhargulati.reactivex.docker.client.representations.DockerContainerRequest;
import com.shekhargulati.reactivex.docker.client.representations.DockerContainerRequestBuilder;
import com.shekhargulati.reactivex.docker.client.representations.DockerInfo;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import page.LoginPage;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class StartDockerAndRunHarness {

    public static final String DOCKER_HOST = "tcp://192.168.99.100:2376";
    public static final String DOCKER_CERT_PATH = "C:\\Users\\porce\\.docker\\machine\\certs";
    public static final String CONTAINER = "my_dvwa_container";
    public static final DockerClient CLIENT = DockerClient.newDockerClient(DOCKER_HOST, DOCKER_CERT_PATH);

    @Before
    public void init(){

        DockerContainerRequest request = new DockerContainerRequestBuilder()
                .setImage("citizenstig/dvwa")
                .setCmd(Arrays.asList("/bin/bash"))
                .setAttachStdin(true)
                .setTty(true)
                .createDockerContainerRequest();


        DockerInfo info = CLIENT.info();
        System.out.println(info.containers());

        CLIENT.createContainerObs(request, CONTAINER)
                .flatMap(res -> CLIENT.startContainerObs(res.getId()))
                .subscribe(System.out::println);

        DockerInfo info2 = CLIENT.info();
        System.out.println(info2.containers());

    }

    @Test
    public void should_login() {
        WebDriver driver = new FirefoxDriver();
        driver.get("http://192.168.99.100/login.php");

        LoginPage page = PageFactory.initElements(driver, LoginPage.class);

        page.login("admin","password");

        WebElement h1 = driver.findElement(By.cssSelector("h1"));
        assertEquals("Welcome to Damn Vulnerable Web App!", h1.getText());

        CLIENT.stopContainer(CONTAINER,5 );
        HttpStatus status = CLIENT.waitContainer(CONTAINER);
        assertEquals(200,status.code());
    }
}
