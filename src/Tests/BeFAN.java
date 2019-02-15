package Tests;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Pages.setConexion;

public class BeFAN extends TestBase {
		
	private WebDriver driver;
	
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
	

	@BeforeClass (alwaysRun = true)
	public void init() {
		driver = setConexion.setupEze();
		loginBeFAN(driver);
	}
	
	@AfterMethod (alwaysRun = true)
	public void after() {
		driver.get(TestBase.urlBeFAN);
		sleep(3000);
	}
	
	@AfterClass (alwaysRun = true)
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
}
