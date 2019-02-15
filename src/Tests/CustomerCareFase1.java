package Tests;

import static org.testng.Assert.assertTrue;

import java.text.ParseException;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.CustomerCare;
import Pages.setConexion;

public class CustomerCareFase1 extends TestBase {

	private CustomerCare cc;
	
	
	@AfterClass(groups = {"CustomerCare", "Vista360Layout"})
	public void tearDown() {
		driver.quit();
		sleep(5000);
	}

	@BeforeClass(groups = {"CustomerCare", "Vista360Layout"})
	public void init() throws Exception {
		driver = setConexion.setupEze();
		sleep(5000);
		cc = new CustomerCare(driver);
		login(driver);
		sleep(10000);
		String a = driver.findElement(By.id("tsidLabel")).getText();
		driver.findElement(By.id("tsidLabel")).click();
		if (a.equals("Ventas")) {
			driver.findElement(By.xpath("//a[@href=\'/console?tsid=02uc0000000D6Hd\']")).click();
		} else {
			driver.findElement(By.xpath("//a[@href=\'/home/home.jsp?tsid=02u41000000QWha\']")).click();
			sleep(4000);
			driver.findElement(By.id("tsidLabel")).click();
			driver.findElement(By.xpath("//a[@href=\'/console?tsid=02uc0000000D6Hd\']")).click();
		}
		sleep(10000);
		cc.elegircuenta("aaaaFernando Care");
		sleep(10000);
	}

