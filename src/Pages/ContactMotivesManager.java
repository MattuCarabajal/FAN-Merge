package Pages;


import org.openqa.selenium.support.FindBy;

import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ContactMotivesManager extends BasePage{
	
	//page_link = https://cs14.salesforce.com/a41?fcf=00Bc0000001LRmb&rolodexIndex=-1&page=1
	//Lista de todos los motivos.
	
	WebDriver driver;
	
	@FindBy (how = How.CLASS_NAME, using ="x-grid3-scroller")
	private WebElement motivesList; //input.
	
	public ContactMotivesManager(WebDriver driver){
		this.driver = driver;
		driver.switchTo().defaultContent();//this is in mainPage, so no iframes.
        PageFactory.initElements(driver, this);
	}
	
	public WebElement getMotivesWrapper() {
		return motivesList;
	}
	
	public WebElement getMotiveByName(String motiveName) {
		//note this function only return the first occurrence
		List<WebElement> motives = getMotivesWrapper().findElements(By.className("x-grid3-row"));
		String currentMotiveName = "";
		for (WebElement motive : motives) {
			currentMotiveName = motive.findElements(By.className("x-grid3-col")).get(4).getText();//index 4 is the index.
			if (currentMotiveName.equals(motiveName)) {
				return motive;
			}
		}
		return null;
	}

	public void modifyMotiveByName(String motiveName) {
		WebElement motive = getMotiveByName(motiveName);
		//nested element, there are 2 "a" inside this grid column. modificar and eliminar
		motive.findElements(By.className("x-grid3-col")).get(2).findElements(By.tagName("a")).get(0).click();//0 is the index for "Modificar"
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		}catch(NoAlertPresentException noAlertExcept) {
			//does Nothing.
		}
	}
	
	public void deleteMotiveByName(String motiveName) {
		WebElement motive = getMotiveByName(motiveName);
		//nested element, there are 2 "a" inside this grid column. modificar and eliminar
		motive.findElements(By.className("x-grid3-col")).get(2).findElements(By.tagName("a")).get(1).click();//1 is the index for "Eliminar"
		try {Thread.sleep(1500);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		}catch(NoAlertPresentException noAlertExcept) {
			//does Nothing.
		}
	}
	
	public void openMotiveByName(String motiveName) {
		WebElement motive = getMotiveByName(motiveName);
		//The index for the name is 4, has "a" element
		motive.findElements(By.className("x-grid3-col")).get(4).findElement(By.tagName("a")).click();
	}
	
	public void seleccionarListado(String nombreL) {
		//driver.switchTo().defaultContent();
		//driver.switchTo().frame(getFrameForElement(driver, By.cssSelector(".topNav.primaryPalette")));
		Select listSelect = new Select(driver.findElement(By.cssSelector(".topNav.primaryPalette")).findElement(By.tagName("select")));
		listSelect.selectByVisibleText(nombreL);
	}
	
	public void crearMotivoDeContacto(String nombre) {
		driver.findElement(By.className("listButtons")).findElement(By.tagName("input")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("Name")).sendKeys(nombre);
		driver.findElement(By.className("pbButton")).findElement(By.tagName("input")).click();
	}
	
	public void activarMotivoDesdeDetalle() {
		WebElement activo = driver.findElements(By.cssSelector(".dataCol.col02.inlineEditWrite")).get(2);
		Actions action = new Actions(driver); 
		action.moveToElement(activo).doubleClick().perform();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		activo.findElement(By.tagName("input")).click();
		driver.findElement(By.id("topButtonRow")).findElement(By.tagName("input")).click();
	}
	
	public void eliminarPrimerSintoma() {
		driver.findElement(By.cssSelector(".x-grid3-col.x-grid3-cell.x-grid3-td-ACTION_COLUMN")).findElements(By.tagName("a")).get(1).click();
		Alert alert = driver.switchTo().alert();
		System.out.println(alert.getText());
		alert.accept();
	}
	
}
