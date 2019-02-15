package Pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class ValidationThroughIdentityDocument extends BasePage{
	
	final WebDriver driver;
	
	@FindBy (how = How.CLASS_NAME, using = "slds-checkbox--faux")
	private WebElement doesNotPossesDocument;
	
	@FindBy (how = How.ID, using = "FileDocumentImage")
	private WebElement documentFile;
	
	@FindBy (how = How.CLASS_NAME, using = "ng-binding")
	private List<WebElement> buttons;
	
	@FindBy (how = How.CSS, using = ".vlc-slds-button--tertiary.ng-binding.ng-scope")
	private WebElement cancel;
	
public ValidationThroughIdentityDocument(WebDriver driver){
		this.driver = driver;
        PageFactory.initElements(driver, this);
}

public void setDocument(String path) {
	documentFile.sendKeys(path);
	buttons.get(1).click();
}

}
