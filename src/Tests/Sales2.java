package Tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;  
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.Accounts;
import Pages.BasePage;
import Pages.BillSimulation;
import Pages.ContactSearch;
import Pages.CustomerCare;
import Pages.DeliveryMethod;
import Pages.HomeBase;
import Pages.LineAssignment;
import Pages.SalesBase;
import Pages.Ta_CPQ;
import Pages.Ta_CPQ.RightPanel;
import Pages.TechCare_Ola1;

public class Sales2 extends TestBase{

	SalesBase sb;
	String DNI = "DNI"; 
	String provincia="Santa Fe" ;
	String localidad="ROSARIO";
	String plan="Plan con tarjeta";
	protected  WebDriverWait wait;
	
	@AfterClass(alwaysRun=true)
	public void tearDown() {
		driver.close();
		driver.quit();
	}
	
	@AfterMethod(alwaysRun=true)
	public void deslogin(){
		sleep(2000);
		SalesBase SB = new SalesBase(driver);
		driver.switchTo().defaultContent();
		sleep(6000);
		SB.cerrarPestaniaGestion(driver);
		
		sleep(5000);
		goToLeftPanel2(driver, "Inicio");
		sleep(18000);

	}

		
	@BeforeClass(alwaysRun=true)
	public void init() {
		inicializarDriver();
		sb = new SalesBase(driver);
		loginAgente(driver);
		try {Thread.sleep(22000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			
		driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
		sleep(18000);
		SalesBase SB = new SalesBase(driver);
		CustomerCare cc = new CustomerCare(driver);
		driver.switchTo().defaultContent();
		sleep(3000);
		goToLeftPanel2(driver, "Inicio");
		sleep(18000);
		try{
			SB.cerrarPestaniaGestion(driver);}
		catch(Exception ex1) {
		}
	}
	
	@BeforeMethod(alwaysRun=true)
	public void setup() throws Exception {
		Accounts accountPage = new Accounts(driver);
		
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		boolean enc = false;
		int index = 0;
		for(WebElement frame : frames) {
			try {
				System.out.println("aca");
				driver.switchTo().frame(frame);

				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).getText(); //each element is in the same iframe.
				//System.out.println(index); //prints the used index.

				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).isDisplayed(); //each element is in the same iframe.
				//System.out.println(index); //prints the used index.

				driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
				enc = true;
				break;
			}catch(NoSuchElementException noSuchElemExcept) {
				index++;
				driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
			}
		}
		if(enc == false)
			index = -1;
		try {
				driver.switchTo().frame(frames.get(index));
		}catch(ArrayIndexOutOfBoundsException iobExcept) {System.out.println("Elemento no encontrado en ningun frame 2.");
			
		}
		List<WebElement> botones = driver.findElements(By.tagName("button"));
		for (WebElement UnB : botones) {
			System.out.println(UnB.getText());
			if(UnB.getText().equalsIgnoreCase("gesti\u00f3n de clientes")) {
				UnB.click();
				break;
			}
		}
		
		sleep(14000);
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SearchClientDocumentNumber")));
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=5, dataProvider="SalesCuentaActiva")
	public void TS94698_Nueva_Venta_Modo_de_Entrega_Verificar_Solicitud_de_Domicilio_de_envio_Envio_Estandar(String sCuenta, String sDni, String sLinea) throws IOException{
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.BuscarCuenta(DNI, sDni);
		sb.acciondecontacto("catalogo");
		boolean x = false;
		sleep(25000);
		List<WebElement> cam = driver.findElements(By.cssSelector(".slds-m-left--x-small.slds-button.slds-button--brand"));
		for(WebElement c : cam ){	
			if(c.getText().toLowerCase().equals("cambiar")){
				c.click();
			}
		sleep(10000);	
		List<WebElement> frame2 = driver.findElements(By.tagName("iframe"));
		driver.switchTo().frame(frame2.get(0));
		Select env = new Select (driver.findElement(By.id("DeliveryMethod")));
		env.selectByVisibleText("Delivery");
		driver.findElement(By.id("SalesChannelConfiguration_nextBtn")).click();
		sleep(10000);
		driver.switchTo().defaultContent();
		}
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(25000);
		sb.Crear_DomicilioLegal( provincia, localidad,"falsa", "", "2154", "", "", "2453");
		sleep(30000);
		CustomerCare page = new CustomerCare(driver);
		WebElement sig = driver.findElement(By.id("LineAssignment_nextBtn"));
		page.obligarclick(sig);
		Select delir= new Select (driver.findElement(By.id("DeliveryServiceType")));
		delir.selectByVisibleText("Env\u00edo Est\u00e1ndar");	
		Assert.assertEquals(delir.getFirstSelectedOption().getText(),"Env\u00edo Est\u00e1ndar");
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=5,dataProvider="SalesCuentaActiva")
	public void TS95296_Ventas_General_Verificar_Que_Este_El_Paso_En_Metodo_De_Entrega_Distinto_A_Presencial(String sCuenta, String sDni, String sLinea) throws IOException{
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.BuscarCuenta(DNI, sDni);
		sb.acciondecontacto("catalogo");
		boolean x = false;
		sleep(20000);
		List<WebElement> cam = driver.findElements(By.cssSelector(".slds-m-left--x-small.slds-button.slds-button--brand"));
		for(WebElement c : cam ){	
			if(c.getText().toLowerCase().equals("cambiar")){
				c.click();
			}
		sleep(10000);	
		List<WebElement> frame2 = driver.findElements(By.tagName("iframe"));
		driver.switchTo().frame(frame2.get(0));
		Select env = new Select (driver.findElement(By.id("DeliveryMethod")));
		env.selectByVisibleText("Delivery");
		driver.findElement(By.id("SalesChannelConfiguration_nextBtn")).click();
		sleep(10000);
		driver.switchTo().defaultContent();
		}
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(18000);
		sb.Crear_DomicilioLegal(provincia, localidad, "falsa", "", "1000", "", "", "1549");
		sleep(20000);
		CustomerCare page = new CustomerCare(driver);
		WebElement sig = driver.findElement(By.id("LineAssignment_nextBtn"));
		page.obligarclick(sig);
		sleep(10000);
		boolean esta = false;
		List<WebElement> titulos = driver.findElements(By.cssSelector(".slds-page-header__title.vlc-slds-page-header__title.slds-truncate.ng-binding"));
		for(WebElement UnT : titulos) {
			if(UnT.getText().toLowerCase().contains("modo de entrega"))
				esta = true;
		}
		Assert.assertTrue(esta);
	}
	
	@Test(groups = { "Sales", "AltaDeLinea", "Ola1" }, priority=5, dataProvider="SalesCuentaActiva")
	public void TS94699_Nueva_Venta_Modo_de_Entrega_Verificar_Solicitud_de_Domicilio_de_envio_Envio_Express(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(25000);
		List<WebElement> cam = driver.findElements(By.cssSelector(".slds-m-left--x-small.slds-button.slds-button--brand"));
		for (WebElement c : cam) {
			if (c.getText().toLowerCase().equals("cambiar")) {
				c.click();
			}
			sleep(15000);
			List<WebElement> frame2 = driver.findElements(By.tagName("iframe"));
			driver.switchTo().frame(frame2.get(0));
			Select env = new Select(driver.findElement(By.id("DeliveryMethod")));
			env.selectByVisibleText("Delivery");
			driver.findElement(By.id("SalesChannelConfiguration_nextBtn")).click();
			sleep(10000);
			driver.switchTo().defaultContent();
		}
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(25000);
		sb.Crear_DomicilioLegal( provincia, localidad,"falsa", "", "2154", "", "", "2453");
		sleep(30000);
		CustomerCare page = new CustomerCare(driver);
		WebElement sig = driver.findElement(By.id("LineAssignment_nextBtn"));
		page.obligarclick(sig);
		Select delir = new Select(driver.findElement(By.id("DeliveryServiceType")));
		delir.selectByVisibleText("Env\u00edo Express");
		Assert.assertEquals(delir.getFirstSelectedOption().getText(), "Env\u00edo Express");
	}
	
	@Test(groups={"Sales", "AltaDeContacto", "Ola1","filtrado"}, priority=2, dataProvider="SalesCuentaActiva")
	public void TS94880_Alta_De_Contacto_Busqueda_Verificar_Accion_De_Ver_Detalle_De_Contacto(String sCuenta, String sDni, String sLinea) throws IOException{//dentro del ver detalles no se muestran las opciones de actualizar ni lanzar carrito
		SalesBase SB = new SalesBase(driver);
		boolean cat = false;
		boolean dc = false;
		sb.BuscarCuenta(DNI, sDni);
		driver.findElement(By.id("tab-scoped-3__item")).click();
		List<WebElement> btns = driver.findElements(By.cssSelector(".slds-button.slds-button.slds-button--icon"));
		for(WebElement e: btns){
			if(e.getText().toLowerCase().equals("catalogo")){ 
				cat = true;
			}else { if (e.getText().toLowerCase().equals("ver contacto")) {
				dc = true;
				}
			}
		}
		Assert.assertTrue(cat&&dc);
	} 

	
	@Test(groups={"Sales", "AltaDeContacto", "Ola1"}, priority=3, dataProvider="SalesCuentaActiva")
	public void TS94661_Alta_De_Contacto_Persona_Fisica_Verificar_Categoria_Impositiva_Por_Default(String sCuenta, String sDni, String sLinea) throws IOException{
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		CustomerCare CC = new CustomerCare(driver);
		CC.obligarclick(driver.findElement(By.id("tab-scoped-3__item")));
		sb.acciondecontacto("catalogo");
		sleep(25000);
		/*List<WebElement> cam = driver.findElements(By.cssSelector(".slds-m-left--x-small.slds-button.slds-button--brand"));
		for(WebElement c : cam ){	
			if(c.getText().toLowerCase().equals("cambiar")){
				c.click();
			}
		sleep(7000);	
		List<WebElement> frame2 = driver.findElements(By.tagName("iframe"));
		driver.switchTo().frame(frame2.get(0));
		Select env = new Select (driver.findElement(By.id("DeliveryMethodSelection")));
		env.selectByVisibleText("Delivery");
		driver.findElement(By.id("SalesChannelConfiguration_nextBtn")).click();
		sleep(10000);
		driver.switchTo().defaultContent();
		}*/
		sb.elegirplan("Plan con tarjeta");
		sleep(15000);
		sb.continuar();
		sleep(20000);
		Select condI = new Select(driver.findElement(By.id("ImpositiveCondition")));
		Assert.assertTrue(condI.getFirstSelectedOption().getText().equalsIgnoreCase("iva consumidor final"));
	}
	
