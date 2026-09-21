package demoWorkShopTest;

import org.testng.annotations.Test;
import pages.generalPage.HomePage;
import pages.generalPage.LoginPage;
import utilities.DemoWorkShopBaseClass;
import utilities.InvocationCounter;

import java.lang.reflect.Method;

public class LoginTest extends DemoWorkShopBaseClass {
    HomePage homePage;
    LoginPage loginPage;

    @Test(invocationCount = 3)
    public void testLogin(Method method) {
        try {
            int invocationCount = InvocationCounter.next(method.getName());
            homePage = new HomePage(getDriver());
            loginPage = homePage.navigateToLoginPage();
            loginPage.login("naruto123@gmail.com", "Naruto@123");
            System.out.println("Login Successful for user:"+invocationCount);
            homePage.logOut();
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while logging in"+e.getMessage());
        }
    }

    @Test(invocationCount = 3)
    public void testLogin2(Method method) {
        try {
            int invocationCount = InvocationCounter.next(method.getName());
            homePage = new HomePage(getDriver());
            loginPage = homePage.navigateToLoginPage();
            loginPage.login("naruto123@gmail.com", "Naruto@123");
            System.out.println("Login Successful for user:"+invocationCount);
            homePage.logOut();
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while logging in"+e.getMessage());
        }
    }
}
