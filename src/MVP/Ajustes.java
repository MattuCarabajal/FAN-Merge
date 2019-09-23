package MVP;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.CBS;
import Pages.CustomerCare;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class Ajustes extends TestBase {

	private WebDriver driver;
	private LoginFw log;
	private CustomerCare cc;
	private CBS cbs;
	private CBS_Mattu cbsm;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private GestionDeClientes_Fw ges;
	String detalles;
	
	
	//@BeforeClass (alwaysRun = true)
	public void initOOCC() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
	}
		
	@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginTelefonico();
		ges.irAConsolaFAN();
		
	}
	
	//@BeforeClass (groups = "PerfilAgente")
		public void initAgente() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginAgente();
		ges.irAConsolaFAN();	
	}
	
	//@BeforeClass (groups = "PerfilBackOffice")
		public void initBackOffice() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginBackOffice();
		ges.irAConsolaFAN();	
	}
	
	@BeforeMethod (alwaysRun = true)
	public void setup() {
		detalles = null;
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
	}

	//@AfterMethod (alwaysRun = true)
	public void after() throws IOException {
		guardarListaTxt(sOrders);
		sOrders.clear();
		tomarCaptura(driver,imagen);
		sleep(2000);
	}

	//@AfterClass (alwaysRun = true)
	public void quit() {
		driver.quit();
		sleep(5000);
	}
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider = "CuentaAjustesPRE")
	public void TS103596_CRM_Movil_REPRO_Ajuste_General_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS103596";
		String datoViejo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		Integer datosInicial = Integer.parseInt(datoViejo);
		boolean gest = false;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		cc.irAGestion("inconvenientes con cargos");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Datos (Mb)");
		driver.findElement(By.id("CantidadDatosms")).sendKeys("123");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("SummaryCaseComment")).sendKeys("Campo Obligatorio");
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(7000);
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito"))
				gest = true;
		}
		
		String datoNuevo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		Integer datosFinal = Integer.parseInt(datoNuevo);
		Assert.assertTrue(datosInicial + (123 * 1024) == datosFinal);
		Assert.assertTrue(gest);
		if (TestBase.urlAmbiente.contains("sit")) {
			String orden = cc.obtenerOrden(driver, "Inconvenientes con cargos tasados y facturados");
			detalles = imagen + " -Ajustes-DNI: " + sDNI + " - Orden: " + orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, orden numero: " + orden + " con numero de DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrden(orden));
		} else {
			String orden = driver.findElement(By.xpath("//*[@id=\"txtSuccessConfirmation\"]/div")).findElement(By.tagName("strong")).getText();
			detalles = imagen + " -Ajustes-DNI: " + sDNI + " - Orden: " + orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, numero de orden: " + orden + " de cuenta con DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrdenYGestion("Inconvenientes con cargos tasados y facturados"));
		}
	}
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider = "CuentaAjustesPRE")
	public void TS112434_CRM_Movil_PRE_Ajuste_Credito_Minutos_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS112434";
		String datoViejo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Segundos Libres");
		Integer datosInicial = Integer.parseInt(datoViejo);
		boolean gest = false;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		cc.irAGestion("inconvenientes con cargos tasados");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Voz");
		driver.findElement(By.id("CantidadVoz")).sendKeys("100000");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("SummaryCaseComment")).sendKeys("Campo Obligatorio");
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(10000);
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito"))
				gest = true;
		}
		String datoNuevo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Segundos Libres");
		Integer datosFinal = Integer.parseInt(datoNuevo);
		Assert.assertTrue(datosInicial + (10 * 3600) == datosFinal);
		Assert.assertTrue(gest);
		if (TestBase.urlAmbiente.contains("sit")) {
			String orden = cc.obtenerOrden(driver, "Inconvenientes con cargos tasados y facturados");
			detalles = imagen + " -Ajustes - DNI: " + sDNI + " - Orden: " + orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, orden numero: " + orden + " con numero de DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrden(orden));		
		} else {
			String orden = driver.findElement(By.xpath("//*[@id=\"txtSuccessConfirmation\"]/div")).findElement(By.tagName("strong")).getText();
			detalles = imagen + " -Ajustes - DNI: " + sDNI + " - Orden: " + orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, numero de orden: " + orden + " de cuenta con DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrdenYGestion("Inconvenientes con cargos tasados y facturados"));
		}
	}
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider = "CuentaAjustesPRE")
	public void TS112435_CRM_Movil_PRE_Ajuste_Credito_SMS_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS112435";
		String datoViejo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "SMS Libres");
		Integer datosInicial = Integer.parseInt(datoViejo);
		boolean gest = false;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		cc.irAGestion("inconvenientes con cargos tasados");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "SMS");
		driver.findElement(By.id("CantidadDatosms")).sendKeys("123");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("SummaryCaseComment")).sendKeys("Campo Obligatorio");
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(10000);
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito"))
				gest = true;
		}
		String datoNuevo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "SMS Libres");
		Integer datosFinal = Integer.parseInt(datoNuevo);
		Assert.assertTrue(datosInicial + 123 == datosFinal);
		Assert.assertTrue(gest);
		if (TestBase.urlAmbiente.contains("sit")) {
			String orden = cc.obtenerOrden(driver, "Inconvenientes con cargos tasados y facturados");
			detalles = imagen + " -Ajustes - DNI: " + sDNI + " - Orden: " + orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, orden numero: " + orden + " con numero de DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrden(orden));		
		} else {
			String orden = driver.findElement(By.xpath("//*[@id=\"txtSuccessConfirmation\"]/div")).findElement(By.tagName("strong")).getText();
			detalles = imagen + " -Ajustes - DNI: " + sDNI + " - Orden: " + orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, numero de orden: " + orden + " de cuenta con DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrdenYGestion("Inconvenientes con cargos tasados y facturados"));
		}
	}
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider = "CuentaAjustesPRE")
	public void TS103599_CRM_Movil_REPRO_Se_crea_caso_de_ajuste_menor_a_500_pesos_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS103599";
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		boolean gest = false;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(20000);
		cc.irAGestion("inconvenientes con cargos tasados");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Credito");
		driver.findElement(By.id("CantidadMonto")).sendKeys("10000");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("SummaryCaseComment")).sendKeys("Campo Obligatorio");
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(10000);
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito"))
				gest = true;
		}
		Assert.assertTrue(gest);
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 5));
		Assert.assertTrue(datosInicial + 10000 == datosFinal);
		if (TestBase.urlAmbiente.contains("sit")) {
			String orden = cc.obtenerOrden(driver, "Inconvenientes con cargos tasados y facturados");
			detalles = imagen + " -Ajustes-DNI: " + sDNI + " - Orden: " + orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, orden numero: " + orden + " con numero de DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrden(orden));		
		} else {
			String orden = driver.findElement(By.xpath("//*[@id=\"txtSuccessConfirmation\"]/div")).findElement(By.tagName("strong")).getText();
			detalles = imagen + " -Ajustes-DNI: " + sDNI + " - Orden: " + orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, numero de orden: " + orden + " de cuenta con DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrdenYGestion("Inconvenientes con cargos tasados y facturados"));
		}
	}
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider = "CuentaAjustesPRE")
	public void TS112437_CRM_Movil_PRE_Ajuste_Credito_MB_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS112437";
		detalles = imagen + " -Ajustes - DNI: " + sDNI;
		String datoViejo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		Integer datosInicial = Integer.parseInt(datoViejo);
		boolean gest = false;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		cc.irAGestion("inconvenientes con cargos tasados");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Datos (Mb)");
		driver.findElement(By.id("CantidadDatosms")).sendKeys("123");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("SummaryCaseComment")).sendKeys("Campo Obligatorio");
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(7000);
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito"))
				gest = true;
		}
		Assert.assertTrue(gest);
		String datoNuevo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		Integer datosFinal = Integer.parseInt(datoNuevo);
		Assert.assertTrue(datosInicial + (123 * 1024) == datosFinal);
	}
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider = "CuentaAjustesPRE")
	public void TS112452_CRM_Movil_PRE_Ajuste_Nota_de_Credito_Derivacion_a_rango_superior_1900_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS112452";
		boolean gest = false;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		cc.irAGestion("inconvenientes con cargos tasados");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta repro");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Credito");
		driver.findElement(By.id("CantidadMonto")).sendKeys("200000");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("SummaryCaseComment")).sendKeys("Campo Obligatorio");
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(10000);
		List <WebElement> element = driver.findElements(By.className("ta-care-omniscript-done"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("el caso fue derivado para autorizaci\u00f3n"))
				gest = true;
		}
		Assert.assertTrue(gest);
		if (TestBase.urlAmbiente.contains("sit")) {
			String orden = cc.obtenerOrden(driver, "Inconvenientes con cargos tasados y facturados");
			detalles = imagen + " -Ajustes - DNI: " + sDNI + " - Orden: " + orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, orden numero: " + orden + " con numero de DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrden(orden));		
		} else {
			String orden = driver.findElement(By.cssSelector(".vlc-slds-inline-control__label.ng-binding")).getText();
			detalles = imagen + " -Ajustes - DNI: " + sDNI + " - Orden: " + orden;
			Assert.assertTrue(cc.verificarOrdenYGestion("Inconvenientes con cargos tasados y facturados"));
		}
	}
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider = "CuentaAjustesPRE")
	public void TS129320_CRM_Movil_REPRO_Escalamiento_segun_RAV_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS129320";
		boolean gest = false;
		WebElement monto = null;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		cc.irAGestion("inconvenientes con cargos tasados");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Credito");
		driver.findElement(By.id("CantidadMonto")).sendKeys("200000");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(7000);
		WebElement fact = driver.findElement(By.cssSelector(".slds-card.ng-scope")).findElements(By.cssSelector(".slds-card__header.slds-grid")).get(1);
		List <WebElement> list = fact.findElements(By.tagName("li"));
		for (WebElement x : list) {
			if (x.getText().toLowerCase().contains("monto"))
				monto = x;
		}
		Assert.assertTrue(monto.getText().equalsIgnoreCase("Monto: 2000"));
		driver.findElement(By.id("SummaryCaseComment")).sendKeys("Campo Obligatorio");
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(10000);
		List <WebElement> element = driver.findElements(By.className("ta-care-omniscript-done"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("el caso fue derivado para autorizaci\u00f3n"))
				gest = true;
		}
		Assert.assertTrue(gest);
		if (TestBase.urlAmbiente.contains("sit")) {
			String orden = cc.obtenerOrden(driver, "Inconvenientes con cargos tasados y facturados");
			detalles = imagen + " -Ajustes - DNI: " + sDNI + " - Orden: " + orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, orden numero: " + orden + " con numero de DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrden(orden));		
		} else {
			String orden = driver.findElement(By.cssSelector(".vlc-slds-inline-control__label.ng-binding")).getText();
			detalles = imagen + " -Ajustes - DNI: " + sDNI + " - Orden: " + orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, numero de orden: " + orden + " de cuenta con DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrdenYGestion("Inconvenientes con cargos tasados y facturados"));
		}
	}
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider = "CuentaAjustesPRE")
	public void TS129317_CRM_Movil_REPRO_Ajuste_RAV_Unidades_Libres_a_Pesos_General_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS129317";
		WebElement monto = null;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		cc.irAGestion("inconvenientes con cargos tasados");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Voz");
		driver.findElement(By.id("CantidadVoz")).sendKeys("100000");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(7000);
		WebElement fact = driver.findElement(By.cssSelector(".slds-card.ng-scope")).findElements(By.cssSelector(".slds-card__header.slds-grid")).get(1);
		List <WebElement> list = fact.findElements(By.tagName("li"));
		for (WebElement x : list)
			if (x.getText().toLowerCase().contains("monto"))
				monto = x;
		Assert.assertTrue(monto.getText().equalsIgnoreCase("Monto: 78.00"));		
	}
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider = "CuentaAjustesPRE")
	public void TS135708_CRM_Movil_REPRO_Ajuste_Credito_Minutos_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS135708";
		String datoViejo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Segundos Libres");
		Integer datosInicial = Integer.parseInt(datoViejo);
		boolean gest = false;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		cc.irAGestion("inconvenientes con cargos tasados");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Voz");
		driver.findElement(By.id("CantidadVoz")).sendKeys("100000");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(10000);
		driver.findElement(By.id("SummaryCaseComment")).sendKeys("Campo Obligatorio");
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(10000);
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito"))
				gest = true;
		}
		String datoNuevo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Segundos Libres");
		Integer datosFinal = Integer.parseInt(datoNuevo);
		Assert.assertTrue(datosInicial + (10 * 3600) == datosFinal);
		Assert.assertTrue(gest);
		if (TestBase.urlAmbiente.contains("sit")) {
			String orden = cc.obtenerOrden(driver, "Inconvenientes con cargos tasados y facturados");
			detalles = imagen + " -Ajustes - DNI: " + sDNI + " - Orden: " + orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, orden numero: " + orden + " con numero de DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrden(orden));		
		} else {
			String orden = driver.findElement(By.xpath("//*[@id=\"txtSuccessConfirmation\"]/div")).findElement(By.tagName("strong")).getText();
			detalles = imagen + " -Ajustes - DNI: " + sDNI + " - Orden: " + orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, numero de orden: " + orden + " de cuenta con DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrdenYGestion("Inconvenientes con cargos tasados y facturados"));
		}
	}
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider = "CuentaAjustesPRE")
	public void TS135705_CRM_Movil_PRE_Ajuste_RAV_Unidades_Libres_a_Pesos_General_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS135705";
		WebElement monto = null;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		cc.irAGestion("inconvenientes con cargos tasados");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Voz");
		driver.findElement(By.id("CantidadVoz")).sendKeys("100000");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(7000);
		WebElement fact = driver.findElement(By.cssSelector(".slds-card.ng-scope")).findElements(By.cssSelector(".slds-card__header.slds-grid")).get(1);
		List <WebElement> list = fact.findElements(By.tagName("li"));
		for (WebElement x : list)
			if (x.getText().toLowerCase().contains("monto")) {
				monto = x;
		}
		Assert.assertTrue(monto.getText().equalsIgnoreCase("Monto: 78.00"));
	}
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider = "CuentaAjustesPRE")
	public void TS136535_CRM_Movil_REPRO_Ajuste_General_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS136535";
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		boolean gestion = false;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		cc.irAGestion("inconvenientes con cargos tasados");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Credito");
		driver.findElement(By.id("CantidadMonto")).sendKeys("10000");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("SummaryCaseComment")).sendKeys("Campo Obligatorio");
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-pending-icon")));
		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
			if (x.getText().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito"))
				gestion = true;
		}
		Assert.assertTrue(gestion);
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 5));
		Assert.assertTrue(datosInicial + 10000 == datosFinal);
	}
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider = "CuentaAjustesPRE")
	public void TS136536_CRM_Movil_REPRO_Escalamiento_segun_RAV_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS136536";
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		System.out.println(datosInicial);
		boolean gestion = false;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		cc.irAGestion("inconvenientes con cargos tasados");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Credito");
		driver.findElement(By.id("CantidadMonto")).sendKeys("200000");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("SummaryCaseComment")).sendKeys("Campo Obligatorio");
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(10000);
		WebElement nroCaso = null;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-pending-icon")));
		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
			if (x.getText().toLowerCase().contains("el caso fue derivado para autorizaci\u00f3n")) {
				gestion = true;
				nroCaso = x;
			}
		}
		Assert.assertTrue(gestion);
		String caso = nroCaso.findElement(By.cssSelector(".vlc-slds-inline-control__label.ng-binding")).getText();
		caso = caso.substring(caso.indexOf("0"), caso.length());
		System.out.println(caso);		
		driver.get("https://telecomcrm--sit02.cs91.my.salesforce.com/home/home.jsp?sdtd=1");
		driver.findElement(By.cssSelector(".menuButton.menuButtonRounded")).click();
		driver.findElement(By.id("userNav-menuItems")).findElements(By.tagName("a")).get(2).click();
		loginBackOffice(driver);
		cc.cerrarTodasLasPestanas();
		goToLeftPanel2(driver, "Casos");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".topNav.primaryPalette")));
		selectByText(driver.findElement(By.cssSelector(".topNav.primaryPalette")).findElement(By.name("fcf")), "BO Centralizado");
		WebElement filaDelCaso = null;
		WebElement tabla = driver.findElement(By.className("x-grid3-body"));
		System.out.println("esta es la tabla: " + tabla.getText());
		for (WebElement x : tabla.findElements(By.tagName("tr"))) {
			if (x.getText().contains(caso))
				filaDelCaso = x;
		}				
		filaDelCaso.findElement(By.tagName("input")).click();		
		driver.findElement(By.cssSelector(".linkBar.brandSecondaryBrd")).findElement(By.name("accept")).click();
		sleep(5000);
		cc.buscarCaso(caso);
		driver.switchTo().frame(cambioFrame(driver, By.name("edit")));
		WebElement aprobar = null;
		for (WebElement x : driver.findElements(By.className("actionColumn"))) {
			if (x.getText().contains("Aprobar/rechazar"))
				aprobar = x;
		}
		for (WebElement x : aprobar.findElements(By.tagName("a"))) {
			if (x.getText().contains("Aprobar/rechazar"))
				x.click();
		}
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.name("goNext")));
		driver.findElement(By.name("goNext")).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("extraStatusDiv_A")));
		Assert.assertTrue(driver.findElement(By.className("extraStatusDiv_A")).getText().equalsIgnoreCase("Aprobado"));
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 5));
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + 200000 == datosFinal);
		detalles = imagen + " -Ajustes-DNI: " + sDNI + ", Caso numero: " + caso;
	}
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider = "CuentaAjustesPRE")
	public void TS103596_CRM_Movil_Pre_Ajuste_General_Derivacion_manual_Crm_OC(String sDNI, String sLinea) {
		imagen = "TS103596";
		ges.BuscarCuenta("DNI", sDNI);
		cc.irAGestion("inconvenientes con cargos tasados");
		cambioDeFrame(driver, By.id("Step-TipodeAjuste_nextBtn"), 0);
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Informacion incorrecta");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-AssetSelection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-VerifyPreviousAdjustments_prevBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-AjusteNivelLinea_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector("[class = 'slds-form-element__label ng-binding ng-scope']")), "equals", "agregar unidades");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(5000);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SummaryDerivateToBO_nextBtn")));
		driver.findElement(By.id("SummaryDerivateToBO_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header h1")).getText().equalsIgnoreCase("la gesti\u00f3n se deriv\u00f3 al area equipo back office centralizado"));
		String caso = driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header p label")).getText();
		String nCaso = cc.getNumeros(caso);
		System.out.println(cc.getNumeros(caso));
		ges.cerrarPestaniaGestion(driver);
		driver.findElement(By.id("phSearchInput")).sendKeys(nCaso);
		driver.findElement(By.id("phSearchInput")).submit();
		cambioDeFrame(driver, By.id("searchPageHolderDiv"), 0);
		sleep(2000);
		String estadoDelCaso = driver.findElement(By.cssSelector("[class = 'listRelatedObject caseBlock'] [class = 'bPageBlock brandSecondaryBrd secondaryPalette'] [class = 'pbBody'] table tbody tr [class = ' dataCell  cellCol3 ']")).getText();
		Assert.assertTrue(estadoDelCaso.equalsIgnoreCase("derivada"));
	}
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider="CuentaAjustesPRE")
	public void TS121281CRM_Movil_Pre_Ajuste_Nota_de_Credito_Monto_1500_Aprobador_BO_Sin_revisor_Exepcion_Crm_OC(String sDNI, String sLinea){
		ges.BuscarCuenta("DNI", "91021694");
		sleep(5000);
		cc.irAGestion("inconvenientes con cargos");
		cambioDeFrame(driver, By.id("Step-TipodeAjuste_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "FACTURA EMITIDA");
		selectByText(driver.findElement(By.id("CboTipo")), "Cargos fijos facturados");
		selectByText(driver.findElement(By.id("CboItem")), "Abono/proporcional");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step1-SelectBillingAccount_nextBtn")));
		driver.findElement(By.cssSelector("[class='slds-radio__label'] [class='slds-form-element__label ng-binding']")).click();
		driver.findElement(By.id("Step1-SelectBillingAccount_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-VerifyPreviousAdjustments_prevBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-AjusteNivelCuenta_nextBtn")));
		driver.findElement(By.xpath("//*[@class='slds-radio ng-scope']//span[text()='Nota de cr\u00e9dito']")).click();
		driver.findElements(By.xpath("//*[@class='slds-radio ng-scope']//span[text()= 'No']")).get(1).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("MontoLibre")));
		driver.findElement(By.id("MontoLibre")).sendKeys("250000");
		selectByText(driver.findElement(By.id("SelectItemAjusteLibre")), "Ajuste Minutos");
		driver.findElement(By.id("Step-AjusteNivelCuenta_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-Summary_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("Step-Summary_nextBtn")));
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='ta-care-omniscript-done']")));
		Assert.assertTrue(driver.findElement(By.xpath("//*[@class='ta-care-omniscript-done']//header//h1")).getText().toLowerCase().contains("el caso fue derivado para autorizaci\u00f3n"));
		String orden = driver.findElement(By.xpath("//*[@class='ta-care-omniscript-done']//header//label")).getText();
		orden = orden.substring(orden.lastIndexOf(" ")+1, orden.length());
		System.out.println(orden);
		Assert.assertTrue(cc.aprobarAjusteConPerfilBOYDirector(orden, "Ua2544674"));
		Assert.assertTrue(false);  //No se impacta el ajuste		
	}
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider="CuentaAjustesPRE")
	public void TS112446_CRM_Movil_Pre_Ajuste_Nota_de_Credito_Monto_15000_Aprobador_Director_Revisor_BO_Exepcion_Crm_OC(String sDNI, String sLinea) throws Exception {
		ges.BuscarCuenta("DNI", "91021694");
		sleep(5000);
		cc.irAGestion("inconvenientes con cargos tasados");
		cambioDeFrame(driver, By.id("Step-TipodeAjuste_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "FACTURA EMITIDA");
		selectByText(driver.findElement(By.id("CboTipo")), "Cargos fijos facturados");
		selectByText(driver.findElement(By.id("CboItem")), "Abono/proporcional");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step1-SelectBillingAccount_nextBtn")));
		driver.findElement(By.cssSelector("[class='slds-radio__label'] [class='slds-form-element__label ng-binding']")).click();
		driver.findElement(By.id("Step1-SelectBillingAccount_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-VerifyPreviousAdjustments_prevBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-AjusteNivelCuenta_nextBtn")));
		driver.findElement(By.xpath("//*[@class='slds-radio ng-scope']//span[text()='Nota de cr\u00e9dito']")).click();
		driver.findElements(By.xpath("//*[@class='slds-radio ng-scope']//span[text()= 'No']")).get(1).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("MontoLibre")));
		driver.findElement(By.id("MontoLibre")).sendKeys("1500000");
		selectByText(driver.findElement(By.id("SelectItemAjusteLibre")), "Ajuste Minutos");
		driver.findElement(By.id("Step-AjusteNivelCuenta_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-Summary_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("Step-Summary_nextBtn")));
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='ta-care-omniscript-done']")));
		Assert.assertTrue(driver.findElement(By.xpath("//*[@class='ta-care-omniscript-done']//header//h1")).getText().toLowerCase().contains("el caso fue derivado para autorizaci\u00f3n"));
		String orden = driver.findElement(By.xpath("//*[@class='ta-care-omniscript-done']//header//label")).getText();
		orden = orden.substring(orden.lastIndexOf(" ")+1, orden.length());
		System.out.println(orden);
		Assert.assertTrue(cc.aprobarAjusteConPerfilBOYDirector(orden, "Ua2544674"));
		Assert.assertTrue(false);  //No se impacta el ajuste
	}
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider="CuentaAjustesPRE")
	public void TS112430_CRM_Movil_Pre_Ajuste_Backoffice_modifica_cantidades_Crm_OC(String sDNI, String sLinea) {
		boolean gestion = false;
		ges.BuscarCuenta("DNI", "91021694");
		sleep(5000);
		cc.irAGestion("inconvenientes con cargos tasados");
		cambioDeFrame(driver, By.id("Step-TipodeAjuste_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "FACTURA EMITIDA");
		selectByText(driver.findElement(By.id("CboTipo")), "Cargos fijos facturados");
		selectByText(driver.findElement(By.id("CboItem")), "Abono/proporcional");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step1-SelectBillingAccount_nextBtn")));
		driver.findElement(By.cssSelector("[class='slds-radio__label'] [class='slds-form-element__label ng-binding']")).click();
		driver.findElement(By.id("Step1-SelectBillingAccount_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-VerifyPreviousAdjustments_prevBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-AjusteNivelCuenta_nextBtn")));
		driver.findElement(By.xpath("//*[@class='slds-radio ng-scope']//span[text()='Nota de cr\u00e9dito']")).click();
		driver.findElements(By.xpath("//*[@class='slds-radio ng-scope']//span[text()= 'No']")).get(1).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("MontoLibre")));
		driver.findElement(By.id("MontoLibre")).sendKeys("1000000");
		selectByText(driver.findElement(By.id("SelectItemAjusteLibre")), "Ajuste Minutos");
		driver.findElement(By.id("Step-AjusteNivelCuenta_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-Summary_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("Step-Summary_nextBtn")));
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='ta-care-omniscript-done']")));
		Assert.assertTrue(driver.findElement(By.xpath("//*[@class='ta-care-omniscript-done']//header//h1")).getText().toLowerCase().contains("el caso fue derivado para autorizaci\u00f3n"));
		String orden = driver.findElement(By.xpath("//*[@class='ta-care-omniscript-done']//header//label")).getText();
		orden = orden.substring(orden.lastIndexOf(" ")+1, orden.length());
		System.out.println(orden);
		ges.cambiarPerfil("Ua2569324");
		ges.irAConsolaFAN();
		cc.buscarCaso(orden);
		cambioDeFrame(driver, By.id("topButtonRow"), 0);
		driver.findElement(By.xpath("//*[@class='pbBody']//a[text()='Continuar Ajuste']")).click();
		cambioDeFrame(driver, By.id("Step-AjusteNivelCuenta_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-AjusteNivelCuenta_nextBtn")));
		driver.findElement(By.xpath("//*[@class='slds-radio ng-scope']//span[text()='Nota de cr\u00e9dito']")).click();
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("MontoLibre")).sendKeys("10000");
		selectByText(driver.findElement(By.id("SelectItemAjusteLibre")), "Ajuste Minutos");
		driver.findElement(By.id("Step-AjusteNivelCuenta_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-Summary_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("Step-Summary_nextBtn")));
		cambioDeFrame(driver, By.id("NewAdjustment"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("NewAdjustment")));
		if (driver.findElement(By.cssSelector("[class='slds-size--1-of-1 slds-medium-size--12-of-12'] [class='slds-form-element vlc-flex vlc-slds-text-block vlc-slds-rte ng-pristine ng-valid ng-scope'] h1")).getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito"))
			gestion = true;
		ges.cambiarPerfil("Ua2544674");
		ges.irAConsolaFAN();
		Assert.assertTrue(gestion);
		Assert.assertTrue(false);  //No se impacta la recarga
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"PerfilTelefonico", "MVP"}, dataProvider = "CuentaAjustesPRE")
	public void TS121138_CRM_Movil_REPRO_Ajuste_Credito_FAN_Front_Telefonico_BO(String sDNI, String sLinea) {
		imagen = "TS121138";
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		boolean gest = false;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(20000);
		cc.irAGestion("inconvenientes con cargos");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Credito");
		driver.findElement(By.id("CantidadMonto")).sendKeys("10000");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(10000);
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito"))
				gest = true;
		}
		Assert.assertTrue(gest);
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 5));
		Assert.assertTrue(datosInicial + 10000 == datosFinal);
		if (TestBase.urlAmbiente.contains("sit")) {
			String orden = cc.obtenerOrden(driver, "Inconvenientes con cargos tasados y facturados");
			detalles = imagen + " -Ajustes-DNI: " + sDNI + " - Orden: " + orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, orden numero: " + orden + " con numero de DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrden(orden));		
		} else {
			String orden = driver.findElement(By.xpath("//*[@id=\"txtSuccessConfirmation\"]/div")).findElement(By.tagName("strong")).getText();
			detalles = imagen + " -Ajustes-DNI: " + sDNI + " - Orden: " + orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, numero de orden: " + orden + " de cuenta con DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrdenYGestion("Inconvenientes con cargos tasados y facturados"));
		}
	}
	
	@Test (groups = {"PerfilTelefonico", "MVP"}, dataProvider = "CuentaAjustesPRE")
	public void TS129321_CRM_Movil_REPRO_Escalamiento_segun_RAV_FAN_Front_Telefonico(String sDNI, String sLinea) {
		imagen = "TS129321";
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		boolean gestion = false;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		cc.irAGestion("inconvenientes con cargos tasados");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Credito");
		driver.findElement(By.id("CantidadMonto")).sendKeys("200000");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(10000);
		WebElement nroCaso = null;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-pending-icon")));
		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
			if (x.getText().toLowerCase().contains("el caso fue derivado para autorizaci\u00f3n")) {
				gestion = true;
				nroCaso = x;
			}
		}
		Assert.assertTrue(gestion);
		String caso = nroCaso.findElement(By.cssSelector(".vlc-slds-inline-control__label.ng-binding")).getText();
		caso = caso.substring(caso.indexOf("0"), caso.length());
		sleep(5000);
		CambiarPerfil("backoffice", driver);
		driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
		sleep(15000);
		cc.cerrarTodasLasPestanas();
		goToLeftPanel2(driver, "Casos");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".topNav.primaryPalette")));
		selectByText(driver.findElement(By.cssSelector(".topNav.primaryPalette")).findElement(By.name("fcf")), "BO Centralizado");
		WebElement filaDelCaso = null;
		WebElement tabla = driver.findElement(By.className("x-grid3-body"));
		for (WebElement x : tabla.findElements(By.tagName("tr"))) {
			if (x.getText().contains(caso))
				filaDelCaso = x;
		}
		filaDelCaso.findElement(By.tagName("input")).click();
		driver.findElement(By.cssSelector(".linkBar.brandSecondaryBrd")).findElement(By.name("accept")).click();
		sleep(5000);
		cc.buscarCaso(caso);
		driver.switchTo().frame(cambioFrame(driver, By.name("edit")));
		WebElement aprobar = null;
		for (WebElement x : driver.findElements(By.className("actionColumn"))) {
			if (x.getText().contains("Aprobar/rechazar"))
				aprobar = x;
		}
		for (WebElement x : aprobar.findElements(By.tagName("a"))) {
			if (x.getText().contains("Aprobar/rechazar"))
				x.click();
		}
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.name("goNext")));
		driver.findElement(By.name("goNext")).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("extraStatusDiv_A")));
		Assert.assertTrue(driver.findElement(By.className("extraStatusDiv_A")).getText().equalsIgnoreCase("Aprobado"));
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 5));
		Assert.assertTrue(datosInicial + 2000 == datosFinal);
		detalles = imagen + " -Ajustes-DNI: " + sDNI + ", Caso numero: " + caso;
	}
	
	@Test (groups = {"PerfilTelefonico", "MVP"}, dataProvider = "CuentaAjustesPRE")
	public void TS121271_CRM_Movil_Pre_Ajuste_Nota_de_Credito_CrmTelefonico() {
		ges.BuscarCuenta("DNI", "91021694");
		sleep(5000);
		cc.irAGestion("inconvenientes con cargos tasados");
		cambioDeFrame(driver, By.id("Step-TipodeAjuste_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "FACTURA EMITIDA");
		selectByText(driver.findElement(By.id("CboTipo")), "Cargos fijos facturados");
		selectByText(driver.findElement(By.id("CboItem")), "Abono/proporcional");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step1-SelectBillingAccount_nextBtn")));
		driver.findElement(By.cssSelector("[class='slds-radio__label'] [class='slds-form-element__label ng-binding']")).click();
		driver.findElement(By.id("Step1-SelectBillingAccount_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-VerifyPreviousAdjustments_prevBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-AjusteNivelCuenta_nextBtn")));
		driver.findElement(By.xpath("//*[@class='slds-radio ng-scope']//span[text()='Nota de cr\u00e9dito']")).click();
		driver.findElements(By.xpath("//*[@class='slds-radio ng-scope']//span[text()= 'No']")).get(1).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("MontoLibre")));
		driver.findElement(By.id("MontoLibre")).sendKeys("1000000");
		selectByText(driver.findElement(By.id("SelectItemAjusteLibre")), "Ajuste Minutos");
		driver.findElement(By.id("Step-AjusteNivelCuenta_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-Summary_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("Step-Summary_nextBtn")));
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='ta-care-omniscript-done']")));
		Assert.assertTrue(driver.findElement(By.xpath("//*[@class='ta-care-omniscript-done']//header//h1")).getText().toLowerCase().contains("el caso fue derivado para autorizaci\u00f3n"));
		String orden = driver.findElement(By.xpath("//*[@class='ta-care-omniscript-done']//header//label")).getText();
		orden = orden.substring(orden.lastIndexOf(" ")+1, orden.length());
		System.out.println(orden);
		Assert.assertTrue(cc.aprobarAjusteConPerfilBOYDirector(orden, "Ua2591324"));		
	}
	
	@Test(groups = {"PerfilTelefonico", "MVP"}, dataProvider = "CuentaAjustesPRE")
	public void TS160697CRM_Movil_Pre_Ajuste_Credito_Monto_20000_Aprobador_Director_Revisor_BO_Ordinaria_Crm_Telefonico(String sDNI, String sLinea) {
		imagen = "TS103596";
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 7));
		System.out.println(datosInicial);
		ges.BuscarCuenta("DNI", "91021695");
		sleep(5000);
		cc.irAGestion("inconvenientes con cargos tasados");
		cambioDeFrame(driver, By.id("Step-TipodeAjuste_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Informacion incorrecta");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-AssetSelection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-VerifyPreviousAdjustments_prevBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-AjusteNivelLinea_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Credito");
		driver.findElement(By.id("CantidadMonto")).sendKeys("2000000");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-Summary_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("Step-Summary_nextBtn")));
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='ta-care-omniscript-done']")));
		Assert.assertTrue(driver.findElement(By.xpath("//*[@class='ta-care-omniscript-done']//header//h1")).getText().toLowerCase().contains("el caso fue derivado para autorizaci\u00f3n"));
		String orden = driver.findElement(By.xpath("//*[@class='ta-care-omniscript-done']//header//label")).getText();
		orden = orden.substring(orden.lastIndexOf(" ")+1, orden.length());
		System.out.println(orden);
		Assert.assertTrue(cc.aprobarAjusteConPerfilBOYDirector(orden, "Ua2591324"));
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 7));
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + 20000000 == datosFinal);
	}
	
	@Test (groups = {"PerfilTelefonico", "MVP"}, dataProvider = "CuentaAjustesPRE")
	public void TS125225_CRM_Movil_Pre_Limitar_cantidad_Ajuste_Por_Usuario_Crm_Telefonico() {
		Assert.assertTrue(false);  //No hay cuenta con el maximo de ajustes hecho
	}
	
	@Test (groups = {"PerfilTelefonico", "MVP"}, dataProvider = "CuentaAjustesPRE")
	public void TS135376_CRM_Movil_Prepago_Otros_Historiales_Historial_de_ajustes_Seleccion_de_Fechas_Ajuste_positivo_FAN_Front_Telefonico(String sDNI, String sLinea) throws ParseException {
		imagen = "TS135376";
		boolean verificarFecha = false;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(20000);
		cc.irAHistoriales();
		sleep(5000);
		WebElement historialDeAjustes = null;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button_brand")));
		for (WebElement x : driver.findElements(By.className("slds-card"))) {
			if (x.getText().toLowerCase().contains("historial de ajustes"))
				historialDeAjustes = x;
		}
		historialDeAjustes.findElement(By.cssSelector(".slds-button.slds-button_brand")).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String desde = driver.findElement(By.id("text-input-id-1")).getAttribute("value");
		String hasta = driver.findElement(By.id("text-input-id-2")).getAttribute("value");
		Date fechaDesde = sdf.parse(desde);
		Date fechaHasta = sdf.parse(hasta);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		sleep(3000);
		WebElement tabla = driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.via-slds-table-pinned-header")).findElements(By.tagName("tbody")).get(1);
		String fechaDeAjuste = tabla.findElement(By.tagName("tr")).findElements(By.tagName("td")).get(0).getText();
		fechaDeAjuste = fechaDeAjuste.substring(0, 10);
		Date fechaAjuste = sdf.parse(fechaDeAjuste);
		if (fechaAjuste.compareTo(fechaDesde) >= 0 && fechaAjuste.compareTo(fechaHasta) <= 0)
			verificarFecha = true;
		Assert.assertTrue(verificarFecha);
	}
	
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test (groups = {"PerfilAgente", "MVP"}, dataProvider = "RecargasHistorias")
	public void TS135380_CRM_Movil_Prepago_Otros_Historiales_Historial_de_ajustes_Ordenamiento_por_Motivo_de_ajuste_FAN_Front_Agente(String sDNI, String sLinea) throws ParseException {
		imagen = "TS135380";
		boolean verificarFecha = false;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		cc.irAHistoriales();
		sleep(10000);
		WebElement historialDeAjustes = null;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button_brand")));
		for (WebElement x : driver.findElements(By.className("slds-card"))) {
			if (x.getText().toLowerCase().contains("historial de ajustes"))
				historialDeAjustes = x;
		}
		historialDeAjustes.findElement(By.cssSelector(".slds-button.slds-button_brand")).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String desde = driver.findElement(By.id("text-input-id-1")).getAttribute("value");
		String hasta = driver.findElement(By.id("text-input-id-2")).getAttribute("value");
		Date fechaDesde = sdf.parse(desde);
		Date fechaHasta = sdf.parse(hasta);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		sleep(3000);
		WebElement tabla = driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.via-slds-table-pinned-header")).findElements(By.tagName("tbody")).get(1);
		String fechaDeAjuste = tabla.findElement(By.tagName("tr")).findElements(By.tagName("td")).get(0).getText();
		fechaDeAjuste = fechaDeAjuste.substring(0, 10);
		Date fechaAjuste = sdf.parse(fechaDeAjuste);
		if (fechaAjuste.compareTo(fechaDesde) >= 0 && fechaAjuste.compareTo(fechaHasta) <= 0)
			verificarFecha = true;
		Assert.assertTrue(verificarFecha);
	}
	
	//----------------------------------------------- BACKOFFICE -------------------------------------------------------\\
	
	@Test (groups = {"PerfilBackOffice", "MVP"}, dataProvider = "CuentaAjustesPRE")
	public void TS121329_CRM_Movil_PRE_Ajuste_Backoffice_modifica_cantidades(String sDNI, String sLinea) {
		imagen = "TS121329";
		detalles = null;
		detalles = imagen + " -Ajustes-DNI: " + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		System.out.println(datosInicial);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		ges.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("inconvenientes");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Informacion incorrecta");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("04-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("24-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Credito");
		driver.findElement(By.id("CantidadMonto")).sendKeys("60000");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(10000);
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(7000);
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 5));
		System.out.println(datosFinal);
		String nroCaso = driver.findElement(By.cssSelector(".vlc-slds-inline-control__label.ng-binding")).getText();
		nroCaso = nroCaso.substring(nroCaso.indexOf("0"), nroCaso.length());
		CambiarPerfil("backoffice", driver);
		driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
		sleep(18000);
		cc.cerrarTodasLasPestanas();
		cc.buscarCaso(nroCaso);
		driver.switchTo().frame(cambioFrame(driver, By.name("edit")));
		WebElement list = driver.findElement(By.xpath("//*[@id=\"ep\"]/div[2]/div[2]/table"));
		list.findElements(By.tagName("td")).get(1).findElements(By.tagName("a")).get(1).click();
		sleep(5000);
		selectByText(driver.findElement(By.id("newOwn_mlktp")), "Usuario");
		driver.findElement(By.id("newOwn")).sendKeys("Marcelo Aletta");
		driver.findElement(By.name("save")).click();
		sleep(5000);
		driver.findElements(By.className("actionLink")).get(2).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.name("goNext")));
		driver.findElement(By.name("goNext")).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("extraStatusDiv_A")));
		Assert.assertTrue(driver.findElement(By.className("extraStatusDiv_A")).getText().equalsIgnoreCase("Aprobado"));
	}
}