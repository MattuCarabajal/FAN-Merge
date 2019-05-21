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

//------------------------------------------------------------Listado Ambientes---------------------------------------------------------------------------
	
	String[] listaAmbientes = {"sit02","uat02"};
	
//------------------------------------------------------------ELEMENTOS---------------------------------------------
	
	final String locator_cerrarPanelDerecho= "[class='x-layout-split x-layout-split-east x-splitbar-h']";
	@FindBy (css= locator_cerrarPanelDerecho)
	private WebElement cerrarPanelDerecho;

	
	
	final String locator_menuAplicaciones= "tsidLabel";
	@FindBy (id= locator_menuAplicaciones)
	private WebElement menuAplicaciones;
	
	final String locator_volverFan= "BackToServiceDesk_Tab";
	@FindBy (id= locator_volverFan)
	private WebElement volverFan;
	
	final String locator_BtnBuscar= "SearchClientsDummy";
	@FindBy (how =How.ID,using= locator_BtnBuscar)
	private WebElement BtnBuscar;
	
	final String locator_DNI= "SearchClientDocumentNumber";
	@FindBy(how=How.ID, using=locator_DNI)
	private WebElement DNI;
	
	final String locator_DNIbuscador= "SearchClientDocumentType";
	@FindBy(how= How.ID, using =locator_DNIbuscador)
	private WebElement DNIbuscador;
	
	final String ref_CajonDeAplicaciones= "[class='menuButton menuButtonRounded appSwitcher']"; 
	@FindBy (css = ref_CajonDeAplicaciones)
	private WebElement cajonDeAplicaciones;

	final String ref_aplicaciones= "menuButtonMenu menuWidthExtended"; 
	@FindBy (xpath = ref_aplicaciones)
	private List<WebElement> aplicaciones;
	
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
	
	final String locator_razonSocial = "[class='slds-tree__item ng-scope']";
	@FindBy (how = How.CSS, using = locator_razonSocial)
	private WebElement razonSocial ;
	
	private WebDriverWait wait;
			
