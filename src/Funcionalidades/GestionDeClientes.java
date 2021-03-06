package Funcionalidades;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.CustomerCare;
import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class GestionDeClientes extends TestBase {

	private WebDriver driver;
	private SalesBase sb;
	private List<String> sOrders = new ArrayList<String>();
	private LoginFw log;
	private GestionDeClientes_Fw ges;
	private String imagen;
	private CBS_Mattu cbsm;
	private CustomerCare cc;
	
	String detalles;
	
	//@BeforeClass (groups= "PerfilOficina")
	public void Sit02() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.LoginSit03();
		//ges.irAConsolaFAN();
	}
	
	//@BeforeClass (groups= "PerfilOficina")
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sb = new SalesBase(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();	
	}
	
	@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		sb = new SalesBase(driver);
		cbsm = new CBS_Mattu();
		log.loginTelefonico();
		ges.irAConsolaFAN();	
	}
	
	@BeforeMethod (alwaysRun = true)
	public void setup() throws Exception {
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
	}

//	@AfterMethod (alwaysRun = true)
	public void after() throws IOException {
		guardarListaTxt(sOrders);
		sOrders.clear();
		tomarCaptura(driver,imagen);
	}
	
//	@AfterClass (alwaysRun = true)
	public void quit() throws IOException {
		driver.quit();
		sleep(5000);
	}
	
	//=============================================== OOCC ===============================================\\
	
	@Test (groups = "PerfilOficina")
	public void TS135495_CRM_Movil_REPRO_Busqueda_Tipo_de_documento_DNI() {
		imagen = "TS135495";
		detalles = imagen + " - Gestion de clientes";
		cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		selectByText(driver.findElement(By.id("SearchClientDocumentType")), "DNI");
		Assert.assertTrue(driver.findElement(By.id("SearchClientDocumentType")).getText().toLowerCase().contains("dni"));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "validaDocumentacion")
	public void TS135496_CRM_Movil_REPRO_Busqueda_DNI_Numero_de_Documento(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sRazon, String sEmail) {
		imagen = "TS135496";
		detalles = imagen + "- Gestion de clientes - DNI:" + sDNI;
		sb.BuscarCuenta("DNI", sDNI);
		WebElement cliente = driver.findElement(By.cssSelector("[class='slds-tabs--scoped__content'] tbody [class='searchClient-body slds-hint-parent ng-scope']"));
		String dni = cliente.findElements(By.tagName("td")).get(3).getText();
		Assert.assertTrue(sDNI.equals(dni));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "invalidaDocumentacion")
	public void TS135497_CRM_Movil_REPRO_Busqueda_DNI_Numero_de_Documento_no_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sRazon, String sEmail){
		imagen = "TS135497";
		detalles = imagen + "-Gestion de clientes - DNI:  "+ sDNI;
		sb.BuscarCuenta("DNI", sDNI);
		String message = "no hay ning\u00fan cliente con este tipo y n\u00famero de documento. busc\u00e1 con otro dato o cre\u00e1 un nuevo cliente.";
		String messageFound = getTextBy(driver, By.cssSelector("[class='slds-form-element vlc-flex vlc-slds-text-block vlc-slds-rte ng-pristine ng-valid ng-scope']"), 0);
		Assert.assertTrue(message.equalsIgnoreCase(messageFound));
	}
	
//	@Test (groups = "PerfilOficina")
	public void TS135498_CRM_Movil_REPRO_Busqueda_Tipo_de_documento_Libreta_de_enrolamiento() {
		imagen = "TS135498";
		detalles = imagen + " - Gestion de clientes";
		cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		selectByText(driver.findElement(By.id("SearchClientDocumentType")), "Libreta de Enrolamiento");
		Assert.assertTrue(driver.findElement(By.id("SearchClientDocumentType")).getText().toLowerCase().contains("libreta de enrolamiento"));
	}
	
//	@Test (groups = "PerfilOficina", dataProvider = "validaDocumentacion")
	public void TS135499_CRM_Movil_REPRO_Busqueda_Libreta_de_enrolamiento_Numero_de_Documento(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail){
		imagen = "TS135499";
		detalles = imagen + "- Gestion de clientes - Libreta de enrolamiento: " + sLibreta;
		sb.BuscarCuenta("Libreta de Enrolamiento", sLibreta);
		List<WebElement> activo = driver.findElement(By.className("slds-tabs--scoped__nav")).findElements(By.tagName("li"));
		Assert.assertTrue(activo.get(0).findElement(By.tagName("a")).getText().equals("Clientes Activos"));
	}
	
