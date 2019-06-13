package Funcionalidades;

import java.awt.AWTException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import DataProvider.ExcelUtils;
import Pages.BasePage;
import Pages.BeFan;
import Pages.CBS;
import Pages.ContactSearch;
import Pages.CustomerCare;
import Pages.DPW;
import Pages.Marketing;
import Pages.PagePerfilTelefonico;
import Pages.SCP;
import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.BeFanPom;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import PagesPOM.VentaDePackFw;
import Tests.CBS_Mattu;
import Tests.MDW;
import Tests.TestBase;

public class Regresion extends TestBase {

	private WebDriver driver;
	private LoginFw log;
	private VentaDePackFw vt;
	private GestionDeClientes_Fw ges;
	private CustomerCare cc;
	private CBS cbs;
	private CBS_Mattu cbsm;
	private ContactSearch contact;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private BeFan Botones;
	private MDW mdw;
	private SCP scp;
	String detalles;
	
	//@BeforeClass (alwaysRun = true)
	public void Sit02() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		contact = new ContactSearch(driver);
		log.LoginSit02();
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		//cc.irAConsolaFAN();
	}
	
	@BeforeClass (groups = "PerfilMayorista")
	public void initMayo() {
		driver = setConexion.setupEze();
		loginBeFANVictor(driver, "mayorista");
		Botones = new BeFan(driver);
		mdw = new MDW();
		cbsm = new CBS_Mattu();
		contact = new ContactSearch(driver);
		ges = new GestionDeClientes_Fw(driver);
		
	//	loginBeFAN(driver);
	}
	
	//@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		contact = new ContactSearch(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		vt = new VentaDePackFw(driver);
		Botones = new BeFan(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
	}
		
//	@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() throws IOException, AWTException {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		contact = new ContactSearch(driver);
		vt = new VentaDePackFw(driver);
		Botones = new BeFan(driver);
		log.loginTelefonico();
		ges.irAConsolaFAN();
	}
	
	//@BeforeClass (groups = "PerfilAgente")
	public void initAgente() throws IOException, AWTException {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		contact = new ContactSearch(driver);
		vt = new VentaDePackFw(driver);		
		Botones = new BeFan(driver);
		log.loginAgente();
		ges.irAConsolaFAN();
	}
	
