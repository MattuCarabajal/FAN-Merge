package Pages;


import org.openqa.selenium.support.FindBy;

import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ContactMotiveManager extends BasePage{
	//NOTA: Modificar Motivo de Contacto, y Nuevo Motivo de Contacto, tienen los mismos elementos. 07/09/2017
	WebDriver driver;
	
	@FindBy (how = How.ID, using ="Name")
	private WebElement contactMotiveName; //input.
	
	@FindBy (how = How.ID , using ="00Nc0000001pWdl")
	private WebElement descripcion; //input. id doesnt change.
	
	@FindBy (how = How.ID , using ="00Nc0000001pWdn")
	private WebElement activo; //check. id doesnt change.
	
	@FindBy (how = How.ID , using ="00Nc0000001pWdp")
	private WebElement asociableAIncidente; //check. id doesnt change.	
	
	@FindBy (how = How.ID , using ="CF00Nc000000247mX")
	private WebElement servicio; //input. id doesnt change.

	@FindBy (how = How.NAME , using ="save")
	private WebElement saveBtn; //button. id doesnt change.
	
	@FindBy (how = How.NAME , using ="cancel")
	private WebElement cancelBtn; //button. id doesnt change.
	
	public ContactMotiveManager(WebDriver driver){
		this.driver = driver;
		driver.switchTo().defaultContent(); //this is in mainPage, so no iframes.
        PageFactory.initElements(driver, this);
	}
	
	public WebElement getContactMotiveName() {
		return contactMotiveName;
	}

	public WebElement getDescripcion() {
		return descripcion;
	}
	
	public WebElement getActivoCheck() {
		return activo;
	}
	
	public WebElement getAsociableAIncidente() {
		return asociableAIncidente;
	}
	
	public WebElement getServicio() {
		return servicio;
	}
	
	public WebElement getSaveBtn() {
		return saveBtn;
	}
	
	public void save() {
		saveBtn.click();
	}
	
	public void cancel() {
		cancelBtn.click();
	}

	public void clearValues() {
		// TODO Auto-generated method stub
		getContactMotiveName().clear();
		getDescripcion().clear();
		getServicio().clear();
	}
	
}
