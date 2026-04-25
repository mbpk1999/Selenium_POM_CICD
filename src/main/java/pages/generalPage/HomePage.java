package pages.generalPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utilities.DemoWorkShopBaseClass;
import utilities.InitialClass;

public class HomePage extends InitialClass {
    By registerLink = By.xpath("//a[text()='Register']");
    By logInLink = By.xpath("//a[text()='Log in']");
    By shoppingCartLink = By.xpath("//span[contains(text(),'Shopping cart')]");
    By wishListLink = By.xpath("//span[contains(text(),'Wishlist')]");
    By logOutLink = By.xpath("//a[text()='Log out']");

    public HomePage(WebDriver driver)
    {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public RegisterPage navigateToRegisterPage()
    {
        clickOnElementByLocator(registerLink);
        return new RegisterPage(driver);
    }

    public void logOut() throws InterruptedException {
        clickOnElementByLocator(logOutLink);
        Thread.sleep(3000);
    }

}
