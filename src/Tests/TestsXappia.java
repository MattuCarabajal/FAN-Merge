package Tests;

import java.awt.AWTException;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.BasePage;
import Pages.Accounts;
import Pages.ContactSearch;
import Pages.CustomerCare;
import Pages.Marketing;
import Pages.PagePerfilTelefonico;
import Pages.SalesBase;
import Pages.setConexion;

public class TestsXappia extends TestBase {

	private WebDriver driver;
	private CustomerCare cc;
	private SalesBase sb;
	private Marketing m;
	
	
	@BeforeClass (groups = "UAT")
	public void loginUAT() {
		driver = setConexion.setupEze();
		driver.get("https://telecomcrm--uat.cs53.my.salesforce.com");
		sleep(2000);
		driver.findElement(By.id("idp_section_buttons")).click();
		sleep(2000);
		driver.findElement(By.name("Ecom_User_ID")).sendKeys("uat569076");
 		driver.findElement(By.name("Ecom_Password")).sendKeys("Testa10k");
 		driver.findElement(By.id("loginButton2")).click();
 		sleep(5000);
 		cc = new CustomerCare(driver);
		sb = new SalesBase(driver);
		m = new Marketing(driver);
	}
	
	//@BeforeClass (groups = "SIT")
	public void loginSIT() {
		driver = setConexion.setupEze();
		driver.get("https://crm--sit.cs14.my.salesforce.com/");
		sleep(2000);
		driver.findElement(By.id("idp_section_buttons")).click();
		sleep(2000);
		driver.findElement(By.name("Ecom_User_ID")).sendKeys("UAT195528");
 		driver.findElement(By.name("Ecom_Password")).sendKeys("Testa10k");
 		driver.findElement(By.id("loginButton2")).click();
 		sleep(5000);
 		cc = new CustomerCare(driver);
		sb = new SalesBase(driver);
		m = new Marketing(driver);
	}
	
	@BeforeMethod (groups = "UAT")
	public void beforeUAT() {
		driver.get("https://telecomcrm--uat.cs53.my.salesforce.com");
	}
	
	//@BeforeMethod (groups = "SIT")
	public void beforeSIT() {
		driver.get("https://crm--sit.cs14.my.salesforce.com/");
	}
	
	//@AfterClass (alwaysRun = true)
	public void quit() {
		driver.quit();
	}
	
	
	//---------------------------------------- METODOS PRIVADOS ----------------------------------------\\
	
	private void irAConsolaFAN() {
		try {
			cc.cajonDeAplicaciones("Consola FAN");
		} catch (Exception e) {
			driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
			sleep(8000);
		}
	}
	
	private void carrito() {
		List <WebElement> boton = driver.findElements(By.cssSelector(".slds-button.slds-button.slds-button--icon"));
		for(WebElement x : boton) {
			if(x.getText().toLowerCase().equals("catalogo")) {
				x.click();
				break;
			}
		}
	}
	
