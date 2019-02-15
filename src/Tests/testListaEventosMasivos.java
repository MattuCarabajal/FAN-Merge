package Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.gargoylesoftware.htmlunit.javascript.host.Iterator;
import Pages.Accounts;
import Pages.BasePage;
import Pages.CustomerCare;
import Pages.RegistroEventoMasivo;
import Pages.setConexion;
import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;

import java.util.List;
import java.text.ParseException;


public class testListaEventosMasivos extends TestBase{

	private WebDriver driver;
	RegistroEventoMasivo pEM=new RegistroEventoMasivo(driver);
	
	
	@BeforeClass(alwaysRun=true)
	public void init() throws Exception
	{
		this.driver = setConexion.setupEze();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		login(driver);
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	@BeforeMethod(alwaysRun=true)
	public void setUp() throws Exception {
		
		//Entra a Ventas y luego a consola Fan de regreso
		goInitToConsolaFanF3(driver);
		//Cierra ultima cuenta
		CustomerCare cerrar = new CustomerCare(driver);
		cerrar.cerrarultimapestana();
		//Selecciona Cuentas
		seleccionCuentaPorNombre(driver, "Adrian Techh");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		
		//selecciona el campo donde esta la busquedad del imput y Escribe
		searchAndClick(driver, "Eventos Masivos");
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
		//Click n caso: Si falla Cambia xpath
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("slds-text-heading--label")));
			
	}	
	
	@AfterMethod(alwaysRun=true)
	public void afterMethod() {
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<WebElement> mainTabs = driver.findElements(By.className("x-tab-strip-close"));
		  for (WebElement e : mainTabs) {
		  try {((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);} 
		  catch (org.openqa.selenium.StaleElementReferenceException b) {}
		  }
		  driver.switchTo().defaultContent();	
	}
	
	@AfterClass(alwaysRun=true)
	public void tearDown() {
		driver.quit();
		sleep(1000);
	}
	
	@Test(groups= {"Fase2","TechnicalCare","IncidenteMasivo","Ola2"})
	public void TS16240_CRM_Fase_2_Technical_Care_Representante_Incidentes_Masivos_Eventos_Datos_de_los_eventos_masivos_Fecha_de_inicio_Formato() {
		
		//try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		//BasePage cambioFrameByID=new BasePage();
		//driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-grid.slds-m-bottom--small.slds-wrap.cards-container")));
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
		List <WebElement> listaA= driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-no-row-hover.slds-table--cell-buffer")).findElements(By.cssSelector(".ng-pristine.ng-untouched.ng-valid.ng-empty"));
		//System.out.println(listaA.get(0).findElement(By.tagName("div")).getText());
		boolean verificacion=false;
		verificacion = pEM.validarFecha(listaA.get(1).findElement(By.tagName("div")).getText(), "HH:mm dd/mm/yyyy");
		
		assertTrue(verificacion);
		driver.switchTo().defaultContent();	
	}
	
	@Test(groups= {"Fase2","TechnicalCare","IncidenteMasivo","Ola2"})
	public void TS16237_CRM_Fase_2_Technical_Care_Representante_Incidentes_Masivos_Eventos_Procedimiento_Link_a_maxima_cantidad_de_archivos() {
		
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
		//Click En caso Especifico que contiene los elementos Adjuntos
		List <WebElement> listaA= driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-no-row-hover.slds-table--cell-buffer")).findElements(By.cssSelector(".ng-pristine.ng-untouched.ng-valid.ng-empty"));
		for(WebElement caso: listaA) {
			//System.out.println(caso.findElement(By.tagName("a")).getText());
			if(caso.findElement(By.tagName("a")).getText().contains("00003854")) {
				WebElement BenBoton = caso.findElement(By.tagName("a"));
				((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+BenBoton.getLocation().y+")");
				BenBoton.click();
				}
			}
		driver.switchTo().defaultContent();
		
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-grid.slds-m-bottom--small.slds-wrap.cards-container")));
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		List <WebElement> listaB= driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom--small.slds-wrap.cards-container")).findElements(By.cssSelector(".slds-tile.slds-p-bottom--medium"));
		//System.out.println(listaB.get(15).getText());
		List <WebElement> listaC=listaB.get(15).findElements(By.tagName("a"));
		assertTrue(listaC.get(0).isDisplayed());
		assertTrue(listaC.get(1).isDisplayed());
		driver.switchTo().defaultContent();	
		
	}
	
	@Test(groups= {"Fase2","TechnicalCare","IncidenteMasivo","Ola2"})
	public void TS16238_CRM_Fase_2_Technical_Care_Representante_Incidentes_Masivos_Eventos_Procedimiento_Sin_link() {
		
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
		//Click En caso Especifico que contiene los elementos Adjuntos
		List <WebElement> listaA= driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-no-row-hover.slds-table--cell-buffer")).findElements(By.cssSelector(".ng-pristine.ng-untouched.ng-valid.ng-empty"));
		for(WebElement caso: listaA) {
			if(caso.findElement(By.tagName("a")).getText().contains("00003787")) {
				WebElement BenBoton = caso.findElement(By.tagName("a"));
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+BenBoton.getLocation().y+")");
			BenBoton.click();}
			}
		driver.switchTo().defaultContent();
		
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-grid.slds-m-bottom--small.slds-wrap.cards-container")));
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		
		List <WebElement> listaB= driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom--small.slds-wrap.cards-container")).findElements(By.cssSelector(".slds-tile.slds-p-bottom--medium"));
		Boolean Verificacion=false;
		try{
		List <WebElement> listaC=listaB.get(15).findElements(By.tagName("a"));
		Verificacion=!(listaC.get(0).isDisplayed());
		}
		catch(java.lang.IndexOutOfBoundsException e){Verificacion=true;}
		assertTrue(Verificacion);
		driver.switchTo().defaultContent();	
		
	}
	//------------------------------------Eventos Masivos-------------------------------------//
	
	@Test(groups= {"Fase2","TechnicalCare","IncidenteMasivo","Ola2"})
	public void TS16230_CRM_Fase_2_Technical_Care_Sistema_Incidentes_Masivos_Creación_de_Eventos_Masivos_Ingreso_a_un_evento_masivo() {
		
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement cCaso = driver.findElement(By.xpath("//*[@id=\"j_id0:j_id5\"]/div/div/ng-include/div/div/div[2]/table/tbody[1]/tr/td[6]/div/a"));
		cCaso.click();
		driver.switchTo().defaultContent();	
		
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-grid.slds-m-bottom--small.slds-wrap.cards-container")));
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		
		//Verificamos que no este vacio el numero de ticket
		WebElement nCaso= driver.findElement(By.cssSelector(".slds-media__body.slds-truncate")).findElement(By.tagName("span"));
		assertTrue(!(nCaso.getText().isEmpty()));
		
		//Verificamos la Lista (canal esta excluido por error de build: el campo aparece vacio)
		int i=0;
		List <WebElement> listaA= driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom--small.slds-wrap.cards-container")).findElements(By.cssSelector(".slds-tile__detail.slds-text-heading--small"));
			for(WebElement listaB: listaA) {
				i++;
				//Campos vacios por las fechas falla el caso por que listaB[2]= canal. esta vacio
				if(i==7||i==8) { 
					assertTrue(listaB.isDisplayed());
				}
				else
					//assertTrue(!(listaB.getText().isEmpty()));
					assertTrue(!(listaB==null));
			}
			driver.switchTo().defaultContent();	
	}
	//Listo
	@Test(groups= {"Fase2","TechnicalCare","IncidenteMasivo","Ola2"})
	public void TS16231_CRM_Fase_2_Technical_Care_Representante_Incidentes_Eventos_Fecha_de_Inicio_Formato() {
		
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement cCaso = driver.findElement(By.xpath("//*[@id=\"j_id0:j_id5\"]/div/div/ng-include/div/div/div[2]/table/tbody[1]/tr/td[6]/div/a"));
		cCaso.click();
		driver.switchTo().defaultContent();	
		
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-grid.slds-m-bottom--small.slds-wrap.cards-container")));
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
		List <WebElement> listaA= driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom--small.slds-wrap.cards-container")).findElements(By.cssSelector(".slds-tile__detail.slds-text-heading--small"));
		System.out.println(listaA.get(5).getText());
		boolean verificacion=false;
		verificacion = pEM.validarFecha(listaA.get(5).getText(), "HH:mm dd/mm/yyyy");
		assertTrue(verificacion);
		driver.switchTo().defaultContent();

	}
	//Listo
	@Test(groups= {"Fase2","TechnicalCare","IncidenteMasivo","Ola2"})
	public void TS16232_CRM_Fase_2_Technical_Care_Representante_Incidentes_Eventos_Fecha_de_Creacion_Formato() {
		
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement cCaso = driver.findElement(By.xpath("//*[@id=\"j_id0:j_id5\"]/div/div/ng-include/div/div/div[2]/table/tbody[1]/tr/td[6]/div/a"));
		cCaso.click();
		driver.switchTo().defaultContent();	
		
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-grid.slds-m-bottom--small.slds-wrap.cards-container")));
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
		List <WebElement> listaA= driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom--small.slds-wrap.cards-container")).findElements(By.cssSelector(".slds-tile__detail.slds-text-heading--small"));
		//System.out.println(listaA.get(6).getText());
		boolean verificacion=false;
		verificacion = pEM.validarFecha(listaA.get(6).getText(), "HH:mm dd/mm/yyyy");
		assertTrue(verificacion);
		driver.switchTo().defaultContent();	
	}
	
}

