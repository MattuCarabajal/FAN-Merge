package Funcionalidades;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.CustomerCare;
import Pages.SalesBase;
import Pages.setConexion;
import Tests.TestBase;

public class BaseDeConocimiento extends TestBase {

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
	
	@Test (groups = {"GestionesPerfilOficina", "BaseDeConocimiento", "Ciclo3"}, dataProvider = "CuentaVista360")
	public void TS100978_CRM_REPRO_BDC_Customer_Care_Suspensiones_y_Rehabilitaciones_Perfil_OOCC_Acceso_a_base_de_conocimiento(String sDNI, String sLinea,String sNombre, String sEmail, String sMovil) {
		imagen = "TS100978";
		detalles = null;
		detalles = imagen + "-Base de Conocimiento - DNI: " +sDNI+ " - Nombre: "+sNombre;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("suspensiones");
		driver.switchTo().defaultContent();
		buscarYClick(driver.findElements(By.className("x-btn-text")), "contains", "knowledge");
		driver.switchTo().frame(cambioFrame(driver, By.id("knowledge2HomePage_kbOneTab")));
		Assert.assertTrue(driver.findElement(By.id("knowledge2HomePage_kbOneTab")).isDisplayed());
	}
	
	@Test (groups = {"GestionPerfilOficina", "BasedeConocimiento", "Ciclo3"}, dataProvider = "BaseDeConocimiento")
	public void TS124899_CRM_REPRO_BDC_Technical_Care_CSR_Suscripciones_Base_de_Conocimiento(String sDNI, String sLinea) {
		imagen = "TS124899";
		detalles = null;
		detalles = imagen+"-Base de Conocimiento -DNI: "+sDNI+" -Linea: "+sLinea;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.className("community-flyout-actions-card")));
		Assert.assertTrue(driver.findElement(By.className("community-flyout-actions-card")).getText().contains("Suscripciones"));
		sleep(3000);
		cc.irAGestionEnCard("Suscripciones");
		sleep(7000);
		driver.switchTo().defaultContent();
		driver.findElement(By.id("scc_widget_knowledgeOne_button")).click();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".zen-unit.zen-lastUnit.kb-center")));
		Assert.assertTrue(driver.findElement(By.cssSelector(".zen-unit.zen-lastUnit.kb-center")).isDisplayed());	
	}
	
	@Test (groups = {"GestionPerfilOficina", "BasedeConocimiento", "Ciclo3"}, dataProvider = "BaseDeConocimiento")
	public void TS124900_CRM_REPRO_BDC_Technical_Care_CSR_Inconvenientes_con_Servicios_Varios_Base_de_Conocimiento(String sDNI, String sLinea) {
		imagen = "TS124900";
		detalles = null;
		detalles = imagen+"-Base de Conocimiento:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("inconvenientes");
		sleep(5000);
		driver.switchTo().defaultContent();
		buscarYClick(driver.findElements(By.className("x-btn-text")), "contains", "knowledge");
		driver.switchTo().frame(cambioFrame(driver, By.id("knowledge2HomePage_kbOneTab")));
		Assert.assertTrue(driver.findElement(By.id("knowledge2HomePage_kbOneTab")).isDisplayed());
	}
	
	@Test (groups = {"GestionesPerfilOficina", "BaseDeConocimiento", "Ciclo3"}, dataProvider = "CuentaVista360")
	public void TS125098_CRM_REPRO_BDC_Customer_Care_Problemas_con_recargas_Valoracion_negativa_en_BC(String sDNI, String sLinea,String sNombre, String sEmail, String sMovil) {
		imagen = "TS125098";
		detalles = null;
		detalles = imagen + "-Base de Conocimiento - DNI: "+sDNI+ " - Nombre: "+sNombre;
		boolean downVote = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(3000);
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(5000);
		driver.switchTo().defaultContent();
		buscarYClick(driver.findElements(By.className("x-btn-text")), "contains", "knowledge");
		driver.switchTo().frame(cambioFrame(driver, By.xpath("//*[@id=\"knowledgeSearchInput_kbOneTab\"]")));
		driver.findElement(By.xpath("//*[@id=\"knowledgeSearchInput_kbOneTab\"]")).sendKeys("problemas con recargas");
		driver.findElement(By.xpath("//*[@id=\"knowledgeSearchInput_kbOneTab\"]")).sendKeys(Keys.ENTER);
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".articleListContainer.loaded")));
		driver.findElement(By.cssSelector(".articleListContainer.loaded")).findElement(By.className("articleEntry")).findElement(By.tagName("a")).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("articleRendererBorderRight")));
		WebElement negativo = driver.findElement(By.className("articleRendererBorderRight")).findElements(By.tagName("td")).get(1).findElements(By.tagName("a")).get(1);
		if (negativo.getAttribute("title").contains("Haga clic si no le gusta este art\u00edculo de knowledge"))
			downVote = true;
		Assert.assertTrue(downVote);
	}
	
	@Test (groups = {"GestionPerfilOficina", "BasedeConocimiento", "Ciclo3"}, dataProvider = "BaseDeConocimiento")
	public void TS125103_CRM_REPRO_BDC_Customer_Care_Suspensiones_y_Rehabilitaciones_Valoracion_positiva_de_un_articulo(String sDNI, String sLinea) {
		imagen = "TS125103";
		detalles = null;
		detalles = imagen+"- Base de Conocimiento - DNI: "+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().defaultContent();
		driver.findElement(By.id("scc_widget_knowledgeOne_button")).click();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".zen-unit.zen-lastUnit.kb-center")));
		driver.findElement(By.id("knowledgeSearchInput_kbOneTab")).sendKeys("Suspensiones y Reconexiones - Requisitos");
		driver.findElement(By.cssSelector(".knowledgeSearchBoxButton.knowledgeSearchButton.leftColumn")).click();
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.id("articleList_kbOneTab")));
		driver.findElement(By.xpath("//*[@id=\"kA0c0000000DD1B_kbOneTab\"]/div/p[1]/a")).click();
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".ptBody.secondaryPalette.brandSecondaryBrd")));
		boolean a = false;
		for(WebElement x : driver.findElements(By.className("voteUDInlineUpCount"))) {
			if(x.getText().toLowerCase().contains("2")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "BaseDeConocimiento", "Ciclo3"}, dataProvider = "CuentaVista360")
	public void TS125107_CRM_REPRO_BDC_Customer_Care_Problemas_con_recargas_Tarjetas_Prepagas_Verificar_acceso_a_BC(String sDNI, String sLinea,String sNombre, String sEmail, String sMovil) {
		imagen = "TS125107";
		detalles = null;
		detalles = imagen + "- Base Conocimiento - DNI: "+sDNI+ " - Nombre: "+sNombre;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(3000);
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(5000);
		driver.switchTo().defaultContent();
		buscarYClick(driver.findElements(By.className("x-btn-text")), "contains", "knowledge");
		driver.switchTo().frame(cambioFrame(driver, By.id("knowledge2HomePage_kbOneTab")));
		Assert.assertTrue(driver.findElement(By.id("knowledge2HomePage_kbOneTab")).isDisplayed());
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"GestionesPerfilTelefonico", "BaseDeConocimiento", "Ciclo3"}, dataProvider = "CuentaVista360")
	public void TS118160_CRM_REPRO_BDC_Customer_Care_Actualizacion_de_Datos_Perfil_Telefonico_Acceso_base_de_conocimientos_dentro_OS(String sDNI, String sLinea,String sNombre, String sEmail, String sMovil) {
		imagen = "TS118160";
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		driver.findElements(By.className("profile-edit")).get(0).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("vlc-slds-knowledge-list-item")));
		Assert.assertTrue(driver.findElement(By.className("vlc-slds-knowledge-list-item")).getText().contains("Actualizaci\u00f3n de Datos"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "BaseDeConocimiento", "Ciclo3"}, dataProvider = "CuentaVista360")
	public void TS130755_CRM_REPRO_BDC_Customer_Care_Problemas_con_Recargas_PerfilTelefonico_Articulo_de_Medios_de_Recargas(String sDNI, String sLinea,String sNombre, String sEmail, String sMovil) {
		imagen = "TS130755";
		boolean knowledge = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		for (WebElement x : driver.findElements(By.cssSelector(".slds-form-element.slds-lookup.vlc-slds-knowledge-component.ng-scope"))) {
			if (x.getText().contains("Informaci\u00f3n De Recargas"))
				knowledge = true;
		}
		Assert.assertTrue(knowledge);
	}
}