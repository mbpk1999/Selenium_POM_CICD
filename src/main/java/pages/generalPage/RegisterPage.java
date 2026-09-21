package pages.generalPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utilities.DemoWorkShopBaseClass;
import utilities.InitialClass;

import java.util.List;

public class RegisterPage extends InitialClass {
    HomePage homePage =  new HomePage(getDriver());
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
    By errorMailMsg = By.cssSelector(".message-error li");

    public RegisterPage(WebDriver driver)
    {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public String registerNewUser(String gender, String firstName, String lastName, String emailID, String password) throws InterruptedException {
        String msg = null;
        selectGender(gender);
        sendKeysByLocator(firstNameTxtBx, "firstNameTxtBx", firstName);
        sendKeysByLocator(lastNameTxtBx,"lastNameTxtBx", lastName);
        sendKeysByLocator(emailIDTxtBx, "emailIDTxtBx", emailID);
        sendKeysByLocator(passwordTxtBx, "passwordTxtBx", password);
        sendKeysByLocator(confirmPasswordTxtBx, "confirmPasswordTxtBx", password);
        clickOnElementByLocator(registerBtn, "Register Button");
        boolean continueVisible = checkElementVisibleByLocator(continueBtn, "Continue Button");
        if (continueVisible) {
            // Success flow
            msg = getElementTextByLocator(registerSuccessMsg, "Registered Message Area");
            clickOnElementByLocator(continueBtn, "Continue Button");
            homePage.logOut();
        } else {
            // If Continue is not visible, try to locate and read the error message area
            List<WebElement> errorMsgList = getDriver().findElements(errorMailMsg);
            if (!errorMsgList.isEmpty()) {
                msg = getElementText(errorMsgList.getFirst(), "Error Message Area");
            } else {
                // Fallback: try to read the registration message (could be empty) to return something useful
                try {
                    msg = getElementTextByLocator(registerSuccessMsg, "Registered Message Area");
                } catch (Exception e) {
                    msg = "Not able to fetch error message"; // no useful message available
                }
            }
        }
        return msg;
    }

    private void selectGender(String gender) {
        if(gender.equalsIgnoreCase("male"))
        {
            clickOnElementByLocator(genderRadioMale, "genderRadioMale");
        }
        else
        {
            clickOnElementByLocator(genderRadioFemale, "genderRadioFemale");
        }
    }

}