//	@BeforeMethod (alwaysRun = true)
	public void setup() throws Exception {
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
	}

	//@AfterMethod(alwaysRun=true)
	public void after() throws IOException {
		guardarListaTxt(sOrders);
		sOrders.clear();
		tomarCaptura(driver,imagen);
		sleep(2000);
	}

	//@AfterClass (alwaysRun = true)
	public void quit() throws IOException {
		driver.quit();
		sleep(5000);
	}
	
	@Test(groups = { "PreactivacionBeFan", "PerfilMayorista" })
    public void preactivacion() throws Exception {
    	BeFan Botones = new BeFan(driver);
        Object[][] testObjArray;
        testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"seriales",1,1,8,"Preactivacion");
        String nombreArchivo = "SerialBalido";
        int filaExcel = 0;
        for (int i = 0; i < testObjArray.length; i++) {
            if (testObjArray[i][1].toString().equalsIgnoreCase(nombreArchivo)) {
                filaExcel = i;
                break;
            }
        }
        String path = testObjArray[filaExcel][0].toString();
        String deposito = testObjArray[filaExcel][2].toString();
        String prefijo1 = testObjArray[filaExcel][3].toString();
        String cantidad = testObjArray[filaExcel][7].toString();
        int numeroDeLineas = Integer.parseInt(cantidad);
        Botones.andaAlMenu("sims", "importacion");
        Botones.SISeleccionDeDeposito(deposito);
        Botones.SISeleccionDePrefijo(prefijo1);
        Botones.SISeleccionCantidadDePrefijo(cantidad);
        Botones.SIClickAgregar();
        Botones.SIImportarArchivo(nombreArchivo = Botones.LecturaDeDatosTxt(path + "\\seriales.txt", numeroDeLineas));
        Botones.SIClickImportar();
        String mensaje = Botones.SIMensajeModal();
        Assert.assertTrue(mensaje.contentEquals("El archivo se import\u00f3 correctamente."));
        Botones.SIClickAceptarImportar();
        nombreArchivo = "seriales" + nombreArchivo.substring(nombreArchivo.length()-18, nombreArchivo.length()-4);
        File informacion = new File(path + "\\resultados\\informacion" + nombreArchivo + ".txt");
        System.out.println("ESTE ES EL NOMBRE DEL ARCHIVO QUE SE IMPORTO: " + nombreArchivo);
        BufferedWriter info = new BufferedWriter(new FileWriter(informacion));
        info.write("Nombre del archivo: " + nombreArchivo);
        info.close();
	}
	
	//@Test (groups = "PerfilTelefonico", dataProvider="rNuevaNomina") 
	public void TS_001_Nominacion_Cliente_Nuevo_Telefonico(String sLinea, String sDni, String sNombre, String sApellido, String sGenero, String sFnac, String sEmail, String sProvincia, String sLocalidad,String sZona, String sCalle, String sNumCa, String sCP, String tDomic) { 
		imagen = "TS_001";
		detalles = imagen + "- Nominacion - DNI:" + sDni;		
		cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("PhoneNumber")));
		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
		driver.findElement(By.id("SearchClientsDummy")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(".slds-button.slds-button--icon.slds-m-right--x-small.ng-scope")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--icon.slds-m-right--x-small.ng-scope")).click();
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".slds-hint-parent.ng-scope"), 0));
		WebElement botonNominar = null;
		for (WebElement x : driver.findElements(By.cssSelector(".slds-hint-parent.ng-scope"))) {
			if (x.getText().toLowerCase().contains("plan con tarjeta"))
				botonNominar = x;
		}
		for (WebElement x : botonNominar.findElements(By.tagName("td"))) {
			if (x.getAttribute("data-label").equals("actions"))
				botonNominar = x;
		}
		botonNominar.findElement(By.tagName("a")).click();
		cambioDeFrame(driver, By.id("DocumentTypeSearch"),0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("DocumentInputSearch")));
		contact.crearClienteNominacion("DNI", sDni, sGenero, sNombre, sApellido, sFnac, sEmail);
		contact.tipoValidacion("documento");
		File directory = new File("Dni.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(".slds-input.ng-pristine.ng-untouched.ng-empty.ng-invalid.ng-invalid-required")));
		contact.completarDomicilio(sProvincia, sLocalidad, sZona, sCalle, sNumCa, sCP, tDomic);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("FinishProcess_nextBtn")));
		List <WebElement> element = driver.findElement(By.id("NominacionExitosa")).findElements(By.tagName("p"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("nominaci\u00f3n exitosa!")) {
				a = true;
				System.out.println(x.getText());
			}
		}
		driver.findElement(By.id("FinishProcess_nextBtn")).click();
		cbsm.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
		Assert.assertTrue(a);
	}
	
	//@Test (groups = "PerfilOficina", dataProvider = "rExistenteNomina") 
	public void TS85097_CRM_Movil_REPRO_Nominatividad_Cliente_Existente_Presencial_DOC_OfCom(String sLinea, String sDni, String sNombre, String sApellido) {
		imagen = "TS85097";
		detalles = imagen + " -Nominacion: " + sDni+"- Linea: "+sLinea;
		boolean nominacion = false;
		cambioDeFrame(driver,By.id("SearchClientDocumentType"),0);
		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(10000);
		WebElement cli = driver.findElement(By.id("tab-scoped-2")); 	
		cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).click();
		sleep(3000);
		List<WebElement> Lineas = driver.findElement(By.id("tab-scoped-2")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement UnaL: Lineas) {
			if(UnaL.getText().toLowerCase().contains("plan con tarjeta repro")) {
				UnaL.findElements(By.tagName("td")).get(6).findElement(By.tagName("svg")).click();
				System.out.println("Linea Encontrada");
				break;
			}
		}
		sleep(3000);
		contact.searchContact2("DNI", sDni, "Masculino");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Contact_nextBtn")));
		driver.findElement(By.id("Contact_nextBtn")).click();	
		sleep(10000);
		contact.tipoValidacion("documento");
		File directory = new File("Dni.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		sleep(18000);
		for (WebElement x : driver.findElement(By.id("NominacionExitosa")).findElements(By.tagName("p"))) {
			if (x.getText().toLowerCase().contains("nominaci\u00f3n exitosa!"))
				nominacion = true;
		}
		Assert.assertTrue(nominacion);
		driver.findElement(By.id("FinishProcess_nextBtn")).click();
		sleep(3000);
		cbsm.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider="rRecargasTC")
	public void TS134332_CRM_Movil_REPRO_Recargas_Telefonico_TC(String sDNI, String sMonto, String sLinea, String sBanco, String sTarjeta, String sPromo, String sCuota, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTitular) {
		imagen = "TS134332";
		detalles = imagen + "-Recarga-DNI:" + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, (datoViejo.length()) - 4));
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Recarga de cr\u00e9dito");
		cambioDeFrame(driver, By.id("RefillAmount"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys(sMonto);
		driver.findElement(By.id("AmountSelectionStep_nextBtn")).click();
		cambioDeFrame(driver, By.id("InvoicePreview_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		String caso = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")).findElement(By.tagName("child")).findElements(By.tagName("p")).get(1).getText();
		caso = caso.substring(caso.lastIndexOf(" ")+1, caso.length());
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		selectByText(driver.findElement(By.id("BankingEntity-0")), sBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), sTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), sPromo);
		selectByText(driver.findElement(By.id("Installment-0")), sCuota);
		driver.findElement(By.id("CardNumber-0")).sendKeys(sNumTarjeta);
		selectByText(driver.findElement(By.id("expirationMonth-0")), sVenceMes);
		selectByText(driver.findElement(By.id("expirationYear-0")), sVenceAno);
		driver.findElement(By.id("securityCode-0")).sendKeys(sCodSeg);
		selectByText(driver.findElement(By.id("documentType-0")), "DNI");
		driver.findElement(By.id("documentNumber-0")).sendKeys(sDNI);
		driver.findElement(By.id("cardHolder-0")).sendKeys(sTitular);		
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("GeneralMessageDesing")));
		Assert.assertTrue(driver.findElement(By.id("GeneralMessageDesing")).getText().toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		cbsm.Servicio_NotificarPago(caso);
		String datoVNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoVNuevo.substring(0, (datoVNuevo.length()) - 4));
		System.out.println("datoinicial: "+ datosInicial);
		System.out.println("dato final: "+datosFinal);
		Assert.assertTrue(datosInicial + 900 == datosFinal);
		cc.buscarOrdenDiag(caso+"*");
		cc.verificarPedido(caso, "activada");
	}

	@Test (groups = "PerfilOficina", dataProvider="rSmsDescuento")
	public void TS_002_Compra_De_Pack_SMS_Descuento_De_Saldo_OOCC(String sDni, String sLinea, String sVentaPack){
		imagen ="TS_002";
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		ges.BuscarCuenta("DNI", sDni);
		try {ges.cerrarPanelDerecho();} catch (Exception e) {}
		ges.irAGestionEnCard("Comprar SMS");
		vt.packSMS(sVentaPack);	
		vt.tipoDePago("descuento de saldo");
		driver.findElement(By.id("SetPaymentType_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SaleOrderMessages_nextBtn")));
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
//		List <WebElement> wMessage = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope")).findElement(By.className("ng-binding")).findElements(By.tagName("p"));
//		boolean bAssert = wMessage.get(1).getText().contains("La orden se realiz\u00f3 con \u00e9xito!");
//		Assert.assertTrue(bAssert);
		String uMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		Assert.assertTrue(iMainBalance > uiMainBalance);
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sVentaPack));
	}
	
	//@Test (groups = "PerfilAgente", dataProvider="rDatosEfectivo")
	public void TS135801_CRM_Movil_PREVenta_de_pack_Paquete_M2M_10_MB_Factura_de_Venta_Efectivo_Presencial_PuntMa_Alta_Agente(String sDni, String sLinea, String sPack){
		imagen = "TS135801";
		detalles = "Venta de Pack "+imagen+"-DNI:"+sDni;
		ges.BuscarCuenta("DNI", sDni);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Comprar SMS");
		vt.packDatos(sPack);
		vt.tipoDePago("en factura de venta");
		driver.findElement(By.id("SetPaymentType_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("InvoicePreview_nextBtn")));
		String sOrden = cc.obtenerOrden2(driver);
		detalles+="-Orden:"+sOrden;
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SaleOrderMessages_nextBtn")));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(10000);
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		detalles+="-Monto:"+orden.split("-")[1]+"-Prefactura:"+orden.split("-")[0];
		cbsm.Servicio_NotificarPago(sOrden);
		//Assert.assertTrue(invoSer.PagoEnCaja("1006", accid, "1001", orden.split("-")[1], orden.split("-")[0],driver));
		sleep(30000);
		boolean a = false;
		for(int i= 0; i < 10 ; i++) {
			cambioDeFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"), 0);
			ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[id = 'ep'] table tr "), 25));
			WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
			String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
			if(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated")) {
				a = true;
			}else {
				driver.navigate().refresh();
				i++;
			}
		}
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sPack) && a);
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider="rMinutosTC")
	public void TS_003_Compra_De_Pack_Minutos_TC_Telefonico(String sDni, String sPack, String sLinea, String sBanco, String sTarjeta, String sPromo, String sCuota, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTitular){
		imagen = "TS_003";
		detalles = "Compra de pack "+imagen+"-DNI:"+sDni;
		ges.BuscarCuenta("DNI", sDni);
		try {ges.cerrarPanelDerecho();} catch (Exception e) {}
		ges.irAGestionEnCard("Comprar Internet");
		vt.packMinutos(sPack);		
		vt.tipoDePago("en factura de venta");
		driver.findElement(By.id("SetPaymentType_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("InvoicePreview_nextBtn")));
		String sOrden = cc.obtenerOrden2(driver);
		detalles+="-Orden:"+sOrden;
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		selectByText(driver.findElement(By.id("BankingEntity-0")), sBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), sTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), sPromo);
		selectByText(driver.findElement(By.id("Installment-0")), sCuota);
		driver.findElement(By.id("CardNumber-0")).sendKeys(sNumTarjeta);
		selectByText(driver.findElement(By.id("expirationMonth-0")), sVenceMes);
		selectByText(driver.findElement(By.id("expirationYear-0")), sVenceAno);
		driver.findElement(By.id("securityCode-0")).sendKeys(sCodSeg);
		selectByText(driver.findElement(By.id("documentType-0")), "DNI");
		driver.findElement(By.id("documentNumber-0")).sendKeys(sDni);
		driver.findElement(By.id("cardHolder-0")).sendKeys(sTitular);
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SaleOrderMessages_nextBtn")));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(20000);
		cc.buscarCaso(sOrden);
		boolean a = false;
		for (int i = 0; i < 10; i++) {
			cambioDeFrame(driver,By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"),0);
			ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[id = 'ep'] table tr "), 25));
			WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
			String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
			// ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[id = 'ep'] table tr "), 10));
			if (datos.equalsIgnoreCase("activada") || datos.equalsIgnoreCase("activated")) {
				a = true;
			} else {
				driver.navigate().refresh();
				i++;
			}
		}
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sPack) && a);
	}

	@Test(groups = { "GestionesPerfilTelefonico", "RenovacionDeCuota","E2E" }, dataProvider = "RenovacionCuotaConSaldo")
	public void TS_CRM_Movil_REPRO_Renovacion_De_Cuota_Telefonico_Descuento_De_Saldo_Con_Credito(String sDNI, String sLinea, String accid) {
		imagen = "TS_CRM_Movil_REPRO_Renovacion_De_Cuota_Telefonico_Descuento_De_Saldo_Con_Credito";
		detalles = "Renovacion de cuota " + imagen + "-DNI:" + sDNI;
		String datosInicial = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		ges.BuscarCuenta("DNI", sDNI);
		detalles += "-Cuenta:" + accid;
		ges.irAGestionEnCard("Renovacion de Datos");
		cambioDeFrame(driver, By.id("combosMegas"), 0);
		sleep(5000);
		List<WebElement> elementos = driver.findElement(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for (WebElement elemento : elementos) {
			if (elemento.getText().contains("50 MB")) {
				elemento.findElement(By.className("slds-checkbox")).click();
				break;
			}
		}
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("CombosDeMegas_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='slds-radio ng-scope']")));
		buscarYClick(driver.findElements(By.cssSelector("[class='slds-radio ng-scope']")), "contains", "Saldo");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SetPaymentType_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		sleep(10000);
		String datosFinal = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
	}

	@Test(groups = { "GestionesPerfilOficina", "RenovacionDeCuota", "E2E" }, dataProvider = "RenovacionCuotaConSaldo")
	public void TS130056_CRM_Movil_REPRO_Renovacion_de_cuota_Presencial_Reseteo_200_MB_por_Dia_Efectivo_con_Credito(String sDNI, String sLinea, String accid) throws AWTException {
		imagen = "TS130056";
		detalles = "Renocavion de cuota: " + imagen + " - DNI: " + sDNI + " - Linea: " + sLinea;
//		String datosInicial = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		ges.BuscarCuenta("DNI", sDNI);
		detalles += "-Cuenta: " + accid;
		ges.irAGestionEnCard("Renovacion de Datos");
		cambioDeFrame(driver, By.id("combosMegas"), 0);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"),2));
		List<WebElement> elementos = driver.findElements(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"));
		for (WebElement elemento : elementos) {
			if (elemento.getText().contains("200 MB")) {
				elemento.findElement(By.className("slds-checkbox")).click();
				break;
			}
		}
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("CombosDeMegas_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='slds-radio ng-scope']")));
		buscarYClick(driver.findElements(By.cssSelector("[class='slds-radio ng-scope']")), "contains", "Factura de Venta");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SetPaymentType_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("InvoicePreview_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		sleep(12000);
		String sOrden = cc.obtenerOrden2(driver);
		detalles+="-Orden: "+sOrden;
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SelectPaymentMethodsStep_nextBtn")));
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SaleOrderMessages_nextBtn")));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		System.out.println("orden = "+orden);
		detalles +="-Monto: "+orden.split("-")[2]+"-Prefactura: "+orden.split("-")[1];
		sOrders.add("Recargas" + orden + ", cuenta:"+accid+", DNI: " + sDni +", Monto:"+orden.split("-")[2]);
		cbsm.Servicio_NotificarPago(orden.split("-")[0]);
		driver.navigate().refresh();
		sleep(5000);
		String datosFinal = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres Adicionales");
		System.out.println("Datos Inicial "+datosInicial);
		System.out.println("Datos final "+datosFinal);
		Assert.assertTrue((Integer.parseInt(datosInicial)+153600)==Integer.parseInt(datosFinal));
		cambioDeFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"), 0);
		boolean condition = false;
		List<WebElement> tabla = driver.findElements(By.cssSelector("[class='detailList'] tr"));
		for (WebElement fila : tabla) {
			if (fila.getText().contains("Estado")) {
				String datos = fila.findElement(By.cssSelector("[class='dataCol col02']")).getText();
				System.out.println(datos);
				condition = datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated");
				break;
			}
		}
		Assert.assertTrue(condition);
	}
	
	@Test (groups = "PerfilAgente", dataProvider="rTCAgente")
	public void TS_Renovacion_De_Cuota_TC_Agente(String sDni, String sLinea, String accid, String sBanco, String sTarjeta, String sPromo, String sCuota, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTitular){
		imagen = "TS_009";
		detalles = null;
		detalles = "Renocavion de cuota: "+imagen+" - DNI: "+sDni+" - Linea: "+sLinea;
		String datosInicial = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		ges.BuscarCuenta("DNI", sDni);
		System.out.println("id "+accid);
		detalles += "-Cuenta: "+accid;
		ges.irAGestionEnCard("Renovacion de Datos");
		cambioDeFrame(driver, By.id("CombosDeMegas_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"), 2));
		List<WebElement> elementos = driver.findElements(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"));
		for(WebElement UnE:elementos) {
			if(UnE.findElement(By.tagName("td")).getText().contains("50 MB")) {
				UnE.findElement(By.className("slds-checkbox")).click();
			}
		}
		driver.findElement(By.id("CombosDeMegas_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SetPaymentType_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "en factura de venta");
		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("InvoicePreview_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		String sOrden = cc.obtenerOrden2(driver);
		detalles += "-Orden:" + sOrden;
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		cambioDeFrame(driver, By.id("BankingEntity-0"), 0);
		selectByText(driver.findElement(By.id("BankingEntity-0")), sBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), sTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), sPromo);
		selectByText(driver.findElement(By.id("Installment-0")), sCuota);
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("GeneralMessageDesing"))); 
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		System.out.println("orden = "+orden);
		detalles+=", Monto:"+orden.split("-")[2]+"Prefactura: "+orden.split("-")[1];
		cbsm.Servicio_NotificarPago(sOrden);
		sleep(5000);
		if(activarFalsos == true) {
			cbsm.Servicio_NotificarPago(sOrden);
			sleep(20000);
		}
		driver.navigate().refresh();
		String datosFinal = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		System.out.println("Inicial: "+datosInicial);
		System.out.println("Final: "+datosFinal);
		Assert.assertTrue((Integer.parseInt(datosInicial)+51200)==Integer.parseInt(datosFinal));
		cambioDeFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"), 0);
		boolean condition = false;
		List<WebElement> tabla = driver.findElements(By.cssSelector("[class='detailList'] tr"));
		for (WebElement fila : tabla) {
			if (fila.getText().contains("Estado")) {
				String datos = fila.findElement(By.cssSelector("[class='dataCol col02']")).getText();
				System.out.println(datos);
				condition = datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated");
				break;
			}
		}
		Assert.assertTrue(condition);	
	}

}