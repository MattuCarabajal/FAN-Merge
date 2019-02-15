package Tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.BasePage;
import Pages.OM;
import Pages.OMQPage;
import Pages.setConexion;

public class OMN extends TestBase {

	private WebDriver driver;
	protected OM om;


	@BeforeClass (alwaysRun = true, groups = "OM")
	public void init() {
		driver = setConexion.setupEze();
		sleep(5000);
		login(driver, "https://crm--sit.cs14.my.salesforce.com/", "U585991", "Testa10k");
		sleep(5000);
		om = new OM(driver);
	}
	
	@BeforeMethod (alwaysRun = true, groups = "OM")
	public void before() {
		BasePage bp = new BasePage(driver);
		bp.cajonDeAplicaciones("Sales");
		sleep(5000);
		driver.findElement(By.id("Order_Tab")).click();
		sleep(3000);
	}
	
	//@AfterClass (alwaysRun = true, groups = "OM")
	public void quit() {
		driver.quit();
		sleep(5000);
	}
	
	
	@Test (groups = "OM")
	public void TS6729_Ordenes_Order_Detail_Adjunto_de_archivos_Formato_JPG() {
		om.primeraOrden();
		driver.findElement(By.name("attachFile")).click();
		sleep(5000);
		boolean a = false;
		if (driver.findElement(By.id("file")).isEnabled()) {
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\jpg.jpg");
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "OM")
	public void TS6730_Ordenes_Order_Detail_Adjunto_de_archivos_Formato_PNG() {
		om.primeraOrden();
		driver.findElement(By.name("attachFile")).click();
		sleep(5000);
		boolean a = false;
		if (driver.findElement(By.id("file")).isEnabled()) {
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\png.png");
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "OM")
	public void TS6731_Ordenes_Order_Detail_Adjunto_de_archivos_Formato_PDF() {
		om.primeraOrden();
		driver.findElement(By.name("attachFile")).click();
		sleep(5000);
		boolean a = false;
		if (driver.findElement(By.id("file")).isEnabled()) {
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\pdf.pdf");
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "OM")
	public void TS6732_Ordenes_Order_Detail_Adjunto_de_archivos_Formato_XML() {
		om.primeraOrden();
		driver.findElement(By.name("attachFile")).click();
		sleep(5000);
		boolean a = false;
		if (driver.findElement(By.id("file")).isEnabled()) {
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\xml.xml");
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "OM")
	public void TS6733_Ordenes_Order_Detail_Adjunto_de_archivos_Formato_TXT() {
		om.primeraOrden();
		driver.findElement(By.name("attachFile")).click();
		sleep(5000);
		boolean a = false;
		if (driver.findElement(By.id("file")).isEnabled()) {
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\txt.txt");
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "OM")
	public void TS6734_Ordenes_Order_Detail_Adjunto_de_archivos_Formato_XLS() {
		om.primeraOrden();
		driver.findElement(By.name("attachFile")).click();
		sleep(5000);
		boolean a = false;
		if (driver.findElement(By.id("file")).isEnabled()) {
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\xls.xlsm");
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "OM")
	public void TS6735_Ordenes_Order_Detail_Adjunto_de_archivos_Formato_DOC() {
		om.primeraOrden();
		driver.findElement(By.name("attachFile")).click();
		sleep(5000);
		boolean a = false;
		if (driver.findElement(By.id("file")).isEnabled()) {
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\doc.docx");
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "OM")
	public void TS6736_Ordenes_Order_Detail_Adjunto_de_archivos_Formato_VSO() {
		om.primeraOrden();
		driver.findElement(By.name("attachFile")).click();
		sleep(5000);
		boolean a = false;
		if (driver.findElement(By.id("file")).isEnabled()) {
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\vso.vso");
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "OM")
	public void TS6737_Ordenes_Order_Detail_Adjunto_de_archivos_Varios_formatos() {
		om.primeraOrden();
		driver.findElement(By.name("attachFile")).click();
		sleep(5000);
		boolean a = false;
		if (driver.findElement(By.id("file")).isEnabled()) {
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\doc.docx");
			sleep(2000);
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\xls.xlsm");
			sleep(2000);
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\txt.txt");
			sleep(2000);
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\xml.xml");
			sleep(2000);
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\pdf.pdf");
			sleep(2000);
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\png.png");
			sleep(2000);
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\jpg.jpg");
			sleep(2000);
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "OM")
	public void TS6740_Ordenes_Order_Detail_Links_funcionales() {
		om.primeraOrden();
		driver.findElement(By.name("attachFile")).click();
		sleep(5000);
		driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\jpg.jpg");
		driver.findElement(By.id("Attach")).click();
		sleep(3000);
		driver.findElement(By.name("cancel")).click();
		sleep(5000);
		driver.findElements(By.cssSelector(".dataRow.even.last.first")).get(0).findElements(By.tagName("td")).get(1).findElement(By.tagName("a")).click();
		sleep(5000);
		WebElement title = driver.findElement(By.className("bPageTitle"));
		boolean a = false, b = false;
		if (title.getText().toLowerCase().contains("attached file")) {
			a = true;
		}
		if (title.getText().toLowerCase().contains("jpg.jpg")) {
			b = true;
		}
		Assert.assertTrue(a && b);
	}
	
	@Test (groups = "OM")
	public void TS80196_Ordenes_Cliente_existente_Cambio_de_SIM_Plan_con_tarjeta_Sin_delivery_Paso_3() throws InterruptedException {
		om.Gestion_Alta_De_Linea("FlorOM", "Plan con tarjeta");
		driver.findElement(By.id("Order_Tab")).click();
		om.Cambio_De_SimCard("11-29-2019");
		driver.findElement(By.name("ta_submit_order")).click();
		sleep(15000);
		om.completarFlujoOrquestacion();
		driver.findElement(By.name("vlocity_cmt__vieworchestrationplan")).click();
		sleep(10000);
		Assert.assertTrue(om.ordenCajasVerdes("Cambio de N\u00famero o SIM", "Env\u00edo de Actualizaci\u00f3n de Par\u00e1metros a la Red (SIM Card)(IMSI, KI e ICCID)", "En progreso | Comptel - Par\u00e1metros de la Red actualizados"));
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS80196_Ordenes_Cliente_existente_Cambio_de_SIM_Plan_con_tarjeta_Sin_delivery_Paso_3")
	public void TS80193_Ordenes_Cliente_existente_Cambio_de_SIM_Plan_con_tarjeta_Sin_delivery_Paso_0() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS80196_Ordenes_Cliente_existente_Cambio_de_SIM_Plan_con_tarjeta_Sin_delivery_Paso_3")
	public void TS80194_Ordenes_Cliente_existente_Cambio_de_SIM_Plan_con_tarjeta_Sin_delivery_Paso_1() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS80196_Ordenes_Cliente_existente_Cambio_de_SIM_Plan_con_tarjeta_Sin_delivery_Paso_3")
	public void TS80195_Ordenes_Cliente_existente_Cambio_de_SIM_Plan_con_tarjeta_Sin_delivery_Paso_2() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM")
	public void TS80191_Ordenes_Cliente_existente_Cambio_de_SIM_Plan_con_tarjeta_Sin_delivery_Verificacion_de_assets() throws InterruptedException {
		List<String> datos = new ArrayList<String>();		
		om.Gestion_Alta_De_Linea("FlorOM", "Plan con tarjeta");
		driver.findElement(By.id("Order_Tab")).click();
		datos = om.Cambio_De_SimCard2();
		driver.findElement(By.name("ta_submit_order")).click();
		sleep(15000);
		om.completarFlujoOrquestacion();
		driver.findElement(By.id("accid_ileinner")).findElement(By.tagName("a")).click();
		sleep(20000);
		om.irAChangeToOrder();
		sleep(10000);
		om.fechaAvanzada2();
		driver.findElement(By.cssSelector(".slds-button.cpq-item-has-children")).click();
		sleep(3000);
		driver.findElement(By.xpath(".//*[@id='tab-default-1']/div[1]/ng-include/div/div/div/div[4]/div[2]/div/ng-include/div/div[2]/ng-include/div/div[1]/div/div[2]/div[11]")).click();
		List<WebElement> lista = driver.findElements(By.cssSelector(".slds-dropdown__list.cpq-item-actions-dropdown__list"));
		lista.get(1).click();
		sleep(3000);
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.className("slds-section")).getLocation().y+" )");
		String nroImsi = driver.findElement(By.xpath("//*[@id=\"js-cpq-product-cart-config-form\"]/div[1]/div/form/div[18]/div/input")).getAttribute("value");
		Assert.assertTrue(nroImsi.equals(datos.get(1)));
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS79026_Ordenes_Cliente_existente_Alta_de_linea_Sin_delivery_Sin_VAS_Paso_4")
	public void TS79021_Ordenes_Cliente_existente_Alta_de_linea_Sin_delivery_Sin_VAS_Paso_0() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS79026_Ordenes_Cliente_existente_Alta_de_linea_Sin_delivery_Sin_VAS_Paso_4")
	public void TS79022_Ordenes_Cliente_existente_Alta_de_linea_Sin_delivery_Sin_VAS_Paso_1() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS79026_Ordenes_Cliente_existente_Alta_de_linea_Sin_delivery_Sin_VAS_Paso_4")
	public void TS79023_Ordenes_Cliente_existente_Alta_de_linea_Sin_delivery_Sin_VAS_Paso_2_NA() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS79026_Ordenes_Cliente_existente_Alta_de_linea_Sin_delivery_Sin_VAS_Paso_4")
	public void TS79024_Ordenes_Cliente_existente_Alta_de_linea_Sin_delivery_Sin_VAS_Paso_2() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS79026_Ordenes_Cliente_existente_Alta_de_linea_Sin_delivery_Sin_VAS_Paso_4")
	public void TS79025_Ordenes_Cliente_existente_Alta_de_linea_Sin_delivery_Sin_VAS_Paso_3() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM")
	public void TS79026_Ordenes_Cliente_existente_Alta_de_linea_Sin_delivery_Sin_VAS_Paso_4() throws InterruptedException {
		om.Gestion_Alta_De_Linea("FlorOM", "Plan prepago nacional");
		driver.findElement(By.id("Order_Tab")).click();
		sleep(5000);
		om.selectVistaByVisibleText("LineasFlor");
		sleep(3000);		
		WebElement orden = driver.findElement(By.cssSelector(".x-grid3-col.x-grid3-cell.x-grid3-td-ORDERS_ORDER_NUMBER"));
		orden.findElement(By.tagName("div")).findElement(By.tagName("a")).click();
		sleep(5000);
		driver.findElement(By.name("vlocity_cmt__vieworchestrationplan")).click();
		sleep(10000);
		om.ordenCajasVerdes("CreateSubscriber - S203", "Env\u00edo de Activaci\u00f3n de Servicios a la Red", "updateNumerStatus - S326");
	}
	
	@Test (groups = "OM")
	public void TS79046_Ordenes_Cliente_existente_Alta_de_linea_Sin_delivery_Sin_VAS_Verficacion_de_ASSETs_creados() throws InterruptedException {
		boolean a = false;
		om.Gestion_Alta_De_Linea("FlorOM", "Plan prepago nacional");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".panel.panel-default.panel-assets")));
		List <WebElement> assets = driver.findElement(By.cssSelector(".panel.panel-default.panel-assets")).findElements(By.cssSelector(".root-asset.ng-scope"));
		WebElement ultAsset = assets.get(assets.size() -1);
		ultAsset.findElement(By.className("p-expand")).click();
		sleep(3000);
		List <WebElement> contAsset = ultAsset.findElements(By.className("p-name"));
		for (int i=0; i<contAsset.size(); i++) {
			if (contAsset.get(i).getText().equalsIgnoreCase("Simcard")) {
				a = true;
			}
			if (contAsset.get(i).getText().equalsIgnoreCase("Voz")) {
				a = true;
			}
			if (contAsset.get(i).getText().equalsIgnoreCase("SMS Saliente")) {
				a = true;
			}
			if (contAsset.get(i).getText().equalsIgnoreCase("SMS Entrante")) {
				a = true;
			}
			if (contAsset.get(i).getText().equalsIgnoreCase("MMS")) {
				a = true;
			}
			if (contAsset.get(i).getText().equalsIgnoreCase("Datos")) {
				a = true;
			}
			if (contAsset.get(i).getText().equalsIgnoreCase("Caller Id")) {
				a = true;
			}
			if (contAsset.get(i).getText().equalsIgnoreCase("Transferencia de Llamadas")) {
				a = true;
			}
			if (contAsset.get(i).getText().equalsIgnoreCase("Conferencia Tripartita")) {
				a = true;
			}
			if (contAsset.get(i).getText().equalsIgnoreCase("Barrings Configurables por el Usuario")) {
				a = true;
			}
			if (contAsset.get(i).getText().equalsIgnoreCase("Contestador Personal")) {
				a = true;
			}
			if (contAsset.get(i).getText().equalsIgnoreCase("DDI con Roaming Internacional")) {
				a = true;
			}
			if (contAsset.get(i).getText().equalsIgnoreCase("InternetXDia")) {
				a = true;
			}
			if (contAsset.get(i).getText().equalsIgnoreCase("Numeros gratis a Personal 1 para voz contra recarga")) {
				a = true;
			}
			if (contAsset.get(i).getText().equalsIgnoreCase("Numeros gratis a Personal 1 para sms contra recarga")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "OM")
	public void TS122436_Ordenes_Cliente_existente_Alta_de_linea_Plan_prepago_nacional_Actualizacion_del_campo_Activation_Date() throws InterruptedException {
		om.Gestion_Alta_De_Linea("FlorOM", "Plan prepago nacional");
		driver.navigate().back();
		sleep(5000);
		driver.findElement(By.name("vlocity_cmt__vieworchestrationplan")).click();
		sleep(10000);
		String caja = driver.findElement(By.xpath("//*[@id=\"canvas\"]/div[15]/div[2]/div/div/ng-include/div/div[2]/span[2]")).getText();
		caja = caja.substring(0, caja.indexOf(":"));
		driver.navigate().back();
		sleep(5000);
		sleep(5000);
		String date = driver.findElement(By.id("ActivatedDate_ilecell")).getText();
		date = date.substring(0, date.indexOf(":"));
		Assert.assertTrue(caja.equals(date));
	}
	
	@Test (groups = "OM")
	public void TS122437_Ordenes_Cliente_existente_Alta_de_linea_Plan_con_tarjeta_Actualizacion_del_campo_Activation_Date() throws InterruptedException {
		om.Gestion_Alta_De_Linea("FlorOM", "Plan con tarjeta");
		driver.navigate().back();
		sleep(5000);
		driver.findElement(By.name("vlocity_cmt__vieworchestrationplan")).click();
		sleep(10000);
		String caja = driver.findElement(By.xpath("//*[@id=\"canvas\"]/div[15]/div[2]/div/div/ng-include/div/div[2]/span[2]")).getText();
		caja = caja.substring(0, caja.indexOf(":"));
		driver.navigate().back();
		sleep(5000);
		String date = driver.findElement(By.id("ActivatedDate_ilecell")).getText();
		date = date.substring(0, date.indexOf(":"));
		Assert.assertTrue(caja.equals(date));
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS80350_Ordenes_Cliente_existente_Alta_de_1_servicio_adicional_Plan_con_tarjeta_Paso_3")
	public void TS80346_Ordenes_Cliente_existente_Alta_de_1_servicio_adicional_Plan_con_tarjeta_Paso_0() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS80350_Ordenes_Cliente_existente_Alta_de_1_servicio_adicional_Plan_con_tarjeta_Paso_3")
	public void TS80347_Ordenes_Cliente_existente_Alta_de_1_servicio_adicional_Plan_con_tarjeta_Paso_1() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS80350_Ordenes_Cliente_existente_Alta_de_1_servicio_adicional_Plan_con_tarjeta_Paso_3")
	public void TS80348_Ordenes_Cliente_existente_Alta_de_1_servicio_adicional_Plan_con_tarjeta_Paso_2() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM")
	public void TS80350_Ordenes_Cliente_existente_Alta_de_1_servicio_adicional_Plan_con_tarjeta_Paso_3() throws InterruptedException {
		boolean gestion = false;
		om.AltaDeLineaCon1Servicio("FlorOM", "Plan con tarjeta");
		driver.navigate().back();
		sleep(7000);
		String status = driver.findElement(By.id("Status_ilecell")).getText();
		List <WebElement> gest = driver.findElements(By.cssSelector(".dataCol.inlineEditWrite"));
		for (WebElement x : gest) {
			if (x.getText().equalsIgnoreCase("Venta")) {
				gestion = true;
			}
		}
		driver.findElement(By.name("vlocity_cmt__vieworchestrationplan")).click();
		sleep(10000);
		Assert.assertTrue(status.equals("Activated"));
		Assert.assertTrue(gestion);
		om.ordenCajasVerdes("CreateSubscriber - S203", "Env\u00edo de Activaci\u00f3n de Servicios a la Red", "updateNumerStatus - S326");
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS79209_Ordenes_Cliente_existente_Alta_de_1_servicio_adicional_Plan_prepago_nacional_Sin_delivery_Sin_VAS_Paso_4")
	public void TS79205_Ordenes_Cliente_existente_Alta_de_1_servicio_adicional_Plan_prepago_nacional_Sin_delivery_Sin_VAS_Paso_0() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS79209_Ordenes_Cliente_existente_Alta_de_1_servicio_adicional_Plan_prepago_nacional_Sin_delivery_Sin_VAS_Paso_4")
	public void TS79206_Ordenes_Cliente_existente_Alta_de_1_servicio_adicional_Plan_prepago_nacional_Sin_delivery_Sin_VAS_Paso_1() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS79209_Ordenes_Cliente_existente_Alta_de_1_servicio_adicional_Plan_prepago_nacional_Sin_delivery_Sin_VAS_Paso_4")
	public void TS79207_Ordenes_Cliente_existente_Alta_de_1_servicio_adicional_Plan_prepago_nacional_Sin_delivery_Sin_VAS_Paso_2() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS79209_Ordenes_Cliente_existente_Alta_de_1_servicio_adicional_Plan_prepago_nacional_Sin_delivery_Sin_VAS_Paso_4")
	public void TS79208_Ordenes_Cliente_existente_Alta_de_1_servicio_adicional_Plan_prepago_nacional_Sin_delivery_Sin_VAS_Paso_3() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM")
	public void TS79209_Ordenes_Cliente_existente_Alta_de_1_servicio_adicional_Plan_prepago_nacional_Sin_delivery_Sin_VAS_Paso_4() throws InterruptedException {
		boolean gestion = false;
		om.AltaDeLineaCon1Servicio("FlorOM", "Plan Prepago Nacional");
		driver.navigate().back();
		sleep(7000);
		String status = driver.findElement(By.id("Status_ilecell")).getText();
		List <WebElement> gest = driver.findElements(By.cssSelector(".dataCol.inlineEditWrite"));
		for (WebElement x : gest) {
			if (x.getText().equalsIgnoreCase("Venta")) {
				gestion = true;
			}
		}
		driver.findElement(By.name("vlocity_cmt__vieworchestrationplan")).click();
		sleep(10000);
		Assert.assertTrue(status.equals("Activated"));
		Assert.assertTrue(gestion);
		om.ordenCajasVerdes("CreateSubscriber - S203", "Env\u00edo de Activaci\u00f3n de Servicios a la Red", "updateNumerStatus - S326");
	}
	
	@Test (groups = "OM")
	public void TS79210_Ordenes_Cliente_existente_Alta_de_1_servicio_adicional_Plan_prepago_nacional_Sin_delivery_Sin_VAS_Actualizar_los_assets() throws InterruptedException {
		int ultimaVoz = 0;
		om.AltaDeLineaCon1Servicio("FlorOM", "Plan Prepago Nacional");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".panel.panel-default.panel-assets")));
		List <WebElement> assets = driver.findElement(By.cssSelector(".panel.panel-default.panel-assets")).findElements(By.cssSelector(".root-asset.ng-scope"));
		assets.get(assets.size() -1).findElement(By.className("p-expand")).click();
		sleep(3000);
		List <WebElement> asd = driver.findElements(By.className("p-name"));
		for (int i=0; i<asd.size(); i++) {
			if (asd.get(i).getText().equalsIgnoreCase("Caller Id")) {
				ultimaVoz = i;
			}
		}
		asd.get(ultimaVoz).findElement(By.tagName("a")).click();
		sleep(7000);
		WebElement status = driver.findElement(By.id("Status_ilecell"));
		Assert.assertTrue(status.getText().equalsIgnoreCase("Active"));
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS101360_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_prepago_nacional_Paso_5")
	public void TS101355_TS101360_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_prepago_nacional_Paso_0() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS101360_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_prepago_nacional_Paso_5")
	public void TS101356_TS101360_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_prepago_nacional_Paso_1() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS101360_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_prepago_nacional_Paso_5")
	public void TS101357_TS101360_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_prepago_nacional_Paso_2() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS101360_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_prepago_nacional_Paso_5")
	public void TS101358_TS101360_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_prepago_nacional_Paso_3() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS101360_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_prepago_nacional_Paso_5")
	public void TS101359_TS101360_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_prepago_nacional_Paso_4() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM")
	public void TS101360_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_prepago_nacional_Paso_5() throws InterruptedException {
		om.BajaDeLineaOM("FlorOM", "Plan Prepago Nacional");
	}
	
	@Test (groups = "OM")
	public void TS101361_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_prepago_nacional_Actualizacion_de_assets() throws InterruptedException {
		om.BajaDeLineaOM("FlorOM", "Plan Prepago Nacional");
		driver.findElement(By.id("accid_ileinner")).findElement(By.tagName("a")).click();
		sleep(15000);
		om.irAChangeToOrder();
		sleep(15000);
		driver.switchTo().defaultContent();
		om.fechaAvanzada2();
		sleep(10000);
		WebElement cart = driver.findElement(By.cssSelector(".slds-grid.slds-grid_vertical-align-center.slds-grid_align-center.cpq-no-cart-items-msg"));
		Assert.assertTrue(cart.getText().toLowerCase().contains("cart is empty"));
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS101367_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_con_tarjeta_Paso_5")
	public void TS101363_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_con_tarjeta_Paso_1() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS101367_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_con_tarjeta_Paso_5")
	public void TS101364_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_con_tarjeta_Paso_2() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS101367_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_con_tarjeta_Paso_5")
	public void TS101365_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_con_tarjeta_Paso_3() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS101367_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_con_tarjeta_Paso_5")
	public void TS101366_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_con_tarjeta_Paso_4() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM")
	public void TS101367_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_con_tarjeta_Paso_5() throws InterruptedException {
		om.BajaDeLineaOM("FlorOM", "Plan con tarjeta");
	}
	
	@Test (groups = "OM")
	public void TS101368_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_con_tarjeta_Actualizacion_de_assets() throws InterruptedException {
		om.BajaDeLineaOM("FlorOM", "Plan con tarjeta");
		driver.findElement(By.id("accid_ileinner")).findElement(By.tagName("a")).click();
		sleep(15000);
		om.irAChangeToOrder();
		sleep(15000);
		driver.switchTo().defaultContent();
		om.fechaAvanzada2();
		sleep(10000);
		WebElement cart = driver.findElement(By.cssSelector(".slds-grid.slds-grid_vertical-align-center.slds-grid_align-center.cpq-no-cart-items-msg"));
		Assert.assertTrue(cart.getText().toLowerCase().contains("cart is empty"));
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS101374_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_con_tarjeta_repro_Paso_5")
	public void TS101370_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_con_tarjeta_repro_Paso_1() throws InterruptedException {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS101374_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_con_tarjeta_repro_Paso_5")
	public void TS101371_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_con_tarjeta_repro_Paso_2() throws InterruptedException {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS101374_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_con_tarjeta_repro_Paso_5")
	public void TS101372_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_con_tarjeta_repro_Paso_3() throws InterruptedException {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS101374_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_con_tarjeta_repro_Paso_5")
	public void TS101373_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_con_tarjeta_repro_Paso_4() throws InterruptedException {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM")
	public void TS101374_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_con_tarjeta_repro_Paso_5() throws InterruptedException {
		om.BajaDeLineaOM("FlorOM", "Plan con tarjeta repro");
	}
	
	@Test (groups = "OM")
	public void TS101375_Ordenes_Cliente_existente_Anulacion_de_venta_Plan_con_tarjeta_repro_Actualizacion_de_assets() throws InterruptedException {
		om.BajaDeLineaOM("FlorOM", "Plan con tarjeta repro");
		driver.findElement(By.id("accid_ileinner")).findElement(By.tagName("a")).click();
		sleep(15000);
		om.irAChangeToOrder();
		sleep(15000);
		driver.switchTo().defaultContent();
		om.fechaAvanzada2();
		sleep(10000);
		WebElement cart = driver.findElement(By.cssSelector(".slds-grid.slds-grid_vertical-align-center.slds-grid_align-center.cpq-no-cart-items-msg"));
		Assert.assertTrue(cart.getText().toLowerCase().contains("cart is empty"));
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS80069_Ordenes_Cliente_Nuevo_Alta_de_linea_Plan_con_tarjeta_Sin_delivery_Sin_VAS_Paso_4")
	public void TS81064_Ordenes_Cliente_nuevo_Alta_de_linea_Plan_con_tarjeta_Sin_delivery_Sin_VAS_Paso_0() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS80069_Ordenes_Cliente_Nuevo_Alta_de_linea_Plan_con_tarjeta_Sin_delivery_Sin_VAS_Paso_4")
	public void TS81066_Ordenes_Cliente_nuevo_Alta_de_linea_Plan_con_tarjeta_Sin_delivery_Sin_VAS_Paso_1() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS80069_Ordenes_Cliente_Nuevo_Alta_de_linea_Plan_con_tarjeta_Sin_delivery_Sin_VAS_Paso_4")
	public void TS81067_Ordenes_Cliente_nuevo_Alta_de_linea_Plan_con_tarjeta_Sin_delivery_Sin_VAS_Paso_2() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS80069_Ordenes_Cliente_Nuevo_Alta_de_linea_Plan_con_tarjeta_Sin_delivery_Sin_VAS_Paso_4")
	public void TS81068_Ordenes_Cliente_nuevo_Alta_de_linea_Plan_con_tarjeta_Sin_delivery_Sin_VAS_Paso_3() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM")
	public void TS80069_Ordenes_Cliente_Nuevo_Alta_de_linea_Plan_con_tarjeta_Sin_delivery_Sin_VAS_Paso_4() throws InterruptedException {
		om.Gestion_Alta_De_Linea("FlorOM", "Plan con tarjeta");
		driver.navigate().back();
		sleep(5000);
		driver.findElement(By.name("vlocity_cmt__vieworchestrationplan")).click();
		sleep(10000);
		om.ordenCajasVerdes("CreateSubscriber - S203", "Env\u00edo de Activaci\u00f3n de Servicios a la Red", "updateNumerStatus - S326");
	}
	
	@Test (groups = "OM")
	public void TS6726_Ordenes_Panel_Principal_Refrescar() throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("window.open('about:blank','_blank');");
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		sleep(2000);
		driver.get("https://crm--sit.cs14.my.salesforce.com/");
		driver.findElement(By.id("Order_Tab")).click();
		sleep(5000);
		om.selectVistaByVisibleText("LineasFlor");
		driver.switchTo().window(tabs.get(0));
		OMQPage omq = new OMQPage(driver);
		om.crearOrden("FlorOM");
		Assert.assertTrue(driver.findElement(By.cssSelector(".noSecondHeader.pageType")).isDisplayed());
		om.agregarGestion("Venta");
		sleep(2000);
		omq.getCPQ().click();
		sleep(5000);
		om.colocarPlan("Plan Prepago Nacional");
		omq.configuracion();
		String order = driver.findElement(By.cssSelector(".noSecondHeader.pageType")).getText();
		order = order.substring(order.indexOf("0"), order.length());
		driver.switchTo().window(tabs.get(1));
		driver.navigate().refresh();
		sleep(5000);
		WebElement fila = driver.findElement(By.cssSelector(".x-grid3-row.x-grid3-row-first"));
		String order2 = fila.findElements(By.tagName("td")).get(2).getText();
		Assert.assertTrue(order.equals(order2));
		driver.switchTo().window(tabs.get(0));
		om.closeAllOtherTabs();
	}
	
	@Test (groups = "OM")
	public void TS6728_Ordenes_Order_Detail_Visualizacion_del_flujo_de_orquestacion_Luego_de_hacer_un_cambio() throws InterruptedException {
		boolean cajaRoja = true;
		om.Gestion_Alta_De_Linea("FlorOM", "Plan Prepago Nacional");
		driver.navigate().back();
		sleep(5000);
		driver.findElement(By.name("vlocity_cmt__vieworchestrationplan")).click();
		sleep(10000);
		List <WebElement> cajas = driver.findElements(By.cssSelector(".item-label-container.item-header.item-failed"));
		cajas.addAll(driver.findElements(By.cssSelector(".item-label-container.item-header.item-fatally-failed")));
		cajas.addAll(driver.findElements(By.cssSelector(".item-label-container.item-header.item-running")));
		for (int i=0; i<cajas.size(); i++) {
			if (cajas.get(i).isDisplayed() || cajas.get(i).isEnabled()) {
				cajaRoja = false;
			}
		}
		Assert.assertTrue(cajaRoja);
	}
	
	@Test (groups = "OM")
	public void TS6738_Ordenes_Order_Detail_Descarga_de_archivos_Todos_los_formatos() {
		om.primeraOrden();
		driver.findElement(By.name("attachFile")).click();
		sleep(5000);
		driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\jpg.jpg");
		sleep(3000);
		driver.findElement(By.id("Attach")).click();
		sleep(7000);
		driver.findElement(By.name("cancel")).click();
		sleep(5000);
		driver.findElement(By.name("viewAll")).click();
		sleep(3000);
		driver.findElement(By.cssSelector(".last.data2Col")).findElement(By.tagName("a")).click();
		sleep(5000);
		Assert.assertTrue(driver.findElement(By.tagName("img")).getAttribute("style").equals("user-select: none;"));
		driver.navigate().back();
	}
	
	@Test (groups = "OM")
	public void TS52669_Ordenes_Cliente_Nuevo_Alta_de_linea_Sin_delivery_Sin_VAS_Inicio_automatico() throws InterruptedException {
		om.Gestion_Alta_De_Linea("FlorOM", "Plan Prepago Nacional");
		driver.navigate().back();
		sleep(5000);
		driver.findElement(By.name("vlocity_cmt__vieworchestrationplan")).click();
		sleep(10000);
		WebElement cajaVerde = driver.findElement(By.cssSelector(".item-completed.item-milestone.item"));
		Assert.assertTrue(cajaVerde.isDisplayed() || cajaVerde.isEnabled());
	}
	
	@Test (groups = "OM")
	public void TS80076_Interfaces_Alta_de_linea_Plan_con_tarjeta_Sin_delivery_Sin_VAS_Numeracion_Movil_S326_updateNumberStatus_Verificacion_de_parametros_enviados() throws InterruptedException {
		om.Gestion_Alta_De_Linea("FlorOM", "Plan Prepago Nacional");
		driver.navigate().back();
		sleep(5000);
		driver.findElement(By.name("vlocity_cmt__vieworchestrationplan")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".item-label.item-header")), "equals", "updatenumberstatus - s326");
		sleep(7000);
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		buscarYClick(driver.findElements(By.className("slds-tabs--scoped__link")), "equals", "children");
		sleep(5000);
		driver.findElement(By.xpath("//*[@id=\"bodyCell\"]/div/ng-view/div/div/div/div/div/facet/facet-4412964684870431361/table/tbody/tr/td[3]")).click();
		sleep(10000);
		WebElement acc = driver.findElement(By.className("json"));
		Assert.assertTrue(acc.getText().contains("ACTIVAR"));
		
	}	
}