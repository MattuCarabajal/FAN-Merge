package Tests;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.setConexion;
import Pages.ContactInformation;
import Pages.ContactSearch;
import Pages.ValidateByDniNumber;
import Pages.ValidationMethodSelection;

public class ValidationByDni extends TestBase{
	
	private WebDriver driver;
	String DNI = "Documento Nacional de Identidad";
	String[] DocValue = {"10000000","3569874563","365","ssss"};
	private String Name = "alex";
	private String LastName = "ray";
	private String DateOfBirthday = "08/08/1990";
	private String validationType = "document";
	private int realUploadNum;
	List <WebElement> filesUploaded;
	
	@BeforeClass
	public void Init() throws Exception
	{
		this.driver = setConexion.setupEze();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		login1(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	@AfterClass
	public void tearDown() {
		driver.close();
	}
	
	@BeforeMethod
	public void Setup() throws Exception
	{
		driver.get("https://goo.gl/ETjDYJ");
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		ContactSearch contact = new ContactSearch(driver);
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		contact.searchContact(DNI, DocValue[0], "femenino");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		contact.sex("femenino");
		driver.findElement(By.id("ContactInfo_nextBtn")).click();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		ContactInformation page = new ContactInformation(driver);
		page.setContactInformation(Name, LastName, DateOfBirthday);
		driver.findElement(By.cssSelector(".slds-checkbox--faux")).click();
		driver.findElement(By.id("Contact_nextBtn")).click();
		
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		ValidationMethodSelection validation = new ValidationMethodSelection(driver);
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		validation.setValidationType(validationType);
		//validation.next(); //in the website just clicking the radio goes to the next page.
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		realUploadNum = 0;
	}
	
	@Test
	public void attachFiles()
	{
		driver.findElement(By.id("FileDocumentImage")).sendKeys("C:\\Users\\Sofia Chardin\\Desktop\\Codigo de seguridad.PNG");
	}
	
	@Test
	public void TS6898_attachTwoFiles()
	{
		driver.findElement(By.id("FileDocumentImage")).sendKeys("C:\\Users\\Sofia Chardin\\Desktop\\Codigo de seguridad.PNG");
		realUploadNum += 1;
		driver.findElement(By.id("FileDocumentImage")).sendKeys("C:\\Users\\Sofia Chardin\\Desktop\\Codigo de seguridad.PNG");
		realUploadNum += 1;
		ValidateByDniNumber validationByDni = new ValidateByDniNumber(driver);
		Assert.assertTrue(realUploadNum == validationByDni.getFilesUploaded().size());
	}
	
	@Test
	public void TS6900_attachTwoMbFiles()
	{
		driver.findElement(By.id("FileDocumentImage")).sendKeys("C:\\Users\\Sofia Chardin\\Downloads\\0F6.gif");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		realUploadNum += 1;
		ValidateByDniNumber validationByDni = new ValidateByDniNumber(driver);
		Assert.assertTrue(realUploadNum == validationByDni.getFilesUploaded().size());
	}
	

}
