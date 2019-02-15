package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class ParametrizationThresholds extends BasePage {


	final WebDriver driver;
		
	@FindBy (how = How.NAME, using = "new")
	private WebElement newThreshold;
	
	public ParametrizationThresholds(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
public void clickOnNewThreshold() {
	newThreshold.click();
}

}
