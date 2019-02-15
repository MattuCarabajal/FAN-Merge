package Tests;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.testng.Assert;
import org.w3c.dom.Document;

public class MDW {

SOAPClientSAAJ SOA = new SOAPClientSAAJ();
	
	//Armado de request S105 para 1 serial
	public String s105Request(String sCodigo,String sSerial,String sDeposito) {
		String sRequest = "";
		sRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:v1=\"http://www.personal.com.ar/Common/RequestMessageHeader/v1.0\" xmlns:v11=\"http://www.personal.com.ar/ESB/MovimientoStock/v1.0\" xmlns:v12=\"http://www.personal.com.ar/Common/Entities/Recurso/Recurso/v1.0\" xmlns:v13=\"http://www.personal.com.ar/Common/Entities/Producto/Logistica/v1.0\">\r\n"
				+ "\r\n<soapenv:Header>\r\n"
				+ "\r\n    <v1:requestHeader>\r\n"
				+ "\r\n        <v1:consumer code=\"WEB\" channel=\"WEB\" additionalData=\"WEB\">\r\n"
				+ "\r\n            <v1:userID>x001437</v1:userID>\r\n"
				+ "\r\n            <v1:credentials>\r\n"
				+ "\r\n                <v1:userPassword>Mp8up1v5xH</v1:userPassword>\r\n"
				+ "\r\n            </v1:credentials>"
				+ "\r\n        </v1:consumer>\r\n"
				+ "\r\n        <v1:message messageId=\"\" consumerMessageId=\"\">\r\n"
				+ "\r\n        </v1:message>\r\n"
				+ "\r\n    </v1:requestHeader>\r\n"
				+ "\r\n</soapenv:Header>\r\n"
				+ "\r\n<soapenv:Body>\r\n"
				+ "\r\n    <v11:MovimientoStockRequest>\r\n"
				+ "\r\n        <v11:ListaOperacion>\r\n"
				+ "\r\n            <v11:Item>\r\n"
				+ "\r\n                <v11:CodOperacion>" + sCodigo + "</v11:CodOperacion>\r\n"
				+ "\r\n                <v12:numeroSerie>" + sSerial + "</v12:numeroSerie>\r\n"
				+ "\r\n                <v13:codDeposito>" + sDeposito + "</v13:codDeposito>\r\n"
				+ "\r\n            </v11:Item>\r\n"
				+ "\r\n        </v11:ListaOperacion>\r\n"
				+ "\r\n    </v11:MovimientoStockRequest>\r\n"
				+ "\r\n</soapenv:Body>\r\n"
				+ "\r\n</soapenv:Envelope>\r\n";
		return sRequest;
	}
	
	//Generacion de request
	public static SOAPMessage createSRequest(String msg) {
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
	
	// Consumo de un servicio
	public Document callSoapWebService(String soapMessageString, String sEndPoint) {
		Document doc = null;
		String sEndPointUAT = "http://mdwtpbusu1.telecom.com.ar:8701/";
		switch (sEndPoint.toLowerCase()) {
    		case "uat105":
	    		sEndPoint = sEndPointUAT + "movimientoStock";
	    		break;
    	}
    	
    	try {
        	// Create SOAP Connection
        	SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            
            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(createSRequest(soapMessageString), sEndPoint);
            //wait(120000);
            soapResponse.writeTo(System.out);
            soapConnection.close();
            
             doc = soapResponse.getSOAPBody().extractContentAsDocument();
             System.out.println(doc.toString());
            return doc;
        } catch (Exception e) {
        	System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
            return doc;
        }
	}
	
	//
	public boolean requestValidadorS105(Document sResponse, String serial) {
		if (sResponse.getElementsByTagName("ns2:numeroSerie").item(0).getTextContent().contains(serial)) {
			if(sResponse.getElementsByTagName("ns1:MensajeResultado").item(0).getTextContent().contains("Proceso NORMAL")) {
			return true;
			}
			else {
				System.out.println(sResponse.getElementsByTagName("ns1:MensajeResultado").item(0).getTextContent());
				return false;
			}
		}
		return false;
	}
	
}
