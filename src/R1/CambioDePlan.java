package R1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.ContactSearch;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.TestBase;

public class CambioDePlan extends TestBase {

	private WebDriver driver;
	private LoginFw log;
	private GestionDeClientes_Fw ges;
	private ContactSearch contact;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	private String fecha = fechaCapro(25);
	
	
	//@BeforeClass (alwaysRun = true)
	public void Sit03() {
		driver = setConexion.setupEze();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		contact = new ContactSearch(driver);
		log.LoginSit03();
	}

	//@BeforeClass(groups = "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		contact = new ContactSearch(driver);
		log = new LoginFw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
	}

	//@BeforeClass (groups = "PerfilAgente")
	public void initAgente() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		log = new LoginFw(driver);
		contact = new ContactSearch(driver);
		log.loginAgente();
		ges.irAConsolaFAN();
	}

	@BeforeMethod(alwaysRun = true)
	public void setup() {
		detalles = null;
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
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
	public void quit(){
		driver.quit();
		sleep(5000);
	}
	
	
	private boolean cambioDePlan(String tipoDNI, String DNI, String plan, String numLinea) {
		ges.BuscarCuenta(tipoDNI, DNI);
		ges.irAGestionEnCardPorNumeroDeLinea("Cambio de Plan", numLinea);
		sleep(7000);
		cambioDeFrame(driver, By.id("OrderRequestDate"),0);
		sleep(10000);
		cambioDeFrame(driver, By.id("Request date_nextBtn"),0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("OrderRequestDate")));
		driver.findElement(By.id("OrderRequestDate")).sendKeys(fecha);
		driver.findElement(By.id("Request date_nextBtn")).click();
		sleep(120000);
		cambioDeFrame(driver,By.id("TargetOffer_nextBtn"),0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.className("ScrollWindow")));
		sleep(9000);
		List<WebElement> planes = driver.findElements(By.cssSelector(".slds-grid.slds-box.vlc-slds-selectableItem.arrowup"));
		for (WebElement p : planes){
			if (p.getText().toLowerCase().contains(plan.toLowerCase()))
				p.click();
		}
		driver.findElement(By.id("TargetOffer_nextBtn")).click();
		sleep(80000);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Comparision_nextBtn")));
		driver.findElement(By.id("Comparision_nextBtn")).click();
		sleep(29000);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Summary_nextBtn")));
		driver.findElement(By.id("Summary_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("AccountData_nextBtn")));
		sleep(5000);
		driver.findElement(By.id("AccountData_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("MethodSelection_nextBtn")));
		contact.tipoValidacion("documento");
		File directory = new File("Dni.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("nextBtn-label")));
		driver.findElement(By.id("nextBtn-label")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(".slds-button.slds-button_brand")));
		sleep(8500);
		driver.findElement(By.cssSelector(".slds-button.slds-button_brand")).click();
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
		sleep(120000);
		ges.BuscarCuenta(tipoDNI, DNI);
		return ges.compararMegasEnCardPorLinea(numLinea);	
	}
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"PerfilOficina", "R1"})
	public void TS145168_CRM_Pospago_SalesCPQ_Cambio_de_plan_OOCC_DNI_de_Plan_con_Tarjeta_a_APRO2() {
		imagen = "TS145168";
		Assert.assertTrue(cambioDePlan("DNI", "57132591", "conexi\u00f3n control abono s", "3861453936"));		
	}
	
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test (groups = {"PerfilAgente", "R1"})
	public void TS169796_CRM_Pospago_SalesCPQ_Cambio_de_plan_Agente_DNI_de_Plan_con_Tarjeta_Repro_a_APRO2() {
		imagen = "TS169796";
		Assert.assertTrue(cambioDePlan("DNI", "57132590", "conexi\u00f3n control abono s", "3861453831"));		
	}
}