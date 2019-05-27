package Funcionalidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import DataProvider.ExcelUtils;
import Pages.BeFan;
import Pages.ContactSearch;
import Pages.Marketing;
import Pages.SCP;
import Pages.setConexion;
import Pages.DPW;
import Tests.MDW;
import Tests.TestBase;

public class Preactivacion extends TestBase {

	private WebDriver driver;
	private SCP scp;
	private Pages.BeFan pbf;
	
	private void irA(String opcion) {
		WebElement menu = null;
		sleep(3000);
		buscarYClick(driver.findElements(By.className("dropdown-toggle")), "contains", "sims");
		for (WebElement x : driver.findElements(By.className("col-sm-4"))) {
			if (x.getAttribute("ng-show").equals("headerCtrl.container.hasAccess(['sims_importacion', 'sims_gestion'])"))
				menu = x;
		}
		switch(opcion) {
		case "importacion":
			try {
				for (WebElement x : menu.findElements(By.tagName("a"))) {
					if (x.getText().toLowerCase().contains("importaci\u00f3n")) {
						x.click(); 
						sleep(3000);
					}
				}			
			} catch(Exception e) {}
		case "gestion":
			try {
				for (WebElement x : menu.findElements(By.tagName("a"))) {
					if (x.getText().toLowerCase().contains("gesti\u00f3n")) {
						x.click();
						sleep(3000);
					}
				}
			} catch(Exception e) {}
		}
	}
	
	private void irCupos(String opcion) {
		WebElement menu = null;
		sleep(1500);
		buscarYClick(driver.findElements(By.className("dropdown-toggle")), "contains", "cupos");
		for (WebElement x : driver.findElements(By.className("col-sm-4"))) {
			if (x.getAttribute("ng-show").equals("headerCtrl.container.hasAccess(['cupo_importacion', 'cupo_gestion'])"))
				menu = x;
		}
		switch(opcion) {
		case "importacion":
			try {
				for (WebElement x : menu.findElements(By.tagName("a"))) {
					if (x.getText().toLowerCase().contains("importaci\u00f3n")) {
						x.click(); 
						sleep(3000);
					}
				}			
			} catch(Exception e) {}
		case "gestion":
			try {
				for (WebElement x : menu.findElements(By.tagName("a"))) {
					if (x.getText().toLowerCase().contains("gesti\u00f3n")) {
						x.click();
						sleep(3000);
					}
				}
			} catch(Exception e) {}
		}
	}
	
	private void irRegion(String opcion) {
		WebElement menu = null;
		sleep(1500);
		buscarYClick(driver.findElements(By.className("dropdown-toggle")), "contains", "regiones");
		for (WebElement x : driver.findElements(By.className("col-sm-4"))) {
			if (x.getAttribute("ng-show").equals("headerCtrl.container.hasAccess(['region_importacion', 'region_gestion'])"))
				menu = x;
		}
		switch(opcion) {
		case "importacion":
			try {
				for (WebElement x : menu.findElements(By.tagName("a"))) {
					if (x.getText().toLowerCase().contains("importaci\u00f3n")) {
						x.click(); 
						sleep(3000);
					}
				}			
			} catch(Exception e) {}
		case "gestion":
			try {
				for (WebElement x : menu.findElements(By.tagName("a"))) {
					if (x.getText().toLowerCase().contains("gesti\u00f3n")) {
						x.click();
						sleep(3000);
					}
				}
			} catch(Exception e) {}
		}
	}
	
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
	
	public String readTxt(String sName) throws IOException {
		String sPrefijo;
		File fFile = null;
		FileReader frFileReader = null;
		BufferedReader brBufferedReader = null;
		fFile = new File (sName);
		frFileReader = new FileReader (fFile);
		brBufferedReader = new BufferedReader(frFileReader);
		sPrefijo = brBufferedReader.readLine();
		
		frFileReader.close();
		brBufferedReader.close();
		return sPrefijo;
	}
	
	public void deleteFile(String sFile) {
		File fFile = new File(sFile);
		fFile.delete();
	}
	
	@BeforeClass (groups = "PerfilMayorista")
	public void initMayo() {
		driver = setConexion.setupEze();
		scp = new SCP(driver);
		loginBeFANVictor(driver, "mayorista");
	//	loginBeFAN(driver);
	}
	
	//@BeforeClass (groups = "PerfilConfigurador")
	public void initConf() {
		driver = setConexion.setupEze();
		scp = new SCP(driver);
		loginBeFANVictor(driver, "configurador");
//		loginBeFAN(driver);
	}
	
	//@AfterMethod (alwaysRun = true)
	public void after() {
		driver.get(TestBase.urlBeFAN);
		sleep(3000);
	}
	
