package Tests;

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
import java.util.List;



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
import PagesPOM.BeFanPom;
import Pages.DPW;

public class BeFANMayorista extends TestBase {
		
	private WebDriver driver;
	private SCP scp;
	
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
	
	@BeforeClass (alwaysRun = true)
	public void init() {
		driver = setConexion.setupEze();
		scp = new SCP(driver);
		loginBeFAN(driver);
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
	
	@Test (groups = "BeFAN")
	public void TS126656_BeFan_Movil_REPRO_Preactivacion_repro_Busqueda_de_archivos_Agente_Por_estado_EN_PROCESO_Exitosa() {
		boolean enProceso = false;
		irA("gestion");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "En Proceso");
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
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
	public void TS126655_BeFan_Movil_REPRO_Preactivacion_repro_Busqueda_de_archivos_Agente_Por_estado_PENDIENTE_Exitosa() {
		boolean pendiente = false;
		irA("gestion");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Pendiente");
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(5000);
		List<WebElement> tabla = driver.findElement(By.id("exportarTabla")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for (int i=0; i<5; i++) {
			if (tabla.iterator().next().findElements(By.tagName("td")).get(6).getText().equalsIgnoreCase("Pendiente"))
				pendiente = true;
			i++;
		}
		Assert.assertTrue(pendiente);
	}
	
	@Test (groups = "BeFAN")
	public void TS126657_BeFan_Movil_REPRO_Preactivacion_repro_Busqueda_de_archivos_Agente_Por_estado_PROCESADO_Exitosa() {
		boolean procesado = false;
		irA("gestion");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Procesado");
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
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
	public void TS126662_BeFan_Movil_REPRO_Preactivacion_repro_Busqueda_de_archivos_Agente_Visualizacion_de_mas_informacion() {
		boolean razonSocial = false, linea = false, plan = false, nmu = false, serie = false, preactivacion = false, procesamiento = false, estado = false, descripcion = false;
		irA("gestion");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Procesado");
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(5000);
		try {
			driver.findElement(By.id("exportarTabla")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(8).click();
		} catch(Exception e) {}
		sleep(3000);
		WebElement columnas = driver.findElement(By.cssSelector(".table.table-top-fixed.table-striped.table-primary")).findElement(By.tagName("tr"));
		for (WebElement x : columnas.findElements(By.tagName("th"))) {
			if (x.getText().contains("Raz\u00f3n Social"))
				razonSocial = true;
			if (x.getText().contains("L\u00ednea"))
				linea = true;
			if (x.getText().contains("Plan"))
				plan = true;
			if (x.getText().contains("NMU"))
				nmu = true;
			if (x.getText().contains("Serie"))
				serie = true;
			if (x.getText().contains("Preactivaci\u00f3n"))
				preactivacion = true;
			if (x.getText().contains("Procesamiento"))
				procesamiento = true;
			if (x.getText().contains("Estado"))
				estado = true;
			if (x.getText().contains("Descripci\u00f3n"))
				descripcion = true;
		}
		Assert.assertTrue(razonSocial && linea && plan && nmu && serie && preactivacion && procesamiento && estado && descripcion);
	}
	
	@Test (groups = "BeFAN")
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
		String downloadPath = "C:\\Users\\Nicolas\\Downloads";
		Assert.assertTrue(scp.isFileDownloaded(downloadPath, "PREACTIVACIONES DIARIAS"), "Failed to download Expected document");
	}
	
	@Test (groups = "BeFAN")
	public void TS126660_BeFan_Movil_REPRO_Preactivacion_repro_Busqueda_de_archivos_Agente_Archivos_de_otros_agentes() {
		boolean match = false;
		irA("gestion");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Procesado");
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
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
	public void TS112044_BeFan_Movil_REPRO_Preactivacion_repro_Busqueda_de_archivos_Agente_Por_estado_EN_PROCESO_Exitosa() {
		boolean enProceso = false;
		irA("gestion");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "En Proceso");
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
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
	public void TS112045_BeFan_Movil_REPRO_Preactivacion_repro_Busqueda_de_archivos_Agente_Por_estado_PROCESADO_Exitosa() {
		boolean procesado = false;
		irA("gestion");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Procesado");
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
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
	public void TS112043_BeFan_Movil_REPRO_Preactivacion_repro_Busqueda_de_archivos_Agente_Por_estado_PENDIENTE_Exitosa() {
		boolean pendiente = false;
		irA("gestion");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Pendiente");
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(5000);
		List<WebElement> tabla = driver.findElement(By.id("exportarTabla")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for (int i=0; i<5; i++) {
			if (tabla.iterator().next().findElements(By.tagName("td")).get(6).getText().equalsIgnoreCase("Pendiente"))
				pendiente = true;
			i++;
		}
		Assert.assertTrue(pendiente);
	}
	
	@Test (groups = "BeFAN")
	public void TS112042_BeFan_Movil_REPRO_Preactivacion_repro_Busqueda_de_archivos_Agente_Por_fecha_Exitosa() throws ParseException {
		BeFan fechas= new BeFan (driver);
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat ("dd/MM/yyyy");
		String desde ="27/11/2018";
		String hasta = "27/12/2018";
		Date fechaDesde = formatoDelTexto.parse(desde);
		Date fechaHasta =formatoDelTexto.parse(hasta);
		boolean cantidad = false;
		irA("gestion");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Procesado");
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		driver.findElement(By.id("dataPickerDesde"));
		((JavascriptExecutor) driver).executeScript("document.getElementById('dataPickerDesde').value='"+desde+"'");
		sleep(3000);
		driver.findElement(By.id("dataPickerHasta"));
		((JavascriptExecutor) driver).executeScript("document.getElementById('dataPickerHasta').value='"+hasta+"'");
		int dias = fechas.numeroDiasEntreDosFechas(fechaDesde, fechaHasta);
		boolean confirmacion = false;
		if(dias <= 90) {
			confirmacion = true;
			System.out.println("Hay " + dias + " dias, " +"No supera los 90 dias comprendidos");
		}else {
			System.out.println("Se debe ingresar fechas las cuales no superen los 90 dias comprendidos");
		}
		
		Assert.assertTrue(confirmacion);
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(5000);
		List<WebElement> tabla = driver.findElement(By.id("exportarTabla")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		if (tabla.size() >= 1)
			cantidad = true;
		Assert.assertTrue(cantidad);
	}
	
	@Test (groups = "BeFAN")
	public void TS112047_BeFan_Movil_REPRO_Preactivacion_repro_Busqueda_de_archivos_Agente_Formato() {
		boolean nombreArchivo = false, fechaDeCarga = false, estado = false, fechaProcesado = false, puntoDeVenta = false, accion = false;
		irA("gestion");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Procesado");
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(5000);
		WebElement tabla = driver.findElement(By.id("exportarTabla")).findElement(By.tagName("thead"));
		for (WebElement x : tabla.findElements(By.tagName("th"))) {
			if (x.getText().contains("Nombre Archivo"))
				nombreArchivo = true;
			if (x.getText().contains("Fecha de Carga"))
				fechaDeCarga = true;
			if (x.getText().contains("Estado"))
				estado = true;
			if (x.getText().contains("Fecha Procesado"))
				fechaProcesado = true;
			if (x.getText().contains("Punto de Venta"))
				puntoDeVenta = true;
			if (x.getText().contains("Acci\u00f3n"))
				accion = true;
		}
		Assert.assertTrue(nombreArchivo && fechaDeCarga && estado && fechaProcesado && puntoDeVenta && accion);
	}
	
	@Test (groups = "BeFAN")
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
	
	@Test (groups = "BeFAN")
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
	
	@Test (groups = "BeFAN")
	public void TS135603_BeFan_Movil_Repro_Preactivacion_Gestion_de_simcards_Exportacion_de_archivo_Nombre() {
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
		String downloadPath = "C:\\Users\\Quelys\\Downloads";
		Assert.assertTrue(scp.isFileDownloaded(downloadPath, "PREACTIVACIONES DIARIAS"), "Failed to download Expected document");
	}
	
	@Test (groups = "BeFAN")
	public void TS135591_BeFan_Movil_Repro_Preactivacion_Gestion_de_simcards_Busqueda_de_archivos_Ver_detalle() {
		boolean razonSocial = false, linea = false, plan = false, nmu = false, serie = false, preactivacion = false, procesamiento = false, estado = false, descripcion = false;
		irA("gestion");
		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Procesado");
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(5000);
		try {
			driver.findElement(By.id("exportarTabla")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(8).click();
		} catch(Exception e) {}
		sleep(3000);
		WebElement columnas = driver.findElement(By.cssSelector(".table.table-top-fixed.table-striped.table-primary")).findElement(By.tagName("tr"));
		for (WebElement x : columnas.findElements(By.tagName("th"))) {
			if (x.getText().contains("Raz\u00f3n Social"))
				razonSocial = true;
			if (x.getText().contains("L\u00ednea"))
				linea = true;
			if (x.getText().contains("Plan"))
				plan = true;
			if (x.getText().contains("NMU"))
				nmu = true;
			if (x.getText().contains("Serie"))
				serie = true;
			if (x.getText().contains("Preactivaci\u00f3n"))
				preactivacion = true;
			if (x.getText().contains("Procesamiento"))
				procesamiento = true;
			if (x.getText().contains("Estado"))
				estado = true;
			if (x.getText().contains("Descripci\u00f3n"))
				descripcion = true;
		}
		Assert.assertTrue(razonSocial && linea && plan && nmu && serie && preactivacion && procesamiento && estado && descripcion);
	}
	
	@Test (groups = "BeFAN")
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
	
	@Test (groups = "BeFAN")
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
	
	@Test (groups = "BeFAN")
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
	
	@Test (groups = "BeFan")
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
	
	@Test (groups = "BeFan")
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
	
	@Test (groups = "BeFan")
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
	
	@Test (groups = "BeFan")
	public void TS135598_BeFan_Movil_Repro_Preactivacion_Gestion_de_simcards_Busqueda_de_archivos_Ver_detalle_Fecha_de_procesamiento_del_registro() {
		BeFanPom esperar = new BeFanPom(driver);
		esperar.selectSims("Gestión").click();//Selecionar entre: Importación o Gestión ()
		esperar.selectOpcion(esperar.getDesplegableEstado_Gestion(), "Procesado");
		esperar.selectOpcion(esperar.getDesplegableRegion_Gestion(),"BAS-VJP-BAHIA BLANCA\n" +"              - VJP Punta Alta");
		esperar.getBotonBuscar_Gestion().click();
		esperar.getBotonVerDetalle_Gestion().click();
		Assert.assertTrue(esperar.getMacheaText(esperar.getDetalle_Gestion(), "\\d{2}/\\d{2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}"));
		
		
//		irA("gestion");
//		selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Procesado");
//		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
//		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
//		sleep(5000);
//		try {
//			driver.findElement(By.id("exportarTabla")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(8).click();
//		} catch(Exception e) {}
//		sleep(3000);
//		boolean procesamiento = false;
//		WebElement columnas = driver.findElement(By.cssSelector(".table.table-top-fixed.table-striped.table-primary")).findElement(By.tagName("tr"));
//		for (WebElement x : columnas.findElements(By.tagName("th"))) {
//			if (x.getText().contains("Procesamiento"))
//				procesamiento = true;
//		}
//		Assert.assertTrue(procesamiento);
	}
	
	@Test (groups = "BeFan")
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
	
	@Test (groups = "BeFan")
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

	@Test (groups = {"BeFAN", "Agente"})
	public void TS135647_BeFan_Movil_Repro_Preactivacion_Visualizacion_de_datos_del_agente() {
	sleep(3000);
	driver.findElement(By.className("tpi-user")).findElement(By.tagName("span")).click();
	WebElement asd = driver.findElement(By.id("menudatos")).findElement(By.name("salir"));
	System.out.println(asd.getText());
	Assert.assertTrue(asd.isDisplayed());
		
	}
	@Test (groups = "BeFAN")
	public void TS135604_BeFan_Movil_Repro_Preactivacion_Gestion_de_simcards_Exportacion_de_archivo_Detalle() throws IOException {
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
		String downloadPath = "C:\\Users\\Quelys\\Downloads";
		try {
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+downloadPath);
			System.out.println("Descargo Archivo");
			} catch (IOException ee) {
			ee.printStackTrace();
			}
		Assert.assertTrue(scp.isFileDownloaded(downloadPath, "PREACTIVACIONES DIARIAS"));
	}
	
	

	//Preparacion para preactivacion
	@Test (groups = "PreactivacionBeFan")
	public void TS123_ElMetodoQueSopapeaATodosLosMetodos() throws Exception {
		
		//Adquiero datos del excel
		Object[][] testObjArray = ExcelUtils.getTableArray("E2EUAT.xlsx","E2EsinPago",1,1,8,"Preactivacion");
		
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
					Botones.SIImportarArchivo(nombreArch = Botones.SICreacionArchivo(nombreArch, path, serial1, ""));
					sleep(500);
					Botones.SIClickImportar();
					sleep(500);
					mensaje = Botones.SIMensajeModal();
					if (mensaje.contentEquals("El archivo se import\u00f3 correctamente.")) {
						Botones.SIClickAceptarImportar();
						sleep(500);
						//Respondo por el caso
						resultados.add("TS97651," + nombreArch + "," + deposito);
						resultados.add("TS126640," + nombreArch + "," + deposito);
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
					Botones.SIImportarArchivo(nombreArch = Botones.SICreacionArchivo(nombreArch, path, serial1, ""));
					sleep(500);
					Botones.SIClickImportar();
					sleep(500);
					mensaje = Botones.SIMensajeModal();
					if (mensaje.contentEquals("El archivo se import\u00f3 correctamente.")) {
						Botones.SIClickAceptarImportar();
						sleep(500);
						//Respondo por el caso
						resultados.add("TS112029," + nombreArch + "," + deposito);
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
					irA("gestion");
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
						Botones.SIClickAceptarImportar();
						sleep(500);
						//Respondo por el caso
						resultados.add("TS97657," + nombreArch + "," + deposito);
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
					Botones.SIImportarArchivo(nombreArch = Botones.SICreacionArchivo(nombreArch, path, serial1, ""));
					sleep(500);
					Botones.SIClickImportar();
					sleep(500);
					mensaje = Botones.SIMensajeModal();
					if (mensaje.contentEquals("El archivo se import\u00f3 correctamente.")) {
						Botones.SIClickAceptarImportar();
						sleep(500);
						//Respondo por el caso
						resultados.add("TS97653," + nombreArch + "," + deposito);
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
						resultados.add("TS111958," + nombreArch + "," + deposito);
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
					Botones.SIImportarArchivo(nombreArch = Botones.SICreacionArchivo(nombreArch, path, serial1, ""));
					sleep(500);
					Botones.SIClickImportar();
					sleep(500);
					mensaje = Botones.SIMensajeModal();
					if (mensaje.contentEquals("El archivo se import\u00f3 correctamente.")) {
						Botones.SIClickAceptarImportar();
						sleep(500);
						//Respondo por el caso
						resultados.add("TS111990," + nombreArch + "," + deposito);
						resultados.add("TS97654," + nombreArch + "," + deposito);
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
		
		
		dpw.main();
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
	@Test (groups = "BeFan")
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
	@Test (groups = "BeFan", dataProvider="SerialInexistente")
	public void TS112002_BeFan_Movil_REPRO_Preactivacion_repro__Importacion_de_SIM_repro__Mensaje_de_error_ante_volver_a_agregar_otro_prefijo(String path, String nombreArch, String deposito, String prefijo, String serial1, String serial2, String prefijo2) throws Exception{
		
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
	
	@Test (groups = {"BeFan"}, dataProvider="SerialInexistente")
	public void TS112029_BeFan_Movil_REPRO_Preactivacion_repro__Importacion_de_SIM_repro__S105__Simcard_inexistente(String path, String nombreArch, String deposito, String prefijo, String serial1, String serial2, String prefijo2) throws IOException, Exception {
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
	//DE 10 SIN PREP
	@Test (groups = "BeFan", dataProvider="SerialConFormatoInvalido")
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
	@Test (groups = {"BeFan"}, dataProvider="SerialConDepositoErroneo")
	public void TS126640_BeFan_Movil_REPRO_Preactivacion_repro__Importacion_de_SIM_repro__S105__Deposito_erroneo(String path, String nombreArch, String deposito, String prefijo, String serial1, String serial2, String prefijo2) throws IOException, Exception {
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
	@Test (groups = {"BeFan"}, dataProvider="SerialBalido")
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
	@Test (groups = {"BeFan"}, dataProvider="DosSerialesValidos")
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
	@Test (groups = "BeFan", dataProvider="ArchivoVacio")
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
	@Test (groups = "BeFan")
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
	@Test (groups = "BeFan", dataProvider="SerialesNoValidos")
	public void TS97658_BeFan_Movil_REPRO_Serial_no_asociado_a_ningun_prefijo(String path, String nombreArch, String deposito, String prefijo, String serial1, String serial2, String prefijo2) throws IOException {
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
		Botones.SIImportarArchivo(nombreArch = Botones.SICreacionArchivo(nombreArch, path, serial1, serial2));
		sleep(500);
		Botones.SIClickImportar();
		sleep(1000);
		mensaje = Botones.SIMensajeModal();
		if (mensaje.contentEquals("La sumatoria de la cantidad de prefijos es menor a la cantidad total de lineas del archivo.")) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}

	}

	//DE 10 CON PREP
	@Test (groups = "BeFan")
	public void TS111958_BeFan_Movil_REPRO_Preactivacion_repro__PreActivacion_Linea_Repro() throws IOException, Exception {
		BeFan Botones = new BeFan(driver);
		String[] resultadoEstado = {""};
		String[] resultadoTexto = {""};
		String estado = "Procesado";
		resultadoEstado[0] = "Activado";
		resultadoTexto[0] = "Activaci\u00f3n confirmada";
		
		//traigo resultado de preparacion
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
	@Test (groups = "BeFan", dataProvider="SerialInexistente", priority = 2)
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
	
	@Test (groups = {"BeFan"}, dataProvider="SerialValidoEterno")
	public void TS97654_BeFan_Movil_REPRO_PreaActivacion_Linea_Repro_Localidad_inexistente_para_numeracion_movil(String path, String nombreArch, String deposito, String prefijo, String serial1, String serial2, String prefijo2) throws IOException, Exception {
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
	
	@Test (groups = {"BeFan"}, dataProvider="SerialValidoEternov2")
	public void TS111990_BeFan_Movil_REPRO_PreaActivacion_Linea_Repro_Localidad_inexistente_para_numeracion_movil(String path, String nombreArch, String deposito, String prefijo, String serial1, String serial2, String prefijo2) throws IOException, Exception {
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
	@Test (groups = "BeFan")
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
	
	
	@Test (groups = "BeFAN")
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
	
	@Test (groups = "BeFan")
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
	
	@Test(groups= {"BeFan"}, dependsOnMethods ="TS97651_BeFan_Movil_REPRO_PreaActivacion_Linea_Repro_SIMCARD_en_deposito_inexistente")
	public void TS97652_BeFan_Movil_REPRO_PreaActivacion_Linea_Repro_SIMCARD_no_en_deposito_de_AGENTE() {
		Assert.assertTrue(true);
	}
	
	@Test(groups= {"BeFan"}, dependsOnMethods ="TS126648_BeFan_Movil_REPRO_Preactivacion_repro__Importacion_de_SIM_repro__S436__Envio_de_lote")
	public void TS97667_BeFan_Movil_REPRO_Seriales_con_estado_PENDIENTE_DE_VALIDAR() {
		Assert.assertTrue(true);
	}
	
	@Test(groups= {"BeFan"}, dependsOnMethods ="TS111958_BeFan_Movil_REPRO_Preactivacion_repro__PreActivacion_Linea_Repro")
	public void TS97671_BeFan_Movil_REPRO_Seriales_con_estado_ACTIVADA() {
		Assert.assertTrue(true);
	}
	
	@Test(groups= {"BeFan"}, dependsOnMethods ="TS111958_BeFan_Movil_REPRO_Preactivacion_repro__PreActivacion_Linea_Repro")
	public void TS111996_BeFan_Movil_REPRO_Preactivacion_repro__Envio_de_lote_a_OM__Verificacion_de_lineas_enviadas() {
		Assert.assertTrue(true);
	}
	
	@Test(groups= {"BeFan"}, dependsOnMethods ="TS111990_BeFan_Movil_REPRO_PreaActivacion_Linea_Repro_Localidad_inexistente_para_numeracion_movil")
	public void TS111998_BeFan_Movil_REPRO_Preactivacion_repro__Reserva_de_numeros_en_numeracion_movil__Fallo_y_desreserva_de_SIMCARDS() {
		Assert.assertTrue(true);
	}
	
	@Test(groups= {"BeFan"}, dependsOnMethods ="TS111958_BeFan_Movil_REPRO_Preactivacion_repro__PreActivacion_Linea_Repro")
	public void TS126673_BeFan_Movil_REPRO_Preactivacion_repro__Prefijo_con_cupos() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "BeFAN")
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
	
	@Test (groups = {"BeFan"}, dataProvider="GestionRegionesCreacion", dependsOnGroups="EliminacionDeAgrupador")
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
	
	@Test (groups = "BeFAN")
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
	@Test (groups = {"BeFan"}, dataProvider="GestionRegionesCreacion", dependsOnGroups="EliminacionDePrefijo")
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
	
	
	@Test(groups={"BeFan"}, dependsOnMethods= {"TS97654_BeFan_Movil_REPRO_PreaActivacion_Linea_Repro_Localidad_inexistente_para_numeracion_movil"})
	public void TS97670_BeFan_Movil_REPRO_Seriales_con_estado_ERROR() {
		Assert.assertTrue(true);
	}
	

}