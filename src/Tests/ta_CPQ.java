package Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.awt.Button;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;

import Pages.BasePage;
import Pages.Bill;
import Pages.BillSimulation;
import Pages.DeliveryMethod;
import Pages.LineAssignment;
import Pages.Login;
import Pages.Order;
import Pages.OrdersTab;
import Pages.PaymentMethod;
import Pages.SalesBase;
import Pages.SerialInput;
import Pages.Ta_CPQ;
import Pages.Ta_CPQ.RightPanel;
import Pages.Ta_CPQ_Validate;
import Pages.setConexion;

import org.testng.Assert;

public class ta_CPQ extends TestBase {
	
	private String province = "Buenos Aires";
	private String locality = "VICENTE LOPEZ";
	private String street = "Av. Gonzalez Chavez";
	private String streetNumber = "485";
	private String postalCode = "7620";
	private String  typeZone = "Urbano";
	
	protected WebDriver driver;
	protected  WebDriverWait wait;

	//@AfterClass(alwaysRun=true)
	public void tearDown() {
		driver.quit();
		sleep(1000);
	}
	
	@BeforeClass(alwaysRun=true)
	public void Init() throws Exception
	{
		this.driver = setConexion.setupEze();
		wait = new WebDriverWait(driver, 10);
		loginAndres(driver);
		sleep(5000);
		//Ir a Ventas:
		/*if (!driver.findElement(By.id("tsidLabel")).getText().equals("Ventas")){
			driver.findElement(By.id("tsidLabel")).click();
			driver.findElement(By.xpath("//a[@href=\"/home/home.jsp?tsid=02u41000000QWha\"]")).click();
		}*/
	}

	@BeforeMethod(alwaysRun=true)
	public void setup() throws Exception {		
		//Ir a Busqueda de Cliente
		driver.get("https://crm--sit--c.cs14.visual.force.com/apex/taClientSearch");
		sleep(10000);
		SalesBase SB = new SalesBase(driver);
		SB.BuscarCuenta("DNI", "32323232");
		SB.acciondecontacto("catalogo");
		sleep(30000);

	}
	    
//------------------------------------------------- Casos de Prueba ---------------------------------------------------//      
	/*
	 * Verifica que se cargue la SimCard cuando se agregue el plan
	 * 
	 * SimCard no disponible, require actualizacion cuando este disponible
	 * Ultima revision 30-01-18
	 */
	@Test
	public void TS6816_checkSimCardAssignment() {
		System.out.println("Empiezan el caso");
		sleep(30000);
		System.out.println("Pasaron 30 Segundos.");
		Ta_CPQ page3 = new Ta_CPQ(driver);
		try { for(WebElement e : driver.findElements(By.className("cpq-product-name"))) {
			page3.clickOnDelete();
			try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		} } catch (java.lang.IndexOutOfBoundsException e) {}
		page3.addPlan();
		System.out.println("Ya agrego el plan");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		System.out.println("pasaron 10 segundos");
		//BasePage frame=new BasePage(driver);
		//driver.switchTo().frame(frame.getFrameForElement(driver, By.cssSelector(".slds-button__icon.slds-button__icon--small.slds-button__icon--left.fix-slds-close-switch")));

		
		//driver.findElement(By.cssSelector(".slds-button__icon.slds-button__icon--small.slds-button__icon--left.fix-slds-close-switch")).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertEquals("1", page3.getSimCardValue()); 	
	}
	
	
	/**
	 * Verifica que al agregar un plan que no requiere prefactibilidad, se pueda borrar.
	 * Passed Ultima revision 30-01-18
	 */
	
	
	/**bin;
	 * Verifica que aparezca "Quitar el producto del carrito" en la etiqueta de la papelera.*/
	
	
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"})
	public void TS94486_Alta_Linea_Asignar_SIMCARD_Ingresar_un_ICCID_con_menor_cantidad_de_numeros() {
		Ta_CPQ page3 = new Ta_CPQ(driver);
		try { for(WebElement e : driver.findElements(By.className("cpq-product-name"))) {
			page3.clickOnDelete();
			try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		} } catch (java.lang.IndexOutOfBoundsException e) {}
		page3.addPlan("plan prepago");
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page3.clickOnSalesConfig();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		LineAssignment page4 = new LineAssignment(driver);
		page4.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		DeliveryMethod page5 = new DeliveryMethod(driver);
		page5.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BillSimulation page6 = new BillSimulation(driver);
		page6.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		PaymentMethod page7 = new PaymentMethod(driver);
		page7.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		SerialInput page8 = new SerialInput(driver);
		page8.setICCD("123456");
		page8.clickOnValidateICCD();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertEquals("Error de formato", page8.getValidationMessage("wrong"));
	}
	
