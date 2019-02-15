package Tests;


import static org.testng.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.CasePage;
import Pages.CustomerCare;
import Pages.CustomerCasesManager;
import Pages.HomeBase;
import Pages.SelectCaseRegisterType;
import Pages.setConexion;

public class CustomerCareCaseManagement extends TestBase {

	private WebDriver driver;
	
	
	@BeforeClass(groups={"Fase1", "CustomerCare","AdministraccionDeCasos"})
	public void init() throws Exception
	{
		this.driver = setConexion.setupEze();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		login(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		goInitToConsolaFanF3(driver);
	}

	@BeforeMethod(groups={"Fase1", "CustomerCare","AdministraccionDeCasos"})
	public void mainSteup() {
	       
	    sleep(5000);
		CustomerCare cerrar = new CustomerCare(driver);
	       cerrar.cerrarultimapestana();		
	    sleep(4000);	
		goToLeftPanel(driver, "Casos");
		WebElement frame0 = driver.findElement(By.tagName("iframe"));
		driver.switchTo().frame(frame0);
		waitFor(driver, (By.name("fcf")));	
		Select field = new Select(driver.findElement(By.name("fcf")));
		field.selectByVisibleText("Mis Casos");
		driver.switchTo().defaultContent();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<WebElement> frame1 = driver.findElements(By.tagName("iframe"));
		driver.switchTo().frame(frame1.get(0));
		driver.findElement(By.name("newCase")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}

		driver.switchTo().defaultContent();
		SelectCaseRegisterType selectCaseRegTypePage = new SelectCaseRegisterType(driver);
		selectCaseRegTypePage.continueToCreate();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(frame1.get(0));
	}
	
	@AfterClass(groups={"Fase1", "CustomerCare","AdministraccionDeCasos"})
	public void tearDown() {
		/*driver.switchTo().defaultContent();
		List<WebElement> mainTabs1 = driver.findElements(By.className("x-tab-strip-close"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", mainTabs1.get(1));
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		waitFor(driver, (By.className("x-toolbar-cell")));
		List<WebElement> btn = driver.findElements(By.cssSelector(".x-btn-text"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn.get(5));*/
		driver.quit();
		sleep(5000);
	}
	
	@AfterMethod(groups={"Fase1", "CustomerCare","AdministraccionDeCasos"})
	public void alert (){
		HomeBase homePage = new HomeBase(driver);
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		homePage.switchAppsMenu();
        homePage.selectAppFromMenuByName("Ventas");
        try{
			Alert alert = driver.switchTo().alert();
			System.out.println(alert.getText());
			alert.accept();
		}catch(org.openqa.selenium.NoAlertPresentException e){}
		
        try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
        homePage.switchAppsMenu();
        homePage.selectAppFromMenuByName("Consola FAN");
	}

	@Test(groups={"Fase1", "CustomerCare","AdministraccionDeCasos"})
	public void TS7193_CaseRelatedFieldsValuesCanalClosing(){
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		List<WebElement> frame2 = driver.findElements(By.tagName("iframe"));
		driver.switchTo().frame(frame2.get(1));
		CasePage page = new CasePage(driver);
		page.ValidChannelClosing();
	}

	@Test(groups={"Fase1", "CustomerCare","AdministraccionDeCasos"})
	public void TS7090_CaseRelatedFieldsValuesSubArea(){

		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		List<WebElement> frame2 = driver.findElements(By.tagName("iframe"));
		driver.switchTo().frame(frame2.get(1));
		CasePage page = new CasePage(driver);
		page.FieldsValuesSubArea();
	}

	@Test(groups={"Fase1", "CustomerCare","AdministraccionDeCasos"})
	public void TS7088_CaseRelatedFieldsValuesType(){
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		List<WebElement> frame2 = driver.findElements(By.tagName("iframe"));
		driver.switchTo().frame(frame2.get(1));
		CasePage page = new CasePage(driver);
		page.FieldsValuesType();
	}

	@Test(groups={"Fase1", "CustomerCare","AdministraccionDeCasos"})
	public void TS7195_CaseRelatedCreateValuesCheck(){
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		for (WebElement currentFrame : frames) {
			try {
				driver.switchTo().frame(currentFrame);
				driver.findElement(By.id("ext-comp-1426"));
				break;
			}catch(NoSuchElementException noSuchElemExcept) {
				driver.switchTo().defaultContent();
				continue;
			}
		}
	}

	@Test(groups={"Fase1", "CustomerCare","AdministraccionDeCasos"})
	public void TS7083_ValidateDueTimeLogic(){
		//Pre-requirement : no other cases or new cases tabss.
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		driver.switchTo().frame(frames.get(1));//CaseCreation frame
		CasePage page = new CasePage(driver);
		page.FieldsValuesType();
		page.setCaseDueDate("01/08/2017 10:47");//older than today date.
		page.setContactName("Robo Tech");
		page.save();

		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
		DateFormat dateWithHourFormat = new SimpleDateFormat("dd/mm/yyyy hh:mm");
		Date caseDueDate = null;
		try {
			caseDueDate = dateWithHourFormat.parse(page.getCaseDueDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date caseCreatedDate = null;
		try {
			caseCreatedDate = dateWithHourFormat.parse(page.getCaseDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertTrue(caseDueDate.after(caseCreatedDate) || caseDueDate.equals(caseCreatedDate));

		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();

	}
	
	
	public void checkAlert() {
	     try {
	         WebDriverWait wait = new WebDriverWait(driver, 2);
	         wait.until(ExpectedConditions.alertIsPresent());
	         Alert alert = driver.switchTo().alert();
	         alert.accept();
	     } catch (Exception e) {
	         //exception handling
	      System.out.println(e.getMessage());
	     }
	 }
	
	//Falla seleccionando campo
	//@Test(groups={"Fase1", "CustomerCare","AdministraccionDeCasos"})
	 public void TS7085_DeleteCasesAdminRestrictedMessage() {
	  String adminRestrictedMsg = "El primer error de validación encontrado fue \"Solo el administrador puede eliminar casos generados.\"";
	  try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  CustomerCasesManager CCManagerPage = new CustomerCasesManager(driver);
	  CCManagerPage.getCase("00001813").findElement(By.xpath("//a[@onclick=\"return confirmDelete();\"]")).click();
	  try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  checkAlert();
	  try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  driver.switchTo().defaultContent();

	  List<WebElement> frames = driver.findElements(By.tagName("iframe"));
	  WebElement frameToSwitchTo = frames.get(0);
	  for (WebElement frame: frames) {
	   if(frame.getAttribute("id").equals("ext-comp-1005")) {
	    frameToSwitchTo = frame;
	   }
	  }
	  
	  driver.switchTo().frame(frameToSwitchTo);
	  List<WebElement> trList = driver.findElements(By.tagName("tr"));

	  String errorMsgInPage = trList.get(1).findElement(By.tagName("td")).getText();

	  Assert.assertTrue(errorMsgInPage.contains(adminRestrictedMsg));
	 }
	
	@Test(groups={"Fase1", "CustomerCare","AdministraccionDeCasos"})
	 public void TS7092_Case_Related_Field_Valores_del_Canal_de_inicio(){
	  try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  driver.switchTo().defaultContent();
	  List<WebElement> frame2 = driver.findElements(By.tagName("iframe"));
	  driver.switchTo().frame(frame2.get(1));
	  CasePage page = new CasePage(driver);
	  page.FieldsValuesinicio();
	  driver.switchTo().defaultContent();
	 }
	
	@Test(groups={"Fase1", "CustomerCare","AdministraccionDeCasos"})
	 public void TS7198_Estados_de_la_Entidad_Caso_Visualizar_picklist_Estado(){
	  try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  driver.switchTo().defaultContent();
	  List<WebElement> frame2 = driver.findElements(By.tagName("iframe"));
	  driver.switchTo().frame(frame2.get(1));
	  CasePage page = new CasePage(driver);
	  page.validpicklistestado();
	  driver.switchTo().defaultContent();
	 }
	 
	 @Test(groups={"Fase1", "CustomerCare","AdministraccionDeCasos"})
	 public void TS7212_Case_Management_Detalles_del_caso_Descripcion() {
	  driver.switchTo().defaultContent();
	  List<WebElement> frame2 = driver.findElements(By.tagName("iframe"));
	  driver.switchTo().frame(frame2.get(1));
	  CasePage page = new CasePage(driver);
	  page.validarcampodescrip();
	 }
	 

}