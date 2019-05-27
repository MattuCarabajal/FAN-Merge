package Funcionalidades;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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
import Pages.BasePage;
import Pages.ContactSearch;
import Pages.CustomerCare;
import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class Nominacion extends TestBase {

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	private LoginFw log;
	private GestionDeClientes_Fw ges;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	@BeforeClass (alwaysRun = true)
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
//		loginOOCC(driver);
//		sleep(15000);
//		cc.irAConsolaFAN();	
//		driver.switchTo().defaultContent();
//		sleep(6000);
	}
		
	//@BeforeClass (alwaysRun = true)
	public void initTelefonico() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		loginTelefonico(driver);
		sleep(15000);
		cc.irAConsolaFAN();	
		driver.switchTo().defaultContent();
		sleep(6000);
	}
	
	//@BeforeClass (alwaysRun = true)
		public void initAgente() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		loginAgente(driver);
		sleep(15000);
		cc.irAConsolaFAN();	
		driver.switchTo().defaultContent();
		sleep(6000);
	}
	
	@BeforeMethod(alwaysRun=true)
	public void setup() throws Exception {
		detalles = null;
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
	}

	//@AfterMethod(alwaysRun=true)
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
	
	@Test(groups = {"PerfilOficina", "Nominacion","E2E","Ciclo1"}, dataProvider="DatosSalesNominacionNuevoOfCom") 
	public void TS85094_CRM_Movil_REPRO_Nominatividad_Cliente_Nuevo_Presencial_DOC_OfCom(String sLinea, String sDni, String sGenero, String sNombre, String sApellido, String sSexo, String sFnac, String sEmail, String sProvincia, String sLocalidad,String sZona, String sCalle, String sNumCa, String sCP, String tDomic) { 
		imagen = "85094-Nominacion"+sDni;
		ContactSearch contact = new ContactSearch(driver);
		TestBase tb = new TestBase();
		detalles = imagen + "- Consulta de Saldo - DNI:" + sDni;
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
		sleep(15000);
		tb.cambioDeFrame(driver, By.id("DocumentTypeSearch"),0);
		contact.crearClienteNominacion("DNI", sDni, sGenero, sNombre, sApellido, sFnac, sEmail);
		contact.tipoValidacion("documento");
		File directory = new File("Dni.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		sleep(12000);
		contact.completarDomicilio(sProvincia, sLocalidad, sZona, sCalle, sNumCa, sCP, tDomic);
		sleep(32000);
		CBS_Mattu invoSer = new CBS_Mattu();
		invoSer.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
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
		invoSer.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Nominacion", "Ciclo1"},dataProvider = "DatosSalesNominacionNuevoPasaporteOfCom") 
	public void TS128436_CRM_Movil_REPRO_Nominatividad_Presencial_DOC_Pasaporte_OfCom(String sLinea, String sPasaporte, String sNombre, String sApellido, String sSexo, String sFnac, String sPermanencia, String sEmail, String sProvincia, String sLocalidad,String sZona, String sCalle, String sNumCa, String sCP, String tDomic) {
		imagen = "TS128436";
		detalles = null;
		detalles = imagen + " -Nominacion: " + sPasaporte + " -Linea: "+sLinea;
		boolean nominacion = false;
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("PhoneNumber")));
		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(5000);
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
		sleep(5000);
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact2("Pasaporte", sPasaporte, "Masculino");
		//contact.Llenar_Contacto(sNombre, sApellido, sFnac);
		driver.findElement(By.id("PermanencyDueDate")).sendKeys(sPermanencia);
		driver.findElement(By.id("Contact_nextBtn")).click();
		sleep(10000);
		contact.tipoValidacion("documento");
		File directory = new File("Dni.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		sleep(12000);
		sb.Crear_DomicilioLegal( sProvincia, sLocalidad, sZona,sCalle,sNumCa,sCP,tDomic);
		sleep(38000);
		directory = new File("form.pdf");
		driver.findElement(By.id("UploadSignedForm")).sendKeys(new File(directory.getAbsolutePath()).toString());
		sleep(2000);
		driver.findElement(By.id("PDFForm_nextBtn")).click();
		sleep(30000);
		CBS_Mattu invoSer = new CBS_Mattu();
		invoSer.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
		for (WebElement x : driver.findElement(By.id("NominacionExitosa")).findElements(By.tagName("p"))) {
			if (x.getText().toLowerCase().contains("nominaci\u00f3n exitosa!"))
				nominacion = true;
		}
		Assert.assertTrue(nominacion);
		driver.findElement(By.id("FinishProcess_nextBtn")).click();
		sleep(3000);
		invoSer.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Nominacion", "Ciclo1"}, dataProvider = "DatosNoNominaNuevoEdadOfCom")
	public void TS130071_CRM_Movil_REPRO_No_Nominatividad_Presencial_DOC_Edad_OfCom(String sLinea, String sDni, String sSexo, String sFnac) {
		imagen = "TS130071";
		detalles = imagen + "No nominatividad- dni: "+sDni+" -Linea: "+sLinea;
		cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("PhoneNumber")));
		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
		driver.findElement(By.id("SearchClientsDummy")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(".slds-button.slds-button--icon.slds-m-right--x-small.ng-scope")));
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
		contact.searchContact2("DNI", sDni, sSexo);
		driver.findElement(By.id("Birthdate")).sendKeys(sFnac);
		sleep(3000);
		WebElement error = driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope"));
		Assert.assertTrue(error.getText().toLowerCase().contains("fecha de nacimiento inv\u00e1lida"));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Nominacion", "Ciclo1"}, dataProvider = "DatosSalesNominacionExistenteOfCom")
	public void TS85097_CRM_Movil_REPRO_Nominatividad_Cliente_Existente_Presencial_DOC_OfCom(String sLinea, String sDni) {
		imagen = "TS85097";
		detalles = null;
		detalles = imagen + " -Nominacion: " + sDni+"- Linea: "+sLinea;
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
		File directory = new File("Dni.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		sleep(30000);
		for (WebElement x : driver.findElement(By.id("NominacionExitosa")).findElements(By.tagName("p"))) {
			if (x.getText().toLowerCase().contains("nominaci\u00f3n exitosa!"))
				nominacion = true;
		}
		Assert.assertTrue(nominacion);
		driver.findElement(By.id("FinishProcess_nextBtn")).click();
		sleep(3000);
	}
	
	@Test(groups = {"Sales", "Nominacion","E2E","Ciclo1"}, dataProvider="DatosSalesNominacionPyRNuevoOfCom") 
	public void TS85099_CRM_Movil_REPRO_Nominatividad_Cliente_Existente_Presencial_Preguntas_Y_Respuestas_Ofcom(String sLinea, String sDni, String sNombre, String sApellido, String sSexo, String sFnac, String sEmail, String sProvincia, String sLocalidad, String sCalle, String sNumCa, String sCP) { 
		imagen = "TS85099-Nominacion"+sDni;
		detalles = null;
		detalles = imagen +"-Linea: "+sLinea;
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		SalesBase SB = new SalesBase(driver);
		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(10000);
		WebElement cli = driver.findElement(By.id("tab-scoped-1"));
		cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).click();
		sleep(3000);
		List<WebElement> Lineas = driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement UnaL: Lineas) {
			if(UnaL.getText().toLowerCase().contains("plan con tarjeta repro")||UnaL.getText().toLowerCase().contains("plan prepago nacional")) {
				UnaL.findElements(By.tagName("td")).get(6).findElement(By.tagName("svg")).click();
				System.out.println("Linea Encontrada");
				break;
			}
		}
		sleep(13000);
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact2("DNI", sDni, sSexo);
		//contact.Llenar_Contacto(sNombre, sApellido, sFnac);
		try {contact.ingresarMail(sEmail, "si");}catch (org.openqa.selenium.ElementNotVisibleException ex1) {}
		contact.tipoValidacion("preguntas y respuestas");
		sleep(5000);
		CustomerCare cCC = new CustomerCare(driver);
		cCC.obligarclick(driver.findElement(By.id("QAContactData_nextBtn")));
		sleep(5000);
		BasePage bp = new BasePage(driver);
		bp.setSimpleDropdown(driver.findElement(By.id("ImpositiveCondition")), "IVA Consumidor Final");
		//SB.Crear_DomicilioLegal(sProvincia, sLocalidad, sCalle, "", sNumCa, "", "", sCP);
		sleep(38000);
		CBS_Mattu invoSer = new CBS_Mattu();
		invoSer.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
		List <WebElement> element = driver.findElement(By.id("NominacionExitosa")).findElements(By.tagName("p"));
		System.out.println("cont="+element.get(0).getText());
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
		invoSer.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Nominacion", "Ciclo1"}, dataProvider = "DatosNominacionExistente5Lineas")
	public void TS85104_CRM_Movil_REPRO_No_Nominatividad_Numero_Limite_De_Lineas_Cliente_Existente_Presencial(String sLinea, String sDni) {
		imagen = "TS85104";
		detalles = null;
		detalles = imagen + " -Nominacion: " + sDni+"- Linea: "+sLinea;
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
		Assert.assertFalse(driver.findElement(By.id("MethodSelection_nextBtn")).isDisplayed());
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Nominacion", "Ciclo1"}, dataProvider = "DatosNoNominacionExistenteFraudeOfcom")
	public void TS85105_CRM_Movil_REPRO_No_Nominatividad_Por_Fraude_Cliente_Existente_Presencial(String sLinea, String sDni) {
		imagen = "TS85105";
		detalles = null;
		detalles = imagen + " -Nominacion: " + sDni+"- Linea: "+sLinea;
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
		Assert.assertTrue(driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText().toLowerCase().contains("el dni figura en lista de fraude"));	
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"GestionesPerfilTelefonico", "Nominacion", "Ciclo1"}, dataProvider="DatosNoNominacionNuevoTelefonicoPasaporte")
	public void TS128437_CRM_Movil_REPRO_No_Nominatividad_Telefonico_Pasaporte(String sLinea, String sPasaporte, String sNombre, String sApellido, String sSexo, String sFnac, String sEmail, String sFperm) {
		imagen = "TS128437";
		detalles = null;
		detalles = imagen+"No nominacion Agente- DNI: "+sPasaporte+"-Linea: "+sLinea;
		sleep(2000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(5000);
		waitForClickeable(driver,By.cssSelector(".slds-button.slds-button--icon.slds-m-right--x-small.ng-scope"));
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
		sleep(10000);
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact2("Pasaporte", sPasaporte, sSexo);
		//sleep(2000);
		//contact.Llenar_Contacto(sNombre, sApellido, sFnac);
		driver.findElement(By.id("PermanencyDueDate")).sendKeys(sFperm);
		driver.findElement(By.id("Contact_nextBtn")).click();
		//sleep(10000);
		waitFor(driver,By.className("ng-binding"));
		Assert.assertTrue(driver.findElement(By.className("ng-binding")).getText().equalsIgnoreCase("No se puede continuar con la operaci\u00f3n. Por favor, dirijase a una oficina comercial."));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "Nominacion", "Ciclo1"}, dataProvider="DatosNoNominacionExistenteTelefonico")
	public void TS134494_CRM_Movil_REPRO_No_Nominatividad_No_Valida_Identidad_Cliente_Existente_Telefonico_Preguntas_Y_Respuestas(String sLinea, String sDni, String sSexo) {
		imagen = "TS134494";
		detalles = null;
		detalles = imagen+"No nominacion Agente- DNI: "+sDni+"-Linea: "+sLinea;
		sleep(2000);
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
		sleep(10000);
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact2("DNI", sDni, sSexo);
		driver.findElement(By.id("Contact_nextBtn")).click();
		contact.tipoValidacion("preguntas y respuestas");
		sleep(8000);
		CustomerCare cCC = new CustomerCare(driver);
		cCC.obligarclick(driver.findElement(By.id("QAContactData_nextBtn"))); 
		sleep(5000);
		List<WebElement> valdni = driver.findElements(By.className("slds-radio__label"));
		for (WebElement x : valdni) {
			System.out.println(x.getText());
			if (x.getText().toLowerCase().contains("ninguno de los anteriores")) {
				x.click();
			}
		}
		cCC.obligarclick(driver.findElement(By.id("QAQuestions_nextBtn")));      
		sleep(5000);
		List<WebElement> errores = driver.findElements(By.cssSelector(".message.description.ng-binding.ng-scope")); 
		boolean error = false;
		for (WebElement UnE: errores) {
			if (UnE.getText().toLowerCase().contains("no superada")) {
				error = true;
			}
		}		
		Assert.assertTrue(error);
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "Nominacion", "Ciclo1"}, dataProvider="DatosNoNominacionNuevoTelefonico")
	public void TS85111_CRM_Movil_REPRO_No_Nominatividad_No_Valida_Identidad_Cliente_Nuevo_Telefonico_Preguntas_y_Respuestas(String sLinea, String sDni, String sNombre, String sApellido, String sSexo, String sFnac, String sEmail) {
		imagen = "TS85111";
		detalles = null;
		detalles = imagen+"No nominacion Agente- DNI: "+sDni+"-Linea: "+sLinea;
		sleep(2000);
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
		sleep(10000);
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact2("DNI", sDni, sSexo);
		sleep(2000);
		//contact.Llenar_Contacto(sNombre, sApellido, sFnac);
		try {contact.ingresarMail(sEmail, "si");}catch (org.openqa.selenium.ElementNotVisibleException ex1) {}
		contact.tipoValidacion("preguntas y respuestas");
		sleep(8000);
		CustomerCare cCC = new CustomerCare(driver);
		cCC.obligarclick(driver.findElement(By.id("QAContactData_nextBtn"))); 
		sleep(5000);
		List<WebElement> valdni = driver.findElements(By.className("slds-radio__label"));
		for (WebElement x : valdni) {
			System.out.println(x.getText());
			if (x.getText().toLowerCase().contains("ninguno de los anteriores")) {
				x.click();
			}
		}
		cCC.obligarclick(driver.findElement(By.id("QAQuestions_nextBtn")));      
		sleep(5000);
		List<WebElement> errores = driver.findElements(By.cssSelector(".message.description.ng-binding.ng-scope")); 
		boolean error = false;
		for (WebElement UnE: errores) {
			if (UnE.getText().toLowerCase().contains("no superada")) {
				error = true;
			}
		}		
		Assert.assertTrue(error);
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "Nominacion", "Ciclo1"}, dataProvider = "DatosNoNominacionNuevoFraudeTelef")
	public void TS85112_CRM_Movil_REPRO_No_Nominatividad_Por_Fraude_Cliente_Nuevo_Telefonico(String sLinea, String sDni) {
		imagen = "TS85112";
		detalles = null;
		detalles = imagen + " -Nominacion: " + sDni+"- Linea: "+sLinea;
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
		Assert.assertTrue(driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText().toLowerCase().contains("el dni figura en lista de fraude"));	
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "Nominacion", "Ciclo1"}, dataProvider = "DatosNominacionExistente5Lineas")
	public void TS85113_CRM_Movil_REPRO_No_Nominatividad_Numero_Limite_De_Lineas_Cliente_Existente_Telefonico(String sLinea, String sDni) {
		imagen = "TS85113";
		detalles = null;
		detalles = imagen + " -Nominacion: " + sDni+"- Linea: "+sLinea;
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
		Assert.assertFalse(driver.findElement(By.id("MethodSelection_nextBtn")).isDisplayed());
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "Nominacion", "Ciclo1"}, dataProvider = "DatosNoNominacionExistenteFraudeOfcom")
	public void TS85114_CRM_Movil_REPRO_No_Nominatividad_Por_Fraude_Cliente_Existente_Presencial(String sLinea, String sDni) {
		imagen = "TS85114";
		detalles = null;
		detalles = imagen + " -Nominacion: " + sDni+"- Linea: "+sLinea;
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
		Assert.assertTrue(driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText().toLowerCase().contains("el dni figura en lista de fraude"));		
	}
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test(groups = {"GestionesPerfilAgente","Sales", "PreparacionNominacion","E2E","Ciclo1"}, dataProvider="DatosSalesNominacionNuevoAgente") 
	public void TS_CRM_Nominacion_Argentino_Agente(String sLinea, String sDni, String sNombre, String sApellido, String sSexo, String sFnac, String sEmail, String sProvincia, String sLocalidad, String sCalle, String sNumCa, String sCP) { 
		imagen = "TS_CRM_Nominacion_Argentino_Agente"+sDni;
		File directory = new File("Dni.jpg");
		detalles = null;
		detalles = imagen+"-Linea: "+sLinea;
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		SalesBase SB = new SalesBase(driver);
		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(10000);
		WebElement cli = driver.findElement(By.id("tab-scoped-1"));
		cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).click();
		sleep(3000);
		List<WebElement> Lineas = driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement UnaL: Lineas) {
			if(UnaL.getText().toLowerCase().contains("plan con tarjeta")||UnaL.getText().toLowerCase().contains("plan prepago nacional")) {
				UnaL.findElements(By.tagName("td")).get(6).findElement(By.tagName("svg")).click();
				System.out.println("Linea Encontrada");
				break;
			}
		}
		sleep(13000);
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact2("DNI", sDni, sSexo);
	//	contact.Llenar_Contacto(sNombre, sApellido, sFnac);
		try {contact.ingresarMail(sEmail, "si");}catch (org.openqa.selenium.ElementNotVisibleException ex1) {}
		contact.tipoValidacion("documento");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		BasePage bp = new BasePage(driver);
		bp.setSimpleDropdown(driver.findElement(By.id("ImpositiveCondition")), "IVA Consumidor Final");
		//SB.Crear_DomicilioLegal(sProvincia, sLocalidad, sCalle, "", sNumCa, "", "", sCP);
		sleep(38000);
		CBS_Mattu invoSer = new CBS_Mattu();
		invoSer.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
		List <WebElement> element = driver.findElement(By.id("NominacionExitosa")).findElements(By.tagName("p"));
		System.out.println("cont="+element.get(0).getText());
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
		invoSer.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
	}
	
	@Test (groups = {"GestionesPerfilAgente", "Nominacion", "Ciclo1"}, dataProvider="DatosNoNominacionExistenteAgente")
	public void TS134492_CRM_Movil_REPRO_No_Nominatividad_No_Valida_Identidad_Cliente_Existente_Presencial_DOC(String sLinea, String sDni, String sSexo, String sFnac, String sEmail) {
		imagen = "TS134492";
		detalles = null;
		detalles = imagen+"No nominacion Agente- DNI: "+sDni+"-Linea: "+sLinea;
		sleep(2000);
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
		sleep(10000);
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact2("DNI", sDni, "Masculino");
		waitForClickeable(driver,By.id("Contact_nextBtn"));
		driver.findElement(By.id("Contact_nextBtn")).click();
		//sleep(10000);
		contact.tipoValidacion("documento");
		File directory = new File("DniMal.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "no");
		List<WebElement> errores = driver.findElements(By.cssSelector(".message.description.ng-binding.ng-scope")); 
		boolean error = false;
		for (WebElement UnE: errores) {
			if (UnE.getText().toLowerCase().contains("identidad no superada")) {
				error = true;
			}
		}
		Assert.assertTrue(error);
	}
	
	@Test (groups = {"GestionesPerfilAgente","Sales", "PreparacionNominacion","E2E","Ciclo1"}, dataProvider="DatosNoNominacionExistenteTelefonico") 
	public void TS134493_CRM_Movil_REPRO_No_Nominatividad_No_Valida_Identidad_Cliente_Existente_Presencial_Preguntas_y_Respuestas(String sLinea, String sDni, String sSexo) {
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
		driver.findElement(By.id("SearchClientsDummy")).click();		
		sleep(10000);
		driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).click();
		sleep(3000);
		for (WebElement x: driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"))) {
			if (x.getText().toLowerCase().contains("plan con tarjeta") || x.getText().toLowerCase().contains("plan prepago nacional")) {
				x.findElements(By.tagName("td")).get(6).findElement(By.tagName("svg")).click();
				break;
			}
		}
		sleep(10000);
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact2("DNI", sDni, sSexo);
		sleep(5000);
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("Contact_nextBtn")).getLocation().y+" )");
		driver.findElement(By.id("Contact_nextBtn")).click();
		contact.tipoValidacion("preguntas y respuestas");
		sleep(8000);
		CustomerCare cCC = new CustomerCare(driver);
		cCC.obligarclick(driver.findElement(By.id("QAContactData_nextBtn"))); 
		sleep(5000);
		List<WebElement> valdni = driver.findElements(By.className("slds-radio__label"));
		for (WebElement x : valdni) {
			System.out.println(x.getText());
			if (x.getText().toLowerCase().contains("ninguno de los anteriores")) {
				x.click();
			}
		}
		cCC.obligarclick(driver.findElement(By.id("QAQuestions_nextBtn")));      
		sleep(5000);
		List<WebElement> errores = driver.findElements(By.cssSelector(".message.description.ng-binding.ng-scope")); 
		boolean error = false;
		for (WebElement UnE: errores) {
			if (UnE.getText().toLowerCase().contains("no superada")) {
				error = true;
			}
		}
		Assert.assertTrue(error);	
	}
	
	@Test (groups = {"GestionesPerfilAgente", "Nominacion", "Ciclo1"}, dataProvider="DatosNoNominacionNuevoAgente")
	public void TS85100_CRM_Movil_REPRO_No_Nominatividad_No_Valida_Identidad_Cliente_nuevo_Presencial_DOC_Agente(String sLinea, String sDni, String sNombre, String sApellido, String sSexo, String sFnac, String sEmail) {
		imagen = "TS85100";
		detalles = null;
		detalles = imagen+"No nominacion Agente- DNI: "+sDni+"-Linea: "+sLinea;
		sleep(2000);
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
		sleep(10000);
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact2("DNI", sDni, sSexo);
		sleep(2000);
		//contact.Llenar_Contacto(sNombre, sApellido, sFnac);
		try {contact.ingresarMail(sEmail, "si");}catch (org.openqa.selenium.ElementNotVisibleException ex1) {}
		contact.tipoValidacion("documento");
		File directory = new File("DniMal.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "no");
		List<WebElement> errores = driver.findElements(By.cssSelector(".message.description.ng-binding.ng-scope")); 
		boolean error = false;
		for (WebElement UnE: errores) {
			if (UnE.getText().toLowerCase().contains("identidad no superada")) {
				error = true;
			}
		}
		Assert.assertTrue(error);
	}
	
}