	/**
	 * Flujo no se completa, por factura
	 * ultima revision 30-01-18
	 */
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"})
	public void TS94488_Alta_Linea_Asignar_SIMCARD_Visualizar_mensaje_al_asignar_un_ICCID_disponible() {
		Ta_CPQ page3 = new Ta_CPQ(driver);
		/*
		try { for(WebElement e : driver.findElements(By.className("cpq-product-name"))) {
			page3.clickOnDelete();
			try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		} } catch (java.lang.IndexOutOfBoundsException e) {}
		*/
		page3.deleteAddedProducts();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page3.addPlan();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page3.clickOnSalesConfig();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		LineAssignment page4 = new LineAssignment(driver);
		page4.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		DeliveryMethod page5 = new DeliveryMethod(driver);
		page5.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		//BillSimulation page6 = new BillSimulation(driver);
		//page6.clickOnNext();
		//try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		PaymentMethod page7 = new PaymentMethod(driver);
		page7.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		SerialInput page8 = new SerialInput(driver);
		page8.setICCD("1234567891");
		page8.clickOnValidateICCD();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertEquals("El ICCID fue asignado", page8.getValidationMessage("right"));
	}

	/**
	 * Flujo no se completa, por factura
	 * ultima revision 30-01-18
	 */
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"})
	public void TS94491_Alta_Linea_Asignar_SIMCARD_Habilitar_boton_asignar_una_vez_ingresado_el_ICCID_07() {
		Ta_CPQ page3 = new Ta_CPQ(driver);
		try { for(WebElement e : driver.findElements(By.className("cpq-product-name"))) {
			page3.clickOnDelete();
			try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		} } catch (java.lang.IndexOutOfBoundsException e) {}
		page3.addPlan();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page3.clickOnSalesConfig();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		LineAssignment page4 = new LineAssignment(driver);
		page4.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		DeliveryMethod page5 = new DeliveryMethod(driver);
		page5.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BillSimulation page6 = new BillSimulation(driver);
		page6.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		PaymentMethod page7 = new PaymentMethod(driver);
		page7.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		SerialInput page8 = new SerialInput(driver);
		page8.setICCD("1234567890");
		Assert.assertEquals("Asignar", page8.getAssignButtonLabel());
	}
	
	/**
	 * Flujo no se completa, por factura
	 * ultima revision 30-01-18
	 */
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"})
	public void TS94487_Alta_Linea_Asignar_SIMCARD_Visualizar_el_estado_de_la_orden_al_contar_con_un_ICCID_asignado() {
		Ta_CPQ page3 = new Ta_CPQ(driver);
		SalesBase SB = new SalesBase(driver);
		
		try { for(WebElement e : driver.findElements(By.className("cpq-product-name"))) {
			page3.clickOnDelete();
			try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		} } catch (java.lang.IndexOutOfBoundsException e) {}
		//page3.addPlan();
		SB.elegirplan("repro");
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page3.clickOnSalesConfig();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		LineAssignment page4 = new LineAssignment(driver);
		page4.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		DeliveryMethod page5 = new DeliveryMethod(driver);
		page5.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BillSimulation page6 = new BillSimulation(driver);
		page6.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		PaymentMethod page7 = new PaymentMethod(driver);
		page7.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		SerialInput page8 = new SerialInput(driver);
		page8.setICCD("1234567890");
		page8.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Bill page9 = new Bill(driver);
		Assert.assertTrue(page9.checkOrderStatusDisplays());
		Assert.assertEquals("Estado de la orden : Pendiente de pago", page9.getOrderStatus());
	}
	
