package Tests;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.Accounts;
import Pages.BasePage;
import Pages.diagnosisTab;


public class TechJoaquin extends TestBase {
	
	public void cerrarTodasLasPestañas() {
		driver.switchTo().defaultContent();
		List<WebElement> pestañasPrimarias = driver.findElements(By.cssSelector(".x-plain-header.sd_primary_tabstrip.x-unselectable .x-tab-strip-closable"));
		if (pestañasPrimarias.size() > 0) {
			for (WebElement t : pestañasPrimarias) {
					WebElement btn_cerrar = t.findElement(By.className("x-tab-strip-close"));
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn_cerrar);	
			}
		}
	}
	
	private void cambiarAFrameActivo(String gest) {
		driver.switchTo().defaultContent();
		sleep(3000);
		List<WebElement> panelesCentrales = driver.findElements(By.cssSelector(".sd_secondary_container.x-border-layout-ct"));
		for (WebElement t : panelesCentrales) {
			if (!t.getAttribute("class").contains("x-hide-display")) {
				driver.switchTo().frame(t.findElement(By.cssSelector("div iframe")));
			}
		}
	}
	
	public void elegirCuenta(String nombreCuenta) {
		WebElement selector = driver.findElement(By.cssSelector(".x-btn-small.x-btn-icon-small-left"));
		if  (!selector.getText().equalsIgnoreCase("Cuentas")) {
			WebElement btnSplit = selector.findElement(By.className("x-btn-split"));
			Actions builder = new Actions(driver);   
			builder.moveToElement(btnSplit, 245, 20).click().build().perform();
			List<WebElement> desplegable = driver.findElements(By.cssSelector(".x-menu-item.accountMru.standardObject.sd-nav-menu-item"));
			for (WebElement op : desplegable) {
				if (op.getText().equalsIgnoreCase("Cuentas")) op.click();
			}
		}
		
		WebElement marcoCuentas = driver.findElement(By.cssSelector(".x-plain-body.sd_nav_tabpanel_body.x-tab-panel-body-top iframe"));
		driver.switchTo().frame(marcoCuentas);
		WebElement selectCuentas = driver.findElement(By.name("fcf"));
		Select field = new Select(selectCuentas);
		if (!field.getFirstSelectedOption().getText().equalsIgnoreCase("Todas las cuentas")) {
			field.selectByVisibleText("Todas las cuentas");
		}
		
		By cuentasLocator = By.cssSelector(".x-grid3-cell-inner.x-grid3-col-ACCOUNT_NAME");
		(new WebDriverWait(driver, 3)).until(ExpectedConditions.numberOfElementsToBe(cuentasLocator, 200));
		List<WebElement> cuentas = driver.findElements(cuentasLocator);
		for (WebElement c : cuentas) {
			if (c.getText().equalsIgnoreCase(nombreCuenta)) {
				c.findElement(By.tagName("a")).click();
				break;
			}
		}
		
		driver.switchTo().defaultContent();
		By panelesLocator = By.cssSelector(".sd_secondary_container.x-border-layout-ct");
		driver.findElements(panelesLocator);
		(new WebDriverWait(driver, 7)).until(ExpectedConditions.numberOfElementsToBe(panelesLocator, 2));
		cambiarAFrameActivo("Elegir Cuenta");
	}
	
	public WebElement obtenerAsset(String numeroLinea) {
		List<WebElement> lineasActivas = driver.findElements(By.cssSelector(".console-card.active"));
		for (WebElement l : lineasActivas) {
			if (l.findElement(By.cssSelector(".card-top")).getText().contains(numeroLinea)) {
				return l;
			}
		}
		return null;
	}
	
	public void irAAccion(WebElement asset, String accion) {
		List<WebElement> acciones = asset.findElements(By.cssSelector(".actions span"));
		for (WebElement a : acciones) {
			if (a.getText().contains(accion)) {
				a.click();
				break;
			}
		}
		cambiarAFrameActivo("Mis Servicios");
	}
	
	public void irAVerMasDetalles() {
		WebElement botonVerDetalles = driver.findElement(By.cssSelector(".slds-button.slds-button--brand"));
		waitFor.elementToBeClickable(botonVerDetalles);
		botonVerDetalles.click();
		cambiarAFrameActivo("Ver más detalles");
	}
	
	// **********************************************************************************
	// **********************************************************************************
	// **********************************************************************************
	
	@BeforeClass(alwaysRun=true)
	public void init() {
		inicializarDriver();
		login();
		IrA.CajonDeAplicaciones.ConsolaFAN();
	}
	
	@AfterClass(alwaysRun=true)
	public void quit() {
		cerrarTodasLasPestañas();
		IrA.CajonDeAplicaciones.Ventas();
		cerrarTodo();
	}
	
	@BeforeMethod(alwaysRun=true)
	public void after() {
		cerrarTodasLasPestañas();
	}
	
	@Test(groups= {"TechnicalCare", "MisServicios","Ola1"})
	public void TS51322_CSR_Mis_Servicios_Visualizacion_mayor_informacion() {
		elegirCuenta("Adrian Tech");
		WebElement asset = obtenerAsset("1122334456");
		irAAccion(asset, "Mis servicios");
		irAVerMasDetalles();
		
		WebElement headerServiciosDeValorAgregado = driver.findElement(By.cssSelector(".via-slds-card__header.slds-card__header"));
		waitFor.visibilityOfElement(headerServiciosDeValorAgregado);
		Assert.assertTrue(headerServiciosDeValorAgregado.isDisplayed());
	}
	
	@Test(groups= {"TechnicalCare", "MisServicios","Ola1"})
	public void TS51323_CSR_Mis_Servicios_Visualizacion_estado_de_los_Servicios() {
		elegirCuenta("Adrian Tech");
		WebElement asset = obtenerAsset("1122334456");
		irAAccion(asset, "Mis servicios");
		irAVerMasDetalles();
		
		List<WebElement> estadoDeCadaServicio = driver.findElements(By.xpath("//tr[@records='records']/td[@class='addedValueServices-card-td'][3]"));
		for (WebElement e : estadoDeCadaServicio) {
			Assert.assertTrue(e.isDisplayed());
		}
	}
	
	@Test(groups= {"TechnicalCare", "MisServicios","Ola1"})
	public void TS51524_CSR_Mis_Servicios_Visualizacion_Servicios_Activos_Inactivos_y_Pendientes() {
		elegirCuenta("Adrian Tech");
		WebElement asset = obtenerAsset("1122334456");
		irAAccion(asset, "Mis servicios");
		irAVerMasDetalles();
		
		List<WebElement> estadoDeCadaServicio = driver.findElements(By.xpath("//tr[@records='records']/td[@class='addedValueServices-card-td'][3]"));
		List<String> estados = new ArrayList<String>();
		for (WebElement e : estadoDeCadaServicio) {
			estados.add(e.getText());
		}
		
		Assert.assertTrue(estados.contains("Inactivo") || estados.contains("En activación") || estados.contains("Activo") || estados.contains("Limitado") || estados.contains("Pendiente"));
	}
	
	@Test(groups= {"TechnicalCare", "MisServicios","Ola1"})
	public void TS51327_CSR_Mis_Servicios_Visualizacion_Servicio_Fecha_Estado() {
		elegirCuenta("Adrian Tech");
		WebElement asset = obtenerAsset("1122334456");
		irAAccion(asset, "Mis servicios");
		irAVerMasDetalles();
		
		List<WebElement> columnas = driver.findElements(By.cssSelector(".slds-card__body.cards-container .slds-text-heading--label th a"));
		List<String> texto = new ArrayList<String>();
		for (WebElement c : columnas) {
			texto.add(c.getText());
		}
		Assert.assertTrue(texto.contains("SERVICIO"));
		Assert.assertTrue(texto.contains("FECHA"));
		Assert.assertTrue(texto.contains("ESTADO"));
	}
	
	@Test(groups= {"TechnicalCare", "MisServicios","Ola1"})
	public void TS51459_CSR_Mis_Servicios_Visualizacion_De_Pregunta() {
		BasePage cambioFrameByID=new BasePage();
		diagnosisTab dT = new diagnosisTab(driver);
		elegirCuenta("Adrian Tech");
		WebElement asset = obtenerAsset("1122334456");
		irAAccion(asset, "Mis servicios");
		irAVerMasDetalles();
		dT.abrirListaDeServicio(driver, "SMS");
		driver.findElement(By.cssSelector(".addedValueServices-row.serviceGroup.activeGroup")).findElement(By.cssSelector(".slds-dropdown-trigger.slds-dropdown-trigger--click")).click();
		driver.findElement(By.cssSelector(".addedValueServices-row.serviceGroup.activeGroup")).findElement(By.cssSelector(".icon.big.icon-v-troubleshoot-line")).click();
		sleep(5000);
		driver.switchTo().defaultContent();
	     driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("IssueSelectStep_nextBtn")));
	     dT.seleccionarMotivo(driver, "particular");
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("IssueSelectStep_nextBtn")).getLocation().y+")");
		 driver.findElement(By.id("IssueSelectStep_nextBtn")).click();
		 sleep(5000);
		 System.out.println(driver.findElement(By.id("SolutionValidationRadio|0")).findElement(By.tagName("label")).getText());
		 assertTrue(driver.findElement(By.id("SolutionValidationRadio|0")).findElement(By.tagName("label")).getText().toLowerCase().contains("el art\u00edculo ofrecido soluciona su inconveniente"));
	}
	
	@Test(groups= {"TechnicalCare", "MisServicios","Ola1"})
	public void TS51461_CSR_Mis_Servicios_Visualizacion_Documento_Base_De_Conocimiento_Respuesta_No() {
		BasePage cambioFrameByID=new BasePage();
		diagnosisTab dT = new diagnosisTab(driver);
		elegirCuenta("Adrian Tech");
		WebElement asset = obtenerAsset("1122334456");
		irAAccion(asset, "Mis servicios");
		irAVerMasDetalles();
		dT.abrirListaDeServicio(driver, "SMS");
		driver.findElement(By.cssSelector(".addedValueServices-row.serviceGroup.activeGroup")).findElement(By.cssSelector(".slds-dropdown-trigger.slds-dropdown-trigger--click")).click();
		driver.findElement(By.cssSelector(".addedValueServices-row.serviceGroup.activeGroup")).findElement(By.cssSelector(".icon.big.icon-v-troubleshoot-line")).click();
		sleep(5000);
		driver.switchTo().defaultContent();
	     driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("IssueSelectStep_nextBtn")));
	     dT.seleccionarMotivo(driver, "particular");
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("IssueSelectStep_nextBtn")).getLocation().y+")");
		 driver.findElement(By.id("IssueSelectStep_nextBtn")).click();
		 sleep(5000);
		 dT.seleccionarMotivo(driver, "no");
		 ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("KnowledgeBaseResults_nextBtn")).getLocation().y+")");
		 driver.findElement(By.id("KnowledgeBaseResults_nextBtn")).click();
		 driver.switchTo().defaultContent();
	     driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("NetworkCategory_nextBtn")));
	    assertTrue(driver.findElement(By.id("NetworkCategory_nextBtn")).isEnabled());
	}
	
	@Test(groups= {"TechnicalCare", "MisServicios","Ola1"})
	public void TS51460_CSR_Mis_Servicios_Visualizacion_Documento_Base_De_Conocimiento_Respuesta_Si() {
		BasePage cambioFrameByID=new BasePage();
		diagnosisTab dT = new diagnosisTab(driver);
		elegirCuenta("Adrian Tech");
		WebElement asset = obtenerAsset("1122334456");
		irAAccion(asset, "Mis servicios");
		irAVerMasDetalles();
		dT.abrirListaDeServicio(driver, "SMS");
		driver.findElement(By.cssSelector(".addedValueServices-row.serviceGroup.activeGroup")).findElement(By.cssSelector(".slds-dropdown-trigger.slds-dropdown-trigger--click")).click();
		driver.findElement(By.cssSelector(".addedValueServices-row.serviceGroup.activeGroup")).findElement(By.cssSelector(".icon.big.icon-v-troubleshoot-line")).click();
		sleep(5000);
		driver.switchTo().defaultContent();
	     driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("IssueSelectStep_nextBtn")));
	     dT.seleccionarMotivo(driver, "particular");
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("IssueSelectStep_nextBtn")).getLocation().y+")");
		 driver.findElement(By.id("IssueSelectStep_nextBtn")).click();
		 sleep(5000);
		 dT.seleccionarMotivo(driver, "si");
		 ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("KnowledgeBaseResults_nextBtn")).getLocation().y+")");
		 driver.findElement(By.id("KnowledgeBaseResults_nextBtn")).click();
		 sleep(6000);
		 driver.switchTo().defaultContent();
	     driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ClosedCaseKnowledgeBase")));
	     System.out.println("texto "+driver.findElement(By.id("ClosedCaseKnowledgeBase")).findElement(By.tagName("Strong")).getText());
	    assertTrue(driver.findElement(By.id("ClosedCaseKnowledgeBase")).findElement(By.tagName("Strong")).getText().contains("Se ha creado la gesti\u00f3n n\u00famero"));
	    assertTrue(driver.findElement(By.id("ClosedCaseKnowledgeBase")).findElements(By.tagName("Strong")).get(1).getText().contains("se procedi\u00f3 a su cierre"));
	}
	
	@Test(groups= {"TechnicalCare", "MisServicios","Ola2"})
	public void TS74013_CSR_Diagnostico_Seleccion_De_La_Opcion_No_Tiene_Cuota_Disponible() {
		BasePage cambioFrameByID=new BasePage();
		diagnosisTab dT = new diagnosisTab(driver);
		elegirCuenta("Adrian Tech");
		WebElement asset = obtenerAsset("1122334456");
		sleep(4000);
		dT.irADiagnostico(driver, "diagn\u00f3stico", asset);
		sleep(5000);
		driver.switchTo().defaultContent();
	    driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("MotiveIncidentSelect_nextBtn")));
		assertTrue(dT.seleccionarMotivoPorSelect(driver, "No puedo navegar"));
		driver.findElement(By.id("MotiveIncidentSelect_nextBtn")).click();
		sleep(5000);
		driver.switchTo().defaultContent();
	    driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("KnowledgeBaseResults_nextBtn")));
		dT.funcionoConfiguracion(driver, "no");
		 ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("KnowledgeBaseResults_nextBtn")).getLocation().y+")");
		 driver.findElement(By.id("KnowledgeBaseResults_nextBtn")).click();
		 sleep(5000);
		 driver.switchTo().defaultContent();
		 driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("DataQuotaQuery_nextBtn")));
		 ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("AvailableDataQuota|0")).getLocation().y+")");
		 driver.findElement(By.id("AvailableDataQuota|0")).findElements(By.cssSelector(".slds-radio--faux.ng-scope")).get(1).click();
		 driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		 sleep(5000);
		 driver.switchTo().defaultContent();
		 driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("UnavailableQuotaMessage")));
		 driver.findElement(By.id("UnavailableQuotaMessage")).isDisplayed();
		
	}
	
	@Test(groups= {"TechnicalCare", "MisServicios","Ola2"})
	public void TS74018_CSR_Diagnostico_Seleccion_De_La_Opcion_Navega_Lento() {
		BasePage cambioFrameByID=new BasePage();
		diagnosisTab dT = new diagnosisTab(driver);
		elegirCuenta("Adrian Tech");
		WebElement asset = obtenerAsset("1122334456");
		sleep(4000);
		dT.irADiagnostico(driver, "diagn\u00f3stico", asset);
		sleep(5000);
		driver.switchTo().defaultContent();
	    driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("MotiveIncidentSelect_nextBtn")));
		assertTrue(dT.seleccionarMotivoPorSelect(driver, "Navega lento"));
	}
	
	@Test(groups= {"TechnicalCare", "MisServicios","Ola2"})
	public void TS74020_CSR_Diagnostico_Seleccion_Opcion_Navega_Lento_Visualizar_La_Informacion_De_Saldo_Datos() {
		BasePage cambioFrameByID=new BasePage();
		diagnosisTab dT = new diagnosisTab(driver);
		elegirCuenta("Adrian Tech");
		WebElement asset = obtenerAsset("1122334456");
		sleep(4000);
		dT.irADiagnostico(driver, "diagn\u00f3stico", asset);
		sleep(5000);
		driver.switchTo().defaultContent();
	    driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("MotiveIncidentSelect_nextBtn")));
		assertTrue(dT.seleccionarMotivoPorSelect(driver, "Navega lento"));
		driver.findElement(By.id("MotiveIncidentSelect_nextBtn")).click();
		sleep(5000);
		driver.switchTo().defaultContent();
	    driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("KnowledgeBaseResults_nextBtn")));
		dT.funcionoConfiguracion(driver, "no");
		 ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("KnowledgeBaseResults_nextBtn")).getLocation().y+")");
		 driver.findElement(By.id("KnowledgeBaseResults_nextBtn")).click();
		 sleep(5000);
		 driver.switchTo().defaultContent();
		 driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("DataQuotaQuery_nextBtn")));
		 assertTrue(driver.findElement(By.id("AvailableDataBalanceDisplay")).findElement(By.tagName("strong")).getText().toLowerCase().equals("saldo"));
		
	}
	
	
	@Test(groups= {"TechnicalCare", "MisServicios","Ola2"})
	public void TS74048_CSR_Diagnostico_Seleccion_De_Motivo_De_Datos_No_Puedo_Navegar() {
		BasePage cambioFrameByID=new BasePage();
		diagnosisTab dT = new diagnosisTab(driver);
		elegirCuenta("Adrian Tech");
		WebElement asset = obtenerAsset("1122334456");
		sleep(4000);
		dT.irADiagnostico(driver, "diagn\u00f3stico", asset);
		sleep(5000);
		driver.switchTo().defaultContent();
	    driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("MotiveIncidentSelect_nextBtn")));
		assertTrue(dT.seleccionarMotivoPorSelect(driver, "No puedo navegar"));
	}
	
	@Test(groups= {"TechnicalCare", "MisServicios","Ola2"})
	public void TS74051_CSR_Diagnostico_Seleccion_De_Motivo_De_Voz_No_Puedo_Realizar_Llamadas() {
		BasePage cambioFrameByID=new BasePage();
		diagnosisTab dT = new diagnosisTab(driver);
		elegirCuenta("Adrian Tech");
		WebElement asset = obtenerAsset("1122334456");
		sleep(4000);
		dT.irADiagnostico(driver, "diagn\u00f3stico", asset);
		sleep(5000);
		driver.switchTo().defaultContent();
	    driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("MotiveIncidentSelect_nextBtn")));
		assertTrue(dT.seleccionarMotivoPorSelect(driver, "No puedo realizar llamadas"));
	}
	
	@Test(groups= {"TechnicalCare", "MisServicios","Ola2"})
	public void TS74052_CSR_Diagnostico_Seleccion_De_Motivo_De_Voz_No_Puedo_Recibir_Llamadas() {
		BasePage cambioFrameByID=new BasePage();
		diagnosisTab dT = new diagnosisTab(driver);
		elegirCuenta("Adrian Tech");
		WebElement asset = obtenerAsset("1122334456");
		sleep(4000);
		dT.irADiagnostico(driver, "diagn\u00f3stico", asset);
		sleep(5000);
		driver.switchTo().defaultContent();
	    driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("MotiveIncidentSelect_nextBtn")));
		assertTrue(dT.seleccionarMotivoPorSelect(driver, "No puedo recibir llamadas"));
	}
	
	@Test(groups= {"TechnicalCare", "MisServicios","Ola2"})
	public void TS74053_CSR_Diagnostico_Seleccion_De_Motivo_De_Voz_No_Puedo_Realizar_Ni_Recibir_Llamadas() {
		BasePage cambioFrameByID=new BasePage();
		diagnosisTab dT = new diagnosisTab(driver);
		elegirCuenta("Adrian Tech");
		WebElement asset = obtenerAsset("1122334456");
		sleep(4000);
		dT.irADiagnostico(driver, "diagn\u00f3stico", asset);
		sleep(5000);
		driver.switchTo().defaultContent();
	    driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("MotiveIncidentSelect_nextBtn")));
		assertTrue(dT.seleccionarMotivoPorSelect(driver, "No puedo realizar ni recibir llamadas"));
	}
	
	@Test(groups= {"TechnicalCare", "MisServicios","Ola2"})
	public void TS74054_CSR_Diagnostico_Seleccion_De_Motivo_De_Voz_No_Puedo_Llamar_Desde_Otro_Pais() {
		BasePage cambioFrameByID=new BasePage();
		diagnosisTab dT = new diagnosisTab(driver);
		elegirCuenta("Adrian Tech");
		WebElement asset = obtenerAsset("1122334456");
		sleep(4000);
		dT.irADiagnostico(driver, "diagn\u00f3stico", asset);
		sleep(5000);
		driver.switchTo().defaultContent();
	    driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("MotiveIncidentSelect_nextBtn")));
		assertTrue(dT.seleccionarMotivoPorSelect(driver, "No puedo llamar desde otro pa\u00eds"));
	}
	
	/**
	 * Verifica que en los motivos se muestren las opciones 
	 * -No puedo navegar -Navega lento
	 */
	@Test(groups= {"TechnicalCare", "MisServicios","Ola2"})
	public void TS74047_CSR_Diagnostico_Visualizacion_de_motivos_de_Datos() {
		BasePage cambioFrameByID=new BasePage();
		diagnosisTab dT = new diagnosisTab(driver);
		elegirCuenta("Adrian Tech");
		WebElement asset = obtenerAsset("1122334456");
		sleep(4000);
		dT.irADiagnostico(driver, "diagn\u00f3stico", asset);
		sleep(5000);
		driver.switchTo().defaultContent();
	    driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("MotiveIncidentSelect_nextBtn")));
		assertTrue(dT.seleccionarMotivoPorSelect(driver, "No puedo navegar"));
		assertTrue(dT.seleccionarMotivoPorSelect(driver, "Navega lento"));
	}
	
	/**
	 * Verifica que pueda visualizar los siguientes motivos de voz: 
	- No puedo realizar llamadas - No puedo recibir llamadas 
	- No puedo realizar ni recibir llamadas - No puedo llamar desde otro país
	 */
	@Test(groups= {"TechnicalCare", "MisServicios","Ola2"})
	public void TS74050_CSR_Diagnostico_Visualizacion_de_motivos_de_Voz() {
		BasePage cambioFrameByID=new BasePage();
		diagnosisTab dT = new diagnosisTab(driver);
		elegirCuenta("Adrian Tech");
		WebElement asset = obtenerAsset("1122334456");
		sleep(4000);
		dT.irADiagnostico(driver, "diagn\u00f3stico", asset);
		sleep(5000);
		driver.switchTo().defaultContent();
	    driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("MotiveIncidentSelect_nextBtn")));
		assertTrue(dT.seleccionarMotivoPorSelect(driver, "No puedo realizar llamadas"));
		assertTrue(dT.seleccionarMotivoPorSelect(driver, "No puedo recibir llamadas"));
		assertTrue(dT.seleccionarMotivoPorSelect(driver, "No puedo realizar ni recibir llamadas"));
		assertTrue(dT.seleccionarMotivoPorSelect(driver, "No puedo llamar desde otro país"));
	}
	
	/**
	 * verifica que al seleccionar la opcion no puedo navegar, se muestre la siguiente pregunta: 
 	 *"Posee cuota disponible?" 
	 */
	@Test(groups= {"TechnicalCare", "MisServicios","Ola2"})
	public void TS74012_CSR_Diagnostico_Visualizacion_pregunta_si_tiene_cuota() {
		BasePage cambioFrameByID=new BasePage();
		diagnosisTab dT = new diagnosisTab(driver);
		elegirCuenta("Adrian Tech");
		WebElement asset = obtenerAsset("1122334456");
		sleep(4000);
		dT.irADiagnostico(driver, "diagn\u00f3stico", asset);
		sleep(5000);
		driver.switchTo().defaultContent();
	    driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("MotiveIncidentSelect_nextBtn")));
		dT.seleccionarMotivoPorSelect(driver, "No puedo navegar");
		driver.findElement(By.id("MotiveIncidentSelect_nextBtn")).click();
		sleep(5000);
		driver.switchTo().defaultContent();
	    driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("KnowledgeBaseResults_nextBtn")));
		dT.funcionoConfiguracion(driver, "no");
		 ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("KnowledgeBaseResults_nextBtn")).getLocation().y+")");
		 driver.findElement(By.id("KnowledgeBaseResults_nextBtn")).click();
		 sleep(5000);
		 WebElement pregunta=driver.findElements(By.cssSelector(".slds-form-element__label.vlc-slds-inline-control__label.ng-binding")).get(1);
		assertTrue(pregunta.getText().toLowerCase().contains("¿posee cuota disponible?"));
	}
	
	
	
}