	@Test(groups={"Sales", "AltaDeCuenta", "Ola1"}, priority=4, dataProvider="SalesCuentaActiva")
	public void TS95515_Alta_de_Cuenta_Business_Visualizar_los_campos_de_documentacion_impositiva_abajo(String sCuenta, String sDni, String sLinea) throws IOException {
		CustomerCare cc = new CustomerCare(driver);
		sb.BtnCrearNuevoCliente();
		String asd = driver.findElement(By.id("SearchClientDocumentNumber")).getAttribute("value");
		ContactSearch contact = new ContactSearch(driver);
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("FirstName")).sendKeys("yy");
		driver.findElement(By.id("LastName")).sendKeys("z");
		driver.findElement(By.id("Birthdate")).sendKeys("28/12/1999");
		contact.sex("masculino");
		driver.findElement(By.id("Contact_nextBtn")).click();
		sleep(18000);
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(20000);
		BasePage imp = new BasePage(driver);
		imp.setSimpleDropdown(driver.findElement(By.id("ImpositiveCondition")), "IVA Responsable Inscripto");
		sb.Crear_DomicilioLegal(provincia, localidad, "falsa", "", "1000", "", "", "1549");
		sleep(2000);
		WebElement dom = driver.findElement(By.id("State"));
		WebElement doc = driver.findElement(By.id("ImageDNI"));
		Assert.assertTrue(doc.getLocation().y > dom.getLocation().y);
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=8, dataProvider="SalesCuentaActiva") 
	public void TS94637_Ventas_Nueva_Venta_Verificar_creacion_orden_de_venta_Usuario(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(10000);
		sb.elegirplan("plan con tarjeta");
		WebElement num = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider"));
		Assert.assertTrue(num.getText().contains("Nro. Orden:"));
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=3, dataProvider="SalesCuentaActiva")
	public void TS94652_Ventas_NumeroOrden_Verificar_Orden_de_Venta_Abierta_Modo_de_Entrega(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(25000);
		driver.findElement(By.cssSelector(".slds-m-left--x-small.slds-button.slds-button--brand")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DeliveryMethod")));
		Select env = new Select (driver.findElement(By.id("DeliveryMethod")));
		env.selectByVisibleText("Delivery");
		driver.findElement(By.id("SalesChannelConfiguration_nextBtn")).click();
		sleep(10000);
		driver.switchTo().defaultContent();
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(15000);
		sb.Crear_DomicilioLegal( provincia, localidad, "falsa", "", "4537", "", "", "5384");
		sleep(20000);
		driver.findElement(By.id("LineAssignment_nextBtn")).click();
		sleep(10000);
		List <WebElement> num = driver.findElements(By.className("slds-form-element__control"));
		boolean a = false;
		for (WebElement x : num) {
			if (x.getText().contains("Nro. orden:")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=3, dataProvider="SalesCuentaActiva")
	public void TS94651_Ventas_NumeroOrden_Verificar_Orden_de_Venta_Abierta_Seleccion_de_Linea(String sCliente, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sb.agregarplan("Plan con tarjeta");
		sleep(15000);
		sb.continuar();
		sleep(10000);
		sb.Crear_DomicilioLegal("Buenos Aires","Vicente Lopez", "falsa", "", "4537", "", "", "5384");
		sleep(15000);
		List <WebElement> num = driver.findElement(By.id("OrderStatus")).findElements(By.className("slds-form-element__control"));
		boolean a = false;
		for (WebElement x : num) {
			System.out.println(x.getText());
			if (x.getText().contains("Nro. orden:")){
				a = true;
			
			}}
		Assert.assertTrue(a);
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=3, dataProvider="SalesCuentaActiva") 
	public void TS94650_Ventas_NumeroOrden_Verificar_Orden_de_Venta_Abierta_Seleccionar_un_producto(String sCliente, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(25000);
		List<WebElement> cam = driver.findElements(By.cssSelector(".slds-m-left--x-small.slds-button.slds-button--brand"));
			for(WebElement c : cam ){	
				if(c.getText().toLowerCase().equals("cambiar")){
				c.click();
			}
		sleep(15000);	
		List<WebElement> frame2 = driver.findElements(By.tagName("iframe"));
		driver.switchTo().frame(frame2.get(0));
		Select env = new Select (driver.findElement(By.id("DeliveryMethod")));
		env.selectByVisibleText("Store Pick Up");
		sleep(2000);
		Select sta = new Select (driver.findElement(By.id("State")));
		sta.selectByVisibleText("Ciudad Aut\u00f3noma de Buenos Aires");
		sleep(2000);
		Select cit = new Select(driver.findElement(By.id("City")));
		cit.selectByVisibleText("CIUD AUTON D BUENOS AIRES");
		sleep(3000);
		driver.findElement(By.id("Store")).click();
		driver.findElements(By.cssSelector(".slds-list__item.ng-binding.ng-scope")).get(1).click();
		sleep(2000);
		driver.findElement(By.id("SalesChannelConfiguration_nextBtn")).click();
		sleep(10000);
		driver.switchTo().defaultContent();
		}
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(10000);
		List<WebElement> cont = driver.findElements(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand"));
			for(WebElement c : cont){
				c.getText().equals("Continuar");
					c.click();
			}
		sleep(5000);
		CustomerCare page = new CustomerCare(driver);
		WebElement sig = driver.findElement(By.id("DeliveryMethodConfiguration_nextBtn"));
		page.obligarclick(sig);
		sleep(7000);
		Assert.assertFalse(driver.findElement(By.id("DeliveryMethod")).isEnabled());
	
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=3, dataProvider="SalesCuentaActiva")  
	public void TS94646_Ventas_NumeroOrden_Verificar_Orden_de_Venta_Abierta_Medio_de_Pago(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(10000);
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(10000);
		List<WebElement> cont = driver.findElements(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand"));
			for(WebElement c : cont){
				c.getText().equals("Continuar");
					c.click();
			}
		sleep(20000);
		sb.Crear_DomicilioLegal(provincia, localidad, "falsa", "", "1000", "", "", "1549");
		sleep(24000);
		CustomerCare page = new CustomerCare(driver);
		WebElement sig = driver.findElement(By.id("LineAssignment_nextBtn"));
		page.obligarclick(sig);
		sleep(8000);
		driver.findElement(By.id("ICCDAssignment_nextBtn")).click();
		sleep(8000);
		WebElement sigue = driver.findElement(By.id("InvoicePreview_nextBtn"));
		page.obligarclick(sigue);
		sleep(15000);
		boolean x = false;
		List<WebElement> ord = driver.findElements(By.cssSelector(".ng-binding"));
			for(WebElement o : ord){
				if(	o.getText().contains("Nro. orden")){
				o.isDisplayed();
				x=true;
				}
			}
		Assert.assertTrue(x);
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=3,dataProvider="SalesCuentaActiva")
	public void TS94641_Ventas_NumeroOrden_Verificar_Orden_de_Venta_Abierta_Nueva_Venta(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		boolean x = false;
		WebElement num = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider"));
			if( num.getText().contains("Nro. Orden:")){
				num.isDisplayed();
				x=true;
			}
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=3, dataProvider="SalesCuentaActiva")
	public void TS94643_Ventas_NumeroOrden_Verificar_Orden_de_Venta_Abierta_Seleccion_de_Linea(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");
		sleep(25000);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();
		sleep(25000);
		sb.Crear_DomicilioLegal( provincia, localidad,"falsa", "", "2154", "", "", "2453");
		sleep(30000);
		WebElement ord = driver.findElement(By.id("OrderStatus"));
		Assert.assertTrue(ord.getText().contains("Nro. orden:"));
		Assert.assertTrue(ord.isDisplayed());			
		
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=8, dataProvider="SalesCuentaActiva" )  
	public void TS94639_Ventas_Nueva_Venta_Verificar_creacion_orden_de_venta_desde_un_Asset_Usuario(String sCuenta, String sDni, String sLinea) {
		driver.findElement(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope")).click();
		sleep(2000);
		driver.findElement(By.id("alert-ok-button")).click();
		sleep(5000);
		driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
		sleep(6000);
		TechCare_Ola1 tc = new TechCare_Ola1(driver);
		sb.cerrarTodasLasPestanias();
		sleep(8000);
		tc.selectAccount (sCuenta);
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.open")));
		driver.findElement(By.cssSelector(".console-card.open")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-col.slds-no-flex.cpq-base-header")));
		List <WebElement> num = driver.findElements(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider"));
		boolean a = false;
		for (WebElement x : num) {
			System.out.println(x.getText());
			if (x.getText().contains("Nro. Orden:")) {
				a = true;
			}
		}	
		Assert.assertTrue(a);
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=1, dataProvider="SalesCuentaActiva" )//luego pasar a cuenta con gestiones
	public void TS94716_Ventas_VentasGestiones_Visualizar_un_historico_de_gestiones_realizadas(String sCuenta, String sDni, String sLinea) {
		driver.findElement(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope")).click();
		sleep(2000);
		driver.findElement(By.id("alert-ok-button")).click();
		sleep(5000);
		driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
		sleep(6000);
		TechCare_Ola1 tc = new TechCare_Ola1(driver);
		sb.cerrarTodasLasPestanias();
		tc.selectAccount (sCuenta);
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-media.slds-media--timeline.slds-timeline__media--custom-custom91")));
		List <WebElement> gest = driver.findElements(By.cssSelector(".slds-media.slds-media--timeline.slds-timeline__media--custom-custom91"));
		boolean a = false;
		boolean b = false;
		for (WebElement x : gest) {
			if (x.getText().toLowerCase().contains("t\u00edtulo")) {
				System.out.println(x.getText());
				a = true;
			}
			if (x.getText().toLowerCase().contains("estado")) {	
				b = true;
			}
		}
		Assert.assertTrue(a && b);
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1","filtrado"}, priority=1, dataProvider="SalesCuentaActiva")//agregar luego uno de cuenta con gestiones
	public void TS94611_Alta_Linea_Nueva_Venta_Verificar_acceso_a_Nueva_Venta_desde_vista_360(String sCuenta, String sDni, String sLinea) {
		driver.findElement(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope")).click();
		sleep(2000);
		driver.findElement(By.id("alert-ok-button")).click();
		sleep(5000);
		driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
		sleep(6000);
		TechCare_Ola1 tc = new TechCare_Ola1(driver);
		sb.cerrarTodasLasPestanias();
		sleep(5000);
		tc.selectAccount (sCuenta);
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.open")));
		WebElement nv = driver.findElement(By.cssSelector(".console-card.open"));
		Assert.assertTrue(nv.getText().contains("Nueva Venta"));
	}
	
	@Test(groups={"Sales", "AltaDeCuenta", "Ola1"}, priority=2, dataProvider="SalesCuentaActiva")
	public void TS94972_Alta_Cuenta_Busqueda_Verificar_flujo_de_cuenta_Clonada(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(25000);
		sb.elegirplan("Plan con tarjeta");
		sleep(25000);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();
		sleep(20000);
		Assert.assertTrue(driver.findElement(By.id("ContactName")).getAttribute("value").toLowerCase().contains(sCuenta.toLowerCase()));
		List <WebElement> prov = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding"));
		boolean a = false;
		for (WebElement x : prov) {
			if (x.getText().toLowerCase().contains("provincia")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test(groups={"Sales", "AltaDeContacto", "Ola1"}, priority=4, dataProvider="SalesCuentaActiva")
	public void TS94582_Alta_de_Contacto_Persona_Fisica_Verificar_confirmacion_de_adjunto_exitoso_XX(String sCuenta, String sDni, String sLinea) throws IOException {
		CustomerCare cc = new CustomerCare(driver);
		sb.BtnCrearNuevoCliente();
		String asd = driver.findElement(By.id("SearchClientDocumentNumber")).getAttribute("value");
		ContactSearch contact = new ContactSearch(driver);
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("FirstName")).sendKeys("yy");
		driver.findElement(By.id("LastName")).sendKeys("z");
		driver.findElement(By.id("Birthdate")).sendKeys("28/12/1999");
		contact.sex("masculino");
		driver.findElement(By.id("Contact_nextBtn")).click();
		sleep(18000);
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(20000);
		sb.Crear_DomicilioLegal(provincia, localidad, "falsa", "", "1000", "", "", "1549");
		sleep(24000);
		WebElement sig = driver.findElement(By.id("LineAssignment_nextBtn"));
		cc.obligarclick(sig);
		sleep(10000);
		cc.obligarclick(driver.findElement(By.id("ICCDAssignment_nextBtn")));
		sleep(15000);
		
		cc.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		sleep(15000);
		cc.obligarclick(driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")));
		sleep(15000);
		sb.elegirvalidacion("DOC");
		sleep(8000);
		driver.findElement(By.id("FileDocumentImage")).sendKeys("C:\\Users\\florangel\\Downloads\\mapache.jpg");
		sleep(3000);
		WebElement up = driver.findElement(By.cssSelector(".slds-box.slds-input-has-icon.slds-input-has-icon--right.ta-boxImageName"));
		Assert.assertTrue(up.getText().toLowerCase().contains("mapache.jpg"));
		Assert.assertTrue(up.getText().toLowerCase().contains("30.55 kb"));
	}
	
	@Test(groups={"Sales", "AltaDeContacto", "Ola1"}, priority=2, dataProvider="SalesContactoSinCuenta")
	public void TS94529_Alta_de_Contacto_Persona_Fisica_Confirmar_direccion_de_email_existente_30(String sCuenta, String sDni) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		String a = driver.findElement(By.xpath("//*[@id=\"tab-scoped-3\"]/section/div/table/tbody/tr/td[4]")).getText();
		List <WebElement> cuenta = driver.findElements(By.cssSelector(".slds-truncate.ng-binding"));
		for (WebElement x : cuenta) {
			if (x.getText().toLowerCase().contains(sCuenta.toLowerCase())) {
				x.click();
				break;
			}
		}
		sleep(7000);
		String b = driver.findElement(By.cssSelector(".slds-input.form-control.ng-pristine.ng-untouched.ng-valid.ng-not-empty.ng-dirty")).getAttribute("value");
		Assert.assertTrue(a.equals(b));
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=3, dataProvider="SalesCuentaActiva")
	public void TS94913_Ventas_General_Verificar_Completitud_Pendiente_para_cada_estado(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sb.elegirplan("plan con tarjeta");
		sleep(15000);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();
		sleep(7000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".list-group.vertical-steps.ng-scope")).isDisplayed());
	}
	
	@Test(groups={"Sales", "AltaDeContacto", "Ola1","filtrado"}, priority=2)
	public void TS94530_Alta_de_Contacto_Persona_Fisica_Dar_de_alta_un_contacto_nuevo_48() {
		BasePage dni = new BasePage(driver);
		Random aleatorio = new Random(System.currentTimeMillis());
		aleatorio.setSeed(System.currentTimeMillis());
		int intAleatorio = aleatorio.nextInt(8999999)+1000000;
		dni.setSimpleDropdown(driver.findElement(By.id("SearchClientDocumentType")),"DNI");
		driver.findElement(By.id("SearchClientDocumentNumber")).click();
		driver.findElement(By.id("SearchClientDocumentNumber")).sendKeys(Integer.toString(intAleatorio));
		driver.findElement(By.id("SearchClientsDummy")).click();
		String a = driver.findElement(By.id("SearchClientDocumentNumber")).getAttribute("value");
		sleep(10000);
		List <WebElement> cc = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding"));
		for (WebElement x : cc) {
			if (x.getText().toLowerCase().contains("+ crear nuevo cliente")) {
				x.click();
				break;
			}
		}
		sleep(15000);
	
		ContactSearch contact = new ContactSearch(driver);
		contact.sex("masculino");
		driver.findElement(By.id("FirstName")).sendKeys("Cuenta");
		driver.findElement(By.id("LastName")).sendKeys("Generica");
		driver.findElement(By.id("Birthdate")).sendKeys("15/05/1980");
		driver.findElements(By.cssSelector(".slds-form-element__control.slds-input-has-icon.slds-input-has-icon--right")).get(2).findElement(By.tagName("input")).sendKeys("asdasd@gmail.com");
		driver.findElement(By.id("Contact_nextBtn")).click();
		sleep(10000);
		Accounts accountPage = new Accounts(driver);
		driver.navigate().refresh();
		sleep(4000);
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		boolean enc = false;
		int index = 0;
		for(WebElement frame : frames) {
			try {
				System.out.println("aca");
				driver.switchTo().frame(frame);

				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).getText(); //each element is in the same iframe.
				//System.out.println(index); //prints the used index.

				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).isDisplayed(); //each element is in the same iframe.
				//System.out.println(index); //prints the used index.

				driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
				enc = true;
				break;
			}catch(NoSuchElementException noSuchElemExcept) {
				index++;
				driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
			}
		}
		if(enc == false)
			index = -1;
		try {
				driver.switchTo().frame(frames.get(index));
		}catch(ArrayIndexOutOfBoundsException iobExcept) {System.out.println("Elemento no encontrado en ningun frame 2.");
			
		}
		
		sleep(14000);
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SearchClientDocumentNumber")));
		sb.BuscarCuenta(DNI, a);
		List <WebElement> cuenta = driver.findElements(By.cssSelector(".slds-truncate.ng-binding"));
		boolean b = false;
		for (WebElement x : cuenta) {
			if (x.getText().toLowerCase().contains("cuenta generica")) {
				b = true;
			}
		}
		WebElement verdni = driver.findElement(By.xpath("//*[@id=\"tab-scoped-3\"]/section/div/table/tbody/tr/td[3]"));
		Assert.assertTrue(b);
		Assert.assertTrue(verdni.getText().toLowerCase().contains(a));
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=3, dataProvider="SalesCuentaActiva")
	public void TS94696_Nueva_Venta_Modo_de_Entrega_Verificar_LOV_Tipo_de_Delivery(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(25000);
		driver.findElement(By.cssSelector(".slds-m-left--x-small.slds-button.slds-button--brand")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DeliveryMethod")));
		Select env = new Select (driver.findElement(By.id("DeliveryMethod")));
		env.selectByVisibleText("Delivery");
		driver.findElement(By.id("SalesChannelConfiguration_nextBtn")).click();
		sleep(10000);
		driver.switchTo().defaultContent();
		sb.elegirplan("Plan con tarjeta");
		sleep(15000);
		sb.continuar();
		sleep(25000);
		sb.Crear_DomicilioLegal( provincia, localidad,"falsa", "", "2154", "", "", "2453");
		sleep(30000);
		driver.findElement(By.id("LineAssignment_nextBtn")).click();
		sleep(10000);
		driver.findElement(By.id("DeliveryServiceType")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//*[text() = 'Env\u00edo Est\u00e1ndar']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[text() = 'Env\u00edo Express']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[text() = 'Retiro en Sucursal de Correo']")).isDisplayed());
		
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=3, dataProvider="SalesCuentaActiva")
	public void TS94690_Nueva_Venta_Modo_de_Entrega_Verificar_que_no_se_puede_cambiar_el_Modo_de_Entrega_Delivery(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(25000);
		driver.findElement(By.cssSelector(".slds-m-left--x-small.slds-button.slds-button--brand")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DeliveryMethod")));
		Select env = new Select (driver.findElement(By.id("DeliveryMethod")));
		env.selectByVisibleText("Delivery");
		driver.findElement(By.id("SalesChannelConfiguration_nextBtn")).click();
		sleep(10000);
		driver.switchTo().defaultContent();
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(10000);
		sb.Crear_DomicilioLegal( provincia, localidad,"falsa", "", "2154", "", "", "2453");
		sleep(25000);	
		driver.findElement(By.id("LineAssignment_nextBtn")).click();
		sleep(10000);
		WebElement mde = driver.findElement(By.id("DeliveryMethod"));
		Assert.assertTrue(mde.getAttribute("disabled").equals("true"));
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1","filtrado"}, priority=3, dataProvider="SalesCuentaActiva")
	public void TS94691_Nueva_Venta_Modo_de_Entrega_Verificar_que_no_se_puede_cambiar_el_Modo_de_Entrega_Store_Pick_Up(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(25000);
		driver.findElement(By.cssSelector(".slds-m-left--x-small.slds-button.slds-button--brand")).click();
		sleep(15000);
		List<WebElement> frame2 = driver.findElements(By.tagName("iframe"));
		driver.switchTo().frame(frame2.get(0));
		Select env = new Select (driver.findElement(By.id("DeliveryMethod")));env.selectByVisibleText("Store Pick Up");
		Select prov = new Select (driver.findElement(By.id("State")));
		prov.selectByVisibleText("Ciudad Aut\u00f3noma de Buenos Aires");
		Select loc = new Select (driver.findElement(By.id("City")));
		loc.selectByVisibleText("CIUD AUTON D BUENOS AIRES");
		driver.findElement(By.id("Store")).click();
		driver.findElements(By.cssSelector(".slds-list__item.ng-binding.ng-scope")).get(1).click();
		driver.findElement(By.id("SalesChannelConfiguration_nextBtn")).click();
		sleep(10000);
		driver.switchTo().defaultContent();
		sb.elegirplan("Plan con tarjeta");
		sleep(15000);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();
		sleep(15000);
		sb.Crear_DomicilioLegal( provincia, localidad,"falsa", "", "5846", "", "", "5248");
		sleep(20000);
		driver.findElement(By.id("LineAssignment_nextBtn")).click();
		sleep(10000);
		WebElement mde = driver.findElement(By.id("DeliveryMethod"));
		Assert.assertTrue(mde.getAttribute("disabled").equals("disabled"));	
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1","filtrado"}, priority=3, dataProvider="SalesCuentaActiva")
	public void TS94694_Nueva_Venta_Modo_de_Entrega_Verificar_que_se_habilite_Tipo_de_Delivery(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(25000);
		driver.findElement(By.cssSelector(".slds-m-left--x-small.slds-button.slds-button--brand")).click();
		sleep(15000);
		List<WebElement> frame2 = driver.findElements(By.tagName("iframe"));
		driver.switchTo().frame(frame2.get(0));
		Select env = new Select (driver.findElement(By.id("DeliveryMethod")));env.selectByVisibleText("Delivery");
		driver.findElement(By.id("SalesChannelConfiguration_nextBtn")).click();
		sleep(10000);
		driver.switchTo().defaultContent();
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(15000);
		sb.Crear_DomicilioLegal( provincia, localidad,"falsa", "", "5846", "", "", "5248");
		sleep(20000);
		driver.findElement(By.id("LineAssignment_nextBtn")).click();
		sleep(10000);
		Assert.assertTrue(driver.findElement(By.id("DeliveryServiceType")).isEnabled());
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=3, dataProvider="SalesCuentaActiva")
	public void TS94730_Alta_De_Linea_Verificar_LOV_De_Modalidad_Entrega_Para_Canal_Presencial_Agentes(String sCuenta, String sDni, String sLinea) throws IOException {
		boolean Pr = false;
		boolean Dl = false;
		boolean St = false;
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(25000);
		driver.findElement(By.cssSelector(".slds-m-left--x-small.slds-button.slds-button--brand")).click();
		sleep(15000);
		driver.switchTo().frame(sb.getFrameForElement(driver, By.id("DeliveryMethod")));
		List<WebElement> OMdE = new Select(driver.findElement(By.id("SalesChannelConfiguration")).findElement(By.id("DeliveryMethod"))).getOptions();
		for (WebElement UnM : OMdE) {
			if (UnM.getText().equalsIgnoreCase("presencial"))
				Pr = true;
			if (UnM.getText().equalsIgnoreCase("delivery"))
				Dl = true;
			if (UnM.getText().equalsIgnoreCase("store pick up"))
				St = true;
		}
		Assert.assertTrue(Pr&&Dl&&St);
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=3, dataProvider="SalesCuentaActiva")
	public void TS94733_Alta_De_Linea_Verificar_Default_De_Modalidad_Entrega_Para_Canal_Presencial_Agentes(String sCuenta, String sDni, String sLinea) throws IOException{
		SalesBase SB = new SalesBase(driver);
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		SB.acciondecontacto("catalogo");
		sleep(15000);
		assertTrue(driver.findElement(By.cssSelector(".slds-col.taChangeDeliveryMethod.slds-text-body--small.slds-m-left--large")).findElement(By.tagName("span")).findElement(By.tagName("strong")).getText().equals("Presencial"));
	}
	
	 @Test(groups = {"Sales", "AltaDeLinea","Ola1"}, priority=2, dataProvider="SalesCuentaInactiva")
	  public void TS94714_Ventas_BuscarCliente_Verificar_Solo_Clientes_No_Activos(String sCuenta, String sDni, String sLinea) throws IOException {
		 String tel = sLinea;
				 //buscarCampoExcel(1, "Cuenta Inactiva", 3);
		  driver.findElement(By.id("PhoneNumber")).sendKeys(tel);
		  driver.findElement(By.id("SearchClientsDummy")).click();
		  sleep(5000);
		  List <WebElement> cai = driver.findElement(By.className("slds-tabs--scoped__nav")).findElements(By.tagName("li"));
		  if (cai.get(0).isDisplayed() || !cai.get(1).isDisplayed()) {
			  Assert.assertTrue(false);
		  }
		  Assert.assertTrue(cai.get(1).findElement(By.tagName("a")).getText().contains("Inactivos"));
	 }
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=4, dataProvider="SalesCuentaActiva")
	public void TS95111_Ventas_General_Verificar_Que_No_Se_Puede_Seleccionar_Una_Linea_Decisora_ProcesoVenta(String sCuenta, String sDni, String sLinea) throws IOException {
		boolean esta = false;
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");
		sleep(15000);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();
		sleep(15000);
		List<WebElement> LD = driver.findElements(By.tagName("a"));
		for (WebElement UnLD : LD) {
			System.out.println(UnLD.getText());
			if (UnLD.getText().equalsIgnoreCase("elegir como l\u00ednea decisora"))
				esta = true;
		}
		Assert.assertFalse(esta);
	}
	
	@Test(groups={"Sales", "AltaDeContacto", "Ola1"}, priority=2)
	public void TS94554_Alta_De_Contacto_Persona_Fisica_Verificar_Campo_Tipo_De_Documento_Por_Default() {
		Random aleatorio = new Random(System.currentTimeMillis());
		aleatorio.setSeed(System.currentTimeMillis());
		int intAleatorio = aleatorio.nextInt(89999999)+10000000;
		driver.findElement(By.id("SearchClientDocumentNumber")).sendKeys(Integer.toString(intAleatorio));
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(5000);
		List <WebElement> cc = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding"));
		for (WebElement x : cc) {
			if (x.getText().toLowerCase().contains("+ crear nuevo cliente")) {
				x.click();
				break;
			}
		}
		sleep(5000);
		System.out.println(new Select(driver.findElement(By.id("DocumentType"))).getFirstSelectedOption().getText());
		Assert.assertTrue(new Select(driver.findElement(By.id("DocumentType"))).getFirstSelectedOption().getText().equalsIgnoreCase("DNI"));
		
	}
	
	@Test(groups={"Sales", "AltaDeContacto","Ola1"}, priority=2)  
	public void TS94566_Alta_De_Contacto_Persona_Fisica_Verificar_Mascara_Del_Campo_CUIT(){
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact("CUIT", "22458954", "");
		WebElement num = driver.findElement(By.id("SearchClientDocumentNumber"));
		Assert.assertTrue(num.getText().matches("\\d{2}-\\d{8}-\\d{1}"));
		List <WebElement> cc = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding"));
		for (WebElement x : cc) {
			if (x.getText().toLowerCase().contains("+ crear nuevo cliente")) {
				x.click();
				break;
			}
		}
		sleep(5000);
		Assert.assertTrue(driver.findElement(By.id("DocumentNumber")).getAttribute("value").matches("\\d{2}-\\d{8}-\\d{1}"));
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=5, dataProvider="SalesCuentaActiva")
	public void TS94763_Ventas_Entregas_General_Modificar_el_lugar_de_entrega(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		sleep(10000);
		sb.acciondecontacto("catalogo");
		sleep(25000);
		sb.elegirplan("Plan con tarjeta");
		String a = driver.findElement(By.cssSelector(".slds-col.taChangeDeliveryMethod.slds-text-body--small.slds-m-left--large")).getText();
		driver.findElement(By.cssSelector(".slds-m-left--x-small.slds-button.slds-button--brand")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SalesChannelConfiguration_nextBtn")));
		Select env = new Select (driver.findElement(By.id("DeliveryMethod")));
		env.selectByVisibleText("Delivery");
		driver.findElement(By.id("SalesChannelConfiguration_nextBtn")).click();
		sleep(7000);
		String b = driver.findElement(By.cssSelector(".slds-col.taChangeDeliveryMethod.slds-text-body--small.slds-m-left--large")).getText();
		Assert.assertTrue(!a.equals(b));
	}
	
	@Test(groups={"Sales","AltaDeLinea","Ola1"}, priority=4, dataProvider="SalesCuentaActiva")
	public void TS94498_Alta_Linea_Configurar_Nueva_Linea_Modificar_linea_pre_asignada_ultimos_cuatro_digitos_XX(String sCuenta, String sDni, String sLinea) throws IOException{
		CustomerCare CC = new CustomerCare(driver);
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(10000);
		sb.Crear_DomicilioLegal( provincia, localidad,"falsa", "", "5846", "", "", "5248");
		sleep(25000);
		List<WebElement> modi = driver.findElements(By.cssSelector(".slds-form-element__label--toggleText.ng-binding"));
			for(WebElement m : modi){
				m.getText().equals("Modificar b\u00fasqueda");
					m.click();}
		WebElement digit = driver.findElement(By.id("Sufijo"));
		digit.sendKeys("7354");
		sleep(1500);
		WebElement cont = driver.findElement(By.id("ChangeNumber"));
		CC.obligarclick(cont);
		sleep(8000);
		WebElement ultnum = driver.findElement(By.cssSelector(".slds-checkbox__label.ta-cyan-color-dark")).findElements(By.tagName("span")).get(1);
		System.out.println(ultnum.getText());
		Assert.assertTrue(ultnum.getText().contains("7354"));
 	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1","filtrado"}, priority=7, dataProvider="SalesCuentaActiva")  
	public void TS94807_Configuracion_Verificar_Asignacion_De_Seriales_AgentePresencial(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(45000);
		sb.Crear_DomicilioLegal( provincia, localidad,"falsa", "", "5846", "", "", "5248");
		sleep(15000);
		//CustomerCare page = new CustomerCare(driver);
		WebElement sig = driver.findElement(By.id("LineAssignment_nextBtn"));
		//page.obligarclick(sig);
		sig.click();
		sleep(10000);
		WebElement serial = driver.findElement(By.id("ICCIDConfiguration")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(1);
		Assert.assertTrue(!serial.findElement(By.tagName("input")).getAttribute("value").isEmpty());
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=0, dataProvider="SalesCuentaActiva")
	public void TS94777_Ventas_Entregas_General_Store_Pickup_Consulta_stock_por_PDV_Visualizar_campos_filtro_de_la_consulta(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(25000);
		driver.findElement(By.cssSelector(".slds-m-left--x-small.slds-button.slds-button--brand")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DeliveryMethod")));
		Select env = new Select (driver.findElement(By.id("DeliveryMethod")));
		env.selectByVisibleText("Store Pick Up");
		sleep(2000);
		Select prov = new Select (driver.findElement(By.id("State")));
		prov.selectByVisibleText("Ciudad Aut\u00f3noma de Buenos Aires");
		sleep(2000);
		Select loc = new Select(driver.findElement(By.id("City")));
		loc.selectByVisibleText("CIUD AUTON D BUENOS AIRES");
		sleep(3000);
		Assert.assertTrue(driver.findElement(By.id("State")).isEnabled());
		Assert.assertTrue(driver.findElement(By.id("City")).isEnabled());
		Assert.assertTrue(driver.findElement(By.id("Store")).isEnabled());
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=6, dataProvider="SalesCuentaActiva")  //falta validar los campos porque los campos no son opcionales
	public void TS94935_Ventas_Modo_De_Pago_Tarjeta_Verificar_Campos_Opcionales_Medio_De_Pago_TC(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(10000);
		List<WebElement> cont = driver.findElements(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand"));
			for(WebElement c : cont){
				c.getText().equals("Continuar");
					c.click();
			}
		sleep(5000);
		CustomerCare page = new CustomerCare(driver);
		WebElement sig = driver.findElement(By.id("LineAssignment_nextBtn"));
		page.obligarclick(sig);
		sleep(10000);
		page.obligarclick(driver.findElement(By.id("ICCDAssignment_nextBtn")));
		sleep(10000);
		page.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		sleep(10000);
		driver.findElement(By.id("PaymentMethodRadio")).click();
		sleep(4000);
		List<WebElement> mediosP = driver.findElements(By.cssSelector(".slds-list__item.ng-binding.ng-scope"));
		for (WebElement UnMP : mediosP) {
			if (UnMP.getText().toLowerCase().contains("tarjeta de credito"))
				UnMP.click();
		}
		sleep(4000);
		System.out.println(driver.findElement(By.id("CardBankingEntity")).getAttribute("required"));
		Assert.assertTrue(false);
		//Assert.assertTrue(driver.findElement(By.id("CardBankingEntity")).getAttribute("required"));
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1","filtrado"}, priority=6, dataProvider="SalesCuentaActiva")  //falta validar los campos porque el campo requerido no existe
	public void TS94936_Ventas_Modo_De_Pago_Tarjeta_Verificar_Campos_requeridos_Medio_De_Pago_TC(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(20000);
		sb.Crear_DomicilioLegal( provincia, localidad,"falsa", "", "5846", "", "", "5248");
		CustomerCare page = new CustomerCare(driver);
		WebElement sig = driver.findElement(By.id("LineAssignment_nextBtn"));
		page.obligarclick(sig);
		sleep(10000);
		page.obligarclick(driver.findElement(By.id("ICCDAssignment_nextBtn")));
		sleep(10000);
		page.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		sleep(10000);
		driver.findElement(By.id("PaymentMethodRadio")).click();
		sleep(4000);
		List<WebElement> mediosP = driver.findElements(By.cssSelector(".slds-list__item.ng-binding.ng-scope"));
		for (WebElement UnMP : mediosP) {
			if (UnMP.getText().toLowerCase().contains("tarjeta de credito"))
				UnMP.click();
		}
		sleep(5000);
		driver.findElement(By.id("CreditCardData")).click();
		sleep(1000);
		Assert.assertTrue(false);
		Assert.assertTrue(driver.findElement(By.id("CardBankingEntity")).getAttribute("required").equals("true"));
		//Assert.assertTrue(driver.findElement(By.id("CardBankingEntity")).getAttribute("required"));
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=6, dataProvider="SalesCuentaActiva")  
	public void TS94937_Ventas_Modo_De_Pago_Tarjeta_Verificar_Datos_Del_Campo_Entidad_De_Tarjeta(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(25000);
		CustomerCare page = new CustomerCare(driver);
		try {
			driver.findElement(By.id("Step_Error_Huawei_S013_nextBtn")).click();
		}catch(NoSuchElementException ex1) {
			sleep(10000);
			WebElement sig = driver.findElement(By.id("LineAssignment_nextBtn"));
			page.obligarclick(sig);
			sleep(10000);
			page.obligarclick(driver.findElement(By.id("ICCDAssignment_nextBtn")));
			sleep(10000);
			page.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		}

		sleep(10000);
		List<WebElement> mediosP = driver.findElements(By.cssSelector(".slds-radio.ng-scope"));
		for (WebElement UnMP : mediosP) {
			if (UnMP.findElement(By.cssSelector(".slds-form-element__label.ng-binding")).getText().toLowerCase().contains("tarjeta de credito")) {
				UnMP.click();
				break;
			}
		}
		sleep(5000);
		driver.findElement(By.id("BankingEntity-0")).click();
		sleep(1000);
		driver.findElement(By.id("BankingEntity-0")).findElements(By.tagName("option")).get(1).click();
		sleep(1000);
		driver.findElement(By.id("CardBankingEntity-0")).click();
		sleep(1000);
		Assert.assertTrue(driver.findElement(By.id("CardBankingEntity-0")).findElements(By.tagName("option")).size()>1);
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=6, dataProvider="SalesCuentaActiva")  
	public void TS94938_Ventas_Modo_De_Pago_Tarjeta_Verificar_Datos_Del_Campo_Entidad_Banco_Emisor_Tarjeta(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(20000);
		CustomerCare page = new CustomerCare(driver);
		try {
			driver.findElement(By.id("Step_Error_Huawei_S013_nextBtn")).click();
		}catch(NoSuchElementException ex1) {
			sleep(10000);
			WebElement sig = driver.findElement(By.id("LineAssignment_nextBtn"));
			page.obligarclick(sig);
			sleep(10000);
			page.obligarclick(driver.findElement(By.id("ICCDAssignment_nextBtn")));
			sleep(10000);
			page.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		}

		sleep(10000);
		List<WebElement> mediosP = driver.findElements(By.cssSelector(".slds-radio.ng-scope"));
		for (WebElement UnMP : mediosP) {
			if (UnMP.findElement(By.cssSelector(".slds-form-element__label.ng-binding")).getText().toLowerCase().contains("tarjeta de credito")) {
				UnMP.click();
				break;
			}
		}
		sleep(5000);
		driver.findElement(By.id("BankingEntity-0")).click();
		sleep(1000);
		Assert.assertTrue(driver.findElement(By.id("BankingEntity-0")).findElements(By.tagName("option")).size()>1);
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=6, dataProvider="SalesCuentaActiva")  
	public void TS94939_Ventas_Modo_De_Pago_Tarjeta_Verificar_Datos_Del_Campo_Codigo_Promo_Bancaria(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(20000);
		
		CustomerCare page = new CustomerCare(driver);
		try {
			driver.findElement(By.id("Step_Error_Huawei_S013_nextBtn")).click();
		}catch(NoSuchElementException ex1) {
			sleep(10000);
			WebElement sig = driver.findElement(By.id("LineAssignment_nextBtn"));
			page.obligarclick(sig);
			sleep(10000);
			page.obligarclick(driver.findElement(By.id("ICCDAssignment_nextBtn")));
			sleep(10000);
			page.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		}

		sleep(10000);
		List<WebElement> mediosP = driver.findElements(By.cssSelector(".slds-radio.ng-scope"));
		for (WebElement UnMP : mediosP) {
			if (UnMP.findElement(By.cssSelector(".slds-form-element__label.ng-binding")).getText().toLowerCase().contains("tarjeta de credito")) {
				UnMP.click();
				break;
			}
		}
		sleep(5000);
		driver.findElement(By.id("BankingEntity-0")).click();
		sleep(1000);
		driver.findElement(By.id("BankingEntity-0")).findElements(By.tagName("option")).get(1).click();
		sleep(1000);
		driver.findElement(By.id("CardBankingEntity-0")).click();
		sleep(1000);
		driver.findElement(By.id("CardBankingEntity-0")).findElements(By.tagName("option")).get(1).click();
		sleep(1500);
		driver.findElement(By.id("promotionsByCardsBank-0")).click();
		sleep(1500);
		Assert.assertTrue(driver.findElement(By.id("promotionsByCardsBank-0")).findElements(By.tagName("option")).size()>1);
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1","filtrado"}, priority=6, dataProvider="SalesBuscarCuenta")  
	public void TS94941_Ventas_Modo_De_Pago_Tarjeta_Verificar_Datos_Del_Campo_Cuotas_TC(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(20000);
		CustomerCare page = new CustomerCare(driver);
		try {
			driver.findElement(By.id("Step_Error_Huawei_S013_nextBtn")).click();
		}catch(NoSuchElementException ex1) {
			sleep(10000);
			WebElement sig = driver.findElement(By.id("LineAssignment_nextBtn"));
			page.obligarclick(sig);
			sleep(10000);
			page.obligarclick(driver.findElement(By.id("ICCDAssignment_nextBtn")));
			sleep(10000);
			page.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		}

		sleep(10000);
		List<WebElement> mediosP = driver.findElements(By.cssSelector(".slds-radio.ng-scope"));
		for (WebElement UnMP : mediosP) {
			if (UnMP.findElement(By.cssSelector(".slds-form-element__label.ng-binding")).getText().toLowerCase().contains("tarjeta de credito")) {
				UnMP.click();
				break;
			}
		}
		sleep(5000);
		driver.findElement(By.id("BankingEntity-0")).click();
		sleep(1000);
		driver.findElement(By.id("BankingEntity-0")).findElements(By.tagName("option")).get(1).click();
		sleep(1000);
		driver.findElement(By.id("CardBankingEntity-0")).click();
		sleep(1000);
		driver.findElement(By.id("CardBankingEntity-0")).findElements(By.tagName("option")).get(1).click();
		sleep(1500);
		driver.findElement(By.id("promotionsByCardsBank-0")).click();
		sleep(1000);
		driver.findElement(By.id("promotionsByCardsBank-0")).findElements(By.tagName("option")).get(1).click();
		sleep(1500);
		driver.findElement(By.id("Installment-0")).click();
		sleep(1500);
		Assert.assertTrue(driver.findElement(By.id("Installment-0")).findElements(By.tagName("option")).size()>1);
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=6, dataProvider="SalesCuentaActiva")  
	public void TS94942_Ventas_Modo_De_Pago_Tarjeta_Verificar_Datos_Del_Campo_Cuotas_CFT(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(20000);
		CustomerCare page = new CustomerCare(driver);
		try {
			driver.findElement(By.id("Step_Error_Huawei_S013_nextBtn")).click();
		}catch(NoSuchElementException ex1) {
			sleep(10000);
			WebElement sig = driver.findElement(By.id("LineAssignment_nextBtn"));
			page.obligarclick(sig);
			sleep(10000);
			page.obligarclick(driver.findElement(By.id("ICCDAssignment_nextBtn")));
			sleep(10000);
			page.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		}

		sleep(10000);
		List<WebElement> mediosP = driver.findElements(By.cssSelector(".slds-radio.ng-scope"));
		for (WebElement UnMP : mediosP) {
			if (UnMP.findElement(By.cssSelector(".slds-form-element__label.ng-binding")).getText().toLowerCase().contains("tarjeta de credito")) {
				UnMP.click();
				break;
			}
		}
		sleep(5000);
		driver.findElement(By.id("BankingEntity-0")).click();
		sleep(1000);
		driver.findElement(By.id("BankingEntity-0")).findElements(By.tagName("option")).get(1).click();
		sleep(1000);
		driver.findElement(By.id("CardBankingEntity-0")).click();
		sleep(1000);
		driver.findElement(By.id("CardBankingEntity-0")).findElements(By.tagName("option")).get(1).click();
		sleep(1500);
		driver.findElement(By.id("promotionsByCardsBank-0")).click();
		sleep(1000);
		driver.findElement(By.id("promotionsByCardsBank-0")).findElements(By.tagName("option")).get(1).click();
		sleep(1500);
		driver.findElement(By.id("Installment-0")).click();
		sleep(1500);
		driver.findElement(By.id("Installment-0")).findElements(By.tagName("option")).get(2).click();
		sleep(2000);
		driver.findElement(By.id("Installment-0")).click();
		sleep(1500);
		Assert.assertTrue(driver.findElement(By.cssSelector(".padding-custom.ng-binding")).getText().toLowerCase().contains("costo financiero"));
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=0, dataProvider="SalesCuentaActiva")
	public void TS94779_Ventas_Entregas_General_Store_Pickup_Consulta_stock_por_PDV_Visualizar_el_campo_LOCALIDAD_con_un_desplegable_que_permita_seleccionar_una(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(25000);
		driver.findElement(By.cssSelector(".slds-m-left--x-small.slds-button.slds-button--brand")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DeliveryMethod")));
		Select env = new Select (driver.findElement(By.id("DeliveryMethod")));
		env.selectByVisibleText("Store Pick Up");
		sleep(2000);
		Select prov = new Select (driver.findElement(By.id("State")));
		prov.selectByVisibleText("Ciudad Aut\u00f3noma de Buenos Aires");
		sleep(2000);
		driver.findElement(By.id("City")).click();
		boolean a = false;
		List <WebElement> list = driver.findElement(By.id("City")).findElements(By.tagName("option"));
		if (list.size() >= 2) {
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=0, dataProvider="SalesCuentaActiva")
	public void TS94778_Ventas_Entregas_General_Store_Pickup_Consulta_stock_por_PDV_Visualizar_el_campo_PROVINCIA_con_un_desplegable_que_permita_seleccionar_una(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(25000);
		driver.findElement(By.cssSelector(".slds-m-left--x-small.slds-button.slds-button--brand")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DeliveryMethod")));
		Select env = new Select (driver.findElement(By.id("DeliveryMethod")));
		env.selectByVisibleText("Store Pick Up");
		sleep(2000);
		driver.findElement(By.id("State")).click();
		boolean a = false;
		List <WebElement> list = driver.findElement(By.id("State")).findElements(By.tagName("option"));
		if (list.size() >= 2) {
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=4, dataProvider="SalesCuentaActiva")
	public void TS94496_Alta_Linea_Configurar_Nueva_Linea_Desplegar_desde_la_descripcion_del_plan_todas_las_lineas_XX(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activas", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");	
		//driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();
		sb.continuar();
		sleep(25000);
		sb.Crear_DomicilioLegal( provincia, localidad,"falsa", "", "5846", "", "", "5248");
		sleep(45000);
		WebElement line = driver.findElement(By.id("tree0-node1__label"));
		WebElement line2 = driver.findElement(By.id("tree0-node1-0"));
		System.out.println(line.getAttribute("value"));
		System.out.println(line2.getAttribute("value"));
		boolean a = false;
		boolean b = false;
		if (line.getText().toLowerCase().contains("lineas disponibles")) {
			a = true;
		}
		if (line2.getText().toLowerCase().contains("034")) {
			b = true;
		}
		Assert.assertTrue(a && b);
		}
			
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=4, dataProvider="SalesCuentaActiva")
	public void TS94495_Alta_Linea_Configurar_Nueva_Linea_Cancelar_la_venta_al_no_tener_lineas_disponibles_XX(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(20000);
		sb.Crear_DomicilioLegal(provincia, "ABEL", "falsa", "", "1000", "", "", "1549");
		//sleep(15000);
		//driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();
		sleep(25000);
		driver.findElement(By.cssSelector(".slds-form-element__label--toggleText.ng-binding")).click();
		Select pro = new Select(driver.findElement(By.id("SelectProvincia")));
		pro.selectByVisibleText("Buenos Aires");
		Select loc = new Select(driver.findElement(By.id("SelectLocalidad")));
		loc.selectByVisibleText("JUAN BLAQUIER");
		driver.findElement(By.id("ChangeNumber")).click();
		sleep(5000);
		WebElement line = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.ng-scope"));
		boolean a = false;
		if (line.getText().toLowerCase().contains("falta asignar n\u00fameros de l\u00edneas")) {
			a = true;
		}
		WebElement canc = driver.findElements(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope")).get(0);
		Assert.assertTrue(a);
		Assert.assertTrue(canc.isDisplayed());
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=3, dataProvider="SalesCuentaActiva")
	public void TS94508_Alta_Linea_Modo_de_Entrega_Seleccionar_modo_de_entrega_presencial_Producto_Tangible_52(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(25000);
		driver.findElement(By.cssSelector(".slds-m-left--x-small.slds-button.slds-button--brand")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DeliveryMethod")));
		Select env = new Select (driver.findElement(By.id("DeliveryMethod")));
		env.selectByVisibleText("Presencial");
		driver.findElement(By.id("SalesChannelConfiguration_nextBtn")).click();
		sleep(7000);
		WebElement mde = driver.findElement(By.cssSelector(".slds-col.taChangeDeliveryMethod.slds-text-body--small.slds-m-left--large"));
		Assert.assertTrue(mde.getText().contains("Presencial"));
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=3, dataProvider="SalesCuentaActiva")
	public void TS94625_Alta_Linea_Asignar_SIMCARD_Verificar_que_se_agrege_la_seleccion_al_carrito(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");
		sleep(15000);
		WebElement plan = driver.findElements(By.cssSelector(".slds-button.cpq-item-has-children")).get(0);
		plan.click();
		sleep(5000);
		List <WebElement> sim = driver.findElements(By.className("cpq-item-no-children"));
		boolean a = false;
		for (WebElement x : sim) {
			if (x.getText().toLowerCase().contains("simcard")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=3, dataProvider="SalesCuentaActiva")
	public void TS94642_Ventas_NumeroOrden_Visualizar_Orden_de_Venta_Abierta_Seleccionar_un_producto(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");
		sleep(15000);
		WebElement num = driver.findElement(By.cssSelector(".slds-m-bottom--x-small"));
		System.out.println(num.getText());
		Assert.assertTrue(num.getText().contains("Nro. Orden:"));
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=3, dataProvider="SalesCuentaActiva")
	public void TS94644_Ventas_NumeroOrden_Visualizar_Orden_de_Venta_Abierta_Modo_de_Entrega(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");
		sleep(15000);
		sb.continuar();
		sleep(20000);
		sb.Crear_DomicilioLegal(provincia, localidad, "falsa", "", "1000", "", "", "1549");
		sleep(25000);
		CustomerCare CC = new CustomerCare(driver);
		WebElement sig = driver.findElement(By.id("LineAssignment_nextBtn"));
 		sleep(15000);
		driver.findElement(By.id("ICCDAssignment_nextBtn")).click();
		sleep(10000);
		List <WebElement> num = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		boolean a = false;
		for (WebElement x : num) {
			if (x.getText().contains("Nro. orden:")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=3, dataProvider="SalesCuentaActiva")
	public void TS94647_Ventas_NumeroOrden_Visualizar_Orden_de_Venta_Abierta_ICCID(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(20000);
		sb.Crear_DomicilioLegal(provincia, localidad, "falsa", "", "1000", "", "", "1549");
		sleep(25000);
		sleep(12000);
		driver.findElement(By.id("LineAssignment_nextBtn")).click();
		sleep(15000);
		List <WebElement> num = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		boolean a = false;
		for (WebElement x : num) {
			if (x.getText().contains("Nro. orden:")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test(groups={"Sales", "AltaDeCuenta", "Ola1"}, priority=6, dataProvider="SalesCuentaActiva")
	public void TS95207_Alta_de_Cuenta_Usuario_Verificar_usuario_del_asset_por_default(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		String a = driver.findElement(By.xpath("//*[@id=\"tab-scoped-1\"]/section/div/table/tbody/tr[1]/th/div")).getText();
		sb.acciondecontacto("catalogo");
		sleep(10000);
		WebElement name = driver.findElement(By.cssSelector(".slds-page-header__title.slds-m-right--small.slds-truncate.slds-align-middle"));
		Assert.assertTrue(name.getText().equals(a));	
	}
	
	@Test(groups={"Sales", "AltaDeCuenta", "Ola1"}, priority=6)
	public void TS95208_Alta_de_Cuenta_Usuario_Verificar_datos_para_la_creacion_de_usuarios() {
		sb.BtnCrearNuevoCliente();
		boolean masc = false;
		boolean fem = false;
		List <WebElement> genero = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope"));
		for (WebElement x : genero) {
			if (x.getText().toLowerCase().contains("femenino")) {
				fem = true;
			}
			if (x.getText().toLowerCase().contains("masculino")) {
				masc = true;
			}
		}
		Assert.assertTrue(fem && masc);
		Assert.assertTrue(driver.findElement(By.id("DocumentType")).getAttribute("disabled").equals("true"));
		Assert.assertTrue(driver.findElement(By.id("DNI")).isEnabled());
		Assert.assertTrue(driver.findElement(By.id("FirstName")).isEnabled());
		Assert.assertTrue(driver.findElement(By.id("LastName")).isEnabled());
		Assert.assertTrue(driver.findElement(By.id("Birthdate")).isEnabled());
	}
	
	
	@Test(groups={"Sales","AltaDeLinea","Ola1","filtrado"}, priority=4, dataProvider="SalesCuentaActiva")  //Continua aunque no se asigne las lianeas
	public void TS94497_Alta_Linea_Configurar_Nueva_Linea_Intentar_pasar_al_siguiente_paso_lineas_incompletas_XX(String sCuenta, String sDni, String sLinea) throws IOException{
		CustomerCare CC = new CustomerCare(driver);
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(25000);
		sb.Crear_DomicilioLegal(provincia, localidad, "falsa", "", "1000", "", "", "1549");
		sleep(35000);
		WebElement bx = driver.findElement(By.id("tree0-node1__label"));
		bx.click();
		sleep(5000);
		WebElement sig = driver.findElement(By.id("LineAssignment_nextBtn"));
		CC.obligarclick(sig);
		sleep(15000);
		boolean x = false;
		List<WebElement> serial = driver.findElements(By.cssSelector(".slds-page-header__title.vlc-slds-page-header__title.slds-truncate.ng-binding"));
			for(WebElement s : serial){
				s.getText().equals("Ingreso de serial");
				System.out.println(s.getText());
				s.isDisplayed();
				x=true;
			}
		Assert.assertTrue(x);
	}
	
	@Test(groups={"Sales","AltaDeLinea","Ola1","filtrado"}, priority=4, dataProvider="SalesCuentaActiva") // No figura el lote de lineas
	public void TS94494_Alta_Linea_Configurar_Nueva_Linea_Buscar_nuevo_lote_de_lineas_pre_asignadas_XX(String sCuenta, String sDni, String sLinea) throws IOException{
		CustomerCare CC = new CustomerCare(driver);
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(20000);
		sb.Crear_DomicilioLegal(provincia, localidad, "falsa", "", "1000", "", "", "1549");
		sleep(30000);
		String lineaVieja = driver.findElement(By.id("tree0-node1")).findElement(By.cssSelector(".slds-checkbox__label.ta-cyan-color-dark")).findElement(By.className("ng-binding")).getText();
		System.out.println("vieja ="+lineaVieja);
		WebElement bx = driver.findElement(By.id("SearchBlock"));
		CC.obligarclick(bx);
		sleep(10000);
		CC.obligarclick(driver.findElement(By.id("ChangeNumber")));
		sleep(15000);
		String lineaNueva = driver.findElement(By.id("tree0-node1")).findElement(By.cssSelector(".slds-checkbox__label.ta-cyan-color-dark")).findElement(By.className("ng-binding")).getText();
		System.out.println("nueva ="+lineaNueva);
		Assert.assertTrue(!lineaVieja.equals(lineaNueva));
	}
	
	@Test(groups={"Sales","AltaDeLinea","Ola1"}, priority=4, dataProvider="SalesCuentaActiva") 	
	public void TS94503_Alta_Linea_Configurar_Nueva_Linea_Visualizar_lineas_pre_asignadas_automaticamente_XX(String sCuenta, String sDni, String sLinea) throws IOException{
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(15000);
		sb.Crear_DomicilioLegal(provincia, localidad, "falsa", "", "1000", "", "", "1549");
		sleep(20000);
		WebElement bx = driver.findElement(By.id("OrderStatus"));
		System.out.println(bx.getText());
		Assert.assertTrue(bx.isDisplayed());
	}
	
	@Test(groups={"Sales","AltaDeLinea","Ola1"}, priority=4, dataProvider="SalesCuentaActiva") 	
	public void TS94499_Alta_Linea_Configurar_Nueva_Linea_Presionar_el_boton_Buscar_XX(String sCuenta, String sDni, String sLinea) throws IOException{
		CustomerCare CC = new CustomerCare(driver);
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(20000);
		sb.Crear_DomicilioLegal(provincia, localidad, "falsa", "", "1000", "", "", "1549");
		sleep(25000);
		List<WebElement> modi = driver.findElements(By.cssSelector(".slds-form-element__label--toggleText.ng-binding"));
			for(WebElement m : modi){
				m.getText().equals("Modificar b\u00fasqueda");
					m.click();}
		Select prov = new Select (driver.findElement(By.id("SelectProvincia")));
		prov.selectByVisibleText("Catamarca");
		sleep(3000);
		Select loc = new Select (driver.findElement(By.id("SelectLocalidad")));
		loc.selectByVisibleText("ACHALCO");
		WebElement digit = driver.findElement(By.id("Sufijo"));
		digit.sendKeys("7354");
		sleep(1500);
		WebElement cont = driver.findElement(By.id("ChangeNumber"));
		CC.obligarclick(cont);
		sleep(8000);
		WebElement txt = driver.findElement(By.id("LineAssingmentMessage")).findElement(By.tagName("div")).findElements(By.tagName("strong")).get(0);
		Assert.assertTrue((txt.getText().equals("Catamarca")));
		WebElement txt2 = driver.findElement(By.id("LineAssingmentMessage")).findElement(By.tagName("div")).findElements(By.tagName("strong")).get(1);
		Assert.assertTrue(txt2.getText().equals("ACHALCO"));
	}
	
	@Test(groups={"Sales","AltaDeLinea","Ola1"}, priority=4, dataProvider="SalesCuentaActiva") 	
	public void TS94501_Alta_Linea_Configurar_Nueva_Linea_Visualizar_filtros_de_localidad_y_provincia_al_modificar_linea_XX(String sCuenta, String sDni, String sLinea) throws IOException{
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(25000);
		/*driver.findElement(By.cssSelector(".slds-m-left--x-small.slds-button.slds-button--brand")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DeliveryMethodSelection")));
		Select env = new Select (driver.findElement(By.id("DeliveryMethodSelection")));
		env.selectByVisibleText("Delivery");
		driver.findElement(By.id("SalesChannelConfiguration_nextBtn")).click();
		sleep(7000);
		driver.switchTo().defaultContent();*/
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(20000);
		sb.Crear_DomicilioLegal(provincia, localidad, "falsa", "", "1000", "", "", "1549");
		sleep(25000);
		List<WebElement> modi = driver.findElements(By.cssSelector(".slds-form-element__label--toggleText.ng-binding"));
		for(WebElement m : modi){
			m.getText().equals("Modificar b\u00fasqueda");
				m.click();}
		sleep(3000);
		try{ driver.findElement(By.id("SelectProvincia")).click();
		driver.findElement(By.id("SelectLocalidad")).click();
		Assert.assertTrue(true);}
		catch(org.openqa.selenium.ElementNotVisibleException ex1){
		Assert.assertTrue(false);	
		}
	}	
	
	@Test(groups={"Sales","AltaDeLinea","Ola1"}, priority=4) 	
	public void TS94502_Alta_Linea_Configurar_Nueva_Linea_Visualizar_home_de_la_linea_pre_asignada_correspondiente_a_direccion_de_facturacion_XX(){
		sb.BtnCrearNuevoCliente();
		ContactSearch contact = new ContactSearch(driver);
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("FirstName")).sendKeys("yy");
		driver.findElement(By.id("LastName")).sendKeys("z");
		driver.findElement(By.id("Birthdate")).sendKeys("28/12/1999");
		contact.sex("masculino");
		driver.findElement(By.id("Contact_nextBtn")).click();
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(20000);
		sb.Crear_DomicilioLegal(provincia, localidad, "falsa", "", "1000", "", "", "1549");
		sleep(25000);
		WebElement text = driver.findElement(By.id("LineAssingmentMessage")).findElement(By.tagName("div")).findElement(By.tagName("p")).findElement(By.tagName("p")).findElements(By.tagName("strong")).get(0);
		WebElement text2 = driver.findElement(By.id("LineAssingmentMessage")).findElement(By.tagName("div")).findElement(By.tagName("p")).findElement(By.tagName("p")).findElements(By.tagName("strong")).get(1);
		//System.out.println(text.getText());
		//System.out.println(text2.getText());
		Assert.assertTrue(text.getText().equals(provincia));
		Assert.assertTrue(text2.getText().equals(localidad));
	
	}
	
	@Test(groups={"Sales","AltaDeLinea","Ola1"}, priority=4)
	public void TS94504_Alta_Linea_Configurar_Nueva_Linea_Visualizar_mensaje_y_opciones_de_lineas_no_disponibles_XX(){
		sb.BtnCrearNuevoCliente();
		ContactSearch contact = new ContactSearch(driver);
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("FirstName")).sendKeys("yy");
		driver.findElement(By.id("LastName")).sendKeys("z");
		driver.findElement(By.id("Birthdate")).sendKeys("28/12/1999");
		contact.sex("masculino");
		driver.findElement(By.id("Contact_nextBtn")).click();
		sleep(10000);
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(10000);
		sb.Crear_DomicilioLegal(provincia, localidad, "falsa", "", "1000", "", "", "1549");
		sleep(20000);
		WebElement lindec = driver.findElement(By.id("tree0-node1")).findElement(By.tagName("ul")).findElements(By.tagName("div")).get(0);
		System.out.println(lindec.getText());
		System.out.println(lindec.getAttribute("value"));
		//Assert.assertTrue(lindec.isDisplayed());
		List<WebElement> modi = driver.findElements(By.cssSelector(".slds-form-element__label--toggleText.ng-binding"));
			for(WebElement m : modi){
			m.getText().equals("Modificar b\u00fasqueda");
			System.out.println("encontrada");
				m.click();}
		sleep(3000);
		try{ driver.findElement(By.id("SelectProvincia")).click();
		driver.findElement(By.id("SelectLocalidad")).click();
		Assert.assertTrue(true);}
		catch(org.openqa.selenium.ElementNotVisibleException ex1){
		Assert.assertTrue(false);}
	}

		
	@Test(groups={"Sales","AltaDeLinea","Ola1"}, priority=4, dataProvider="SalesCuentaActiva")    //no se ve la asignacion de lineas
	public void TS94505_Alta_Linea_Configurar_Nueva_Linea_Visualizar_misma_cantidad_de_lineas_que_planes_XX(String sCuenta, String sDni, String sLinea) throws IOException{
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");
		sleep(3000);
		List<WebElement> agregar = driver.findElements(By.cssSelector(".slds-button.slds-button_neutral.cpq-add-button")); 
		agregar.get(0).click();
		sb.continuar();
		sleep(7000);
		List <WebElement> faf = driver.findElements(By.id("tree0-node1"));
		for(WebElement f : faf){
			System.out.println(f.getSize());
		}
	}
	

	
	@Test(groups={"Sales", "AltaDeContacto","Ola1"}, priority=9)
	public void TS94590_Alta_de_Contacto_Persona_Fisica_Verificar_campos_inhabilitados_hasta_la_validacion_existosa_del_contacto_XX(){
		BasePage Bp= new BasePage();
		Random aleatorio = new Random(System.currentTimeMillis());
		aleatorio.setSeed(System.currentTimeMillis());
		int intAletorio = aleatorio.nextInt(8999999)+1000000;
		Bp.setSimpleDropdown(driver.findElement(By.id("SearchClientDocumentType")),"DNI");
		driver.findElement(By.id("SearchClientDocumentNumber")).sendKeys(Integer.toString(intAletorio));
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(5000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".OSradioButton.ng-scope.only-buttom")).isEnabled());
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1","filtrado"}, priority=0, dataProvider="SalesCuentaActiva")
	public void TS94782_Ventas_Entregas_General_Store_Pickup_Consulta_stock_por_PDV_Verificar_que_se_hablilite_solo_las_localidades_con_punto_de_venta_para_Store_Pickup(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(25000);
		driver.findElement(By.cssSelector(".slds-m-left--x-small.slds-button.slds-button--brand")).click();
		sleep(15000);
		List<WebElement> frame2 = driver.findElements(By.tagName("iframe"));
		driver.switchTo().frame(frame2.get(0));
		Select env = new Select (driver.findElement(By.id("DeliveryMethod")));
		env.selectByVisibleText("Store Pick Up");
		Select prov = new Select (driver.findElement(By.id("State")));
		prov.selectByVisibleText("Ciudad Aut\u00f3noma de Buenos Aires");
		Select loc = new Select (driver.findElement(By.id("City")));
		loc.selectByVisibleText("CIUD AUTON D BUENOS AIRES");
		driver.findElement(By.id("Store")).click();
		List <WebElement> pdv = driver.findElements(By.cssSelector(".slds-list__item.ng-binding.ng-scope"));
		boolean a = false;
		boolean b = false;
		for (WebElement x : pdv) {
			if (x.getText().toLowerCase().contains("centro de servicio santa fe - juan de garay 444")) {
				a = true;
			}
			
		}
		Assert.assertTrue(a);
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=5, dataProvider="SalesCuentaActiva")
	public void TS94785_Ventas_Entregas_General_Verificar_que_se_pueda_seleccionar_ModEntrega_Tangible(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(25000);
		driver.findElement(By.cssSelector(".slds-m-left--x-small.slds-button.slds-button--brand")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DeliveryMethod")));
		Select env = new Select (driver.findElement(By.id("DeliveryMethod")));
		env.selectByVisibleText("Store Pick Up");
		Select prov = new Select (driver.findElement(By.id("State")));
		prov.selectByVisibleText("Ciudad Aut\u00f3noma de Buenos Aires");
		Select loc = new Select (driver.findElement(By.id("City")));
		loc.selectByVisibleText("CIUD AUTON D BUENOS AIRES");
		driver.findElement(By.id("Store")).click();
		driver.findElements(By.cssSelector(".slds-list__item.ng-binding.ng-scope")).get(0).click();
		driver.findElement(By.id("SalesChannelConfiguration_nextBtn")).click();
		sleep(7000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-col.taChangeDeliveryMethod.slds-text-body--small.slds-m-left--large")).getText().contains("Store Pick Up"));
		driver.findElement(By.cssSelector(".slds-m-left--x-small.slds-button.slds-button--brand")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DeliveryMethod")));
		Select nenv = new Select (driver.findElement(By.id("DeliveryMethod")));
		nenv.selectByVisibleText("Delivery");
		driver.findElement(By.id("SalesChannelConfiguration_nextBtn")).click();
		sleep(7000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-col.taChangeDeliveryMethod.slds-text-body--small.slds-m-left--large")).getText().contains("Delivery"));		
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=6, dataProvider="SalesCuentaActiva")
	public void TS94903_Venta_Medio_de_pago_Verificar_LOV_para_canal_Presencial_Oficinas_Comerciales_POC(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(15000);
		sb.Crear_DomicilioLegal(provincia, localidad, "falsa", "", "1000", "", "", "1549");
		sleep(20000);
		//driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();
		sleep(15000);
		driver.findElement(By.id("LineAssignment_nextBtn")).click();
		sleep(10000);
		driver.findElement(By.id("ICCDAssignment_nextBtn")).click();
		sleep(10000);
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		sleep(10000);
		driver.findElement(By.id("PaymentMethodRadio")).click();
		sleep(3000);
		List <WebElement> list = driver.findElements(By.cssSelector(".slds-list__item.ng-binding.ng-scope"));
		boolean a = false;
		boolean b = false;
		boolean c = false;
		for (WebElement x : list) {
			if (x.getText().toLowerCase().contains("debito a proxima factura")) {
				a = true;
			}
			if (x.getText().toLowerCase().contains("efectivo")) {
				b = true;
			}
			if (x.getText().toLowerCase().contains("tarjeta de credito")) {
				c = true;
			}
		}
		Assert.assertTrue(a && b && c);
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=4, dataProvider="SalesCuentaActiva")
	public void TS95260_Venta_General_Visualizar_Packs_Plan(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.agregarplan("Plan con Tarjeta");
		sleep(25000);
		 driver.findElement(By.cssSelector(".slds-button.slds-button_icon-border-filled.cpq-item-actions-dropdown-button")).click();
	      sleep(1000);
	      List <WebElement> mas = driver.findElement(By.cssSelector(".slds-dropdown__list.cpq-item-actions-dropdown__list")).findElements(By.tagName("span"));
	      boolean a = false; 
	      for (WebElement x : mas) { 
	        if (x.getText().toLowerCase().contains("inspect")) { 
	          x.click();
	          sleep(5000);
	          break;
	        } 
	      } 
	      driver.findElement(By.id("js-cpq-lineitem-details-modal-content")).findElement(By.className("cpq-product-name")).click();
	      sleep(3000);
	     mas = driver.findElement(By.id("js-cpq-lineitem-details-modal-content")).findElements(By.className("cpq-item-base-product"));
	      for (WebElement UnaM : mas) {
	    	  if (UnaM.getText().toLowerCase().contains("packs opcionales")) {
	    		  UnaM.findElement(By.tagName("button")).click();
	    		  sleep(7000);
	    		  mas = driver.findElement(By.id("js-cpq-lineitem-details-modal-content")).findElements(By.cssSelector(".cpq-item-base-product"));
	    		  for(WebElement x : mas) {
	    			 if( x.getText().toLowerCase().contains("packs de sms")) {
	    			  a = true;
	    			  break;
	    			 }
	    		  }
	    		  break;
	    	  }
	      }
	     Assert.assertTrue(a);
	}
	
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=4, dataProvider="SalesCuentaActiva")
	public void TS95051_Ventas_Acciones_Verificar_Visualizacion_De_Detalles_Pack(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");
		sleep(25000);
		driver.findElement(By.cssSelector(".cpq-product-link.slds-text-body_small.slds-float_right")).click();
		sleep(20000);
		List <WebElement> detalles = driver.findElement(By.className("services")).findElements(By.className("margin-space"));
	    boolean voz = false, sms = false, datos = false, amigos = false; 
	    for (WebElement x : detalles) { 
	        if (x.getText().toLowerCase().contains("voz")) { 
	          voz = true;
	        } 
	        if (x.getText().toLowerCase().contains("sms")) { 
		          sms = true;
		    } 
	        if (x.getText().toLowerCase().contains("mms")) { 
		          datos = true;
		    } 
	        if (x.getText().toLowerCase().contains("datos")) { 
		          amigos = true;
		    } 
	    } 
	     Assert.assertTrue(voz);
	     Assert.assertTrue(sms);
	     Assert.assertTrue(datos);
	     Assert.assertTrue(amigos);
	}
	
	@Test(groups={"Sales","AltaDeLinea","Ola1"}, priority=3, dataProvider="SalesCuentaActiva")
	public void TS95048_Ventas_Acciones_Verificar_accion_detalle_Plan_Movil(String sCuenta, String sDni, String sLinea) throws IOException{
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		List<WebElement> opcs = driver.findElements(By.cssSelector(".categoryLabel.slds-text-align--center"));
		for(WebElement UnaO : opcs) {
			if(UnaO.getText().equalsIgnoreCase("planes")) {
				UnaO.click();
				break;
			}
		}
		sleep(5000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".cpq-product-link.slds-text-body_small.slds-float_right")).getText().equalsIgnoreCase("more"));
		driver.findElement(By.cssSelector(".cpq-product-link.slds-text-body_small.slds-float_right")).click();
		sleep(8000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-modal.slds-fade-in-open.slds-modal--large")).findElement(By.tagName("h2")).getText().equalsIgnoreCase("product details"));
	}
	
	@Test(groups={"Sales","AltaDeLinea","Ola1"}, priority=3, dataProvider="SalesCuentaActiva")
	public void TS95050_Ventas_Acciones_Verificar_accion_detalle_Packs(String sCuenta, String sDni, String sLinea) throws IOException{
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		List<WebElement> opcs = driver.findElements(By.cssSelector(".categoryLabel.slds-text-align--center"));
		for(WebElement UnaO : opcs) {
			if(UnaO.getText().equalsIgnoreCase("packs")) {
				UnaO.click();
				break;
			}
		}
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-input.ng-pristine.ng-untouched.ng-valid")).sendKeys("pack");		
		sleep(8000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".cpq-product-link.slds-text-body_small.slds-float_right")).getText().equalsIgnoreCase("more"));
		WebElement more = driver.findElements(By.cssSelector(".cpq-product-link.slds-text-body_small.slds-float_right")).get(0);
		more.click();
		sleep(8000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-modal.slds-fade-in-open.slds-modal--large")).findElement(By.tagName("h2")).getText().equalsIgnoreCase("product details"));
	}
	
	@Test(groups={"Sales","AltaDeLinea","Ola1"}, priority=6, dataProvider="SalesCuentaActiva")//*******************Arreglar
	public void TS94762_Ventas_Modo_De_Pago_General_Verificar_LOV_Modalidad_De_Pago(String sCuenta, String sDni, String sLinea) throws IOException{
		boolean DPF = false;
		boolean E = false;
		boolean TC = false;
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(25000);
		sb.Crear_DomicilioLegal( provincia, localidad,"falsa", "", "2154", "", "", "2453");
		sleep(25000);
		CustomerCare page = new CustomerCare(driver);
		WebElement sig = driver.findElement(By.id("LineAssignment_nextBtn"));
		page.obligarclick(sig);
		sleep(10000);
		try {driver.findElement(By.id("Step_Error_Huawei_S013_nextBtn")).click();
		sleep(8000);}catch (org.openqa.selenium.NoSuchElementException ex1) {}
		//page.obligarclick(driver.findElement(By.id("ICCDAssignment_nextBtn")));
		//sleep(10000);
		//page.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		//sleep(10000);
		sleep(8000);
		List<WebElement> mediosP = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding"));
		for (WebElement UnMP : mediosP) {
			if (UnMP.getText().toLowerCase().contains("tarjeta de credito")){System.out.println("TDC");
			TC = true;
		}
			if (UnMP.getText().toLowerCase().contains("efectivo")) {System.out.println("Efectivo");
				E = true;
			}
			if (UnMP.getText().toLowerCase().contains("debito a proxima factura")){System.out.println("factura");
			DPF = true;
		}
		}
		Assert.assertTrue(TC&&E&&DPF);
		sleep(5000);
	}
	
	@Test(groups={"Sales","AltaDeLinea","Ola1"}, priority=7)
	public void TS95261_Ventas_General_Asignar_Una_Linea_Como_Decisora_Durante_La_Venta_En_Misma_Pantalla_De_Asignacion_De_Lineas(){
		boolean estaM = false;
		sb.BtnCrearNuevoCliente();
		ContactSearch contact = new ContactSearch(driver);
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("FirstName")).sendKeys("yy");
		driver.findElement(By.id("LastName")).sendKeys("z");
		driver.findElement(By.id("Birthdate")).sendKeys("28/12/1999");
		contact.sex("masculino");
		driver.findElement(By.id("Contact_nextBtn")).click();
		sleep(10000);
		sb.elegirplan("Plan con tarjeta");
		sb.continuar();
		sleep(25000);
		sb.Crear_DomicilioLegal(provincia, "ABEL", "falsa", "", "1000", "", "", "1549");
		sleep(15000);
		List<WebElement> mns = driver.findElements(By.cssSelector(".message.description.ng-binding.ng-scope"));
		for(WebElement UnM : mns) {
			if(UnM.getText().toLowerCase().contains("sin l\u00ednea decisoria."))
				estaM = true;
		}
		Assert.assertTrue(estaM);
		Assert.assertTrue(driver.findElement(By.id("text-input-01")).isDisplayed());
	}
	
	@Test(groups={"Sales", "AltaDeContacto", "Ola1"}, priority=2, dataProvider="SalesCuentaActiva")
	public void TS94947_Alta_de_Contacto_Busqueda_Verificar_boton_3_sobre_contactos(String sCuenta, String sDni, String sLinea) throws IOException {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		List <WebElement> btn = driver.findElements(By.cssSelector(".slds-button.slds-button.slds-button--icon"));
		boolean a = false;
		for (WebElement x : btn) {
			if (x.getText().toLowerCase().equals("ver contacto")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test(groups={"Sales", "AltaDeCuenta","Ola1"}, priority=2, dataProvider="SalesCuentaActiva")
	public void TS95198_Alta_Cuenta_Busqueda_Verificar_busqueda_combinada_DNI_con_NyAp_DNI_No_Existe_NyAP_Existe(String sCuenta, String sDni, String sLinea) throws IOException {
		BasePage dni = new BasePage(driver);
		String NyA = sCuenta;
		dni.setSimpleDropdown(driver.findElement(By.id("SearchClientDocumentType")), "DNI");
		driver.findElement(By.id("SearchClientDocumentNumber")).click();
		driver.findElement(By.id("SearchClientDocumentNumber")).sendKeys("5849652");
		sleep(10000);
		SalesBase SB = new SalesBase(driver);
		SB.BuscarAvanzada(NyA.split(" ")[0], NyA.split(" ")[1], "", "", "");
		WebElement tTel = driver.findElement(By.id("tab-scoped-3")).findElement(By.tagName("tbody")).findElements(By.tagName("td")).get(2);
		Assert.assertTrue(!tTel.getText().equals("5849652"));
		WebElement tNom = driver.findElement(By.id("tab-scoped-3")).findElement(By.tagName("tbody")).findElements(By.tagName("td")).get(0);
		Assert.assertTrue(tNom.getText().toLowerCase().contains(sCuenta.toLowerCase()));
	}

	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=8,dataProvider="SalesCuentaActiva")
	public void TS95302_Ventas_General_Visualizar_Datos_De_La_Pantalla_De_Resumen_De_Venta(String sCuenta, String sDni, String sLinea) throws IOException{
		boolean nOrden = false, nyA = false, pago = false, dni = false, entrega = false, modelo = false, serial = false, linea = false, plan = false;
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		boolean x = false;
		sleep(18000);
		sb.elegirplan("Plan con tarjeta");
		/*driver.findElement(By.cssSelector(".slds-input.ng-valid.ng-not-empty.ng-dirty.ng-valid-parse.ng-touched")).clear();
		driver.findElement(By.cssSelector(".slds-input.ng-valid.ng-dirty.ng-valid-parse.ng-touched.ng-empty")).sendKeys("Plan con tarjeta");		
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<WebElement> agregar = driver.findElements(By.cssSelector(".slds-button.slds-button_neutral.cpq-add-button")); 
		agregar.get(0).click();*/
		sb.continuar();
		sleep(20000);
		sb.Crear_DomicilioLegal(provincia, "ABEL", "falsa", "", "1000", "", "", "1549");
		sleep(7000);
		CustomerCare page = new CustomerCare(driver);
		sb.Crear_DomicilioLegal("Buenos Aires","Vicente Lopez","falsa", "", "5846", "", "", "5248");
		sleep(15000);
		try {
			driver.findElement(By.id("Step_Error_Huawei_S013_nextBtn")).click();
			sleep(8000);
		}catch(NoSuchElementException ex1) {
			WebElement este = driver.findElement(By.id("LineAssignment_nextBtn"));
			page.obligarclick(este);
			sleep(5000);
		}
		WebElement sig = driver.findElement(By.id("PaymentMethod_nextBtn"));
		page.obligarclick(sig);
		sleep(5000);
		sb.validacionProcesoVenta("qa");
		sig = driver.findElement(By.id("QAContactData_nextBtn"));
		page.obligarclick(sig);
		sleep(5000);
		sb.praguntasRespuestasLino("no", "13/09/1988");
		sig = driver.findElement(By.id("QAResult_nextBtn"));
		page.obligarclick(sig);
		sleep(5000);
		sig = driver.findElement(By.id("QAError_nextBtn"));
		page.obligarclick(sig);
		sleep(5000);
		sig = driver.findElement(By.id("ValidationResult_nextBtn"));
		page.obligarclick(sig);
		sleep(5000);
		List<WebElement> datos = driver.findElement(By.id("DetailsOrderSummary")).findElements(By.className("ng-binding"));
		for(WebElement UnD : datos) {
			System.out.println(UnD.getText());
			if (UnD.getText().toLowerCase().contains("nro de orden"))
				nOrden = true;
			if (UnD.getText().toLowerCase().matches("titular de producto\\/servicio\\s\\w[a-z]*\\s\\w[a-z]*"))
				nyA = true;
			if (UnD.getText().toLowerCase().matches("dni\\s\\w[0-9]*"))
				dni = true;
			if (UnD.getText().toLowerCase().contains("efectivo")||UnD.getText().toLowerCase().contains("credito")||UnD.getText().toLowerCase().contains("factura"))
				pago = true;
			if (UnD.getText().toLowerCase().contains("entrega"))
				entrega = true;
		}
		Assert.assertTrue(nOrden&&nyA&&dni&&pago&&entrega&&modelo&&serial&&linea&&plan);
		
		/*sig = driver.findElement(By.id("LineAssignment_nextBtn"));
		page.obligarclick(sig);
		sleep(5000);*/
		/*sig = driver.findElement(By.id("DeliveryMethodConfiguration_nextBtn"));
		page.obligarclick(sig);
		sleep(5000);*/
		
	}
	
	@Test(groups={"Sales","AltaDeLinea","Ola1"}, priority=3, dataProvider="SalesCuentaActiva")
	public void TS94649_Ventas_NumeroOrden_Verificar_Orden_de_Venta_Abierta_Nueva_Venta(String sCuenta, String sDni, String sLinea) throws IOException{
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan("Plan con tarjeta");
		String ord = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
		ord=ord.substring(ord.length()-8, ord.length());
		sb.continuar();
		sleep(10000);
		System.out.println(ord);
		List<WebElement> cont = driver.findElements(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand"));
			for(WebElement c : cont){
			c.getText().equals("Continuar");
			c.click();}
		sleep(7000);
		driver.findElements(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope")).get(1).click();
		sleep(3000);
		driver.findElement(By.id("alert-ok-button")).click();
		sleep(2000);
		driver.get("https://crm--sit.cs14.my.salesforce.com/home/home.jsp?tsid=02u41000000QWha/");
		sleep(20000);
		List<WebElement> frame2 = driver.findElements(By.tagName("iframe"));
		driver.switchTo().frame(frame2.get(0));
		WebElement list = driver.findElement(By.cssSelector(".slds-truncate.slds-text-align--center")).findElement(By.tagName("a"));
		System.out.println(list.getText());
		list.click();
		sleep(5000);
		List<WebElement> lst = driver.findElements(By.cssSelector(".bPageBlock.brandSecondaryBrd.secondaryPalette"));
		for (WebElement UnE : lst) {
			if(UnE.findElement(By.className("pbTitle")).getText().toLowerCase().contains("productos de pedido")) {
				if (UnE.findElement(By.cssSelector(".dataRow.even.first")).getText().toLowerCase().contains("Plan con tarjeta"))
					Assert.assertTrue(true);
				else 
					Assert.assertTrue(false);
			}
		}
	}
		
	@Test(groups={"Sales", "AltaDeLinea", "Ola1","filtrado"}, priority=3, dataProvider="SalesCuentaActiva")
	public void TS94479_Alta_Linea_Carrito_Eliminar_productos_del_carrito(String sCuenta, String sDni, String sLinea) throws IOException {
		Ta_CPQ page3 = new Ta_CPQ(driver);
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		sb.elegirplan(plan);
		sleep(2000);
		page3.abrirprimeraflecha();
		sleep(3000);
		page3.deleteoneplan();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertFalse(page3.isPlanPresent());
	}
	@Test(groups={"Sales", "AltaDeLinea", "Ola1","filtrado"}, priority=3, dataProvider="SalesCuentaActiva")
	public void TS94482_Alta_Linea_Carrito_Eliminar_todos_los_productos_del_carrito1(String sCuenta, String sDni, String sLinea) throws IOException {
		Ta_CPQ page3 = new Ta_CPQ(driver);
		sb.BuscarCuenta(DNI, sDni); 
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(12000);
		try { for(WebElement e : driver.findElements(By.className("cpq-product-name"))) {
			page3.clickOnDelete();
			try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		} } catch (java.lang.IndexOutOfBoundsException e) {}
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.cssSelector(".slds-input.ng-valid.ng-not-empty.ng-dirty.ng-valid-parse.ng-touched")).clear();
		/*driver.findElement(By.cssSelector(".slds-input.ng-valid.ng-not-empty.ng-dirty.ng-valid-parse.ng-touched")).sendKeys("Galaxy S8");		
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.cssSelector(".slds-button.slds-button--neutral.add-button")).click();*/
		sb.elegirplan("Galaxy S8");
		sleep(15000);
		page3.abrirprimeraflecha();
		sleep(3000);
		page3.deleteoneplan();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page3.abrirprimeraflecha();
		sleep(3000);
		page3.deleteoneplan();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement cart = driver.findElement(By.cssSelector(".slds-grid.slds-grid_vertical-align-center.slds-grid_align-center.cpq-no-cart-items-msg"));
		Assert.assertTrue((cart.getText().equals("Cart is empty.")));
	}
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=3, dataProvider="SalesCuentaActiva")
	public void TS94522_CRM_Fase_1_SalesCPQ_Alta_Linea_Carrito_Verificar_el_mensaje_al_vaciar_el_carrito_XX(String sCuenta, String sDni, String sLinea) throws IOException {
		Ta_CPQ cart = new Ta_CPQ(driver);
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sb.elegirplan("Plan con tarjeta");
		sleep(5000);
		cart.abrirprimeraflecha();
		sleep(3000);
		cart.deleteoneplan();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		//WebElement messageEmptyCart = driver.findElement(By.xpath(".//div[@class=\"slds-grid slds-grid--vertical-align-center slds-grid--align-center cpq-no-cart-items-msg\"]"));
		//Assert.assertEquals(messageEmptyCart.getText().trim(), "Cart is empty.");
		Assert.assertEquals("Cart is empty.", cart.getEmptyCartMessage());
	}
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=9, dataProvider="SalesCuentaActiva")
	public void TS94515_CRM_Fase_1_SalesCPQ_Alta_Linea_Costo_Operacion_Validar_formato_del_monto(String sCuenta, String sDni, String sLinea) throws IOException {
		Ta_CPQ cart = new Ta_CPQ(driver);
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		sleep(15000);
		WebElement produc = driver.findElement(By.cssSelector(".cpq-categories-container")).findElements(By.tagName("li")).get(1);
		produc.click();
		driver.findElement(By.cssSelector(".slds-input.ng-pristine.ng-untouched.ng-valid")).sendKeys("Galaxy S8");		
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.cssSelector(".slds-button.slds-button--neutral.add-button")).click();
		sleep(15000);
		WebElement more	 = driver.findElements(By.cssSelector(".product-link.slds-text-body--small.slds-float--right")).get(0);
		more.click();
		sleep(15000);
		//WebElement waiter = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".slds-item--detail.slds-truncate")));
		List<WebElement> values = driver.findElements(By.cssSelector(".slds-item--detail.slds-truncate"));
		String[] precissionCounter = values.get(3).getText().split(",");
		Assert.assertEquals(precissionCounter[1].length(), 2);
		List<WebElement> frame2 = driver.findElements(By.tagName("iframe"));
		//driver.switchTo().frame(frame2.get(0));
		driver.findElements(By.cssSelector(".slds-button.slds-button--neutral")).get(3).click();
		driver.switchTo().defaultContent();
		sleep(10000);
		List<WebElement> montos = driver.findElements(By.className("cpq-underline"));
		montos = montos.subList(0, 3);
		for (WebElement UnM : montos) {
			System.out.println("monto="+UnM.getText());
			precissionCounter =UnM.getText().split(",");
			Assert.assertEquals(precissionCounter[1].length(), 2);
		}
	}
	@Test(groups={"Sales", "AltaLinea", "Ola1"},dataProvider="SalesCuentaActiva")              
	public void TS94481_checkPaperCanLabel(String sCuenta, String sDni, String sLinea) throws IOException {
		Ta_CPQ page3 = new Ta_CPQ(driver);
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sb.elegirplan("Plan con tarjeta");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement boton = driver.findElements(By.cssSelector(".slds-button.slds-button_icon-border-filled.cpq-item-actions-dropdown-button")).get(0);
		boton.click();
		WebElement delet = driver.findElements(By.cssSelector(".slds-dropdown.slds-dropdown_right.cpq-item-actions-dropdown")).get(0);
		Assert.assertTrue(delet.getText().contains("Delete"));
	}
	
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=9, dataProvider="SalesCuentaActiva")
	public void TS94518_CRM_Fase_1_SalesCPQ_Alta_Linea_Costo_Operacion_Verificar_opciones_del_carrito_Boton_Siguiente(String sCuenta, String sDni, String sLinea) throws IOException{
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sb.elegirplan("Plan con tarjeta");
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Boolean r = false;
		List<WebElement> cont = driver.findElements(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand"));
		for(WebElement c : cont){
			if(c.getText().equals("Continuar")){
			c.isDisplayed();
			r=true;}}
		Assert.assertTrue(r);
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=9, dataProvider="SalesCuentaActiva")
	public void TS94707_CRM_Fase_2_SalesCPQ_Nueva_Venta_Orden_Venta_Verficar_que_se_puede_modificar_el_ciclo_de_facturacion(String sCuenta, String sDni, String sLinea) throws Exception {
		/*Se verifica que el sistema permite modificar el ciclo de facturacion*/
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sb.elegirplan("Plan con tarjeta"); 
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sb.continuar();
		sleep(8000);
		Assert.assertTrue(false);
	/*	LineAssignment lineAssignment = new LineAssignment (driver);
		lineAssignment.clickOnNext();
		BillSimulation billSimulation = new BillSimulation (driver);
		billSimulation.clickOnNext();
		DeliveryMethod deliveryMethod = new DeliveryMethod (driver);
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Select billingCycleSelect = new Select (deliveryMethod.getBillingCycle());
		billingCycleSelect.selectByValue("7");
		Assert.assertEquals(billingCycleSelect.getFirstSelectedOption().getText(), "7");*/
	}
	
	@Test(groups = {"Sales", "AltaDeLinea", "Ola1"}, priority=9, dataProvider="SalesCuentaActiva")
	public void TS94706_CRM_Fase_2_SalesCPQ_Nueva_Venta_Orden_Venta_Verficar_ciclo_de_facturacion_asignado_por_default(String sCuenta, String sDni, String sLinea) throws Exception {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sb.elegirplan("Plan con tarjeta");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sb.continuar();
		sleep(8000);
		Assert.assertTrue(false);
	/*	LineAssignment lineAssignmentPage = new LineAssignment(driver);
		lineAssignmentPage.clickOnNext();
		BillSimulation billSimulationPage = new BillSimulation(driver);
		billSimulationPage.clickOnNext();
		DeliveryMethod deliveryMethodPage = new DeliveryMethod (driver);
		Select billingCycleSelect = new Select(deliveryMethodPage.getBillingCycle());
		Assert.assertEquals(billingCycleSelect.getFirstSelectedOption().getText(), "21");*/
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, priority=9, dataProvider="SalesCuentaActiva")
	public void TS94708_CRM_Fase_2_SalesCPQ_Nueva_Venta_Orden_Venta_Verficar_ciclos_de_facturacion_disponibles(String sCuenta, String sDni, String sLinea) throws Exception{
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sb.elegirplan("Plan con tarjeta");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sb.continuar();
		sleep(8000);
		Assert.assertTrue(false);
		/*
		LineAssignment lineAssignment = new LineAssignment (driver);
		lineAssignment.clickOnNext();
		BillSimulation billSimulation = new BillSimulation (driver);
		billSimulation.clickOnNext();
		DeliveryMethod deliveryMethod = new DeliveryMethod (driver);
		
		Assert.assertTrue(deliveryMethod.getBillingCycleOptions().contains("1"));
		Assert.assertTrue(deliveryMethod.getBillingCycleOptions().contains("7"));
		Assert.assertTrue(deliveryMethod.getBillingCycleOptions().contains("14"));
		Assert.assertTrue(deliveryMethod.getBillingCycleOptions().contains("21"));*/
	}
	
	@Test(groups={"Sales", "AltaDeLinea", "Ola1"}, dataProvider="SalesCuentaActiva")
	public void TS94617_CRM_Fase_1_SalesCPQ_Alta_Linea_Buscar_Cliente_Buscar_por_Nombre_del_plan_V360(String sCuenta, String sDni, String sLinea) throws IOException {
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sb.elegirplan("Plan con tarjeta");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
		WebElement result = driver.findElement(By.cssSelector(".slds-tile__title.slds-truncate.cpq-product-name"));
		System.out.println(result.getText());
		Assert.assertTrue(result.getText().contains("Plan con tarjeta"));
			
	}
	
	@Test(groups={"Sales", "AltaLinea", "Ola1"}, dataProvider="SalesCuentaActiva")
	public void TS94480_checkPaperCanIsPresent(String sCuenta, String sDni, String sLinea) {
		sb.BuscarCuenta(DNI, sDni);
		//sb.BuscarCuenta(DNI, buscarCampoExcel(1, "Cuenta Activa", 2));
		sb.acciondecontacto("catalogo");
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sb.elegirplan("Plan con tarjeta");
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-button__icon.slds-button__icon--.slds-icon-text-default")).click();
		WebElement icon = driver.findElements(By.cssSelector(".slds-icon.slds-icon--x-small")).get(4);
		Assert.assertTrue(icon.isDisplayed());
	}
}

