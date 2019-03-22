package Pages;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.Accounts;
import Pages.BasePage;
import Pages.Marketing;
import Pages.OM;
import Pages.SalesBase;
import Pages.setConexion;
import Tests.TestBase;

/**
 * @author Quelys
 *
 */
public class PagePerfilTelefonico extends TestBase {
	
	CustomerCare cCC = new CustomerCare(driver);
	Marketing mM = new Marketing(driver);
	
	public PagePerfilTelefonico(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(id="SetPaymentType_nextBtn")
	private WebElement Tipodepago;
	
	@FindBy(id="InvoicePreview_nextBtn")
	private WebElement SimulaciondeFactura;
	
	@FindBy(id="DeliveryMethodConfiguration_nextBtn")
	private WebElement Delivery;
	
	@FindBy(id="SaleOrderMessages_nextBtn")
	private WebElement OrdenSeRealizoConExito;
	
	@FindBy(id="SelectPaymentMethodsStep_nextBtn")
	private WebElement MediodePago;
	
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

	
	
	public WebElement getMediodePago() {
		return MediodePago;
	}


	public WebElement getOrdenSeRealizoConExito() {
		return OrdenSeRealizoConExito;
	}


	public WebElement getSimulaciondeFactura() {
		return SimulaciondeFactura;
	}


	public WebElement getTipodepago() {
		return Tipodepago;
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
	
	public void buscarAssert() {
	CustomerCare cc= new CustomerCare(driver);
	sleep(8000);
	driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
	sleep(12000);
	driver.switchTo().defaultContent();
	if (driver.findElements(By.cssSelector(".x-layout-split.x-layout-split-west.x-splitbar-h")).size()>0) {
		System.out.println("Entre aqui");
		cc.panelIzquierdo();
		driver.switchTo().defaultContent();
		driver.findElement(By.cssSelector(".x-layout-split.x-layout-split-west.x-splitbar-h")).click();	
	}
	//cc.closeleftpanel();
	sleep(4000);
	driver.switchTo().frame(cambioFrame(driver, By.className("card-top"))); 
	sleep(8000);
	}
	
	
	public void comprarPack() {
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".items-card.ng-not-empty.ng-valid")));
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".slds-grid.community-flyout-content")).getLocation().y+")");
		List<WebElement> comprar = driver.findElements(By.className("community-flyout-grid-items-card"));
		for (WebElement comp : comprar) {
			if (comp.getText().toLowerCase().contains("comprar minutos") || comp.getText().toLowerCase().contains("comprar internet") || comp.getText().toLowerCase().contains("comprar sms")) {
				comp.findElement(By.tagName("button")).click();
				sleep(30000);
				break;
			}
		}		

	}
	
	public void comprarPack(String sTypeOfPack) {
		 //"comprar minutos", "comprar internet", o "comprar sms"
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".items-card.ng-not-empty.ng-valid")));
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".slds-grid.community-flyout-content")).getLocation().y+")");
		List<WebElement> comprar = driver.findElements(By.className("community-flyout-grid-items-card"));
		for (WebElement comp : comprar) {
			if (comp.getText().toLowerCase().contains(sTypeOfPack)) {
				comp.findElement(By.tagName("button")).click();
				sleep(45000);
				break;
			}
		}
	}
	
	public String agregarPack(String Pack1) {
		sleep(5000);		
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-radio_faux")));
		Pack("Packs Opcionales", "Packs de Datos", Pack1);
		//String chargeCode = obtenerChargeCode();
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();
		sleep(10000);
		try{ 
		      driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")).get(1).click(); 
		      sleep(10000); 
		    }catch(Exception ex1){} 
		sleep(10000);
		return "nada";
	}
	
	public String PackCombinado(String Pack1) {
		sleep(5000);		
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.cpq-item-has-children")));
		Pack("Packs Opcionales", "Packs Combinados", Pack1);
		sleep(10000);
		String chargeCode = "nada";//obtenerChargeCode();
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();
		sleep(25000);
		try{ 
		      driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")).get(1).click(); 
		      sleep(8000); 
		    }catch(Exception ex1){} 
		sleep(12000);
		return chargeCode;
	}
	
	public String PackLDI(String Pack1) {
		sleep(5000);		
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.cpq-item-has-children")));
		Pack("Packs Opcionales", "Packs LDI", Pack1);
		sleep(10000);
		String chargeCode = "nada";//obtenerChargeCode();
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();
		sleep(25000);
		try{ 
		      driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")).get(1).click(); 
		      sleep(8000); 
		    }catch(Exception ex1){} 
		sleep(12000);
		return chargeCode;
	}
	
	public String PacksRoaming(String Pack1) {
		sleep(5000);		
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.cpq-item-has-children")));
		Pack("Packs Opcionales", "Packs de Roaming", Pack1);
		String chargeCode = obtenerChargeCode();
		sleep(10000);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();
		sleep(25000);
		try{ 
		      driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")).get(1).click(); 
		      sleep(8000); 
		    }catch(Exception ex1){} 
		sleep(12000);
		return chargeCode;
	}
	
	public void tipoDePago(String tipodepago) {
	List<WebElement> tipodePago = driver.findElements(By.cssSelector(".slds-radio.ng-scope"));
	for (WebElement pago : tipodePago) {
		//System.out.print(pago.getText().toLowerCase());
			if (pago.getText().toLowerCase().contains(tipodepago)) {
				System.out.println(tipodepago);
					pago.findElement(By.tagName("span")).click();
						sleep(8000);
							break;
						}
					}
				}
	
	public void siguiente() {
	sleep(5000);
	WebElement siguiente=driver.findElement(By.className("vlc-control-wrapper"));
	((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+siguiente.getLocation().y+")");
	sleep(8000);
	try {driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")).get(1).click();}
		catch(Exception ex1){
			sleep(8000);
		 	 try {driver.findElement(By.id("Step_Error_Huawei_S029_nextBtn")).click();}
		 	 	catch(org.openqa.selenium.ElementNotVisibleException EnviodefacturayDatos) {
		 	 		sleep(8000);
		 	 		try {driver.findElement(By.id("Step_Error_Huawei_S013_nextBtn")).click();}
		 	 			catch(org.openqa.selenium.ElementNotVisibleException facturayDatos) {
		 	 				sleep(8000);
		 	 			}
					}
				}
			}
			
		

	
	public void mododeEntrega(WebDriver driver, String entrega, String provincia, String localidad, String puntodeventa)  {
		BasePage cambioFrameByID=new BasePage();
		sleep(12000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("DeliveryMethodSelection")));
		sleep(15000);
		Select metodoEntrega = new Select (driver.findElement(By.id("DeliveryMethodSelection")));
		metodoEntrega.selectByVisibleText(entrega);
		Delivery.click();
		Select State = new Select (driver.findElement(By.id("PickState")));
		State.selectByVisibleText(provincia);
		Select City = new Select (driver.findElement(By.id("PickCity")));
		City.selectByVisibleText(localidad);
		//driver.findElement(By.id("Store")).click();
		Select Store = new Select (driver.findElement(By.id("Store")));
		Store.selectByVisibleText(puntodeventa);
		Delivery.click();
		sleep(25000);
		//SimulaciondeFactura.click();
		
		}
	
	
	public void Pack(String servicio1, String servicio2,String Pack1){
		CustomerCare cCC = new CustomerCare(driver);
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-button.cpq-item-has-children")).click();
		sleep(5000);
		List<WebElement> NomPack = driver.findElements(By.xpath("//*[@class='cpq-item-product-child-level-1 cpq-item-child-product-name-wrapper']"));
		for(WebElement a: NomPack) {
			//System.out.print(a.getText().toLowerCase());
			//System.out.println(" : "+servicio1.toLowerCase());
				if (a.getText().toLowerCase().contains(servicio1.toLowerCase())) {
					System.out.println(servicio1);
						a.findElement(By.tagName("button")).click();
							sleep(8000);
								break;
							}
						}
	
		List<WebElement> subPack = driver.findElements(By.xpath("//*[@class='cpq-item-product-child-level-2 cpq-item-child-product-name-wrapper']"));
		List<WebElement> Btnsubpack = driver.findElements(By.xpath("//*[@class='cpq-item-product-child-level-2 cpq-item-child-product-name-wrapper']//*[@class='slds-button slds-button_icon-small']"));			
		if (subPack.size() == Btnsubpack.size()) {
		for(WebElement b: subPack) {			
			//System.out.println("+++++"+b.getText().substring(b.getText().indexOf("\n")+1, b.getText().length())+"++++++");
					if (b.getText().substring(b.getText().indexOf("\n")+1, b.getText().length()).toLowerCase().contains(servicio2.toLowerCase())) {
						System.out.println(servicio2);
						  b.findElement(By.tagName("button")).click();
						   sleep(8000);
						     break;
						}
				    }
				}
		boolean estaPack=false;
		 //subtablas
		List<String> packs = Arrays.asList(Pack1);
		 List<WebElement> Pack = driver.findElements( By.xpath("//*[@class='cpq-item-product-child-level-3 ng-not-empty ng-valid']//*[@class='cpq-item-no-children']"));
		 List<WebElement> Agregar= driver.findElements(By.xpath("//*[@class='cpq-item-product-child-level-3 ng-not-empty ng-valid']//*[@class='slds-button slds-button_neutral']"));
		 if (Pack.size() == Agregar.size()) {
			 for (int i = 0; i < Pack.size(); i++) {
				 for (int j = 0; j < packs.size(); j++) {
				 if (Pack.get(i).getText().equals(packs.get(j))) {
					sleep(8000);
					System.out.println(Pack.get(i).getText());
					cCC.obligarclick(Agregar.get(i));
					estaPack=true;
							break;	
						}
					}	
				}
			}
		 Assert.assertTrue(estaPack);
		}
	
	
	public void closerightpanel() {
		sleep(5000);
		try {
		driver.switchTo().defaultContent();
		if(driver.findElements(By.cssSelector(".x-layout-mini.x-layout-mini-east.x-layout-mini-custom-logo")).size() != 0) {
			driver.findElement(By.cssSelector(".x-layout-mini.x-layout-mini-east.x-layout-mini-custom-logo")).click();
		}
		}
		catch (ElementNotVisibleException e) {
		
		}
		}
	
	public void altaBajaServicio(String sAltaBaja,String sTipoServicio, String sServicio, WebDriver driver) {
		boolean bAssert= false;
		
		driver.findElement(By.cssSelector(".slds-button.cpq-item-has-children")).click();//Plan con Tarjeta Repro button
		//getwPlanConTarjetaRepro().click();//Plan con Tarjeta Repro button
		
		//Select Servicios Telefonia Movil or Servicios Basicos General Movil
		List<WebElement> wTipoServicios= driver.findElements(By.xpath("//*[@class='cpq-item-product-child-level-1 cpq-item-child-product-name-wrapper']"));
		for(WebElement wAux : wTipoServicios){
			if(wAux.getText().toLowerCase().contains(sTipoServicio.toLowerCase())){
				wAux.findElement(By.tagName("button")).click();
				sleep(8000);
				bAssert = true;
				break;
			}
		}
		Assert.assertTrue(bAssert);
		sleep(20000);
		
		//Select specific service
		WebElement wTable = driver.findElement(By.cssSelector("[class='slds-tabs--default__content slds-show']"));
		List <WebElement> wServicios = wTable.findElements(By.cssSelector("[class='cpq-item-product-child-level-2 ng-not-empty ng-valid'] [class='cpq-item-base-product']"));
		
		bAssert = false;
		for(WebElement wAux2 : wServicios){
			
			System.out.println("\nService: " + wAux2.findElement(By.className("cpq-item-no-children")).getText().toLowerCase() + " = " + sServicio.toLowerCase());
			System.out.println("Result: " + wAux2.findElement(By.className("cpq-item-no-children")).getText().toLowerCase().contains(sServicio.toLowerCase()));
			
			if(wAux2.findElement(By.className("cpq-item-no-children")).getText().toLowerCase().contains(sServicio.toLowerCase())){
				System.out.println("\nGet In");
				switch (sAltaBaja.toLowerCase()) {
					case "alta":
						System.out.println("\nSign Up");
						try {
							System.out.println("Is Displayed? " + wAux2.findElement(By.cssSelector(".slds-button.slds-button_neutral")).isDisplayed());
						}
						catch(Exception eE) {
							Assert.assertTrue(false);
						}
						wAux2.findElement(By.cssSelector(".slds-button.slds-button_neutral")).click();
						sleep(5000);
						bAssert = true;
						break;
					case "baja":
						System.out.println("\nSign Down");
						sleep(5000);
						try {
							System.out.println("Is Displayed? " + wAux2.findElement(By.cssSelector(".slds-button.slds-button_icon-border-filled.cpq-item-actions-dropdown-button")).isDisplayed());
						}
						catch(Exception eE) {
							Assert.assertTrue(false);
						}
						wAux2.findElement(By.cssSelector(".slds-button.slds-button_icon-border-filled.cpq-item-actions-dropdown-button")).click();
						sleep(10000);
						List<WebElement> wButtons = wAux2.findElements(By.cssSelector(".slds-dropdown__item.cpq-item-actions-dropdown__item"));
						for(WebElement wAux3 : wButtons) {
							System.out.println("Option: " + wAux2.getText());
							if(wAux3.getText().equalsIgnoreCase("Delete")) {
								System.out.println("Found it");
								//cCC.obligarclick(wAux2);
								wAux3.click();
							}
						}
						bAssert = true;
						sleep(3000);
						driver.findElement(By.cssSelector("[class='slds-button slds-button--destructive']")).click();

						break;
					default:
						System.out.println("Opción incorrecta, solo Alta o Baja");
						break;
				}
			}
			
			if (bAssert==true) break;
		}
		sleep(10000);
		if (sAltaBaja.toLowerCase().equalsIgnoreCase("Baja")) {
			wTable = driver.findElement(By.cssSelector("[class='slds-tabs--default__content slds-show']"));
			wServicios = wTable.findElements(By.cssSelector("[class='cpq-item-product-child-level-2 ng-not-empty ng-valid'] [class='cpq-item-base-product']"));
			
			bAssert = false;
			for(WebElement wAux4 : wServicios){
				
				System.out.println("\nService: " + wAux4.findElement(By.className("cpq-item-no-children")).getText().toLowerCase() + " = " + sServicio.toLowerCase());
				System.out.println("Result: " + wAux4.findElement(By.className("cpq-item-no-children")).getText().toLowerCase().contains(sServicio.toLowerCase()));
				
				try {
					if(wAux4.findElement(By.className("cpq-item-no-children")).getText().toLowerCase().contains(sServicio.toLowerCase()) && wAux4.findElement(By.cssSelector(".slds-button.slds-button_neutral")).isDisplayed()) {
						bAssert = true;
						break;
					}
				}
				catch (Exception eE) {
					//Always Empty
				}
				
			}
			if(!bAssert) {
				System.out.println("Here we go again");
				driver.navigate().refresh();
				mM.selectMainTabByName("Alta/Baja de Servicios");
				altaBajaServicio(sAltaBaja, sTipoServicio, sServicio, driver);
			}
		}
	}
	
	public void altaBajaServicio(String sAltaBaja,String sTipoServicio, String sSubTipoServicio, String sServicio, WebDriver driver) {
		boolean bAssert= false;
		boolean hizoGestion = false;
		driver.findElement(By.cssSelector(".slds-button.cpq-item-has-children")).click();//Plan con Tarjeta Repro button
		//getwPlanConTarjetaRepro().click();//Plan con Tarjeta Repro button
		
		//Select Servicios Telefonia Movil or Servicios Basicos General Movil
		List<WebElement> wTipoServicios= driver.findElements(By.xpath("//*[@class='cpq-item-product-child-level-1 cpq-item-child-product-name-wrapper']"));
		for(WebElement wAux : wTipoServicios){
			if(wAux.getText().toLowerCase().contains(sTipoServicio.toLowerCase())){
				System.out.println("\nGet In " + sTipoServicio);
				wAux.findElement(By.tagName("button")).click();
				sleep(8000);
				bAssert = true;
				break;
			}
		}
		Assert.assertTrue(bAssert);
		sleep(20000);
		
		bAssert = false;
		List <WebElement> wSubTipoServicio = driver.findElements(By.cssSelector(".cpq-item-product-child-level-2.cpq-item-child-product-name-wrapper"));
		for(WebElement wAux2 : wSubTipoServicio){
			if(wAux2.getText().toLowerCase().contains(sSubTipoServicio.toLowerCase())){
				System.out.println("\nGet In " + sSubTipoServicio);
				wAux2.findElement(By.cssSelector(".slds-button.slds-button_icon-small")).click();
				bAssert = true;
				break;
			}
		}
		Assert.assertTrue(bAssert);
		sleep(10000);
		
		//Select specific service
		WebElement wTable = driver.findElement(By.cssSelector("[class='slds-tabs--default__content slds-show']"));
		List <WebElement> wServicios = wTable.findElements(By.cssSelector("[class='cpq-item-product-child-level-2 ng-not-empty ng-valid'] [class='cpq-item-base-product']"));
		
		bAssert = false;
		for(WebElement wAux3 : wServicios){
			try {
				System.out.println("\nService: " + wAux3.findElement(By.className("cpq-item-no-children")).getText().toLowerCase() + " = " + sServicio.toLowerCase());
				System.out.println("Result: " + wAux3.findElement(By.className("cpq-item-no-children")).getText().toLowerCase().contains(sServicio.toLowerCase()));
				
				if(wAux3.findElement(By.className("cpq-item-no-children")).getText().toLowerCase().contains(sServicio.toLowerCase())){
					System.out.println("\nGet In" + sServicio);
					switch (sAltaBaja.toLowerCase()) {
						case "alta":
							
							System.out.println("\nSign Up");
							try {
								System.out.println("Is Displayed? " + wAux3.findElement(By.cssSelector(".slds-button.slds-button_neutral")).isDisplayed());
							}
							catch(Exception eE) {
								Assert.assertTrue(false);
							}
							Assert.assertTrue(wAux3.findElement(By.cssSelector(".slds-button.slds-button_neutral")).isDisplayed());
							wAux3.findElement(By.cssSelector(".slds-button.slds-button_neutral")).click();
							hizoGestion = true;
							sleep(5000);
							bAssert = true;
							break;
						case "baja":
							System.out.println("\nSign Down");
							sleep(5000);
							try {
								System.out.println("Is Displayed? " + wAux3.findElement(By.cssSelector(".slds-button.slds-button_icon-border-filled.cpq-item-actions-dropdown-button")).isDisplayed());
							}
							catch(Exception eE) {
								Assert.assertTrue(false);
							}
							wAux3.findElement(By.cssSelector(".slds-button.slds-button_icon-border-filled.cpq-item-actions-dropdown-button")).click();
							sleep(10000);
							List<WebElement> wButtons = wAux3.findElements(By.cssSelector(".slds-dropdown__item.cpq-item-actions-dropdown__item"));
							for(WebElement wAux4 : wButtons) {
								System.out.println("Option: " + wAux4.getText());
								if(wAux4.getText().equalsIgnoreCase("Delete")) {
									System.out.println("Found it");
									//cCC.obligarclick(wAux2);
									wAux4.click();
								}
							}
							bAssert = true;
							sleep(3000);
							driver.findElement(By.cssSelector("[class='slds-button slds-button--destructive']")).click();
	
							break;
						default:
							System.out.println("Opción incorrecta, solo Alta o Baja");
							break;
					}
				}
				
				if (bAssert==true) break;
			}
			catch (Exception eE) {
				//Always Empty
			}
		}
		sleep(10000);
		if (sAltaBaja.toLowerCase().equalsIgnoreCase("Baja")) {
			if(sServicio.toLowerCase().contains("roaming internacional")) {
				wTable = driver.findElement(By.cssSelector("[class='slds-tabs--default__content slds-show']"));
				wServicios = wTable.findElements(By.cssSelector("[class='cpq-item-product-child-level-2 ng-not-empty ng-valid'] [class='cpq-item-base-product']"));
				String sNServ = null;
				if(sServicio.toLowerCase().contains("con roaming internacional"))
					sNServ = "ddi sin roaming internacional";
				else
					sNServ = "ddi con roaming internacional";
				for(WebElement wAux3 : wServicios){
					try {
						if(wAux3.findElement(By.className("cpq-item-no-children")).getText().toLowerCase().contains(sNServ)){
							System.out.println("\nGet In" + sServicio);
							System.out.println("\nSign Up");
							try {
								System.out.println("Is Displayed? " + wAux3.findElement(By.cssSelector(".slds-button.slds-button_neutral")).isDisplayed());
							}
							catch(Exception eE) {
								Assert.assertTrue(false);
							}
							Assert.assertTrue(wAux3.findElement(By.cssSelector(".slds-button.slds-button_neutral")).isDisplayed());
							wAux3.findElement(By.cssSelector(".slds-button.slds-button_neutral")).click();
							sleep(5000);
							bAssert = true;
							break;
						}
					}catch (Exception eE) {
						//Always Empty
					}
				}
			}
			if(!sServicio.toLowerCase().contains("roaming internacional")) {
				wTable = driver.findElement(By.cssSelector("[class='slds-tabs--default__content slds-show']"));
				wServicios = wTable.findElements(By.cssSelector("[class='cpq-item-product-child-level-2 ng-not-empty ng-valid'] [class='cpq-item-base-product']"));
				
				bAssert = false;
				for(WebElement wAux5 : wServicios){
					System.out.println("*****Serivicio: "+wAux5.getText());
					System.out.println("\nService: " + wAux5.findElement(By.className("cpq-item-no-children")).getText().toLowerCase() + " = " + sServicio.toLowerCase());
					System.out.println("Result: " + wAux5.findElement(By.className("cpq-item-no-children")).getText().toLowerCase().contains(sServicio.toLowerCase()));
					
					try {
						if(wAux5.findElement(By.className("cpq-item-no-children")).getText().toLowerCase().contains(sServicio.toLowerCase()) && wAux5.findElement(By.cssSelector(".slds-button.slds-button_neutral")).isDisplayed()) {
							bAssert = true;
							break;
						}
					}
					catch (Exception eE) {
						//Always Empty
					}
					
				}
				if(!bAssert) {
					System.out.println("Here we go again");
					driver.navigate().refresh();
					mM.selectMainTabByName("Alta/Baja de Servicios");
					altaBajaServicio(sAltaBaja, sTipoServicio, sServicio, driver);
				}
			}
		}
	}
	
	public boolean forEach(List<WebElement> wWebList, List<String> sTextList) {
		boolean bAssert = true;
		
		for(WebElement wAux : wWebList) {
			for (int i=0; i<sTextList.size(); i++) {
				if(!wAux.getText().equalsIgnoreCase(sTextList.get(i))) {
					if(i == sTextList.size()) {
						bAssert = false;
						break;
					}
				}
				else {
					break;
				}
			}
			if(bAssert == false) {
				break;
			}
		}
		
		return bAssert;
	}
	public void altaBajaServicioContestador(String sAltaBaja,String sTipoServicio, String sSubTipoServicio, String sServicio, WebDriver driver) {
		boolean bAssert= false;
		boolean hizoGestion = false;
		/*if(sAltaBaja.equalsIgnoreCase("alta")) {
			if(sServicio.toLowerCase().equals("voice mail con clave"))
				altaBajaServicio("Baja", "Servicios basicos general movil", "Contestador", "Contestador Personal", driver);
			else
				altaBajaServicio("Baja", "Servicios basicos general movil", "Contestador", "Voice Mail con Clave", driver);
		}*/
		if(driver.findElement(By.cssSelector("[class='slds-tabs--default__content slds-show']")).findElements(By.cssSelector("[class='cpq-item-product-child-level-2 ng-not-empty ng-valid'] [class='cpq-item-base-product']")).size()<3){
			driver.findElement(By.cssSelector(".slds-button.cpq-item-has-children")).click();//Plan con Tarjeta Repro button
			//getwPlanConTarjetaRepro().click();//Plan con Tarjeta Repro button
			
			//Select Servicios Telefonia Movil or Servicios Basicos General Movil
			List<WebElement> wTipoServicios= driver.findElements(By.xpath("//*[@class='cpq-item-product-child-level-1 cpq-item-child-product-name-wrapper']"));
			for(WebElement wAux : wTipoServicios){
				if(wAux.getText().toLowerCase().contains(sTipoServicio.toLowerCase())){
					System.out.println("\nGet In " + sTipoServicio);
					wAux.findElement(By.tagName("button")).click();
					sleep(8000);
					bAssert = true;
					break;
				}
			}
			Assert.assertTrue(bAssert);
			sleep(20000);
			
			bAssert = false;
			List <WebElement> wSubTipoServicio = driver.findElements(By.cssSelector(".cpq-item-product-child-level-2.cpq-item-child-product-name-wrapper"));
			for(WebElement wAux2 : wSubTipoServicio){
				if(wAux2.getText().toLowerCase().contains(sSubTipoServicio.toLowerCase())){
					System.out.println("\nGet In " + sSubTipoServicio);
					wAux2.findElement(By.cssSelector(".slds-button.slds-button_icon-small")).click();
					bAssert = true;
					break;
				}
			}
			Assert.assertTrue(bAssert);
			sleep(10000);
			
			//Select specific service
		}
		WebElement wTable = driver.findElement(By.cssSelector("[class='slds-tabs--default__content slds-show']"));
		List <WebElement> wServicios = wTable.findElements(By.cssSelector("[class='cpq-item-product-child-level-2 ng-not-empty ng-valid'] [class='cpq-item-base-product']"));
		
		bAssert = false;
		String sOtroServ = null;
		if(sAltaBaja.toLowerCase().contains("alta")){
			if(sServicio.toLowerCase().contains("voice mail con clave")||sServicio.toLowerCase().contains("contestador personal")) {
				if(sServicio.toLowerCase().contains("voice mail con clave")) {
					sOtroServ = "contestador personal";
				}else {
					sOtroServ = "voice mail con clave";
				}
				for(WebElement wAux3 : wServicios){
					if(!wAux3.getText().toLowerCase().contains("toggle")) {
						System.out.println("\nService: " + wAux3.findElement(By.className("cpq-item-no-children")).getText().toLowerCase() + " = " + sServicio.toLowerCase());
						System.out.println("Result: " + wAux3.findElement(By.className("cpq-item-no-children")).getText().toLowerCase().contains(sServicio.toLowerCase()));
						
						if(wAux3.findElement(By.className("cpq-item-no-children")).getText().toLowerCase().contains(sOtroServ)){
							System.out.println("\nSign Down");
							sleep(5000);
							try {
								System.out.println("Is Displayed? " + wAux3.findElement(By.cssSelector(".slds-button.slds-button_icon-border-filled.cpq-item-actions-dropdown-button")).isDisplayed());
							}
							catch(Exception eE) {
								Assert.assertTrue(false);
							}
							wAux3.findElement(By.cssSelector(".slds-button.slds-button_icon-border-filled.cpq-item-actions-dropdown-button")).click();
							sleep(10000);
							List<WebElement> wButtons = wAux3.findElements(By.cssSelector(".slds-dropdown__item.cpq-item-actions-dropdown__item"));
							for(WebElement wAux4 : wButtons) {
								System.out.println("Option: " + wAux4.getText());
								if(wAux4.getText().equalsIgnoreCase("Delete")) {
									System.out.println("Found it");
									//cCC.obligarclick(wAux2);
									wAux4.click();
								}
							}
							bAssert = true;
							sleep(3000);
							driver.findElement(By.cssSelector("[class='slds-button slds-button--destructive']")).click();
						}
					}
				}
				sleep(6000);
			}
		
			wTable = driver.findElement(By.cssSelector("[class='slds-tabs--default__content slds-show']"));
			wServicios = wTable.findElements(By.cssSelector("[class='cpq-item-product-child-level-2 ng-not-empty ng-valid'] [class='cpq-item-base-product']"));
			for(WebElement wAux3 : wServicios) {
				if(!wAux3.getText().toLowerCase().contains("toggle")) {
					if(wAux3.findElement(By.className("cpq-item-no-children")).getText().toLowerCase().contains(sServicio.toLowerCase())){
						System.out.println("\nSign Up");
						try {
							System.out.println("Is Displayed? " + wAux3.findElement(By.cssSelector(".slds-button.slds-button_neutral")).isDisplayed());
						}
						catch(Exception eE) {
							Assert.assertTrue(false);
						}
						Assert.assertTrue(wAux3.findElement(By.cssSelector(".slds-button.slds-button_neutral")).isDisplayed());
						/*System.out.println("size: "+wAux3.findElements(By.tagName("button")).size());
						wAux3.findElements(By.tagName("button")).get(1).click();*/
						wAux3.findElement(By.cssSelector(".slds-button.slds-button_neutral")).click();
						hizoGestion = true;
						sleep(5000);
						bAssert = true;
					}
				}
			}
		}
		
		sleep(10000);
		
		if(!bAssert) {
			System.out.println("Here we go again");
			driver.navigate().refresh();
			mM.selectMainTabByName("Alta/Baja de Servicios");
			altaBajaServicio(sAltaBaja, sTipoServicio, sServicio, driver);
		}
			
	}
}