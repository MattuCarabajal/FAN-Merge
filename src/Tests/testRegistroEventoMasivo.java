package Tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.javascript.host.Iterator;

import Pages.Accounts;
import Pages.RegistroEventoMasivo;
import Pages.setConexion;


public class testRegistroEventoMasivo extends TestBase{
	
	private WebDriver driver;
	public RegistroEventoMasivo pageRegistroEventoMasivo = new RegistroEventoMasivo(driver);
	
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
		
		//Entrar en Velocity Telecomunication servics
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		String a = driver.findElement(By.id("tsidLabel")).getText();
		if (a.contains("Vlocity Telecommunication...")){}
		else {
			driver.findElement(By.id("tsid")).click();
			try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			driver.findElement(By.xpath("//a[@href=\"/home/home.jsp?tsid=02uc0000000D663\"]")).click();
		}
		
		//click +
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.xpath("//a[@href=\"/home/showAllTabs.jsp\"]")).click();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
				
		//click velociti omniscript designer
		
		List<WebElement> optns= driver.findElements(By.cssSelector(".dataCol.Custom108Block.col02"));
	      for (WebElement option : optns) {
	      if(option.getText().toLowerCase().equals("Vlocity OmniScript Designer".toLowerCase())){
	        WebElement BenBoton = option.findElement(By.tagName("a"));
	        ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+BenBoton.getLocation().y+")");
	        try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	          BenBoton.click();
	        break;
	      }
	    }
	      
	    try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.navigate().refresh();
	    try {Thread.sleep(15000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
		
		//WebElement TcEM=techCareClick();
		//click techcare massive create
		//WebElement Wait =  ( new WebDriverWait ( driver , 10 ))
				//   .  until ( ExpectedConditions . presenceOfElementLocated ( By .xpath ( "//a[text()=\"TechCare/Massive Event - Create Case\"]" )));
		
		WebElement tcmc=driver.findElement(By.xpath("//a[text()=\"TechCare/Massive Event - Create Case\"]"));
		
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+tcmc.getLocation().y+")");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		tcmc.click();
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
				
		//click version 8
		//WebElement cv8 = driver.findElement(By.xpath("//a[text()=\"ta-techCare-MassiveIncident-CreateCase (Version 8)\"]"));
		//click version 9
		WebElement cv8 = driver.findElement(By.xpath("//a[text()=\"ta-techCare-MassiveIncident-CreateCase (Version 28)\"]"));
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+cv8.getLocation().y+")");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		cv8.click();
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			    
		//click preview
		WebElement cpw = driver.findElement(By.xpath("//*[@id=\"bodyTable\"]/tbody/tr/td/div[2]/div[4]/div/div/div[3]/div[1]/ul/li[2]/a"));
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+cpw.getLocation().y+")");
		cpw.click();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
		pageRegistroEventoMasivo = new RegistroEventoMasivo(driver);
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		}
	
	@AfterMethod(alwaysRun=true)
	public void closeTechCareTab() {
		driver.switchTo().defaultContent();
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
		sleep(1000);
	}
	
	
	public WebElement techCareClick() {
		WebElement Creacion=null;
		List<WebElement> tcmc = driver.findElements(By.className("slds-truncate"));
		for(WebElement e: tcmc) {
			System.out.println(e.getText());
			if(e.getText().startsWith("TechCare/Massive Event - Create Case")) {
				Creacion=e;
				System.out.println("_____________________________________________________________________");
				return Creacion;
				
			}
		}
		return null;
	}
	//Flor
	@Test(groups= {"Fase","TechnicalCare","IncidenteMasivo","Ola2"})
	public void TS16246_CRM_Fase_2_Technical_Care_Sistema_Incidentes_Masivos_Creación_de_Eventos_Masivos_Canal_Formato() {
		Accounts accPage = new Accounts(driver);
		boolean estan = true;
		String[] todos = {"web","ivr","sms","sistema de gestion","ussd","todos los medios","redes sociales"};
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.id("InputValues_nextBtn")));
		driver.findElement(By.id("SelectChannel")).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<WebElement> optns = driver.findElement(By.id("SelectChannel")).findElements(By.tagName("option"));
		List<String> textos = new ArrayList<String>();
		for (WebElement optn : optns)
		{
			textos.add(optn.getText().toLowerCase());
		}
		for (String uno : todos) {
			if (!textos.contains(uno)) {
				estan = false;
			}
		}
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		assertTrue(estan);
	}
	
	//Flor
	@Test(groups= {"Fase","TechnicalCare","IncidenteMasivo","Ola2"})
	public void TS16318_CRM_Fase_2_Technical_Care_Sistema_Incidentes_Masivos_Creación_de_Eventos_Masivos_Fecha_De_Inicio_Formato() {
		RegistroEventoMasivo REM = new RegistroEventoMasivo(driver);
		Accounts accPage = new Accounts(driver);
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.id("InputValues_nextBtn")));
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+ driver.findElement(By.id("StartDate")).getLocation().y+")");
		driver.findElement(By.id("StartDate")).click();
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+ driver.findElement(By.id("datepickers-container")).findElement(By.cssSelector(".datepicker--cell.datepicker--cell-day.-current-")).getLocation().y+")");
		driver.findElement(By.id("datepickers-container")).findElement(By.cssSelector(".datepicker--cell.datepicker--cell-day.-current-")).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		assertTrue(REM.validarFecha(driver.findElement(By.id("StartDate")).getAttribute("value"), "dd/mm/yyyy HH:mm"));
	}
	
	//Flor
	@Test(groups= {"Fase","TechnicalCare","IncidenteMasivo","Ola2"})
	public void TS16321_CRM_Fase_2_Technical_Care_Sistema_Incidentes_Masivos_Creación_de_Eventos_Masivos_Fecha_De_Fin_Formato() {
		RegistroEventoMasivo REM = new RegistroEventoMasivo(driver);
		Accounts accPage = new Accounts(driver);
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.id("InputValues_nextBtn")));
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+ driver.findElement(By.id("EndDate")).getLocation().y+")");
		driver.findElement(By.id("EndDate")).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("datepickers-container")).findElements(By.cssSelector(".datepicker--cell.datepicker--cell-day.-current-")).get(1).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		assertTrue(REM.validarFecha(driver.findElement(By.id("EndDate")).getAttribute("value"), "dd/mm/yyyy HH:mm"));
	}
	
	
	//Listo
	@Test(groups= {"Fase","TechnicalCare","IncidenteMasivo","Ola2"})
	public void TS16329_CRM_Fase_2_Technical_Care_Sistema_Incidentes_Masivos_Creación_de_Eventos_Masivos_Tipo_Obligatorio(){
			assertTrue(pageRegistroEventoMasivo.VerificarObligatorio(By.id("SelectType")));
	}
		
	//Listo
	@Test(groups= {"Fase","TechnicalCare","IncidenteMasivo","Ola2"})
	public void TS16330_CRM_Fase_2_Technical_Care_Sistema_Incidentes_Masivos_Creación_de_Eventos_Masivos_Subtipo_Obligatorio(){
		assertTrue(pageRegistroEventoMasivo.VerificarObligatorio(By.id("SelectSubType")));
	}
	
	//Listo
	@Test(groups= {"Fase","TechnicalCare","IncidenteMasivo","Ola2"})
	public void TS16331_CRM_Fase_2_Technical_Care_Sistema_Incidentes_Masivos_Creación_de_Eventos_Masivos_Canal_Obligatorio() {
			
	pageRegistroEventoMasivo.registrarExcepto(By.id("SelectChannel"));
	try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	pageRegistroEventoMasivo.ClickOnSave();
	try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	assertTrue(driver.findElement(By.id("alert-ok-button")).isDisplayed());		
	}
		
	//Listo
	@Test(groups= {"Fase","TechnicalCare","IncidenteMasivo","Ola2"})
	public void TS16332_CRM_Fase_2_Technical_Care_Sistema_Incidentes_Masivos_Creación_de_Eventos_Masivos_Incidente_Obligatorio(){
			
	pageRegistroEventoMasivo.registrarExceptoIncidente(By.id("SelectIncident"));
	try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	pageRegistroEventoMasivo.ClickOnSave();
	try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	assertTrue(driver.findElement(By.id("alert-ok-button")).isDisplayed());
	}
	
	//Listo
	@Test(groups= {"Fase","TechnicalCare","IncidenteMasivo","Ola2"})
	public void TS16333_CRM_Fase_2_Technical_Care_Sistema_Incidentes_Masivos_Creación_de_Eventos_Masivos_Titulo_Obligatorio() {
		assertTrue(pageRegistroEventoMasivo.VerificarObligatorio(By.id("TextIncidentTitle")));
	}
	
	//Listo
	@Test(groups= {"Fase","TechnicalCare","IncidenteMasivo","Ola2"})
	public void TS16334_CRM_Fase_2_Technical_Care_Sistema_Incidentes_Masivos_Creación_de_Eventos_Masivos_Impacto_Obligatorio() {
		assertTrue(pageRegistroEventoMasivo.VerificarObligatorio(By.id("SelectImpact")));
	}
	
	//Listo
	@Test(groups= {"Fase","TechnicalCare","IncidenteMasivo","Ola2"})
	public void TS16335_CRM_Fase_2_Technical_Care_Sistema_Incidentes_Masivos_Creación_de_Eventos_Masivos_Fecha_de_Inicio_Obligatorio() {
		assertTrue(pageRegistroEventoMasivo.VerificarObligatorio(By.id("StartDate")));
	}
	
	//Listo
	@Test(groups= {"Fase","TechnicalCare","IncidenteMasivo","Ola2"})
	public void TS16337_CRM_Fase_2_Technical_Care_Sistema_Incidentes_Masivos_Creación_de_Eventos_Masivos_Estado_Obligatorio() {
		assertTrue(pageRegistroEventoMasivo.VerificarObligatorio(By.id("SelectStatus")));
	}
	
	
	//Flor
	@Test(groups= {"Fase","TechnicalCare","IncidenteMasivo","Ola2"}) 
	public void TS16336_CRM_Fase_2_Technical_Care_Sistema_Incidentes_Masivos_Creación_de_Eventos_Masivos_Fecha_Estimada_De_Cierre_No_Obligatorio() {
	RegistroEventoMasivo REM = new RegistroEventoMasivo(driver);
	Accounts accPage = new Accounts(driver);
	REM.llenarListas(By.id("EndDate"));
	try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	REM.llenarTextos(By.id("EndDate"));
	try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	driver.findElement(By.id("TextIncidentTitle")).sendKeys("Algo mas");
	REM.ClickOnSave();
	try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	driver.switchTo().frame(accPage.getFrameForElement(driver, By.id("ConfirmationText")));
	assertTrue(driver.findElement(By.id("ConfirmationText")).getText().contains("correctamente el IncidenteMasivo"));   
	 }
	
	//Listo
	@Test(groups= {"Fase","TechnicalCare","IncidenteMasivo","Ola2"})
	public void TS16338_CRM_Fase_2_Technical_Care_Sistema_Incidentes_Masivos_Creación_de_Eventos_Masivos_Detalle_del_Incidente_Obligatorio() {
		assertTrue(pageRegistroEventoMasivo.VerificarObligatorio(By.id("TextDetailIncident")));
	}
	
	//Listo
	@Test(groups= {"Fase","TechnicalCare","IncidenteMasivo","Ola2"})
	public void TS16339_CRM_Fase_2_Technical_Care_Sistema_Incidentes_Masivos_Creación_de_Eventos_Masivos_Cliente_afectado_Obligatorio() {
		assertTrue(pageRegistroEventoMasivo.VerificarObligatorio(By.id("TextAffectedCustomer")));
	}
	
	//Listo
	@Test(groups= {"Fase","TechnicalCare","IncidenteMasivo","Ola2"})
	public void TS16340_CRM_Fase_2_Technical_Care_Sistema_Incidentes_Masivos_Creación_de_Eventos_Masivos_Cliente_Procedimiento_Obligatorio() {
		assertTrue(pageRegistroEventoMasivo.VerificarObligatorio(By.id("TextProcedure")));
	}
	
	//Listo
	@Test(groups= {"Fase","TechnicalCare","IncidenteMasivo","Ola2"})
	public void TS16341_CRM_Fase_2_Technical_Care_Sistema_Incidentes_Masivos_Creación_de_Eventos_Masivos_Speech_Obligatorio() {
		assertTrue(pageRegistroEventoMasivo.VerificarObligatorio(By.id("TextSpeech")));
	}
	
	
	//
}

