package Tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Pages.BasePage;
import Pages.CustomerCare;
import Pages.SCP;
import Pages.setConexion;

public class CustomerCare_Mattu extends TestBase{
	
	private WebDriver driver;
	
	//-------------------------------------------------------------------------------------------------
	//@Befor&After
	@BeforeClass (groups = {"CustomerCare", "ActualizarDatos", "DetalleDeConsumos", "Vista360Layout"})
    public void init() throws Exception
    {
        this.driver = setConexion.setupEze();
        login(driver);
        try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
        String a = driver.findElement(By.id("tsidLabel")).getText();
        driver.findElement(By.id("tsidLabel")).click();
        if(a.equals("Ventas"))
        {
            driver.findElement(By.xpath("//a[@href=\'/console?tsid=02uc0000000D6Hd\']")).click();
        }else
        {            driver.findElement(By.xpath("//a[@href=\'/home/home.jsp?tsid=02u41000000QWha\']")).click();
            try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
            driver.findElement(By.id("tsidLabel")).click();
            driver.findElement(By.xpath("//a[@href=\'/console?tsid=02uc0000000D6Hd\']")).click();
        }
    }    
    @BeforeMethod (groups = {"CustomerCare", "ActualizarDatos", "DetalleDeConsumos", "Vista360Layout"})
    public void setup(){
        try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
        CustomerCare page = new CustomerCare(driver);
        page.cerrarultimapestana();
    }
    @AfterClass(groups = {"CustomerCare", "ActualizarDatos", "DetalleDeConsumos", "Vista360Layout"})
    public void quit() {
      driver.quit();
      sleep(4000);
    }
	
