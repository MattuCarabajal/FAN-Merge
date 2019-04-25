package Funcionalidades;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.Accounts;
import Pages.CustomerCare;
import Pages.SalesBase;
import Pages.setConexion;
import Tests.TestBase;

public class GestionDeClientes extends TestBase {

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	@BeforeClass (alwaysRun = true)
	public void init() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
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
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"})
	public void TS135495_CRM_Movil_REPRO_Busqueda_Tipo_de_documento_DNI() {
		imagen = "TS135495";
		detalles = null;
		detalles = imagen + " - Gestion de clientes";
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		selectByText(driver.findElement(By.id("SearchClientDocumentType")), "DNI");
		sleep(10000);
		boolean a = false;
		if(driver.findElement(By.id("SearchClientDocumentType")).getText().toLowerCase().contains("dni")) {
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test(groups = { "Sales", "GestionDeClientes", "Ciclo1" }, dataProvider = "validaDocumentacion")
	public void TS135496_CRM_Movil_REPRO_Busqueda_DNI_Numero_de_Documento(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135496";
		detalles = null;
		detalles = imagen + "- Gestion de clientes - DNI:" + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		sleep(10000);
		List<WebElement> datos = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.cssSelector(".slds-truncate.ng-binding"));
		Assert.assertTrue(datos.get(3).getText().equals(sDNI));
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "invalidaDocumentacion")
	public void TS135497_CRM_Movil_REPRO_Busqueda_DNI_Numero_de_Documento_no_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail){
		imagen = "TS135497";
		detalles = null;
		detalles = imagen + "-Gestion de clientes - DNI:  "+ sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		sleep(10000);
		boolean a = false;
		for(WebElement x : driver.findElements(By.className("slds-form-element__control"))){
			if(x.getText().toLowerCase().equals("no hay ning\u00fan cliente con este tipo y n\u00famero de documento. busc\u00e1 con otro dato o cre\u00e1 un nuevo cliente."))
				a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"})
	public void TS135498_CRM_Movil_REPRO_Busqueda_Tipo_de_documento_Libreta_de_enrolamiento() {
		imagen = "TS135498";
		detalles = null;
		detalles = imagen + " - Gestion de clientes";
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		selectByText(driver.findElement(By.id("SearchClientDocumentType")), "Libreta de Enrolamiento");
		sleep(10000);
		boolean a = false;
		if(driver.findElement(By.id("SearchClientDocumentType")).getText().toLowerCase().contains("libreta de enrolamiento"))
			a = true;
		Assert.assertTrue(a);
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "validaDocumentacion")
	public void TS135499_CRM_Movil_REPRO_Busqueda_Libreta_de_enrolamiento_Numero_de_Documento(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail){
		imagen = "TS135499";
		detalles = null;
		detalles = imagen + "- Gestion de clientes - Libreta de enrolamiento: " + sLibreta;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("Libreta de Enrolamiento", sLibreta);
		sleep(10000);
		List<WebElement> activo = driver.findElement(By.className("slds-tabs--scoped__nav")).findElements(By.tagName("li"));
		Assert.assertTrue(activo.get(0).findElement(By.tagName("a")).getText().equals("Clientes Activos"));
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "invalidaDocumentacion")
	public void TS135500_CRM_Movil_REPRO_Busqueda_Libreta_dE_enrolamiento_Numero_de_Documento_no_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135500";
		detalles = null;
		detalles = imagen + "- Gestion de clientes - Libreta de enrolamiento: " + sLibreta;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("Libreta de Enrolamiento", sLibreta);
		boolean libreta = false;
		for (WebElement x : driver.findElements(By.className("slds-form-element__control"))){
			if (x.getText().toLowerCase().contains("no hay ning\u00fan cliente con este tipo y n\u00famero de documento."))
				libreta = true;
		}
		Assert.assertTrue(libreta);
	}
	
	@Test(groups = { "Sales", "GestionDeClientes", "Ciclo1" }, dataProvider = "validaDocumentacion")
	public void TS135501_CRM_Movil_REPRO_Busqueda_Nombre(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135501";
		detalles = null;
		detalles = imagen + " - Gestion de clientes - Nombre: " + sNombre;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada(sNombre, "", "", "", "");
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(10000);
		List<WebElement> filasDeResultados = driver.findElements(By.cssSelector("[class='slds-tabs--scoped ng-scope'] [class='slds-tabs--scoped__content'] div table tbody [class='searchClient-body slds-hint-parent ng-scope']"));
		boolean assertCondition = true;
		for (WebElement fila : filasDeResultados) {
			List<WebElement> elements  = fila.findElements(By.cssSelector(".slds-truncate.ng-binding"));
			if (!elements.get(0).getText().substring(0,4).equals("Juan")) {
				assertCondition = false;
			}
		}
		Assert.assertTrue(assertCondition);
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "invalidaDocumentacion")
	public void TS135502_CRM_Movil_REPRO_Busqueda_Nombre_No_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135502";
		detalles = null;
		detalles = imagen + " - Gestion de clientes - Nombre: " + sNombre;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada(sNombre,"","","","");
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(10000);
		boolean a = false;
		for(WebElement x : driver.findElements(By.className("slds-form-element__control"))){
			if(x.getText().toLowerCase().equals("no hay ning\u00fan cliente con este tipo y n\u00famero de documento. busc\u00e1 con otro dato o cre\u00e1 un nuevo cliente."))
				a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test(groups = { "Sales", "GestionDeClientes", "Ciclo1" }, dataProvider = "validaDocumentacion")
	public void TS135503_CRM_Movil_REPRO_Busqueda_Apellido(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135503";
		detalles = null;
		detalles = imagen + "- Gestion de Clientes - DNI: " + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada("", sApellido, "", "", "");
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(10000);	
		List<WebElement> datos = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.cssSelector(".slds-truncate.ng-binding"));
		Assert.assertTrue(datos.get(3).getText().equals(sDNI));
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "invalidaDocumentacion")
	public void TS135504_CRM_Movil_REPRO_Busqueda_Apellido_No_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135504";
		detalles = null;
		detalles = imagen + "- Gestion de clinetes - Apellido: " + sApellido;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada("",sApellido,"","","");
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(10000);
		boolean a = false;
		for(WebElement x : driver.findElements(By.className("slds-form-element__control"))){
			if(x.getText().toLowerCase().equals("no hay ning\u00fan cliente con este tipo y n\u00famero de documento. busc\u00e1 con otro dato o cre\u00e1 un nuevo cliente."))
				a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test(groups = { "Sales", "GestionDeClientes", "Ciclo1" }, dataProvider = "validaDocumentacion")
	public void TS135505_CRM_Movil_REPRO_Busqueda_Razon_Social(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135505";
		detalles = null;
		detalles = imagen + " - Gestion de clientes - Razon social: " + sRazon;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada("", "", sRazon, "", "");
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(10000);
		List<WebElement> datos = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.cssSelector(".slds-truncate.ng-binding"));
		Assert.assertTrue(datos.get(3).getText().equals(sDNI));		
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "invalidaDocumentacion")
	public void TS135506_CRM_Movil_REPRO_Busqueda_Razon_social_No_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135506";
		detalles = null;
		detalles = imagen+" - Gestion de clientes - Razon social: "+sRazon;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada("","",sRazon,"","");
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(10000);
		List<WebElement> vacio = driver.findElement(By.className("slds-tabs--scoped__nav")).findElements(By.tagName("li"));
		Assert.assertTrue(vacio.get(0).findElement(By.tagName("a")).getText().isEmpty());
	}
	
