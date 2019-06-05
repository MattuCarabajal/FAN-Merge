package Funcionalidades;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.BasePage;
import Pages.ContactSearch;
import Pages.CustomerCare;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class Nominacion extends TestBase {

	private WebDriver driver;
	private LoginFw log;
	private GestionDeClientes_Fw ges;
	private ContactSearch contact;
	private CBS_Mattu cbsm;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		contact = new ContactSearch(driver);
		cbsm = new CBS_Mattu();
		log.loginOOCC();
		ges.irAConsolaFAN();
	}
		
	//@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() {
		driver = setConexion.setupEze();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		contact = new ContactSearch(driver);
		cbsm = new CBS_Mattu();
		log.loginTelefonico();
		ges.irAConsolaFAN();
	}
	
	//@BeforeClass (groups = "PerfilAgente")
	public void initAgente() {
		driver = setConexion.setupEze();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		contact = new ContactSearch(driver);
		cbsm = new CBS_Mattu();
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
	
	@Test (groups = "PerfilOficina", dataProvider="DatosSalesNominacionNuevoOfCom") 
	public void TS85094_CRM_Movil_REPRO_Nominatividad_Cliente_Nuevo_Presencial_DOC_OfCom(String sLinea, String sDni, String sNombre, String sApellido, String sGenero, String sFnac, String sEmail, String sProvincia, String sLocalidad,String sZona, String sCalle, String sNumCa, String sCP, String tDomic) { 
		imagen = "TS85094";
		detalles = imagen + "- Nominacion - DNI:" + sDni;		
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
		contact.tipoValidacion("documento");
		File directory = new File("Dni.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(".slds-input.ng-pristine.ng-untouched.ng-empty.ng-invalid.ng-invalid-required")));
		contact.completarDomicilio(sProvincia, sLocalidad, sZona, sCalle, sNumCa, sCP, tDomic);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("FinishProcess_nextBtn")));
		List <WebElement> element = driver.findElement(By.id("NominacionExitosa")).findElements(By.tagName("p"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("nominaci\u00f3n exitosa!")) {
				a = true;
				System.out.println(x.getText());
			}
		}
		driver.findElement(By.id("FinishProcess_nextBtn")).click();
		cbsm.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
		Assert.assertTrue(a);
	}	
	
	@Test (groups = "PerfilOficina", dataProvider = "DatosSalesNominacionNuevoPasaporteOfCom") 
	public void TS128436_CRM_Movil_REPRO_Nominatividad_Presencial_DOC_Pasaporte_OfCom(String sLinea, String sPasaporte, String sNombre, String sApellido, String sGenero, String sFnac, String sPermanencia, String sEmail, String sProvincia, String sLocalidad,String sZona, String sCalle, String sNumCa, String sCP, String tDomic) {
		imagen = "TS128436";
		detalles = imagen + " -Nominacion: " + sPasaporte + " -Linea: "+sLinea;
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
		contact.crearClienteNominacionPasaporte("Pasaporte", sPasaporte, sGenero, sNombre, sApellido, sFnac, sEmail, sPermanencia);
		contact.tipoValidacion("documento");
		File directory = new File("Dni.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		contact.completarDomicilio(sProvincia, sLocalidad, sZona, sCalle, sNumCa, sCP, tDomic);
		File directory2 = new File ("form.pdf"); 
		contact.subirformulario(new File(directory2.getAbsolutePath()).toString());
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("FinishProcess_nextBtn")));
		sleep(20000);
		List <WebElement> element = driver.findElement(By.id("NominacionExitosa")).findElements(By.tagName("p"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("nominaci\u00f3n exitosa!")) {
				a = true;
				System.out.println(x.getText());
			}
		}
		driver.findElement(By.id("FinishProcess_nextBtn")).click();
		cbsm.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
		Assert.assertTrue(a);
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "DatosNoNominaNuevoEdadOfCom")
	public void TS130071_CRM_Movil_REPRO_No_Nominatividad_Presencial_DOC_Edad_OfCom(String sLinea, String sDni, String sSexo, String sFnac) {
		imagen = "TS130071";
		detalles = imagen + " No nominatividad-DNI: "+ sDni + " -Linea: " + sLinea;
		boolean msj = false;
		cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("PhoneNumber")));
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
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("DocumentInputSearch")));
		contact.searchContact2("DNI", sDni, sSexo);
		driver.findElement(By.id("Birthdate")).clear();
		driver.findElement(By.id("Birthdate")).sendKeys(sFnac);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope")));
		WebElement error = driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope"));
		if (error.getText().toLowerCase().contains("fecha de nacimiento inv\u00e1lida"))
			msj = true;
		driver.findElement(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("alert-ok-button")));
		driver.findElement(By.id("alert-ok-button")).click();
		Assert.assertTrue(msj);
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "NominacionExistenteOfCom") 
	public void TS85097_CRM_Movil_REPRO_Nominatividad_Cliente_Existente_Presencial_DOC_OfCom(String sLinea, String sDni, String sNombre, String sApellido) {
		imagen = "TS85097";
		detalles = imagen + " -Nominacion: " + sDni+"- Linea: "+sLinea;
		boolean nominacion = false;
		cambioDeFrame(driver,By.id("SearchClientDocumentType"),0);
		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(10000);
		WebElement cli = driver.findElement(By.id("tab-scoped-2")); 	
		cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).click();
		sleep(3000);
		List<WebElement> Lineas = driver.findElement(By.id("tab-scoped-2")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement UnaL: Lineas) {
			if(UnaL.getText().toLowerCase().contains("plan con tarjeta repro")) {
				UnaL.findElements(By.tagName("td")).get(6).findElement(By.tagName("svg")).click();
				System.out.println("Linea Encontrada");
				break;
			}
		}
		sleep(3000);
		contact.searchContact2("DNI", sDni, "Masculino");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Contact_nextBtn")));
		driver.findElement(By.id("Contact_nextBtn")).click();	
		sleep(10000);
		contact.tipoValidacion("documento");
		File directory = new File("Dni.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		sleep(18000);
		for (WebElement x : driver.findElement(By.id("NominacionExitosa")).findElements(By.tagName("p"))) {
			if (x.getText().toLowerCase().contains("nominaci\u00f3n exitosa!"))
				nominacion = true;
		}
		Assert.assertTrue(nominacion);
		driver.findElement(By.id("FinishProcess_nextBtn")).click();
		sleep(3000);
		cbsm.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "DatosNominacionExistente5Lineas")
	public void TS85104_CRM_Movil_REPRO_No_Nominatividad_Numero_Limite_De_Lineas_Cliente_Existente_Presencial(String sLinea, String sDni) {
		imagen = "TS85104";
		detalles = imagen + " - Nominacion: " + sDni + " - Linea: " + sLinea;
		boolean enc = false;
		cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("PhoneNumber")));
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
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("DocumentInputSearch")));
		contact.searchContact2("DNI", sDni, "Masculino");
		driver.findElement(By.id("Contact_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("MethodSelection_nextBtn")));
		if (driver.findElement(By.id("MethodSelection_nextBtn")).isDisplayed())
			enc = true;
		driver.findElement(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("alert-ok-button")));
		driver.findElement(By.id("alert-ok-button")).click();
		Assert.assertTrue(enc);
	}
