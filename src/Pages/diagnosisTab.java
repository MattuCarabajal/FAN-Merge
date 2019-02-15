package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class diagnosisTab extends BasePage{
	
final WebDriver driver;

@FindBy (how = How.ID, using = "SelectedMotives")
private WebElement motives;

@FindBy (how = How.CLASS_NAME, using = "ng-binding")
private List<WebElement> buttons;

public diagnosisTab(WebDriver driver) {
	this.driver = driver;
	PageFactory.initElements(driver, this);
}

public void setMotive() {
	setSimpleDropdown(motives, "No me funciona internet");
}

public Boolean isExecuteButtonPresent() {
	Boolean a = false;
	if(buttons.get(14).getText().equals("Ejecutar")) {
		a = true;
	}
	return a;	
}

public void clickOnExeccute() {
	buttons.get(14).click();
}

	public void abrirListaDeServicio(WebDriver driver, String serv) {
		List<WebElement> servicios = driver.findElements(By.cssSelector(".addedValueServices-row.ng-pristine.ng-untouched.ng-valid.ng-empty"));
		for (WebElement UnS : servicios) {
			if(UnS.findElement(By.className("addedValueServices-card-td")).getText().equals(serv)) {
				UnS.findElement(By.className("addedValueServices-arrowWrapper")).click();
				break;
			}
		}
	}
	
	public void seleccionarMotivo(WebDriver driver, String motiv) {
		List<WebElement> wAsd = driver.findElements(By.cssSelector(".slds-radio.ng-scope"));
	     for (WebElement x:wAsd) {
	    	if (x.findElement(By.tagName("span")).getText().toLowerCase().contains(motiv)) {
	    		x.click();
	    		break;
	      }
	     }
	}
	
	public void irADiagnostico(WebDriver driver, String opcion, WebElement card) {
		BasePage cambioFrameByID=new BasePage();
		 card.findElement(By.className("card-info")).findElement(By.className("details")).click();
	     try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	     driver.switchTo().defaultContent();
	     driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-small-size--3-of-12.slds-medium-size--3-of-12.slds-large-size--3-of-12.flyout-actions")));
	     ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".console-flyout.active.flyout")).getLocation().y+")");
	     try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();} 
	     List<WebElement> gestiones = driver.findElement(By.className("community-flyout-actions-card")).findElements(By.tagName("a"));
	     for (WebElement UnaG : gestiones) {
	    	 System.out.println("opc: "+UnaG.findElement(By.tagName("span")).getText());
		    	if (UnaG.findElement(By.tagName("span")).getText().toLowerCase().contains(opcion)) {
		    		UnaG.click();
		    		break;
		      }
		     }
	    
	}
	
	public boolean seleccionarMotivoPorSelect(WebDriver driver, String motivo) {
		Select listSelect = new Select(driver.findElement(By.id("Motive")));
		listSelect.selectByVisibleText(motivo);
		return(listSelect.getFirstSelectedOption().getText().equals(motivo));
	}
	
	public void funcionoConfiguracion(WebDriver driver, String resp) {
		if (resp.toLowerCase().equals("si"))
			driver.findElement(By.id("SolutionValidationRadio|0")).findElement(By.cssSelector(".slds-radio--faux.ng-scope")).click();
		else
			driver.findElement(By.id("SolutionValidationRadio|0")).findElements(By.cssSelector(".slds-radio--faux.ng-scope")).get(1).click();
		
	}
	
}
