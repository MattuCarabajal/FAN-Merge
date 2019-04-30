package Funcionalidades;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.Accounts;
import Pages.BasePage;
import Pages.CustomerCare;
import Pages.Marketing;
import Pages.SalesBase;
import Pages.TechCare_Ola1;
import Pages.TechnicalCareCSRAutogestionPage;
import Pages.TechnicalCareCSRDiagnosticoPage;
import Pages.setConexion;
import Tests.TestBase;

public class DiagnosticoInconvenientes extends TestBase {

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	private Marketing mk;
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
		super.loginMerge(driver);
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
		super.goToLeftPanel4(driver, "Inicio");
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
			sleep(5000);
			if (UnB.getText().equalsIgnoreCase("gesti\u00f3n de clientes")) {
				cc.obligarclick(UnB);
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
	
	@Test (groups = {"GestionesPerfilOficina", "DiagnosticoInconvenientes","E2E", "Ciclo3"}, dataProvider = "Diagnostico")
	public void TS119162_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_realizar_llamadas(String sDNI, String sLinea){
		imagen = "TS119262";
		detalles = null;
		detalles = imagen + " -ServicioTecnico: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(12000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo realizar llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		page.seleccionarPreguntaFinal("S\u00ed");
		buscarYClick(driver.findElements(By.id("BalanceValidation_nextBtn")), "equals", "continuar");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("NetworkCategory_nextBtn")));
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(12000);
		cCC.cobertura("no son las antenas");
		/*driver.switchTo().frame(cambioFrame(driver, By.id("CoverageResult|0")));
		List<WebElement> cobertura = driver.findElements(By.cssSelector(".imgItemContainer.ng-scope"));
			for(WebElement c : cobertura){
				if(c.getText().toLowerCase().contains("no son las antenas")){
					c.click();
					break;
				}
			}*/
		sleep(12000);
		driver.switchTo().frame(cambioFrame(driver, By.id("CoverageOkNetMessage")));
		WebElement gesti = driver.findElement(By.id("CoverageOkNetMessage")).findElement(By.tagName("div")).findElement(By.tagName("p")).findElements(By.tagName("p")).get(1).findElement(By.tagName("span")).findElement(By.tagName("strong"));
		String orden = gesti.getText();
		sleep(5000);
		System.out.println("El numero de orden es:" + orden);
		cCC.buscarOrdenDiag(orden+"*");
		Boolean ord = false;
		WebElement status = driver.findElement(By.id("Case_body")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(1);
			if(status.getText().toLowerCase().contains("derivada")){
    		ord = true;
			}
		Assert.assertTrue(ord);	
		System.out.println("La gestion fue derivada");
    //La gestion tiene que quedar como derivada, pero se cierra el caso para poder reutilizar la cuenta.
		tech.cerrarCaso("Resuelta exitosa", "Consulta");
    
	}
	
