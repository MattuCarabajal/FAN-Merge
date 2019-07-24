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
import Tests.CBS_Mattu;
import Tests.TestBase;

public class RenovacionDeCuota extends TestBase {

	private WebDriver driver;
	private LoginFw log;
	private CBS_Mattu cbsm;
	private CustomerCare cc;
	private GestionDeClientes_Fw ges;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	//@BeforeClass (groups= "PerfilOficina")
	public void Sit02() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		cbsm = new CBS_Mattu();
		//log.LoginSit02();
		//ges.irAConsolaFAN();
	}
	
	//@BeforeClass (groups= "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		cbsm = new CBS_Mattu();
		cc = new CustomerCare(driver);
		ges = new GestionDeClientes_Fw(driver);
		log = new LoginFw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();	
	}
		
	@BeforeClass (groups= "PerfilTelefonico")
	public void initTelefonico() {
		driver = setConexion.setupEze();
		cbsm = new CBS_Mattu();
		cc = new CustomerCare(driver);
		ges = new GestionDeClientes_Fw(driver);
		log = new LoginFw(driver);
		log.loginTelefonico();
		ges.irAConsolaFAN();
	}
	
	@BeforeMethod(alwaysRun=true)
	public void setup() {
		detalles = null;
		ges.selectMenuIzq("Inicio");
		ges.cerrarPestaniaGestion(driver);
		ges.irGestionClientes();
	}

	//@AfterMethod(alwaysRun=true)
	public void after() throws IOException {
		guardarListaTxt(sOrders);
		sOrders.clear();
		tomarCaptura(driver,imagen);
		sleep(2000);
	}

	//@AfterClass(alwaysRun=true)
	public void quit() {
		driver.quit();
		sleep(5000);
	}
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"PerfilOficina", "R1"}, dataProvider = "ConsultaSaldo")
	public void TS163167_CRM_Movil_Mix_Renovacion_de_Datos_APRO2_OOCC_Efectivo(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS163167";
		ges.BuscarCuenta("DNI", sDNI);
		sleep(500);
		ges.irRenovacionDeDatos(sLinea);
		sleep(15000);
		cambioDeFrame(driver, By.id("combosMegas"), 0);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer"), 0));
		List<WebElement> elementos = driver.findElement(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for (WebElement elemento : elementos) {
			if (elemento.getText().contains("1 GB")) {
				elemento.findElement(By.className("slds-checkbox")).click();
				break;
			}
		}
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("CombosDeMegas_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));	
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='slds-radio ng-scope']")));
		buscarYClick(driver.findElements(By.cssSelector("[class='slds-radio ng-scope']")), "contains", "Venta");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SetPaymentType_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		sleep(10000);
		cambioDeFrame(driver,By.id("InvoicePreview_nextBtn"),0);
		clickBy(driver,By.id("InvoicePreview_nextBtn"),0);
		cambioDeFrame(driver,By.xpath("//*[@id=\"PaymentMethods\"]/div/ng-include/div/section[2]/div[1]/div/div/div/fieldset/div[1]/ul/li[1]/label/span[2]"),0);
		sleep(5000);
		String orden = driver.findElement(By.xpath("//p [contains(text(),'Nro')]")).getText().substring(11);
		clickBy(driver,By.xpath("//*[@id=\"PaymentMethods\"]/div/ng-include/div/section[2]/div[1]/div/div/div/fieldset/div[1]/ul/li[1]/label/span[2]"),0);
		clickBy(driver,By.id("SelectPaymentMethodsStep_nextBtn"),0);
		cbsm.Servicio_NotificarPago(orden);
		ges.cerrarPestaniaGestion(driver);
		cc.buscarOrden(orden+"*");
		cc.verificarPedido(orden, "Activada");		
	}
	
	@Test (groups = {"PerfilOficina", "R1"}, dataProvider = "ConsultaSaldo")
	public void TS163166_CRM_Movil_Mix_Renovacion_de_Datos_APRO2_OOCC_Descuento_Saldo(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS163166";
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver,By.cssSelector("[class='details']"),0);
		sleep(500);
		int saldoOriginal = Integer.valueOf(ges.getInfoNuevaCard("2932598337","Disponible"));
		int datosOriginal = Integer.valueOf(ges.getInfoNuevaCard("2932598337","Internet"));
		ges.irRenovacionDeDatos(sLinea);
		cambioDeFrame(driver, By.id("combosMegas"), 0);
		sleep(5000);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer"), 0));
		List<WebElement> elementos = driver.findElement(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for (WebElement elemento : elementos) {
			if (elemento.getText().contains("1 GB")) {
				elemento.findElement(By.className("slds-checkbox")).click();
				break;
			}
		}
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("CombosDeMegas_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));	
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='slds-radio ng-scope']")));
		buscarYClick(driver.findElements(By.cssSelector("[class='slds-radio ng-scope']")), "contains", "Saldo");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SetPaymentType_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		sleep(5000);
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
		ges.BuscarCuenta("DNI", "9585089");
		sleep(5000);
		Assert.assertTrue((saldoOriginal - 17500) == Integer.valueOf(ges.getInfoNuevaCard("2932598337","Disponible")));
		Assert.assertTrue((datosOriginal + 1024) == Integer.valueOf(ges.getInfoNuevaCard("2932598337","Internet")));	
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"PerfilTelefonico", "R1"}, dataProvider = "ConsultaSaldo")
	public void TS163182_CRM_Movil_Mix_Renovacion_de_Datos_APRO2_Telefonico_TDC(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS163182";
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver,By.cssSelector("[class='details']"),0);
		sleep(500);
		int datosOriginal = Integer.valueOf(ges.getInfoNuevaCard("2932598337","Internet"));
		ges.irRenovacionDeDatos(sLinea);
		cambioDeFrame(driver, By.id("combosMegas"), 0);
		sleep(5000);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer"), 0));
		List<WebElement> elementos = driver.findElement(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for (WebElement elemento : elementos) {
			if (elemento.getText().contains("1 GB")) {
				elemento.findElement(By.className("slds-checkbox")).click();
				break;
			}
		}
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("CombosDeMegas_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));	
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='slds-radio ng-scope']")));
		buscarYClick(driver.findElements(By.cssSelector("[class='slds-radio ng-scope']")), "contains", "Venta");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SetPaymentType_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("InvoicePreview_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SelectPaymentMethodsStep_nextBtn")));
		sleep(8000);
		selectByText(driver.findElement(By.id("BankingEntity-0")), "BANCO DE LA NACION ARGENTINA");
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), "MASTERCARD");
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), "MASTERCARD/ARGENCARD S.A. - Cuotas: 1.. 1 Recargo: 0,00%");
		selectByText(driver.findElement(By.id("Installment-0")), "1 - CFT %1.0");
		driver.findElement(By.id("CardNumber-0")).sendKeys("5399099990081010");
		selectByText(driver.findElement(By.id("expirationMonth-0")), "6");
		selectByText(driver.findElement(By.id("expirationYear-0")), "2020");
		driver.findElement(By.id("securityCode-0")).sendKeys("159");
		selectByText(driver.findElement(By.id("documentType-0")), "DNI");
		driver.findElement(By.id("documentNumber-0")).sendKeys("22222000");
		driver.findElement(By.id("cardHolder-0")).sendKeys("Nombre Cuenta");
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		sleep(5000);
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
		ges.BuscarCuenta("DNI", "9585089");
		sleep(5000);
		System.out.println(datosOriginal + " ---> " + ges.getInfoNuevaCard("2932598337","Internet"));
		Assert.assertTrue((datosOriginal + 1024) == Integer.valueOf(ges.getInfoNuevaCard("2932598337","Internet")));
	}
	
	@Test (groups = {"PerfilTelefonico", "R1"}, dataProvider = "ConsultaSaldo")
	public void TS163181_CRM_Movil_Mix_Renovacion_de_Datos_APRO2_Telefonico_Descuento_Saldo(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS163181";
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver,By.cssSelector("[class='details']"),0);
		sleep(500);
		int saldoOriginal = Integer.valueOf(ges.getInfoNuevaCard("2932598337","Disponible"));
		int datosOriginal = Integer.valueOf(ges.getInfoNuevaCard("2932598337","Internet"));
		ges.irRenovacionDeDatos(sLinea);
		cambioDeFrame(driver, By.id("combosMegas"), 0);
		sleep(5000);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer"), 0));
		List<WebElement> elementos = driver.findElement(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for (WebElement elemento : elementos) {
			if (elemento.getText().contains("1 GB")) {
				elemento.findElement(By.className("slds-checkbox")).click();
				break;
			}
		}
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("CombosDeMegas_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));	
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='slds-radio ng-scope']")));
		buscarYClick(driver.findElements(By.cssSelector("[class='slds-radio ng-scope']")), "contains", "Saldo");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SetPaymentType_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		sleep(5000);
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
		ges.BuscarCuenta("DNI", "9585089");
		sleep(5000);
		Assert.assertTrue((saldoOriginal - 17500) == Integer.valueOf(ges.getInfoNuevaCard("2932598337","Disponible")));
		Assert.assertTrue((datosOriginal + 1024) == Integer.valueOf(ges.getInfoNuevaCard("2932598337","Internet")));	
	}
}