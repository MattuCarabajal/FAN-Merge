package Funcionalidades;

import java.awt.AWTException;
import java.io.File;
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
import Pages.CBS;
import Pages.CustomerCare;
import Pages.Marketing;
import Pages.SalesBase;
import Pages.setConexion;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class ProblemasConRecargas extends TestBase {

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	private Marketing mk;
	private CBS cbs;
	private CBS_Mattu cbsm;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	@BeforeClass (alwaysRun = true)
	public void init() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		loginMerge(driver);
		sleep(22000);
		try {
			cc.cajonDeAplicaciones("Consola FAN");
		} catch(Exception e) {
			/*sleep(3000);
			waitForClickeable(driver,By.id("tabBar"));
			driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
			sleep(6000);*/
		}		
		driver.switchTo().defaultContent();
		sleep(6000);
	}
	
	@BeforeMethod(alwaysRun=true)
	public void setup() throws Exception {
		sleep(3000);
		goToLeftPanel4(driver, "Inicio");
		sleep(10000);
		try {
			sb.cerrarPestaniaGestion(driver);
		} catch (Exception ex1) {}
		Accounts accountPage = new Accounts(driver);
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		boolean enc = false;
		int index = 0;
		for(WebElement frame : frames) {
			try {
				System.out.println("aca");
				driver.switchTo().frame(frame);
				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).getText();
				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).isDisplayed();
				driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
				enc = true;
				break;
			} catch(NoSuchElementException e) {
				index++;
				driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
			}
		}
		if(enc == false)
			index = -1;
		try {
			driver.switchTo().frame(frames.get(index));
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Elemento no encontrado en ningun frame 2.");			
		}
		List<WebElement> botones = driver.findElements(By.tagName("button"));
		for (WebElement UnB : botones) {
			System.out.println(UnB.getText());
			if (UnB.getText().equalsIgnoreCase("gesti\u00f3n de clientes")) {
				UnB.click();
				break;
			}
		}		
		sleep(10000);		
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
	
	@Test (groups = {"GestionesPerfilOficina", "ProblemasConRecargas","E2E","Ciclo3"}, dataProvider = "ProblemaRecargaPrepaga")  //Se necesitan nuevos numeros de tarjeta, solo se pueden usar 1 vez
	public void GestionProblemasConRecargasTarjetaPrepaga(String cDNI,String sLinea, String cBatch, String cPin) {
		CBS_Mattu verifM = new CBS_Mattu();
		CBS verif = new CBS();
		String saldo = verif.ObtenerValorResponse(verifM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer saldo1 = Integer.parseInt(saldo.substring(0, 5));
		System.out.println(saldo1);
		imagen = "GestionProblemasConRecargasTarjetaPrepaga";
		detalles = null;
		detalles = imagen+"-Problema Con Recargas-DNI:"+cDNI;
		boolean gest = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		buscarYClick(driver.findElements(By.className("borderOverlay")), "equals", "tarjeta prepaga");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("BatchNumber")).sendKeys(cBatch);
		driver.findElement(By.id("PIN")).sendKeys(cPin);
		driver.findElement(By.id("PrepaidCardData_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(10000);
		boolean b = false;
		List <WebElement> element = driver.findElements(By.className("ta-care-omniscript-done"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("recarga realizada con \u00e9xito")) {
				gest = true;
			}
		}
		Assert.assertTrue(gest);
		String saldo2 = verif.ObtenerValorResponse(verifM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer saldo3 = Integer.parseInt(saldo2.substring(0, 5));
		System.out.println(saldo3);
		if((saldo1+500 == saldo3)) {
			b = true;
		}
		Assert.assertTrue(b);
		String orden = cc.obtenerOrden(driver, "Problema con recarga con tarjeta prepaga");
		detalles+=" - Orden: "+orden;
		Assert.assertTrue(cc.verificarOrden(orden));
	}
	
	@Test (groups = {"ProblemasConRecargas", "GestionesPerfilOficina","E2E","Ciclo3"}, dataProvider="CuentaProblemaRecarga") //Error al intentar impactar la recarga
	public void poblemaRecargaCredito(String cDNI, String sLinea) {
		imagen = "problemaRecargaCredito";
		detalles = null;
		detalles = imagen+" - DNI: "+cDNI+" - Linea: "+sLinea;
		CBS_Mattu verifM = new CBS_Mattu();
		CBS verif = new CBS();
		String saldo = verif.ObtenerValorResponse(verifM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer saldo1 = Integer.parseInt(saldo.substring(0, (saldo.length()) - 1));
		System.out.println(saldo1);
		imagen = "poblemaRecargaCredito";
		detalles = null;
		detalles = imagen+"-Problema Con Recargas-DNI:"+cDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(8000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")),"contains", "tarjeta de cr\u00e9dito");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains", "si");
		driver.findElement(By.id("CreditCardRefillAmount")).sendKeys("123");
		driver.findElement(By.id("CreditCardRefillReceipt")).sendKeys("123");
		driver.findElement(By.id("CreditCardData_nextBtn")).click();
		sleep(8000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(8000);
		boolean a = false;
		boolean b = false;
		List <WebElement> conf = driver.findElements(By.cssSelector(".slds-box.ng-scope"));
		for(WebElement x : conf) {
			if(x.getText().toLowerCase().contains("recarga realizada con \u00e9xito!")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
		String saldo2 = verif.ObtenerValorResponse(verifM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer saldo3 = Integer.parseInt(saldo2.substring(0, (saldo2.length()) - 1));
		System.out.println(saldo3);
		if((saldo1+123000 == saldo3)) {
			b = true;
		}
		Assert.assertTrue(b);
		sleep(5000);
		String orden = cc.obtenerOrden(driver, "Problemas con Recargas");
		detalles+=" - Orden: "+orden;
		sOrders.add("Recargas, orden numero: " + orden + " con DNI: " + cDNI );
		System.out.println(sOrders);
	}
	
	@Test (groups = {"ProblemasConRecargas", "GestionesPerfilOficina","E2E","Ciclo3"}, dataProvider="CuentaProblemaRecarga")
	public void problemaRecargaOnline(String sDNI, String sLinea) {
		imagen= "problemaRecargaOnline";
		detalles = null;
		detalles = imagen+" - DNI:"+sDNI+" - Linea: "+sLinea;
		CBS_Mattu verifM = new CBS_Mattu();
		CBS verif = new CBS();
		String saldo = verif.ObtenerValorResponse(verifM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer saldo1 = Integer.parseInt(saldo.substring(0, (saldo.length()) - 1));
		imagen = "problemaRecargaOnline";
		detalles = null;
		detalles = imagen+"-Problema Con Recargas-DNI:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(8000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")),"contains", "recarga online");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(8000);
		driver.findElement(By.id("RefillDate")).sendKeys("23-07-2018");
		sleep(8000);
		driver.findElement(By.id("RefillAmount")).sendKeys("123");
		sleep(8000);
		driver.findElement(By.id("ReceiptCode")).sendKeys("123");
		driver.findElement(By.id("OnlineRefillData_nextBtn")).click();
		sleep(8000);
		driver.findElement(By.xpath("//*[@id=\"SessionCase|0\"]/div/div[1]/label[2]/span/div/div")).click();
		driver.findElement(By.id("ExistingCase_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains","si");
		WebElement img = driver.findElement(By.id("FileAttach"));
		File directory = new File("Dni.jpg");
		img.sendKeys(new File(directory.getAbsolutePath()).toString());
		sleep(8000);
		driver.findElement(By.id("AttachDocuments_nextBtn")).click();
		sleep(8000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(8000);
		boolean a = false;
		boolean b = false;
		List <WebElement> conf = driver.findElements(By.cssSelector(".slds-box.ng-scope"));
		for(WebElement x : conf) {
			if(x.getText().toLowerCase().contains("recarga realizada con \u00e9xito!")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
		String saldo2 = verif.ObtenerValorResponse(verifM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer saldo3 = Integer.parseInt(saldo2.substring(0, (saldo2.length()) - 1));
		if(saldo1+123000 == saldo3) {
			b = true;
		}
		Assert.assertTrue(b);
		sleep(5000);
		String orden = cc.obtenerOrden(driver, "Problemas con Recargas");
		detalles+=" - Orden: "+orden;
		sOrders.add("Recargas, orden numero: " + orden + " con DNI: " + sDNI );
		System.out.println(sOrders);
	}
	
	@Test (groups = {"ProblemasConRecargas", "GestionesPerfilOficina", "E2E", "Ciclo3"}, dataProvider = "CuentaProblemaRecarga") 
	public void TS104346_CRM_Movil_Repro_Problemas_con_Recarga_Presencial_On_Line_Ofcom(String sDNI, String sLinea) {
		imagen = "TS104346";
		detalles = null;
		detalles = imagen + " -Problemas Con Recargas-DNI: " + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		driver.findElements(By.className("borderOverlay")).get(1).click();
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("OnlineRefillData_nextBtn")));
		driver.findElement(By.id("RefillDate")).sendKeys("11-08-2018");
		driver.findElement(By.id("RefillAmount")).sendKeys("5000");
		driver.findElement(By.id("ReceiptCode")).sendKeys("111");
		driver.findElement(By.id("OnlineRefillData_nextBtn")).click();
		sleep(5000);
		try {
			driver.findElement(By.xpath("//*[@id=\"SessionCase|0\"]/div/div[1]/label[2]/span/div/div")).click();
			driver.findElement(By.id("ExistingCase_nextBtn")).click();
			sleep(5000);
		} catch (Exception e) {}
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("FileAttach")).sendKeys("C:\\Users\\Sofia Chardin\\Desktop\\DNI.jpg");
		driver.findElement(By.id("AttachDocuments_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(5000);
		WebElement gestion = driver.findElement(By.className("ta-care-omniscript-done")).findElement(By.tagName("header")).findElement(By.tagName("h1"));
		Assert.assertTrue(gestion.getText().contains("Recarga realizada con \u00e9xito"));
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 5));
		Assert.assertTrue(datosInicial + 500 == datosFinal);
	}
	
	@Test (groups = {"ProblemasConRecargas", "GestionesPerfilOficina","E2E","Ciclo3"}, dataProvider="CuentaProblemaRecargaQuemada")
	public void TS104347_CRM_Movil_REPRO_Problemas_con_Recarga_Presencial_Tarjeta_Scratch_Caso_Nuevo_Quemada(String cDNI,String sLinea, String cSerie, String cPIN){
		CBS_Mattu verifM = new CBS_Mattu();
		CBS verif = new CBS();
		String saldo = verif.ObtenerValorResponse(verifM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer saldo1 = Integer.parseInt(saldo.substring(0, 5));
		System.out.println(saldo1);
		imagen = "TS104347";
		detalles = null;
		detalles = imagen + " -Problema con recargas - DNI: " + cDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(8000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")), "contains", "tarjeta prepaga");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(8000);
		driver.findElement(By.id("BatchNumber")).sendKeys(cSerie);
		driver.findElement(By.id("PIN")).sendKeys(cPIN);
		driver.findElement(By.id("PrepaidCardData_nextBtn")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-error-icon")));
		boolean b = false;
		for(WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
			if(x.getText().toLowerCase().contains("la tarjeta ya fue utilizada para una recarga")) {
				b = true;
			}
		}
		Assert.assertTrue(b);
	}
	
	@Test (groups = {"ProblemasConRecargas", "GestionesPerfilOficina", "E2E", "Ciclo3"}, dataProvider = "CuentaProblemaRecarga") 
	public void TS104351_CRM_Movil_Repro_Problemas_con_Recarga_On_line_Sin_comprobante_En_espera_del_cliente_Ofcom(String sDNI, String sLinea) {
		imagen = "TS104351";
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		driver.findElements(By.className("borderOverlay")).get(1).click();
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("RefillDate")).sendKeys("11-08-2018");
		driver.findElement(By.id("RefillAmount")).sendKeys("5000");
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
	
	@Test (groups = {"ProblemasConRecargas", "GestionesPerfilOficina", "E2E", "Ciclo3"}, dataProvider = "CuentaProblemaRecargaAYD") 
	public void TS104353_CRM_Movil_Repro_Problemas_con_Recarga_Presencial_Tarjeta_Scratch_Caso_Nuevo_Tarjeta_Activa_y_Disponible_Ofcom(String sDNI, String sLinea, String sSerie, String sPIN) {
		imagen = "TS104353";
		detalles = null;
		detalles = imagen + " -Problemas Con Recargas-DNI: " + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		driver.findElements(By.className("borderOverlay")).get(0).click();
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("BatchNumber")).sendKeys(sSerie);
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
		Assert.assertTrue(datosInicial + 5000000 == datosFinal);
	}
	
	@Test (groups = {"ProblemasConRecargas", "GestionesPerfilTelefonico", "E2E", "Ciclo3"}, dataProvider = "CuentaProblemaRecarga")
	public void TS125372_CRM_Movil_Repro_Problemas_con_Recarga_Presencial_TC(String sDNI, String sLinea) {
		imagen = "TS125372";
		boolean gestion = false;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(8000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		driver.findElements(By.className("borderOverlay")).get(1).click();
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("RefillDate")).sendKeys("01-12-2018");
		driver.findElement(By.id("RefillAmount")).sendKeys("5000");
		driver.findElement(By.id("ReceiptCode")).sendKeys("123");
		driver.findElement(By.id("OnlineRefillData_nextBtn")).click();
		sleep(7000);
		try {
			driver.findElement(By.xpath("//*[@id=\"SessionCase|0\"]/div/div[1]/label[2]/span/div/div")).click();
			driver.findElement(By.id("ExistingCase_nextBtn")).click();
			sleep(10000);
		} catch (Exception e) {}
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("FileAttach")).sendKeys("C:\\Users\\Nicolas\\Desktop\\descarga.jpg");
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
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 5));
		Assert.assertTrue(datosInicial + 500 == datosFinal);
	}
	
	@Test (groups = {"ProblemasConRecargas", "GestionesPerfilOficina","E2E","Ciclo3"}, dataProvider="CuentaProblemaRecargaAYD") //Lote: 11120000001688 PIN: 02222
	public void TS135714_CRM_Movil_PRE_Problemas_con_Recarga_Telefonico_Tarjeta_Scratch_Caso_Nuevo_Tarjeta_Activa_y_Disponible(String cDNI,String sLinea, String cSerie, String cPIN){
		CBS_Mattu verifM = new CBS_Mattu();
		CBS verif = new CBS();
		String saldo = verif.ObtenerValorResponse(verifM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer saldo1 = Integer.parseInt(saldo.substring(0, 5));
		System.out.println(saldo1);
		imagen = "TS135714";
		detalles = null;
		detalles = imagen + " -Problema con recargas - DNI: " + cDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(8000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")), "contains", "tarjeta prepaga");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(8000);
		driver.findElement(By.id("BatchNumber")).sendKeys("11120000009352");
		driver.findElement(By.id("PIN")).sendKeys("0589");
		driver.findElement(By.id("PrepaidCardData_nextBtn")).click();
		sleep(15000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(10000);
		boolean b = false;
		boolean a = false;
		List <WebElement> prob = driver.findElements(By.cssSelector(".slds-box.ng-scope"));
		for(WebElement x : prob) {
			if(x.getText().toLowerCase().contains("recarga realizada con \u00e9xito")) {
				b = true;
			}
		}
		Assert.assertTrue(b);
		String saldo2 = verif.ObtenerValorResponse(verifM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer saldo3 = Integer.parseInt(saldo2.substring(0, 5));
		if(saldo1+500 == saldo3) {
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"ProblemasConRecargas", "GestionesPerfilTelefonico", "E2E", "Ciclo3"}, dataProvider = "CuentaProblemaRecarga")
	public void TS104330_CRM_Movil_REPRO_Problemas_con_Recarga_Telefonico_Tarjeta_Scratch_Caso_Existente(String sDNI, String sLinea) {
		imagen = "TS104330";
		boolean gestion = false;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		System.out.println(datosInicial);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(8000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		buscarYClick(driver.findElements(By.className("borderOverlay")), "equals", "tarjeta prepaga");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("BatchNumber")).sendKeys("11120000009323");
		driver.findElement(By.id("PIN")).sendKeys("0418");
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
	
	@Test (groups = {"ProblemasConRecargas", "GestionesPerfilTelefonico", "E2E", "Ciclo3"}, dataProvider = "CuentaProblemaRecargaAYD") 
	public void TS104332_CRM_Movil_Repro_Problemas_con_Recarga_Telefonico_Tarjeta_Scratch_Caso_Nuevo_Tarjeta_Activa_y_Disponible(String sDNI, String sLinea, String sSerie, String sPIN) {
		imagen = "TS104332";
		detalles = null;
		detalles = imagen + " -Problemas Con Recargas-DNI: " + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		driver.findElements(By.className("borderOverlay")).get(0).click();
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("BatchNumber")).sendKeys(sSerie);
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
		Assert.assertTrue(datosInicial + 5000000 == datosFinal);
	}
	
	@Test (groups = {"ProblemasConRecargas", "GestionesPerfilTelefonico", "E2E", "Ciclo3"}, dataProvider = "CuentaProblemaRecarga")
	public void TS104338_CRM_Movil_REPRO_Problemas_con_Recarga_Telefonico_Tarjeta_Scratch_Caso_Nuevo_Quemada(String sDNI, String sLinea) {
		imagen = "TS104338";
		boolean gestion = false, error = false;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(8000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		buscarYClick(driver.findElements(By.className("borderOverlay")), "equals", "tarjeta prepaga");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("BatchNumber")).sendKeys("11120000009321");
		driver.findElement(By.id("PIN")).sendKeys("0804");
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
		driver.findElement(By.id("BatchNumber")).sendKeys("11120000009321");
		driver.findElement(By.id("PIN")).sendKeys("0804");
		driver.findElement(By.id("PrepaidCardData_nextBtn")).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-error-icon")));
		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
			if (x.getText().contains("La tarjeta ya fue utilizada para una recarga"))
				error = true;
		}
		Assert.assertTrue(error);
	}
	
	@Test (groups = {"ProblemasConRecargas", "GestionesPerfilTelefonico", "E2E", "Ciclo3"}, dataProvider = "CuentaProblemaRecarga")
	public void TS104344_CRM_Movil_Repro_Problemas_con_Recarga_Telefonico_On_Line(String sDNI, String sLinea) {
		imagen = "TS104344";
		boolean gestion = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(8000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		driver.findElements(By.className("borderOverlay")).get(1).click();
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("RefillDate")).sendKeys("01-12-2018");
		driver.findElement(By.id("RefillAmount")).sendKeys("5000");
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