	/** pasado a sales2
	 * Verifica que se puedan borrar varios planes.
	 * Ultima Revision 30-01-18
	 */
	//@Test(groups={"Sales", "AltaDeLinea", "Ola1"})
	
	@Test
	public void TS7007_checkCancelOrder() {
		Ta_CPQ page3 = new Ta_CPQ(driver);
		try { for(WebElement e : driver.findElements(By.className("cpq-product-name"))) {
			page3.clickOnDelete();
			try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		} } catch (java.lang.IndexOutOfBoundsException e) {}
		page3.addPlan();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page3.clickOnSalesConfig();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		LineAssignment page4 = new LineAssignment(driver);
		page4.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		DeliveryMethod page5 = new DeliveryMethod(driver);
		page5.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		//BillSimulation page6 = new BillSimulation(driver);
		//page6.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		PaymentMethod page7 = new PaymentMethod(driver);
		page7.selectDebitoAProximaFactura();
		page7.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("alert-ok-button")).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page7.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		SerialInput page8 = new SerialInput(driver);
		page8.setICCD("1234567890");
		page8.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Bill page9 = new Bill(driver);
		page9.cancelLineAssignment();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertEquals("Ventas", driver.findElement(By.id("tsidLabel")).getText());
	}
	
	/**
	 * Flujo no se completa, por factura
	 * ultima revision 30-01-18
	 */
	@Test
	public void TS6890_checkCashAsValueForPaymentMethod() {
		Ta_CPQ page3 = new Ta_CPQ(driver);
		try { for(WebElement e : driver.findElements(By.className("cpq-product-name"))) {
			page3.clickOnDelete();
			try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		} } catch (java.lang.IndexOutOfBoundsException e) {}
		page3.addPlan();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page3.clickOnSalesConfig();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		LineAssignment page4 = new LineAssignment(driver);
		page4.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		DeliveryMethod page5 = new DeliveryMethod(driver);
		page5.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BillSimulation page6 = new BillSimulation(driver);
		page6.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		PaymentMethod page7 = new PaymentMethod(driver);
		Assert.assertTrue(page7.getPaymentMethod());
	}
	
	/**
	 * Verifica que los metodos de pago esten disponible.
	 * 
	 * ultima revision 30-01-18
	 */
	@Test
	public void TS6889_checkPaymentMethodIsPresent() {
		Ta_CPQ page3 = new Ta_CPQ(driver);
		try { for(WebElement e : driver.findElements(By.className("cpq-product-name"))) {
			page3.clickOnDelete();
			try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		} } catch (java.lang.IndexOutOfBoundsException e) {}
		page3.addPlan();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page3.clickOnSalesConfig();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		LineAssignment page4 = new LineAssignment(driver);
		page4.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		DeliveryMethod page5 = new DeliveryMethod(driver);
		page5.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		/*BillSimulation page6 = new BillSimulation(driver);
		page6.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}*/
		PaymentMethod page7 = new PaymentMethod(driver);
		Assert.assertTrue(page7.isPaymentMethodPresent());
	}
	
	/**
	 * Verifica que el metodo de entrega por default sea "Presencial".
	 * 
	 * ultima revision 30-01-18
	 */
	@Test
	public void TS6895_checkDefaultValueForDeliveryMethod() {
		Ta_CPQ page3 = new Ta_CPQ(driver);
		try { for(WebElement e : driver.findElements(By.className("cpq-product-name"))) {
			page3.clickOnDelete();
			try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		} } catch (java.lang.IndexOutOfBoundsException e) {}
		page3.addPlan();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page3.clickOnSalesConfig();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		LineAssignment page4 = new LineAssignment(driver);
		page4.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		DeliveryMethod page5 = new DeliveryMethod(driver);
		Assert.assertEquals("Presencial", page5.getCurrentValueForDeliveryMethod());
	}
	
	/**
	 * Verifica que se pueda buscar un plan por el nombre y que aparezca en los resultados.
	 * 
	 * ultima revision 30-01-18
	 */

	@Test
	public void TS6826_CRM_Fase_1_SalesCPQ_Alta_Linea_Carrito_Verificar_seleccion_de_productos() {
		Ta_CPQ cart = new Ta_CPQ (driver);
		String productName ="productName"; //productName
		String productNameAdded = "true"; //productNameAdded
		
		cart.deleteAddedProducts();
		for (WebElement div: cart.getDivsProducts()) {
			if (!cart.requiresPrefactibility(div)) {
				WebElement addToCartButton = div.findElement(By.cssSelector(".slds-button.slds-button--neutral.add-button"));
				addToCartButton.click();
				try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
				productName = div.findElement(By.cssSelector(".slds-tile__title.slds-truncate.product-name")).getText();
				try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
				productNameAdded = driver.findElement(By.xpath(".//div[@class=\"cpq-item-no-children\"]/span")).getText();
				break;
			}
		}		
		Assert.assertEquals (productNameAdded, productName);
	}

	/**
	 * Verifica que al agregar un producto al carrito el boton Incomplete cambie de nombre.
	 * 
	 * Ultima revision 30-01-18
	 */
	@Test
	public void TS6827_CRM_Fase_1_SalesCPQ_Alta_Linea_Configurar_Nueva_Linea_Boton_Siguiente() throws Exception {
		Ta_CPQ cart = new Ta_CPQ(driver);
		cart.addAnyProductToCart();
		sleep(4000);
		//System.out.println(cart.getCartStatus());
		Assert.assertNotEquals(cart.getCartStatus(),"Incomplete");
	}
	
	//Listo 26-01-18
	@Test
	public void TS6835_CRM_Fase_SalesCPQ_Alta_Linea_Configurar_Nueva_Linea_Lista_de_cuentas_del_cliente() {
		Ta_CPQ cart = new Ta_CPQ(driver);
		Assert.assertTrue(cart.getAccountSelector() != null);
	}
	
	//Listo 26-01-18
	@Test
	public void TS6836_CRM_Fase_1_SalesCPQ_Alta_Linea_Configurar_Nueva_Linea_Nueva_Cuenta() {
		Ta_CPQ cart = new Ta_CPQ(driver);
		Assert.assertTrue(cart.getButtonNewAccount() != null);
	}
	
	/** pasado a sales 2
	 * Verifica que despues de eliminar los productos del carrito se muestre el mensaje
	 * "Cart is empty."
	 * 
	 * Ultima revision 30-01-18
	 */
	
	
