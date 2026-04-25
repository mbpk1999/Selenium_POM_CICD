package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InitialClass {

    public static final String HIGHLIGHT_SCRIPT_JS = "arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');";
    public static final String REMOVE_SCRIPT_JS = "arguments[0].removeAttribute('style');";
    public WebDriver driver;
    public JavascriptExecutor js;
    public WebDriverWait wait;
    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    // Setter for the driver
    public void setDriver(WebDriver driver) {
        threadLocalDriver.set(driver);
    }

    // Getter for the driver
    public WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    public InitialClass(WebDriver driver)
    {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public InitialClass()
    {
        //Empty Constructor - used by DemoWorkShopBaseClass
    }

    public void browserFactory(String browser, String url)
    {
        WebDriver locaWebDriver = null;
        if(browser.equalsIgnoreCase("chrome"))
        {
            WebDriverManager.chromedriver().setup();
            locaWebDriver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            locaWebDriver = new EdgeDriver();
        }
        else
        {
            System.out.println("Invalid Browser Option: "+browser);
        }
        if(locaWebDriver!=null)
        {
            threadLocalDriver.set(locaWebDriver);
        }
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        getDriver().get(url);
        js = (JavascriptExecutor) getDriver();
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
    }

    public void genericWaitAndHighLightByLocator(By by) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        wait.until(ExpectedConditions.elementToBeClickable(by));
        js.executeScript(HIGHLIGHT_SCRIPT_JS, driver.findElement(by));
        Thread.sleep(500);
        removeHighLight(driver.findElement(by));
    }

    private void removeHighLight(WebElement element) {
        js.executeScript(REMOVE_SCRIPT_JS, element);
    }

    public void clickOnElementByLocator(By by)
    {
        try {
            genericWaitAndHighLightByLocator(by);
            driver.findElement(by).click();
        } catch (InterruptedException e) {
            throw new RuntimeException("Error in clickOnELementByLocator() method: "+e);
        }
    }

    public String getElementTextByLocator(By by) throws RuntimeException
    {
        String text = null;
        try
        {
            genericWaitAndHighLightByLocator(by);
            text = driver.findElement(by).getText();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error in getElementTextByLocator() method: "+e);
        }
        return text;
    }

    public void sendKeysByLocator(By by, String text)
    {
        try {
            genericWaitAndHighLightByLocator(by);
            driver.findElement(by).sendKeys(text);
        } catch (InterruptedException e) {
            throw new RuntimeException("Error in sendKeysByLocator() method: "+e);
        }
    }
}
