package Tests;

import java.awt.AWTException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

import Pages.CBS;
import Pages.ManejoCaja;
import Pages.setConexion;

public class CBS_Mattu extends TestBase {
	
	private WebDriver driver;
	
	//-------------------------------------------------------------------------------------------------
	//@Befor&After
	//@BeforeClass(alwaysRun=true)
	public void readySteady() throws Exception {
		this.driver = setConexion.setupEze();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		loginCBS(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	//@AfterClass(alwaysRun=true)
	public void tearDown() {
		driver.close();
	}
	
	//-------------------------------------------------------------------------------------------------
	//TCC =
	
	@Test
	public void cajita() {
		ManejoCaja MN = new ManejoCaja();
		this.driver = setConexion.setupEze();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		MN.ingresarCaja(driver);
		MN.configuracionesIniciales(driver);
		MN.seleccionarOpcionCatalogo(driver, "Cuentas por cobrar");
		MN.abrirCajaRegistradora(driver);
		MN.pagarTC(driver,"20181005000000098056","1000000026310001");
		/*MN.seleccionarOpcionCatalogo(driver, "Cuentas por cobrar");
		MN.cerrarCajaRegistradora(driver);*/
	}
	
	@Test
	public boolean cajeta(WebDriver driver, String prefactura, String cuenta) throws AWTException {
		ManejoCaja mn = new ManejoCaja();
		boolean exito = false;
		//this.driver = setConexion.setupEze();
		abrirPestaniaNueva(driver);
		ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
		sleep(5000);
		try {
			driver.switchTo().window(tabs2.get(1));
			mn.ingresarCaja(driver);
			exito = true;
		} catch(Exception ex1) {
			driver.close();
		    driver.switchTo().window(tabs2.get(0));
		}
		if (exito == true) {
			mn.configuracionesIniciales(driver);
			mn.seleccionarOpcionCatalogo(driver, "Cuentas por cobrar");
			mn.abrirCajaRegistradora(driver);
			//MN.seleccionarOpcionCatalogo(driver, "Cuentas por cobrar");
			mn.pagarEfectivo(driver,prefactura,cuenta);
			//llamar al cerrarcaja registradora
			mn.cerrarPestanias(driver);
			mn.cerrarCajaRegistradora(driver);
			driver.close();
		    driver.switchTo().window(tabs2.get(0));
		}
		return(exito);
	}
	
	@Test
	public void openPage() {
		String sEndPoint = "Pago en Caja";
		String sPaymentChannelID = "1003";
		String sAccountKey = "9900000326610001";
		String sPaymentMethod = "2001";
		String sAmount = "123005600";
		String sInvoiceno = "20180827000000056800";
		String sPaymentSerialNo = ((new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date())).toString()+Integer.toString((int)(Math.random()*1000));		
		SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
		CBS cCBS = new CBS();
		boolean sResponse = cCBS.sCBS_Request_ServicioWeb_Validador(sSCS.callSoapWebService(cCBS.sRequest(sPaymentSerialNo, sPaymentChannelID, sAccountKey, sPaymentMethod, sAmount, sInvoiceno), sEndPoint));
		System.out.println("sResponse: " + sResponse);
	}
	
	public boolean PagoEnCaja(String sPaymentChannelID, String sAccountKey, String sPaymentMethod, String sAmount, String sInvoiceno, WebDriver driver) throws AWTException {
		if (cajeta(driver,sInvoiceno, sAccountKey)==false) {
			String sEndPoint = "Pago en Caja";
			String sPaymentSerialNo = ((new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date())).toString()+Integer.toString((int)(Math.random()*1000));
			SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
			CBS cCBS = new CBS();
			boolean sResponse = cCBS.sCBS_Request_ServicioWeb_Validador(sSCS.callSoapWebService(cCBS.sRequest(sPaymentSerialNo, sPaymentChannelID, sAccountKey, sPaymentMethod, sAmount, sInvoiceno), sEndPoint));
			System.out.println("sResponse: " + sResponse);
			return(sResponse);
		}
		return (true);
	}
	
	@Test
	public void openPage2(String sOrder) {
		String sEndPoint = "Pago Simulado";
		//String sOrder = "00080253";		
		SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
		CBS cCBS = new CBS();
		Document sResponse = sSCS.callSoapWebService(cCBS.sRequestByOrder(sOrder), sEndPoint);
		System.out.println("sResponse: " + sResponse);
	}
	
	@Test
	public void ValidarInfoCuenta(String sLinea, String sNombre, String sApellido, String sPlan) {
		String sEndPoint = "Datos Usuario";
		//String sLinea = "";
		String sMessageSeq = "QCI"+ ((new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date())).toString()+Integer.toString((int)(Math.random()*1000));
		String sImsi = "";
		String sICCD = "";
		SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
		CBS cCBS = new CBS();
		Assert.assertTrue(cCBS.sCBS_Request_Validador_Alta_Linea(sSCS.callSoapWebService(cCBS.sRequestByLinea(sLinea, sMessageSeq), sEndPoint), sLinea, sImsi, sICCD, sNombre, sApellido,sPlan));
	}
	
	@Test
	public boolean PagaEnCajaTC(String sPaymentChannelID, String sAccountKey, String sPaymentMethod, String sAmount, String sInvoiceno, String sAccountNumber, String sAccountName, String sExpirationDate, String sCVV, String sCardHolderName, String sCardHolderNumber) {
		String sEndPoint = "PagoEnCaja";
		String sMessageSeq = ((new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date())).toString()+Integer.toString((int)(Math.random()*1000));
		
		SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
		CBS cCBS = new CBS();
		Document sResponse = cCBS.sCBS_TC_Request_Validador(sSCS.callSoapWebService(cCBS.sRequestByTC(sMessageSeq, sPaymentChannelID, sAccountKey, sPaymentMethod, sAmount, sAccountNumber, sAccountName, sExpirationDate, sCVV, sInvoiceno, sCardHolderName, sCardHolderNumber), sEndPoint));
		//cCBS.sCBS_TC_Request_Validador(sResponse);
		System.out.println("sResponde ="+sResponse);
		boolean bAssert = sResponse.equals("true");
		return bAssert;
	}
	
	@Test
	public Document Servicio_queryLiteBySubscriber(String sLinea) {
		String sEndPoint = "Datos Usuario";
		//String sLinea = "";
		String sMessageSeq = "QCI"+ ((new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date())).toString()+Integer.toString((int)(Math.random()*1000));
		SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
		CBS cCBS = new CBS();
		Document sResponse = cCBS.sValidacion_ResponseQueryLiteBySubscriber(sSCS.callSoapWebService(cCBS.sRequestQueryLiteBySubscriber(sLinea, sMessageSeq), sEndPoint));
		System.out.println("sResponde ="+sResponse);
		//Assert.assertFalse(sResponse.startsWith("false"));
		System.out.println(cCBS.ObtenerValorResponse(sResponse, "bcs:MainBalance"));
		return sResponse;
	}
	
	@Test
	public Document Servicio_QueryCustomerInfo(String sLinea) {
		String sEndPoint = "Datos Usuario";
		//String sLinea = "";
		String sMessageSeq = "QCI"+ ((new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date())).toString()+Integer.toString((int)(Math.random()*1000));
		SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
		CBS cCBS = new CBS();
		Document Response = cCBS.sValidacion_ResponseQueryLiteBySubscriber(sSCS.callSoapWebService(cCBS.sRequestByLinea(sLinea, sMessageSeq), sEndPoint));
		return Response;
	}
	
	@Test
	public Document Servicio_QueryFreeUnit(String sLinea) {
		String sEndPoint = "unidades libres";
		//String sLinea = "";
		String sMessageSeq = "QCI"+ ((new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date())).toString()+Integer.toString((int)(Math.random()*1000));
		SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
		CBS cCBS = new CBS();
		Document Response = cCBS.sValidacion_ResponseQueryLiteBySubscriber(sSCS.callSoapWebService(cCBS.sRequestQueryFreeUnit(sLinea, sMessageSeq), sEndPoint));
		return Response;
	}
	
	@Test
	public Document Servicio_obtenerInformacionOrden(String sOrden) {
		String sEndPoint = "obtener informacion orden";
		//String sLinea = "";
		String sFecha =((new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())).toString();
		SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
		CBS cCBS = new CBS();
		Document sResponse = cCBS.sValidacion_ResponseObtenerInformacionOrden(sSCS.callSoapWebService(cCBS.sRequestObtenerInformacionOrden(sOrden, sFecha), sEndPoint));
		//Assert.assertFalse(sResponse.startsWith("false"));
		System.out.println(cCBS.ObtenerValorResponse(sResponse, "ns2:idCliente1"));
		return sResponse;
	}
	
	@Test
	public Document Servicio_notificarResultadoOrden(String sOrden, String sCodPag) {
		String sEndPoint = "notificar resultado orden";
		//String sLinea = "";
		String sFecha =((new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())).toString();
		String sHora =((new java.text.SimpleDateFormat("HHmmss")).format(new Date())).toString();
		SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
		CBS cCBS = new CBS();
		Document sResponse = cCBS.sValidacion_ResponseNotificarResultadoOrden(sSCS.callSoapWebService(cCBS.sRequestNotificarResultadoOrden(sOrden, sFecha, sHora,sCodPag), sEndPoint));
		//Assert.assertFalse(sResponse.startsWith("false"));
		System.out.println(cCBS.ObtenerValorResponse(sResponse, "ns2:NotificarResultadoOrdenResponse"));
		return sResponse;
	}
	
	@Test
	public void PagarTCPorServicio(String sOrden) throws KeyManagementException, NoSuchAlgorithmException {
		//String sOrden = "00009148";
		SOAPClientSAAJ.turnOffSslChecking();
		Document doc = Servicio_obtenerInformacionOrden(sOrden);
		CBS cCBS = new CBS();
		String CodPag = cCBS.obtenerValorCodPago(doc);
		System.out.println("CodPago:"+CodPag);
		Servicio_notificarResultadoOrden(sOrden,CodPag);
	}
	
	@Test
	public String obtenerStatusLinea(String sLinea) {
		String sEndPoint = "Datos Usuario";
		//String sLinea = "";
		String sMessageSeq = "QCI"+ ((new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date())).toString()+Integer.toString((int)(Math.random()*1000));
		SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
		CBS cCBS = new CBS();
		Document sResponse = cCBS.sValidacion_ResponseQueryLiteBySubscriber(sSCS.callSoapWebService(cCBS.sRequestQueryLiteBySubscriber(sLinea, sMessageSeq), sEndPoint));
		System.out.println("sResponde ="+sResponse);
		//Assert.assertFalse(sResponse.startsWith("false"));
		return(cCBS.sacarStatusLinea(sResponse));
	}
	
	@Test(dataProvider = "LineasNominadas")
	public void ObtenerInfoNominacion(String sLinea) throws IOException {
		String sEndPoint = "Datos Usuario";
		//String sLinea = "3572408521";
		//String sLinea = "";
		String sMessageSeq = "QCI"+ ((new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date())).toString()+Integer.toString((int)(Math.random()*1000));
		SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
		CBS cCBS = new CBS();
		String texto = null;
		texto = sLinea+(cCBS.ObtenerDatosNominacion(sSCS.callSoapWebService(cCBS.sRequestByLinea(sLinea, sMessageSeq), sEndPoint)));
		guardarLineasNominadas(texto);
	}
	
	@Test
	public Document Servicio_Recharge(String sLinea, String sMonto, String sCanal) {
		String sEndPoint = "pago en caja";
		//String sMonto = "35000000";
		//String sLinea = "5572408875";
		String sFecha =((new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())).toString();
		SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
		CBS cCBS = new CBS();
		Document sResponse = sSCS.callSoapWebService(cCBS.sRequestRecharge(sLinea, sFecha, sMonto, sCanal), sEndPoint);
		Assert.assertTrue(cCBS.sValidacion_ResponseRecharge(sResponse));
		//Assert.assertFalse(sResponse.startsWith("false"));
		System.out.println(cCBS.ObtenerValorResponse(sResponse, "cbs:ResultDesc"));
		return sResponse;
	}
	
	@Test
	public Document Servicio_Loan(String sLinea, String sMonto) {
		String sEndPoint = "pago en caja";
		//String sMonto = "35000000";
		//String sLinea = "5572408875";
		String sFecha =((new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())).toString();
		SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
		CBS cCBS = new CBS();
		Document sResponse = sSCS.callSoapWebService(cCBS.sRequestLoan(sLinea, sFecha, sMonto), sEndPoint);
		Assert.assertTrue(cCBS.sValidacion_ResponseRecharge(sResponse));
		//Assert.assertFalse(sResponse.startsWith("false"));
		System.out.println(cCBS.ObtenerValorResponse(sResponse, "cbs:ResultDesc"));
		return sResponse;
	}
	
	@Test
	public Document Servicio_RealizarAltaSuscripcion(String sLinea, String sCodigo) {
		String sEndPoint = "alta suscripcion";
		//String sMonto = "35000000";
		//String sLinea = "5572408875";
		SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
		CBS cCBS = new CBS();
		Document sResponse = sSCS.callSoapWebService(cCBS.sRequestRealizarAltaSuscripcion(sLinea, sCodigo), sEndPoint);
		Assert.assertTrue(cCBS.sValidacion_RealizarAltaSuscripcion(sResponse));
		System.out.println(cCBS.ObtenerValorResponse(sResponse, "v2.:codInteraccionNegocio"));
		return sResponse;
	}
	
	@Test
	public boolean Imprimir(WebDriver driver, String prefactura, String cuenta) throws AWTException {
		/*String prefactura = "20181203000000105017";
		String cuenta = "1000000026310001";*/
		ManejoCaja mn = new ManejoCaja();
		boolean exito = false;
		//this.driver = setConexion.setupEze();
		abrirPestaniaNueva(driver);
		ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
		sleep(5000);
		try {
			driver.switchTo().window(tabs2.get(1));
			mn.ingresarCaja(driver);
			exito = true;
		} catch(Exception ex1) {
			driver.close();
		    driver.switchTo().window(tabs2.get(0));
		}
		if (exito == true) {
			mn.configuracionesIniciales(driver);
			mn.imprimirFactura(driver,prefactura,cuenta);
			//llamar al cerrarcaja registradora
			mn.cerrarPestanias(driver);
			mn.cerrarCajaRegistradora(driver);
			driver.close();
		    driver.switchTo().window(tabs2.get(0));
		}
		return (exito);
	}
	
	public void probarCaja(WebDriver driver) throws AWTException {
		ManejoCaja mn = new ManejoCaja();
		boolean exito = false;
		abrirPestaniaNueva(driver);
		ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
		sleep(5000);
		try {
			driver.switchTo().window(tabs2.get(1));
			mn.ingresarCaja(driver);
			exito = true;
		} catch(Exception ex1) {
			driver.close();
		    driver.switchTo().window(tabs2.get(0));
		}
		if (exito == true) {
			mn.configuracionesIniciales(driver);
			mn.seleccionarOpcionCatalogo(driver, "Cuentas por cobrar");
			mn.abrirCajaRegistradora(driver);
			mn.cerrarPestanias(driver);
			mn.cerrarCajaRegistradora(driver);
			driver.close();
		    driver.switchTo().window(tabs2.get(0));
		}
	}
	
	@Test
	public void Servicio_NotificarPago(String sOrder) {
		String sEndPoint = "Notificar Pago";
		//String sOrder = "00014864";		
		SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
		CBS cCBS = new CBS();
		Document sResponse = sSCS.callSoapWebService(cCBS.sNotificarPago(sOrder), sEndPoint);
		Assert.assertTrue(cCBS.ObtenerValorResponse(sResponse, "ns2:resultDesc").equalsIgnoreCase("ok")&&cCBS.ObtenerValorResponse(sResponse, "ns2:resultCode").equalsIgnoreCase("0"));
		System.out.println("sResponse: " + sResponse);
	}
	
	@Test
	public void Servicio_NotificarEmisionFactura(String sOrder) {
		String sEndPoint = "Notificar Pago";
		//String sOrder = "00014866";		
		SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
		CBS cCBS = new CBS();
		Document sResponse = sSCS.callSoapWebService(cCBS.sNotificarEmisionFactura(sOrder), sEndPoint);
		Assert.assertTrue(cCBS.ObtenerValorResponse(sResponse, "ns2:resultDesc").equalsIgnoreCase("ok")&&cCBS.ObtenerValorResponse(sResponse, "ns2:resultCode").equalsIgnoreCase("0"));
		System.out.println("sResponse: " + sResponse);
	}
	
	@Test
	public Document verificarSaldo(String sAccountKey) {
		String sEndPoint = "verificar saldo";
		SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
		CBS cCBS = new CBS();
		Document response = cCBS.sValidacion_VerificarSaldo(sSCS.callSoapWebService(cCBS.sRequestVerificarSaldoEnFacturacion(sAccountKey), sEndPoint));
		return response;
	}
}