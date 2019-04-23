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
import Pages.CBS;
import Pages.CustomerCare;
import Pages.Marketing;
import Pages.SalesBase;
import Pages.setConexion;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class ConsultaDeSaldo extends TestBase {

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	private Marketing mk;
	private CBS cbs;
	private CBS_Mattu cbsm;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	@BeforeClass (alwaysRun = true)
	public void init() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
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
	
	@Test (groups = {"GestionesPerfilOficina", "ConsultaDeSaldo", "Ciclo1"}, dataProvider = "ConsultaSaldo")
	public void TS134373_CRM_Movil_Prepago_Vista_360_Consulta_de_Saldo_Verificar_credito_prepago_de_la_linea_FAN_Front_OOCC(String sDNI, String sLinea, String sAccountKey) {
		imagen ="TS134373";
		detalles = null;
		detalles = imagen + "- Consulta de Saldo - DNI:" + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer credito = Integer.parseInt(sMainBalance.substring(0, 5));
		System.out.println(credito);
		Assert.assertTrue(false);  //Credito recarga: Informacion no disponible
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ConsultaDeSaldo", "Ciclo1"}, dataProvider = "ConsultaSaldo")
	public void TS134376_CRM_Movil_Prepago_Vista_360_Consulta_de_Saldo_Verificar_saldo_del_cliente_FAN_Front_OOCC(String sDNI, String sLinea, String sAccountKey) {
		imagen ="TS134376";
		detalles = null;
		detalles = imagen + "- Consulta de Saldo - DNI:" + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		String saldo = driver.findElement(By.className("header-right")).getText();
		saldo = saldo.replaceAll("[^\\d]", "");
		Integer saldoEnCard = Integer.parseInt(saldo);	
		String response = cbs.ObtenerValorResponse(cbsm.verificarSaldo(sAccountKey), "ars:TotalOutStandAmt");
		Integer saldoFacturacion = Integer.parseInt(response.substring(0, 6));
		Assert.assertTrue(saldoEnCard.equals(saldoFacturacion));
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"GestionesPerfilTelefonico", "ConsultaDeSaldo", "Ciclo1" }, dataProvider = "ConsultaSaldo")
	public void TS134811_CRM_Movil_Prepago_Vista_360_Consulta_de_Saldo_Verificar_credito_prepago_de_la_linea_FAN_Front_Telefonico(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS134811";
		detalles = null;
		detalles = imagen + " -Consulta de Saldo - DNI: " + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer credito = Integer.parseInt(sMainBalance.substring(0, 5));
		System.out.println(credito);
		Assert.assertTrue(false);  //Credito recarga: Informacion no disponible
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "ConsultaDeSaldo", "Ciclo1"}, dataProvider = "ConsultaSaldo")
	public void TS134813_CRM_Movil_Prepago_Vista_360_Consulta_de_Saldo_Verificar_saldo_del_cliente_FAN_Front_Telefonico(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS134813";
		detalles = null;
		detalles = imagen + " -Consulta de saldo - DNI: " + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		String saldo = driver.findElement(By.className("header-right")).getText();
		saldo = saldo.replaceAll("[^\\d]", "");
		Integer saldoEnCard = Integer.parseInt(saldo);	
		String response = cbs.ObtenerValorResponse(cbsm.verificarSaldo(sAccountKey), "ars:TotalOutStandAmt");
		Integer saldoFacturacion = Integer.parseInt(response.substring(0, 6));
		Assert.assertTrue(saldoEnCard.equals(saldoFacturacion));
	}
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test (groups = {"GestionesPerfilAgente", "ConsultaDeSaldo", "Ciclo1"}, dataProvider = "ConsultaSaldo")
	public void TS134814_CRM_Movil_Prepago_Vista_360_Consulta_de_Saldo_Verificar_credito_prepago_de_la_linea_FAN_Front_Agentes(String sDNI, String sLinea, String sAccountKey){
		imagen = "TS134814";
		detalles = null;
		detalles = imagen + "Consulta de Saldo -DNI:" + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer credito = Integer.parseInt(sMainBalance.substring(0, 5));
		System.out.println(credito);
		Assert.assertTrue(false);  //Credito recarga: Informacion no disponible
	}
	
	@Test (groups = {"GestionesPerfilAgente", "ConsultaDeSaldo", "Ciclo1"}, dataProvider = "ConsultaSaldo")
	public void TS134815_CRM_Movil_Prepago_Vista_360_Consulta_de_Saldo_Verificar_saldo_del_cliente_FAN_Front_Agentes(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS134815";
		detalles = null;
		detalles = imagen + "Consulta de saldo -DNI:" + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		String saldo = driver.findElement(By.className("header-right")).getText();
		saldo = saldo.replaceAll("[^\\d]", "");
		Integer saldoEnCard = Integer.parseInt(saldo);	
		String response = cbs.ObtenerValorResponse(cbsm.verificarSaldo(sAccountKey), "ars:TotalOutStandAmt");
		Integer saldoFacturacion = Integer.parseInt(response.substring(0, 6));
		Assert.assertTrue(saldoEnCard.equals(saldoFacturacion));
	}
}