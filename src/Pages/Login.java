package Pages;
 
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import Tests.TestBase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Login extends BasePage {
	
	final WebDriver driver;
	TestBase TB = new TestBase();
	
	//Fields
	
	@FindBy (how = How.NAME, using = "username")
	private WebElement userMarketing;
	
	@FindBy (how = How.NAME, using = "pw")
	private WebElement passwordMarketing;
	
	@FindBy (how = How.ID, using = "Login")
	private WebElement loginMarketing;
	
	@FindBy (how = How.NAME, using = "Ecom_User_ID")
	private WebElement username;
	
	@FindBy (how = How.NAME, using = "Ecom_Password")
	private WebElement password;

	@FindBy (how = How.ID, using = "loginButton2")
	private WebElement login;
	
	@FindBy (how = How.NAME, using = "rememberUn")
	private WebElement rememberMe;
	
	@FindBy(how = How.ID, using = "idp_section_buttons")
	private WebElement logininterno;
	
	@FindBy (how= How.NAME, using = "Ecom_User_ID")
	private WebElement Ecom_User_ID;
	   
	@FindBy (how= How.NAME, using = "Ecom_Password")
	private WebElement Ecom_Password;
   
	@FindBy (how = How.ID, using = "loginButton2")
	private WebElement loginButton2;
   
	@FindBy (how = How.CSS, using = ".sfdc_usernameinput.sfdc.input.input")
	private WebElement wLoginCommunity;
   
	@FindBy (how = How.CSS, using = ".sfdc_passwordinput.sfdc.input.input")
	private WebElement wPasswordCommunity;
   
	@FindBy (how = How.CSS , using = ".slds-button.slds-button--neutral.sfdc_button.loginBtn.uiButton--default.uiButton")
	private WebElement wIngresarCommunity;
	 
	@FindBy (how = How.ID, using = "TxtUsuario")
	private WebElement UserF;
   
	@FindBy (how = How.ID, using = "TxtContrasenia")
	private WebElement PassF;
   
	@FindBy (how = How.ID, using = "username")
	private WebElement usernameMerge;
	
	@FindBy (how = How.ID, using = "password")
	private WebElement passwordMerge;
	
	@FindBy (how = How.ID, using = "Login")
	private WebElement loginMerge;
	
	@FindBy (how = How.CSS, using = ".btn.btn-lg.btn-primary.btn-block.btn-signin")
	private WebElement ClickF;
	//Constructor
	
	public Login(WebDriver driver){
		this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	
	public void ingresarGeneral() {
		  logininterno.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  Ecom_User_ID.sendKeys("uat569076");//UAT 
		  Ecom_Password.sendKeys("Testa10k");
		  loginButton2.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		 }
	
	//Methods
	public void ingresarSCPconTodo() { 
		 try {logininterno.click();}
		 catch(org.openqa.selenium.ElementNotVisibleException ex1) {
			 driver.findElement(By.id("idp_hint")).click();
		 }
		 //viejo Ecom_User_ID.sendKeys("UA198998");
		 //viejo Ecom_Password.sendKeys("Teco1234");
		 sleep(2000);
		 Ecom_User_ID.sendKeys("UAT580714");
	     Ecom_Password.sendKeys("Testa10k");
	     loginButton2.click();
	     try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void ingresarAdminSCP() {
		 logininterno.click();
	     Ecom_User_ID.sendKeys("UAT585244");
	     Ecom_Password.sendKeys("Testa10k");
	     loginButton2.click();
	     try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	   }
	   
	  
	   public void ingresarUsuarioSCP() {
		 try {logininterno.click();}
		 catch(org.openqa.selenium.ElementNotVisibleException ex1) {
			 driver.findElement(By.id("idp_hint")).click();
		 }
	     Ecom_User_ID.sendKeys("UA177426");
	     Ecom_Password.sendKeys("Testa10k");
	     loginButton2.click();
	     try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	   }
	 
	public void ingresar() {
		logininterno.click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		//Usuario Daniel
		//username.sendKeys("u589831");
		//Usuario UAT OOCC
		username.sendKeys("UAT195528");
		password.sendKeys("Testa10k");
    	//rememberMe.click();
		login.click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	public void ingresarDani() {
		logininterno.click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		//Usuario Daniel
		username.sendKeys("u589831");
		//Usuario UAT OOCC
		//username.sendKeys("UAT195528");
		password.sendKeys("Testa10k");
    	//rememberMe.click();
		login.click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void ingresarMarketing() {
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		logininterno.click();
		//Usuario Cesar
		//username.sendKeys("u198427");
		//Usuario UAT OOCC
		username.sendKeys("uat195528");
		password.sendKeys("Testa10k");
		login.click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	//******************Agente
   //Para el Modulo Sales tiene vinculado el perfil de Agente y Atenci�n a clientes
	public void ingresarAndres() {
		  logininterno.click();
		  //TestBase TB = new TestBase();
		  //TB.waitFor(driver,By.id("Ecom_User_ID"));
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  
		  if (TB.urlAmbiente.contains("sit"))
			  Ecom_User_ID.sendKeys("UAT549492");//SIT
		  else
			  Ecom_User_ID.sendKeys("uat195528");//UAT
		  Ecom_Password.sendKeys("Testa10k");
		  loginButton2.click();
		  //try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		 }
	//*************************Telefonico
	//Para el Modulo Sales tiene vinculado el perfil de Call center
	public void ingresarElena() {
		  logininterno.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  
		  if (TB.urlAmbiente.contains("sit"))
			  Ecom_User_ID.sendKeys("UAT569076");//SIT
		  else
			  Ecom_User_ID.sendKeys("uat592149");//UAT
		  Ecom_Password.sendKeys("Testa10k");
		  loginButton2.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	public void ingresarLogistica() {
		  logininterno.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  
		  if (TB.urlAmbiente.contains("sit"))
			  Ecom_User_ID.sendKeys("UAT178596");//SIT
		  else
		  	  Ecom_User_ID.sendKeys("UAT577822"); //UAT
		  Ecom_Password.sendKeys("Testa10k");
		  loginButton2.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	public void ingresarEntrega() {
		  logininterno.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		   Ecom_User_ID.sendKeys("UAT544381");
		  Ecom_Password.sendKeys("Testa10k");
		  loginButton2.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	//Para el Modulo Sales tiene vinculado el perfil de Vendedor Oficina Comercial
	public void ingresarFrancisco() {
		  logininterno.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  Ecom_User_ID.sendKeys("u581441");
		  Ecom_Password.sendKeys("Testa10k");
		  loginButton2.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		 }
	//***********************Oficina comercial
	public void ingresarNominaciones() {
		  logininterno.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  
		  if (TB.urlAmbiente.contains("sit")) 
			 Ecom_User_ID.sendKeys("UAT195528");//SIT
		  else 
		  	Ecom_User_ID.sendKeys("uat569076");//UAT
		  		 
		  Ecom_Password.sendKeys("Testa10k");
		  loginButton2.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		 }
	
	//Para el Modulo Sales tiene vinculado el perfil de Logistica
	public void ingresarNicolas() {
		  logininterno.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  Ecom_User_ID.sendKeys("u586760");
		  Ecom_Password.sendKeys("Testa10k");
		  loginButton2.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		 }
	
	//Para el Modulo Sales tiene vinculado el perfil de Entregas y Configuraciones
	public void ingresarMarcela() {
		  logininterno.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  Ecom_User_ID.sendKeys("u591584");
		  Ecom_Password.sendKeys("Testa10k");
		  loginButton2.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		 }
	
	public void ingresarFabiana() {
		  logininterno.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  Ecom_User_ID.sendKeys("u585244");
		  Ecom_Password.sendKeys("Testa10k");
		  loginButton2.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}

	public void ingresarVictor() {
		  logininterno.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  Ecom_User_ID.sendKeys("U585991");
		  Ecom_Password.sendKeys("Testa10k");
		  loginButton2.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void ingresarSCPAdminServices() {
		try {logininterno.click();}
		 catch(org.openqa.selenium.ElementNotVisibleException ex1) {
			 driver.findElement(By.id("idp_hint")).click();
		 }
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  //Ecom_User_ID.sendKeys("UA090571");
		  Ecom_User_ID.sendKeys("UAT551577");
		  Ecom_Password.sendKeys("Testa10k");
		  loginButton2.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void ingresarSCPConPermisos() {
		try {logininterno.click();}
		 catch(org.openqa.selenium.ElementNotVisibleException ex1) {
			 driver.findElement(By.id("idp_hint")).click();
		 }
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		 //Viejo Ecom_User_ID.sendKeys("UAT090022");
		  Ecom_User_ID.sendKeys("UAT506677");
		  Ecom_Password.sendKeys("Testa10k");
		  loginButton2.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void ingresar(String User, String Password) {
		logininterno.click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		username.sendKeys(User);
		password.sendKeys(Password);
		login.click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void ingresarCBS() {
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		logininterno.click();
		username.sendKeys("CBS543438");
		password.sendKeys("Keke136!");
		login.click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void ingresarBackOffice() {
		//logininterno.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  System.out.println("llego otra vez");
		  usernameMerge.sendKeys("u193882@telecom.com.ar.sit02");
		  passwordMerge.sendKeys("Atos.002");
		  loginMerge.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  /*if (TB.urlAmbiente.contains("sit"))
				Ecom_User_ID.sendKeys("UAT186579");//SIT
		  else
		  		Ecom_User_ID.sendKeys("uat569076");//UAT 
		  Ecom_Password.sendKeys("Testa10k");
		  loginButton2.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}*/
	}
	
	public void ingresarComunidad() {
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		if (TB.urlCommunity.contains("sit")) {
			wLoginCommunity.sendKeys("alan.rodriguez@xappia.com");//SIT
			wPasswordCommunity.sendKeys("Telecom*77");
		}
		else {
			wLoginCommunity.sendKeys("rabal@yopmail.com");//UAT 
			wPasswordCommunity.sendKeys("Salesforce1");
		}
		wIngresarCommunity.click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void ingresarflow(){
		UserF.sendKeys("wdi593809");
		PassF.sendKeys("Telecom01!");
		ClickF.click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void ingresarOperativo() {
		logininterno.click();
		username.sendKeys("UAT552929");
		password.sendKeys("Testa10k");
		login.click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void ingresarLautaro() {
		logininterno.click();
		username.sendKeys("Ua2184370");
		password.sendKeys("Testa10k");
		login.click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void ingresarBeFAN() {
		driver.findElement(By.cssSelector(".btn.btn-primary.btn-lg.btn-block.btn-continuar")).click();
		driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-empty.ng-invalid.ng-invalid-required.ng-valid-maxlength.ng-touched")).sendKeys("UAT195528");
		driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-empty.ng-invalid.ng-invalid-required.ng-valid-maxlength")).sendKeys("Testa10k");
		driver.findElement(By.cssSelector(".btn.btn-primary.btn-lg.btn-block.btn-continuar")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void ingresarBeFANConfigurador() {
		driver.findElement(By.cssSelector(".btn.btn-primary.btn-lg.btn-block.btn-continuar")).click();
		driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-empty.ng-invalid.ng-invalid-required.ng-valid-maxlength.ng-touched")).sendKeys("UAT529763");
		driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-empty.ng-invalid.ng-invalid-required.ng-valid-maxlength")).sendKeys("Testa10k");
		driver.findElement(By.cssSelector(".btn.btn-primary.btn-lg.btn-block.btn-continuar")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void ingresarFraude() {
		logininterno.click();
		username.sendKeys("uat542845");
		password.sendKeys("Testa10k");
		login.click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void ingresarLogisticaYEntrega() {
		  logininterno.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  
		  if (TB.urlAmbiente.contains("sit"))
			  Ecom_User_ID.sendKeys("UAT178596");//SIT
		  else
		  	  Ecom_User_ID.sendKeys("uat577822"); //UAT
		  Ecom_Password.sendKeys("Testa10k");
		  loginButton2.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	//*************** Merge: Flor
	public void ingresarMerge() {
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  usernameMerge.sendKeys("florangel.rojas@xappia.com.fan.sit02");
		  passwordMerge.sendKeys("Testa10k");
		  loginMerge.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		 }
	
	/*public void ingresarMerge() {
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  usernameMerge.sendKeys("u577822@telecom.com.ar.sit02");
		  passwordMerge.sendKeys("Atos.001");
		  loginMerge.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		 }*/
	
	public void ingresarMerge2() {
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  usernameMerge.sendKeys("ezequiel-matias.girola@atos.net.sit02");
		  passwordMerge.sendKeys("Atos.001");
		  loginMerge.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		 }
	
	public void ingresarFANFront() {
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  usernameMerge.sendKeys("u577822@telecom.com.ar.sit02");
		  passwordMerge.sendKeys("Atos.001");
		  loginMerge.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void ingresarOOCC() {
		 logininterno.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  Ecom_User_ID.sendKeys("Ua2544674"); //UAT
		  Ecom_Password.sendKeys("Testa10k");
		  loginButton2.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}

	public void ingresarTelefonico() {
		logininterno.click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  Ecom_User_ID.sendKeys("Ua2591324"); //UAT
		  Ecom_Password.sendKeys("Testa10k");
		  loginButton2.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
	}

	public void ingresarAgente() {
		logininterno.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  Ecom_User_ID.sendKeys("Ua2554620"); //UAT
		  Ecom_Password.sendKeys("Testa10k");
		  loginButton2.click();
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
	}
	
	public void ingresarBeFANVictor(String perfil, String urlAmbiente) {
		if (urlAmbiente.contains("befantest2.personal.corp")) {
			switch (perfil) {
			case "mayorista":
				driver.findElement(By.name("username")).sendKeys("UAT195528");
				driver.findElement(By.name("txtPass")).sendKeys("Testa10k");
				break;
			case "configurador":
				driver.findElement(By.name("username")).sendKeys("UAT529763");
				driver.findElement(By.name("txtPass")).sendKeys("Testa10k");
				break;
			}
		} else {
			if (urlAmbiente.contains("befanuat2")) {
				switch (perfil) {
				case "mayorista":
					driver.findElement(By.name("username")).sendKeys("ua2180116");
					driver.findElement(By.name("txtPass")).sendKeys("Testa10k");
					break;
				case "configurador":
					driver.findElement(By.name("username")).sendKeys("ua2608027");
					driver.findElement(By.name("txtPass")).sendKeys("Testa10k");
					break;
				}
			}
			
		}
		driver.findElement(By.name("btnIngresar")).click();
	}
	
	
}