	private void irAGestionDeClientes() {
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		boolean enc = false;
		int index = 0;
		for (WebElement frame : frames) {
			try {
				driver.switchTo().frame(frame);
				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).getText();
				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).isDisplayed();
				driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
				enc = true;
				break;
			} catch (NoSuchElementException e) {
				index++;
				driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
			}
		}
		if (enc == false)
			index = -1;
		try {
			driver.switchTo().frame(frames.get(index));
		} catch (ArrayIndexOutOfBoundsException e) {}
		buscarYClick(driver.findElements(By.tagName("button")), "equals", "gesti\u00f3n de clientes");
	}
	
	private void LoguearAgente(){
		irAConsolaFAN();
		driver.findElement(By.id("userNav")).click();
		driver.findElement(By.id("app_logout")).click();
		sleep(9000);
		driver.findElement(By.id("userDropdown")).click();
		sleep(3000);
		driver.findElement(By.id("logout")).click();
		sleep(5000);
		driver.get(urlAmbiente);
		driver.findElement(By.id("cancel_idp_hint")).click();
		sleep(7500);
		driver.findElement(By.id("idp_section_buttons")).click();
		sleep(7000);
		if(TestBase.urlAmbiente.contains("sit")) {
			driver.findElement(By.name("Ecom_User_ID")).sendKeys("UAT549492");
			sleep(3000);
			driver.findElement(By.name("Ecom_Password")).sendKeys("Testa10k");
		
		}else {
			driver.findElement(By.name("Ecom_User_ID")).sendKeys("uat195528");
			sleep(3000);
			driver.findElement(By.name("Ecom_Password")).sendKeys("Testa10k");
		}
		sleep(3000);
		driver.findElement(By.id("loginButton2")).click();
		sleep(8000);
	}
	
	private void LoguearTelefonico() {
		irAConsolaFAN();
		driver.findElement(By.id("userNav")).click();
		driver.findElement(By.id("app_logout")).click();
		sleep(9000);
		driver.findElement(By.id("userDropdown")).click();
		sleep(3000);
		driver.findElement(By.id("logout")).click();
		sleep(5000);
		driver.get(urlAmbiente);
		driver.findElement(By.id("cancel_idp_hint")).click();
		sleep(7500);
		driver.findElement(By.id("idp_section_buttons")).click();
		sleep(7000);
		if(TestBase.urlAmbiente.contains("uat")) {
			driver.findElement(By.name("Ecom_User_ID")).sendKeys("uat592149");
			sleep(3000);
			driver.findElement(By.name("Ecom_Password")).sendKeys("Testa10k");
		}else {
			driver.findElement(By.name("Ecom_User_ID")).sendKeys("UAT569076");
			sleep(3000);
			driver.findElement(By.name("Ecom_Password")).sendKeys("Testa10k");
		}
		sleep(3000);
		driver.findElement(By.id("loginButton2")).click();
		sleep(8000);
		}
	
	public void LoguearBackOffice() {
		irAConsolaFAN();
		driver.findElement(By.id("userNav")).click();
		driver.findElement(By.id("app_logout")).click();
		sleep(9000);
		driver.findElement(By.id("userDropdown")).click();
		sleep(3000);
		driver.findElement(By.id("logout")).click();
		sleep(5000);
		driver.get(urlAmbiente);
		driver.findElement(By.id("cancel_idp_hint")).click();
		sleep(7500);
		driver.findElement(By.id("idp_section_buttons")).click();
		sleep(7000);
		if(TestBase.urlAmbiente.contains("sit")) {
			driver.findElement(By.name("Ecom_User_ID")).sendKeys("uat569076");
			sleep(3000);
			driver.findElement(By.name("Ecom_Password")).sendKeys("Testa10k");
			
		}else {
			driver.findElement(By.name("Ecom_User_ID")).sendKeys("UAT569076");
			sleep(3000);
			driver.findElement(By.name("Ecom_Password")).sendKeys("Testa10k");
			}
		sleep(3000);
		driver.findElement(By.id("loginButton2")).click();
		sleep(8000);
	}
	
	//--------------------------------------------------------------------------------------------------\\
	
	
	@Test (groups = "UAT")
	public void TXU0001_Gestiones_Del_Panel_Izquierdo_En_Consola_FAN() {
		irAConsolaFAN();
		driver.switchTo().frame(cambioFrame(driver, By.className("slds-spinner_container")));
		WebElement gestiones = driver.findElement(By.className("slds-spinner_container"));
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0," + gestiones.getLocation().y + ")");
		Assert.assertTrue(!gestiones.getText().isEmpty());
	}
	
	@Test (groups = "SIT")
	public void TXS0001_SmokeTest_Tiempo_De_Carga_De_Consola_FAN() {
		Date start = new Date();
		irAConsolaFAN();
		Date end = new Date();
		long startTime = start.getTime();
		long endTime = end.getTime();
		long tiempoTotal = endTime - startTime;
		tiempoTotal = tiempoTotal / 1000;
		Assert.assertTrue(tiempoTotal < 55);
	}
	
	@Test (groups = "UAT")
	public void TXU0002_Verificacion_De_Superposicion_De_Elementos_En_El_Carrito() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", "22222001");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		WebElement btnComprarInternet = null;
		List<WebElement> btn = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.slds-truncate"));
		for (int i=0; i<btn.size(); i++) {
			if (btn.get(i).getText().toLowerCase().contains("comprar internet"))
				btnComprarInternet = btn.get(i);
		}
		btnComprarInternet.click();
		sleep(40000);
		driver.switchTo().defaultContent();
		if (driver.findElements(By.cssSelector(".x-layout-mini.x-layout-mini-east.x-layout-mini-custom-logo")).size() == 0)
			driver.findElement(By.cssSelector(".x-layout-mini.x-layout-mini-east.x-layout-mini-custom-logo")).click();
		WebElement planConTarjeta = null;
		driver.switchTo().frame(cambioFrame(driver, By.className("cpq-product-cart-order")));
		List<WebElement> plan = driver.findElements(By.className("cpq-product-name"));
		for (int i=0; i<plan.size(); i++) {
			if (plan.get(i).getText().toLowerCase().contains("plan con tarjeta repro"))
				planConTarjeta = plan.get(i);
		}
		try {
			planConTarjeta.click();
		} catch (org.openqa.selenium.WebDriverException e) {
			Assert.assertTrue(false);
		}
	}
	
	@Test (groups = "UAT")
	public void TXU0003_Gestion_De_Verificacion_De_Dos_Idiomas_En_El_Carrito() {
		SalesBase sb = new SalesBase(driver);
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", "22222001");
		carrito();
		sleep(35000);
		List <WebElement> prod = driver.findElements(By.className("slds-radio_button__label"));
		System.out.println(prod.get(0).getText());
		sleep(3000);
		List <WebElement> agreg = driver.findElements(By.cssSelector(".slds-button.slds-button_neutral.cpq-add-button"));
		System.out.println(agreg.get(0).getText());
		boolean conf = false; 
		for(WebElement x: prod) {
			if(x.getText().toLowerCase().equals("productos") ) {
				conf = true;
			}
			Assert.assertTrue(conf);
		}
		for(WebElement y : agreg) {
			if(y.getText().toLowerCase().equals("agregar")) {
				conf = true;
			}
			Assert.assertTrue(conf);
		}
	}
	
	@Test (groups = "UAT", dataProvider="NumerosAmigos")
	public void TXU0004_FF_No_Acepta_Numeros_De_Personal(String sDNI, String sLinea, String sNumeroVOZ, String sNumeroSMS) {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);		
		CustomerCare cCC = new CustomerCare(driver);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cCC.irAGestionEnCard("N\u00fameros Gratis");		
		sleep(5000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-col--padded.slds-size--1-of-2")));
		List<WebElement> wNumerosAmigos = driver.findElements(By.cssSelector(".slds-col--padded.slds-size--1-of-2"));
		Marketing mMarketing = new Marketing(driver);
		int iIndice = mMarketing.numerosAmigos(sNumeroVOZ, sNumeroSMS);
		switch (iIndice) {
			case 0:
				wNumerosAmigos.get(0).findElement(By.tagName("input")).sendKeys(sNumeroVOZ);
				break;
			case 1:
				wNumerosAmigos.get(1).findElement(By.tagName("input")).sendKeys(sNumeroSMS);
				break;
			default:
				Assert.assertTrue(false);
		}
		sleep(5000);
		WebElement wBox = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-tel.ng-scope.ng-dirty.ng-valid-mask.ng-valid.ng-valid-parse.ng-valid-required.ng-valid-minlength.ng-valid-maxlength")).findElement(By.className("error"));
		Assert.assertFalse(wBox.getText().equalsIgnoreCase("la linea no pertenece a Telecom, verifica el n\u00famero."));
	}
	
	@Test (groups = "UAT")
	public void TXU0005_Informacion_Internet_En_Card() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", "22222009");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);		
		driver.switchTo().frame(cambioFrame(driver,By.cssSelector(".console-card.active")));
		List<WebElement> wDetails = driver.findElements(By.className("detail"));
		WebElement wDetail = null;
		for (WebElement wAux : wDetails) {
			if (wAux.findElement(By.cssSelector(".slds-text-body_regular.detail-label")).getText().equalsIgnoreCase("Internet disponible")) {
				wDetail = wAux;
				break;
			}
		}
		List<WebElement> wMessages = wDetail.findElements(By.cssSelector(".slds-text-body_regular.value"));
		Assert.assertFalse(wMessages.get(1).getText().contains("Informaci\u00f3n no disponible"));
	}
	
	@Test (groups = "UAT")
	public void TXU0006_Informacion_Credito_En_Card() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", "22222009");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);		
		driver.switchTo().frame(cambioFrame(driver,By.cssSelector(".console-card.active")));
		List<WebElement> wDetails = driver.findElements(By.className("detail"));
		WebElement wDetail = null;
		for (WebElement wAux : wDetails) {
			if (wAux.findElement(By.cssSelector(".slds-text-body_regular.detail-label")).getText().equalsIgnoreCase("Cr\u00e9dito recarga")) {
				wDetail = wAux;
				break;
			}
		}
		List<WebElement> wMessages = wDetail.findElements(By.cssSelector(".slds-text-body_regular.value"));
		Assert.assertTrue(!wMessages.get(1).getText().isEmpty() && wMessages.get(1).getText().matches("([$][0]([,][0-9]{2}))|([$](?![0])[0-9]{0,3}([/.][0-9]{3})*([,][0-9]{2}))"));
	}
	
	@Test (groups = "SIT")
	public void TXS0002_Verificacion_De_Pestana_Detalles_En_Las_Cuentas() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", "41582129");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().defaultContent();
		for (WebElement x : driver.findElements(By.className("x-tab-left"))) {
			if (!x.getText().equalsIgnoreCase("Detalles"))
				Assert.assertTrue(false);
		}
	}
	
	@Test (groups = "SIT")
	public void TXS0003_Busqueda_De_Productos_En_El_Carrito() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", "41582129");
		carrito();
		sleep(15000);
		driver.findElement(By.cssSelector(".slds-input.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("galaxy");
		sleep(2500);
		for (WebElement x : driver.findElements(By.cssSelector(".categoryButton.cat-icon"))) {
			if (x.getText().equalsIgnoreCase("Planes"))
				x.click();
		}
		sleep(2500);
		WebElement list = driver.findElements(By.cssSelector(".slds-tile.cpq-product-item")).get(0);
		if (list.getText().toLowerCase().contains("galaxy s8"))
			Assert.assertTrue(false);
	}
	
	@Test (groups = "SIT")
	public void TXS0008_Informacion_Credito_En_Facturacion() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", "41582129");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);		
		try {
			cc.openleftpanel();
		}
		catch (Exception x) {
			//Always empty
		}
		cc.irAFacturacion();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector("div[class='header-right'] span[class='slds-text-heading_medium expired-date expired-pink']")));
		WebElement wMessage = driver.findElement(By.cssSelector("div[class='header-right'] span[class='slds-text-heading_medium expired-date expired-pink']"));
		Assert.assertTrue(!wMessage.getText().isEmpty() && wMessage.getText().matches("([$][0]([,][0-9]{2}))|([$](?![0])[0-9]{0,3}([/.][0-9]{3})*([,][0-9]{2}))"));
	}
	
	@Test (groups = "SIT")
	public void TXS0009_Busqueda_De_Cliente_Inexistente_Por_Linea() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		driver.findElement(By.id("PhoneNumber")).sendKeys("2944675251");
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(3000);
		WebElement msj = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		Assert.assertTrue(!msj.getText().toLowerCase().contains("no hay ning\u00fan cliente con este tipo y n\u00famero de documento. busc\u00e1 con otro dato o cre\u00e1 un nuevo cliente"));
	}
	
	@Test (groups = {"SIT","UAT"}, dataProvider="NumerosAmigosLetras")
	public void TXSU0001_CRM_Movil_REPRO_FF_Alta_Presencial_Ingreso_Letras(String sDNI, String sLinea) {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		BasePage cambioFrame=new BasePage();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);		
		CustomerCare cCC = new CustomerCare(driver);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cCC.irAGestionEnCard("N\u00fameros Gratis");		
		sleep(5000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-col--padded.slds-size--1-of-2")));
		List<WebElement> wNumerosAmigos = driver.findElements(By.cssSelector(".slds-col--padded.slds-size--1-of-2"));
		wNumerosAmigos.get(0).findElement(By.tagName("input")).sendKeys("A");
		wNumerosAmigos.get(1).findElement(By.tagName("input")).sendKeys("B");
		wNumerosAmigos = driver.findElements(By.cssSelector(".slds-col--padded.slds-size--1-of-2"));
		Assert.assertFalse(wNumerosAmigos.get(0).findElement(By.tagName("input")).getText().equals("A"));
		Assert.assertFalse(wNumerosAmigos.get(1).findElement(By.tagName("input")).getText().equals("B"));
	}
	
	@Test (groups = "SIT")
	public void TXS0012_CRM_Ubicacion_Mapa_Direccion_de_Envio() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		BasePage cambioFrame=new BasePage();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", "41582129");
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(15000);
		Marketing mM = new Marketing(driver);
		mM.closeActiveTab();
		driver.switchTo().frame(cambioFrame(driver, By.className("detailList")));
		WebElement wBody = driver.findElements(By.className("detailList")).get(1);
		WebElement wElement = mM.traerColumnaElement(wBody, 4, 1).get(4);
		Assert.assertTrue(wElement.getText().equals("Direcci\u00f3n de env\u00edo"));
		//wElement = driver.findElement(By.xpath("//*[@id=\"ep_Account_View_j_id4\"]/div[2]/div[5]/table/tbody/tr[5]/td[2]/table/tbody/tr[2]/td/div/div"));
		//Assert.assertTrue(wElement.getAttribute("class").equals("staticMap"));
		try {
			driver.findElement(By.xpath("((((//table[@class='detailList'])[2])/..//tr)[7])/td[2]//div[@class='staticMap']"));
			Assert.assertTrue(true);
		}
		catch (NoSuchElementException ex) {
			System.out.println("Elemento no encontrado");
			Assert.assertTrue(false);
		}
	}
	
	@Test (groups = "SIT")
	public void TXS_0004_CRM_Verificar_Pasos_Alta_de_Linea_Cliente_Existente_OfCom(){
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		sb.BuscarCuenta("DNI", "2222203");
		sleep(5000);
		carrito();
		sleep(20000);
		sb.elegirplan("Plan con Tarjeta");
		sleep(12000);
		driver.findElement(By.cssSelector(".slds-input.ng-valid.ng-not-empty.ng-dirty.ng-valid-parse.ng-touched")).clear();
		sleep(8000);
		driver.findElement(By.cssSelector(".slds-input.ng-valid.ng-dirty.ng-valid-parse.ng-touched.ng-empty")).sendKeys("Galaxy s8 - Negro");
		sleep(8000);
		List<WebElement> agregar = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.add-button")); 
		for(WebElement a : agregar){
			if(a.getText().equals("Agregar"))
				cc.obligarclick(a);
		}
		sleep(5000);	
		sb.continuar();
		sleep(10000);
		ArrayList<String> txt1 = new ArrayList<String>();
		ArrayList<String> txt2 = new ArrayList<String>();
		txt2.add("ASIGNACI\u00d3N DE L\u00cdNEA");
		txt2.add("VALIDACI\u00d3N DE IDENTIDAD");
		txt2.add("SIMULACI\u00d3N DE FACTURA");
		txt2.add("SELECCI\u00d3N DE MEDIO DE PAGO");
		txt2.add("RESUMEN DE LA ORDEN DE VENTA");
		txt2.add("ENV\u00edO FACTURA Y DATOS DE COBRO");
		txt2.add("INFORMACI\u00d3N");
		List<WebElement> pasos = driver.findElements(By.cssSelector(".list-group.vertical-steps.ng-scope"));
		System.out.println(pasos.size());
		for(WebElement e: pasos){
			txt1.add(e.getText());
			System.out.println(e.getText());
		}
		Assert.assertTrue(txt1.containsAll(txt2));
	}
		
	@Test (groups = "SIT")
	public void TXS_0005_CRM_Verificar_Pasos_Alta_de_Linea_Cliente_Existente_Agente(){
		LoguearAgente();
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		sb.BuscarCuenta("DNI", "2222203");
		sleep(5000);
		carrito();
		sleep(20000);
		sb.elegirplan("Plan con Tarjeta");
		sleep(12000);
		driver.findElement(By.cssSelector(".slds-input.ng-valid.ng-not-empty.ng-dirty.ng-valid-parse.ng-touched")).clear();
		sleep(8000);
		driver.findElement(By.cssSelector(".slds-input.ng-valid.ng-dirty.ng-valid-parse.ng-touched.ng-empty")).sendKeys("Galaxy s8 - Negro");
		sleep(8000);
		List<WebElement> agregar = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.add-button")); 
		for(WebElement a : agregar){
			if(a.getText().equals("Agregar"))
				cc.obligarclick(a);
		}
		sleep(5000);	
		sb.continuar();
		sleep(10000);
		ArrayList<String> txt1 = new ArrayList<String>();
		ArrayList<String> txt2 = new ArrayList<String>();
		txt2.add("ASIGNACI\u00d3N DE L\u00cdNEA");
		txt2.add("VALIDACI\u00d3N DE IDENTIDAD");
		txt2.add("INGRESO DE SERIAL");
		txt2.add("SIMULACI\u00d3N DE FACTURA");
		txt2.add("SELECCI\u00d3N DE MEDIO DE PAGO");
		txt2.add("RESUMEN DE LA ORDEN DE VENTA");
		txt2.add("ENV\u00edO FACTURA Y DATOS DE COBRO");
		txt2.add("INFORMACI\u00d3N");
		List<WebElement> pasos = driver.findElements(By.cssSelector(".list-group.vertical-steps.ng-scope"));
		System.out.println(pasos.size());
		for(WebElement e: pasos){
			txt1.add(e.getText());
			System.out.println(e.getText());
		}
		Assert.assertTrue(txt1.containsAll(txt2));
	}
	
	@Test (groups = "SIT")
	public void TXS_0006_CRM_Verificar_Pasos_Alta_de_Linea_Cliente_Nuevo_OfCom_StorePickUp(){
		CustomerCare cc = new CustomerCare(driver);
		SalesBase sb = new SalesBase(driver);
		Accounts accountPage = new Accounts(driver);
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		sleep(25000);
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SearchClientDocumentNumber")));
		sb.BtnCrearNuevoCliente();
		ContactSearch contact = new ContactSearch(driver);
		contact.sex("Masculino");
		contact.Llenar_Contacto("Nuevo", "OfCom", "10/10/1990");
		driver.findElement(By.id("EmailSelectableItems")).findElement(By.tagName("input")).sendKeys("a@a.com");
		driver.findElement(By.id("Contact_nextBtn")).click();
		sleep(20000);
		carrito();
		sleep(20000);
		sb.ResolverEntrega(driver, "Store Pick Up", "Ciudad Aut�noma de Buenos Aires","CIUD AUTON D BUENOS AIRES");
		sleep(8000);
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".slds-input.ng-pristine.ng-untouched.ng-valid.ng-empty")));
		sb.elegirplan("Plan con tarjeta");
		sleep(12000);
		driver.findElement(By.cssSelector(".slds-input.ng-valid.ng-not-empty.ng-dirty.ng-valid-parse.ng-touched")).clear();
		sleep(8000);
		driver.findElement(By.cssSelector(".slds-input.ng-valid.ng-dirty.ng-valid-parse.ng-touched.ng-empty")).sendKeys("Galaxy s8 - Negro");
		sleep(8000);
		List<WebElement> agregar = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.add-button")); 
		for(WebElement a : agregar){
			if(a.getText().equals("Agregar"))
				cc.obligarclick(a);
		}
		sleep(5000);	
		sb.continuar();
		sleep(22000);
		ArrayList<String> txt1 = new ArrayList<String>();
		ArrayList<String> txt2 = new ArrayList<String>();
		txt2.add("DATOS DE LA CUENTA");
		txt2.add("ASIGNACI\u00d3N DE L\u00cdNEA");
		txt2.add("SELECCI\u00d3N DE MODO DE ENTREGA");
		txt2.add("SIMULACI\u00d3N DE FACTURA");
		txt2.add("SELECCI\u00d3N MEDIO DE PAGO");
		txt2.add("INGRESO DE SERIAL");
		//txt2.add("VALIDACI\u00d3N DE IDENTIDAD");
		txt2.add("RESUMEN DE LA ORDEN DE VENTA");
		//txt2.add("ENV\u00edO FACTURA Y DATOS DE COBRO");
		txt2.add("INFORMACI\u00d3N");
		List<WebElement> pasos = driver.findElements(By.cssSelector(".list-group.vertical-steps.ng-scope"));
		System.out.println(pasos.size());
		for(WebElement e: pasos){
			txt1.add(e.getText());
			System.out.println(e.getText());
		}
		Assert.assertTrue(txt1.containsAll(txt2));
	}
	
	@Test (groups = "SIT")
	public void TXS_0007_CRM_Verificar_Pasos_Alta_de_Linea_Cliente_Nuevo_Agente_StorePickUp(){
		CustomerCare cc = new CustomerCare(driver);
		SalesBase sb = new SalesBase(driver);
		Accounts accountPage = new Accounts(driver);
		LoguearAgente();
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		sleep(25000);
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SearchClientDocumentNumber")));
		sb.BtnCrearNuevoCliente();
		ContactSearch contact = new ContactSearch(driver);
		contact.sex("Masculino");
		contact.Llenar_Contacto("Nuevo", "OfCom", "10/10/1990");
		driver.findElement(By.id("EmailSelectableItems")).findElement(By.tagName("input")).sendKeys("a@a.com");
		driver.findElement(By.id("Contact_nextBtn")).click();
		sleep(20000);
		carrito();
		sleep(20000);
		sb.ResolverEntrega(driver, "Store Pick Up", "Ciudad Aut�noma de Buenos Aires","CIUD AUTON D BUENOS AIRES");
		sleep(8000);
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".slds-input.ng-pristine.ng-untouched.ng-valid.ng-empty")));
		sb.elegirplan("Plan con tarjeta");
		sleep(12000);
		driver.findElement(By.cssSelector(".slds-input.ng-valid.ng-not-empty.ng-dirty.ng-valid-parse.ng-touched")).clear();
		sleep(8000);
		driver.findElement(By.cssSelector(".slds-input.ng-valid.ng-dirty.ng-valid-parse.ng-touched.ng-empty")).sendKeys("Galaxy s8 - Negro");
		sleep(8000);
		List<WebElement> agregar = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.add-button")); 
		for(WebElement a : agregar){
			if(a.getText().equals("Agregar"))
				cc.obligarclick(a);
		}
		sleep(5000);	
		sb.continuar();
		sleep(22000);
		ArrayList<String> txt1 = new ArrayList<String>();
		ArrayList<String> txt2 = new ArrayList<String>();
		txt2.add("DATOS DE LA CUENTA");
		txt2.add("ASIGNACI\u00d3N DE L\u00cdNEA");
		txt2.add("SELECCI\u00d3N DE MODO DE ENTREGA");
		txt2.add("SIMULACI\u00d3N DE FACTURA");
		txt2.add("SELECCI\u00d3N MEDIO DE PAGO");
		txt2.add("INGRESO DE SERIAL");
		//txt2.add("VALIDACI\u00d3N DE IDENTIDAD");
		txt2.add("RESUMEN DE LA ORDEN DE VENTA");
		//txt2.add("ENV\u00edO FACTURA Y DATOS DE COBRO");
		txt2.add("INFORMACI\u00d3N");
		List<WebElement> pasos = driver.findElements(By.cssSelector(".list-group.vertical-steps.ng-scope"));
		System.out.println(pasos.size());
		for(WebElement e: pasos){
			txt1.add(e.getText());
			System.out.println(e.getText());
		}
		Assert.assertTrue(txt1.containsAll(txt2));
	}
	
	@Test (groups = "SIT")
	public void TXS_0010_CRM_Verificar_Pasos_Alta_de_Linea_Cliente_Nuevo_OfCom_Presencial(){
		CustomerCare cc = new CustomerCare(driver);
		SalesBase sb = new SalesBase(driver);
		Accounts accountPage = new Accounts(driver);
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		sleep(25000);
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SearchClientDocumentNumber")));
		sb.BtnCrearNuevoCliente();
		ContactSearch contact = new ContactSearch(driver);
		contact.sex("Masculino");
		contact.Llenar_Contacto("Nuevo", "OfCom", "10/10/1990");
		driver.findElement(By.id("EmailSelectableItems")).findElement(By.tagName("input")).sendKeys("a@a.com");
		driver.findElement(By.id("Contact_nextBtn")).click();
		sleep(20000);
		carrito();
		sleep(20000);
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".slds-input.ng-pristine.ng-untouched.ng-valid.ng-empty")));
		sb.elegirplan("Plan con tarjeta");
		sleep(12000);
		driver.findElement(By.cssSelector(".slds-input.ng-valid.ng-not-empty.ng-dirty.ng-valid-parse.ng-touched")).clear();
		sleep(8000);
		driver.findElement(By.cssSelector(".slds-input.ng-valid.ng-dirty.ng-valid-parse.ng-touched.ng-empty")).sendKeys("Galaxy s8 - Negro");
		sleep(8000);
		List<WebElement> agregar = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.add-button")); 
		for(WebElement a : agregar){
			if(a.getText().equals("Agregar"))
				cc.obligarclick(a);
		}
		sleep(5000);	
		sb.continuar();
		sleep(22000);
		ArrayList<String> txt1 = new ArrayList<String>();
		ArrayList<String> txt2 = new ArrayList<String>();
		txt2.add("DATOS DE LA CUENTA");
		txt2.add("ASIGNACI\u00d3N DE L\u00cdNEA");
		txt2.add("SELECCI\u00d3N DE MODO DE ENTREGA");
		txt2.add("SIMULACI\u00d3N DE FACTURA");
		txt2.add("SELECCI\u00d3N MEDIO DE PAGO");
		txt2.add("INGRESO DE SERIAL");
		//txt2.add("VALIDACI\u00d3N DE IDENTIDAD");
		txt2.add("RESUMEN DE LA ORDEN DE VENTA");
		//txt2.add("ENV\u00edO FACTURA Y DATOS DE COBRO");
		txt2.add("INFORMACI\u00d3N");
		List<WebElement> pasos = driver.findElements(By.cssSelector(".list-group.vertical-steps.ng-scope"));
		System.out.println(pasos.size());
		for(WebElement e: pasos){
			txt1.add(e.getText());
			System.out.println(e.getText());
		}
		Assert.assertTrue(txt1.containsAll(txt2));
	}
	
	@Test (groups = "SIT")
	public void TXS_0011_CRM_Verificar_Pasos_Alta_de_Linea_Cliente_Nuevo_Agente_Presencial(){
		CustomerCare cc = new CustomerCare(driver);
		SalesBase sb = new SalesBase(driver);
		Accounts accountPage = new Accounts(driver);
		LoguearAgente();
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		sleep(25000);
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SearchClientDocumentNumber")));
		sb.BtnCrearNuevoCliente();
		ContactSearch contact = new ContactSearch(driver);
		contact.sex("Masculino");
		contact.Llenar_Contacto("Nuevo", "OfCom", "10/10/1990");
		driver.findElement(By.id("EmailSelectableItems")).findElement(By.tagName("input")).sendKeys("a@a.com");
		driver.findElement(By.id("Contact_nextBtn")).click();
		sleep(20000);
		carrito();
		sleep(20000);
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".slds-input.ng-pristine.ng-untouched.ng-valid.ng-empty")));
		sb.elegirplan("Plan con tarjeta");
		sleep(12000);
		driver.findElement(By.cssSelector(".slds-input.ng-valid.ng-not-empty.ng-dirty.ng-valid-parse.ng-touched")).clear();
		sleep(8000);
		driver.findElement(By.cssSelector(".slds-input.ng-valid.ng-dirty.ng-valid-parse.ng-touched.ng-empty")).sendKeys("Galaxy s8 - Negro");
		sleep(8000);
		List<WebElement> agregar = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.add-button")); 
		for(WebElement a : agregar){
			if(a.getText().equals("Agregar"))
				cc.obligarclick(a);
		}
		sleep(5000);	
		sb.continuar();
		sleep(22000);
		ArrayList<String> txt1 = new ArrayList<String>();
		ArrayList<String> txt2 = new ArrayList<String>();
		txt2.add("DATOS DE LA CUENTA");
		txt2.add("ASIGNACI\u00d3N DE L\u00cdNEA");
		txt2.add("SELECCI\u00d3N DE MODO DE ENTREGA");
		txt2.add("SIMULACI\u00d3N DE FACTURA");
		txt2.add("SELECCI\u00d3N MEDIO DE PAGO");
		txt2.add("INGRESO DE SERIAL");
		//txt2.add("VALIDACI\u00d3N DE IDENTIDAD");
		txt2.add("RESUMEN DE LA ORDEN DE VENTA");
		//txt2.add("ENV\u00edO FACTURA Y DATOS DE COBRO");
		txt2.add("INFORMACI\u00d3N");
		List<WebElement> pasos = driver.findElements(By.cssSelector(".list-group.vertical-steps.ng-scope"));
		System.out.println(pasos.size());
		for(WebElement e: pasos){
			txt1.add(e.getText());
			System.out.println(e.getText());
		}
		Assert.assertTrue(txt1.containsAll(txt2));
	}
	
	@Test (groups = "SIT")
	public void TXS0013_Comunidad_Ingreso_A_Las_Ordenes_Desde_Mis_Gestiones() {
		loginCommunity(driver);
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-size--1-of-1.slds-align-middle.slds-p-vertical--small.cursor")));
		driver.findElement(By.cssSelector(".slds-size--1-of-1.slds-align-middle.slds-p-vertical--small.cursor")).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-size--1-of-1.slds-p-vertical--medium.slds-m-horizontal--xxx-small.slds-border--bottom.vloc-action-grid-cell.gestionlink")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-size--1-of-1.slds-p-vertical--medium.slds-m-horizontal--xxx-small.slds-border--bottom.vloc-action-grid-cell.gestionlink")), "contains", "mis gestiones");
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		sleep(3000);
		WebElement fila = driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
		fila.findElements(By.tagName("td")).get(2).click();
		if (driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).isDisplayed())
			Assert.assertTrue(false);
		else
			Assert.assertTrue(true);
	}
	
	@Test (groups = "UAT")
	public void TXU0007_En_Casos_Cerrados_o_Realizados_el_Boton_Enviar_a_Aprobacion_No_Debe_Estar_Disponible() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		cc.menu_360_Ir_A("Casos");
		
		List<WebElement> sCaseNumber = driver.findElements(By.cssSelector("[class='x-grid3-cell-inner x-grid3-col-CASES_CASE_NUMBER']"));
		List<WebElement> sCaseStatus = driver.findElements(By.cssSelector("[class='x-grid3-cell-inner x-grid3-col-CASES_STATUS']"));
		for (int i = 0; i < sCaseNumber.size(); i++) {
			if (sCaseStatus.get(i).getText().equalsIgnoreCase("Closed") || sCaseStatus.get(i).getText().equalsIgnoreCase("Realizada exitosa")) {
				sCaseNumber.get(i).findElement(By.tagName("a")).click();
				break;
			}
		}
		sleep(15000);
		
		driver.switchTo().frame(cambioFrame(driver, By.id("topButtonRow")));
		List<WebElement> wTopMenu = driver.findElement(By.id("topButtonRow")).findElements(By.className("btn"));
		boolean bAssert = false;
		for (WebElement wAux : wTopMenu) {
			if (wAux.getAttribute("title").equalsIgnoreCase("Enviar para aprobaci\u00f3n")) {
				bAssert = true;
				break;
			}
		}
		cc.menu_360_Ir_A("Inicio");
		Assert.assertFalse(bAssert);
	}
	
	@Test (groups = {"SIT","UAT"})
	public void TXSU00003_En_La_Lista_de_Casos_Debe_Haber_un_Nombre_de_Contacto_Relacionado() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		cc.menu_360_Ir_A("Casos");
		
		/*boolean bAssert = false;
		int iCicle = 0;
		for (int i = 0; i < 2; i++) {
			System.out.println("Vuelta " + i);
			List<WebElement> sCaseContactName = driver.findElements(By.cssSelector("[class='x-grid3-cell-inner x-grid3-col-NAME']"));
			for (WebElement wAux: sCaseContactName) {
				
				System.out.println("wAux.getText: " + wAux.getText());
				
				if (wAux.getText().equalsIgnoreCase(" ")) {
					bAssert = true;
					break;
				}
			}
			if (bAssert = false) {
				List<WebElement> wTableHeader = driver.findElement(By.cssSelector("[class='x-grid3-hd-row']")).findElements(By.tagName("td"));
				wTableHeader.get(3).click();
				iCicle++;
			}
			else {
				break;
			}
		}*/
		
		sleep(5000);
		List<WebElement> wTableHeader = driver.findElement(By.cssSelector("[class='x-grid3-hd-row']")).findElements(By.tagName("td"));
		wTableHeader.get(3).click();
		boolean bAssert = false;
		for (int i = 0; i < 2; i++) {
			System.out.println("Cicle " + i);
			driver.findElement(By.className("x-grid3-row-table")).findElements(By.tagName("td"));
			List<WebElement> sCaseContactName = driver.findElements(By.cssSelector(".x-grid3-cell-inner.x-grid3-col-NAME"));
			if (sCaseContactName.get(i).getText().equals(" ")) {
				bAssert = true;
				break;
			}
		Assert.assertFalse(bAssert);
	}
		}
	@Test (groups = {"SIT","UAT"})
	public void TXSU00004_En_La_Lista_de_Casos_Debe_Haber_un_Tipo_Relacionado() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		cc.menu_360_Ir_A("Casos");		
		sleep(5000);
		List<WebElement> wTableHeader = driver.findElement(By.cssSelector("[class='x-grid3-hd-row']")).findElements(By.tagName("td"));
		wTableHeader.get(8).click();
		boolean bAssert = false;
		for (int i = 0; i < 2; i++) {
			System.out.println("Cicle " + i);
			driver.findElement(By.className("x-grid3-row-table")).findElements(By.tagName("td"));
			List<WebElement> sCaseContactName = driver.findElements(By.cssSelector(".x-grid3-cell-inner.x-grid3-col-CASES_TYPE"));
			if (sCaseContactName.get(i).getText().equals(" ")) {
				System.out.println(sCaseContactName.size());
				bAssert = true;
				break;
			}
		Assert.assertFalse(bAssert);
		}
	}
	
	@Test (groups = {"SIT","UAT"}, dataProvider="ventaPackInternacional30SMS")
	public void TXSU00011_Al_Cancelar_Una_Compra_De_Pack_Que_No_Quede_Dada_De_Alta(String sDNI, String sLinea, String sVentaPack, String sBanco, String sTarjeta, String sPromo, String sCuotas, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTipoDNI, String sDNITarjeta, String sTitular) throws InterruptedException, AWTException{
		SalesBase sale = new SalesBase(driver);
		CustomerCare cCC = new CustomerCare(driver);
		PagePerfilTelefonico pagePTelefo = new PagePerfilTelefonico(driver);
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		cc.menu_360_Ir_A("Inicio");
		irAGestionDeClientes();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sleep(8000);
		sale.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		pagePTelefo.buscarAssert();
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		pagePTelefo.comprarPack("comprar sms");
		sleep(5000);
		/*try {
			cCC.openrightpanel();
		}
		catch (Exception eE) {
			//Always empty
		}
		cCC.closerightpanel();*/
		pagePTelefo.PackLDI(sVentaPack);
		pagePTelefo.tipoDePago("en factura de venta");
		try {
			pagePTelefo.getSimulaciondeFactura().click();
		}
		catch (Exception eE) {
			pagePTelefo.getTipodepago().click();
		}
		sleep(12000);
		String sOrder = cc.obtenerOrden2(driver);
		List<WebElement> wMenu = driver.findElements(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope"));
		for (WebElement wAux : wMenu) {
			if (wAux.getText().equalsIgnoreCase("Cancelar")) {
				wAux.click();
				break;
			}
		}
		driver.findElement(By.id("alert-ok-button")).click();
		Marketing mM = new Marketing(driver);
		mM.closeActiveTab();
		sleep(5000);
		pagePTelefo.comprarPack("comprar sms");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.cpq-item-has-children")));
		//pagePTelefo.Pack("Packs Opcionales", "Packs LDI", "Pack internacional 30 SMS al Resto del Mundo");
		String servicio1 = "Packs Opcionales";
		String servicio2 = "Packs LDI";
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-button.cpq-item-has-children")).click();
		sleep(5000);
		List<WebElement> NomPack = driver.findElements(By.xpath("//*[@class='cpq-item-product-child-level-1 cpq-item-child-product-name-wrapper']"));
		for(WebElement a: NomPack) {
			//System.out.print(a.getText().toLowerCase());
			//System.out.println(" : "+servicio1.toLowerCase());
				if (a.getText().toLowerCase().contains(servicio1.toLowerCase())) {
					System.out.println(servicio1);
						a.findElement(By.tagName("button")).click();
							sleep(8000);
								break;
							}
						}
	
		List<WebElement> subPack = driver.findElements(By.xpath("//*[@class='cpq-item-product-child-level-2 cpq-item-child-product-name-wrapper']"));
		List<WebElement> Btnsubpack = driver.findElements(By.xpath("//*[@class='cpq-item-product-child-level-2 cpq-item-child-product-name-wrapper']//*[@class='slds-button slds-button_icon-small']"));			
		if (subPack.size() == Btnsubpack.size()) {
			for(WebElement b: subPack) {			
				//System.out.println("+++++"+b.getText().substring(b.getText().indexOf("\n")+1, b.getText().length())+"++++++");
				if (b.getText().substring(b.getText().indexOf("\n")+1, b.getText().length()).toLowerCase().contains(servicio2.toLowerCase())) {
					System.out.println(servicio2);
					b.findElement(By.tagName("button")).click();
					sleep(10000);
					break;
				}
			}
		}
		List<WebElement> wServicios = driver.findElements(By.cssSelector("[class='cpq-item-base-product'][class='cpq-item-base-product']"));
		for(WebElement wAux2 : wServicios) {
			try {
				if(wAux2.findElement(By.cssSelector("[class='cpq-item-no-children']")).getText().equalsIgnoreCase(sVentaPack)) {
					Assert.assertTrue(wAux2.findElement(By.cssSelector("[class='slds-button slds-button_neutral']")).isEnabled());
					break;
				}
			}
			catch (NoSuchElementException eNSEE) {
				if(wAux2.findElement(By.cssSelector("[class='cpq-product-name js-cpq-cart-product-hierarchy-path-01tc000000578L1AAI<01tc0000005fMVtAAM<01tc0000005fMY4AAM<01tc0000005vzaPAAQ']")).getText().equalsIgnoreCase(sVentaPack)) {
					Assert.assertTrue(wAux2.findElement(By.cssSelector("[class='slds-button slds-button_neutral']")).isEnabled());
					break;
				}
			}
		}
		Assert.assertTrue(mM.corroborarEstadCaso(sOrder, "Draft"));
		//Verify when the page works
	}
	@Test (groups = {"SIT", "UAT"})
	public void TXSU00005_En_La_Lista_de_Cuentas_Debe_Haber_un_Estado_o_Provincia_Relacionado() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		cc.menu_360_Ir_A("Cuentas");
		List<WebElement> tabla = driver.findElement(By.cssSelector("[class='x-grid3-hd-row']")).findElements(By.tagName("td"));
		tabla.get(9).click();
		boolean a = false;
		for(int i = 0; i < 1; i++) {
			System.out.println("Cicle " + i);
			driver.findElement(By.className("x-grid3-row-table")).findElements(By.tagName("td"));
			List<WebElement> estadoOProvincia = driver.findElements(By.cssSelector(".x-grid3-hd-inner.x-grid3-hd-ACCOUNT_ADDRESS1_STATE_CODE"));
			if (estadoOProvincia.get(i).getText().equals("")) {
				System.out.println(estadoOProvincia.size());
				a= true;
				break;
			}
		Assert.assertFalse(a);
		}
	}
	
	@Test (groups = {"SIT","UAT"})
	public void TXSU00006_En_La_Lista_de_Cuentas_Debe_Haber_un_Documento_o_CUIT_Relacionado() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		cc.menu_360_Ir_A("Cuentas");
		WebElement confirmacion = driver.findElement(By.cssSelector("[class='x-grid3-hd-row']"));
		if (confirmacion.getText().toLowerCase().contains("documentnumber")) {
			List<WebElement> tabla = driver.findElement(By.cssSelector("[class='x-grid3-hd-row']")).findElements(By.tagName("td"));
			tabla.get(9).click();
			boolean a = false;
			for(int i = 0; i < 1; i++) {
			System.out.println("Cicle " + i);
			driver.findElement(By.className("x-grid3-row-table")).findElements(By.tagName("td"));
			List<WebElement> estadoOProvincia = driver.findElements(By.cssSelector(".x-grid3-hd-inner.x-grid3-hd-00Nc0000001pWcd"));
			if (estadoOProvincia.get(i).getText().equals("")) {
				System.out.println(estadoOProvincia.size());
				a= true;
				break;
				}
			Assert.assertFalse(a);
			sleep(5000);
			List<WebElement> tabla1 = driver.findElement(By.cssSelector("[class='x-grid3-hd-row']")).findElements(By.tagName("td"));
			tabla1.get(10).click();
			boolean b = false;
			for(int j = 0; j < 1; j++) {
			System.out.println("Cicle " + j);
			driver.findElement(By.className("x-grid3-row-table")).findElements(By.tagName("td"));
			List<WebElement> estadoOProvincia1 = driver.findElements(By.cssSelector(".x-grid3-hd-inner.x-grid3-hd-00Nc000000351Kq"));
			if (estadoOProvincia1.get(i).getText().equals("")) {
				System.out.println(estadoOProvincia1.size());
				b= true;
				break;
					}
			Assert.assertFalse(b);
				}
			}
		}
		else {
			boolean b = false;
			WebElement tabla = driver.findElement(By.cssSelector("[class='x-grid3-hd-row']"));
			if(tabla.getText().toLowerCase().contains("documentnumber") || tabla.getText().toLowerCase().contains("cuit")){
				b = true;
			}
			Assert.assertFalse(b);
		}
	}

	@Test (groups = "UAT")
	public void TXU0009_Verificar_funcionamiento_del_boton_modificar_dentro_del_caso() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		cc.menu_360_Ir_A("Casos");
		List<WebElement> CaseNumber = driver.findElements(By.cssSelector("[class='x-grid3-cell-inner x-grid3-col-CASES_CASE_NUMBER']"));
		CaseNumber.get(0).findElement(By.tagName("a")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("topButtonRow")));
		List<WebElement> Menu = driver.findElement(By.id("topButtonRow")).findElements(By.className("btn"));
		for (WebElement wAux : Menu) {
			sleep(8000);
			if (wAux.getAttribute("title").equalsIgnoreCase("Modificar")) {
				Assert.assertTrue(wAux.isDisplayed());
					sleep(8000);
						wAux.click();
						break;
				}
//		Alert alert = driver.switchTo().alert();
//		alert.accept();
//		driver.switchTo().defaultContent();
//		List<WebElement> error = driver.findElement(By.xpath("/html/body/table")).findElements(By.tagName("span"));
//		for(WebElement x : error) {
//			if(x.getText().toLowerCase().contains("no se ha podido enviar para la aprobaci\u00f3n")) {
//			System.out.println(x.getText());	
//			}
//			
//	}
		}
		driver.switchTo().defaultContent();
		System.out.println("No permite Modificar");
	}
	
	@Test (groups = "UAT")
	public void TXU0008_Verificar_funcionamiento_del_boton_modificar_dentro_de_la_orden() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		sleep(8000);
		cc.menu_360_Ir_A("Inicio");
		sleep(18000);
		driver.switchTo().defaultContent();
		List<WebElement> CaseNumber = driver.findElements(By.cssSelector("[class='slds-truncate slds-text-align--center']"));
		CaseNumber.get(1).click();
		//sleep(8000);
		//driver.switchTo().frame(cambioFrame(driver, By.xpath("//*[@id=\"tab-default-1\"]/div/ng-include/div/div/div[3]/table/tbody[1]/tr")));
		
	}
	
	@Test (groups = "UAT")
	public void TXU0010_Validacion_de_fecha_de_modificacion_en_detalles_del_caso() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		cc.menu_360_Ir_A("Casos");
		List<WebElement> CaseNumber = driver.findElements(By.cssSelector("[class='x-grid3-cell-inner x-grid3-col-CASES_CASE_NUMBER']"));
		CaseNumber.get(0).findElement(By.tagName("a")).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.name("close")));
		driver.findElement(By.name("close")).click();
		sleep(5000);
		selectByText(driver.findElement(By.name("cas7")), "Anulada");
		driver.findElement(By.name("save")).click();
		WebElement ultModificacion = null;
		for (WebElement x : driver.findElements(By.className("pbSubsection"))) {
			if (x.getText().toLowerCase().contains("owned by me"))
				ultModificacion = x;
		}
		ultModificacion = ultModificacion.findElement(By.tagName("tbody"));
		for (WebElement x : ultModificacion.findElements(By.tagName("tr"))) {
			if (x.getText().toLowerCase().contains("\u00faltima modificaci\u00f3n por"))
				ultModificacion = x;
		}
		String lastUpdate = ultModificacion.getText();
		lastUpdate = lastUpdate.substring(lastUpdate.indexOf(",")+2);
		WebElement fechaYHora = null;
		for (WebElement x : driver.findElements(By.className("pbSubsection"))) {
			if (x.getText().toLowerCase().contains("owned by me"))
				fechaYHora = x;
		}
		fechaYHora = fechaYHora.findElement(By.tagName("tbody"));
		for (WebElement x : fechaYHora.findElements(By.tagName("tr"))) {
			if (x.getText().toLowerCase().contains("fecha/hora de cierre"))
				fechaYHora = x;
		}
		String closeCase = fechaYHora.getText();
		closeCase = closeCase.substring(closeCase.lastIndexOf("e")+2);
		DateFormat format = new SimpleDateFormat("dd/mm/yyyy hh:mm");
		Date fechaUltimaModificacion  = format.parse(lastUpdate, new ParsePosition(0));
		Date fechaDeCierre = format.parse(closeCase, new ParsePosition(0));
		if (!(fechaDeCierre.before(fechaUltimaModificacion)) || !(fechaDeCierre == fechaUltimaModificacion))
			Assert.assertTrue(false);
	}
	
	@Test (groups = {"SIT", "UAT"})
	public void TXSU00012_Anulacion_De_Caso_Previamente_Anulado() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		cc.menu_360_Ir_A("Casos");
		List<WebElement> CaseNumber = driver.findElements(By.cssSelector("[class='x-grid3-cell-inner x-grid3-col-CASES_CASE_NUMBER']"));
		CaseNumber.get(0).findElement(By.tagName("a")).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.name("close")));
		driver.findElement(By.name("close")).click();
		sleep(5000);
		selectByText(driver.findElement(By.name("cas7")), "Anulada");
		driver.findElement(By.name("save")).click();
		sleep(5000);
		driver.findElement(By.name("close")).click();
		sleep(5000);
		for (WebElement x : driver.findElement(By.name("cas7")).findElements(By.tagName("option"))) {
			if (x.getText().toLowerCase().equals("anulada")) {
				driver.findElement(By.name("cancel")).click();
				Assert.assertTrue(false);
			} else
				driver.findElement(By.name("cancel")).click();
		}
	}
	
	@Test (groups = {"SIT", "UAT"})
	public void TXSU00007_Verificar_Asunto_En_Detalles_Del_Caso_Al_Realizar_Una_Suspension() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		if (driver.getCurrentUrl().contains("sit"))
			cc.buscarCaso("00064566");
		else
			cc.buscarCaso("00003522");
		driver.switchTo().frame(cambioFrame(driver, By.name("close")));
		WebElement asunto = null;
		for (WebElement x : driver.findElements(By.className("pbSubsection"))) {
			if (x.getText().toLowerCase().contains("owned by me"))
				asunto = x;
		}
		asunto = asunto.findElement(By.tagName("tbody"));
		for (WebElement x : asunto.findElements(By.tagName("tr"))) {
			if (x.getText().toLowerCase().contains("asunto"))
				asunto = x;
		}
		Assert.assertTrue(asunto.getText().contains("Suspensi\u00f3n de Linea + Equipo"));
	}
	
	@Test (groups = {"UAT","SIT"}, dataProvider = "CuentaModificacionDeDatos") 
	public void TXSU00008_Validar_que_el_DNI_solo_se_pueda_modificar_cada_30_dias (String sDNI, String sLinea) {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		cc.menu_360_Ir_A("Inicio");
		irAGestionDeClientes();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		driver.findElement(By.cssSelector(".slds-form-element__label--toggleText.ng-binding")).click();
		sleep(3000);
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		cc.openleftpanel();
		List <WebElement> actualizar = driver.findElements(By.className("profile-edit"));
		actualizar.get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DocumentNumber")));
		boolean a = false;
		boolean b = false;
		for(WebElement x : driver.findElements(By.id("MessagingDocumentNumberModificationNotAllowed"))) {
			if(x.getText().toLowerCase().contains("aclaraci�n: se realiz\u00f3 un cambio de dni en el \u00faltimo mes. no se permite una nueva modificaci\u00f3n.")) {
				a = true;
				System.out.println("No se puede realizar una modificacion de DNI");
			}
		}
		Assert.assertTrue(a);
		driver.findElement(By.id("ClientInformation_nextBtn")).click();
		sleep(10000);
		List <WebElement> element = driver.findElements(By.className("ta-care-omniscript-done"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("se realizaron correctamente las modificaciones")) {
				b = true;
			}
		}
		Assert.assertFalse(b);
	}
	
	@Test (groups = {"SIT","UAT"})
	public void TXSU00013_Venta_de_Servicio_a_un_Nuevo_Contacto_Agente(){
		SalesBase sb = new SalesBase(driver);
		Accounts accountPage = new Accounts(driver);
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		sleep(25000);
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SearchClientDocumentNumber")));
		sb.BtnCrearNuevoCliente();
		ContactSearch contact = new ContactSearch(driver);
		contact.sex("Masculino");
		contact.Llenar_Contacto("NoVenta", "Pack", "10/10/1990");
		driver.findElement(By.id("EmailSelectableItems")).findElement(By.tagName("input")).sendKeys("a@a.com");
		driver.findElement(By.id("Contact_nextBtn")).click();
		sleep(20000);
		carrito();
		sleep(20000);
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".slds-input.ng-pristine.ng-untouched.ng-valid.ng-empty")));
		driver.findElement(By.cssSelector(".slds-input.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("Pack 3 dias de SMS ilimitados");
		sleep(10000);
		driver.findElements(By.cssSelector(".slds-button.slds-button_neutral.add-button")).get(1).click();
		sleep(10000);
		boolean a = true;
		List<WebElement> cont = driver.findElements(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand"));
			for(WebElement c : cont){
				System.out.println(c.getText());
				if(c.getText().equals("Continuar")){
					a = false;
				}
			}
		Assert.assertTrue(a);
	}	
	
	@Test (groups = {"SIT","UAT"}, dataProvider="ventaPackInternacional30SMS")
	public void TXSU00009_Validar_pantalla_al_finalizar_un_proceso_de_compra_de_pack(String sDNI, String sLinea, String sVentaPack, String sBanco, String sTarjeta, String sPromo, String sCuotas, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTipoDNI, String sDNITarjeta, String sTitular) throws InterruptedException, AWTException{
		BasePage cambioFrameByID=new BasePage();
		PagePerfilTelefonico pagePTelefo = new PagePerfilTelefonico(driver);
		
		//Delete when UAT loguin works
		driver = setConexion.setupEze();
		driver.get("https://telecomcrm--uat.cs53.my.salesforce.com");
		sleep(2000);
		driver.findElement(By.id("idp_section_buttons")).click();
		sleep(2000);
		driver.findElement(By.name("Ecom_User_ID")).sendKeys("uat569076");
 		driver.findElement(By.name("Ecom_Password")).sendKeys("Testa10k");
 		driver.findElement(By.id("loginButton2")).click();
 		sleep(5000);
		driver.get("https://telecomcrm--uat.cs53.my.salesforce.com");
		//End
		
		irAConsolaFAN();
		sleep(20000);
		cc.cerrarTodasLasPestanas();;
		//cc.menu_360_Ir_A("Inicio");
		irAGestionDeClientes();
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));	
		sleep(8000);
		sb.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		pagePTelefo.buscarAssert();
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		pagePTelefo.comprarPack("comprar sms");
		sleep(5000);
		cc.closeleftpanel();
		try {
			pagePTelefo.PackLDI(sVentaPack);
		}
		catch (Exception eE) {
			driver.navigate().refresh();
			sleep(10000);
			m.closeTabByName(driver, "Comprar SMS");
			cc.seleccionarCardPornumeroLinea(sLinea, driver);
			pagePTelefo.comprarPack("comprar sms");
			pagePTelefo.PackLDI(sVentaPack);
		}
		pagePTelefo.tipoDePago("en factura de venta");
		//pagePTelefo.getSimulaciondeFactura().click();
		pagePTelefo.getTipodepago();
		sleep(12000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		sleep(8000);
		pagePTelefo.getMediodePago().click();
		sleep(45000);
		pagePTelefo.getOrdenSeRealizoConExito().click();
		sleep(10000);
		//AssertFalse on the "Detalle" title
	}
	
	@Test (groups = {"UAT"}, dataProvider="NumerosAmigos")
	public void TXU0011_UAT_FF_No_Acepta_Numeros_De_Personal(String sDNI, String sLinea, String sNumeroVOZ, String sNumeroSMS) {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		cc.menu_360_Ir_A("Inicio");
		irAGestionDeClientes();
		sleep(5000);
		BasePage cambioFrame=new BasePage();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);
		
		CustomerCare cCC = new CustomerCare(driver);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cCC.irAGestionEnCard("N\u00fameros Gratis");
		
		sleep(5000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-col--padded.slds-size--1-of-2")));
		List<WebElement> wNumerosAmigos = driver.findElements(By.cssSelector(".slds-col--padded.slds-size--1-of-2"));
		Marketing mMarketing = new Marketing(driver);
		int iIndice = mMarketing.numerosAmigos(sNumeroVOZ, sNumeroSMS);
		switch (iIndice) {
			case 0:
				wNumerosAmigos.get(0).findElement(By.tagName("input")).sendKeys(sNumeroVOZ);
				break;
			case 1:
				wNumerosAmigos.get(1).findElement(By.tagName("input")).sendKeys(sNumeroSMS);
				break;
			default:
				Assert.assertTrue(false);
		}
		sleep(5000);
		WebElement wBox = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-tel.ng-scope.ng-dirty.ng-valid-mask.ng-valid.ng-valid-parse.ng-valid-required.ng-valid-minlength.ng-valid-maxlength")).findElement(By.className("error"));
		Assert.assertFalse(wBox.getText().equalsIgnoreCase("la linea no pertenece a Telecom, verifica el n\u00famero."));
	}
	
	@Test (groups = {"SIT","UAT"}, dataProvider = "DatosSalesNominacionExistenteOfCom")
	public void TXSU00010_Intentar_subir_un_exe_a_la_validacion_del_contacto_al_nominaro(String sLinea, String sDni) {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		cc.menu_360_Ir_A("Inicio");
		irAGestionDeClientes();
		boolean nominacion = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-button.slds-button--icon.slds-m-right--x-small.ng-scope")).click();
		sleep(2000);
		WebElement botonNominar = null;
		for (WebElement x : driver.findElements(By.cssSelector(".slds-hint-parent.ng-scope"))) {
			if (x.getText().toLowerCase().contains("plan con tarjeta"))
				botonNominar = x;
		}
		for (WebElement x : botonNominar.findElements(By.tagName("td"))) {
			if (x.getAttribute("data-label").equals("actions"))
				botonNominar = x;
		}
		botonNominar.findElement(By.tagName("a")).click();
		sleep(5000);
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact2("DNI", sDni, "Masculino");
		driver.findElement(By.id("Contact_nextBtn")).click();
		sleep(10000);
		contact.tipoValidacion("documento");
		sleep(5000);
		driver.findElement(By.id("FileDocumentImage")).sendKeys("C:\\Users\\xappiens\\flowerengel\\TelecomProyectoFAN\\chromedriver.exe");
		sleep(5000);
		for(WebElement x : driver.findElements(By.className("slds-form-element__control"))) {
			if(x.getText().toLowerCase().equals("la imagen posee un formato no v\u00e1lido.")) {
				nominacion = true;
				
			}
		}
		Assert.assertTrue(nominacion);
	}
	
	@Test (groups = "UAT")
	public void TXU0011_Crear_Sugerencias_Estado_Del_Caso() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", "22223002");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		cc.irAGestion("sugerencia");
		driver.switchTo().frame(cambioFrame(driver, By.id("Category")));
		selectByText(driver.findElement(By.id("Category")), "Sugerencias");
		selectByText(driver.findElement(By.id("Subcategory")), "Calidad en la Atenci\u00f3n");
		driver.findElement(By.id("ManagementType_nextBtn")).click();
		sleep(5000);
		WebElement msj = null, estado = null;
		for (WebElement x : driver.findElements(By.cssSelector(".vlc-slds-inline-control__label.ng-binding"))) {
			if (x.getText().toLowerCase().contains("el n\u00famero de confirmaci\u00f3n es"))
				msj = x;
		}
		String caso = msj.getText();
		caso = caso.substring(caso.indexOf("0"), caso.length());
		cc.buscarCaso(caso);
		driver.switchTo().frame(cambioFrame(driver, By.name("close")));
		for (WebElement x : driver.findElements(By.className("pbSubsection"))) {
			if (x.getText().toLowerCase().contains("propietario del caso"))
				estado = x;
		}
		estado = estado.findElement(By.tagName("tbody"));
		for (WebElement x : estado.findElements(By.tagName("tr"))) {
			if (x.getText().toLowerCase().contains("estado"))
				estado = x;
		}
		if (estado.findElements(By.tagName("td")).get(3).getText().equalsIgnoreCase("Closed"))
			Assert.assertTrue(false);
	}
	
	@Test (groups = "SIT")
	public void TXS0014_Gestion_Sugerencias() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", "22222035");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		cc.buscarGestion("sugerencia");
		sleep(3000);
		if (!driver.findElement(By.cssSelector(".slds-button.slds-button--neutral.slds-truncate")).isDisplayed())
			Assert.assertTrue(false);
	}
	
	@Test (groups = {"SIT","UAT"})
	public void TXSU00018_Mensaje_Guardar_Para_Despues_Recarga_De_Credito() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		if (driver.getCurrentUrl().contains("sit"))
			sb.BuscarCuenta("DNI", "15907314");
		else
			sb.BuscarCuenta("DNI", "22223001");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		cc.irAGestionEnCard("Recarga de cr\u00e9dito");
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys("123");
		driver.findElement(By.id("AmountSelectionStep_nextBtn")).click();
		sleep(10000);
		if (driver.getCurrentUrl().contains("sit"))
			try {
				driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")).get(1).click();
				sleep(5000);
			} catch (Exception e) {}
		buscarYClick(driver.findElements(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope")), "contains", "guardar para despu\u00e9s");
		sleep(3000);
		driver.findElement(By.id("alert-ok-button")).click();
		sleep(5000);
		if (driver.findElement(By.className("vlc-slds-figure")).getText().equalsIgnoreCase("Your OmniScript is saved for later"))
			Assert.assertTrue(false);
	}
	
	@Test (groups = "UAT")
	public void TXU0012_Existencia_de_Renovacion_De_Cuota_En_Flyout() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", "22223001");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		WebElement lista = driver.findElement(By.cssSelector(".slds-small-size--3-of-12.slds-medium-size--3-of-12.slds-large-size--3-of-12.flyout-actions"));
		for (WebElement x : lista.findElements(By.tagName("li"))) {
			if (!x.getText().toLowerCase().contains("renovacion de cuota"))
				Assert.assertTrue(false);
		}
	}
	
	@Test (groups = {"SIT","UAT"})
	public void TXSU00019_Funcionamiento_De_Buscador_De_Base_De_Conocimiento() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		if (driver.getCurrentUrl().contains("sit"))
			sb.BuscarCuenta("DNI", "15907314");
		else
			sb.BuscarCuenta("DNI", "22223001");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		cc.irAGestion("inconvenientes");
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		driver.findElement(By.id("vlcKnowledgeKeyword")).clear();
		driver.findElement(By.id("vlcKnowledgeKeyword")).sendKeys("a");
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10); 
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".slds-icon.slds-icon--small.slds-input__icon.slds-icon-text-default")));
			element.click();
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(false);
		}		
	}
	
	@Test (groups = {"SIT","UAT"})
	public void TXSU00014_Validar_existencia_del_boton_gestion_de_clientes_en_perfil_oficina_comercial() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		cc.menu_360_Ir_A("Inicio");
		sleep(45000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		boolean a = false;
		boolean enc = false;
		int index = 0;
		for (WebElement frame : frames) {
			try {
				driver.switchTo().frame(frame);
				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).getText();
				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).isDisplayed();
				driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
				enc = true;
				break;
			} catch (NoSuchElementException e) {
				index++;
				driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
			}
		}
		if (enc == false)
			index = -1;
		try {
			driver.switchTo().frame(frames.get(index));
		} catch (ArrayIndexOutOfBoundsException e) {}
		WebElement conf = driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container"));
		if(conf.getText().toLowerCase().contains("gesti\u00f3n de clientes")) {
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"SIT","UAT"})
	public void TXSU00015_Validar_existencia_del_boton_gestion_de_clientes_en_perfil_Agente() {
		LoguearAgente();
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		cc.menu_360_Ir_A("Inicio");
		sleep(45000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		boolean a = false;
		boolean enc = false;
		int index = 0;
		for (WebElement frame : frames) {
			try {
				driver.switchTo().frame(frame);
				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).getText();
				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).isDisplayed();
				driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
				enc = true;
				break;
			} catch (NoSuchElementException e) {
				index++;
				driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
			}
		}
		if (enc == false)
			index = -1;
		try {
			driver.switchTo().frame(frames.get(index));
		} catch (ArrayIndexOutOfBoundsException e) {}
		WebElement conf = driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container"));
		if(conf.getText().toLowerCase().contains("gesti\u00f3n de clientes")) {
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"SIT","UAT"})
	public void TXSU00016_Validar_existencia_del_boton_gestion_de_clientes_en_perfil_Telefonico() {
		LoguearTelefonico();
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		cc.menu_360_Ir_A("Inicio");
		sleep(45000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		boolean a = false;
		boolean enc = false;
		int index = 0;
		for (WebElement frame : frames) {
			try {
				driver.switchTo().frame(frame);
				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).getText();
				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).isDisplayed();
				driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
				enc = true;
				break;
			} catch (NoSuchElementException e) {
				index++;
				driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
			}
		}
		if (enc == false)
			index = -1;
		try {
			driver.switchTo().frame(frames.get(index));
		} catch (ArrayIndexOutOfBoundsException e) {}
		WebElement conf = driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container"));
		if(conf.getText().toLowerCase().contains("gesti\u00f3n de clientes")) {
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"SIT","UAT"})
	public void TXSU00017_Validar_existencia_del_boton_gestion_de_clientes_en_perfil_Back_office() {
		LoguearBackOffice();
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		cc.menu_360_Ir_A("Inicio");
		sleep(45000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		boolean a = false;
		boolean enc = false;
		int index = 0;
		for (WebElement frame : frames) {
			try {
				driver.switchTo().frame(frame);
				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).getText();
				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).isDisplayed();
				driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
				enc = true;
				break;
			} catch (NoSuchElementException e) {
				index++;
				driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
			}
		}
		if (enc == false)
			index = -1;
		try {
			driver.switchTo().frame(frames.get(index));
		} catch (ArrayIndexOutOfBoundsException e) {}
		WebElement conf = driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container"));
		if(conf.getText().toLowerCase().contains("gesti\u00f3n de clientes")) {
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"SIT","UAT"})
	public void TXSU00022_Recarga_De_Credito_Ingresar_Valores_Menores_A_5() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		if (driver.getCurrentUrl().contains("sit"))
			sb.BuscarCuenta("DNI", "15907314");
		else
			sb.BuscarCuenta("DNI", "22223001");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		cc.irAGestionEnCard("Recarga de cr\u00e9dito");
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys("3");
		driver.findElement(By.id("AmountSelectionStep_nextBtn")).click();
		sleep(3000);
		driver.findElement(By.id("alert-ok-button")).click();
		Assert.assertTrue(driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText().contains("El monto debe estar comprendido entre 5 y 500"));
	}
	
	@Test (groups = {"SIT","UAT"})
	public void TXSU00023_Recarga_De_Credito_Ingresar_Valores_Mayores_A_500() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		if (driver.getCurrentUrl().contains("sit"))
			sb.BuscarCuenta("DNI", "15907314");
		else
			sb.BuscarCuenta("DNI", "22223001");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		cc.irAGestionEnCard("Recarga de cr\u00e9dito");
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys("501");
		driver.findElement(By.id("AmountSelectionStep_nextBtn")).click();
		sleep(3000);
		driver.findElement(By.id("alert-ok-button")).click();
		Assert.assertTrue(driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText().contains("El monto debe estar comprendido entre 5 y 500"));
	}
	
	@Test (groups = {"SIT","UAT"})
	public void TXSU00024_Recarga_De_Credito_Ingresar_Letras() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		if (driver.getCurrentUrl().contains("sit"))
			sb.BuscarCuenta("DNI", "15907314");
		else
			sb.BuscarCuenta("DNI", "22223001");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		cc.irAGestionEnCard("Recarga de cr\u00e9dito");
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys("a");
		if ((!driver.findElement(By.id("RefillAmount")).getAttribute("value").equals("a")) || driver.findElement(By.id("RefillAmount")).getAttribute("value").equals("0"))
			Assert.assertTrue(false);
	}
	
	@Test (groups = {"SIT","UAT"})
	public void TXSU00025_Recarga_De_Credito_Ingresar_Valores_De_Recarga_Con_Decimales() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		if (driver.getCurrentUrl().contains("sit"))
			sb.BuscarCuenta("DNI", "15907314");
		else
			sb.BuscarCuenta("DNI", "22223001");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		cc.irAGestionEnCard("Recarga de cr\u00e9dito");
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys("17.50");
		if ((!driver.findElement(By.id("RefillAmount")).getAttribute("value").equals("17.50")) || (!driver.findElement(By.id("RefillAmount")).getAttribute("value").equals("17,50")))
			Assert.assertTrue(false);
	}
	
	@Test (groups = {"SIT","UAT"})
	public void TXSU00026_Modificacion_de_datos_No_Permite_Actualizar_datos_campo_DNI_CUIT_Telefonico() {
		LoguearTelefonico();
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		cc.menu_360_Ir_A("Inicio");
		irAGestionDeClientes();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		if (driver.getCurrentUrl().contains("sit"))
			sb.BuscarCuenta("DNI", "10777541");
		else
			sb.BuscarCuenta("DNI", "22222070");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		driver.findElements(By.className("profile-edit")).get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DocumentNumber")));
		Assert.assertTrue(driver.findElement(By.id("DocumentType")).getAttribute("disabled").equals("true"));
	}
	
	@Test (groups = {"SIT","UAT"})
	public void TXSU00026_Modificacion_de_datos_No_Permite_Actualizar_datos_campo_DNI_CUIT_Agente() {
		LoguearAgente();
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		cc.menu_360_Ir_A("Inicio");
		irAGestionDeClientes();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		if (driver.getCurrentUrl().contains("sit"))
			sb.BuscarCuenta("DNI", "10777541");
		else
			sb.BuscarCuenta("DNI", "22222070");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		driver.findElements(By.className("profile-edit")).get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DocumentNumber")));
		Assert.assertTrue(driver.findElement(By.id("DocumentType")).getAttribute("disabled").equals("true"));
		}
	
	@Test (groups = {"SIT","UAT"})
	public void TXSU00027_Modificacion_de_datos_No_Permitir_actualizar_fecha_de_nacimiento_al_colocar_un_a�o_mayor_a_120_ofcom() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		cc.menu_360_Ir_A("Inicio");
		irAGestionDeClientes();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		if (driver.getCurrentUrl().contains("sit"))
			sb.BuscarCuenta("DNI", "10777541");
		else
			sb.BuscarCuenta("DNI", "22222070");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		driver.findElements(By.className("profile-edit")).get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DocumentNumber")));
		driver.findElement(By.id("Birthdate")).clear();
		driver.findElement(By.id("Birthdate")).sendKeys("08/08/1898");
		sleep(5000);
		driver.findElement(By.id("ClientInformation_nextBtn")).click();
		sleep(8000);
		Assert.assertTrue(!(driver.findElement(By.className("ta-care-omniscript-done")).getText().contains("Las modificaciones se realizaron con \u00e9xito")));
	}
	
	@Test (groups = {"SIT","UAT"})
	public void TXSU00028_Modificacion_de_datos_No_Permitir_actualizar_fecha_de_nacimiento_al_colocar_un_a�o_menor_a_16_Telefonico() {
		LoguearTelefonico();
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		cc.menu_360_Ir_A("Inicio");
		irAGestionDeClientes();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		if (driver.getCurrentUrl().contains("sit"))
			sb.BuscarCuenta("DNI", "10777541");
		else
			sb.BuscarCuenta("DNI", "22222070");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		driver.findElements(By.className("profile-edit")).get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DocumentNumber")));
		driver.findElement(By.id("Birthdate")).clear();
		driver.findElement(By.id("Birthdate")).sendKeys("08/08/2016");
		sleep(5000);
		driver.findElement(By.id("ClientInformation_nextBtn")).click();
		sleep(8000);
		Assert.assertTrue(!(driver.findElement(By.className("ta-care-omniscript-done")).getText().contains("Las modificaciones se realizaron con \u00e9xito")));
	}
	
	@Test (groups = {"SIT","UAT"})
	public void TXSU00029_Renovacion_de_cuota_Ofcom_Reseteo_Internet_por_Dia_Lim�trofe_Descuento_de_saldo_sin_Credito(){
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		if (driver.getCurrentUrl().contains("sit"))
			sb.BuscarCuenta("DNI", "95905674");
		else
			sb.BuscarCuenta("DNI", "33332002");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		if (driver.getCurrentUrl().contains("sit"))
			cc.seleccionarCardPornumeroLinea("2932441219", driver); 
		else
			cc.seleccionarCardPornumeroLinea("3572409631", driver);
		sleep(3000);
		cc.irAGestionEnCard("Renovacion de Datos");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("combosMegas")));
		List<WebElement> elementos = driver.findElement(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement UnE:elementos) {
			if(UnE.findElement(By.tagName("td")).getText().contains("Reseteo Internet por D\u00eda Lim\u00edtrofe")) {
				UnE.findElement(By.className("slds-checkbox")).click();
			}
		}
		cc.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));
		sleep(3000);
		List<WebElement> pago = driver.findElement(By.id("PaymentTypeRadio|0")).findElements(By.cssSelector(".slds-radio.ng-scope"));
		for (WebElement UnP : pago) {
			System.out.println(UnP.getText());
			if (UnP.getText().toLowerCase().contains("descuento de saldo")){
				UnP.click();
				break;
			}
		}
		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		sleep(12000);
		WebElement ok = driver.findElement(By.id("alert-container")).findElements(By.tagName("div")).get(2);
		System.out.println(ok.getText());
		ok.click();
		sleep(4000);
		try {
			Assert.assertTrue(driver.findElement(By.id("PaymentTypeRadio|0")).findElements(By.tagName("div")).get(1).findElement(By.tagName("div")).findElement(By.tagName("small")).isDisplayed());
		}
		catch (Exception e) {
		}
	}
		
	@Test (groups = {"SIT","UAT"}) 
	public void TXSU00031_Renovacion_de_cuota_Agente_Reseteo_200_MB_por_Dia_Descuento_de_saldo_sin_Credito(){
		LoguearAgente();
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		cc.menu_360_Ir_A("Inicio");
		irAGestionDeClientes();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		if (driver.getCurrentUrl().contains("sit"))
			sb.BuscarCuenta("DNI", "95905674");
		else
			sb.BuscarCuenta("DNI", "33332002");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		if (driver.getCurrentUrl().contains("sit"))
			cc.seleccionarCardPornumeroLinea("2932441219", driver);
		else
			cc.seleccionarCardPornumeroLinea("3572409631", driver);
		sleep(3000);
		cc.irAGestionEnCard("Renovacion de Datos");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("combosMegas")));
		List<WebElement> elementos = driver.findElement(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement UnE:elementos) {
			System.out.println(UnE);
			if(UnE.findElement(By.tagName("td")).getText().contains("Reseteo 200 MB")) {
				UnE.findElement(By.className("slds-checkbox")).click();
			}
		}
		cc.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));
		sleep(3000);
		List<WebElement> pago = driver.findElement(By.id("PaymentTypeRadio|0")).findElements(By.cssSelector(".slds-radio.ng-scope"));
		for (WebElement UnP : pago) {
			System.out.println(UnP.getText());
			if (UnP.getText().toLowerCase().contains("descuento de saldo")){
				UnP.click();
				break;
			}
		}
		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		sleep(12000);
		WebElement ok = driver.findElement(By.id("alert-container")).findElements(By.tagName("div")).get(2);
		System.out.println(ok.getText());
		ok.click();
		sleep(4000);
		try {
			Assert.assertTrue(driver.findElement(By.id("PaymentTypeRadio|0")).findElements(By.tagName("div")).get(1).findElement(By.tagName("div")).findElement(By.tagName("small")).isDisplayed());
		}
		catch (Exception e) {
		}
	}
	
	@Test (groups = {"SIT","UAT"})
	public void TXSU00030_Renovacion_de_cuota_Agente_Reseteo_Internet_por_Dia_Limitrofe_Descuento_de_saldo_con_Credito(){
		LoguearAgente();
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		cc.menu_360_Ir_A("Inicio");
		irAGestionDeClientes();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		if (driver.getCurrentUrl().contains("sit"))
			sb.BuscarCuenta("DNI", "22222035");
		else
			sb.BuscarCuenta("DNI", "22223002");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		if (driver.getCurrentUrl().contains("sit"))
			cc.seleccionarCardPornumeroLinea("2932443389", driver);
		else
			cc.seleccionarCardPornumeroLinea("3572409631", driver);
		sleep(3000);
		cc.irAGestionEnCard("Renovacion de Datos");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("combosMegas")));
		List<WebElement> elementos = driver.findElement(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement UnE:elementos) {
			System.out.println(UnE);
			if(UnE.findElement(By.tagName("td")).getText().contains("Reseteo Internet por D\u00eda Lim\u00edtrofe")) {
				UnE.findElement(By.className("slds-checkbox")).click();
			}
		}
		cc.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));
		sleep(3000);
		List<WebElement> pago = driver.findElement(By.id("PaymentTypeRadio|0")).findElements(By.cssSelector(".slds-radio.ng-scope"));
		for (WebElement UnP : pago) {
			System.out.println(UnP.getText());
			if (UnP.getText().toLowerCase().contains("descuento de saldo")){
				UnP.click();
				break;
			}
		}
		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		sleep(18000);
		WebElement ok = driver.findElement(By.id("VlocityBPView")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElement(By.tagName("header")).findElement(By.tagName("h1"));
		System.out.println(ok.getText());
		Assert.assertTrue(ok.isDisplayed());
		Assert.assertTrue(ok.getText().equals("La operaci\u00f3n termino exitosamente"));
	}
}