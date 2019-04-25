package PagesPOM;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import Pages.Login;

public class LoginFw extends BasePageFw {
	//URLS 
	public static String urlMerge = "https://telecomcrm--SIT02.cs91.my.salesforce.com";
	
	
	public static String urlAmbiente = "https://telecomcrm--uat.cs53.my.salesforce.com";
	//public static String urlAmbiente = "https://crm--sit.cs14.my.salesforce.com/";
	
	
	// viejo public String urlSCP = "https://telecomcrm--uat.cs8.my.salesforce.com";
	public static String urlSCP = "https://telecomcrm--uat.cs53.my.salesforce.com";
	
	//public static String urlComunidad = "https://uat-autogestion-uat.cs53.force.com/clientes/s/";
	public static String urlCommunity = "https://sit-scrumcella.cs14.force.com/clientes/s/";
	
	public static String urlFlow	= "https://webgestionmoviltesting/default.aspx";
	
	public static String urlBeFAN = "http://snapuat.telecom.com.ar/#/home";
	
	//ELEMENTOS 
	@FindBy(how = How.ID, using = "idp_section_buttons")
	private WebElement logininterno;
	
	@FindBy (how = How.ID, using = "username")
	private WebElement username;
	
	@FindBy (how= How.ID, using = "password")
	private WebElement password;
	
	
	@FindBy (how = How.ID, using = "Login")
	private WebElement loginMerge;


	//CONTRUCTOR
	public LoginFw(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		super.fluentWait = new FluentWait<WebDriver>(driver);
		
		super.fluentWait.withTimeout(30, TimeUnit.SECONDS)
		.pollingEvery(5, TimeUnit.SECONDS)
		.ignoring(NoSuchElementException.class)
		.ignoring(NullPointerException.class)
		.ignoring(TimeoutException.class)
		.ignoring(ElementNotVisibleException.class);
	}
	//METODOS
	public void ingresarLogin() {
		  driver.get(urlMerge);
		  fluentWait.until(ExpectedConditions.elementToBeClickable(loginMerge));
		  username.sendKeys("florangel.rojas@xappia.com.fan.sit02");
		  password.sendKeys("Testa10k");
		  	
		  	System.out.println("termino Corecto");	
		  	fluentWait.until(ExpectedConditions.elementToBeClickable(loginMerge)).click();
	}
		 
}
