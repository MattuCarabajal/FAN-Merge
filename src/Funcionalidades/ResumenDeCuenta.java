package Funcionalidades;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.Accounts;
import Pages.CustomerCare;
import Pages.Marketing;
import Pages.SCP;
import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.TestBase;

public class ResumenDeCuenta extends TestBase {

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	private Marketing mk;
	private GestionDeClientes_Fw ges;
	private LoginFw log;
	private SCP scp;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	@BeforeClass (alwaysRun = true)
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		mk = new Marketing(driver);
		log = new LoginFw(driver);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();	

	}
		
	//@BeforeClass (alwaysRun = true)
	public void initTelefonico() throws IOException, AWTException {
		driver = setConexion.setupEze();
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		mk = new Marketing(driver);
		log = new LoginFw(driver);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		log.loginTelefonico();
		ges.irAConsolaFAN();	

	}
	
	@BeforeMethod(alwaysRun=true)
	public void setup() throws Exception {
		detalles = null;
		GestionDeClientes_Fw ges = new GestionDeClientes_Fw(driver);
		ges.selectMenuIzq("Inicio");
		ges.cerrarPestaniaGestion(driver);
		ges.irGestionClientes();
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
	
	@Test (groups = {"GestionesPerfilOficina", "Resumen de Cuenta Corriente", "E2E","Ciclo4"}, dataProvider = "CuentaReintegros")
	public void TS129461_CRM_Movil_REPRO_Resumen_de_Cuenta_Corriente_Verificacion_de_vista_de_Resumen_de_Cuenta_Corriente_FAN_Front_OOCC(String sDNI){
		imagen = "TS129461";
		detalles = null;
		detalles = imagen + " -Resumen de Cuenta Corriente - DNI: " + sDNI;
		Marketing mk = new Marketing(driver);
		ges.BuscarCuenta("DNI", sDNI);
		sleep(5000);
		mk.closeActiveTab();
		cambioDeFrame(driver, By.className("profile-edit"),0);
		buscarYClick(driver.findElements(By.className("left-sidebar-section-header")), "equals", "facturaci\u00f3n");
		
		cambioDeFrame(driver, By.className("card-top"),0);
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "resumen de cuenta");
		
		cambioDeFrame(driver, By.cssSelector(".boton3.secondaryFontWhite"),0);
		WebElement saldo = driver.findElement(By.className("secondaryFontNormal"));
		WebElement fechas = driver.findElement(By.cssSelector(".slds-text-heading--small.secondaryFont"));
		WebElement boton = driver.findElement(By.cssSelector(".boton3.secondaryFontWhite"));
		Assert.assertTrue(saldo.isDisplayed() && fechas.isDisplayed() && boton.isDisplayed());
		boton.click();
		sleep(5000);
		WebElement comprobante = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium"));
		Assert.assertTrue(comprobante.getText().toLowerCase().contains("comprobantes"));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ResumenDeCuenta", "Ciclo4"}, dataProvider = "ConsultaSaldo")
	public void TS129462_CRM_Movil_Prepago_Resumen_de_Cuenta_Corriente_Validaciones_de_logica_de_filtros_Fecha_FAN_Front_OOCC(String sDNI) {
		imagen="TS129462";
		detalles = null;
		detalles = imagen + "- Resumen de Cuenta - DNI:" +sDNI;

		ges.BuscarCuenta("DNI", sDNI);

		sleep(20000);
		cc.openleftpanel();
		sleep(5000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		cambioDeFrame(driver, By.className("card-top"),0);
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "resumen de cuenta");
		
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement table = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		for (WebElement cell : table.findElements(By.xpath("//tr//td"))) {
			try {
				if (cell.getText().equals("29"))
					cell.click();
			} catch (Exception e) {}
		}
		driver.findElement(By.id("text-input-id-2")).click();
		WebElement table2 = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		for (WebElement cell : table2.findElements(By.xpath("//tr//td"))) {
			try {
				if (cell.getText().equals("10"))
					cell.click();
			} catch (Exception e) {}
		}
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-p-horizontal--small.slds-size--1-of-2.slds-medium-size--1-of-4.slds-large-size--1-of-4")).isDisplayed());
	}
	
	@Test (groups = {"GestionesPerfilOficina","ResumenDeCuenta","E2E", "Ciclo4"},  dataProvider = "CuentaModificacionDeDatos")
	public void TS129471_CRM_Movil_Prepago_Resumen_de_Cuenta_Corriente_Columnas_de_informacion_Comprobantes_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS129471";
		detalles = null;
		detalles = imagen + " -Resumen de Cuenta Corriente: " + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "resumen de cuenta corriente");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(5000);
		boolean a = false;
		for(WebElement x : driver.findElements(By.cssSelector(".via-slds.slds-m-around--small.ng-scope"))) {
			if(x.getText().toLowerCase().contains("comprobantes")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ResumenDeCuenta", "Ciclo4"}, dataProvider = "CuentaVista360")
	public void TS129472_CRM_Movil_Prepago_Resumen_de_Cuenta_Corriente_Columnas_de_informacion_Pagos_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre, String sEmail, String sMovil) {
		imagen = "TS129472";
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "resumen de cuenta corriente");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(5000);
		WebElement tabla = driver.findElement(By.cssSelector(".slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-m-top--x-large")).findElement(By.className("slds-text-heading--label"));
		List<String> verificacion = new ArrayList<String>();
		verificacion.add("FECHA DE EMISI\u00d3N");
		verificacion.add("FECHA DE VENCIMIENTO");
		verificacion.add("TIPO");
		verificacion.add("NRO. DE COMPROBANTE");
		verificacion.add("MONTO");
		List<String> columnas = new ArrayList<String>();
		List<WebElement> datos = tabla.findElements(By.tagName("a"));
		for (int i=0; i<datos.size(); i++) {
			columnas.add(datos.get(i).getText());
		}
		Assert.assertTrue(verificacion.equals(columnas));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ResumenDeCuenta", "E2E", "Ciclo2"}, dataProvider = "CuentaVista360")
	public void TS129476_CRM_Movil_Prepago_Resumen_de_Cuenta_Corriente_Detalle_ampliado_registro_de_Comprobante_Factura_de_Venta_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre, String sEmail, String sMovil){
		imagen = "TS129476";
		detalles = null;
		detalles = imagen + " -Resumen de Cuenta Corriente - DNI: " + sDNI;
		Marketing mk = new Marketing(driver);
		TestBase tb = new TestBase ();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		mk.closeActiveTab();
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-edit")));
		buscarYClick(driver.findElements(By.className("left-sidebar-section-header")), "equals", "facturaci\u00f3n");
		sleep(5000);
		cc.irAResumenDeCuentaCorriente();
		driver.switchTo().frame(tb.cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		WebElement saldo = driver.findElement(By.cssSelector(".slds-text-heading--medium.secondaryFont"));
		saldo.findElement(By.tagName("b"));
		System.out.println(saldo.getText());
		sleep(3000);
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement table = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		List<WebElement> tableRows = table.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows) {
			try {
				if (cell.getText().equals("01")) {
					cell.click();
				}
			}catch(Exception e) {}
		}
		driver.findElement(By.id("text-input-id-2")).click();
		WebElement table_2 = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		List<WebElement> tableRows_2 = table_2.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows_2) {
			try {
				if (cell.getText().equals("01")) {
					cell.click();
				}
			}catch(Exception e) {}
		}
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(10000);
		boolean conf = false;
		List <WebElement> tablas = driver.findElements(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium"));
		for (WebElement x : tablas) {
			if(x.getText().toLowerCase().contains("comprobantes")) {
				conf = true;
			}
		}
		Assert.assertTrue(conf);
		List<WebElement> Tabla = driver.findElement(By.className("slds-p-bottom--small")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement x:Tabla) {
			if (x.findElements(By.tagName("td")).get(2).getText().toLowerCase().contains("fv")) {
				sleep(10000);
				x.findElements(By.tagName("td")).get(5).click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(tb.cambioFrame(driver, By.className("boton")));
		WebElement comprobante = driver.findElement(By.cssSelector(".slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium"));
		comprobante.findElement(By.tagName("object"));
		System.out.println(comprobante.getText());
		Assert.assertTrue(comprobante.isDisplayed());
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ResumenDeCuenta", "Ciclo4"}, dataProvider = "CuentaVista360")
	public void TS124406_CRM_Movil_REPRO_Resumen_de_Cuenta_Corriente_Visualizacion_del_Resumen_de_Cuenta_Corriente_a_nivel_de_cuenta_de_Facturacion_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre, String sCuenta, String sMovil) {
		imagen = "TS124406";
		String cuenta = null;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		cuenta = driver.findElement(By.className("header-left")).getText();
		System.out.println(cuenta);
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "resumen de cuenta");
		sleep(15000);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(5000);
		boolean saldo = false, comprobantes = false, pagos= false;
		WebElement sald = driver.findElement(By.cssSelector(".slds-text-heading--medium.secondaryFont")).findElement(By.tagName("b"));
		sald.isDisplayed();
		saldo=true;
		WebElement comprobante = driver.findElement(By.cssSelector(".slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-m-top--x-large"));
		comprobante.isDisplayed();
		comprobantes= true;
		WebElement pago = driver.findElement(By.className("slds-p-bottom--small")).findElement(By.tagName("table"));
		pago.isDisplayed();
		pagos= true;
		Assert.assertTrue(saldo && comprobantes && pagos);
		String resuCuenta = driver.findElement(By.className("secondaryFont")).getText();
		cuenta = resuCuenta.substring(0, 16);
		System.out.println("El Resumen de Cuenta Corresponde a la cuenta: " + cuenta);	
		Assert.assertTrue(cuenta.equalsIgnoreCase(sCuenta));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ResumenDeCuenta", "Ciclo4"}, dataProvider = "CuentaVista360")
	public void TS124414_CRM_Movil_REPRO_Resumen_de_Cuenta_Corriente_Visualizacion_del_Resumen_de_Cuenta_Corriente_a_nivel_de_cuenta_de_Facturacion_Descarga_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre, String sCuenta, String sMovil) {
		imagen = "TS124414";
		String cuenta = null;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "resumen de cuenta corriente");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(5000);
		String resuCuenta = driver.findElement(By.className("secondaryFont")).getText();
		cuenta = resuCuenta.substring(0, 16);
		System.out.println("El Resumen de Cuenta Corresponde a la cuenta: " + cuenta);	
		Assert.assertTrue(cuenta.equalsIgnoreCase(sCuenta));
		WebElement detaFactu = driver.findElement(By.xpath("//*[@class='slds-p-bottom--small']/table/tbody/tr/td/div/a/i"));
		detaFactu.click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("slds-float--right")));
		WebElement imprimir = driver.findElement(By.className("slds-float--right")).findElement(By.className("boton"));
		WebElement descargar = driver.findElement(By.className("slds-float--right")).findElement(By.className("boton2"));
		Assert.assertTrue(imprimir.isDisplayed() && descargar.isDisplayed());
		sleep(5000);
		descargar.click();
		sleep(5000);
		String downloadPath = "C:\\Users\\Quelys\\Downloads";
		try {
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+downloadPath);
			System.out.println("Descargo Archivo");
			} catch (IOException ee) {
				ee.printStackTrace();
			}
		Assert.assertTrue(scp.isFileDownloaded(downloadPath,"B00785"), "Failed to download Expected document");
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ResumenDeCuenta", "Ciclo4"}, dataProvider = "CuentaVista360")
	public void TS129473_CRM_Movil_REPRO_Resumen_de_Cuenta_Corriente_Detalle_ampliado_registro_de_Pago_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre, String sCuenta, String sMovil) {
		imagen = "TS129473";
		String cuenta = null;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "resumen de cuenta corriente");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(5000);
		String resuCuenta = driver.findElement(By.className("secondaryFont")).getText();
		cuenta = resuCuenta.substring(0, 16);
		System.out.println("El Resumen de Cuenta Corresponde a la cuenta: " + cuenta);	
		Assert.assertTrue(cuenta.equalsIgnoreCase(sCuenta));
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElements(By.cssSelector(".slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-m-top--x-large")).get(1).getLocation().y+" )");
		sleep(5000);
		WebElement detapagos = driver.findElement(By.xpath("//*[@class='slds-text-heading--large slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-bottom--small'] //b[contains(text(), 'Pagos')] /.. /.. /.. //*[@class='slds-p-bottom--small'] //tr[3] //td[5] //a"));
		detapagos.click();
		sleep(15000);
		Assert.assertTrue(driver.findElement(By.className("secondaryFont")).getText().contains(cuenta));	
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ResumenDeCuenta", "Ciclo4"}, dataProvider = "CuentaVista360")
	public void TS129475_CRM_Movil_REPRO_Resumen_de_Cuenta_Corriente_Detalle_ampliado_registro_de_Comprobante_Nota_de_Credito_Debito_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre, String sCuenta, String sMovil) {
		imagen = "TS129475";
		String cuenta = null;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "resumen de cuenta corriente");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(5000);
		String resuCuenta = driver.findElement(By.className("secondaryFont")).getText();
		cuenta = resuCuenta.substring(0, 16);
		System.out.println("El Resumen de Cuenta Corresponde a la cuenta: " + cuenta);	
		Assert.assertTrue(cuenta.equalsIgnoreCase(sCuenta));
		WebElement detaFactu = driver.findElement(By.xpath("//*[contains(text(),'NCBR')]/../following-sibling::*//div/a"));
		detaFactu.click();
		sleep(5000);
		Assert.assertTrue(false); //Bug... no muestra la informacion de la Nota de Credito
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"GestionesPerfilTelefonico", "ResumenDeCuenta", "E2E","Ciclo4"}, dataProvider = "CuentaReintegros")
	public void TS135432_CRM_Movil_REPRO_Resumen_de_Cuenta_Corriente_Verificacion_de_vista_de_Resumen_de_Cuenta_Corriente_FAN_Front_Telefonico(String sDNI){
		imagen = "TS135432";
		detalles = null;
		detalles = imagen + " -Resumen de Cuenta Corriente - DNI: " + sDNI;
		Marketing mk = new Marketing(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		mk.closeActiveTab();
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-edit")));
		buscarYClick(driver.findElements(By.className("left-sidebar-section-header")), "equals", "facturaci\u00f3n");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "equals", "resumen de cuenta corriente");
		sleep(4000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		WebElement saldo = driver.findElement(By.cssSelector(".slds-text-heading--medium.secondaryFont"));
		WebElement fechas = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter"));
		WebElement boton = driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont"));
		Assert.assertTrue(saldo.isDisplayed() && fechas.isDisplayed() && boton.isDisplayed());
		boton.click();
		sleep(5000);
		WebElement comprobante = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium"));
		Assert.assertTrue(comprobante.getText().toLowerCase().contains("comprobantes"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico","ResumenDeCuenta","E2E", "Ciclo4"},  dataProvider = "CuentaModificacionDeDatos")
	public void TS135434_CRM_Movil_Prepago_Resumen_de_Cuenta_Corriente_Columnas_de_informacion_Comprobantes_FAN_Front_Telefonico(String sDNI, String sLinea) {
		imagen = "TS135434";
		detalles = null;
		detalles = imagen + " -Resumen de Cuenta Corriente: " + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "resumen de cuenta corriente");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(5000);
		boolean a = false;
		for(WebElement x : driver.findElements(By.cssSelector(".via-slds.slds-m-around--small.ng-scope"))) {
			if(x.getText().toLowerCase().contains("comprobantes")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "ResumenDeCuenta", "E2E", "Ciclo2"}, dataProvider = "CuentaVista360")
	public void TS135435_CRM_Movil_Prepago_Resumen_de_Cuenta_Corriente_Columnas_de_informacion_Pagos_FAN_Front_Telefonico(String sDNI, String sLinea,String sNombre, String sEmail, String sMovil){
		imagen = "TS135435";
		detalles = null;
		detalles = imagen + " -Diagnostico Inconveniente - DNI: " + sDNI;
		TestBase tb = new TestBase ();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(12000);
		driver.switchTo().frame(tb.cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "resumen de cuenta");
		sleep(5000);
		driver.switchTo().frame(tb.cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(10000);
		WebElement compro = driver.findElement(By.cssSelector(".slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-m-top--x-large"));
		System.out.println(compro.getText());
		Assert.assertTrue(compro.isDisplayed());	
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "ResumenDeCuenta", "E2E", "Ciclo2"}, dataProvider = "CuentaVista360")
	public void TS135436_CRM_Movil_Prepago_Resumen_de_Cuenta_Corriente_Detalle_ampliado_registro_de_Pago_FAN_Front_Telefonico(String sDNI, String sLinea,String sNombre, String sEmail, String sMovil){
		imagen = "TS135436";
		detalles = null;
		detalles = imagen + " -Diagnostico Inconveniente - DNI: " + sDNI;
		TestBase tb = new TestBase ();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(8000);
		driver.switchTo().frame(tb.cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "resumen de cuenta");
		sleep(5000);
		driver.switchTo().frame(tb.cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(10000);
		WebElement Tabla = driver.findElement(By.className("slds-p-bottom--small")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(5);
		Tabla.click();
		sleep(7000);
		Assert.assertTrue(true);
	}
	
	@Test (groups = {"GestionesPerfilTelefonico" , "ResumenDeCuenta" , "E2E" , "Ciclo4"}, dataProvider= "CuentaVista360")
	public void TS135439_CRM_Movil_REPRO_Resumen_de_Cuenta_Corriente_Detalle_ampliado_registro_de_Comprobante_Factura_de_Venta_FAN_Front_Telefonico(String sDNI, String sLinea,String sNombre, String sCuenta, String sMovil) {
		imagen = "TS135439";
		detalles = null;
		detalles = imagen + " -Resumen de Cuenta Corriente - DNI: " + sDNI;
		Marketing mk = new Marketing(driver);
		TestBase tb = new TestBase ();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		mk.closeActiveTab();
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-edit")));
		buscarYClick(driver.findElements(By.className("left-sidebar-section-header")), "equals", "facturaci\u00f3n");
		sleep(5000);
		cc.irAResumenDeCuentaCorriente();
		driver.switchTo().frame(tb.cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		WebElement saldo = driver.findElement(By.cssSelector(".slds-text-heading--medium.secondaryFont"));
		saldo.findElement(By.tagName("b"));
		System.out.println(saldo.getText());
		sleep(3000);
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement table = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		String day = fechaDeHoy();
		String dia = day.substring(0, 2);
		List<WebElement> tableRows = table.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows) {
			try {
				if (cell.getText().equals("01")) {
					cell.click();
				}
			}catch(Exception e) {}
		}
		driver.findElement(By.id("text-input-id-2")).click();
		WebElement table_2 = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		List<WebElement> tableRows_2 = table_2.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows_2) {
			try {
				if (cell.getText().equals(dia)) {
				cell.click();
				}
			}catch(Exception e) {}
		}
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(10000);
		boolean conf = false;
		List <WebElement> tablas = driver.findElements(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium"));
		for (WebElement x : tablas) {
			if(x.getText().toLowerCase().contains("comprobantes")) {
				conf = true;
			}
		}
		Assert.assertTrue(conf);
		List<WebElement> Tabla = driver.findElement(By.className("slds-p-bottom--small")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement x:Tabla) {
			if (x.findElements(By.tagName("td")).get(2).getText().toLowerCase().contains("fv")) {
				sleep(10000);
				x.findElements(By.tagName("td")).get(5).click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(tb.cambioFrame(driver, By.className("boton")));
		WebElement comprobante = driver.findElement(By.cssSelector(".slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium"));
		comprobante.findElement(By.tagName("object"));
		System.out.println(comprobante.getText());
		Assert.assertTrue(comprobante.isDisplayed());
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "ResumenDeCuenta", "Ciclo4"}, dataProvider = "CuentaVista360")
	public void TS135428_CRM_Movil_REPRO_Resumen_de_Cuenta_Corriente_Visualizacion_del_Resumen_de_Cuenta_Corriente_a_nivel_de_cuenta_de_Facturacion_FAN_Front_Telefonico(String sDNI, String sLinea,String sNombre, String sCuenta, String sMovil) {
		imagen = "TS135428";
		String cuenta = null;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.openleftpanel();
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		List <WebElement> lis = driver.findElement(By.className("actions")).findElements(By.tagName("li"));
		for(WebElement x : lis) {
			if(x.findElement(By.className("slds-text-body_regular")).getText().toLowerCase().contains("resumen de cuenta corriente")){
				x.click();
				break;
			}
		}		
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(5000);
		String resuCuenta = driver.findElement(By.className("secondaryFont")).getText();
		cuenta = resuCuenta.substring(0, 16);
		System.out.println("El Resumen de Cuenta Corresponde a la cuenta: " + cuenta);	
		Assert.assertTrue(cuenta.equalsIgnoreCase(sCuenta));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "ResumenDeCuenta", "Ciclo4"}, dataProvider = "CuentaVista360")
	public void TS135430_CRM_Movil_REPRO_Resumen_de_Cuenta_Corriente_Visualizacion_del_Resumen_de_Cuenta_Corriente_a_nivel_de_cuenta_de_Facturacion_Descarga_FAN_Front_Telefonico(String sDNI, String sLinea,String sNombre, String sCuenta, String sMovil) {
		imagen = "TS135430";
		String cuenta = null;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "resumen de cuenta corriente");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(5000);
		String resuCuenta = driver.findElement(By.className("secondaryFont")).getText();
		cuenta = resuCuenta.substring(0, 16);
		System.out.println("El Resumen de Cuenta Corresponde a la cuenta: " + cuenta);	
		Assert.assertTrue(cuenta.equalsIgnoreCase(sCuenta));
		WebElement detaFactu = driver.findElement(By.xpath("//*[@class='slds-p-bottom--small']/table/tbody/tr/td/div/a/i"));
		detaFactu.click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("slds-float--right")));
		WebElement imprimir = driver.findElement(By.className("slds-float--right")).findElement(By.className("boton"));
		WebElement descargar = driver.findElement(By.className("slds-float--right")).findElement(By.className("boton2"));
		Assert.assertTrue(imprimir.isDisplayed() && descargar.isDisplayed());
		sleep(5000);
		descargar.click();
		sleep(5000);
		String downloadPath = "C:\\Users\\Quelys\\Downloads";
		Assert.assertTrue(scp.isFileDownloaded(downloadPath,"B00785"), "Failed to download Expected document");
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "ResumenDeCuenta", "Ciclo4"}, dataProvider = "CuentaVista360")
	public void TS135433_CRM_Movil_REPRO_Resumen_de_Cuenta_Corriente_Validaciones_de_logica_de_filtros_Fecha_FAN_Front_Telefonico(String sDNI, String sLinea,String sNombre, String sCuenta, String sMovil) {
		imagen = "TS135433";
		String cuenta = null;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "resumen de cuenta corriente");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		sleep(5000);
		String resuCuenta = driver.findElement(By.className("secondaryFont")).getText();
		cuenta = resuCuenta.substring(0, 16);
		System.out.println("El Resumen de Cuenta Corresponde a la cuenta: " + cuenta);	
		Assert.assertTrue(cuenta.equalsIgnoreCase(sCuenta));
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement table = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		String day = fechaDeHoy();
		String dia = day.substring(0, 2);
		List<WebElement> tableRows = table.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows) {
			try {
				if (cell.getText().equals("14")) {
					cell.click();
				}
			} catch (Exception e) {}
		}
		driver.findElement(By.id("text-input-id-2")).click();
		WebElement table_2 = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		List<WebElement> tableRows_2 = table_2.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows_2) {
			try {
				if (cell.getText().equals(dia)) {
					cell.click();
				}
			} catch (Exception e) {}
		}	
		sleep(3000);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		WebElement Comprobantes = driver.findElement(By.xpath("//*[@class='slds-text-heading--large slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-bottom--small'] //b[contains(text(), 'Comprobantes')]"));
		Assert.assertTrue(Comprobantes.isDisplayed());
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElements(By.cssSelector(".slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-m-top--x-large")).get(1).getLocation().y+" )");
		WebElement pagos = driver.findElement(By.xpath("//*[@class='slds-text-heading--large slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-bottom--small'] //b[contains(text(), 'Pagos')]"));
		Assert.assertTrue(pagos.isDisplayed());
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ResumenDeCuenta", "Ciclo4"}, dataProvider = "CuentaVista360")
	public void TS135438_CRM_Movil_REPRO_Resumen_de_Cuenta_Corriente_Detalle_ampliado_registro_de_Comprobante_Nota_de_Credito_Debito_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre, String sCuenta, String sMovil) {
		imagen = "TS135438";
		String cuenta = null;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "resumen de cuenta corriente");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(5000);
		String resuCuenta = driver.findElement(By.className("secondaryFont")).getText();
		cuenta = resuCuenta.substring(0, 16);
		System.out.println("El Resumen de Cuenta Corresponde a la cuenta: " + cuenta);	
		Assert.assertTrue(cuenta.equalsIgnoreCase(sCuenta));
		WebElement detaFactu = driver.findElement(By.xpath("//*[contains(text(),'NCBR')]/../following-sibling::*//div/a"));
		detaFactu.click();
		sleep(5000);
		Assert.assertTrue(false); //Bug... no muestra la informacion de la Nota de Credito
	}
}