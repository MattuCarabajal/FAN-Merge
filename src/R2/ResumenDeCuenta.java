package R2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.*;
import org.testng.Assert;

import Pages.CustomerCare;
import Pages.Marketing;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.TestBase;

public class ResumenDeCuenta extends TestBase {

	private WebDriver driver;
	private CustomerCare cc;
	private Marketing mk;
	private LoginFw log;
	private GestionDeClientes_Fw ges;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	//@BeforeClass (groups = "PerfilOficina")
	public void initSIT() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		log = new LoginFw(driver);
		log.LoginSit();
		ges.irAConsolaFAN();
	}
		
	//@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		log = new LoginFw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
	}
	
		
	@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		log = new LoginFw(driver);
		log.loginTelefonico();
		ges.irAConsolaFAN();
	}
	
	@BeforeMethod (alwaysRun = true)
	public void setup() {
		detalles = null;
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
	}

	@AfterMethod (alwaysRun = true)
	public void after() throws IOException {
		guardarListaTxt(sOrders);
		sOrders.clear();
		tomarCaptura(driver,imagen);
		sleep(2000);
	}
	
	//@AfterClass (alwaysRun = true)
	public void quit() {
		driver.quit();
		sleep(5000);
	}

	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "ConsultaSaldo")
	public void TS146041_CRM_Movil_Mix_Resumen_de_Cuenta_Verificacion_de_cuentas_de_Facturacion_Impresion_OK_Crm_OC(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS146041";
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='card-top']")));
		mk.closeActiveTab();
		cc.irAFacturacion();
		cambioDeFrame(driver, By.cssSelector("[class='console-card active expired']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='console-card active expired']")));
		driver.findElement(By.xpath("//*[@class='card-info']//ul//span[text()='Resumen de Cuenta']")).click();
		cambioDeFrame(driver, By.cssSelector("[class='slds-text-heading--large slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-p-around--medium']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='slds-text-heading--large slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-p-around--medium']")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='slds-float--right'] [ng-click='printPdf()']")).getText().equalsIgnoreCase("Imprimir"));
	}
	
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"PerfilTelefonico", "R2"}, dataProvider = "ConsultaSaldo")
	public void TS147573_CRM_Movil_Mix_Resumen_de_Cuenta_Verificacion_de_cuentas_de_Facturacion_Crm_Telefonico(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS147573";
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='card-top']")));
		mk.closeActiveTab();
		cc.irAFacturacion();
		cambioDeFrame(driver, By.cssSelector("[class='console-card active expired']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='console-card active expired']")));
		driver.findElement(By.xpath("//*[@class='card-info']//ul//span[text()='Resumen de Cuenta']")).click();
		cambioDeFrame(driver, By.cssSelector("[class='slds-text-heading--large slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-p-around--medium']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='slds-text-heading--large slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-p-around--medium']")));
		String ctaFacturacion = driver.findElement(By.cssSelector("[class='slds-text-heading--large slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-p-around--medium'] [class='slds-float--left'] [class='secondaryFont']")).getText();
		ctaFacturacion = ctaFacturacion.substring(ctaFacturacion.lastIndexOf(" ")+1, ctaFacturacion.length());
		Assert.assertTrue(ctaFacturacion.equals(sAccountKey));
	}
	
	@Test (groups = {"PerfilTelefonico", "R2"}, dataProvider = "ConsultaSaldo")
	public void TS147577_CRM_Movil_Mix_Resumen_de_Cuenta_Verificacion_de_vista_de_Resumen_de_Cuenta_Crm_Telefonico(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS147577";
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='card-top']")));
		mk.closeActiveTab();
		cc.irAFacturacion();
		cambioDeFrame(driver, By.cssSelector("[class='console-card active expired']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='console-card active expired']")));
		driver.findElement(By.xpath("//*[@class='card-info']//ul//span[text()='Resumen de Cuenta']")).click();
		cambioDeFrame(driver, By.cssSelector("[class='slds-text-heading--large slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-p-around--medium']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='slds-text-heading--large slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-p-around--medium']")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='slds-text-heading--large slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-p-around--medium'] [class='secondaryFontNormal'] [class='expired-black']")).getText().contains("$"));
		Assert.assertTrue(driver.findElement(By.name("minDate")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.name("maxDate")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-top--small'] [ng-click='filtersAccepted()']")).getText().equalsIgnoreCase("Consultar"));
		cambioDeFrame(driver, By.cssSelector("[class='slds-grid slds-wrap slds-card slds-m-bottom--small slds-p-around--medium'] [class='slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1']"), 0);		
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='slds-grid slds-wrap slds-card slds-m-bottom--small slds-p-around--medium'] [class='slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1'] tbody td:nth-child(5)")).getText().contains("$"));
	}
}