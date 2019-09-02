package R2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.ContactSearch;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class Nominacion extends TestBase {
	
	private WebDriver driver;
	private LoginFw log;
	private GestionDeClientes_Fw ges;
	private CBS_Mattu cbsm;
	private ContactSearch contact;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	//@BeforeClass (groups = "PerfilOficina")
	public void initSIT() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		contact = new ContactSearch(driver);
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		log.LoginSit();		
		ges.irAConsolaFAN();
	}
	
	@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		contact = new ContactSearch(driver);
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		log.loginOOCC();
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

	@Test (groups = "PerfilOficina", dataProvider = "rNuevaNomina") 
	public void TS141398_CRM_Nuevo_Proceso_Repro_Cliente_nuevo_Presencial_OFCOM_Validacion_Documento(String sLinea, String sDni, String sNombre, String sApellido, String sGenero, String sFnac, String sEmail, String sProvincia, String sLocalidad,String sZona, String sCalle, String sNumCa, String sCP, String tDomic) { 
		imagen = "TS141398";
		cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("PhoneNumber")));
		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
		driver.findElement(By.id("SearchClientsDummy")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(".slds-button.slds-button--icon.slds-m-right--x-small.ng-scope")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--icon.slds-m-right--x-small.ng-scope")).click();
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".slds-hint-parent.ng-scope"), 0));
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
		cambioDeFrame(driver, By.id("DocumentTypeSearch"),0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("DocumentInputSearch")));
		contact.crearClienteNominacion("DNI", sDni, sGenero, sNombre, sApellido, sFnac, sEmail);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("MethodSelection_prevBtn")));
		driver.findElement(By.xpath("//*[@class='slds-size--1-of-1 slds-radio slds-grid slds-box vlc-slds-selectableItem ng-scope']//h2[text()='Validaci\u00f3n por DNI']")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.className("uploadFile")));
		File directory = new File("Dni.jpg");
		driver.findElement(By.className("uploadFile")).sendKeys(directory.getAbsolutePath().toString());
//		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("DocumentMethod_nextBtn")));
//		driver.findElement(By.id("DocumentMethod_nextBtn")).click();
	}
}
