package R1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class TriviasYSuscripciones extends TestBase {

	private WebDriver driver;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private GestionDeClientes_Fw ges;
	private CBS_Mattu cbsm;
	private LoginFw log;
	String detalles;
	
	
	//@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
	}
	
	@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() {
		driver = setConexion.setupEze();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
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
	
	@Test (groups = {"PerfilOficina", "R1"}, dataProvider = "CuentaTriviasYSuscripciones")
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
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"PerfilTelefonico", "R1"}, dataProvider = "CuentaTriviasYSuscripciones")
	public void TS151385_CRM_Movil_MIX_Suscripciones_Baja_de_suscripciones_sin_BlackList_Presencial(String sDNI, String sLinea) {
		imagen = "TS151385";
		detalles = imagen +" -Trivias y suscripciones - DNI: " +sDNI;
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
}