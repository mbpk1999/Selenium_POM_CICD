package demoWorkShopTest;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.generalPage.HomePage;
import pages.generalPage.RegisterPage;
import utilities.DemoWorkShopBaseClass;

public class RegisterUser extends DemoWorkShopBaseClass {

    HomePage homePage;
    RegisterPage registerPage;
    String expectedRegisterMsg = "Your registration completed";
    String gender = "male";
    String firstName = "Qwerty2";
    String lastName = "Hello";
    String email = "qwerty2@gello.com";
    String password = "Welcome123";
    @Test
    public void createUser()
    {
        try {
            System.out.println("Creating user...!");
            SoftAssert sf = new SoftAssert();

            homePage  = new HomePage(getDriver());
            registerPage = homePage.navigateToRegisterPage();
            String actualRegisterMsg = registerPage.registerNewUser(gender, firstName,lastName, email, password);

            sf.assertEquals(actualRegisterMsg, expectedRegisterMsg, "Mismatch in Register Message");
            homePage.logOut();
            sf.assertAll();
        } catch (InterruptedException e) {
            throw new RuntimeException("Error in createUser Test:"+e);
        }
    }
}