//	@Test (groups = "PerfilOficina", dataProvider="DatosSalesNominacionPyRNuevoOfCom") 
//	public void TS85099_CRM_Movil_REPRO_Nominatividad_Cliente_Existente_Presencial_Preguntas_Y_Respuestas_Ofcom(String sLinea, String sDni, String sNombre, String sApellido, String sSexo, String sFnac, String sEmail, String sProvincia, String sLocalidad, String sCalle, String sNumCa, String sCP) { 
//		imagen = "TS85099-Nominacion"+sDni;
//		detalles = imagen +"-Linea: "+sLinea;
//		CustomerCare cc = new CustomerCare(driver);
//		BasePage bp = new BasePage();
//		sleep(5000);
//		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
//		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
//		sleep(1500);
//		driver.findElement(By.id("SearchClientsDummy")).click();
//		sleep(10000);
//		WebElement cli = driver.findElement(By.id("tab-scoped-1"));
//		cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).click();
//		sleep(3000);
//		List<WebElement> Lineas = driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
//		for(WebElement UnaL: Lineas) {
//			if(UnaL.getText().toLowerCase().contains("plan con tarjeta repro")||UnaL.getText().toLowerCase().contains("plan prepago nacional")) {
//				UnaL.findElements(By.tagName("td")).get(6).findElement(By.tagName("svg")).click();
//				System.out.println("Linea Encontrada");
//				break;
//			}
//		}
//		sleep(13000);
//		contact.searchContact2("DNI", sDni, sSexo);
//		try {contact.ingresarMail(sEmail, "si");}catch (org.openqa.selenium.ElementNotVisibleException ex1) {}
//		contact.tipoValidacion("preguntas y respuestas");
//		sleep(5000);
//		cc.obligarclick(driver.findElement(By.id("QAContactData_nextBtn")));
//		sleep(5000);
//		bp.setSimpleDropdown(driver.findElement(By.id("ImpositiveCondition")), "IVA Consumidor Final");
//		sleep(38000);
//		cbsm.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
//		List <WebElement> element = driver.findElement(By.id("NominacionExitosa")).findElements(By.tagName("p"));
//		System.out.println("cont="+element.get(0).getText());
//		boolean a = false;
//		for (WebElement x : element) {
//			if (x.getText().toLowerCase().contains("nominaci\u00f3n exitosa!")) {
//				a = true;
//				System.out.println(x.getText());
//			}
//		}
//		Assert.assertTrue(a);
//		driver.findElement(By.id("FinishProcess_nextBtn")).click();
//		sleep(3000);
//		cbsm.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
//	}
	
	//@Test(groups = "PerfilOficina", dataProvider = "DatosNoNominacionExistenteFraudeOfcom")
