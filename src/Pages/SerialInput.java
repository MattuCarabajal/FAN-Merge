package Pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class SerialInput extends BasePage {
	
	final WebDriver driver;

	@FindBy (how = How.XPATH, using = "//*[@id=\"ICCIDConfiguration\"]/div/ng-include/div/table/tbody/tr/td[3]/div/input")
	private WebElement iccd;
	
	@FindBy (how = How.CSS, using = ".vlc-slds-button--tertiary.ng-binding.ng-scope")
	private List<WebElement> buttons;
	
	@FindBy (how = How.CLASS_NAME, using = "ng-binding")
	private List<WebElement> assignNextAndPrevious;
	
public SerialInput(WebDriver driver) {
		this.driver = driver;
	    PageFactory.initElements(driver, this);	
}

public void setICCD(String serial) {
	iccd.sendKeys(serial);
}

public void clickOnValidateICCD() {
	assignNextAndPrevious.get(71).click();
}

public String getAssignButtonLabel() {
	return assignNextAndPrevious.get(71).getText();
}

public String getValidationMessage(String format) {
	ArrayList<String> str = new ArrayList<String>();
	switch(format) {
	case "wrong":
	str.add(driver.findElement(By.cssSelector(".textWrapped.ng-binding.slds-text-color--error")).getText());
	break;
	case "right":
	str.add(driver.findElement(By.cssSelector(".textWrapped.ng-binding")).getText());
	break;
	}
return str.get(0);
}

public void clickOnNext() {
	getElementFromList(assignNextAndPrevious, "Siguiente").click();
}

}
