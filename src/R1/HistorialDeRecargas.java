package R1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.CustomerCare;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class HistorialDeRecargas extends TestBase {

	private WebDriver driver;
	private CustomerCare cc;
	private CBS_Mattu cbsm;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private GestionDeClientes_Fw ges;
	private LoginFw log;
	String detalles;
	
	//@BeforeClass (groups= "PerfilOficina")
	public void Sit03() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.LoginSit03();
		//ges.irAConsolaFAN();
	}
	
	//@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();	
	}
	
	@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() {
		driver = setConexion.setupEze();
		sleep(5000);
		cc = new CustomerCare(driver);
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginTelefonico();
		ges.irAConsolaFAN();	
	}
	
	@BeforeMethod (alwaysRun = true)
	public void setup() {
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
		sleep(5000);
	}

	@AfterMethod (alwaysRun = true)
	public void after() throws IOException {
		guardarListaTxt(sOrders);
		sOrders.clear();
		tomarCaptura(driver,imagen);
		sleep(2000);
	}

	//@AfterClass(alwaysRun = true)
	public void quit() {
		driver.quit();
		sleep(5000);
	}
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"PerfilOficina", "R1"}, dataProvider = "RecargasHistorialApro")
	public void TS148835_CRM_Movil_Mix_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_TODOS_Crm_OC(String sDNI , String sLinea) {
		imagen = "TS148835";
		detalles = imagen + " -Historial de recargas - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCardPorNumeroDeLinea("Historiales", sLinea);
		cc.verificacionDeHistorial("Historial de packs");
		cc.verificacionDeHistorial("Historial de ajustes");
		cc.verificacionDeHistorial("Historial de recargas");
		cc.verificacionDeHistorial("Historial de recargas S.O.S");
		cc.seleccionDeHistorial("historial de recargas");
		cambioDeFrame(driver, By.id("text-input-03"), 0);
		driver.findElement(By.id("text-input-03")).click();
		driver.findElement(By.xpath("//*[text() = 'Todos']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-p-bottom--small slds-p-left--medium slds-p-right--medium']")));
		ArrayList<String> canales = new ArrayList<String>(Arrays.asList("Recarga Online", "Otros", "*111#", "SMS", "Atenci\u00f3n al cliente / Mi Personal"));
		List<WebElement> recargasRealizadas = driver.findElements(By.cssSelector("[class='slds-p-bottom--small slds-p-left--medium slds-p-right--medium'] tbody tr"));
		for (WebElement recargaRealizada : recargasRealizadas) {
			String canal = recargaRealizada.findElements(By.tagName("td")).get(2).getText();
			Assert.assertTrue(canales.contains(canal));
		}
		ges.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[class = 'slds-p-bottom--small slds-p-left--medium slds-p-right--medium'] table thead [class = 'slds-text-heading--label'] [class = 'slds-p-bottom--small slds-is-sorted--asc']")));
		List<WebElement> detalles = driver.findElements(By.cssSelector("[class = 'slds-p-bottom--small slds-p-left--medium slds-p-right--medium'] table thead [class = 'slds-text-heading--label'] [class = 'slds-p-bottom--small slds-is-sorted--asc']"));
		List <String> detallesAConsultar = new ArrayList<String>(Arrays.asList("FECHA", "VENCIMIENTO", "CANAL", "BENEFICIOS", "DESCRIPCI\u00d3N", "MONTO"));
		for (int i = 0; i < detalles.size(); i++) {
			Assert.assertTrue(detalles.get(i).getText().toLowerCase().contains(detallesAConsultar.get(i).toLowerCase()));
		}
		Assert.assertTrue(driver.findElement(By.cssSelector("[class = 'slds-grid slds-p-top--small page-options'] [class = 'slds-select_container'] select")).isDisplayed());
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"PerfilTelefonico", "R1"}, dataProvider = "RecargasHistorialApro")
	public void TS148824_CRM_Movil_Mix_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_Crm_Telefonico(String sDNI , String sLinea) {
		detalles = imagen+"-Historial De Recarga-DNI:"+sDNI;
		String dia = fechaDeHoy();
		String monto = "10000000";
		cbsm.Servicio_Recharge(sLinea,monto,"2");
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCardPorNumeroDeLinea("Historiales", sLinea);
		cc.verificacionDeHistorial("Historial de packs");
		cc.verificacionDeHistorial("Historial de ajustes");
		cc.verificacionDeHistorial("Historial de recargas");
		cc.verificacionDeHistorial("Historial de recargas S.O.S");
		cc.seleccionDeHistorial("historial de recargas");
		cambioDeFrame(driver, By.id("text-input-03"), 0);
		driver.findElement(By.id("text-input-03")).click();
		List<WebElement> atencion = driver.findElement(By.cssSelector(".slds-dropdown__list.slds-dropdown--length-5")).findElements(By.tagName("li"));
		for(WebElement a : atencion){
			if(a.getText().contains("SMS"))
				a.click();
		}
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();	
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")));
		boolean verif = false;
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+ driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).getLocation().y+" )");
		WebElement verificacion = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
		for(WebElement x : verificacion.findElements(By.tagName("tr"))) {
			if(x.findElements(By.tagName("td")).get(0).getText().contains(dia) && x.getText().contains("SMS"))
				verif = true;
		}
		Assert.assertTrue(verif);
		boolean a = false;
		List <WebElement> fecha = driver.findElements(By.cssSelector(".slds-truncate.slds-th__action"));		
		for(WebElement x : fecha) {
			if(x.getText().toLowerCase().equals("canal")) {
				x.click();
				ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")));
				Assert.assertTrue(driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).isDisplayed());
				a= true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"PerfilTelefonico", "R1"}, dataProvider = "RecargasHistorialApro")
	public void TS148836_CRM_Movil_Mix_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_TODOS_Crm_Telefonico(String sDNI, String sLinea) {
		imagen = "TS148836";
		detalles = imagen + " -Historial de recargas - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCardPorNumeroDeLinea("Historiales", sLinea);
		cc.verificacionDeHistorial("Historial de packs");
		cc.verificacionDeHistorial("Historial de ajustes");
		cc.verificacionDeHistorial("Historial de recargas");
		cc.verificacionDeHistorial("Historial de recargas S.O.S");
		cc.seleccionDeHistorial("historial de recargas");
		cambioDeFrame(driver, By.id("text-input-03"), 0);
		driver.findElement(By.id("text-input-03")).click();
		driver.findElement(By.xpath("//*[text() = 'Todos']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-p-bottom--small slds-p-left--medium slds-p-right--medium']")));
		ArrayList<String> canales = new ArrayList<String>(Arrays.asList("Recarga Online", "Otros", "*111#", "SMS", "Atenci\u00f3n al cliente / Mi Personal"));
		List<WebElement> recargasRealizadas = driver.findElements(By.cssSelector("[class='slds-p-bottom--small slds-p-left--medium slds-p-right--medium'] tbody tr"));
		for (WebElement recargaRealizada : recargasRealizadas) {
			String canal = recargaRealizada.findElements(By.tagName("td")).get(2).getText();
			Assert.assertTrue(canales.contains(canal));
		}
		ges.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[class = 'slds-p-bottom--small slds-p-left--medium slds-p-right--medium'] table thead [class = 'slds-text-heading--label'] [class = 'slds-p-bottom--small slds-is-sorted--asc']")));
		List<WebElement> detalles = driver.findElements(By.cssSelector("[class = 'slds-p-bottom--small slds-p-left--medium slds-p-right--medium'] table thead [class = 'slds-text-heading--label'] [class = 'slds-p-bottom--small slds-is-sorted--asc']"));
		List <String> detallesAConsultar = new ArrayList<String>(Arrays.asList("FECHA", "VENCIMIENTO", "CANAL", "BENEFICIOS", "DESCRIPCI\u00d3N", "MONTO"));
		for (int i = 0; i < detalles.size(); i++) {
			Assert.assertTrue(detalles.get(i).getText().toLowerCase().contains(detallesAConsultar.get(i).toLowerCase()));
		}
		Assert.assertTrue(driver.findElement(By.cssSelector("[class = 'slds-grid slds-p-top--small page-options'] [class = 'slds-select_container'] select")).isDisplayed());
	}
}