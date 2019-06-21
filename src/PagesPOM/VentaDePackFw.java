package PagesPOM;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Arrays;
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
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Pages.Accounts;
import Pages.CustomerCare;
import Pages.Marketing;
import Tests.TestBase;

public class VentaDePackFw extends BasePageFw {
	
	
	private WebDriverWait wait;
	private CustomerCare cc;
	private TestBase tb;
	private Marketing mk;
	
	
	
	@FindBy(id="DeliveryMethodConfiguration_nextBtn")
	private WebElement Delivery;
	
	
	@FindBy(id="ICCDAssignment_nextBtn")
	private WebElement IngresodeSerial;

	
	@FindBy(id="OrderSumary_nextBtn")
	private WebElement ResumenOrdenCompra;
	
	@FindBy(css=".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")
	private WebElement wAltaBajaContinuar;
	
	@FindBy(css=".slds-button.cpq-item-has-children")
	private WebElement wPlanConTarjetaRepro;
	
	public WebElement getResumenOrdenCompra() {
		return ResumenOrdenCompra;
	}

	public WebElement getIngresodeSerial() {
		return IngresodeSerial;
	}

	public WebElement getDelivery() {
		return Delivery;
	}
	
	public WebElement getwAltaBajaContinuar() {
		return wAltaBajaContinuar;
	}
	
	public WebElement getwPlanConTarjetaRepro() {
		return wPlanConTarjetaRepro;
	}
	
	public WebDriverWait getWait() {
		return  wait;
	}
	
	
	//-------------------------------------------------------------------CONTRUCTOR-----------------------------------------------------------------------------------//
		public VentaDePackFw(WebDriver driver) {
			super(driver);
			super.setDriver(driver);
			wait = new WebDriverWait(driver,30);
			cc = new CustomerCare(driver);
			tb = new TestBase();
			mk = new Marketing(driver);
			//PageFactory.initElements(driver, this);
			PageFactory.initElements(getDriver(), this);
			super.setFluentWait(new FluentWait<WebDriver>(driver));
			super.getFluentWait().withTimeout(30, TimeUnit.SECONDS)
			.pollingEvery(500, TimeUnit.MILLISECONDS)
			.ignoring(NoSuchElementException.class)
			.ignoring(NullPointerException.class)
//			.ignoring(TimeoutException.class)
			.ignoring(ElementNotVisibleException.class);
			
		}
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------//
		
