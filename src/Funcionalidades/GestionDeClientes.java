package Funcionalidades;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.CustomerCare;
import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import Tests.TestBase;

public class GestionDeClientes extends TestBase {

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
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"})
	public void TS135495_CRM_Movil_REPRO_Busqueda_Tipo_de_documento_DNI() {
		driver.switchTo().defaultContent();
		imagen = "TS135495";
		detalles = null;
		detalles = imagen + " - Gestion de clientes";
		sleep(6000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		selectByText(driver.findElement(By.id("SearchClientDocumentType")), "DNI");
		sleep(10000);
		Assert.assertTrue(driver.findElement(By.id("SearchClientDocumentType")).getText().toLowerCase().contains("dni"));
	}
	
	@Test(groups = { "Sales", "GestionDeClientes", "Ciclo1" }, dataProvider = "validaDocumentacion")
	public void TS135496_CRM_Movil_REPRO_Busqueda_DNI_Numero_de_Documento(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135496";
		detalles = null;
		detalles = imagen + "- Gestion de clientes - DNI:" + sDNI;
		sleep(6000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		sleep(6000);
		WebElement cliente = driver.findElement(By.cssSelector("[class='slds-tabs--scoped__content'] tbody [class='searchClient-body slds-hint-parent ng-scope']"));
		String dni = cliente.findElements(By.tagName("td")).get(3).getText();
		Assert.assertTrue(sDNI.equals(dni));
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "invalidaDocumentacion")
	public void TS135497_CRM_Movil_REPRO_Busqueda_DNI_Numero_de_Documento_no_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail){
		imagen = "TS135497";
		detalles = null;
		detalles = imagen + "-Gestion de clientes - DNI:  "+ sDNI;
		sleep(6000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		sleep(5000);
		String message = "no hay ning\u00fan cliente con este tipo y n\u00famero de documento. busc\u00e1 con otro dato o cre\u00e1 un nuevo cliente.";
		String messageFound = driver.findElement(By.cssSelector("[class='slds-form-element vlc-flex vlc-slds-text-block vlc-slds-rte ng-pristine ng-valid ng-scope']")).getText();
		Assert.assertTrue(message.equalsIgnoreCase(messageFound));
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"})
	public void TS135498_CRM_Movil_REPRO_Busqueda_Tipo_de_documento_Libreta_de_enrolamiento() {
		imagen = "TS135498";
		detalles = null;
		detalles = imagen + " - Gestion de clientes";
		sleep(6000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		selectByText(driver.findElement(By.id("SearchClientDocumentType")), "Libreta de Enrolamiento");
		sleep(5000);
		Assert.assertTrue(driver.findElement(By.id("SearchClientDocumentType")).getText().toLowerCase().contains("libreta de enrolamiento"));
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "validaDocumentacion") 
	public void TS135499_CRM_Movil_REPRO_Busqueda_Libreta_de_enrolamiento_Numero_de_Documento(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail){
		imagen = "TS135499";
		detalles = null;
		detalles = imagen + "- Gestion de clientes - Libreta de enrolamiento: " + sLibreta;
		sleep(6000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("Libreta de Enrolamiento", sLibreta);
		sleep(5000);
		List<WebElement> activo = driver.findElement(By.className("slds-tabs--scoped__nav")).findElements(By.tagName("li"));
		Assert.assertTrue(activo.get(0).findElement(By.tagName("a")).getText().equals("Clientes Activos"));
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "invalidaDocumentacion")
	public void TS135500_CRM_Movil_REPRO_Busqueda_Libreta_dE_enrolamiento_Numero_de_Documento_no_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135500";
		detalles = null;
		detalles = imagen + "- Gestion de clientes - Libreta de enrolamiento: " + sLibreta;
		sleep(6000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("Libreta de Enrolamiento", sLibreta);
		sleep(5000);
		String message = "no hay ning\u00fan cliente con este tipo y n\u00famero de documento";
		String messageFound = driver.findElement(By.cssSelector("[class='slds-form-element vlc-flex vlc-slds-text-block vlc-slds-rte ng-pristine ng-valid ng-scope']")).getText().toLowerCase();
		Assert.assertTrue(messageFound.contains(message));
	}
	
	@Test(groups = { "Sales", "GestionDeClientes", "Ciclo1" }, dataProvider = "validaDocumentacion")
	public void TS135501_CRM_Movil_REPRO_Busqueda_Nombre(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135501";
		detalles = null;
		detalles = imagen + " - Gestion de clientes - Nombre: " + sNombre;
		sleep(6000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada(sNombre, "", "", "", "");
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(5000);
		boolean encontrado = false;
		List<WebElement> clientes = driver.findElements(By.cssSelector("[class='slds-tabs--scoped__content'] tbody [class='searchClient-body slds-hint-parent ng-scope']"));
		for (WebElement cliente : clientes) {
			String dni = cliente.findElements(By.tagName("td")).get(3).getText();
			if (sDNI.equals(dni)) {
				encontrado = true;
				break;
			}
		}
		Assert.assertTrue(encontrado);
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "invalidaDocumentacion")
	public void TS135502_CRM_Movil_REPRO_Busqueda_Nombre_No_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135502";
		detalles = null;
		detalles = imagen + " - Gestion de clientes - Nombre: " + sNombre;
		sleep(6000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada(sNombre,"","","","");
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(5000);
		String message = "no hay ning\u00fan cliente con este tipo y n\u00famero de documento. busc\u00e1 con otro dato o cre\u00e1 un nuevo cliente.";
		String messageFound = driver.findElement(By.cssSelector("[class='slds-form-element vlc-flex vlc-slds-text-block vlc-slds-rte ng-pristine ng-valid ng-scope']")).getText();
		Assert.assertTrue(message.equalsIgnoreCase(messageFound));
	}
	
	@Test(groups = { "Sales", "GestionDeClientes", "Ciclo1" }, dataProvider = "validaDocumentacion")
	public void TS135503_CRM_Movil_REPRO_Busqueda_Apellido(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135503";
		detalles = null;
		detalles = imagen + "- Gestion de Clientes - DNI: " + sDNI;
		sleep(6000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada("", sApellido, "", "", "");
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(5000);
		boolean encontrado = false;
		List<WebElement> clientes = driver.findElements(By.cssSelector("[class='slds-tabs--scoped__content'] tbody [class='searchClient-body slds-hint-parent ng-scope']"));
		for (WebElement cliente : clientes) {
			String dni = cliente.findElements(By.tagName("td")).get(3).getText();
			if (sDNI.equals(dni)) {
				encontrado = true;
				break;
			}
		}
		Assert.assertTrue(encontrado);
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "invalidaDocumentacion")
	public void TS135504_CRM_Movil_REPRO_Busqueda_Apellido_No_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135504";
		detalles = null;
		detalles = imagen + "- Gestion de clinetes - Apellido: " + sApellido;
		sleep(6000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada("",sApellido,"","","");
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(5000);
		String message = "no hay ning\u00fan cliente con este tipo y n\u00famero de documento. busc\u00e1 con otro dato o cre\u00e1 un nuevo cliente.";
		String messageFound = driver.findElement(By.cssSelector("[class='slds-form-element vlc-flex vlc-slds-text-block vlc-slds-rte ng-pristine ng-valid ng-scope']")).getText();
		Assert.assertTrue(message.equalsIgnoreCase(messageFound));
	}
	
	@Test(groups = { "Sales", "GestionDeClientes", "Ciclo1" }, dataProvider = "validaDocumentacion")
	public void TS135505_CRM_Movil_REPRO_Busqueda_Razon_Social(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135505";
		detalles = null;
		detalles = imagen + " - Gestion de clientes - Razon social: " + sRazon;
		sleep(6000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada("", "", sRazon, "", "");
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(5000);
		boolean encontrado = false;
		List<WebElement> clientes = driver.findElements(By.cssSelector("[class='slds-tabs--scoped__content'] tbody [class='searchClient-body slds-hint-parent ng-scope']"));
		for (WebElement cliente : clientes) {
			String dni = cliente.findElements(By.tagName("td")).get(3).getText();
			if (sDNI.equals(dni)) {
				encontrado = true;
				break;
			}
		}
		Assert.assertTrue(encontrado);
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "invalidaDocumentacion")
	public void TS135506_CRM_Movil_REPRO_Busqueda_Razon_social_No_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135506";
		detalles = null;
		detalles = imagen+" - Gestion de clientes - Razon social: "+sRazon;
		sleep(6000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada("","",sRazon,"","");
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(5000);
		String message = "no hay ning\u00fan cliente con este tipo y n\u00famero de documento. busc\u00e1 con otro dato o cre\u00e1 un nuevo cliente.";
		String messageFound = driver.findElement(By.cssSelector("[class='slds-form-element vlc-flex vlc-slds-text-block vlc-slds-rte ng-pristine ng-valid ng-scope']")).getText();
		Assert.assertTrue(message.equalsIgnoreCase(messageFound));
	}
	
	@Test(groups = { "Sales", "GestionDeClientes", "Ciclo1" }, dataProvider = "validaDocumentacion")
	public void TS135507_CRM_Movil_REPRO_Busqueda_Email(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135507";
		detalles = null;
		detalles = imagen + " - Gestion de clientes - Email: " + sEmail;
		sleep(6000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada("", "", "", "", sEmail);
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(5000);
		WebElement cliente = driver.findElement(By.cssSelector("[class='slds-tabs--scoped__content'] tbody [class='searchClient-body slds-hint-parent ng-scope']"));
		String dni = cliente.findElements(By.tagName("td")).get(3).getText();
		Assert.assertTrue(sDNI.equals(dni));
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "invalidaDocumentacion")
	public void TS135508_CRM_Movil_REPRO_Busqueda_Email_No_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135508";
		detalles = null;
		detalles = imagen + " - Gestion de clientes - Email: " + sEmail;
		sleep(6000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada("","","","",sEmail);
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(5000);
		String message = "no hay ning\u00fan cliente con este tipo y n\u00famero de documento. busc\u00e1 con otro dato o cre\u00e1 un nuevo cliente.";
		String messageFound = driver.findElement(By.cssSelector("[class='slds-form-element vlc-flex vlc-slds-text-block vlc-slds-rte ng-pristine ng-valid ng-scope']")).getText();
		Assert.assertTrue(message.equalsIgnoreCase(messageFound));
	}
	
	@Test(groups = { "Sales", "GestionDeClientes", "Ciclo1" }, dataProvider = "validaDocumentacion")
	public void TS135509_CRM_Movil_REPRO_Busqueda_Numero_de_Cuenta(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135509";
		detalles = null;
		detalles = imagen + "- Gestion de Clientes - DNI: " + sDNI;
		sleep(6000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada("", "", "", sNumeroDeCuenta, "");
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(5000);
		WebElement cliente = driver.findElement(By.cssSelector("[class='slds-tabs--scoped__content'] tbody [class='searchClient-body slds-hint-parent ng-scope']"));
		String dni = cliente.findElements(By.tagName("td")).get(3).getText();
		Assert.assertTrue(sDNI.equals(dni));
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "invalidaDocumentacion")
	public void TS135510_CRM_Movil_REPRO_Busqueda_Numero_de_Cuenta_No_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135510";
		detalles = null;
		detalles = imagen + " - Gestion de clientes - Numero de cuenta: " + sNumeroDeCuenta;
		sleep(6000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada("", "", "", sNumeroDeCuenta, "");
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(10000);
		String message = "no hay ning\u00fan cliente con este tipo y n\u00famero de documento. busc\u00e1 con otro dato o cre\u00e1 un nuevo cliente.";
		String messageFound = driver.findElement(By.cssSelector("[class='slds-form-element vlc-flex vlc-slds-text-block vlc-slds-rte ng-pristine ng-valid ng-scope']")).getText();
		Assert.assertTrue(message.equalsIgnoreCase(messageFound));
	}
}