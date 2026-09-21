package utilities;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

public class DemoWorkShopBaseClass extends InitialClass{

    String browser = "chrome";
    String url = "https://demowebshop.tricentis.com/";

    @BeforeMethod
    public void beginMethod()
    {
        System.out.println("Starting Browser...!");
        browserFactory(browser, url);
    }

    @AfterMethod
    public void endMethod()
    {
        System.out.println("Closing Browser...!");
        getDriver().close();
    }
}
