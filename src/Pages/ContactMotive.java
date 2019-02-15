package Pages;


import org.openqa.selenium.support.FindBy;

import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ContactMotive extends BasePage{
	//NOTA: Modificar Motivo de Contacto, y Nuevo Motivo de Contacto, tienen los mismos elementos. 07/09/2017
	WebDriver driver;
	
	@FindBy (how = How.XPATH, using = "//*[@value=\'Nuevo caso\']")
	private WebElement newCaseBtn; //button
	
	public ContactMotive(WebDriver driver){
		this.driver = driver;
		driver.switchTo().defaultContent(); //this is in mainPage, so no iframes.
        PageFactory.initElements(driver, this);
	}
	
	public void newCase() {
		newCaseBtn.click();
	}
	
}
