package MVP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.CustomerCare;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.TestBase;

public class Suspension extends TestBase {

	private WebDriver driver;
	private CustomerCare cc;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private GestionDeClientes_Fw ges;
	private LoginFw log ;
	String detalles;
	
	
	//@BeforeClass (groups= "PerfilOficina")
	public void initSIT() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.LoginSit();
		ges.irAConsolaFAN();
	}
	
	@BeforeClass (groups = "PerfilOficina")
	public void initOOCC(){
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
	}

	@BeforeMethod(alwaysRun = true)
	public void setup() {
		detalles = null;
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
	}

	@AfterMethod (alwaysRun = true)
	public void after() throws IOException {
		guardarListaTxt(sOrders);
		sOrders.clear();
		tomarCaptura(driver, imagen);
		sleep(2000);
	}

	// @AfterClass (alwaysRun = true)
	public void quit() {
		driver.quit();
		sleep(5000);
	}
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider = "CuentaSuspension")
	public void TS_98438_CRM_Movil_REPRO_Suspension_por_Siniestro_Hurto_Linea_Titular_Presencial(String sDNI, String sLinea, String sProvincia, String sLocalidad, String sPartido) {
		imagen = "TS_98438";
		detalles = imagen + "- Suspension - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		cc.irAGestion("suspensiones");
		cambioDeFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn"), 0);
		clickBy(driver, By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Suspensi\u00f3n')]"), 0);
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@class ='slds-radio ng-scope']//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text() , 'Linea')]")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("Step3-AvailableAssetsSelection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")),"contains","l\u00ednea: "+sLinea);
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("AccountData_nextBtn")));
		selectByText(driver.findElement(By.cssSelector("[id = 'Radio3-ReasonSuspension']")), "Hurto");
		clickBy(driver, By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Argentina')]"), 0);
		selectByText(driver.findElement(By.cssSelector("[id = 'State']")), sProvincia);
		driver.findElement(By.id("CityTypeAhead")).sendKeys(sLocalidad);
		driver.findElement(By.id("Partido")).sendKeys(sPartido);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Si')]")));
		clickBy(driver, By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Si')]"), 0);
		driver.findElement(By.id("AccountData_nextBtn")).click();
		sleep(3000);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step6-Summary_nextBtn")));
		clickBy(driver, By.id("Step6-Summary_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done']")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header h1")).getText().equalsIgnoreCase("tu solicitud est\u00e1 siendo procesada."));
		sleep(15000);
		String ncaso = driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header")).getText();
		String numeroCaso = cc.getNumeros(ncaso);
		System.out.println(cc.getNumeros(ncaso));
		ges.cerrarPestaniaGestion(driver);
		driver.findElement(By.id("phSearchInput")).sendKeys(numeroCaso);
		driver.findElement(By.id("phSearchInput")).submit();
		boolean gestion = false;
		for (int i = 0; i < 10; i++) {
			cambioDeFrame(driver, By.id("searchPageHolderDiv"), 0);
			String estado = driver.findElement(By.xpath("//*[@id='Case_body']//tr[2]//td[3]")).getText();
			if (estado.equalsIgnoreCase("realizada exitosa")) {
				gestion = true;
			} else {
				sleep(8000);
				driver.navigate().refresh();
			}
		}
		Assert.assertTrue(gestion);
		ges.cerrarPestaniaGestion(driver);
		driver.findElement(By.id("phSearchInput")).clear();
		driver.findElement(By.id("phSearchInput")).sendKeys(sDNI);
		driver.findElement(By.id("phSearchInput")).submit();
		cambioDeFrame(driver, By.id("Contact_body"), 0);
		WebElement nombreCuenta = driver.findElement(By.xpath("//*[@id='Contact_body']//tbody/tr[2]//td[2]//a"));
		nombreCuenta.click(); 
		sleep(5000);
		cambioDeFrame(driver, By.className("card-top"), 0);
		String verificacion = driver.findElement(By.xpath("//section[@class = 'console-card active expired']//div[@class = 'card-info']//ul[@class = 'uLdetails']//span[@class = 'imagre']")).getText();
		Assert.assertTrue(verificacion.equals("Suspendido"));	
	}
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider = "CuentaSuspension")
	public void TS_98442_CRM_Movil_REPRO_Suspension_por_Siniestro_Extravio_Linea_Titular_Presencial(String sDNI, String sLinea, String sProvincia, String sLocalidad, String sPartido) {
		imagen = "TS_98442";
		detalles = imagen + "- Suspension - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		cc.irAGestion("suspensiones");
		cambioDeFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn"), 0);
		clickBy(driver, By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Suspensi\u00f3n')]"), 0);
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@class ='slds-radio ng-scope']//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text() , 'Linea')]")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("Step3-AvailableAssetsSelection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")),"contains","l\u00ednea: "+sLinea);
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("AccountData_nextBtn")));
		selectByText(driver.findElement(By.cssSelector("[id = 'Radio3-ReasonSuspension']")), "Extravio");
		clickBy(driver, By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Argentina')]"), 0);
		selectByText(driver.findElement(By.cssSelector("[id = 'State']")), sProvincia);
		driver.findElement(By.id("CityTypeAhead")).sendKeys(sLocalidad);
		driver.findElement(By.id("Partido")).sendKeys(sPartido);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Si')]")));
		clickBy(driver, By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Si')]"), 0);
		driver.findElement(By.id("AccountData_nextBtn")).click();
		sleep(2000);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step6-Summary_nextBtn")));
		clickBy(driver, By.id("Step6-Summary_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done']")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header h1")).getText().equalsIgnoreCase("tu solicitud est\u00e1 siendo procesada."));
		sleep(20000);
		String ncaso = driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header")).getText();
		String numeroCaso = cc.getNumeros(ncaso);
		System.out.println(cc.getNumeros(ncaso));
		ges.cerrarPestaniaGestion(driver);
		driver.findElement(By.id("phSearchInput")).sendKeys(numeroCaso);
		driver.findElement(By.id("phSearchInput")).submit();
		boolean gestion = false;
		for (int i = 0; i < 10; i++) {
			cambioDeFrame(driver, By.id("searchPageHolderDiv"), 0);
			String estado = driver.findElement(By.xpath("//*[@id='Case_body']//tr[2]//td[3]")).getText();
			if (estado.equalsIgnoreCase("realizada exitosa")) {
				gestion = true;
			} else {
				sleep(8000);
				driver.navigate().refresh();
			}
		}
		Assert.assertTrue(gestion);
		ges.cerrarPestaniaGestion(driver);
		driver.findElement(By.id("phSearchInput")).clear();
		driver.findElement(By.id("phSearchInput")).sendKeys(sDNI);
		driver.findElement(By.id("phSearchInput")).submit();
		cambioDeFrame(driver, By.id("Contact_body"), 0);
		WebElement nombreCuenta = driver.findElement(By.xpath("//*[@id='Contact_body']//tbody/tr[2]//td[2]//a"));
		nombreCuenta.click(); 
		sleep(5000);
		cambioDeFrame(driver, By.className("card-top"), 0);
		String verificacion = driver.findElement(By.xpath("//section[@class = 'console-card active expired']//div[@class = 'card-info']//ul[@class = 'uLdetails']//span[@class = 'imagre']")).getText();
		Assert.assertTrue(verificacion.equals("Suspendido"));
	}
}