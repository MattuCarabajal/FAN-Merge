package MVP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.BasePage;
import Pages.CBS;
import Pages.CustomerCare;
import Pages.Marketing;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class NumerosAmigos extends TestBase {

	private WebDriver driver;
	private CustomerCare cc;
	private GestionDeClientes_Fw ges;
	private CBS cbs;
	private CBS_Mattu cbsm;
	private Marketing mk;
	private LoginFw log;
	private BasePage bp;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	//@BeforeClass (groups= "PerfilOficina")
	public void initSIT() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		bp = new BasePage();
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		log.LoginSit();
		ges.irAConsolaFAN();
	}
		
	@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		bp = new BasePage();
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
	}
		
	//@BeforeClass (groups = "PerfilAgente")
	public void initAgente() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		log.loginAgente();
		ges.irAConsolaFAN();
	}
	
	@BeforeMethod (alwaysRun = true)
	public void setup() {
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
	public void quit() {
		driver.quit();
		sleep(5000);
	}
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"PerfilOficina", "MVP"}, priority = 2, dataProvider = "NumerosAmigos")
	public void TS100602_CRM_Movil_REPRO_FF_Alta_Presencial(String sDNI, String sLinea, String sNumeroVOZ, String sNumeroSMS) {
		imagen = "TS100602";
		detalles = imagen+"-Numeros Amigos-DNI:"+sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("N\u00fameros Gratis");		
		cambioDeFrame(driver, By.cssSelector("[class = 'slds-col--padded slds-size--1-of-2']"), 0);
		List<WebElement> wNumerosAmigos = driver.findElements(By.cssSelector(".slds-col--padded.slds-size--1-of-2"));
		int iIndice = mk.numerosAmigos(sNumeroVOZ, sNumeroSMS);
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
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class = 'OSradioButton ng-scope only-buttom'] span")));
		driver.findElement(By.cssSelector("[class = 'OSradioButton ng-scope only-buttom'] span")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[class = 'slds-form-element vlc-flex vlc-slds-text-block vlc-slds-rte ng-pristine ng-valid ng-scope'] [class = 'ng-binding'] p")));
		List <WebElement> wMessage = driver.findElements(By.cssSelector("[class = 'slds-form-element vlc-flex vlc-slds-text-block vlc-slds-rte ng-pristine ng-valid ng-scope'] [class = 'ng-binding'] p"));
		boolean bAssert = wMessage.get(1).getText().contains("La orden se realiz\u00f3 con \u00e9xito!");
		Assert.assertTrue(bAssert);
		sleep(5000);
		if (iIndice == 0)
			Assert.assertTrue(cbs.validarNumeroAmigos(cbsm.Servicio_QueryCustomerInfo(sLinea), "voz", sNumeroVOZ));
		else
			Assert.assertTrue(cbs.validarNumeroAmigos(cbsm.Servicio_QueryCustomerInfo(sLinea), "sms", sNumeroSMS));
		String orden = cc.obtenerOrden(driver, "Change Friends and Family Number");
		System.out.println(orden);
		//Descomentar cuando se arregle el bug de la card
		//bp.closeTabByName(driver, "N\u00fameros Gratis");
		//ges.irAGestionEnCard("N\u00fameros Gratis");
		//Assert.assertTrue(mk.verificarNumerosAmigos(driver, sNumeroVOZ, sNumeroSMS));
		Assert.assertTrue(cc.corroborarEstadoCaso(orden, "Activated") || cc.corroborarEstadoCaso(orden, "Activada"));

	}
	
	@Test (groups = {"PerfilOficina", "MVP"}, priority = 3, dataProvider = "NumerosAmigosModificacion")
	public void TS100604_CRM_Movil_REPRO_FF_Modificacion_Presencial(String sDNI, String sLinea, String sNumeroVOZ, String sNumeroSMS) {
		imagen = "TS100604";
		detalles = imagen+"-Numeros Amigos-DNI:"+sDNI;
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		System.out.println(iMainBalance);
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("N\u00fameros Gratis");		
		cambioDeFrame(driver, By.cssSelector("[class = 'slds-col--padded slds-size--1-of-2']"), 0);
		List<WebElement> wNumerosAmigos = driver.findElements(By.cssSelector(".slds-col--padded.slds-size--1-of-2"));
		int iIndice = mk.numerosAmigos(sNumeroVOZ, sNumeroSMS);
		switch (iIndice) {
			case 0:
				wNumerosAmigos.get(0).findElement(By.tagName("input")).clear();
				sleep(3000);
				wNumerosAmigos.get(0).findElement(By.tagName("input")).sendKeys(sNumeroVOZ);
				sleep(2000);
				break;
			case 1:
				wNumerosAmigos.get(1).findElement(By.tagName("input")).clear();
				sleep(3000);
				wNumerosAmigos.get(1).findElement(By.tagName("input")).sendKeys(sNumeroSMS);
				sleep(2000);
				break;
			default:
				Assert.assertTrue(false);
		}
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class = 'OSradioButton ng-scope only-buttom'] span")));
		driver.findElement(By.cssSelector("[class = 'OSradioButton ng-scope only-buttom'] span")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("ChargeConfirmation_nextBtn")));
		buscarYClick( driver.findElements(By.cssSelector("[class ='slds-form-element__label ng-binding ng-scope']")), "contains", "si");
		cc.obligarclick(driver.findElement(By.id("ChargeConfirmation_nextBtn")));
		ges.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[class = 'slds-form-element vlc-flex vlc-slds-text-block vlc-slds-rte ng-pristine ng-valid ng-scope'] [class = 'ng-binding'] p")));
		List <WebElement> wMessage = driver.findElements(By.cssSelector("[class = 'slds-form-element vlc-flex vlc-slds-text-block vlc-slds-rte ng-pristine ng-valid ng-scope'] [class = 'ng-binding'] p"));
		boolean bAssert = wMessage.get(1).getText().contains("La orden se realiz\u00f3 con \u00e9xito!");
		Assert.assertTrue(bAssert);
		if (iIndice == 0)
			Assert.assertTrue(cbs.validarNumeroAmigos(cbsm.Servicio_QueryCustomerInfo(sLinea), "voz",sNumeroVOZ));
		else
			Assert.assertTrue(cbs.validarNumeroAmigos(cbsm.Servicio_QueryCustomerInfo(sLinea), "sms",sNumeroSMS));
		String orden = cc.obtenerOrden(driver, "Change Friends and Family Number");
		System.out.println(orden);
		sleep(10000);
		//Descomentar cuando se arregle el bug de la card
