package Funcionalidades;

import java.awt.AWTException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.CustomerCare;
import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class CambioDePlan extends TestBase {

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	private CBS_Mattu cbsm;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	@BeforeClass (alwaysRun = true)
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		loginOOCC(driver);
		sleep(15000);
		cc.irAConsolaFAN();	
		driver.switchTo().defaultContent();
		sleep(6000);
	}
	
	@BeforeClass (alwaysRun = true)
	public void initTelefonico() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		loginTelefonico(driver);
		sleep(15000);
		cc.irAConsolaFAN();	
		driver.switchTo().defaultContent();
		sleep(6000);
	}
	
	@BeforeMethod(alwaysRun=true)
	public void setup() throws Exception {
		GestionDeClientes_Fw ges = new GestionDeClientes_Fw(driver);
		ges.selectMenuIzq("Inicio");
		ges.cerrarPestaniaGestion(driver);
		ges.irGestionClientes();	
	}

	@AfterMethod(alwaysRun=true)
	public void after() throws IOException {
		guardarListaTxt(sOrders);
		sOrders.clear();
		tomarCaptura(driver,imagen);
		sleep(2000);
	}
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"GestionesPerfilOficina", "CambioDePlan", "Release1"})
	public void TS_CambioDePlan() {
//		imagen = "TS134787";
//		detalles = imagen + " - Historial de recargas - DNI:" + sDNI;
//		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
//		sb.BuscarCuenta("DNI", sDNI);
//		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
//		sleep(12000);
//		cc.irAHistoriales();
//		WebElement historialDeRecargas = null;
//		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button_brand")));
//		for (WebElement x : driver.findElements(By.className("slds-card"))) {
//			if (x.getText().toLowerCase().contains("historial de recargas"))
//				historialDeRecargas = x;
//		}
//		historialDeRecargas.findElement(By.cssSelector(".slds-button.slds-button_brand")).click();
//		sleep(7000);
//		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-03")));
//		driver.findElement(By.id("text-input-03")).click();
//		driver.findElement(By.xpath("//*[text() = 'Todos']")).click();
//		driver.findElement(By.id("text-input-04")).click();
//		driver.findElement(By.xpath("//*[text() = 'Con Beneficios']")).click();
//		if (driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).isDisplayed()) {
//			driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
//			Assert.assertTrue(true);
//		} else
//			Assert.assertTrue(false);
	}
	
}
