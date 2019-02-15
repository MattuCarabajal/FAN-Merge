package Tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.Accounts;
import Pages.BasePage;
import Pages.CustomerCare;
import Pages.HomeBase;
import Pages.setConexion;


public class TechnicalCare extends TestBase  {
	private static final boolean WebElement = false;
	private WebDriver driver;
	private String validIMEI = "545229703256596";
	
	@BeforeClass(alwaysRun=true)
	public void init() throws Exception
	{
		this.driver = setConexion.setupEze();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		login(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}

	@BeforeMethod(alwaysRun=true)
	public void setUp() throws Exception {
		HomeBase homePage = new HomeBase(driver);
	       if(driver.findElement(By.id("tsidLabel")).getText().equals("Consola FAN")) {
	        homePage.switchAppsMenu();
	        try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	        homePage.selectAppFromMenuByName("Ventas");
	        try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}    
	       }
	       homePage.switchAppsMenu();
	       try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	       homePage.selectAppFromMenuByName("Consola FAN");
	       
	       CustomerCare cerrar = new CustomerCare(driver);
			cerrar.cerrarultimapestana();
	       
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}			
		goToLeftPanel2(driver, "Cuentas");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Accounts accountPage = new Accounts(driver);
		//Selecciono Vista Tech
		driver.switchTo().defaultContent();
		accountPage.accountSelect("Vista Tech");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		//select accountName "Robo Tech", currently has index 10.
		accountPage.selectAccountByName("Adrian Tech");
		//try {Thread.sleep(15000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		try {Thread.sleep(9000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		//escribe ServicioTecnico
		BasePage sImput=new BasePage();
		driver.switchTo().frame(sImput.getFrameForElement(driver,By.xpath("/html/body/div/div[1]/ng-include/div/div[1]/ng-include/div/div[2]/input")));
		WebElement elemento = driver.findElement(By.xpath("/html/body/div/div[1]/ng-include/div/div[1]/ng-include/div/div[2]/input"));
		elemento.sendKeys("Servicio t");
		//Click en el resultado ServicioTecnico
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement eMasivos= driver.findElement(By.xpath("//*[text() = 'Servicio Técnico']"));
		eMasivos.click();
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	

	@AfterMethod(alwaysRun=true)
	public void closeTechCareTab() {
		driver.switchTo().defaultContent();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<WebElement> mainTabs = driver.findElements(By.className("x-tab-strip-close"));
		  for (WebElement e : mainTabs) {
		  try {((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);} catch (org.openqa.selenium.StaleElementReferenceException b) {}
		  }
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+ driver.findElement(By.id("tsidButton")).getLocation().y+")");
		driver.findElement(By.id("tsidButton")).click();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<WebElement> options = driver.findElement(By.id("tsid-menuItems")).findElements(By.tagName("a"));
		for (WebElement option : options) {
	      if(option.getText().toLowerCase().equals("Sales".toLowerCase()) || option.getText().toLowerCase().equals("Ventas".toLowerCase()) ){
	        option.click();
	        break;
	      }
	    }
	       
	  }

	@AfterClass(alwaysRun=true)
	public void tearDown() {
		driver.quit();
		sleep(2000);
	}

