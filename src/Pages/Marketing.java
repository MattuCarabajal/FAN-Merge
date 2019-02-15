package Pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import Tests.TestBase;

public class Marketing extends CustomerCare {
	
	/*Datos login Marketing:s
	Username: usit@telecom.sit
	Password: pruebas09
	 */
	
	//final WebDriver driver;
	
	//Constructor
	public Marketing(WebDriver driver){
		super(driver);
	}
	
	//Sleep
	
	//Working
	public void sleepShort(int iDiferencia) {
		try {Thread.sleep(5000 + iDiferencia);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void sleepMedium(int iDiferencia) {
		try {Thread.sleep(10000 + iDiferencia);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void sleepLong(int iDiferencia) {
		try {Thread.sleep(15000 + iDiferencia);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	//Weekends
	/*
	public void sleepShort(int iDiferencia) {
		try {Thread.sleep(8000 + iDiferencia);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void sleepMedium(int iDiferencia) {
		try {Thread.sleep(12000 + iDiferencia);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void sleepLong(int iDiferencia) {
		try {Thread.sleep(18000 + iDiferencia);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	} 
	*/
	
	public void clubPersonal (String sAltaBajaModificacion) {
		waitForVisibilityOfElementLocated(By.cssSelector(".slds-grid.slds-wrap.via-slds-action-grid-card"));
		WebElement wMenuABM = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.via-slds-action-grid-card"));
		List<WebElement> lMenuesABM = wMenuABM.findElements(By.cssSelector(".slds-size--1-of-2.slds-x-small-size--1-of-3.slds-medium-size--1-of-4.slds-large-size--1-of-6.slds-align--absolute-center.slds-m-bottom--xx-small.slds-m-top--xx-small.slds-p-left--xx-small.slds-p-right--xx-small.slds-grid"));
		
		switch (sAltaBajaModificacion.toLowerCase()) {
			case "alta":
				lMenuesABM.get(0).click();
				sleep(7000);
				cambiarAFrameActivo();
				break;
			case "baja":
				lMenuesABM.get(1).click();
				sleepShort(0);
				BasePage cambioFrame=new BasePage();
				driver.switchTo().defaultContent();
				driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.cssSelector(".slds-page-header.vlc-slds-page--header.ng-scope")));
				break;
			case "modificacion":
				lMenuesABM.get(2).click();
				break;
			default:
				System.out.println("Las opciones validas solo son 'alta', 'baja', y 'modificacion'.");
		}
	}
	
	public void closeActiveTab () {
		CustomerCare cCC = new CustomerCare(driver);
		WebElement wCloseTab = cCC.obtenerPestanaActiva();
		Actions aAction = new Actions(driver);
		WebElement wClose = wCloseTab.findElement(By.className("x-tab-strip-close"));
		aAction.moveToElement(wClose).perform();
		wClose.click();
	}
	
	public void irAGestionMarketing() {
		CustomerCare cCC = new CustomerCare(driver);
		cCC.buscarGestion("Club Personal");
		if (gestionesEncontradas.isEmpty()) {
			System.err.println("ERROR: No existe la gesti�n 'Club Personal'");
			Assert.assertFalse(gestionesEncontradas.isEmpty());
		}
		for (WebElement wAux:gestionesEncontradas) {
			if (wAux.getText().equalsIgnoreCase("Club Personal")) {
				wAux.click();
			}
		}
		//gestionesEncontradas.get(1).click();
		sleep(3000);
		cambiarAFrameActivo();
		/*BasePage cambioFrame=new BasePage(driver);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.className("actions-content")));
		driver.findElement(By.xpath("//input[@ng-model='searchTerm']")).clear();
		driver.findElement(By.xpath("//input[@ng-model='searchTerm']")).sendKeys("Club Personal");*/
	}
	
	/*public void buscarGestion(String gest) {
		CustomerCare cCC = new CustomerCare(driver);
		cCC.panelDerecho();
		buscadorGestiones.clear();
		buscadorGestiones.sendKeys(gest);
	}*/
	
	public void cambiarAFrameActivo() {
		driver.switchTo().defaultContent();
		for (WebElement t : panelesCentrales) {
			if (!t.getAttribute("class").contains("x-hide-display")) {
				driver.switchTo().frame(t.findElement(By.cssSelector("div iframe")));
			}
		}
	}
	
	public List<WebElement> traerColumnaElement(WebElement wBody, int iColumnas, int iColumna) {
		//wBody = WebElement del cuerpo completo del cuadro
		//iColumnas = cantidad de columnas
		//iColumna = columna requerida
		WebElement wSubBody = wBody.findElement(By.tagName("tbody"));
		List<WebElement> wRows = wSubBody.findElements(By.tagName("tr"));
		List<WebElement> wElements =   new ArrayList<WebElement>();
		for (int i = 0; i < wRows.size(); i++) {
			wElements.clear();
			wElements = wRows.get(i).findElements(By.tagName("td"));
			if (wElements.size() < iColumnas) {
				wRows.remove(i);
			}
		}
		List<WebElement> wElementsExtrated =  new ArrayList<WebElement>();
		for (WebElement wAux:wRows) {
			if (!wAux.getText().isEmpty()) {
				List<WebElement> wColumns = wAux.findElements(By.tagName("td"));
				wElementsExtrated.add(wColumns.get(iColumna - 1));
			}
		}
		
		return wElementsExtrated;
	}
	
	public List<WebElement> obtenerBotonesClubPersonal() {
		return driver.findElements(By.cssSelector(".slds-align--absolute-center.slds-m-bottom--xx-small"));
	}
	
	public void seleccionarFecha (int iColumna, int iDias, String sId, String sTiempo) {
		List <WebElement> wBodyComplete = driver.findElements(By.cssSelector(".slds-media.slds-media--center.slds-has-flexi-truncate"));
		WebElement wBody = wBodyComplete.get(5);
		List <WebElement> wColumna = traerColumnaElement(wBody, 7, iColumna);
		WebElement wFechaDesde = wColumna.get(0).findElement(By.id(sId));
		wFechaDesde.click();
		wColumna = traerColumnaElement(wBody, 7, iColumna);
		wFechaDesde = wColumna.get(0);
		List<WebElement> wDias = wFechaDesde.findElements(By.tagName("td"));
		switch (sTiempo.toLowerCase()) {
			case "futuro":
				for(int i = 0; i < wDias.size(); i++) {
					if (wDias.get(i).getAttribute("class").equals("slds-is-today")) {
						wDias.get(i + iDias).findElement(By.tagName("span")).click();
						break;
					}
				}
				break;
			case "pasado":
				for(int i = 0; i < wDias.size(); i++) {
					if (wDias.get(i).getAttribute("class").equals("slds-is-today")) {
						wDias.get(i - iDias).findElement(By.tagName("span")).click();
						break;
					}
				}
			default:
				System.out.println("Palabra incorrecta. Usar 'Futuro' o 'Pasado'");
				break;
		}
	}
	
	public void cambioCuenta(String sVista, String sCliente) throws IOException {
		TestBase tTB = new TestBase();
		CustomerCare cCC = new CustomerCare(driver);
		cCC.cerrarTodasLasPestanas();
		tTB.goToLeftPanel(driver, "Cuentas");
		WebElement frame0 = driver.findElement(By.tagName("iframe"));
		driver.switchTo().frame(frame0);
		tTB.waitFor(driver, (By.name("fcf")));
		Select field = new Select(driver.findElement(By.name("fcf")));
		field.selectByVisibleText(sVista);
		sleepMedium(0);
		WebElement wBody = driver.findElement(By.className("x-grid3-body"));
		List<WebElement> wAccountName = wBody.findElements(By.cssSelector(".x-grid3-col.x-grid3-cell.x-grid3-td-ACCOUNT_NAME"));
		String sNombreCuenta = tTB.buscarCampoExcel(0, sCliente, 1);
		
		for (WebElement wAux:wAccountName) {
			WebElement wContenido = wAux.findElement(By.tagName("span"));
			
			if (wContenido.getText().toLowerCase().equals(sNombreCuenta.toLowerCase())) {
				wAux.click();
				break;
			}
		}
		sleepShort(3);
		BasePage cambioFrame=new BasePage();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.cssSelector(".slds-input.actionSearch.ng-pristine.ng-untouched.ng-valid.ng-empty")));
		driver.findElement(By.cssSelector(".slds-input.actionSearch.ng-pristine.ng-untouched.ng-valid.ng-empty")).clear();
		driver.findElement(By.cssSelector(".slds-input.actionSearch.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("Club Personal");
		List<WebElement> wGestiones = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.slds-truncate"));
		
		for (WebElement wAux2:wGestiones) {
			WebElement wContenido = wAux2.findElement(By.cssSelector(".slds-text-body_regular.ta-button-font"));
			if (wContenido.getText().toLowerCase().equals("club personal")) {
				wAux2.click();
				break;
			}
		}
		sleepMedium(0);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.cssSelector(".slds-panel__section.slds-p-around--small")));
	}
	
	public Boolean verificarMensajeDeErrorCuentaFraude() {
		waitForVisibilityOfElementLocated(By.xpath("//ng-form[@id='TxtError']"));
		String msg = driver.findElement(By.xpath("//ng-form[@id='TxtError']")).getText();
		return msg.contains("Para continuar es necesario regularizar su estado de cuenta");
	}
	
	public String obtenerNumeroCasoCuentaFraude() {
		String msg = driver.findElement(By.xpath("//ng-form[@id='TxtError']")).getText();
		int i = 0;
		while(msg.charAt(i++) != '0') {	}
		return msg.substring(i-1, msg.length());
	}
	
	public String obtenerNumeroCasoAltaOBaja() {
		waitForVisibilityOfElementLocated(By.xpath("//ng-form[@id='Headline']"));
		String msg = driver.findElement(By.xpath("//ng-form[@id='Headline']")).getText();
		int i = 0;
		while(msg.charAt(i++) != '0') {	}
		return msg.substring(i-1, msg.length());
	}
	
	public String obtenerTextoCasoGeneradoAltaOBaja() {
		waitForVisibilityOfElementLocated(By.xpath("//ng-form[@id='Headline']"));
		return driver.findElement(By.xpath("//ng-form[@id='Headline']")).getText();
	}
	
	public String estadoAltaBaja (String sAltaBaja) {
		BasePage cambioFrame=new BasePage();
		WebElement wConsumerBox;
		WebElement wBusinessBox;
		WebElement wConsumerTable;
		WebElement wBusinessTable;
		List<WebElement> wConsumerTableRows;
		List<WebElement> wBusinessTableRows;
		WebElement wCTCheckBoxConsumer;
		WebElement wCTCheckBoxBusiness;
		WebElement wCTCheckBoxLabelConsumer;
		WebElement wCTCheckBoxLabelBusiness;
		WebElement wCTCheckBoxDisableConsumer;
		WebElement wCTCheckBoxDisableBusiness;
		String sCaso = "";
		switch (sAltaBaja.toLowerCase()) {
			case "alta":
				clubPersonal("Alta");
				sleepMedium(0);
				cambioFrame=new BasePage();
				driver.switchTo().defaultContent();
				driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("consumerAccounts")));
				wConsumerBox = driver.findElement(By.id("consumerAccounts"));
				driver.switchTo().defaultContent();
				driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("businessAccounts")));
				wBusinessBox = driver.findElement(By.id("businessAccounts"));
				wConsumerTable = wConsumerBox.findElement(By.tagName("tbody"));
				wConsumerTableRows = wConsumerTable.findElements(By.tagName("tr"));
				wCTCheckBoxConsumer = wConsumerTableRows.get(0).findElement(By.tagName("th"));
				wCTCheckBoxLabelConsumer = wCTCheckBoxConsumer.findElement(By.tagName("label"));
				wCTCheckBoxDisableConsumer = wCTCheckBoxLabelConsumer.findElement(By.tagName("input"));
				wBusinessTable = wBusinessBox.findElement(By.tagName("tbody"));
				wBusinessTableRows = wBusinessTable.findElements(By.tagName("tr"));
				wCTCheckBoxBusiness = wBusinessTableRows.get(0).findElement(By.tagName("th"));
				wCTCheckBoxLabelBusiness = wCTCheckBoxBusiness.findElement(By.tagName("label"));
				wCTCheckBoxDisableBusiness = wCTCheckBoxLabelBusiness.findElement(By.tagName("input"));
				if (wCTCheckBoxDisableConsumer.getAttribute("ng-disabled").equals("true") || wCTCheckBoxDisableBusiness.getAttribute("ng-disabled").equals("true")) {
					closeActiveTab();
					sCaso = darDeBajaCP("No lo uso", "");
					sleepShort(3);
					closeActiveTab();
					sleepShort(0);
					driver.switchTo().defaultContent();
					driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.cssSelector(".slds-panel__section.slds-p-around--small")));
					clubPersonal("Alta");
					sleepMedium(3);
					cambioFrame=new BasePage();
					driver.switchTo().defaultContent();
					driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.cssSelector(".slds-page-header.vlc-slds-page--header.ng-scope")));
				}
				break;
			case "baja":
				clubPersonal("Baja");
				sleepShort(3);
				cambioFrame=new BasePage();
				driver.switchTo().defaultContent();
				driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("consumerAccounts")));
				wConsumerBox = driver.findElement(By.id("consumerAccounts"));
				driver.switchTo().defaultContent();
				driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("businessAccounts")));
				wBusinessBox = driver.findElement(By.id("businessAccounts"));
				wConsumerTable = wConsumerBox.findElement(By.tagName("tbody"));
				wConsumerTableRows = wConsumerTable.findElements(By.tagName("tr"));
				wCTCheckBoxConsumer = wConsumerTableRows.get(0).findElement(By.tagName("th"));
				wCTCheckBoxLabelConsumer = wCTCheckBoxConsumer.findElement(By.tagName("label"));
				wCTCheckBoxDisableConsumer = wCTCheckBoxLabelConsumer.findElement(By.tagName("input"));
				wBusinessTable = wBusinessBox.findElement(By.tagName("tbody"));
				wBusinessTableRows = wBusinessTable.findElements(By.tagName("tr"));
				wCTCheckBoxBusiness = wBusinessTableRows.get(0).findElement(By.tagName("th"));
				wCTCheckBoxLabelBusiness = wCTCheckBoxBusiness.findElement(By.tagName("label"));
				wCTCheckBoxDisableBusiness = wCTCheckBoxLabelBusiness.findElement(By.tagName("input"));
				if (wCTCheckBoxDisableConsumer.getAttribute("ng-disabled").equals("true") || wCTCheckBoxDisableBusiness.getAttribute("ng-disabled").equals("true")) {
					closeActiveTab();
					sCaso = darDeAltaCP();
					sleepShort(3);
					closeActiveTab();
					sleepShort(0);
					driver.switchTo().defaultContent();
					driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.cssSelector(".slds-panel__section.slds-p-around--small")));
					clubPersonal("baja");
					sleepShort(3);
					cambioFrame=new BasePage();
					driver.switchTo().defaultContent();
					driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.cssSelector(".slds-page-header.vlc-slds-page--header.ng-scope")));
				}
				break;
			default:
				System.out.println("Selecci�n incorrecta, por favor selecciona 'Alta' o 'baja'");
		}
		return sCaso;
	}
	
	public void bajaMotivo(String sMotivo, String sOtros) {
		sleepShort(0);
		BasePage cambioFrame=new BasePage();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.cssSelector(".slds-panel__section.slds-p-around--small")));
		clubPersonal("baja");
		sleepShort(0);
		seleccionarCuenta("consumerAccounts");
		seleccionarCuenta("businessAccounts");
		sleepShort(0);
		BasePage bBP = new BasePage(driver);
		bBP.setSimpleDropdown(driver.findElement(By.id("SelectReason")), sMotivo);
		sleepShort(0);
		if (!sOtros.isEmpty()) {
			driver.findElement(By.id("Others")).clear();
			driver.findElement(By.id("Others")).sendKeys(sOtros);
		}
		sleepShort(0);
	}
	
	public String darDeBajaCP (String sMotivo, String sOtros) {
		bajaMotivo(sMotivo, sOtros);
		driver.findElement(By.id("CPMembershipCancellation_nextBtn")).click();
		sleepShort(0);
		driver.findElement(By.id("ConfirmStep_nextBtn")).click();
		sleepMedium(0);
		BasePage cambioFrame = new BasePage();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("Headline")));
		String sCaso = driver.findElement(By.id("Headline")).findElement(By.tagName("p")).getText();
		sCaso = sCaso.substring(44);
		return sCaso;
	}
	
	public String darDeAltaCP () {
		sleepShort(0);
		BasePage cambioFrame=new BasePage();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.cssSelector(".slds-panel__section.slds-p-around--small")));
		clubPersonal("alta");
		sleepShort(0);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("consumerAccounts")));
		seleccionarCuenta("consumerAccounts");
		seleccionarCuenta("businessAccounts");
		driver.findElement(By.id("AltaClubPersonal_nextBtn")).click();
		sleepShort(0);
		driver.findElement(By.id("ConfirmStep_nextBtn")).click();
		sleepMedium(0);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("Headline")));
		String sCaso = driver.findElement(By.id("Headline")).findElement(By.tagName("strong")).getText();
		return sCaso;
	}
	
	public void seleccionarCuenta(String sTable) {
		BasePage cambioFrame=new BasePage();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id(sTable)));
		sleepShort(0);
		WebElement wConsumerBox = driver.findElement(By.id(sTable));
		WebElement wConsumerTable= wConsumerBox.findElement(By.tagName("tbody"));
		List<WebElement> wConsumerTableRows = wConsumerTable.findElements(By.tagName("tr"));
		List<WebElement> wCTCheckBox = new ArrayList<WebElement>();
		for (WebElement wAux : wConsumerTableRows) {
			wCTCheckBox.add(wAux.findElement(By.tagName("th")));
		}
		for (WebElement wAux : wCTCheckBox) {
			wAux.findElement(By.tagName("label")).click();
		}
	} 
	
	public boolean corroborarCasoCerrado(String sCaso) {
		BasePage cambioFrame=new BasePage();
		driver.switchTo().defaultContent();
		sleepShort(0);
		driver.findElement(By.id("phSearchInput")).clear();
		driver.findElement(By.id("phSearchInput")).sendKeys(sCaso + "\n");
		sleepShort(0);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("searchResultsHolderDiv")));
		WebElement wBody = driver.findElement(By.id("Case_body")).findElement(By.tagName("table"));
		List <WebElement> wEstado = traerColumnaElement(wBody, 5, 3);
		Boolean bAssert = wEstado.get(0).getText().toLowerCase().equals("closed");
		return bAssert;
	}
	
	public Boolean visualizarCuentasBusinessUsuarioCP() {
	    waitForVisibilityOfElementLocated(By.xpath("//ng-form[@id='businessAccounts']//tbody"));
	    List<WebElement> listaCuentas = driver.findElements(By.xpath("//ng-form[@id='businessAccounts']//tbody//tr"));
		return (listaCuentas.size() > 0 && listaCuentas.get(0).isDisplayed());
	}
		 
	public Boolean visualizarCuentasConsumerUsuarioCP() {
		waitForVisibilityOfElementLocated(By.xpath("//ng-form[@id='consumerAccounts']//tbody"));
		List<WebElement> listaCuentas = driver.findElements(By.xpath("//ng-form[@id='consumerAccounts']//tbody//tr"));
		return (listaCuentas.size() > 0 && listaCuentas.get(0).isDisplayed());
	}
		  
	public Boolean visualizarCuentasSeleccionadasConsumerCP() {
		waitForVisibilityOfElementLocated(By.xpath("//ng-form[@id='consumerResult']//tbody"));
		return (driver.findElement(By.xpath("//ng-form[@id='consumerResult']//tbody")).getText().length() > 0);
	}
		 
	public Boolean visualizarCuentasSeleccionadasBusinessCP() {
		waitForVisibilityOfElementLocated(By.xpath("//ng-form[@id='businessResult']//tbody"));
		return (driver.findElement(By.xpath("//ng-form[@id='businessResult']//tbody")).getText().length() > 0);
	}
		  
	public void closeActiveUpperTab () {
		driver.switchTo().defaultContent();
		WebElement wTabBox = driver.findElement(By.id("ext-gen25"));
		List<WebElement> wTabs = wTabBox.findElements(By.tagName("li"));
		for(WebElement wAux : wTabs) {
			if (wAux.getAttribute("class").contains("x-tab-strip-active")) {
				Actions aAction = new Actions(driver);
				WebElement wCloseMovment = wAux.findElement(By.className("x-tab-strip-close"));
				aAction.moveToElement(wCloseMovment).perform();
				WebElement wClose = wAux.findElement(By.className("x-tab-strip-close"));
				wClose.click();
				break;
			}
		}
	}
	
	public Boolean verificarMensajeDeErrorEmail() {
		waitForVisibilityOfElementLocated(By.xpath("//ng-form[@id='TextEmailValidation']"));
		Boolean p = driver.findElement(By.xpath("//ng-form[@id='TextEmailValidation']")).isDisplayed();
		Boolean q = driver.findElement(By.xpath("//ng-form[@id='TextEmailValidation']")).getText().contains("es necesario indicar un email v�lido");
		return (p && q);
	}
	
	public void seleccionarMotivo(int num) {
		Select motivo = new Select(driver.findElement(By.xpath("//select[@id='SelectReason']")));
		motivo.selectByIndex(num);
	}
	
	public String unDigitoADosDigitos(int iNumero) {
		//Para las fechas por ejemplo, que se necesite que tenga un cero adelante
		String sNumero = String.valueOf(iNumero);
		
		if (sNumero.length() != 2) {
			sNumero = "0" + sNumero;
		}
		
		return sNumero;
	}
	
	public void ingresarANuevoProspecto() {
		driver.get("https://crm--sit.cs14.my.salesforce.com/home/home.jsp?tsid=02u41000000QWhf");
		driver.findElement(By.id("Lead_Tab")).click();
		driver.findElement(By.id("hotlist")).findElement(By.className("pbButton")).findElement(By.tagName("input")).click();
		sleepShort(0);
	}
	
	public void seleccionarCuentaMarketing(String sCuenta, String sVista) throws IOException {
		CustomerCare cCC = new CustomerCare(driver);
		cCC.cerrarTodasLasPestanas();
		goToLeftPanel(driver, "Cuentas");
		WebElement frame0 = driver.findElement(By.tagName("iframe"));
		driver.switchTo().frame(frame0);
		TestBase tTB = new TestBase();
		tTB.waitFor(driver, (By.name("fcf")));
		Select field = new Select(driver.findElement(By.name("fcf")));
		field.selectByVisibleText(sVista);
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement wBody = driver.findElement(By.className("x-grid3-body"));
		List<WebElement> wAccountName = wBody.findElements(By.cssSelector(".x-grid3-col.x-grid3-cell.x-grid3-td-ACCOUNT_NAME"));
		for (WebElement wAux2:wAccountName) {
			WebElement wContenido = wAux2.findElement(By.tagName("span"));
			if (wContenido.getText().toLowerCase().equals(sCuenta.toLowerCase())) {
				wAux2.click();
				break;
			}
		}
		sleepShort(0);
		irAGestionMarketing();
		sleepShort(0);
		BasePage cambioFrame=new BasePage();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.cssSelector(".slds-panel__section.slds-p-around--small")));
	}
	
	public int numerosAmigos(String sVOZ, String sSMS) {
		int iIndice = 2;
		System.out.println("sVOZ = " + sVOZ);
		if (!sVOZ.isEmpty() && !sVOZ.equalsIgnoreCase("**")) {
			iIndice = 0;
		}
		else {
			if (!sSMS.isEmpty() && !sSMS.equalsIgnoreCase("**")) {
				iIndice = 1;
			}
			else {
				System.out.println("Ambas celdas estan vacias en el DataProvider.");			}
		}
		return iIndice;
	}
	
	public boolean verificarNumerosAmigos(WebDriver driver, String sNumeroVOZ, String sNumeroSMS) {
		boolean aAssert = false;
		
		sleep(5000);
		TestBase tTb = new TestBase();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(tTb.cambioFrame(driver, By.cssSelector(".slds-col--padded.slds-size--1-of-2")));
		List<WebElement> wNumerosAmigos = driver.findElements(By.cssSelector(".slds-col--padded.slds-size--1-of-2"));
		int iIndice = numerosAmigos(sNumeroVOZ, sNumeroSMS);
		switch (iIndice) {
			case 0:
				aAssert = wNumerosAmigos.get(0).findElement(By.tagName("input")).getText().equals(sNumeroVOZ);
				break;
			case 1:
				aAssert = wNumerosAmigos.get(1).findElement(By.tagName("input")).getText().equals(sNumeroSMS);
				break;
		}
		
		return aAssert;
	}
	
	public boolean corroborarEstadCaso(String sCaso, String sEstado) {
		BasePage cambioFrame=new BasePage();
		driver.switchTo().defaultContent();
		sleepShort(0);
		driver.findElement(By.id("phSearchInput")).clear();
		driver.findElement(By.id("phSearchInput")).sendKeys(sCaso + "\n");
		sleepShort(0);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("searchResultsHolderDiv")));
		WebElement wBody = driver.findElement(By.id("Case_body")).findElement(By.tagName("table"));
		List <WebElement> wEstado = traerColumnaElement(wBody, 5, 3);
		Boolean bAssert = wEstado.get(0).getText().toLowerCase().equals(sEstado);
		return bAssert;
	}
	
}