//	public void TS85105_CRM_Movil_REPRO_No_Nominatividad_Por_Fraude_Cliente_Existente_Presencial(String sLinea, String sDni) {
//		imagen = "TS85105";
//		detalles = imagen + " -Nominacion: " + sDni+"- Linea: "+sLinea;
//		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
//		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
//		driver.findElement(By.id("SearchClientsDummy")).click();
//		sleep(5000);
//		driver.findElement(By.cssSelector(".slds-button.slds-button--icon.slds-m-right--x-small.ng-scope")).click();
//		sleep(2000);
//		WebElement botonNominar = null;
//		for (WebElement x : driver.findElements(By.cssSelector(".slds-hint-parent.ng-scope"))) {
//			if (x.getText().toLowerCase().contains("plan con tarjeta"))
//				botonNominar = x;
//		}
//		for (WebElement x : botonNominar.findElements(By.tagName("td"))) {
//			if (x.getAttribute("data-label").equals("actions"))
//				botonNominar = x;
//		}
//		botonNominar.findElement(By.tagName("a")).click();
//		sleep(5000);
//		contact.searchContact2("DNI", sDni, "Masculino");
//		Assert.assertTrue(driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText().toLowerCase().contains("el dni figura en lista de fraude"));	
//	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = "PerfilTelefonico", dataProvider="DatosNoNominacionNuevoTelefonicoPasaporte")
	public void TS128437_CRM_Movil_REPRO_No_Nominatividad_Telefonico_Pasaporte(String sLinea, String sPasaporte, String sNombre, String sApellido, String sSexo, String sFnac, String sEmail, String sFperm) {
		imagen = "TS128437";
		detalles = imagen + " No nominacion Telefonico- DNI: " + sPasaporte + "-Linea: " + sLinea;
		boolean msj = false;
		cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("PhoneNumber")));
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
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("DocumentInputSearch")));
		contact.searchContact2("Pasaporte", sPasaporte, "Masculino");
		contact.Llenar_Contacto(sNombre, sApellido, sFnac, "", "a@yahoo.com");
		sleep(3000);
		driver.findElement(By.id("PermanencyDueDate")).sendKeys(sFperm);
		System.out.println(driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText());
		if (driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText().toLowerCase().contains("la permanencia no puede ser mayor a 2 a�os a partir de la fecha o menor a la fecha actual"))
			msj = true;
		driver.findElement(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("alert-ok-button")));
		driver.findElement(By.id("alert-ok-button")).click();
		Assert.assertTrue(msj);
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider="DatosNoNominaNuevoEdadOfCom")
	public void TS134494_CRM_Movil_REPRO_No_Nominatividad_No_Valida_Identidad_Cliente_Existente_Telefonico_Preguntas_Y_Respuestas(String sLinea, String sDni, String sSexo, String sFnac) {
		imagen = "TS134494";
		detalles = imagen + " -No nominacion Agente- DNI: " + sDni + " -Linea: " + sLinea;		
		boolean msj = false;
		cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("PhoneNumber")));
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
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("DocumentInputSearch")));
		contact.searchContact2("DNI", "22222035", "Masculino");
		contact.Llenar_Contacto("Quenico", "Newton", "15/02/1992", "", "asd@yahoo.com");
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("MethodSelection_nextBtn")));
		List<WebElement> valdni = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding"));
		for (WebElement x : valdni) {
			if (x.getText().toLowerCase().equals("validaci\u00f3n por preguntas y respuestas"))
				x.click();
		}
		driver.findElement(By.id("MethodSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("QAError_nextBtn")));
		for (WebElement x : driver.findElements(By.cssSelector(".slds-page-header__title.vlc-slds-page-header__title.slds-truncate.ng-binding"))) {
			if (x.getText().toLowerCase().equals("validaci\u00f3n no superada"))
				msj = true;
		}
		driver.findElement(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("alert-ok-button")));
		driver.findElement(By.id("alert-ok-button")).click();
		Assert.assertTrue(msj);
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider="DatosNoNominaNuevoEdadOfCom")
	public void TS85111_CRM_Movil_REPRO_No_Nominatividad_No_Valida_Identidad_Cliente_Nuevo_Telefonico_Preguntas_y_Respuestas(String sLinea, String sDni, String sSexo, String sFnac) {
		imagen = "TS85111";
		boolean msj = false;
		cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("PhoneNumber")));
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
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("DocumentInputSearch")));
		Random aleatorio = new Random(System.currentTimeMillis());
		aleatorio.setSeed(System.currentTimeMillis());
		int intAleatorio = aleatorio.nextInt(8999999)+1000000;
		String dni = Integer.toString(intAleatorio);
		contact.searchContact2("DNI", dni, "Masculino");
		contact.Llenar_Contacto("Quenico", "Newton", "15/02/1992", "", "asdasd@gmail.com");
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("MethodSelection_nextBtn")));
		List<WebElement> valdni = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding"));
		for (WebElement x : valdni) {
			if (x.getText().toLowerCase().equals("validaci\u00f3n por preguntas y respuestas"))
				x.click();
		}
		driver.findElement(By.id("MethodSelection_nextBtn")).click();	
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("QAContactData_nextBtn")));
		driver.findElement(By.id("QAContactData_nextBtn")).click();
		cambioDeFrame(driver, By.id("QAResult_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("QAResult_nextBtn")));
		for (WebElement x : driver.findElements(By.cssSelector(".message.description.ng-binding.ng-scope"))) {
			if (x.getText().toLowerCase().contains("validaci\u00f3n no superada"))
				msj = true;
		}
		driver.findElement(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("alert-ok-button")));
		driver.findElement(By.id("alert-ok-button")).click();
		Assert.assertTrue(msj);
	}
	
	//@Test (groups = "PerfilTelefonico", dataProvider = "DatosNoNominacionNuevoFraudeTelef")
