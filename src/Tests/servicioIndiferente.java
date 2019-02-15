package Tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
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
//Librerias server
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.RemoteWebDriver;


public class servicioIndiferente extends TestBase {
	private WebDriver driver;

	@BeforeClass(groups = "Fase2")
	public void Init() throws MalformedURLException
	{
		//DesiredCapabilities capability = DesiredCapabilities.chrome();
		//capability.setBrowserName("chrome");
		//capability.setPlatform(Platform.WINDOWS);
		//driver = new RemoteWebDriver(new URL("http://10.249.36.59:5566/wd/hub"), capability);
		this.driver = setConexion.setupEze();
		//driver.manage().window().maximize();
		login(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Calendar Factual = Calendar.getInstance();
		System.out.println("fecha act: "+(Integer.toString(Factual.get(Calendar.DATE))+"/"+Integer.toString(Factual.get(Calendar.MONTH))+"/"+Integer.toString(Factual.get(Calendar.YEAR))));
	}
	
	@BeforeMethod(groups = "Fase2")
		public void setUp() throws Exception {
			try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			goInitToConsolaFanF3(driver);
		     CustomerCare cerrar = new CustomerCare(driver);
		     cerrar.cerrarultimapestana();
			}
	
	@AfterMethod(groups = "Fase2")
	public void afterMethod() {
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		CustomerCare cerrar = new CustomerCare(driver);
	     cerrar.cerrarultimapestana();
	}

	
	@AfterClass(groups = "Fase2")
	public void tearDown() {
		driver.quit();
		
	}
	
	
	@Test(groups = "Fase2")
	public void TS11600_CRM_Fase_2_Technical_Care_CSR_Diagnostico_Servicio_Indiferente_Boton_ejecutar_no_disponible(){
	try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	seleccionCuentaPorNombre(driver, "Adrian Tech");
	try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	searchAndClick(driver, "Asistencia Técnica");
	
	
	
	driver.switchTo().defaultContent();
	try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
    BasePage pagina = new BasePage (driver);
    driver.switchTo().frame(pagina.getFrameForElement(driver, By.id("LookupSelectofService")));
    try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
    WebElement BenBoton = driver.findElement((By.id("LookupSelectofService")));
    BenBoton.click();
    try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
    JavascriptExecutor js = (JavascriptExecutor)driver;
	js.executeScript("document.getElementsByClassName('slds-list__item ng-binding ng-scope')[0].click();");
	WebElement Continuar = driver.findElement((By.id("SelectServiceStep_nextBtn")));
	((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+Continuar.getLocation().y+")");
	try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	Continuar.click();
	try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	
	
	//Click ventana emergente
	try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	 JavascriptExecutor jsa = (JavascriptExecutor)driver;
	jsa.executeScript("document.getElementsByClassName('slds-button slds-button--neutral ng-binding ng-scope')[1].click();");
	try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	
	//Verificamos la prueba
	boolean Error=false;
	try {
	if(driver.findElement((By.id("IntegProc_Diagnóstico"))).isDisplayed())
		Error=false;
	}
	catch (org.openqa.selenium.NoSuchElementException e) {Error=true; }
	assertTrue(Error);
	driver.switchTo().defaultContent();	
	}
	
	
	@Test(groups = "Fase2")
	public void prueba() {
		System.out.println("Esta es una prueba");
		assertTrue(true);
	}
	
}
