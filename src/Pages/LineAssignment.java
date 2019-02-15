package Pages;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LineAssignment extends BasePage {
	
	final WebDriver driver;
	private WebDriverWait wait;

	@FindBy (how = How.ID, using = "SelectProvincia")
	private WebElement selectState;
	
	@FindBy (how = How.ID, using = "SelectLocalidad")
	private WebElement selectCity;
	
	@FindBy (how = How.ID, using = "Sufijo")
	private WebElement suffix;
	
	@FindBy (how = How.ID, using = "CambiarNumero")
	private WebElement search;
	
	@FindBy (how = How.CSS, using = ".slds-button__icon.slds-button__icon--small")
	private WebElement arrow;
	
	@FindBy (how = How.CLASS_NAME, using = "slds-checkbox--faux")
	private List<WebElement> checkboxes;
	
	@FindBy (how = How.CSS, using = ".vlc-slds-button--tertiary.ng-binding.ng-scope")
	private List<WebElement> buttons;
	
	@FindBy (how = How.CLASS_NAME, using = "ng-binding")
	private List<WebElement> next;
	
	@FindBy (how = How.CSS, using = ".slds-form-element__label.slds-text-color--error.ng-binding.ng-scope")
	private WebElement noLineAvailableMessage;
	
public LineAssignment(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait (driver, 10);
	    PageFactory.initElements(driver, this);	
}

public void searchLineAvailable(String state, String city, String sufijo) {
	setSimpleDropdown(selectState, state);
	setSimpleDropdown(selectCity, city);
	suffix.sendKeys(sufijo);
	search.click();
}

public String getNoLineMessage() {
	arrow.click();
	try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	return noLineAvailableMessage.getText();
}

public void cancelLineAssignment() {
	getElementFromList(buttons, "Cancelar").click();
	Alert alert = driver.switchTo().alert();
    alert.accept();
	try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
    driver.findElement(By.id("ext-gen25")).click();
	try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
}

public void clickOnNext() {
	WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("LineAssignment_nextBtn")));
    ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+ nextButton.getLocation().y+")");
    nextButton.click();
}


}
