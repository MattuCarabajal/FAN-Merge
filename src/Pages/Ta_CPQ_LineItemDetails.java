package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Ta_CPQ_LineItemDetails extends BasePage {
	
final WebDriver driver;

@FindBy (how = How.NAME, using = "productconfigmodal802c00000009fsEAAQ_field_1_1")
private WebElement gamaSelect;

@FindBy (how = How.NAME, using = "productconfigmodal802c00000009fsEAAQ_field_1_2")
private WebElement warrantyPeriodSelect;

@FindBy (how = How.NAME, using = "productconfigmodal802c00000009fsEAAQ_field_1_3")
private WebElement protectionTypeSelect;

@FindBy (how = How.NAME, using = "productconfigmodal802c00000009fsEAAQ_field_1_4")
private WebElement model;

@FindBy (how = How.NAME, using = "productconfigmodal802c00000009fsEAAQ_field_1_5")
private WebElement colour;

@FindBy (how = How.NAME, using = "productconfigmodal802c00000009fsEAAQ_field_1_6")
private WebElement screenSize;







public Ta_CPQ_LineItemDetails(WebDriver driver){
	this.driver = driver;
    PageFactory.initElements(driver, this);
}
}