	@BeforeMethod(groups = {"CustomerCare", "Vista360Layout"})
	public void setup() {
		sleep(10000);
		driver.switchTo().defaultContent();
	}

	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7059_Verificar_Visualizar_Panel_Promociones() {
		cc.openleftpanel();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".via-slds-story-cards--header.spacer.acct-spacer")));
		List<WebElement> promociones = driver.findElements(By.cssSelector(".via-slds-story-cards--header.spacer.acct-spacer"));
		Assert.assertTrue(promociones.get(1).isDisplayed());
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7058_Visualizar_Panel_Servicios_Activos() {
		sleep(5000);
		List <WebElement> element = driver.findElements(By.className("x-tab-right"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().contains("Servicios")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7200_VerifyDisplayLogo() {
		sleep(10000);
		driver.findElement(By.className("sd_custom_logo"));
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7060_Visualizar_Panel_Alertas() {
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("ta-alertMessage-content")));
		WebElement element = driver.findElement(By.className("ta-alertMessage-content"));
		Assert.assertTrue(element.getText().contains("Se aproxima la fecha de pago"));
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7201_VerifyDisplayfilterAccounts() {
		sleep(5000);
		cc.openleftpanel();
		driver.switchTo().frame(cambioFrame(driver, By.className("account-select-table")));
		List<WebElement> filtro = driver.findElements(By.className("account-select-table"));
		Assert.assertTrue(filtro.get(0).isDisplayed());
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7066_VerifyDisplayPanelAccountsClient() {
		cc.openleftpanel();
		driver.switchTo().frame(cambioFrame(driver, By.className("account-select-container")));
		Assert.assertTrue(driver.findElement(By.className("account-select-container")).isDisplayed());
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7054_VerifyDisplayPanelBusinessData() {
		cc.openleftpanel();
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		driver.findElement(By.className("profile-box"));
	}

	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7061_Visualizar_Panel_Sesiones_Guiadas() {
		driver.switchTo().frame(cambioFrame(driver, By.className("actions-content")));
		Assert.assertTrue(driver.findElement(By.className("actions-content")).isDisplayed());
	}

	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7062_Visualizar_Panel_Gestiones_abandonadas() {
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".via-slds-story-cards--header.spacer")));
		List <WebElement> element = driver.findElements(By.cssSelector(".via-slds-story-cards--header.spacer"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().contains("Gestiones Abandonadas")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7063_Contraccion_Paneles() {
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-p-right--x-small.via-slds-story-cards--header-title")));
		driver.findElement(By.cssSelector(".slds-p-right--x-small.spacer")).click();
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-grid.slds-p-around--small.slds-wrap.via-slds-story-cards--header.slds-theme--shade.profile-tags-header"));
		element.get(1).click();
		List <WebElement> ua = driver.findElements(By.cssSelector(".slds-p-right--x-small.via-slds-story-cards--header-title"));
		if (ua.get(1).getText().contains("Últimas Gestiones")) {
			ua.get(1).click();
		}
		driver.switchTo().frame(cambioFrame(driver, By.className("actions-content")));
		List <WebElement> x = driver.findElements(By.cssSelector(".via-slds-story-cards--header.spacer"));
		x.get(0).click();
		x.get(1).click();
		x.get(2).click();
		try {
			Assert.assertFalse(driver.findElement(By.className("actions-content")).isDisplayed());
		} catch (org.openqa.selenium.NoSuchElementException e) {
			Assert.assertTrue(true);
		}
	}

	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7064_Contraccion_Panel_Datos_Comerciales() {
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		Assert.assertTrue(driver.findElement(By.className("profile-box")).isDisplayed());
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7065_Expansion_Paneles() {
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-p-right--x-small.via-slds-story-cards--header-title")));
		driver.findElement(By.cssSelector(".slds-p-right--x-small.spacer")).click();
		driver.findElement(By.cssSelector(".slds-p-right--x-small.spacer")).click();
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-grid.slds-p-around--small.slds-wrap.via-slds-story-cards--header.slds-theme--shade.profile-tags-header"));
		element.get(1).click();
		List <WebElement> ua = driver.findElements(By.cssSelector(".slds-p-right--x-small.via-slds-story-cards--header-title"));
		if (ua.get(1).getText().contains("Últimas Gestiones")) {
			ua.get(1).click();
			ua.get(1).click();
		}
		driver.switchTo().frame(cambioFrame(driver, By.className("actions-content")));
		List <WebElement> x = driver.findElements(By.cssSelector(".via-slds-story-cards--header.spacer"));
		x.get(0).click();
		x.get(1).click();
		x.get(1).click();
		x.get(2).click();
		x.get(2).click();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame(driver, By.className("actions-content")));
		Assert.assertTrue(driver.findElement(By.className("actions-content")).isDisplayed());
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7074_Key_Metrics_Visualizar_Picklist() {
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-01")));
		Assert.assertTrue(driver.findElement(By.id("text-input-01")).isDisplayed());
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7075_Key_Metrics_Funcionamiento_Picklist() {
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-p-right--x-small.via-slds-story-cards--header-title")));
		driver.findElement(By.xpath("/html/body/div/div[1]/ng-include/div[5]/ng-include/div/div[1]/div/div[1]/div")).click();
		driver.findElement(By.id("text-input-01")).click();
		List <WebElement> x = driver.findElements(By.cssSelector(".slds-lookup__item-action.slds-lookup__item-action--label.customer-story-label"));
		for (WebElement a : x) {
			if (a.getText().toLowerCase().contains("preocupaciones")) {
				a.click();
			}
		}
		WebElement element = driver.findElement(By.className("profile-tags-container"));
		Assert.assertTrue(element.getText().toLowerCase().contains("preocupaciones"));
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7076_Key_Metrics_Visualizar_boton_Añadir_Nuevo_Intereses_personales() {
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-p-right--x-small.via-slds-story-cards--header-title")));
		driver.findElement(By.id("text-input-01")).click();
		List <WebElement> x = driver.findElements(By.cssSelector(".slds-lookup__item-action.slds-lookup__item-action--label.customer-story-label"));
		for (WebElement a : x) {
			if (a.getText().toLowerCase().contains("todos")) {
				a.click();
			}
		}
		sleep(3000);
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.profile-tags-btn"));
		Assert.assertTrue(element.get(5).isDisplayed());
	}

	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7078_Key_Metrics_Visualizar_boton_Añadir_Nuevo_Criterios_de_compra() {
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-p-right--x-small.via-slds-story-cards--header-title")));
		driver.findElement(By.id("text-input-01")).click();
		List <WebElement> x = driver.findElements(By.cssSelector(".slds-lookup__item-action.slds-lookup__item-action--label.customer-story-label"));
		for (WebElement a : x) {
			if (a.getText().toLowerCase().contains("todos")) {
				a.click();
			}
		}
		sleep(3000);
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.profile-tags-btn"));
		Assert.assertTrue(element.get(3).isDisplayed());
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7079_Key_Metrics_Visualizar_boton_Añadir_Nuevo_Familia() {
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-p-right--x-small.via-slds-story-cards--header-title")));
		driver.findElement(By.id("text-input-01")).click();
		List <WebElement> x = driver.findElements(By.cssSelector(".slds-lookup__item-action.slds-lookup__item-action--label.customer-story-label"));
		for (WebElement a : x) {
			if (a.getText().toLowerCase().contains("todos")) {
				a.click();
			}
		}
		sleep(3000);
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.profile-tags-btn"));
		Assert.assertTrue(element.get(4).isDisplayed());
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7080_Key_Metrics_Visualizar_boton_Añadir_Nuevo_Productos_de_interes() {
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-p-right--x-small.via-slds-story-cards--header-title")));
		driver.findElement(By.id("text-input-01")).click();
		List <WebElement> x = driver.findElements(By.cssSelector(".slds-lookup__item-action.slds-lookup__item-action--label.customer-story-label"));
		for (WebElement a : x) {
			if (a.getText().toLowerCase().contains("todos")) {
				a.click();
			}
		}
		sleep(3000);
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.profile-tags-btn"));
		Assert.assertTrue(element.get(1).isDisplayed());
	}

	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7081_Key_Metrics_Visualizar_boton_Añadir_Nuevo_Preocupaciones() {
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-p-right--x-small.via-slds-story-cards--header-title")));
		driver.findElement(By.id("text-input-01")).click();
		List <WebElement> x = driver.findElements(By.cssSelector(".slds-lookup__item-action.slds-lookup__item-action--label.customer-story-label"));
		for (WebElement a : x) {
			if (a.getText().toLowerCase().contains("todos")) {
				a.click();
			}
		}
		sleep(3000);
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.profile-tags-btn"));
		Assert.assertTrue(element.get(0).isDisplayed());
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7082_Visualizar_Fecha_de_vencimiento() throws ParseException {
		cc.openleftpanel();
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-text-body_regular.story-field"));
		for (WebElement x : element) {
			if (x.getText().contains("  /  /    ")) {
				Assert.assertTrue(x.isDisplayed());
			}
		}		
	}

	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7120_Key_Metrics_Panel_Perfil_Visualizar_Scroll() {
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		JavascriptExecutor javascript = (JavascriptExecutor) driver;
		Boolean VertscrollStatus = (Boolean) javascript.executeScript("return document.documentElement.scrollHeight>document.documentElement.clientHeight;");
		assertTrue(VertscrollStatus);
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7144_Customer_Account_Management_Customer_Segmentation_Estado_Activo_Usuario_Externo() {
		List <WebElement> pest = driver.findElements(By.className("x-tab-left"));
        pest.get(3).click();
        driver.switchTo().frame(cambioFrame(driver, By.className("detailList")));
		WebElement status = driver.findElement(By.xpath("//*[@id=\"ep_Account_View_j_id4\"]/div[2]/div[2]/table/tbody/tr[2]/td[4]"));
		Assert.assertTrue(status.getText().equals("Active"));
	}	
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
    public void TS7148_Customer_Account_Management_Customer_Segmentation_Estado_inactivo_Usuario_Externo() {
        cc.cerrarTodasLasPestanas();
        cc.elegircuenta("aaaaAndres Care");
        List <WebElement> pest = driver.findElements(By.className("x-tab-left"));
        pest.get(3).click();
        driver.switchTo().frame(cambioFrame(driver, By.className("detailList")));
		WebElement status = driver.findElement(By.xpath("//*[@id=\"ep_Account_View_j_id4\"]/div[2]/div[2]/table/tbody/tr[2]/td[4]"));
		Assert.assertTrue(status.getText().equals("Inactive"));
		cc.cerrarTodasLasPestanas();
		cc.elegircuenta("aaaaFernando Care");
    }
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7069_ValidationButtons () {
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		List<WebElement> botones = driver.findElements(By.className("profile-box"));
		for (WebElement x : botones) {
			Assert.assertTrue(x.getText().toLowerCase().contains("actualizar datos"));
			Assert.assertTrue(x.getText().toLowerCase().contains("reseteo clave"));
		}
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7068_ValidationFields (){
		List <WebElement> element = driver.findElements(By.className("acct-info"));
		for (WebElement x : element) {
			Assert.assertTrue(x.getText().toLowerCase().contains("correo electrónico"));
			Assert.assertTrue(x.getText().toLowerCase().contains("teléfono"));
			Assert.assertTrue(x.getText().toLowerCase().contains("club personal"));
			Assert.assertTrue(x.getText().toLowerCase().contains("categoría"));
		}
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7070_ValidationClubPersonalBasico (){
		sleep(10000);
		cc.cerrarultimapestana();
		cc.elegircuenta("aaaaAndres Care");
		List<WebElement> profileinfo = driver.findElements(By.className("acct-info"));
		for (WebElement x : profileinfo) {
			Assert.assertTrue(x.getText().toLowerCase().contains("básico"));
		}
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7072_ValidationNPS(){
		driver.switchTo().frame(cambioFrame(driver, By.className("account-detail-content")));
		List <WebElement> profileinfo = driver.findElements(By.className("account-detail-content"));
		for(int i=1; i<20; i++){
			String b = Integer.toString(i);
			Assert.assertFalse(profileinfo.get(0).getText().contains("-"+b));
		}
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7071_ValidationClubPersonalPremium (){
		sleep(10000);
		cc.cerrarultimapestana();
		cc.elegircuenta("aaaaAndres Care");
		List<WebElement> profileinfo = driver.findElements(By.className("acct-info"));
		for (WebElement x : profileinfo) {
			Assert.assertTrue(x.getText().toLowerCase().contains("premium"));
		}
		cc.cerrarultimapestana();
		cc.elegircuenta("aaaaFernando Care");
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7126_ValidationPerfilPanel (){
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box-details")));
		Assert.assertTrue(driver.findElement(By.className("profile-box-details")).isDisplayed());
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7106_ValidationNumberEstatus (){
		driver.switchTo().frame(cambioFrame(driver, By.className("story-field")));
		List<WebElement> profileInfo = driver.findElements(By.className("story-field"));
		boolean a = false, b = false;
		for (WebElement x : profileInfo) {
			if (x.getText().toLowerCase().contains("new")) {
				a = true;
			}
			if (x.getText().toLowerCase().contains("closed")) {
				b = true;
			}
		}
		Assert.assertTrue(a && b);
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7112_ValidationDateFormat (){
		List<WebElement> profileinfo = driver.findElements(By.className("story-field"));	
		for(int i=1; i<profileinfo.size(); i+=2){
			String b = (profileinfo.get(i).getText());		
			String datePattern = "\\d{2}/\\d{2}/\\d{4}";
			String date1 = b;
			Boolean isDate1 = date1.matches(datePattern);
			Assert.assertTrue(isDate1);	
		}
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7108_ValidationCustomerTransactionsViewFilter (){
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box-details")));
		driver.findElement(By.id("text-input-01"));	
		driver.switchTo().defaultContent();
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7110_ValidationCustomerTransactionsScroll(){
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box-details")));
	    ((JavascriptExecutor)driver).executeScript("scroll(0,400)");
	    JavascriptExecutor javascript = (JavascriptExecutor) driver;
	    Boolean VertscrollStatus = (Boolean) javascript.executeScript("return document.documentElement.scrollHeight>document.documentElement.clientHeight;");
	    assertTrue(VertscrollStatus);
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7113_ValidationCustomerTransactionsIconType(){
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box-details")));
		driver.findElement(By.className("slds-icon_container"));
	}
}
