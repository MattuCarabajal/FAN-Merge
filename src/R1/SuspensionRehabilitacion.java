package R1;

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

public class SuspensionRehabilitacion extends TestBase {

	private WebDriver driver;
	private CustomerCare cc;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private GestionDeClientes_Fw ges;
	private LoginFw log ;
	String detalles;
	
	
	@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
	}

	//@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginTelefonico();
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
	
	@Test (groups = {"PerfilOficina", "R1"}, dataProvider = "CuentaSuspensionApro")
	public void TS148711_CRM_Movil_Mix_Suspension_por_Siniestro_Hurto_Linea_Titular_Presencial(String sDNI, String sLinea, String sProvincia, String sLocalidad, String sPartido) {
		imagen = "TS_148711";
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
		sleep(2000);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step6-Summary_nextBtn")));
		clickBy(driver, By.id("Step6-Summary_nextBtn"), 0);
		esperarElemento(driver,By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done']"), -50);
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
		String verificacion = driver.findElement(By.xpath("//div[@class = 'card-info-hybrid']//ul[@class = 'details']//span[@class = 'imagre']")).getText();
		Assert.assertTrue(verificacion.equals("Suspendido"));
	}
	
	@Test (groups = {"PerfilOficina","R1"}, dataProvider="CuentaHabilitacion")
	public void TS148676_CRM_Movil_Mix_Rehabilitacion_por_Siniestro_Presencial_Hurto_Linea(String sDNI, String sLinea) {
		imagen = "TS148676";
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
		for (int i = 0; i<10; i++ ) {
			cambioDeFrame(driver, By.id("Case_body"),0);
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
		String verificacion = driver.findElement(By.xpath("//div[@class ='card-info-hybrid']//ul[@class = 'details']//span[@class = 'imagre']")).getText();
		Assert.assertTrue(verificacion.equals("Activo"));
	}
	
	@Test (groups = {"PerfilOficina","R1"}, dataProvider="CuentaHabilitacion")
	public void TS148675_CRM_Movil_Mix_Rehabilitacion_por_Siniestro_Presencial_Robo_Linea(String sDNI, String sLinea) {
		imagen = "TS148675";
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
		for (int i = 0; i<10; i++ ) {
			cambioDeFrame(driver, By.id("Case_body"),0);
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
		String verificacion = driver.findElement(By.xpath("//div[@class ='card-info-hybrid']//ul[@class = 'details']//span[@class = 'imagre']")).getText();
		Assert.assertTrue(verificacion.equals("Activo"));
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"PerfilTelefonico", "R1"}, dataProvider = "CuentaSuspensionApro")
	public void TS148626_CRM_Movil_Mix_Suspension_por_Siniestro_Hurto_Linea_No_Titular_Telefonico(String sDNI, String sLinea, String sProvincia, String sLocalidad, String sPartido) {
		imagen = "TS148626";
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
		sleep(2000);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step6-Summary_nextBtn")));
		clickBy(driver, By.id("Step6-Summary_nextBtn"), 0);
		esperarElemento(driver,By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done']"), -50);
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
			if (estado.equalsIgnoreCase("realizada exitosa"))
				gestion = true;
			else {
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
		String verificacion = driver.findElement(By.xpath("//div[@class = 'card-info-hybrid']//ul[@class = 'details']//span[@class = 'imagre']")).getText();
		Assert.assertTrue(verificacion.equals("Suspendido"));
	}
	
	@Test (groups = {"PerfilTelefonico", "R1"}, dataProvider = "CuentaSuspensionApro")
	public void TS148622_CRM_Movil_Mix_Suspension_por_Siniestro_Robo_Linea_Titular_Telefonico(String sDNI, String sLinea, String sProvincia, String sLocalidad, String sPartido) {
		imagen = "TS147604";
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
		selectByText(driver.findElement(By.cssSelector("[id = 'Radio3-ReasonSuspension']")), "Robo");
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
		esperarElemento(driver,By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done']"), -50);
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
			if (estado.equalsIgnoreCase("realizada exitosa"))
				gestion = true;
			else {
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
		String verificacion = driver.findElement(By.xpath("//div[@class = 'card-info-hybrid']//ul[@class = 'details']//span[@class = 'imagre']")).getText();
		Assert.assertTrue(verificacion.equals("Suspendido"));
	}
}