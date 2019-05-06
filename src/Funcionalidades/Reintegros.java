package Funcionalidades;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.Accounts;
import Pages.CustomerCare;
import Pages.Marketing;
import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import Tests.TestBase;

public class Reintegros extends TestBase {

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
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
	
	@BeforeMethod(alwaysRun=true)
	public void setup() throws Exception {
		detalles = null;
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

	//@AfterClass(alwaysRun=true)
	public void quit() throws IOException {
		driver.quit();
		sleep(5000);
	}
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"GestionesPerfilOficina", "Reintegros", "E2E","Ciclo4"}, dataProvider = "CuentaReintegros")
	public void TS112597_CRM_Movil_PRE_Pago_con_Tarjeta_de_debito_Reintegro_con_Efectivo_Menos_de_1000(String cDNI) {
		imagen = "TS112597";
		detalles = null;
		detalles = imagen + " -Reintegros - DNI: " + cDNI;
		Marketing mk = new Marketing(driver);
		boolean gest = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "solicitud de reintegros");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("stepRefundData_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "tarjeta de d\u00e9bito");
		selectByText(driver.findElement(By.id("selectReason")), "Pago duplicado");
		driver.findElement(By.id("inputCurrencyAmount")).sendKeys("90000");
		driver.findElement(By.id("stepRefundData_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		driver.findElement(By.id("stepRefundMethod_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(10000);
		List <WebElement> msj = driver.findElements(By.className("ta-care-omniscript-done"));
		for (WebElement x : msj) {
			if (x.getText().toLowerCase().contains("la gesti\u00f3n se deriv\u00f3 correctamente")) {
				gest = true;
			}
		}
		Assert.assertTrue(gest);
		if (TestBase.urlAmbiente.contains("sit")) {
			String orden = cc.obtenerOrden(driver, "Solicitud de Reintegros");
			detalles+=" - Orden: "+orden;
			sOrders.add("Solicitud de Reintegros, orden numero: " + orden + " con numero de DNI: " + cDNI);
			Assert.assertTrue(cc.verificarOrden(orden));		
		} else {
			String orden = driver.findElement(By.cssSelector(".vlc-slds-inline-control__label.ng-binding")).getText();
			detalles+=" - Orden: "+orden;
			orden = orden.substring(orden.lastIndexOf(" ")+1, orden.lastIndexOf("."));
			sOrders.add("Solicitud de Reintegros, numero de orden: " + orden + " de cuenta con DNI: " + cDNI);
			Assert.assertTrue(cc.verificarOrdenYGestion("Solicitud de Reintegros"));
		}
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Reintegros", "E2E","Ciclo4"}, dataProvider = "CuentaReintegros")
	public void TS112598_CRM_Movil_PRE_Pago_con_Tarjeta_de_debito_Reintegro_con_Efectivo_1000(String cDNI) {
		imagen = "TS112598";
		detalles = null;
		detalles = imagen + " -Reintegros - DNI: " + cDNI;
		Marketing mk = new Marketing(driver);
		boolean gest = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "solicitud de reintegros");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("stepRefundData_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "tarjeta de d\u00e9bito");
		selectByText(driver.findElement(By.id("selectReason")), "Pago duplicado");
		driver.findElement(By.id("inputCurrencyAmount")).sendKeys("100000");
		driver.findElement(By.id("stepRefundData_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		driver.findElement(By.id("stepRefundMethod_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(10000);
		List <WebElement> msj = driver.findElements(By.className("ta-care-omniscript-done"));
		for (WebElement x : msj) {
			if (x.getText().toLowerCase().contains("la gesti\u00f3n se deriv\u00f3 correctamente")) {
				gest = true;
			}
		}
		Assert.assertTrue(gest);
		if (TestBase.urlAmbiente.contains("sit")) {
			String orden = cc.obtenerOrden(driver, "Solicitud de Reintegros");
			detalles+=" - Orden: "+orden;
			sOrders.add("Solicitud de Reintegros, orden numero: " + orden + " con numero de DNI: " + cDNI);
			Assert.assertTrue(cc.verificarOrden(orden));		
		} else {
			String orden = driver.findElement(By.cssSelector(".vlc-slds-inline-control__label.ng-binding")).getText();
			detalles+=" - Orden: "+orden;
			orden = orden.substring(orden.lastIndexOf(" ")+1, orden.lastIndexOf("."));
			sOrders.add("Solicitud de Reintegros, numero de orden: " + orden + " de cuenta con DNI: " + cDNI);
			Assert.assertTrue(cc.verificarOrdenYGestion("Solicitud de Reintegros"));
		}
	}
}