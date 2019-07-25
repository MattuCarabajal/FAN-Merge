package PagesPOM;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
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
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.WebDriverWait;

import Pages.Accounts;
import Pages.CBS;
import Pages.CustomerCare;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class GestionDeClientes_Fw extends BasePageFw {

	//------------------------------------------------------------Listado Ambientes---------------------------------------------------------------------------
	
	String[] listaAmbientes = {"sit02","uat02"};
	
	//------------------------------------------------------------ELEMENTOS---------------------------------------------
	
	private TestBase tb;
	
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

	final String locator_listaMenuIzq= "[class='x-menu-item-text'][id*='ext-gen']";
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
	
	final String locator_razonSocial = "[class='slds-tree__item ng-scope'] div";
	@FindBy (how = How.CSS, using = locator_razonSocial)
	private WebElement razonSocial ;
	
	private WebDriverWait wait;
			
	//-------------------------------------------------------------------CONTRUCTOR
	
	public GestionDeClientes_Fw(WebDriver driver) {
		super(driver);
		super.setDriver(driver);
		tb = new TestBase();
		wait = new WebDriverWait(driver,30);
		//PageFactory.initElements(driver, this);
		PageFactory.initElements(getDriver(), this);
		super.setFluentWait(new FluentWait<WebDriver>(driver));
		super.getFluentWait().withTimeout(50, TimeUnit.SECONDS)
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
		if(!super.matchText(this.getElementosCajon(), "Consola FAN" )) {
			System.out.println("no hace  falta  cambiar");
		}else{
			super.getBuscarElementoPorText(this.getElementosCajon(), "Consola FAN").click();
		}
	}
	
	public void irConsolaFanUat02(){
		tb = new TestBase();
		tb.clickBy(driver, By.xpath("//*[contains(text(),'a Consola FAN')]"), 0);
	 }
	
	public void cerrarPestaniaGestion(WebDriver driver) {//copiado de SalesBase Cierra todas las pesta�as de gestion
		driver.switchTo().defaultContent();
		try{
			fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(locator_Pestanas), 0));
			for (WebElement pestana : pestanas) {
				try {
					//System.out.println(pestana.getText());
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", pestana.findElement(By.className("x-tab-strip-close")));	
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
		tb = new TestBase();
		try {Thread.sleep(5000);} catch (InterruptedException e1) {}
		clickMenuIzq();
		try {
			try {Thread.sleep(3000);} catch (InterruptedException e1) {}
			WebElement select = driver.findElement(By.xpath("//li//span[contains(text(),'" + opcionVisible + "')]"));
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("arguments[0].click();", select);
		} catch(Exception e) {
			System.out.println("no se encuentra elemento verificar que coincida con el texto visible");
		}
	}
	
	public void irGestionClientes() {
		tb = new TestBase();
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
		tb.clickBy(driver, By.xpath("//button[contains(text(),'Gesti\u00f3n de Clientes')]"), 0);
	}
	
	public void BuscarCuenta(String Type, String NDNI){
		TestBase tb = new TestBase();
		tb.cambioDeFrame(driver, By.id("SearchClientDocumentType"), -10);
		fluentWait.until(ExpectedConditions.elementToBeClickable(By.id(locator_DNI)));
		getSelect(DNIbuscador).selectByVisibleText(Type);
		tb.esperarElemento(driver, By.id("SearchClientDocumentNumber"), 0);
		DNI.sendKeys(NDNI);
		tb.esperarElemento(driver, By.id(locator_BtnBuscar), 0);
		//fluentWait.until(ExpectedConditions.elementToBeClickable(By.id(locator_BtnBuscar)));
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
		Robot robot = getRobot();																								
		driver.switchTo().defaultContent();
		fluentWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator_cerrarPanelDerecho)));
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
				resp = index;
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
		tb = new TestBase();
		tb.cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		tb.sleep(2000);
		fluentWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='card-top']")));
		driver.findElement(By.cssSelector("[class='card-top']")).click();
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
	
	public void irAGestionEnCardPorNumeroDeLinea(String sGestion, String sLinea) {
		tb = new TestBase();
		tb.cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='card-top']"),1));
		List<WebElement> cards = driver.findElements(By.cssSelector("[class*='console-card active']"));
		WebElement cardPorLinea= getBuscarElementoPorText(listaDeElementosPorText(cards, sLinea),"Activo");
		boolean resp=false;
		try{
			if(cardPorLinea.getText().contains("tarjeta")||cardPorLinea.getText().contains("Tarjeta")) {
				resp =true;
			}
		}catch (Exception e) {
			
		}
		if(resp) {
			cardPorLinea.findElement(By.cssSelector("[class='card-top']")).click();
			fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='community-flyout-actions-card'] ul li"), 0));
			fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='card-info'] [class='actions'] li"), 0));
			fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='console-flyout active flyout'] [class='card-info'] [class*='slds-grid slds-grid--vertical slds-align-middle'] [class='items-card ng-not-empty ng-valid'] [class='slds-col'] button"), 0));
			List<WebElement> elementos = driver.findElements(By.cssSelector("[class='card-info'] [class='actions'] li"));
			elementos.addAll(driver.findElements(By.cssSelector("[class='community-flyout-actions-card'] ul li")));
			elementos.addAll(driver.findElements(By.cssSelector("[class='console-flyout active flyout'] [class='card-info'] [class*='slds-grid slds-grid--vertical slds-align-middle'] [class='items-card ng-not-empty ng-valid'] [class='slds-col'] button")));
			System.out.println(clickElementoPorTextExacto(elementos, sGestion));
			
		}else {
			cardPorLinea.findElement(By.cssSelector("[id='flecha'] i")).click(); 
			fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='community-flyout-actions-card'] ul li"), 0));
			fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='card-info-hybrid'] [class='actions'] li"), 0));
			fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-button slds-button--neutral ']"), 0));
