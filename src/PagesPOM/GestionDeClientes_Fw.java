package PagesPOM;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import Pages.Accounts;
import Pages.CustomerCare;
import Tests.TestBase;

public class GestionDeClientes_Fw extends BasePageFw {

//ELEMENTOS 
	
	final String locator_cerrarPanelDerecho= "[class='x-layout-split x-layout-split-east x-splitbar-h']";
	@FindBy (css= locator_cerrarPanelDerecho)
	private WebElement cerrarPanelDerecho;
	
	
	final String locator_BtnBuscar= "SearchClientsDummy";
	@FindBy (how =How.ID,using= locator_BtnBuscar)
	private WebElement BtnBuscar;
	
	final String locator_DNI= "SearchClientDocumentNumber";
	@FindBy(how=How.ID, using=locator_DNI)
	private WebElement DNI;
	
	final String locator_DNIbuscador= "SearchClientDocumentType";
	@FindBy(how= How.ID, using =locator_DNIbuscador)
	private WebElement DNIbuscador;
	
	final String ref_CajonDeAplicaciones= "//span[@id='tsidLabel']"; 
	@FindBy (xpath = ref_CajonDeAplicaciones)
	private WebElement cajonDeAplicaciones;

	final String locator_TipoDoc= "SearchClientDocumentType"; 
	@FindBy (id = locator_TipoDoc)
	private WebElement tipoDoc;

	final String locator_listaMenuIzq= "[class='x-menu-list'] li";
	@FindBy (css = locator_listaMenuIzq)
	private List<WebElement> listaMenuIzq;
	
	final String ref_elementosCajon= "[class='menuButtonMenuLink']";
	@FindBy (how = How.CSS, using = ref_elementosCajon)
	private List<WebElement>   elementosCajon;
	
	final String locator_MenuDesplegableIzq ="[class='navigator-sbmenu']";
	@FindBy (how = How.CSS, using = locator_MenuDesplegableIzq)
	private WebElement MenuDesplegableIzq;
	
	final String locator_DataImputGestion ="dataInput";
	@FindBy (how = How.ID, using = locator_DataImputGestion)
	private WebElement dataImputGestion;
	
	final String locator_Pestanas ="[class*='x-tab-strip-closable']";
	@FindBy (how = How.CSS, using = locator_Pestanas)
	private List<WebElement> pestanas;
	
	final String locator_PestaPeque = "[class*='x-tab-strip-closable x-tab-strip-active ']";
	@FindBy (how = How.CSS, using = locator_PestaPeque)
	private List<WebElement> pestaPeque;

	final String locator_elementosMenuIzq = "[class='support-servicedesk-navigator'] table";
	@FindBy (how = How.CSS, using = locator_elementosMenuIzq)
	private WebElement MenuIzq ;
	
	final String locator_iframes = "iframe";
	@FindBy (how = How.TAG_NAME, using = locator_iframes)
	private List<WebElement> iframes ;
	

	final String locator_Bodys="[class='hasMotif homeTab homepage  ext-webkit ext-chrome net-withGlobalHeader sfdcBody brandQuaternaryBgr']";
	@FindBy (how = How.CSS, using = locator_Bodys)
	private List<WebElement> bodys;

	
	
	final String locator_BotonesInf = "[class='slds-grid slds-m-bottom_small slds-wrap cards-container']";
	@FindBy (how = How.CSS, using = locator_BotonesInf)
	private WebElement botonesInf ;
	
			
//CONTRUCTOR
	public GestionDeClientes_Fw(WebDriver driver) {
		super(driver);
		super.setDriver(driver);
		//PageFactory.initElements(driver, this);
		PageFactory.initElements(getDriver(), this);
		super.setFluentWait(new FluentWait<WebDriver>(driver));
		super.getFluentWait().withTimeout(30, TimeUnit.SECONDS)
		.pollingEvery(500, TimeUnit.MILLISECONDS)
		.ignoring(NoSuchElementException.class)
		.ignoring(NullPointerException.class)
//		.ignoring(TimeoutException.class)
		.ignoring(ElementNotVisibleException.class);

	}
	
//METODOS
	
	public WebElement getTipoDoc() {
		driver.switchTo().frame(cambioFrame(By.id(locator_TipoDoc)));
		fluentWait.until(ExpectedConditions.elementToBeClickable(tipoDoc));
		
		return tipoDoc;
	}	


	public void setMenuIzq(WebElement menuIzq) {
		MenuIzq = menuIzq;
	}

	public WebElement getCajon() {

		fluentWait.until(ExpectedConditions.elementToBeClickable(cajonDeAplicaciones));
		return cajonDeAplicaciones;
	}
	
