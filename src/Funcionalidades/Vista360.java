package Funcionalidades;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import Pages.PagePerfilTelefonico;
import Pages.SalesBase;
import Pages.setConexion;
import Tests.TestBase;

public class Vista360 extends TestBase {

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	private PagePerfilTelefonico ppt;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	@BeforeClass (alwaysRun = true)
	public void init() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		ppt = new PagePerfilTelefonico(driver);
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
	
	@Test (groups= {"GestionPerfilOficina", "Ciclo2", "Vista360"}, dataProvider = "documentacionVista360")
	public void TS134379_CRM_Movil_Prepago_Vista_360_Mis_Servicios_Visualizacion_del_estado_de_los_servicios_activos_FAN_Front_OOCC(String sDNI) {
		imagen = "TS134379";
		detalles = null;
		detalles = imagen + "-Vista 360 - DNI: "+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(3000);
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "equals", "mis servicios");
		sleep(10000);
		boolean a = false;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium")));
		List <WebElement> servicios = driver.findElement(By.className("slds-p-bottom--small")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement s : servicios) {
			if (s.getText().toLowerCase().contains("activo"))
				System.out.println(s.getText());
				a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Vista360", "Ciclo2"}, dataProvider = "CuentaVista360")
	public void TS134349_CRM_Movil_Prepago_Vista_360_Consulta_por_gestiones_Gestiones_abiertas_Plazo_vencido_Asistencia_registrada_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre) {
		imagen = "TS134349";
		detalles = null;
		detalles = imagen + " - Vista 360 - DNI: "+sDNI+" - Nombre: "+sNombre;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		cc.irAGestiones();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.id("text-input-03")).click();
		driver.findElement(By.xpath("//*[text() = 'Casos']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(10000);
		WebElement nroCaso = driver.findElement(By.cssSelector(".slds-p-bottom--small")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(2).findElement(By.tagName("div")).findElement(By.tagName("a"));
		nroCaso.click();
		sleep(8000);
		WebElement fechaYHora = null;
		driver.switchTo().frame(cambioFrame(driver, By.name("close")));
		for (WebElement x : driver.findElements(By.className("pbSubsection"))) {
			if (x.getText().toLowerCase().contains("owned by me"))
				fechaYHora = x;
		}
		fechaYHora = fechaYHora.findElement(By.tagName("tbody"));
		for (WebElement x : fechaYHora.findElements(By.tagName("tr"))) {
			if (x.getText().toLowerCase().contains("fecha/hora de cierre"))
				fechaYHora = x;
		}
		Assert.assertTrue(fechaYHora.getText().contains("Fecha/Hora de cierre"));
		Assert.assertTrue(fechaYHora.findElements(By.tagName("td")).get(3).getText().matches("^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}$"));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Vista360", "Ciclo2"}, dataProvider = "CuentaVista360") 
	public void TS134368_OFCOM_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_visualizacion_y_busqueda_de_los_distintos_consumos_realizados_por_el_cliente_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre) {
		imagen = "TS134368";
		detalles = null;
		detalles = imagen + "-Vista 360 - DNI: "+sDNI+ " - Nombre: "+sNombre;
		detalles = null;
		detalles = imagen + "- Detalle de Consumo - DNI: "+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(3000);
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		CustomerCare cCC = new CustomerCare(driver);
		cCC.irADetalleDeConsumos();
		sleep(9500);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-02")));
		System.out.println(driver.findElement(By.id("text-input-02")).getAttribute("value"));
		driver.findElement(By.id("text-input-02")).click();
		List<WebElement> pres = driver.findElement(By.id("option-list-01")).findElements(By.tagName("li"));
		for(WebElement p : pres){
			if(p.getText().toLowerCase().equals("los \u00faltimos 15 d\u00edas"))
			cc.obligarclick(p);
		}
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(7000);
		boolean a = false;
		List<WebElement> filtro = driver.findElements(By.cssSelector(".slds-text-heading--small"));
		for(WebElement f : filtro){
			if(f.getText().toLowerCase().equals("filtros avanzados"))
				a = true;
		}	
		Assert.assertTrue(a);
		Select pagina = new Select (driver.findElement(By.cssSelector(".slds-select.ng-pristine.ng-untouched.ng-valid.ng-not-empty")));
		pagina.selectByVisibleText("30");
		sleep(7500);
		boolean b = false;
		WebElement lista = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
		List <WebElement> consumos = lista.findElements(By.tagName("tr"));
		for(WebElement x : consumos) {
			System.out.println(x.getText().replaceAll("\\s", " "));
			b = true;
		}
		Assert.assertTrue(b);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Vista360", "E2E","ConsultaPorGestion", "Ciclo2"}, dataProvider = "CuentaModificacionDeDatos")
	public void TS134370_CRM_Movil_Prepago_Vista_360_Consulta_por_gestiones_Gestiones_no_registradas_FAN_Front_OOCC(String sDNI , String sLinea) {
		imagen = "TS134370";
		detalles = null;
		detalles = imagen+"- Vista 360 - DNI: "+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "equals", "gestiones");
		sleep(2000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement table = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		List<WebElement> tableRows = table.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows) {
			try {
				if (cell.getText().equals("06")) {
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
				if (cell.getText().equals("31")) {
					cell.click();
				}
			} catch (Exception e) {}
		}
		sleep(3000);
		driver.findElement(By.id("text-input-id-2")).click();
		WebElement table_3 = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		List<WebElement> tableRows_3 = table_3.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows_3) {
			try {
				if (cell.getText().equals("06")) {
					cell.click();
				}
			} catch (Exception e) {}
		}
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")));
		sleep(5000);
		boolean a = false;
		for(WebElement x : driver.findElements(By.cssSelector(".ng-pristine.ng-untouched.ng-valid.ng-empty"))) {
			if(x.getText().toLowerCase().equals("order")) {
				a = true;
			}
		}
		Assert.assertFalse(a);
	}
	
	@Test (groups = {"GestionesPerfilOficina","Vista360","E2E", "Ciclo 1"}, dataProvider="CuentaVista360")
	public void TS134371_CRM_Movil_Prepago_Vista_360_Consulta_por_gestiones_Gestiones_abiertas_Plazo_No_vencido_Consulta_registrada_FAN_Front_OOCC(String sDNI, String sNombre, String sLinea){
		imagen = "TS134371";
		detalles = null;
		detalles = imagen+"- Vista 360 - DNI: "+sDNI;
		String day = fechaDeHoy();
		String dia = day.substring(0, 2);
		boolean gestion = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(3000);
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "equals", "gestiones");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement table = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		List<WebElement> tableRows = table.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows) {
			try {
				if (cell.getText().equals("17")) {
					cell.click();
				}
			} catch (Exception e) {}
		}
		sleep(3000);
		driver.switchTo().defaultContent();
		WebElement fecha = driver.findElement(By.id("text-input-id-2"));
		List<WebElement> tableRows2 = fecha.findElements(By.xpath("//tr//td"));
		for (WebElement x: tableRows2) {
			try {
				if (x.getText().equals(dia)) {
					x.click();
				}
			} catch (Exception e) {}
		}
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-03")));
		driver.findElement(By.id("text-input-03")).click();
		driver.findElement(By.xpath("//*[text() = 'Casos']")).click();
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(12000);
		WebElement nroCaso = driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
		nroCaso.findElements(By.tagName("td")).get(2).click();
		System.out.println(nroCaso);
		sleep(15000);
		WebElement estado = null;
		driver.switchTo().frame(cambioFrame(driver, By.name("ta_clone")));
		for (WebElement x : driver.findElements(By.className("detailList"))) {
			if (x.getText().toLowerCase().contains("n\u00famero de pedido"))
				estado = x;
		}
		for (WebElement x : estado.findElements(By.tagName("tr"))) {
			if (x.getText().toLowerCase().contains("estado"))
				estado = x;
		}
		if (estado.getText().toLowerCase().contains("activada") || (estado.getText().toLowerCase().contains("iniciada")))
			gestion = true;
		Assert.assertTrue(gestion);
	}
	
