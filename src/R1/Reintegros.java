package R1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.CustomerCare;
import Pages.Marketing;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.TestBase;

public class Reintegros extends TestBase {

	private WebDriver driver;
	private CustomerCare cc;
	private LoginFw log;
	private GestionDeClientes_Fw ges;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private Marketing mk;
	String detalles;
	
	
	@BeforeClass (groups= "PerfilOficina")
	public void initSIT() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		mk = new Marketing(driver);
		log.LoginSit();
		ges.irAConsolaFAN();
	}
		
	//@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		mk = new Marketing(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
		
	}
	
	@BeforeMethod (alwaysRun = true)
	public void setup() {
		detalles = null;
		ges.selectMenuIzq("Inicio");
		ges.cerrarPestaniaGestion(driver);
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
	
	@Test (groups = {"PerfilOficina", "R1"}, dataProvider = "CuentaReintegros")
	public void TS145909_CRM_Movil_Mix_Solicitud_de_Reintegros_Efectivo_Debito_por_error_de_sistema_Efectivo_Exitosa_Crm_OC(String cDNI) {
		imagen = "TS145909";
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
			for (WebElement s : status ){
				if (s.getText().toLowerCase().contains("solicitud de reintegros"))
					h = true;
			}
			Assert.assertTrue(h);
		}
	}
	
	@Test (groups = {"PerfilOficina", "R1"}, dataProvider = "CuentaReintegros")
	public void TS148611_CRM_Movil_Mix_Pago_con_Efectivo_Reintegro_con_Efectivo_1000_Crm_OC(String cDNI) {
		imagen = "TS145909";
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
			for (WebElement s : status ){
				if (s.getText().toLowerCase().contains("solicitud de reintegros"))
					h = true;
			}
			Assert.assertTrue(cc.aprobarAjusteConPerfilBOYDirector(orden, "Ua2544674") && h);
		}
	}
	
	@Test (groups = {"PerfilOficina", "R1"}, dataProvider = "CuentaReintegros")
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
			for (WebElement s : status ){
				if (s.getText().toLowerCase().contains("solicitud de reintegros"))
					h = true;
			}
			Assert.assertTrue(cc.aprobarAjusteConPerfilBOYDirector(orden, "Ua2544674") && h);
		}	
	}
}