package Tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.BeFan;


public class BeFanTest extends TestBase {
	
	private WebDriver driver;
	
	@BeforeClass
	public void init() throws Exception {
		
		driver=BeFan.initDriver();
		BeFan.irABefan();
	    BeFan page=new BeFan(driver);
	    page.loginBefan("UAT195528", "Testa10k");
	    Thread.sleep(3000);
	//    page.opcionDeSim("Importación");
	//    page.selectPrefijo("351");
	//    page.setCantidad("2");
	//    page.clickEnBoton("Agregar");
	//    page.adjuntarArchivoBefan("C:\\Pruebaautomatizacion.txt");
	//    page.clickEnBoton("Importar");
	//    page.clickAceptar();

	}
	
	
	@Test
	public void TS112047_BeFan_Movil_REPRO_Preactivacion_repro_Busqueda_de_archivos_Agente_Formato() throws InterruptedException {
		BeFan page=new BeFan(driver);
		page.opcionDeSim("Gestión");
		page.selectEstado("Procesado");
		page.setNombreArchivo("Pruebaautomatizacion.txt");
		//String f1="01/04/2018", f2="14/04/2018";
		//page.setFechaDesde();
		//page.SeleccionarFechas(f1,f2);
		page.clickEnBoton("Buscar");

	}
	
}
	

