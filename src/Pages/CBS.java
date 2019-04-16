package Pages;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.soap.Node;

import org.testng.Assert;

public class CBS {
	public String sRequestByOrder(String sOrder) {
		sOrder = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:v1=\"http://www.personal.com.ar/Common/RequestMessageHeader/v1.0\" xmlns:v11=\"http://www.personal.com.ar/ESB/NotificarPago/v1.0\">\r\n"
				+ "\r\n   <soapenv:Header>\r\n"
				+ "\r\n      <v1:requestHeader>\r\n"
				+ "\r\n         <v1:consumer code=\"IVR\" channel=\"IVR\" additionalData=\"\">\r\n"
				+ "\r\n            <v1:userID>jack</v1:userID>\r\n"
				+ "\r\n            <v1:credentials>\r\n"
				+ "\r\n               <!--You have a CHOICE of the next 2 items at this level-->\r\n"
				+ "\r\n               <!--v1:userCertificate>?</v1:userCertificate-->\r\n"
				+ "\r\n               <v1:userPassword>jack</v1:userPassword>\r\n"
				+ "\r\n            </v1:credentials>\r\n"
				+ "\r\n         </v1:consumer>\r\n"
				+ "\r\n         <v1:message messageId=\"\" consumerMessageId=\"\">\r\n"
				+ "\r\n            <!--Optional:-->\r\n"
				+ "\r\n            <!--v1:timestamp>?</v1:timestamp-->\r\n"
				+ "\r\n         </v1:message>\r\n"
				+ "\r\n      </v1:requestHeader>\r\n"
				+ "\r\n   </soapenv:Header>\r\n"
				+ "\r\n   <soapenv:Body>\r\n"
				+ "\r\n      <v11:NotificarPagoRequest>\r\n"
				+ "\r\n         <v11:salesOrderId>" + sOrder;
		
		sOrder+= "</v11:salesOrderId>\r\n"
				+ "\r\n         <v11:status>payment succeed</v11:status>\r\n"
				+ "\r\n         <v11:statusInvoice>invoice succeed</v11:statusInvoice>\r\n"
				+ "\r\n      </v11:NotificarPagoRequest>\r\n"
				+ "\r\n   </soapenv:Body>\r\n"
				+ "\r\n</soapenv:Envelope>";
		return sOrder;
	}
	
	public boolean sCBS_Request_ServicioWeb_Validador(Document sResponse) {
		boolean sAssert = false;
		if (sResponse.getElementsByTagName("cbs:ResultDesc").item(0).getTextContent().equalsIgnoreCase("0OK")||sResponse.getElementsByTagName("cbs:ResultDesc").item(0).getTextContent().equalsIgnoreCase("Operation successfully."))   {
			System.out.println("Correcto");
			sAssert = true;
		}
		else {
			System.out.println(sResponse.getElementsByTagName("cbs:ResultDesc").item(0).getTextContent());
			Assert.assertTrue(false);
		}
		return sAssert;
	}
	
	public String sRequest(String sPaymentSerialNo, String sPaymentChannelID, String sAccountKey, String sPaymentMethod, String sAmount, String sInvoiceno) {
		String sRequest = "";
		sRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ars=\"http://www.huawei.com/bme/cbsinterface/arservices\" xmlns:cbs=\"http://www.huawei.com/bme/cbsinterface/cbscommon\" xmlns:arc=\"http://cbs.huawei.com/ar/wsservice/arcommon\">\r\n"
				+ "    <soapenv:Header/>\r\n"
				+ "\r\n    <soapenv:Body>\r\n"
				+ "\r\n        <ars:PaymentRequestMsg>\r\n"
				+ "\r\n            <RequestHeader>\r\n"
				+ "      	       		<cbs:Version>5.5</cbs:Version>\r\n"
				+ "      	       		<cbs:BusinessCode>Charge2AR</cbs:BusinessCode>\r\n"
				+ "       	       		<cbs:MessageSeq>"+sPaymentSerialNo;
		
		sRequest+="</cbs:MessageSeq>\r\n"
				+ "       	       		<cbs:OwnershipInfo>\r\n"
				+ "       	         		<cbs:BEID>10101</cbs:BEID>\r\n"
				+ "       	         		<cbs:BRID>101</cbs:BRID>\r\n"
				+ "         	   		</cbs:OwnershipInfo>\r\n"
				+ "             		<cbs:AccessSecurity>\r\n"
				+ "     	           		<cbs:LoginSystemCode>101</cbs:LoginSystemCode>\r\n"
				+ "         	       		<cbs:Password>yVEy3349bxN6lvViA8yK6Cd1JsRRcKO5QMmml3e7qp0=</cbs:Password>\r\n"
				+ "             	   		<cbs:RemoteIP>10.75.197.142</cbs:RemoteIP>\r\n"
				+ "       	      		</cbs:AccessSecurity>\r\n"
				+ "        	       		<cbs:OperatorInfo>\r\n"
				+ "        	        		<cbs:OperatorID>101</cbs:OperatorID>\r\n"
				+ "        	        		<cbs:ChannelID>1</cbs:ChannelID>\r\n"
				+ "        	       		</cbs:OperatorInfo>\r\n"
				+ "        	        	<cbs:AccessMode>A</cbs:AccessMode>\r\n"
				+ "        	        	<cbs:MsgLanguageCode>2002</cbs:MsgLanguageCode>\r\n"
				+ "        	        	<cbs:TimeFormat>\r\n"
				+ "        	        		<cbs:TimeType>1</cbs:TimeType>\r\n"
				+ "        	        		<cbs:TimeZoneID>8</cbs:TimeZoneID>\r\n"
				+ "        	       		</cbs:TimeFormat>\r\n"
				+ "        	        	<cbs:AdditionalProperty>\r\n"
				+ "        	        		<cbs:Code>108</cbs:Code>\r\n"
				+ "        	        		<cbs:Value>109</cbs:Value>\r\n"
				+ "        	        	</cbs:AdditionalProperty>\r\n"
				+ "     	      </RequestHeader>\r\n"
				+ "            <PaymentRequest>\r\n"
				+ "                <ars:PaymentSerialNo>" + sPaymentSerialNo;
		
		sRequest+= "</ars:PaymentSerialNo>\r\n"
				+ "                <ars:PaymentChannelID>" + sPaymentChannelID;
		
		sRequest+= "</ars:PaymentChannelID>\r\n"
				+ "                <ars:OpType>1</ars:OpType>\r\n"
				+ "                <ars:PaymentObj>\r\n"
				+ "                    <ars:AcctAccessCode>\r\n"
				+ "                        <arc:AccountKey>" + sAccountKey;
		
		sRequest+= "</arc:AccountKey>\r\n"
				+ "                    </ars:AcctAccessCode>\r\n"
				+ "                </ars:PaymentObj>\r\n"
				+ "                <ars:PaymentInfo>\r\n"
				+ "                    <ars:CashPayment>\r\n"
				+ "                        <ars:PaymentMethod>" + sPaymentMethod;
		
		sRequest+= "</ars:PaymentMethod>\r\n"
				+ "                        <ars:Amount>" + sAmount;
		
		sRequest+= "</ars:Amount>\r\n"
				+ "                        <ars:ApplyList>\r\n"
				+ "                            <ars:Invoiceno>" + sInvoiceno;
		
		sRequest+= "</ars:Invoiceno>                    \r\n"
				+ "                        </ars:ApplyList>\r\n"
				+ "                    </ars:CashPayment>\r\n"
				+ "                </ars:PaymentInfo>\r\n"
				+ "            </PaymentRequest>\r\n"
				+ "        </ars:PaymentRequestMsg>\r\n"
				+ "    </soapenv:Body>\r\n"
				+ "</soapenv:Envelope>";
		
		return sRequest;
	}
	
	public boolean sCBS_Request_Validador(String sResponse) {
		boolean sAssert = false;
		
		if (sResponse.contains("Operation successfully")) {
			sAssert = true;
		}
		
		return sAssert;
	}
	
