package Pages;


import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;


public class ValidateByDniNumber {
	
	final WebDriver driver;
	
	@FindBy (how = How.ID, using = "DocumentMethod_prevBtn")
	private WebElement prev;
	
	@FindBy (how = How.ID, using = "DocumentMethod_nextBtn")
	private WebElement next;
	
	@FindBy (how = How.CLASS_NAME, using = "slds-m-bottom--small")
	private List<WebElement> filesUploaded;
	
	@FindBy (how = How.ID, using = "FileDocumentImage")
	private WebElement img;
	
	@FindBy (how = How.CLASS_NAME, using = "vlc-slds-button--tertiary ng-binding ng-scope")
	private WebElement cancel;
	
	public ValidateByDniNumber(WebDriver driver){
		this.driver = driver;
        PageFactory.initElements(driver, this);
}
	
	public void validationDniNumber()
	{
		img.sendKeys("C:\\Users\\Sofia Chardin\\Desktop");
		next.click();
	}
	public List<WebElement> getFilesUploaded() {
		return filesUploaded;
	}

}
