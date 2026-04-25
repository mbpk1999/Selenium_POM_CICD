package utilities;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class DemoWorkShopBaseClass extends InitialClass{

    String browser = "chrome";
    String url = "https://demowebshop.tricentis.com/";

    @BeforeTest
    public void beginMethod()
    {
        System.out.println("Starting Browser...!");
        browserFactory(browser, url);
    }

    @AfterTest
    public void endMethod()
    {
        System.out.println("Closing Browser...!");
        getDriver().close();
    }
}