	public String sRequestByLinea(String sLinea, String sMessageSeq) {
		String sRequest = "";
		sRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:bcs=\"http://www.huawei.com/bme/cbsinterface/bcservices\" xmlns:cbs=\"http://www.huawei.com/bme/cbsinterface/cbscommon\" xmlns:bcc=\"http://www.huawei.com/bme/cbsinterface/bccommon\">\r\n"
				+ "\r\n   <soapenv:Header/>\r\n"
				+ "\r\n   <soapenv:Body>\r\n"
				+ "\r\n      <bcs:QueryCustomerInfoRequestMsg>\r\n"
				+ "\r\n                    <RequestHeader>\r\n"
				+ "\r\n                                               <cbs:Version>5.5</cbs:Version>\r\n"
				+ "\r\n                                               <cbs:BusinessCode>QueryCustomerInfo</cbs:BusinessCode>\r\n"
				+ "\r\n                                               <cbs:MessageSeq>" + sMessageSeq;
		
		sRequest += "</cbs:MessageSeq>\r\n"
				+ "\r\n                                               <cbs:OwnershipInfo>\r\n"
				+ "\r\n                                                               <cbs:BEID>10101</cbs:BEID>\r\n"
				+ "\r\n                                                               <cbs:BRID>101</cbs:BRID>\r\n"
				+ "\r\n                                               </cbs:OwnershipInfo>\r\n"
				+ "\r\n                				<cbs:AccessSecurity>\r\n"
				+ "\r\n                                                               <cbs:LoginSystemCode>101</cbs:LoginSystemCode>\r\n"
				+ "\r\n                                                               <cbs:Password>yVEy3349bxN6lvViA8yK6Cd1JsRRcKO5QMmml3e7qp0=</cbs:Password>\r\n"
				+ "\r\n                                                               <cbs:RemoteIP>10.138.22.65</cbs:RemoteIP>\r\n"
				+ "\r\n                                               </cbs:AccessSecurity>\r\n"
				+ "\r\n                                               <cbs:OperatorInfo>\r\n"
				+ "\r\n                                                               <cbs:OperatorID>101</cbs:OperatorID>\r\n"
				+ "\r\n                                                               <cbs:ChannelID>1</cbs:ChannelID>\r\n"
				+ "\r\n                                               </cbs:OperatorInfo>\r\n"
				+ "\r\n                                               <cbs:TimeFormat>\r\n"
				+ "\r\n                                                               <cbs:TimeType>1</cbs:TimeType>\r\n"
				+ "\r\n                                                               <cbs:TimeZoneID>8</cbs:TimeZoneID>\r\n"
				+ "\r\n                                               </cbs:TimeFormat>\r\n"
				+ "\r\n                                               <cbs:AdditionalProperty>\r\n"
				+ "\r\n                                                               <cbs:Code>108</cbs:Code>\r\n"
				+ "\r\n                                                               <cbs:Value>109</cbs:Value>\r\n"
				+ "\r\n                                               </cbs:AdditionalProperty>\r\n"
				+ "\r\n                             </RequestHeader> \r\n"
				+ "\r\n      		<QueryCustomerInfoRequest>\r\n"
				+ "\r\n            	  		<bcs:QueryObj>\r\n"
				+ "\r\n               			<bcs:SubAccessCode>\r\n"
				+ "\r\n                  			<bcc:PrimaryIdentity>" + sLinea;
		
		sRequest+= "</bcc:PrimaryIdentity>\r\n"
				+ "\r\n               			</bcs:SubAccessCode>\r\n"
				+ "\r\n            			</bcs:QueryObj>\r\n"
				+ "\r\n         	</QueryCustomerInfoRequest>      \r\n"
				+ "\r\n      </bcs:QueryCustomerInfoRequestMsg>\r\n"
				+ "\r\n   </soapenv:Body>\r\n"
				+ "\r\n</soapenv:Envelope>";
		return sRequest;
	}
	
	public boolean sCBS_Request_Validador_Alta_Linea(Document sResponse, String sLinea, String sImsi, String sICCD, String sNombre, String sApellido, String sPlan) {
		System.out.println("nombre: "+sResponse.getElementsByTagName("bcc:FirstName").item(0).getTextContent());
		boolean resultado = false;
		if (sResponse.getElementsByTagName("bcc:FirstName").item(0).getTextContent().contains(sNombre) && sResponse.getElementsByTagName("bcc:LastName").item(0).getTextContent().contains(sApellido)) {
			if(sResponse.getElementsByTagName("bcc:OfferingName").item(0).getTextContent().contains(sPlan)) {
				System.out.println("Correcto");
				resultado = true;
			}
			else {
				System.out.println(sResponse.getElementsByTagName("bcc:FirstName").item(0).getTextContent());
				Assert.assertTrue(false);
			}
		}
		return resultado;
	}
	
