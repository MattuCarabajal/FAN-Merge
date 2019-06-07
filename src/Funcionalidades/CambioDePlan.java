package Funcionalidades;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.BeFan;
import Pages.CBS;
import Pages.ContactSearch;
import Pages.CustomerCare;
import Pages.SCP;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.MDW;
import Tests.TestBase;

public class CambioDePlan extends TestBase {

	private WebDriver driver;
	private LoginFw log;
	private GestionDeClientes_Fw ges;
	private CustomerCare cc;
	private CBS cbs;
	private CBS_Mattu cbsm;
	private ContactSearch contact;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private SCP scp;
	String detalles;
	
	@BeforeClass (alwaysRun = true)
	public void Sit02() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		contact = new ContactSearch(driver);
		log.LoginSit02();
		cbs = new CBS();
		cbsm = new CBS_Mattu();
				
	}
	
	@BeforeMethod (alwaysRun = true)
	public void setup(){
		detalles = null;
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
	}
	
	//@AfterMethod (alwaysRun=true)
	public void after() throws IOException{
		guardarListaTxt(sOrders);
		sOrders.clear();
		tomarCaptura(driver,imagen);
		sleep(2000);
	}
	
	//@AfterClass(alwaysRun = true)
	public void quit(){
		driver.quit();
		sleep(5000);
	}
	
	@Test()
	public void asd(){
		imagen = "TS_CambioDePlan";
		detalles = imagen + "- TS_CambioDePlan - DNI: " + "";
		boolean enc = false;
		String fecha = fechaDeHoy();
		ges.BuscarCuenta("DNI", "3289731");
		ges.irAGestionEnCard("Cambio de Plan");
		sleep(7000);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("OrderRequestDate")));
		driver.findElement(By.id("OrderRequestDate")).sendKeys(fecha);
		driver.findElement(By.id("Request date_nextBtn")).click();
		//ERROR CREAR ORDEN
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Failed_FDO_nextBtn")));
		driver.findElement(By.id("Failed_FDO_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("alert-ok-button")));
		driver.findElement(By.id("alert-ok-button")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Failed_FDO_nextBtn")));
		driver.findElement(By.id("Failed_FDO_nextBtn")).click();
	}
}