	public List<WebElement> getElementosCajon(){
		fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(ref_elementosCajon), 0));
		return elementosCajon;
	}
	
	public void cajonDeAplicaciones(String nombreVisible) {
		driver.switchTo().defaultContent();
		 getCajon().click();
		if(!super.containText(this.getElementosCajon(), nombreVisible )) {
			 getCajon().click();
			System.out.println("no hace  falta  cambiar");
		}else{
			super.getBuscarElementoPorText(this.getElementosCajon(), nombreVisible).click();
		};
	}
		
	public void cerrarPestaniaGestion(WebDriver driver) {//copiado de SalesBase Cierra todas las pestañas de gestion
		driver.switchTo().defaultContent();
		try{
			fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(locator_Pestanas), 0));
			
			for (WebElement UnB : pestanas) {
				try {
					//System.out.println(UnB.getText());
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", UnB.findElement(By.className("x-tab-strip-close")));	
				}catch(Exception ex1) {} 
			}
		}catch(Exception e){
		//	System.out.println("pestanas cerradas");
		}
		
	}
	
	
	public void clickMenuIzq() {
		driver.switchTo().defaultContent();
		fluentWait.until(ExpectedConditions.elementToBeClickable(MenuIzq));
		//System.out.println("x= "+MenuIzq.getLocation().getX()+"y= "+MenuIzq.getLocation().getY());
		super.getAction().moveToElement(MenuIzq).moveByOffset(MenuIzq.getLocation().getX()+110, MenuIzq.getLocation().getY()-90).click().build().perform();
		super.getEjecutorJavaScipt().executeScript("arguments[0].click();", this.MenuIzq);
		
	}
	
	
	public void selectMenuIzq(String opcionVisible) {
		clickMenuIzq();
		fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(this.locator_listaMenuIzq), 0));
		try{
			super.getBuscarElementoPorText(listaMenuIzq, opcionVisible).click();
		}catch(Exception e) {
			System.out.println("no se encuentra elemento verificar que coincida con el texto visible");
		}
	}
	
	public void irGestionClientes() {
		TestBase tb = new TestBase();
		driver.switchTo().defaultContent();
		switchToFrameBySrc("/home/home.jsp?i");
		switchToFrameBySrc("https://telecomcrm--uat02-");
		fluentWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Gesti\u00f3n de Clientes')]")));
		driver.findElement(By.xpath("//button[contains(text(),'Gesti')]")).click();
		tb.sleepCambioDeFrame(driver, "SearchClientDocumentType", 10, 0);
	}
	
	public void BuscarCuenta(String Type, String NDNI){
		fluentWait.until(ExpectedConditions.elementToBeClickable(By.id(locator_DNI)));
		getSelect(DNIbuscador).selectByVisibleText(Type);
		DNI.sendKeys(NDNI);
		fluentWait.until(ExpectedConditions.elementToBeClickable(By.id(locator_BtnBuscar)));
		BtnBuscar.click();

	}
	public void BuscarCuentaConLinea(String Type, String NDNI, String numlinea){//modificar para que funcione
		fluentWait.until(ExpectedConditions.elementToBeClickable(By.id(locator_DNI)));
		getSelect(DNIbuscador).selectByVisibleText(Type);
		DNI.sendKeys(NDNI);
		fluentWait.until(ExpectedConditions.elementToBeClickable(By.id(locator_BtnBuscar)));
		BtnBuscar.click();

	}
	public void cerrarPanelDerecho() throws AWTException {		
		driver.switchTo().defaultContent();
		fluentWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator_cerrarPanelDerecho)));
		Robot robot = getRobot();																									
		WebElement boton  = this.cerrarPanelDerecho;	//SELECCIONA LA BARRA PARA HACERLA VISIBLE
		robot.mouseMove((int) (boton.getLocation().getX()*1.01),(int) (boton.getLocation().getY()*4));					// SE MUEVE A LA POSICION 
		if(boton.getAttribute("class").compareTo("x-layout-split x-layout-split-east x-splitbar-h x-layout-split-over")==0) {//VERIFICA QUE ESTE SELECCIONADO Y VISIBLE
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);																	//HACE CLICK PARA COLAPSAR EL PANEL
			System.out.println("hace click la concha de tu hermana");

		}
	}
	public void cerrarPanelDerecho2() throws AWTException {		
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}		
		driver.switchTo().defaultContent();
		//fluentWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator_cerrarPanelDerecho)));
		Robot robot = new Robot();																									
		WebElement boton  = driver.findElement(By.cssSelector(".x-layout-split.x-layout-split-east.x-splitbar-h"));		//SELECCIONA LA BARRA PARA HACERLA VISIBLE
		robot.mouseMove((int) (boton.getLocation().getX()*1.01),(int) (boton.getLocation().getY()*4));					// SE MUEVE A LA POSICION 
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		if(boton.getAttribute("class").compareTo("x-layout-split x-layout-split-east x-splitbar-h x-layout-split-over")==0) {//VERIFICA QUE ESTE SELECCIONADO Y VISIBLE
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);																	//HACE CLICK PARA COLAPSAR EL PANEL
			try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		}
	}
	
	public void switchToFrameBySrc(String src) {//el ruben ZAPE!!
		WebElement frame = fluentWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("iframe[src*='"+src+"']")));
		//WebElement frame = driver.findElement(By.cssSelector("iframe[src*='"+src+"']"));
		fluentWait.until(ExpectedConditions.visibilityOf(frame));
		//System.out.println("lo encontro");
		driver.switchTo().frame(frame);
	}
	
	
	
}
