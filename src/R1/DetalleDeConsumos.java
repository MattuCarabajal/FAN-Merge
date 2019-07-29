package R1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.TestBase;

public class DetalleDeConsumos extends TestBase {

	private WebDriver driver;
	private LoginFw log;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private GestionDeClientes_Fw ges;
	String detalles;
	
	
	@BeforeClass (groups= "PerfilOficina")
	public void initSIT() {
		driver = setConexion.setupEze();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.LoginSit();
		ges.irAConsolaFAN();
	}
	
	//@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();	
	}
		
	//@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() {
		driver = setConexion.setupEze();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginTelefonico();
		ges.irAConsolaFAN();	
	}
	
	@BeforeMethod (alwaysRun = true)
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
		tomarCaptura(driver,imagen);
		sleep(2000);
	}

	//@AfterClass (alwaysRun = true)
	public void quit() {
		driver.quit();
		sleep(5000);
	}
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"PerfilOficina", "R1"}, dataProvider = "DetalleDeConsumoApro")
	public void TS171828_CRM_Movil_Mix_Detalle_de_consumo_Consulta_detalle_de_consumo_SMS_Crm_OC(String sDNI, String sLinea) {
		imagen = "TS171828";
		ges.BuscarCuenta("DNI", sDNI);;
		ges.irAGestionEnCardPorNumeroDeLinea("Detalles de Consumo", sLinea);
		cambioDeFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter"), 0);
		driver.findElement(By.id("text-input-03")).click();
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[@class= 'slds-dropdown slds-dropdown--left resize-dropdowns']//li"), 0));
		driver.findElement(By.xpath("//div[@class= 'slds-dropdown slds-dropdown--left resize-dropdowns']//li//*[contains(text(),'Plan con Tarjeta Repro - "+sLinea+"')]")).click();
		driver.findElement(By.id("text-input-02")).click();
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[@class= 'slds-dropdown slds-dropdown--left']//li"), 0));
		driver.findElement(By.xpath("//*[text() = 'Los \u00faltimos 15 d\u00edas']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".slds-text-heading--small"),0));
		clickBy(driver, By.xpath("//label[contains (text(), 'Filtros avanzados')]"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-grid slds-wrap slds-card slds-p-around--medium']")));
		WebElement desplegable = driver.findElements(By.cssSelector("[class='slds-grid slds-wrap slds-card slds-p-around--medium'] [class='slds-p-horizontal--small slds-size--1-of-1 slds-medium-size--4-of-8 slds-large-size--2-of-8'] [class = 'slds-form-element']")).get(0);
		desplegable.click();
		driver.findElement(By.xpath("//div[@class= 'slds-dropdown slds-dropdown--left']//li//*[contains(text(),'SMS')]")).click();
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-table slds-table_striped slds-table--bordered slds-table--resizable-cols slds-table--fixed-layout via-slds-table-pinned-header'] tbody tr"), 0));
		boolean b = false;
		WebElement lista = driver.findElement(By.cssSelector("[class='slds-table slds-table_striped slds-table--bordered slds-table--resizable-cols slds-table--fixed-layout via-slds-table-pinned-header'] tbody"));
		List <WebElement> consumos = lista.findElements(By.tagName("tr"));
		System.out.println(lista.getText());
		for(WebElement x : consumos) {
			if(x.getText().contains("SMS")) {
				b = true;
				break;
			}
		}
		Assert.assertTrue(b);
		List<WebElement> filas = driver.findElements(By.cssSelector("[class='slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-top--medium'] tbody tr"));
		filas.get(0).click();
		filas = driver.findElements(By.cssSelector("[class='slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-top--medium'] tbody tr"));
		List<WebElement> detalles = filas.get(1).findElements(By.cssSelector(".slds-text-heading--label"));
		List<String> detallesReferencia = new ArrayList<String>(Arrays.asList("FECHA DE INICIO", "FECHA DE FIN", "PRODUCTO", "IMPORTE", "ORIGEN/DESTINO"));
		for (int i = 0; i < detalles.size(); i++) {
			Assert.assertTrue(detalles.get(i).getText().equals(detallesReferencia.get(i)));
			
		}
		String origen = driver.findElements(By.cssSelector("[class = 'slds-p-top--medium'] table [class = 'ng-pristine ng-untouched ng-valid ng-empty'] td")).get(2).getText();
		String producto = driver.findElements(By.cssSelector("[class = 'slds-p-top--medium'] table [class = 'ng-pristine ng-untouched ng-valid ng-empty'] td")).get(4).getText();
		List<WebElement> match = driver.findElements(By.cssSelector("[class = 'nghided'] [class = 'slds-grid'] [class = 'slds-text-color--default']"));
		for(int i = 0; i< match.size(); i++) {
			System.out.println(match.get(i).getText());
			Assert.assertTrue(match.get(i).getText().matches("^\\d{2}[/]\\d{2}[/]\\d{4}\\s\\d{2}[:]\\d{2}[:]\\d{2}") || match.get(i).getText().equals(origen) || match.get(i).getText().matches("[-][$](\\d+\\,\\d{1,2})") || match.get(i).getText().matches(producto));
		}
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"PerfilTelefonico", "R1"}, dataProvider = "DetalleDeConsumoApro")
	public void TS171834_CRM_Movil_Mix_Detalle_de_consumo_Consulta_detalle_de_consumo_Datos_Crm_Telefonico(String sDNI, String sLinea) {
		imagen = "TS171834";
		ges.BuscarCuenta("DNI", sDNI);;
		ges.irAGestionEnCardPorNumeroDeLinea("Detalles de Consumo", sLinea);
		cambioDeFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter"), 0);
		driver.findElement(By.id("text-input-03")).click();
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[@class= 'slds-dropdown slds-dropdown--left resize-dropdowns']//li"), 0));
		driver.findElement(By.xpath("//div[@class= 'slds-dropdown slds-dropdown--left resize-dropdowns']//li//*[contains(text(),'Plan con Tarjeta Repro - "+sLinea+"')]")).click();
		driver.findElement(By.id("text-input-02")).click();
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[@class= 'slds-dropdown slds-dropdown--left']//li"), 0));
		driver.findElement(By.xpath("//*[text() = 'Los \u00faltimos 15 d\u00edas']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".slds-text-heading--small"),0));
		clickBy(driver, By.xpath("//label[contains (text(), 'Filtros avanzados')]"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-grid slds-wrap slds-card slds-p-around--medium']")));
		WebElement desplegable = driver.findElements(By.cssSelector("[class='slds-grid slds-wrap slds-card slds-p-around--medium'] [class='slds-p-horizontal--small slds-size--1-of-1 slds-medium-size--4-of-8 slds-large-size--2-of-8'] [class = 'slds-form-element']")).get(0);
		desplegable.click();
		driver.findElement(By.xpath("//div[@class= 'slds-dropdown slds-dropdown--left']//li//*[contains(text(),'Servicio de datos')]")).click();
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-table slds-table_striped slds-table--bordered slds-table--resizable-cols slds-table--fixed-layout via-slds-table-pinned-header'] tbody tr"), 0));
		boolean b = false;
		WebElement lista = driver.findElement(By.cssSelector("[class='slds-table slds-table_striped slds-table--bordered slds-table--resizable-cols slds-table--fixed-layout via-slds-table-pinned-header'] tbody"));
		List <WebElement> consumos = lista.findElements(By.tagName("tr"));
		System.out.println(lista.getText());
		for(WebElement x : consumos) {
			if(x.getText().contains("Servicio de datos")) {
				b = true;
				break;
			}
		}
		Assert.assertTrue(b);
		List<WebElement> filas = driver.findElements(By.cssSelector("[class='slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-top--medium'] tbody tr"));
		filas.get(0).click();
		filas = driver.findElements(By.cssSelector("[class='slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-top--medium'] tbody tr"));
		List<WebElement> detalles = filas.get(1).findElements(By.cssSelector(".slds-text-heading--label"));
		List<String> detallesReferencia = new ArrayList<String>(Arrays.asList("FECHA DE INICIO", "FECHA DE FIN", "PRODUCTO", "IMPORTE", "ORIGEN/DESTINO"));
		for (int i = 0; i < detalles.size(); i++) {
			Assert.assertTrue(detalles.get(i).getText().equals(detallesReferencia.get(i)));
		}
		String origen = driver.findElements(By.cssSelector("[class = 'slds-p-top--medium'] table [class = 'ng-pristine ng-untouched ng-valid ng-empty'] td")).get(2).getText();
		String producto = driver.findElements(By.cssSelector("[class = 'slds-p-top--medium'] table [class = 'ng-pristine ng-untouched ng-valid ng-empty'] td")).get(4).getText();
		List<WebElement> match = driver.findElements(By.cssSelector("[class = 'nghided'] [class = 'slds-grid'] [class = 'slds-text-color--default']"));
		for(int i = 0; i< match.size(); i++) {
			System.out.println(match.get(i).getText());
			Assert.assertTrue(match.get(i).getText().matches("^\\d{2}[/]\\d{2}[/]\\d{4}\\s\\d{2}[:]\\d{2}[:]\\d{2}") || match.get(i).getText().equals(origen) || match.get(i).getText().matches("[-][$](\\d+\\,\\d{1,2})") || match.get(i).getText().matches(producto));
		}
	}
	
	@Test (groups = {"PerfilTelefonico", "R1"}, dataProvider = "DetalleDeConsumoApro")
	public void TS171835_CRM_Movil_Mix_Detalle_de_consumo_Consulta_detalle_de_consumo_Voz_Crm_Telefonico(String sDNI, String sLinea) {
		imagen = "TS171835";
		ges.BuscarCuenta("DNI", sDNI);;
		ges.irAGestionEnCardPorNumeroDeLinea("Detalles de Consumo", sLinea);
		cambioDeFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter"), 0);
		driver.findElement(By.id("text-input-03")).click();
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[@class= 'slds-dropdown slds-dropdown--left resize-dropdowns']//li"), 0));
		driver.findElement(By.xpath("//div[@class= 'slds-dropdown slds-dropdown--left resize-dropdowns']//li//*[contains(text(),'Plan con Tarjeta Repro - "+sLinea+"')]")).click();
		driver.findElement(By.id("text-input-02")).click();
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[@class= 'slds-dropdown slds-dropdown--left']//li"), 0));
		driver.findElement(By.xpath("//*[text() = 'Los \u00faltimos 15 d\u00edas']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".slds-text-heading--small"),0));
		clickBy(driver, By.xpath("//label[contains (text(), 'Filtros avanzados')]"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-grid slds-wrap slds-card slds-p-around--medium']")));
		WebElement desplegable = driver.findElements(By.cssSelector("[class='slds-grid slds-wrap slds-card slds-p-around--medium'] [class='slds-p-horizontal--small slds-size--1-of-1 slds-medium-size--4-of-8 slds-large-size--2-of-8'] [class = 'slds-form-element']")).get(0);
		desplegable.click();
		driver.findElement(By.xpath("//div[@class= 'slds-dropdown slds-dropdown--left']//li//*[contains(text(),'Llamada de voz')]")).click();
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-table slds-table_striped slds-table--bordered slds-table--resizable-cols slds-table--fixed-layout via-slds-table-pinned-header'] tbody tr"), 0));
		boolean b = false;
		WebElement lista = driver.findElement(By.cssSelector("[class='slds-table slds-table_striped slds-table--bordered slds-table--resizable-cols slds-table--fixed-layout via-slds-table-pinned-header'] tbody"));
		List <WebElement> consumos = lista.findElements(By.tagName("tr"));
		System.out.println(lista.getText());
		for(WebElement x : consumos) {
			if(x.getText().contains("Llamada de voz")) {
				b = true;
				break;
			}
		}
		Assert.assertTrue(b);
		List<WebElement> filas = driver.findElements(By.cssSelector("[class='slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-top--medium'] tbody tr"));
		filas.get(0).click();
		filas = driver.findElements(By.cssSelector("[class='slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-top--medium'] tbody tr"));
		List<WebElement> detalles = filas.get(1).findElements(By.cssSelector(".slds-text-heading--label"));
		List<String> detallesReferencia = new ArrayList<String>(Arrays.asList("FECHA DE INICIO", "FECHA DE FIN", "PRODUCTO", "IMPORTE", "ORIGEN/DESTINO"));
		for (int i = 0; i < detalles.size(); i++) {
			Assert.assertTrue(detalles.get(i).getText().equals(detallesReferencia.get(i)));
		}
		String origen = driver.findElements(By.cssSelector("[class = 'slds-p-top--medium'] table [class = 'ng-pristine ng-untouched ng-valid ng-empty'] td")).get(2).getText();
		String producto = driver.findElements(By.cssSelector("[class = 'slds-p-top--medium'] table [class = 'ng-pristine ng-untouched ng-valid ng-empty'] td")).get(4).getText();
		List<WebElement> match = driver.findElements(By.cssSelector("[class = 'nghided'] [class = 'slds-grid'] [class = 'slds-text-color--default']"));
		for(int i = 0; i< match.size(); i++) {
			System.out.println(match.get(i).getText());
			Assert.assertTrue(match.get(i).getText().matches("^\\d{2}[/]\\d{2}[/]\\d{4}\\s\\d{2}[:]\\d{2}[:]\\d{2}") || match.get(i).getText().equals(origen) || match.get(i).getText().matches("[-][$](\\d+\\,\\d{1,2})") || match.get(i).getText().matches(producto));
		}
	}
}