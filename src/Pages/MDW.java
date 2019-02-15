package Pages;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.soap.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class MDW {
	
	String usuariouat = "x001437";
	String passworduat = "Mp8up1v5xH";
	String endpointuat = "http://mdwtpbusu1.telecom.com.ar:8701/movimientoStock";
	String headeruat = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:v1=\"http://www.personal.com.ar/Common/RequestMessageHeader/v1.0\" xmlns:v11=\"http://www.personal.com.ar/ESB/MovimientoStock/v1.0\" xmlns:v12=\"http://www.personal.com.ar/Common/Entities/Recurso/Recurso/v1.0\" xmlns:v13=\"http://www.personal.com.ar/Common/Entities/Producto/Logistica/v1.0\">" 
						+ "<soapenv:Header>"
						+ "<v1:requestHeader>"
						+ "<v1:consumer code=\"WEB\" channel=\"WEB\" additionalData=\"WEB\">"
						+ "<v1:userID>" + usuariouat + "</v1:userID>"
						+ "<v1:credentials>"
						+ "<v1:userPassword>" + passworduat + "</v1:userPassword>"
						+ "</v1:credentials>"
						+ "</v1:consumer>"
						+ "<v1:message messageId=\"\" consumerMessageId=\"\">"
						+ "</v1:message>"
						+ "</v1:requestHeader>"
						+ "</soapenv:Header>"
						+ "<soapenv:Body>";
	String enderuat = "</soapenv:Body>"
						+ "</soapenv:Envelope>";
	
	String endpointtst = "";
	
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
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        return request;
    }
	
	
	public Document S095 (String ambiente, String seriales) {
		String body = "";
		String request = "";
		String endpoint = "";
		String header = "";
		String ender = "";
		Document response = null;
		if (ambiente == "uat") {
			endpoint = endpointuat;
			header = headeruat;
			ender = enderuat;
		}
		body = "<v11:ObtenerInformacionInventarioRequest>"
				+ "<v11:ListaNumeroSerie>";
		String[] serialesvec = seriales.split(",");
		for (String x : serialesvec) {
			body = body + "<v12:numeroSerie>" + x + "</v12:numeroSerie>";
		}
		body = body + "</v11:ListaNumeroSerie>" + "</v11:ObtenerInformacionInventarioRequest>";
		request = header + body + ender;
		
    	try {
        	// Create SOAP Connection
        	SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            
            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(createSRequest(request), endpoint);
            
            soapConnection.close();
             response = soapResponse.getSOAPBody().extractContentAsDocument();
            return response;
        } catch (Exception e) {
        	System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
            return response;
        }
	}
	
}
