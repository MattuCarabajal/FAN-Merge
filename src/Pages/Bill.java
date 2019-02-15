package Pages;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Bill extends BasePage {
	
	final WebDriver driver;

	@FindBy (how = How.CSS, using = ".slds-table.slds-table--bordered.slds-table--cell-buffer.slds-table--col-bordered.ng-scope")
	private List<WebElement> billTable;
	
	@FindBy (how = How.CLASS_NAME, using = "ng-binding")
	private List<WebElement> assignNextAndPrevious;
	
	@FindBy (how = How.CSS, using = ".vlc-slds-button--tertiary.ng-binding.ng-scope")
	private List<WebElement> buttons;
	
public Bill(WebDriver driver) {
		this.driver = driver;
	    PageFactory.initElements(driver, this);	
}

public Boolean checkOrderStatusDisplays() {
	Boolean a = false;
	if (billTable.get(1).getText().contains("$")) {
		a = true;
	}
	return a;
}

public String getOrderStatus() {
	return assignNextAndPrevious.get(76).getText();
}

public void cancelLineAssignment() {
	getElementFromList(buttons, "Cancelar").click();
	Alert alert = driver.switchTo().alert();
    alert.accept();
	try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
    driver.findElement(By.id("ext-gen25")).click();
	try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
}
	
}