//	public void TS85112_CRM_Movil_REPRO_No_Nominatividad_Por_Fraude_Cliente_Nuevo_Telefonico(String sLinea, String sDni) {
//		imagen = "TS85112";
//		detalles = imagen + " -Nominacion: " + sDni+"- Linea: "+sLinea;
//		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
//		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
//		driver.findElement(By.id("SearchClientsDummy")).click();
//		sleep(5000);
//		driver.findElement(By.cssSelector(".slds-button.slds-button--icon.slds-m-right--x-small.ng-scope")).click();
//		sleep(2000);
//		WebElement botonNominar = null;
//		for (WebElement x : driver.findElements(By.cssSelector(".slds-hint-parent.ng-scope"))) {
//			if (x.getText().toLowerCase().contains("plan con tarjeta"))
//				botonNominar = x;
//		}
//		for (WebElement x : botonNominar.findElements(By.tagName("td"))) {
//			if (x.getAttribute("data-label").equals("actions"))
//				botonNominar = x;
//		}
//		botonNominar.findElement(By.tagName("a")).click();
//		sleep(5000);
//		contact.searchContact2("DNI", sDni, "Masculino");
//		Assert.assertTrue(driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText().toLowerCase().contains("el dni figura en lista de fraude"));	
//	}
	
	@Test (groups = "PerfilTelefonico", dataProvider = "DatosNominacionExistente5Lineas")
	public void TS85113_CRM_Movil_REPRO_No_Nominatividad_Numero_Limite_De_Lineas_Cliente_Existente_Telefonico(String sLinea, String sDni) {
		imagen = "TS85113";
		detalles = imagen + " -Nominacion: " + sDni+"- Linea: "+sLinea;
		boolean enc = false;
		cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("PhoneNumber")));
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
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("DocumentInputSearch")));
		contact.searchContact2("DNI", sDni, "Masculino");
		driver.findElement(By.id("Contact_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("MethodSelection_nextBtn")));
		if (driver.findElement(By.id("MethodSelection_nextBtn")).isDisplayed())
			enc = true;
		driver.findElement(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("alert-ok-button")));
		driver.findElement(By.id("alert-ok-button")).click();
		Assert.assertTrue(enc);
	}
	
	//@Test (groups = "PerfilTelefonico", dataProvider = "DatosNoNominacionExistenteFraudeOfcom")