	public String sRequestByTC(String sMessageSeq, String sPaymentChannelID, String sAccountKey, String sPaymentMethod, String sAmount, String sAccountNumber, String sAccountName, String sExpirationDate, String sCVV, String sInvoiceno, String sCardHolderName, String sCardHolderNumber) {
		String sRequest = "";
		sRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ars=\"http://www.huawei.com/bme/cbsinterface/arservices\" xmlns:cbs=\"http://www.huawei.com/bme/cbsinterface/cbscommon\" xmlns:arc=\"http://cbs.huawei.com/ar/wsservice/arcommon\">\r\n"
				+ "<soapenv:Header/>\r\n"
				+ "<soapenv:Body>\r\n"
				+ "<ars:PaymentRequestMsg>\r\n"
				+ "<RequestHeader>\r\n"
				+ "<cbs:Version>5.5</cbs:Version>\r\n"
				+ "<cbs:BusinessCode>Charge2AR</cbs:BusinessCode>\r\n"
				+ "<cbs:MessageSeq>" + sMessageSeq;
		
		sRequest+= "</cbs:MessageSeq>\r\n"
				+ "<cbs:OwnershipInfo>\r\n"
				+ "<cbs:BEID>10101</cbs:BEID>\r\n"
				+ "<cbs:BRID>101</cbs:BRID>\r\n"
				+ "</cbs:OwnershipInfo>\r\n"
				+ "<cbs:AccessSecurity>\r\n"
				+ "<cbs:LoginSystemCode>117</cbs:LoginSystemCode>\r\n"
				+ "<cbs:Password>jW6lRxU4leO5Xev+SISea/Ie7Dp5wDPgfGR9MNVDJRo=</cbs:Password>\r\n"
				+ "<cbs:RemoteIP>10.75.197.142</cbs:RemoteIP>\r\n"
				+ "</cbs:AccessSecurity>\r\n"
				+ "<cbs:OperatorInfo>\r\n"
				+ "<cbs:OperatorID>101</cbs:OperatorID>\r\n"
				+ "<cbs:ChannelID>1</cbs:ChannelID>\r\n"
				+ "</cbs:OperatorInfo>\r\n"
				+ "<cbs:AccessMode>A</cbs:AccessMode>\r\n"
				+ "<cbs:MsgLanguageCode>2002</cbs:MsgLanguageCode>\r\n"
				+ "<cbs:TimeFormat>\r\n"
				+ "<cbs:TimeType>1</cbs:TimeType>\r\n"
				+ "<cbs:TimeZoneID>8</cbs:TimeZoneID>\r\n"
				+ "</cbs:TimeFormat>\r\n"
				+ "<cbs:AdditionalProperty>\r\n"
				+ "<cbs:Code>108</cbs:Code>\r\n"
				+ "<cbs:Value>109</cbs:Value>\r\n"
				+ "</cbs:AdditionalProperty>\r\n"
				+ "</RequestHeader>\r\n"
				+ "<PaymentRequest>\r\n"
				+ "<ars:PaymentSerialNo>${=(new java.text.SimpleDateFormat(\"yyyyMMddHHmmss\")).format(new Date())}${=(int)(Math.random()*1000)}</ars:PaymentSerialNo>\r\n"
				+ "<ars:PaymentChannelID>" + sPaymentChannelID;
		
		sRequest+= "</ars:PaymentChannelID>\r\n"
				+ "<ars:OpType>1</ars:OpType>\r\n"
				+ "<ars:PaymentObj>\r\n"
				+ "<ars:AcctAccessCode>\r\n"
				+ "<arc:AccountKey>" + sAccountKey;
		
		sRequest+= "</arc:AccountKey>\r\n"
				+ "</ars:AcctAccessCode>\r\n"
				+ "</ars:PaymentObj>\r\n"
				+ "<ars:PaymentInfo>\r\n"
				+ "<ars:CashPayment>\r\n"
				+ "<ars:PaymentMethod>" + sPaymentMethod;
		
		sRequest+= "</ars:PaymentMethod>\r\n"
				+ "<ars:Amount>" + sAmount;
		
		sRequest+= "</ars:Amount>\r\n"
				+ "<ars:BankInfo>\r\n"
				+ "<arc:BankCode>11</arc:BankCode>\r\n"
				+ "<arc:AcctType>C</arc:AcctType>\r\n"
				+ "<arc:AcctNo>" + sAccountNumber;
		
		sRequest+= "</arc:AcctNo>\r\n"
				+ "<arc:CreditCardType>403</arc:CreditCardType>\r\n"
				+ "<arc:AcctName>" + sAccountName;
		
		sRequest+= "</arc:AcctName>\r\n"
				+ "<arc:ExpDate>" + sExpirationDate;
		
		sRequest+= "</arc:ExpDate>\r\n"
				+ "<arc:CVVNumber>" + sCVV;
		
		sRequest+= "</arc:CVVNumber>\r\n"
				+ "<arc:NumberOfInstallment>1</arc:NumberOfInstallment>\r\n"
				+ "</ars:BankInfo>\r\n"
				+ "<!--Zero or more repetitions:-->\r\n"
				+ "<ars:ApplyList>\r\n"
				+ "<ars:Invoiceno>" + sInvoiceno;
				
		sRequest+= "</ars:Invoiceno>\r\n"
				+ "</ars:ApplyList>\r\n"
				+ "<ars:PaymentPlan>0</ars:PaymentPlan>\r\n"
				+ "</ars:CashPayment>\r\n"
				+ "</ars:PaymentInfo>\r\n"
				+ "<ars:PointOfSaleID>782</ars:PointOfSaleID>\r\n"
				+ "<ars:PaymentOperationType>SalesInvoice</ars:PaymentOperationType>\r\n"
				+ "<ars:CurrencyID>1006</ars:CurrencyID>\r\n"
				+ "<ars:WondersoftInfo>\r\n"
				+ "<ars:OriginIP>10.70.26.101</ars:OriginIP>\r\n"
				+ "<ars:CardHolderName>" + sCardHolderName;
		
		sRequest+= "</ars:CardHolderName>\r\n"
				+ "<ars:CardHolderDocumentType>96</ars:CardHolderDocumentType>\r\n"
				+ "<ars:CardHolderDocumentNumber>" + sCardHolderNumber;
		
		sRequest+= "</ars:CardHolderDocumentNumber>\r\n"
				+ "<ars:BankPromotionCode>0</ars:BankPromotionCode>\r\n"
				+ "</ars:WondersoftInfo>\r\n"
				+ "</PaymentRequest>\r\n"
				+ "</ars:PaymentRequestMsg>\r\n"
				+ "</soapenv:Body>\r\n"
				+ "</soapenv:Envelope>";
		
		return sRequest;
	}
	
	public Document sCBS_TC_Request_Validador(Document sResponse) {
		
		if (sResponse.getElementsByTagName("cbs:ResultDesc").item(0).getTextContent().contains("Operation successfully")) {
			System.out.println("Correcto");
		}
		else {
			System.out.println(sResponse.getElementsByTagName("cbs:ResultDesc").item(0).getTextContent());
			Assert.assertTrue(false);
		}
		return sResponse;
	}
	
	public String sRequestQueryLiteBySubscriber(String sLinea, String sMessageSeq) {
		String sRequest = "";
		sRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:bcs=\"http://www.huawei.com/bme/cbsinterface/bcservices\" xmlns:cbs=\"http://www.huawei.com/bme/cbsinterface/cbscommon\" xmlns:bcc=\"http://www.huawei.com/bme/cbsinterface/bccommon\">\r\n"
				+ "\r\n   <soapenv:Header/>\r\n"
				+ "\r\n   <soapenv:Body>\r\n"
				+ "\r\n      <bcs:QueryLiteBySubscriberRequestMsg>\r\n"
				+ "\r\n                    <RequestHeader>\r\n"
				+ "\r\n                                               <cbs:Version>5.5</cbs:Version>\r\n"
				+ "\r\n                                               <cbs:BusinessCode>QueryLiteBy</cbs:BusinessCode>\r\n"
				+ "\r\n                                               <cbs:MessageSeq>" + sMessageSeq;
		
		sRequest += "</cbs:MessageSeq>\r\n"
				+ "\r\n                                               <cbs:OwnershipInfo>\r\n"
				+ "\r\n                                                               <cbs:BEID>10101</cbs:BEID>\r\n"
				+ "\r\n                                                               <cbs:BRID>101</cbs:BRID>\r\n"
				+ "\r\n                                               </cbs:OwnershipInfo>\r\n"
				+ "\r\n                				<cbs:AccessSecurity>\r\n"
				+ "\r\n                                                               <cbs:LoginSystemCode>101</cbs:LoginSystemCode>\r\n"
				+ "\r\n                                                               <cbs:Password>yVEy3349bxN6lvViA8yK6Cd1JsRRcKO5QMmml3e7qp0=</cbs:Password>\r\n"
				+ "\r\n                                                               <cbs:RemoteIP>10.138.22.65</cbs:RemoteIP>\r\n"
				+ "\r\n                                               </cbs:AccessSecurity>\r\n"
				+ "\r\n                                               <cbs:OperatorInfo>\r\n"
				+ "\r\n                                                               <cbs:OperatorID>101</cbs:OperatorID>\r\n"
				+ "\r\n                                                               <cbs:ChannelID>1</cbs:ChannelID>\r\n"
				+ "\r\n                                               </cbs:OperatorInfo>\r\n"
				+ "\r\n                                               <cbs:TimeFormat>\r\n"
				+ "\r\n                                                               <cbs:TimeType>1</cbs:TimeType>\r\n"
				+ "\r\n                                                               <cbs:TimeZoneID>8</cbs:TimeZoneID>\r\n"
				+ "\r\n                                               </cbs:TimeFormat>\r\n"
				+ "\r\n                                               <cbs:AdditionalProperty>\r\n"
				+ "\r\n                                                               <cbs:Code>108</cbs:Code>\r\n"
				+ "\r\n                                                               <cbs:Value>109</cbs:Value>\r\n"
				+ "\r\n                                               </cbs:AdditionalProperty>\r\n"
				+ "\r\n                             </RequestHeader> \r\n"
				+ "\r\n      		<QueryLiteBySubscriberRequest>\r\n"
				+ "\r\n               			<bcs:SubAccessCode>\r\n"
				+ "\r\n                  			<bcc:PrimaryIdentity>" + sLinea;
		
		sRequest+= "</bcc:PrimaryIdentity>\r\n"
				+ "\r\n               			</bcs:SubAccessCode>\r\n"
				+ "\r\n         	</QueryLiteBySubscriberRequest>      \r\n"
				+ "\r\n      </bcs:QueryLiteBySubscriberRequestMsg>\r\n"
				+ "\r\n   </soapenv:Body>\r\n"
				+ "\r\n</soapenv:Envelope>";
		return sRequest;
	}
	
