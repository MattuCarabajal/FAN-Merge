package Tests;

import Pages.ContactInformation;
import Pages.ContactSearch;
import Pages.Login;
import Pages.setConexion;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class ValidationQuestions extends TestBase {
	
	
	private WebDriver driver;
	String Name = "Pepeasd";
	String LastName = "Argentoasd";
	String DateOfBirthday = "06/07/1988";
	String DNI = "Documento Nacional de Identidad";
	String[] DocValue = {"52694444","3569874563","365","ssss"};
	
	@AfterClass
	public void tearDown() {
	driver.close();
	}
	
	@BeforeClass
	public void Init() throws Exception
	{
		this.driver = setConexion.setupEze();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		login1(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}

	@BeforeMethod
	public void Setup() throws Exception {
		driver.get("https://goo.gl/ETjDYJ");
		ContactSearch contact = new ContactSearch(driver);
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		contact.searchContact(DNI, DocValue[0], "femenino");
		contact.sex("femenino");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		ContactInformation page = new ContactInformation(driver);
		page.setContactInformation(Name, LastName, DateOfBirthday);

		driver.findElement(By.cssSelector(".slds-checkbox--faux")).click();

		driver.findElement(By.id("Contact_nextBtn")).click();

}
	
	@Test	
	public void ValidationQuestion() {
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}

	//Seleccion de metodo por preguntas
		driver.findElement(By.xpath("//*[@id='ValidationMethod0']/div/div[1]/label[2]/span[1]")).click();
			try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
 
			driver.findElement(By.xpath("//input[@name='question-0' and @type='radio']"));
		List<WebElement> radio=driver.findElements(By.xpath("//input[@name='question-0' and @type='radio']"));
		for(int i=0; i<radio.size();i++)
		{	radio.get(i).click();
			WebElement local_radio= radio.get(i);
			
			String value=local_radio.getAttribute("value");
			System.out.println(value);
		}
		List<WebElement> radio1=driver.findElements(By.xpath("//input[@name='question-1' and @type='radio']"));
		for(int i=0; i<radio1.size();i++)
		{
			WebElement local_radio= radio1.get(i);
		String value=local_radio.getAttribute("value");
			System.out.println(value);
		}
		
		List<WebElement> radio2=driver.findElements(By.xpath("//input[@name='question-2' and @type='radio']"));
		for(int i=0; i<radio2.size();i++)
		{
			WebElement local_radio= radio2.get(i);
			String value=local_radio.getAttribute("value");
			System.out.println(value);
		}
		List<WebElement> radio3=driver.findElements(By.xpath("//input[@name='question-3' and @type='radio']"));
		for(int i=0; i<radio3.size();i++)
		{	
			WebElement local_radio= radio3.get(i);
			
			String value=local_radio.getAttribute("value");
			System.out.println(value);
		}
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}

	}
	
	@Test	
	public void ValidationTime() {
		//Seleccion de metodo por preguntas
		driver.findElement(By.xpath("//*[@id='ValidationMethod0']/div/div[1]/label[2]/span[1]")).click();
		try {Thread.sleep(65000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.xpath("//*[@id='QAForm']/div/ng-include/div/div[2]"));
		
	}
	
}