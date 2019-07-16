package Funcionalidades;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class Reintegros extends TestBase {

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	private LoginFw log;
	private GestionDeClientes_Fw ges;
	private CBS_Mattu cbsm;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private Marketing mk;
	String detalles;
	
	
	@BeforeClass (alwaysRun = true)
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		cbsm = new CBS_Mattu();
		mk = new Marketing(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
		
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
	
	@Test (groups = {"GestionesPerfilOficina", "Reintegros", "E2E","R1"}, dataProvider = "CuentaReintegros")
	public void TS145909_CRM_Movil_Mix_Solicitud_de_Reintegros_Efectivo_Debito_por_error_de_sistema_Efectivo_Exitosa_Crm_OC(String cDNI) {
		imagen = "TS145909";
		detalles = null;
		detalles = imagen + " -Reintegros - DNI: " + cDNI;
		boolean gest = false;
		ges.BuscarCuenta("DNI", cDNI);
		mk.closeActiveTab();
		cc.irAFacturacion();
		cambioDeFrame(driver, By.className("card-top"),0);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className("card-top"), 0));
		driver.findElement(By.xpath("//*[@class='console-card active expired']//*[@class='icon-v-down-caret flyout-open expired-fly']")).click();
		sleep(3000);
		driver.findElement(By.xpath("//*[@class='community-flyout-actions-card']//li//a//span[contains(text(),'Solicitud de Reintegros')]")).click();
		cambioDeFrame(driver,By.id("stepRefundData_nextBtn"),0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("stepRefundData_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		selectByText(driver.findElement(By.id("selectReason")),"D\u00e9bito por error de sistema");
		driver.findElement(By.id("inputCurrencyAmount")).sendKeys("1000");
		driver.findElement(By.id("stepRefundData_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("stepRefundMethod_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-radio.ng-scope.itemSelected")), "equals", "efectivo");
		driver.findElement(By.id("stepRefundMethod_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Summary_nextBtn")));
		esperarElemento(driver, By.id("Summary_nextBtn"), 0);
		cc.obligarclick(driver.findElement(By.id("Summary_nextBtn")));
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.className("ta-care-omniscript-done")));
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
			Assert.assertTrue(cc.verificarOrden(orden+"*"));		
		} else {
			String texto = driver.findElement(By.xpath("//*[@class='ta-care-omniscript-done']//header//p//label")).getText();
			String orden = cc.getNumeros(texto);
			cc.buscarOrdenDiag(orden+"*");
			boolean h = false;
			cambioDeFrame(driver, By.id("Case_body"),0);
			List<WebElement> status = driver.findElement(By.id("Case_body")).findElements(By.tagName("td"));
			for(WebElement s : status ){
				if(s.getText().toLowerCase().contains("solicitud de reintegros"))
					h = true;
			}
		}
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Reintegros", "E2E","R1"}, dataProvider = "CuentaReintegros")
	public void TS148611_CRM_Movil_Mix_Pago_con_Efectivo_Reintegro_con_Efectivo_1000_Crm_OC(String cDNI) {
		imagen = "TS145909";
		detalles = null;
		detalles = imagen + " -Reintegros - DNI: " + cDNI;
		boolean gest = false;
		ges.BuscarCuenta("DNI", cDNI);
		mk.closeActiveTab();
		cc.irAFacturacion();
		cambioDeFrame(driver, By.className("card-top"),0);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className("card-top"), 0));
		driver.findElement(By.xpath("//*[@class='console-card active expired']//*[@class='icon-v-down-caret flyout-open expired-fly']")).click();
		sleep(3000);
		driver.findElement(By.xpath("//*[@class='community-flyout-actions-card']//li//a//span[contains(text(),'Solicitud de Reintegros')]")).click();
		cambioDeFrame(driver,By.id("stepRefundData_nextBtn"),0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("stepRefundData_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		selectByText(driver.findElement(By.id("selectReason")),"Pago duplicado");
		driver.findElement(By.id("inputCurrencyAmount")).sendKeys("100000");
		driver.findElement(By.id("stepRefundData_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("stepRefundMethod_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-radio.ng-scope.itemSelected")), "equals", "efectivo");
		driver.findElement(By.id("stepRefundMethod_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Summary_nextBtn")));
		esperarElemento(driver, By.id("Summary_nextBtn"), 0);
		sleep(3000);
		cc.obligarclick(driver.findElement(By.id("Summary_nextBtn")));
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.className("ta-care-omniscript-done")));
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
			Assert.assertTrue(cc.verificarOrden(orden+"*"));		
		} else {
			String texto = driver.findElement(By.xpath("//*[@class='ta-care-omniscript-done']//header//p//label")).getText();
			String orden = cc.getNumeros(texto);
			cc.buscarOrdenDiag(orden+"*");
			boolean h = false;
			cambioDeFrame(driver, By.id("Case_body"),0);
			List<WebElement> status = driver.findElement(By.id("Case_body")).findElements(By.tagName("td"));
			for(WebElement s : status ){
				if(s.getText().toLowerCase().contains("solicitud de reintegros"))
					h = true;
			}
			Assert.assertTrue(cc.aprobarAjusteConPerfilBOYDirector(orden, "Ua2544674"));
		}
	}
	
	

	@Test (groups = {"GestionesPerfilOficina", "Reintegros", "E2E","R1"}, dataProvider = "CuentaReintegros")
	public void TS148610_CRM_Movil_Mix_Pago_con_Efectivo_Reintegro_con_Efectivo_Menos_de_1000_Crm_OC(String cDNI) {
		imagen = "TS145909";
		detalles = null;
		detalles = imagen + " -Reintegros - DNI: " + cDNI;
		boolean gest = false;
		ges.BuscarCuenta("DNI", cDNI);
		mk.closeActiveTab();
		cc.irAFacturacion();
		cambioDeFrame(driver, By.className("card-top"),0);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className("card-top"), 0));
		String saldoinicial = driver.findElement(By.xpath("//*[@class='header-right']//*[@class='slds-text-heading_medium expired-date expired-black']")).getText();
		saldoinicial = saldoinicial.replaceAll("[-$,.]","");
		System.out.println(saldoinicial);
		driver.findElement(By.xpath("//*[@class='console-card active expired']//*[@class='icon-v-down-caret flyout-open expired-fly']")).click();
		sleep(3000);
		driver.findElement(By.xpath("//*[@class='community-flyout-actions-card']//li//a//span[contains(text(),'Solicitud de Reintegros')]")).click();
		cambioDeFrame(driver,By.id("stepRefundData_nextBtn"),0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("stepRefundData_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		selectByText(driver.findElement(By.id("selectReason")),"Pago duplicado");
		driver.findElement(By.id("inputCurrencyAmount")).sendKeys("700");
		driver.findElement(By.id("stepRefundData_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("stepRefundMethod_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-radio.ng-scope.itemSelected")), "equals", "efectivo");
		driver.findElement(By.id("stepRefundMethod_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Summary_nextBtn")));
		esperarElemento(driver, By.id("Summary_nextBtn"), 0);
		sleep(3000);
		cc.obligarclick(driver.findElement(By.id("Summary_nextBtn")));
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.className("ta-care-omniscript-done")));
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
			Assert.assertTrue(cc.verificarOrden(orden+"*"));		
		} else {
			String texto = driver.findElement(By.xpath("//*[@class='ta-care-omniscript-done']//header//p//label")).getText();
			String orden = cc.getNumeros(texto);
			cc.buscarOrdenDiag(orden+"*");
			boolean h = false;
			cambioDeFrame(driver, By.id("Case_body"),0);
			List<WebElement> status = driver.findElement(By.id("Case_body")).findElements(By.tagName("td"));
			for(WebElement s : status ){
				if(s.getText().toLowerCase().contains("solicitud de reintegros"))
					h = true;
			}
			Assert.assertTrue(cc.aprobarAjusteConPerfilBOYDirector(orden, "Ua2544674"));
		}	
	}
}