package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class NewThreshold extends BasePage {
	
	final WebDriver driver;
	
	@FindBy (how = How.ID, using = "CF00Nc0000001pWcV")
	private WebElement device;
	
	@FindBy (how = How.ID, using = "CF00Nc0000001pWca")
	private WebElement status;
	
	@FindBy (how = How.ID, using = "00Nc0000001pWdd")
	private WebElement applyRange;
	
	@FindBy (how = How.ID, using = "00Nc0000001pWdf")
	private WebElement maximum;
	
	@FindBy (how = How.ID, using = "00Nc0000001pWdg")
	private WebElement minimum;
	
	@FindBy (how = How.ID, using = "00Nc0000001pWde")
	private WebElement active;
	
	@FindBy (how = How.ID, using = "00Nc0000001pWdh_unselected")
	private WebElement leftValue;
	
	@FindBy (how = How.ID, using = "00Nc0000001pWdh_selected")
	private WebElement rightValue;
	
	@FindBy (how = How.ID, using = "00Nc0000001pWdh_right_arrow")
	private WebElement rightArrow;
	
	@FindBy (how = How.ID, using = "00Nc0000001pWdh_left_arrow")
	private WebElement leftArrow;
	
	@FindBy (how = How.NAME, using = "save")
	private WebElement save;
	
	@FindBy (how = How.NAME, using = "save_new")
	private WebElement saveNew;
	
	@FindBy (how = How.NAME, using = "cancel")
	private WebElement cancel;
	
	public NewThreshold(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void createNewThreshold(String line, String estado, String max, String min) {
		device.sendKeys(line);
		status.sendKeys(estado);
		applyRange.click();
		maximum.sendKeys(max);
		minimum.sendKeys(min);
		active.click();
		setElementFromList(leftValue, "Down", rightArrow);
		save.click();
	}
	
	public void modifyThreshold() {
		setElementFromList(leftValue, "Up", rightArrow);
		setElementFromList(rightValue, "Down", leftArrow);
		save.click();
	}
}
