package Tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.CustomerCare;
import Pages.SCP;
import Pages.setConexion;

public class CustomerCareOla1 extends TestBase {

	private WebDriver driver;
	protected CustomerCare cc;
	
	
	@BeforeClass (alwaysRun = true, groups = {"CustomerCare", "AjustesYEscalamiento", "SuspensionYRehabilitacion", "ProblemasConRecargas", "Ola1"})
	public void init() {
		driver = setConexion.setupEze();
		sleep(5000);
		cc = new CustomerCare(driver);
		loginOfCom(driver);
		try {
			cc.cajonDeAplicaciones("Consola FAN");
		} catch(Exception e) {
			sleep(3000);
			driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
			sleep(6000);
		}
	}
	
	@AfterClass (alwaysRun = true, groups = {"CustomerCare", "AjustesYEscalamiento", "SuspensionYRehabilitacion", "ProblemasConRecargas", "Ola1"})
	public void quit() {
		driver.quit();
		sleep(5000);
	}
	
	@BeforeMethod (alwaysRun = true, groups = {"CustomerCare", "AjustesYEscalamiento", "SuspensionYRehabilitacion", "ProblemasConRecargas", "Ola1"})
	public void before() {
		cc.cerrarTodasLasPestanas();
	}
	
	
	@Test (groups = {"CustomerCare", "AjustesYEscalamiento", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 3)
	public void TS90442_Adjustments_and_Escalations_Configurar_Ajuste_Formato_dd_mm_yyyy_fecha_hasta_desde(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("inconvenientes");
		cc.flujoInconvenientes();
		Assert.assertTrue(driver.findElement(By.id("Desde")).getAttribute("model-date-format").contains("dd-MM-yyyy"));
		Assert.assertTrue(driver.findElement(By.id("Hasta")).getAttribute("model-date-format").contains("dd-MM-yyyy"));
	}
	
	@Test (groups = {"CustomerCare", "AjustesYEscalamiento", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 3)
	public void TS90444_Adjustments_and_Escalations_Configurar_Ajuste_Tipos_Unidades_a_Ajustar(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("inconvenientes");
		cc.flujoInconvenientes();
		driver.findElement(By.id("Unidad")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//*[text() = 'Datos (Mb)']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[text() = 'Voz']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[text() = 'SMS']")).isDisplayed());
	}
	
	@Test (groups = {"CustomerCare", "AjustesYEscalamiento", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 3)
	public void TS90447_Adjustments_and_Escalations_Configurar_Ajuste_Tipos_Unidades_SMS(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("inconvenientes");
		cc.flujoInconvenientes();
		driver.findElement(By.id("Unidad")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//*[text() = 'SMS']")).isDisplayed());
	}
	
	@Test (groups = {"CustomerCare", "AjustesYEscalamiento", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 3)
	public void TS90448_Adjustments_and_Escalations_Configurar_Ajuste_Tipos_Unidades_Datos_adaptar_campo(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("inconvenientes");
		cc.flujoInconvenientes();
		driver.findElement(By.id("Unidad")).click();
		driver.findElement(By.xpath("//*[text() = 'Datos (Mb)']")).click();
		Assert.assertTrue(driver.findElement(By.id("CantidadDatosms")).isDisplayed());
	}
	
	@Test (groups = {"CustomerCare", "AjustesYEscalamiento", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 0)
	public void TS90461_Adjustments_and_Escalations_Sesion_guiada_Visualizar_Gestion_Ajustes(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.buscarGestion("inconvenientes con cargos tasados y facturados");
		List <WebElement> list = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.slds-truncate"));
		Assert.assertTrue(list.get(0).getText().toLowerCase().contains("inconvenientes con cargos tasados y facturados"));
	}
	
	@Test (groups = {"CustomerCare", "SuspensionYRehabilitacion", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 1)
	public void TS90462_360_VIEW_Suspensiones_and_Reconexiones_Visualizar_pantalla_para_seleccionar_el_tipo_de_accion_a_realizar_Suspension_Rehabilitacion(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("suspensiones");
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
		List <WebElement> gest = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope"));
		boolean a = false, b = false;
		for (WebElement x : gest) {
			if (x.getText().contains("Suspensi\u00f3n")) {
				a = true;
			}
			if (x.getText().contains("Habilitaci\u00f3n")) {
				b = true;
			}
		}
		Assert.assertTrue(a && b);
	}
	
	@Test (groups = {"CustomerCare", "AjustesYEscalamiento", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 1)
	public void TS90469_360_VIEW_Ajustes_y_Escalaciones_Seleccion_de_Concepto_Tipo_de_Cargo_Item_Motivo_Visualizar_parametro_Concepto(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("inconvenientes");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		Assert.assertTrue(driver.findElement(By.id("CboConcepto")).getAttribute("required").equals("true"));
	}
	
	@Test (groups = {"CustomerCare", "AjustesYEscalamiento", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 2)
	public void TS90481_360_VIEW_Adjustments_and_scalations_Visualizacion_Ajustes_y_Casos_Relacionados_Visualizar_un_boton_Siguiente_que_me_permita_avanzar_al_siguiente_paso_del_proceso(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("inconvenientes");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		boolean a = false;
		if (driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).isDisplayed()) {
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"CustomerCare", "SuspensionYRehabilitacion", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 0)
	public void TS90498_360_VIEW_Suspensiones_and_Reconexiones_Session_Guiada_Visualizar_la_opcion_Suspension_en_el_panel_de_gestiones(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.buscarGestion("suspensiones");
		List <WebElement> list = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.slds-truncate"));
		Assert.assertTrue(list.get(0).getText().toLowerCase().contains("suspensiones y reconexion"));
	}
	
	@Test (groups = {"CustomerCare", "SuspensionYRehabilitacion", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 1)
	public void TS90499_360_VIEW_Suspensiones_and_Reconexiones_Session_Guiada_Visualizar_la_opcion_Habilitacion_en_el_panel_de_gestiones(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("suspensiones");
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
		List <WebElement> hab = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope"));
		boolean a = false;
		for (WebElement x : hab) {
			if (x.getText().toLowerCase().contains("habilitaci\u00f3n")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"CustomerCare", "SuspensionYRehabilitacion", "Ola1","filtrado"}, dataProvider = "CustomerCuentaActiva", priority = 6)
	public void TS95637_Suspensiones_and_Reconexiones_Creacion_del_Caso_Back_office_Creacion_caso_comentario_de_resolucion_La_gestion_ha_sido_realizada_exitosamente(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("suspensiones y reconexion back");
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1SelectSuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "habilitaci\u00f3n");
		driver.findElement(By.id("Step1SelectSuspensionOrReconnection_nextBtn")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "dni/cuit");
		driver.findElement(By.id("Step2-SelectAssetOrDocument_nextBtn")).click();
		sleep(3000);
		driver.findElement(By.id("Step3_nextBtn")).click();
		sleep(3000);
		driver.findElement(By.id("Step4_nextBtn")).click();
		sleep(3000);
		driver.findElement(By.id("StepSummary_nextBtn")).click();
		sleep(7000);
		try {
			buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")), "equals", "continue");
		} catch(Exception e) {}
		sleep(5000);
		List <WebElement> gest = driver.findElements(By.className("ta-care-omniscript-done"));
		boolean a = false;
		for (WebElement x : gest) {
			if (x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito")) {
				a = true;
			}
		}
		String msg = driver.findElement(By.cssSelector(".vlc-slds-inline-control__label.ng-binding")).getText();
		int i = 0;
		while(msg.charAt(i++) != '0') {	}
		String caso = msg.substring(i-1, msg.length());
		cc.buscarCaso(caso);
		driver.switchTo().frame(cambioFrame(driver, By.id("cas14_ilecell")));
		WebElement vc = driver.findElement(By.id("cas14_ilecell"));
		Assert.assertTrue(vc.getText().toLowerCase().contains("habilitaci\u00f3n administrativa"));
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"CustomerCare", "SuspensionYRehabilitacion", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 4)  //Rompe porque la cuenta no posee equipo
	public void TS95641_Suspensiones_and_Reconexiones_Creacion_del_Caso_Creacion_caso_habilitacion_status(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("suspensiones");
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "habilitaci\u00f3n");
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".ta-radioBtnContainer.taBorderOverlay.slds-grid.slds-grid--align-center.slds-grid--vertical-align-center.ng-scope")), "contains", "validaci\u00f3n por l\u00ednea decisora");
		driver.findElement(By.id("MethodSelection_nextBtn")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "equipo");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "543343344409154");
		driver.findElement(By.id("Step3.5B-DeviceForLine_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("Step6-Summary_nextBtn")).click();
		sleep(3000);
		driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")).get(1).click();
		sleep(3000);
		List <WebElement> msj = driver.findElements(By.className("ta-care-omniscript-done"));
		boolean a = false;
		for (WebElement x : msj) {
			if (x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"CustomerCare", "SuspensionYRehabilitacion", "Ola1","filtrado"}, dataProvider = "CustomerCuentaActiva", priority = 4)  //Rompe poorque la cuenta no posee equipo
	public void TS95647_Suspensiones_and_Reconexiones_Creacion_del_Caso_Creacion_caso_habilitacion_Lineas_y_o_equipos_seleccionados(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("suspensiones");
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "habilitaci\u00f3n");
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".ta-radioBtnContainer.taBorderOverlay.slds-grid.slds-grid--align-center.slds-grid--vertical-align-center.ng-scope")), "contains", "validaci\u00f3n por l\u00ednea decisora");
		driver.findElement(By.id("MethodSelection_nextBtn")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "equipo");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "543343344409154");
		driver.findElement(By.id("Step3.5B-DeviceForLine_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("Step6-Summary_nextBtn")).click();
		sleep(3000);
		driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")).get(1).click();
		sleep(3000);
		String msg = driver.findElement(By.cssSelector(".vlc-slds-inline-control__label.ng-binding")).getText();
		int i = 0;
		while(msg.charAt(i++) != '0') {	}
		String caso = msg.substring(i-1, msg.length());
		cc.buscarCaso(caso);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".feeditemaux.cxfeeditemaux.CreateRecordAuxBody")));
		WebElement vc = driver.findElement(By.cssSelector(".feeditemaux.cxfeeditemaux.CreateRecordAuxBody"));
		Assert.assertTrue(vc.getText().toLowerCase().contains("suspensiones & reconexiones"));
	}
	
	@Test (groups = {"CustomerCare", "SuspensionYRehabilitacion", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 4)  //Rompe porque no posee equipo
	public void TS95651_Suspensiones_and_Reconexiones_Creacion_del_Caso_Suspension_Nivel_cuenta_campo_pais(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("suspensiones");
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "equipo");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "equipos facturados");
		driver.findElement(By.id("Step3.5A-DeviceForLine_nextBtn")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "robo");
		driver.findElement(By.id("Step4-SuspensionReason_nextBtn")).click();
		sleep(3000);
		List <WebElement> pais = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope"));
		boolean a = false, b = false;
		for (WebElement x : pais) {
			if (x.getText().toLowerCase().contains("argentina")) {
				a = true;
			}
			if (x.getText().toLowerCase().contains("exterior del pa\u00eds")) {
				b = true;
			}
		}
		Assert.assertTrue(a && b);
		Assert.assertTrue(driver.findElement(By.id("State")).isEnabled() && driver.findElement(By.id("State")).getAttribute("required").equals("true"));
		Assert.assertTrue(driver.findElement(By.id("Partido")).isEnabled() && driver.findElement(By.id("Partido")).getAttribute("required").equals("true"));
		Assert.assertTrue(driver.findElement(By.id("CityTypeAhead")).isEnabled() && driver.findElement(By.id("CityTypeAhead")).getAttribute("required").equals("true"));
	}
	
	@Test (groups = {"CustomerCare", "SuspensionYRehabilitacion", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 6)
	public void TS95927_360_VIEW_Suspensiones_and_Reconexiones_Seleccionar_tipo_Siniestro_Back_Office_Verificar_que_si_selecciono_Suspension_pueda_ser_de_DNI_CUIT(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("suspensiones y reconexion back");
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1SelectSuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		driver.findElement(By.id("Step1SelectSuspensionOrReconnection_nextBtn")).click();
		sleep(3000);
		List <WebElement> dni = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope"));
		boolean a = false;
		for (WebElement x : dni) {
			if (x.getText().toLowerCase().contains("dni/cuit")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"CustomerCare", "SuspensionYRehabilitacion", "Ola1","filtrado"}, dataProvider = "CustomerCuentaActiva", priority = 6)
	public void TS95928_Suspensiones_and_Reconexiones_Seleccionar_tipo_Siniestro_Back_Office_Verificar_que_si_selecciono_Suspension_pueda_ser_de_CUENTA_DE_FACTURACION(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("suspensiones y reconexion back");
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1SelectSuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		driver.findElement(By.id("Step1SelectSuspensionOrReconnection_nextBtn")).click();
		sleep(3000);
		List <WebElement> dni = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope"));
		boolean a = false, b = false;
		for (WebElement x : dni) {
			if (x.getText().toLowerCase().contains("cuenta de facturacion")) {
				a = true;
			}
			if (x.getText().toLowerCase().contains("linea")) {
				b = true;
			}
		}
		Assert.assertTrue(a && b);
	}
	
	@Test (groups = {"CustomerCare", "SuspensionYRehabilitacion", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 5)  //Rompe porque la cuenta no posee equipo
	public void TS95965_Suspensiones_and_Reconexiones_Configurar_el_tipo_de_Siniestro_Seleccionar_Solicitante_No_titular_habilita_para_completar_Apellido(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("suspensiones");
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		sleep(2000);
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "equipo");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "equipos facturados");
		driver.findElement(By.id("Step3.5A-DeviceForLine_nextBtn")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "robo");
		driver.findElement(By.id("Step4-SuspensionReason_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		Assert.assertTrue(driver.findElement(By.id("LastName")).isEnabled());
		Assert.assertTrue(driver.findElement(By.id("DNI")).isEnabled());
		Assert.assertTrue(driver.findElement(By.id("FirstName")).isEnabled());
		Assert.assertTrue(driver.findElement(By.id("Phone")).isEnabled());
	}
	
	@Test (groups = {"CustomerCare", "AjustesYEscalamiento", "Ola1","filtrado"}, dataProvider = "CustomerCuentaActiva", priority = 2)
	public void TS96020_Adjustments_and_Escalations_Consulta_de_Ajuste_Historicos_Visualizar_Ajuste_historico_de_la_cuenta(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("story-container")));
		List <WebElement> list = driver.findElements(By.className("story-container"));
		boolean a = false, b = false;
		for (WebElement x : list) {
			if (x.getText().toLowerCase().contains("t\u00edtulo")) {
				a = true;
			}
			if (x.getText().toLowerCase().contains("inconvenientes con cargos tasados y facturados")) {
				b = true;
			}
		}
		Assert.assertTrue(a && b);
	}
	
	@Test (groups = {"CustomerCare", "SuspensionYRehabilitacion", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 6)
	public void TS96046_Suspensiones_and_Reconexiones_Creacion_del_Caso_Back_office_Creacion_caso_Subject_Suspencion_Administrativa(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("suspensiones y reconexion back");
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1SelectSuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		driver.findElement(By.id("Step1SelectSuspensionOrReconnection_nextBtn")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "dni/cuit");
		driver.findElement(By.id("Step2-SelectAssetOrDocument_nextBtn")).click();
		sleep(3000);
		driver.findElement(By.id("Step3_nextBtn")).click();
		sleep(3000);
		Select tsus = new Select (driver.findElement(By.id("SelectFraud")));  
		tsus.selectByVisibleText("Comercial");
		Select sub = new Select (driver.findElement(By.id("SelectSubFraud")));  
		sub.selectByVisibleText("Administrativo por suscripci\u00f3n");
		driver.findElement(By.id("Step4_nextBtn")).click();
		sleep(3000);
		driver.findElement(By.id("StepSummary_nextBtn")).click();
		sleep(3000);
		try {
			buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")), "equals", "continue");
		} catch(Exception e) {}
		sleep(5000);
		List <WebElement> msj = driver.findElements(By.className("ta-care-omniscript-done"));
		boolean a = false;
		for (WebElement x : msj) {
			if (x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito")) {
				a = true;
			}
		}
		String msg = driver.findElement(By.cssSelector(".vlc-slds-inline-control__label.ng-binding")).getText();
		int i = 0;
		while(msg.charAt(i++) != '0') {	}
		String caso = msg.substring(i-1, msg.length());
		cc.buscarCaso(caso);
		driver.switchTo().frame(cambioFrame(driver, By.id("cas14_ilecell")));
		WebElement vc = driver.findElement(By.id("cas14_ilecell"));
		Assert.assertTrue(a);
		Assert.assertTrue(vc.getText().toLowerCase().contains("suspension administrativa"));
	}
	
	@Test (groups = {"CustomerCare", "SuspensionYRehabilitacion", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 5)
	public void TS96074_360_VIEW_Suspensiones_and_Reconexiones_Seleccionar_tipo_Siniestro_Visualizar_opcion_Tipo_de_Siniestro(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("suspensiones");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "l\u00ednea: 3413105385");
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		sleep(5000);
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope"));
		boolean a = false, b = false, c = false;
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("robo")) {
				a = true;
			}
			if (x.getText().toLowerCase().contains("hurto")) {
				b = true;
			}
			if (x.getText().toLowerCase().contains("extrav\u00edo")) {
				c = true;
			}
		}
		Assert.assertTrue(a && b && c);
	}
	
	@Test (groups = {"CustomerCare", "SuspensionYRehabilitacion", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 5)  //Rompe porque no posee equipo
	public void TS96075_Suspensiones_and_Reconexiones_Seleccionar_tipo_Siniestro_Verificar_que_la_opcion_Tipo_de_Siniestro_se_de_seleccion_unica(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("suspensiones");
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "equipo");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "equipos facturados");
		driver.findElement(By.id("Step3.5A-DeviceForLine_nextBtn")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "robo");
		Assert.assertTrue(driver.findElement(By.cssSelector(".ng-not-empty.ng-dirty.ng-valid.ng-valid-required.ng-touched.ng-valid-parse")).isSelected());
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "hurto");
		Assert.assertTrue(!(driver.findElement(By.xpath("//*[@id=\"Radio3-ReasonSuspension|0\"]/div/div[1]/label[1]/span[1]")).isSelected()));
	}
	
