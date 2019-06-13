package Funcionalidades;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.Accounts;
import Pages.BasePage;
import Pages.CustomerCare;
import Pages.PagePerfilTelefonico;
import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.GestionFlow;
import Tests.TestBase;

public class CambioDeSimcard extends TestBase {

	private WebDriver driver;
	private SalesBase sb;
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
		
//	@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		cbsm = new CBS_Mattu();
		log.loginTelefonico();
		ges.irAConsolaFAN();	
	}
	
//	@BeforeClass (groups = "PerfilAgente")
	public void initAgente() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges =  new GestionDeClientes_Fw(driver);
		cbsm = new CBS_Mattu();
		log.loginAgente();
		ges.irAConsolaFAN();
	}
	
	@BeforeMethod(alwaysRun=true)
	public void setup() throws Exception {
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
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
	
	@Test(groups = { "GestionesPerfilOficina","CambioSimcardDer", "E2E","Ciclo3" }, priority = 1, dataProvider = "CambioSimCardOficina")
	public void TS134381_CRM_Movil_REPRO_Cambio_de_simcard_sin_costo_Voluntario_Ofcom_Presencial_Con_entega_de_pedido(String sDNI, String sLinea, String accid) throws AWTException	{
		imagen = "134381";
		detalles = imagen + "-DNI:" + sDNI + "-Cuenta:"+accid;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Cambio SimCard");
		cambioDeFrame(driver, By.id("DeliveryMethodSelection"), 0);
		clickBy(driver, By.id("DeliveryMethodSelection"), 0);
		selectByText(driver.findElement(By.id("DeliveryMethodSelection")), "Presencial");
		clickBy(driver, By.id("DeliveryMethodConfiguration_nextBtn"), 0);
		cambioDeFrame(driver, By.id("InvoicePreview_nextBtn"), 0);
		clickBy(driver, By.id("InvoicePreview_nextBtn"), 0);
		cambioDeFrame(driver, By.id("OrderSumary_nextBtn"), 0);
		String orden = getTextBy(driver, By.xpath("//div[contains(text(),'Order:')]"), 0);
		orden = orden.replaceAll("[^\\d]", "");
		detalles += "-Orden:" + orden;
		clickBy(driver, By.id("OrderSumary_nextBtn"), 0);
		esperarElemento(driver, By.id("GeneralMessageDesing"), 0);
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		clickBy(driver, By.id("SaleOrderMessages_nextBtn"), 0);
//		cbsm.Servicio_NotificarEmisionFactura(orden);
	}
	
	@Test(groups = { "GestionesPerfilOficina","CambioDeSimcard", "E2E","Ciclo3" }, priority = 1, dataProvider = "SimCardSiniestroOfCom")
	public void TS134382_CRM_Movil_REPRO_Cambio_de_simcard_sin_costo_Siniestro_Ofcom_Presencial_Con_entega_de_pedido(String sDNI, String sLinea, String accid) throws AWTException {
		imagen = "134382";
		detalles = imagen + "-DNI:" + sDNI + "-Cuenta:" + accid;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Cambio de Simcard");
		cambioDeFrame(driver, By.id("DeliveryMethodSelection"), 0);
		clickBy(driver, By.id("DeliveryMethodSelection"), 0);
		selectByText(driver.findElement(By.id("DeliveryMethodSelection")), "Presencial");
		clickBy(driver, By.id("DeliveryMethodConfiguration_nextBtn"), 0);
		cambioDeFrame(driver, By.id("InvoicePreview_nextBtn"), 0);
		clickBy(driver, By.id("InvoicePreview_nextBtn"), 0);
		cambioDeFrame(driver, By.id("OrderSumary_nextBtn"), 0);
		String orden = getTextBy(driver, By.xpath("//div[contains(text(),'Order:')]"), 0);
		orden = orden.replaceAll("[^\\d]", "");
		detalles += "-Orden:" + orden;
		clickBy(driver, By.id("OrderSumary_nextBtn"), 0);
		esperarElemento(driver, By.id("GeneralMessageDesing"), 0);
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		clickBy(driver, By.id("SaleOrderMessages_nextBtn"), 0);
//		cbsm.Servicio_NotificarEmisionFactura(orden);
	}
	
//	@Test(groups = { "GestionesPerfilTelefonico","CambioDeSimcardDer", "E2E" }, priority = 1, dataProvider = "SimCardSiniestroOfCom") 
	public void TS134406_OF_COM_CRM_Movil_REPRO_Cambio_de_simcard_con_costo_Siniestro_Ofcom_Store_pickUp_Con_entega_de_pedido_pago_en_Efectivo(String sDNI, String sLinea, String accid){
		imagen = "134406";
		detalles = imagen + "-DNI:" + sDNI + "-Cuenta:" + accid;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Cambio de Simcard");
		cambioDeFrame(driver, By.id("DeliveryMethodSelection"), 0);
		clickBy(driver, By.id("DeliveryMethodSelection"), 0);
		selectByText(driver.findElement(By.id("DeliveryMethodSelection")), "Store Pick Up");
		selectByText(driver.findElement(By.id("PickState")), "PREGUNTAR PROVINCIA");  	    // PREGUNTAR PROVINCIA
		selectByText(driver.findElement(By.id("PickCity")), "PREGUNTAR LOCALIDAD");			// PREGUNTAR LOCALIDAD
		selectByText(driver.findElement(By.id("Store")), "PREGUNTAR PUNTO DE VENTA");		// PREGUNTAR LOCALIDAD
		clickBy(driver, By.id("DeliveryMethodConfiguration_nextBtn"), 0);
		cambioDeFrame(driver, By.id("InvoicePreview_nextBtn"), 0);
		clickBy(driver, By.id("InvoicePreview_nextBtn"), 0);
		cambioDeFrame(driver, By.id("OrderSumary_nextBtn"), 0);
		String orden = getTextBy(driver, By.xpath("//div[contains(text(),'Order:')]"), 0);
		orden = orden.replaceAll("[^\\d]", "");
		detalles += "-Orden:" + orden;
		clickBy(driver, By.id("OrderSumary_nextBtn"), 0);
		esperarElemento(driver, By.id("GeneralMessageDesing"), 0);
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		clickBy(driver, By.id("SaleOrderMessages_nextBtn"), 0);
		// EN DESARROLLO AUN - FECHA: 11/06/2019
	}
	
//	@Test(groups = { "GestionesPerfilTelefonico","CambioSimCardDer", "E2E" }, priority = 1, dataProvider = "SimCardSiniestroAG") //NO APARECE EL MEDIO DE PAGO
	public void TS134407_CRM_Movil_REPRO_Cambio_de_simcard_con_costo_Siniestro_Ofcom_Store_pickUp_Con_entega_de_pedido_pago_con_TD(String sDNI, String sLinea, String accid, String cEntrega, String cProvincia, String cLocalidad, String cPuntodeVenta, String cBanco, String cTarjeta, String cPromo, String cCuotas, String cNumTarjeta, String cVenceMes, String cVenceAno, String cCodSeg, String cTipoDNI,String cDNITarjeta, String cTitular){
		imagen = "134407";
		detalles = imagen + "-DNI:" + sDNI + "-Cuenta:" + accid;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Cambio de Simcard");
		cambioDeFrame(driver, By.id("DeliveryMethodSelection"), 0);
		clickBy(driver, By.id("DeliveryMethodSelection"), 0);
		selectByText(driver.findElement(By.id("DeliveryMethodSelection")), "Store Pick Up");
		selectByText(driver.findElement(By.id("PickState")), "PREGUNTAR PROVINCIA");           // PREGUNTAR PROVINCIA
		selectByText(driver.findElement(By.id("PickCity")), "PREGUNTAR LOCALIDAD");		// PREGUNTAR LOCALIDAD
		selectByText(driver.findElement(By.id("Store")), "PREGUNTAR PUNTO DE VENTA");		// PREGUNTAR LOCALIDAD
		clickBy(driver, By.id("DeliveryMethodConfiguration_nextBtn"), 0);
		cambioDeFrame(driver, By.id("InvoicePreview_nextBtn"), 0);
		clickBy(driver, By.id("InvoicePreview_nextBtn"), 0);
		cambioDeFrame(driver, By.id("OrderSumary_nextBtn"), 0);
		String orden = getTextBy(driver, By.xpath("//div[contains(text(),'Order:')]"), 0);
		orden = orden.replaceAll("[^\\d]", "");
		detalles += "-Orden:" + orden;
		clickBy(driver, By.id("OrderSumary_nextBtn"), 0);
		esperarElemento(driver, By.id("GeneralMessageDesing"), 0);
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		clickBy(driver, By.id("SaleOrderMessages_nextBtn"), 0);
		// EN DESARROLLO AUN - FECHA: 11/06/2019
	}
	
	@Test(groups = { "GestionesPerfilOficina","CambioDeSimcard", "E2E","Ciclo3" }, priority = 1, dataProvider = "SimCardSiniestroOfCom")
	public void TS99030_CRM_Movil_REPRO_Cambio_de_SimCard_Presencial_Siniestro_Ofcom(String sDNI, String sLinea, String accid) throws AWTException {
		imagen = "TS99030";
		detalles = imagen + "-DNI:" + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Cambio SimCard");
		cambioDeFrame(driver, By.id("DeliveryMethodSelection"), 0);
		clickBy(driver, By.id("DeliveryMethodSelection"), 0);
		selectByText(driver.findElement(By.id("DeliveryMethodSelection")), "Presencial");
		clickBy(driver, By.id("DeliveryMethodConfiguration_nextBtn"), 0);
		cambioDeFrame(driver, By.id("InvoicePreview_nextBtn"), 0);
		clickBy(driver, By.id("InvoicePreview_nextBtn"), 0);
		cambioDeFrame(driver, By.id("OrderSumary_nextBtn"), 0);
		String orden = getTextBy(driver, By.xpath("//div[contains(text(),'Order:')]"), 0);
		orden = orden.replaceAll("[^\\d]", "");
		detalles += "-Orden:" + orden;
		clickBy(driver, By.id("OrderSumary_nextBtn"), 0);
		esperarElemento(driver, By.id("GeneralMessageDesing"), 0);
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		clickBy(driver, By.id("SaleOrderMessages_nextBtn"), 0);
//		cbsm.Servicio_NotificarEmisionFactura(orden);
	}
	
	@Test(groups = { "GestionesPerfilOficina", "CambioSimCard","Ciclo3" }, priority = 1, dataProvider = "CambioSimCardOficina")
	public void TSCambioSimCardOficina(String sDNI, String sLinea, String accid) throws AWTException {
		imagen = "TSCambioSimCardOficina";
		detalles = imagen + "-DNI:" + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Cambio SimCard");
		cambioDeFrame(driver, By.id("DeliveryMethodSelection"), 0);
		clickBy(driver, By.id("DeliveryMethodSelection"), 0);
		selectByText(driver.findElement(By.id("DeliveryMethodSelection")), "Presencial");
		clickBy(driver, By.id("DeliveryMethodConfiguration_nextBtn"), 0);
		cambioDeFrame(driver, By.id("InvoicePreview_nextBtn"), 0);
		clickBy(driver, By.id("InvoicePreview_nextBtn"), 0);
		cambioDeFrame(driver, By.id("OrderSumary_nextBtn"), 0);
		String orden = getTextBy(driver, By.xpath("//div[contains(text(),'Order:')]"), 0);
		orden = orden.replaceAll("[^\\d]", "");
		detalles += "-Orden:" + orden;
		clickBy(driver, By.id("OrderSumary_nextBtn"), 0);
		esperarElemento(driver, By.id("GeneralMessageDesing"), 0);
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		clickBy(driver, By.id("SaleOrderMessages_nextBtn"), 0);
//		cbsm.Servicio_NotificarEmisionFactura(orden);
	}
	
	@Test(groups = { "GestionesPerfilTelefonico","CambioSimCard", "E2E" }, priority = 1, dataProvider = "CambioSimCardOficina")
	public void TS158806_CRM_Movil_PRE_Cambio_de_Simcard_suspension_siniestro_OOCC_presencial(String sDNI, String sLinea, String accid) {
		imagen = "134381";
		detalles = imagen + "-DNI:" + sDNI + "-Cuenta:"+accid;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Cambio SimCard");
		cambioDeFrame(driver, By.id("DeliveryMethodSelection"), 0);
		clickBy(driver, By.id("DeliveryMethodSelection"), 0);
		selectByText(driver.findElement(By.id("DeliveryMethodSelection")), "Presencial");
		clickBy(driver, By.id("DeliveryMethodConfiguration_nextBtn"), 0);
		cambioDeFrame(driver, By.id("InvoicePreview_nextBtn"), 0);
		clickBy(driver, By.id("InvoicePreview_nextBtn"), 0);
		cambioDeFrame(driver, By.id("OrderSumary_nextBtn"), 0);
		String orden = getTextBy(driver, By.xpath("//div[contains(text(),'Order:')]"), 0);
		orden = orden.replaceAll("[^\\d]", "");
		detalles += "-Orden:" + orden;
		clickBy(driver, By.id("OrderSumary_nextBtn"), 0);
		esperarElemento(driver, By.id("GeneralMessageDesing"), 0);
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		clickBy(driver, By.id("SaleOrderMessages_nextBtn"), 0);
//		FALTA TERMINARLO / FLUJO INCOMPLETO
	}
	
	@Test(groups = { "GestionesPerfilTelefonico","CambioSimCard", "E2E" }, priority = 1, dataProvider = "CambioSimCardOficina")
	public void TS159155_CRM_Movil_PRE_Cambio_de_Simcard_voluntario_OOCC_presencial(String sDNI, String sLinea, String accid) {
		imagen = "134381";
		detalles = imagen + "-DNI:" + sDNI + "-Cuenta:"+accid;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Cambio SimCard");
		cambioDeFrame(driver, By.id("DeliveryMethodSelection"), 0);
		clickBy(driver, By.id("DeliveryMethodSelection"), 0);
		selectByText(driver.findElement(By.id("DeliveryMethodSelection")), "Presencial");
		clickBy(driver, By.id("DeliveryMethodConfiguration_nextBtn"), 0);
		cambioDeFrame(driver, By.id("InvoicePreview_nextBtn"), 0);
		clickBy(driver, By.id("InvoicePreview_nextBtn"), 0);
		cambioDeFrame(driver, By.id("OrderSumary_nextBtn"), 0);
		String orden = getTextBy(driver, By.xpath("//div[contains(text(),'Order:')]"), 0);
		orden = orden.replaceAll("[^\\d]", "");
		detalles += "-Orden:" + orden;
		clickBy(driver, By.id("OrderSumary_nextBtn"), 0);
		esperarElemento(driver, By.id("GeneralMessageDesing"), 0);
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		clickBy(driver, By.id("SaleOrderMessages_nextBtn"), 0);
//		FALTA TERMINARLO / FLUJO INCOMPLETO
	}
	
	//-------------------------------------------- TELEFONICO ----------------------------------------------------\\
	
//	@Test(groups = { "GestionesPerfilTelefonico","CambioSimCard", "E2E" }, priority = 1, dataProvider = "CambioSimCardTelef")
	public void TS_Cambio_Sim_Card_Store_Pick_up_Telefonico(String sDNI, String sLinea, String accid, String sEntrega, String sProvincia, String sLocalidad, String sPuntodeVenta) throws AWTException {
		imagen = "TS_Cambio_Sim_Card_Store_Pick_up_Telefonico";
		detalles = imagen+"-Telef-DNI:"+sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Cambio SimCard");
//		detalles += "-Orden:" + orden;
	}
	
//	@Test(groups = { "GestionesPerfilTelefonico","CambioSimcardDer", "E2E" }, priority = 1, dataProvider = "SimCardSiniestroOfCom") 
	public void TS134392_TELEF_CRM_Movil_REPRO_Cambio_de_simcard_sin_costo_Siniestro_Telefonico_Store_pickUp_Con_entega_de_pedido(String sDNI, String sLinea, String accid){
		imagen = "134392";
		detalles = imagen + "-DNI:" + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		detalles +="-Cuenta:"+accid;
		ges.irAGestionEnCard("Cambio de Simcard");
	}
	
//	@Test(groups = { "GestionesPerfilTelefonico","CambioSimCard","Ciclo 3", "E2E" }, priority = 1, dataProvider = "SimCardSiniestroTelef")
	public void TS134427_CRM_Movil_REPRO_Cambio_de_simcard_con_costo_Voluntario_Telefonico_Store_pickUp_Con_Siniestro_Telef(String sDNI, String sLinea, String accid, String sEntrega, String sProvincia, String sLocalidad, String sPuntodeVenta) throws AWTException {
		imagen = "134427";
		detalles = imagen+"-Telef-DNI:"+sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		detalles +="-Cuenta:"+accid;
		ges.irAGestionEnCard("Cambio SimCard");
//		detalles += "-Orden:" + orden;
	}
	
//	@Test(groups = { "GestionesPerfilTelefonico","CambioSimCard","Ciclo 3", "E2E" }, priority = 1, dataProvider = "CambioSimCardTelef")
	public void TS159157_CRM_Movil_PRE_Cambio_de_Simcard_voluntario_Telefonico_store_pick_up(String sDNI, String sLinea, String accid, String sEntrega, String sProvincia, String sLocalidad, String sPuntodeVenta) throws AWTException {
		imagen = "134381";
		detalles = imagen + "-DNI:" + sDNI + "-Cuenta:" + accid;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Cambio SimCard");
	}
	
	//---------------------------------------------- AGENTE ------------------------------------------------------\\
	
//	@Test(groups = { "GestionesPerfilAgente","Ciclo 3", "E2E", "CambioDeSimCard" }, priority = 1, dataProvider = "SimCardSiniestroAG")
	public void TS99020_CRM_Movil_REPRO_Cambio_de_SimCard_Presencial_Siniestro_DOC_Store_Pick_Up_TC_Agente(String sDNI, String sLinea, String accid, String sEntrega, String sProvincia, String sLocalidad, String sPuntodeVenta) throws AWTException {
		imagen = "99020";
		detalles = imagen+"-Telef-DNI:"+sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Cambio SimCard");
		driver.navigate().refresh();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		driver.switchTo().defaultContent();
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
		GestionFlow gf = new GestionFlow();
		String sIMSIFlow = gf.FlowIMSI(driver,sLinea);
		System.out.println("sIMSIFlow: " + sIMSIFlow);
	}
	
//	@Test(groups = { "GestionesPerfilAgente","CambioSimCard", "E2E","Ciclo3" }, priority = 1, dataProvider = "CambioSimCardAgente")
	public void TS_Cambio_SimCard_Agente(String sDNI, String sLinea) throws AWTException {
		imagen = "TS_Cambio_SimCard_Agente";
		detalles = imagen + "-DNI:" + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Cambio SimCard");
		cambioDeFrame(driver, By.id("DeliveryMethodSelection"), 0);
		clickBy(driver, By.id("DeliveryMethodSelection"), 0);
		selectByText(driver.findElement(By.id("DeliveryMethodSelection")), "Presencial");
		clickBy(driver, By.id("DeliveryMethodConfiguration_nextBtn"), 0);
	}
}