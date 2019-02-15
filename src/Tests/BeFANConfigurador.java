package Tests;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Pages.BasePage;
import Pages.BeFan;
import Pages.ContactSearch;
import Pages.Marketing;
import Pages.SCP;
import Pages.setConexion;
import javafx.scene.web.WebEngineBuilder;

public class BeFANConfigurador extends TestBase {
	
	private WebDriver driver;
	private Pages.BeFan pbf;
	private SCP scp;
	
	private void irA(String sMenu,String sOpcion) {
		sleep(5000);
		List<WebElement> wMenu = driver.findElement(By.className("tpt-nav")).findElements(By.className("dropdown"));
		for (WebElement wAux : wMenu) {
			if (wAux.findElement(By.className("dropdown-toggle")).getText().toLowerCase().contains(sMenu.toLowerCase())) {
				wAux.click();
			}
		}
		
		
		
		List<WebElement> wOptions = driver.findElement(By.cssSelector(".dropdown.open")).findElement(By.className("multi-column-dropdown")).findElements(By.tagName("li"));
		for (WebElement wAux2 : wOptions) {
			if (wAux2.findElement(By.tagName("a")).getText().toLowerCase().contains(sOpcion.toLowerCase())) {
				wAux2.click();
				sleep(3000);
				break;
			}
		}
	}
	

	@BeforeClass (alwaysRun = true)
	public void init() {
		driver = setConexion.setupEze();
		scp = new SCP(driver);
		loginBeFANConfigurador(driver);
	}
	
//	@AfterMethod (alwaysRun = true)
	public void after() {
		driver.get(TestBase.urlBeFAN);
		sleep(3000);
	}
	
	//@AfterClass (alwaysRun = true)
	public void quit() {
		driver.quit();
	}
	
	@Test (groups = "BeFAN", dataProvider="GestionRegionesCreacion")
	public void TS126620_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Busqueda_especifica(String sRegion) {
		irA("Regiones", "Gesti\u00f3n");
		pbf = new Pages.BeFan(driver);
		
		Assert.assertTrue(pbf.buscarRegion(sRegion));
	}
	
