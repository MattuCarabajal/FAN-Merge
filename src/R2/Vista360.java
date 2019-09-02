package R2;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import Pages.CBS;
import Pages.CustomerCare;
import Pages.Marketing;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class Vista360 extends TestBase {

	private WebDriver driver;
	private CustomerCare cc;
	private Marketing mk;
	private CBS_Mattu cbsm;
	private CBS cbs;
	private LoginFw log;
	private GestionDeClientes_Fw ges;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	//@BeforeClass (groups = "PerfilOficina")
	public void initSIT() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		log = new LoginFw(driver);
		log.LoginSit();
		//ges.irAConsolaFAN();
	}
		
	@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		log = new LoginFw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
	}
	
		
	//@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		cbsm = new CBS_Mattu();
		cbs = new CBS();
		log = new LoginFw(driver);
		log.loginTelefonico();
		ges.irAConsolaFAN();
	}
	
	//@BeforeClass (groups = "PerfilAgente")
	public void initAgente() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		cbsm = new CBS_Mattu();
		cbs = new CBS();
		log = new LoginFw(driver);
		log.loginAgente();
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
		@Test (groups = {"PerfilOficina", "R1"}, dataProvider = "CuentaVista360")
		public void TS134349_CRM_Movil_Pre_Vista_360_Consulta_de_gestiones_Gestiones_abiertas_Fecha_inicio_Crm_OC(String sDNI, String sLinea, String sAccountKey) throws ParseException  {
			imagen="TS134349";
			ges.BuscarCuenta("DNI", sDNI);
			ges.irAGestionEnCard("Gestiones");
			cambioDeFrame(driver,By.id("text-input-id-1"),0);
			driver.findElement(By.id("text-input-id-1")).click();
			List <WebElement> semanas = driver.findElement(By.cssSelector("[class = 'slds-datepicker__month nds-datepicker__month']")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			for(WebElement sm : semanas){
				for(WebElement fi :sm.findElements(By.tagName("td"))){
					//if(fi.getText().equals("17"))
					if(fi.getAttribute("value").equals("17"))
						fi.click();
					}
				}
			driver.findElement(By.id("text-input-id-2")).click();
			List <WebElement> semanasf = driver.findElement(By.cssSelector("[class = 'slds-datepicker__month nds-datepicker__month']")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			for(WebElement sf : semanasf){
				for(WebElement fi :sf.findElements(By.tagName("td"))){
					if(fi.getAttribute("value").equals("25"))
						fi.click();
					}
				}
			
			driver.findElement(By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small secondaryFont']")).click();
			
			}
			
		
			
	
}

