package Funcionalidades;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.CBS;
import Pages.CustomerCare;
import Pages.Marketing;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class ProblemasConRecargas extends TestBase {

	private WebDriver driver;
	private CustomerCare cc;
	private Marketing mk;
	private CBS cbs;
	private CBS_Mattu cbsm;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private GestionDeClientes_Fw ges;
	private LoginFw log;
	String detalles;
	
	@BeforeClass (groups= "PerfilOficina")
	public void Sit02() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		cc = new CustomerCare(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.LoginSit02();
		//ges.irAConsolaFAN();
	}
	
	//@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log.loginOOCC();
		ges.irAConsolaFAN();	
	}
		
	//@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() throws IOException, AWTException {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log.loginTelefonico();
		ges.irAConsolaFAN();
		
	}
	
	@BeforeMethod (alwaysRun = true)
	public void setup() throws Exception {
		detalles = null;
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();	
	}

	//@AfterMethod (alwaysRun = true)
	public void after() throws IOException {
		guardarListaTxt(sOrders);
		sOrders.clear();
		tomarCaptura(driver,imagen);
		sleep(2000);
	}

	//@AfterClass (alwaysRun = true)
	public void quit() throws IOException {
		driver.quit();
		sleep(5000);
	}
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = "PerfilOficina", dataProvider = "ProblemaRecargaPrepaga")
	public void GestionProblemasConRecargasTarjetaPrepaga(String sDNI, String sLinea, String sBatch, String sPin) {
		imagen = "GestionProblemasConRecargasTarjetaPrepaga";
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		System.out.println(datosInicial);
		boolean gest = false;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Inconvenientes con Recargas");
		cambioDeFrame(driver, By.id("RefillMethods_nextBtn"), 0);
		buscarYClick(driver.findElements(By.className("borderOverlay")), "equals", "tarjeta prepaga");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("BatchNumber")));
		driver.findElement(By.id("BatchNumber")).sendKeys(sBatch);
		driver.findElement(By.id("PIN")).sendKeys(sPin);
		driver.findElement(By.id("PrepaidCardData_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("Summary_nextBtn")));
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(10000);
		List <WebElement> element = driver.findElements(By.className("ta-care-omniscript-done"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("recarga realizada con \u00e9xito"))
				gest = true;
		}
		Assert.assertTrue(gest);
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 5));
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + 500 == datosFinal);
		String orden = cc.obtenerOrden(driver, "Problemas con Recargas");
		detalles = imagen + "-Problema Con Recargas-DNI: "+ sDNI + " - Orden: " + orden;
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "CuentaProblemaRecarga")
	public void problemaRecargaCredito(String sDNI, String sLinea) {
		imagen = "problemaRecargaCredito";
		String davoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(davoViejo.substring(0, 6));
		System.out.println(datosInicial);
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Inconvenientes con Recargas");
		cambioDeFrame(driver, By.id("RefillMethods_nextBtn"), 0);
		buscarYClick(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")),"contains", "tarjeta de cr\u00e9dito");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("CreditCardData_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains", "si");
		driver.findElement(By.id("CreditCardRefillAmount")).sendKeys("1000");
		driver.findElement(By.id("CreditCardRefillReceipt")).sendKeys("123");
		driver.findElement(By.id("CreditCardData_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("Summary_nextBtn")));
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(5000);
		boolean gest = false;
		for (WebElement x :  driver.findElements(By.cssSelector(".slds-box.ng-scope"))) {
			if (x.getText().toLowerCase().contains("recarga realizada con \u00e9xito!"))
				gest = true;
		}
		Assert.assertTrue(gest);
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 6));
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + 100000 == datosFinal);
		String orden = cc.obtenerOrden(driver, "Problemas con Recargas");
		detalles = imagen + "-Problema Con Recargas-DNI: "+ sDNI + " - Orden: " + orden;
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "CuentaProblemaRecarga")
	public void problemaRecargaOnline(String sDNI, String sLinea) {
		imagen = "problemaRecargaOnline";
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 6));
		System.out.println(datosInicial);
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Inconvenientes con Recargas");
		cambioDeFrame(driver, By.id("RefillMethods_nextBtn"), 0);
		buscarYClick(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")),"contains", "recarga online");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("RefillDate")));
		driver.findElement(By.id("RefillDate")).sendKeys("23-07-2018");
		driver.findElement(By.id("OnlineRefillAmount")).sendKeys("1000");
		driver.findElement(By.id("ReceiptCode")).sendKeys("123");
		cc.obligarclick(driver.findElement(By.id("OnlineRefillData_nextBtn")));
		try {
			ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("ExistingCase_nextBtn")));
			driver.findElement(By.xpath("//*[@id=\"SessionCase|0\"]/div/div[1]/label[2]/span/div/div")).click();
			driver.findElement(By.id("ExistingCase_nextBtn")).click();
			sleep(10000);
		} catch (Exception e) {}
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "si");
		File directory = new File("Dni.jpg");
		driver.findElement(By.id("FileAttach")).sendKeys(new File(directory.getAbsolutePath()).toString());
		driver.findElement(By.id("AttachDocuments_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(5000);
		boolean gest = false;
		for (WebElement x : driver.findElements(By.cssSelector(".slds-box.ng-scope"))) {
			if (x.getText().toLowerCase().contains("recarga realizada con \u00e9xito!"))
				gest = true;
		}
		Assert.assertTrue(gest);
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 6));
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + 100000 == datosFinal);
		String orden = cc.obtenerOrden(driver, "Problemas con Recargas");
		detalles = imagen + "-Problema Con Recargas-DNI: "+ sDNI + " - Orden: " + orden;
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "CuentaProblemaRecarga") 
	public void TS104346_CRM_Movil_Repro_Problemas_con_Recarga_Presencial_On_Line_Ofcom(String sDNI, String sLinea) {
		imagen = "TS104346";
		detalles = imagen + " -Problemas Con Recargas-DNI: " + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 6));
		System.out.println(datosInicial);
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Inconvenientes con Recargas");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		driver.findElements(By.className("borderOverlay")).get(1).click();
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("OnlineRefillData_nextBtn")));
		driver.findElement(By.id("RefillDate")).sendKeys("11-08-2018");
		driver.findElement(By.id("OnlineRefillAmount")).sendKeys("1000");
		driver.findElement(By.id("ReceiptCode")).sendKeys("111");
		driver.findElement(By.id("OnlineRefillData_nextBtn")).click();
		sleep(5000);
		try {
			driver.findElement(By.xpath("//*[@id=\"SessionCase|0\"]/div/div[1]/label[2]/span/div/div")).click();
			driver.findElement(By.id("ExistingCase_nextBtn")).click();
			sleep(5000);
		} catch (Exception e) {}
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		File directory = new File("Dni.jpg");
		driver.findElement(By.id("FileAttach")).sendKeys(new File(directory.getAbsolutePath()).toString());
		driver.findElement(By.id("AttachDocuments_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(5000);
		WebElement gestion = driver.findElement(By.className("ta-care-omniscript-done")).findElement(By.tagName("header")).findElement(By.tagName("h1"));
		Assert.assertTrue(gestion.getText().contains("Recarga realizada con \u00e9xito"));
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 7));
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + 100000 == datosFinal);
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "CuentaProblemaRecargaQuemada")
	public void TS104347_CRM_Movil_REPRO_Problemas_con_Recarga_Presencial_Tarjeta_Scratch_Caso_Nuevo_Quemada(String sDNI, String sLinea, String sTarjeta, String sPIN){
		imagen = "TS104347";
		detalles = imagen + " -Problema con recargas - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Inconvenientes con Recargas");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")), "contains", "tarjeta prepaga");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(8000);
		driver.findElement(By.id("BatchNumber")).sendKeys(sTarjeta);
		driver.findElement(By.id("PIN")).sendKeys(sPIN);
		driver.findElement(By.id("PrepaidCardData_nextBtn")).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-error-icon")));
		boolean error = false;
		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
			if (x.getText().toLowerCase().contains("la tarjeta ya fue utilizada para una recarga"))
				error = true;
		}
		Assert.assertTrue(error);
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "CuentaProblemaRecarga") 
	public void TS104351_CRM_Movil_Repro_Problemas_con_Recarga_On_line_Sin_comprobante_En_espera_del_cliente_Ofcom(String sDNI, String sLinea) {
		imagen = "TS104351";
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Inconvenientes con Recargas");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		driver.findElements(By.className("borderOverlay")).get(1).click();
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("RefillDate")).sendKeys("11-08-2018");
		driver.findElement(By.id("OnlineRefillAmount")).sendKeys("5000");
		driver.findElement(By.id("ReceiptCode")).sendKeys("111");
		driver.findElement(By.id("OnlineRefillData_nextBtn")).click();
		sleep(5000);		
		try {
			driver.findElement(By.xpath("//*[@id=\"SessionCase|0\"]/div/div[1]/label[2]/span/div/div")).click();
			driver.findElement(By.id("ExistingCase_nextBtn")).click();
			sleep(5000);
		} catch (Exception e) {}	
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("AttachDocuments_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(5000);
		WebElement gestion = driver.findElement(By.className("ta-care-omniscript-done")).findElement(By.tagName("header")).findElement(By.tagName("h1"));
		Assert.assertTrue(gestion.getText().contains("La gesti\u00f3n fue derivada"));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "ProblemaRecargaPrepaga") 
	public void TS104353_CRM_Movil_Repro_Problemas_con_Recarga_Presencial_Tarjeta_Scratch_Caso_Nuevo_Tarjeta_Activa_y_Disponible_Ofcom(String sDNI, String sLinea, String sTarjeta, String sPIN) {
		imagen = "TS104353";
		detalles = imagen + " -Problemas Con Recargas-DNI: " + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		System.out.println(datosInicial);
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Inconvenientes con Recargas");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		driver.findElements(By.className("borderOverlay")).get(0).click();
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("BatchNumber")).sendKeys(sTarjeta);
		driver.findElement(By.id("PIN")).sendKeys(sPIN);
		driver.findElement(By.id("PrepaidCardData_nextBtn")).click();
		sleep(7000);
		WebElement estado = driver.findElement(By.id("PrepaidCardStatusLabel"));
		Assert.assertTrue(estado.getText().toLowerCase().contains("activa"));
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(5000);
		WebElement gestion = driver.findElement(By.className("ta-care-omniscript-done")).findElement(By.tagName("header")).findElement(By.tagName("h1"));
		Assert.assertTrue(gestion.getText().contains("Recarga realizada con \u00e9xito"));
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 5));
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + 500 == datosFinal);
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "ProblemaRecargaPrepaga")
	public void TS135714_CRM_Movil_PRE_Problemas_con_Recarga_Telefonico_Tarjeta_Scratch_Caso_Nuevo_Tarjeta_Activa_y_Disponible(String sDNI, String sLinea, String sTarjeta, String sPIN){
		imagen = "TS135714";
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		System.out.println(datosInicial);
		detalles = imagen + " -Problema con recargas - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Inconvenientes con Recargas");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")), "contains", "tarjeta prepaga");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("BatchNumber")).sendKeys(sTarjeta);
		driver.findElement(By.id("PIN")).sendKeys(sPIN);
		driver.findElement(By.id("PrepaidCardData_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(5000);
		boolean gest = false;
		List <WebElement> prob = driver.findElements(By.cssSelector(".slds-box.ng-scope"));
		for (WebElement x : prob) {
			if (x.getText().toLowerCase().contains("recarga realizada con \u00e9xito"))
				gest = true;
		}
		Assert.assertTrue(gest);
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 5));
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + 500 == datosFinal);
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = "PerfilTelefonico", dataProvider = "CuentaProblemaRecarga")
	public void TS125372_CRM_Movil_Repro_Problemas_con_Recarga_Presencial_TC(String sDNI, String sLinea) {
		imagen = "TS125372";
		boolean gestion = false;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 7));
		System.out.println(datosInicial);
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(8000);
		cc.irAGestionEnCard("Inconvenientes con Recargas");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		driver.findElements(By.className("borderOverlay")).get(1).click();
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("RefillDate")).sendKeys("01-12-2018");
		driver.findElement(By.id("OnlineRefillAmount")).sendKeys("1000");
		driver.findElement(By.id("ReceiptCode")).sendKeys("123");
		driver.findElement(By.id("OnlineRefillData_nextBtn")).click();
		sleep(7000);
		try {
			driver.findElement(By.xpath("//*[@id=\"SessionCase|0\"]/div/div[1]/label[2]/span/div/div")).click();
			driver.findElement(By.id("ExistingCase_nextBtn")).click();
			sleep(10000);
		} catch (Exception e) {}
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		File directory = new File("Dni.jpg");
		driver.findElement(By.id("FileAttach")).sendKeys(new File(directory.getAbsolutePath()).toString());
		driver.findElement(By.id("AttachDocuments_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-done-icon")));
		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
			if (x.getText().contains("Recarga realizada con \u00e9xito"))
				gestion = true;
		}
		Assert.assertTrue(gestion);
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 7));
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + 100000 == datosFinal);
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider = "ProblemaRecargaPrepaga")
	public void TS104330_CRM_Movil_REPRO_Problemas_con_Recarga_Telefonico_Tarjeta_Scratch_Caso_Existente(String sDNI, String sLinea, String sTarjeta, String sPin) {
		imagen = "TS104330";
		boolean gestion = false;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		System.out.println(datosInicial);
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(8000);
		cc.irAGestionEnCard("Inconvenientes con Recargas");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		buscarYClick(driver.findElements(By.className("borderOverlay")), "equals", "tarjeta prepaga");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("BatchNumber")).sendKeys(sTarjeta);
		driver.findElement(By.id("PIN")).sendKeys(sPin);
		driver.findElement(By.id("PrepaidCardData_nextBtn")).click();
		sleep(10000);
		try {
			driver.findElement(By.xpath("//*[@id=\"SessionCase|0\"]/div/div[1]/label[2]/span/div/div")).click();
			driver.findElement(By.id("ExistingCase_nextBtn")).click();
			sleep(10000);
		} catch (Exception e) {}
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(10000);
		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
			if (x.getText().toLowerCase().contains("recarga realizada con \u00e9xito"))
				gestion = true;
		}
		Assert.assertTrue(gestion);
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 5));
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + 500 == datosFinal);
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider = "ProblemaRecargaPrepaga") 
	public void TS104332_CRM_Movil_Repro_Problemas_con_Recarga_Telefonico_Tarjeta_Scratch_Caso_Nuevo_Tarjeta_Activa_y_Disponible(String sDNI, String sLinea, String sTarjeta, String sPIN) {
		imagen = "TS104332";
		detalles = imagen + " -Problemas Con Recargas-DNI: " + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		System.out.println(datosInicial);
		ges.BuscarCuenta("DNI", sDNI);
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		cc.irAGestionEnCard("Inconvenientes con Recargas");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		driver.findElements(By.className("borderOverlay")).get(0).click();
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("BatchNumber")).sendKeys(sTarjeta);
		driver.findElement(By.id("PIN")).sendKeys(sPIN);
		driver.findElement(By.id("PrepaidCardData_nextBtn")).click();
		sleep(5000);
		WebElement estado = driver.findElement(By.id("PrepaidCardStatusLabel"));
		Assert.assertTrue(estado.getText().toLowerCase().contains("activa"));
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(5000);
		WebElement gestion = driver.findElement(By.className("ta-care-omniscript-done")).findElement(By.tagName("header")).findElement(By.tagName("h1"));
		Assert.assertTrue(gestion.getText().contains("Recarga realizada con \u00e9xito"));
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 5));
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + 500 == datosFinal);
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider = "ProblemaRecargaPrepaga")
	public void TS104338_CRM_Movil_REPRO_Problemas_con_Recarga_Telefonico_Tarjeta_Scratch_Caso_Nuevo_Quemada(String sDNI, String sLinea, String sTarjeta, String sPIN) {
		imagen = "TS104338";
		boolean gestion = false, error = false;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Inconvenientes con Recargas");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		buscarYClick(driver.findElements(By.className("borderOverlay")), "equals", "tarjeta prepaga");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("BatchNumber")).sendKeys(sTarjeta);
		driver.findElement(By.id("PIN")).sendKeys(sPIN);
		driver.findElement(By.id("PrepaidCardData_nextBtn")).click();
		sleep(10000);
		try {
			driver.findElement(By.xpath("//*[@id=\"SessionCase|0\"]/div/div[1]/label[2]/span/div/div")).click();
			driver.findElement(By.id("ExistingCase_nextBtn")).click();
			sleep(10000);
		} catch (Exception e) {}
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(10000);
		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
			if (x.getText().toLowerCase().contains("recarga realizada con \u00e9xito"))
				gestion = true;
		}
		Assert.assertTrue(gestion);
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 5));
		Assert.assertTrue(datosInicial + 500 == datosFinal);
		mk.closeActiveTab();
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		buscarYClick(driver.findElements(By.className("borderOverlay")), "equals", "tarjeta prepaga");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("BatchNumber")).sendKeys(sTarjeta);
		driver.findElement(By.id("PIN")).sendKeys(sPIN);
		driver.findElement(By.id("PrepaidCardData_nextBtn")).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-error-icon")));
		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
			if (x.getText().contains("La tarjeta ya fue utilizada para una recarga"))
				error = true;
		}
		Assert.assertTrue(error);
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider = "CuentaProblemaRecarga")
	public void TS104344_CRM_Movil_Repro_Problemas_con_Recarga_Telefonico_On_Line(String sDNI, String sLinea) {
		imagen = "TS104344";
		boolean gestion = false;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Inconvenientes con Recargas");
		cambioDeFrame(driver, By.id("RefillMethods_nextBtn"), 0);
		driver.findElements(By.className("borderOverlay")).get(1).click();
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("OnlineRefillData_nextBtn")));
		driver.findElement(By.id("RefillDate")).sendKeys("01-12-2018");
		driver.findElement(By.id("OnlineRefillAmount")).sendKeys("5000");
		driver.findElement(By.id("ReceiptCode")).sendKeys("123");
		driver.findElement(By.id("OnlineRefillData_nextBtn")).click();
		sleep(7000);
		try {
			driver.findElement(By.xpath("//*[@id=\"SessionCase|0\"]/div/div[1]/label[2]/span/div/div")).click();
			driver.findElement(By.id("ExistingCase_nextBtn")).click();
			sleep(10000);
		} catch (Exception e) {}
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("AttachDocuments_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-pending-icon")));
		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
			if (x.getText().contains("La gesti\u00f3n fue derivada"))
				gestion = true;
		}
		Assert.assertTrue(gestion);
	}
}