//		bp.closeTabByName(driver, "N\u00fameros Gratis");
//		ges.irAGestionEnCard("N\u00fameros Gratis");
//		Assert.assertTrue(mk.verificarNumerosAmigos(driver, sNumeroVOZ, sNumeroSMS));
		String uMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		Assert.assertTrue(iMainBalance > uiMainBalance);
		Assert.assertTrue(cc.corroborarEstadoCaso(orden, "Activated") || cc.corroborarEstadoCaso(orden, "Activada"));
	}
	
	@Test (groups = {"PerfilOficina", "MVP"}, priority = 6, dataProvider = "NumerosAmigosBaja")
	public void TS100605_CRM_Movil_REPRO_FF_Baja_Presencial(String sDNI, String sLinea, String sVOZorSMS) {
		imagen = "TS100605";
		detalles = imagen+"-Numeros Amigos-DNI:"+sDNI;
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("N\u00fameros Gratis");	
		cambioDeFrame(driver, By.cssSelector("[class = 'slds-col--padded slds-size--1-of-2']"), 0);
		List<WebElement> wNumerosAmigos = driver.findElements(By.cssSelector(".slds-col--padded.slds-size--1-of-2"));
		wNumerosAmigos.get(Integer.parseInt(sVOZorSMS)).click();
		wNumerosAmigos.get(Integer.parseInt(sVOZorSMS)).findElement(By.tagName("input")).clear();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class = 'OSradioButton ng-scope only-buttom'] span")));
		driver.findElement(By.cssSelector("[class = 'OSradioButton ng-scope only-buttom'] span")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("ChargeConfirmation_nextBtn")));
		buscarYClick( driver.findElements(By.cssSelector("[class ='slds-form-element__label ng-binding ng-scope']")), "contains", "si");
		cc.obligarclick(driver.findElement(By.id("ChargeConfirmation_nextBtn")));
		ges.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[class = 'slds-form-element vlc-flex vlc-slds-text-block vlc-slds-rte ng-pristine ng-valid ng-scope'] [class = 'ng-binding'] p")));
		List <WebElement> wMessage = driver.findElements(By.cssSelector("[class = 'slds-form-element vlc-flex vlc-slds-text-block vlc-slds-rte ng-pristine ng-valid ng-scope'] [class = 'ng-binding'] p"));
		boolean bAssert = wMessage.get(1).getText().contains("La orden se realiz\u00f3 con \u00e9xito!");
		Assert.assertTrue(bAssert);
		if (Integer.parseInt(sVOZorSMS) == 0)
			Assert.assertFalse(cbs.validarNumeroAmigos(cbsm.Servicio_QueryCustomerInfo(sLinea), "voz",""));
		else
			Assert.assertFalse(cbs.validarNumeroAmigos(cbsm.Servicio_QueryCustomerInfo(sLinea), "sms",""));
		String uMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		Assert.assertTrue(iMainBalance > uiMainBalance);
		String orden = cc.obtenerOrden(driver, "Change Friends and Family Number");
		System.out.println(orden);
		//Descomentar cuando se arregle el bug de la card
