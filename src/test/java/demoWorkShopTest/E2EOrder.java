package demoWorkShopTest;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.generalPage.*;
import utilities.DemoWorkShopBaseClass;

public class E2EOrder extends DemoWorkShopBaseClass {
    HomePage homePage;
    LoginPage loginPage;
    SearchPage searchPage;
    ShoppingCartPage shoppingCartPage;
    CheckoutPage checkoutPage;
    SoftAssert sf = new SoftAssert();
    String userEmail = "naruto123@gmail.com";
    String password = "xxxxxxx";
    String product = "Phone Cover";
    String quantity = "2";

    @Test
    public void orderE2E()
    {
        try {
            homePage = customerLogin(userEmail, password);
            shoppingCartPage = addProductToCart(product, quantity);
            checkoutPage = shoppingCartPage.checkOutCart();
            goToSleep(3);
            int orderID = checkoutAndCreateOrder();
            System.out.println("Order Created successfully with Order ID: " + orderID);
            homePage = checkoutPage.navigateToHomePage();
            homePage.logOut();
            sf.assertAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    private int checkoutAndCreateOrder() throws InterruptedException {
        checkoutPage.checkoutAndConfirmOrder();
        return getOrderID();

    }

    private int getOrderID() {
        sf.assertEquals("Your order has been successfully processed!", checkoutPage.getOrderMsg().trim(), "Mismatch in Order Successful Message");
        int orderID = checkoutPage.getOrderID();
        return orderID;
    }

    private ShoppingCartPage addProductToCart(String product, String quantity) throws InterruptedException {
        searchPage = homePage.searchItem(product);
        goToSleep(3);
        Assert.assertFalse(searchPage.checkForNoSearchResult(), "Invalid Search Result");
        Assert.assertTrue(searchPage.goToProduct(product), "Issue with the product");
        sf.assertEquals(searchPage.addProductToCart(quantity), "The product has been added to your shopping cart");
        return searchPage.goToCart();
    }

    private HomePage customerLogin(String email, String password) {
        homePage = new HomePage(getDriver());
        loginPage = homePage.navigateToLoginPage();
        homePage = loginPage.login(email, password);
        sf.assertTrue(homePage.checkCustomerLoggedIn(), "Customer is not Logged In");
        return new HomePage(getDriver());
    }
}
