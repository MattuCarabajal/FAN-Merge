package R1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.CBS;
import Pages.CustomerCare;
import Pages.Marketing;
import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class ActualizarDatos extends TestBase {

	private WebDriver driver;
	private CustomerCare cc;
	private LoginFw log;
	private GestionDeClientes_Fw ges;
	private CBS_Mattu cbsm;
	private CBS  cbs;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	//@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		cbsm = new CBS_Mattu();
		cbs = new CBS();
		log.loginOOCC();
		ges.irAConsolaFAN();
	}
		
	//@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		cbsm = new CBS_Mattu();
		cbs = new CBS();
		log.loginTelefonico();
		ges.irAConsolaFAN();	
	}
	
	@BeforeClass (groups = "PerfilAgente")
	public void initAgente() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges =  new GestionDeClientes_Fw(driver);
		cbsm = new CBS_Mattu();
		cbs = new CBS();
		log.loginAgente();
		ges.irAConsolaFAN();
	}
	
	@BeforeMethod (alwaysRun = true)
	public void setup() {
		detalles = null;
		ges.selectMenuIzq("Inicio");
		ges.cerrarPestaniaGestion(driver);
		ges.irGestionClientes();
	}

	@AfterMethod (alwaysRun = true)
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
	
	@Test (groups = {"PerfilOficina", "ModificarDatos", "E2E", "R1"}, dataProvider = "CuentaModificacionDeDNI")
	public void TS148950_CRM_Movil_Mix_Modificacion_de_datos_Actualizar_datos_campo_DNI_Cliente_Crm_OC(String sDNI, String sLinea){
		imagen = "TS148950";
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.className("profile-box-details"), 0);
		driver.findElement(By.xpath("//*[@class='profile-box-details']//*[@class='profile-links-wrapper']//div//a[text()='Actualizar Datos']")).click();
		cambioDeFrame(driver, By.id("ClientInformation_nextBtn"),0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("ClientInformation_nextBtn")));
		String dniviejo = driver.findElement(By.id("DocumentNumber")).getAttribute("value");
		dniviejo = dniviejo.substring(0, dniviejo.length()-1);
		System.out.println("Nuevo DNI"+dniviejo);
		driver.findElement(By.id("DocumentNumber")).clear();
		driver.findElement(By.id("DocumentNumber")).sendKeys(dniviejo);
		driver.findElement(By.id("Email")).clear();
		driver.findElement(By.id("Email")).sendKeys("nominacioncrm@gmail.com");
		driver.findElement(By.id("MobilePhone")).clear();
		driver.findElement(By.id("MobilePhone")).sendKeys("1122334455");
		driver.findElement(By.id("ClientInformation_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='ta-care-omniscript-done']//header//*[@class='ng-binding']")));
		Assert.assertTrue(driver.findElement(By.xpath("//*[@class='ta-care-omniscript-done']//header//*[@class='ng-binding']")).getText().toLowerCase().contains("las modificaciones se realizaron con \u00e9xito"));
		String texto = driver.findElement(By.xpath("//*[@class='ta-care-omniscript-done']//header//p//label")).getText();
		String orden = cc.getNumeros(texto);
		System.out.println(orden);
		cc.buscarOrdenDiag(orden+"*");
		boolean h = false;
		cambioDeFrame(driver, By.id("Case_body"),0);
		List<WebElement> status = driver.findElement(By.id("Case_body")).findElements(By.tagName("td"));
		for (WebElement s : status ){
			if (s.getText().toLowerCase().contains("realizada exitosa"))
				h = true;
		}
		Assert.assertTrue(h);
		Assert.assertTrue(cbs.ObtenerValorResponse(cbsm.Servicio_QueryCustomerInfo(sLinea), "bcc:IDNumber").contains(dniviejo));		
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"PerfilTelefonico", "R1"}, dataProvider = "CuentaModificacionDeDatos")
	public void	TS148960_CRM_Movil_Mix_Modificacion_de_datos_Actualizar_los_datos_del_cliente_completos_Crm_Telefonico(String sDNI, String sLinea){
		imagen = "TS148960";
		detalles = imagen + " -ActualizarDatos-DNI: "+ sDNI;
		String nuevoNombre = "Otro";
		String nuevoApellido = "Apellido";
		String nuevoNacimiento = "10/10/1982";
		String nuevoMail = "nominacioncrm@gmail.com";
		String nuevoPhone = "3574409239";
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.className("profile-box-details"), 0);
		driver.findElement(By.xpath("//*[@class='profile-box-details']//*[@class='profile-links-wrapper']//div//a[text()='Actualizar Datos']")).click();
		cambioDeFrame(driver, By.id("ClientInformation_nextBtn"),0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("ClientInformation_nextBtn")));
		if (driver.findElement(By.id("MobilePhone")).getAttribute("value").isEmpty()){
			driver.findElement(By.id("MobilePhone")).clear();
			driver.findElement(By.id("MobilePhone")).sendKeys("5214864852");
		}
		String nombre = driver.findElement(By.id("FirstName")).getAttribute("value");
		String apellido = driver.findElement(By.id("LastName")).getAttribute("value");
		String fechaNacimiento = driver.findElement(By.id("Birthdate")).getAttribute("value");
		String mail = driver.findElement(By.id("Email")).getAttribute("value");
		String phone = driver.findElement(By.id("MobilePhone")).getAttribute("value");
		driver.findElement(By.id("FirstName")).clear();
		driver.findElement(By.id("FirstName")).sendKeys(nuevoNombre);
		driver.findElement(By.id("LastName")).clear();
		driver.findElement(By.id("LastName")).sendKeys(nuevoApellido);
		driver.findElement(By.id("Birthdate")).clear();
		driver.findElement(By.id("Birthdate")).sendKeys(nuevoNacimiento);
		driver.findElement(By.id("Email")).clear();
		driver.findElement(By.id("Email")).sendKeys(nuevoMail);
		driver.findElement(By.id("MobilePhone")).clear();
		driver.findElement(By.id("MobilePhone")).sendKeys(nuevoPhone);
		driver.findElement(By.id("ClientInformation_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.presenceOfElementLocated(By.className("ta-care-omniscript-done")));
		Assert.assertTrue(driver.findElement(By.xpath("//*[@class='ta-care-omniscript-done']//header//*[@class='ng-binding']")).getText().toLowerCase().contains("las modificaciones se realizaron con \u00e9xito"));
		String texto = driver.findElement(By.xpath("//*[@class='ta-care-omniscript-done']//header//p//label")).getText();
		String orden = cc.getNumeros(texto);
		detalles +="-Orden:"+orden;		
		cc.buscarOrdenDiag(orden+"*");
		boolean h = false;
		cambioDeFrame(driver, By.id("Case_body"),0);
		List<WebElement> status = driver.findElement(By.id("Case_body")).findElements(By.tagName("td"));
		for (WebElement s : status ){
			if (s.getText().toLowerCase().contains("realizada exitosa"))
				h = true;
		}
		driver.switchTo().defaultContent();
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class*='x-tab-strip-closable']"),0));
		List<WebElement> pestana = driver.findElements(By.cssSelector("[class*='x-tab-strip-closable']"));
		for (WebElement p : pestana){
			if (p.getText().toLowerCase().contains(orden))
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", p.findElement(By.className("x-tab-strip-close")));
		}
		Assert.assertTrue(cbs.ObtenerValorResponse(cbsm.Servicio_QueryCustomerInfo(sLinea), "bcc:Email").equals(nuevoMail));
		Assert.assertTrue(cbs.ObtenerValorResponse(cbsm.Servicio_QueryCustomerInfo(sLinea), "bcc:FirstName").equalsIgnoreCase(nuevoNombre));
		Assert.assertTrue(cbs.ObtenerValorResponse(cbsm.Servicio_QueryCustomerInfo(sLinea), "bcc:LastName").equalsIgnoreCase(nuevoApellido));
		Assert.assertTrue(cbs.ObtenerValorResponse(cbsm.Servicio_QueryCustomerInfo(sLinea), "bcc:Birthday").contains("19821010"));
		cambioDeFrame(driver, By.className("profile-box-details"), 0);
		driver.findElement(By.xpath("//*[@class='profile-box-details']//*[@class='profile-links-wrapper']//div//a[text()='Actualizar Datos']")).click();
		cambioDeFrame(driver, By.id("ClientInformation_nextBtn"),0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("ClientInformation_nextBtn")));
		Assert.assertTrue(driver.findElement(By.id("FirstName")).getAttribute("value").equals(nuevoNombre));
		Assert.assertTrue(driver.findElement(By.id("LastName")).getAttribute("value").equals(nuevoApellido));
		Assert.assertTrue(driver.findElement(By.id("Birthdate")).getAttribute("value").equals(nuevoNacimiento));
		Assert.assertTrue(driver.findElement(By.id("Email")).getAttribute("value").equals(nuevoMail));
		Assert.assertTrue(driver.findElement(By.id("MobilePhone")).getAttribute("value").equals(nuevoPhone));
		Assert.assertTrue(driver.findElement(By.id("DocumentType")).getAttribute("disabled").equals("true"));
		driver.findElement(By.id("FirstName")).clear();
		driver.findElement(By.id("FirstName")).sendKeys(nombre);
		driver.findElement(By.id("LastName")).clear();
		driver.findElement(By.id("LastName")).sendKeys(apellido);
		driver.findElement(By.id("Birthdate")).clear();
		driver.findElement(By.id("Birthdate")).sendKeys(fechaNacimiento);
		driver.findElement(By.id("Email")).clear();
		driver.findElement(By.id("Email")).sendKeys(mail);
		driver.findElement(By.id("MobilePhone")).clear();
		driver.findElement(By.id("MobilePhone")).sendKeys(phone);
		driver.findElement(By.id("ClientInformation_nextBtn")).click();			
	}
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test (groups = {"PerfilAgente", "R1"}, dataProvider = "CuentaModificacionDeDatos")
	public void TS148961_CRM_Movil_Mix_Modificacion_de_datos_Actualizar_los_datos_del_cliente_completos_Crm_Agente(String sDNI, String sLinea){
		imagen = "TS148961";
		detalles = null;
		detalles = imagen + " -ActualizarDatos-DNI: "+ sDNI;
		String nuevoNombre = "Otro";
		String nuevoApellido = "Apellido";
		String nuevoNacimiento = "10/10/1982";
		String nuevoMail = "nominacioncrm@gmail.com";
		String nuevoPhone = "3574409239";
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.className("profile-box-details"), 0);
		driver.findElement(By.xpath("//*[@class='profile-box-details']//*[@class='profile-links-wrapper']//div//a[text()='Actualizar Datos']")).click();
		cambioDeFrame(driver, By.id("ClientInformation_nextBtn"),0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("ClientInformation_nextBtn")));
		if (driver.findElement(By.id("MobilePhone")).getAttribute("value").isEmpty()){
			driver.findElement(By.id("MobilePhone")).clear();
			driver.findElement(By.id("MobilePhone")).sendKeys("5214864852");
		}
		String nombre = driver.findElement(By.id("FirstName")).getAttribute("value");
		String apellido = driver.findElement(By.id("LastName")).getAttribute("value");
		String fechaNacimiento = driver.findElement(By.id("Birthdate")).getAttribute("value");
		String mail = driver.findElement(By.id("Email")).getAttribute("value");
		String phone = driver.findElement(By.id("MobilePhone")).getAttribute("value");
		driver.findElement(By.id("FirstName")).clear();
		driver.findElement(By.id("FirstName")).sendKeys(nuevoNombre);
		driver.findElement(By.id("LastName")).clear();
		driver.findElement(By.id("LastName")).sendKeys(nuevoApellido);
		driver.findElement(By.id("Birthdate")).clear();
		driver.findElement(By.id("Birthdate")).sendKeys(nuevoNacimiento);
		driver.findElement(By.id("Email")).clear();
		driver.findElement(By.id("Email")).sendKeys(nuevoMail);
		driver.findElement(By.id("MobilePhone")).clear();
		driver.findElement(By.id("MobilePhone")).sendKeys(nuevoPhone);
		driver.findElement(By.id("ClientInformation_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.presenceOfElementLocated(By.className("ta-care-omniscript-done")));
		Assert.assertTrue(driver.findElement(By.xpath("//*[@class='ta-care-omniscript-done']//header//*[@class='ng-binding']")).getText().toLowerCase().contains("las modificaciones se realizaron con \u00e9xito"));
		String texto = driver.findElement(By.xpath("//*[@class='ta-care-omniscript-done']//header//p//label")).getText();
		String orden = cc.getNumeros(texto);
		detalles +="-Orden:"+orden;		
		cc.buscarOrdenDiag(orden+"*");
		boolean h = false;
		cambioDeFrame(driver, By.id("Case_body"),0);
		List<WebElement> status = driver.findElement(By.id("Case_body")).findElements(By.tagName("td"));
		for (WebElement s : status ){
			if (s.getText().toLowerCase().contains("realizada exitosa"))
				h = true;
		}
		driver.switchTo().defaultContent();
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class*='x-tab-strip-closable']"),0));
		List<WebElement> pestana = driver.findElements(By.cssSelector("[class*='x-tab-strip-closable']"));
		for (WebElement p : pestana){
			if (p.getText().toLowerCase().contains(orden))
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", p.findElement(By.className("x-tab-strip-close")));
		}
		Assert.assertTrue(cbs.ObtenerValorResponse(cbsm.Servicio_QueryCustomerInfo(sLinea), "bcc:Email").equals(nuevoMail));
		Assert.assertTrue(cbs.ObtenerValorResponse(cbsm.Servicio_QueryCustomerInfo(sLinea), "bcc:FirstName").equalsIgnoreCase(nuevoNombre));
		Assert.assertTrue(cbs.ObtenerValorResponse(cbsm.Servicio_QueryCustomerInfo(sLinea), "bcc:LastName").equalsIgnoreCase(nuevoApellido));
		Assert.assertTrue(cbs.ObtenerValorResponse(cbsm.Servicio_QueryCustomerInfo(sLinea), "bcc:Birthday").contains("19821010"));
		cambioDeFrame(driver, By.className("profile-box-details"), 0);
		driver.findElement(By.xpath("//*[@class='profile-box-details']//*[@class='profile-links-wrapper']//div//a[text()='Actualizar Datos']")).click();
		cambioDeFrame(driver, By.id("ClientInformation_nextBtn"),0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("ClientInformation_nextBtn")));
		Assert.assertTrue(driver.findElement(By.id("FirstName")).getAttribute("value").equals(nuevoNombre));
		Assert.assertTrue(driver.findElement(By.id("LastName")).getAttribute("value").equals(nuevoApellido));
		Assert.assertTrue(driver.findElement(By.id("Birthdate")).getAttribute("value").equals(nuevoNacimiento));
		Assert.assertTrue(driver.findElement(By.id("Email")).getAttribute("value").equals(nuevoMail));
		Assert.assertTrue(driver.findElement(By.id("MobilePhone")).getAttribute("value").equals(nuevoPhone));
		Assert.assertTrue(driver.findElement(By.id("DocumentType")).getAttribute("disabled").equals("true"));
		driver.findElement(By.id("FirstName")).clear();
		driver.findElement(By.id("FirstName")).sendKeys(nombre);
		driver.findElement(By.id("LastName")).clear();
		driver.findElement(By.id("LastName")).sendKeys(apellido);
		driver.findElement(By.id("Birthdate")).clear();
		driver.findElement(By.id("Birthdate")).sendKeys(fechaNacimiento);
		driver.findElement(By.id("Email")).clear();
		driver.findElement(By.id("Email")).sendKeys(mail);
		driver.findElement(By.id("MobilePhone")).clear();
		driver.findElement(By.id("MobilePhone")).sendKeys(phone);
		driver.findElement(By.id("ClientInformation_nextBtn")).click();
	}	
}