//	@Test (groups = "PerfilOficina", dataProvider = "invalidaDocumentacion")
	public void TS135500_CRM_Movil_REPRO_Busqueda_Libreta_dE_enrolamiento_Numero_de_Documento_no_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135500";
		detalles = imagen + "- Gestion de clientes - Libreta de enrolamiento: " + sLibreta;
		sb.BuscarCuenta("Libreta de Enrolamiento", sLibreta);
		String message = "no hay ning\u00fan cliente con este tipo y n\u00famero de documento";
		String messageFound = getTextBy(driver, By.cssSelector("[class='slds-form-element vlc-flex vlc-slds-text-block vlc-slds-rte ng-pristine ng-valid ng-scope']"), 0);
		Assert.assertTrue(messageFound.contains(message));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "validaDocumentacion")
	public void TS135501_CRM_Movil_REPRO_Busqueda_Nombre(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sRazon, String sEmail) {
		imagen = "TS135501";
		detalles = imagen + " - Gestion de clientes - Nombre: " + sNombre;
		sb.BuscarAvanzada(sNombre, "", "", "", "");
		Assert.assertTrue(ges.estaEnClientes(sDNI));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "invalidaDocumentacion")
	public void TS135502_CRM_Movil_REPRO_Busqueda_Nombre_No_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sRazon, String sEmail) {
		imagen = "TS135502";
		detalles = imagen + " - Gestion de clientes - Nombre: " + sNombre;
		sb.BuscarAvanzada(sNombre,"","","","");
		String message = "no hay ning\u00fan cliente con este tipo y n\u00famero de documento. busc\u00e1 con otro dato o cre\u00e1 un nuevo cliente.";
		String messageFound = getTextBy(driver, By.cssSelector("[class='slds-form-element vlc-flex vlc-slds-text-block vlc-slds-rte ng-pristine ng-valid ng-scope']"), 0);
		Assert.assertTrue(message.equalsIgnoreCase(messageFound));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "validaDocumentacion")
	public void TS135503_CRM_Movil_REPRO_Busqueda_Apellido(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sRazon, String sEmail) {
		imagen = "TS135503";
		detalles = imagen + "- Gestion de Clientes - DNI: " + sDNI;
		sb.BuscarAvanzada("", sApellido, "", "", "");
		Assert.assertTrue(ges.estaEnClientes(sDNI));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "invalidaDocumentacion")
	public void TS135504_CRM_Movil_REPRO_Busqueda_Apellido_No_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sRazon, String sEmail) {
		imagen = "TS135504";
		detalles = imagen + "- Gestion de clinetes - Apellido: " + sApellido;
		sb.BuscarAvanzada("",sApellido,"","","");
		String message = "no hay ning\u00fan cliente con este tipo y n\u00famero de documento. busc\u00e1 con otro dato o cre\u00e1 un nuevo cliente.";
		String messageFound = getTextBy(driver, By.cssSelector("[class='slds-form-element vlc-flex vlc-slds-text-block vlc-slds-rte ng-pristine ng-valid ng-scope']"), 0);
		Assert.assertTrue(message.equalsIgnoreCase(messageFound));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "validaDocumentacion")
	public void TS135505_CRM_Movil_REPRO_Busqueda_Razon_Social(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sRazon, String sEmail) {
		imagen = "TS135505";
		detalles = imagen + " - Gestion de clientes - Razon social: " + sRazon;
		sb.BuscarAvanzada("", "", sRazon, "", "");
		Assert.assertTrue(ges.estaEnClientes(sDNI));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "invalidaDocumentacion")
	public void TS135506_CRM_Movil_REPRO_Busqueda_Razon_social_No_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sRazon, String sEmail) {
		imagen = "TS135506";
		detalles = imagen+" - Gestion de clientes - Razon social: "+sRazon;
		sb.BuscarAvanzada("","",sRazon,"","");
		String message = "no hay ning\u00fan cliente con este tipo y n\u00famero de documento. busc\u00e1 con otro dato o cre\u00e1 un nuevo cliente.";
		String messageFound = getTextBy(driver, By.cssSelector("[class='slds-form-element vlc-flex vlc-slds-text-block vlc-slds-rte ng-pristine ng-valid ng-scope']"), 0);
		Assert.assertTrue(message.equalsIgnoreCase(messageFound));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "validaDocumentacion")
	public void TS135507_CRM_Movil_REPRO_Busqueda_Email(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sRazon, String sEmail) {
		imagen = "TS135507";
		detalles = imagen + " - Gestion de clientes - Email: " + sEmail;
		sb.BuscarAvanzada("", "", "", "", sEmail);
		Assert.assertTrue(ges.estaEnClientes(sDNI));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "invalidaDocumentacion")
	public void TS135508_CRM_Movil_REPRO_Busqueda_Email_No_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sRazon, String sEmail) {
		imagen = "TS135508";
		detalles = imagen + " - Gestion de clientes - Email: " + sEmail;
		sb.BuscarAvanzada("","","","",sEmail);
		String message = "no hay ning\u00fan cliente con este tipo y n\u00famero de documento. busc\u00e1 con otro dato o cre\u00e1 un nuevo cliente.";
		String messageFound = getTextBy(driver, By.cssSelector("[class='slds-form-element vlc-flex vlc-slds-text-block vlc-slds-rte ng-pristine ng-valid ng-scope']"), 0);
		Assert.assertTrue(message.equalsIgnoreCase(messageFound));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "validaDocumentacion")
	public void TS135509_CRM_Movil_REPRO_Busqueda_Numero_de_Cuenta(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sRazon, String sEmail) {
		imagen = "TS135509";
		detalles = imagen + "- Gestion de Clientes - DNI: " + sDNI;
		sb.BuscarAvanzada("", "", "", sNumeroDeCuenta, "");
		Assert.assertTrue(ges.estaEnClientes(sDNI));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "invalidaDocumentacion")
	public void TS135510_CRM_Movil_REPRO_Busqueda_Numero_de_Cuenta_No_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sRazon, String sEmail) {
		imagen = "TS135510";
		detalles = imagen + " - Gestion de clientes - Numero de cuenta: " + sNumeroDeCuenta;
		sb.BuscarAvanzada("", "", "", sNumeroDeCuenta, "");
		String message = "no hay ning\u00fan cliente con este tipo y n\u00famero de documento. busc\u00e1 con otro dato o cre\u00e1 un nuevo cliente.";
		String messageFound = getTextBy(driver, By.cssSelector("[class='slds-form-element vlc-flex vlc-slds-text-block vlc-slds-rte ng-pristine ng-valid ng-scope']"), 0);
		Assert.assertTrue(message.equalsIgnoreCase(messageFound));
	}
	
	@Test (groups = "PerfilOficina, R1", dataProvider = "invalidaDocumentacion")
	public void TS150642_CRM_Movil_MIX_Busqueda_DNI_Numero_de_Documento_no_existente_OOCC(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sRazon, String sEmail){
		imagen = "TS150642";
		sb.BuscarCuenta("DNI", sDNI);
		String message = "no hay ning\u00fan cliente con este tipo y n\u00famero de documento. busc\u00e1 con otro dato o cre\u00e1 un nuevo cliente.";
		String messageFound = getTextBy(driver, By.cssSelector("[class='slds-form-element vlc-flex vlc-slds-text-block vlc-slds-rte ng-pristine ng-valid ng-scope']"), 0);
		Assert.assertTrue(message.equalsIgnoreCase(messageFound));
	}
	
	@Test (groups = "PerfilOficina, R1", dataProvider = "validaDocumentacion")
	public void TS150654_CRM_Movil_MIX_Busqueda_Numero_de_Cuenta_OOCC(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sRazon, String sEmail){
		imagen = "150654";
		detalles = imagen + "- Gestion de Clientes - DNI: " + sDNI;
		sb.BuscarAvanzada("", "", "", sNumeroDeCuenta, "");
		Assert.assertTrue(ges.estaEnClientes(sDNI));
	}
	
	//------------------------------------------------------------    TELEFONICO     -------------------------------------------------------------------------------
	@Test (groups = "PerfilTelefonico, R1", dataProvider = "validaDocumentacion")
	public void TS150641_CRM_Movil_MIX_Busqueda_DNI_Numero_de_Documento_Telefonico(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sRazon, String sEmail){
		imagen = "150641";
		sb.BuscarCuenta("DNI", sDNI);
		Assert.assertTrue(ges.estaEnClientes(sDNI));
		
		
		
		
	}
}