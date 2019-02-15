package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class PaymentMethod extends BasePage{
	final WebDriver driver;

	@FindBy (how = How.ID, using = "PaymentMethodRadio")
	private List<WebElement> paymentMethod;
	
	@FindBy (how = How.CSS, using = ".vlc-slds-button--tertiary.ng-binding.ng-scope")
	private List<WebElement> buttons;
	
	@FindBy (how = How.CLASS_NAME, using = "ng-binding")
	private List<WebElement> nextAndPrevious;
	
public PaymentMethod(WebDriver driver) {
		this.driver = driver;
	    PageFactory.initElements(driver, this);	
}

public void clickOnNext() {
	getElementFromList(nextAndPrevious, "Siguiente").click();
}

public Boolean getPaymentMethod() {
	Boolean a = false;
	if (paymentMethod.get(0).getText().contains("Efectivo")) {
		a = true;
	}
	return a;
}

public Boolean isPaymentMethodPresent() {
	Boolean a = false;
	if (paymentMethod.size() != 0) {
		a = true;
	}
	return a;
}

public boolean selectDebitoAProximaFactura() {
	driver.findElement(By.id("PaymentMethodRadio")).click();
	try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	try {
		driver.findElement(By.xpath("//*[@id=\"PaymentMethods\"]/div/div/div[2]/child[1]/div/div/ng-form/div[2]/div[1]/ul/li[2]")).click();
		return true;
	}
	catch(Exception e) {
		return false;
	}
}
}
