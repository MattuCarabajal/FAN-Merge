package Funcionalidades;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;

import Pages.Accounts;
import Pages.ContactSearch;
import Pages.CustomerCare;
import Pages.SalesBase;
import Pages.setConexion;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class AltaDeLinea extends TestBase {

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
	
	@Test(groups={"Sales", "AltaLineaDatos","E2E"}, priority=2, dataProvider="DatosAltaLineaOfCom")
	public void TS_CRM_Movil_PRE_Alta_Linea_Cliente_Nuevo_OfCom_Efectivo_Presencial_DNI(String sDni, String sNombre, String sApellido, String sSexo, String sFNac, String sEmail, String sPlan, String sProvincia, String sLocalidad) throws IOException {
		imagen = "TS_CRM_Movil_PRE_Alta_Linea_Cliente_Nuevo_OfCom_Efectivo_Presencial_DNI";
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
		sleep(4000);
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".slds-input.ng-pristine.ng-untouched.ng-valid.ng-empty")));
		sb.elegirplan(sPlan);
		sb.continuar();
		sleep(20000);
		sb.Crear_DomicilioLegal(sProvincia, sLocalidad, "falsa", "", "1000", "", "", "1549");
		sleep(12000);
		WebElement sig = driver.findElement(By.id("LineAssignment_nextBtn"));
		cc.obligarclick(sig);
		sleep(25000);
		cc.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		sleep(20000);
		try {
			cc.obligarclick(driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")));
			sleep(12000);
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
	
	@Test(groups={"Sales", "AltaLineaDatos","E2E"}, priority=2, dataProvider="AltaLineaExistenteOfComPresencial")//verificado
	public void TS119298_CRM_Movil_PRE_Alta_Linea_Cliente_Existente_OFCOM_Efectivo_Presencial_DNI(String sDni, String sPlan, String sNombre, String sApellido) throws IOException {
		imagen = "TS119298";
		sleep(5000);
		sb.BuscarCuenta("DNI", sDni);
		sleep(5000);
		List<WebElement> btns = driver.findElements(By.cssSelector(".slds-button.slds-button.slds-button--icon"));
		for(WebElement e: btns){
			System.out.println(e.getText());
			if(e.getText().toLowerCase().equals("catalogo")){ 
				e.click();
				break;
			}
		}
		sb.elegirplan(sPlan);
		sleep(18000);
		sb.ResolverEntrega(driver, "Presencial","nada","nada");
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
		sb.continuar();
		sleep(22000);
		WebElement sig = driver.findElement(By.id("LineAssignment_nextBtn"));
		cc.obligarclick(sig);
		sleep(20000);
		cc.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		sleep(20000);
		try {
			cc.obligarclick(driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")));
			sleep(20000);
		}catch(Exception ex1) {}
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
	
	@Test(groups={"Sales", "AltaLineaDatos","E2E"}, priority=2, dataProvider="AltaLineaExistenteOfComTD")//verificado
	public void TS119300_CRM_Movil_PRE_Alta_Linea_Cliente_Existente_OFCOM_TD_Presencial_DNI(String sDni, String sNombre, String sApellido, String sPlan, String sBanco, String sTarjeta, String sPromo, String sCuotas, String sNumTar) throws IOException {
		imagen = "TS119300";
		sleep(5000);
		sb.BuscarCuenta("DNI", sDni);
		sleep(5000);
		List<WebElement> btns = driver.findElements(By.cssSelector(".slds-button.slds-button.slds-button--icon"));
		for(WebElement e: btns){
			System.out.println(e.getText());
			if(e.getText().toLowerCase().equals("catalogo")){ 
				e.click();
				break;
			}
		}
		sleep(18000);
		//sb.ResolverEntrega(driver, "Presencial","nada","nada");
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
		sb.continuar();
		sleep(22000);
		WebElement sig = driver.findElement(By.id("LineAssignment_nextBtn"));
		cc.obligarclick(sig);
		sleep(25000);
		cc.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
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
	
	@Test(groups={"Sales", "AltaLineaDatos","E2E"}, priority=1, dataProvider="DatosAltaLineaAgente")
	public void TS_CRM_Alta_de_Linea_Agente(String sDni, String sNombre, String sApellido, String sSexo, String sFNac, String sEmail, String sPlan, String sProvincia, String sLocalidad, String sCalle, String sNumCa, String sCP, String sEntrega, String sStoreProv, String sStoreLoc, String sTipoDelivery) throws IOException {
		imagen = "TS_CRM_Alta_de_Linea_Agente";
		sb.BtnCrearNuevoCliente();
		sDni = driver.findElement(By.id("SearchClientDocumentNumber")).getAttribute("value");
		
		//sb.Crear_Cliente(sDni);
		ContactSearch contact = new ContactSearch(driver);
		contact.sex(sSexo);
		contact.Llenar_Contacto(sNombre, sApellido, sFNac);
		driver.findElement(By.id("EmailSelectableItems")).findElement(By.tagName("input")).sendKeys(sEmail);
		driver.findElement(By.id("Contact_nextBtn")).click();
		sleep(28000);
		sb.ResolverEntrega(driver, sEntrega,sStoreProv,sStoreLoc);
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
		sb.continuar();
		sleep(22000);
		sb.Crear_DomicilioLegal(sProvincia, sLocalidad, sCalle, "", sNumCa, "", "", sCP);
		sleep(24000);
		WebElement sig = driver.findElement(By.id("LineAssignment_nextBtn"));
		cc.obligarclick(sig);
		sleep(20000);
		if (sEntrega.equalsIgnoreCase("Delivery")) {
			
		}
		if (sEntrega.equalsIgnoreCase("Store pick up")) {
			cc.obligarclick(driver.findElement(By.id("DeliveryMethodConfiguration_nextBtn")));
			sleep(20000);
		}
		if (sEntrega.equalsIgnoreCase("Presencial")) {
			cc.obligarclick(driver.findElement(By.id("ICCDAssignment_nextBtn")));
			sleep(30000);
		}
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
			sleep(10000);
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
		sleep(30000);
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(15000);		
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
		CambiarPerfil("agente",driver);
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
	
	@Test(groups={"Sales", "AltaLinea","E2E"}, priority=1, dataProvider="AltaLineaNuevoAgentePresencial")
	public void TS118938_CRM_Movil_PRE_Alta_Linea_Cliente_Nuevo_Agente_Efectivo_Presencial_DNI(String sDni, String sNombre, String sApellido, String sSexo, String sFNac, String sEmail, String sPlan, String sProvincia, String sLocalidad, String sCalle, String sNumCa, String sCodPos) throws IOException {
		imagen = "TS118938";
		sleep(5000);
		sb.BtnCrearNuevoCliente();
		sDni = driver.findElement(By.id("SearchClientDocumentNumber")).getAttribute("value");		
		ContactSearch contact = new ContactSearch(driver);
		contact.sex(sSexo);
		contact.Llenar_Contacto(sNombre, sApellido, sFNac);
		driver.findElement(By.id("EmailSelectableItems")).findElement(By.tagName("input")).sendKeys(sEmail);
		driver.findElement(By.id("Contact_nextBtn")).click();
		sleep(18000);
		sb.ResolverEntrega(driver, "Presencial", "nada", "nada");
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
		sb.continuar();
		sleep(22000);
		sb.Crear_DomicilioLegal(sProvincia, sLocalidad, sCalle, "", sNumCa, "", "", sCodPos);
		sleep(24000);
		WebElement sig = driver.findElement(By.id("LineAssignment_nextBtn"));
		cc.obligarclick(sig);
		sleep(20000);
		cc.obligarclick(driver.findElement(By.id("ICCDAssignment_nextBtn")));
		sleep(20000);
		
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
			sleep(10000);
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
		sleep(30000);
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(15000);		
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
		CambiarPerfil("agente",driver);
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
	
	@Test(groups={"Sales", "AltaLineaDatos","E2E"}, priority=1, dataProvider="DatosAltaAgenteCredito")
	public void TS135761_CRM_Movil_PRE_Alta_Linea_Cliente_Nuevo_Agente_TC_Presencial_DNI_Punta_Alta(String sDni, String sNombre, String sApellido, String sSexo, String sFNac, String sEmail, String sPlan, String sProvincia, String sLocalidad, String sCalle, String sNumCa, String sCP, String cBanco, String cTarjeta, String cPromo, String cCuotas, String cNumTarjeta) throws IOException, KeyManagementException, NoSuchAlgorithmException {
		imagen = "TS135761";
		sb.BtnCrearNuevoCliente();
		sDni = driver.findElement(By.id("SearchClientDocumentNumber")).getAttribute("value");		
		//sb.Crear_Cliente(sDni);
		ContactSearch contact = new ContactSearch(driver);
		contact.sex(sSexo);
		contact.Llenar_Contacto(sNombre, sApellido, sFNac);
		driver.findElement(By.id("EmailSelectableItems")).findElement(By.tagName("input")).sendKeys(sEmail);
		driver.findElement(By.id("Contact_nextBtn")).click();
		sleep(28000);
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
		sb.continuar();
		sleep(22000);
		sb.Crear_DomicilioLegal(sProvincia, sLocalidad, sCalle, "", sNumCa, "", "", sCP);
		sleep(24000);
		WebElement sig = driver.findElement(By.id("LineAssignment_nextBtn"));
		cc.obligarclick(sig);
		sleep(20000);
		cc.obligarclick(driver.findElement(By.id("ICCDAssignment_nextBtn")));
		sleep(30000);
		cc.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		selectByText(driver.findElement(By.id("BankingEntity-0")), cBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), cTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), cPromo);
		sleep(5000);
		selectByText(driver.findElement(By.id("Installment-0")), cCuotas);
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
		invoSer.PagarTCPorServicio(orden);
		sleep(5000);
		if(activarFalsos == true) {
			invoSer.Servicio_NotificarPago(orden);
			sleep(20000);
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
		CambiarPerfil("agente",driver);
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
}