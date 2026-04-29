package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InitialClass {

    public static final String HIGHLIGHT_SCRIPT_JS = "arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');";
    public static final String REMOVE_SCRIPT_JS = "arguments[0].removeAttribute('style');";
    public WebDriver driver;
    public JavascriptExecutor js;
    public WebDriverWait wait;
    public Wait<WebDriver> fluentWait;
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
        this.fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
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
        //getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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

    public void fluentWaitAndHighLightByLocator(By by)
    {
        WebElement element = fluentWait.until(ExpectedConditions.presenceOfElementLocated(by));
        fluentWait.until(ExpectedConditions.elementToBeClickable(by));
        js.executeScript(HIGHLIGHT_SCRIPT_JS, element);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException("Error in fluentWaitAndHighLightByLocator() method while sleeping after highlighting element: " + by + ". Details: " + e.getMessage());
        }
        removeHighLight(element);
    }

    public void waitAndClick(By locator) {
        // 1. Combined Wait: presence + visibility + enabled
        WebElement element = fluentWait.until(ExpectedConditions.elementToBeClickable(locator));

        // 2. Optional: Scroll & Highlight (from your previous method)
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

        // 3. Click the element directly
        element.click();
    }

    public void genericWaitAndHighLight(WebElement element) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        js.executeScript(HIGHLIGHT_SCRIPT_JS, element);
        Thread.sleep(500);
        removeHighLight(element);
    }

    private void removeHighLight(WebElement element) {
        js.executeScript(REMOVE_SCRIPT_JS, element);
    }

    public void clickOnElementByLocator(By by, String elementName)
    {
        try {
            genericWaitAndHighLightByLocator(by);
            driver.findElement(by).click();
        } catch (InterruptedException e) {
            throw new RuntimeException("Error in clickOnElementByLocator() method: " + elementName +
                    " [" + by + "]. Details: " + e.getMessage());
        }
    }

    public void fluentClickOnElementByLocator(By by, String elementName)
    {
        try {
            fluentWaitAndHighLightByLocator(by);
            driver.findElement(by).click();
        } catch (Exception e) {
            throw new RuntimeException("Error in fluentClickOnElementByLocator() method: " + elementName +
                    " [" + by + "]. Details: " + e.getMessage());
        }
    }

    public void clickOnElement(WebElement ele, String elementName)
    {
        try {
            genericWaitAndHighLight(ele);
            ele.click();
        } catch (InterruptedException e) {
            throw new RuntimeException("Error in clickOnElementByLocator() method: " + elementName +
                    " [" +ele+ "]. Details: " + e.getMessage());
        }
    }

    public String getElementTextByLocator(By by, String elementName) throws RuntimeException
    {
        String text = null;
        try
        {
            genericWaitAndHighLightByLocator(by);
            text = driver.findElement(by).getText();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error in getElementTextByLocator() method: " + elementName +
                    " [" + by + "]. Details: " + e.getMessage());
        }
        return text;
    }

    public void sendKeysByLocator(By by, String elementName, String text)
    {
        try {
            genericWaitAndHighLightByLocator(by);
            driver.findElement(by).sendKeys(text);
        } catch (InterruptedException e) {
            throw new RuntimeException("Error in sendKeysByLocator() method: " + elementName +
                    " [" + by + "]. Details: " + e.getMessage());
        }
    }

    public void sendKeysCharByChar(By by, String elementName, String text) {
        try {
            genericWaitAndHighLightByLocator(by);
            WebElement element = getDriver().findElement(by);
            element.clear(); // Good practice to clear before typing character by character

            for (char c : text.toCharArray()) {
                element.sendKeys(String.valueOf(c));
                // Optional: small sleep to simulate human typing speed
                Thread.sleep(100);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error in sendKeysCharByChar() method: " + elementName + " [" + by + "] while typing char-by-char. Details: " + e.getMessage());
        }
    }

    public boolean checkElementVisibleByLocator(By by, String elementName)
    {
        try {
            return driver.findElement(by).isDisplayed();
        } catch (Exception e) {
            System.out.println("Element not found or not visible: " + elementName + " [" + by + "]"+e.getMessage());
            return false;
        }
    }

    public void scrollToElementByLocator(By by, String elementName)
    {
        try {
            js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
        } catch (Exception e) {
            throw new RuntimeException("Error in scrollToElementByLocator() method "
                    + elementName + " [" + by + "]"+e.getMessage());
        }
    }

    public void goToSleep(int seconds) throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(seconds));
    }
}
