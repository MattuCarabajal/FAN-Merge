package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import Tests.TestBase;

public class TechCare_Ola1 {
	
	private WebDriver driver;
	
	//*************************** CONSTRUCTOR *****************************//
	public TechCare_Ola1(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	//**************************** VARIABLES ******************************//
	
	@FindBy (how = How.CSS, using = ".slds-button.slds-button--brand")
	private WebElement verDetalle;
	
	@FindBy (how= How.CSS, using = ".slds-input.ng-valid.ng-touched.ng-dirty.ng-valid-parse.ng-empty")
	private WebElement campoBusqueda;
	
	//-----------------------------pregunta final desregistrar -----------------//
	@FindBy(css=".slds-form-element__label.ng-binding.ng-scope")
	private List<WebElement> opcionesFinal;

	public WebElement getOpcionesFinal(int index) {
		return opcionesFinal.get(index);
	}

	//----------------------------- Busqueda Cuenta ----------------------------//
	@FindBy(css = ".x-plain-header.sd_primary_tabstrip.x-unselectable .x-tab-strip-closable")
	private List<WebElement> pestanasPrimarias;
	
	@FindBy(css = ".x-btn-small.x-btn-icon-small-left")
	private WebElement selector;
	
	@FindBy(css = ".x-menu-item.standardObject.sd-nav-menu-item")
	private List<WebElement> desplegable;
	
	@FindBy(name = "fcf")
	private WebElement selectCuentas;
	
	@FindBy(css = ".x-plain-body.sd_nav_tabpanel_body.x-tab-panel-body-top iframe")
	private WebElement marcoCuentas;
	
	@FindBy(css = ".x-grid3-cell-inner.x-grid3-col-ACCOUNT_NAME")
	private List<WebElement> cuentas;
	
	//---------------------------- Busqueda GEO ---------------------------//
	@FindBy(id="AdressInput")
	private WebElement direccion;

	public void setDireccion(String dir) {
		direccion.sendKeys(dir);
	}

	@FindBy(id="GeoMock")
	private WebElement buscar;
	
	//***************************** METODOS *******************************//
	
	public void clickVerDetalle() {
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.cssSelector(".slds-button.slds-button--brand")));
		WebElement vD=driver.findElement(By.cssSelector(".slds-button.slds-button--brand"));
		//System.out.println(vD.getText());
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).getLocation().y+")");
		sleep(1000);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
	}
	
	public void buscarServicio(String servicio) {
		
		campoBusqueda.sendKeys(servicio);	
	}

	public void sleep(long ms) {
		//ms=ms*1000;
		try {Thread.sleep(ms);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void clickOpcionEnAsset(String Asset,String Opcion) {
		boolean assetEncontrado=false,opcion=false;
		Opcion=Opcion.toLowerCase();
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.cssSelector(".console-card.active.expired")));
		List<WebElement> asset=driver.findElements(By.cssSelector(".console-card.active"));
		for(WebElement a:asset) {
			//System.out.println(a.getText());
			if(a.findElement(By.className("header-right")).getText().contains(Asset)) {
				assetEncontrado=true;
				List<WebElement> opciones=a.findElement(By.className("actions")).findElements(By.tagName("li"));
				for(WebElement o:opciones) {
					
					if(o.findElement(By.tagName("span")).getText().toLowerCase().contains(Opcion)) {
						o.findElement(By.tagName("span")).click();
						opcion=true;
						break;
					}
				}
			}
		if(assetEncontrado) break;
		}
		if(!assetEncontrado) System.out.println("Asset No encontrado");
		if(!opcion) System.out.println("asset encontrado, Opcion No encontrada");
	}

	
	public void clickDiagnosticarServicio(String servicio, String subServicio) {
		sleep(5000);
		boolean sEncontrado=true;
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.cssSelector(".slds-card__body.cards-container")));
		List<WebElement> tablas=driver.findElements(By.cssSelector(".slds-card__body.cards-container"));
		//Listado de opciones
		List<WebElement> servicios=tablas.get(0).findElements(By.xpath("//table//tbody//tr"));
		for(WebElement S:servicios) {
			if(S.getText().toLowerCase().contains(servicio.toLowerCase())) {
				S.findElement(By.className("addedValueServices-arrowWrapper")).click();
				sleep(2000);
				sEncontrado=false;
				break;
			}
		}
		if(sEncontrado) { System.out.println("Servicio no encontrado."); return;}
		
		List<WebElement> sServicios=tablas.get(selectionTable(servicio)).findElements(By.xpath("//table//tbody//tr"));
	      for(WebElement S:sServicios) {
	        //System.out.println(S.getText());
	        if(S.getText().toLowerCase().contains(subServicio.toLowerCase())) {
	          ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+S.getLocation().y+")");
	          sleep(100);
	          S.findElement(By.className("slds-cell-shrink")).click();
	          sleep(2000);
	          try {
	            S.findElement(By.className("slds-cell-shrink")).findElement(By.xpath("//div//div//ul//li")).click();
	          }catch(org.openqa.selenium.ElementNotVisibleException e) {
	          sleep(2000);
	          List<WebElement> actions=  S.findElement(By.className("slds-cell-shrink")).findElements(By.xpath("//*[@class='dropdown__list']//li"));
	               //S.findElements(By.xpath("//*[@class='dropdown__list']//li"));
	           for (WebElement opt : actions) {
	        	   if (opt.isDisplayed()) {
	        		   opt.click();
	        		   break;} }
	          }
	        }
	  
	      }
		
	}
	
	
	public void clickDiagnosticarServicio(String servicio) {
	      sleep(5000);
	      Accounts accPage = new Accounts(driver);
	      driver.switchTo().frame(accPage.getFrameForElement(driver, By.cssSelector(".slds-card__body.cards-container")));
	      List<WebElement> tablas=driver.findElements(By.cssSelector(".slds-card__body.cards-container"));
	      //Listado de opciones
	      List<WebElement> servicios=tablas.get(selectionTable(servicio)).findElements(By.xpath("//table//tbody//tr"));
	      for(WebElement S:servicios) {
	        //System.out.println(S.getText());
	        if(S.getText().toLowerCase().contains(servicio.toLowerCase())) {
	          ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+S.getLocation().y+")");
	          sleep(100);
	          S.findElement(By.className("slds-cell-shrink")).click();
	          sleep(2000);
	          try {
	            S.findElement(By.className("slds-cell-shrink")).findElement(By.xpath("//div//div//ul//li")).click();
	          }catch(org.openqa.selenium.ElementNotVisibleException e) {
	          sleep(2000);
	          List<WebElement> actions=  S.findElement(By.className("slds-cell-shrink")).findElements(By.xpath("//*[@class='dropdown__list']//li"));
	               //S.findElements(By.xpath("//*[@class='dropdown__list']//li"));
	           for (WebElement opt : actions) {
	        	   if (opt.isDisplayed()) {
	        		   opt.click();
	        		   break;} }
	          }
	        }
	  
	      } 
	  
	  }
	
	
	private int selectionTable(String serviceName) {
	    switch (serviceName.toUpperCase()) {
	    case "SMS":
	      return 1;
	    case "VOZ":
	      return 2;
	    default:
	      return 0;
	    }}

	public final void seleccionarCualquierCuenta(WebDriver driver, String vista, String Cuenta) {
		String selection="cuentas";
		String inicialCuenta=Cuenta.substring(0, 1);
		System.out.println(inicialCuenta);
		driver.switchTo().defaultContent();
		try {
			driver.findElement(By.className("x-btn-split"));
		}catch(NoSuchElementException noSuchElemExcept) {
			List<WebElement> frames = driver.findElements(By.tagName("iframe"));
			for (WebElement frame : frames) {
				try {
					driver.findElement(By.className("x-btn-split"));
					break;
				}catch(NoSuchElementException noSuchElemExceptInside) {
					driver.switchTo().defaultContent();
					driver.switchTo().frame(frame);
				}
			}
		}
		WebElement dropDown = driver.findElement(By.className("x-btn-split"));
		Actions builder = new Actions(driver);   
		builder.moveToElement(dropDown, 245, 20).click().build().perform();
		List<WebElement> options = driver.findElements(By.tagName("li"));
		for(WebElement option : options) {
			if(option.findElement(By.tagName("span")).getText().toLowerCase().equals(selection.toLowerCase())) {
				option.findElement(By.tagName("a")).click();
				//System.out.println("Seleccionado"); //13/09/2017 working.
				break;
			}
		}
		
/////////////////////////////////Selecciono VIsta///////////////////////////////////////////////
		
		WebElement frame0 = driver.findElement(By.tagName("iframe"));
		driver.switchTo().frame(frame0);
		Select field = new Select(driver.findElement(By.name("fcf")));
		field.selectByVisibleText(vista);
		
/////////////////////////////////Selecciono Cuentas en la vista///////////////////////////////////
		Accounts accPage = new Accounts(driver);
		accPage.accountSelect(vista);
	    sleep(4000);
	    //try {accPage.selectAccountByName(Cuenta);}
	 //   catch(NoSuchElementException ex) {
	    	//System.out.println("entro al catch");
		    driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("listItemPad")));
		    List<WebElement> Iniciales=driver.findElements(By.className("listItemPad"));
		    for(WebElement letras:Iniciales) {
		    	//System.out.println("Entro a las letras");
		    	System.out.println(letras.getText());
		    	if(letras.getText().toLowerCase().startsWith(inicialCuenta.toLowerCase())) {
		    		letras.click();
		    		break;
		    	}
		//    }
		    CustomerCare ccPage=new CustomerCare(driver);
		    ccPage.elegirCuenta(Cuenta);
		    //accPage.selectAccountByName(Cuenta);
	    }
	}
	
	public void selectAccount(String cuenta) {
		driver.switchTo().defaultContent();
		Boolean flag = false;
		if (pestanasPrimarias.size() > 0) {
			for (WebElement t : pestanasPrimarias) {
				if (t.getText().equals(cuenta)) {
					flag = true;
					t.click();
					// Verificar que exista la pesta�a Servicios almenos
				} else {
					WebElement btn_cerrar = t.findElement(By.className("x-tab-strip-close"));
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn_cerrar);	
				}
			}
		}
	
		if (flag == false) {
			if  (!selector.getText().equalsIgnoreCase("Cuentas")) {
				WebElement btnSplit = selector.findElement(By.className("x-btn-split"));
				Actions builder = new Actions(driver);   
				builder.moveToElement(btnSplit, 245, 20).click().build().perform();
				for (WebElement op : desplegable) {
					if (op.getText().equalsIgnoreCase("Cuentas")) op.click();
				}
			}
					
			driver.switchTo().frame(marcoCuentas);
			Select field = new Select(selectCuentas);
			if (!field.getFirstSelectedOption().getText().equalsIgnoreCase("Todas las cuentas")) {
				field.selectByVisibleText("Todas las cuentas");
				sleep(4000);
			}
			sleep(8000);
			char char0 = cuenta.toUpperCase().charAt(0);
			driver.findElement(By.xpath("//div[@class='rolodex']//span[contains(.,'" + char0 + "')]")).click();
			sleep(10000);
			
			try {
				List<WebElement>  lCuentas1=driver.findElements(By.cssSelector(".dataRow.odd"));
				lCuentas1.add(driver.findElement(By.cssSelector(".dataRow.even.first")));
				List<WebElement>  lCuentas2=driver.findElements(By.cssSelector(".dataRow.even"));
				lCuentas1.addAll(lCuentas2);
			
				for (WebElement c : lCuentas1) {
					if (c.getText().toLowerCase().contains(cuenta.toLowerCase())) {
						sleep(500);
						c.findElement(By.tagName("th")).findElement(By.tagName("a")).click();
						sleep(10000);
						return;
					}
				}
				
			}catch(Exception e) {
			for (WebElement c : cuentas) {
				if (c.getText().equalsIgnoreCase(cuenta)) {
					(new Actions(driver)).click(c.findElement(By.tagName("a"))).build().perform();
					sleep(500);
					try {
						((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+c.findElement(By.tagName("a")).getLocation().y+")");
						sleep(300);
						c.findElement(By.tagName("a")).click();
						}catch(org.openqa.selenium.ElementNotVisibleException a) {}
					sleep(10000);
					return;
				}
			}
			}
			
		}
	}
	
	public void BajaryContinuar() {
		sleep(1000);
		WebElement cancelar=driver.findElement(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope"));
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+cancelar.getLocation().y+")");
		sleep(100);
		try {driver.findElement(By.id("IssueSelectStep_nextBtn")).click(); }
		catch(org.openqa.selenium.ElementNotVisibleException Inco) {
			try{driver.findElement(By.id("KnowledgeBaseResults_nextBtn")).click();}
			catch(org.openqa.selenium.ElementNotVisibleException BasedeConocmiento) {
				try{driver.findElement(By.id("NetworkCategory_nextBtn")).click();}
				catch(org.openqa.selenium.NoSuchElementException CategoriadeRed) {
					try{driver.findElement(By.id("CoverageValidation_nextBtn")).click();}
					catch(org.openqa.selenium.NoSuchElementException PosicionGeo) {
						driver.findElement(By.id("Address Section_nextBtn")).click();
					}
				}
			}
		}
	}
	
	public void clickContinuar() {
		try {Thread.sleep(500);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement BenBoton = driver.findElement(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope"));
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+BenBoton.getLocation().y+")");
		WebElement continuar= driver.findElement(By.xpath("//*[text() = 'Continuar']"));
		try {Thread.sleep(500);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		try {
		continuar.click();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		}
		catch(org.openqa.selenium.ElementNotVisibleException a) {
			List <WebElement> continuar2=driver.findElements(By.className("ng-binding"));
			for(WebElement c:continuar2) {
				if(c.getText().contains("Continuar")) {
					c.click();
					break;}}}		
	}
	
	/**vlc-slds-button--tertiary ng-binding ng-scope
	 * Selecciona una opcion luego de diagnosticar
	 * @param opcion
	 */
	public void seleccionarRespuesta(String opcion) {
		sleep(2000);
		Accounts accPage = new Accounts(driver);
	    driver.switchTo().frame(accPage.getFrameForElement(driver, By.cssSelector(".imgItemContainer.ng-scope")));
	    List<WebElement> preguntas=driver.findElements(By.cssSelector(".imgItemContainer.ng-scope"));
	    for(WebElement p:preguntas) {
	    	//System.out.println(p.getText());
	    	if(p.getText().toLowerCase().contains(opcion)) {
	    		sleep(8000);
	    		p.click();
	    		sleep(5000);
	    		return;
	    	}
	    }
	}
	
	public void buscarDireccion(String Direccion) {
		direccion.sendKeys(Direccion);
		sleep(1000);
		direccion.sendKeys(Keys.ARROW_DOWN);
		direccion.submit();
		sleep(5000);
		buscar.click();
		//ystem.out.println("primer Click");
		sleep(5000);
		//System.out.println("Segundo Click");
		buscar.click();
	}
	
	public void seleccionarPreguntaFinal(String opcion) {
		opcion=opcion.toLowerCase();
		boolean flag=false;
		for(WebElement opt:opcionesFinal) {
			if(opt.getText().toLowerCase().contains(opcion)) {
				opt.click();
				flag=true;
				return;
			}
		}
		if(!flag) System.out.println("Opcion no disponible");
	}

	
		
	
	
}
