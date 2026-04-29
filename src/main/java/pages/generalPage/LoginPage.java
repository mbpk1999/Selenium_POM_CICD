package pages.generalPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utilities.InitialClass;

public class LoginPage extends InitialClass {
    By loginEmailTxtBx = By.xpath("//*[@id='Email']");
    By loginPasswordTxtBx = By.xpath("//*[@id='Password']");
    By logInBtn = By.xpath("//*[@value='Log in']");

    public LoginPage(WebDriver driver)
    {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public HomePage login(String emailID, String password)
    {
        sendKeysByLocator(loginEmailTxtBx, "loginEmailTxtBx", emailID);
        sendKeysByLocator(loginPasswordTxtBx, "loginPasswordTxtBx", password);
        clickOnElementByLocator(logInBtn, "logInBtn");
        return new HomePage(driver);
    }
}