	public Document sValidacion_ResponseQueryLiteBySubscriber(Document sResponse) {
		
		if (sResponse.getElementsByTagName("cbs:ResultDesc").item(0).getTextContent().contains("Operation successfully")) {
			System.out.println("Correcto");
		}
		else {
			System.out.println(sResponse.getElementsByTagName("cbs:ResultDesc").item(0).getTextContent());
			//Assert.assertTrue(false);
		}
		return sResponse;
	}
	
	public Document sValidacion_ResponseObtenerInformacionOrden(Document sResponse) {
		if (sResponse.getElementsByTagName("ns2:idCliente1").getLength()>0) {
			System.out.println("Correcto");
		}
		else {
			Assert.assertTrue(false);
		}
		return sResponse;
	}
	public Document sValidacion_ResponseNotificarResultadoOrden(Document sResponse) {
		
		if (sResponse.getElementsByTagName("ns2:NotificarResultadoOrdenResponse").getLength()>0) {
			System.out.println("Correcto");
		}
		else {
			Assert.assertTrue(false);
		}
		return sResponse;
	}
	
	public String ObtenerValorResponse(Document Response, String Campo) {
		
		return Response.getElementsByTagName(Campo).item(0).getTextContent();
	}
	
	public boolean validarNumeroAmigos(Document Response, String tipo, String numero) {
		boolean esta = false;
		NodeList ofertas = (NodeList) Response.getElementsByTagName("bcc:OfferingCode");
		if (tipo.equalsIgnoreCase("voz")) {
			for (int i=0; i<ofertas.getLength();i++) {
				if(ofertas.item(i).getTextContent().equals("SO_FYF029")) {
					ofertas = (NodeList) Response.getElementsByTagName("bcc:Value");
					for (int j=0; j<ofertas.getLength();j++) {
						if(ofertas.item(i).getTextContent().equals("54"+numero)) {
							esta = true;
							break;
						}
					}
					break;
						
				}
					
			}
		}else {
			for (int i=0; i<ofertas.getLength();i++) {
				if(ofertas.item(i).getTextContent().equals("SO_FYF032")) {
					ofertas = (NodeList) Response.getElementsByTagName("bcc:Value");
					for (int j=0; j<ofertas.getLength();j++) {
						if(ofertas.item(i).getTextContent().equals("54"+numero)) {
							esta = true;
							break;
						}
					}
					break;
				}
			}
		}
		return esta;
	}
	
	public boolean validarRenovacionDatos(Document Response, String tipo) {
		boolean esta = false;
		NodeList ofertas = (NodeList) Response.getElementsByTagName("bcc:OfferingCode");
		if (tipo.equalsIgnoreCase("internet 50 mb dia")) {
			for (int i=0; i<ofertas.getLength();i++) {
				if(ofertas.item(i).getTextContent().equals("SO_DATA_50MBXDIA"))
					esta = true;
			}
		}else {
			if (tipo.equalsIgnoreCase("reseteo 200 mb por dia")) {
				for (int i=0; i<ofertas.getLength();i++) {
					if(ofertas.item(i).getTextContent().equals("SO_DATA_200MB_Diario"))
						esta = true;
				}
			}else {
				if (tipo.toLowerCase().contains("reseteo internet por dia")) {
					for (int i=0; i<ofertas.getLength();i++) {
						if(ofertas.item(i).getTextContent().equals("SO_ROI_P_PYBOL_R"))
							esta = true;
					}
				}
			}
		}
		return esta;
	}
	
	public String sRequestQueryFreeUnit(String sLinea, String sMessageSeq) {
		String sRequest = "";
		sRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:bbs=\"http://www.huawei.com/bme/cbsinterface/bbservices\" xmlns:cbs=\"http://www.huawei.com/bme/cbsinterface/cbscommon\" xmlns:bbc=\"http://www.huawei.com/bme/cbsinterface/bbcommon\">\r\n"
				+ "\r\n   <soapenv:Header/>\r\n"
				+ "\r\n   <soapenv:Body>\r\n"
				+ "\r\n      <bbs:QueryFreeUnitRequestMsg>\r\n"
				+ "\r\n                    <RequestHeader>\r\n"
				+ "\r\n                                               <cbs:Version>5.5</cbs:Version>\r\n"
				+ "\r\n                                               <cbs:BusinessCode>QueryFreeUnit</cbs:BusinessCode>\r\n"
				+ "\r\n                                               <cbs:MessageSeq>" + sMessageSeq;
		
		sRequest += "</cbs:MessageSeq>\r\n"
				+ "\r\n                                               <cbs:OwnershipInfo>\r\n"
				+ "\r\n                                                               <cbs:BEID>10101</cbs:BEID>\r\n"
				+ "\r\n                                                               <cbs:BRID>101</cbs:BRID>\r\n"
				+ "\r\n                                               </cbs:OwnershipInfo>\r\n"
				+ "\r\n                				<cbs:AccessSecurity>\r\n"
				+ "\r\n                                                               <cbs:LoginSystemCode>101</cbs:LoginSystemCode>\r\n"
				+ "\r\n                                                               <cbs:Password>yVEy3349bxN6lvViA8yK6Cd1JsRRcKO5QMmml3e7qp0=</cbs:Password>\r\n"
				+ "\r\n                                                               <cbs:RemoteIP>10.75.197.142</cbs:RemoteIP>\r\n"
				+ "\r\n                                               </cbs:AccessSecurity>\r\n"
				+ "\r\n                                               <cbs:OperatorInfo>\r\n"
				+ "\r\n                                                               <cbs:OperatorID>101</cbs:OperatorID>\r\n"
				+ "\r\n                                                               <cbs:ChannelID>1</cbs:ChannelID>\r\n"
				+ "\r\n                                               </cbs:OperatorInfo>\r\n"
				+ "\r\n                                               <cbs:TimeFormat>\r\n"
				+ "\r\n                                                               <cbs:TimeType>1</cbs:TimeType>\r\n"
				+ "\r\n                                                               <cbs:TimeZoneID>8</cbs:TimeZoneID>\r\n"
				+ "\r\n                                               </cbs:TimeFormat>\r\n"
				+ "\r\n                                               <cbs:AdditionalProperty>\r\n"
				+ "\r\n                                                               <cbs:Code>108</cbs:Code>\r\n"
				+ "\r\n                                                               <cbs:Value>109</cbs:Value>\r\n"
				+ "\r\n                                               </cbs:AdditionalProperty>\r\n"
				+ "\r\n                             </RequestHeader>\r\n"
				+ "\r\n      		<QueryFreeUnitRequest>\r\n"
				+ "\r\n      			<bbs:QueryObj>\r\n"
				+ "\r\n               			<bbs:SubAccessCode>\r\n"
				+ "\r\n                  			<bbc:PrimaryIdentity>" + sLinea;
		
		sRequest+= "</bbc:PrimaryIdentity>\r\n"
				+ "\r\n               			</bbs:SubAccessCode>\r\n"
				+ "\r\n         		</bbs:QueryObj>\r\n"
				+ "\r\n         	</QueryFreeUnitRequest>\r\n"
				+ "\r\n      </bbs:QueryFreeUnitRequestMsg>\r\n"
				+ "\r\n   </soapenv:Body>\r\n"
				+ "\r\n</soapenv:Envelope>";
		return sRequest;
	}
	