	//--------------------------------------------------------Seleccion---De---Packs---------------------------------------------------------------------------------//	
		//TODOS LOS PACKS == Packs Combinados, Packs de Datos, Packs LDI, Packs de Roaming, Packs de SMS, Packs de Minutos, Packs OTT.
		public void packCombinado(String Pack1) {
			tb.cambioDeFrame(driver, By.cssSelector(".slds-button.cpq-item-has-children"), 0);		
			Pack("Packs Opcionales", "Packs Combinados", Pack1);
			try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class = 'slds-button slds-button--brand ta-button-brand']")));
			driver.findElement(By.cssSelector("[class = 'slds-button slds-button--brand ta-button-brand']")).click();
		}
		
		public void packDatos(String Pack1) {
			tb.cambioDeFrame(driver, By.cssSelector(".slds-button.cpq-item-has-children"), 0);
			Pack("Packs Opcionales", "Packs de Datos", Pack1);
			try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class = 'slds-button slds-button--brand ta-button-brand']")));
			driver.findElement(By.cssSelector("[class = 'slds-button slds-button--brand ta-button-brand']")).click();
		}
		
		public void packLDI(String Pack1) {
			tb.cambioDeFrame(driver, By.cssSelector(".slds-button.cpq-item-has-children"), 0);
			Pack("Packs Opcionales", "Packs LDI", Pack1);
			try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class = 'slds-button slds-button--brand ta-button-brand']")));
			driver.findElement(By.cssSelector("[class = 'slds-button slds-button--brand ta-button-brand']")).click();
		}
		
		public void packRoaming(String Pack1) {
			tb.cambioDeFrame(driver, By.cssSelector(".slds-button.cpq-item-has-children"), 0);
			Pack("Packs Opcionales", "Packs de Roaming", Pack1);
			try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class = 'slds-button slds-button--brand ta-button-brand']")));
			driver.findElement(By.cssSelector("[class = 'slds-button slds-button--brand ta-button-brand']")).click();
		}
		
		public void packSMS(String Pack1) {
			tb.cambioDeFrame(driver, By.cssSelector(".slds-button.cpq-item-has-children"), 0);
			Pack("Packs Opcionales", "Packs de SMS", Pack1);
			try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class = 'slds-button slds-button--brand ta-button-brand']")));
			driver.findElement(By.cssSelector("[class = 'slds-button slds-button--brand ta-button-brand']")).click();
		}
		
		public void packMinutos(String Pack1) {
			tb.cambioDeFrame(driver, By.cssSelector(".slds-button.cpq-item-has-children"), 0);
			Pack("Packs Opcionales", "Packs de Minutos", Pack1);
			try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class = 'slds-button slds-button--brand ta-button-brand']")));
			driver.findElement(By.cssSelector("[class = 'slds-button slds-button--brand ta-button-brand']")).click();
		}
		
		public void packOTT(String Pack1) {
			tb.cambioDeFrame(driver, By.cssSelector(".slds-button.cpq-item-has-children"), 0);
			Pack("Packs Opcionales", "Packs OTT", Pack1);
			try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class = 'slds-button slds-button--brand ta-button-brand']")));
			driver.findElement(By.cssSelector("[class = 'slds-button slds-button--brand ta-button-brand']")).click();
		}
	//--------------------------------------------------------------------------------------------------------------------------------------------------//
	//------------------------------------------------------Ir--a--Seleccion--De--Packs-----------------------------------------------------------------// 
		//En el Servicio1(Corresponde a las opciones Packs Opcionales y Servicios Internet por Dia)
		//En el servicio2 se selecciona los subPacks (Packs de Minutos Packs de SMS, Packs de Datos, Packs OTT, Packs Combinados, Packs LDI, Packs de Roaming)
		//Pack1 se selecciona el pack correspondiente (los nombre pueden variar) 
		public void Pack(String servicio1, String servicio2,String Pack1){
			seleccionDeServicio1(servicio1);
			seleccionDeServicio2(servicio2);
			seleccionDePack(Pack1);
		}
		
		
		
		
		private void seleccionDeServicio1(String servicio1) {
			getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(".slds-button.cpq-item-has-children")));
			driver.findElement(By.cssSelector(".slds-button.cpq-item-has-children")).click();
			getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class = 'cpq-item-product-child-level-1 cpq-item-child-product-name-wrapper']"), 3));
			List<WebElement> NomPack = driver.findElements(By.cssSelector("[class = 'cpq-item-product-child-level-1 cpq-item-child-product-name-wrapper']"));
			for (WebElement a : NomPack) {
				if (a.getText().toLowerCase().contains(servicio1.toLowerCase())) {
					System.out.println(servicio1);
					a.findElement(By.tagName("button")).click();
					break;
				}
			}
		}
		
		private void seleccionDeServicio2(String servicio2) {
			getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='cpq-item-product-child-level-2 cpq-item-child-product-name-wrapper']"), 3));
			List<WebElement> subPack = driver.findElements(By.cssSelector("[class='cpq-item-product-child-level-2 cpq-item-child-product-name-wrapper']"));
			List<WebElement> Btnsubpack = driver.findElements(By.cssSelector("[class='cpq-item-product-child-level-2 cpq-item-child-product-name-wrapper'] button"));
			if (subPack.size() == Btnsubpack.size()) {
				for (WebElement b : subPack) {
					if (b.getText().toLowerCase().contains(servicio2.toLowerCase())) {
						System.out.println(servicio2);
						b.findElement(By.tagName("button")).click();
						break;
					}
				}
			}
		}
		
		private void seleccionDePack(String Pack1) {
			boolean estaPack = false;
			getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class = 'cpq-item-product-child-level-3 ng-not-empty ng-valid'] [class = 'cpq-item-no-children']"), 0));
			// subtablas = seleccion del pack
			List<String> packs = Arrays.asList(Pack1);
			List<WebElement> Pack = driver.findElements(By.cssSelector("[class = 'cpq-item-product-child-level-3 ng-not-empty ng-valid'] [class = 'cpq-item-no-children']"));
			List<WebElement> Agregar = driver.findElements(By.cssSelector("[class='cpq-item-product-child-level-3 ng-not-empty ng-valid'] [class='slds-button slds-button_neutral secondary']"));
			if (Pack.size() == Agregar.size()) {
				for (int i = 0; i < Pack.size(); i++) {
					for (int j = 0; j < packs.size(); j++) {
						if (Pack.get(i).getText().equals(packs.get(j))) {
							System.out.println(Pack.get(i).getText());
							cc.obligarclick(Agregar.get(i));
							estaPack = true;
							break;
						}
					}
				}
				Assert.assertTrue(estaPack);
			}
		}
		
		//----------------------------------------------------------------------------------------------------------------------------------------------------------------//
		//-----------------------------------------------------------Seleccion--De--Pago----------------------------------------------------------------------------------//
		public void tipoDePago(String tipodepago) {
			getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SetPaymentType_nextBtn")));
			List<WebElement> tipodePago = driver.findElements(By.cssSelector(".slds-radio.ng-scope"));
			for (WebElement pago : tipodePago) {
				if (pago.getText().toLowerCase().contains(tipodepago)) {
					System.out.println(tipodepago);
					pago.findElement(By.tagName("span")).click();
					break;
				}
			}
		}
}
