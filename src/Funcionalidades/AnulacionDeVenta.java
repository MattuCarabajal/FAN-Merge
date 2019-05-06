package Funcionalidades;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.CustomerCare;
import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import Tests.TestBase;

public class AnulacionDeVenta extends TestBase {

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	@BeforeClass (alwaysRun = true)
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		loginOOCC(driver);
		sleep(15000);
		cc.irAConsolaFAN();	
		driver.switchTo().defaultContent();
		sleep(6000);
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
	
	@Test (groups = {"GestionesPerfilOficina", "AnulacionDeVenta", "E2E","Ciclo4"}, dataProvider = "CuentaAnulacionDeVenta")
	public void Anulacion_De_Venta(String sDNI) {
		imagen = "Anulacion_De_Venta";
		detalles = null;
		detalles = imagen + " - DNI: " + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("anulaci\u00f3n de ordenes");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-select.ng-pristine.ng-untouched.ng-valid.ng-not-empty")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral")), "equals", "anulaci\u00f3n de venta");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("AnnulmentReasonSelect")));
		selectByText(driver.findElement(By.id("AnnulmentReasonSelect")), "Arrepentimiento");
		driver.findElement(By.id("AnnulmentReason_nextBtn")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.xpath("//*[@id=\"ep\"]/div[2]/div[2]/table")));
		String gestion = driver.findElement(By.xpath("//*[@id=\"ep\"]/div[2]/div[2]/table")).findElements(By.tagName("tr")).get(4).getText();
		Assert.assertTrue(gestion.contains("Estado") && (gestion.contains("Cancelada") || gestion.contains("Cancelled")));
	}
}