//-------------------------------------------------------------------CONTRUCTOR
	public GestionDeClientes_Fw(WebDriver driver) {
		super(driver);
		super.setDriver(driver);
		wait = new WebDriverWait(driver,30);
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
	
//--------------------------------------------------------------------METODOS
	
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
	
	public void irConsolaFanSit02() {
		driver.switchTo().defaultContent();
		 getCajon().click();
		if(!super.macheaText(this.getElementosCajon(), "Consola FAN" )) {
			System.out.println("no hace  falta  cambiar");
		}else{
			super.getBuscarElementoPorText(this.getElementosCajon(), "Consola FAN").click();
		};
	}
	
	public void irConsolaFanUat02(){
		 fluentWait.until(ExpectedConditions.elementToBeClickable(menuAplicaciones));
			if (driver.findElement(By.id("tsidLabel")).getText().equalsIgnoreCase("Consola FAN")) {
				fluentWait.until(ExpectedConditions.elementToBeClickable(volverFan));
				driver.findElement(By.id("BackToServiceDesk_Tab")).click();
			} else {
				fluentWait.until(ExpectedConditions.elementToBeClickable(cajonDeAplicaciones));
				driver.findElement(By.cssSelector(ref_CajonDeAplicaciones)).click();
				fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(ref_aplicaciones), 0));
				for (WebElement x : driver.findElement(By.cssSelector(ref_aplicaciones))
						.findElements(By.tagName("a"))) {
					if (x.getText().equalsIgnoreCase("Consola FAN")) {
						x.click();
					}
				}
			}
	 }
	
	public void cerrarPestaniaGestion(WebDriver driver) {//copiado de SalesBase Cierra todas las pesta�as de gestion
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
		super.getAction().moveToElement(MenuIzq).moveByOffset(MenuIzq.getLocation().getX()+110, MenuIzq.getLocation().getY()-90).click().build().perform();
		super.getEjecutorJavaScipt().executeScript("arguments[0].click();", this.MenuIzq);
		
	}
	
	public void selectMenuIzq(String opcionVisible) {
		clickMenuIzq();
		fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(this.locator_listaMenuIzq), 0));
		try{
			fluentWait.until(ExpectedConditions.elementToBeClickable(listaMenuIzq.get(0)));
			super.getBuscarElementoPorText(listaMenuIzq, opcionVisible).click();
		}catch(Exception e) {
			System.out.println("no se encuentra elemento verificar que coincida con el texto visible");
		}
	}
	
	public void irGestionClientes() {

		driver.switchTo().defaultContent();
		switchToFrameBySrc("/home/home.jsp?i");
		switch (this.getIndexAmbienteForList()) {
		case 0:
			switchToFrameBySrc("https://telecomcrm--sit02--c.cs91.visual.force.com/a");
			System.out.println("Gestion Clinete Sit02");
			break;
		case 1:
			switchToFrameBySrc("https://telecomcrm--uat02-");
			System.out.println("Gestion cliente Uat");
			break;

		}
		fluentWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Gesti\u00f3n de Clientes')]")));
		driver.findElement(By.xpath("//button[contains(text(),'Gesti')]")).click();
	}
	
	public void BuscarCuenta(String Type, String NDNI){
		driver.switchTo().defaultContent();
		TestBase tb = new TestBase();
		tb.cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		fluentWait.until(ExpectedConditions.elementToBeClickable(By.id(locator_DNI)));
		getSelect(DNIbuscador).selectByVisibleText(Type);
		DNI.sendKeys(NDNI);
		fluentWait.until(ExpectedConditions.elementToBeClickable(By.id(locator_BtnBuscar)));
		BtnBuscar.click();
		fluentWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locator_razonSocial)));
		razonSocial.click();
		driver.switchTo().defaultContent();
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
	
	public void switchToFrameBySrc(String src) {//el Ruben ZAPE!!
		WebElement frame = fluentWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("iframe[src*='"+src+"']")));
		//WebElement frame = driver.findElement(By.cssSelector("iframe[src*='"+src+"']"));
		fluentWait.until(ExpectedConditions.visibilityOf(frame));
		//System.out.println("lo encontro");
		driver.switchTo().frame(frame);
	}
	
	private int getIndexAmbienteForList() {
		//compara la url con el listado de ambientes retorna el numero de Ambiente(ese listado esta al comienzo de la clase y funciona de acuerdao al orden de insercion)
		String url =driver.getCurrentUrl().toLowerCase();
		int index =0;
		int resp =0;
		for(String ambiente : listaAmbientes ) {
			if(url.contains(ambiente)) {
				resp= index;
			}else {
				index ++;
			}
		}
		return resp;
	}
	
	public void irAConsolaFAN() {//el Nico Manda!!
		switch (this.getIndexAmbienteForList()) {
		case 0:
			irConsolaFanSit02();
			//System.out.println("consola Sit02");
			break;
		case 1:
			irConsolaFanUat02();
			//System.out.println("consola Uat");
			break;

		}		
	}
	
	public void irAGestionEnCard(String sGestion) {
		driver.switchTo().defaultContent();
		switchToFrameBySrc("/apex/vlocity_cmt__ConsoleCards?Id=0010r000008A9jg&layout=ta-console-services&");
		fluentWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='card-top']")));
		WebElement card = driver.findElement(By.cssSelector("[class='card-top']"));
		card.click();
		fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='community-flyout-actions-card'] ul li"), 0));
		fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='card-info'] [class='actions'] li"), 0));
		fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='console-flyout active flyout'] [class='card-info'] [class*='slds-grid slds-grid--vertical slds-align-middle'] [class='items-card ng-not-empty ng-valid'] [class='slds-col'] button"), 0));
		List<WebElement> elementos = driver.findElements(By.cssSelector("[class='card-info'] [class='actions'] li"));
		elementos.addAll(driver.findElements(By.cssSelector("[class='community-flyout-actions-card'] ul li")));
		elementos.addAll(driver.findElements(By.cssSelector("[class='console-flyout active flyout'] [class='card-info'] [class*='slds-grid slds-grid--vertical slds-align-middle'] [class='items-card ng-not-empty ng-valid'] [class='slds-col'] button")));
		System.out.println(clickElementoPorTextExacto(elementos, sGestion));		
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();

	}
	
	public WebDriverWait getWait() {
		return  wait;
		
	}
	
}
