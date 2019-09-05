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
		@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "CuentaVista360")
		public void TS134349_CRM_Movil_Pre_Vista_360_Consulta_de_gestiones_Gestiones_abiertas_Fecha_inicio_Crm_OC(String sDNI, String sLinea, String sAccountKey) throws ParseException  {
			imagen="TS134349";
			ges.BuscarCuenta("DNI", sDNI);
			ges.irAGestionEnCard("Gestiones");
			cambioDeFrame(driver, By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small secondaryFont']"), 0);
			driver.findElement(By.id("text-input-id-1")).click();
			driver.findElement(By.xpath("//*[text() = '17']")).click();
			driver.findElement(By.id("text-input-id-3")).click();
			driver.findElement(By.xpath("//*[text() = '3']")).click();
			driver.findElement(By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small secondaryFont']")).click();
			
			}
			
		
		@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "CuentaVista360")
		public void TS134369_CRM_Movil_Pre_Vista_360_Consulta_de_gestiones_Gestiones_Cerradas_Informacion_brindada_Crm_OC(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS134369";
			boolean order = false, estado = false;
			ges.BuscarCuenta("DNI", sDNI);
			cambioDeFrame(driver, By.cssSelector("[class='console-card active']"), 0);
			ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='console-card active']")));
			driver.findElement(By.xpath("//*[@class='console-card active']//*[@class='card-info']//*[@class='actions']//span[text()='Gestiones']")).click();
			cambioDeFrame(driver, By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small secondaryFont']"), 0);
			ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small secondaryFont']")));		
			driver.findElement(By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small secondaryFont']")).click();
			ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-wrap slds-card slds-m-bottom--small  slds-m-around--medium']"), 0)); //tbody tr
			List<WebElement> orderTabla = driver.findElements(By.cssSelector("[class='slds-grid slds-wrap slds-card slds-m-bottom--small  slds-m-around--medium'] tbody tr td:nth-child(2)"));
			List<WebElement> estadoTabla = driver.findElements(By.cssSelector("[class='slds-grid slds-wrap slds-card slds-m-bottom--small  slds-m-around--medium'] tbody tr td:nth-child(5)"));
			for (int i=0; i<orderTabla.size(); i++) {
				if (orderTabla.get(i).getText().contains("Order"))
					order = true;
			}
			for (int i=0; i<estadoTabla.size(); i++) {
				if (estadoTabla.get(i).getText().contains("Activada") || estadoTabla.get(i).getText().contains("Iniciada"))
					estado = true;
			}
			Assert.assertTrue(order && estado);
		}
	
		@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS134370_CRM_Movil_Pre_Vista_360_Consulta_de_gestiones_Gestiones_Crm_OC(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS134370";
		}
		
		@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS134371_CRM_Movil_Pre_Vista_360_Consulta_de_gestiones_Gestiones_abiertas_Estado_de_la_gestion_Crm_OC(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS134371";
		}
		
				
		@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS134380_CRM_Movil_Pre_Vista_360_Mis_Servicios_Visualizacion_del_estado_de_los_Productos_activos_Crm_OC(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS134380";
		}
		
		@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS134495_CRM_Movil_Pre_Vista_360_Distribucion_de_paneles_Informacion_del_cliente_Crm_OC(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS134495";
		}
		

		@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS134496_CRM_Movil_Pre_Vista_360_Distribucion_de_paneles_Perfil_Crm_OC(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS134496";
		}
		
		@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS135351_CRM_Movil_Pre_Vista_360_Consulta_de_Gestiones_Gestiones_abiertas_CASOS_Crm_OC(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS135351";
		}
		
		@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS164532_CRM_Movil_Pre_Vista_360_Consulta_de_Gestiones_Gestiones_abiertas_ORDENES_Crm_OC(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS164532";
		}
		
		@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS170887_CRM_Movil_Mix_Vista_360_Suscripcion_de_abono_fijo_Visualizar_las_acciones_secundarias_Crm_OC(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS170887";
		}
		
		@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS172457_CRM_Movil_Mix_Vista_360_Consulta_por_gestiones_Gestiones_abiertas_ORDENES_Crm_OC(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS172457";
		}
		
		@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS176283_CRM_Movil_Mix_Vista_360_Productos_y_Servicios_Visualizacion_del_estado_de_los_Productos_activos_Crm_OC(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS176283";
		}
				
		
		//--------------------------------------------- TELEFONICO-----------------------------------------------------------------
		
		
		@Test (groups = {"PerfilTelefonico", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS134794_CRM_Movil_Pre_Vista_360_Distribucion_de_paneles_Informacion_del_cliente_Crm_Telefonico(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS134794";
		}
		

		@Test (groups = {"PerfilTelefonico", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS134795_CRM_Movil_Pre_Vista_360_Distribucion_de_paneles_Perfil_Crm_Telefonico(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS134795";
		}
		
		
		@Test (groups = {"PerfilTelefonico", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS134796_CRM_Movil_Pre_Vista_360_Distribucion_de_paneles_Visualizacion_e_ingreso_a_las_ultimas_gestiones_Crm_Telefonico(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS134796";
		}
		
		
		@Test (groups = {"PerfilTelefonico", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS134799_CCRM_Movil_Pre_Vista_360_Producto_Activo_del_cliente_Desplegable_Crm_Telefonico(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS134799";
		}
		
		
		@Test (groups = {"PerfilTelefonico", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS134800_CRM_Movil_Pre_Vista_360_Mis_Servicios_Visualizacion_del_estado_de_los_servicios_activos_Crm_Telefonico(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS134800";
		}
		
		@Test (groups = {"PerfilTelefonico", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS134801_CRM_Movil_Pre_Vista_360_Mis_Servicios_Visualizacion_del_estado_de_los_Productos_activos_Crm_Telefonico(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS134801";
		}
		
		@Test (groups = {"PerfilTelefonico", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS134807_CRM_Movil_Pre_Vista_360_Consulta_de_gestiones_Gestiones_abiertas_Ordenadores_Crm_Telefonico(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS134807";
		}
		
		@Test (groups = {"PerfilTelefonico", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS134808_CRM_Movil_Pre_Vista_360_Consulta_de_gestiones_Gestiones_Cerradas_Informacion_brindada_Crm_Telefonico(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS134808";
		}
		

		@Test (groups = {"PerfilTelefonico", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS134809_CRM_Movil_Pre_Vista_360_Consulta_de_gestiones_Gestiones_Crm_Telefonico(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS134809";
		}
		
		@Test (groups = {"PerfilTelefonico", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS134810_CRM_Movil_Pre_Vista_360_Consulta_de_gestiones_Gestiones_abiertas_Estado_de_la_gestion_Crm_Telefonico(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS134810";
		}
		
		@Test (groups = {"PerfilTelefonico", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS135356_CRM_Movil_Pre_Vista_360_Consulta_de_Gestiones_Gestiones_abiertas_ORDENES_Crm_Telefonico(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS135356";
		}
		
		@Test (groups = {"PerfilTelefonico", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS164518_CRM_Movil_Pre_Vista_360_Consulta_de_gestiones_Gestiones_abiertas_Fecha_inicio_Crm_Telefonico(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS164518";
		}
		
		@Test (groups = {"PerfilTelefonico", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS164531_CRM_Movil_Pre_Vista_360_Consulta_de_Gestiones_Gestiones_biertas_CASOS_Crm_Telefonico(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS164531";
		}
		
		@Test (groups = {"PerfilTelefonico", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS172455_CRM_Movil_Mix_Vista_360_Consulta_de_Gestiones_Gestiones_abiertas_CASOS_Crm_Telefonico(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS172455";
		}
	
		////////////////////////////////////////////////////////////////   AGENTE      /////////////////////////////////////////////////////////
		
		@Test (groups = {"PerfilAgente", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS134817_CRM_Movil_Pre_Vista_360_Mis_Servicios_Visualizacion_del_estado_de_los_servicios_activos_Crm_Agente(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS134817";
		}
		
		@Test (groups = {"PerfilAgente", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS134818_CRM_Movil_Pre_Vista_360_Mis_Servicios_Visualizacion_del_estado_de_los_Productos_activos_Crm_Agente(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS134818";
		}
		
		@Test (groups = {"PerfilAgente", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS134819_CRM_Movil_Pre_Vista_360_Distribucion_de_paneles_Informacion_del_cliente_Crm_Agente(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS134819";
		}
		
		@Test (groups = {"PerfilAgente", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS134820_CRM_Movil_Pre_Vista_360_Distribucion_de_paneles_Perfil_Crm_Agente(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS134820";
		}
		
		@Test (groups = {"PerfilAgente", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS134822_CRM_Movil_Pre_Vista_360_Distribucion_de_paneles_Panel_Derecho_Busqueda_de_gestiones_promociones_y_gestiones_abandonadas_Crm_Agente(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS134822";
		}
		
		@Test (groups = {"PerfilAgente", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS134824_CRM_Movil_Pre_Vista_360_Producto_Activo_del_cliente_Desplegable_Crm_Agente(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS134824";
		}
		
		@Test (groups = {"PerfilAgente", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS134830_CRM_Movil_Pre_Vista_360_Consulta_de_gestiones_Gestiones_abiertas_Ordenadores_Crm_Agente(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS134830";
		}
		
		@Test (groups = {"PerfilAgente", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS134831_CRM_Movil_Pre_Vista_360_Consulta_de_gestiones_Gestiones_Cerradas_Informacion_brindada_Crm_Agente(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS134831";
		}
		
		@Test (groups = {"PerfilAgente", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS134832_CRM_Movil_Pre_Vista_360_Consulta_de_gestiones_Gestiones_Crm_Agente(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS134832";
		}
		
		@Test (groups = {"PerfilAgente", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS134833_CRM_Movil_Pre_Vista_360_Consulta_de_gestiones_Gestiones_abiertas_Estado_de_la_gestion_Crm_Agente(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS134833";
		}
		
		
		@Test (groups = {"PerfilAgente", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS135352_CRM_Movil_Pre_Vista_360_Consulta_de_gestiones_Gestiones_abiertas_CASOS_Crm_Agente(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS135352";
		}
		
		
		@Test (groups = {"PerfilAgente", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS135357_CRM_Movil_Pre_Vista_360_Consulta_de_gestiones_Gestiones_abiertas_ORDENES_Crm_Agente(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS135357";
		}
				
		@Test (groups = {"PerfilAgente", "R2"}, dataProvider = "ConsultaSaldo")
		public void TS176265_CRM_Movil_Mix_Vista_360_Distribucion_de_paneles_Informacion_del_cliente_Crm_Agente(String sDNI, String sLinea, String sAccountKey) {
			imagen = "TS176265";
		}
		
		
}

