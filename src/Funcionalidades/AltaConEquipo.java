package Funcionalidades;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.*;

import Pages.Accounts;
import Pages.CBS;
import Pages.ContactSearch;
import Pages.CustomerCare;
import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class AltaConEquipo extends TestBase {

	private WebDriver driver;
	private GestionDeClientes_Fw ges;
	private CBS cbs;
	private CBS_Mattu cbsm;
	private LoginFw log;
	private ContactSearch contact;
	private SalesBase sb;
	private CustomerCare cc;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	@BeforeClass (alwaysRun = true)
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		contact = new ContactSearch(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
	}
	
	//@BeforeClass (alwaysRun = true)
	public void initAgente() throws IOException, AWTException {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		contact = new ContactSearch(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
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
	
	@Test(groups={"Sales", "AltaLineaDatos","E2E"}, priority=1, dataProvider="DatosAltaEquipoExiste") //========  31- 8 Aparece el paso de carga de datos de la cuenta
	public void TS125214_CRM_Movil_PRE_Alta_Linea_con_Equipo_Cliente_existente_Presencial_OFCOM(String sDni, String sNombre, String sApellido, String sPlan, String sEquipo) throws IOException, AWTException {
		imagen = "TS125214";
		sb.BuscarCuenta("DNI", "32328505");
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".slds-button.slds-button.slds-button--icon"), 0));
		contact.seleccionarCatalogo();
		contact.elegirPlan("plan con tarjeta");
		driver.findElement(By.cssSelector(".slds-input.input-icon.ng-valid.ng-not-empty.ng-dirty.ng-valid-parse.ng-touched")).clear();
		driver.findElement(By.cssSelector(".slds-input.input-icon.ng-valid.ng-dirty.ng-valid-parse.ng-touched.ng-empty")).sendKeys("moto z");
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slds-button.slds-button--neutral.add-button")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--neutral.add-button")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(".slds-button.slds-button--brand.ta-button-brand")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.ta-button-brand")).click();
		
		
		
		
		
		
		
