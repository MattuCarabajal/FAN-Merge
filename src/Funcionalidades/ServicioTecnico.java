package Funcionalidades;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.Accounts;
import Pages.BasePage;
import Pages.CustomerCare;
import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import Tests.TestBase;

public class ServicioTecnico extends TestBase {

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	@BeforeClass (alwaysRun = true)
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		loginOOCC(driver);
		sleep(15000);
		cc.irAConsolaFAN();	
		driver.switchTo().defaultContent();
		sleep(6000);
	}
	
	//@BeforeClass (alwaysRun = true)
		public void initAgente() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		loginAgente(driver);
		sleep(15000);
		cc.irAConsolaFAN();	
		driver.switchTo().defaultContent();
		sleep(6000);
	}
	
	@BeforeMethod(alwaysRun=true)
	public void setup() throws Exception {
		detalles = null;
		GestionDeClientes_Fw ges = new GestionDeClientes_Fw(driver);
		ges.selectMenuIzq("Inicio");
		ges.cerrarPestaniaGestion(driver);
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
	
	@Test (groups = {"GestionesPerfilOficina", "ServicioTecnico","E2E", "Ciclo4"}, dataProvider = "serviciotecnicoC")
	public void TS121362_CRM_Movil_REPRO_Servicio_Tecnico_Realiza_configuraciones_de_equipos_Validacion_de_IMEI_Ok_Sin_Garantia_Sin_Muleto_Reparar_ahora_No_acepta_presupuesto_ofCom(String sDNI, String sIMEI, String sEmail, String sLinea, String sOpcion, String sOperacion, String sSintoma) throws InterruptedException {
		imagen = "TS121362";
		detalles = null;
		detalles = imagen + " -ServicioTecnico - DNI: " + sDNI;
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(10000);
		searchAndClick(driver, "Servicio T\u00e9cnico");
		sleep(8000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".imgItemContainer.ng-scope")));
		List<WebElement> clickOnButtons= driver.findElements(By.xpath(".//*[@class='borderOverlay']"));
		clickOnButtons.get(1).click();
		WebElement IMEI = driver.findElement(By.id("ImeiCode"));
		IMEI.click();
		IMEI.sendKeys(sIMEI);
		driver.findElement(By.id("ImeiInput_nextBtn")).click();
		sleep(15000);
		try {
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver,By.id("PopulateRepairs")));
			driver.findElement(By.cssSelector(".vlc-slds-remote-action__content.ng-scope")).findElement(By.xpath("//*[@id=\"PopulateRepairs\"]/div/p[3]"));
			List<WebElement> butt=driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope"));
			butt.get(1).click();
		} catch (Exception e) {
			Assert.assertTrue(false);
		}
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.className("vlc-control-wrapper")).getLocation().y+" )");
		driver.findElement(By.id("AlternativeEmail")).click();
		driver.findElement(By.id("AlternativeEmail")).sendKeys(sEmail);
		driver.findElement(By.id("AlternativePhone")).click();
		driver.findElement(By.id("AlternativePhone")).sendKeys(sLinea);
		sleep(8000);
		sOpcion=sOpcion.toLowerCase();
		boolean flag=false;
		for(WebElement opt:driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope"))) {
			if(opt.getText().toLowerCase().contains(sOpcion)) {
				opt.click();
				flag=true;
				break;
			}
		}
		if(!flag) System.out.println("Opcion no disponible");
		sleep(8000);
		buscarYClick(driver.findElements(By.id("ClientInformation_nextBtn")),"equals", "continuar");
		sleep(8000);
		selectByText(driver.findElement(By.id("SelectOperationType")), sOperacion);
		sleep(8000);
		driver.findElement(By.className("vlc-control-wrapper")).click();
		List<WebElement> sint = driver.findElement(By.cssSelector(".slds-list--vertical.vlc-slds-list--vertical")).findElements(By.tagName("li"));
		for(WebElement sintoma : sint) {
			if(sintoma.getText().contains(sSintoma));
			sintoma.click();
			break;
		}
		sleep(8000);
		buscarYClick(driver.findElements(By.id("SymptomExplanation_nextBtn")), "equals", "continuar");
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ServicioTecnico","E2E", "Ciclo4"}, dataProvider = "serviciotecnicoR")
	public void TS121372_CRM_Movil_REPRO_Servicio_Tecnico_Realiza_reparaciones_de_equipos_Busqueda_de_cliente_Reparacion_Sin_Muleto_Equipo_en_destruccion_total_Con_seguro_de_proteccion_total_No_se_pudo_realizar_la_reparacion_OfCom(String sDNI, String sIMEI, String sEmail, String sLinea, String sOpcion, String sOperacion, String sSintoma) throws InterruptedException {
		imagen = "TS121372";
		detalles = null;
		detalles = imagen + " -ServicioTecnico - DNI: " + sDNI+" - Linea: "+sLinea;
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(10000);
		searchAndClick(driver, "Servicio T\u00e9cnico");
		sleep(8000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".imgItemContainer.ng-scope")));
		List<WebElement> clickOnButtons= driver.findElements(By.xpath(".//*[@class='borderOverlay']"));
		clickOnButtons.get(1).click();
		WebElement IMEI = driver.findElement(By.id("ImeiCode"));
		IMEI.click();
		IMEI.sendKeys(sIMEI);
		driver.findElement(By.id("ImeiInput_nextBtn")).click();
		sleep(15000);
		try {
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver,By.id("PopulateRepairs")));
			driver.findElement(By.cssSelector(".vlc-slds-remote-action__content.ng-scope")).findElement(By.xpath("//*[@id=\"PopulateRepairs\"]/div/p[3]"));
			List<WebElement> butt=driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope"));
			butt.get(1).click();
		} catch (Exception e) {
		}
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.className("vlc-control-wrapper")).getLocation().y+" )");
		driver.findElement(By.id("AlternativeEmail")).click();
		driver.findElement(By.id("AlternativeEmail")).sendKeys(sEmail);
		driver.findElement(By.id("AlternativePhone")).click();
		driver.findElement(By.id("AlternativePhone")).sendKeys(sLinea);
		sleep(8000);
		sOpcion=sOpcion.toLowerCase();
		boolean flag=false;
		for(WebElement opt:driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope"))) {
			if(opt.getText().toLowerCase().contains(sOpcion)) {
				opt.click();
				flag=true;
				break;
			}
		}
		if(!flag) System.out.println("Opcion no disponible");
		sleep(5000);
		buscarYClick(driver.findElements(By.id("ClientInformation_nextBtn")),"equals", "continuar");
		sleep(8000);
		driver.findElement(By.cssSelector(".slds-form-element__control.slds-input-has-icon.slds-has-input-has-icon--right"));
		selectByText(driver.findElement(By.id("SelectOperationType")), sOperacion);
		sleep(8000);
		driver.findElement(By.id("SelectSymptomType")).click();
		sleep(5000);
		List<WebElement> sint = driver.findElement(By.cssSelector(".slds-list--vertical.vlc-slds-list--vertical")).findElements(By.tagName("li"));
		for(WebElement sintoma : sint) {
			System.out.println(sintoma.getText());
			if(sintoma.getText().equalsIgnoreCase(sSintoma)) {
				System.out.println("entreeeeeee");
				sintoma.click();
				break;
			}
		}
		sleep(8000);
		buscarYClick(driver.findElements(By.id("SymptomExplanation_nextBtn")), "equals", "continuar");
		sleep(8000);
		boolean precio = false;
		List<WebElement> soluciones = driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-table--cell-buffer.techCare-PriceListSelection.ng-scope")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement sol: soluciones) {
			if(sol.getText().contains("$0")) {
				sol.findElement(By.tagName("td")).findElement(By.tagName("td")).getText();
				System.out.println(sol);
				precio = true;
			}
		}			
		for(WebElement sol:soluciones) {
			if(sol.getText().contains("Irreparable")) {
				System.out.println(sol.getText());
				sleep(8000);
				sol.findElement(By.tagName("td")).findElement(By.className("slds-checkbox")).click();
			}
		}
		sleep(8000);
		buscarYClick(driver.findElements(By.id("1stSolutionList_nextBtn")), "equals", "continuar");
		List<WebElement> costo = driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-table--cell-bufferslds-m-bottom--x-large.ta-table-list.ng-scope")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement cost:costo) {
			if(cost.getText().contains("$0"));{
				System.out.println(cost.getText());
				precio = true;
				Assert.assertTrue(precio);
				}
			}
		
		sleep(8000);
		List<WebElement> presup = driver.findElement(By.className("slds-form-element__control")).findElement(By.tagName("label")).findElements(By.tagName("div"));
		for(WebElement opt:presup) {
			if(opt.getText().toLowerCase().contains("Si")) {
				System.out.println(opt.getText());
				opt.findElement(By.tagName("label")).findElement(By.id("RadioBudgetAcceptance")).click();
				break;
			}
		}
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.TechCare-createOrder-Btn")), "equals", "crear orden");		
	}
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test (groups = {"GestionesPerfilAgente", "ServicioTecnico","E2E", "Ciclo4"}, dataProvider = "serviciotecnico")
	public void TS121364_CRM_Movil_REPRO_Servicio_Tecnico_Realiza_reparaciones_de_equipos_Validacion_de_IMEI_Ok_Con_Garantia_Derivado_a_Servicio_Tecnico_Sin_Muleto_Terminal_reparada_y_entregada_Agente(String sDNI, String sLinea, String sIMEI) throws InterruptedException {
		imagen = "TS121364";
		detalles = null;
		detalles = imagen + " -ServicioTecnico: " + sDNI;
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(10000);
		searchAndClick(driver, "Servicio T\u00e9cnico");
		sleep(8000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".imgItemContainer.ng-scope")));
		List<WebElement> clickOnButtons= driver.findElements(By.xpath(".//*[@class='borderOverlay']"));
		clickOnButtons.get(1).click();
		WebElement IMEI = driver.findElement(By.id("ImeiCode"));
		IMEI.click();
		IMEI.sendKeys(sIMEI);
		driver.findElement(By.id("ImeiInput_nextBtn")).click();
		Assert.assertTrue(false); 
	}
	
	@Test (groups = {"GestionesPerfilAgente", "ServicioTecnico","E2E", "Ciclo4"}, dataProvider = "serviciotecnico")
	public void TS121367_CRM_Movil_REPRO_Servicio_Tecnico_Realiza_configuraciones_de_equipos_Validacion_de_IMEI_Ok_Configuracion_Con_Garantia_Sin_Muleto_Reparar_ahora_Agente(String sDNI, String sLinea, String sIMEI, String sEmail, String sOpcion, String sSintoma) throws InterruptedException {
		imagen = "TS121367";
		detalles = null;
		detalles = imagen + " -ServicioTecnico: " + sDNI;
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		searchAndClick(driver, "Servicio T\u00e9cnico");
		sleep(8000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".imgItemContainer.ng-scope")));
		List<WebElement> clickOnButtons= driver.findElements(By.xpath(".//*[@class='borderOverlay']"));
		clickOnButtons.get(1).click();
		WebElement IMEI = driver.findElement(By.id("ImeiCode"));
		IMEI.click();
		IMEI.sendKeys(sIMEI);driver.findElement(By.id("ImeiInput_nextBtn")).click();
		sleep(15000);
		try {
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver,By.id("PopulateRepairs")));
			driver.findElement(By.cssSelector(".vlc-slds-remote-action__content.ng-scope")).findElement(By.xpath("//*[@id=\"PopulateRepairs\"]/div/p[3]"));
			List<WebElement> butt=driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope"));
			butt.get(1).click();
		}catch (Exception e) {}
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.className("vlc-control-wrapper")).getLocation().y+" )");
		driver.findElement(By.id("AlternativeEmail")).click();
		driver.findElement(By.id("AlternativeEmail")).sendKeys(sEmail);
		driver.findElement(By.id("AlternativePhone")).click();
		driver.findElement(By.id("AlternativePhone")).sendKeys(sLinea);
		sleep(8000);
		sOpcion=sOpcion.toLowerCase();
		boolean flag=false;
		for(WebElement opt:driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope"))) {
			if(opt.getText().toLowerCase().contains(sOpcion)) {
				opt.click();
				flag=true;
				break;
			}
		}
		if(!flag) System.out.println("Opcion no disponible");
		sleep(8000);
		buscarYClick(driver.findElements(By.id("ClientInformation_nextBtn")),"equals", "continuar");
		sleep(8000);
		selectByText(driver.findElement(By.id("SelectOperationType")), sOpcion);
		sleep(8000);
		driver.findElement(By.className("vlc-control-wrapper")).click();
		List<WebElement> sint = driver.findElement(By.cssSelector(".slds-list--vertical.vlc-slds-list--vertical")).findElements(By.tagName("li"));
		for(WebElement sintoma : sint) {
			if(sintoma.getText().contains(sSintoma));
			sintoma.click();
			break;
		}
		sleep(8000);
		buscarYClick(driver.findElements(By.id("SymptomExplanation_nextBtn")), "equals", "continuar");
	}
}