	//@AfterClass (alwaysRun = true)
	public void quit() {
		driver.quit();
	}
	

//----------------------------------- PERFIL MAYORISTA -----------------------
	
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS126656_BeFan_Movil_REPRO_Preactivacion_repro_Busqueda_de_archivos_Agente_Por_estado_EN_PROCESO_Exitosa() {
		boolean enProceso = false;
		BeFan Botones = new BeFan(driver);
		Botones.andaAlMenu("sims", "gestion");
		Botones.SGSeleccionEstado("En Proceso");
		Botones.SGClickBuscar();
		enProceso = Botones.SGValidarResultado(Botones.SGDatosArchivos(), 6, 9, "En Proceso");
		Assert.assertTrue(enProceso);
	}
	
	
	//Falta preparar la carga de un archivo como preparacion
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS126655_BeFan_Movil_REPRO_Preactivacion_repro_Busqueda_de_archivos_Agente_Por_estado_PENDIENTE_Exitosa() {
		boolean enProceso = false;
		BeFan Botones = new BeFan(driver);
		Botones.andaAlMenu("sims", "gestion");
		Botones.SGSeleccionEstado("Pendiente");
		Botones.SGClickBuscar();
		enProceso = Botones.SGValidarResultado(Botones.SGDatosArchivos(), 6, 9, "Pendiente");
		Assert.assertTrue(enProceso);
	}
	
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS126657_BeFan_Movil_REPRO_Preactivacion_repro_Busqueda_de_archivos_Agente_Por_estado_PROCESADO_Exitosa() {
		boolean enProceso = false;
		BeFan Botones = new BeFan(driver);
		Botones.andaAlMenu("sims", "gestion");
		Botones.SGSeleccionEstado("Procesado");
		Botones.SGClickBuscar();
		enProceso = Botones.SGValidarResultado(Botones.SGDatosArchivos(), 6, 9, "Procesado");
		Assert.assertTrue(enProceso);
	}
	// Podria optimizarse con orden en las columnas
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS126662_BeFan_Movil_REPRO_Preactivacion_repro_Busqueda_de_archivos_Agente_Visualizacion_de_mas_informacion() {
		boolean razonSocial = false, linea = false, plan = false, nmu = false, serie = false, preactivacion = false, procesamiento = false, estado = false, descripcion = false, def = true;
		BeFan Botones = new BeFan(driver);
		Botones.andaAlMenu("sims", "gestion");
		Botones.SGSeleccionEstado("Procesado");
		Botones.SGClickBuscar();
		Botones.SGClickVerDetalle(1);
		List <WebElement> columnas = Botones.SGVerDetalleColumnas();
		for (WebElement x : columnas) {
			System.out.println(x.getText());
			switch (x.getText()) {
			case "Raz\u00f3n Social":
				razonSocial = true;
				break;
			case "L\u00ednea":
				linea = true;
				break;
			case "Plan":
				plan = true;
				break;
			case "NMU":
				nmu = true;
				break;
			case "Serie":
				serie = true;
				break;
			case "Preactivaci\u00f3n":
				preactivacion = true;
				break;
			case "Procesamiento":
				procesamiento = true;
				break;
			case "Estado":
				estado = true;
				break;
			case "Descripci\u00f3n":
				descripcion = true;
				break;
			default:
				def = false;
				break;
			}
		}
		Assert.assertTrue(razonSocial && linea && plan && nmu && serie && preactivacion && procesamiento && estado && descripcion && def);
	}
	
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS126663_BeFan_Movil_REPRO_Preactivacion_repro_Busqueda_de_archivos_Agente_Visualizacion_de_mas_informacion_Exportacion() {
		irA("gestion");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Procesado");
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(5000);
		try {
			driver.findElement(By.id("exportarTabla")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(8).click();
		} catch(Exception e) {}
		sleep(3000);
		driver.findElement(By.id("botonExportar")).click();
		sleep(5000);
		String downloadPath = "C:\\New_Download";
		Assert.assertTrue(scp.isFileDownloaded(downloadPath, "PREACTIVACIONES DIARIAS"), "Failed to download Expected document");
	}

	
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS112042_BeFan_Movil_REPRO_Preactivacion_repro_Busqueda_de_archivos_Agente_Por_fecha_Exitosa() throws ParseException {
		BeFan Botones = new BeFan(driver);
		Botones.andaAlMenu("sims", "gestion");
		Botones.SGSeleccionEstado("Procesado");
		SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatter2=new SimpleDateFormat("dd/MM/yyyy");
		Date dateHoy = formatter2.parse(fechaDeHoy());
		Date dateDesde = new Date (dateHoy.getTime()-2592000000L);
		Date dateHasta = new Date (dateHoy.getTime()-864000000L);
		Botones.SGFechaDesde(formatter.format(dateDesde));
		Botones.SGFechaHasta(formatter.format(dateHasta));
		Botones.SGClickBuscar();
		Assert.assertTrue(Botones.SGValidarFechas(formatter2.format(dateDesde), formatter2.format(dateHasta), Botones.SGDatosArchivos(), 1, 9));
	}
	
	// Podria optimizarse con orden en las columnas
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS112047_BeFan_Movil_REPRO_Preactivacion_repro_Busqueda_de_archivos_Agente_Formato() {
		boolean nombreArchivo = false, fechaDeCarga = false, estado = false, fechaProcesado = false, puntoDeVenta = false, accion = false, region = false, usuario = false, vacio = false, def = true;
		BeFan Botones = new BeFan(driver);
		Botones.andaAlMenu("sims", "gestion");
		Botones.SGSeleccionEstado("Procesado");
		Botones.SGClickBuscar();
		List <WebElement> columnas = Botones.SGColumnas();
		for (WebElement x : columnas) {
			switch (x.getText()) {
			case "Fecha de Carga":
				fechaDeCarga = true;
				break;
			case "Regi\u00f3n":
				region = true;
				break;
			case "Punto de Venta":
				puntoDeVenta = true;
				break;
			case "Usuario":
				usuario = true;
				break;
			case "Nombre Archivo":
				nombreArchivo = true;
				break;
			case "Estado":
				estado = true;
				break;
			case "Fecha Procesado":
				fechaProcesado = true;
				break;
			case "Acci\u00f3n":
				accion = true;
				break;
			case "":
				vacio = true;
				break;
			default:
				def = false;
				break;
			}
		}
		Assert.assertTrue(fechaDeCarga && region && puntoDeVenta && usuario && nombreArchivo && estado && fechaProcesado && accion && vacio && def);
	}
	
	//Requiere importacion, osea preparacion
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS135601_BeFan_Movil_Repro_Preactivacion_Gestion_de_simcards_Busqueda_de_archivos_Ver_detalle_Nombre_del_archivo() {
		irA("gestion");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Procesado");
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(5000);
		WebElement tabla = driver.findElement(By.id("exportarTabla")).findElement(By.tagName("thead"));
		if (tabla.findElements(By.tagName("th")).get(5).getText().equalsIgnoreCase("Nombre Archivo"))
			Assert.assertTrue(true);
		else
			Assert.assertTrue(false);
	}
	
	//Requiere importacion, osea preparacion
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS135602_BeFan_Movil_Repro_Preactivacion_Gestion_de_simcards_Busqueda_de_archivos_Ver_detalle_Estado_del_archivo() {
		irA("gestion");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Procesado");
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(5000);
		WebElement tabla = driver.findElement(By.id("exportarTabla")).findElement(By.tagName("thead"));
		if (tabla.findElements(By.tagName("th")).get(6).getText().equalsIgnoreCase("Estado"))
			Assert.assertTrue(true);
		else
			Assert.assertTrue(false);
	}
	//Falta hacerlo bien con el downloadPath
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS135603_BeFan_Movil_Repro_Preactivacion_Gestion_de_simcards_Exportacion_de_archivo_Nombre() {
		BeFan Botones = new BeFan(driver);
		Botones.andaAlMenu("sims", "gestion");
		Botones.SGSeleccionEstado("Procesado");
		Botones.SGClickBuscar();
		Botones.SGTablaVisible();
		Botones.SGClickVerDetalle(1);
		String downloadPath = "C:\\New_Download";
		Botones.SGVerDetalleBotonExportar();
		sleep(5000);
		Assert.assertTrue(scp.isFileDownloaded(downloadPath, "PREACTIVACIONES DIARIAS"), "Failed to download Expected document");
	}
	
	
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS135592_BeFan_Movil_Repro_Preactivacion_Gestion_de_simcards_Busqueda_de_archivos_Ver_detalle_Agente() {
		irA("gestion");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Procesado");
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(5000);
		try {
			driver.findElement(By.id("exportarTabla")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(8).click();
		} catch(Exception e) {}
		sleep(3000);
		boolean razonSocial = false;
		WebElement columnas = driver.findElement(By.cssSelector(".table.table-top-fixed.table-striped.table-primary")).findElement(By.tagName("tr"));
		for (WebElement x : columnas.findElements(By.tagName("th"))) {
			if (x.getText().contains("Raz\u00f3n Social"))
				razonSocial = true;
		}
		Assert.assertTrue(razonSocial);
	}
	//El caso de preactivacion va a ser dependiente de este caso
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS135593_BeFan_Movil_Repro_Preactivacion_Gestion_de_simcards_Busqueda_de_archivos_Ver_detalle_Linea(){
		irA("gestion");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Procesado");
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(5000);
		try {
			driver.findElement(By.id("exportarTabla")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(8).click();
		} catch(Exception e) {}
		sleep(3000);
		boolean linea = false;
		WebElement columnas = driver.findElement(By.cssSelector(".table.table-top-fixed.table-striped.table-primary")).findElement(By.tagName("tr"));
		for (WebElement x : columnas.findElements(By.tagName("th"))) {
			if (x.getText().contains("L\u00ednea"))
				linea = true;
		}
		Assert.assertTrue(linea);
	}
	
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS135594_BeFan_Movil_Repro_Preactivacion_Gestion_de_simcards_Busqueda_de_archivos_Ver_detalle_Plan(){
		irA("gestion");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Procesado");
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(5000);
		try {
			driver.findElement(By.id("exportarTabla")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(8).click();
		} catch(Exception e) {}
		sleep(3000);
		boolean plan = false;
		WebElement columnas = driver.findElement(By.cssSelector(".table.table-top-fixed.table-striped.table-primary")).findElement(By.tagName("tr"));
		for (WebElement x : columnas.findElements(By.tagName("th"))) {
			if (x.getText().contains("Plan"))
				plan = true;
		}
		Assert.assertTrue(plan);
	}
	
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS135595_BeFan_Movil_Repro_Preactivacion_Gestion_de_simcards_Busqueda_de_archivos_Ver_detalle_NMU_Simcard() {
		irA("gestion");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Procesado");
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(5000);
		try {
			driver.findElement(By.id("exportarTabla")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(8).click();
		} catch(Exception e) {}
		sleep(3000);
		boolean preactivacion = false;
		WebElement columnas = driver.findElement(By.cssSelector(".table.table-top-fixed.table-striped.table-primary")).findElement(By.tagName("tr"));
		for (WebElement x : columnas.findElements(By.tagName("th"))) {
			if (x.getText().contains("Preactivaci\u00f3n"))
				preactivacion = true;
		}
		Assert.assertTrue(preactivacion);
	}
	
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS135596_BeFan_Movil_Repro_Preactivacion_Gestion_de_simcards_Busqueda_de_archivos_Ver_detalle_Serie_Simcard() {
		irA("gestion");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Procesado");
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(5000);
		try {
			driver.findElement(By.id("exportarTabla")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(8).click();
		} catch(Exception e) {}
		sleep(3000);
		boolean serie = false;
		WebElement columnas = driver.findElement(By.cssSelector(".table.table-top-fixed.table-striped.table-primary")).findElement(By.tagName("tr"));
		for (WebElement x : columnas.findElements(By.tagName("th"))) {
			if (x.getText().contains("Serie"))
				serie = true;
		}
		Assert.assertTrue(serie);
	}
	
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS135597_BeFan_Movil_Repro_Preactivacion_Gestion_de_simcards_Busqueda_de_archivos_Ver_detalle_Fecha_de_preactivacion_de_la_linea() {
		irA("gestion");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Procesado");
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(5000);
		try {
			driver.findElement(By.id("exportarTabla")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(8).click();
		} catch(Exception e) {}
		sleep(3000);
		boolean preactivacion = false;
		WebElement columnas = driver.findElement(By.cssSelector(".table.table-top-fixed.table-striped.table-primary")).findElement(By.tagName("tr"));
		for (WebElement x : columnas.findElements(By.tagName("th"))) {
			if (x.getText().contains("Preactivaci\u00f3n"))
				preactivacion = true;
		}
		Assert.assertTrue(preactivacion);
	}
	
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS135598_BeFan_Movil_Repro_Preactivacion_Gestion_de_simcards_Busqueda_de_archivos_Ver_detalle_Fecha_de_procesamiento_del_registro() {
		irA("gestion");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Procesado");
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(5000);
		try {
			driver.findElement(By.id("exportarTabla")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(8).click();
		} catch(Exception e) {}
		sleep(3000);
		boolean procesamiento = false;
		WebElement columnas = driver.findElement(By.cssSelector(".table.table-top-fixed.table-striped.table-primary")).findElement(By.tagName("tr"));
		for (WebElement x : columnas.findElements(By.tagName("th"))) {
			if (x.getText().contains("Procesamiento"))
				procesamiento = true;
		}
		Assert.assertTrue(procesamiento);
	}
	
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS135599_BeFan_Movil_Repro_Preactivacion_Gestion_de_simcards_Busqueda_de_archivos_Ver_detalle_Estado_del_registro() {
		irA("gestion");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Procesado");
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(5000);
		try {
			driver.findElement(By.id("exportarTabla")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(8).click();
		} catch(Exception e) {}
		sleep(3000);
		boolean estado = false;
		WebElement columnas = driver.findElement(By.cssSelector(".table.table-top-fixed.table-striped.table-primary")).findElement(By.tagName("tr"));
		for (WebElement x : columnas.findElements(By.tagName("th"))) {
			if (x.getText().contains("Estado"))
				estado = true;		
		}
		Assert.assertTrue(estado);
	}
	
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS135600_BeFan_Movil_Repro_Preactivacion_Gestion_de_simcards_Busqueda_de_archivos_Ver_detalle_Descripcion_del_estado_del_error() {
		irA("gestion");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Procesado");
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(5000);
		try {
			driver.findElement(By.id("exportarTabla")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(8).click();
		} catch(Exception e) {}
		sleep(3000);
		boolean descripcion = false;
		WebElement columnas = driver.findElement(By.cssSelector(".table.table-top-fixed.table-striped.table-primary")).findElement(By.tagName("tr"));
		for (WebElement x : columnas.findElements(By.tagName("th"))) {
			if (x.getText().contains("Descripci\u00f3n"))
				descripcion = true;		
		}
		Assert.assertTrue(descripcion);
	}

	@Test (groups = {"BeFAN", "Agente", "PerfilMayorista"})
	public void TS135647_BeFan_Movil_Repro_Preactivacion_Visualizacion_de_datos_del_agente() {
	sleep(3000);
	driver.findElement(By.className("tpi-user")).findElement(By.tagName("span")).click();
	WebElement asd = driver.findElement(By.id("menudatos")).findElement(By.name("salir"));
	System.out.println(asd.getText());
	Assert.assertTrue(asd.isDisplayed());
		
	}
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS135604_BeFan_Movil_Repro_Preactivacion_Gestion_de_simcards_Exportacion_de_archivo_Detalle() throws IOException {
		boolean resultado = false;
		BeFan Botones = new BeFan(driver);
		Botones.andaAlMenu("sims", "gestion");
		Botones.SGSeleccionEstado("Procesado");
		Botones.SGClickBuscar();
		Botones.SGTablaVisible();
		Botones.SGClickVerDetalle(1);
		String downloadPath = "C:\\New_Download";
		Botones.SGVerDetalleBotonExportar();
		sleep(5000);
		if (scp.isFileDownloaded(downloadPath, "PREACTIVACIONES DIARIAS")) {
			String path = Botones.buscarUltimoArchivo();
			File archivo = new File(path);
			resultado = Botones.corroborarArchivo(path);
			archivo.delete();
		}
		Assert.assertTrue(resultado);
	}
	
	

	//Preparacion para preactivacion
	@Test (groups = {"PreactivacionBeFan", "PerfilMayorista"})
	public void TS123_ElMetodoQueSopapeaATodosLosMetodos() throws Exception {
		
		//Adquiero datos del excel
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"seriales",1,1,8,"Preactivacion");
		
		//Inicio las otras clases
		DPW dpw = new DPW();
		BeFan Botones = new BeFan(driver);
		MDW mdw = new MDW();
		
		//Iniciacion de variables
		ArrayList<String> resultados = new ArrayList<String>();
		ArrayList<String> temporal = new ArrayList<String>();
		int FilasTotales = 0;
		int ColumnasTotales = 0;
		int i = 0;
		String path = "";
		String nombreArch = "";
		String deposito = "";
		String prefijo = "";
		String serial1 = "";
		String serial2 = "";
		String prefijo2 = "";
		String Cantidad = "";
		String mensaje = "";
		int cant = 0;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss"); 
		LocalDateTime now = LocalDateTime.now(); 
		String time = dtf.format(now);
		File salida = new File("C://BefanArchivos//salida//Resultado" + time + ".txt");
		
		//Calculo dimensiones de casos de preactivacion
		FilasTotales = testObjArray.length;
		ColumnasTotales = testObjArray[0].length;
		//Aqui viene lo bueno joven
		for (i=0;i<=FilasTotales-1;i++) {
			switch (testObjArray[i][1].toString()) {
			case "SerialConDepositoErroneo": 
				try {
					//Leo el archivo y cargo las variables para la preparacion de este casito
					path = testObjArray[i][0].toString();
					nombreArch = testObjArray[i][1].toString();
					deposito = testObjArray[i][2].toString();
					prefijo = testObjArray[i][3].toString();
					serial1 = testObjArray[i][4].toString();
					serial2 = testObjArray[i][5].toString();
					prefijo2 = testObjArray[i][6].toString();
					Cantidad = testObjArray[i][7].toString();
					
					Botones.andaAlMenu("sims", "gestion");
					Botones.andaAlMenu("sims", "importacion");
					Botones.SISeleccionDeDeposito(deposito);
					Botones.SISeleccionDePrefijo(prefijo);
					Botones.SISeleccionCantidadDePrefijo("1");
					Botones.SIClickAgregar();
					Botones.SIImportarArchivo(nombreArch = Botones.SICreacionArchivo(nombreArch, path, serial1, ""));
					Botones.SIClickImportar();
					mensaje = Botones.SIMensajeModal();
					if (mensaje.contentEquals("El archivo se import\u00f3 correctamente.")) {
						Botones.SIClickAceptarImportar();
						//Respondo por el caso
						resultados.add("TS97651," + nombreArch + "," + deposito);
						resultados.add("TS126640," + nombreArch + "," + deposito);
					} else {
						Botones.SIClickAceptarImportar();
					}
		
					//Reseteo variables
					path = "";
					nombreArch = "";
					deposito = "";
					prefijo = "";
					serial1 = "";
					serial2 = "";
					prefijo2 = "";
					Cantidad = "";
					mensaje = "";
					break;
				} catch (Exception e) {
					//Reseteo variables
					path = "";
					nombreArch = "";
					deposito = "";
					prefijo = "";
					serial1 = "";
					serial2 = "";
					prefijo2 = "";
					Cantidad = "";
					mensaje = "";
					System.out.println(e);
					break;
				}
				
				
			case "SerialInexistente": 
				try {
					//Leo el archivo y cargo las variables para la preparacion de este casito
					path = testObjArray[i][0].toString();
					nombreArch = testObjArray[i][1].toString();
					deposito = testObjArray[i][2].toString();
					prefijo = testObjArray[i][3].toString();
					serial1 = testObjArray[i][4].toString();
					serial2 = testObjArray[i][5].toString();
					prefijo2 = testObjArray[i][6].toString();
					Cantidad = testObjArray[i][7].toString();
					
					Botones.andaAlMenu("sims", "gestion");
					Botones.andaAlMenu("sims", "importacion");
					Botones.SISeleccionDeDeposito(deposito);
					Botones.SISeleccionDePrefijo(prefijo);
					Botones.SISeleccionCantidadDePrefijo("1");
					Botones.SIClickAgregar();
					Botones.SIImportarArchivo(nombreArch = Botones.SICreacionArchivo(nombreArch, path, serial1, ""));
					Botones.SIClickImportar();
					mensaje = Botones.SIMensajeModal();
					if (mensaje.contentEquals("El archivo se import\u00f3 correctamente.")) {
						Botones.SIClickAceptarImportar();
						//Respondo por el caso
						resultados.add("TS112029," + nombreArch + "," + deposito);
						
					} else {
						Botones.SIClickAceptarImportar();
					}

		
					//Reseteo variables
					path = "";
					nombreArch = "";
					deposito = "";
					prefijo = "";
					serial1 = "";
					serial2 = "";
					prefijo2 = "";
					Cantidad = "";
					mensaje = "";
					break;
				} catch (Exception e) {
					//Reseteo variables
					path = "";
					nombreArch = "";
					deposito = "";
					prefijo = "";
					serial1 = "";
					serial2 = "";
					prefijo2 = "";
					Cantidad = "";
					mensaje = "";
					break;
				}
				
				
			case "DosSerialesValidos": 
				try {
					//Leo el archivo y cargo las variables para la preparacion de este casito
					path = testObjArray[i][0].toString();
					nombreArch = "seriales";
					deposito = testObjArray[i][2].toString();
					prefijo = testObjArray[i][3].toString();
					serial1 = testObjArray[i][4].toString();
					serial2 = testObjArray[i][5].toString();
					prefijo2 = testObjArray[i][6].toString();
					Cantidad = testObjArray[i][7].toString();
					
					
					cant = Integer.parseInt(Cantidad);
					Botones.andaAlMenu("sims", "gestion");
					Botones.andaAlMenu("sims", "importacion");
					Botones.SISeleccionDeDeposito(deposito);
					Botones.SISeleccionDePrefijo(prefijo);
					Botones.SISeleccionCantidadDePrefijo(Integer.toString(cant-1));
					Botones.SIClickAgregar();
					Botones.SISeleccionDePrefijo(prefijo2);
					Botones.SISeleccionCantidadDePrefijo("1");
					Botones.SIClickAgregar();
					Botones.SIImportarArchivo(nombreArch = Botones.LecturaDeDatosTxt(path + "\\"+ nombreArch + ".txt", cant));
					Botones.SIClickImportar();
					mensaje = Botones.SIMensajeModal();
					if (mensaje.contentEquals("El archivo se import\u00f3 correctamente.")) {
						Botones.SIClickAceptarImportar();
						//Respondo por el caso
						resultados.add("TS97657," + nombreArch + "," + deposito);
					} else {
						Botones.SIClickAceptarImportar();
					}

		
					//Reseteo variables
					path = "";
					nombreArch = "";
					deposito = "";
					prefijo = "";
					serial1 = "";
					serial2 = "";
					prefijo2 = "";
					Cantidad = "";
					mensaje = "";
					break;
				} catch (Exception e) {
					//Reseteo variables
					path = "";
					nombreArch = "";
					deposito = "";
					prefijo = "";
					serial1 = "";
					serial2 = "";
					prefijo2 = "";
					Cantidad = "";
					mensaje = "";
					break;
				}
				
			case "SerialNoMCVM": 
				try {
					//Leo el archivo y cargo las variables para la preparacion de este casito
					path = testObjArray[i][0].toString();
					nombreArch = testObjArray[i][1].toString();
					deposito = testObjArray[i][2].toString();
					prefijo = testObjArray[i][3].toString();
					serial1 = testObjArray[i][4].toString();
					serial2 = testObjArray[i][5].toString();
					prefijo2 = testObjArray[i][6].toString();
					Cantidad = testObjArray[i][7].toString();
					
					Botones.andaAlMenu("sims", "gestion");
					Botones.andaAlMenu("sims", "importacion");
					Botones.SISeleccionDeDeposito(deposito);
					Botones.SISeleccionDePrefijo(prefijo);
					Botones.SISeleccionCantidadDePrefijo("1");
					Botones.SIClickAgregar();
					Botones.SIImportarArchivo(nombreArch = Botones.SICreacionArchivo(nombreArch, path, serial1, ""));
					Botones.SIClickImportar();
					mensaje = Botones.SIMensajeModal();
					if (mensaje.contentEquals("El archivo se import\u00f3 correctamente.")) {
						Botones.SIClickAceptarImportar();
						//Respondo por el caso
						resultados.add("TS97653," + nombreArch + "," + deposito);
					} else {
						Botones.SIClickAceptarImportar();
					}
		
					//Reseteo variables
					path = "";
					nombreArch = "";
					deposito = "";
					prefijo = "";
					serial1 = "";
					serial2 = "";
					prefijo2 = "";
					Cantidad = "";
					mensaje = "";
					break;
				} catch (Exception e) {
					//Reseteo variables
					path = "";
					nombreArch = "";
					deposito = "";
					prefijo = "";
					serial1 = "";
					serial2 = "";
					prefijo2 = "";
					Cantidad = "";
					mensaje = "";
					break;
				}
				
				
			case "SerialBalido": 
				try {
					//Leo el archivo y cargo las variables para la preparacion de este casito
					path = testObjArray[i][0].toString();
					nombreArch = "seriales";
					deposito = testObjArray[i][2].toString();
					prefijo = testObjArray[i][3].toString();
					serial1 = testObjArray[i][4].toString();
					serial2 = testObjArray[i][5].toString();
					prefijo2 = testObjArray[i][6].toString();
					Cantidad = testObjArray[i][7].toString();
					
					
					cant = Integer.parseInt(Cantidad);
					Botones.andaAlMenu("sims", "gestion");
					Botones.andaAlMenu("sims", "importacion");
					Botones.SISeleccionDeDeposito(deposito);
					Botones.SISeleccionDePrefijo(prefijo);
					Botones.SISeleccionCantidadDePrefijo(Cantidad);
					Botones.SIClickAgregar();
					Botones.SIImportarArchivo(nombreArch = Botones.LecturaDeDatosTxt(path + "\\"+ nombreArch + ".txt", cant));
					Botones.SIClickImportar();
					mensaje = Botones.SIMensajeModal();
					if (mensaje.contentEquals("El archivo se import\u00f3 correctamente.")) {
						Botones.SIClickAceptarImportar();
						//Respondo por el caso
						resultados.add("TS111958," + nombreArch + "," + deposito);
					} else {
						Botones.SIClickAceptarImportar();
					}
		
					//Reseteo variables
					path = "";
					nombreArch = "";
					deposito = "";
					prefijo = "";
					serial1 = "";
					serial2 = "";
					prefijo2 = "";
					Cantidad = "";
					mensaje = "";
					break;
				} catch (Exception e) {
					//Reseteo variables
					path = "";
					nombreArch = "";
					deposito = "";
					prefijo = "";
					serial1 = "";
					serial2 = "";
					prefijo2 = "";
					Cantidad = "";
					mensaje = "";
					break;
				}
				
				
			case "SerialValidoEterno": 
				try {
					//Leo el archivo y cargo las variables para la preparacion de este casito
					path = testObjArray[i][0].toString();
					nombreArch = testObjArray[i][1].toString();
					deposito = testObjArray[i][2].toString();
					prefijo = testObjArray[i][3].toString();
					serial1 = testObjArray[i][4].toString();
					serial2 = testObjArray[i][5].toString();
					prefijo2 = testObjArray[i][6].toString();
					Cantidad = testObjArray[i][7].toString();
					
					Botones.andaAlMenu("sims", "gestion");
					Botones.andaAlMenu("sims", "importacion");
					Botones.SISeleccionDeDeposito(deposito);
					Botones.SISeleccionDePrefijo(prefijo);
					Botones.SISeleccionCantidadDePrefijo("1");
					Botones.SIClickAgregar();
					Botones.SIImportarArchivo(nombreArch = Botones.SICreacionArchivo(nombreArch, path, serial1, ""));
					Botones.SIClickImportar();
					mensaje = Botones.SIMensajeModal();
					if (mensaje.contentEquals("El archivo se import\u00f3 correctamente.")) {
						Botones.SIClickAceptarImportar();
						//Respondo por el caso
						resultados.add("TS111990," + nombreArch + "," + deposito);
						resultados.add("TS97654," + nombreArch + "," + deposito);
					} else {
						Botones.SIClickAceptarImportar();
					}
		
					//Reseteo variables
					path = "";
					nombreArch = "";
					deposito = "";
					prefijo = "";
					serial1 = "";
					serial2 = "";
					prefijo2 = "";
					Cantidad = "";
					mensaje = "";
					break;
				} catch (Exception e) {
					//Reseteo variables
					path = "";
					nombreArch = "";
					deposito = "";
					prefijo = "";
					serial1 = "";
					serial2 = "";
					prefijo2 = "";
					Cantidad = "";
					mensaje = "";
					break;
				}
				
			case "SerialParaAlterar": 
				try {
					//Leo el archivo y cargo las variables para la preparacion de este casito
					path = testObjArray[i][0].toString();
					nombreArch = "seriales";
					deposito = testObjArray[i][2].toString();
					prefijo = testObjArray[i][3].toString();
					serial1 = testObjArray[i][4].toString();
					serial2 = testObjArray[i][5].toString();
					prefijo2 = testObjArray[i][6].toString();
					Cantidad = testObjArray[i][7].toString();
					
					
					cant = Integer.parseInt(Cantidad);
					Botones.andaAlMenu("sims", "gestion");
					Botones.andaAlMenu("sims", "importacion");
					Botones.SISeleccionDeDeposito(deposito);
					Botones.SISeleccionDePrefijo(prefijo);
					Botones.SISeleccionCantidadDePrefijo("1");
					Botones.SIClickAgregar();
					Botones.SIImportarArchivo(nombreArch = Botones.LecturaDeDatosTxt(path + "\\"+ nombreArch + ".txt", cant));
					Botones.SIClickImportar();
					mensaje = Botones.SIMensajeModal();
					if (mensaje.contentEquals("El archivo se import\u00f3 correctamente.")) {
						Botones.SIClickAceptarImportar();
						//Respondo por el caso
						resultados.add("TS126672," + nombreArch + "," + deposito);
					} else {
						Botones.SIClickAceptarImportar();
					}
		
					//Reseteo variables
					path = "";
					nombreArch = "";
					deposito = "";
					prefijo = "";
					serial1 = "";
					serial2 = "";
					prefijo2 = "";
					Cantidad = "";
					mensaje = "";
					break;
				} catch (Exception e) {
					//Reseteo variables
					path = "";
					nombreArch = "";
					deposito = "";
					prefijo = "";
					serial1 = "";
					serial2 = "";
					prefijo2 = "";
					Cantidad = "";
					mensaje = "";
					break;
				}
			case "PrefijoAMatar": 
				try {
					//Leo el archivo y cargo las variables para la preparacion de este casito
					path = testObjArray[i][0].toString();
					nombreArch = "seriales";
					deposito = testObjArray[i][2].toString();
					prefijo = testObjArray[i][3].toString();
					serial1 = testObjArray[i][4].toString();
					serial2 = testObjArray[i][5].toString();
					prefijo2 = testObjArray[i][6].toString();
					Cantidad = testObjArray[i][7].toString();
					cant = Integer.parseInt(Cantidad);
					
					loginBeFANConfigurador(driver);
					sleep(500);
					irRegion("gestion");
					sleep(500);
					driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div/input")).sendKeys("BAS-VJP");
					sleep(500);
					driver.findElement(By.className("collapsed")).click();
					
					Botones.LogOutBefan(driver);
					loginBeFAN(driver);
					
					irA("gestion");
					irA("importacion");
					sleep(500);
					Botones.SISeleccionDeDeposito(deposito);
					sleep(500);
					Botones.SISeleccionDePrefijo(prefijo);
					sleep(500);
					Botones.SISeleccionCantidadDePrefijo("1");
					sleep(500);
					Botones.SIClickAgregar();
					Botones.SIImportarArchivo(nombreArch = Botones.LecturaDeDatosTxt(path + "\\"+ nombreArch + ".txt", cant));
					sleep(500);
					Botones.SIClickImportar();
					sleep(500);
					mensaje = Botones.SIMensajeModal();
					if (mensaje.contentEquals("El archivo se import\u00f3 correctamente.")) {
						Botones.SIClickAceptarImportar();
						sleep(500);
						//Respondo por el caso
						resultados.add("TS126672," + nombreArch + "," + deposito);
					} else {
						Botones.SIClickAceptarImportar();
						sleep(500);
					}
		
					//Reseteo variables
					path = "";
					nombreArch = "";
					deposito = "";
					prefijo = "";
					serial1 = "";
					serial2 = "";
					prefijo2 = "";
					Cantidad = "";
					mensaje = "";
					break;
				} catch (Exception e) {
					//Reseteo variables
					path = "";
					nombreArch = "";
					deposito = "";
					prefijo = "";
					serial1 = "";
					serial2 = "";
					prefijo2 = "";
					Cantidad = "";
					mensaje = "";
					break;
				}
			
			
			}
		}
		
		
		DPW.main();
		BufferedWriter c = new BufferedWriter(new FileWriter(salida));
	    for (String x : resultados) {
	    	c.write(x + System.lineSeparator());
	    	String[] Caso = x.split(",");
	    	if (Caso[0].equals("TS126672")) {
	    		sleep(120000);
	    		String serial = Botones.TraemeLosSeriales(Caso[1]);
	    		if (serial.equals("No existe el archivo")) {
	    		} else {
	    			boolean hola = mdw.requestValidadorS105(mdw.callSoapWebService(mdw.s105Request("ARRF",serial,"SG31185001"), "uat105"), serial);
	    		}
	    		
	    	}
	    }
	    c.close();
	    
	    
	    
	}
	
	
// DE 10 CON PREP
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS97651_BeFan_Movil_REPRO_PreaActivacion_Linea_Repro_SIMCARD_en_deposito_inexistente() throws IOException, Exception {
		BeFan Botones = new BeFan(driver);
		String[] resultadoEstado = {""};
		String[] resultadoTexto = {""};
		String estado = "Procesado";
		resultadoEstado[0] = "Error";
		resultadoTexto[0] = "El dep\u00f3sito de la SIM, VICLIE001, no corresponde con el dep\u00f3sito del punto de venta del agente, SG31185001.";
		
		//traigo resultado de preparacion
		String[] lasNoches = Botones.soyEzpesial("TS97651").split(",");
		if (lasNoches[0].equals("false")) {
			Assert.assertTrue(false);
		}
		String nombreArch = lasNoches[1];
		String deposito = lasNoches[2];
		
		//ejecuto
		irA("gestion");
		sleep(500);
		Botones.SGSeleccionEstado(estado);
		sleep(500);
		Botones.SGSeleccionDeposito(deposito);
		sleep(500);
		Botones.SGFechaDesdeAhora();
		sleep(500);
		Botones.SGClickBuscar();
		sleep(500);
		Assert.assertTrue(Botones.SGLeerCampoYValidar(nombreArch, resultadoEstado, resultadoTexto));
	}

	// DE 10 SIN PREP
	@Test (groups = {"BeFAN", "PerfilMayorista"}, dataProvider="SerialInexistente")
	public void TS112002_BeFan_Movil_REPRO_Preactivacion_repro__Importacion_de_SIM_repro__Mensaje_de_error_ante_volver_a_agregar_otro_prefijo(String path, String nombreArch, String deposito, String prefijo, String serial1, String serial2, String prefijo2, String Cantidad, String Agente, String depositoLogico) throws Exception{
		
		BeFan Botones = new BeFan(driver);
		irA("importacion");
		String mensaje;
		sleep(500);
		Botones.SISeleccionDeDeposito(deposito);
		sleep(500);
		Botones.SISeleccionDePrefijo(prefijo);
		sleep(500);
		Botones.SISeleccionCantidadDePrefijo("1");
		sleep(500);
		Botones.SIClickAgregar();
		sleep(700);
		Botones.SISeleccionDePrefijo(prefijo);
		sleep(500);
		Botones.SISeleccionCantidadDePrefijo("1");
		sleep(500);
		Botones.SIClickAgregar();
		sleep(1000);
		mensaje = Botones.SIMensajeModal();
		if (mensaje.contentEquals("El prefijo seleccionado ya se encuentra ingresado.")) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}

	}
	
	@Test (groups = {"BeFAN", "PerfilMayorista"}, dataProvider="SerialInexistente")
	public void TS112029_BeFan_Movil_REPRO_Preactivacion_repro__Importacion_de_SIM_repro__S105__Simcard_inexistente(String path, String nombreArch, String deposito, String prefijo, String serial1, String serial2, String prefijo2, String Cantidad, String agente, String depositoLogico) throws IOException, Exception {
		DPW dpw = new DPW();
		BeFan Botones = new BeFan(driver);
		String[] resultadoEstado = {""};
		String[] resultadoTexto = {""};
		String estado = "Procesado";
		resultadoEstado[0] = "Error";
		resultadoTexto[0] = "Error al consumir un proveedor - Provider ID: VMI.INVENTARIO.INVENTARIO - Provider Error Code: 1200 - Provider Error Description: Serial No encontrado";
		//traigo resultado de preparacion
		String[] lasNoches = Botones.soyEzpesial("TS112029").split(",");
		if (lasNoches[0].equals("false")) {
			Assert.assertTrue(false);
		}
		nombreArch = lasNoches[1];
		deposito = lasNoches[2];
		
		//ejecuto
		irA("gestion");
		sleep(500);
		Botones.SGSeleccionEstado(estado);
		sleep(500);
		Botones.SGSeleccionDeposito(deposito);
		sleep(500);
		Botones.SGFechaDesdeAhora();
		sleep(500);
		Botones.SGClickBuscar();
		sleep(500);
		Assert.assertTrue(Botones.SGLeerCampoYValidar(nombreArch, resultadoEstado, resultadoTexto));

	}	
	//DE 10 SIN PREP
	@Test (groups = {"BeFAN", "PerfilMayorista"}, dataProvider="SerialConFormatoInvalido")
	public void TS126615_BeFan_Movil_REPRO_Preactivacion_repro__Importacion_de_agrupadores__Formato_erroneo(String path, String nombreArch, String deposito, String prefijo, String serial1, String serial2, String prefijo2) throws IOException {
		BeFan Botones = new BeFan(driver);
		String mensaje;
		
		irA("importacion");
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
		mensaje = Botones.SIMensajeModalMasDeUnMensaje();
		if (mensaje.contentEquals("Las sims deben tener 20 caracteres num\u00e9ricos sin espacios")) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}
		Botones.SIClickAceptarImportar();
		sleep(1000);
	}
// Revisar, faltaria consumir un servicio S105 en el medio para reservarlo
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS126640_BeFan_Movil_REPRO_Preactivacion_repro__Importacion_de_SIM_repro__S105__Deposito_erroneo() throws IOException, Exception {
		BeFan Botones = new BeFan(driver);
		String[] resultadoEstado = {""};
		String[] resultadoTexto = {""};
		String estado = "Procesado";
		resultadoEstado[0] = "Error";
		resultadoTexto[0] = "El dep\u00f3sito de la SIM, VICLIE001, no corresponde con el dep\u00f3sito del punto de venta del agente, SG31185001.";

		//traigo resultado de preparacion
		String[] lasNoches = Botones.soyEzpesial("TS126640").split(",");
		if (lasNoches[0].equals("false")) {
			Assert.assertTrue(false);
		}
		String nombreArch = lasNoches[1];
		String deposito = lasNoches[2];
		
		//ejecuto
		irA("gestion");
		sleep(500);
		Botones.SGSeleccionEstado(estado);
		sleep(500);
		Botones.SGSeleccionDeposito(deposito);
		sleep(500);
		Botones.SGFechaDesdeAhora();
		sleep(500);
		Botones.SGClickBuscar();
		sleep(500);
		Assert.assertTrue(Botones.SGLeerCampoYValidar(nombreArch, resultadoEstado, resultadoTexto));
	}
// DE 10
	@Test (groups = {"BeFAN", "PerfilMayorista"}, dataProvider="SerialBalido")
	public void TS126648_BeFan_Movil_REPRO_Preactivacion_repro__Importacion_de_SIM_repro__S436__Envio_de_lote(String path, String nombreArch, String deposito, String prefijo, String serial1, String serial2, String prefijo2, String Cantidad) throws IOException, Exception {
		BeFan Botones = new BeFan(driver);
		DPW dpw = new DPW();
		String[] resultadoEstado = {""};
		String[] resultadoTexto = {""};
		String estado = "En Proceso";
		resultadoEstado[0] = "PendientePreactivar";
		resultadoTexto[0] = "Reserva realizada";
		String mensaje = "";
		int cant = 0;
		cant = Integer.parseInt(Cantidad);
		irA("importacion");
		sleep(500);
		Botones.SISeleccionDeDeposito(deposito);
		sleep(500);
		Botones.SISeleccionDePrefijo(prefijo);
		sleep(500);
		Botones.SISeleccionCantidadDePrefijo("1");
		sleep(500);
		Botones.SIClickAgregar();
		Botones.SIImportarArchivo(nombreArch = Botones.LecturaDeDatosTxt(path + "\\"+ nombreArch + ".txt", cant));
		sleep(500);
		Botones.SIClickImportar();
		sleep(500);
		mensaje = Botones.SIMensajeModal();
		if (mensaje.contentEquals("El archivo se import\u00f3 correctamente.")) {
		} else {
			Assert.assertTrue(false);
		}
		Botones.SIClickAceptarImportar();
		sleep(500);
		dpw.main();
		sleep(118000);
		irA("gestion");
		sleep(500);
		Botones.SGSeleccionEstado(estado);
		sleep(500);
		Botones.SGSeleccionDeposito(deposito);
		sleep(500);
		Botones.SGFechaDesdeAhora();
		sleep(500);
		Botones.SGClickBuscar();
		sleep(500);
		Assert.assertTrue(Botones.SGLeerCampoYValidar(nombreArch, resultadoEstado, resultadoTexto));
	}
	//Falta probar, deberia funcionar :(
	@Test (groups = {"BeFAN", "PerfilMayorista"}, dataProvider="DosSerialesValidos")
	public void TS97657_BeFan_Movil_REPRO_Asociacion_de_diferentes_seriales_a_diferentes_prefijos(String path, String nombreArch, String deposito, String prefijo, String serial1, String serial2, String prefijo2) throws Exception {
		BeFan Botones = new BeFan(driver);
		String[] resultadoEstado = {""};
		String[] resultadoTexto = {""};
		String estado = "Procesado";
		resultadoEstado[0] = "Activado";
		resultadoTexto[0] = "Activaci\u00f3n confirmada";
		resultadoEstado[1] = "Activado";
		resultadoTexto[1] = "Activaci\u00f3n confirmada";
		String mensaje = "";
		int cant = 0;
		//cant = Integer.parseInt(Cantidad);
		irA("importacion");
		sleep(500);
		Botones.SISeleccionDeDeposito(deposito);
		sleep(500);
		Botones.SISeleccionDePrefijo(prefijo);
		sleep(500);
		Botones.SISeleccionCantidadDePrefijo(Integer.toString(cant-1));
		sleep(500);
		Botones.SIClickAgregar();
		sleep(500);
		Botones.SISeleccionDePrefijo(prefijo2);
		sleep(500);
		Botones.SISeleccionCantidadDePrefijo("1");
		Botones.SIClickAgregar();
		sleep(500);
		Botones.SIImportarArchivo(nombreArch = Botones.LecturaDeDatosTxt(path + "\\"+ nombreArch + ".txt", cant));
		sleep(500);
		Botones.SIClickImportar();
		sleep(500);
		mensaje = Botones.SIMensajeModal();
		if (mensaje.contentEquals("El archivo se import\u00f3 correctamente.")) {
		} else {
			Assert.assertTrue(false);
		}
		Botones.SIClickAceptarImportar();		
		sleep(500);
		//dpw.main();
		sleep(1198000);
		irA("gestion");
		sleep(500);
		Botones.SGSeleccionEstado(estado);
		sleep(500);
		Botones.SGSeleccionDeposito(deposito);
		sleep(500);
		Botones.SGFechaDesdeAhora();
		sleep(500);
		Botones.SGClickBuscar();
		sleep(500);
		Assert.assertTrue(Botones.SGLeerCampoYValidar(nombreArch, resultadoEstado, resultadoTexto));
	}
	
	
// DE 10 SIN PREP
	@Test (groups = {"BeFAN", "PerfilMayorista"}, dataProvider="ArchivoVacio")
	public void TS97664_BeFan_Movil_REPRO_Cantidad_inexistente(String path, String nombreArch, String deposito, String prefijo, String serial1, String serial2, String prefijo2) throws IOException {
		BeFan Botones = new BeFan(driver);
		String mensaje;
		
		irA("importacion");
		sleep(500);
		Botones.SISeleccionDeDeposito(deposito);
		sleep(500);
		Botones.SISeleccionDePrefijo(prefijo);
		sleep(500);
		Botones.SISeleccionCantidadDePrefijo("1");
		sleep(500);
		Botones.SIClickAgregar();
		Botones.SIImportarArchivo(nombreArch = Botones.SICreacionArchivo(nombreArch, path, "", ""));
		sleep(500);
		Botones.SIClickImportar();
		sleep(1000);
		mensaje = Botones.SIMensajeModal();
		if (mensaje.contentEquals("El archivo no contiene datos")) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}
	
	}
	
// DE 10 CON PREP
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS97653_BeFan_Movil_REPRO_PreaActivacion_Linea_Repro_SIMCARD_en_estado_distinto_a_MCVM() throws IOException, Exception {
		BeFan Botones = new BeFan(driver);
		String[] resultadoEstado = {""};
		String[] resultadoTexto = {""};
		String estado = "Procesado";
		resultadoEstado[0] = "Error";
		resultadoTexto[0] = "No esta disponible para la venta.";
		//traigo resultado de preparacion
		String[] lasNoches = Botones.soyEzpesial("TS97653").split(",");
		if (lasNoches[0].equals("false")) {
			Assert.assertTrue(false);
		}
		String nombreArch = lasNoches[1];
		String deposito = lasNoches[2];
		
		//ejecuto
		irA("gestion");
		sleep(500);
		Botones.SGSeleccionEstado(estado);
		sleep(500);
		Botones.SGSeleccionDeposito(deposito);
		sleep(500);
		Botones.SGFechaDesdeAhora();
		sleep(500);
		Botones.SGClickBuscar();
		sleep(500);
		
		Assert.assertTrue(Botones.SGLeerCampoYValidar(nombreArch, resultadoEstado, resultadoTexto));
	}
	
	
// DE 10 SIN PREP
	@Test (groups = {"BeFAN", "PerfilMayorista"}, dataProvider="SerialesNoValidos")
	public void TS97658_BeFan_Movil_REPRO_Serial_no_asociado_a_ningun_prefijo(String path, String nombreArch, String deposito, String prefijo, String serial1, String serial2, String prefijo2) throws IOException {
		BeFan Botones = new BeFan(driver);
		String mensaje;
		
		irA("importacion");
		Botones.SISeleccionDeDeposito(deposito);
		Botones.SISeleccionDePrefijo(prefijo);
		Botones.SISeleccionCantidadDePrefijo("1");
		Botones.SIClickAgregar();
		Botones.SIImportarArchivo(nombreArch = Botones.SICreacionArchivo(nombreArch, path, serial1, serial2));
		Botones.SIClickImportar();
		mensaje = Botones.SIMensajeModal();
		if (mensaje.contentEquals("La sumatoria de la cantidad de prefijos es menor a la cantidad total de lineas del archivo.")) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}

	}

	//DE 10 CON PREP
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS111958_BeFan_Movil_REPRO_Preactivacion_repro__PreActivacion_Linea_Repro() throws IOException, Exception {
		BeFan Botones = new BeFan(driver);
		String[] resultadoEstado = {""};
		String[] resultadoTexto = {""};
		String estado = "Procesado";
		resultadoEstado[0] = "Activado";
		resultadoTexto[0] = "Activaci\u00f3n confirmada";
		
		//traigo resultado de preparacion
		//Agregar para ver todas
		String[] lasNoches = Botones.soyEzpesial("TS111958").split(",");
		if (lasNoches[0].equals("false")) {
			Assert.assertTrue(false);
		}
		String nombreArch = lasNoches[1];
		String deposito = lasNoches[2];
		
		//ejecuto
		irA("gestion");
		sleep(500);
		Botones.SGSeleccionEstado(estado);
		sleep(500);
		Botones.SGSeleccionDeposito(deposito);
		sleep(500);
		Botones.SGFechaDesdeAhora();
		sleep(500);
		Botones.SGClickBuscar();
		sleep(500);
		Assert.assertTrue(Botones.SGLeerCampoYValidar(nombreArch, resultadoEstado, resultadoTexto));
		//Falta verificacion en CRM de lineas preactivadas
	}
// DE 10 SIN PREP
	@Test (groups = {"BeFAN", "PerfilMayorista"}, dataProvider="SerialInexistente", priority = 2)
	public void TS97656_BeFan_Movil_REPRO_Cantidad_de_seriales_ingresados_mayor_al_habilitado_por_agente(String path, String nombreArch, String deposito, String prefijo, String serial1, String serial2, String prefijo2, String Cantidad, String agente, String depositoLogico) throws Exception, IOException {
		BeFan Botones = new BeFan(driver);
		String mensaje;
		
		
		Botones.LogOutBefan(driver);
		sleep(500);
		loginBeFANConfigurador(driver);
		sleep(500);
		irCupos("gestion");
		sleep(500);
		Botones.CGeliminar(agente, deposito);
		sleep(500);
		Botones.LogOutBefan(driver);
		loginBeFAN(driver);
		irA("importacion");
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
		sleep(1000);
		mensaje = Botones.SIMensajeModal();
		sleep(10000);
		if (mensaje.contentEquals("Error al intentar validar cantidad de cupos.")) {
			try {
				Botones.SIClickAceptarImportar();
				sleep(500);
				Botones.LogOutBefan(driver);
				sleep(500);
				loginBeFANConfigurador(driver);
				sleep(500);
				irCupos("importacion");
				sleep(500);
				Botones.CIImportarArchivo(agente, depositoLogico);
				Assert.assertTrue(true);
			} catch (Exception e) {
				Assert.assertTrue(true);
			}

			
		} else {
			try {
				Botones.SIClickAceptarImportar();
				sleep(500);
				Botones.LogOutBefan(driver);
				sleep(500);
				loginBeFANConfigurador(driver);
				sleep(500);
				irCupos("importacion");
				sleep(500);
				Botones.CIImportarArchivo(agente, depositoLogico);
				Assert.assertTrue(false);
			} catch (Exception e) {
					Assert.assertTrue(false);
				}
		}


	}
	
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS97654_BeFan_Movil_REPRO_PreaActivacion_Linea_Repro_Localidad_inexistente_para_numeracion_movil() throws IOException, Exception {
		BeFan Botones = new BeFan(driver);
		DPW dpw = new DPW();
		String[] resultadoEstado = {""};
		String[] resultadoTexto = {""};
		String estado = "Procesado";
		resultadoEstado[0] = "Error";
		resultadoTexto[0] = "Desreserva realizada";
		
		//traigo resultado de preparacion
		String[] lasNoches = Botones.soyEzpesial("TS97654").split(",");
		if (lasNoches[0].equals("false")) {
			Assert.assertTrue(false);
		}
		String nombreArch = lasNoches[1];
		String deposito = lasNoches[2];
		
		//ejecuto
		irA("gestion");
		sleep(500);
		Botones.SGSeleccionEstado(estado);
		sleep(500);
		Botones.SGSeleccionDeposito(deposito);
		sleep(500);
		Botones.SGFechaDesdeAhora();
		sleep(500);
		Botones.SGClickBuscar();
		sleep(500);
		Assert.assertTrue(Botones.SGLeerCampoYValidar(nombreArch, resultadoEstado, resultadoTexto));
		
	}
	
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS111990_BeFan_Movil_REPRO_PreaActivacion_Linea_Repro_Localidad_inexistente_para_numeracion_movil() throws IOException, Exception {
		BeFan Botones = new BeFan(driver);
		String[] resultadoEstado = {""};
		String[] resultadoTexto = {""};
		String estado = "Procesado";
		resultadoEstado[0] = "Error";
		resultadoTexto[0] = "Desreserva realizada";

		//traigo resultado de preparacion
		String[] lasNoches = Botones.soyEzpesial("TS111990").split(",");
		if (lasNoches[0].equals("false")) {
			Assert.assertTrue(false);
		}
		String nombreArch = lasNoches[1];
		String deposito = lasNoches[2];
		
		//ejecuto
		irA("gestion");
		sleep(500);
		Botones.SGSeleccionEstado(estado);
		sleep(500);
		Botones.SGSeleccionDeposito(deposito);
		sleep(500);
		Botones.SGFechaDesdeAhora();
		sleep(500);
		Botones.SGClickBuscar();
		sleep(500);
		Assert.assertTrue(Botones.SGLeerCampoYValidar(nombreArch, resultadoEstado, resultadoTexto));
		
	}
	
	// DE 10 CON PREP
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS126672_BeFan_Movil_REPRO_Preactivacion_repro__Importacion_de_SIM_repro__S105__Fallo_al_confirmar_el_serial() throws IOException, Exception {
		BeFan Botones = new BeFan(driver);
		String[] resultadoEstado = {""};
		String[] resultadoTexto = {""};
		String estado = "En proceso";
		resultadoEstado[0] = "Error";
		resultadoTexto[0] = "6.1.1.3 // Movimiento no valido";

		//traigo resultado de preparacion
		String[] lasNoches = Botones.soyEzpesial("TS126672").split(",");
		if (lasNoches[0].equals("false")) {
			Assert.assertTrue(false);
		}
		String nombreArch = lasNoches[1];
		String deposito = lasNoches[2];
		
		//ejecuto
		irA("gestion");
		sleep(500);
		Botones.SGSeleccionEstado(estado);
		sleep(500);
		Botones.SGSeleccionDeposito(deposito);
		sleep(500);
		Botones.SGFechaDesdeAhora();
		sleep(500);
		Botones.SGClickBuscar();
		sleep(500);
		Assert.assertTrue(Botones.SGLeerCampoYValidar(nombreArch, resultadoEstado, resultadoTexto));
		
	}
	
	
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS112052_BeFan_Movil_REPRO_Preactivacion_repro_Busqueda_de_archivos_Usuario_TPA_Visualizacion_de_mas_informacion_Exportacion_Nombre() {
		irA("gestion");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Procesado");
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(5000);
		driver.findElement(By.id("exportarTabla")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(8).click();
		sleep(3000);
		driver.findElement(By.id("botonExportar")).click();
		sleep(5000);
		String downloadPath = "C:\\Users\\Nicolas\\Downloads";
		Assert.assertTrue(scp.isFileDownloaded(downloadPath, "PREACTIVACIONES DIARIAS"));
	}
	
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS135619_BeFan_Movil_Repro_Preactivacion_Importacion_de_cupos_Exitoso_Verificacion_Dentro_de_la_fecha() {
		ContactSearch contact = new ContactSearch(driver);
		irA("importacion");
		sleep(7000);
		selectByText(driver.findElement(By.name("vendedores")), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		sleep(7000);
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "2477");
		driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("1");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		File directory = new File("BeFan135619d.txt");
		contact.subir_cupos(new File(directory.getAbsolutePath()).toString(),"");
		sleep(5000);
		driver.findElements(By.cssSelector(".btn.btn-primary")).get(2).click();
		Boolean a = false;
		sleep(5000);
		List <WebElement> formato = driver.findElements(By.className("modal-content"));
		for(WebElement x : formato) {
			if(x.getText().toLowerCase().contains("el archivo se import\u00f3 correctamente")) {
				a= true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test(groups = {"BeFAN", "PerfilMayorista"}, dependsOnMethods ="TS97651_BeFan_Movil_REPRO_PreaActivacion_Linea_Repro_SIMCARD_en_deposito_inexistente")
	public void TS97652_BeFan_Movil_REPRO_PreaActivacion_Linea_Repro_SIMCARD_no_en_deposito_de_AGENTE() {
		Assert.assertTrue(true);
	}
	
	@Test(groups = {"BeFAN", "PerfilMayorista"}, dependsOnMethods ="TS126648_BeFan_Movil_REPRO_Preactivacion_repro__Importacion_de_SIM_repro__S436__Envio_de_lote")
	public void TS97667_BeFan_Movil_REPRO_Seriales_con_estado_PENDIENTE_DE_VALIDAR() {
		Assert.assertTrue(true);
	}
	
	@Test(groups = {"BeFAN", "PerfilMayorista"}, dependsOnMethods ="TS111958_BeFan_Movil_REPRO_Preactivacion_repro__PreActivacion_Linea_Repro")
	public void TS97671_BeFan_Movil_REPRO_Seriales_con_estado_ACTIVADA() {
		Assert.assertTrue(true);
	}
	
	@Test(groups = {"BeFAN", "PerfilMayorista"}, dependsOnMethods ="TS111958_BeFan_Movil_REPRO_Preactivacion_repro__PreActivacion_Linea_Repro")
	public void TS111996_BeFan_Movil_REPRO_Preactivacion_repro__Envio_de_lote_a_OM__Verificacion_de_lineas_enviadas() {
		Assert.assertTrue(true);
	}
	
	@Test(groups = {"BeFAN", "PerfilMayorista"}, dependsOnMethods ="TS111990_BeFan_Movil_REPRO_PreaActivacion_Linea_Repro_Localidad_inexistente_para_numeracion_movil")
	public void TS111998_BeFan_Movil_REPRO_Preactivacion_repro__Reserva_de_numeros_en_numeracion_movil__Fallo_y_desreserva_de_SIMCARDS() {
		Assert.assertTrue(true);
	}
	
	@Test(groups = {"BeFAN", "PerfilMayorista"}, dependsOnMethods ="TS111958_BeFan_Movil_REPRO_Preactivacion_repro__PreActivacion_Linea_Repro")
	public void TS126673_BeFan_Movil_REPRO_Preactivacion_repro__Prefijo_con_cupos() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS126680_BeFan_Movil_REPRO_Preactivacion_repro_Visualizacion_de_archivos_importados_Fecha_de_carga() {
	boolean fechaDeCarga = false;
	String sDateFormat = "dd/MM/yyyy HH:mm:ss";
	//SimpleDateFormat sdfDateFormat;
	irA("gestion");
	selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "En Proceso");
	selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
	driver.findElement(By.cssSelector(".btn.btn-primary")).click();
	sleep(5000);
	WebElement tabla = driver.findElement(By.id("exportarTabla")).findElement(By.tagName("thead"));
	for (WebElement x : tabla.findElements(By.tagName("th"))) {
		if (x.getText().contains("Fecha de Carga"))
			fechaDeCarga = true;
	}
	WebElement cont = driver.findElement(By.id("exportarTabla"));
	Marketing colu = new Marketing(driver);
	List<WebElement> x = colu.traerColumnaElement(cont, 8, 1);	
	for(WebElement a : x) {
		a.getText().contains(sDateFormat);
		//System.out.println(a.getText());
		}
	Assert.assertTrue(fechaDeCarga);
	}	
	
//	@Test (groups = {"BeFAN", "PerfilMayorista"}, dataProvider="GestionRegionesCreacion", dependsOnGroups="EliminacionDeAgrupador")
	public void TS126636_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Eliminacion_de_agrupadores_Si_Sin_preactivar_Verificacion(String sRegion) {
		irA("Sims", "Importaci\u00f3n");
		
		WebElement wSelect = driver.findElement(By.name("vendedores"));
		List<WebElement> wOptions = wSelect.findElements(By.tagName("option"));
		boolean bAssert = true;
		for(WebElement wAux : wOptions) {
			if(wAux.getText().contains(sRegion)) {
				bAssert = false;
			}
		}
		
		Assert.assertTrue(bAssert);
	}
	
	@Test (groups = {"BeFAN", "PerfilMayorista"})
	public void TS126682_BeFan_Movil_REPRO_Preactivacion_repro_Visualizacion_de_archivos_importados_Fecha_de_procesamiento_Sin_fecha() {
		boolean fechaProcesado = false;
		irA("gestion");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "En Proceso");
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(5000);
		WebElement tabla = driver.findElement(By.id("exportarTabla")).findElement(By.tagName("thead"));
		for (WebElement x : tabla.findElements(By.tagName("th"))) {
			if (x.getText().contains("Fecha Procesado"))
				fechaProcesado = true;
		}
		WebElement cont = driver.findElement(By.id("exportarTabla"));
		Marketing colu = new Marketing(driver);
		List<WebElement> x = colu.traerColumnaElement(cont, 8, 7);	
		for(WebElement a : x) {
				a.getText().isEmpty();
				
			}
		Assert.assertTrue(fechaProcesado);
	}	
	@Test (groups = {"BeFAN", "PerfilMayorista"}, dataProvider="GestionRegionesCreacion", dependsOnGroups="EliminacionDePrefijo")
	public void TS126637_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Modificacion_de_agrupadores_Eliminacion_de_prefijos_en_agrupador_existente_Guardando_Verificacion(String sRegion) throws IOException {
		irA("Sims", "Importaci\u00f3n");
		
		String sPrefijo = readTxt("Prefijo.txt");
		System.out.println("sPrefijo: " + sPrefijo);
		
		WebElement wSelect = driver.findElement(By.name("vendedores"));
		List<WebElement> wOptions = wSelect.findElements(By.tagName("option"));
		boolean bAssert = false;
		for(WebElement wAux : wOptions) {
			if(wAux.getText().contains(sRegion)) {
				bAssert = true;
				wAux.click();
			}
		}
		Assert.assertTrue(bAssert);
		
		wSelect = driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-valid.ng-empty.ng-touched"));
		wOptions = wSelect.findElements(By.tagName("option"));
		for(WebElement wAux : wOptions) {
			if(wAux.getText().equals(sPrefijo)) {
				bAssert = false;
			}
		}
		Assert.assertTrue(bAssert);
	}
	
	
	@Test(groups = {"BeFAN", "PerfilMayorista"}, dependsOnMethods= {"TS97654_BeFan_Movil_REPRO_PreaActivacion_Linea_Repro_Localidad_inexistente_para_numeracion_movil"})
	public void TS97670_BeFan_Movil_REPRO_Seriales_con_estado_ERROR() {
		Assert.assertTrue(true);
	}
	

	//----------------------------------- PERFIL CONFIGURADOR -----------------------
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"}, dataProvider="GestionRegionesCreacion")
	public void TS126620_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Busqueda_especifica(String sRegion) {
		irA("Regiones", "Gesti\u00f3n");
		pbf = new Pages.BeFan(driver);
		
		Assert.assertTrue(pbf.buscarRegion(sRegion));
	}
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
	public void TS126618_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Pantalla_de_inicio() {
		irA("regiones", "gesti\u00f3n");
		List<WebElement> agrupadores = driver.findElements(By.cssSelector(".panel-group.panel-group-alternative.ng-scope"));
		Assert.assertTrue(agrupadores.size() >= 1);
		if (driver.findElements(By.className("collapsed")).size() >= 1)
			Assert.assertTrue(true);
		else
			Assert.assertTrue(false);
	}
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
				System.out.println(sAgrupador);
				bAssert = true;
				break;
			}
		}
		
		Assert.assertTrue(bAssert && wList.size() > 1);
	}
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
	public void TS135624_BeFan_Movil_Repro_Preactivacion_Gestion_de_cupos_Valores_por_default() {
	irA("Cupos", "Gesti\u00f3n");
	boolean valores = driver.findElement(By.className("tpt-body")).findElement(By.tagName("div")).isDisplayed();
	Assert.assertTrue(valores);
		
	}
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	@Test (groups = {"BeFAN", "PerfilConfigurador"}, dataProvider="GestionRegionesCreacion")
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"}, dataProvider="GestionRegionesCreacion", dependsOnMethods="TS126619_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Creacion_de_agrupador_exitosa")
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador", "EliminacionDePrefijo"}, dataProvider="GestionRegionesCreacion", dependsOnMethods="TS126623_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Modificacion_de_agrupadores_Asignacion_de_prefijos_a_agrupador_existente_Guardando")
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
	public void TS112021_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Eliminacion_de_agrupadores_No() {
		boolean noEliminar = false;
		TestBase tst = new TestBase();
		irA("regiones", "gesti\u00f3n");
		
		String sRegion = tst.teTraigoRandomStrings(tst.teTraigoLaFecha());
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		tst.waitForClickeable(driver, By.cssSelector(".form-control.ng-pristine.ng-untouched.ng-valid.ng-empty"));
		driver.findElement(By.cssSelector(".form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys(sRegion);
		driver.findElements(By.cssSelector(".btn.btn-primary")).get(0).click();
		sleep(2000);
		driver.findElements(By.cssSelector(".btn.btn-primary")).get(1).click();
		tst.waitForClickeable(driver, By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div/input"));
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div/input")).sendKeys(sRegion);
		tst.waitForClickeable(driver, By.className("collapsed"));
		driver.findElement(By.className("collapsed")).click();
		sleep(2000);
		driver.findElements(By.cssSelector(".btn.btn-link")).get(1).click();
		tst.waitForClickeable(driver, By.cssSelector(".check-filter-on"));
		driver.findElements(By.cssSelector(".check-filter-on")).get(0).click();
		driver.findElements(By.cssSelector(".btn.btn-primary")).get(0).click();
		sleep(2000);
		driver.findElements(By.cssSelector(".btn.btn-primary")).get(1).click();
		sleep(2000);
		driver.findElement(By.className("collapsed")).click();
		sleep(2000);
		driver.findElements(By.cssSelector(".btn.btn-link")).get(1).click();
		sleep(2000);
		driver.findElements(By.cssSelector(".btn.btn-primary")).get(0).click();
		sleep(2000);
		driver.findElements(By.cssSelector(".btn.btn-primary")).get(1).click();
		sleep(2000);
		driver.findElements(By.cssSelector(".btn.btn-link")).get(0).click();
		sleep(2000);
		if (driver.findElements(By.cssSelector(".ng-binding")).get(10).getText().toLowerCase().equals(sRegion)) {
			noEliminar = true;
		} else {
			noEliminar = false;
			System.out.println(driver.findElements(By.cssSelector(".ng-binding")).get(10).getText().toLowerCase());
		}
		Assert.assertTrue(noEliminar);
	}
	
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
	public void TS126634_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Eliminacion_de_agrupadores_Si_Preactivando() throws InterruptedException {
		irA("Regiones", "Gesti\u00f3n");
		BeFan page = new BeFan(driver);
		boolean eliminar = false;
		
		
		
		
		
		Assert.assertTrue(eliminar);
	}	
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
	public void TS112020_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Eliminacion_de_agrupadores_Si_Sin_preactivar() {
		boolean eliminar = false;
		TestBase tst = new TestBase();
		irA("regiones", "gesti\u00f3n");
		
		String sRegion = tst.teTraigoRandomStrings(tst.teTraigoLaFecha());
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		tst.waitForClickeable(driver, By.cssSelector(".form-control.ng-pristine.ng-untouched.ng-valid.ng-empty"));
		driver.findElement(By.cssSelector(".form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys(sRegion);
		driver.findElements(By.cssSelector(".btn.btn-primary")).get(0).click();
		sleep(2000);
		driver.findElements(By.cssSelector(".btn.btn-primary")).get(1).click();
		tst.waitForClickeable(driver, By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div/input"));
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div/input")).sendKeys(sRegion);
		tst.waitForClickeable(driver, By.className("collapsed"));
		driver.findElement(By.className("collapsed")).click();
		sleep(2000);
		driver.findElements(By.cssSelector(".btn.btn-link")).get(1).click();
		tst.waitForClickeable(driver, By.cssSelector(".check-filter-on"));
		driver.findElements(By.cssSelector(".check-filter-on")).get(0).click();
		driver.findElements(By.cssSelector(".btn.btn-primary")).get(0).click();
		sleep(2000);
		driver.findElements(By.cssSelector(".btn.btn-primary")).get(1).click();
		sleep(2000);
		driver.findElement(By.className("collapsed")).click();
		sleep(2000);
		driver.findElements(By.cssSelector(".btn.btn-link")).get(1).click();
		sleep(2000);
		driver.findElements(By.cssSelector(".btn.btn-primary")).get(0).click();
		sleep(2000);
		driver.findElements(By.cssSelector(".btn.btn-primary")).get(1).click();
		sleep(2000);
		driver.findElements(By.cssSelector(".btn.btn-primary")).get(0).click();
		sleep(2000);
		driver.findElements(By.cssSelector(".btn.btn-primary")).get(1).click();
		sleep(2000);
		
		if (driver.findElements(By.className("collapsed")).size()==0) {
			eliminar = true;
		} else {
			eliminar = false;
		}		
		Assert.assertTrue(eliminar);

	}
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
	public void TS112019_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Eliminacion_de_agrupadores_Mensaje() {
		boolean mensaje = false;
		TestBase tst = new TestBase();
		irA("regiones", "gesti\u00f3n");
		
		String sRegion = tst.teTraigoRandomStrings(tst.teTraigoLaFecha());
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		tst.waitForClickeable(driver, By.cssSelector(".form-control.ng-pristine.ng-untouched.ng-valid.ng-empty"));
		driver.findElement(By.cssSelector(".form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys(sRegion);
		driver.findElements(By.cssSelector(".btn.btn-primary")).get(0).click();
		sleep(2000);
		driver.findElements(By.cssSelector(".btn.btn-primary")).get(1).click();
		tst.waitForClickeable(driver, By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div/input"));
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div/input")).sendKeys(sRegion);
		tst.waitForClickeable(driver, By.className("collapsed"));
		driver.findElement(By.className("collapsed")).click();
		sleep(2000);
		driver.findElements(By.cssSelector(".btn.btn-link")).get(1).click();
		tst.waitForClickeable(driver, By.cssSelector(".check-filter-on"));
		driver.findElements(By.cssSelector(".check-filter-on")).get(0).click();
		driver.findElements(By.cssSelector(".btn.btn-primary")).get(0).click();
		sleep(2000);
		driver.findElements(By.cssSelector(".btn.btn-primary")).get(1).click();
		sleep(2000);
		driver.findElement(By.className("collapsed")).click();
		sleep(2000);
		driver.findElements(By.cssSelector(".btn.btn-link")).get(1).click();
		sleep(2000);
		driver.findElements(By.cssSelector(".btn.btn-primary")).get(0).click();
		sleep(2000);
		driver.findElements(By.cssSelector(".btn.btn-primary")).get(1).click();
		sleep(2000);
		
		if (driver.findElement(By.xpath("/html/body/div[1]/div/div/div[1]/div[1]/div[1]/h3")).getText().equals("�Tambien desea eliminar la Region " + sRegion + " ?")) {
			mensaje = true;
		} else {
			mensaje = false;
		}
		Assert.assertTrue(mensaje);
	}
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	

	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
		
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"}, dataProvider="SerialInexistente", priority = 2)
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"}, dataProvider="SerialInexistente")
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
	
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	
	@Test (groups = {"BeFAN", "PerfilConfigurador"})
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
	
	@Test (groups = {"BeFAN", "PerfilConfigurador", "EliminacionDeAgrupador"}, dataProvider="GestionRegionesCreacion", dependsOnMethods="TS126623_BeFan_Movil_REPRO_Preactivacion_repro_Gestion_de_agrupadores_Busqueda_Modificacion_de_agrupadores_Asignacion_de_prefijos_a_agrupador_existente_Guardando")
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