//	public void TS85114_CRM_Movil_REPRO_No_Nominatividad_Por_Fraude_Cliente_Existente_Presencial(String sLinea, String sDni) {
//		imagen = "TS85114";
//		detalles = imagen + " -Nominacion: " + sDni+"- Linea: "+sLinea;
//		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
//		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
//		driver.findElement(By.id("SearchClientsDummy")).click();
//		sleep(5000);
//		driver.findElement(By.cssSelector(".slds-button.slds-button--icon.slds-m-right--x-small.ng-scope")).click();
//		sleep(2000);
//		WebElement botonNominar = null;
//		for (WebElement x : driver.findElements(By.cssSelector(".slds-hint-parent.ng-scope"))) {
//			if (x.getText().toLowerCase().contains("plan con tarjeta"))
//				botonNominar = x;
//		}
//		for (WebElement x : botonNominar.findElements(By.tagName("td"))) {
//			if (x.getAttribute("data-label").equals("actions"))
//				botonNominar = x;
//		}
//		botonNominar.findElement(By.tagName("a")).click();
//		sleep(5000);
//		contact.searchContact2("DNI", sDni, "Masculino");
//		Assert.assertTrue(driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText().toLowerCase().contains("el dni figura en lista de fraude"));		
//	}
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test (groups = "PerfilAgente", dataProvider="DatosSalesNominacionNuevoAgente") 
	public void TS_CRM_Nominacion_Cliente_Nuevo_Agente(String sLinea, String sDni, String sNombre, String sApellido, String sGenero, String sFnac, String sEmail, String sProvincia, String sLocalidad,String sZona, String sCalle, String sNumCa, String sCP, String tDomic) { 
		imagen = "TS_CRM_Nominacion_Argentino_Agente"+sDni;
		detalles = imagen + "- Nominacion - DNI:" + sDni;
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("PhoneNumber")));
		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(10000);
		WebElement cli = driver.findElement(By.id("tab-scoped-2")); 	
		cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).click();
		sleep(3000);
		List<WebElement> Lineas = driver.findElement(By.id("tab-scoped-2")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement UnaL: Lineas) {
			if(UnaL.getText().toLowerCase().contains("plan con tarjeta repro")) {
				UnaL.findElements(By.tagName("td")).get(6).findElement(By.tagName("svg")).click();
				System.out.println("Linea Encontrada");
				break;
			}
		}
		sleep(10000);
		cambioDeFrame(driver, By.id("DocumentTypeSearch"),0);
		contact.crearClienteNominacion("DNI", sDni, sGenero, sNombre, sApellido, sFnac, sEmail);
		contact.tipoValidacion("documento");
		File directory = new File("Dni.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(".slds-input.ng-pristine.ng-untouched.ng-empty.ng-invalid.ng-invalid-required")));
		contact.completarDomicilio(sProvincia, sLocalidad, sZona, sCalle, sNumCa, sCP, tDomic);
		sleep(10000);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("FinishProcess_nextBtn")));
		cbsm.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
		List <WebElement> element = driver.findElement(By.id("NominacionExitosa")).findElements(By.tagName("p"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("nominaci\u00f3n exitosa!")) {
				a = true;
				System.out.println(x.getText());
			}
		}
		Assert.assertTrue(a);
		driver.findElement(By.id("FinishProcess_nextBtn")).click();
		sleep(3000);
		cbsm.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
	}
	
	@Test (groups = "PerfilAgente", dataProvider="DatosNoNominaNuevoEdadOfCom")
	public void TS134492_CRM_Movil_REPRO_No_Nominatividad_No_Valida_Identidad_Cliente_Existente_Presencial_DOC(String sLinea, String sDni, String sSexo, String sFnac) {
		imagen = "TS134492";
		detalles = imagen+"No nominacion Agente- DNI: "+sDni+"-Linea: "+sLinea;
		cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("PhoneNumber")));
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
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("DocumentInputSearch")));
		Random aleatorio = new Random(System.currentTimeMillis());
		aleatorio.setSeed(System.currentTimeMillis());
		int intAleatorio = aleatorio.nextInt(8999999)+1000000;
		String dni = Integer.toString(intAleatorio);
		contact.searchContact2("DNI", dni, "Masculino");
		contact.Llenar_Contacto("asdasd", "lalala", "12/05/2000", "", "asd@gmail.com");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("MethodSelection_nextBtn")));
		List<WebElement> valdni = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding"));
		for (WebElement x : valdni) {
			if (x.getText().toLowerCase().equals("validaci\u00f3n por documento de identidad"))
				x.click();
		}
		driver.findElement(By.id("MethodSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("DocumentMethod_nextBtn")));
		File directory = new File("DniMal.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "no");
		List<WebElement> errores = driver.findElements(By.cssSelector(".message.description.ng-binding.ng-scope")); 
		boolean error = false;
		for (WebElement UnE: errores) {
			if (UnE.getText().toLowerCase().contains("identidad no superada"))
				error = true;
		}
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("alert-ok-button")));
		driver.findElement(By.id("alert-ok-button")).click();
		Assert.assertTrue(error);
	}
	
	@Test (groups = "PerfilAgente", dataProvider="DatosNoNominaNuevoEdadOfCom") 
	public void TS134493_CRM_Movil_REPRO_No_Nominatividad_No_Valida_Identidad_Cliente_Existente_Presencial_Preguntas_y_Respuestas(String sLinea, String sDni, String sSexo, String sFnac) {		
		boolean msj = false;
		cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("PhoneNumber")));
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
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("DocumentInputSearch")));
		contact.searchContact2("DNI", "22222035", "Masculino");
		contact.Llenar_Contacto("Quenico", "Newton", "15/02/1992", "", "a@yahoo.com");
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("MethodSelection_nextBtn")));
		List<WebElement> valdni = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding"));
		for (WebElement x : valdni) {
			if (x.getText().toLowerCase().equals("validaci\u00f3n por preguntas y respuestas"))
				x.click();
		}
		driver.findElement(By.id("MethodSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("QAError_nextBtn")));
		for (WebElement x : driver.findElements(By.cssSelector(".slds-page-header__title.vlc-slds-page-header__title.slds-truncate.ng-binding"))) {
			if (x.getText().toLowerCase().equals("validaci\u00f3n no superada"))
				msj = true;
		}
		driver.findElement(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("alert-ok-button")));
		driver.findElement(By.id("alert-ok-button")).click();
		Assert.assertTrue(msj);
	}
	
	@Test (groups = "PerfilAgente", dataProvider="DatosNoNominaNuevoEdadOfCom")
	public void TS85100_CRM_Movil_REPRO_No_Nominatividad_No_Valida_Identidad_Cliente_nuevo_Presencial_DOC_Agente(String sLinea, String sDni, String sSexo, String sFnac) {
		imagen = "TS85100";
		detalles = imagen+"No nominacion Agente- DNI: "+sDni+"-Linea: "+sLinea;		
		cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("PhoneNumber")));
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
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("DocumentInputSearch")));
		Random aleatorio = new Random(System.currentTimeMillis());
		aleatorio.setSeed(System.currentTimeMillis());
		int intAleatorio = aleatorio.nextInt(8999999)+1000000;
		String dni = Integer.toString(intAleatorio);
		contact.searchContact2("DNI", dni, "Masculino");
		contact.Llenar_Contacto("asdasd", "lalala", "12/05/2000", "", "asd@gmail.com");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("MethodSelection_nextBtn")));
		List<WebElement> valdni = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding"));
		for (WebElement x : valdni) {
			if (x.getText().toLowerCase().equals("validaci\u00f3n por documento de identidad"))
				x.click();
		}
		driver.findElement(By.id("MethodSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("DocumentMethod_nextBtn")));
		File directory = new File("DniMal.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "no");
		List<WebElement> errores = driver.findElements(By.cssSelector(".message.description.ng-binding.ng-scope")); 
		boolean error = false;
		for (WebElement UnE: errores) {
			if (UnE.getText().toLowerCase().contains("identidad no superada"))
				error = true;
		}
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("alert-ok-button")));
		driver.findElement(By.id("alert-ok-button")).click();
		Assert.assertTrue(error);
	}	
}