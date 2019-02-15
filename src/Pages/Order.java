package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Order extends SalesBase {
	
final WebDriver driver;
	
	@FindBy (how = How.NAME, using = "ta_cpq")
	private WebElement taCPQ;
	
	@FindBy (how = How.NAME, using = "edit")
	private WebElement edit;
	
	@FindBy (how = How.NAME, using = "del")
	private WebElement delete;
	
	@FindBy (how = How.ID, using = "CloneButton")
	private WebElement dupplicate;
	
	@FindBy (how = How.CSS, using = ".menuButtonMenuLink.firstMenuItem")
	private WebElement dupplicateWithProducts;
	
	@FindBy (how = How.CLASS_NAME, using = "menuButtonMenuLink")
	private WebElement dupplicateWithoutProducts;
	
public Order(WebDriver driver) {
		super(driver);
		this.driver = driver;
        PageFactory.initElements(driver, this);	
}

public void goToTaCPQ() {
	taCPQ.click();
}

}
