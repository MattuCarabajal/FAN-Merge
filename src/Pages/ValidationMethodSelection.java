package Pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class ValidationMethodSelection extends BasePage{

	final WebDriver driver;
	
	@FindBy (how = How.CSS, using = ".slds-radio--faux.ng-scope")
	private List<WebElement> validations;
	
public ValidationMethodSelection(WebDriver driver){
		this.driver = driver;
        PageFactory.initElements(driver, this);
}

public void setValidationType(String type) {
	switch(type) {
	case "document":
		validations.get(2).click();
		break;
	case "questions":
		validations.get(3).click();
		break;
	}
}

}
