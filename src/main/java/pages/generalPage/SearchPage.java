package pages.generalPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utilities.InitialClass;

import java.util.List;

public class SearchPage extends InitialClass {
    By noResultMsg = By.xpath("//*[contains(text(),'No products were found that matched your criteria.')]");
    By searchProduct = By.xpath("//div[@class='item-box']//a");
    By productQuantity = By.xpath("//input[@class='qty-input']");
    By addToCartProduct = By.xpath("//div[@class='overview']//input[@value='Add to cart']");
    By addedCartMsg = By.xpath("//p[@class='content']");
    By shoppingCartLink = By.xpath("//span[contains(text(),'Shopping cart')]");

    public SearchPage(WebDriver driver)
    {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean checkForNoSearchResult()
    {
        return checkElementVisibleByLocator(noResultMsg, "noResultMsg");
    }


    public boolean goToProduct(String item)
    {
        List<WebElement> relatedProductList = getDriver().findElements(searchProduct);
        for(WebElement productElement: relatedProductList)
        {
            if(productElement.getText().equalsIgnoreCase(item))
            {
                clickOnElement(productElement, item);
                return true;
            }
        }
        return false;
    }

    public String addProductToCart(String quantity)
    {
        try {
            getDriver().findElement(productQuantity).clear();
            sendKeysByLocator(productQuantity, "productQuantity", quantity);
            clickOnElementByLocator(addToCartProduct, "addToCartProduct");
            return getElementTextByLocator(addedCartMsg, "addedCartMsg");
        } catch (Exception e) {
            throw new RuntimeException("Error with addProductToCart() method");
        }
    }

    public ShoppingCartPage goToCart()
    {
        clickOnElementByLocator(shoppingCartLink, "shoppingCartLink");
        return new ShoppingCartPage(driver);
    }
}
