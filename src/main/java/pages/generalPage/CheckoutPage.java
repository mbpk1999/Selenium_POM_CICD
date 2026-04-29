package pages.generalPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utilities.DemoWorkShopBaseClass;
import utilities.InitialClass;

public class CheckoutPage extends InitialClass {

    By billingAddressContinueBtn = By.cssSelector("input[onclick*='Billing.save']");
    By pickUpFromStoreChkBx = By.id("PickUpInStore");
    By shippingAddressContinueBtn = By.cssSelector("input[onclick*='Shipping.save']");
    By paymentMethodContinueBtn = By.cssSelector("input[onclick*='PaymentMethod.save']");
    By paymentInfoContinueBtn = By.cssSelector("input[onclick*='PaymentInfo.save']");
    By confirmOrderBtn = By.cssSelector("input[onclick*='ConfirmOrder.save']");

    By orderSuccessMsg = By.xpath("//div[@class='title']");//Msg: Your order has been successfully processed!
    By orderDetailwithOrderID = By.xpath("//ul[@class='details']/li[1]");//Msg: Order number: xxxxx
    By continueBtn = By.xpath("//input[@value='Continue']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void checkoutAndConfirmOrder() throws InterruptedException {
        fluentClickOnElementByLocator(billingAddressContinueBtn, "billingAddressContinueBtn");
        fluentClickOnElementByLocator(pickUpFromStoreChkBx, "pickUpFromStoreChkBx");
        fluentClickOnElementByLocator(shippingAddressContinueBtn, "shippingAddressContinueBtn");
        fluentClickOnElementByLocator(paymentMethodContinueBtn, "paymentMethodContinueBtn");
        fluentClickOnElementByLocator(paymentInfoContinueBtn, "paymentInfoContinueBtn");
        goToSleep(3);
        scrollToElementByLocator(confirmOrderBtn, "confirmOrderBtn");
        fluentClickOnElementByLocator(confirmOrderBtn, "confirmOrderBtn");

//        waitAndClick(billingAddressContinueBtn);
//        waitAndClick(pickUpFromStoreChkBx);
//        waitAndClick(shippingAddressContinueBtn);
//        waitAndClick(paymentMethodContinueBtn);
//        waitAndClick(paymentInfoContinueBtn);
//        goToSleep(3);
//        scrollToElementByLocator(confirmOrderBtn, "confirmOrderBtn");
//        waitAndClick(confirmOrderBtn);
    }

    public String getOrderMsg()
    {
        return getElementTextByLocator(orderSuccessMsg, "orderSuccessMsg");
    }

    public int getOrderID()
    {
        String orderDetailAndID = getElementTextByLocator(orderDetailwithOrderID, "orderDetailwithOrderID");
        String orderID = orderDetailAndID.split(":")[1].trim();
        return Integer.parseInt(orderID);  // Parse the string to int and return it
    }

    public HomePage navigateToHomePage()
    {
        clickOnElementByLocator(continueBtn, "continueBtn");
        return new HomePage(driver);
    }
}
