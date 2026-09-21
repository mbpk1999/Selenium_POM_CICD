package demoWorkShopTest;

import com.github.javafaker.Faker;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.generalPage.HomePage;
import pages.generalPage.RegisterPage;
import utilities.DemoWorkShopBaseClass;
import utilities.ExcelUtils;
import utilities.InvocationCounter;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class RegisterUser2 extends DemoWorkShopBaseClass {

    ExcelUtils excelUtils = new ExcelUtils();
    Faker faker = new Faker();
    HomePage homePage;
    RegisterPage registerPage;
    String filepath = "src/test/resources/DWS_UserRegister.xlsx";
    String expectedRegisterMsg = "Your registration completed";
    List<String> headerSet = Arrays.asList("First Name", "Last Name", "Gender", "Email", "Password", "Register Message");
    /*String gender = faker.options().option("male", "female");
    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    String email = faker.internet().emailAddress();
    String password = faker.internet().password();*/
    @Test(invocationCount = 1)
    public void createUser(Method method)
    {
        try {

            String gender = faker.options().option("male", "female");
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = faker.internet().emailAddress();
            String password = faker.internet().password();
            System.out.println(method.getName()+":"+ InvocationCounter.next(method.getName())+" Creating user..."+firstName+" "+lastName);
            SoftAssert sf = new SoftAssert();

            homePage  = new HomePage(getDriver());
            registerPage = homePage.navigateToRegisterPage();
            String actualRegisterMsg = registerPage.registerNewUser(gender, firstName,lastName, email, password);
            List<String> outputData = Arrays.asList(firstName, lastName, gender, email, password, actualRegisterMsg);
            sf.assertEquals(actualRegisterMsg, expectedRegisterMsg, "Mismatch in Register Message");
            homePage.clickDWSLogo();
            excelUtils.writeDataToExcelAppend(filepath, "DWS_RegisteredUser", headerSet, outputData);
            sf.assertAll();
        } catch (InterruptedException e) {
            throw new RuntimeException("Error in createUser "+method.getName()+" "+InvocationCounter.next(method.getName())+":"+e);
        }
    }

}
