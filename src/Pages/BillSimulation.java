package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BillSimulation extends BasePage {
	
	final WebDriver driver;
	private WebDriverWait wait;

	@FindBy (how = How.CSS, using = ".vlc-slds-button--tertiary.ng-binding.ng-scope")
	private List<WebElement> buttons;
	
	@FindBy (how = How.CLASS_NAME, using = "ng-binding")
	private List<WebElement> nextAndPrevious;
	
public BillSimulation(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait (driver,15);
	    PageFactory.initElements(driver, this);	
}

public void clickOnNext() {
	WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("InvoicePreview_nextBtn")));
    ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+ nextButton.getLocation().y+")");
    nextButton.click();
}
}