	public boolean validarActivacionPack(Document Response, String tipo) {
		boolean esta = false;
		NodeList ofertas = (NodeList) Response.getElementsByTagName("bbs:OfferingCode");
		switch(tipo.toLowerCase()) {
			case "pack 50 min y 50 sms x 7 dias":
				for (int i=0; i<ofertas.getLength();i++) {
					if(ofertas.item(i).getTextContent().equals("SO_VOICE_50MIN_50SMS_7D"))
						esta = true;
				}
			break;
			case  "pack internacional 30 sms al resto del mundo":
				for (int i=0; i<ofertas.getLength();i++) {
					if(ofertas.item(i).getTextContent().equals("SO_LDI_30MIN_REST_MUNDO"))
						esta = true;
				}
			break;
			case "pack 1gb x 1 dia + whatsapp gratis":
				for (int i=0; i<ofertas.getLength();i++) {
					if(ofertas.item(i).getTextContent().equals("SO_DATA_1GX1D"))
						esta = true;
				}
			break;
			case "pack 1 dia de sms y minutos a personal ilimitados":
				System.out.println("estoy en el case");
				for (int i=0; i<ofertas.getLength();i++) {
					System.out.println("Oferta: "+ofertas.item(i).getTextContent());
					if(ofertas.item(i).getTextContent().equals("SO_VOICE_SMS_ILIMITADO_1D"))
						esta = true;
				}
			break;
			case "pack 100mb uruguay":
				for (int i=0; i<ofertas.getLength();i++) {
					if(ofertas.item(i).getTextContent().equals("SO_ROI_100MB_URU"))
						esta = true;
				}
			break;
		}
		
		return esta;
	}
	
	public String ObtenerUnidadLibre(Document Response, String unidad) {
		NodeList ofertas = (NodeList) Response.getElementsByTagName("bbs:FreeUnitTypeName");
		for (int i=0; i<ofertas.getLength();i++) {
			if(ofertas.item(i).getTextContent().toLowerCase().contains(unidad.toLowerCase()))
				return Response.getElementsByTagName("bbs:TotalInitialAmount").item(i).getTextContent();
		}
		return "0";
	}
	
	public String sRequestObtenerInformacionOrden(String sOrden, String sFecha) {
		String sRequest = "";
		sRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:v1=\"http://www.personal.com.ar/Common/RequestMessageHeader/v1.0\" xmlns:v11=\"http://www.personal.com.ar/ESB/ObtenerInformacionOrden/v1.0\" xmlns:v12=\"http://www.personal.com.ar/Common/Entities/NegocioComun/Orden/v1.0\" xmlns:v2=\"http://www.personal.com.ar/Common/Entities/Cliente/MedioDePago/v2.0\" xmlns:v3=\"http://www.personal.com.ar/Common/Entities/Cliente/Pago/v3.0\">\r\n"
				+ "\r\n   <soapenv:Header>\r\n"
				+ "\r\n   <v1:requestHeader>\r\n"
				+ "\r\n       <v1:consumer code=\"IVR\" channel=\"IVR\" additionalData=\"?\">\r\n"
				+ "\r\n             <v1:userID>x001412</v1:userID>\r\n"
				+ "\r\n                   <v1:credentials>\r\n"
				+ "\r\n                       <v1:userPassword>NS_jh7#8ds</v1:userPassword>\r\n"
				+ "\r\n                   </v1:credentials>\r\n"
				+ "\r\n      </v1:consumer>\r\n"
				+ "\r\n      <v1:message messageId=\"\" consumerMessageId=\"\"/>\r\n"
				+ "\r\n   </v1:requestHeader>\r\n"
				+ "\r\n   </soapenv:Header>\r\n"
				+ "\r\n   <soapenv:Body>\r\n"
				+ "\r\n       <v11:ObtenerInformacionOrdenRequest>\r\n"
				+ "\r\n       <v12:codOrden>"+sOrden;
		sRequest+="</v12:codOrden>\r\n"
				+ "\r\n       <v11:tipoOperacion>COMPRA</v11:tipoOperacion>\r\n"
				//+ "\r\n       <!-- <v2:nroCuponTarjeta>00003093</v2:nroCuponTarjeta>\r\n"
				//+ "\r\n       <v3:fechaPago>2018-07-19 00:00:00</v3:fechaPago>-->\r\n"
				+ "\r\n         <v11:usuario>367</v11:usuario>\r\n"
				+ "\r\n       </v11:ObtenerInformacionOrdenRequest>\r\n"
				+ "\r\n   </soapenv:Body>\r\n"
				+ "\r\n</soapenv:Envelope>";
		return sRequest;
	}
	
	public String obtenerValorCodPago(Document dResponse) {
		String CodPago = null;
		CodPago = ObtenerValorResponse(dResponse, "ns2:idCliente1");
		CodPago = CodPago.substring(43,75);
		CodPago= CodPago.replace(" ", "");
		return CodPago;
	}
	
	public String sRequestNotificarResultadoOrden(String sOrden, String sFecha, String sHora, String sCodPag) {
		String sRequest = "";
		sRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:v1=\"http://www.personal.com.ar/Common/RequestMessageHeader/v1.0\" xmlns:v11=\"http://www.personal.com.ar/ESB/NotificarResultadoOrden/v1.0\" xmlns:v12=\"http://www.personal.com.ar/Common/Entities/NegocioComun/Orden/v1.0\" xmlns:v3=\"http://www.personal.com.ar/Common/Entities/Cliente/Pago/v3.0\" xmlns:v2=\"http://www.personal.com.ar/Common/Entities/Cliente/MedioDePago/v2.0\">\r\n"
				+ "\r\n   <soapenv:Header>\r\n"
				+ "\r\n   <v1:requestHeader>\r\n"
				+ "\r\n       <v1:consumer code=\"IVR\" channel=\"IVR\" additionalData=\"?\">\r\n"
				+ "\r\n             <v1:userID>x001412</v1:userID>\r\n"
				+ "\r\n                   <v1:credentials>\r\n"
				+ "\r\n                       <v1:userPassword>NS_jh7#8ds</v1:userPassword>\r\n"
				+ "\r\n                   </v1:credentials>\r\n"
				+ "\r\n      </v1:consumer>\r\n"
				+ "\r\n      <v1:message messageId=\"\" consumerMessageId=\"\"/>\r\n"
				+ "\r\n   </v1:requestHeader>\r\n"
				+ "\r\n   </soapenv:Header>\r\n"
				+ "\r\n   <soapenv:Body>\r\n"
				+ "\r\n       <v11:NotificarResultadoOrdenRequest>\r\n"
				+ "\r\n       <v12:codOrden>"+sOrden;
		sRequest+="</v12:codOrden>\r\n"
				+ "\r\n       <v11:equipo>001</v11:equipo>\r\n"
				+ "\r\n       <v11:tipoOperacion>COMPRA</v11:tipoOperacion>\r\n"
				+ "\r\n        <v3:codPago>"+sCodPag;
		sRequest+="</v3:codPago>\r\n"
				+ "\r\n        <v11:numeroTarjetaEnmascarado>539909******1010</v11:numeroTarjetaEnmascarado>\r\n"
				+ "\r\n        <v2:nroCuponTarjeta>00002870</v2:nroCuponTarjeta>\r\n"
				//+ "\r\n         <!--<v11:numeroAutorizacion>?</v11:numeroAutorizacion>-->\r\n"
				+ "\r\n       <v3:fechaPago>"+sFecha;
		sRequest+="</v3:fechaPago>\r\n"
				+ "\r\n       <v11:horaCompra>"+sHora;
		sRequest+="</v11:horaCompra>\r\n"
				+ "\r\n        <v2:nroComercio>00000413</v2:nroComercio>\r\n"
				+ "\r\n         <v11:flagPinpadOffline>0</v11:flagPinpadOffline>\r\n"
				//+ "\r\n         <!--<v11:resultadoOperacion>TEC0001999</v11:resultadoOperacion>-->\r\n"
				+ "\r\n         <v11:resultadoOperacion>OK</v11:resultadoOperacion>\r\n"
				//+ "\r\n          <!--<v11:descripcionOperacion>?</v11:descripcionOperacion>-->\r\n"
				+ "\r\n       </v11:NotificarResultadoOrdenRequest>\r\n"
				+ "\r\n   </soapenv:Body>\r\n"
				+ "\r\n</soapenv:Envelope>";
		return sRequest;
	}
	public String sacarStatusLinea(Document dResponse) {
		String StatusIndex = ObtenerValorResponse(dResponse, "bcs:CurrentStatusIndex");
		String StatusDetail = ObtenerValorResponse(dResponse, "bcs:StatusDetail");
		switch(StatusIndex) {
			case "1":
				return ("creada");
			case "2":
				if(StatusDetail.equals("000000000000000000000000"))
					return ("activa");
			break;
			case "3":
				return ("expirada");
			case "4":
				if(StatusDetail.equals("200000000000000000000000"))
					return ("suspendida voluntaria");
				else
					if(StatusDetail.equals("020000000000000000000000"))
						return ("suspendida siniestro");
					else
						if(StatusDetail.equals("000002000000000000000000"))
							return ("suspendida fraude");
			break;
			case "8":
				return ("predesactiva");
			case "9":
				return ("desactiva");
		}
		return "error";
	}
	
