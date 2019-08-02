package MVP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.CustomerCare;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.TestBase;

public class Rehabilitacion extends TestBase {

	private WebDriver driver;
	private LoginFw log;
	private GestionDeClientes_Fw ges;
	private CustomerCare cc;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	//@BeforeClass (groups= "PerfilOficina")
	public void initSIT() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.LoginSit();
		ges.irAConsolaFAN();
	}
	
	@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
	}
	
	@BeforeMethod (alwaysRun = true)
	public void setup() {
		detalles = null;
		ges.selectMenuIzq("Inicio");
		ges.cerrarPestaniaGestion(driver);
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
	
	@Test (groups = "PerfilOficina", dataProvider="CuentaHabilitacion")
	public void TS98590_CRM_Movil_REPRO_Rehabilitacion_por_Siniestro_Presencial_Robo_Linea(String sDNI, String sLinea) {
		imagen = "TS98590";
		detalles = imagen + " - Rehabilitacion - DNI: " + sDNI;
		cambioDeFrame(driver,By.id("phSearchInput"),0);
		driver.findElement(By.id("phSearchInput")).sendKeys(sDNI);
		driver.findElement(By.id("phSearchInput")).submit();
		sleep(7500);
		cambioDeFrame(driver,By.id("Contact_body"),0);
		WebElement nombreCuenta = driver.findElement(By.xpath("//*[@id='Contact_body']//tbody/tr[2]//td[2]//a"));
		nombreCuenta.click();
		cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='card-top']")));
		cc.irAGestion("Suspensiones");
		cambioDeFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn"),0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step1-SuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains", "habilitaci\u00f3n");
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step2-AssetTypeSelection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step3-AvailableAssetsSelection_nextBtn")));
		driver.findElement(By.xpath("//*[@class='slds-radio--faux']")).click();
		sleep(2500);
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step6-Summary_nextBtn")));
		sleep(2500);
		driver.findElement(By.id("Step6-Summary_nextBtn")).click();
		sleep(5000);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done']")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header h1")).getText().equalsIgnoreCase("tu solicitud est\u00e1 siendo procesada."));
		String ncaso = driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header")).getText();
		String numeroCaso = cc.getNumeros(ncaso);
		System.out.println(cc.getNumeros(ncaso));
		ges.cerrarPestaniaGestion(driver);
		driver.findElement(By.id("phSearchInput")).clear();
		driver.findElement(By.id("phSearchInput")).sendKeys(numeroCaso);
		driver.findElement(By.id("phSearchInput")).submit();
		boolean z = false;
		for (int i = 0; i < 10; i++) {
			cambioDeFrame(driver, By.id("Case_body"), 0);
			WebElement status = driver.findElement(By.xpath("//*[@id='Case_body']//tr[2]//td[3]"));
			if (status.getText().contains("Realizada exitosa"))
				z = true;
			else {
				sleep(5000);
				driver.navigate().refresh();
			}
		}
		Assert.assertTrue(z);
		ges.cerrarPestaniaGestion(driver);
		ges.irGestionClientes();
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='card-top']")));
		WebElement postestado = driver.findElement(By.xpath("//div[@class = 'card-info']//ul[@class = 'uLdetails']//span[@class = 'imagre']"));
		Assert.assertTrue(postestado.getText().contains("Activo"));		
	}
}