	@Test(groups = {"Fase2","TechnicalCare","ServicioTecnico","Ola2"})
	public void TS11622_SST_Servicio_Indiferente_Adjunto_Formato_invalido() {
		Accounts accPage = new Accounts(driver);
		String invalidFilePath = "C:\\Users\\pablo\\Desktop\\SampleFiles\\unZip.zip";
		String mensajeParcialErrorEnPagina = "Solo se pueden adjuntar archivos de tipo";
		//Literal en pagina: " Solo se pueden adjuntar archivos de tipo .doc, .docx, .xls, .xlsx, .pdf, .jpg, .jpeg "
		//imgItem ng-binding ng-scope
		accPage.fillIMEI(validIMEI);
		accPage.continueFromImeiInput();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}			
		accPage.continueFromClientInfo();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		accPage.selectOperationType("Consulta");
		accPage.selectSymptomByIndex(2);
		accPage.attachFile(invalidFilePath);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		String errMessage = accPage.getMessageDescription();
		Assert.assertTrue(errMessage.trim().contains(mensajeParcialErrorEnPagina));
	}
	
	@Test(groups = {"Fase2","TechnicalCare","ServicioTecnico","Ola2"})
	public void TS11620_SST_Servicio_Indiferente_Adjunto_Valido_doc() {
		Accounts accPage = new Accounts(driver);
		String filePath = "C:\\Users\\pablo\\Desktop\\SampleFiles\\unDoc.doc";
		//Literal en pagina: " Solo se pueden adjuntar archivos de tipo .doc, .docx, .xls, .xlsx, .pdf, .jpg, .jpeg "
		accPage.fillIMEI(validIMEI);
		accPage.continueFromImeiInput();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}			
		accPage.continueFromClientInfo();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		accPage.selectOperationType("Consulta");
		accPage.selectSymptomByIndex(2);
		accPage.attachFile(filePath);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		String textoArchivoAdjunto = accPage.getAttachedFileTxt();
		System.out.println(textoArchivoAdjunto);
		Assert.assertTrue(textoArchivoAdjunto.toLowerCase().trim().contains(".doc"));
	}
	
	@Test(groups = {"Fase2","TechnicalCare","ServicioTecnico","Ola2"})
	public void TS11631_SST_Servicio_Indiferente_Adjunto_Valido_docx() {
		Accounts accPage = new Accounts(driver);
		String filePath = "C:\\Users\\pablo\\Desktop\\SampleFiles\\unDocx.docx";
		//Literal en pagina: " Solo se pueden adjuntar archivos de tipo .doc, .docx, .xls, .xlsx, .pdf, .jpg, .jpeg "
		accPage.fillIMEI(validIMEI);
		accPage.continueFromImeiInput();
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}			
		accPage.continueFromClientInfo();
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		accPage.selectOperationType("Consulta");
		accPage.selectSymptomByIndex(2);
		accPage.attachFile(filePath);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		String textoArchivoAdjunto = accPage.getAttachedFileTxt();
		System.out.println(textoArchivoAdjunto);
		Assert.assertTrue(textoArchivoAdjunto.toLowerCase().trim().contains(".docx"));
	}
	
	@Test(groups = {"Fase2","TechnicalCare","ServicioTecnico","Ola2"})
	public void TS11635_SST_Servicio_Indiferente_Adjunto_Valido_jpeg() {
		Accounts accPage = new Accounts(driver);
		String filePath = "C:\\Users\\pablo\\Desktop\\SampleFiles\\unJpeg.jpeg";
		//Literal en pagina: " Solo se pueden adjuntar archivos de tipo .doc, .docx, .xls, .xlsx, .pdf, .jpg, .jpeg "
		accPage.fillIMEI(validIMEI);
		accPage.continueFromImeiInput();
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}			
		accPage.continueFromClientInfo();
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		accPage.selectOperationType("Consulta");
		accPage.selectSymptomByIndex(2);
		accPage.attachFile(filePath);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		String textoArchivoAdjunto = accPage.getAttachedFileTxt();
		System.out.println(textoArchivoAdjunto);
		Assert.assertTrue(textoArchivoAdjunto.toLowerCase().trim().contains(".jpeg"));
	}
	
	@Test(groups = {"Fase2","TechnicalCare","ServicioTecnico","Ola2"})
	public void TS11634_SST_Servicio_Indiferente_Adjunto_Valido_jpg() {
		Accounts accPage = new Accounts(driver);
		String filePath = "C:\\Users\\pablo\\Desktop\\SampleFiles\\unJpg.jpg";
		//Literal en pagina: " Solo se pueden adjuntar archivos de tipo .doc, .docx, .xls, .xlsx, .pdf, .jpg, .jpeg "
		accPage.fillIMEI(validIMEI);
		accPage.continueFromImeiInput();
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}			
		accPage.continueFromClientInfo();
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		accPage.selectOperationType("Consulta");
		accPage.selectSymptomByIndex(2);
		accPage.attachFile(filePath);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		String textoArchivoAdjunto = accPage.getAttachedFileTxt();
		System.out.println(textoArchivoAdjunto);
		Assert.assertTrue(textoArchivoAdjunto.toLowerCase().trim().contains(".jpg"));
	}
	
	@Test(groups = {"Fase2","TechnicalCare","ServicioTecnico","Ola2"})
	public void TS11636_SST_Servicio_Indiferente_Adjunto_Valido_pdf() {
		Accounts accPage = new Accounts(driver);
		String filePath = "C:\\Users\\pablo\\Desktop\\SampleFiles\\unPdf.pdf";
		//Literal en pagina: " Solo se pueden adjuntar archivos de tipo .doc, .docx, .xls, .xlsx, .pdf, .jpg, .jpeg "
		accPage.fillIMEI(validIMEI);
		accPage.continueFromImeiInput();
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}			
		accPage.continueFromClientInfo();
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		accPage.selectOperationType("Consulta");
		accPage.selectSymptomByIndex(2);
		accPage.attachFile(filePath);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		String textoArchivoAdjunto = accPage.getAttachedFileTxt();
		System.out.println(textoArchivoAdjunto);
		Assert.assertTrue(textoArchivoAdjunto.toLowerCase().trim().contains(".pdf"));
	}
	
}