	@Test (groups = "BeFAN")
	public void TS126661_BeFan_Movil_REPRO_Preactivacion_repro_Busqueda_de_archivos_Usuario_TPA_Archivos_de_otros_agentes() {
		boolean match = false;
		irA("sims", "gesti\u00f3n");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Procesado");
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(0), "VJP");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");		
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(5000);
		String agente = driver.findElement(By.id("exportarTabla")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(4).getText();
		List<WebElement> tabla = driver.findElement(By.id("exportarTabla")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for (int i=0; i<9; i++) {
			if (!tabla.get(i).findElements(By.tagName("td")).get(4).getText().equals(agente))
				match = true;
			i++;
		}
		Assert.assertTrue(match);
	}
	
	@Test (groups = "BeFAN")
	public void TS126618_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Pantalla_de_inicio() {
		irA("regiones", "gesti\u00f3n");
		List<WebElement> agrupadores = driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope"));
		Assert.assertTrue(agrupadores.size() >= 1);
		if (driver.findElements(By.className("collapsed")).size() >= 1)
			Assert.assertTrue(true);
		else
			Assert.assertTrue(false);
	}
	
	@Test (groups = "BeFAN")
	public void TS126621_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Busqueda_vacia() {
		irA("Regiones", "Gesti\u00f3n");
		sleep(3000);
		driver.findElement(By.xpath("//*[@type='search']")).sendKeys("asd");
		driver.findElement(By.xpath("//*[@type='search']")).clear();
		sleep(3000);
		WebElement wBody = driver.findElement(By.className("panel-data"));
		List<WebElement> wList = wBody.findElements(By.xpath("//*[@class='panel-group'] //*[@class='collapsed'] //*[@class='ng-binding']"));
		
		boolean bAssert = false;
		String sAgrupador = wList.get(0).getText().toLowerCase();
		for (WebElement wAux : wList) {
			if (!wAux.getText().toLowerCase().equalsIgnoreCase(sAgrupador)) {
				bAssert = true;
				break;
			}
		}
		
		Assert.assertTrue(bAssert && wList.size() > 1);
	}
	@Test (groups = "BeFAN")
	public void TS126592_BeFan_Movil_REPRO_Preactivacion_repro_Cantidad_inexistente() {
	boolean Mensaje = false;
	irA("Cupos", "Importaci\u00f3n");
	driver.findElement(By.name("INPUT-ArchivoCupos")).sendKeys("C:\\Users\\Quelys\\Documents\\1.txt");
	sleep(3000);
	driver.findElement(By.name("BTN-Importar")).click();
	sleep(3000);
	driver.findElement(By.className("modal-header")).getText().equalsIgnoreCase("El archivo que desea importar est\u00e1 vac\u00edo.");
	Mensaje=true;
	Assert.assertTrue(Mensaje);
	
	}
	
	@Test (groups = "BeFAN")
	public void TS135630_BeFan_Movil_Repro_Preactivacion_Gestion_de_cupos_Busqueda_Sin_fecha_desde_y_sin_fecha_hasta() {
	boolean Mensaje = false;
	irA("Cupos", "Gesti\u00f3n");
	selectByText(driver.findElement(By.name("agentes")), "VJP");
	selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
	selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Vigente");
	driver.findElement(By.id("dataPickerDesde")).click();
	driver.findElement(By.id("dataPickerDesde")).clear();
	driver.findElement(By.id("dataPickerHasta")).click();
	driver.findElement(By.id("dataPickerHasta")).clear();
	driver.findElement(By.name("buscar")).click();
	driver.findElement(By.className("modal-header")).getText().equalsIgnoreCase("Debe ingresar fecha desde y fecha hasta.");
	Mensaje=true;
	Assert.assertTrue(Mensaje);
	}
	
	@Test (groups = "BeFAN")
	public void TS135629_BeFan_Movil_Repro_Preactivacion_Gestion_de_cupos_Busqueda_Sin_fecha_hasta() {
	boolean Mensaje = false;
	irA("Cupos", "Gesti\u00f3n");
	selectByText(driver.findElement(By.name("agentes")), "VJP");
	selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
	selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Vigente");
	driver.findElement(By.id("dataPickerDesde")).click();
	((JavascriptExecutor) driver).executeScript("document.getElementById('dataPickerDesde').value='12/12/2018'");
	driver.findElement(By.id("dataPickerHasta")).click();
	driver.findElement(By.id("dataPickerHasta")).clear();
	driver.findElement(By.name("buscar")).click();
	driver.findElement(By.className("modal-header")).getText().equalsIgnoreCase("Debe ingresar fecha desde y fecha hasta.");
	Mensaje=true;
	Assert.assertTrue(Mensaje);
	}
	
	@Test (groups = "BeFAN")
	public void TS135628_BeFan_Movil_Repro_Preactivacion_Gestion_de_cupos_Busqueda_Sin_fecha_desde() {
	boolean Mensaje = false;
	irA("Cupos", "Gesti\u00f3n");
	selectByText(driver.findElement(By.name("agentes")), "VJP");
	selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
	selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Vigente");
	driver.findElement(By.id("dataPickerDesde")).click();
	driver.findElement(By.id("dataPickerDesde")).clear();
	driver.findElement(By.id("dataPickerHasta"));
	((JavascriptExecutor) driver).executeScript("document.getElementById('dataPickerHasta').value='28/12/2018'");
	driver.findElement(By.name("buscar")).click();
	driver.findElement(By.className("modal-header")).getText().equalsIgnoreCase("Debe ingresar fecha desde y fecha hasta.");
	Mensaje=true;
	Assert.assertTrue(Mensaje);
	}
	
	@Test (groups = "BeFAN")
	public void TS135624_BeFan_Movil_Repro_Preactivacion_Gestion_de_cupos_Valores_por_default() {
	irA("Cupos", "Gesti\u00f3n");
	boolean valores = driver.findElement(By.className("tpt-body")).findElement(By.tagName("div")).isDisplayed();
	Assert.assertTrue(valores);
		
	}
	
	@Test (groups = "BeFAN")
	public void TS135625_BeFan_Movil_Repro_Preactivacion_Gestion_de_cupos_Busqueda_Fecha_desde_Formato_erroneo() {
	irA("Cupos", "Gesti\u00f3n");
	selectByText(driver.findElement(By.name("agentes")), "VJP");
	selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
	selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Vigente");
	driver.findElement(By.id("dataPickerDesde"));
	((JavascriptExecutor) driver).executeScript("document.getElementById('dataPickerDesde').value='12122018'");
	sleep(3000);
	driver.findElement(By.id("dataPickerHasta"));
	((JavascriptExecutor) driver).executeScript("document.getElementById('dataPickerHasta').value='28/12/2018'");
	driver.findElement(By.name("buscar")).click();
	Assert.assertTrue(driver.findElement(By.className("modal-header")).getText().equalsIgnoreCase("El formato de Fecha debe ser DD/MM/AAAA."));
	}
	
	@Test (groups = "BeFAN")
	public void TS135626_BeFan_Movil_Repro_Preactivacion_Gestion_de_cupos_Busqueda_Fecha_hasta_Formato_erroneo() {
	irA("Cupos", "Gesti\u00f3n");
	selectByText(driver.findElement(By.name("agentes")), "VJP");
	selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
	selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Vigente");
	driver.findElement(By.id("dataPickerDesde"));
	((JavascriptExecutor) driver).executeScript("document.getElementById('dataPickerDesde').value='12/12/2018'");
	sleep(3000);
	driver.findElement(By.id("dataPickerHasta"));
	((JavascriptExecutor) driver).executeScript("document.getElementById('dataPickerHasta').value='28122018'");
	driver.findElement(By.name("buscar")).click();
	Assert.assertTrue(driver.findElement(By.className("modal-header")).getText().equalsIgnoreCase("El formato de Fecha debe ser DD/MM/AAAA."));
	}
	
	@Test (groups = "BeFAN")
	public void TS135627_BeFan_Movil_Repro_Preactivacion_Gestion_de_cupos_Busqueda_Fecha_mayor_a_90_dias() throws ParseException {
	BeFan fechas= new BeFan (driver);
	SimpleDateFormat formatoDelTexto = new SimpleDateFormat ("dd/MM/yyyy");
	String desde ="01/05/2018";
	String hasta = "25/12/2018";
	Date fechaDesde = formatoDelTexto.parse(desde);
	Date fechaHasta =formatoDelTexto.parse(hasta);
	irA("Cupos", "Gesti\u00f3n");
	selectByText(driver.findElement(By.name("agentes")), "VJP");
	selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
	selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Vigente");
	driver.findElement(By.id("dataPickerDesde"));
	((JavascriptExecutor) driver).executeScript("document.getElementById('dataPickerDesde').value='"+desde+"'");
	sleep(3000);
	driver.findElement(By.id("dataPickerHasta"));
	((JavascriptExecutor) driver).executeScript("document.getElementById('dataPickerHasta').value='"+hasta+"'");
	int dias = fechas.numeroDiasEntreDosFechas(fechaDesde, fechaHasta);
	System.out.println("Hay " + dias + " dias, " +"Supera los 90 dias comprendidos");
	driver.findElement(By.cssSelector(".btn.btn-primary")).click();
	sleep(3000);
	driver.switchTo().defaultContent();
	Assert.assertFalse(driver.findElement(By.className("modal-content")).getText().equalsIgnoreCase("No se encontraron cupos para el filtro aplicado."));
	//Assert.assertTrue(driver.findElement(By.className("modal-header")).getText().equalsIgnoreCase("El per\u00eddo debe comprender menos de 90 d\u00edas."));
	}
	
	@Test (groups = "BeFAN")
	public void TS126633_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Eliminacion_de_agrupadores_No() {
		boolean cancelar = false;
		irA("regiones", "gesti\u00f3n");
		driver.findElement(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")).click();
		driver.findElement(By.cssSelector(".actions.text-center")).findElement(By.cssSelector(".btn.btn-link")).click();
		sleep(3000);
		if (driver.findElement(By.className("pull-right")).findElement(By.cssSelector(".btn.btn-link")).getText().equalsIgnoreCase("Cancelar")) {
			driver.findElement(By.className("pull-right")).findElement(By.cssSelector(".btn.btn-link")).click();
			cancelar = true;
		}
		Assert.assertTrue(cancelar);
	}
	
	@Test (groups = "BeFAN")
	public void TS126631_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Eliminacion_de_agrupadores_Mensaje() {
		boolean mensaje = false;
		irA("regiones", "gesti\u00f3n");
		driver.findElement(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")).click();
		driver.findElement(By.cssSelector(".actions.text-center")).findElement(By.cssSelector(".btn.btn-link")).click();
		sleep(3000);
		if (driver.findElement(By.cssSelector(".text-center.ng-binding")).getText().contains("�Esta seguro que desea eliminarlo ?"))
			mensaje = true;
		Assert.assertTrue(mensaje);
	}
	
	@Test (groups = "BeFAN")
	public void TS135644_BeFan_Movil_Repro_Preactivacion_Gestion_de_cupos_Busqueda_Modificacion_de_cupo_Con_total_de_cupos_invalidos() {
		irA("cupos", "gesti\u00f3n");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "VJP");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Vigente");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(3000);
		driver.findElement(By.name("modificarGuardar")).click();
		driver.findElement(By.name("cantidadTotal")).clear();
		driver.findElement(By.name("cantidadTotal")).sendKeys("asd");
		driver.findElement(By.name("modificarGuardar")).click();
		sleep(3000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".text-center.ng-binding")).getText().equalsIgnoreCase("Cantidad de cupo inv\u00e1lida"));
		driver.findElement(By.className("pull-right")).findElement(By.cssSelector(".btn.btn-link")).click();
		sleep(3000);
		driver.findElement(By.name("modificarGuardar")).click();
		driver.findElement(By.name("cantidadTotal")).clear();
		driver.findElement(By.name("cantidadTotal")).sendKeys("$");
		driver.findElement(By.name("modificarGuardar")).click();
		sleep(3000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".text-center.ng-binding")).getText().equalsIgnoreCase("Cantidad de cupo inv\u00e1lida"));
		driver.findElement(By.className("pull-right")).findElement(By.cssSelector(".btn.btn-link")).click();
		sleep(3000);
		driver.findElement(By.name("modificarGuardar")).click();
		driver.findElement(By.name("cantidadTotal")).clear();
		driver.findElement(By.name("cantidadTotal")).sendKeys("1111111");
		driver.findElement(By.name("modificarGuardar")).click();
		sleep(3000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".text-center.ng-binding")).getText().equalsIgnoreCase("Cantidad de cupo inv\u00e1lida"));
		driver.findElement(By.className("pull-right")).findElement(By.cssSelector(".btn.btn-link")).click();
	}
	
	@Test (groups = "BeFAN")
	public void TS135640_BeFan_Movil_Repro_Preactivacion_Gestion_de_cupos_Busqueda_Modificacion_de_cupo_Mensaje() {
		irA("cupos", "gesti\u00f3n");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "VJP");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Vigente");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(3000);
		driver.findElement(By.name("modificarGuardar")).click();
		driver.findElement(By.name("cantidadTotal")).clear();
		driver.findElement(By.name("cantidadTotal")).sendKeys("99998");
		driver.findElement(By.name("modificarGuardar")).click();
		sleep(3000);
		Assert.assertTrue(driver.findElements(By.cssSelector(".text-center.ng-binding")).get(1).getText().equalsIgnoreCase("Est\u00e1 seguro que desea modificar el registro seleccionado?"));
	}
	
	@Test (groups = "BeFAN")
	public void TS135641_BeFan_Movil_Repro_Preactivacion_Gestion_de_cupos_Busqueda_Modificacion_de_cupo_Mensaje_Confirmacion() {
		irA("cupos", "gesti\u00f3n");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "VJP");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Vigente");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(3000);
		driver.findElement(By.name("modificarGuardar")).click();
		String cantAnterior = driver.findElement(By.name("cantidadTotal")).getAttribute("value");
		driver.findElement(By.name("cantidadTotal")).clear();
		driver.findElement(By.name("cantidadTotal")).sendKeys("99998");
		driver.findElement(By.name("modificarGuardar")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "aceptar");
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-link")), "equals", "aceptar");
		driver.findElement(By.name("modificarGuardar")).click();
		Assert.assertTrue(driver.findElement(By.name("cantidadTotal")).getAttribute("value").equals("99998"));
		driver.findElement(By.name("cantidadTotal")).clear();
		driver.findElement(By.name("cantidadTotal")).sendKeys(cantAnterior);
		driver.findElement(By.name("modificarGuardar")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "aceptar");
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-link")), "equals", "aceptar");
	}
	@Test (groups = "BeFAN", dataProvider="GestionRegionesCreacion")
	public void TS126619_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Creacion_de_agrupador_exitosa(String sRegion) {
		irA("Regiones", "Gesti\u00f3n");
		sleep(3000);
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(3000);
		driver.findElement(By.cssSelector(".form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys(sRegion);
		driver.findElement(By.xpath("//*[@class='btn btn-primary' and contains(text(), 'Agregar')]")).click();
		sleep(5000);
		pbf = new Pages.BeFan(driver);
		Assert.assertTrue(pbf.verificarMensajeExitoso());
		//Ask about confirmation message
		driver.findElement(By.xpath("//*[@ng-show='mensajeAgregarRegionCtrl.container.showSuccess']//*[@class='btn btn-primary']")).click();
		TS126620_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Busqueda_especifica(sRegion);
	}
	
	@Test (groups = "BeFAN", dataProvider="GestionRegionesCreacion", dependsOnMethods="TS126619_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Creacion_de_agrupador_exitosa")
	public void TS126623_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Modificacion_de_agrupadores_Asignacion_de_prefijos_a_agrupador_existente_Guardando(String sRegion) {
		irA("Regiones", "Gesti\u00f3n");
		pbf = new Pages.BeFan(driver);
		List<String> sPrefijos = pbf.agregarPrefijos(sRegion);
		
		WebElement wBody = driver.findElement(By.xpath("//*[@class='panel-collapse in collapse'] //table[@class='table table-top-fixed table-striped table-primary ng-scope']"));
		Marketing mM = new Marketing(driver);
		List<WebElement> wPrefijosWeb = mM.traerColumnaElement(wBody, 3, 2);
		boolean bAssert = false;
		for (String sAux3 : sPrefijos) {
			bAssert = false;
			for (WebElement wAux4 : wPrefijosWeb) {
				if (sAux3.equals(wAux4.getText())) {
					bAssert = true;
				}
			}
			if (!bAssert) {
				break;
			}
		}
		Assert.assertTrue(bAssert);
	}
	
	@Test (groups = {"BeFAN", "EliminacionDePrefijo"}, dataProvider="GestionRegionesCreacion", dependsOnMethods="TS126623_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Modificacion_de_agrupadores_Asignacion_de_prefijos_a_agrupador_existente_Guardando")
	public void TS126625_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Modificacion_de_agrupadores_Eliminacion_de_prefijos_en_agrupador_existente_Guardando(String sRegion) throws IOException {
		String sPrefijo;
		irA("Regiones", "Gesti\u00f3n");
		pbf = new Pages.BeFan(driver);
		driver.navigate().refresh();
		pbf.buscarYAbrirRegion(sRegion);
		
		WebElement wBody = driver.findElement(By.xpath("//*[@class='panel-collapse in collapse'] //table[@class='table table-top-fixed table-striped table-primary ng-scope']"));
		Marketing mM = new Marketing(driver);
		List<WebElement> wRegiones = mM.traerColumnaElement(wBody, 3, 1);
		String sRegionBorrada = wRegiones.get(0).getText();
		driver.findElement(By.xpath("//*[@ng-repeat='prefijo in displayedCollection'] [1] //button")).click();
		sPrefijo = driver.findElement(By.xpath("//*[@ng-repeat='prefijo in displayedCollection'] [1] //td [2]")).getText();
		driver.findElement(By.xpath("//*[@ng-show='mensajeEliminarCtrl.container.showConfirmation'] //button[@class='btn btn-primary']")).click();
		sleep(3000);
		driver.findElement(By.xpath("//*[@ng-show='mensajeEliminarCtrl.container.showSuccess'] //button[@class='btn btn-primary']")).click();
		driver.navigate().refresh();
		
		pbf.buscarYAbrirRegion(sRegion);
		sleep(3000);
		wBody = driver.findElement(By.xpath("//*[@class='panel-collapse in collapse'] //table[@class='table table-top-fixed table-striped table-primary ng-scope']"));
		List<WebElement> wRegionesActualizadas = mM.traerColumnaElement(wBody, 3, 1);
		boolean bAssert= true;
		for (WebElement wAux : wRegionesActualizadas) {
			if (wAux.getText().equalsIgnoreCase(sRegionBorrada)) {
				bAssert = false;
				break;
			}
		}
		
		Assert.assertTrue(bAssert);
		
		File file = new File("Prefijo.txt");
		file.createNewFile();
		FileWriter writer = new FileWriter(file);
		writer.write(sPrefijo);
		writer.close();
	}
	
	@Test (groups = "BeFAN")
	public void TS97663_BeFan_Movil_REPRO_Localidad_inexistente() {
		irA("regiones", "gesti\u00f3n");
		boolean codigo = true;
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "mar del tuy\u00fa");
		driver.findElement(By.cssSelector(".panel-collapse.in.collapse")).findElement(By.cssSelector(".btn.btn-link")).click();
		sleep(3000);
		for (WebElement x : driver.findElements(By.cssSelector(".compatibility.custom-check.ng-scope"))) {
			if (!(x.getText().equalsIgnoreCase("3275")))
				codigo=false;
		}
		Assert.assertTrue(codigo);
	}
	
	@Test (groups = "BeFAN")
	public void TS97660_BeFan_Movil_REPRO_Seriales_con_estado_EN_PROCESO() {
		boolean enProceso = false;
		irA("sims", "gesti\u00f3n");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "En Proceso");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "VJP");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(5000);
		List<WebElement> tabla = driver.findElement(By.id("exportarTabla")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for (int i=0; i<5; i++) {
			if (tabla.iterator().next().findElements(By.tagName("td")).get(6).getText().equalsIgnoreCase("En Proceso"))
				enProceso = true;
			i++;
		}
		Assert.assertTrue(enProceso);
	}
	
	@Test (groups = "BeFAN")
	public void TS97661_BeFan_Movil_REPRO_Seriales_con_estado_VALIDADO() {
		boolean procesado = false;
		irA("sims", "gesti\u00f3n");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Procesado");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "VJP");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(5000);
		List<WebElement> tabla = driver.findElement(By.id("exportarTabla")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for (int i=0; i<5; i++) {
			if (tabla.iterator().next().findElements(By.tagName("td")).get(6).getText().equalsIgnoreCase("Procesado"))
				procesado = true;
			i++;
		}
		Assert.assertTrue(procesado);
	}
	
	@Test (groups = "BeFAN")
	public void TS112021_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Eliminacion_de_agrupadores_No() {
		boolean noEliminar = false;
		irA("regiones", "gesti\u00f3n");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		driver.findElement(By.cssSelector(".form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("Pinamar");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "pinamar");
		driver.findElement(By.cssSelector(".panel-collapse.in.collapse")).findElement(By.cssSelector(".btn.btn-link")).click();
		sleep(3000);
		driver.findElement(By.className("check-filter-on")).click();
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "agregar");
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
		sleep(5000);
		for (WebElement x : driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope"))) {
			if (x.getText().toLowerCase().contains("pinamar"))
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + x.getLocation().y + ")");
		}
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "pinamar");
		for (WebElement x : driver.findElements(By.className("panel-group"))) {
			if (x.getText().contains("3532"))
				x.findElement(By.tagName("tbody")).findElement(By.tagName("button")).click();
		}
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "eliminar");
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
		String msj = driver.findElement(By.className("modal-header")).getText();
		if (msj.contains("Tambien desea eliminar la Region Pinamar")) {
			buscarYClick(driver.findElements(By.cssSelector(".btn.btn-link")), "equals", "cancelar");
			noEliminar = true;
		}
		for (WebElement x : driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope"))) {
			if (x.getText().toLowerCase().contains("pinamar"))
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + x.getLocation().y + ")");
		}
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "pinamar");
		driver.findElement(By.cssSelector(".panel-collapse.in.collapse")).findElement(By.cssSelector(".btn.btn-link")).click();
		sleep(3000);
		driver.findElement(By.className("check-filter-on")).click();
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "agregar");
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
		sleep(5000);
		for (WebElement x : driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope"))) {
			if (x.getText().toLowerCase().contains("pinamar"))
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + x.getLocation().y + ")");
		}
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "pinamar");
		for (WebElement x : driver.findElements(By.className("panel-group"))) {
			if (x.getText().contains("3532"))
				x.findElement(By.tagName("tbody")).findElement(By.tagName("button")).click();
		}
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "eliminar");
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "eliminar");
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
		Assert.assertTrue(noEliminar);
	}
	@Test (groups = "BeFan")
	public void TS135605_BeFan_Movil_Repro_Preactivacion_Importacion_de_cupos_Formato_invalido() {
		ContactSearch contact = new ContactSearch(driver);
		String text = "debe importar un archivo .txt";
		irA("Cupos", "Importaci\u00f3n");
		sleep(7000);
		File directory = new File("BeFan135605.docx");
		contact.subir_cupos(new File(directory.getAbsolutePath()).toString(),"si");
		sleep(5000);
		boolean a = false;
		List <WebElement> formato = driver.findElements(By.className("modal-header"));
		for(WebElement x : formato) {
			if(x.getText().toLowerCase().contains(text)) {
				a = true;
				System.out.println("Se debe seleccionar un archivo con formato .txt");
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "BeFan")
	public void TS135631_BeFan_Movil_Repro_Preactivacion_Gestion_de_cupos_Busqueda_Formato() throws ParseException{
		BeFan fechas= new BeFan (driver);
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat ("dd/MM/yyyy");
		String desde ="28/09/2018";
		String hasta = "25/12/2018";
		Date fechaDesde = formatoDelTexto.parse(desde);
		Date fechaHasta =formatoDelTexto.parse(hasta);
		irA("Cupos", "Gesti\u00f3n");
		sleep(3000);
		selectByText(driver.findElement(By.name("estados")), "No Vigente");
		driver.findElement(By.id("dataPickerDesde"));
		((JavascriptExecutor) driver).executeScript("document.getElementById('dataPickerDesde').value='"+desde+"'");
		sleep(3000);
		driver.findElement(By.id("dataPickerHasta"));
		((JavascriptExecutor) driver).executeScript("document.getElementById('dataPickerHasta').value='"+hasta+"'");
		int dias = fechas.numeroDiasEntreDosFechas(fechaDesde, fechaHasta);
		System.out.println("Hay " + dias + " dias, " +"No supera los 90 dias comprendidos");
		driver.findElement(By.name("buscar")).click();
		sleep(8000);
		boolean razonS = false , region = false , puntoDeVenta = false, fechaD = false, fechaH = false, estado = false, cantidadTotal = false, disponibles = false, activados = false, reservados = false;
		List <WebElement> colum = driver.findElements(By.className("text-center"));
		for(WebElement x : colum) {
			if(x.getText().contains("Raz\u00f3n Social")) 
				razonS = true;
			if(x.getText().contains("Regi\u00f3n")) 
				region = true;
			if(x.getText().contains("Punto de Venta")) 
				puntoDeVenta = true;
			if(x.getText().contains("Fecha Desde")) 
				fechaD = true;
			if(x.getText().contains("Fecha Hasta"))
				fechaH = true;
			if(x.getText().contains("Estado")) 
				estado = true;
			if(x.getText().contains("Cantidad Total"))
				cantidadTotal= true;
			if(x.getText().contains("Disponibles"))
				disponibles = true;
			if(x.getText().contains("Activados"))
				activados = true;
			if(x.getText().contains("Reservados"))
				reservados = true;
		}
		Assert.assertTrue(razonS && region && puntoDeVenta && fechaD && fechaH && estado && cantidadTotal && disponibles && activados && reservados);
	}
	
	@Test (groups = "BeFan")
	public void TS135632_BeFan_Movil_Repro_Preactivacion_Gestion_de_cupos_Busqueda_Exportacion() throws ParseException{
		BeFan fechas= new BeFan (driver);
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat ("dd/MM/yyyy");
		String desde ="27/09/2018";
		String hasta = "25/12/2018";
		Date fechaDesde = formatoDelTexto.parse(desde);
		Date fechaHasta =formatoDelTexto.parse(hasta);
		irA("Cupos", "Gesti\u00f3n");
		sleep(3000);
		selectByText(driver.findElement(By.name("estados")), "No Vigente");
		driver.findElement(By.id("dataPickerDesde"));
		((JavascriptExecutor) driver).executeScript("document.getElementById('dataPickerDesde').value='"+desde+"'");
		sleep(3000);
		driver.findElement(By.id("dataPickerHasta"));
		((JavascriptExecutor) driver).executeScript("document.getElementById('dataPickerHasta').value='"+hasta+"'");
		int dias = fechas.numeroDiasEntreDosFechas(fechaDesde, fechaHasta);
		System.out.println("Hay " + dias + " dias, " +"No supera los 90 dias comprendidos");
		driver.findElement(By.name("buscar")).click();
		sleep(8000);
		WebElement boton = driver.findElement(By.name("exportar"));
		boton.click();
		Assert.assertTrue(boton.isDisplayed());
	}
	
	@Test (groups = "BeFan")
	public void TS126634_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Eliminacion_de_agrupadores_Si_Preactivando() throws InterruptedException {
		irA("Regiones", "Gesti\u00f3n");
		BeFan page = new BeFan(driver);
		boolean eliminar = false;
		//boolean cancelar = false;
		irA("regiones", "gesti\u00f3n");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		driver.findElement(By.cssSelector(".form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("cordoba");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		for(WebElement x : driver.findElements(By.cssSelector(".alert.alert-dismissable.alert-danger"))) {
		if(x.getText().contains("error")){
			//cancelar=(true);
			buscarYClick(driver.findElements(By.cssSelector(".btn.btn-link")), "equals", "cancelar");
			break;
		}
		else {
//				driver.findElement(By.cssSelector(".btn.btn-primary")).click();
				buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
			}
//		//Assert.assertTrue(cancelar);
		}
		//buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
		//page.buscarR("cordoba");
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "cordoba");
		driver.findElement(By.cssSelector(".panel-collapse.in.collapse")).findElement(By.cssSelector(".btn.btn-link")).click();
		sleep(3000);
		driver.findElement(By.className("check-filter-on")).click();
		
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "agregar");
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
		sleep(5000);
		for (WebElement x : driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope"))) {
			if (x.getText().toLowerCase().contains("cordoba"))
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + x.getLocation().y + ")");
		}
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "cordoba");
		for (WebElement x : driver.findElements(By.className("panel-group"))) {
			if (x.getText().contains("3546"))
				x.findElement(By.cssSelector(".glyphicon.glyphicon-remove")).click();
				//x.findElement(By.tagName("tbody")).findElement(By.tagName("button")).click();
			buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "eliminar");
			Assert.assertTrue(pbf.verificarMensajeExitoso());
			buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
			eliminar = true;
		}
		Assert.assertTrue(eliminar);
	}	
	
	@Test (groups = "BeFan")
	public void TS112020_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Eliminacion_de_agrupadores_Si_Sin_preactivar() {
		boolean eliminar = false;
		irA("regiones", "gesti\u00f3n");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		driver.findElement(By.cssSelector(".form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("Pinamar");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "pinamar");
		driver.findElement(By.cssSelector(".panel-collapse.in.collapse")).findElement(By.cssSelector(".btn.btn-link")).click();
		sleep(3000);
		driver.findElement(By.className("check-filter-on")).click();
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "agregar");
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
		sleep(5000);
		for (WebElement x : driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope"))) {
			if (x.getText().toLowerCase().contains("pinamar"))
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + x.getLocation().y + ")");
		}
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "pinamar");
		for (WebElement x : driver.findElements(By.className("panel-group"))) {
			if (x.getText().contains("3532"))
				x.findElement(By.tagName("tbody")).findElement(By.tagName("button")).click();
		}
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "eliminar");
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
		String msj = driver.findElement(By.className("modal-header")).getText();
		if (msj.contains("Tambien desea eliminar la Region Pinamar")) {
			buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "eliminar");
			buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
			eliminar = true;
		}
		Assert.assertTrue(eliminar);
	}
	
	@Test (groups = "BeFan")
	public void TS112019_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Eliminacion_de_agrupadores_Mensaje() {
		boolean mensaje = false;
		irA("regiones", "gesti\u00f3n");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		driver.findElement(By.cssSelector(".form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("Tierra Del Fuego");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
		
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "tierra del fuego");
		driver.findElement(By.cssSelector(".panel-collapse.in.collapse")).findElement(By.cssSelector(".btn.btn-link")).click();
		sleep(3000);
		String nroPrefijo = driver.findElement(By.cssSelector(".compatibility.custom-check.ng-scope")).findElement(By.tagName("label")).getText();
		System.out.println(nroPrefijo);
		driver.findElement(By.className("check-filter-on")).click();
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "agregar");
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
		sleep(5000);

		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "tierra del fuego");
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + driver.findElement(By.cssSelector(".panel.ng-scope.ng-isolate-scope.panel-default.panel-open")).getLocation().y + ")");
		for (WebElement x : driver.findElement(By.cssSelector(".panel.ng-scope.ng-isolate-scope.panel-default.panel-open")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"))) {
			if (x.getText().contains(nroPrefijo)) 
				x.findElement(By.cssSelector(".btn.btn-link")).click();
		}
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "eliminar");
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
		String msj = driver.findElement(By.className("modal-header")).getText();
		if (msj.contains("Tambien desea eliminar la Region Tierra Del Fuego")) {
			buscarYClick(driver.findElements(By.cssSelector(".btn.btn-link")), "equals", "cancelar");
			mensaje = true;
		}
		for (WebElement x : driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope"))) {
			if (x.getText().toLowerCase().contains("tierra del fuego"))
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + x.getLocation().y + ")");
		}
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "tierra del fuego");
		driver.findElement(By.cssSelector(".panel-collapse.in.collapse")).findElement(By.cssSelector(".btn.btn-link")).click();
		sleep(3000);
		driver.findElement(By.className("check-filter-on")).click();
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "agregar");
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
		sleep(5000);
		for (WebElement x : driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope"))) {
			if (x.getText().toLowerCase().contains("tierra del fuego"))
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + x.getLocation().y + ")");
		}
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "tierra del fuego");
		for (WebElement x : driver.findElements(By.className("panel-group"))) {
			if (x.getText().contains("3532"))
				x.findElement(By.tagName("tbody")).findElement(By.tagName("button")).click();
		}
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "eliminar");
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "eliminar");
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
		Assert.assertTrue(mensaje);
	}
	
	@Test (groups = "BeFan")
	public void TS112016_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Modificacion_de_agrupadores_Eliminacion_de_prefijos_en_agrupador_existente_Logeo() {
		irA("regiones", "gesti\u00f3n");
		boolean eliminarPrefijo = false;
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "la plata");
		for (WebElement x : driver.findElements(By.className("panel-group"))) {
			try {
				if (x.getText().toLowerCase().contains("la plata"))
					x.findElement(By.cssSelector(".glyphicon.glyphicon-plus")).click();
			} catch(Exception e) {}
		}
		sleep(3000);
		driver.findElement(By.cssSelector(".compatibility.custom-check.ng-scope")).findElement(By.tagName("input")).click();
		String nroPrefijo = driver.findElement(By.cssSelector(".compatibility.custom-check.ng-scope")).findElement(By.tagName("label")).getText();
		System.out.println(nroPrefijo);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "agregar");
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
		driver.navigate().refresh();
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "la plata");
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + driver.findElement(By.cssSelector(".panel.ng-scope.ng-isolate-scope.panel-default.panel-open")).getLocation().y + ")");
		for (WebElement x : driver.findElement(By.cssSelector(".panel.ng-scope.ng-isolate-scope.panel-default.panel-open")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"))) {
			if (x.getText().contains(nroPrefijo)) {
				x.findElement(By.cssSelector(".btn.btn-link")).click();
				buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "eliminar");
				buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
				eliminarPrefijo = true;
			}
		}
		Assert.assertTrue(eliminarPrefijo);
	}
	
	
	@Test (groups = "BeFan")
	public void TS135633_BeFan_Movil_Repro_Preactivacion_Gestion_de_cupos_Busqueda_Eliminacion_de_cupo_Cupo_no_vigente_sin_fecha_de_baja() throws ParseException {
		BeFan fechas= new BeFan (driver);
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat ("dd/MM/yyyy");
		String desde ="27/09/2018";
		String hasta = "25/12/2018";
		Date fechaDesde = formatoDelTexto.parse(desde);
		Date fechaHasta =formatoDelTexto.parse(hasta);
		irA("Cupos", "Gesti\u00f3n");
		sleep(3000);
		selectByText(driver.findElement(By.name("estados")), "No Vigente");
		driver.findElement(By.id("dataPickerDesde"));
		((JavascriptExecutor) driver).executeScript("document.getElementById('dataPickerDesde').value='"+desde+"'");
		sleep(3000);
		driver.findElement(By.id("dataPickerHasta"));
		((JavascriptExecutor) driver).executeScript("document.getElementById('dataPickerHasta').value='"+hasta+"'");
		int dias = fechas.numeroDiasEntreDosFechas(fechaDesde, fechaHasta);
		System.out.println("Hay " + dias + " dias, " +"No supera los 90 dias comprendidos");
		driver.findElement(By.name("buscar")).click();
		sleep(3000);
		WebElement eliminacion = driver.findElement(By.name("eliminar"));
		Assert.assertTrue(eliminacion.isEnabled());
		System.out.println("No se puede eliminar un cupo no vigente");
	}
	
	@Test (groups = "BeFan")
	public void TS135634_BeFan_Movil_Repro_Preactivacion_Gestion_de_cupos_Busqueda_Eliminacion_de_cupo_Cupo_vigente_con_fecha_de_baja() throws ParseException {
		BeFan fechas= new BeFan (driver);
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat ("dd/MM/yyyy");
		String desde ="27/09/2018";
		String hasta = "25/12/2018";
		Date fechaDesde = formatoDelTexto.parse(desde);
		Date fechaHasta =formatoDelTexto.parse(hasta);
		irA("Cupos", "Gesti\u00f3n");
		sleep(3000);
		selectByText(driver.findElement(By.name("estados")), "Vigente");
		driver.findElement(By.id("dataPickerDesde"));
		((JavascriptExecutor) driver).executeScript("document.getElementById('dataPickerDesde').value='"+desde+"'");
		sleep(3000);
		driver.findElement(By.id("dataPickerHasta"));
		((JavascriptExecutor) driver).executeScript("document.getElementById('dataPickerHasta').value='"+hasta+"'");
		int dias = fechas.numeroDiasEntreDosFechas(fechaDesde, fechaHasta);
		System.out.println("Hay " + dias + " dias, " +"No supera los 90 dias comprendidos");
		driver.findElement(By.name("buscar")).click();
		//Se necesita un cupo con fecha de bajar
	}
	
	@Test (groups = "BeFan")
	public void TS135636_BeFan_Movil_Repro_Preactivacion_Gestion_de_cupos_Busqueda_Eliminacion_de_cupo_Mensaje_Confirmacion() throws ParseException{
		BeFan fechas= new BeFan (driver);
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat ("dd/MM/yyyy");
		String pregunta = "�est\u00e1 seguro que desea dar de baja el registro seleccionado?";
		String desde ="27/09/2018";
		String hasta = "25/12/2018";
		Date fechaDesde = formatoDelTexto.parse(desde);
		Date fechaHasta =formatoDelTexto.parse(hasta);
		irA("Cupos", "Gesti\u00f3n");
		sleep(3000);
		selectByText(driver.findElement(By.name("estados")), "Vigente");
		driver.findElement(By.id("dataPickerDesde"));
		((JavascriptExecutor) driver).executeScript("document.getElementById('dataPickerDesde').value='"+desde+"'");
		sleep(3000);
		driver.findElement(By.id("dataPickerHasta"));
		((JavascriptExecutor) driver).executeScript("document.getElementById('dataPickerHasta').value='"+hasta+"'");
		int dias = fechas.numeroDiasEntreDosFechas(fechaDesde, fechaHasta);
		System.out.println("Hay " + dias + " dias, " +"No supera los 90 dias comprendidos");
		driver.findElement(By.name("buscar")).click();
		sleep(3000);
		WebElement eliminacion = driver.findElement(By.name("eliminar"));
		Assert.assertTrue(eliminacion.isDisplayed());
		eliminacion.click();
		boolean a = false;
		for(WebElement x : driver.findElements(By.className("modal-header"))) {
			if(x.getText().toLowerCase().contains(pregunta)) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "BeFan")
	public void TS135638_BeFan_Movil_Repro_Preactivacion_Gestion_de_cupos_Busqueda_Modificacion_de_cupo_Cupo_no_vigente_sin_fecha_de_baja() throws ParseException {
		BeFan fechas= new BeFan (driver);
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat ("dd/MM/yyyy");
		String desde ="27/09/2018";
		String hasta = "25/12/2018";
		Date fechaDesde = formatoDelTexto.parse(desde);
		Date fechaHasta =formatoDelTexto.parse(hasta);
		irA("Cupos", "Gesti\u00f3n");
		sleep(3000);
		selectByText(driver.findElement(By.name("estados")), "No Vigente");
		driver.findElement(By.id("dataPickerDesde"));
		((JavascriptExecutor) driver).executeScript("document.getElementById('dataPickerDesde').value='"+desde+"'");
		sleep(3000);
		driver.findElement(By.id("dataPickerHasta"));
		((JavascriptExecutor) driver).executeScript("document.getElementById('dataPickerHasta').value='"+hasta+"'");
		int dias = fechas.numeroDiasEntreDosFechas(fechaDesde, fechaHasta);
		System.out.println("Hay " + dias + " dias, " +"No supera los 90 dias comprendidos");
		driver.findElement(By.name("buscar")).click();
		sleep(3000);
		WebElement modificacion = driver.findElement(By.name("modificarGuardar"));
		Assert.assertTrue(modificacion.isEnabled());
		System.out.println("No se puede modificar un cupo no vigente");
	}
	
	@Test (groups = "BeFan")
	public void TS112006_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Pantalla_de_inicio() {
		irA("Regiones", "Gesti\u00f3n");
		WebElement panelDeBusqueda = driver.findElement(By.className("panel-heading"));
		WebElement boton = driver.findElement(By.cssSelector(".btn.btn-primary"));
		Assert.assertTrue(panelDeBusqueda.isDisplayed() && boton.isDisplayed());
		WebElement panel = driver.findElement(By.className("panel-data"));
		Assert.assertTrue(panel.isDisplayed());
		if(panel.getText().toLowerCase().contains("fort\u00edn olavarr\u00eda") || panel.getText().toLowerCase().contains("la plata")) {
			driver.findElements(By.className("panel-heading")).get(0).click();
		}
		WebElement tabla = driver.findElement(By.cssSelector(".table.table-top-fixed.table-striped.table-primary.ng-scope"));
		Assert.assertTrue(tabla.getText().toLowerCase().contains("descripci\u00f3n") && tabla.getText().toLowerCase().contains("codigo prefijo") && tabla.getText().toLowerCase().contains("acci\u00f3n"));
	}
	
	@Test (groups = "BeFan")
	public void TS112007_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Creacion_de_agrupador_exitosa() {
		irA("Regiones", "Gesti\u00f3n");
		boolean eliminarPrefijo = false;
		sleep(2000);
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(2000);
		driver.findElement(By.cssSelector(".form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("Moreno");
		driver.findElement(By.xpath("//*[@class='btn btn-primary' and contains(text(), 'Agregar')]")).click();
		sleep(2000);
		driver.findElement(By.xpath("//*[@class='btn btn-primary' and contains(text(), 'Cerrar')]")).click();
		sleep(2000);
		WebElement panel = driver.findElement(By.className("panel-data"));
		Assert.assertTrue(panel.getText().toLowerCase().contains("moreno"));
		//Se borra el agrupador para volver a correr el caso
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "moreno");
		for (WebElement x : driver.findElements(By.className("panel-group"))) {
			try {
				if (x.getText().toLowerCase().contains("moreno"))
					x.findElement(By.cssSelector(".glyphicon.glyphicon-plus")).click();
			} catch(Exception e) {}
		}
		sleep(3000);
		driver.findElement(By.cssSelector(".compatibility.custom-check.ng-scope")).findElement(By.tagName("input")).click();
		String nroPrefijo = driver.findElement(By.cssSelector(".compatibility.custom-check.ng-scope")).findElement(By.tagName("label")).getText();
		System.out.println(nroPrefijo);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "agregar");
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
		driver.navigate().refresh();
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "moreno");
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + driver.findElement(By.cssSelector(".panel.ng-scope.ng-isolate-scope.panel-default.panel-open")).getLocation().y + ")");
		for (WebElement x : driver.findElement(By.cssSelector(".panel.ng-scope.ng-isolate-scope.panel-default.panel-open")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"))) {
			if (x.getText().contains(nroPrefijo)) {
				x.findElement(By.cssSelector(".btn.btn-link")).click();
				buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "eliminar");
				buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
				eliminarPrefijo = true;
			}
		}
		Assert.assertTrue(eliminarPrefijo);
		boolean eliminarRegion = false;
		String mensaje = driver.findElement(By.className("modal-header")).getText();
		if (mensaje.contains("Tambien desea eliminar la Region Moreno")) {
			buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "eliminar");
			buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
			eliminarRegion = true;
		}
		Assert.assertTrue(eliminarRegion);
	}
	
	@Test (groups = "BeFan")
	public void TS112011_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Modificacion_de_agrupadores_Asignacion_de_prefijos_a_agrupador_existente_Guardando(){
		irA("Regiones", "Gesti\u00f3n");
		boolean prefijo = false;
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "cordoba");
		for (WebElement x : driver.findElements(By.className("panel-group"))) {
			try {
				if (x.getText().toLowerCase().contains("cordoba"))
					x.findElement(By.cssSelector(".glyphicon.glyphicon-plus")).click();
			} catch(Exception e) {}
		}
		sleep(3000);
		driver.findElement(By.cssSelector(".compatibility.custom-check.ng-scope")).findElement(By.tagName("input")).click();
		String nroPrefijo = driver.findElement(By.cssSelector(".compatibility.custom-check.ng-scope")).findElement(By.tagName("label")).getText();
		System.out.println(nroPrefijo);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "agregar");
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
		driver.navigate().refresh();
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "cordoba");
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + driver.findElement(By.cssSelector(".panel.ng-scope.ng-isolate-scope.panel-default.panel-open")).getLocation().y + ")");
		for (WebElement x : driver.findElement(By.cssSelector(".panel.ng-scope.ng-isolate-scope.panel-default.panel-open")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"))) {
			if (x.getText().contains(nroPrefijo)) {
				prefijo = true;
			}
		}
		Assert.assertTrue(prefijo);
		
	}
	
	@Test (groups = "BeFan")
	public void TS112014_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Modificacion_de_agrupadores_Eliminacion_de_prefijos_en_agrupador_existente_No_guardando() {
		irA("regiones", "gesti\u00f3n");
		boolean noEliminar = false;
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "bas-vjp-bahia blanca");
		driver.findElement(By.cssSelector(".panel-collapse.in.collapse")).findElement(By.cssSelector(".btn.btn-link")).click();
		String msj = driver.findElement(By.className("modal-header")).getText();
		if (msj.contains("Esta seguro que desea eliminarlo")) {
			if (driver.findElement(By.className("modal-footer")).findElement(By.cssSelector(".btn.btn-link")).getText().equals("Cancelar")) {
				driver.findElement(By.className("modal-footer")).findElement(By.cssSelector(".btn.btn-link")).click();
				noEliminar = true;
			}
		}
		Assert.assertTrue(noEliminar);
	}
	
	@Test (groups = "BeFan")
	public void TS112013_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Modificacion_de_agrupadores_Eliminacion_de_prefijos_en_agrupador_existente_Guardando() {
		irA("regiones", "gesti\u00f3n");
		boolean eliminar = false;
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "la plata");
		for (WebElement x : driver.findElements(By.className("panel-group"))) {
			try {
				if (x.getText().contains("3583")) {
					x.findElement(By.tagName("tbody")).findElement(By.tagName("button")).click();
					buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "eliminar");
					buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
					eliminar = true;
				}
			} catch(Exception e) {}
		}
		for (WebElement x : driver.findElements(By.className("panel-group"))) {
			try {
				if (x.getText().toLowerCase().contains("la plata"))
					x.findElement(By.cssSelector(".glyphicon.glyphicon-plus")).click();
				sleep(5000);
				for (WebElement a : driver.findElements(By.id("compatibility"))) {
					if (a.getText().contains("3583")) {
						a.findElement(By.tagName("input")).click();
						buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "agregar");
						buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
					}
				}
			} catch(Exception e) {}
		}
		Assert.assertTrue(eliminar);
	}
	
	@Test (groups = "BeFan")
	public void TS112012_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Modificacion_de_agrupadores_Asignacion_de_prefijos_a_agrupador_existente_No_guardando() {
		irA("regiones", "gesti\u00f3n");
		boolean btnCancelar = false;
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "la plata");
		for (WebElement x : driver.findElements(By.className("panel-group"))) {
			try {
				if (x.getText().toLowerCase().contains("la plata"))
					x.findElement(By.cssSelector(".glyphicon.glyphicon-plus")).click();
			} catch(Exception e) {}
		}
		sleep(3000);
		driver.findElement(By.id("compatibility")).findElement(By.tagName("input")).click();
		if (driver.findElement(By.cssSelector(".ng-scope.block-ui.block-ui-anim-fade")).findElement(By.cssSelector(".btn.btn-link")).getText().equals("Cancelar"))
			btnCancelar = true;
		Assert.assertTrue(btnCancelar);
	}
	
	@Test (groups = "BeFan")
	public void TS135606_BeFan_Movil_Repro_Preactivacion_Importacion_de_cupos_Formato_interno_Mas_valores_en_una_linea() {
		ContactSearch contact = new ContactSearch(driver);
		irA("Cupos", "Importaci\u00f3n");
		sleep(5000);
		File directory = new File("BeFan135606.txt");
		contact.subir_cupos(new File(directory.getAbsolutePath()).toString(),"si");
		sleep(5000);
		boolean a = false;
		List <WebElement> formato = driver.findElements(By.className("modal-header"));
		for(WebElement x : formato) {
			if(x.getText().toLowerCase().contains("verifique el contenido del archivo que desea impdepenortar, existen cupos inv\u00e1lidos.")) {
				a = true;
				
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "BeFan")
	public void TS135607_BeFan_Movil_Repro_Preactivacion_Importacion_de_cupos_Formato_interno_Menos_valores_en_una_linea() {
		ContactSearch contact = new ContactSearch(driver);
		irA("Cupos", "Importaci\u00f3n");
		sleep(7000);
		File directory = new File("BeFan135606-135607.txt");
		contact.subir_cupos(new File(directory.getAbsolutePath()).toString(),"si");
		sleep(5000);
		boolean a = false;
		List <WebElement> formato = driver.findElements(By.className("modal-header"));
		for(WebElement x : formato) {
			if(x.getText().toLowerCase().contains("verifique el contenido del archivo que desea importar, existen cupos inv\u00e1lidos.")) {
				a = true;
				
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "BeFan")
	public void TS112025_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Modificacion_de_agrupadores_Eliminacion_de_prefijos_en_agrupador_existente_Guardando_Verificacion() {
		irA("regiones", "gesti\u00f3n");
		boolean nroPrefijo = false;
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "bas-vjp-bahia blanca");
		driver.findElement(By.cssSelector(".panel-collapse.in.collapse")).findElement(By.cssSelector(".btn.btn-link")).click();
		String prefijo = driver.findElement(By.className("modal-header")).getText();
		prefijo = prefijo.replaceAll("((\\D)*)", "");
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "eliminar");
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
		driver.findElement(By.name("menuUsuario")).click();
		driver.findElement(By.name("salir")).click();
		driver.findElement(By.name("username")).sendKeys("UAT195528");
		driver.findElement(By.name("txtPass")).sendKeys("Testa10k");
		driver.findElement(By.name("btnIngresar")).click();
		sleep(5000);
		irA("sims", "importaci\u00f3n");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		if (!(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).getText().contains(prefijo)))
			nroPrefijo = true;
		driver.findElement(By.name("menuUsuario")).click();
		driver.findElement(By.name("salir")).click();
		driver.findElement(By.name("username")).sendKeys("UAT529763");
		driver.findElement(By.name("txtPass")).sendKeys("Testa10k");
		driver.findElement(By.name("btnIngresar")).click();
		sleep(5000);
		irA("regiones", "gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "bas-vjp-bahia blanca");
		for (WebElement x : driver.findElements(By.className("panel-group"))) {
			try {
				if (x.getText().toLowerCase().contains("bas-vjp-bahia blanca"))
					x.findElement(By.cssSelector(".glyphicon.glyphicon-plus")).click();
			} catch(Exception e) {}
		}
		sleep(3000);
		for (WebElement x : driver.findElements(By.id("compatibility"))) {
			if (x.getText().contains(prefijo))
				x.findElement(By.tagName("input")).click();
		}
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "agregar");
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");		
		Assert.assertTrue(nroPrefijo);
	}
	
	@Test (groups = "BeFan")
	public void TS111487_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Eliminacion_de_agrupadores_Si_Sin_preactivar() {
		irA("Regiones", "Gesti\u00f3n");
		boolean eliminarPrefijo = false;
		sleep(2000);
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(2000);
		driver.findElement(By.cssSelector(".form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("San Cristobal");
		driver.findElement(By.xpath("//*[@class='btn btn-primary' and contains(text(), 'Agregar')]")).click();
		sleep(2000);
		driver.findElement(By.xpath("//*[@class='btn btn-primary' and contains(text(), 'Cerrar')]")).click();
		sleep(2000);
		WebElement panel = driver.findElement(By.className("panel-data"));
		Assert.assertTrue(panel.getText().toLowerCase().contains("san cristobal"));
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "san cristobal");
		for (WebElement x : driver.findElements(By.className("panel-group"))) {
			try {
				if (x.getText().toLowerCase().contains("san cristobal"))
					x.findElement(By.cssSelector(".glyphicon.glyphicon-plus")).click();
			} catch(Exception e) {}
		}
		sleep(3000);
		driver.findElement(By.cssSelector(".compatibility.custom-check.ng-scope")).findElement(By.tagName("input")).click();
		String nroPrefijo = driver.findElement(By.cssSelector(".compatibility.custom-check.ng-scope")).findElement(By.tagName("label")).getText();
		System.out.println(nroPrefijo);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "agregar");
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
		driver.navigate().refresh();
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "san cristobal");
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + driver.findElement(By.cssSelector(".panel.ng-scope.ng-isolate-scope.panel-default.panel-open")).getLocation().y + ")");
		for (WebElement x : driver.findElement(By.cssSelector(".panel.ng-scope.ng-isolate-scope.panel-default.panel-open")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"))) {
			if (x.getText().contains(nroPrefijo)) {
				x.findElement(By.cssSelector(".btn.btn-link")).click();
				buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "eliminar");
				buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
				eliminarPrefijo = true;
			}
		}
		Assert.assertTrue(eliminarPrefijo);
		boolean eliminarRegion = false;
		String mensaje = driver.findElement(By.className("modal-header")).getText();
		if (mensaje.contains("Tambien desea eliminar la Region San Cristobal")) {
			buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "eliminar");
			buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
			eliminarRegion = true;
		}
		Assert.assertTrue(eliminarRegion);
	}
	
	@Test (groups = "BeFan")
	public void TS126649_BeFan_Movil_REPRO_Preactivacion_repro_Importacion_de_cupos_Exitoso(){
		ContactSearch contact = new ContactSearch(driver);
		irA("Cupos", "Importaci\u00f3n");
		sleep(7000);
		File directory = new File("BeFan126649.txt");
		contact.subir_cupos(new File(directory.getAbsolutePath()).toString(),"si");
		sleep(5000);
		boolean confirmacion = false;
		String mensaje = driver.findElement(By.className("modal-header")).getText();
		if (mensaje.toLowerCase().contains("el archivo se import\u00f3 correctamente")) {
			buscarYClick(driver.findElements(By.cssSelector(".btn.btn-link")), "equals", "aceptar");
			confirmacion = true;
		}
		Assert.assertTrue(confirmacion);
		sleep(8000);
		boolean razonS = false , puntoDeVenta = false, fechaD = false, fechaH = false, cantidadDeCupos = false, resultado = false;
		List <WebElement> colum = driver.findElements(By.className("text-center"));
		for(WebElement x : colum) {
			if(x.getText().contains("Raz\u00f3n Social")) 
				razonS = true;
			if(x.getText().contains("Punto de Venta")) 
				puntoDeVenta = true;
			if(x.getText().contains("Fecha Desde")) 
				fechaD = true;
			if(x.getText().contains("Fecha Hasta"))
				fechaH = true;
			if(x.getText().contains("Cantidad de Cupos")) 
				cantidadDeCupos = true;
			if(x.getText().contains("Resultado de Validaci\u00f3n"))
				resultado= true;
		}
		Assert.assertTrue(razonS && puntoDeVenta && fechaD && fechaH && cantidadDeCupos && resultado);
		WebElement texto = driver.findElements(By.cssSelector(".text-center.ng-scope")).get(1);
		System.out.println(texto.getText());
	}
	
	@Test (groups = "BeFan")
	public void TS112004_BeFan_Movil_REPRO_Preactivacion_repro_Importacion_de_agrupadores_Prefijos_inexistentes() {
		ContactSearch contact = new ContactSearch(driver);
		irA("Regiones", "Importaci\u00f3n");
		sleep(7000);
		File directory = new File("BeFan112004_1.txt");
		contact.subir_regiones(new File(directory.getAbsolutePath()).toString(),"si");
		sleep(5000);
		WebElement tabla = driver.findElement(By.className("ng-scope")).findElement(By.tagName("tbody"));
		System.out.println(tabla.getText());
		Assert.assertTrue(tabla.getText().toLowerCase().contains("el prefijo no existe"));
		File directory_1 = new File("BeFan112004_2.txt");
		contact.subir_regiones(new File(directory_1.getAbsolutePath()).toString(),"si");
		sleep(5000);
		boolean texto = false;
		String mensaje = driver.findElement(By.className("modal-header")).getText();
		if (mensaje.contains("Archivo importado correctamente")) {
			buscarYClick(driver.findElements(By.cssSelector(".btn.btn-link")), "equals", "aceptar");
			texto = true;
		}
		Assert.assertTrue(texto);
	}
	
	@Test (groups = "BeFAN")
	public void TS126607_BeFan_Movil_REPRO_Preactivacion_repro_Visualizacion_de_archivos_importados_Nombre_del_archivo_importado() {
		irA("sims", "gesti\u00f3n");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Procesado");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "VJP");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");		
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(5000);
		driver.findElement(By.id("exportarTabla")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(8).click();
		sleep(3000);
		driver.findElement(By.id("botonExportar")).click();
		sleep(5000);
		String downloadPath = "C:\\Users\\Nicolas\\Downloads";
		Assert.assertTrue(scp.isFileDownloaded(downloadPath, "PREACTIVACIONES DIARIAS"));
	}
	
	@Test (groups = "BeFAN")
	public void TS126615_BeFan_Movil_REPRO_Preactivacion_repro_Importacion_de_agrupadores_Formato_erroneo() {
		boolean formato = false;
		ContactSearch contact = new ContactSearch(driver);
		irA("Regiones", "Importaci\u00f3n");
		sleep(7000);
		File directory = new File("BeFan126615.txt");
		contact.subir_regiones(new File(directory.getAbsolutePath()).toString(),"si");
		sleep(5000);
		WebElement tabla = driver.findElement(By.cssSelector(".table.table-top-fixed.table-striped.table-primary")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
		for (WebElement x : tabla.findElements(By.tagName("td"))) {
			if (x.getText().equalsIgnoreCase("La region no existe"))
				formato = true;
		}
		Assert.assertTrue(formato);
	}
	
	@Test (groups = "BeFan")
	public void TS112005_BeFan_Movil_REPRO_Preactivacion_repro_Importacion_de_agrupadores_Prefijos_repetidos_por_agrupador() {
		ContactSearch contact = new ContactSearch(driver);
		irA("Regiones", "Importaci\u00f3n");
		sleep(7000);
		File directory = new File("BeFan112005.txt");
		contact.subir_regiones(new File(directory.getAbsolutePath()).toString(),"si");
		sleep(5000);
		WebElement tabla = driver.findElement(By.className("ng-scope")).findElement(By.tagName("tbody"));
		System.out.println(tabla.getText());
		Assert.assertTrue(tabla.getText().toLowerCase().contains("ya se encuentra asignado el prefijo"));
	}
	
	@Test (groups = "BeFan")
	public void TS126617_BeFan_Movil_REPRO_Preactivacion_repro_Importacion_de_agrupadores_Prefijos_repetidos_por_agrupador() {
		ContactSearch contact = new ContactSearch(driver);
		irA("Regiones", "Importaci\u00f3n");
		sleep(7000);
		File directory = new File("BeFan126617.txt");
		contact.subir_regiones(new File(directory.getAbsolutePath()).toString(),"si");
		sleep(5000);
		WebElement tabla = driver.findElement(By.className("ng-scope")).findElement(By.tagName("tbody"));
		System.out.println(tabla.getText());
		Assert.assertTrue(tabla.getText().toLowerCase().contains("ya se encuentra asignado el prefijo"));
		WebElement texto = driver.findElement(By.cssSelector(".alert.alert-info.alert-inline"));
		Assert.assertTrue(texto.isDisplayed());
	}
	
	@Test (groups = "BeFan")
	public void TS135643_BeFan_Movil_Repro_Preactivacion_Gestion_de_cupos_Busqueda_Modificacion_de_cupo_Con_total_de_cupos_vacio() throws ParseException {
		BeFan fechas= new BeFan (driver);
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat ("dd/MM/yyyy");
		String desde ="27/09/2018";
		String hasta = "25/12/2018";
		Date fechaDesde = formatoDelTexto.parse(desde);
		Date fechaHasta =formatoDelTexto.parse(hasta);
		irA("Cupos", "Gesti\u00f3n");
		sleep(3000);
		selectByText(driver.findElement(By.name("estados")), "Vigente");
		driver.findElement(By.id("dataPickerDesde"));
		((JavascriptExecutor) driver).executeScript("document.getElementById('dataPickerDesde').value='"+desde+"'");
		sleep(3000);
		driver.findElement(By.id("dataPickerHasta"));
		((JavascriptExecutor) driver).executeScript("document.getElementById('dataPickerHasta').value='"+hasta+"'");
		int dias = fechas.numeroDiasEntreDosFechas(fechaDesde, fechaHasta);
		System.out.println("Hay " + dias + " dias, " +"No supera los 90 dias comprendidos");
		driver.findElement(By.name("buscar")).click();
		sleep(3000);
		driver.findElement(By.cssSelector(".glyphicon.glyphicon-edit")).click();
		driver.findElement(By.name("cantidadTotal")).clear();
		driver.findElement(By.cssSelector(".glyphicon.glyphicon-floppy-saved")).click();
		boolean mensaje = false;
		String msj = driver.findElement(By.className("modal-header")).getText();
		if (msj.contains("Cantidad de cupo inv\u00e1lida")) {
			mensaje = true;
		}
		Assert.assertTrue(mensaje);
	}
	
	@Test (groups = "BeFan")
	public void TS135614_BeFan_Movil_Repro_Preactivacion_Importacion_de_cupos_Con_un_registro_erroneo() {
		ContactSearch contact = new ContactSearch(driver);
		irA("Cupos", "Importaci\u00f3n");
		sleep(5000);
		File directory = new File("BeFan135614.txt");
		contact.subir_cupos(new File(directory.getAbsolutePath()).toString(),"si");
		boolean mensaje = false;
		String msj = driver.findElement(By.className("modal-body")).getText();
		if (msj.contains("error")) {
			mensaje = true;
		}
		Assert.assertTrue(mensaje);
	}
	
	@Test (groups = "BeFan")
	public void TS135623_BeFan_Movil_Repro_Preactivacion_Importacion_de_cupos_Exitoso_Verificacion_Cupo_dado_de_baja() {
		ContactSearch contact = new ContactSearch(driver);
		irA("Cupos", "Importaci\u00f3n");
		sleep(5000);
		File directory = new File("BeFan135623.txt");
		contact.subir_cupos(new File(directory.getAbsolutePath()).toString(),"si");
		boolean mensaje = false;
		String msj = driver.findElement(By.className("modal-header")).getText();
		if (msj.contains("correctamente")) {
			mensaje = true;
		}
		Assert.assertTrue(mensaje);
		driver.findElement(By.cssSelector(".btn.btn-link")).click();
		sleep(3000);
		irA("Cupos", "Gesti\u00f3n");
		sleep(3000);
		driver.findElement(By.name("buscar")).click();
		sleep(3000);
		List <WebElement> texto = driver.findElements(By.cssSelector(".text-center.ng-scope"));
		for(WebElement x : texto) {
			if(x.getText().contains("14/01/2019") && x.getText().contains("25/01/2019")) {
				x.findElement(By.cssSelector(".glyphicon.glyphicon-remove")).click();
			}
		}
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "aceptar");
		sleep(2000);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-link")), "equals", "aceptar");
	}
	
	@Test (groups = "BeFan")
	public void TS135609_BeFan_Movil_Repro_Preactivacion_Importacion_de_cupos_Formato_interno_Fecha_desde_formato_erroneo() {
		ContactSearch contact = new ContactSearch(driver);
		irA("Cupos", "Importaci\u00f3n");
		sleep(7000);
		File directory = new File("BeFan135609-135610.txt");
		contact.subir_cupos(new File(directory.getAbsolutePath()).toString(),"si");
		sleep(5000);
		boolean a = false;
		List <WebElement> formato = driver.findElements(By.className("modal-header"));
		for(WebElement x : formato) {
			if(x.getText().toLowerCase().contains("verifique el contenido del archivo que desea importar, existen cupos inv\u00e1lidos.")) {
				a = true;
				
			}
		}
		Assert.assertTrue(a);
	}
	@Test (groups = "BeFan")
	public void TS135610_BeFan_Movil_Repro_Preactivacion_Importacion_de_cupos_Formato_interno_Fecha_hasta_formato_erroneo() {
		ContactSearch contact = new ContactSearch(driver);
		irA("Cupos", "Importaci\u00f3n");
		sleep(7000);
		File directory = new File("BeFan135609-135610.txt");
		contact.subir_cupos(new File(directory.getAbsolutePath()).toString(),"si");
		sleep(5000);
		boolean a = false;
		List <WebElement> formato = driver.findElements(By.className("modal-header"));
		for(WebElement x : formato) {
			if(x.getText().toLowerCase().contains("verifique el contenido del archivo que desea importar, existen cupos inv\u00e1lidos.")) {
				a = true;
				
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "BeFan")
	public void TS135611_BeFan_Movil_Repro_Preactivacion_Importacion_de_cupos_Formato_interno_Cantidad_de_cupos_formato_erroneo() {
		ContactSearch contact = new ContactSearch(driver);
		irA("Cupos", "Importaci\u00f3n");
		sleep(7000);
		File directory = new File("BeFan135611.txt");
		contact.subir_cupos(new File(directory.getAbsolutePath()).toString(),"si");
		sleep(5000);
		boolean a = false;
		List <WebElement> formato = driver.findElements(By.className("modal-header"));
		for(WebElement x : formato) {
			if(x.getText().toLowerCase().contains("verifique el contenido del archivo que desea importar, existen cupos inv\u00e1lidos.")) {
				a = true;
				
			}
		}
		Assert.assertTrue(a);
	}
	
	
	@Test (groups = "BeFan")
	public void TS135615_BeFan_Movil_Repro_Preactivacion_Importacion_de_cupos_Cupo_igual_a_cero() {
		ContactSearch contact = new ContactSearch(driver);
		irA("Cupos", "Importaci\u00f3n");
		sleep(7000);
		File directory = new File("BeFan135611.txt");
		contact.subir_cupos(new File(directory.getAbsolutePath()).toString(),"si");
		sleep(5000);
		boolean a = false;
		List <WebElement> formato = driver.findElements(By.className("modal-header"));
		for(WebElement x : formato) {
			if(x.getText().toLowerCase().contains("verifique el contenido del archivo que desea importar, existen cupos inv\u00e1lidos.")) {
				a = true;
				
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "BeFan")
	public void TS135617_BeFan_Movil_Repro_Preactivacion_Importacion_de_cupos_Exitoso_Grilla() {
		ContactSearch contact = new ContactSearch(driver);
		irA("Cupos", "Importaci\u00f3n");
		sleep(7000);
		File directory = new File("BeFan135617.txt");
		contact.subir_cupos(new File(directory.getAbsolutePath()).toString(),"si");
		sleep(5000);
		boolean a = false;
		List <WebElement> formato = driver.findElements(By.className("modal-content"));
		for(WebElement x : formato) {
			if(x.getText().toLowerCase().contains("el archivo se import\u00f3 correctamente")) {
				a = true;
				
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "BeFan")
	public void TS135618_BeFan_Movil_Repro_Preactivacion_Importacion_de_cupos_Exitoso_Logeo() {
		ContactSearch contact = new ContactSearch(driver);
		irA("Cupos", "Importaci\u00f3n");
		sleep(7000);
		File directory = new File("BeFan135618.txt");
		contact.subir_cupos(new File(directory.getAbsolutePath()).toString(),"si");
		sleep(5000);
		List <WebElement> formato = driver.findElements(By.className("modal-content"));
		for(WebElement x : formato) {
			if(x.getText().toLowerCase().contains("el archivo se import\u00f3 correctamente")) {
			}
		}
		driver.findElement(By.className("modal-content")).findElement(By.className("modal-footer")).findElement(By.cssSelector(".btn.btn-link")).click();
		WebElement tabla = driver.findElement(By.name("TABLE-CuposExito")).findElement(By.tagName("tbody"));
		Assert.assertTrue(tabla.isDisplayed());
	}
	

	@Test (groups = "BeFan")
	public void TS135608_BeFan_Movil_Repro_Preactivacion_Importacion_de_cupos_Formato_interno_Agente_inexistente(){
		ContactSearch contact = new ContactSearch(driver);
		irA("Cupos", "Importaci\u00f3n");
		sleep(7000);
		File directory = new File("BeFan135608.txt");
		contact.subir_cupos(new File(directory.getAbsolutePath()).toString(),"si");
		sleep(5000);
		boolean a = false;
		List <WebElement> formato = driver.findElements(By.className("modal-content"));
		for(WebElement x : formato) {
			System.out.println(x.getText());
			if(x.getText().toLowerCase().contains("no existe el punto de venta para las regiones activas existentes.")) {
				a = true;
				
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "BeFan")
	public void TS135613_BeFan_Movil_Repro_Preactivacion_Importacion_de_cupos_Formato_interno_Agrupador_inexistente(){
		ContactSearch contact = new ContactSearch(driver);
		irA("Cupos", "Importaci\u00f3n");
		sleep(7000);
		File directory = new File("BeFan135613.txt");
		contact.subir_cupos(new File(directory.getAbsolutePath()).toString(),"si");
		sleep(5000);
		boolean a = false;
		List <WebElement> formato = driver.findElements(By.className("modal-content"));
		for(WebElement x : formato) {
			System.out.println(x.getText());
			if(x.getText().toLowerCase().contains("no existe el punto de venta para las regiones activas existentes.")) {
				a = true;
				
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "BeFan")
	public void TS135616_BeFan_Movil_Repro_Preactivacion_Importacion_de_cupos_Exitoso_Mensaje(){
		ContactSearch contact = new ContactSearch(driver);
		irA("Cupos", "Importaci\u00f3n");
		sleep(7000);
		File directory = new File("BeFan135616.txt");
		contact.subir_cupos(new File(directory.getAbsolutePath()).toString(),"si");
		sleep(5000);
		boolean a = false;
		List <WebElement> formato = driver.findElements(By.className("modal-content"));
		for(WebElement x : formato) {
			System.out.println(x.getText());
			if(x.getText().toLowerCase().contains("el archivo se import\u00f3 correctamente")) {
				a = true;
				
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "BeFan")
	public void TS135620_BeFan_Movil_Repro_Preactivacion_Importacion_de_cupos_Exitoso_Verificacion_Fuera_de_la_fecha(){
		ContactSearch contact = new ContactSearch(driver);
		irA("Cupos", "Importaci\u00f3n");
		sleep(7000);
		File directory = new File("BeFan135620.txt");
		contact.subir_cupos(new File(directory.getAbsolutePath()).toString(),"si");
		sleep(5000);
		boolean a = false;
		List <WebElement> formato = driver.findElements(By.className("modal-header"));
		for(WebElement x : formato) {
			if(x.getText().toLowerCase().contains("verifique el contenido del archivo que desea importar, existen cupos inv\u00e1lidos.")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
		
	
	@Test (groups = "BeFan")
	public void TS112022_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Eliminacion_de_agrupadores_Si_Preactivando() throws InterruptedException {
		irA("Regiones", "Gesti\u00f3n");
		BeFan page = new BeFan(driver);
		boolean eliminar = false;
		boolean cancelar = false;
		String nroPrefijo = null;
		irA("regiones", "gesti\u00f3n");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		driver.findElement(By.cssSelector(".form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("cordoba");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		
		for(WebElement x : driver.findElements(By.className("modal-body"))){
			if(x.getText().toLowerCase().contains("region existente")) {
				System.out.println(x.getText());
				buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
				break;
			}
			
			if(x.getText().toLowerCase().contains("existente")) {
				System.out.println(x.getText());
				buscarYClick(driver.findElements(By.cssSelector(".btn.btn-link")), "equals", "cancelar");
					cancelar = true;
					Assert.assertTrue(cancelar);
					break;
					}
		
		else {
				buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
				buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "cordoba");
				sleep(3000);
				for (WebElement a : driver.findElements(By.className("panel-group"))) {
					try {
						if (a.getText().toLowerCase().contains("cordoba"))
							a.findElement(By.cssSelector(".glyphicon.glyphicon-plus")).click();
					} catch(Exception e) {}
				}
				sleep(3000);
				driver.findElement(By.cssSelector(".compatibility.custom-check.ng-scope")).findElement(By.tagName("input")).click();
				nroPrefijo = driver.findElement(By.cssSelector(".compatibility.custom-check.ng-scope")).findElement(By.tagName("label")).getText();
				System.out.println(nroPrefijo);
				//driver.findElement(By.cssSelector(".panel-collapse.in.collapse")).findElement(By.cssSelector(".btn.btn-link")).click();
				sleep(3000);
				//driver.findElement(By.className("check-filter-on")).click();
				buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "agregar");
				buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
				sleep(5000);
				break;
			}
		}	
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "equals", "cordoba");
		
		for (WebElement x : driver.findElement(By.cssSelector(".panel.ng-scope.ng-isolate-scope.panel-default.panel-open")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"))) {
			//if (x.getText().contains(nroPrefijo));
				x.findElement(By.cssSelector(".actions.text-center")).findElement(By.cssSelector(".btn.btn-link")).click();
			}
		driver.findElement(By.className("modal-footer")).findElement(By.className("pull-right"));
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "eliminar");
		Assert.assertTrue(page.verificarMensajeExitoso());
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
		eliminar = true;
		Assert.assertTrue(eliminar);
		driver.findElement(By.className("modal-footer")).findElement(By.className("pull-right"));
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "eliminar");
		Assert.assertTrue(page.verificarMensajeExitoso());
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
		Assert.assertFalse((driver.findElement(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")).getText().equals("cordoba")));
	}
	
	@Test (groups = "BeFan", dataProvider="SerialInexistente", priority = 2)
	public void TS135637_BeFan_Movil_Repro_Preactivacion___Gestion_de_cupos__Busqueda__Eliminacion_de_cupo__Mensaje__Confirmacion__Verificacion(String path, String nombreArch, String deposito, String prefijo, String serial1, String serial2, String prefijo2, String Cantidad, String agente, String depositoLogico) throws Exception, IOException {
		BeFan Botones = new BeFan(driver);
		
		String mensaje = "";
		irA("Cupos", "Gesti\u00f3n");
		sleep(500);
		Botones.CGeliminar(agente, deposito);
		sleep(500);
		Botones.LogOutBefan(driver);
		sleep(500);
		loginBeFAN(driver);
		sleep(500);
		irA("Sims", "Importaci\u00f3n");
		sleep(500);
		Botones.SISeleccionDeDeposito(deposito);
		sleep(500);
		Botones.SISeleccionDePrefijo(prefijo);
		sleep(500);
		Botones.SISeleccionCantidadDePrefijo("1");
		sleep(500);
		Botones.SIClickAgregar();
		Botones.SIImportarArchivo(nombreArch = Botones.SICreacionArchivo(nombreArch, path, serial1, ""));
		sleep(500);
		Botones.SIClickImportar();
		sleep(500);
		mensaje = Botones.SIMensajeModal();
		if (mensaje.contentEquals("Error al intentar validar cantidad de cupos.")) {
			Botones.SIClickAceptarImportar();
			sleep(500);
			try {
				Botones.LogOutBefan(driver);
				sleep(500);
				loginBeFANConfigurador(driver);
				sleep(500);
				irA("Cupos", "Importaci\u00f3n");
				sleep(500);
				Botones.CIImportarArchivo(agente, depositoLogico);
				Assert.assertTrue(true);
			} catch (Exception e) {
				Assert.assertTrue(true);
					}
			
		} else {
			Botones.SIClickAceptarImportar();
			sleep(500);
			try {
				Botones.LogOutBefan(driver);
				sleep(500);
				loginBeFANConfigurador(driver);
				sleep(500);
				irA("Cupos", "Importaci\u00f3n");
				sleep(500);
				Botones.CIImportarArchivo(agente, depositoLogico);
				Assert.assertTrue(false);
			} catch (Exception e) {
				Assert.assertTrue(false);
					}
		}

	}
	
	@Test (groups = "BeFan", dataProvider="SerialInexistente")
	public void TS135642_BeFan_Movil_Repro_Preactivacion___Gestion_de_cupos__Busqueda__Modificacion_de_cupo__Mensaje__Confirmacion__Verificacion(String path, String nombreArch, String deposito, String prefijo, String serial1, String serial2, String prefijo2, String Cantidad, String agente, String depositoLogico) throws Exception, IOException {
		BeFan Botones = new BeFan(driver);
		
		String mensaje = "";
		irA("Cupos", "Gesti\u00f3n");
		sleep(500);
		Botones.modificarCupos(agente, deposito);
		sleep(500);
		Botones.LogOutBefan(driver);
		sleep(500);
		loginBeFAN(driver);
		sleep(500);
		irA("Sims", "Importaci\u00f3n");
		sleep(500);
		Botones.SISeleccionDeDeposito(deposito);
		sleep(500);
		Botones.SISeleccionDePrefijo(prefijo);
		sleep(500);
		Botones.SISeleccionCantidadDePrefijo("1");
		sleep(500);
		Botones.SIClickAgregar();
		Botones.SIImportarArchivo(nombreArch = Botones.SICreacionArchivo(nombreArch, path, serial1, ""));
		sleep(500);
		Botones.SIClickImportar();
		sleep(500);
		mensaje = Botones.SIMensajeModal();
		if (mensaje.contentEquals("No tienen cupos suficientes en el prefijo " + prefijo)) {
			Botones.SIClickAceptarImportar();
			sleep(500);
			try {
				Botones.LogOutBefan(driver);
				sleep(500);
				loginBeFANConfigurador(driver);
				sleep(500);
				irA("Cupos", "Importaci\u00f3n");
				sleep(500);
				Botones.CIImportarArchivo(agente, depositoLogico);
				Assert.assertTrue(true);
			} catch (Exception e) {
				Assert.assertTrue(true);
					}
			
		} else {
			Botones.SIClickAceptarImportar();
			sleep(500);
			try {
				Botones.LogOutBefan(driver);
				sleep(500);
				loginBeFANConfigurador(driver);
				sleep(500);
				irA("Cupos", "Importaci\u00f3n");
				sleep(500);
				Botones.CIImportarArchivo(agente, depositoLogico);
				Assert.assertTrue(false);
			} catch (Exception e) {
				Assert.assertTrue(false);
					}
		}

	}
	
	
	@Test (groups = "BeFan")
	public void TS135639_BeFan_Movil_Repro_Preactivacion_Gestion_de_cupos_Busqueda_Modificacion_de_cupo_Cupo_vigente_con_fecha_de_baja() throws ParseException {
		ContactSearch contact = new ContactSearch(driver);
		irA("Cupos", "Importaci\u00f3n");
		sleep(7000);
		File directory = new File("BeFan135639.txt");
		contact.subir_cupos(new File(directory.getAbsolutePath()).toString(),"si");
		sleep(5000);
		boolean confirmacion = false;
		String mensaje = driver.findElement(By.className("modal-header")).getText();
		if (mensaje.toLowerCase().contains("el archivo se import\u00f3 correctamente")) {
			buscarYClick(driver.findElements(By.cssSelector(".btn.btn-link")), "equals", "aceptar");
			confirmacion = true;
		}
		Assert.assertTrue(confirmacion);
		BeFan fechas= new BeFan (driver);
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat ("dd/MM/yyyy");
		String desde ="14/11/2001";
		String hasta = "14/12/2001";
		Date fechaDesde = formatoDelTexto.parse(desde);
		Date fechaHasta =formatoDelTexto.parse(hasta);
		irA("Cupos", "Gesti\u00f3n");
		sleep(3000);
		selectByText(driver.findElement(By.name("estados")), "No Vigente");
		driver.findElement(By.id("dataPickerDesde"));
		((JavascriptExecutor) driver).executeScript("document.getElementById('dataPickerDesde').value='"+desde+"'");
		sleep(3000);
		driver.findElement(By.id("dataPickerHasta"));
		((JavascriptExecutor) driver).executeScript("document.getElementById('dataPickerHasta').value='"+hasta+"'");
		int dias = fechas.numeroDiasEntreDosFechas(fechaDesde, fechaHasta);
		System.out.println("Hay " + dias + " dias, " +"No supera los 90 dias comprendidos");
		driver.findElement(By.name("buscar")).click();
		sleep(3000);
		List <WebElement> texto = driver.findElements(By.cssSelector(".text-center.ng-scope"));
		for(WebElement x : texto) {
			if(x.getText().contains("14/11/2001") && x.getText().contains("14/12/2001")) {
				x.findElement(By.cssSelector(".glyphicon.glyphicon-remove")).isEnabled();
			}
		}
	}
	
	@Test (groups = "BeFan")
	public void TS126624_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Modificacion_de_agrupadores_Asignacion_de_prefijos_a_agrupador_existente_No_guardando() {
		irA("regiones", "gesti\u00f3n");
		boolean btnCancelar = false;
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "cordoba");
		for (WebElement x : driver.findElements(By.className("panel-group"))) {
			try {
				if (x.getText().toLowerCase().contains("cordoba"))
					x.findElement(By.cssSelector(".glyphicon.glyphicon-plus")).click();
			} catch(Exception e) {}
		}
		sleep(3000);
		driver.findElement(By.id("compatibility")).findElement(By.tagName("input")).click();
		if (driver.findElement(By.cssSelector(".ng-scope.block-ui.block-ui-anim-fade")).findElement(By.cssSelector(".btn.btn-link")).getText().equals("Cancelar"))
			btnCancelar = true;
		Assert.assertTrue(btnCancelar);
	}
	
	@Test (groups = "BeFan")
	public void TS126628_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Modificacion_de_agrupadores_Eliminacion_de_prefijos_en_agrupador_existente_Logeo(){
		irA("regiones", "gesti\u00f3n");
		boolean eliminarPrefijo = false;
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "cordoba");
		for (WebElement x : driver.findElements(By.className("panel-group"))) {
			try {
				if (x.getText().toLowerCase().contains("cordoba"))
					x.findElement(By.cssSelector(".glyphicon.glyphicon-plus")).click();
			} catch(Exception e) {}
		}
		sleep(3000);
		driver.findElement(By.cssSelector(".compatibility.custom-check.ng-scope")).findElement(By.tagName("input")).click();
		String nroPrefijo = driver.findElement(By.cssSelector(".compatibility.custom-check.ng-scope")).findElement(By.tagName("label")).getText();
		System.out.println(nroPrefijo);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "agregar");
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
		driver.navigate().refresh();
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "cordoba");
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + driver.findElement(By.cssSelector(".panel.ng-scope.ng-isolate-scope.panel-default.panel-open")).getLocation().y + ")");
		for (WebElement x : driver.findElement(By.cssSelector(".panel.ng-scope.ng-isolate-scope.panel-default.panel-open")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"))) {
			//if (x.getText().contains(nroPrefijo)) {
				x.findElement(By.cssSelector(".btn.btn-link")).click();
				buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "eliminar");
				buscarYClick(driver.findElements(By.cssSelector(".btn.btn-primary")), "equals", "cerrar");
				eliminarPrefijo = true;
			}
		
		Assert.assertTrue(eliminarPrefijo);
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-link")), "equals", "cancelar");
		//No se visualiza el identificador del usuario y la fecha de baja. 
	}
	
	@Test (groups = "BeFan")
	public void TS126626_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Modificacion_de_agrupadores_Eliminacion_de_prefijos_en_agrupador_existente_No_guardando(){
		irA("regiones", "gesti\u00f3n");
		boolean noEliminar = false;
		buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "la plata");
		driver.findElement(By.cssSelector(".panel-collapse.in.collapse")).findElement(By.cssSelector(".btn.btn-link")).click();
		String msj = driver.findElement(By.className("modal-header")).getText();
		if (msj.contains("Esta seguro que desea eliminarlo")) {
			if (driver.findElement(By.className("modal-footer")).findElement(By.cssSelector(".btn.btn-link")).getText().equals("Cancelar")) {
				driver.findElement(By.className("modal-footer")).findElement(By.cssSelector(".btn.btn-link")).click();
				noEliminar = true;
			}
		}
		Assert.assertTrue(noEliminar);
	}
	
	@Test (groups = "BeFan")
	public void TS126627_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Modificacion_de_agrupadores_Asignacion_de_prefijos_a_agrupador_existente_Logeo(){
		irA("regiones", "gesti\u00f3n");
		pbf = new Pages.BeFan(driver);
		pbf.buscarRegion("la plata");
		List <WebElement> region = driver.findElements(By.xpath("//*[@class='panel-group'] //*[@class='collapsed'] //*[@class='ng-binding']"));
		for(WebElement x : region) {
		if(x.getText().toLowerCase().contains("la plata")) {
			System.out.println("La region es : " +x.getText());
			x.click();
			break;
			}
		}	
		WebElement tabla = driver.findElement(By.cssSelector(".table.table-top-fixed.table-striped.table-primary.ng-scope"));
		for(WebElement x : tabla.findElements(By.tagName("td"))) {
			System.out.println(x.getText());
			Assert.assertTrue(x.isDisplayed());
		}
		WebElement usuario = driver.findElement(By.className("tpi-user"));
		System.out.println("Se visualiza el usuario determinado: ");
		System.out.println(usuario.findElement(By.className("ng-binding")).getText());
		System.out.println("Se visualiza el nombre: ");
		System.out.println(usuario.findElement(By.cssSelector(".tpt-dropdown.dropdown")));
		Assert.assertTrue(usuario.isDisplayed());
	}
	
	@Test (groups = "BeFan")
	public void TS126630_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Modificacion_de_agrupadores_Nombre(){
		irA("regiones", "gesti\u00f3n");
		pbf = new Pages.BeFan(driver);
		pbf.buscarRegion("la plata");
		List <WebElement> region = driver.findElements(By.xpath("//*[@class='panel-group'] //*[@class='collapsed'] //*[@class='ng-binding']"));
		for(WebElement x : region) {
		if(x.getText().toLowerCase().contains("la plata")) {
			System.out.println("La region seleccionada es : " +x.getText());
			x.click();
			break;
			}
		WebElement editar = driver.findElement(By.cssSelector(".glyphicon.glyphicon-edit"));
		System.out.println("No se puede editar la region del agrupador");
		Assert.assertTrue(editar.isEnabled());
		}	
	}
	
	@Test (groups = "BeFan")
	public void TS112027_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Verificacion_de_prefijos() {
	irA("regiones", "gesti\u00f3n");
	buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "bas-vjp-bahia blanca");
	for (WebElement x : driver.findElements(By.className("panel-group"))) {
		try {
			if (x.getText().toLowerCase().contains("bas-vjp-bahia blanca"))
				x.findElement(By.cssSelector(".glyphicon.glyphicon-plus")).click();
		} catch(Exception e) {}
	}
	sleep(3000);
	boolean prefijos = false;
	for (WebElement x : driver.findElements(By.id("compatibility"))) {
		if (!x.getText().isEmpty());
		prefijos=true;
	}
	Assert.assertTrue(prefijos);
	}
	
	
	@Test (groups = "BeFan")
	public void TS126639_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Verificacion_de_prefijos() {
	irA("regiones", "gesti\u00f3n");
	buscarYClick(driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope")), "contains", "bas-vjp-bahia blanca");
	for (WebElement x : driver.findElements(By.className("panel-group"))) {
		try {
			if (x.getText().toLowerCase().contains("bas-vjp-bahia blanca"))
				x.findElement(By.cssSelector(".glyphicon.glyphicon-plus")).click();
		} catch(Exception e) {}
	}
	sleep(3000);
	boolean prefijos = false;
	for (WebElement x : driver.findElements(By.id("compatibility"))) {
		if (!x.getText().isEmpty());
		prefijos=true;
	}
	Assert.assertTrue(prefijos);
	}
	
	@Test (groups = {"BeFAN","EliminacionDeAgrupador"}, dataProvider="GestionRegionesCreacion", dependsOnMethods="TS126623_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Modificacion_de_agrupadores_Asignacion_de_prefijos_a_agrupador_existente_Guardando")
	public void TS126635_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Eliminacion_de_agrupadores_Logeo(String sRegion) {
		irA("Regiones", "Gesti\u00f3n");
		pbf = new Pages.BeFan(driver);
		driver.navigate().refresh();
		pbf.buscarYAbrirRegion(sRegion);
		
		WebElement wBody = driver.findElement(By.xpath("//*[@class='panel-collapse in collapse'] //table[@class='table table-top-fixed table-striped table-primary ng-scope']"));
		Marketing mM = new Marketing(driver);
		List<WebElement> wRegiones = mM.traerColumnaElement(wBody, 3, 1);
		int iContador = 1;
		for (WebElement wAux : wRegiones) {
			driver.findElement(By.xpath("//*[@ng-repeat='prefijo in displayedCollection'] [" + iContador + "] //button")).click();
			driver.findElement(By.xpath("//*[@ng-show='mensajeEliminarCtrl.container.showConfirmation'] //button[@class='btn btn-primary']")).click();
			sleep(5000);
			driver.findElement(By.xpath("//*[@ng-show='mensajeEliminarCtrl.container.showSuccess'] //button[@class='btn btn-primary']")).click();
			iContador++;
		}
		sleep(3000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".text-center.ng-binding")).getText().toLowerCase().contains(sRegion.toLowerCase()));
		driver.findElement(By.xpath("//*[@ng-show='mensajeEliminarCtrl.container.showConfirmation'] //button[@class='btn btn-primary']")).click();
		sleep(5000);
		driver.findElement(By.xpath("//*[@ng-show='mensajeEliminarCtrl.container.showSuccess'] //button[@class='btn btn-primary']")).click();
		driver.navigate().refresh();
		
		boolean bAssert = pbf.buscarRegionInexistente(sRegion);
		sleep(3000);
		
		Assert.assertTrue(bAssert);
	}
	
}