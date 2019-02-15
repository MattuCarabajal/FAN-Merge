package Tests;

import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import Pages.SCP;
import Pages.setConexion;

public class SCPPrioritarios extends TestBase{
	private WebDriver driver;
	
	@BeforeClass(groups = "SCP")
	public void init() throws Exception
	{
		this.driver = setConexion.setupEze();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		loginSCPConPermisos(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
	}
	
	@BeforeMethod(groups = "SCP")
	public void setup() {
		SCP pScp = new SCP(driver);
		pScp.goToMenu("scp");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pScp.clickOnTabByName("cuentas");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	//@AfterClass(groups = "SCP")
	public void tearDown() {
		driver.quit();
	}
	

@Test(groups = "SCP", priority=1)
 public void TS116024_SCP_Crear_Cuenta() { 
  SCP prueba = new SCP(driver);

          driver.findElement(By.className("pbButton")).findElement(By.className("btn")).click();
  		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
  		List<WebElement> campos = driver.findElement(By.className("pbSubsection")).findElements(By.tagName("tr"));
  		//Tipo
  		Select listSelect = new Select (campos.get(0).findElements(By.tagName("td")).get(3).findElement(By.tagName("select")));
  		listSelect.selectByIndex(1);
  		//Segmento
  		listSelect = new Select (campos.get(1).findElements(By.tagName("td")).get(1).findElement(By.tagName("select")));
  		listSelect.selectByIndex(1);
  		//nombre de la cuenta
  		campos.get(2).findElements(By.tagName("td")).get(1).findElement(By.tagName("input")).sendKeys("APEREZ APEREZ");
  		  //driver.findElement(By.id("acc2")).
  		//razon social
  		//campos.get(2).findElements(By.tagName("td")).get(3).findElement(By.tagName("input")).sendKeys("APEREZ APEREZ");
  		  //driver.findElement(By.id("00N3F000000JoSt")).sendKeys("APEREZ APEREZ");
  		  //CUIT
  		 Random aleatorio = new Random(System.currentTimeMillis());
  		     aleatorio.setSeed(System.currentTimeMillis());
  		  int intAletorio = aleatorio.nextInt(8999)+1000;
  		campos.get(3).findElements(By.tagName("td")).get(3).findElement(By.tagName("input")).sendKeys("3053806"+Integer.toString(intAletorio));
  		  //driver.findElement(By.id("00N3F000000JoSZ")).sendKeys("30538068899");
  		  //numero de cliente
  		campos.get(7).findElements(By.tagName("td")).get(1).findElement(By.tagName("input")).sendKeys("000009"+Integer.toString(intAletorio));
  		  //driver.findElement(By.id("00N3F000000JoSf")).sendKeys("0000096399");
  		  try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
  		  driver.findElement(By.name("save")).click();
  		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		prueba.clickOnTabByName("cuentas");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		assertTrue(driver.findElement(By.className("hotListElement")).findElement(By.cssSelector(".dataRow.even.first")).findElement(By.tagName("a")).getText().toLowerCase().contains("aperez"));
  	
}

		/*@Test(groups = "Fase2")
		 public void TS_SCP_Crear_Cuenta() { 
		  SCP prueba = new SCP(driver);
		  String[] separado;
		  File archivo = null;
	      FileReader fr = null;
	      BufferedReader br = null;
	      try {
	         archivo = new File ("C:\\Users\\Florangel\\Downloads\\clientes.txt");
	         fr = new FileReader (archivo);
	         br = new BufferedReader(fr);

	         // Lectura del fichero
	         String linea;
	         while((linea=br.readLine())!=null) {
		          separado = linea.split(",");
		          
		          driver.findElement(By.className("pbButton")).findElement(By.className("btn")).click();
		  		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
		  		  //nombre de la cuenta
		  		  driver.findElement(By.id("acc2")).sendKeys(separado[0]);
		  		//razon social
		  		  driver.findElement(By.id("00N3F000000JoSt")).sendKeys(separado[1]);
		  		  //CUIT
		  		  driver.findElement(By.id("00N3F000000JoSZ")).sendKeys(separado[2]);
		  		  //numero de cliente
		  		  driver.findElement(By.id("00N3F000000JoSf")).sendKeys(separado[3]);
		  		  try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  		  driver.findElement(By.name("save")).click();
		  		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
				prueba.clickOnTabByName("cuentas");
				try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  		  
	         }
	      }
	      catch(Exception e){
	         e.printStackTrace();		  
		 }
		}*/
	
	public boolean Eliminar_Cuenta(String Cuenta){
		SCP page=new SCP(driver);
		page.clickOnTabByName("cuentas");
		sleep(2000);
		driver.findElement(By.name("go")).click();
		sleep(3000);
		List<WebElement> listaDeCuentas=driver.findElements(By.className("x-grid3-row-table"));
		//listaDeCuentas.add(driver.findElement(By.cssSelector(".x-grid3-row.x-grid3-row-first")));
		for(WebElement lc:listaDeCuentas) {
			WebElement nombre=lc.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(2);
			WebElement eliminar=lc.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(1).findElements(By.tagName("a")).get(1);
			//System.out.println(nombre.getText());
			if(nombre.getText().toLowerCase().contains(Cuenta.toLowerCase())) {
				eliminar.click();
				try {driver.switchTo().alert().accept();}catch(org.openqa.selenium.NoAlertPresentException e) {}
				sleep(1000);
				return true;
			}
				
		}
		return false;
	}
		
	public void Crear_Cuenta(String NombreDeLaCuenta, String Cuit, String NumeroDeCliente) { 
		SCP prueba = new SCP(driver);
        driver.findElement(By.className("pbButton")).findElement(By.className("btn")).click();
  		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
  		List<WebElement> campos = driver.findElement(By.className("pbSubsection")).findElements(By.tagName("tr"));
  		//Tipo
  		Select listSelect = new Select (campos.get(0).findElements(By.tagName("td")).get(3).findElement(By.tagName("select")));
  		listSelect.selectByIndex(1);
  		//Segmento
  		listSelect = new Select (campos.get(1).findElements(By.tagName("td")).get(1).findElement(By.tagName("select")));
  		listSelect.selectByIndex(1);
  		//nombre de la cuenta
  		campos.get(2).findElements(By.tagName("td")).get(1).findElement(By.tagName("input")).sendKeys(NombreDeLaCuenta);
  		  //CUIT
  		campos.get(3).findElements(By.tagName("td")).get(3).findElement(By.tagName("input")).sendKeys(Cuit);
  		  //numero de cliente
  		campos.get(7).findElements(By.tagName("td")).get(1).findElement(By.tagName("input")).sendKeys(NumeroDeCliente);
  		sleep(2000);
  		  driver.findElement(By.name("save")).click();
  		sleep(4000);
}
 
 
		//@BeforeSuite
		public void InicializarDatos() throws Exception {
			//init();
			int i=0;
			Cuentas C=new Cuentas();
			/*while(i<C.getNombreDeLaCuenta().length){
			setup();
			Crear_Cuenta(C.getNombreDeLaCuenta(i),C.getCuit(i),C.getNumeroDelCliente(i));
			i++;
			}
			i=0;
			SCP page=new SCP(driver);
			while(i<C.getNombreDeLaCuenta().length){
				//boolean creada=page.verificarExistenciaDeCuenta(C.getNombreDeLaCuenta(i));
				if(page.verificarExistenciaDeCuenta(C.getNombreDeLaCuenta(i))) System.out.println("Cuenta: "+C.getNombreDeLaCuenta(i)+" Creada.");
				else System.out.println("Cuenta "+C.getNombreDeLaCuenta(i)+" no Creada o no Disponible en el Listado.");
				i++;
			}
			tearDown();
			page.EjecutarInterfazBatch();
			sleep(5000);*/
			this.driver = setConexion.setupEze();
			sleep(10000);
			loginSCPConPermisos(driver);
			sleep(5000);
			SCP page=new SCP(driver);
			i=0;
			while(i<C.getNombreDeLaCuenta().length) {
				page.clickOnTabByName("cuentas");
				sleep(3000);
				page.clickEnCuentaPorNombre(C.getNombreDeLaCuenta(i));
				sleep(2000);
				page.scroll(By.name("newOpp"));
				driver.findElement(By.name("newOpp")).click();
				sleep(3000);
					for(int j=0;j<2;j++) {
					page.CrearOportunidad("aaaaaprueba");
				}
			i++;
			}
			
			System.out.println("Before Suite Ejecutado.");
		}
		
		//@AfterSuite
		//@BeforeSuite
		 public void EliminarDatos() throws Exception {
			 init();
			 int i=0;
			 Cuentas C=new Cuentas();
			 while(i<C.getNombreDeLaCuenta().length){
				 if(Eliminar_Cuenta(C.getNombreDeLaCuenta(i))) System.out.println("Cuenta: "+C.getNombreDeLaCuenta(i)+" Eliminada.");
				 else System.out.println("Cuenta no Encontrada o no eliminada");
				 i++;
			 }
			 tearDown();
		 }
}



//------------------------------------------------ Valores de Cuentas a Crear-----------------------------------------//
 class Cuentas{
	String[] NombreDeLaCuenta;
	//lo uso para determinar el Size
	public String[] getNombreDeLaCuenta() {
		return NombreDeLaCuenta;
	}

	public String getNombreDeLaCuenta(int i) {
		return NombreDeLaCuenta[i];
	}

	String[] Cuit;
	public String getCuit(int i) {
		return Cuit[i];
	}

	String[] NumeroDelCliente;
	
	public String getNumeroDelCliente(int i) {
		return NumeroDelCliente[i];
	}

	public Cuentas() {
		NombreDeLaCuenta= new String[] {"Almer","Flor"};
		Cuit= new String[] {"300030000","400040000"};
		NumeroDelCliente= new String[] {"0000400000","0000500000"};
	}
	
}
//------------------------------------------------ ---------------------- ----------------------------------------------//
 
 class Oportunidad{
	 String nombreDeLaOportunidad;
	 String etapa;
	 String contacto;
	 String fechaProbableVenta;
	 String fechaProbableInstalacion;
	 String fechaCierre;
	 
	 public String getNombreDeLaOportunidad() {
			return nombreDeLaOportunidad;
	}
	 
	 

 }
 