//		sleep(10000);
//		bp.closeTabByName(driver, "N\u00fameros Gratis");
//		cc.irAGestionEnCard("N\u00fameros Gratis");
//		wNumerosAmigos = driver.findElements(By.cssSelector(".slds-col--padded.slds-size--1-of-2"));
//		Assert.assertTrue(wNumerosAmigos.get(Integer.parseInt(sVOZorSMS)).getText().isEmpty());
		Assert.assertTrue(cc.corroborarEstadoCaso(orden, "Activated") || cc.corroborarEstadoCaso(orden, "Activada"));
	}
	
	@Test (groups = {"PerfilOficina", "MVP"}, priority = 1, dataProvider = "NumerosAmigosNoPersonalAlta")
	public void TS100607_CRM_Movil_REPRO_FF_No_Alta_Amigo_No_Personal_Presencial(String sDNI, String sLinea, String sNumeroVOZ, String sNumeroSMS) {
		imagen = "TS100607";
		detalles = imagen+"- Numeros Amigos - DNI:"+sDNI+" - Linea: "+sLinea;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("N\u00fameros Gratis");		
		cambioDeFrame(driver, By.cssSelector("[class = 'slds-col--padded slds-size--1-of-2']"), 0);
		List<WebElement> wNumerosAmigos = driver.findElements(By.cssSelector(".slds-col--padded.slds-size--1-of-2"));
		int iIndice = mk.numerosAmigos(sNumeroVOZ, sNumeroSMS);
		System.out.println(iIndice);
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
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='vlc-slds-error-block'] [class='error'] [class ='description']")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='vlc-slds-error-block'] [class='error']")).getText().equalsIgnoreCase("La linea no pertenece a Telecom, verifica el n\u00famero."));
	}
	
	@Test (groups = {"PerfilOficina", "MVP"}, priority = 4, dataProvider = "NumerosAmigosNoPersonalModificacion")
	public void TS100609_CRM_Movil_REPRO_FF_No_Modificacion_Presencial(String sDNI, String sLinea, String sNumeroVOZ, String sNumeroSMS) {
		imagen = "TS";
		detalles = imagen+"- Numeros Amigos - DNI: "+sDNI+" - Linea: "+sLinea;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("N\u00fameros Gratis");	
		cambioDeFrame(driver, By.cssSelector("[class = 'slds-col--padded slds-size--1-of-2']"), 0);
		List<WebElement> wNumerosAmigos = driver.findElements(By.cssSelector(".slds-col--padded.slds-size--1-of-2"));
		int iIndice = mk.numerosAmigos(sNumeroVOZ, sNumeroSMS);
		switch (iIndice) {
			case 0:
				wNumerosAmigos.get(0).findElement(By.tagName("input")).clear();
				sleep(3000);
				wNumerosAmigos.get(0).findElement(By.tagName("input")).sendKeys(sNumeroVOZ);
				sleep(2000);
				break;
			case 1:
				wNumerosAmigos.get(1).findElement(By.tagName("input")).clear();
				sleep(3000);
				wNumerosAmigos.get(1).findElement(By.tagName("input")).sendKeys(sNumeroSMS);
				sleep(2000);
				break;
			default:
				Assert.assertTrue(false);
		}
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='vlc-slds-error-block'] [class='error'] [class ='description']")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='vlc-slds-error-block'] [class='error']")).getText().equalsIgnoreCase("La linea no pertenece a Telecom, verifica el n\u00famero."));
	}
	
	@Test (groups = {"PerfilOficina", "MVP"}, priority = 5, dataProvider = "NumerosAmigosNoPersonalBaja")
	public void TS100612_CRM_Movil_REPRO_FF_No_Baja_Presencial(String sDNI, String sLinea, String sVOZorSMS) {
		imagen = "TS100612";
		detalles = imagen+"- Numeros Amigos - DNI:"+sDNI+" - Linea: "+sLinea;
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");		
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("N\u00fameros Gratis");		
		cambioDeFrame(driver, By.cssSelector("[class = 'slds-col--padded slds-size--1-of-2']"), 0);
		List<WebElement> wNumerosAmigos = driver.findElements(By.cssSelector(".slds-col--padded.slds-size--1-of-2"));
		wNumerosAmigos.get(Integer.parseInt(sVOZorSMS)).findElement(By.tagName("input")).clear();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class = 'OSradioButton ng-scope only-buttom'] span")));
		driver.findElement(By.cssSelector("[class = 'OSradioButton ng-scope only-buttom'] span")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("ChargeConfirmation_nextBtn")));
		buscarYClick( driver.findElements(By.cssSelector("[class ='slds-form-element__label ng-binding ng-scope']")), "contains", "no");
		cc.obligarclick(driver.findElement(By.id("ChargeConfirmation_nextBtn")));
		waitFor(driver,By.id("ErrorTB"));
		Assert.assertTrue(driver.findElement(By.id("ErrorTB")).getText().toLowerCase().contains("no fue posible realizar el cambio"));
		Assert.assertTrue(driver.findElement(By.id("ErrorTB")).getText().toLowerCase().contains("orden cancelada a pedido del cliente"));
		Assert.assertTrue(sMainBalance.equals(cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance")));
	}
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test (groups = {"PerfilAgente","MVP"}, dataProvider = "NumerosAmigosModificacion")
	public void TS139724_CRM_Movil_REPRO_FF_Modificacion_Agente(String sDNI, String sLinea, String sNumeroVOZ, String sNumeroSMS) {
		imagen = "TS139724";
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		System.out.println(iMainBalance);
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("N\u00fameros Gratis");		
		cambioDeFrame(driver, By.cssSelector("[class = 'slds-col--padded slds-size--1-of-2']"), 0);
		List<WebElement> wNumerosAmigos = driver.findElements(By.cssSelector(".slds-col--padded.slds-size--1-of-2"));
		int iIndice = mk.numerosAmigos(sNumeroVOZ, sNumeroSMS);
		switch (iIndice) {
			case 0:
				wNumerosAmigos.get(0).findElement(By.tagName("input")).clear();
				sleep(3000);
				wNumerosAmigos.get(0).findElement(By.tagName("input")).sendKeys(sNumeroVOZ);
				sleep(2000);
				break;
			case 1:
				wNumerosAmigos.get(1).findElement(By.tagName("input")).clear();
				sleep(3000);
				wNumerosAmigos.get(1).findElement(By.tagName("input")).sendKeys(sNumeroSMS);
				sleep(2000);
				break;
			default:
				Assert.assertTrue(false);
		}
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class = 'OSradioButton ng-scope only-buttom'] span")));
		driver.findElement(By.cssSelector("[class = 'OSradioButton ng-scope only-buttom'] span")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("ChargeConfirmation_nextBtn")));
		buscarYClick( driver.findElements(By.cssSelector("[class ='slds-form-element__label ng-binding ng-scope']")), "contains", "si");
		cc.obligarclick(driver.findElement(By.id("ChargeConfirmation_nextBtn")));
		ges.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[class = 'slds-form-element vlc-flex vlc-slds-text-block vlc-slds-rte ng-pristine ng-valid ng-scope'] [class = 'ng-binding'] p")));
		List <WebElement> wMessage = driver.findElements(By.cssSelector("[class = 'slds-form-element vlc-flex vlc-slds-text-block vlc-slds-rte ng-pristine ng-valid ng-scope'] [class = 'ng-binding'] p"));
		boolean bAssert = wMessage.get(1).getText().contains("La orden se realiz\u00f3 con \u00e9xito!");
		Assert.assertTrue(bAssert);
		if (iIndice == 0)
			Assert.assertTrue(cbs.validarNumeroAmigos(cbsm.Servicio_QueryCustomerInfo(sLinea), "voz",sNumeroVOZ));
		else
			Assert.assertTrue(cbs.validarNumeroAmigos(cbsm.Servicio_QueryCustomerInfo(sLinea), "sms",sNumeroSMS));
		String orden = cc.obtenerOrden(driver, "Change Friends and Family Number");
		System.out.println(orden);
		sleep(10000);
		//Descomentar cuando se arregle el bug de la card
//		bp.closeTabByName(driver, "N\u00fameros Gratis");
//		ges.irAGestionEnCard("N\u00fameros Gratis");
//		Assert.assertTrue(mk.verificarNumerosAmigos(driver, sNumeroVOZ, sNumeroSMS));
		String uMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		Assert.assertTrue(iMainBalance > uiMainBalance);
		Assert.assertTrue(cc.corroborarEstadoCaso(orden, "Activated") || cc.corroborarEstadoCaso(orden, "Activada"));
	}
}