	public String ObtenerDatosNominacion(Document sResponse) {
		String datos;
		datos =" "+sResponse.getElementsByTagName("bcc:IDNumber").item(0).getTextContent()+" ";//
		datos += sResponse.getElementsByTagName("bcc:FirstName").item(0).getTextContent();
		return datos;
	}
	
	public String sRequestRecharge(String sLinea, String sMessageSeq, String sMonto) {
		String sRequest = "";
		sRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ars=\"http://www.huawei.com/bme/cbsinterface/arservices\" xmlns:cbs=\"http://www.huawei.com/bme/cbsinterface/cbscommon\" xmlns:arc=\"http://cbs.huawei.com/ar/wsservice/arcommon\">\r\n"
				+ "\r\n   <soapenv:Header/>\r\n"
				+ "\r\n   <soapenv:Body>\r\n"
				+ "\r\n      <ars:RechargeRequestMsg>\r\n"
				+ "\r\n                    <RequestHeader>\r\n"
				+ "\r\n                                               <cbs:Version>5.5</cbs:Version>\r\n"
				//+ "\r\n                                               <cbs:BusinessCode></cbs:BusinessCode>\r\n"
				+ "\r\n                                               <cbs:MessageSeq>" + sMessageSeq;
		
		sRequest += "</cbs:MessageSeq>\r\n"
				+ "\r\n                                               <cbs:OwnershipInfo>\r\n"
				+ "\r\n                                                               <cbs:BEID>10101</cbs:BEID>\r\n"
				+ "\r\n                                                               <cbs:BRID>101</cbs:BRID>\r\n"
				+ "\r\n                                               </cbs:OwnershipInfo>\r\n"
				+ "\r\n                				<cbs:AccessSecurity>\r\n"
				+ "\r\n                                                               <cbs:LoginSystemCode>101</cbs:LoginSystemCode>\r\n"
				+ "\r\n                                                               <cbs:Password>yVEy3349bxN6lvViA8yK6Cd1JsRRcKO5QMmml3e7qp0=</cbs:Password>\r\n"
				+ "\r\n                                                               <cbs:RemoteIP>10.75.193.200</cbs:RemoteIP>\r\n"
				+ "\r\n                                               </cbs:AccessSecurity>\r\n"
				+ "\r\n                                               <cbs:OperatorInfo>\r\n"
				+ "\r\n                                                               <cbs:OperatorID>101</cbs:OperatorID>\r\n"
				+ "\r\n                                                               <cbs:ChannelID>1</cbs:ChannelID>\r\n"
				+ "\r\n                                               </cbs:OperatorInfo>\r\n"
				+ "\r\n                                               <cbs:TimeFormat>\r\n"
				+ "\r\n                                                               <cbs:TimeType>1</cbs:TimeType>\r\n"
				+ "\r\n                                                               <cbs:TimeZoneID>8</cbs:TimeZoneID>\r\n"
				+ "\r\n                                               </cbs:TimeFormat>\r\n"
				+ "\r\n                                               <cbs:AdditionalProperty>\r\n"
				+ "\r\n                                                               <cbs:Code>108</cbs:Code>\r\n"
				+ "\r\n                                                               <cbs:Value>109</cbs:Value>\r\n"
				+ "\r\n                                               </cbs:AdditionalProperty>\r\n"
				+ "\r\n                             </RequestHeader> \r\n"
				+ "\r\n      		<RechargeRequest>\r\n"
				//+ "\r\n            	  		<!--Optional:-->\r\n"
				//+ "\r\n            	  		<!--ars:RechargeSerialNo>11120000001600</ars:RechargeSerialNo-->\r\n"
				//+ "\r\n            	  		<!--Optional:-->\r\n"
				+ "\r\n            	  		<ars:RechargeType>41</ars:RechargeType>\r\n"
				//+ "\r\n            	  		<!--Optional:-->\r\n"
				+ "\r\n            	  		<ars:RechargeChannelID>C</ars:RechargeChannelID>\r\n"
				+ "\r\n            	  		<ars:RechargeObj>\r\n"
				+ "\r\n               			<ars:SubAccessCode>\r\n"
				+ "\r\n                  			<arc:PrimaryIdentity>" + sLinea; 
		
		sRequest+= "</arc:PrimaryIdentity>\r\n"
				+ "\r\n               			</ars:SubAccessCode>\r\n"
				+ "\r\n            			</ars:RechargeObj>\r\n"
				+ "\r\n            	  		<ars:RechargeInfo>\r\n"
				+ "\r\n               			<ars:CashPayment>\r\n"
				//+ "\r\n            	  		<!--Optional:-->\r\n"
				+ "\r\n               			<ars:PaymentMethod>1001</ars:PaymentMethod>\r\n"
				+ "\r\n                  			<ars:Amount>" + sMonto; 
				
				sRequest+= "</ars:Amount>\r\n"
				+ "\r\n               			</ars:CashPayment>\r\n"
				+ "\r\n            			</ars:RechargeInfo>\r\n"
				+ "\r\n            			<ars:CurrencyID>1006</ars:CurrencyID>\r\n"
				//+ "\r\n            			<!--Zero or more repetitions:-->\r\n"
//				+ "\r\n            			<!--ars:AdditionalProperty>\r\n"
//				+ "\r\n            			<arc:Code></arc:Code>\r\n"
//				+ "\r\n            			<arc:Value></arc:Value>\r\n"
//				+ "\r\n            			</ars:AdditionalProperty-->\r\n"
				+ "\r\n         	</RechargeRequest>      \r\n"
				+ "\r\n      </ars:RechargeRequestMsg>\r\n"
				+ "\r\n   </soapenv:Body>\r\n"
				+ "\r\n</soapenv:Envelope>";
		return sRequest;
	}
	public boolean sValidacion_ResponseRecharge(Document sResponse) {
		if (ObtenerValorResponse(sResponse,"cbs:ResultDesc").equalsIgnoreCase("operaci\u00f3n exitosa") ) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String sRequestLoan(String sLinea, String sMessageSeq, String sMonto) {
		String sRequest = "";
		sRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ars=\"http://www.huawei.com/bme/cbsinterface/arservices\" xmlns:cbs=\"http://www.huawei.com/bme/cbsinterface/cbscommon\" xmlns:arc=\"http://cbs.huawei.com/ar/wsservice/arcommon\">\r\n"
				+ "\r\n   <soapenv:Header/>\r\n"
				+ "\r\n   <soapenv:Body>\r\n"
				+ "\r\n      <ars:LoanRequestMsg>\r\n"
				+ "\r\n                    <RequestHeader>\r\n"
				+ "\r\n                                               <cbs:Version>5.5</cbs:Version>\r\n"
				+ "\r\n                                               <cbs:BusinessCode/>\r\n"
				+ "\r\n                                               <cbs:MessageSeq>" + sMessageSeq;
		
		sRequest += "</cbs:MessageSeq>\r\n"
				+ "\r\n                                               <cbs:OwnershipInfo>\r\n"
				+ "\r\n                                                               <cbs:BEID>10101</cbs:BEID>\r\n"
				+ "\r\n                                                               <cbs:BRID>101</cbs:BRID>\r\n"
				+ "\r\n                                               </cbs:OwnershipInfo>\r\n"
				+ "\r\n                				<cbs:AccessSecurity>\r\n"
				+ "\r\n                                                               <cbs:LoginSystemCode>101</cbs:LoginSystemCode>\r\n"
				+ "\r\n                                                               <cbs:Password>yVEy3349bxN6lvViA8yK6Cd1JsRRcKO5QMmml3e7qp0=</cbs:Password>\r\n"
				+ "\r\n                                                               <cbs:RemoteIP>10.75.193.200</cbs:RemoteIP>\r\n"
				+ "\r\n                                               </cbs:AccessSecurity>\r\n"
				+ "\r\n                                               <cbs:OperatorInfo>\r\n"
				+ "\r\n                                                               <cbs:OperatorID>101</cbs:OperatorID>\r\n"
				+ "\r\n                                                               <cbs:ChannelID>1</cbs:ChannelID>\r\n"
				+ "\r\n                                               </cbs:OperatorInfo>\r\n"
				+ "\r\n                                               <cbs:TimeFormat>\r\n"
				+ "\r\n                                                               <cbs:TimeType>1</cbs:TimeType>\r\n"
				+ "\r\n                                                               <cbs:TimeZoneID>8</cbs:TimeZoneID>\r\n"
				+ "\r\n                                               </cbs:TimeFormat>\r\n"
				+ "\r\n                                               <cbs:AdditionalProperty>\r\n"
				+ "\r\n                                                               <cbs:Code>108</cbs:Code>\r\n"
				+ "\r\n                                                               <cbs:Value>109</cbs:Value>\r\n"
				+ "\r\n                                               </cbs:AdditionalProperty>\r\n"
				+ "\r\n                             </RequestHeader> \r\n"
				+ "\r\n      		<LoanRequest>\r\n"           	  		
				+ "\r\n               			<ars:SubAccessCode>\r\n"
				+ "\r\n                  			<arc:PrimaryIdentity>" + sLinea; 
		
		sRequest+= "</arc:PrimaryIdentity>\r\n"
				//+ "\r\n            	  		<!--arc:SubscriberKey></arc:SubscriberKey-->\r\n"
				+ "\r\n               			</ars:SubAccessCode>\r\n"
				//+ "\r\n            			<!--You have a CHOICE of the next 2 items at this level-->\r\n"
				//+ "\r\n            	  		<!--ars:LoanGrade></ars:LoanGrade-->\r\n"
				+ "\r\n                  		<ars:LoanAmount>" + sMonto; 
				
				sRequest+= "</ars:LoanAmount>\r\n"
//				+ "\r\n               		<!--ars:Remark></ars:Remark>\r\n"
//				+ "\r\n            			<ars:AdditionalProperty>\r\n"
//				+ "\r\n            			<arc:Code></arc:Code>\r\n"
//				+ "\r\n            			<arc:Value></arc:Value>\r\n"
//				+ "\r\n            			</ars:AdditionalProperty-->\r\n"
				+ "\r\n         	</LoanRequest>      \r\n"
				+ "\r\n      </ars:LoanRequestMsg>\r\n"
				+ "\r\n   </soapenv:Body>\r\n"
				+ "\r\n</soapenv:Envelope>";
		return sRequest;
	}
	
	public String sRequestRealizarAltaSuscripcion(String sLinea, String sCodigo) {
		String sRequest = "";
		sRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:v1=\"http://www.personal.com.ar/Common/RequestMessageHeader/v1.0\" xmlns:v11=\"http://www.personal.com.ar/ESB/RealizarAltaSuscripInfotaiment/v1.0\" xmlns:v12=\"http://www.personal.com.ar/Common/Entities/Producto/Linea/v1.0\" xmlns:v2=\"http://www.personal.com.ar/Common/Entities/Cliente/Suscripcion/v2.0\" xmlns:v21=\"http://www.personal.com.ar/Common/Entities/NegocioComun/InteraccionDelNegocio/v2.0\" xmlns:v13=\"http://www.personal.com.ar/Common/Entities/Recurso/Recurso/v1.0\" xmlns:v14=\"http://www.personal.com.ar/Common/Entities/Ventas/CanalDeAtencion/v1.0\" xmlns:v22=\"http://www.personal.com.ar/Common/Entities/Servicio/Servicio/v2.0\" xmlns:v15=\"http://www.personal.com.ar/Common/Entities/NegocioComun/EstructuraComun/v1.0\">\r\n"
				+ "\r\n   <soapenv:Header>\r\n"
				+ "\r\n   <v1:requestHeader>\r\n"
				+ "\r\n       <v1:consumer code=\"IVR\" channel=\"IVR\" additionalData=\"\">\r\n"
				+ "\r\n             <v1:userID>x001437</v1:userID>\r\n"
				+ "\r\n                   <v1:credentials>\r\n"
//				+ "\r\n                       <!--You have a CHOICE of the next 2 items at this level-->\r\n"
//				+ "\r\n                   	  <!--<v1:userCertificate>x001437</v1:userCertificate>-->\r\n"
				+ "\r\n                       <v1:userPassword>Mp8up1v5xH</v1:userPassword>\r\n"
				+ "\r\n                   </v1:credentials>\r\n"
				+ "\r\n      </v1:consumer>\r\n"
				+ "\r\n      <v1:message messageId=\"\" consumerMessageId=\"\">\r\n"
//				+ "\r\n      	<!--Optional:-->\r\n"
//				+ "\r\n      	<!--<v1:timestamp>?</v1:timestamp>-->\r\n"
				+ "\r\n      </v1:message>\r\n"
				+ "\r\n   </v1:requestHeader>\r\n"
				+ "\r\n   </soapenv:Header>\r\n"
				+ "\r\n   <soapenv:Body>\r\n"
				+ "\r\n       <v11:RealizarAltaSuscripInfotaimentRequest>\r\n"
				+ "\r\n       <v12:nroLinea>"+sLinea;
		sRequest+="</v12:nroLinea>\r\n"
				+ "\r\n        <v2:codSuscripcion>"+sCodigo;
		sRequest+="</v2:codSuscripcion>\r\n"
				+ "\r\n        <v21:codInteraccionNegocio>SIEBEL</v21:codInteraccionNegocio>\r\n"
				+ "\r\n        <v13:codInterfazComunicacion>WS</v13:codInterfazComunicacion>\r\n"
				+ "\r\n        <v14:codCanal>VENTAS</v14:codCanal>\r\n"
				+ "\r\n        <v22:palabraClaveSVA>SIEBEL</v22:palabraClaveSVA>\r\n"
				//+ "\r\n        <!--Zero or more repetitions:-->\r\n"
				+ "\r\n        <v11:FiltrosExtra>\r\n"
				+ "\r\n          <v15:nombreParametro/>\r\n"
				+ "\r\n       	 <v15:valorParametro/>\r\n"
				+ "\r\n        </v11:FiltrosExtra>\r\n"
				+ "\r\n     </v11:RealizarAltaSuscripInfotaimentRequest>\r\n"
				+ "\r\n   </soapenv:Body>\r\n"
				+ "\r\n</soapenv:Envelope>";
		return sRequest;
	}
	
	public boolean sValidacion_RealizarAltaSuscripcion(Document sResponse) {
		if (ObtenerValorResponse(sResponse,"v2.:codInteraccionNegocio").matches("\\w[0-9]+") ) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String sNotificarPago(String sOrder) {
		sOrder = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:v1=\"http://www.personal.com.ar/Common/RequestMessageHeader/v1.0\" xmlns:v11=\"http://www.personal.com.ar/ESB/NotificarPago/v1.0\">\r\n"
				+ "\r\n   <soapenv:Header>\r\n"
				+ "\r\n      <v1:requestHeader>\r\n"
				+ "\r\n         <v1:consumer code=\"\" channel=\"\" additionalData=\"\">\r\n"
				+ "\r\n            <v1:userID>x001437</v1:userID>\r\n"
				+ "\r\n            <v1:credentials>\r\n"
				+ "\r\n               <!--You have a CHOICE of the next 2 items at this level-->\r\n"
				+ "\r\n               <!--v1:userCertificate>?</v1:userCertificate-->\r\n"
				+ "\r\n               <v1:userPassword>Mp8up1v5xH</v1:userPassword>\r\n"
				+ "\r\n            </v1:credentials>\r\n"
				+ "\r\n         </v1:consumer>\r\n"
				+ "\r\n         <v1:message messageId=\"\" consumerMessageId=\"\">\r\n"
				+ "\r\n         </v1:message>\r\n"
				+ "\r\n      </v1:requestHeader>\r\n"
				+ "\r\n   </soapenv:Header>\r\n"
				+ "\r\n   <soapenv:Body>\r\n"
				+ "\r\n      <v11:NotificarPagoRequest>\r\n"
				+ "\r\n         <v11:salesOrderId>" + sOrder;
		
		sOrder+= "</v11:salesOrderId>\r\n"
				+ "\r\n         <v11:status>Payment succeed</v11:status>\r\n"
				+ "\r\n      </v11:NotificarPagoRequest>\r\n"
				+ "\r\n   </soapenv:Body>\r\n"
				+ "\r\n</soapenv:Envelope>";
		return sOrder;
	}
	
	public String sNotificarEmisionFactura(String sOrder) {
		sOrder = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:v1=\"http://www.personal.com.ar/Common/RequestMessageHeader/v1.0\" xmlns:v11=\"http://www.personal.com.ar/ESB/NotificarPago/v1.0\">\r\n"
				+ "\r\n   <soapenv:Header>\r\n"
				+ "\r\n      <v1:requestHeader>\r\n"
				+ "\r\n         <v1:consumer code=\"\" channel=\"\" additionalData=\"\">\r\n"
				+ "\r\n            <v1:userID>x001437</v1:userID>\r\n"
				+ "\r\n            <v1:credentials>\r\n"
				+ "\r\n               <!--You have a CHOICE of the next 2 items at this level-->\r\n"
				+ "\r\n               <!--v1:userCertificate>?</v1:userCertificate-->\r\n"
				+ "\r\n               <v1:userPassword>Mp8up1v5xH</v1:userPassword>\r\n"
				+ "\r\n            </v1:credentials>\r\n"
				+ "\r\n         </v1:consumer>\r\n"
				+ "\r\n         <v1:message messageId=\"\" consumerMessageId=\"\">\r\n"
				+ "\r\n         </v1:message>\r\n"
				+ "\r\n      </v1:requestHeader>\r\n"
				+ "\r\n   </soapenv:Header>\r\n"
				+ "\r\n   <soapenv:Body>\r\n"
				+ "\r\n      <v11:NotificarPagoRequest>\r\n"
				+ "\r\n         <v11:salesOrderId>" + sOrder;
		
		sOrder+= "</v11:salesOrderId>\r\n"
				+ "\r\n         <v11:status>Payment succeed</v11:status>\r\n"
				//+ "\r\n         <!--Optional:-->"
				+ "\r\n         <v11:statusInvoice>Invoice succeed</v11:statusInvoice>\r\n"
				//+ "\r\n         <!--Optional:-->"
				+ "\r\n         <v11:totalInvoiceAmount>0</v11:totalInvoiceAmount>\r\n"
				//+ "\r\n         <!--Optional:-->"
				+ "\r\n         <v11:legalInvoiceNumber>B9311-00000073</v11:legalInvoiceNumber>\r\n"
				+ "\r\n      </v11:NotificarPagoRequest>\r\n"
				+ "\r\n   </soapenv:Body>\r\n"
				+ "\r\n</soapenv:Envelope>";
		return sOrder;
	}
	
	public String sRequestVerificarSaldoEnFacturacion(String sAccountKey) {
		sAccountKey = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ars=\"http://www.huawei.com/bme/cbsinterface/arservices\" xmlns:cbs=\"http://www.huawei.com/bme/cbsinterface/cbscommon\" xmlns:arc=\"http://cbs.huawei.com/ar/wsservice/arcommon\">\r\n" + 
				"\r\n" + 
				"   <soapenv:Header/>\r\n" + 
				"\r\n" + 
				"   <soapenv:Body>\r\n" + 
				"\r\n" + 
				"      <ars:QueryBalanceRequestMsg>\r\n" + 
				"\r\n" + 
				"         <RequestHeader>\r\n" + 
				"\r\n" + 
				"            <cbs:Version>5.5</cbs:Version>\r\n" + 
				"\r\n" + 
				"            <cbs:BusinessCode></cbs:BusinessCode>\r\n" + 
				"\r\n" + 
				"            <cbs:MessageSeq>63633363</cbs:MessageSeq>\r\n" + 
				"\r\n" + 
				"            <cbs:OwnershipInfo>\r\n" + 
				"\r\n" + 
				"               <cbs:BEID>10101</cbs:BEID>\r\n" + 
				"\r\n" + 
				"               <cbs:BRID>101</cbs:BRID>\r\n" + 
				"\r\n" + 
				"            </cbs:OwnershipInfo>\r\n" + 
				"\r\n" + 
				"            <cbs:AccessSecurity>\r\n" + 
				"\r\n" + 
				"                                                                <cbs:LoginSystemCode>117</cbs:LoginSystemCode>\r\n" + 
				"\r\n" + 
				"               <cbs:Password>BGGEPm7q7l19gc+Z48pWz//0r31b0HV5LynosEn0kMs=</cbs:Password>\r\n" + 
				"\r\n" + 
				"               <cbs:RemoteIP>10.75.197.142</cbs:RemoteIP>\r\n" + 
				"\r\n" + 
				"            </cbs:AccessSecurity>\r\n" + 
				"\r\n" + 
				"            <cbs:OperatorInfo>\r\n" + 
				"\r\n" + 
				"               <cbs:OperatorID>101</cbs:OperatorID>\r\n" + 
				"\r\n" + 
				"               <cbs:ChannelID>1</cbs:ChannelID>\r\n" + 
				"\r\n" + 
				"            </cbs:OperatorInfo>\r\n" + 
				"\r\n" + 
				"            <cbs:AccessMode>12</cbs:AccessMode>\r\n" + 
				"\r\n" + 
				"            <cbs:MsgLanguageCode>2048</cbs:MsgLanguageCode>\r\n" + 
				"\r\n" + 
				"            <cbs:TimeFormat>\r\n" + 
				"\r\n" + 
				"               <cbs:TimeType>1</cbs:TimeType>\r\n" + 
				"\r\n" + 
				"               <cbs:TimeZoneID>8</cbs:TimeZoneID>\r\n" + 
				"\r\n" + 
				"            </cbs:TimeFormat>\r\n" + 
				"\r\n" + 
				"            <cbs:AdditionalProperty>\r\n" + 
				"\r\n" + 
				"               <cbs:Code>108</cbs:Code>\r\n" + 
				"\r\n" + 
				"               <cbs:Value>109</cbs:Value>\r\n" + 
				"\r\n" + 
				"            </cbs:AdditionalProperty>\r\n" + 
				"\r\n" + 
				"         </RequestHeader>\r\n" + 
				"\r\n" + 
				"         <QueryBalanceRequest>\r\n" + 
				"\r\n" + 
				"            <ars:QueryObj>\r\n" + 
				"\r\n" + 
				"               <ars:AcctAccessCode>\r\n" + 
				"\r\n" + 
				"                  <arc:AccountKey>" + sAccountKey;
		sAccountKey += "</arc:AccountKey>\r\n" + 
				"\r\n" + 
				"               </ars:AcctAccessCode>\r\n" + 
				"\r\n" + 
				"            </ars:QueryObj>\r\n" + 
				"\r\n" + 
				"         </QueryBalanceRequest>\r\n" + 
				"\r\n" + 
				"      </ars:QueryBalanceRequestMsg>\r\n" + 
				"\r\n" + 
				"   </soapenv:Body>\r\n" + 
				"\r\n" + 
				"</soapenv:Envelope>";
		return sAccountKey;
	}
	
public Document sValidacion_VerificarSaldo(Document sResponse) {
		return sResponse;
	}
}