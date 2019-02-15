package Tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.Accounts;
import Pages.CustomerCare;
import Pages.SalesBase;
import Pages.setConexion;

public class CompletarAltas extends TestBase {
	
	protected WebDriver driver;
	protected  WebDriverWait wait;
	
	@BeforeClass(alwaysRun=true)
	public void Init2() {
		driver = setConexion.setupEze();
		//driver.manage().deleteAllCookies();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}		
		SalesBase SB = new SalesBase(driver);
		loginOfCom(driver);  
		CustomerCare cc = new CustomerCare(driver);
		
		 try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			
			driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
			sleep(10000);
			try{
				SB.cerrarPestaniaGestion(driver);}
			catch(Exception ex1) {
			}
			goToLeftPanel2(driver, "Inicio");
			sleep(5000);	
		}
	//@AfterMethod(alwaysRun=true)
		public void deslogin(){
			sleep(2000);
			SalesBase SB = new SalesBase(driver);
			driver.switchTo().defaultContent();
			sleep(6000);
			SB.cerrarPestaniaGestion(driver);
			
			sleep(5000);

		}
	
	//@AfterClass(alwaysRun=true)
	public void Exit() throws IOException {
		driver.quit();
		sleep(2000);
	}
	
	@Test(groups={"Sales", "AltaLineaDatos","E2E"}, priority=1)
	public void finalizar_altas() throws IOException {
		CustomerCare cc = new CustomerCare(driver);
		SalesBase SB = new SalesBase(driver);
		String orden = "00080253";
		/*BufferedReader archiDatos= new BufferedReader(new FileReader("DatosOrdenes.txt"));
		String linea =archiDatos.readLine();
		List<String> numordenes = new ArrayList<String>();
		while (linea!=null) {
			numordenes.add(linea.split("-")[0].split(":")[1]);*/
			/*System.out.println(cc.obtenerMontoyTNparaAlta(driver, linea.split("-")[0].split(":")[1]));
			SB.cerrarPestaniaGestion(driver);
			sleep(5000);*/
			/*linea =archiDatos.readLine();
		}
		sleep(2000);*/
		CambiarPerfil("logistica",driver);
		//for(String orden:numordenes) {
			SB.completarLogistica(orden, driver);
		//}
		//CBS_Mattu invoSer = new CBS_Mattu();
		//invoSer.openPage2(orden);
		CambiarPerfil("entrega",driver);
		//for(String orden:numordenes) {
			SB.completarEntrega(orden, driver);
		//}
		
	}
	

}
