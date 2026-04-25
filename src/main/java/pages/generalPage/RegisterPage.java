package pages.generalPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utilities.DemoWorkShopBaseClass;
import utilities.InitialClass;

public class RegisterPage extends InitialClass {
    By genderRadioMale = By.xpath("//input[@id='gender-male']");
    By genderRadioFemale = By.xpath("//input[@id='gender-female']");
    By firstNameTxtBx = By.xpath("//input[@id='FirstName']");
    By lastNameTxtBx = By.xpath("//input[@id='LastName']");
    By emailIDTxtBx = By.xpath("//input[@id='Email']");
    By passwordTxtBx = By.xpath("//input[@id='Password']");
    By confirmPasswordTxtBx = By.xpath("//input[@id='ConfirmPassword']");
    By registerBtn = By.xpath("//input[@id='register-button']");
    By registerSuccessMsg = By.xpath("//div[@class='result']");//Msg: Your registration completed
    By continueBtn = By.xpath("//input[@value='Continue']");

    public RegisterPage(WebDriver driver)
    {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public String registerNewUser(String gender, String firstName, String lastName, String emailID, String password)
    {
        String msg = null;
        selectGender(gender);
        sendKeysByLocator(firstNameTxtBx, firstName);
        sendKeysByLocator(lastNameTxtBx, lastName);
        sendKeysByLocator(emailIDTxtBx, emailID);
        sendKeysByLocator(passwordTxtBx, password);
        sendKeysByLocator(confirmPasswordTxtBx, password);
        clickOnElementByLocator(registerBtn);

        msg =  getElementTextByLocator(registerSuccessMsg);
        clickOnElementByLocator(continueBtn);

        return msg;
    }

    private void selectGender(String gender) {
        if(gender.equalsIgnoreCase("male"))
        {
            clickOnElementByLocator(genderRadioMale);
        }
        else
        {
            clickOnElementByLocator(genderRadioFemale);
        }
    }

}
