package Tests;


import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.bag.SynchronizedSortedBag;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.Accounts;
import Pages.BasePage;
import Pages.CBS;
import Pages.ContactSearch;
import Pages.CustomerCare;
import Pages.Marketing;
import Pages.PagePerfilTelefonico;
import Pages.SalesBase;
import Pages.TechCare_Ola1;
import Pages.TechnicalCareCSRAutogestionPage;
import Pages.TechnicalCareCSRDiagnosticoPage;
import Pages.setConexion;

public class GestionesPerfilAdminFuncional extends TestBase{

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	private Marketing mk;
	List <String> datosOrden =new ArrayList<String>();
	PagePerfilTelefonico ppt;
	String imagen;
	String detalles;
	
	@BeforeClass(alwaysRun=true)
	public void init() {
		driver = setConexion.setupEze();
		sleep(5000);
		cc = new CustomerCare(driver);
		sb = new SalesBase(driver);
		mk = new Marketing(driver);
		loginAdminFuncional(driver);
		sleep(22000);
		try {
			cc.cajonDeAplicaciones("Consola FAN");
		} catch(Exception e) {
			sleep(3000);
			driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
			sleep(6000);
		}
		driver.switchTo().defaultContent();
		sleep(3000);
		
	}
	
	@BeforeMethod(alwaysRun=true)
	public void setup() throws Exception {
		sleep(10000);
		goToLeftPanel2(driver, "Inicio");
		sleep(15000);
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
		List<WebElement> botones = driver.findElements(By.tagName("button"));
		for (WebElement UnB : botones) {
			System.out.println(UnB.getText());
			if(UnB.getText().equalsIgnoreCase("gesti\u00f3n de clientes")) {
				UnB.click();
				break;
			}
		}
		
		sleep(15000);
	}
	
	//@AfterMethod(alwaysRun=true)
	public void after() throws IOException {
		datosOrden.add(detalles);
		guardarListaTxt(datosOrden);
		datosOrden.clear();
		tomarCaptura(driver,imagen);
	}
	
	//@AfterClass(alwaysRun=true)
	public void quit() throws IOException {
		//guardarListaTxt(datosOrden);
		driver.quit();
		sleep(5000);
	}
	
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
		sleep(10000);
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
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAProductosyServicios();
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
		cCC.irAProductosyServicios();
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
	    sleep(4000);
	    driver.findElement(By.id("HLR_IFS_S132_Button")).isDisplayed();
	    buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
	    sleep(6000);
	    driver.findElement(By.id("Deregister_nextBtn")).click();
	    buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed, funciona correctamente");
	    sleep(8000);
	    for(WebElement x : driver.findElements(By.className("slds-form-element__control"))) {
	    	if(x.getText().toLowerCase().contains("�tu caso se resolvi\u00f3 con \u00e9xito!")) {
	    		desregistrar = true;
	    	}
	    	
	    }
	    Assert.assertTrue(desregistrar);
	}
	
	@Test (groups = {"GestionesReclamosDeRed", "Diagnostico/Inconvenientes", }, dataProvider = "Diagnostico")
	public void TS105443_CRM_Movil_Repro_Autogestion_WEB_No_envia_configuracion_Resuelto(String cDNI, String cLinea) throws InterruptedException {
		imagen = "TS105443";
		detalles = null;
		detalles = imagen + "- Autogestion - DNI: "+cDNI;
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		searchAndClick(driver, "Diagn\u00f3stico de Autogesti\u00f3n");
		tech.listadoDeSeleccion("WEB", "Otro", "No env\u00eda configuraci\u00f3n");
		sleep(4000);
		System.out.println(tech.verificarNumDeGestion());
		
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesReclamosDeRed", "Diagnostico/Inconvenientes", }, dataProvider = "Diagnostico")
	public void TS119277_CRM_Movil_PRE_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Navegar_SIN_LOCACION_MISMA_LINEA_NO_BAM(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS119277";
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
		driver.switchTo().frame(cambioFrame(driver, By.id("DataQuotaQuery_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(8000);
		String caso_1 = driver.findElement(By.xpath("//*[@id=\"UnavailableQuotaMessage\"]/div/p/p[2]/span/strong")).getText();
		System.out.println(caso_1);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso_1);
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesReclamosDeRed","Diagnostico/Inconvenientes"}, dataProvider = "Diagnostico")
	public void TS119239_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Llamar_desde_otro_pais_Servicio_Inactivo(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS119239";
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
		selectByText(driver.findElement(By.id("Motive")), "No puedo llamar desde otro pa\u00eds");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("BalanceValidation_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("NetworkCategory_nextBtn")));
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-radio--faux.ng-scope")), "equals", "si");
		driver.findElement(By.id("BlackListValidationOk_nextBtn")).click();
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
}