	@Test (groups = {"CustomerCare", "SuspensionYRehabilitacion", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 2)
	public void TS96078_Suspensiones_and_Reconexiones_Seleccionar_Tipo_de_gestion_Suspension_Reconexion_Verficiar_que_al_seleccionar_Suspension_se_muestren_las_opciones_Linea_Linea__Equipo_Equipo(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("suspensiones");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		sleep(5000);
		List <WebElement> linea = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope"));
		boolean a = false, b = false, c = false;
		for (WebElement x : linea) {
			if (x.getText().toLowerCase().contains("linea")) {
				a = true;
			}
			if (x.getText().toLowerCase().contains("linea + equipo")) {
				b = true;
			}
			if (x.getText().toLowerCase().contains("equipo")) {
				c = true;
			}
		}
		Assert.assertTrue(a && b && c);
	}
	
	@Test (groups = {"CustomerCare", "SuspensionYRehabilitacion", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 2)
	public void TS96080_Suspensiones_and_Reconexiones_Seleccionar_Tipo_de_gestion_Suspension_Reconexion_Seleccionar_Habilitacion_para_workplace_personalizada_se_muestren_las_opciones_Linea_Linea___Equipo_Equipo(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("suspensiones");
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "habilitaci\u00f3n");
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".ta-radioBtnContainer.taBorderOverlay.slds-grid.slds-grid--align-center.slds-grid--vertical-align-center.ng-scope")), "contains", "validaci\u00f3n por l\u00ednea decisora");
		driver.findElement(By.id("MethodSelection_nextBtn")).click();
		sleep(5000);
		List <WebElement> linea = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope"));
		boolean a = false, b = false, c = false;
		for (WebElement x : linea) {
			if (x.getText().toLowerCase().contains("linea")) {
				a = true;
			}
			if (x.getText().toLowerCase().contains("linea + equipo")) {
				b = true;
			}
			if (x.getText().toLowerCase().contains("equipo")) {
				c = true;
			}
		}
		Assert.assertTrue(a && b && c);
	}
	
	@Test (groups = {"CustomerCare", "SuspensionYRehabilitacion", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 3)
	public void TS96104_Suspensiones_and_Reconexiones_Visualizar_Lineas_Habilitacion_Verificar_que_sean_campos_de_seleccion_unica(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("suspensiones");
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "habilitaci\u00f3n");
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		Assert.assertTrue(driver.findElement(By.cssSelector(".ng-not-empty.ng-dirty.ng-valid.ng-valid-required.ng-touched.ng-valid-parse")).isSelected());
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "equipo");
		Assert.assertTrue(!(driver.findElement(By.cssSelector(".ng-not-empty.ng-dirty.ng-valid.ng-valid-required.ng-touched")).isSelected()));
	}
	
	@Test (groups = {"CustomerCare", "SuspensionYRehabilitacion", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 6)
	public void TS96111_Suspensiones_and_Reconexiones_Seleccionar_tipo_Siniestro_Back_Office_Verificar_que_si_selecciono_Reconexion_pueda_ser_de_DNI_CUIT(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("suspensiones y reconexion back");
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1SelectSuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "habilitaci\u00f3n");
		driver.findElement(By.id("Step1SelectSuspensionOrReconnection_nextBtn")).click();
		sleep(3000);
		List <WebElement> dni = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope"));
		boolean a = false, b = false;
		for (WebElement x : dni) {
			if (x.getText().toLowerCase().contains("dni/cuit")) {
				a = true;
			}
			if (x.getText().toLowerCase().contains("cuenta de facturacion")) {
				b = true;
			}
		}
		Assert.assertTrue(a && b);
	}
	
	@Test(groups = {"CustomerCare", "Ola1", "AjustesYEscalamiento"}, dataProvider = "CustomerCuentaActiva", priority = 3)
	public void TS90443_Adjustments_and_Esccalations_Adjustments_and_Escalations_Configurar_Ajuste_Formato_monto_con_2_decimales(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("inconvenientes");
		cc.flujoInconvenientes();
		driver.findElement(By.id("Unidad")).click();
		driver.findElement(By.xpath("//*[text() = 'Credito']")).click();
		sleep(2000);
		WebElement monto = cc.obtenerCampo("CantidadMonto");
		monto.sendKeys("55511");
		String valorCampo = obtenerValorDelCampo(monto);
		Assert.assertTrue(valorCampo.contentEquals("555.11"));
	}
	
	@Test(groups = {"CustomerCare", "Ola1", "AjustesYEscalamiento"}, dataProvider = "CustomerCuentaActiva", priority = 3)
	public void TS90446_Adjustments_and_Esccalations_Adjustments_and_Escalations_Configurar_Ajuste_Tipos_Unidades_VOZ_HH_MM_SS(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("inconvenientes");
		cc.flujoInconvenientes();
		driver.findElement(By.id("Unidad")).click();
		driver.findElement(By.xpath("//*[text() = 'Voz']")).click();
		sleep(2000);
		WebElement cantidad = cc.obtenerCampo("CantidadVoz");
		cantidad.sendKeys("042050");
		String valorCampo = obtenerValorDelCampo(cantidad);		
		Assert.assertTrue(valorCampo.contentEquals("04:20:50"));
	}
	
	@Test(groups = {"CustomerCare", "Ola1", "AjustesYEscalamiento"}, dataProvider = "CustomerCuentaActiva", priority = 2)
	public void TS90454_Adjustments_and_Esccalations_Adjustments_and_Escalations_UX_Visualizacion_Ajustes_y_Casos_Relacionados_Visualizar_boton_siguiente_OS(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("inconvenientes");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		driver.findElement(By.id("CboConcepto")).click();
		driver.findElement(By.xpath("//*[text() = 'CREDITO PREPAGO']")).click();
		driver.findElement(By.id("CboItem")).click();
		driver.findElement(By.xpath("//*[text() = 'Consumos de datos']")).click();
		driver.findElement(By.id("CboMotivo")).click();
		driver.findElement(By.xpath("//*[text() = 'Error/omisi\u00f3n/demora gesti\u00f3n']")).click();
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(5000);
		Assert.assertTrue(driver.findElement(By.id("Step-AssetSelection_nextBtn")).isDisplayed());
	}
	
	@Test (groups = {"CustomerCare", "SuspensionYRehabilitacion", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 2)
	public void TS95964_360_VIEW_Suspensiones_and_Reconexiones_Configurar_el_tipo_de_Siniestro_Solicitante_No_titular_habilita_para_completar_Nombre_Apellido_DNI_telefono_de_contacto(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("suspensiones");
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		sleep(2000);
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "l\u00ednea: 3413105385");
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "robo");
		driver.findElement(By.id("Step4-SuspensionReason_nextBtn")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "no");
		Assert.assertTrue(driver.findElement(By.id("DNI")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("FirstName")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("LastName")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("Phone")).isDisplayed());
	}
	
	@Test (groups = {"CustomerCare", "SuspensionYRehabilitacion", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 1)
	public void TS95934_360_VIEW_Suspensiones_and_Reconexiones_Seleccionar_tipo_Siniestro_Back_Office_Reconexion_pueda_ser_de_Linea(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("suspensiones y reconexion back");
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1SelectSuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "habilitaci\u00f3n");
		driver.findElement(By.id("Step1SelectSuspensionOrReconnection_nextBtn")).click();
		sleep(3000);
		boolean a = false;
		List <WebElement> linea = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope"));
		for (WebElement x : linea) {
			if (x.getText().toLowerCase().contains("linea")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"CustomerCare", "SuspensionYRehabilitacion", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 2)
	public void TS95929_360_VIEW_Suspensiones_and_Reconexiones_Seleccionar_tipo_Siniestro_Back_Office_Verificar_que_si_selecciono_Suspension_pueda_ser_de_Linea(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("suspensiones y reconexion back");
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1SelectSuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		driver.findElement(By.id("Step1SelectSuspensionOrReconnection_nextBtn")).click();
		sleep(3000);
		boolean a = false;
		List <WebElement> linea = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope"));
		for (WebElement x : linea) {
			if (x.getText().toLowerCase().contains("linea")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"CustomerCare", "SuspensionYRehabilitacion", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 3)
	public void TS95973_360_VIEW_Suspensiones_and_Reconexiones_Configurar_el_tipo_de_Siniestro_Direccion_del_Siniestro_y_Exterior_del_Pais_habilita_un_campo_para_ingresar_el_pais(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("suspensiones");
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "l\u00ednea: 3413105385");
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "robo");
		driver.findElement(By.id("Step4-SuspensionReason_nextBtn")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "exterior del pa\u00eds");
		Assert.assertTrue(driver.findElement(By.id("Country")).isDisplayed());
	}
	
	@Test (groups = {"CustomerCare", "SuspensionYRehabilitacion", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 1)
	public void TS96112_360_VIEW_Suspensiones_and_Reconexiones_Seleccionar_tipo_Siniestro_Back_Office_Verificar_que_si_selecciono_Reconexion_pueda_ser_de_CUENTA_DE_FACTURACION(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("suspensiones y reconexion back");
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1SelectSuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "habilitaci\u00f3n");
		driver.findElement(By.id("Step1SelectSuspensionOrReconnection_nextBtn")).click();
		sleep(3000);
		boolean a = false;
		List <WebElement> linea = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope"));
		for (WebElement x : linea) {
			if (x.getText().toLowerCase().contains("cuenta de facturacion")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"CustomerCare", "AjustesYEscalamiento", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 4)
	public void TS90472_360_VIEW_Ajustes_y_Escalaciones_Seleccion_de_Concepto_Tipo_de_Cargo_Item_Motivo_Validar_Concepto_solo_se_puede_permitir_1_valor_de_la_lista_indicada_del_parametro_Tipo_de_Cargo(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("inconvenientes");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		driver.findElement(By.id("CboConcepto")).click();
		driver.findElement(By.xpath("//*[text() = 'CARGOS AUN NO FACTURADOS']")).click();
		driver.findElement(By.id("CboTipo")).click();
		driver.findElement(By.xpath("//*[text() = 'Otros cargos no facturados']")).click();
		String a = driver.findElement(By.id("CboTipo")).getAttribute("value");
		driver.findElement(By.id("CboTipo")).click();
		driver.findElement(By.xpath("//*[text() = 'Cargos fijos no facturados']")).click();
		Assert.assertTrue(!driver.findElement(By.id("CboTipo")).getAttribute("value").equals(a));
	}
	
	@Test (groups = {"CustomerCare", "AjustesYEscalamiento", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 4)
	public void TS90476_360_VIEW_Ajustes_y_Escalaciones_Seleccion_de_Concepto_Tipo_de_Cargo_Item_Motivo_Validar_que_se_puede_seleccionar_un_Motivo_solo_si_se_han_completado_los_valores_anteriores(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("inconvenientes");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		driver.findElement(By.id("CboMotivo")).click();
		driver.findElement(By.xpath("//*[text() = 'Error/omisi\u00f3n/demora gesti\u00f3n']")).click();
		String a = driver.findElement(By.id("CboMotivo")).getAttribute("value");
		driver.findElement(By.id("CboMotivo")).click();
		driver.findElement(By.xpath("//*[text() = 'Informacion incorrecta']")).click();
		Assert.assertTrue(!driver.findElement(By.id("CboMotivo")).getAttribute("value").equals(a));
	}
	
	@Test (groups = {"CustomerCare", "AjustesYEscalamiento", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 4)
	public void TS90470_360_VIEW_Ajustes_y_Escalaciones_Seleccion_de_Concepto_Tipo_de_Cargo_Item_Motivo_Validar_que_solo_se_puede_permitir_1_valor_de_la_lista_indicada_del_parametro_Conceptos(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("inconvenientes");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		driver.findElement(By.id("CboConcepto")).click();
		driver.findElement(By.xpath("//*[text() = 'CREDITO PREPAGO']")).click();
		String a = driver.findElement(By.id("CboConcepto")).getAttribute("value");
		driver.findElement(By.id("CboConcepto")).click();
		driver.findElement(By.xpath("//*[text() = 'CREDITO POSPAGO']")).click();
		Assert.assertTrue(!driver.findElement(By.id("CboMotivo")).getAttribute("value").equals(a));
	}
	
	@Test (groups = {"CustomerCare", "AjustesYEscalamiento", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 3)
	public void TS96011_360_VIEW_Ajustes_y_Escalaciones_Seleccion_de_Concepto_Tipo_de_Cargo_Item_Motivo_Visualizar_parametro_item(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("inconvenientes");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		Assert.assertTrue(driver.findElement(By.id("CboItem")).isEnabled());
	}
	
	@Test (groups = {"CustomerCare", "AjustesYEscalamiento", "Ola1"}, dataProvider = "CustomerCuentaActiva", priority = 2)
	public void TS95996_Adjustments_and_Escalations_Cierre_Caso_Gestion_Exitosa_Rechazada_Ampliar_detalles_Cerrar_caso_exitoso_comentario(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("inconvenientes");
		cc.flujoInconvenientes();
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(5000);
		try {
			buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")), "equals", "continue");
		} catch(Exception e) {}
		driver.findElement(By.id("SummaryDerivateToBO_nextBtn")).click();
		sleep(3000);
		WebElement gest = driver.findElement(By.cssSelector(".slds-box.ng-scope"));
		boolean a = false, b = false;
		if (gest.getText().toLowerCase().contains("la gesti\u00f3n se deriv\u00f3 al area")) {
			a = true;
		}
		if (gest.getText().toLowerCase().contains("el n\u00famero de caso es")) {
			b = true;
		}
		Assert.assertTrue(a && b);
	}
	
	@Test (groups = {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")  //Rompe porque el PIN acepta 4 numeros
	public void TS38553_Problems_with_Refills_Problemas_con_Recargas_Medio_de_recarga_PIN_Ingresa_15_digitos(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.tarjetaPrepaga();
	    driver.findElement(By.id("pinNumber")).sendKeys("123456789012345");
	    Assert.assertTrue(!driver.findElement(By.cssSelector(".error.ng-scope")).getText().isEmpty());
	}
	
	@Test (groups = {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")  //Rompe porque el PIN acepta 4 numeros
	public void TS37554_Problems_with_Refills_Problemas_con_Recargas_Medio_de_recarga_PIN_Ingresa_16_digitos(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.tarjetaPrepaga();
		driver.findElement(By.id("PIN")).sendKeys("1234567890123456");
	    Assert.assertTrue(driver.findElement(By.cssSelector(".error.ng-scope")).getText().isEmpty());
	}
	
	@Test (groups = {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")  //Rompe porque el PIN acepta 4 numeros
	public void TS37555_Problems_with_Refills_Problemas_con_Recargas_Medio_de_recarga_PIN_Ingresa_17_digitos(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.tarjetaPrepaga();
		driver.findElement(By.id("PIN")).sendKeys("12345678901234567");
	    System.out.println(driver.findElement(By.cssSelector(".vlc-slds-error-block.ng-scope")).getText());
	    Assert.assertTrue(!driver.findElement(By.cssSelector(".error.ng-scope")).getText().isEmpty());
	}
	
	@Test (groups = {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")
	public void TS37556_Problems_with_Refills_Problemas_con_Recargas_Medio_de_recarga_PIN_Ingresa_letras(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.tarjetaPrepaga();
		driver.findElement(By.id("PIN")).sendKeys("a");
	    Assert.assertTrue(!driver.findElement(By.cssSelector(".error.ng-scope")).getText().isEmpty());
	}
	
	@Test (groups = {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")  //Rompe por error en el flujo de Tarjeta Prepaga
	public void TS37536_Problems_with_Refills_Problemas_con_Recargas_Medio_de_recarga_Seleccionar_Tarjeta_Pre_Paga_PIN_Invisible(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.tarjetaPrepaga();
		driver.findElement(By.id("BatchNumber")).sendKeys("2222222222222222");
		driver.findElement(By.id("PrepaidCardData_nextBtn")).click();
		sleep(5000);
		Assert.assertTrue(false);
	}
	
	@Test (groups = {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")
	public void TS37549_Problems_with_Refills_Problemas_con_Recargas_Recarga_sin_PIN_Gestion_pendiente_Recarga_sin_PIN_Lote_Ingresa_15_digitos(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.tarjetaPrepaga();
		driver.findElement(By.id("BatchNumber")).sendKeys("123456789012345");
	    Assert.assertTrue(!driver.findElement(By.cssSelector(".error.ng-scope")).getText().isEmpty());
	}
	
	@Test (groups = {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")
	public void TS37550_Problems_with_Refills_Problemas_con_Recargas_Recarga_sin_PIN_Gestion_pendiente_Recarga_sin_PIN_Lote_Ingresa_16_digitos(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.tarjetaPrepaga();
		driver.findElement(By.id("BatchNumber")).sendKeys("1234567890123456");
		sleep(2000);
	    Assert.assertTrue(driver.findElement(By.cssSelector(".error.ng-scope")).getText().isEmpty());
	}
	
	@Test (groups = {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")
	public void TS37551_Problems_with_Refills_Problemas_con_Recargas_Recarga_sin_PIN_Gestion_pendiente_Recarga_sin_PIN_Lote_Ingresa_17_digitos(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.tarjetaPrepaga();
		driver.findElement(By.id("BatchNumber")).sendKeys("12345678901234567");
	    System.out.println(driver.findElement(By.cssSelector(".vlc-slds-error-block.ng-scope")).getText());
	    Assert.assertTrue(!driver.findElement(By.cssSelector(".error.ng-scope")).getText().isEmpty());
	}
	
	@Test (groups = {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")
	public void TS37552_Problems_with_Refills_Problemas_con_Recargas_Recarga_sin_PIN_Gestion_pendiente_Recarga_sin_PIN_Lote_Ingresa_Letras(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.tarjetaPrepaga();
		driver.findElement(By.id("BatchNumber")).sendKeys("a");
	    Assert.assertTrue(!driver.findElement(By.cssSelector(".error.ng-scope")).getText().isEmpty());
	}
	
	@Test(groups = {"CustomerCare", "ProblemasConRecargas", "Ola1","filtrado"}, dataProvider = "CustomerCuentaActiva")
	public void TS37331_Problems_with_Refills_UX_Tarjeta_de_Recarga_Pre_paga_Verificacion_Visualizar_Boton_Cancelar(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
	    sleep(5000);
	    driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-small-size--3-of-12.slds-medium-size--3-of-12.slds-large-size--3-of-12.flyout-actions")));
	    ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".console-flyout.active.flyout")).getLocation().y+")");
	    sleep(3000);
	    driver.findElement(By.className("community-flyout-actions-card")).findElements(By.tagName("li")).get(4).click();
	    sleep(8000);
	    driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
	    ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope")).getLocation().y+")");
	    Assert.assertTrue(driver.findElement(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope")).isDisplayed());
	}
	
	@Test(groups = {"CustomerCare", "ProblemasConRecargas", "Ola1","filtrado"}, dataProvider = "CustomerCuentaActiva")
	public void TS37338_Problems_with_Refills_UX_Tarjeta_de_Recarga_Pre_paga_Verificacion_Visualizar_Boton_Consultar(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
	    sleep(5000);
	    driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-small-size--3-of-12.slds-medium-size--3-of-12.slds-large-size--3-of-12.flyout-actions")));
	    ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".console-flyout.active.flyout")).getLocation().y+")");
	    sleep(3000);
	    driver.findElement(By.className("community-flyout-actions-card")).findElements(By.tagName("li")).get(4).click();
	    sleep(8000);
	    driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
	    ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("RefillMethods_nextBtn")).getLocation().y+")");
	    Assert.assertTrue(driver.findElement(By.id("RefillMethods_nextBtn")).isDisplayed());
	}
	
	@Test(groups = {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")
	public void TS37330_Problems_with_Refills_UX_Tarjeta_de_Recarga_Pre_paga_Verificacion_Visualizar_panel_de_Steps(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
	    sleep(5000);
	    driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-small-size--3-of-12.slds-medium-size--3-of-12.slds-large-size--3-of-12.flyout-actions")));
	    ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".console-flyout.active.flyout")).getLocation().y+")");
	    sleep(3000);
	    driver.findElement(By.cssSelector(".console-flyout.active.flyout")).findElements(By.tagName("i")).get(1).click();
	    sleep(8000);
	    driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".vlc-slds-wizard.ng-scope.ng-isolate-scope")));
	    Assert.assertTrue(driver.findElement(By.cssSelector(".vlc-slds-wizard.ng-scope.ng-isolate-scope")).isDisplayed());
	}
	
	@Test(groups = {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")
	public void TS69190_360_View_Visualizacion_de_gestiones_desde_el_asset_Detalles_Gestion_Columna_numero(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		WebElement card = driver.findElement(By.className("card-info"));
	    buscarYClick(card.findElements(By.className("slds-text-body_regular")), "equals", "gestiones");
	    sleep(5000);
	    driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")));
	    driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
	    sleep(2000);
	    driver.switchTo().frame(cambioFrame(driver, By.className("slds-p-bottom--small")));
	    WebElement table = driver.findElement(By.className("slds-text-heading--label"));
	    List <WebElement> type = table.findElements(By.tagName("th"));
	    String num = type.get(2).getText();
	    Assert.assertTrue(num.toLowerCase().equals("n\u00famero"));
	}
	
	@Test(groups = {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")  //Rompe porque no ordena en orden alfabetico
	public void TS69182_360_View_Visualizacion_de_gestiones_desde_el_asset_Estado_Ordenar_ascendente(String cCuenta) throws ParseException {
		cc.elegirCuenta(cCuenta);
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
	    WebElement card = driver.findElement(By.className("card-info"));
	    buscarYClick(card.findElements(By.className("slds-text-body_regular")), "equals", "gestiones");
	    sleep(5000);
	    driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")));
	    driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
	    sleep(2000);
	    driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")));
	    SCP scp = new SCP(driver);
	    Assert.assertTrue(scp.Triangulo_Ordenador_Validador(driver, By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header"), 5, 5));
	}
	
	@Test(groups = {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")  //Rompe porque no ordena en orden alfabetico
	public void TS69187_360_View_Visualizacion_de_gestiones_desde_el_asset_Estado_Ordenar_descendente(String cCuenta) throws ParseException {
		cc.elegirCuenta(cCuenta);
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
	    WebElement card = driver.findElement(By.className("card-info"));
	    buscarYClick(card.findElements(By.className("slds-text-body_regular")), "equals", "gestiones");
	    sleep(5000);
	    driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")));
	    driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
	    sleep(2000);
	    driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")));
	    SCP scp = new SCP(driver);
	    WebElement table = driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header"));
	    WebElement menu = table.findElement(By.tagName("thead"));
	    List <WebElement> list = menu.findElements(By.tagName("th"));
	    List <String> sList = scp.TraerColumna(table, 5, 5);
	    Collections.sort(sList);
	    List <String> sListOrdered = new ArrayList<String>();
	    for (int i = sList.size()-1; i >= 0; i--) {
	    	sListOrdered.add(sList.get(i));
	    }
	    list.get(4).click();
	    list.get(4).click();
	    /*List <String> sListOrderedOnPage = scp.TraerColumna(table, 5, 5);
	    boolean a = false;
	    for (int i=0; i<sList.size(); i++) {
	    	if (sListOrdered.get(i).equals(sListOrderedOnPage.get(i))) {
	    		a = true;
	    	}
	    }*/
	    Assert.assertTrue(false);
	}
	
	@Test (groups= {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")
	public void TS38537_Problems_with_Refills_Problemas_con_Recargas_Medio_de_recarga_Seleccion_simple(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.tarjetaPrepaga();
		driver.findElement(By.id("PrepaidCardData_prevBtn")).click();
		sleep(3000);
		List<WebElement> elementos = driver.findElements(By.cssSelector(".slds-radio.ng-scope"));
		if (!elementos.get(1).getAttribute("class").contains("itemSelected")) {
			elementos.get(1).click();
		}
		sleep(2000);
		Assert.assertTrue(!elementos.get(0).getAttribute("class").contains("itemSelected"));
	}
	
	@Test (groups= {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")
	public void TS38538_Problems_with_Refills_Problemas_con_Recargas_Medio_de_recarga_Seleccion_Multiple(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.tarjetaPrepaga();
		driver.findElement(By.id("PrepaidCardData_prevBtn")).click();
		sleep(3000);
		List<WebElement> elementos = driver.findElements(By.cssSelector(".slds-radio.ng-scope"));
		if (!elementos.get(1).getAttribute("class").contains("itemSelected")) {
			elementos.get(1).click();
		}
		sleep(2000);
		Assert.assertTrue(!elementos.get(0).getAttribute("class").contains("itemSelected"));
	}
	
	@Test (groups= {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")  //Rompe por error en el flujo de Tarjeta Prepaga
	public void TS38541_Problems_with_Refills_Problemas_con_Recargas_Medio_de_recarga_Seleccionar_Tarjeta_Pre_Paga_PIN_Visible_Lote_activo(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.tarjetaPrepaga();
		driver.findElement(By.id("BatchNumber")).sendKeys("2222222222222222");
		driver.findElement(By.id("stepPrepaidCardData_nextBtn")).click();
		sleep(5000);
		Assert.assertTrue(driver.findElement(By.id("rechargeImpacted_prevBtn")).isDisplayed());
	}
	
	@Test (groups= {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")
	public void TS38549_Problems_with_Refills_Problemas_con_Recargas_Medio_de_recarga_Lote_Ingresa_15_digitos(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.tarjetaPrepaga();
		WebElement numeroLote = driver.findElement(By.id("BatchNumber"));
		numeroLote.sendKeys("123456789012345");
		Assert.assertTrue(numeroLote.getAttribute("class").contains("ng-invalid-minlength"));
	}
	
	@Test (groups= {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")
	public void TS38550_Problems_with_Refills_Problemas_con_Recargas_Medio_de_recarga_Lote_Ingresa_16_digitos(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.tarjetaPrepaga();
		WebElement numeroLote = driver.findElement(By.id("BatchNumber"));
		numeroLote.sendKeys("1234567890123456");
		Assert.assertTrue(numeroLote.getAttribute("class").contains("ng-valid-minlength"));
		Assert.assertTrue(numeroLote.getAttribute("class").contains("ng-valid-maxlength"));
	}
	
	@Test (groups= {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")
	public void TS38551_Problems_with_Refills_Problemas_con_Recargas_Medio_de_recarga_Lote_Ingresa_17_digitos(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.tarjetaPrepaga();
		WebElement numeroLote = driver.findElement(By.id("BatchNumber"));
		numeroLote.sendKeys("12345678901234567");
		Assert.assertTrue(numeroLote.getAttribute("class").contains("ng-invalid-maxlength"));
	}
	
	@Test (groups= {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")
	public void TS38552_Problems_with_Refills_Problemas_con_Recargas_Medio_de_recarga_Lote_Ingresa_letras(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.tarjetaPrepaga();
		WebElement numeroLote = driver.findElement(By.id("BatchNumber"));
		numeroLote.sendKeys("abcde");
		Assert.assertTrue(numeroLote.getAttribute("class").contains("ng-invalid-pattern"));
	}
	
	@Test (groups= {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")
	public void TS68976_Problems_with_Refills_UX_Tarjeta_de_Recarga_Pre_paga_Verificacion_Visualizar_panel_de_Steps(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.tarjetaPrepaga();
		Assert.assertTrue(driver.findElement(By.cssSelector(".vlc-slds-wizard")).getText().contains("Pasos"));
		Assert.assertTrue(driver.findElement(By.cssSelector(".list-group.vertical-steps")).isDisplayed());
	}
	
	@Test (groups= {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")
	public void TS68977_Problems_with_Refills_UX_Tarjeta_de_Recarga_Pre_paga_Verificacion_Visualizar_Boton_Cancelar(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.tarjetaPrepaga();
		WebElement botonCancelar = driver.findElement(By.cssSelector(".vlc-slds-button--tertiary"));
		Assert.assertTrue(botonCancelar.getText().contains("Cancelar"));
		Assert.assertTrue(botonCancelar.isDisplayed());
	}
	
	@Test (groups= {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")
	public void TS68982_Problems_with_Refills_UX_Tarjeta_de_Recarga_Pre_paga_Verificacion_Visualizar_Titulo(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.tarjetaPrepaga();
		List <WebElement> title = driver.findElements(By.className("slds-page-header__title"));
		boolean a = false;
		for (WebElement x : title) {
			if (x.getText().toLowerCase().contains("datos de la tarjeta")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups= {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")
	public void TS69091_Problems_with_Refills_Problemas_con_Recargas_Base_de_Conocimiento_Tarjeta_Prepaga_Panel_Visualizar_base_de_conocimiento_paso_omniscript(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.tarjetaPrepaga();
		WebElement baseConocimiento = driver.findElement(By.cssSelector(".slds-form-element.slds-lookup.vlc-slds-knowledge-component"));
		Assert.assertTrue(baseConocimiento.isDisplayed());
		Assert.assertTrue(baseConocimiento.getText().contains("Informaci\u00f3n De Recargas"));
	}
	
	@Test (groups= {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")  //No da error al ingresar 15 digitos
	public void TS37326_Problems_With_Refills_Tarjeta_De_Recarga_Prepaga_Verificacion_Numero_De_Lote_Ingresa_15_Digitos(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.tarjetaPrepaga();
		driver.findElement(By.id("BatchNumber")).sendKeys("145789654212458");
		Assert.assertTrue(driver.findElement(By.cssSelector(".vlc-slds-error-block.ng-scope")).findElement(By.cssSelector(".error.ng-scope")).findElement(By.cssSelector(".description.ng-binding")).getText().toLowerCase().equals("longitud m\u00ednima de 16"));
	}
	
	@Test (groups= {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")
	public void TS37328_Problems_With_Refills_Tarjeta_De_Recarga_Prepaga_Verificacion_Numero_De_Lote_Ingresa_Letras(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.tarjetaPrepaga();
		driver.findElement(By.id("BatchNumber")).sendKeys("letrasletrasletr");
		sleep(2000);
		List<WebElement> errores = driver.findElement(By.cssSelector(".vlc-slds-error-block.ng-scope")).findElement(By.cssSelector(".error.ng-scope")).findElements(By.cssSelector(".description.ng-binding"));
		boolean enc = false;
		for (WebElement UnE : errores) {
			if (UnE.getText().toLowerCase().equals("s\u00f3lo se permiten n\u00fameros")) {
				enc = true;
				break;
			}
		}
		Assert.assertTrue(enc);
	}
	
	@Test (groups= {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")
	public void TS37327_Problems_With_Refills_Tarjeta_De_Recarga_Prepaga_Verificacion_Numero_De_Lote_Ingresa_17_Digitos(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.tarjetaPrepaga();
		driver.findElement(By.id("BatchNumber")).sendKeys("12457856321457895");
		List<WebElement> errores = driver.findElement(By.cssSelector(".vlc-slds-error-block.ng-scope")).findElement(By.cssSelector(".error.ng-scope")).findElements(By.cssSelector(".description.ng-binding"));
		boolean enc = false;
		for (WebElement UnE : errores) {
			if (UnE.getText().toLowerCase().equals("longitud m\u00e1xima de 16")) {
				enc = true;
				break;
			}
		}
		Assert.assertTrue(enc);
	}
	
	@Test (groups= {"CustomerCare", "ProblemasConRecargas", "Ola1","filtrado"}, dataProvider = "CustomerCuentaActiva")
	public void TS37325_Problems_With_Refills_Tarjeta_De_Recarga_Prepaga_Verificacion_Numero_De_Lote_Ingresa_16_Digitos(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.tarjetaPrepaga();
		driver.findElement(By.id("BatchNumber")).sendKeys("1245785632145789");
		List<WebElement> errores = driver.findElement(By.cssSelector(".vlc-slds-error-block.ng-scope")).findElement(By.cssSelector(".error.ng-scope")).findElements(By.cssSelector(".description.ng-binding"));
		boolean enc = true;
		for (WebElement UnE : errores) {
			if (!UnE.getText().isEmpty()) {
				enc = false;
				break;
			}
		}
		Assert.assertTrue(enc);
	}
	
	@Test (groups= {"CustomerCare", "ProblemasConRecargas", "Ola1","filtrado"}, dataProvider = "CustomerCuentaActiva")  //Rompe por mensaje de error al finalizar la recarga
	public void TS37534_Problems_With_Refills_Problemas_Con_Recargas_Medio_De_Recarga_Seleccionar_ROL(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.tarjetaPrepaga();
		driver.findElement(By.id("PrepaidCardData_prevBtn")).click();
		sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"RefillMethod|0\"]/div/div[1]/label[2]/span/div/div")).click();
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillDate")));
		driver.findElement(By.id("RefillDate")).sendKeys("22-05-2018");
		driver.findElement(By.id("RefillAmount")).sendKeys("150");
		driver.findElement(By.id("ReceiptCode")).sendKeys("150");
		driver.findElement(By.id("OnlineRefillData_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");		
		driver.findElement(By.id("FileAttach")).sendKeys("C:\\Users\\Nicolas\\Desktop\\descarga.jpg");
		driver.findElement(By.id("AttachDocuments_nextBtn")).click();
		sleep(3000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(8000);
		List <WebElement> msj = driver.findElements(By.className("ta-care-omniscript-done"));
		boolean a = false;
		for (WebElement x : msj) {
			if (x.getText().toLowerCase().contains("recarga realizada con \u00e9xito!")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups= {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")
	public void TS69021_Problems_with_Refills_Problemas_con_Recargas_Base_de_Conocimiento_Tarjeta_Prepaga_OS_Verificar_articulo_en_Base_de_conocimiento(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.tarjetaPrepaga();
		driver.switchTo().defaultContent();
		driver.findElements(By.className("sd_widget_btn_text")).get(0).click();
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.className("blocksettings")));
		Assert.assertTrue(driver.findElement(By.className("blocksettings")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.className("articleListItem")).isDisplayed());
	}
	
	@Test (groups= {"CustomerCare", "ProblemasConRecargas", "Ola1"}, dataProvider = "CustomerCuentaActiva")
	public void TS68984_Problems_with_Refills_UX_Tarjeta_de_Recarga_Pre_paga_Verificacion_Visualizar_Boton_Consultar(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("estado de tarjeta");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-form-element.vlc-flex.vlc-form-group.vlc-slds-remote-action--button.ng-pristine.ng-valid.ng-scope")));
		WebElement boton = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-form-group.vlc-slds-remote-action--button.ng-pristine.ng-valid.ng-scope"));
		Assert.assertTrue(boton.getText().equals("Consultar"));
	}
}