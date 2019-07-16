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
import Pages.CBS;
import Pages.CustomerCare;
import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class TriviasYSuscripciones extends TestBase {

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private GestionDeClientes_Fw ges;
	private CBS_Mattu cbsm;
	private CBS cbs;
	private LoginFw log;
	String detalles;
	
	
	//@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
	}
	
	@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginTelefonico();
		ges.irAConsolaFAN();
	}
	
	
	@BeforeMethod(alwaysRun=true)
	public void setup() throws Exception {
		detalles = null;
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
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
	
	@Test (groups = "PerfilOficina", dataProvider = "CuentaTriviasYSuscripciones")
	public void TS110877_CRM_Movil_REPRO_Suscripciones_Baja_de_una_suscripcion_sin_BlackList_con_ajuste_Presencial(String sDNI, String sLinea) {
		imagen = "TS110877";
		detalles = null;
		detalles = imagen + "- Trivias y suscripciones - DNI: "+sDNI;
		boolean gest = false;
		CBS_Mattu cCBSM = new CBS_Mattu();		
		WebElement blackList = null;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cc.irAGestionEnCard("Suscripciones");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".addedValueServices-row.ng-pristine.ng-untouched.ng-valid.ng-empty")).findElement(By.className("slds-checkbox")).click();
		driver.findElements(By.cssSelector(".slds-button.slds-button--brand")).get(1).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.id("UnsubscriptionOptions_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "nunca me suscrib\u00ed");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		List <WebElement> bl = driver.findElements(By.className("slds-form-element__control"));
		for (WebElement x : bl) {
			if (x.getText().toLowerCase().contains("quer\u00e9s agregar al cliente a la blacklist")) {
				blackList = x;
			}
		}
		buscarYClick(blackList.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("UnsubscriptionOptions_nextBtn")).click();
		sleep(10000);
		try {
			driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")));
			driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")).get(1).click();
		} catch (Exception e) {}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("UnsubscriptionSummary_nextBtn")));
		driver.switchTo().frame(cambioFrame(driver, By.id("UnsubscriptionSummary_nextBtn")));
		driver.findElement(By.id("UnsubscriptionSummary_nextBtn")).click();
		sleep(10000);
		cCBSM.Servicio_RealizarAltaSuscripcion(sLinea, "178");
		List <WebElement> msj = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for (WebElement x : msj) {
			if (x.getText().toLowerCase().contains("tu caso se resolvi\u00f3 con \u00e9xito")) {
				gest = true;
			}
		}
		Assert.assertTrue(gest);
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "CuentaTriviasYSuscripciones")
	public void TS110893_CRM_Movil_REPRO_Suscripciones_Baja_de_una_suscripcion_con_BlackList_con_ajuste_Presencial(String sDNI, String sLinea) {
		imagen="TS110893";
		detalles = null;
		detalles = imagen + "- Trivias y suscripciones - DNI:" + sDNI;
		boolean gest = false;
		CBS_Mattu cCBSM = new CBS_Mattu();
		cCBSM.Servicio_RealizarAltaSuscripcion(sLinea, "178");
		WebElement blackList = null;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cc.irAGestionEnCard("Suscripciones");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".addedValueServices-row.ng-pristine.ng-untouched.ng-valid.ng-empty")).findElement(By.className("slds-checkbox")).click();
		driver.findElements(By.cssSelector(".slds-button.slds-button--brand")).get(1).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.id("UnsubscriptionOptions_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "nunca me suscrib\u00ed");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		List <WebElement> bl = driver.findElements(By.className("slds-form-element__control"));
		for (WebElement x : bl) {
			if (x.getText().toLowerCase().contains("quer\u00e9s agregar al cliente a la blacklist")) {
				blackList = x;
			}
		}
		buscarYClick(blackList.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("UnsubscriptionOptions_nextBtn")).click();
		sleep(10000);
		try {
			driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")));
			driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")).get(1).click();
		} catch (Exception e) {}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("UnsubscriptionSummary_nextBtn")));
		driver.findElement(By.id("UnsubscriptionSummary_nextBtn")).click();
		sleep(10000);
		List <WebElement> msj = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for (WebElement x : msj) {
			if (x.getText().toLowerCase().contains("tu caso se resolvi\u00f3 con \u00e9xito")) {
				gest = true;
			}
		}
		Assert.assertTrue(gest);
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "CuentaTriviasYSuscripciones")
	public void TS119032_CRM_Movil_REPRO_Suscripciones_Baja_de_suscripciones_sin_BlackList_Presencial(String cDNI,String cLinea) {
		imagen = "TS119032";
		detalles = null;
		detalles = imagen +" -Trivias y suscripciones - DNI: " +cDNI;
		boolean gest = false;
		CBS_Mattu cCBSM = new CBS_Mattu();
		cCBSM.Servicio_RealizarAltaSuscripcion(cLinea,"178");
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		cc.seleccionarCardPornumeroLinea(cLinea, driver);
		sleep(3000);
		cc.irAGestionEnCard("Suscripciones");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".addedValueServices-row.ng-pristine.ng-untouched.ng-valid.ng-empty")).findElement(By.className("slds-checkbox")).click();
		driver.findElements(By.cssSelector(".slds-button.slds-button--brand")).get(1).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.id("UnsubscriptionOptions_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "nunca me suscrib\u00ed");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("UnsubscriptionOptions_nextBtn")).click();
		sleep(10000);
		try {
			driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")));
			driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")).get(1).click();
		} catch (Exception e) {}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("UnsubscriptionSummary_nextBtn")));
		driver.findElement(By.id("UnsubscriptionSummary_nextBtn")).click();
		sleep(10000);
		List <WebElement> msj = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for (WebElement x : msj) {
			if (x.getText().toLowerCase().contains("tu caso se resolvi\u00f3 con \u00e9xito")) {
				gest = true;
			}
		}
		Assert.assertTrue(gest);
	}
	
	@Test (groups = {"PerfilOficica" , "R1"}, dataProvider = "CuentaTriviasYSuscripciones")
	public void TS151385_CRM_Movil_MIX_Suscripciones_Baja_de_suscripciones_sin_BlackList_Presencial(String sDNI, String sLinea) {
		imagen = "TS151384";
		detalles = imagen +" -Trivias y suscripciones - DNI: " +sDNI;
		//cbsm.Servicio_RealizarAltaSuscripcion("2932598789","178");
		ges.BuscarCuenta("DNI", sDNI);
		ges.irSuscripciones(sLinea);
		cambioDeFrame(driver, By.cssSelector("[class = 'slds-button slds-button--brand']"), 0);
		sleep(5000);
		driver.findElement(By.cssSelector("[class = 'slds-card__body cards-container'] [class = 'addedValueServices'] [class = 'slds-checkbox']")).click();
		driver.findElement(By.cssSelector("[class = 'slds-p-horizontal--x-small slds-p-bottom--large'] button")).click();
		cambioDeFrame(driver, By.id("UnsubscriptionOptions_nextBtn"), 0);
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector("[class = 'slds-form-element__label ng-binding ng-scope']")), "contains", "nunca me suscrib\u00ed");
		buscarYClick(driver.findElements(By.cssSelector("[class = 'slds-form-element__label ng-binding ng-scope']")), "contains", "no");
		driver.findElement(By.id("UnsubscriptionOptions_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("UnsubscriptionSummary_nextBtn")));
		String mensaje = driver.findElement(By.cssSelector("[class = 'slds-form-element__control'] p p span")).getText();
		Assert.assertTrue(mensaje.toLowerCase().contains("las suscripciones fueron dadas de baja exitosamente"));
		driver.findElement(By.id("UnsubscriptionSummary_nextBtn")).click();
		cambioDeFrame(driver, By.cssSelector("[class = 'slds-icon slds-icon--large nds-icon nds-icon_large ta-Icon-wrapper-content']"), 0);
		esperarElemento(driver, By.cssSelector("[class = 'slds-form-element__control'] p h1 span"), 0);
		String verificacion = driver.findElement(By.cssSelector("[class = 'slds-form-element__control'] p h1 span")).getText();
		Assert.assertTrue(verificacion.toLowerCase().contains("tu caso se resolvi\u00f3 con \u00e9xito"));
		String nGestion = driver.findElement(By.cssSelector("[class = 'slds-form-element__control'] p span strong")).getText();
		sleep(5000);
		ges.cerrarPestaniaGestion(driver);
		driver.findElement(By.id("phSearchInput")).sendKeys(nGestion);
		driver.findElement(By.id("phSearchInput")).submit();
		boolean gestion = false;
		for (int i = 0; i < 10; i++) {
			cambioDeFrame(driver, By.id("searchPageHolderDiv"), 0);
			String estado = driver.findElement(By.xpath("//*[@id='Case_body']//tr[2]//td[3]")).getText();
			if (estado.equalsIgnoreCase("resuelta exitosa")) {
				gestion = true;
			} else {
				sleep(8000);
				driver.navigate().refresh();
			}
		}
		Assert.assertTrue(gestion);
	}
	
	
	//----------------------------------------------- Telefonico -------------------------------------------------------\\
	
	@Test (groups = {"PerfilOficica" , "R1"}, dataProvider = "CuentaTriviasYSuscripciones")
	public void TS151384_CRM_Movil_MIX_Suscripciones_Baja_de_suscripciones_sin_BlackList_Crm_Telefonico(String sDNI, String sLinea) {
		imagen = "TS151384";
		detalles = imagen +" -Trivias y suscripciones - DNI: " +sDNI;
		cbsm.Servicio_RealizarAltaSuscripcion("2932598789","178");
		ges.BuscarCuenta("DNI", "38010123");
		ges.irSuscripciones("2932598789");
		cambioDeFrame(driver, By.cssSelector("[class = 'slds-button slds-button--brand']"), 0);
		sleep(5000);
		driver.findElement(By.cssSelector("[class = 'slds-card__body cards-container'] [class = 'addedValueServices'] [class = 'slds-checkbox']")).click();
		driver.findElement(By.cssSelector("[class = 'slds-p-horizontal--x-small slds-p-bottom--large'] button")).click();
		cambioDeFrame(driver, By.id("UnsubscriptionOptions_nextBtn"), 0);
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector("[class = 'slds-form-element__label ng-binding ng-scope']")), "contains", "nunca me suscrib\u00ed");
		buscarYClick(driver.findElements(By.cssSelector("[class = 'slds-form-element__label ng-binding ng-scope']")), "contains", "no");
		driver.findElement(By.id("UnsubscriptionOptions_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("UnsubscriptionSummary_nextBtn")));
		String mensaje = driver.findElement(By.cssSelector("[class = 'slds-form-element__control'] p p span")).getText();
		Assert.assertTrue(mensaje.toLowerCase().contains("las suscripciones fueron dadas de baja exitosamente"));
		driver.findElement(By.id("UnsubscriptionSummary_nextBtn")).click();
		cambioDeFrame(driver, By.cssSelector("[class = 'slds-icon slds-icon--large nds-icon nds-icon_large ta-Icon-wrapper-content']"), 0);
		esperarElemento(driver, By.cssSelector("[class = 'slds-form-element__control'] p h1 span"), 0);
		String verificacion = driver.findElement(By.cssSelector("[class = 'slds-form-element__control'] p h1 span")).getText();
		Assert.assertTrue(verificacion.toLowerCase().contains("tu caso se resolvi\u00f3 con \u00e9xito"));
		String nGestion = driver.findElement(By.cssSelector("[class = 'slds-form-element__control'] p span strong")).getText();
		sleep(5000);
		ges.cerrarPestaniaGestion(driver);
		driver.findElement(By.id("phSearchInput")).sendKeys(nGestion);
		driver.findElement(By.id("phSearchInput")).submit();
		boolean gestion = false;
		for (int i = 0; i < 10; i++) {
			cambioDeFrame(driver, By.id("searchPageHolderDiv"), 0);
			String estado = driver.findElement(By.xpath("//*[@id='Case_body']//tr[2]//td[3]")).getText();
			if (estado.equalsIgnoreCase("resuelta exitosa")) {
				gestion = true;
			} else {
				sleep(8000);
				driver.navigate().refresh();
			}
		}
		Assert.assertTrue(gestion);
	}
}