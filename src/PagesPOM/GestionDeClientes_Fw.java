package PagesPOM;

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
	

	final String ref_CajonDeAplicaciones= "//span[@id='tsidLabel']"; 
	@FindBy (xpath = ref_CajonDeAplicaciones)
	private WebElement cajonDeAplicaciones;

	final String locatorTipoDoc= "SearchClientDocumentType"; 
	@FindBy (id = locatorTipoDoc)
	private WebElement tipoDoc;

	final String locatorlistaMenuIzq= "[class='x-menu-list'] li";
	@FindBy (css = locatorlistaMenuIzq)
	private List<WebElement> listaMenuIzq;
	
	final String ref_elementosCajon= "[class='menuButtonMenuLink']";
	@FindBy (how = How.CSS, using = ref_elementosCajon)
	private List<WebElement>   elementosCajon;
	
	final String locatorMenuDesplegableIzq ="[class='navigator-sbmenu']";
	@FindBy (how = How.CSS, using = locatorMenuDesplegableIzq)
	private WebElement MenuDesplegableIzq;
	
	final String locatorDataImputGestion ="dataInput";
	@FindBy (how = How.ID, using = locatorDataImputGestion)
	private WebElement dataImputGestion;
	
	final String locatorPestanas ="[class*='x-tab-strip-closable']";
	@FindBy (how = How.CSS, using = locatorPestanas)
	private List<WebElement> pestanas;
	
	final String locatorPestaPeque = "[class*='x-tab-strip-closable x-tab-strip-active ']";
	@FindBy (how = How.CSS, using = locatorPestaPeque)
	private List<WebElement> pestaPeque;

	final String locatorelementosMenuIzq = "[class='support-servicedesk-navigator'] table";
	@FindBy (how = How.CSS, using = locatorelementosMenuIzq)
	private WebElement MenuIzq ;
	
	final String locatoriframes = "iframe";
	@FindBy (how = How.TAG_NAME, using = locatoriframes)
	private List<WebElement> iframes ;
	
	final String locatorTextGestion = "[class='slds-page-header__title vlc-slds-page-header__title slds-truncate ng-binding']";
	@FindBy (how = How.CSS, using = locatorTextGestion)
	private WebElement textGestion ;



	final String locatorBodys="[class='hasMotif homeTab homepage  ext-webkit ext-chrome sfdcBody brandQuaternaryBgr']";
	@FindBy (how = How.CSS, using = locatorBodys)
	private List<WebElement> bodys;

	
	
	final String locatorBotonesInf = "[class='slds-grid slds-m-bottom_small slds-wrap cards-container']";
	@FindBy (how = How.CSS, using = locatorBotonesInf)
	private WebElement botonesInf ;
	
			
	//CONTRUCTOR
	public GestionDeClientes_Fw(WebDriver driver) {
		super(driver);
		super.setDriver(driver);
		//PageFactory.initElements(driver, this);
		PageFactory.initElements(getDriver(), this);
		super.setFluentWait(new FluentWait<WebDriver>(driver));
		super.getFluentWait().withTimeout(15, TimeUnit.SECONDS)
		.pollingEvery(5, TimeUnit.SECONDS)
		.ignoring(NoSuchElementException.class)
		.ignoring(NullPointerException.class)
//		.ignoring(TimeoutException.class)
		.ignoring(ElementNotVisibleException.class);

	}
	
//METODOS
	
	public WebElement getTipoDoc() {
		driver.switchTo().frame(cambioFrame(By.id(locatorTipoDoc)));
		fluentWait.until(ExpectedConditions.elementToBeClickable(tipoDoc));
		
		return tipoDoc;
	}	
	public WebElement getTextGestion() {
		return textGestion;
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
		try{
			fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(locatorPestanas), 0));
			
			for (WebElement UnB : pestanas) {
				try {
					System.out.println(UnB.getText());
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", UnB.findElement(By.className("x-tab-strip-close")));	
				}catch(Exception ex1) {} 
			}
		}catch(Exception e){
			System.out.println("pestanas cerradas");
		}
		
	}
	
	
	public void clickMenuIzq() {
		fluentWait.until(ExpectedConditions.elementToBeClickable(MenuIzq));
		System.out.println("x= "+MenuIzq.getLocation().getX()+"y= "+MenuIzq.getLocation().getY());
		super.getAction().moveToElement(MenuIzq).moveByOffset(MenuIzq.getLocation().getX()+110, MenuIzq.getLocation().getY()-90).click().build().perform();
		super.getEjecutorJavaScipt().executeScript("arguments[0].click();", this.MenuIzq);
		
	}
	
	
	public void selectMenuIzq(String opcionVisible) {
		clickMenuIzq();
		fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(this.locatorlistaMenuIzq), 0));
		try{
			super.getBuscarElementoPorText(listaMenuIzq, opcionVisible).click();
		}catch(Exception e) {
			System.out.println("no se encuentra elemento verificar que coincida con el texto visible");
		}
	}
	
	public void irGestionClientes() {
		driver.switchTo().frame(cambioFrame(By.cssSelector(locatorBodys)));
		fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(locatorBodys), 0));
		fluentWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locatorBodys)));//esperar que los elementos sean visibles
		boolean enc = false;
		int index = 0; 
		for(WebElement frame : iframes) {//recorre los frames
			try {
				driver.switchTo().frame(frame);
				fluentWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locatorBotonesInf)));
				driver.findElement(By.cssSelector(this.locatorBotonesInf));//each element is in the same iframe.
				driver.switchTo().frame(super.cambioFrame(By.cssSelector(this.locatorBodys)));
				enc = true;
				break;
			}catch(NoSuchElementException noSuchElemExcept) {
				index++;
				System.out.println("catch 1");
				driver.switchTo().frame(super.cambioFrame(By.cssSelector(this.locatorBodys)));
			}
		}
		if(enc == false) {
			index = -1;
		}
		try { 
			driver.switchTo().frame(iframes.get(index));
		}catch(ArrayIndexOutOfBoundsException iobExcept) {
			System.out.println("Elemento no encont2");
			}
		List<WebElement> botones = driver.findElements(By.tagName("button"));
		for (WebElement UnB : botones) {
			//System.out.println(UnB.getText());
			if(UnB.getText().equalsIgnoreCase("gesti\u00f3n de clientes")) {
				fluentWait.until(ExpectedConditions.elementToBeClickable(UnB));
				UnB.click();
				break;
			}
		driver.switchTo().defaultContent();
		}
		try {
			driver.switchTo().defaultContent();
			fluentWait.until(ExpectedConditions.presenceOfElementLocated(By.id("SearchClientDocumentType")));

		}catch(Exception e) {
			driver.switchTo().frame(cambioFrame(By.id("SearchClientDocumentType")));
			fluentWait.until(ExpectedConditions.presenceOfElementLocated(By.id("SearchClientDocumentType")));
			System.out.println(pestanas.size()+" pestanas cerrada");			
		}

	}
	
	
	
	
	
}