	@Test (groups = {"GestionesPerfilOficina","Autogestion","E2E", "Ciclo3"},  dataProvider = "Diagnostico")
	public void TS105418_CRM_Movil_Repro_Autogestion_0800_Inconv_con_derivacion_a_representante_Resuelto(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS105418";
		detalles = null;
		detalles = imagen + "- Autogestion - DNI: "+sDNI;
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		cc.irAGestion("diagn\u00f3stico de autogesti\u00f3n");
		sleep(15000);
		tech.listadoDeSeleccion("0800", "0800-444-0531", "Incov con derivaci\u00f3n a representante");
		sleep(4000);
		tech.verificarNumDeGestion();
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "DiagnosticoInconvenientes", }, dataProvider = "Diagnostico")
	public void TS105428_CRM_Movil_Repro_Autogestion_USSD_No_Interactua_Resuelto(String cDNI, String cLinea) throws InterruptedException {
		imagen = "TS105428";
		detalles = null;
		detalles = imagen + "- Autogestion - DNI: "+cDNI;
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		searchAndClick(driver, "Diagn\u00f3stico de Autogesti\u00f3n");
		tech.listadoDeSeleccion("USSD", "*150#", "No Interact\u00faa");
		sleep(4000);
		tech.verificarNumDeGestion();
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "DiagnosticoInconvenientes", }, dataProvider = "Diagnostico")
	public void TS105431_CRM_Movil_Repro_Autogestion_WAP_Sitio_Caido_No_carga_informacion_Resuelto(String cDNI, String cLinea) throws InterruptedException {
		imagen = "TS105431";
		detalles = null;
		detalles = imagen + "- Autogestion - DNI: "+cDNI;
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		searchAndClick(driver, "Diagn\u00f3stico de Autogesti\u00f3n");
		tech.listadoDeSeleccion("WAP", "email", "informaci\u00f3n incompleta");
		sleep(4000);
		tech.verificarNumDeGestion();
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
		
		
	}
	
	@Test (groups = {"GestionesPerfilOficina", "DiagnosticoInconvenientes", }, dataProvider = "Diagnostico")
	public void TS105449_CRM_Movil_Repro_Autogestion_0800_Informa_Sistema_Fuera_de_Servicio_No_Resuelto(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS105449";
		detalles = null;
		detalles = imagen + "- Autogestion - DNI: " + sDNI;
		boolean estado = false;
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("diagnostico de autogestion");
		sleep(5000);
		tech.listadoDeSeleccion("0800", "0800-444-0531", "Informa Sistema Fuera de Servicio");
		sleep(7000);
		String caso = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope")).getText();
		caso = caso.substring(caso.indexOf("0"), caso.length());
		System.out.println(caso);
		cc.buscarCaso(caso);
		driver.switchTo().frame(cambioFrame(driver, By.name("close")));
		WebElement tabla = null;
		for (WebElement x : driver.findElements(By.className("pbSubsection"))) {
			if (x.getText().contains("Propietario del caso"))
				tabla = x;
		}
		for (WebElement x : tabla.findElements(By.tagName("tr"))) {
			if (x.getText().contains("Estado"))
				tabla = x;
		}
		if (tabla.findElements(By.tagName("td")).get(3).getText().equalsIgnoreCase("Realizada exitosa"))
			estado = true;
		Assert.assertTrue(estado);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "DiagnosticoInconvenientes", }, dataProvider = "Diagnostico")
	public void TS105453_CRM_Movil_Repro_Autogestion_0800_La_Linea_esta_muda_No_Resuelto(String cDNI, String cLinea) throws InterruptedException {
		imagen = "TS105453";
		detalles = null;
		detalles = imagen + "- Autogestion - DNI: "+cDNI;
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		searchAndClick(driver, "Diagn\u00f3stico de Autogesti\u00f3n");
		tech.listadoDeSeleccion("0800", "0800-444-4100 (Asistencia Tienda Online)", "La l\u00ednea esta muda");
		sleep(4000);
		System.out.println(tech.verificarNumDeGestion());
		//driver.switchTo().frame(cambioFrame(driver, By.id("srchErrorDiv_Case")));
		String estado= driver.findElement(By.xpath("//*[@id='Case_body']/table/tbody/tr[2]/td[3]")).getText();
		Assert.assertTrue(estado.equalsIgnoreCase("Informada"));	
	}
	
	@Test (groups = {"GestionesPerfilOficina","DiagnosticoInconvenientes"},  dataProvider = "Diagnostico")
	public void TS105466_CRM_CRM_Movil_Repro_Autogestion_WEB_Incon_con_Compra_de_packs_No_Resuelto(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS105466";
		detalles = null;
		detalles = imagen + "- Autogestion - DNI: "+sDNI;
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		cc.irAGestion("diagn\u00f3stico de autogesti\u00f3n");
		sleep(15000);
		tech.listadoDeSeleccion("WEB", "Packs", "Incon.con Compra de packs");
		sleep(4000);
		tech.verificarNumDeGestion();
		driver.switchTo().frame(cambioFrame(driver, By.id("srchErrorDiv_Case")));
		String estado= driver.findElement(By.xpath("//*[@id='Case_body']/table/tbody/tr[2]/td[3]")).getText();
		Assert.assertTrue(estado.equalsIgnoreCase("Informada"));
		//Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "DiagnosticoInconvenientes", }, dataProvider = "Diagnostico")
	public void TS105831_Nros_Emergencia_Informa_Sistema_Fuera_de_Servicio_Resuelto(String cDNI, String cLinea) throws InterruptedException {
		imagen = "TS105831";
		detalles = null;
		detalles = imagen + "- Autogestion - DNI: "+cDNI;
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		searchAndClick(driver, "Diagn\u00f3stico de Autogesti\u00f3n");
		tech.listadoDeSeleccion("Nros. Emergencia", "Otro", "Informa Sistema Fuera de Servicio");
		sleep(4000);
		tech.verificarNumDeGestion();
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "DiagnosticoInconvenientes","E2E", "Ciclo3"}, dataProvider = "Diagnostico")
	public void TS111871_CRM_Movil_REPRO_Diagnostico_SVA_Configuracion_Disponible_Presencial_SMS_Saliente_SMS_a_fijo_Geo_No_Ok_Desregistrar_OfCom(String sDNI, String sLinea) throws Exception  {
		imagen = "TS111871";
		detalles = null;
		detalles = imagen + " -ServicioTecnico - DNI: "+sDNI+" - Linea: "+sLinea;
		CustomerCare cCC=new CustomerCare(driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-info")));
		sleep(5000);
		cCC.irAMisServicios();
		tech.verDetalles();
	    tech.clickDiagnosticarServicio("sms", "SMS Saliente", true);
	    tech.selectionInconvenient("SMS a fijo");
	    tech.continuar();
	    tech.seleccionarRespuesta("no");
	    buscarYClick(driver.findElements(By.id("KnowledgeBaseResults_nextBtn")), "equals", "continuar");
	    page.seleccionarPreguntaFinal("S\u00ed");
	    buscarYClick(driver.findElements(By.id("BalanceValidation_nextBtn")), "equals", "continuar");
	    sleep(9000);
	    tech.categoriaRed("Desregistrar");
	    sleep(8000);
	    cCC.obligarclick(driver.findElement(By.id("NetworkCategory_nextBtn")));
	    sleep(8000);
	    page.seleccionarPreguntaFinal("S\u00ed");
	    sleep(8000);
	    driver.findElement(By.id("DeregisterSpeech_nextBtn")).click();
	    sleep(8000);
	    page.seleccionarPreguntaFinal("S\u00ed");
	    driver.findElement(By.id("Deregister_nextBtn")).click();
	    sleep(5000);
	    page.seleccionarPreguntaFinal("S\u00ed");
	    sleep(5000);
	    String orden = null;
	    List<WebElement> ord = driver.findElements(By.cssSelector(".slds-form-element__control"));
	    	for(WebElement o : ord){
	    		if(o.getText().contains("El n\u00famero de caso es")){
	    			orden = o.findElement(By.tagName("p")).findElement(By.tagName("p")).findElement(By.tagName("span")).findElement(By.tagName("strong")).getText();
	    			}
	    	}
	    System.out.println("El numero de orden es:" + orden);
	    cCC.buscarCaso(orden+"*");
	    cCC.verificarStatus(orden);
	    
	}
	
	@Test (groups = {"GestionesPerfilOficina","DiagnosticoInconvenientes"}, dataProvider = "Diagnostico")
	public void TS119178_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_realizar_llamadas_Sin_Locacion_Evento_Masivo(String sDNI, String sLinea) throws Exception  {
		imagen = "TS119178";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRDiagnosticoPage Tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage (driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo realizar llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(3000);
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		Tech.categoriaRed("Desregistrar");
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(15000);
		page.seleccionarPreguntaFinal("No");
		buscarYClick(driver.findElements(By.id("DeregisterSpeech_nextBtn")), "equals", "continuar");
		sleep(15000);
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")).getLocation().y+" )");
		page.seleccionarPreguntaFinal("No, sigue con inconvenientes");
		buscarYClick(driver.findElements(By.id("HlrDeregister_nextBtn")), "equals", "continuar");
		sleep(8000);
		Tech.categoriaRed("Encontr\u00e9 un problema (Rojo)");
		sleep(8000);
		WebElement evento = driver.findElement(By.id("MassiveIncidentLookUp"));
		evento.click();
		evento.sendKeys("Evento Masivo");
		buscarYClick(driver.findElements(By.id("AddressSection_nextBtn")), "equals", "continuar");
		sleep(8000);
		String caso = null;
		List <WebElement> lista = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for(WebElement x : lista) {
			if(x.getText().toLowerCase().contains("su gesti\u00f3n")) {
				caso = x.findElement(By.tagName("div")).findElement(By.tagName("span")).findElement(By.tagName("strong")).getText();
			}
		}
		System.out.println(caso);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso);
		Assert.assertTrue(tech.cerrarCaso("Resuelta Masiva", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","DiagnosticoInconvenientes"}, dataProvider = "Diagnostico")
	public void TS119183_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_realizar_llamadas_Sin_Locacion_Servicio_con_suspencion(String sDNI, String sLinea) throws Exception  {
		imagen = "TS119183";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRDiagnosticoPage Tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage (driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo recibir llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(3000);
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		Tech.categoriaRed("Desregistrar");
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(15000);
		page.seleccionarPreguntaFinal("No");
		buscarYClick(driver.findElements(By.id("DeregisterSpeech_nextBtn")), "equals", "continuar");
		sleep(15000);
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")).getLocation().y+" )");
		page.seleccionarPreguntaFinal("S\u00ed, funciona correctamente");
		buscarYClick(driver.findElements(By.id("HlrDeregister_nextBtn")), "equals", "continuar");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		Tech.categoriaRed("Fuera del Area de Cobertura");
		sleep(5000);
		WebElement caso = driver.findElement(By.xpath("//*[@id=\"MobileConfigSendingMessage\"]/div/p/h1/span/strong"));
		String Ncaso = caso.getText();
		System.out.println(Ncaso);
		driver.switchTo().defaultContent();
		tech.buscarCaso(Ncaso);
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","DiagnosticoInconvenientes"}, dataProvider = "Diagnostico")
	public void TS119186_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_realizar_llamadas_Sin_Locacion_NO_recupera_locacion_Geo_rojo(String sDNI, String sLinea) throws Exception  {
		imagen = "TS119186";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRDiagnosticoPage Tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage (driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(8000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo realizar llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(3000);
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		Tech.categoriaRed("Desregistrar");
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(15000);
		page.seleccionarPreguntaFinal("No");
		buscarYClick(driver.findElements(By.id("DeregisterSpeech_nextBtn")), "equals", "continuar");
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")).getLocation().y+" )");
		page.seleccionarPreguntaFinal("S\u00ed, funciona correctamente");
		sleep(8000);
		buscarYClick(driver.findElements(By.id("HlrDeregister_nextBtn")), "equals", "continuar");
		sleep(8000);
		Tech.categoriaRed("Encontr\u00e9 un problema (Rojo)");
		sleep(8000);
		WebElement evento = driver.findElement(By.id("MassiveIncidentLookUp"));
		evento.click();
		evento.sendKeys("Evento Masivo");
		sleep(8000);
		String caso = null;
		buscarYClick(driver.findElements(By.id("AddressSection_nextBtn")), "equals", "continuar");
		List <WebElement> lista = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for(WebElement x : lista) {
			if(x.getText().toLowerCase().contains("su gesti\u00f3n")) {
				caso = x.findElement(By.tagName("div")).findElement(By.tagName("span")).findElement(By.tagName("strong")).getText();
			}
		}
		//String caso = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope")).getText();
		System.out.println(caso);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso);
		driver.switchTo().frame(cambioFrame(driver, By.id("srchErrorDiv_Case")));
		String estado= driver.findElement(By.xpath("//*[@id='Case_body']/table/tbody/tr[2]/td[3]")).getText();
		Assert.assertTrue(estado.equalsIgnoreCase("Pendiente evento masivo"));
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));	
	}
	
	@Test (groups = {"GestionesPerfilOficina","DiagnosticoInconvenientes"}, dataProvider = "Diagnostico")
	public void TS119201_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_recibir_llamadas_Sin_Locacion_Envia_configuraciones(String sDNI, String sLinea) throws Exception  {
		imagen = "TS119201";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRDiagnosticoPage Tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage (driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo recibir llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(3000);
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		Tech.categoriaRed("Desregistrar");
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(15000);
		page.seleccionarPreguntaFinal("No");
		buscarYClick(driver.findElements(By.id("DeregisterSpeech_nextBtn")), "equals", "continuar");
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")).getLocation().y+" )");
		page.seleccionarPreguntaFinal("S\u00ed, funciona correctamente");
		sleep(8000);
		buscarYClick(driver.findElements(By.id("HlrDeregister_nextBtn")), "equals", "continuar");
		sleep(8000);
		Tech.categoriaRed("Fuera del Area de Cobertura");
		sleep(8000);
		buscarYClick(driver.findElements(By.id("AddressSection_nextBtn")), "equals", "continuar");
		String caso = null;
		List <WebElement> lista = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for(WebElement x : lista) {
			if(x.getText().toLowerCase().contains("su gesti\u00f3n")) {
				caso = x.findElement(By.tagName("div")).findElement(By.tagName("span")).findElement(By.tagName("strong")).getText();
			}
		}
		System.out.println(caso);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso);
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));	
	}
	
	@Test (groups = {"GestionesPerfilOficina","DiagnosticoInconvenientes"}, dataProvider = "Diagnostico")
	public void TS119210_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_realizar_ni_recibir_llamadas(String sDNI, String sLinea) throws Exception  {
		imagen = "TS119210";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage Tech = new TechnicalCareCSRAutogestionPage(driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(3000);
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo realizar ni recibir llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		tech.categoriaRed("No son las antenas (Verde)");
		try {
			buscarYClick(driver.findElements(By.id("AddressSection_nextBtn")), "equals", "continuar");
		}
		catch (Exception x) {		
		}
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("CoverageOkNetMessage")));
		String caso = null;
		WebElement gesti = driver.findElement(By.id("CoverageOkNetMessage")).findElement(By.tagName("div")).findElement(By.tagName("p")).findElements(By.tagName("p")).get(1).findElement(By.tagName("span")).findElement(By.tagName("strong"));
		caso = gesti.getText();
		System.out.println("El numero de caso es: "+caso);
		driver.switchTo().defaultContent();
		Tech.buscarCaso(caso);
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("srchErrorDiv_Case")));
		String estado= driver.findElement(By.xpath("//*[@id='Case_body']/table/tbody/tr[2]/td[3]")).getText();
		Assert.assertTrue(estado.equalsIgnoreCase("derivada"));
		Assert.assertTrue(Tech.cerrarCaso("Resuelta exitosa", "Consulta"));	
	}
	
	@Test (groups = {"GestionesPerfilOficina","Diagnostico/Inconvenientes"}, dataProvider = "Diagnostico")
	public void TS119221_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_realizar_ni_recibir_llamadas_Sin_Locacion_Evento_Masivo(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS119221";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRDiagnosticoPage Tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage (driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		Select motiv = new Select(driver.findElement(By.id("Motive")));
		motiv.selectByVisibleText("No puedo realizar ni recibir llamadas");
		//selectByText(driver.findElement(By.id("Motive")), "No puedo realizar ni recibir llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.id("BalanceValidation_nextBtn")));
		String saldo = driver.findElement(By.xpath("//*[@id=\"AvailableVoiceBalanceDisplay\"]/div/p/div/p[2]/span")).getText();
		System.out.println("El saldo es: " +saldo);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(30000);
		driver.switchTo().frame(cambioFrame(driver, By.id("NetworkCategory_nextBtn")));
		String informacion = driver.findElement(By.xpath("//*[@id=\"CommercialInfo\"]/div/p/div")).getText();
		System.out.println(informacion);
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(15000);
		/*driver.switchTo().frame(cambioFrame(driver, By.id("BlackListValidationOk_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("BlackListValidationOk_nextBtn")).click();
		sleep(8000);*/
		driver.switchTo().frame(cambioFrame(driver, By.id("AddressSection_nextBtn")));
		Tech.categoriaRed("Encontr\u00e9 un problema (Rojo)");
		WebElement evento = driver.findElement(By.id("MassiveIncidentLookUp"));
		evento.click();
		evento.sendKeys("Evento Masivo");
		driver.findElement(By.id("AddressSection_nextBtn")).click();
		sleep(8000);
		String caso = driver.findElement(By.xpath("//*[@id=\"MassiveIncidentMessage\"]/div/p/p[2]/span/strong")).getText();
		System.out.println(caso);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso);
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "DiagnosticoInconvenientes","E2E", "Ciclo3"}, dataProvider = "Diagnostico")
	public void TS119262_CRM_Movil_REPRO_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Llamar_desde_otro_pais_Sin_Locacion_NO_recupera_locacion_Geo_Fuera_de_area_de_cobertura_OfCom(String sDNI, String sLinea) throws Exception  {
		imagen = "TS119262";
		detalles = null;
		detalles = imagen + " -Diagnostico: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage Tech = new TechnicalCareCSRAutogestionPage (driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo llamar desde otro pa\u00eds");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		page.seleccionarPreguntaFinal("S\u00ed");
		buscarYClick(driver.findElements(By.id("BalanceValidation_nextBtn")), "equals", "continuar");
		tech.categoriaRed("Desregistrar");
		buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
		page.seleccionarPreguntaFinal("S\u00ed");
		buscarYClick(driver.findElements(By.id("DeregisterSpeech_nextBtn")), "equals", "continuar");
		page.seleccionarPreguntaFinal("S\u00ed");
		buscarYClick(driver.findElements(By.id("BlackListValidationOk_nextBtn")), "equals", "continuar");
		tech.categoriaRed("Fuera del Area de Cobertura");
		sleep(10000);
		String caso = null;
		List <WebElement> lista = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for(WebElement x : lista) {
			if(x.getText().toLowerCase().contains("su gesti\u00f3n")) {
				caso = x.findElement(By.tagName("div")).findElement(By.tagName("span")).findElement(By.tagName("strong")).getText();
			}
		}
		System.out.println(caso);
		driver.switchTo().defaultContent();
		Tech.buscarCaso(caso);
		Assert.assertTrue(Tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","Diagnostico/Inconvenientes"}, dataProvider = "Diagnostico")
	public void TS119266_CRM_Movil_PRE_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Navegar_sin_rellamado_NO_BAM(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS119266";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRDiagnosticoPage Tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage (driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(8000);
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo navegar");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(8000);
		String cuota = driver.findElement(By.xpath("//*[@id=\"AvailableDataQuotaDisplay\"]/div/p/div/p[2]/span")).getText();
		String saldo = driver.findElement(By.xpath("//*[@id=\"AvailableDataBalanceDisplay\"]/div/p/div/p[2]/span")).getText();
		System.out.println("Saldo: " + saldo);
		System.out.println("Cuota: " + cuota);
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(15000);
		buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
		driver.switchTo().frame(cambioFrame(driver, By.id("BlackListValidationOk_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-radio--faux.ng-scope")), "equals", "si");
		driver.findElement(By.id("BlackListValidationOk_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RecentConfiguration_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("RecentConfiguration_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		Tech.categoriaRed("No son las antenas (Verde)");
		try {
			buscarYClick(driver.findElements(By.id("AddressSection_nextBtn")), "equals", "continuar");
		}
		catch (Exception x) {		
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SignalValidation_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("SignalValidation_nextBtn")).click();
//		driver.switchTo().frame(cambioFrame(driver, By.id("MobileConfigurationSending_nextBtn")));
//		List <WebElement> opcion = driver.findElements(By.cssSelector(".imgItemContainer.ng-scope"));
//		for(WebElement x : opcion ) {
//			if(x.getText().toLowerCase().equals("si, enviar configuraci\u00f3n")) {
//				x.click();
//			}
//		}
//		driver.findElement(By.id("MobileConfigurationSending_nextBtn")).click();
//		sleep(8000);
		sleep(10000);
		String caso = null;
		List <WebElement> lista = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for(WebElement x : lista) {
			if(x.getText().toLowerCase().contains("su gesti\u00f3n")) {
				caso = x.findElement(By.tagName("div")).findElement(By.tagName("span")).findElement(By.tagName("strong")).getText();
			}
		}
		System.out.println(caso);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso);
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","Diagnostico/Inconvenientes"}, dataProvider = "Diagnostico")
	public void TS119267_CRM_Movil_PRE_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Navegar_Sin_conciliar_ni_desregistrar_Envio_de_configuracion(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS119267";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage (driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(8000);
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo navegar");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(8000);
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("NetworkCategory_nextBtn")));
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-radio--faux.ng-scope")), "equals", "si");
		driver.findElement(By.id("BlackListValidationOk_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RecentConfiguration_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("RecentConfiguration_nextBtn")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("MobileConfigurationSending_nextBtn")));
		//buscarYClick(driver.findElements(By.className("borderOverlay")), "contains", "enviar");
		cCC.obligarclick(driver.findElements(By.className("borderOverlay")).get(2));
		driver.findElement(By.id("MobileConfigurationSending_nextBtn")).click();
		sleep(8000);
		String caso = null;
		List <WebElement> lista = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for(WebElement x : lista) {
			if(x.getText().toLowerCase().contains("su gesti\u00f3n")) {
				caso = x.findElement(By.tagName("div")).findElement(By.tagName("span")).findElement(By.tagName("strong")).getText();
			}
		}
		System.out.println(caso);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso);
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("srchErrorDiv_Case")));
		String estado= driver.findElement(By.xpath("//*[@id='Case_body']/table/tbody/tr[2]/td[3]")).getText();
		Assert.assertTrue(estado.equalsIgnoreCase("resuelta exitosa"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","Diagnostico/Inconvenientes"}, dataProvider = "Diagnostico")
	public void TS119275_CRM_Movil_PRE_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Navegar_ANTENA_ROJO_Evento_Masivo_NO_BAM(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS119275";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRDiagnosticoPage Tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage (driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(3000);
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo navegar");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(8000);
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
		driver.switchTo().frame(cambioFrame(driver, By.id("BlackListValidationOk_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-radio--faux.ng-scope")), "equals", "si");
		driver.findElement(By.id("BlackListValidationOk_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RecentConfiguration_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("RecentConfiguration_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		Tech.categoriaRed("Encontr\u00e9 un problema (Rojo)");
		driver.findElement(By.id("MassiveIncidentLookUp")).sendKeys("Evento Masivo");
		try {
			buscarYClick(driver.findElements(By.id("AddressSection_nextBtn")), "equals", "continuar");
		}
		catch (Exception x) {		
		}
		sleep(8000);
		buscarYClick(driver.findElements(By.id("AddressSection_nextBtn")), "equals", "continuar");
		String caso_1 = driver.findElement(By.xpath("//*[@id=\"MassiveIncidentMessage\"]/div/p/p[2]/span/strong")).getText();
		System.out.println(caso_1);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso_1);
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","Diagnostico/Inconvenientes"}, dataProvider = "Diagnostico")
	public void TS119279_CRM_Movil_PRE_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Navegar_CONCILIAR_NO_BAM(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS119279";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage (driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(8000);
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo navegar");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(8000);
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("NetworkCategory_nextBtn")));
		cCC.obligarclick(driver.findElements(By.className("borderOverlay")).get(0));
		buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
		Assert.assertTrue(false); // Genera error al llegar a este punto.
//		sleep(30000);
//		String caso_1 = driver.findElement(By.xpath("//*[@id=\"IncorrectCategoriesMessage\"]/div/p/p[2]/span/strong")).getText();
//		System.out.println(caso_1);
//		driver.switchTo().defaultContent();
//		tech.buscarCaso(caso_1);
//		BasePage cambioFrameByID=new BasePage();
//		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
//		driver.findElement(By.xpath("//*[@id='Case_body']/table/tbody/tr[2]/th/a")).click();		
//		sleep(5000);
//		boolean a = false;
//		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ep")));
//		List <WebElement> texto = driver.findElements(By.className("dataCol"));
//		for(WebElement x : texto) {
//			if(x.getText().toLowerCase().contains("en proceso")) {
//				a = true;
//			}
//		}
//		Assert.assertTrue(a);
	}
	
	@Test (groups = {"GestionesPerfilOficina","Diagnostico/Inconvenientes"}, dataProvider = "Diagnostico")
	public void TS119283_CRM_Movil_REPRO_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_navegar_Antena_rojo_NO_BAM(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS119283";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRDiagnosticoPage Tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage (driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(8000);
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo navegar");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(8000);
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
		driver.switchTo().frame(cambioFrame(driver, By.id("BlackListValidationOk_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-radio--faux.ng-scope")), "equals", "si");
		driver.findElement(By.id("BlackListValidationOk_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RecentConfiguration_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("RecentConfiguration_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		Tech.categoriaRed("Encontr\u00e9 un problema (Rojo)");
		driver.findElement(By.id("MassiveIncidentLookUp")).sendKeys("Evento Masivo");
		try {
			buscarYClick(driver.findElements(By.id("AddressSection_nextBtn")), "equals", "continuar");
		}
		catch (Exception x) {		
		}
		sleep(8000);
		buscarYClick(driver.findElements(By.id("AddressSection_nextBtn")), "equals", "continuar");
		String caso_1 = driver.findElement(By.xpath("//*[@id=\"MassiveIncidentMessage\"]/div/p/p[2]/span/strong")).getText();
		System.out.println(caso_1);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso_1);
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","Diagnostico/Inconvenientes"}, dataProvider = "Diagnostico")
	public void TS119286_CRM_Movil_PRE_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_Navega_con_lentitud_Fuera_del_area_de_cobertura_NO_BAM(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS119286";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRDiagnosticoPage Tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage (driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(8000);
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "Navega lento");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(8000);
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RecentConfiguration_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("RecentConfiguration_nextBtn")).click();
		sleep(8000);
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		Tech.categoriaRed("Fuera del Area de Cobertura");
		sleep(8000);
		String caso_1 = driver.findElement(By.xpath("//*[@id=\"OutOfCoverageMessage\"]/div/p/p[2]/span/strong")).getText();
		System.out.println(caso_1);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso_1);
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"GestionesPerfilTelefonico","Diagnostico/Inconvenientes"},  dataProvider = "Diagnostico")
	public void TS105845_CRM_Movil_REPRO_Autogestion_APP_Abre_aplicacion_y_cierra_automaticamente_No_Resuelto(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS105845";
		detalles = null;
		detalles = imagen + "- Autogestion - DNI: "+sDNI;
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		cc.irAGestion("diagn\u00f3stico de autogesti\u00f3n");
		sleep(15000);
		tech.listadoDeSeleccion("APP", "C", "Abre aplicaci\u00f3n y cierra autom\u00e1ticamente");
		sleep(4000);
		tech.verificarNumDeGestion();
		driver.switchTo().frame(cambioFrame(driver, By.id("srchErrorDiv_Case")));
		String estado = driver.findElement(By.xpath("//*[@id=\"Case_body\"]/table/tbody/tr[2]/td[3]")).getText();
//		Assert.assertTrue(estado.equalsIgnoreCase("Informada"));
		Assert.assertTrue(estado.equalsIgnoreCase("Iniciada"));
		//Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "DiagnosticoInconvenientes","E2E", "Ciclo3"}, dataProvider = "Diagnostico")
	public void TS111300_CRM_Movil_REPRO_Diagnostico_SVA_Telefonico_SMS_Saliente_SMS_a_fijo_Geo_No_Ok_Desregistrar(String sDNI, String sLinea) throws Exception  {
		imagen = "TS111300";
		detalles = null;
		detalles = imagen + " -Diagnostico Inconveniente - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		driver.switchTo().frame(cambioFrame(driver, By.className("card-info")));
		sleep(15000);
		cCC.irAMisServicios();
		tech.verDetalles();
	    tech.clickDiagnosticarServicio("sms", "SMS Saliente", true);
	    tech.selectionInconvenient("SMS a fijo");
	    tech.continuar();
	    tech.seleccionarRespuesta("no");
	    buscarYClick(driver.findElements(By.id("KnowledgeBaseResults_nextBtn")), "equals", "continuar");
	    page.seleccionarPreguntaFinal("S\u00ed");
	    buscarYClick(driver.findElements(By.id("BalanceValidation_nextBtn")), "equals", "continuar");
	    tech.categoriaRed("Desregistrar");
	    sleep(5000);
	    Assert.assertTrue(driver.findElement(By.cssSelector("[class='slds-form-element vlc-flex vlc-slds-radio-control ng-scope ng-valid ng-valid-required ng-dirty ng-valid-parse'] [class='slds-radio ng-scope itemSelected']")).getText().equalsIgnoreCase("Desregistrar"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "DiagnosticoInconvenientes","E2E", "Ciclo3"}, dataProvider = "Diagnostico")
	public void TS112441_CRM_Movil_REPRO_Diagnostico_SVA_Telefonico_SMS_Entrante_No_Recibe_De_Un_Numero_En_Particular_Geo_Ok_Rojo(String sDNI, String sLinea) throws Exception  {
		imagen = "TS112441";
		detalles = null;
		detalles = imagen + " -Diagnostico Inconveniente - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(12000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		driver.switchTo().frame(cambioFrame(driver, By.className("card-info")));
		sleep(15000);
		cCC.irAMisServicios();
		tech.verDetalles();
	    tech.clickDiagnosticarServicio("sms", "SMS Entrante", true);
	    tech.selectionInconvenient("No recibe de un n\u00famero particular");
	    tech.continuar();
	    tech.seleccionarRespuesta("no");
	    buscarYClick(driver.findElements(By.id("KnowledgeBaseResults_nextBtn")), "equals", "continuar");
	    page.seleccionarPreguntaFinal("No");
	    buscarYClick(driver.findElements(By.id("BalanceValidation_nextBtn")), "equals", "continuar");
	    Assert.assertTrue(driver.findElements(By.cssSelector("[class='slds-page-header vlc-slds-page--header']")).get(3).getText().contains("Saldo Insuficiente"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico","Diagnostico/Inconvenientes"}, dataProvider = "Diagnostico")
	public void TS119171_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_realizar_llamadas_Conciliacion_Exitosa(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS119171";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage (driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(8000);
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo realizar llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("BalanceValidation_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("NetworkCategory_nextBtn")));
		cCC.obligarclick(driver.findElements(By.className("borderOverlay")).get(0));
		buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
		sleep(20000);
		String caso_1 = driver.findElement(By.xpath("//*[@id=\"IncorrectCategoriesMessage\"]/div/p/p[2]/span/strong")).getText();
		System.out.println(caso_1);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso_1);
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test(groups = { "GestionesPerfilTelefonico","DiagnosticoInconvenientes","Ciclo 3", "E2E" }, priority = 1, dataProvider = "Diagnostico") 
	public void TS119198_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_recibir_llamadas_Conciliacion_Exitosa(String sDNI, String sLinea){
		imagen = "TS119198";
		detalles = null;
		detalles = imagen + " -Diagnostico Inconveniente - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage Tech = new TechnicalCareCSRAutogestionPage (driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		sleep(8000);
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.id("Motive")));
		Select motiv = new Select (driver.findElement(By.id("Motive")));
		motiv.selectByVisibleText("No puedo recibir llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(3000);
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(8000);
		tech.categoriaRed("Conciliar");
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(45000);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.id("IncorrectCategoriesMessage")));
		WebElement caso = driver.findElement(By.id("IncorrectCategoriesMessage")).findElement(By.tagName("div")).findElement(By.tagName("p")).findElements(By.tagName("p")).get(1).findElement(By.tagName("span")).findElement(By.tagName("strong"));
		System.out.println(caso.getText());
		String Ncaso = caso.getText();
		System.out.println("El numero de caso es: "+Ncaso);
		driver.switchTo().defaultContent();
		sleep(1000);
		WebElement Buscador = driver.findElement(By.id("phSearchInput"));
		Buscador.sendKeys(Ncaso);
		Buscador.submit();
		sleep(14000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Case_body")));
		WebElement gest = driver.findElement(By.id("Case_body")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElement(By.cssSelector(".dataRow.even.last.first")).findElements(By.tagName("td")).get(2);
		System.out.println(gest.getText());
		Assert.assertTrue(Tech.cerrarCaso("Resuelta exitosa", "Consulta"));
		sleep(14000);
		mk.closeAllTabs(driver);
		Buscador.click();
		Buscador.clear();
		Buscador.sendKeys(Ncaso);
		Buscador.submit();
		sleep(14000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Case_body")));
		gest = driver.findElement(By.id("Case_body"));	
		List<WebElement> x = mk.traerColumnaElement(gest, 5, 3);
		for (WebElement a :x) {
		System.out.println(a.getText());
			if (a.getText().toLowerCase().contains("resuelta exitosa")){
			}
		Assert.assertTrue(a.getText().equals("Resuelta exitosa")||a.getText().equals("Realizada exitosa"));
		}
	}
	
	@Test(groups = { "GestionesPerfilTelefonico","DiagnosticoInconvenientes","Ciclo 3", "E2E" }, priority = 1, dataProvider = "Diagnostico") 
	public void TS119231_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_realizar_ni_recibir_llamadas_Sin_Locacion_Equipo_sin_senal(String sDNI, String sLinea) throws InterruptedException{
		imagen = "TS119231";
		detalles = null;
		detalles = imagen + " -Diagnostico Inconveniente - DNI: " + sDNI;
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage otherTech = new TechnicalCareCSRAutogestionPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cc.irAGestionEnCard("Diagn\u00f3stico");
		sleep(8000);
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.id("Motive")));
		Select motiv = new Select (driver.findElement(By.id("Motive")));
		motiv.selectByVisibleText("No puedo realizar ni recibir llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(3000);
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		tech.categoriaRed("No son las antenas (Verde)");
		sleep(15000);
		buscarYClick(driver.findElements(By.id("AddressSection_nextBtn")), "equals", "continuar");
		driver.switchTo().frame(cambioFrame(driver, By.id("CoverageOkNetMessage")));
		WebElement caso = driver.findElement(By.id("CoverageOkNetMessage")).findElement(By.tagName("div")).findElement(By.tagName("p")).findElements(By.tagName("p")).get(1).findElement(By.tagName("span")).findElement(By.tagName("strong"));
		System.out.println(caso.getText());
		String Ncaso = caso.getText();
		sleep(15000);
		driver.switchTo().defaultContent();
		otherTech.buscarCaso(Ncaso);
		otherTech.cerrarCaso("Realizada Exitosa", "Consulta");
		Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"cas7_ileinner\"]")).getText().equalsIgnoreCase("realizada exitosa"));
		
	}
	
	@Test(groups = { "GestionesPerfilTelefonico","Ciclo 3", "E2E","DiagnosticoInconveniente" }, priority = 1, dataProvider = "Diagnostico") 
	public void TS119245_CRM_Movil_REPRO_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Llamar_desde_otro_pais_Conciliacion_Exitosa_Telefonico(String sDNI, String sLinea){
		imagen = "TS119245";
		detalles = null;
		detalles = imagen + " -Diagnostico Inconveniente - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);;
		TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		sleep(8000);
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.id("Motive")));
		Select motiv = new Select (driver.findElement(By.id("Motive")));
		motiv.selectByVisibleText("No puedo navegar");
		sleep(5000);
		driver.findElement(By.id("MotiveIncidentSelect_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DataQuotaQuery_nextBtn")));
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
		tech.categoriaRed("Conciliar");
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(25000);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.id("IncorrectCategoriesMessage")));
		WebElement caso = driver.findElement(By.id("IncorrectCategoriesMessage")).findElement(By.tagName("div")).findElement(By.tagName("p")).findElements(By.tagName("p")).get(1).findElement(By.tagName("span")).findElement(By.tagName("strong"));
		System.out.println(caso.getText());
		String Ncaso = caso.getText();
		System.out.println("El numero de caso es: "+Ncaso);
		sleep(3000);
		driver.switchTo().defaultContent();
		WebElement Buscador = driver.findElement(By.id("phSearchInput"));
		Buscador.sendKeys(Ncaso);
		Buscador.submit();
		sleep(14000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Case_body")));
		WebElement gest = driver.findElement(By.id("Case_body")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElement(By.cssSelector(".dataRow.even.last.first")).findElements(By.tagName("td")).get(2);
		System.out.println(gest.getText());
		sleep(5000);
		driver.navigate().refresh();
		Assert.assertTrue(gest.equals("Realizada exitosa"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico","Diagnostico/Inconvenientes"}, dataProvider = "Diagnostico")
	public void TS119269_CRM_Movil_PRE_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Navegar_con_rellamado_NO_BAM(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS119269";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRDiagnosticoPage Tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage (driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		Select motiv = new Select(driver.findElement(By.id("Motive")));
		motiv.selectByVisibleText("No puedo navegar");
		//selectByText(driver.findElement(By.id("Motive")), "No puedo realizar ni recibir llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(8000);
		String cuota = driver.findElement(By.xpath("//*[@id=\"AvailableDataQuotaDisplay\"]/div/p/div/p[2]/span")).getText();
		String saldo = driver.findElement(By.xpath("//*[@id=\"AvailableDataBalanceDisplay\"]/div/p/div/p[2]/span")).getText();
		System.out.println("Saldo: " + saldo);
		System.out.println("Cuota: " + cuota);
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("NetworkCategory_nextBtn")));
		String informacion = driver.findElement(By.xpath("//*[@id=\"CommercialInfo\"]/div/p/div")).getText();
		System.out.println(informacion);
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("BlackListValidationOk_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("BlackListValidationOk_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RecentConfiguration_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("RecentConfiguration_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("AddressSection_nextBtn")));
		Tech.categoriaRed("No son las antenas (Verde)");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SignalValidation_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("SignalValidation_nextBtn")).click();
		sleep(8000);
		/*driver.switchTo().frame(cambioFrame(driver, By.id("MobileConfigurationSending_nextBtn")));
		List <WebElement> opcion = driver.findElements(By.cssSelector(".imgItemContainer.ng-scope"));
		for(WebElement x : opcion ) {
			if(x.getText().toLowerCase().equals("si, enviar configuraci\u00f3n")) {
				x.click();
			}
		}
		driver.findElement(By.id("MobileConfigurationSending_nextBtn")).click();*/
		sleep(8000);
		String caso = driver.findElement(By.xpath("//*[@id=\"MobileConfigSendingMessage\"]/div/p/h1/span/strong")).getText();
		System.out.println(caso);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso);
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico","Diagnostico/Inconvenientes"}, dataProvider = "Diagnostico")
	public void TS119271_CRM_Movil_PRE_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Navegar_SIN_SEnAL_NO_BAM(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS119271";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRDiagnosticoPage Tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage (driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(8000);
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo navegar");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DataQuotaQuery_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
		driver.switchTo().frame(cambioFrame(driver, By.id("BlackListValidationOk_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-radio--faux.ng-scope")), "equals", "si");
		driver.findElement(By.id("BlackListValidationOk_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RecentConfiguration_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("RecentConfiguration_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		Tech.categoriaRed("Fuera del Area de Cobertura");
		sleep(8000);
		String caso_1 = driver.findElement(By.xpath("//*[@id=\"OutOfCoverageMessage\"]/div/p/p[2]/span/strong")).getText();
		System.out.println(caso_1);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso_1);
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico","Diagnostico/Inconvenientes"}, dataProvider = "Diagnostico")
	public void TS119272_CRM_Movil_PRE_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Navegar_SIN_CUOTA_NO_BAM(String sDNI, String sLinea) throws Exception  {
		imagen = "TS119272";
		detalles = null;
		detalles = imagen + " -ServicioTecnico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(6000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo navegar");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		page.seleccionarPreguntaFinal("No");
		buscarYClick(driver.findElements(By.id("DataQuotaQuery_nextBtn")), "equals", "continuar");
		driver.switchTo().frame(cambioFrame(driver, By.id("UnavailableQuotaMessage")));
		WebElement MediosDispon = driver.findElement(By.className("ng-binding")).findElement(By.xpath("//*[@id='UnavailableQuotaMessage']/div/p/p[1]/span"));
		Assert.assertTrue(MediosDispon.getText().equalsIgnoreCase("Prob\u00e1 realizar una recarga o comprar un pack de datos"));
		String caso = driver.findElement(By.xpath("//*[@id='UnavailableQuotaMessage']/div/p/p[2]/span/strong")).getText();
		System.out.println(caso);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso);
		Assert.assertTrue(tech.cerrarCaso("Informada", "Consulta"));
	}
	
	@Test(groups = { "GestionesPerfilTelefonico","DiagnosticoInconvenientes","Ciclo 3", "E2E" }, priority = 1, dataProvider = "Diagnostico") 
	public void TS119281_CRM_Movil_REPRO_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Navegar_CONCILIACION_EXITOSA_NO_BAM_Telefonico(String sDNI, String sLinea){
		imagen = "TS119281";
		detalles = null;
		detalles = imagen + " -Diagnostico Inconveniente - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		sleep(8000);
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.id("Motive")));
		Select motiv = new Select (driver.findElement(By.id("Motive")));
		motiv.selectByVisibleText("No puedo navegar");
		sleep(5000);
		driver.findElement(By.id("MotiveIncidentSelect_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DataQuotaQuery_nextBtn")));
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
		tech.categoriaRed("Conciliar");
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(45000);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.id("IncorrectCategoriesMessage")));
		WebElement caso = driver.findElement(By.id("IncorrectCategoriesMessage")).findElement(By.tagName("div")).findElement(By.tagName("p")).findElements(By.tagName("p")).get(1).findElement(By.tagName("span")).findElement(By.tagName("strong"));
		System.out.println(caso.getText());
		String Ncaso = caso.getText();
		System.out.println("El numero de caso es: "+Ncaso);
		driver.switchTo().defaultContent();
		sleep(1000);
		WebElement Buscador = driver.findElement(By.id("phSearchInput"));
		Buscador.sendKeys(Ncaso);
		Buscador.submit();
		sleep(14000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Case_body")));
		WebElement gest = driver.findElement(By.id("Case_body")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElement(By.cssSelector(".dataRow.even.last.first")).findElements(By.tagName("td")).get(2);
		System.out.println(gest.getText());
		Assert.assertTrue(gest.getText().equals("Realizada exitosa"));
	}
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test (groups = {"GestionesPerfilAgente", "DiagnosticoIncoveniente","E2E", "Ciclo3"}, dataProvider = "Diagnostico")
	public void TS119283_CRM_Movil_REPRO_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_navegar_Antena_rojo_NO_BAM_Agente(String sDNI, String sLinea) throws Exception  {
		imagen = "TS119283";
		detalles = null;
		detalles = imagen + " -ServicioTecnico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");// campo diagnostico no aparece en perfil agente
	    tech.clickDiagnosticarServicio("datos", "Datos", true);
	    tech.selectionInconvenient("No puedo navegar");
	    tech.continuar();
	    tech.seleccionarRespuesta("si");
	    buscarYClick(driver.findElements(By.id("KnowledgeBaseResults_nextBtn")), "equals", "continuar");
	}
	
	//----------------------------------------------- ADMIN FUNCIONAL -------------------------------------------------------\\
	
	@Test (groups = {"GestionesPerfilAdminFuncional", "Diagnostico/Inconvenientes", }, dataProvider = "Diagnostico")
	public void TS105437_CRM_Movil_Repro_Autogestion_WEB_Inconveniente_con_Informe_de_pago_Resuelto(String cDNI, String cLinea) throws InterruptedException {
		imagen = "TS105437";
		detalles = null;
		detalles = imagen + "- Autogestion - DNI: "+cDNI;
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		searchAndClick(driver, "Diagn\u00f3stico de Autogesti\u00f3n");
		tech.listadoDeSeleccion("WEB", "Otro", "Inconveniente con Informe de pago");
		sleep(4000);
		tech.verificarNumDeGestion();
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilAdminFuncional", "Diagnostico/Inconvenientes", }, dataProvider = "Diagnostico")
	public void TS105441_CRM_Movil_Repro_Autogestion_WEB_Informacion_Incompleta_Resuelto(String cDNI, String cLinea) throws InterruptedException {
		imagen = "TS105441";
		detalles = null;
		detalles = imagen + "- Autogestion - DNI: "+cDNI;
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		searchAndClick(driver, "Diagn\u00f3stico de Autogesti\u00f3n");
		tech.listadoDeSeleccion("WEB", "Otro", "Informaci\u00f3n Incompleta");
		sleep(4000);
		tech.verificarNumDeGestion();
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilAdminFuncional", "Diagnostico/Inconvenientes"}, dataProvider = "Diagnostico")
	public void TS111042_CRM_Movil_REPRO_Diagnostico_SVA_Telefonico_SMS_Saliente_SMS_a_fijo_Geo_No_Ok_Conciliacion_No_habia_nada_que_conciliar(String sDNI, String sLinea) throws Exception  {
		imagen = "TS111042";
		detalles = null;
		detalles = imagen + " -Diagnostico Inconveniente - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAProductosyServicios();
		tech.verDetalles();
	    tech.clickDiagnosticarServicio("sms", "SMS Saliente", true);
	    tech.selectionInconvenient("SMS a fijo");
	    tech.continuar();
	    tech.seleccionarRespuesta("no");
	    buscarYClick(driver.findElements(By.id("KnowledgeBaseResults_nextBtn")), "equals", "continuar");
	    page.seleccionarPreguntaFinal("S\u00ed");
	    buscarYClick(driver.findElements(By.id("BalanceValidation_nextBtn")), "equals", "continuar");
	    tech.categoriaRed("Conciliar");
	    buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
	    //pendiente por terminar// error "createFDOAutoReqDate"// preguntar cual es el final del flujo
	}
	
	@Test (groups = {"GestionesPerfilAdminFuncional", "Diagnostico/Inconvenientes"}, dataProvider = "Diagnostico")
	public void TS111043_CRM_Movil_REPRO_Diagnostico_SVA_Telefonico_SMS_Saliente_SMS_Emision_a_algun_destino_en_particular_Geo_No_Ok_Conciliacion_No_habia_nada_que_conciliar(String sDNI, String sLinea) throws Exception  {
		imagen = "TS111043";
		detalles = null;
		detalles = imagen + " -Diagnostico Inconveniente - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "equals", "mis servicios");
		sleep(5000);
		tech.verDetalles();
	    tech.clickDiagnosticarServicio("sms", "SMS Saliente", true);
	    tech.selectionInconvenient("SMS Emisi\u00f3n a alg\u00fan destino en particular");
	    tech.continuar();
	    tech.seleccionarRespuesta("no");
	    buscarYClick(driver.findElements(By.id("KnowledgeBaseResults_nextBtn")), "equals", "continuar");
	    page.seleccionarPreguntaFinal("S\u00ed");
	    buscarYClick(driver.findElements(By.id("BalanceValidation_nextBtn")), "equals", "continuar");
	    tech.categoriaRed("Conciliar");
	    buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
	    //pendiente por terminar// error "createFDOAutoReqDate"// preguntar cual es el final del flujo
	}
	
	@Test (groups = {"GestionesPerfilAdminFuncional", "DiagnosticoInconveniente","E2E", "Ciclo3"}, dataProvider = "Diagnostico")
	public void TS111487_CRM_Movil_REPRO_Diagnostico_SVA_Con_Modificacion_en_el_Equipo_Telefonico_SMS_Entrante_No_Recibe_De_Un_Numero_En_Particular_Geo_No_Ok_Desregistrar(String sDNI, String sLinea) throws Exception  {
		imagen = "TS111487";
		detalles = null;
		detalles = imagen + " -ServicioTecnico - DNI: "+sDNI+" - Linea: "+sLinea;
		boolean desregistrar = false;
		CustomerCare cCC=new CustomerCare(driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "equals", "mis servicios");
		sleep(3000);
		tech.verDetalles();
	    tech.clickDiagnosticarServicio("sms", "SMS Entrante", true);
	    tech.selectionInconvenient("No recibe de un n\u00famero particular");
	    tech.continuar();
	    tech.seleccionarRespuesta("no");
	    buscarYClick(driver.findElements(By.id("KnowledgeBaseResults_nextBtn")), "equals", "continuar");
	    page.seleccionarPreguntaFinal("S\u00ed");
	    buscarYClick(driver.findElements(By.id("BalanceValidation_nextBtn")), "equals", "continuar");
	    tech.categoriaRed("Desregistrar");
	    driver.findElement(By.id("NetworkCategory_nextBtn")).click();
	    sleep(4000);
	    driver.findElement(By.id("DeregisterSpeech_nextBtn")).click();    
	    sleep(6000);
	    driver.findElement(By.id("HLR_IFS_S132_Button")).isDisplayed();
	    buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
	    sleep(6000);
	    driver.findElement(By.id("Deregister_nextBtn")).click();
	    buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no, sigue con inconvenientes");
	    sleep(3000);
	    buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");	    
	    sleep(8000);
	    driver.switchTo().frame(cambioFrame(driver, By.id("MobileConfigurationSending_nextBtn")));
		List <WebElement> opcion = driver.findElements(By.cssSelector(".imgItemContainer.ng-scope"));
		for(WebElement x : opcion ) {
			if(x.getText().toLowerCase().equals("si, enviar configuraci\u00f3n")) {
				x.click();
			}
		}
		driver.findElement(By.id("MobileConfigurationSending_nextBtn")).click();
		sleep(8000);
	    for(WebElement x : driver.findElements(By.className("slds-form-element__control"))) {
	    	if(x.getText().toLowerCase().contains("\u00a1tu configuraci\u00f3n se envi\u00f3 con \u00e9xito\u0021")) {
	    		desregistrar = true;
	    	}
	    	
	    }
	    Assert.assertTrue(desregistrar);
	}
}
