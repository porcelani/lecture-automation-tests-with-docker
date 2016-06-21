package page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {

    @FindBy(name = "username")
    private WebElement username;

    @FindBy(name = "password")
    private WebElement password;

    @FindBy(name = "Login")
    private WebElement login;

    public void login(String usuario, String senha) {
        username.sendKeys(usuario);
        password.sendKeys(senha);
        login.click();

    }
}