//HACer en SALES2
//	@Test(groups={"Sales", "AltaLinea", "Ola1"})
	public void TS94506_CRM_Fase_1_SalesCPQ_Alta_Linea_Configurar_Nueva_Linea_Visualizar_una_descripcion_por_varios_productos_iguales() {

		//Mismo TS6849
		assertTrue(false);
	}
//HACER EN SALES	
//	@Test
	public void TS6858_CRM_Fase_1_SalesCPQ_Alta_Linea_Asignar_SIMCARD_Visualizar_mensaje_al_asignar_un_ICCID_No_disponible(){
		assertTrue(false);
	}
	
	//Listo 26-01-18 no hay costo pasado a SALES2
	//@Test(groups={"Sales", "AltaDeLinea", "Ola1"})
	
	@Test
	public void TS6883_CRM_Fase_1_SalesCPQ_Alta_Linea_Costo_Operacion_Verificar_el_detalle_de_los_impuestos_aplicados_a_la_venta() throws Exception {
		Ta_CPQ cart = new Ta_CPQ(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		cart.selectFromRightPanel(RightPanel.DISPOSITIVOS);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		cart.addAnyProductToCart();
		sleep(7000);
		cart.getButtonNext().click();
		sleep(10000);
		
		
		driver.findElement(By.id("LineAssignment_nextBtn")).click();
		sleep(7000);
		//driver.findElement(By.xpath("//*[@id=\"DeliveryMethodConfiguration_nextBtn\"]/p")).click();
		sleep(7000);
		
		assertTrue(driver.findElement(By.xpath("//*[@id=\"Invoice\"]/div/ng-include/div/div[2]/div[2]/div[2]")).isDisplayed());
		//verificar impuestos
		
	}
	
	
	/*
	 * TODO: el assert deber�a verificar que el dropdown con id "DeliveryMethod" ofrezca varios m�todos de entrega.
	 * Actualmente el bot�n est� bloqueado, y no se puede ver que opciones contiene.
	 * 
	 * Ultima revision 05-02-18 PASSED
	 * */
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"})
	public void TS94520_CRM_Fase_1_SalesCPQ_Alta_Linea_Costo_Operacion_Visualizar_costo_cero_en_modo_de_entrega() {
		SalesBase SB = new SalesBase(driver);
		sleep(20000);
		SB.elegirplan("Galaxy s8");
		sleep(20000);
		List <WebElement> plan = driver.findElements(By.cssSelector(".slds-button.cpq-item-has-children"));
		boolean a = false;
		for (WebElement x : plan) {
			if (x.getText().toLowerCase().contains("galaxy s8")) {
				a = true;
			}
		}
		List <WebElement> precio = driver.findElements(By.cssSelector(".slds-col.slds-shrink.slds-text-align--center"));
		Assert.assertTrue(a);
		Assert.assertTrue(precio.get(3).getText().contains("0,00"));
		assertTrue(false);
	}
	
	/**
	 * Verifica que aparezca la opcion "Presencial" como un metodo de entrega.
	 */
	@Test
	public void TS6893_CRM_Fase_1_SalesCPQ_Alta_Linea_Modo_de_Entrega_Seleccionar_modo_de_entrega_presencial_Producto_Tangible () throws Exception {
		Ta_CPQ cart = new Ta_CPQ(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		cart.selectFromRightPanel(RightPanel.DISPOSITIVOS);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		cart.addAnyProductToCart();
		sleep(7000);
		cart.getButtonNext().click();
		sleep(7000);
		//WebElement waiter = wait.until(ExpectedConditions.elementToBeClickable(By.className("ng-binding")));
		//BillSimulation bill = new BillSimulation (driver);
		//presiono Siguiente 1 vezpara llegar al paso "Modo de Entrega"
		//bill.clickOnNext();
		driver.findElement(By.id("LineAssignment_nextBtn")).click();
		sleep(7000);
		//driver.findElement(By.xpath("//*[@id=\"DeliveryMethodConfiguration_nextBtn\"]/p")).click();
		//sleep(7000);
		WebElement selectDeliveryMethod = driver.findElement(By.id("DeliveryMethod"));
		Select deliveryMethod = new Select(selectDeliveryMethod);
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<String> optionsDeliveryMethod = new ArrayList<String>();
		for (WebElement option: deliveryMethod.getOptions()) {
			optionsDeliveryMethod.add(option.getText());
		}
		
		Assert.assertTrue(optionsDeliveryMethod.contains("Presencial"));
	}
	
	@Test//(groups = {"Fase2-1"})
	public void TS11950_CRM_Fase_2_SalesCPQ_Alta_Linea_Nueva_Venta_Verificar_campo_desplegable_con_cuentas_activas() {
		TS6835_CRM_Fase_SalesCPQ_Alta_Linea_Configurar_Nueva_Linea_Lista_de_cuentas_del_cliente();
	}
	
	@Test //(groups = {"Fase2-1"})
	public void TS11951_CRM_Fase_2_SalesCPQ_Alta_Linea_Nueva_Venta_Verificar_que_se_habilite_la_opcion_de_creacion_de_cuenta_nueva() {
		TS6836_CRM_Fase_1_SalesCPQ_Alta_Linea_Configurar_Nueva_Linea_Nueva_Cuenta();
	}
	
	/**Se verifica que el sistema muestra disponibles los ciclos de facturacion 1, 7, 14 y 21
	 * @throws Exception *
	 * 
	 */

	
	/**
	 * Se verifica que, cuando se selecciona un producto para linea movil 
	 * del Bundle Convergente, se agrega a la vista previa del carrito 
	 * y se habilita el boton Siguiente
	 */
	@Test(groups = {"Fase2-1"})
	public void TS15424_CRM_Fase_2_SalesCPQ_Nueva_Venta_Seleccion_Dispositivos_Verificar_boton_siguiente_habilitado() {
		Ta_CPQ cart = new Ta_CPQ(driver);
		cart.deleteAddedProducts();
		cart.selectFromRightPanel(RightPanel.BUNDLES);
		cart.addAnyProductToCartThatNeedsPrefactibility();
		sleep(4000);
		Assert.assertNotEquals(cart.getCartStatus(),"Incomplete");
	}
	
	/**
	 * Se verifica que, cuando no se selecciona un producto para linea movil del 
	 * Bundle Convergente, no se agrega a la vista previa del carrito, no se encuentra habilitado el boton Siguiente
	 * @throws Exception 
	 */
	@Test(groups = {"Fase2-1"})
	public void TS15423_CRM_Fase_2_SalesCPQ_Nueva_Venta_Seleccion_Dispositivos_Verificar_boton_siguiente_inhabilitado() throws Exception {
		Ta_CPQ cart = new Ta_CPQ(driver);
		cart.deleteAddedProducts();
		cart.addAnyProductToCart();
		Assert.assertEquals(cart.getCartStatus(), "Incomplete");
		Assert.assertFalse(cart.getButtonNext().isEnabled());
	}
	
	/**
	 * Se visualiza que el producto movil se incorpora en forma automatica 
	 * dentro de la familia Dispositivos cuando se agrega a la vista previa del carrito
	 * @throws Exception 
	 */
	@Test(groups = {"Fase2-1"})
	public void TS15422_CRM_Fase_2_SalesCPQ_Nueva_Venta_Seleccion_Dispositivos_Verificar_producto_incorporado_Autom_Familia_Dispositivos() throws Exception {
		Ta_CPQ cart = new Ta_CPQ(driver);
		cart.deleteAddedProducts();
		cart.selectFromRightPanel(RightPanel.DISPOSITIVOS);
		cart.addAnyProductToCart();
		sleep(4000);
				
		//WebElement leftProductDiv = cart.getAddableDivProduct(1);
		//leftProductDiv = leftProductDiv.findElement(By.cssSelector(".slds-tile__title.slds-truncate.product-name"));
		String leftProductName = cart.addAnyProductToCart().getText();
		//String leftProductName = leftProductDiv.getText().trim();
		Assert.assertTrue(cart.verifyAddition(cart.getAddedProducts(), leftProductName));
		
		//Assert.assertTrue(cart.getAddedProducts().contains(leftProductName));
	}
	
 	//Almer:listo. detalles:faltan planes para comparar
	@Test(groups = {"Fase2-1"})
	public void TS14361_CRM_Fase_2_SalesCPQ_Carrito_CreacionCategorias_Verificar_contenido_de_la_categoria_Planes(){
		Ta_CPQ cart = new Ta_CPQ(driver);
		  cart.deleteAddedProducts();
		  cart.selectFromRightPanel(RightPanel.PLANES);
		  try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		 //Falta completar por Bug del Sistema planes no disponibles.
		  boolean disponible=false;
		  try {
		  if(driver.findElement(By.cssSelector(".slds-tile__title slds-truncate.product-name")).isDisplayed()) {
			 if(cart.verificarSiContenido("plan movil")&&cart.verificarSiContenido("plan datos"))
				 disponible=true;
			 //aqui van los planes que se van a comparar
		  }
		  else
			  disponible=false;}
		  catch(org.openqa.selenium.NoSuchElementException e) {disponible=false;}
		  assertTrue(disponible);
	}
	
	//Almer:listo. Faltan los datos a comparar
	@Test(groups = {"Fase2-1"})
	public void TS14362_CRM_Fase_2_SalesCPQ_Carrito_CreacionCategorias_Verificar_contenido_de_la_categoria_TV(){
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Ta_CPQ cart = new Ta_CPQ(driver);
		  cart.deleteAddedProducts();
		  cart.selectFromRightPanel(RightPanel.TV);
		  try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  //La palabra "combo" debe ser remplazada por los datos a comparar
		  assertTrue(cart.verificarSiContenido("Combo"));  
	}
	
	//Almer:listo
	@Test(groups = {"Fase2-1"})
	public void TS14357_CRM_Fase_2_SalesCPQ_Carrito_CreacionCategorias_Verificar_visualizacion_de_productos_y_servicios_agrupados_por_categoria() {
		Ta_CPQ cart = new Ta_CPQ(driver);
		  cart.deleteAddedProducts();
		  try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  assertTrue(cart.categoriasDisponibles());
	}
	
	//Almer: listo
	@Test(groups={"Fase2-1"})
	public void TS14280_CRM_Fase_2_SalesCPQ_Carrito_Productos_Verificar_Orden_de_Familias_v360() {
		Ta_CPQ cart = new Ta_CPQ(driver);
		  cart.deleteAddedProducts();
		  try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		 assertTrue(cart.categoriasDisponiblesEnOrden());
	}
	
	//Almer:listo
	@Test(groups={"Fase2-1"})
	public void TS14282_CRM_Fase_2_SalesCPQ_Carrito_Productos_Verificar_Req_domicilio_de_instalacion_Producto(){
		Ta_CPQ cart = new Ta_CPQ(driver);
		  cart.deleteAddedProducts();
		  try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  assertTrue(cart.CheckAnyProductRequiresPrefactibility());
	}
	
	//Almer:listo
	@Test(groups={"Fase2-1"})
	public void TS14284_CRM_Fase_2_SalesCPQ_Carrito_Productos_Verificar_Req_domicilio_de_instalacion_Familia(){
		Ta_CPQ cart = new Ta_CPQ(driver);
		  cart.deleteAddedProducts();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  cart.selectFromRightPanel(RightPanel.BUNDLES);
		  try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  assertTrue(cart.CheckAnyProductRequiresPrefactibility());
	}
	
	//Almer:listo
	@Test(groups={"Fase2-1"})
	public void TS15331_CRM_Fase_2_SalesCPQ_Prefactibilidad_v360_Visualizar_boton_Prefactibilidad_en_v360(){
		Ta_CPQ cart = new Ta_CPQ(driver);
		  cart.deleteAddedProducts();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  WebElement boton = driver.findElement(By.cssSelector(".slds-list--horizontal.slds-col.slds-no-flex.slds-align-top")).findElement(By.className("slds-button-group")).findElement(By.cssSelector(".slds-button.slds-button--neutral"));
		  //boton.click();
		  assertTrue(boton.isDisplayed());
	}
	
	//Almer:listo
	@Test(groups={"Fase2-1"})
	public void TS14331_CRM_Fase_2_SalesCPQ_Ventas_ProductosEPC_Verificar_producto_incorporado_Automaticamente() throws Exception {
		Ta_CPQ cart = new Ta_CPQ(driver);
		cart.deleteAddedProducts();
		sleep(5000);
		String leftProductName = cart.addAnyProductToCart().getText();
		sleep(4000);		
		//WebElement leftProductDiv = cart.getAddableDivProduct(1);
	//	leftProductDiv = leftProductDiv.findElement(By.cssSelector(".slds-tile__title.slds-truncate.product-name"));
		Assert.assertTrue(cart.verifyAddition(cart.getAddedProducts(), leftProductName));
		//Assert.assertTrue(cart.getAddedProducts().get(0).contains(leftProductName.substring(0, cart.getAddedProducts().get(0).length())));
	}
	
	//Ultimo de Nacho
	@Test(groups = {"Sales", "AltaDeLinea", "Ola1"})
	public void TS94706_CRM_Fase_2_SalesCPQ_Nueva_Venta_Orden_Venta_Verficar_ciclo_de_facturacion_asignado_por_default() throws Exception {
		Ta_CPQ cart = new Ta_CPQ(driver);
		cart.deleteAddedProducts();
		cart.addAnyProductToCart();
		WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(cart.getButtonNext()));
		nextButton.click();
		LineAssignment lineAssignmentPage = new LineAssignment(driver);
		lineAssignmentPage.clickOnNext();
		BillSimulation billSimulationPage = new BillSimulation(driver);
		billSimulationPage.clickOnNext();
		DeliveryMethod deliveryMethodPage = new DeliveryMethod (driver);
		Select billingCycleSelect = new Select(deliveryMethodPage.getBillingCycle());
		Assert.assertEquals(billingCycleSelect.getFirstSelectedOption().getText(), "21");
	}
}



	
	

