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
import Tests.CBS_Mattu;
import Tests.TestBase;

public class CambioDeSimcard extends TestBase {

	private WebDriver driver;
	private CustomerCare cc;
	private LoginFw log;
	private GestionDeClientes_Fw ges;
	private CBS_Mattu cbsm;
	private List<String> sOrders = new ArrayList<String>();
	String imagen;
	String detalles;
	
	
	@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		cbsm = new CBS_Mattu();
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
		log.loginTelefonico();
		ges.irAConsolaFAN();	
	}
	
	//@BeforeClass (groups = "PerfilAgente")
	public void initAgente() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges =  new GestionDeClientes_Fw(driver);
		cbsm = new CBS_Mattu();
		log.loginAgente();
		ges.irAConsolaFAN();
	}
	
	@BeforeMethod (alwaysRun = true)
	public void setup() {
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
	public void quit() {
		driver.quit();
		sleep(5000);
	}
	
	
	private boolean cambioDeSimcard(String metodoEntrega, String sLinea) {
		ges.irAGestionEnCardPorNumeroDeLinea("Cambio SimCard", sLinea);
		cambioDeFrame(driver, By.id("DeliveryMethodSelection"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("DeliveryMethodConfiguration_nextBtn")));
		clickBy(driver, By.id("DeliveryMethodSelection"), 0);
		switch(metodoEntrega.toLowerCase()) {
		case "presencial":
			selectByText(driver.findElement(By.id("DeliveryMethodSelection")), "Presencial");
			break;
		case "store pick up":
			selectByText(driver.findElement(By.id("DeliveryMethodSelection")), "Store Pick Up");
			selectByText(driver.findElement(By.id("PickState")), "Ciudad Aut\u00f3noma de Buenos Aires");
			selectByText(driver.findElement(By.id("PickCity")), "CIUD AUTON D BUENOS AIRES");
			selectByText(driver.findElement(By.id("Store")), "OC Centro - Av. Corrientes 566");
			break;
		}
		clickBy(driver, By.id("DeliveryMethodConfiguration_nextBtn"), 0);
		cambioDeFrame(driver, By.id("InvoicePreview_nextBtn"), 0);
		clickBy(driver, By.id("InvoicePreview_nextBtn"), 0);
		cambioDeFrame(driver, By.id("OrderSumary_nextBtn"), 0);
		String orden = getTextBy(driver, By.xpath("//div[contains(text(),'Order:')]"), 0);
		orden = orden.replaceAll("[^\\d]", "");
		System.out.println(orden);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("OrderSumary_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("OrderSumary_nextBtn")));
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SaleOrderMessages_nextBtn")));
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		clickBy(driver, By.id("SaleOrderMessages_nextBtn"), 0);
		cbsm.Servicio_NotificarPago(orden);
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Logistica");
		cambioDeFrame(driver, By.cssSelector("[class='slds-card__body cards-container']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='slds-card__body cards-container']")));
		WebElement filaLogistica = null;
		for (WebElement x : driver.findElement(By.cssSelector(".slds-card__body.cards-container")).findElements(By.tagName("tbody"))) {
			if (x.findElement(By.tagName("td")).getText().contains(orden))
				filaLogistica = x;
		}
		filaLogistica.findElement(By.xpath("//button//span[text()= 'Armar pedido']")).click();
		cambioDeFrame(driver, By.id("OrderItemNumeration"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("OrderItemNumeration")));
		String serial = driver.findElement(By.xpath("//*[@id='OrderItemNumeration']//tbody//*[@data-label='Serial']//div")).getText();
		driver.findElement(By.cssSelector(".slds-input.ng-pristine.ng-untouched.ng-empty.ng-invalid.ng-invalid-required.ng-valid-pattern")).sendKeys(serial);
		driver.findElement(By.id("SerialNumberValidation_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Confirmation_nextBtn")));
		driver.findElement(By.id("Confirmation_nextBtn")).click();
		sleep(5000);
		cbsm.Servicio_NotificarEmisionFactura(orden);
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Entregas");
		cambioDeFrame(driver, By.cssSelector("[class='slds-card__body cards-container']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='slds-card__body cards-container']")));
		WebElement filaEntregas = null;
		for (WebElement x : driver.findElement(By.cssSelector(".slds-card__body.cards-container")).findElements(By.tagName("tbody"))) {
			if (x.findElement(By.tagName("td")).getText().contains(orden))
				filaEntregas = x;
		}
		filaEntregas.findElement(By.xpath("//button//span[text()= 'Entregar pedido']")).click();		
		cambioDeFrame(driver, By.xpath("//*[@id='ItemGrid']//*[@data-label='NMU']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='ItemGrid']//*[@data-label='NMU']")));
		driver.findElement(By.xpath("//*[@id='ItemGrid']//tbody//*[@data-label='Entregado']//label//span")).click();
		driver.findElement(By.id("OrderItemVerification_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Confirmation_nextBtn")));
		driver.findElement(By.id("Confirmation_nextBtn")).click();
		ges.cerrarPestaniaGestion(driver);
		cambioDeFrame(driver, By.id("phSearchInput"), 0);
		driver.findElement(By.id("phSearchInput")).sendKeys("00040494");
		driver.findElement(By.id("phSearchInput")).submit();
		cambioDeFrame(driver, By.id("searchAllSummaryView"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='bPageBlock brandSecondaryBrd secondaryPalette'] [id='Order_body']")));
		return driver.findElement(By.cssSelector("[class='bPageBlock brandSecondaryBrd secondaryPalette'] [id='Order_body']")).getText().contains("Activada");
	}
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"PerfilOficina", "MVP"})
	public void TS158806_CRM_Movil_PRE_Cambio_de_Simcard_suspension_siniestro_OOCC_presencial() {
		imagen = "TS158806";
		ges.BuscarCuenta("DNI", "93645609");
		Assert.assertTrue(cambioDeSimcard("Presencial","2932598809"));
	}
	
	@Test (groups = {"PerfilOficina", "MVP"})
	public void TS159155_CRM_Movil_PRE_Cambio_de_Simcard_voluntario_OOCC_presencial() {
		imagen = "TS159155";
		ges.BuscarCuenta("DNI", "17954137");
		Assert.assertTrue(cambioDeSimcard("Presencial","2932598828"));
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"PerfilTelefonico", "MVP"})
	public void TS159157_CRM_Movil_PRE_Cambio_de_Simcard_voluntario_Telefonico_store_pick_up() {
		imagen = "TS159157";
		ges.BuscarCuenta("DNI", "17954137");
		Assert.assertTrue(cambioDeSimcard("Store Pick Up", "2932598828"));
	}
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test (groups = {"PerfilAgente", "MVP"})
	public void TS158809_CRM_Movil_PRE_Cambio_de_Simcard_suspension_siniestro_Agente_presencial(String sDNI, String sLinea, String accid) {
		imagen = "TS158809";
		ges.BuscarCuenta("DNI", "93645609");
		Assert.assertTrue(cambioDeSimcard("Presencial","2932598809"));
	}
	
	@Test (groups = {"PerfilAgente", "MVP"})
	public void TS159156_CRM_Movil_PRE_Cambio_de_Simcard_voluntario_Agente_presencial(String sDNI, String sLinea, String accid) {
		imagen = "TS159156";
		ges.BuscarCuenta("DNI", "17954137");
		Assert.assertTrue(cambioDeSimcard("Presencial","2932598828"));
	}
}