//			List<WebElement> elementos = driver.findElements(By.cssSelector("[class='community-flyout-actions-card'] ul li"));
//			elementos.addAll(driver.findElements(By.cssSelector("[class='card-info-hybrid'] [class='actions'] li")));
//			elementos.addAll(driver.findElements(By.cssSelector("[class='slds-button slds-button--neutral ']")));
			driver.findElement(By.xpath("//a//span[contains(text(),'"+sGestion+"')]")).click();;

			//System.out.println(clickElementoPorTextExacto(elementos, sGestion));

			

		}

	}
	
	public void irRenovacionDeDatos(String sLinea) { 
		tb = new TestBase();
		tb.cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		List<WebElement> cards = driver.findElements(By.cssSelector("[class*='console-card active']"));
		WebElement cardPorLinea= getBuscarElementoPorText(listaDeElementosPorText(cards, sLinea),"Activo");
		try {
		fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='community-flyout-actions-card'] ul li"), 0));
		fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='card-info-hybrid'] [class='actions'] li"), 0));
		fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-button slds-button--neutral ']"), 0));
		driver.findElement(By.xpath("//a//span[contains(text(),'Renovacion de Datos')]")).click();;
		}catch(Exception e) {
			
			cardPorLinea.findElement(By.cssSelector("[id='flecha'] i")).click();
			fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='community-flyout-actions-card'] ul li"), 0));
			fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='card-info-hybrid'] [class='actions'] li"), 0));
			fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-button slds-button--neutral ']"), 0));
			driver.findElement(By.xpath("//a//span[contains(text(),'Renovacion de Datos')]")).click();;

		}
	}
	
	public void irSuscripciones(String sLinea) {
		tb = new TestBase();
		tb.cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		List<WebElement> cards = driver.findElements(By.cssSelector("[class*='console-card active']"));
		WebElement cardPorLinea= getBuscarElementoPorText(listaDeElementosPorText(cards, sLinea),"Activo");
		try {
		fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='community-flyout-actions-card'] ul li"), 0));
		fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='card-info-hybrid'] [class='actions'] li"), 0));
		fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-button slds-button--neutral ']"), 0));
		driver.findElement(By.xpath("//a//span[contains(text(),'Suscripciones')]")).click();;
		}catch(Exception e) {
			
			cardPorLinea.findElement(By.cssSelector("[id='flecha'] i")).click();
			fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='community-flyout-actions-card'] ul li"), 0));
			fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='card-info-hybrid'] [class='actions'] li"), 0));
			fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-button slds-button--neutral ']"), 0));
			driver.findElement(By.xpath("//a//span[contains(text(),'Suscripciones')]")).click();

		}
	}
	
	
	public boolean compararMegasEnCard() { 
		driver.switchTo().defaultContent(); 
		TestBase tb = new TestBase(); 
		tb.cambioDeFrame(driver, By.cssSelector("[id='flecha']"), 0); 
		fluentWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id='flecha'] i"))); 
		fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-text-body_regular value']"), 0)); 
		WebElement elemento = getBuscarElementoPorText(driver.findElements(By.cssSelector("[class='detail']")), "MB").findElements(By.cssSelector("[class='slds-text-body_regular value']")).get(1); 
		String megasAnterior = elemento.getText().substring(0,elemento.getText().length()-3); 
		 
		fluentWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id='flecha']"))); 
		driver.findElement(By.cssSelector("[id='flecha'] i")).click(); 
		 
		fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-gutters']"), 0)); 
		List<WebElement> elementos = driver.findElements(By.cssSelector("[class='slds-grid slds-gutters']")); 
		fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-gutters'] [class='slds-col slds-size_1-of-3'] [class='value']"), 0)); 
		elemento = getBuscarElementoPorText(elementos, "Internet").findElements(By.cssSelector("[class='slds-grid slds-gutters'] [class='slds-col slds-size_1-of-3'] [class='value']")).get(1); 
		String nuevomega = elemento.getText(); 
 
		boolean result =nuevomega.contains(megasAnterior); 
		assertTrue(result); 
		 
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();} 
		driver.switchTo().defaultContent(); 
		return result;
	}

	public boolean compararMegasEnCardPorLinea(String linea) { 
		driver.switchTo().defaultContent(); 
		boolean result =false;
		WebElement elemento =null;
		String megasAnterior="0";
		String nuevomega ="1";
		TestBase tb = new TestBase(); 
		//busca por linea y verifica el estado 
		
		tb.cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		tb.sleep(10000);
		List<WebElement> cards = driver.findElements(By.cssSelector("[class*='console-card ']"));
		cards =listaDeElementosPorText(cards, linea);
		if(cards.size()<=0) {
			System.out.println("numero de linea inexistente");
		}
		
		WebElement cardActiva= getBuscarElementoPorText(cards, "Activo");
		WebElement cardInactiva= getBuscarElementoPorText(cards, "Inactivo");
		//Verificar nombre del plan de la card activa
		Assert.assertTrue(cardActiva.getText().toLowerCase().contains("conexi\u00f3n control abono"));
		// obtienen los megas anteriores
		try {
			fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-text-body_regular value']"), 0));
			elemento = getBuscarElementoPorText(cardInactiva.findElements(By.cssSelector("[class='detail']")), "MB").findElements(By.cssSelector("[class='slds-text-body_regular value']")).get(1);
			megasAnterior = elemento.getText().substring(0, elemento.getText().length() - 3);
		} catch (Exception e) {
			System.out.println("Verificar el estado de la card ");
		}
		try {
			// despliega el menu de la nueva card
			tb.cambioDeFrame(driver, By.cssSelector("[id='flecha'] i"), 0);
			fluentWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id='flecha'] i")));
			cardActiva.findElement(By.cssSelector("[id='flecha'] i")).click();
			// obtienen la cantidad de megas del nuevo plan
			fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-gutters']"), 0));
			List<WebElement> elementos = driver.findElements(By.cssSelector("[class='slds-grid slds-gutters']"));
			fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-gutters'] [class='slds-col slds-size_1-of-3'] [class='value']"),0));
			elemento = getBuscarElementoPorText(elementos, "Internet").findElements(By.cssSelector("[class='slds-grid slds-gutters'] [class='slds-col slds-size_1-of-3'] [class='value']")).get(1);
			nuevomega = elemento.getText();
		} catch (Exception e) {
			System.out.println("Verificar el estado de la card actual");
		}
		 
		//compara
		result = nuevomega.equals(megasAnterior); 
		assertTrue(result); 
		driver.switchTo().defaultContent();
		
		return result;
	}

	public String getMegasNuevaCard(String linea) {
		driver.switchTo().defaultContent(); 
		String nuevomega ="no hay datos";
		TestBase tb = new TestBase(); 
		//busca por linea y verifica el estado 
		tb.cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		tb.sleep(10000);
		List<WebElement> cards = driver.findElements(By.cssSelector("[class*='console-card ']"));
		cards =listaDeElementosPorText(cards, linea);
		if(cards.size()<=0) {
			System.out.println("numero de linea inexistente");
		}
		WebElement cardActiva= getBuscarElementoPorText(cards, "Activo");
		try {
			// despliega el menu de la nueva card
			tb.cambioDeFrame(driver, By.cssSelector("[id='flecha'] i"), 0);
			fluentWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id='flecha'] i")));
			cardActiva.findElement(By.cssSelector("[id='flecha'] i")).click();
			// obtienen la cantidad de megas del nuevo plan
			fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-gutters']"), 0));
			List<WebElement> elementos = driver.findElements(By.cssSelector("[class='slds-grid slds-gutters']"));
			fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-gutters'] [class='slds-col slds-size_1-of-3'] [class='value']"),0));
			WebElement elemento = getBuscarElementoPorText(elementos, "Internet").findElements(By.cssSelector("[class='slds-grid slds-gutters'] [class='slds-col slds-size_1-of-3'] [class='value']")).get(1);
			 nuevomega = elemento.getText();
		} catch (Exception e) {
			System.out.println("Verificar el est sado de la card actual");
		}
		 
		return nuevomega;
	}
	
	public String getInfoNuevaCard(String linea, String TipoDeConsumo) {
		//retorna los datos de la linea pasado por parametro, el tipo de comsumo puede ser, internet, minutos, SMS, credito disponible
		driver.switchTo().defaultContent(); 
		String info ="no hay datos";
		TestBase tb = new TestBase(); 
		//busca por linea y verifica el estado 
		tb.cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		tb.sleep(10000);
		List<WebElement> cards = driver.findElements(By.cssSelector("[class*='console-card ']"));
		cards =listaDeElementosPorText(cards, linea);
		if(cards.size()<=0) {
			System.out.println("numero de linea inexistente");
		}
		WebElement cardActiva= getBuscarElementoPorText(cards, "Activo");
		try {
			// intenta obtener los datos en caso de que la card se encuentre desplegada
			fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-gutters']"), 0));
			List<WebElement> elementos = driver.findElements(By.cssSelector("[class='slds-grid slds-gutters']"));
			fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-gutters'] [class='slds-col slds-size_1-of-3'] [class='value']"),0));
			WebElement elemento = getBuscarElementoPorText(elementos, TipoDeConsumo).findElements(By.cssSelector("[class='slds-grid slds-gutters'] [class='slds-col slds-size_1-of-3'] [class='value']")).get(1);
			info = elemento.getText().replaceAll("[$,.]", "");
		} catch (Exception e) {
			tb.cambioDeFrame(driver, By.cssSelector("[id='flecha'] i"), 0);
			fluentWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id='flecha'] i")));
			cardActiva.findElement(By.cssSelector("[id='flecha'] i")).click();
			// obtienen la cantidad de megas del nuevo plan
			fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-gutters']"), 0));
			List<WebElement> elementos = driver.findElements(By.cssSelector("[class='slds-grid slds-gutters']"));
			fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-gutters'] [class='slds-col slds-size_1-of-3'] [class='value']"),0));
			WebElement elemento = getBuscarElementoPorText(elementos, TipoDeConsumo).findElements(By.cssSelector("[class='slds-grid slds-gutters'] [class='slds-col slds-size_1-of-3'] [class='value']")).get(1);
			info = elemento.getText().replaceAll("[$,.]", "");
		}
		 
		return info;
	}
	
	public WebDriverWait getWait() {
		return  wait;
	}
	
	public boolean estaEnClientes(String sDNI) {
		tb = new TestBase();
		tb.esperarElemento(driver, By.cssSelector("[class='slds-tabs--scoped__content'] tbody [class='searchClient-body slds-hint-parent ng-scope']"), 0);
		List<WebElement> clientes = driver.findElements(By.cssSelector("[class='slds-tabs--scoped__content'] tbody [class='searchClient-body slds-hint-parent ng-scope']"));
		for (WebElement cliente : clientes) {
			String dni = cliente.findElements(By.tagName("td")).get(3).getText();
			if (sDNI.equals(dni)) {
				return true;
			}
		}
		return false;
	}
	
	public void altaBajaServicio(String altaBaja, String servicio) {
		WebElement servicioEncontrado=null;
		tb.cambioDeFrame(driver, By.cssSelector( "[class='cpq-product-name']"),0);
		clickElementoPorText(driver.findElements(By.cssSelector( "[class='cpq-product-name']")), "Plan con Tarjeta Repro");
		this.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector( "[class='slds-tabs--default'] [class*='slds-tabs--default__content slds-show'] [class='cpq-item-product-child-level-1 cpq-item-child-product-name-wrapper'] button"), 2)); 
		List<WebElement>primerosBotones= driver.findElements(By.cssSelector( "[class='slds-tabs--default'] [class*='slds-tabs--default__content slds-show'] [class='cpq-item-product-child-level-1 cpq-item-child-product-name-wrapper'] button"));
		for(WebElement x : primerosBotones) {
			try{x.click();}catch(Exception e) {};
		}
		this.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector( "[class='cpq-item-base-product'] [class='cpq-item-product-child-level-2 cpq-item-child-product-name-wrapper'] button"), 0) );
		List<WebElement>segundosBotones= driver.findElements(By.cssSelector( "[class='cpq-item-base-product'] [class='cpq-item-product-child-level-2 cpq-item-child-product-name-wrapper'] button"));
		for(WebElement x : segundosBotones) {
			try{x.click();}catch(Exception e) {};
		}
		this.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector( "[class='cpq-item-base-product']"), 19) );
		List<WebElement> listaServicios= driver.findElements(By.cssSelector( "[class='cpq-item-base-product']"));
		for(WebElement x : listaServicios) {
			System.out.println(x.getText());
			if(x.getText().contains(servicio)) {
				System.out.println(true+" ");
				servicioEncontrado=x;
				break;
			}			
		}
		switch(altaBaja) {
		case "baja":
			try {
				servicioEncontrado.findElement(By.cssSelector("[class='cpq-item-base-product-actions slds-text-align_right'] [class='icon-outline-delete']")).click();
				tb.cambioDeFrame(driver, By.cssSelector("[class='slds-button slds-button--destructive']"), 0);
				this.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='slds-button slds-button--destructive']")));
				driver.findElement(By.cssSelector("[class='slds-button slds-button--destructive']")).click();
			} catch (Exception e) {
				System.out.println("el estado del servicio es INACTIVO");
			}
			break;
		case "alta":
			try{servicioEncontrado.findElement(By.cssSelector( "[class='cpq-item-base-product-actions slds-text-align_right'] [class='slds-button slds-button_neutral secondary']")).click();
			}catch(Exception e) {
				System.out.println("el estado del servicio es ACTIVO");
			}
			break;
		default:
			System.out.println("no se encuentra el servicio - verifique si existe o este bien escrito ");
			break;
		}
	}
	
	public void cambiarPerfil(String user) {
		cerrarPestaniaGestion(driver);
		driver.switchTo().defaultContent();
		getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("userNavLabel")));
		driver.findElement(By.id("userNavLabel")).click();		
		WebElement option = driver.findElement(By.xpath("//*[@id='userNavMenu']//a[text()='Finalizar sesi\u00f3n']"));
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].click();", option);
		getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("userDropdown")));
		driver.findElement(By.id("userDropdown")).click();
		driver.findElement(By.id("logout")).click();
		tb.cambioDeFrame(driver, By.cssSelector("[class='head3b']"), 0);
		getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='head3b'][text()='Salida de la sesi\u00f3n']")));
		driver.get(TestBase.urlAmbiente);
		getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[id='idp_hint'] [class='button mb24 secondary wide']")));
		driver.findElement(By.cssSelector("[id='idp_hint'] [class='button mb24 secondary wide']")).click();
		getWait().until(ExpectedConditions.elementToBeClickable(By.id("loginButton2")));
		driver.findElement(By.name("Ecom_User_ID")).sendKeys(user);
		driver.findElement(By.name("Ecom_Password")).sendKeys("Testa10k");
		driver.findElement(By.id("loginButton2")).click();
	}
	
	public boolean verificacionDeSMS (String sLinea) {
		tb.cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		String smsViejo = null;
		WebElement elemento =null;
		List<WebElement> elementos = driver.findElements(By.cssSelector("[class='slds-grid slds-gutters']"));
		fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-gutters'] [class='slds-col slds-size_1-of-3'] [class='value']"),0));
		elemento = getBuscarElementoPorText(elementos, "SMS").findElements(By.cssSelector("[class='slds-grid slds-gutters'] [class='slds-col slds-size_1-of-3'] [class='value']")).get(1);
		smsViejo = elemento.getText();
		int datoViejo = Integer.parseInt(smsViejo);
		System.out.println(datoViejo);
		driver.navigate().refresh();
		tb.sleep(5000);
		tb.cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		tb.sleep(10000);
		List<WebElement> cards = driver.findElements(By.cssSelector("[class*='console-card ']"));
		cards = listaDeElementosPorText(cards, sLinea);
		WebElement cardActiva= getBuscarElementoPorText(cards, "Activo");
		tb.cambioDeFrame(driver, By.cssSelector("[id='flecha'] i"), 0);
		fluentWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id='flecha'] i")));
		cardActiva.findElement(By.cssSelector("[id='flecha'] i")).click();
		String smsNuevo = null;
		WebElement elemento1 =null;
		List<WebElement> elementos1 = driver.findElements(By.cssSelector("[class='slds-grid slds-gutters']"));
		fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-gutters'] [class='slds-col slds-size_1-of-3'] [class='value']"),0));
		elemento1 = getBuscarElementoPorText(elementos1, "SMS").findElements(By.cssSelector("[class='slds-grid slds-gutters'] [class='slds-col slds-size_1-of-3'] [class='value']")).get(1);
		smsNuevo = elemento1.getText();
		int datoNuevo = Integer.parseInt(smsNuevo);
		System.out.println(datoNuevo);
		boolean verificacion = false;
		if(datoViejo + 40 == datoNuevo) {
			verificacion = true;
		}
		return verificacion;
	}
	
	public String obtenerDatoSMSViejo() {
		tb.cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		String smsViejo = null;
		WebElement elemento =null;
		List<WebElement> elementos = driver.findElements(By.cssSelector("[class='slds-grid slds-gutters']"));
		fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-gutters'] [class='slds-col slds-size_1-of-3'] [class='value']"),0));
		elemento = getBuscarElementoPorText(elementos, "SMS").findElements(By.cssSelector("[class='slds-grid slds-gutters'] [class='slds-col slds-size_1-of-3'] [class='value']")).get(1);
		smsViejo = elemento.getText();
		return smsViejo;
	}
	
	public String obtenerDatoSMSNuevo(String sLinea) {
		tb.cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		tb.sleep(10000);
		List<WebElement> cards = driver.findElements(By.cssSelector("[class*='console-card ']"));
		cards = listaDeElementosPorText(cards, sLinea);
		WebElement cardActiva= getBuscarElementoPorText(cards, "Activo");
		tb.cambioDeFrame(driver, By.cssSelector("[id='flecha'] i"), 0);
		fluentWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id='flecha'] i")));
		cardActiva.findElement(By.cssSelector("[id='flecha'] i")).click();
		String smsNuevo = null;
		WebElement elemento1 =null;
		List<WebElement> elementos1 = driver.findElements(By.cssSelector("[class='slds-grid slds-gutters']"));
		fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-gutters'] [class='slds-col slds-size_1-of-3'] [class='value']"),0));
		elemento1 = getBuscarElementoPorText(elementos1, "SMS").findElements(By.cssSelector("[class='slds-grid slds-gutters'] [class='slds-col slds-size_1-of-3'] [class='value']")).get(1);
		smsNuevo = elemento1.getText();
		return smsNuevo;
	}
}
