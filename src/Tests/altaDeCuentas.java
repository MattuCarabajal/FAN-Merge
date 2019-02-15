package Tests;

import java.util.List;
import java.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.BasePage;
import Pages.CustomerCare;
import Pages.HomeBase;
import Pages.setConexion;
public class altaDeCuentas extends TestBase{
	
	private WebDriver driver;

	@BeforeClass(groups= {"Wave1"})
	public void init() throws Exception
	{
		this.driver = setConexion.setupEze();
		sleep(4000);
		login(driver);
		sleep(5000);
	}
	

	@AfterClass
	public void Teardown() {
		driver.quit();
		
	}
	@Test
	public void Carga() throws IOException {
		
		String[] separado;
		 File archivo = null;
	     FileReader fr = null;
	     BufferedReader br = null;
	     String Documento="1050476";
		 String Nombre="prueba";
		 String Apellido="aut";
		 String FechaNacimiento="01/02/1990";
		 Random rnd = new Random();
		 int opcionGenero=rnd.nextInt(1);
	     archivo = new File ("C:\\Users\\Florangel\\Downloads\\LineasListadas.txt");
	     fr = new FileReader (archivo);
	     br = new BufferedReader(fr);
	     String linea;
	     while((linea=br.readLine())!=null) {
	    	 System.out.println("Linea: "+linea);
	     }
	      // Lectura del fichero
	     /*String linea;
	     while((linea=br.readLine())!=null) {
		      separado = linea.split(",");  
		      Documento = separado[1];
		      Nombre = separado[3];
		      Apellido = separado[4];
		      FechaNacimiento = separado[5];
		      if (separado[2].equals("FEMENINO")) 
		    	  opcionGenero = 0;
		      else
		    	  opcionGenero = 1;
			  driver.get("https://crm--sit--c.cs14.visual.force.com/apex/taClientSearch#/");
			  sleep(5000);
			  //FIJO DNI
			  Select tipoDeDocumento=new Select(driver.findElement(By.id("SearchClientDocumentType")));
			  tipoDeDocumento.selectByVisibleText("DNI");
			  //Documento Cambia
			  driver.findElement(By.id("SearchClientDocumentNumber")).sendKeys(Documento);
			  //Click en Crear Nuevo
			  sleep(1500);
			  driver.findElement(By.id("dataInput_nextBtn")).click();
			  sleep(5000);
			  //LLena los datos
			  //Nombre
			  driver.findElement(By.id("FirstName")).sendKeys(Nombre);
			  //Apellido
			  driver.findElement(By.id("LastName")).sendKeys(Apellido);
			  //Fecha de Nacimiento
			  driver.findElement(By.id("Birthdate")).sendKeys(FechaNacimiento);
			  //Genero
			  List<WebElement> Genero=driver.findElements(By.cssSelector(".slds-radio--faux.ng-scope"));
			  Genero.get(opcionGenero).click();
			  sleep(1500);
			  //CLick Continuar
			  driver.findElement(By.id("Contact_nextBtn")).click();
			  sleep(10000);
	        }*/
	}
}
