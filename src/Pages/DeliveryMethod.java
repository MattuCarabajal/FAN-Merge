package Pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DeliveryMethod extends BasePage{
	
	final WebDriver driver;
	private WebDriverWait wait;

	@FindBy (how = How.ID, using = "DlvMethod")
	private WebElement deliveryMethod;
	
	@FindBy (how = How.CSS, using = ".vlc-slds-button--tertiary.ng-binding.ng-scope")
	private List<WebElement> buttons;
	
	@FindBy (how = How.CLASS_NAME, using = "ng-binding")
	private List<WebElement> nextAndPrevious;
	
	@FindBy (how = How.ID, using = "BillingCycle")
	private WebElement billingCycle;

	public DeliveryMethod(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait (driver, 10);
	    PageFactory.initElements(driver, this);	
}
	
public void clickOnNext() {
	WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("DeliveryMethodConfiguration_nextBtn")));
    ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+ nextButton.getLocation().y+")");
    nextButton.click();	
}
	
public String getCurrentValueForDeliveryMethod() {
	Select dropdown = new Select(deliveryMethod);
	 return dropdown.getFirstSelectedOption().getText();
}

public List<String> getBillingCycleOptions(){
	WebElement waitForOptionsUntilTheyAppear = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("label")));
	Select dropdownInvoicingCycle = new Select (billingCycle);
	List<String> invoicingCycleOptions = new ArrayList <String>();
	for (WebElement invoicingCycleOption:dropdownInvoicingCycle.getOptions()) {
		invoicingCycleOptions.add(invoicingCycleOption.getText());
	}
	return (invoicingCycleOptions);
}

public WebElement getBillingCycle() {return billingCycle;}
}