//		CustomerCare cc = new CustomerCare(driver);
//		SalesBase sb = new SalesBase(driver);
//		sleep(5000);
//		sb.BuscarCuenta("DNI", sDni);
//		sleep(5000);
//		List<WebElement> btns = driver.findElements(By.cssSelector(".slds-button.slds-button.slds-button--icon"));
//		for(WebElement e: btns){
//			if(e.getText().toLowerCase().equals("catalogo")){ 
//				e.click();
//				break;
//			}
//		}
//		sleep(25000);
//		sb.elegirplan(sPlan);
//		sleep(12000);
//		driver.findElement(By.cssSelector(".slds-input.ng-valid.ng-not-empty.ng-dirty.ng-valid-parse.ng-touched")).clear();
//		sleep(3000);
//		driver.findElement(By.cssSelector(".slds-input.ng-valid.ng-dirty.ng-valid-parse.ng-touched.ng-empty")).sendKeys(sEquipo);
//		sleep(13000);
//		List<WebElement> acept = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.add-button"));
//			for(WebElement a : acept){
//				System.out.println(a.getText());
//				if(a.getText().equals("Agregar")){
//					cc.obligarclick(a);
//					break;
//				}
//			}
//		sleep(5000);	
//		sb.continuar();
//		sleep(25000);
//		cc.obligarclick(driver.findElement(By.id("LineAssignment_nextBtn")));
//		sleep(15000);
//		cc.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
//		sleep(13000);
//		
//		List<WebElement> medpag = driver.findElements(By.cssSelector(".slds-radio.ng-scope"));
//			for(WebElement m :medpag){
//				if(m.getText().equals("Efectivo")){
//					cc.obligarclick(m.findElement(By.cssSelector(".slds-radio--faux")));
//				}
//			}
//		cc.obligarclick(driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")));
//		sleep(20000);
//		ContactSearch contact = new ContactSearch(driver);
//		contact.tipoValidacion("documento");
//		sleep(8000);
//		File directory = new File("Dni.jpg");
//		driver.findElement(By.id("FileDocumentImage")).sendKeys(new File(directory.getAbsolutePath()).toString());
//		sleep(3000);
//		cc.obligarclick(driver.findElement(By.id("DocumentMethod_nextBtn")));
//		sleep(10000);
//		try {
//			driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")).get(1).click();
//			sleep(15000);
//		}catch(Exception ex1) {}
//		String orden = driver.findElement(By.className("top-data")).findElement(By.className("ng-binding")).getText();
//		String NCuenta = driver.findElements(By.className("top-data")).get(1).findElements(By.className("ng-binding")).get(3).getText();
//		String Linea = driver.findElement(By.cssSelector(".top-data.ng-scope")).findElements(By.className("ng-binding")).get(1).getText();
//		System.out.println("Orden "+orden);
//		System.out.println("cuenta "+NCuenta);
//		System.out.println("Linea "+Linea);
//		orden = orden.substring(orden.length()-8);
//		NCuenta = NCuenta.substring(NCuenta.length()-16);
//		Linea = Linea.substring(Linea.length()-10);
//		cc.obligarclick(driver.findElement(By.id("OrderSumary_nextBtn")));
//		sleep(20000);
//		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
//		sOrders.add("Orden:"+orden+"-DNI:"+sDni+"-Cuenta:"+NCuenta+"-Linea"+Linea);
//		sleep(15000);
//		String DOrden = cc.obtenerMontoyTNparaAlta(driver, orden);
//		System.out.println(DOrden);
//		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
//		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
//		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
//	
//		CBS_Mattu invoSer = new CBS_Mattu();
//		Assert.assertTrue(invoSer.PagoEnCaja("1006", NCuenta, "1001", DOrden.split("-")[1], DOrden.split("-")[0],driver));
//		sleep(5000);
//		driver.navigate().refresh();
//		sleep(10000);
//		CambiarPerfil("logisticayentrega",driver);
//		sb.completarLogistica(orden, driver);
//		//CambiarPerfil("entrega",driver);
//		sb.completarEntrega(orden, driver);
//		CambiarPerfil("ofcom",driver);
//		try {
//			cc.cajonDeAplicaciones("Consola FAN");
//		} catch(Exception e) {
//			waitForClickeable(driver,By.id("tabBar"));
//			driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
//			sleep(6000);
//		}
//		driver.switchTo().defaultContent();
//		sleep(6000);
//		cc.obtenerMontoyTNparaAlta(driver, orden);
//		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
//		tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
//		datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
//		invoSer.ValidarInfoCuenta(Linea, sNombre,sApellido, sPlan);
//		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
	}
	
	@Test(groups={"Sales", "AltaLineaDatos","E2E"}, priority=1, dataProvider="AltaEquipoExisteSPU") //verificado
	public void TS125211_CRM_Movil_PRE_Alta_Linea_Con_Equipo_Cliente_Existente_Store_PickUp_OFCOM(String sDni, String sNombre, String sApellido, String sPlan, String sEquipo, String sStoreProv, String sStoreLoc, String sPunto) throws IOException, AWTException {
		imagen = "TS125211";
		CustomerCare cc = new CustomerCare(driver);
		SalesBase sb = new SalesBase(driver);
		sleep(5000);
		sb.BuscarCuenta("DNI", sDni);
		sleep(5000);
		List<WebElement> btns = driver.findElements(By.cssSelector(".slds-button.slds-button.slds-button--icon"));
		for(WebElement e: btns){
			if(e.getText().toLowerCase().equals("catalogo")){ 
				e.click();
				break;
			}
		}
		sleep(25000);
		sb.ResolverEntrega(driver, "Store Pick Up",sStoreProv,sStoreLoc);
		sleep(7000);
		driver.switchTo().defaultContent();
		Accounts accountPage = new Accounts(driver);
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		boolean enc = false;
		int index = 0;
		for(WebElement frame : frames) {
			try {
				System.out.println("aca");
				driver.switchTo().frame(frame);

				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).getText(); //each element is in the same iframe.
				//System.out.println(index); //prints the used index.

				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).isDisplayed(); //each element is in the same iframe.
				//System.out.println(index); //prints the used index.

				driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
				enc = true;
				break;
			}catch(NoSuchElementException noSuchElemExcept) {
				index++;
				driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
			}
		}
		if(enc == false)
			index = -1;
		try {
				driver.switchTo().frame(frames.get(index));
		}catch(ArrayIndexOutOfBoundsException iobExcept) {System.out.println("Elemento no encontrado en ningun frame 2.");
			
		}
		sleep(14000);
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".slds-input.ng-pristine.ng-untouched.ng-valid.ng-empty")));
		sb.elegirplan(sPlan);
		sleep(12000);
		driver.findElement(By.cssSelector(".slds-input.ng-valid.ng-not-empty.ng-dirty.ng-valid-parse.ng-touched")).clear();
		sleep(3000);
		driver.findElement(By.cssSelector(".slds-input.ng-valid.ng-dirty.ng-valid-parse.ng-touched.ng-empty")).sendKeys(sEquipo);
		sleep(13000);
		List<WebElement> acept = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.add-button"));
		for(WebElement a : acept){
			System.out.println(a.getText());
			if(a.getText().equals("Agregar")){
				cc.obligarclick(a);
				break;
			}
		}
		sleep(5000);	
		sb.continuar();
		sleep(24000);
		cc.obligarclick(driver.findElement(By.id("DeliveryMethodConfiguration_nextBtn")));
		sleep(15000);
		cc.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		sleep(20000);
		List<WebElement> medpag = driver.findElements(By.cssSelector(".slds-radio.ng-scope"));
		for(WebElement m :medpag){
			if(m.getText().equals("Efectivo")){
				cc.obligarclick(m.findElement(By.cssSelector(".slds-radio--faux")));
			}
		}
		cc.obligarclick(driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")));
		sleep(20000);
		ContactSearch contact = new ContactSearch(driver);
		contact.tipoValidacion("documento");
		sleep(8000);
		File directory = new File("Dni.jpg");
		driver.findElement(By.id("FileDocumentImage")).sendKeys(new File(directory.getAbsolutePath()).toString());
		sleep(3000);
		cc.obligarclick(driver.findElement(By.id("DocumentMethod_nextBtn")));
		sleep(10000);
		
		try {
			driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")).get(1).click();
			sleep(10000);
		}catch(Exception ex1) {}
		String orden = driver.findElement(By.className("top-data")).findElement(By.className("ng-binding")).getText();
		String NCuenta = driver.findElements(By.className("top-data")).get(1).findElements(By.className("ng-binding")).get(3).getText();
		String Linea = driver.findElement(By.cssSelector(".top-data.ng-scope")).findElements(By.className("ng-binding")).get(1).getText();
		orden = orden.substring(orden.length()-8);
		NCuenta = NCuenta.substring(NCuenta.length()-16);
		Linea = Linea.substring(Linea.length()-10);
		//00072466 9900000724810001
		cc.obligarclick(driver.findElement(By.id("OrderSumary_nextBtn")));
		sleep(20000);
		cc.obligarclick(driver.findElement(By.id("SaleOrderMessages_nextBtn")));
		sleep(15000);
		CBS_Mattu invoSer = new CBS_Mattu();
		sOrders.add("Orden:"+orden+"-DNI:"+sDni+"-Cuenta:"+NCuenta);
		sleep(15000);
		String DOrden = cc.obtenerMontoyTNparaAlta(driver, orden);
		System.out.println(DOrden);
		Assert.assertTrue(invoSer.PagoEnCaja("1006", NCuenta, "1001", DOrden.split("-")[1], DOrden.split("-")[0],driver));
//		if(activarFalsos==true) {
//			invoSer.Servicio_NotificarEmisionFactura(orden);
//			sleep(10000);
//		}
		driver.navigate().refresh();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		if(activarFalsos==true) {
			boolean esta = false;
			List<WebElement> campos = tabla.findElements(By.tagName("tr"));
			for(WebElement UnC: campos) {
				if(UnC.getText().toLowerCase().contains("tracking status")) {
					Assert.assertTrue(UnC.getText().toLowerCase().contains("preparar pedido"));
					esta = true;
					break;
				}
			}
			Assert.assertTrue(esta);
				
		}
		CambiarPerfil("logisticayentrega",driver);
		sb.completarLogistica(orden, driver);
		//CambiarPerfil("entrega",driver);
		sb.completarEntrega(orden, driver);
		CambiarPerfil("ofcom",driver);
		try {
			cc.cajonDeAplicaciones("Consola FAN");
		} catch(Exception e) {
			waitForClickeable(driver,By.id("tabBar"));
			driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
			sleep(6000);
		}
		driver.switchTo().defaultContent();
		sleep(6000);
		cc.obtenerMontoyTNparaAlta(driver, orden);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		invoSer.ValidarInfoCuenta(Linea, sNombre,sApellido, sPlan);
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
	}
	
	@Test(groups={"Sales", "AltaLineaDatos","E2E"}, priority=2, dataProvider="AltaLineaEquipoOfCom")//verificado
	public void TS135814_CRM_Movil_PRE_Alta_Linea_Con_Equipo_Cliente_Nuevo_Presencial_OFCOM_Baroliche(String sDni, String sNombre, String sApellido, String sSexo, String sFNac, String sEmail, String sPlan, String sEquipo) throws IOException {
		imagen = "TS135814";
		CustomerCare cc = new CustomerCare(driver);
		SalesBase sb = new SalesBase(driver);
		sleep(8000);
		sb.BtnCrearNuevoCliente();
		sDni = driver.findElement(By.id("SearchClientDocumentNumber")).getAttribute("value");
		//sb.Crear_Cliente(sDni);
		ContactSearch contact = new ContactSearch(driver);
		contact.sex(sSexo);
		contact.Llenar_Contacto(sNombre, sApellido, sFNac);
		driver.findElement(By.id("EmailSelectableItems")).findElement(By.tagName("input")).sendKeys(sEmail);
		driver.findElement(By.id("Contact_nextBtn")).click();
		sleep(35000);
		sb.ResolverEntrega(driver, "Presencial","","");
		sleep(7000);
		driver.switchTo().defaultContent();
		Accounts accountPage = new Accounts(driver);
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		boolean enc = false;
		int index = 0;
		for(WebElement frame : frames) {
			try {
				System.out.println("aca");
				driver.switchTo().frame(frame);

				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).getText(); //each element is in the same iframe.
				//System.out.println(index); //prints the used index.

				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).isDisplayed(); //each element is in the same iframe.
				//System.out.println(index); //prints the used index.

				driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
				enc = true;
				break;
			}catch(NoSuchElementException noSuchElemExcept) {
				index++;
				driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
			}
		}
		if(enc == false)
			index = -1;
		try {
				driver.switchTo().frame(frames.get(index));
		}catch(ArrayIndexOutOfBoundsException iobExcept) {System.out.println("Elemento no encontrado en ningun frame 2.");
			
		}
		
		sleep(14000);
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".slds-input.ng-pristine.ng-untouched.ng-valid.ng-empty")));
		sb.elegirplan(sPlan);
		sleep(12000);
		driver.findElement(By.cssSelector(".slds-input.ng-valid.ng-not-empty.ng-dirty.ng-valid-parse.ng-touched")).clear();
		sleep(3000);
		driver.findElement(By.cssSelector(".slds-input.ng-valid.ng-dirty.ng-valid-parse.ng-touched.ng-empty")).sendKeys(sEquipo);
		sleep(10000);
		List<WebElement> acept = driver.findElements(By.cssSelector(".slds-button.slds-button_neutral.cpq-add-button"));
		for(WebElement a : acept){
			System.out.println(a.getText());
			if(a.getText().equals("Agregar")){
				cc.obligarclick(a);
				break;
			}
		}	
		sleep(5000);	
		sb.continuar();
		sleep(22000);
		sb.Crear_DomicilioLegal("R\u00edo Negro", "Bariloche", "falsa", "", "1000", "", "", "1549");
		sleep(24000);
		WebElement sig = driver.findElement(By.id("LineAssignment_nextBtn"));
		cc.obligarclick(sig);
		sleep(25000);
		try {
			cc.obligarclick(driver.findElement(By.id("Step_Error_Huawei_S015_nextBtn")));
		}catch(Exception ex1) {}
		sleep(15000);
		cc.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		sleep(20000);
		try {
			cc.obligarclick(driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")));
			sleep(20000);
		}catch(Exception ex1) {}
		
		contact.tipoValidacion("documento");
		sleep(8000);
		File directory = new File("Dni.jpg");
		driver.findElement(By.id("FileDocumentImage")).sendKeys(new File(directory.getAbsolutePath()).toString());
		sleep(3000);
		cc.obligarclick(driver.findElement(By.id("DocumentMethod_nextBtn")));
		sleep(15000);
		try {
			driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")).get(1).click();
			sleep(15000);
		}catch(Exception ex1) {}
		String orden = driver.findElement(By.className("top-data")).findElement(By.className("ng-binding")).getText();
		String NCuenta = driver.findElements(By.className("top-data")).get(1).findElements(By.className("ng-binding")).get(3).getText();
		String Linea = driver.findElement(By.cssSelector(".top-data.ng-scope")).findElements(By.className("ng-binding")).get(1).getText();
		System.out.println("Orden "+orden);
		System.out.println("cuenta "+NCuenta);
		System.out.println("Linea "+Linea);
		orden = orden.substring(orden.length()-8);
		NCuenta = NCuenta.substring(NCuenta.length()-16);
		Linea = Linea.substring(Linea.length()-10);
		cc.obligarclick(driver.findElement(By.id("OrderSumary_nextBtn")));
		sleep(20000);
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sOrders.add("Orden:"+orden+"-DNI:"+sDni+"-Cuenta:"+NCuenta+"-Linea"+Linea);
		sleep(15000);
		System.out.println(cc.obtenerMontoyTNparaAlta(driver, orden));
		CBS_Mattu invoSer = new CBS_Mattu();
		if(activarFalsos==true) {
			invoSer.Servicio_NotificarEmisionFactura(orden);
			sleep(10000);
		}
		driver.navigate().refresh();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		if(activarFalsos==true) {
			boolean esta = false;
			List<WebElement> campos = tabla.findElements(By.tagName("tr"));
			for(WebElement UnC: campos) {
				if(UnC.getText().toLowerCase().contains("tracking status")) {
					Assert.assertTrue(UnC.getText().toLowerCase().contains("preparar pedido"));
					esta = true;
					break;
				}
			}
			Assert.assertTrue(esta);
				
		}
		CambiarPerfil("logisticayentrega",driver);
		sb.completarLogistica(orden, driver);
		//CambiarPerfil("entrega",driver);
		sb.completarEntrega(orden, driver);
		CambiarPerfil("ofcom",driver);
		try {
			cc.cajonDeAplicaciones("Consola FAN");
		} catch(Exception e) {
			//sleep(3000);
			waitForClickeable(driver,By.id("tabBar"));
			driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
			sleep(6000);
		}
			
		driver.switchTo().defaultContent();
		sleep(6000);
		cc.obtenerMontoyTNparaAlta(driver, orden);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		invoSer.ValidarInfoCuenta(Linea, sNombre,sApellido, sPlan);
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
	}
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test(groups={"Sales", "AltaLineaDatos","E2E"}, priority=1, dataProvider="AltaLineaNuevoconEquipo") 
	public void TS125004_CRM_Movil_PRE_Alta_Linea_con_Equipo_Cliente_Nuevo_Presencial_AG(String cDni, String sNombre, String sApellido, String sSexo, String sFNac, String sEmail, String sPlan,String sEquipo, String sProvincia, String sLocalidad, String sCalle, String sNumero, String sCP) throws IOException, AWTException {
		imagen = "TS125004";
		CustomerCare cc = new CustomerCare(driver);
		SalesBase sb = new SalesBase(driver);
		sleep(6000);
		sb.BtnCrearNuevoCliente();
		String sDni = driver.findElement(By.id("SearchClientDocumentNumber")).getText();
		System.out.println(sDni);
		ContactSearch contact = new ContactSearch(driver);
		contact.sex(sSexo);
		contact.Llenar_Contacto(sNombre, sApellido, sFNac);
		driver.findElement(By.id("EmailSelectableItems")).findElement(By.tagName("input")).sendKeys(sEmail);
		driver.findElement(By.id("Contact_nextBtn")).click();
		sleep(20000);
		List<WebElement> btns = driver.findElements(By.cssSelector(".slds-button.slds-button.slds-button--icon"));
			for(WebElement e: btns){
				if(e.getText().toLowerCase().equals("catalogo")){ 
					e.click();
					break;
				}
			}
		sleep(25000);
		sb.elegirplan(sPlan);
		sleep(12000);
		driver.findElement(By.cssSelector(".slds-input.ng-valid.ng-not-empty.ng-dirty.ng-valid-parse.ng-touched")).clear();
		sleep(8000);
		driver.findElement(By.cssSelector(".slds-input.ng-valid.ng-dirty.ng-valid-parse.ng-touched.ng-empty")).sendKeys(sEquipo);
		sleep(8000);
		List<WebElement> agregar = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.add-button")); 
			for(WebElement a : agregar){
				if(a.getText().equals("Agregar")){
					cc.obligarclick(a);
				}
			}
		sleep(5000);	
		sb.continuar();
		sleep(24000);
		sb.Crear_DomicilioLegal(sProvincia, sLocalidad, sCalle, "", sNumero, "", "", sCP);
		sleep(24000);
		cc.obligarclick(driver.findElement(By.id("LineAssignment_nextBtn")));
		sleep(23000);
		cc.obligarclick(driver.findElement(By.id("ICCDAssignment_nextBtn")));
		sleep(20000);
		cc.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		sleep(20000);
		List<WebElement> medpag = driver.findElements(By.cssSelector(".slds-radio.ng-scope"));
			for(WebElement m :medpag){
				if(m.getText().equals("Efectivo")){
				cc.obligarclick(m.findElement(By.cssSelector(".slds-radio--faux")));
				}
			}
		cc.obligarclick(driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")));
		sleep(20000);
		contact.tipoValidacion("documento");
		sleep(8000);
		File directory = new File("Dni.jpg");
		driver.findElement(By.id("FileDocumentImage")).sendKeys(new File(directory.getAbsolutePath()).toString());
		sleep(3000);
		cc.obligarclick(driver.findElement(By.id("DocumentMethod_nextBtn")));
		sleep(10000);
		String orden = driver.findElement(By.className("top-data")).findElement(By.className("ng-binding")).getText();
		String NCuenta = driver.findElements(By.className("top-data")).get(1).findElements(By.className("ng-binding")).get(3).getText();
		String Linea = driver.findElement(By.cssSelector(".top-data.ng-scope")).findElements(By.className("ng-binding")).get(1).getText();
		orden = orden.substring(orden.length()-8);
		NCuenta = NCuenta.substring(NCuenta.length()-16);
		Linea = Linea.substring(Linea.length()-10);
		cc.obligarclick(driver.findElement(By.id("OrderSumary_nextBtn")));
		sleep(20000);
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(15000);
		CBS_Mattu invoSer = new CBS_Mattu();
		sOrders.add("Orden:"+orden+"-DNI:"+sDni+"-Cuenta:"+NCuenta);
		sleep(15000);
		String DOrden = cc.obtenerMontoyTNparaAlta(driver, orden);
		System.out.println(DOrden);
		Assert.assertTrue(invoSer.PagoEnCaja("1006", NCuenta, "1001", DOrden.split("-")[1], DOrden.split("-")[0],driver));
//		if(activarFalsos==true) {
//			invoSer.Servicio_NotificarEmisionFactura(orden);
//			sleep(10000);
//		}
		driver.navigate().refresh();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		if(activarFalsos==true) {
			boolean esta = false;
			List<WebElement> campos = tabla.findElements(By.tagName("tr"));
			for(WebElement UnC: campos) {
				if(UnC.getText().toLowerCase().contains("tracking status")) {
					Assert.assertTrue(UnC.getText().toLowerCase().contains("preparar pedido"));
					esta = true;
					break;
				}
			}
			Assert.assertTrue(esta);
				
		}
		CambiarPerfil("logisticayentrega",driver);
		sb.completarLogistica(orden, driver);
		//CambiarPerfil("entrega",driver);
		sb.completarEntrega(orden, driver);
		CambiarPerfil("ofcom",driver);
		try {
			cc.cajonDeAplicaciones("Consola FAN");
		} catch(Exception e) {
			waitForClickeable(driver,By.id("tabBar"));
			driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
			sleep(6000);
		}
		driver.switchTo().defaultContent();
		sleep(6000);
		cc.obtenerMontoyTNparaAlta(driver, orden);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		invoSer.ValidarInfoCuenta(Linea, sNombre,sApellido, sPlan);
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
	}
}