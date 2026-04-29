package pages.generalPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
    By customerMailIDLink = By.xpath("//div[@class='header-links']//a[@class='account']");
    By searchField = By.xpath("//*[@value='Search store']");


    public HomePage(WebDriver driver)
    {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public RegisterPage navigateToRegisterPage()
    {
        clickOnElementByLocator(registerLink, "Register Link");
        return new RegisterPage(driver);
    }

    public LoginPage navigateToLoginPage()
    {
        clickOnElementByLocator(logInLink, "logInLink");
        return new LoginPage(driver);
    }

    public boolean checkCustomerLoggedIn()
    {
        return checkElementVisibleByLocator(customerMailIDLink, "customerMailIDLink");
    }

    public SearchPage searchItem(String item)
    {
        sendKeysCharByChar(searchField, "searchField", item);
        sendKeysByLocator(searchField, "searchField", String.valueOf(Keys.ENTER));
        return new SearchPage(driver);
    }

    public void logOut() throws InterruptedException {
        clickOnElementByLocator(logOutLink, "LogOut Link");
        Thread.sleep(3000);
    }


}
