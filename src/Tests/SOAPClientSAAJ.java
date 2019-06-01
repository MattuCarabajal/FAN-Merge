package Tests;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.soap.*;

import org.w3c.dom.Document;

public class SOAPClientSAAJ {
	
	static String sPagoEnCajaSIT = "http://10.75.197.161:8080/services/ArServices";
	static String sPagoEnCajaUAT = "http://10.75.39.146:8080/services/ArServices";		
	static String sPagoSimuladoSIT = "http://mdwtpbust2.telecom.com.ar:8701/notificarPago";
	static String sPagoSimuladoUAT = "http://mdwtpbusu2.telecom.com.ar:8701/notificarPago";
	static String sQueryCustomerInfoUAT = "http://10.75.39.146:8080/services/BcServices";
	static String sQueryCustomerInfoSIT = "http://10.75.197.163:8080/services/BcServices";
	static String sQueryFreeUnitSIT = "http://10.75.197.163:8080/services/BbServices";
	static String sQueryFreeUnitUAT = "http://10.75.39.146:8080/services/BbServices";
	static String sObtenerInformacionOrdenUAT = "https://mdwtpbusu2.telecom.com.ar:8702/obtenerInformacionOrden";
	static String sObtenerInformacionOrdenSIT = "https://mdwtpbust2.telecom.com.ar:8702/obtenerInformacionOrden";
	static String sNotificarResultadoOrdenUAT = "https://mdwtpbusu2.telecom.com.ar:8702/notificarResultadoOrden";
	static String sNotificarResultadoOrdenSIT = "https://mdwtpbust2.telecom.com.ar:8702/notificarResultadoOrden";
	static String sRealizarAltaSuscripUAT = "http://mdwtpbusu2.telecom.com.ar:8701/realizarAltaSuscripInfotaiment?WSDL";
	static String sRealizarAltaSuscripSIT = "http://mdwtpbust2.telecom.com.ar:8701/realizarAltaSuscripInfotaiment?WSDL";
	static String sVerificarSaldoEnFacturacionSIT = "http://10.75.197.163:8080/services/ArServices";
	static String sVerificarSaldoEnFacturacionUAT = "http://10.75.39.146:8080/services/ArServices";
	
	public Document callSoapWebService(String soapMessageString, String sEndPoint) {
		Document doc = null;
		switch (sEndPoint.toLowerCase()) {
    		case "pago simulado":
	    		sEndPoint = sPagoSimuladoSIT;
	    		break;
	    	case "pago en caja":
	    		if (TestBase.urlAmbiente.contains("sit02"))
	    			sEndPoint = sPagoEnCajaSIT;
	    		else
	    			sEndPoint = sPagoEnCajaUAT;
	    		break;
	    	case "datos usuario":
	    		if (TestBase.urlAmbiente.contains("sit02"))
	    			sEndPoint = sQueryCustomerInfoSIT;
	    		else
	    			sEndPoint = sQueryCustomerInfoUAT;
	    		break;
	    	case "unidades libres":
	    		if (TestBase.urlAmbiente.contains("sit02"))
	    			sEndPoint = sQueryFreeUnitSIT;
	    		else
	    			sEndPoint = sQueryFreeUnitUAT;
	    		break;
	    	case "notificar resultado orden":
	    		if (TestBase.urlAmbiente.contains("sit02"))
	    			sEndPoint = sNotificarResultadoOrdenSIT;
	    		else
	    			sEndPoint = sNotificarResultadoOrdenUAT;
	    		break;
	    	case "obtener informacion orden":
	    		if (TestBase.urlAmbiente.contains("sit02"))
	    			sEndPoint = sObtenerInformacionOrdenSIT;
	    		else
	    			sEndPoint = sObtenerInformacionOrdenUAT;
	    		break;
	    	case "alta suscripcion":
	    		if (TestBase.urlAmbiente.contains("sit02".toUpperCase()))
	    			sEndPoint = sRealizarAltaSuscripSIT;
	    		else
	    			sEndPoint = sRealizarAltaSuscripUAT;
	    		break;
	    	case "notificar pago":
	    		if (TestBase.urlAmbiente.contains("sit02".toUpperCase()))
	    			sEndPoint = sPagoSimuladoSIT;
	    		else
	    			sEndPoint = sPagoSimuladoUAT;
	    		break;
	    	case "verificar saldo":
	    		if (TestBase.urlAmbiente.contains("sit02".toUpperCase()))
	    			sEndPoint = sVerificarSaldoEnFacturacionSIT;
	    		else
	    			sEndPoint = sVerificarSaldoEnFacturacionUAT;
	    		break;
    	}
		System.out.println("endo point : " +sEndPoint);
    	try {
        	// Create SOAP Connection
        	SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();           
            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(createSRequest(soapMessageString), sEndPoint);            
            soapConnection.close();
            doc = soapResponse.getSOAPBody().extractContentAsDocument();
            return doc;
        } catch(Exception e) {
        	System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
            return doc;
        }
	}
    
	private static SOAPMessage createSRequest(String msg) {
		SOAPMessage request = null;
        try {
        	MessageFactory msgFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
            request = msgFactory.createMessage();            
            SOAPPart msgPart = request.getSOAPPart();
            SOAPEnvelope envelope = msgPart.getEnvelope();
            SOAPBody body = envelope.getBody();           
            javax.xml.transform.stream.StreamSource _msg = new javax.xml.transform.stream.StreamSource(new java.io.StringReader(msg));
            msgPart.setContent(_msg);            
            request.saveChanges();            
            /* Print the request message, just for debugging purposes */
            //System.out.println("Request SOAP Message:");
            // request.writeTo(System.out);
            // System.out.println("\n");
        } catch(Exception ex) {
        	ex.printStackTrace();
        }
        return request;
    }
	private static final TrustManager[] UNQUESTIONING_TRUST_MANAGER = new TrustManager[]{ 
      new X509TrustManager() { 
	       public java.security.cert.X509Certificate[] getAcceptedIssuers(){ 
	    	   return null; 
	       } 
	       public void checkClientTrusted(X509Certificate[] certs, String authType){} 
	       public void checkServerTrusted(X509Certificate[] certs, String authType){} 
      } 
    };
	
	public static void turnOffSslChecking() throws NoSuchAlgorithmException, KeyManagementException { 
	     // Install the all-trusting trust manager 
	     final SSLContext sc = SSLContext.getInstance("SSL"); 
	     sc.init(null, UNQUESTIONING_TRUST_MANAGER, null); 
	     HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory()); 
   } 
}