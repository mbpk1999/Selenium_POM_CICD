package demoWorkShopTest;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.generalPage.HomePage;
import pages.generalPage.RegisterPage;
import utilities.DemoWorkShopBaseClass;
import utilities.ExcelUtils;

public class RegisterUser extends DemoWorkShopBaseClass {

    ExcelUtils excelUtils = new ExcelUtils();
    HomePage homePage;
    RegisterPage registerPage;
    String expectedRegisterMsg = "Your registration completed";
    /*String gender = "male";
    String firstName = "Qwerty3";
    String lastName = "Hello";
    String email = "qwerty3@gello.com";
    String password = "qwerty3";*/
    @Test(dataProvider = "UserData")
    public void createUser(String firstName, String lastName, String gender, String email, String password)
    {
        try {
            System.out.println("Creating user..."+firstName+" "+lastName);
            SoftAssert sf = new SoftAssert();

            homePage  = new HomePage(getDriver());
            registerPage = homePage.navigateToRegisterPage();
            String actualRegisterMsg = registerPage.registerNewUser(gender, firstName,lastName, email, password);

            sf.assertEquals(actualRegisterMsg, expectedRegisterMsg, "Mismatch in Register Message");
            homePage.clickDWSLogo();
            sf.assertAll();
        } catch (InterruptedException e) {
            throw new RuntimeException("Error in createUser Test:"+e);
        }
    }

    @DataProvider(name = "UserData")
    public String[][] getTestData() {
        /*return new String[][]{
                {"Ramar", "Ram", "Male", "ramar1@gello.com", "ramar1@123"}
        };*/
        return excelUtils.readDataFromExcel("src/test/resources/DWS_UserRegister.xlsx", "UserCredentials");
    }
}