	@Test(groups = { "Sales", "GestionDeClientes", "Ciclo1" }, dataProvider = "validaDocumentacion")
	public void TS135507_CRM_Movil_REPRO_Busqueda_Email(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135507";
		detalles = null;
		detalles = imagen + " - Gestion de clientes - Email: " + sEmail;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada("", "", "", "", sEmail);
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(10000);
		List<WebElement> datos = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.cssSelector(".slds-truncate.ng-binding"));
		Assert.assertTrue(datos.get(3).getText().equals(sDNI));
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "invalidaDocumentacion")
	public void TS135508_CRM_Movil_REPRO_Busqueda_Email_No_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135508";
		detalles = null;
		detalles = imagen + " - Gestion de clientes - Email: " + sEmail;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada("","","","",sEmail);
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(10000);
		boolean a = false;
		for(WebElement x : driver.findElements(By.className("slds-form-element__control"))){
			if(x.getText().toLowerCase().equals("no hay ning\u00fan cliente con este tipo y n\u00famero de documento. busc\u00e1 con otro dato o cre\u00e1 un nuevo cliente."))
				a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test(groups = { "Sales", "GestionDeClientes", "Ciclo1" }, dataProvider = "validaDocumentacion")
	public void TS135509_CRM_Movil_REPRO_Busqueda_Numero_de_Cuenta(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135509";
		detalles = null;
		detalles = imagen + "- Gestion de Clientes - DNI: " + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada("", "", "", sNumeroDeCuenta, "");
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(10000);
		List<WebElement> datos = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.cssSelector(".slds-truncate.ng-binding"));
		Assert.assertTrue(datos.get(3).getText().equals(sDNI));
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "invalidaDocumentacion")
	public void TS135510_CRM_Movil_REPRO_Busqueda_Numero_de_Cuenta_No_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135510";
		detalles = null;
		detalles = imagen + " - Gestion de clientes - Numero de cuenta: " + sNumeroDeCuenta;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada("", "", "", sNumeroDeCuenta, "");
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(10000);
		List<WebElement> vacio = driver.findElement(By.className("slds-tabs--scoped__nav")).findElements(By.tagName("li"));
		Assert.assertTrue(vacio.get(0).findElement(By.tagName("a")).getText().isEmpty());
	}
}