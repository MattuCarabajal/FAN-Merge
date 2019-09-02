package PagesPOM;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class LoginFw extends BasePageFw {
	
	//============================================== URLS ==============================================\\
		
	public static String urlAmbiente = "https://telecomcrm--uat02.cs45.my.salesforce.com/"; // UAT02
	//public static String urlAmbiente = "https://telecomcrm--uat.cs53.my.salesforce.com"; //UAT
	//public static String urlAmbiente = "https://telecomcrm--sit02.cs91.my.salesforce.com"; // SIT02
	//public static String urlAmbiente = "https://telecomcrm--sit03.cs42.my.salesforce.com"; //SIT03
	
	// viejo public String urlSCP = "https://telecomcrm--uat.cs8.my.salesforce.com";
	
	//public static String urlCommunity = "https://uat-autogestion-uat.cs53.force.com/clientes/s/";
	public static String urlCommunity = "https://sit-scrumcella.cs14.force.com/clientes/s/";
	
	public static String urlFlow	= "https://webgestionmoviltesting/default.aspx";
	
	public static String urlBeFAN = "http://snapuat.telecom.com.ar/#/home";
	
	//=========================================== ELEMENTOS ===========================================\\
	
	@FindBy(how = How.ID, using = "idp_section_buttons")
	private WebElement logininterno;

	@FindBy(how = How.ID, using = "username")
	private WebElement username;

	@FindBy(how = How.ID, using = "password")
	private WebElement password;

	@FindBy(how = How.NAME, using = "Ecom_User_ID")
	private WebElement Ecom_User_ID;

	@FindBy(how = How.NAME, using = "Ecom_Password")
	private WebElement Ecom_Password;

	@FindBy(how = How.ID, using = "Login")
	private WebElement loginMerge;

	@FindBy(how = How.ID, using = "loginButton2")
	private WebElement loginButton2;

	//========================================== CONSTRUCTOR ==========================================\\
	
	public LoginFw(WebDriver driver) {
		super.setDriver(driver);
		PageFactory.initElements(getDriver(), this);
		super.setFluentWait(new FluentWait<WebDriver>(driver));
		super.getFluentWait().withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class).ignoring(NullPointerException.class)
				.ignoring(ElementNotVisibleException.class);
	}
	
	//============================================ METODOS ============================================\\
	
	public void LoginSit() {
		driver.get(urlAmbiente);
		fluentWait.until(ExpectedConditions.elementToBeClickable(loginMerge));
		username.sendKeys("Victor-sebastian.rodriguez@atos.net.fan.sit02"); //("alan.rodriguez@xappia.com.fan.sit03");/
		password.sendKeys("Testa10k"); //("Diciembre*8");
		fluentWait.until(ExpectedConditions.elementToBeClickable(loginMerge)).click();
	}

	public void loginOOCC() {
		driver.get(urlAmbiente);
		fluentWait.until(ExpectedConditions.elementToBeClickable(logininterno));
		logininterno.click();
		fluentWait.until(ExpectedConditions.elementToBeClickable(loginButton2));
		Ecom_User_ID.sendKeys("Ua2544674"); //Ua2544674 //uat528737
		Ecom_Password.sendKeys("Testa10k");
		loginButton2.click();
	}
	
	public void loginTelefonico() {
		driver.get(urlAmbiente);
		fluentWait.until(ExpectedConditions.elementToBeClickable(logininterno));
		logininterno.click();
		fluentWait.until(ExpectedConditions.elementToBeClickable(loginButton2));
		Ecom_User_ID.sendKeys("Ua2591324");
		Ecom_Password.sendKeys("Testa10k");
		loginButton2.click();
	}

	public void loginAgente() {
		driver.get(urlAmbiente);
		fluentWait.until(ExpectedConditions.elementToBeClickable(logininterno));
		logininterno.click();
		fluentWait.until(ExpectedConditions.elementToBeClickable(loginButton2));
		Ecom_User_ID.sendKeys("Ua2554620");
		Ecom_Password.sendKeys("Testa10k");
		loginButton2.click();
	}
	
	public void loginOperativo() {
		driver.get(urlAmbiente);
		fluentWait.until(ExpectedConditions.elementToBeClickable(logininterno));
		logininterno.click();
		fluentWait.until(ExpectedConditions.elementToBeClickable(loginButton2));
		Ecom_User_ID.sendKeys("Ua2552929");
		Ecom_Password.sendKeys("Testa10k");
		loginButton2.click();
	}

	public void loginBackOffice() {
		driver.get(urlAmbiente);
		fluentWait.until(ExpectedConditions.elementToBeClickable(logininterno));
		logininterno.click();
		fluentWait.until(ExpectedConditions.elementToBeClickable(loginButton2));
		Ecom_User_ID.sendKeys("Ua2569324");
		Ecom_Password.sendKeys("Testa10k");
		loginButton2.click();
	}
	
	public void loginAdminFuncional() {
		driver.get(urlAmbiente);
		fluentWait.until(ExpectedConditions.elementToBeClickable(logininterno));
		logininterno.click();
		fluentWait.until(ExpectedConditions.elementToBeClickable(loginButton2));
		Ecom_User_ID.sendKeys("Ua2184370");
		Ecom_Password.sendKeys("Testa10k");
		loginButton2.click();
	}
}