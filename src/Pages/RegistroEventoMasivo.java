package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


public class RegistroEventoMasivo {


	final WebDriver driver;
	
	
	//¿De qué tipo de incidente se trata?
	
	@FindBy (how = How.ID, using = "SelectType")
	private WebElement SelectType;
	
	@FindBy (how = How.ID, using = "SelectSubType")
	private WebElement SelectSubType;
	
	@FindBy (how = How.ID, using = "SelectChannel")
	private WebElement SelectChannel;
	
	@FindBy (how = How.ID, using = "SelectIncident")
	private WebElement SelectIncident;
	
	//¿Por cuánto tiempo va a persistir el inconveniente?
	
	@FindBy (how = How.ID, using = "StartDate")
	private WebElement StartDate;
	
	@FindBy (how = How.ID, using = "EndDate")
	private WebElement EndDate;
	
	//¿En qué estado se encuentra?
	
	@FindBy (how = How.ID, using = "SelectStatus")
	private WebElement SelectStatus;
	
	//Descripción del evento:
	
	@FindBy (how = How.ID, using = "TextIncidentTitle")
	private WebElement TextIncidentTitle;
	
	@FindBy (how = How.ID, using = "SelectImpact")
	private WebElement SelectImpact;
	
	//Detalles del incidente:
	
	@FindBy (how = How.ID, using = "TextDetailIncident")
	private WebElement TextDetailIncident;
	
	@FindBy (how = How.ID, using = "FileDetailIncident")
	private WebElement FileDetailIncident;
	
	//Cliente afectado:
	
	@FindBy (how = How.ID, using = "TextAffectedCustomer")
	private WebElement TextAffectedCustomer;
	
	//Procedimiento
	
	@FindBy (how = How.ID, using = "TextProcedure")
	private WebElement TextProcedure;
	
	@FindBy (how = How.ID, using = "FileProcedure")
	private WebElement FileProcedure;
	
	//Speech
	
	@FindBy (how = How.ID, using = "TextSpeech")
	private WebElement TextSpeech;
	
	
	
	
	public boolean VerificarObligatorio(By lookFor) {
	
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, lookFor));
		WebElement elemento = driver.findElement(lookFor);
	    return (elemento.getAttribute("required").equals("true"));
	  
	}
	
	public void llenarListas(By lookfor) {
		
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, lookfor));
		List<WebElement> opDesplegables = driver.findElements(By.xpath("//select"));
		opDesplegables.remove(driver.findElement(lookfor));
		for (WebElement opDesplegable : opDesplegables) {
			Select opDesplegableSelect = new Select (opDesplegable);
			opDesplegableSelect.selectByIndex(1);
		}
	}
	
	
	public void llenarTextos(By lookfor) {
		
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, lookfor));
		List<WebElement> textFields = driver.findElements(By.xpath("//textarea"));
		for (WebElement textField : textFields) {
			textField.sendKeys("TESTING QA");;
		}
		
		 driver.findElement(By.id("StartDate")).sendKeys("24/10/2017 16:00");
	}
	
	
	public void registrarExcepto(By lookfor) {
		
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, lookfor));
		
		//Llena los selects
		List<WebElement> opDesplegables = driver.findElements(By.xpath("//select"));
		opDesplegables.remove(driver.findElement(lookfor));
		for (WebElement opDesplegable : opDesplegables) {
			Select opDesplegableSelect = new Select (opDesplegable);
			opDesplegableSelect.selectByIndex(1);
		}
		//llena los campos de textos
		List<WebElement> textFields = driver.findElements(By.xpath("//textarea"));
		for (WebElement textField : textFields) {
			textField.sendKeys("TESTING QA");;
		}
		
		//llena las horas
		 driver.findElement(By.id("StartDate")).sendKeys("24/10/2017 16:00");
		//llenar incidentes
		 driver.findElement(By.id("TextIncidentTitle")).sendKeys("Testing QA");
		
	}
	
public void registrarExceptoIncidente(By lookfor) {
		
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, lookfor));
		
		//Llena los selects
		List<WebElement> opDesplegables = driver.findElements(By.xpath("//select"));
		for (WebElement opDesplegable : opDesplegables) {
			Select opDesplegableSelect = new Select (opDesplegable);
			opDesplegableSelect.selectByIndex(1);
		}
		//llena los campos de textos
		List<WebElement> textFields = driver.findElements(By.xpath("//textarea"));
		for (WebElement textField : textFields) {
			textField.sendKeys("TESTING QA");;
		}
		//llena las horas
		 driver.findElement(By.id("StartDate")).sendKeys("24/10/2017 16:00");	
	}
	
	
	
	public void ClickOnSave() {
	    Accounts accPage = new Accounts(driver);
	    try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    driver.switchTo().frame(accPage.getFrameForElement(driver, By.id("InputValues_nextBtn")));
	    ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+ driver.findElement(By.id("InputValues_nextBtn")).getLocation().y+")");
	      driver.findElement(By.id("InputValues_nextBtn")).click();
	  }
	
	public boolean validarFecha(String fecha, String Formato) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat(Formato);
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
	
	public RegistroEventoMasivo(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	

}
