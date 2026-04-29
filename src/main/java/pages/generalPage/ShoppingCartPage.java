package pages.generalPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utilities.DemoWorkShopBaseClass;
import utilities.InitialClass;

public class ShoppingCartPage extends InitialClass {

    By termsOfServiceChkBox = By.xpath("//input[@id='termsofservice']");
    By checkoutBtn = By.xpath("//button[@id='checkout']");

    public ShoppingCartPage(WebDriver driver)
    {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public CheckoutPage checkOutCart()
    {
        clickOnElementByLocator(termsOfServiceChkBox, "termsOfServiceChkBox");
        clickOnElementByLocator(checkoutBtn, "checkoutBtn");
        return new CheckoutPage(driver);
    }
}