	//-------------------------------------------------------------------------------------------------
	//TestCases Alan
	@Test(groups = {"CustomerCare", "ActualizarDatos"})
	public void TS38042_Profile_Changes_Validación_Correo_Electronico_Cuenta_Email_Existente(){
	CustomerCare CP = new CustomerCare (driver);
	try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
	CP.elegircuenta("aaaaFernando Care");
	BasePage cambioFrameByID=new BasePage();
	driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("profile-edit")));
	List <WebElement> actualizar = driver.findElements(By.className("profile-edit"));
		  for (WebElement x : actualizar) {
			  if (x.getText().toLowerCase().contains("actualizar datos")) {
				  x.click();
			  } 
		   }
	try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	driver.switchTo().defaultContent();
	driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("Email")));
	boolean ff =false;
	WebElement qw = driver.findElement(By.id("Email"));
	try {Thread.sleep(15000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
	String as = qw.getAttribute("value");
	 qw.clear();
	 try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
	WebElement qwe = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.ng-scope.ng-dirty.ng-invalid.ng-valid-email.ng-invalid-required"));
		if(qwe.isDisplayed());
		ff = true;
	driver.findElement(By.xpath("//*[@id=\"Email\"]")).sendKeys(as);
	try {
		   Assert.assertFalse(driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.ng-scope.ng-dirty.ng-invalid.ng-valid-email.ng-invalid-required")).isDisplayed());
		  } catch (org.openqa.selenium.NoSuchElementException e) {
		   Assert.assertTrue(true);
		  }
}
 
	@Test(groups = {"CustomerCare", "ActualizarDatos"})
	public void TS38043_Profile_Changes_Validación_Correo_Electronico_Cuenta_Email_No_existente(){
	 CustomerCare CP = new CustomerCare (driver);
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		CP.elegircuenta("aaaaFernando Care");
	BasePage cambioFrameByID=new BasePage();
	driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("profile-edit")));
	List <WebElement> actualizar = driver.findElements(By.className("profile-edit"));
		  for (WebElement x : actualizar) {
		   if (x.getText().toLowerCase().contains("actualizar datos")) {
		    x.click();
		   }
		  }
	try {Thread.sleep(15000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	driver.switchTo().defaultContent();
	driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("Email")));
	WebElement mail = driver.findElement(By.id("Email"));
	mail.clear();
	boolean f = true;
	try {Thread.sleep(20000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	WebElement qwe = driver.findElement(By.cssSelector(".slds-input.form-control.ng-valid-email.ng-touched.ng-dirty.ng-empty.ng-invalid.ng-invalid-required"));
	try {Thread.sleep(20000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	mail.sendKeys("asfasdasd");
	Assert.assertTrue(qwe.isDisplayed());
	

}

	@Test(groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS38346_Consumption_Details_Detalle_de_consumos_Servicio_Pre_pago_Prepago_Muestra_los_ultimos_3_días() {
		 CustomerCare CP = new CustomerCare (driver);
			try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
			CP.elegircuenta("aaaaFernando Care");
			try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
			BasePage cambioFrameByID=new BasePage();
			driver.switchTo().defaultContent();
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("card-info")));
			List<WebElement> btn = driver.findElements(By.cssSelector(".icon.icon-v-graph-line"));
			for(WebElement b:btn) {
				b.getText().toLowerCase().contains("detalle de consumos");
				b.click();
			}
			try {Thread.sleep(15000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
			driver.switchTo().defaultContent();
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium")));
			//WebElement wed = driver.findElement(By.id("text-input-02"));
			//System.out.println(wed.getAttribute("value"));
			Assert.assertTrue(driver.findElement(By.id("text-input-02")).getAttribute("value").toLowerCase().contains("los últimos 3 días"));
	}

	@Test(groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS38347_Consumption_Details_Detalle_de_consumos_Servicio_Pre_pago_Prepago_Visualizar_leyenda_Los_ultimos_3_días() {
		 CustomerCare CP = new CustomerCare (driver);
			try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
			CP.elegircuenta("aaaaFernando Care");
			try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
			BasePage cambioFrameByID=new BasePage();
			driver.switchTo().defaultContent();
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("card-info")));
			List<WebElement> btn = driver.findElements(By.cssSelector(".icon.icon-v-graph-line"));
			for(WebElement b:btn) {
				b.getText().toLowerCase().contains("detalle de consumos");
				b.click();
			}
			try {Thread.sleep(15000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
			driver.switchTo().defaultContent();
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium")));
			WebElement wed = driver.findElement(By.id("text-input-02"));
			
			System.out.println(wed.getAttribute("value"));
			wed.click();
			driver.findElement(By.xpath("//*[text() = 'Los últimos 3 días']")).click();
			Assert.assertTrue(driver.findElement(By.id("text-input-01")).isDisplayed());
	}
	
	/*@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38410_360_View_Vista_360_de_facturación_clientes_individuos_Información_de_las_Cards_Visualizar_campos(){
		 CustomerCare CP = new CustomerCare (driver);
			try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
			CP.elegircuenta("aaaaFernando Care");
			try {Thread.sleep(15000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
			Accounts aAcc = new Accounts (driver);
			aAcc.closeAccountServiceTabByName("Servicios");
			try {Thread.sleep(20000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			BasePage cambioFrameByID=new BasePage();
			aAcc.findAndClickButton("Facturación");
			driver.switchTo().defaultContent();
			try {Thread.sleep(15000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".console-card.active")));
		
			
	 WebElement asd = driver.findElement(By.cssSelector(".console-card.active"));
	 Assert.assertTrue(asd.getText().toLowerCase().contains("último vencimiento"));
	 Assert.assertTrue(asd.getText().toLowerCase().contains("recepción de factura"));
	 Assert.assertTrue(asd.getText().toLowerCase().contains("ciclo de facturación"));
	 Assert.assertTrue(asd.getText().toLowerCase().contains("servicios"));
	}*/

	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38440_360_View_360_Card_Servicio_Prepago_Flyout_AccionesCampañas() {
		CustomerCare CP = new CustomerCare (driver);
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		CP.elegircuenta("aaaaFernando Care");
		try {Thread.sleep(15000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".console-card.active")));
		WebElement wCon = driver.findElement(By.cssSelector(".console-card.active"));
		wCon.click();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-small-size--3-of-12.slds-medium-size--3-of-12.slds-large-size--3-of-12.flyout-actions")));
		WebElement wElement = driver.findElement(By.cssSelector(".slds-small-size--3-of-12.slds-medium-size--3-of-12.slds-large-size--3-of-12.flyout-actions"));
		Assert.assertTrue(wElement.getText().toLowerCase().contains("campañas"));
	}
	 
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38434_360_View_360_Card_Servicio_Prepago_Flyoutv_AccionesRecargar() {
		 CustomerCare CP = new CustomerCare (driver);
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		CP.elegircuenta("aaaaFernando Care");
		try {Thread.sleep(15000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".console-card.active")));
		WebElement wCon = driver.findElement(By.cssSelector(".console-card.active"));
		wCon.click();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-small-size--3-of-12.slds-medium-size--3-of-12.slds-large-size--3-of-12.flyout-actions")));
		WebElement wElement = driver.findElement(By.cssSelector(".slds-small-size--3-of-12.slds-medium-size--3-of-12.slds-large-size--3-of-12.flyout-actions"));
		Assert.assertTrue(wElement.getText().toLowerCase().contains("recargar"));
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38436_360_View_360_Card_Servicio_Prepago_Flyoutv_AccionesGestiones() {
		CustomerCare CP = new CustomerCare (driver);
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		CP.elegircuenta("aaaaFernando Care");
		try {Thread.sleep(15000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".console-card.active")));
		WebElement wCon = driver.findElement(By.cssSelector(".console-card.active"));
		wCon.click();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-small-size--3-of-12.slds-medium-size--3-of-12.slds-large-size--3-of-12.flyout-actions")));
		WebElement wElement = driver.findElement(By.cssSelector(".slds-small-size--3-of-12.slds-medium-size--3-of-12.slds-large-size--3-of-12.flyout-actions"));
		Assert.assertTrue(wElement.getText().toLowerCase().contains("gestiones"));
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38612_Diseño_Seleccion_asset_Numero_de_linea_como_identificador_principal() {
		CustomerCare CP = new CustomerCare (driver);
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		CP.elegircuenta("aaaaFernando Care");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".header-right")));
		WebElement tex = driver.findElement(By.cssSelector(".slds-text-body_regular.account-number.slds-p-bottom--xx-small"));
		Assert.assertTrue(tex.getText().toLowerCase().equals("línea"));
		List<WebElement> asf = driver.findElements(By.cssSelector(".slds-text-heading_medium"));
		for(int i=0 ; i<asf.size(); i++) {
		}
		String lin = "(011) 1566 - 6666";	
		Assert.assertTrue(asf.get(2).getText().contains(lin));
	 }
		
}