	@Test (groups = {"GestionesPerfilOficina","Vista360","E2E", "Ciclo1"}, dataProvider="CuentaVista360")
	public void TS134380_CRM_Movil_Prepago_Vista_360_Mis_Servicios_Visualizacion_del_estado_de_los_Productos_activos_FAN_Front_OOCC(String sDNI,String sNombre, String sLinea){
		imagen = "TS134380";
		detalles = null;
		detalles = imagen + " - Vista 360 - DNI: "+sDNI;
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "equals", "mis servicios");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium")));
		WebElement wTabla = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
		List <WebElement> lTabla = wTabla.findElements(By.tagName("tr"));
 		for(WebElement x : lTabla) {
 			System.out.println(x.getText());
 		}
		Assert.assertTrue(wTabla.isDisplayed());
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Vista360", "Ciclo2"}, dataProvider = "CuentaVista360Version2")
	public void TS134495_CRM_Movil_Prepago_Vista_360_Distribucion_de_paneles_Informacion_del_cliente_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre, String sEmail, String sMovil, String sNps, String sTelAlternativo, String sPuntosClub, String sCategoria) {
		imagen = "TS134495";
		detalles = null;
		detalles = imagen + "-Vista 360 - DNI: "+sDNI+ " - Nombre: "+sNombre;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		WebElement nombre = driver.findElement(By.cssSelector(".slds-text-heading_large.title-card"));
		WebElement nps = driver.findElement(By.className("detail-card")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(2);
		WebElement acceso = driver.findElement(By.className("profile-links-wrapper"));
		WebElement dni = null;
		for (WebElement x : driver.findElements(By.cssSelector(".slds-text-body_regular.account-detail-content"))) {
			if (x.getText().toLowerCase().contains(sDNI))
				dni = x;
		}
		Assert.assertTrue(nombre.getText().contains(sNombre) && dni.getText().contains(sDNI) && acceso.isDisplayed() && (nps.getText().contains(sNps) || nps.isDisplayed()));
		WebElement mail = driver.findElements(By.className("left-sidebar-section-container")).get(0).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(0);
		WebElement movil = driver.findElements(By.className("left-sidebar-section-container")).get(0).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(1);
		WebElement telefonoAlternativo = driver.findElements(By.className("left-sidebar-section-container")).get(0).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(2);
		WebElement puntosClubPersonal = driver.findElements(By.className("left-sidebar-section-container")).get(0).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(3);
		WebElement categoria = driver.findElements(By.className("left-sidebar-section-container")).get(0).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(4);
		Assert.assertTrue(mail.getText().contains(sEmail) && (movil.getText().contains(sMovil) || movil.isDisplayed()) && (telefonoAlternativo.getText().contains(sTelAlternativo) || telefonoAlternativo.isDisplayed()));
		Assert.assertTrue(puntosClubPersonal.getText().contains(sPuntosClub) || puntosClubPersonal.isDisplayed());
		Assert.assertTrue(categoria.getText().contains(sCategoria) || categoria.isDisplayed());
		WebElement segmentoYAtriburo = driver.findElements(By.className("left-sidebar-section-container")).get(1).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElement(By.tagName("td"));
		Assert.assertTrue(segmentoYAtriburo.getText().contains("Alta reciente - Masivo"));	
	}
	
	@Test (groups = {"GestionesPerfilOficina","Vista360","E2E", "Ciclo2"}, dataProvider = "CuentaVista360")
	public void TS134496_CRM_Movil_Prepago_Vista_360_Distribucion_de_paneles_Perfil_FAN_Front_OOCC(String sDNI,String sLinea, String sNombre) {
		imagen = "TS134496";
		detalles = null;
		detalles = imagen + " - Vista 360 - DNI: "+sDNI+" - Nombre: "+sNombre;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		WebElement linea = driver.findElement(By.className("card-top")).findElements(By.className("slds-text-heading_medium")).get(2);
		Assert.assertTrue(linea.getText().contains(sLinea));		
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Vista360", "Ciclo2"}, dataProvider = "CuentaVista360")
	public void TS134745_CRM_Movil_Prepago_Vista_360_Producto_Activo_del_cliente_Datos_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre) {
		imagen = "TS134745";
		detalles = null;
		detalles = imagen + " - Vista 360 - DNI: "+sDNI+" - Nombre: "+sNombre;
		boolean creditoRecarga = false, creditoPromocional = false, estado = false, internetDisponible = false;
		boolean detalles = false, historiales = false, misServicios = false, gestiones = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		WebElement plan = driver.findElement(By.className("card-top")).findElements(By.className("slds-text-heading_medium")).get(0);
		WebElement FechaActivacion = driver.findElement(By.cssSelector(".slds-text-body_regular.expired-title"));
		WebElement linea = driver.findElement(By.className("card-top")).findElements(By.className("slds-text-heading_medium")).get(2);
		System.out.println(plan.getText()); System.out.println(FechaActivacion.getText()); System.out.println(linea.getText());
		assertTrue(plan.isDisplayed() && FechaActivacion.isDisplayed() && linea.getText().contains(sLinea));
		for (WebElement x : driver.findElements(By.cssSelector(".slds-text-body_regular.detail-label"))) {
			if (x.getText().toLowerCase().contains("cr\u00e9dito recarga"))
				creditoRecarga = true;
			if (x.getText().toLowerCase().contains("cr\u00e9dito promocional"))
				creditoPromocional = true;
			if (x.getText().toLowerCase().contains("estado"))
				estado = true;
			if (x.getText().toLowerCase().contains("internet disponible"))
				internetDisponible = true;
		}
		Assert.assertTrue(creditoRecarga && creditoPromocional && estado && internetDisponible);
		for(WebElement y : driver.findElements(By.className("slds-text-body_regular"))) {
			if(y.getText().toLowerCase().contains("detalles de consumo"))
				detalles = true;
			if(y.getText().toLowerCase().contains("historiales"))
				historiales = true;
			if(y.getText().toLowerCase().contains("mis servicios"))
				misServicios = true;
			if(y.getText().toLowerCase().contains("gestiones"))
				gestiones = true;
		}
		Assert.assertTrue(detalles && historiales && misServicios && gestiones);
	}
	
	@Test (groups = {"GestionesPerfilOficina","Vista360","E2E", ""}, dataProvider="CuentaVista360")
	public void TS134746_CRM_Movil_Prepago_Vista_360_Producto_Activo_del_cliente_Desplegable_FAN_Front_OOCC(String sDNI,String sNombre, String sLinea) {
		imagen = "TS134746";
		detalles = imagen+" - Vista360 - DNI: "+sDNI+" - Linea: "+sLinea;
		BasePage cambioFrameByID=new BasePage();
		sleep(30000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI",sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(18000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		WebElement wFlyoutCard = driver.findElement(By.name("Flyout Grid Actions"));
		List<WebElement> wWebList = wFlyoutCard.findElements(By.tagName("span"));
		List<String> sTextList = new ArrayList<String>();
		sTextList.add("Recarga de cr\u00e9dito");
		sTextList.add("Renovacion de Datos");
		sTextList.add("Alta/Baja de Servicios");
		sTextList.add("Suscripciones");
		sTextList.add("Problemas con Recargas");
		sTextList.add("Historial de Suspensiones");
		sTextList.add("Diagn\u00f3stico");
		sTextList.add("N\u00fameros Gratis");
		sTextList.add("Cambio SimCard");
		Assert.assertTrue(ppt.forEach(wWebList, sTextList));
		Assert.assertTrue(!wFlyoutCard.findElement(By.xpath("//div[@class='items-card ng-not-empty ng-valid'] //div[contains(text(),'Mensajes')] /following-sibling::label")).getText().equalsIgnoreCase("Informaciï¿½n no disponible"));
		Assert.assertTrue(!wFlyoutCard.findElement(By.xpath("//div[@class='items-card ng-not-empty ng-valid'] //div[contains(text(),'MB')] /following-sibling::label")).getText().equalsIgnoreCase("Informaciï¿½n no disponible"));
		Assert.assertTrue(!wFlyoutCard.findElement(By.xpath("//div[@class='items-card ng-not-empty ng-valid'] //div[contains(text(),'KB')] /following-sibling::label")).getText().equalsIgnoreCase("Informaciï¿½n no disponible"));
		Assert.assertTrue(!wFlyoutCard.findElement(By.xpath("//div[@class='items-card ng-not-empty ng-valid'] //div[contains(text(),'Minutos')] /following-sibling::label")).getText().equalsIgnoreCase("Informaciï¿½n no disponible"));
		Assert.assertTrue(!wFlyoutCard.findElement(By.xpath("//div[@class='items-card ng-not-empty ng-valid'] //div[contains(text(),'Segundos')] /following-sibling::label")).getText().equalsIgnoreCase("Informaciï¿½n no disponible"));
		WebElement wRightSideCard = driver.findElement(By.xpath("//*[@class='items-card ng-not-empty ng-valid']"));
		wWebList = wRightSideCard.findElements(By.xpath("//button[@class='slds-button slds-button--neutral slds-truncate']"));
		sTextList.clear();
		sTextList.add("Comprar SMS");
		sTextList.add("Comprar Internet");
		sTextList.add("Comprar Minutos");
		Assert.assertTrue(ppt.forEach(wWebList, sTextList));
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"GestionesPerfilTelefonico", "Vista360", "E2E", "Ciclo1"}, dataProvider = "CuentaVista360")
	public void TS134798_CRM_Movil_Prepago_Vista_360_Producto_Activo_del_cliente_Datos_FAN_Front_Telefonico(String sDNI, String sLinea, String sNombre){
		imagen = "TS134798";
		detalles = null;
		detalles = imagen + " -ServicioTecnico: " + sDNI;
		boolean creditoRecarga = false, creditoPromocional = false, estado = false, internetDisponible = false;
		boolean detalles = false, historiales = false, misServicios = false, gestiones = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		WebElement plan = driver.findElement(By.className("card-top")).findElements(By.className("slds-text-heading_medium")).get(0);
		WebElement FechaActivacion = driver.findElement(By.cssSelector(".slds-text-body_regular.expired-title"));
		WebElement linea = driver.findElement(By.className("card-top")).findElements(By.className("slds-text-heading_medium")).get(2);
		System.out.println(plan.getText()); System.out.println(FechaActivacion.getText()); System.out.println(linea.getText());
		Assert.assertTrue(plan.isDisplayed() && FechaActivacion.isDisplayed() && linea.getText().contains(sLinea));
		for (WebElement x : driver.findElements(By.cssSelector(".slds-text-body_regular.detail-label"))) {
			if (x.getText().toLowerCase().contains("cr\u00e9dito recarga"))
				creditoRecarga = true;
			if (x.getText().toLowerCase().contains("cr\u00e9dito promocional"))
				creditoPromocional = true;
			if (x.getText().toLowerCase().contains("estado"))
				estado = true;
			if (x.getText().toLowerCase().contains("internet disponible"))
				internetDisponible = true;
		}
		Assert.assertTrue(creditoRecarga && creditoPromocional && estado && internetDisponible);
		for(WebElement y : driver.findElements(By.className("slds-text-body_regular"))) {
			if(y.getText().toLowerCase().contains("detalles de consumo"))
				detalles = true;
			if(y.getText().toLowerCase().contains("historiales"))
				historiales = true;
			if(y.getText().toLowerCase().contains("mis servicios"))
				misServicios = true;
			if(y.getText().toLowerCase().contains("gestiones"))
				gestiones = true;
		}
		Assert.assertTrue(detalles && historiales && misServicios && gestiones);
	}
	
	@Test(groups = { "GestionPerfilTelefonico", "Ciclo2", "Vista360" }, dataProvider = "documentacionVista360")
	public void TS134800_CRM_Movil_Prepago_Vista_360_Mis_Servicios_Visualizacion_del_estado_de_los_servicios_activos_FAN_Front_Telefonico(String sDNI) {
		imagen = "TS134800";
		detalles = null;
		detalles = imagen + "-Vista 360-DNI:" + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(3000);
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "equals", "mis servicios");
		sleep(10000);
		boolean a = false;
		driver.switchTo().frame(cambioFrame(driver,By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium")));
		WebElement verif = driver.findElement(By.cssSelector(".via-slds.slds-m-around--small.ng-scope"));
		if (verif.getText().toLowerCase().contains("servicios incluidos"))
			a = true;
		Assert.assertTrue(a);
		WebElement tabla = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium"));
		List<WebElement> elementosDeLaTabla = tabla.findElement(By.cssSelector("[class='slds-grid slds-wrap slds-card slds-m-bottom--small slds-p-around--medium'] [class='slds-p-bottom--small'] ")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		System.out.println(elementosDeLaTabla.get(0).getText());
		ArrayList<String> tablaComparar = new ArrayList<String>(Arrays.asList("Contestador Personal", "DDI con Roaming Internacional", "Voz", "SMS Saliente", "SMS Entrante", "MMS", "Datos"));
		for (int i = 0; i < tablaComparar.size(); i++) {
			String nombre = elementosDeLaTabla.get(i).findElements(By.tagName("td")).get(0).getText();
			String nombreComparar = tablaComparar.get(i);
			String estado = elementosDeLaTabla.get(i).findElements(By.tagName("td")).get(2).getText();
			Assert.assertTrue(nombre.equals(nombreComparar));
			Assert.assertTrue(estado.equals("Activo"));
		}
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "Vista360", "E2E", "Ciclo1"}, dataProvider = "CuentaVista360")
	public void TS134801_CRM_Movil_Prepago_Vista_360_Mis_Servicios_Visualizacion_del_estado_de_los_Productos_activos_FAN_Front_Telefonico(String sDNI, String sLinea, String sNombre){
		imagen = "TS134801";
		detalles = null;
		detalles = imagen + " -ServicioTecnico: " + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(15000);
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "equals","mis servicios");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-card.slds-m-around--small.ta-fan-slds")));
		 List <WebElement> serv= driver.findElements(By.cssSelector(".slds-p-bottom--small"));
		 boolean a = false;
		 for(WebElement s : serv){
			 if(s.getText().toLowerCase().contains("mms"))
			 a = true;
		 }
		 Assert.assertTrue(a);
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "Vista360", "E2E", "Ciclo1"}, dataProvider = "CuentaVista360")
	public void TS134809_CRM_Movil_Prepago_Vista_360_Consulta_por_gestiones_Gestiones_no_registradas_FAN_Front_Telefonico(String sDNI, String sLinea, String sNombre){
		imagen = "TS134809";
		detalles = null;
		detalles = imagen + " -ServicioTecnico: " + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		CustomerCare cCC = new CustomerCare(driver);
		cCC.irAGestiones();
		String day = fechaDeHoy();
		String dia = day.substring(0, 2);
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-id-1")));
		driver.findElement(By.id("text-input-id-1")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--icon-container.nds-button.nds-button_icon-container")).click();
		driver.findElement(By.xpath("//*[text() = '01']")).click();
		driver.findElement(By.id("text-input-id-2")).click();
		WebElement table_2 = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		List<WebElement> tableRows_2 = table_2.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows_2) {
			try {
				if (cell.getText().equals(dia)) {
					cell.click();
					sleep(5000);
				}
			} catch (Exception e) {}
		}
		sleep(3000);
		List<WebElement> tipo = driver.findElement(By.cssSelector(".slds-dropdown.slds-dropdown--left.resize-dropdowns")).findElements(By.tagName("li"));
		for(WebElement t : tipo){
			if(t.getText().toLowerCase().equals("todos")){
				t.click();
			}
		}
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(9000);
		Boolean asd = true;
		List <WebElement> cuadro = driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement c : cuadro){
			if(c.getText().toLowerCase().contains("Order")){
				asd = false;
			}
		}
		Assert.assertTrue(asd);	
	}
	
	@Test(groups = { "GestionesPerfilOficina", "Vista360", "E2E", "" }, dataProvider = "CuentaVista360")
	public void TS134794_CRM_Movil_Prepago_Vista_360_Distribucion_de_paneles_Informacion_del_cliente_FAN_Front_Telefonico(String sDNI, String sNombre, String sLinea) {
		imagen = "TS134794";
		detalles = imagen + " - Vista360 - DNI: " + sDNI;
		BasePage cambioFrameByID = new BasePage();
		sleep(30000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id " + accid);
		detalles += "-Cuenta:" + accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(18000);
		try {
			cc.openleftpanel();
		} catch (Exception eE) {}
		WebElement wProfileBoxDetails = driver.findElement(By.className("profile-box-details"));
		Assert.assertTrue(wProfileBoxDetails.findElement(By.tagName("h1")).getText().toLowerCase().contains(sNombre.toLowerCase()));
		String sWebDNI = wProfileBoxDetails.findElement(By.className("detail-card")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(1).getText();
		Assert.assertTrue(sWebDNI.equals(sDNI));
		Assert.assertTrue(sWebDNI.matches("[0-9]+"));
		String npsText = wProfileBoxDetails.findElement(By.className("detail-card")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(2).findElements(By.tagName("td")).get(0).getText();
		System.out.println(npsText);
		Assert.assertTrue(npsText.equals("NPS:"));
		List<WebElement> wWebList = wProfileBoxDetails.findElements(By.className("profile-edit"));
		Assert.assertTrue(wWebList.get(0).getText().toLowerCase().equals("actualizar datos"));
		Assert.assertTrue(wWebList.get(1).getText().toLowerCase().equals("reseteo clave"));
		driver.findElement(By.xpath("//div[@class='left-sidebar-section-header'] //span[contains(text(),'Datos Personales')] /.. /.. /.. /.. /..")).click();
		String headerDatosPersonales = driver.findElement(By.xpath("//div[@class='left-sidebar-section-header'] //span[contains(text(),'Datos Personales')]")).getText();
		Assert.assertTrue(headerDatosPersonales.equals("Datos Personales"));
		driver.findElement(By.xpath("//div[@class='left-sidebar-section-header'] //span[contains(text(),'Segmentaci')] /.. /.. /.. /.. /..")).click();
		String headerSegmentacion = driver.findElement(By.xpath("//div[@class='left-sidebar-section-header'] //span[contains(text(),'Segmentaci')]")).getText();
		Assert.assertTrue(headerSegmentacion.equalsIgnoreCase("Segmentaci\u00f3n"));
		List<WebElement> panelesIzquierdos = driver.findElements(By.cssSelector(".left-sidebar-section-container"));
		List<WebElement> datosPersonales = panelesIzquierdos.get(0).findElements(By.cssSelector(".client-data-label"));
		List<WebElement> segmentacion = panelesIzquierdos.get(1).findElements(By.cssSelector(".client-data-label"));
		ArrayList<String> datosPersonalesComparar = new ArrayList<String>(Arrays.asList("Correo Electr\u00f3nico:","M\u00f3vil:", "Tel\u00e9fono alternativo:", "Club Personal:", "Categor\u00eda:"));
		ArrayList<String> segmentacionComparar = new ArrayList<String>(Arrays.asList("Segmento:", "Atributos:"));
		for (int i = 0; i < datosPersonales.size(); i++) {
			Assert.assertTrue(datosPersonales.get(i).getText().equals(datosPersonalesComparar.get(i)));
		}
		for (int i = 0; i < segmentacion.size(); i++) {
			WebElement campo = segmentacion.get(i);
			String textoDelCampo = campo.getText();
			Assert.assertTrue(textoDelCampo.equals(segmentacionComparar.get(i)));
			if (textoDelCampo.equals("Segmento:")) {
				String valorDelCampo = driver.findElement(By.xpath("/html/body/div/div[1]/ng-include/div[4]/ng-include/div/div[2]/table[1]/tbody/tr/td/div[2]")).getText();
				Assert.assertTrue(valorDelCampo.equals("Alta reciente - Masivo"));
			}
		}
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "Vista360", "Ciclo2"}, dataProvider = "CuentaVista360")
	public void TS134796_CRM_Movil_Prepago_Vista_360_Distribucion_de_paneles_Visualizacion_e_ingreso_a_las_ultimas_gestiones_FAN_Front_Telefonico(String sDNI, String sLinea,String sNombre) {
		imagen = "TS134796";
		detalles = null;
		detalles = imagen+"-Vista 360 - DNI:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-p-around--small.slds-col")));
		WebElement gestiones = driver.findElement(By.cssSelector(".slds-p-around--small.slds-col"));
		Assert.assertTrue(gestiones.getText().toLowerCase().contains("t\u00edtulo") && gestiones.getText().contains("Fecha de creacion") && gestiones.getText().toLowerCase().contains("estado") && gestiones.getText().toLowerCase().contains("numero de orden"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "Vista360", "Ciclo2"}, dataProvider = "CuentaVista360")
	public void TS134797_CRM_Movil_Prepago_Vista_360_Distribucion_de_paneles_Panel_Derecho_Busqueda_de_gestiones_promociones_y_gestiones_abandonadas_FAN_Front_Telefonico(String sDNI, String sLinea,String sNombre) {
		imagen = "TS134797";
		detalles = null;
		detalles = imagen+"-Vista 360 - DNI:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".abandoned-content.scrollmenu")));
		WebElement abandoned = driver.findElement(By.className("abandoned-section"));
		WebElement promocion = driver.findElement(By.className("promotions-section"));
		WebElement busquedgestion = driver.findElement(By.className("sidebar-actions"));
		Assert.assertTrue(abandoned.getText().contains("Gestiones Abandonadas") && driver.findElement(By.className("abandoned-section")).isDisplayed());
		Assert.assertTrue(promocion.getText().contains("Promociones") && driver.findElement(By.className("promotions-section")).isDisplayed());
		Assert.assertTrue(busquedgestion.getText().contains("Iniciar Gestiones") && driver.findElement(By.className("sidebar-actions")).isDisplayed());
	}
	
	@Test(groups = { "GestionPerfilTelefonico", "Ciclo2", "Vista360" }, dataProvider = "CuentaVista360")
	public void TS134799_CRM_Movil_Prepago_Vista_360_Producto_Activo_del_cliente_Desplegable_FAN_Front_Telefonico(String sDNI, String sLinea, String sNombre) {
		imagen = "134799";
		detalles = null;
		detalles = imagen + "-Vista 360-DNI:" + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(14000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(3000);
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		WebElement desplegable = driver.findElement(By.cssSelector(".console-flyout.active.flyout"));
		Assert.assertTrue(desplegable.isDisplayed());
		ArrayList<String> elementosDesplegableIzquierdoComparar = new ArrayList<String>(Arrays.asList(
				"Historial de Suspensiones", "Recarga de crédito", "Renovacion de Datos",
				"Alta/Baja de Servicios", "Suscripciones", "Inconvenientes con Recargas", "Diagnóstico",
				"Números Gratis", "Cambio SimCard", "Cambio de Plan"));
		List<WebElement> elementosDesplegableIzquierdo = desplegable.findElements(By.cssSelector("[class='console-flyout active flyout'] [class='community-flyout-actions-card'] ul li"));
		int i = 0;
		for (WebElement fila : elementosDesplegableIzquierdo) {
			Assert.assertTrue(fila.getText().equals(elementosDesplegableIzquierdoComparar.get(i)));
			i++;
		}
		Assert.assertTrue(true);
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "Vista360", "E2E","ConsultaPorGestion", "Ciclo2"}, dataProvider = "CuentaVista360")
	public void TS134808_CRM_Movil_Prepago_Vista_360_Consulta_por_gestiones_Gestiones_Cerradas_Informacion_brindada_FAN_Front_Telefonico(String sDNI, String sLinea, String sNombre) {
		imagen = "TS134808";
		detalles = null;
		detalles = imagen+"-Vista 360 - DNI:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "equals", "gestiones");
		sleep(2000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement table = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		List<WebElement> tableRows = table.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows) {
			try {
				if (cell.getText().equals("25"))
					cell.click();
			} catch (Exception e) {}
		}
		driver.findElement(By.id("text-input-id-2")).click();
		WebElement table_2 = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		List<WebElement> tableRows_2 = table_2.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows_2) {
			try {
				if (cell.getText().equals("01"))
					cell.click();
			} catch (Exception e) {}
		}
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")));
		sleep(5000);
		WebElement tabla = driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header"));
		Assert.assertTrue(tabla.isDisplayed());
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "Vista360", "Ciclo2"},  dataProvider = "CuentaVista360")
	public void TS135351_CRM_Movil_Prepago_Vista_360_Consulta_de_Gestiones_Gestiones_abiertas_Plazo_No_vencido_Consulta_registrada_CASOS_FAN_Telefonico(String sDNI, String sLinea,String sNombre) {
		imagen = "TS135351";
		boolean gestion = false;
		detalles = null;
		detalles = imagen +" -Vista 360-DNI: " + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", "64747868");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		cc.irAGestiones();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.id("text-input-03")).click();
		driver.findElement(By.xpath("//*[text() = 'Casos']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(9000);
		String nroCaso = driver.findElement(By.cssSelector(".slds-p-bottom--small")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(2).findElement(By.tagName("div")).findElement(By.tagName("a")).getText();
		driver.switchTo().defaultContent();
		sb.cerrarPestaniaGestion(driver);
		cc.buscarCaso(nroCaso);
		WebElement estado = null;
		driver.switchTo().frame(cambioFrame(driver, By.className("detailList")));
		for (WebElement x : driver.findElements(By.className("detailList"))) {
			if (x.getText().toLowerCase().contains("propietario del caso"))
				estado = x;
		}
		for (WebElement x : estado.findElements(By.tagName("tr"))) {
			if (x.getText().toLowerCase().contains("estado"))
				estado = x;			
		}
		if (estado.getText().toLowerCase().contains("en espera de ejecuci\u00f3n") || (estado.getText().toLowerCase().contains("informada") ||(estado.getText().toLowerCase().contains("resuelta exitosa"))))
			gestion = true;
		Assert.assertTrue(gestion);
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "Vista360", "Ciclo2"},  dataProvider = "CuentaVista360")
	public void TS135356_CRM_Movil_Prepago_Vista_360_Consulta_de_Gestiones_Gestiones_abiertas_Plazo_No_vencido_Consulta_registrada_ORDENES_FAN_Telefonico(String sDNI, String sLinea,String sNombre) {
		imagen = "TS135356";
		detalles = null;
		detalles = imagen+"-Vista 360 - DNI:"+sDNI;
		boolean gestion = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		cc.irAGestiones();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.id("text-input-03")).click();
		driver.findElement(By.xpath("//*[text() = 'Ordenes']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")));
		WebElement nroCaso = driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
		nroCaso.findElements(By.tagName("td")).get(2).findElement(By.tagName("div")).findElement(By.tagName("a")).click();
		cc.obligarclick(nroCaso);
		sleep(15000);
		WebElement estado = null;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		for (WebElement x : driver.findElements(By.className("pbSubsection"))) {
			if (x.getText().toLowerCase().contains("n\u00famero de pedido"))
				estado = x;
		}
		for (WebElement x : estado.findElement(By.className("detailList")).findElements(By.tagName("tr"))) {
			if (x.getText().toLowerCase().contains("estado"))
				estado = x;
		}
		if (estado.getText().toLowerCase().contains("activada") || (estado.getText().toLowerCase().contains("iniciada")))
			gestion = true;
		Assert.assertTrue(gestion);
	}
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test (groups = {"GestionesPerfilAgente", "Vista360", "E2E", "Ciclo1"}, dataProvider = "CuentaVista360")
	public void TS134823_CRM_Movil_Prepago_Vista_360_Producto_Activo_del_cliente_Datos_FAN_Front_Agentes(String sDNI, String sLinea, String sNombre){
		imagen = "TS13823";
		detalles = null;
		detalles = imagen + " -ServicioTecnico: " + sDNI;
		boolean creditoRecarga = false, creditoPromocional = false, estado = false, internetDisponible = false;
		boolean detalles = false, historiales = false, misServicios = false, gestiones = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		WebElement plan = driver.findElement(By.className("card-top")).findElements(By.className("slds-text-heading_medium")).get(0);
		WebElement FechaActivacion = driver.findElement(By.cssSelector(".slds-text-body_regular.expired-title"));
		WebElement linea = driver.findElement(By.className("card-top")).findElements(By.className("slds-text-heading_medium")).get(2);
		System.out.println(plan.getText()); System.out.println(FechaActivacion.getText()); System.out.println(linea.getText());
		assertTrue(plan.isDisplayed() && FechaActivacion.isDisplayed() && linea.getText().contains(sLinea));
		for (WebElement x : driver.findElements(By.cssSelector(".slds-text-body_regular.detail-label"))) {
			if (x.getText().toLowerCase().contains("cr\u00e9dito recarga"))
				creditoRecarga = true;
			if (x.getText().toLowerCase().contains("cr\u00e9dito promocional"))
				creditoPromocional = true;
			if (x.getText().toLowerCase().contains("estado"))
				estado = true;
			if (x.getText().toLowerCase().contains("internet disponible"))
				internetDisponible = true;
		}
		Assert.assertTrue(creditoRecarga && creditoPromocional && estado && internetDisponible);
		for(WebElement y : driver.findElements(By.className("slds-text-body_regular"))) {
			if(y.getText().toLowerCase().contains("detalles de consumo"))
				detalles = true;
			if(y.getText().toLowerCase().contains("historiales"))
				historiales = true;
			if(y.getText().toLowerCase().contains("mis servicios"))
				misServicios = true;
			if(y.getText().toLowerCase().contains("gestiones"))
				gestiones = true;
		}
		Assert.assertTrue(detalles && historiales && misServicios && gestiones);
	}
	
	@Test (groups = {"GestionesPerfilAgente", "Vista360", "E2E", "Ciclo1"}, dataProvider = "CuentaVista360")
	public void TS134824_CRM_Movil_Prepago_Vista_360_Producto_Activo_del_cliente_Desplegable_FAN_Front_Agentes(String sDNI, String sLinea, String sNombre){
		imagen = "TS13824";
		detalles = null;
		detalles = imagen + " -ServicioTecnico: " + sDNI;
		boolean historial = false, recarga = false, renovacion = false, servicios = false, suscripciones = false, inconvenientes = false, diagnostico = false, numeros = false, cambioSim = false, cambioPlan = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		WebElement wFlyOutActionCard = driver.findElement(By.className("community-flyout-actions-card"));
		List<WebElement> wGestiones = wFlyOutActionCard.findElements(By.tagName("li"));
		for (WebElement wAux : wGestiones) {
			if (wAux.getText().toLowerCase().contains("historial de suspensiones"))
				historial = true;
			if(wAux.getText().toLowerCase().contains("recarga de cr\u00e9dito"))
				recarga = true;
			if(wAux.getText().toLowerCase().contains("renovacion de datos"))
				renovacion = true;	
			if(wAux.getText().toLowerCase().contains("alta/baja de servicios"))
				servicios = true;
			if(wAux.getText().toLowerCase().contains("suscripciones"))
				suscripciones = true;
			if(wAux.getText().toLowerCase().contains("inconvenientes de recargas"))
				inconvenientes = true;
			if(wAux.getText().toLowerCase().contains("diagn\u00f3stico"))
				diagnostico = true;
			if(wAux.getText().toLowerCase().contains("n\u00fameros gratis"))
				numeros = true;
			if(wAux.getText().toLowerCase().contains("cambio simcard"))
				cambioSim = true;
			if(wAux.getText().toLowerCase().contains("cambio de plan"))
				cambioPlan = true;					
		}
		Assert.assertTrue(historial && recarga && renovacion && servicios && suscripciones && inconvenientes && diagnostico && numeros && cambioSim && cambioPlan);
	}
	
	@Test (groups = {"GestionesPerfilAgente", "Vista360", "E2E", "Ciclo1"}, dataProvider = "CuentaVista360")
	public void TS134832_CRM_Movil_Prepago_Vista_360_Consulta_por_gestiones_Gestiones_no_registradas_FAN_Front_Agentes(String sDNI, String sLinea, String sNombre){
		imagen = "TS13832";
		detalles = null;
		detalles = imagen + " -ServicioTecnico: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestiones();
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-id-1")));
		String day = fechaDeHoy();
		String dia = day.substring(0, 2);
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement table = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		List<WebElement> tableRows = table.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows) {
			try {
				if (cell.getText().equals("09"))
					cell.click();
			} catch (Exception e) {}
		}
		driver.findElement(By.id("text-input-id-2")).click();
		WebElement table_2 = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		List<WebElement> tableRows_2 = table_2.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows_2) {
			try {
				if (cell.getText().equals(dia))
					cell.click();
			} catch (Exception e) {}
		}
		sleep(3000);
		List<WebElement> tipo = driver.findElement(By.cssSelector(".slds-dropdown.slds-dropdown--left.resize-dropdowns")).findElements(By.tagName("li"));
		for(WebElement t : tipo){
			if(t.getText().toLowerCase().equals("todos"))
				t.click();
		}
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(9000);
		Boolean asd = true;
		List <WebElement> cuadro = driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement c : cuadro){
			if(c.getText().toLowerCase().contains("Order"))
				asd = false;
		}
		Assert.assertTrue(asd);
	}
	
	@Test (groups= {"GestionesPerfilAgente", "Ciclo2", "Vista360"}, dataProvider = "documentacionVista360")
	public void TS134817_CRM_Movil_Prepago_Vista_360_Mis_Servicios_Visualizacion_del_estado_de_los_servicios_activos_FAN_Front_OOCC_Agentes(String sDNI){
		imagen = "TS134817";
		detalles = null;
		detalles = imagen + "-Vista 360 - DNI: "+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(3000);
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "equals", "mis servicios");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--brand")), "equals", "ver detalle");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".via-slds-card__header.slds-card__header.headerList")));
		boolean b = false;
		for(WebElement x : driver.findElements(By.cssSelector(".slds-card.slds-m-bottom--small"))) {
			if(x.getText().toLowerCase().contains("servicio") && x.getText().toLowerCase().contains("fecha") && x.getText().toLowerCase().contains("estado")) {
				b = true;
			}
		}
		Assert.assertTrue(b);
	}
	
	@Test (groups = {"GestionesPerfilAgente", "Vista360","Ciclo2"}, dataProvider="CuentaVista360")
	public void TS134818_CRM_Movil_Prepago_Vista_360_Mis_Servicios_Visualizacion_del_estado_de_los_Productos_activos_FAN_Front_Agentes(String sDNI, String sNombre, String sLinea){
		imagen = "TS134818";
		detalles = null;
		detalles = imagen + "Vista 360-DNI:" + sDNI;
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver); 
		sSB.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "equals", "mis servicios");
		sleep(10000);
		boolean a = false;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium")));
		WebElement wTabla = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
		List <WebElement> lTabla = wTabla.findElements(By.tagName("tr"));
 		for(WebElement x : lTabla) {
 			if(x.getText().toLowerCase().contains("activo")){
 				System.out.println(x.getText());
 				a = true;
 			}
 		}
		Assert.assertTrue(a);
	}
	
	@Test (groups= {"GestionesPerfilAgente", "Ciclo2", "Vista360"}, dataProvider = "CuentaVista360")
	public void TS134819_CRM_Movil_Prepago_Vista_360_Distribucion_de_paneles_Informacion_del_cliente_FAN_Front_Agentes(String sDNI, String sNombre, String sLinea){
		imagen = "TS134819";
		detalles = null;
		detalles = imagen + "-Vista 360 - DNI: "+ sDNI+ "- Nombre: " + sNombre;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		WebElement nombre = driver.findElement(By.cssSelector(".slds-text-heading_large.title-card"));
		WebElement dni = null;
		for (WebElement x : driver.findElements(By.cssSelector(".slds-text-body_regular.account-detail-content"))) {
			if (x.getText().toLowerCase().contains(sDNI))
				dni = x;
		}
		Assert.assertTrue(nombre.getText().contains(sNombre) && dni.getText().contains(sDNI));
		WebElement tabla = driver.findElement(By.className("detail-card"));
		Assert.assertTrue(tabla.getText().toLowerCase().contains("atributo") && tabla.getText().toLowerCase().contains("nps"));
		WebElement tabla2 = driver.findElement(By.className("profile-box"));
		Assert.assertTrue(tabla2.getText().toLowerCase().contains("actualizar datos") && tabla2.getText().toLowerCase().contains("reseteo clave"));
		WebElement tabla3 = driver.findElement(By.className("left-sidebar-section-container"));
		Assert.assertTrue(tabla3.getText().contains("Correo Electr\u00f3nico") && tabla3.getText().contains("M\u00f3vil"));
		Assert.assertTrue(tabla3.getText().toLowerCase().contains("tel\u00e9fono alternativo") && tabla3.getText().toLowerCase().contains("club personal") && tabla3.getText().toLowerCase().contains("categor\u00eda"));
	}
	
	@Test (groups = {"GestionesPerfilAgente", "Vista360", "E2E", "Ciclo1"}, dataProvider = "CuentaVista360")
	public void TS134821_CRM_Movil_Prepago_Vista_360_Distribucion_de_paneles_Visualizacion_e_ingreso_a_las_ultimas_gestiones_FAN_Front_Agentes(String sDNI, String sNombre, String sLinea){
		imagen = "TS134821";
		detalles = null;
		detalles = imagen + "Vista 360 -DNI:" + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		cc.irAGestiones();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-03")));
		driver.findElement(By.id("text-input-03")).click();
		driver.findElement(By.xpath("//*[text() = 'Ordenes']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(5000);
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-m-around--medium")).getLocation().y+" )");
		WebElement nroCaso = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-m-around--medium")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
		boolean ingresar = false;
		if(nroCaso.findElements(By.tagName("td")).get(2).findElement(By.tagName("div")).findElement(By.tagName("a")).isDisplayed()) {
			nroCaso.findElements(By.tagName("td")).get(2).findElement(By.tagName("div")).findElement(By.tagName("a")).click();
			ingresar = true;
		}
		Assert.assertTrue(ingresar);
	}
	
	@Test (groups = {"GestionesPerfilAgente", "Vista360", "E2E","ConsultaPorGestion", "Ciclo2"}, dataProvider = "CuentaVista360")
	public void TS134831_CRM_Movil_Prepago_Vista_360_Consulta_por_gestiones_Gestiones_Cerrada_Informacion_brindada_FAN_Front_Agentes(String sDNI, String sNombre, String sLinea) {
		imagen = "TS134831";
		detalles = null;
		detalles = imagen+"-Vista 360 -DNI:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", "34372815");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(18000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "equals", "gestiones");
		sleep(2000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement table = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		List<WebElement> tableRows = table.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows) {
			try {
				if (cell.getText().equals("16"))
					cell.click();
			} catch (Exception e) {}
		}
		driver.findElement(By.id("text-input-id-2")).click();
		WebElement table_2 = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		List<WebElement> tableRows_2 = table_2.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows_2) {
			try {
				if (cell.getText().equals("16"))
					cell.click();
			} catch (Exception e) {}
		}
		driver.findElement(By.id("text-input-03")).click();
		driver.findElement(By.xpath("//*[text() = 'Casos']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")));
		sleep(5000);
		boolean verificacion = false;
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-m-around--medium")).getLocation().y+" )");
		WebElement nroCaso = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-m-around--medium")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
		List <WebElement> lista = nroCaso.findElements(By.tagName("tr"));
		for(WebElement x : lista) {
			if(x.getText().toLowerCase().contains("resuelta exitosa") || x.getText().toLowerCase().contains("realizada exitosa")) {
				System.out.println(x.getText());
				verificacion = true;
			}
		}
		Assert.assertTrue(verificacion);
	}
}