package Tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import Pages.remoteScriptExec;

public class Reportes {
	
	remoteScriptExec rsePage = new remoteScriptExec();
	String sDateFormat = "dd/MM/yyyy HH:mm:ss";
	String sDateFormatDMY = "dd/MM/yyyy";
	String sDateFormatYMD = "yyyy/MM/dd";
	
	SimpleDateFormat sdfDateFormat;
	List<String> sListOfFiles = null;
	
	//Before & AfterClass
	
	@BeforeClass(alwaysRun=true)
	public void download() throws MalformedURLException, UnknownHostException, FileNotFoundException, IOException, JSchException, SftpException {
		rsePage.FTPConnection();
		System.out.println("Connection stablished.");
		rsePage.FTPDownload();
		System.out.println("Download completed.");
	}
	
	@AfterClass(alwaysRun=true)
	public void quit() throws IOException {
		rsePage.deleteAllFiles();
	}
	
	//Tests
	
	//Test #1
	@Test
	public void TS125398_CRM_Interfaz_LCRM_Individuo() throws IOException, ParseException {
		String sName = "_INDIVIDUO_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIDIndividuo = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDIndividuo, 18));
				Assert.assertFalse(sIDIndividuo.isEmpty());
				
				String sNumeroTelefono = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroTelefono, 40));
				
				String sCodUsuarioAlta = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioAlta, 18));
				
				String sDepartamentoEmpresa = lsAux.get(3);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sDepartamentoEmpresa, 80));
				
				String sMarcaNoLlamar = lsAux.get(4);
				Integer.parseInt(sMarcaNoLlamar);
				Assert.assertTrue(sMarcaNoLlamar.equals("1") || sMarcaNoLlamar.equals("0"));
				
				String sEmail = lsAux.get(5);
				if (!sEmail.isEmpty()) sEmail.contains("@");
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEmail, 80));
				
				String sMarcaEnviarMail = lsAux.get(6);
				Integer.parseInt(sMarcaEnviarMail);
				Assert.assertTrue(sMarcaEnviarMail.equals("1") || sMarcaEnviarMail.equals("0"));
				
				String sMarcaEnviarFax = lsAux.get(7);
				Integer.parseInt(sMarcaEnviarFax);
				Assert.assertTrue(sMarcaEnviarFax.equals("1") || sMarcaEnviarFax.equals("0"));
				
				String sNumeroTelefonoCasa = lsAux.get(8);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroTelefonoCasa, 40));
				
				String sCodUsuarioMod = lsAux.get(9);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 18));
				
				String sFechaUltInteraccion = lsAux.get(10);
				if (!sFechaUltInteraccion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaUltInteraccion);
				}
				
				String sNumeroMovil = lsAux.get(11);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroMovil, 40));
				
				String sNombre = lsAux.get(12);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNombre, 40));
				
				String sApellido = lsAux.get(13);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sApellido, 80));
				
				String sTituloCortesia = lsAux.get(14);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTituloCortesia, 128));
				
				String sFechaAltaContacto = lsAux.get(15);
				if (!sFechaAltaContacto.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaAltaContacto);
				}
				
				String sEdad = lsAux.get(16);
				Integer.parseInt(sEdad);
				
				String sIdContacto = lsAux.get(17);
				if (!sIdContacto.isEmpty()) {
					Integer.parseInt(sIdContacto);
				}
				
				String sCUIL = lsAux.get(18);
				if (!sCUIL.isEmpty()) {
					rsePage.verifyCUITCUIT(sCUIL, sCUIL);
				}
				
				String sTipoDocumento = lsAux.get(19);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoDocumento, 255));
				
				String sNumeroDocumento = lsAux.get(20);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroDocumento, 30));
				
				String sLicenciaDeConducir = lsAux.get(21);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sLicenciaDeConducir, 50));
				
				String sCodEmpleado = lsAux.get(22);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodEmpleado, 255));
				
				String sCodPersonaContacto = lsAux.get(23);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodPersonaContacto, 18));
				
				String sMarcaFraude = lsAux.get(24);
				Integer.parseInt(sMarcaFraude);
				Assert.assertTrue(sMarcaFraude.equals("1") || sMarcaFraude.equals("0"));
				
				String sGenero = lsAux.get(25);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sGenero, 255));
				
				String sEstado = lsAux.get(26);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEstado, 255));
				
				String sFechaNacimiento = lsAux.get(27);
				if (!sFechaNacimiento.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormatDMY);
					sdfDateFormat.parse(sFechaNacimiento);
				}
				
				String sOcupacion = lsAux.get(28);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sOcupacion, 50));
				
				String sEstadoLaboral = lsAux.get(29);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEstadoLaboral, 255));
				
				String sNivelSatisfaccion = lsAux.get(30);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNivelSatisfaccion, 255));
				
				String sFechaCreaAudit = lsAux.get(31);
				if (!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);
				}
				
				String sFechaModAudit = lsAux.get(32);
				if (!sFechaModAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModAudit);
				}
				
				String sFechaMod = lsAux.get(33);
				if (!sFechaMod.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaMod);
				}
				
				String sFechaCreacion = lsAux.get(34);
				if (!sFechaCreacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreacion);
				}
				
				String sOrigenContacto = lsAux.get(35);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sOrigenContacto, 40));
			}
		}
	}
	
	//Test #2
	@Test
	public void TS125399_CRM_Interfaz_LCRM_IndividuoCuentaCliente() throws ParseException, IOException {
		String sName = "_INDIVIDUOCUENTACLIENTE_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIDIndividuo = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDIndividuo, 18));
				Assert.assertFalse(sIDIndividuo.isEmpty());
				
				String sIDCuentaCliente = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDCuentaCliente, 255));
				
				String sFechaCreaAudit = lsAux.get(2);
				if (!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);
				}
				
				String sFechaModAudit = lsAux.get(3);
				if (!sFechaModAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModAudit);
				}
				
				String sRol = lsAux.get(4);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sRol, 18));
			}
		}
	}
	
	//Test #3
	@Test
	public void TS125400_CRM_Interfaz_LCRM_CuentaCliente() throws ParseException, IOException {
		String sName = "_CUENTACLIENTE_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIdCuentaCliente = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdCuentaCliente, 18));
				Assert.assertFalse(sIdCuentaCliente.isEmpty());
				
				String sCodCuenta = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodCuenta, 255));
				Assert.assertFalse(sCodCuenta.isEmpty());
				
				String sMarcaFraude = lsAux.get(2);
				Integer.parseInt(sMarcaFraude);
				Assert.assertTrue(sMarcaFraude.equals("1") || sMarcaFraude.equals("0"));
				
				String sMarcaPartner = lsAux.get(3);
				Integer.parseInt(sMarcaPartner);
				Assert.assertTrue(sMarcaPartner.equals("1") || sMarcaPartner.equals("0"));
				
				String sMarcaPrensa = lsAux.get(4);
				Integer.parseInt(sMarcaPrensa);
				Assert.assertTrue(sMarcaPrensa.equals("1") || sMarcaPrensa.equals("0"));
				
				String sMarcaNoNominado = lsAux.get(5);
				Integer.parseInt(sMarcaNoNominado);
				Assert.assertTrue(sMarcaNoNominado.equals("1") || sMarcaNoNominado.equals("0"));
				
				String sMarcaVIP = lsAux.get(6);
				Integer.parseInt(sMarcaVIP);
				Assert.assertTrue(sMarcaVIP.equals("1") || sMarcaVIP.equals("0"));
				
				String sValorCuenta = lsAux.get(7);
				if (!sValorCuenta.isEmpty()) {
					Double.parseDouble(sValorCuenta);
				}
				
				String sIngresoBrutoAnual = lsAux.get(8);
				if (!sIngresoBrutoAnual.isEmpty()) {
					Double.parseDouble(sIngresoBrutoAnual);
				}
				
				String sIngresoNetoAnual = lsAux.get(9);
				if (!sIngresoNetoAnual.isEmpty()) {
					Double.parseDouble(sIngresoNetoAnual);
				}
				
				String sFechaFundacion = lsAux.get(10);
				if (!sFechaFundacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaFundacion);
				}
				
				String sNumeroFax = lsAux.get(11);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroFax, 40));
				
				String sRazonSocial = lsAux.get(12);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sRazonSocial, 255));
				Assert.assertFalse(sRazonSocial.isEmpty());
				
				String sNumeroTelefonoPpal = lsAux.get(13);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroTelefonoPpal, 40));
				
				String sNumeroTelefonoAlternativo = lsAux.get(14);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroTelefonoAlternativo, 40));
				
				String sEstado = lsAux.get(15);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEstado, 255));
				
				String sCUIT = lsAux.get(16);
				if (!sCUIT.isEmpty()) {
					rsePage.verifyCUITCUIT(sCUIT, sCUIT.split("-")[1]);
				}
				
				String sMarcaJubilado = lsAux.get(17);
				Integer.parseInt(sMarcaJubilado);
				Assert.assertTrue(sMarcaJubilado.equals("1") || sMarcaJubilado.equals("0"));
				
				String sFechaModEmail = lsAux.get(18);
				if (!sFechaModEmail.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModEmail);
				}
				
				String sCantidadEmpleados = lsAux.get(19);
				if (!sCantidadEmpleados.isEmpty()) {
					Integer.parseInt(sCantidadEmpleados);
				}
				
				String sIndustria = lsAux.get(20);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIndustria, 40));
				
				String sEmail = lsAux.get(21);
				if (!sEmail.isEmpty()) sEmail.contains("@");
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEmail, 255));
				
				String sSegmentoNivel1 = lsAux.get(22);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sSegmentoNivel1, 255));
				
				String sSegmentoNivel2 = lsAux.get(23);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sSegmentoNivel2, 255));
				
				String sCodPersonaContacto = lsAux.get(24);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodPersonaContacto, 18));
				
				String sCodCuentaOrigen = lsAux.get(25);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodCuentaOrigen, 18));
				
				String sFechaCreaAudit = lsAux.get(26);
				Assert.assertFalse(sIdCuentaCliente.isEmpty());
				sdfDateFormat = new SimpleDateFormat(sDateFormat);
				sdfDateFormat.parse(sFechaCreaAudit);
				
				String sCodUsuariosAlta = lsAux.get(27);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuariosAlta, 18));
				
				String sFechaModAudit = lsAux.get(28);
				Assert.assertFalse(sFechaModAudit.isEmpty());
				sdfDateFormat = new SimpleDateFormat(sDateFormat);
				sdfDateFormat.parse(sFechaModAudit);
				
				String sCodCuentaPadre = lsAux.get(29);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodCuentaPadre, 18));
				
				String sCodCliente = lsAux.get(30);
				Integer.parseInt(sCodCliente);
				
				String sFechaMod = lsAux.get(31);
				if (!sFechaMod.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaMod);
				}
				
				String sCodUsuarioMod = lsAux.get(32);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 18));
				
				String sFechaCreacion = lsAux.get(33);
				if (!sFechaCreacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreacion);
				}
				
				String sRiesgoCrediticio = lsAux.get(34);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sRiesgoCrediticio, 18));
				
				String sTipoCuenta = lsAux.get(35);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoCuenta, 80));
			}
		}
	}
	
	//Test #4
	@Test
	public void TS125401_CRM_Interfaz_LCRM_IndividuoCuentaFacturacion() throws ParseException, IOException {
		String sName = "_INDIVIDUOCUENTAFACTURACION_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIdIndividuo = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdIndividuo, 18));
				Assert.assertFalse(sIdIndividuo.isEmpty());
				
				String sIdCuentaFacturacion = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdCuentaFacturacion, 255));
				
				String sFechaCreaAudit = lsAux.get(2);
				Assert.assertFalse(sFechaCreaAudit.isEmpty());
				sdfDateFormat = new SimpleDateFormat(sDateFormat);
				sdfDateFormat.parse(sFechaCreaAudit);
				
				String sFechaModAudit = lsAux.get(3);
				Assert.assertFalse(sFechaModAudit.isEmpty());
				sdfDateFormat = new SimpleDateFormat(sDateFormat);
				sdfDateFormat.parse(sFechaModAudit);
				
				String sRol = lsAux.get(4);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sRol, 18));
				Assert.assertTrue(sRol.equalsIgnoreCase("Referente de Pago"));
			}
		}
	}
	
	//Test #5
	@Test
	public void TS125402_CRM_Interfaz_LCRM_CuentaFacturacion() throws ParseException, IOException {
		String sName = "_CUENTAFACTURACION_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sCodCuenta = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodCuenta, 255));
				Assert.assertFalse(sCodCuenta.isEmpty());
				
				String sIdCuentaFacturacion = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdCuentaFacturacion, 18));
				Assert.assertFalse(sIdCuentaFacturacion.isEmpty());
				
				String sMarcaDeudaFinanciada = lsAux.get(2);
				Assert.assertFalse(sMarcaDeudaFinanciada.isEmpty());
				Integer.parseInt(sMarcaDeudaFinanciada);
				Assert.assertTrue(sMarcaDeudaFinanciada.equals("1") || sMarcaDeudaFinanciada.equals("0"));
				
				String sMarcaFOL = lsAux.get(3);
				Assert.assertFalse(sMarcaFOL.isEmpty());
				Integer.parseInt(sMarcaFOL);
				Assert.assertTrue(sMarcaFOL.equals("1") || sMarcaFOL.equals("0"));
				
				String sMarcaCompraFinanciada = lsAux.get(4);
				Assert.assertFalse(sMarcaCompraFinanciada.isEmpty());
				Integer.parseInt(sMarcaCompraFinanciada);
				Assert.assertTrue(sMarcaCompraFinanciada.equals("1") || sMarcaCompraFinanciada.equals("0"));
				
				String sMarcaDebito = lsAux.get(5);
				Assert.assertFalse(sMarcaDebito.isEmpty());
				Integer.parseInt(sMarcaDebito);
				Assert.assertTrue(sMarcaDebito.equals("1") || sMarcaDebito.equals("0"));
				
				String sDireccionEmail = lsAux.get(6);
				Assert.assertFalse(sDireccionEmail.isEmpty());
				Assert.assertTrue(sDireccionEmail.contains("@"));
				Assert.assertTrue(rsePage.verifyTextMaxSize(sDireccionEmail, 80));
				
				String sCodCuentaFacturacion = lsAux.get(7);
				Assert.assertFalse(sCodCuentaFacturacion.isEmpty());
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodCuentaFacturacion, 5));
				Integer.parseInt(sCodCuentaFacturacion);
				
				String sCodCuentaPadre = lsAux.get(8);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodCuentaPadre, 18));
				
				String sCodCuentaOrigen = lsAux.get(9);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodCuentaOrigen, 18));
				
				String sIdMedioDePago = lsAux.get(10);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdMedioDePago, 18));
				
				String sCodUsuarioAlta = lsAux.get(11);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioAlta, 18));
				
				String sCodUsuarioMod = lsAux.get(12);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 18));
				
				String sCodCliente = lsAux.get(13);
				Integer.parseInt(sCodCliente);
				
				String sNumeroTelefonoPpal = lsAux.get(14);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroTelefonoPpal, 40));
				
				String sNumeroTelefonoAlternativo = lsAux.get(15);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroTelefonoAlternativo, 40));
				
				String sCicloFacturacion = lsAux.get(16);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCicloFacturacion, 255));
				
				String sIDDireccionFacturacion = lsAux.get(17);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDDireccionFacturacion, 255));
				
				String sIDDireccionEnvio = lsAux.get(18);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDDireccionEnvio, 255));
				
				String sMarcaSocio = lsAux.get(19);
				Integer.parseInt(sMarcaSocio);
				Assert.assertTrue(sMarcaSocio.equals("1") || sMarcaSocio.equals("0"));
				
				String sMarcaMorosidad = lsAux.get(20);
				Integer.parseInt(sMarcaMorosidad);
				Assert.assertTrue(sMarcaMorosidad.equals("1") || sMarcaMorosidad.equals("0"));
				
				String sFechaModEmail = lsAux.get(21);
				if (!sFechaModEmail.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModEmail);
				}
				
				String sPuntosAcumulados = lsAux.get(22);
				Integer.parseInt(sPuntosAcumulados);
				
				String sCategoriaSocio = lsAux.get(23);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCategoriaSocio, 255));
				
				String sTipoCuenta = lsAux.get(24);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoCuenta, 40));
				
				String sMetodoEntrega = lsAux.get(25);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sMetodoEntrega, 255));
				
				String sFrecuenciaFacturacion = lsAux.get(26);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sFrecuenciaFacturacion, 255));
				
				String sSLA = lsAux.get(27);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sSLA, 255));
				
				String sTipoRegistro = lsAux.get(28);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoRegistro, 18));
				
				String sTipoDePago = lsAux.get(29);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoDePago, 255));
				
				String sRiesgoCrediticio = lsAux.get(30);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sRiesgoCrediticio, 18));
				
				String sContactoPrimario = lsAux.get(31);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sContactoPrimario, 255));
				
				String sFechaCreaAudit = lsAux.get(32);
				if (!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);
				}
				
				String sFechaModAudit = lsAux.get(33);
				if (!sFechaModAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModAudit);
				}
				
				String sFechaCreacion = lsAux.get(34);
				if (!sFechaCreacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreacion);
				}
				
				String sFechaMod = lsAux.get(35);
				if (!sFechaMod.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaMod);
				}
				
				String sEstado = lsAux.get(36);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEstado, 255));
				
				String sPreferenciasContacto = lsAux.get(37);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sPreferenciasContacto, 255));
			}
		}
	}
	
	//Test #6
	@Test
	public void TS125403_CRM_Interfaz_LCRM_ProductoAquirido() throws ParseException, IOException {
		String sName = "_PRODUCTOADQUIRIDO_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIDProductoAdquirido = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDProductoAdquirido, 18));
				Assert.assertFalse(sIDProductoAdquirido.isEmpty());
				
				String sIDCuentaFacturacion = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDCuentaFacturacion, 18));
				Assert.assertFalse(sIDCuentaFacturacion.isEmpty());
				
				String sIDProducto = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDProducto, 18));
				Assert.assertFalse(sIDProducto.isEmpty());
				
				String sMarcaProdCompetencia = lsAux.get(3);
				Integer.parseInt(sMarcaProdCompetencia);
				
				String sFechaInstalacion = lsAux.get(4);
				if (!sFechaInstalacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaInstalacion);
				}
				
				String sCodUsuarioAlta = lsAux.get(5);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioAlta, 18));
				
				String sCodUsuarioMod = lsAux.get(6);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 18));
				
				String sCantUnidadesProdAdquiridas = lsAux.get(7);
				Double.parseDouble(sCantUnidadesProdAdquiridas);
				
				String sTipoProducto = lsAux.get(8);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoProducto, 255));
				
				String sEstadoProductoAdq = lsAux.get(9);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEstadoProductoAdq, 40));
				
				String sNombreProducto = lsAux.get(10);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNombreProducto, 255));
				
				String sNumeroSerie = lsAux.get(11);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroSerie, 80));
				
				String sEstadoEnRed = lsAux.get(12);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEstadoEnRed, 255));
				
				String sSubestadoProductoAdq = lsAux.get(13);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sSubestadoProductoAdq, 255));
				
				String sNumeroLinea = lsAux.get(14);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroLinea, 10));
				
				String sFechaActivacion = lsAux.get(15);
				if (!sFechaActivacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaActivacion);
				}
				
				String sEstadoProvisionamiento = lsAux.get(16);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEstadoProvisionamiento, 255));
				
				String sFamiliaProducto = lsAux.get(17);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sFamiliaProducto, 255));
				
				String sCodProductoPadre = lsAux.get(18);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodProductoPadre, 255));
				
				String sFechaCreacion = lsAux.get(19);
				if (!sFechaCreacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreacion);
				}
				
				String sFechaCreaAudit = lsAux.get(20);
				if (!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);
				}
				
				String sFechaModAudit = lsAux.get(21);
				if (!sFechaModAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModAudit);
				}
				
				String sIMEI = lsAux.get(22);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIMEI, 15));
				
				String sIdOrdenItem = lsAux.get(23);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdOrdenItem, 18));
				Assert.assertFalse(sIdOrdenItem.isEmpty());
				
				String sCodProductoRaiz = lsAux.get(24);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodProductoRaiz, 255));
				
				String sICCID = lsAux.get(25);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sICCID, 255));
				
				String sNMU = lsAux.get(26);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNMU, 200));
				
				String sGamaEquipo = lsAux.get(27);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sGamaEquipo, 200));
				
				String sCodProducto = lsAux.get(28);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodProducto, 510));
				
				String sIMSI = lsAux.get(29);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIMSI, 512));
				
				String sCodSuscripcion = lsAux.get(30);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodSuscripcion, 72));
				
				String sIDProductoAdquiridoReferente = lsAux.get(31);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDProductoAdquiridoReferente, 510));
				
				String sMarcaMigracion = lsAux.get(32);
				if (!sMarcaMigracion.isEmpty()) {
					Integer.parseInt(sMarcaMigracion);
					Assert.assertTrue(sMarcaMigracion.equals("1") || sMarcaMigracion.equals("0"));
				}
				
				String sMarcaGarantiaInvalida = lsAux.get(33);
				if (!sMarcaGarantiaInvalida.isEmpty()) {
					Integer.parseInt(sMarcaGarantiaInvalida);
					Assert.assertTrue(sMarcaGarantiaInvalida.equals("1") || sMarcaGarantiaInvalida.equals("0"));
				}
				
				String sNivelJerarquia = lsAux.get(34);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNivelJerarquia, 255));
			}
		}
	}
	
	//Test #7
	@Test
	public void TS125404_CRM_Interfaz_LCRM_Orden() throws ParseException, IOException {
		String sName = "_ORDEN_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIDOrden = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDOrden, 18));
				Assert.assertFalse(sIDOrden.isEmpty());
				
				String sNumeroOrden = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroOrden, 30));
				Assert.assertFalse(sNumeroOrden.isEmpty());
				
				String sIdCuentaFacturacion = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdCuentaFacturacion, 18));
				Assert.assertFalse(sIdCuentaFacturacion.isEmpty());
				
				String sTipoGestion = lsAux.get(3);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoGestion, 255));
				
				String sCodUsuarioAlta = lsAux.get(4);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioAlta, 18));
				
				String sEstadoOrden = lsAux.get(5);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEstadoOrden, 40));
				
				String sMetodoEntrega = lsAux.get(6);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sMetodoEntrega, 255));
				
				String sPrecioTotalOrden = lsAux.get(7);
				Double.parseDouble(sPrecioTotalOrden);
				
				String sFechaInicioOrden = lsAux.get(8);
				if (!sFechaInicioOrden.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaInicioOrden);
				}
				
				String sFechaFinOrden = lsAux.get(9);
				if (!sFechaFinOrden.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaFinOrden);
				}
				
				String sFechaVenta = lsAux.get(10);
				if (!sFechaVenta.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaVenta);
				}
				
				String sFechaEntrega = lsAux.get(11);
				if (!sFechaEntrega.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaEntrega);
				}
				
				String sFechaModificacion = lsAux.get(12);
				if (!sFechaModificacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModificacion);
				}
				
				String sCodUsuarioMod = lsAux.get(13);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 18));
				
				String sEstadoTrackeo = lsAux.get(14);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEstadoTrackeo, 255));
				
				String sFechaCreaAudit = lsAux.get(15);
				if (!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);
				}
				
				String sFechaModAudit = lsAux.get(16);
				if (!sFechaModAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModAudit);
				}
				
				String sFechaCreacion = lsAux.get(17);
				if (!sFechaCreacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreacion);
				}
				
				String sFechaMod = lsAux.get(18);
				if (!sFechaMod.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaMod);
				}
				
				String sNumeroComprobante = lsAux.get(19);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroComprobante, 255));
				
				String sNumeroPreFactura = lsAux.get(20);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroPreFactura, 255));
				
				String sCodMetodoPago = lsAux.get(21);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodMetodoPago, 255));
				
				String sCodBanco = lsAux.get(22);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodBanco, 10));
				
				String sCodTarjeta = lsAux.get(23);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodTarjeta, 10));
				
				String sCFT = lsAux.get(24);
				if (!sCFT.isEmpty()) {
					Double.parseDouble(sCFT);
				}
				
				String sCanalOrigen = lsAux.get(25);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCanalOrigen, 255));
				
				String sEstadoProvisionamientoOrden = lsAux.get(26);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEstadoProvisionamientoOrden, 255));
				
				String sFechaActivacion = lsAux.get(27);
				if (!sFechaActivacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaActivacion);
				}
				
				String sIDPuntoDeVenta = lsAux.get(28);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDPuntoDeVenta, 18));
				
				String sTipoSubgestion = lsAux.get(39);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoSubgestion, 255));
			}
		}
	}
	
	//Test #8
	@Test
	public void TS125405_CRM_Interfaz_LCRM_OrdenItem() throws ParseException, IOException {
		String sName = "_ORDENITEM_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIdOrdenItem = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdOrdenItem, 18));
				Assert.assertFalse(sIdOrdenItem.isEmpty());
				
				String sNumeroOrdenItem = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroOrdenItem, 30));
				Assert.assertFalse(sNumeroOrdenItem.isEmpty());
				
				String IdOrden = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(IdOrden, 30));
				Assert.assertFalse(IdOrden.isEmpty());
				
				String sPrecioEfectivoUnicaVez = lsAux.get(3);
				if (!sPrecioEfectivoUnicaVez.isEmpty()) {
					Double.parseDouble(sPrecioEfectivoUnicaVez);
				}
				
				String sPrecioRecurrente = lsAux.get(4);
				if (!sPrecioRecurrente.isEmpty()) {
					Double.parseDouble(sPrecioRecurrente);				
				}
				
				String sMRC = lsAux.get(5);
				if (!sMRC.isEmpty()) {
					Double.parseDouble(sMRC);
				}
				
				String sPrecioDctoUnicaVez = lsAux.get(6);
				if (!sPrecioDctoUnicaVez.isEmpty()) {
					Double.parseDouble(sPrecioDctoUnicaVez);
				}
				
				String sCargoUnicaVez = lsAux.get(7);
				if (!sCargoUnicaVez.isEmpty()) {
					Double.parseDouble(sCargoUnicaVez);
				}
				
				String sPrecioTotalUnicaVez = lsAux.get(8);
				if (!sPrecioTotalUnicaVez.isEmpty()) {
					Double.parseDouble(sPrecioTotalUnicaVez);
				}
				
				String sPrecioCalculadoRecurrente = lsAux.get(9);
				if (!sPrecioCalculadoRecurrente.isEmpty()) {
					Double.parseDouble(sPrecioCalculadoRecurrente);
				}
				
				String sCargoRecurrente = lsAux.get(10);
				if (!sCargoRecurrente.isEmpty()) {
					Double.parseDouble(sCargoRecurrente);
				}
				
				String sTotalRecurrente = lsAux.get(11);
				if (!sTotalRecurrente.isEmpty()) {
					Double.parseDouble(sTotalRecurrente);
				}
				
				String sNumeroSecuencia = lsAux.get(12);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroSecuencia, 255));
				
				String sCantidadItem = lsAux.get(13);
				if (!sCantidadItem.isEmpty()) {
					Double.parseDouble(sCantidadItem);
				}
				
				String sPrecioUnitario = lsAux.get(14);
				if (!sPrecioUnitario.isEmpty()) {
					Double.parseDouble(sPrecioUnitario);
				}
				
				String sAccion = lsAux.get(15);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sAccion, 255));
				
				String sFechaCreaAudit = lsAux.get(16);
				if (!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);
				}
				
				String sFechaModAudit = lsAux.get(17);
				if (!sFechaModAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModAudit);
				}
				
				String sEstadoProvisionamientoItem = lsAux.get(18);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEstadoProvisionamientoItem, 255));
				
				String sSubaccion = lsAux.get(19);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sSubaccion, 255));
				
				String sEstadoOrdenItem = lsAux.get(20);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEstadoOrdenItem, 255));
				
				String sPrecioLista = lsAux.get(21);
				if (!sPrecioLista.isEmpty()) {
					Double.parseDouble(sPrecioLista);
				}
				
				String sIdProducto = lsAux.get(22);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdProducto, 18));
				
				String sMarcaFacturable = lsAux.get(23);
				if (!sMarcaFacturable.isEmpty()) {
					Integer.parseInt(sMarcaFacturable);
					Assert.assertTrue(sMarcaFacturable.equals("1") || sMarcaFacturable.equals("0"));
				}
				
				String sCodDeposito = lsAux.get(24);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodDeposito, 10));
				
				String sCodOperacion = lsAux.get(25);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodOperacion, 10));
				
				String sFechaCreacion = lsAux.get(26);
				if (!sFechaCreacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreacion);
				}
				
				String sCodUsuarioAlta = lsAux.get(27);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioAlta, 18));
				
				String sCodUsuarioMod = lsAux.get(28);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 18));
				
				String sFechaMod = lsAux.get(29);
				if (!sFechaMod.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaMod);
				}
				
				String sCodProductoRaiz = lsAux.get(30);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodProductoRaiz, 255));
				
				String sCodProductoPadre = lsAux.get(31);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodProductoPadre, 255));
				
				String sIdProductoAdquiridoReferente = lsAux.get(32);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdProductoAdquiridoReferente, 255));
				
				String sTipoAjuste = lsAux.get(33);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoAjuste, 255));
				
				String sMonto = lsAux.get(34);
				if (!sMonto.isEmpty()) {
					Double.parseDouble(sMonto);
				}
				
				String sValorAjuste = lsAux.get(35);
				if (!sMonto.isEmpty()) {
					Double.parseDouble(sValorAjuste);
				}
				
				String sValorBase = lsAux.get(36);
				if (!sValorBase.isEmpty()) {
					Double.parseDouble(sValorBase);
				}
				
				String sEstadoStock = lsAux.get(37);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEstadoStock, 20));
				
				String sMarcaNoProvisionable = lsAux.get(38);
				Integer.parseInt(sMarcaNoProvisionable);
				Assert.assertTrue(sMarcaNoProvisionable.equals("1") || sMarcaNoProvisionable.equals("0"));
			}
		}
	}
	
	//Test #9
	//@Test
	public void TS125406_CRM_Interfaz_LCRM_CustomerANI() throws ParseException, IOException {
		String sName = "_CUSTOMERANY_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIDcuenta = lsAux.get(0); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDcuenta, 18)); 
				Assert.assertFalse(sIDcuenta.isEmpty()); 
				 
				String sIDDeIntegracionDeLaCuenta = lsAux.get(1); 
				Integer.parseInt(sIDDeIntegracionDeLaCuenta); 
				 
				String sTipoDocumentoTitular = lsAux.get(2); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoDocumentoTitular, 255)); 
				 
				String sNroDocumentoTitular = lsAux.get(3); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNroDocumentoTitular, 30)); 
				 
				String sCUIL =	lsAux.get(4); 
				if (!sCUIL.isEmpty()) { 
					rsePage.verifyCUITCUIT(sCUIL, sNroDocumentoTitular); 
				} 
				 
				String sRazonSocial = lsAux.get(5); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sRazonSocial, 0));//preguntar no tiene cantidad de caracteres 
				 
				String sCUIT = lsAux.get(6); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCUIT, 0));//preguntar no tiene cantidad de caracteres 
				 
				String sSegmentoDelCliente = lsAux.get(7); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sSegmentoDelCliente, 100)); 
				 
				String sSubsegmentoDelCliente = lsAux.get(8); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sSubsegmentoDelCliente, 100)); 
				 
				String sTipoDeSuscripcion = lsAux.get(9); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoDeSuscripcion, 255)); 
				 
				String sCiclo = lsAux.get(10); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCiclo, 0));//preguntar no tiene cantidad de caracteres 
				
				String sNombreTitular = lsAux.get(11); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNombreTitular, 121)); 
				 
				String sApellidoTitular = lsAux.get(12); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sApellidoTitular, 121)); 
				 
				String sFechaDeCcreacionDelProductoAdquirido = lsAux.get(13); 
				if (!sFechaDeCcreacionDelProductoAdquirido.isEmpty()) { 
					sdfDateFormat = new SimpleDateFormat(sDateFormat); 
					sdfDateFormat.parse(sFechaDeCcreacionDelProductoAdquirido); 
				}
				
				String sLinea = lsAux.get(14); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sLinea, 20)); 
				Assert.assertFalse(sLinea.isEmpty()); 
				 
				String sFamiliaDelProducto = lsAux.get(15); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sFamiliaDelProducto, 1300)); 
				 
				String sIdDelProducto = lsAux.get(16); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdDelProducto, 18)); 
				Assert.assertFalse(sIdDelProducto.isEmpty()); 
				 
				String sTipoDePlan = lsAux.get(17); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoDePlan, 0));//preguntar no tiene cantidad de caracteres 
				 
				String sCompaniaPortIN = lsAux.get(18); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCompaniaPortIN, 0));//preguntar no tiene cantidad de caracteres 
				 
				String sIDContacto = lsAux.get(19); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDContacto, 0));//preguntar no tiene cantidad de caracteres 
				 
				String sMarcaFOL = lsAux.get(20); 
				if (!sMarcaFOL.isEmpty()) { 
					Integer.parseInt(sMarcaFOL); 
					Assert.assertTrue(sMarcaFOL.equals("1") || sMarcaFOL.equals("0")); 
				} 
				 
				String sTelefono = lsAux.get(21); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTelefono, 0));//preguntar no tiene cantidad de caracteres 
			}
		}
	}
	
	//Test #10
	@Test
	public void TS125407_CRM_Interfaz_LCRM_Lineas_pre_activadas() throws ParseException, IOException {
		String sName = "_LINEASPREACTIVADAS_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sLinea = lsAux.get(0);
				Assert.assertFalse(sLinea.isEmpty());
				Assert.assertTrue(lsAux.size() == 1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sLinea, 20));
			}
		}
	}
	
	//Test #11
	@Test
	public void TS125408_CRM_Interfaz_LCRM_CuentaFacturacionInsercion() throws ParseException, IOException {
		String sName = "_CUENTAFACTURACIONINSERCION_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIdCuentaFacturacion = lsAux.get(0); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdCuentaFacturacion, 255)); 
				 
				String sFechaDeAltaCuentaFacturacion = lsAux.get(1); 
				if (!sFechaDeAltaCuentaFacturacion.isEmpty()) { 
					sdfDateFormat = new SimpleDateFormat(sDateFormat); 
					sdfDateFormat.parse(sFechaDeAltaCuentaFacturacion);
				}
				
				String sRazonSocial = lsAux.get(2); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sRazonSocial, 0));//preguntar no tiene cantidad de caracteres 
				 
				String sDireccionlinea = lsAux.get(3); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sDireccionlinea, 0));//preguntar mensaje "Pendiente Vlovity" 
				 
				String sProvincia_Linea = lsAux.get(4); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sProvincia_Linea, 0));//preguntar mensaje "Pendiente Vlovity" 
				 
				String sCiudad_Linea = lsAux.get(5); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCiudad_Linea, 0));//preguntar mensaje "Pendiente Vlovity" 
				 
				String sNumeroDeTelefonoDelIndividuo = lsAux.get(6); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroDeTelefonoDelIndividuo, 40)); 
				 
				String sNumeroDeCelularDeContactoDelIndividuo = lsAux.get(7); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroDeCelularDeContactoDelIndividuo, 40)); 
				 
				String sLimiteDeCredito = lsAux.get(8); 
				if (!sLimiteDeCredito.isEmpty()) { 
					Double.parseDouble(sLimiteDeCredito); 
				} 
				 
				String sMedioDePagoDeLaCuentaDeFacturacion = lsAux.get(9); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sMedioDePagoDeLaCuentaDeFacturacion, 255)); 
				 
				String sNumeroDetarjeta_CBU_AsociadoAlMedioDePago = lsAux.get(10); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroDetarjeta_CBU_AsociadoAlMedioDePago, 22)); 
				 
				String sN_A = lsAux.get(11); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sN_A, 3)); 
				 
				String sCicloDeFacturacion = lsAux.get(12); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCicloDeFacturacion, 255)); 
				 
				String sRiesgoCrediticio = lsAux.get(13); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sRiesgoCrediticio, 18)); 
				 
				String sTipoDeDocumento = lsAux.get(14); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoDeDocumento, 255)); 
				 
				String sNumeroDeDocumento = lsAux.get(15); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroDeDocumento, 30)); 
				 
				String sFechaDeNacimiento = lsAux.get(16); 
				if (!sFechaDeNacimiento.isEmpty()) { 
					sdfDateFormat = new SimpleDateFormat(sDateFormatDMY); 
					sdfDateFormat.parse(sFechaDeNacimiento); 
				}
			}
		}
	}
	
	//Test #12
	@Test
	public void TS125409_CRM_Interfaz_LCRM_Acuerdoservicio() throws ParseException, IOException {
		String sName = "_ACUERDOSERVICIO_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIdAcuerdoServicio = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdAcuerdoServicio, 18));
				
				String sAcuerdo = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sAcuerdo, 255));
				
				String sFechaCreacion = lsAux.get(2);
				if (!sFechaCreacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreacion);
				}
				
				String sCodUsuarioAlta = lsAux.get(3);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioAlta, 18));
				
				String sFechaMod = lsAux.get(4);
				if (!sFechaMod.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaMod);
				}
				
				String sCodUsuarioMod = lsAux.get(5);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 18));
				
				String sIdCuentaFacturacion = lsAux.get(6);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdCuentaFacturacion, 18));
				
				String sTipoAcuerdo = lsAux.get(7);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoAcuerdo, 40));
				
				String sIdProductoAdquirido = lsAux.get(8);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdProductoAdquirido, 40));
				
				String sFechaDesdeAcuerdo = lsAux.get(9);
				if (!sFechaDesdeAcuerdo.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaDesdeAcuerdo);
				}
				
				String sFechaHastaAcuerdo = lsAux.get(10);
				if (!sFechaHastaAcuerdo.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaHastaAcuerdo);
				}
				
				String sMarcaIncidente = lsAux.get(11);
				if (!sMarcaIncidente.isEmpty()) {
					Integer.parseInt(sMarcaIncidente);
					Assert.assertTrue(sMarcaIncidente.equals("1") || sMarcaIncidente.equals("0"));
				}
				
				String sEstadoAcuerdo = lsAux.get(12);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEstadoAcuerdo, 255));
				
				String sFechaCreaAudit = lsAux.get(13);
				if (!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);
				}
				
				String sFechaModAudit = lsAux.get(14);
				if (!sFechaModAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModAudit);
				}
			}
		}
	}
	
	//Test #13
	//@Test
	public void TS125411_CRM_Interfaz_LCRM_ProductoAdquiridoCaracteristica() throws ParseException, IOException {
		String sName = "_PRODADQUIRIDOCARACTERISTICA_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIDCaracteristica = lsAux.get(0);
				Assert.assertFalse(sIDCaracteristica.isEmpty());
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDCaracteristica, 18));
				
				String sIDProductoAdquirido = lsAux.get(1);
				Assert.assertFalse(sIDProductoAdquirido.isEmpty());
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDProductoAdquirido, 18));
				
				String sValor = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sValor, 200));
				
				String sTipo = lsAux.get(3);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipo, 256));
				
				String sFechaCreacion = lsAux.get(4);
				if (!sFechaCreacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreacion);
				}
				
				String sFechaMod = lsAux.get(5);
				if (!sFechaMod.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaMod);
				}
				
				String sFechaCreaAudit = lsAux.get(6);
				if (!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);
				}
				
				String sFechaModAudit = lsAux.get(7);
				if (!sFechaModAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModAudit);
				}
			}
		}
	}
	
	//Test #14
	//@Test
	public void TS125412_CRM_Interfaz_LCRM_ProductoAdquiridoLinea() throws ParseException, IOException {
		String sName = "_PRODUCTOADQUIRIDOLINEA_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIntegrationId = lsAux.get(0); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIntegrationId, 255)); 
				 
				String sStatusDelAsset = lsAux.get(1); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sStatusDelAsset, 255)); 
				 
				String sNumeroDeLinea = lsAux.get(2); 
				if (!sNumeroDeLinea.isEmpty()) { 
					Integer.parseInt(sNumeroDeLinea); 
				} 
				 
				String sIDRed = lsAux.get(3); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDRed, 200)); 
				 
				String sIMEI = lsAux.get(4); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIMEI, 0));//preguntar no tiene cantidad de caracteres 
				 
				String sMercado = lsAux.get(5); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sMercado, 255)); 
				 
				String sSegmento = lsAux.get(6); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sSegmento, 255)); 
				 
				String sFechaActivacion = lsAux.get(7); 
				if (!sFechaActivacion.isEmpty()) { 
					sdfDateFormat = new SimpleDateFormat(sDateFormat); 
					sdfDateFormat.parse(sFechaActivacion); 
				} 
				 
				String sFamiliaDelProducto = lsAux.get(8); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sFamiliaDelProducto, 255)); 
				 
				String sValorConstante = lsAux.get(9); 
				if (!sValorConstante.isEmpty()) { 
					Integer.parseInt(sValorConstante); 
				} 
				 
				String sCicloFactura = lsAux.get(10); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCicloFactura, 255)); 
				 
				String sIDDelProductoAdquirido = lsAux.get(11); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDDelProductoAdquirido, 18)); 
				 
				String sEstadoDelProductoAdquirido = lsAux.get(12); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEstadoDelProductoAdquirido, 255)); 
				 
				String sDireccion = lsAux.get(13); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sDireccion, 0));//preguntar no tiene cantidad de caracteres 
				 
				String sProvincia = lsAux.get(14); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sProvincia, 0));//preguntar no tiene cantidad de caracteres 
				 
				String sCiudad = lsAux.get(15); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCiudad, 0));//preguntar no tiene cantidad de caracteres 
				 
				String sTelefonoContacto = lsAux.get(16); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTelefonoContacto, 40)); 
				 
				String sTelefonoCelularDelContacto = lsAux.get(17); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTelefonoCelularDelContacto, 40)); 
				 
				String sTelefonoFijoDelContacto = lsAux.get(18); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTelefonoFijoDelContacto, 40)); 
				 
				String sTipoDocumento = lsAux.get(19); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoDocumento, 255)); 
				 
				String sNumeroDocumento = lsAux.get(20); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroDocumento, 40)); 
				 
				String sPlanTarifario = lsAux.get(21); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sPlanTarifario, 18)); 
				 
				String sCodigoVendedor = lsAux.get(22); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodigoVendedor, 18)); 
				 
				String sNombreVendedor = lsAux.get(23); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNombreVendedor, 0));//preguntar no tiene cantidad de caracteres 
				 
				String sCodigoPuntoVenta = lsAux.get(24); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodigoPuntoVenta, 0));//preguntar no tiene cantidad de caracteres 
				 
				String sNombrePuntoVenta = lsAux.get(25); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNombrePuntoVenta, 0));//preguntar no tiene cantidad de caracteres 
				 
				String sEstadoLineaSusp = lsAux.get(26); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEstadoLineaSusp, 0));//preguntar no tiene cantidad de caracteres 
				 
				String sProcedencia_portabilidad = lsAux.get(27); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sProcedencia_portabilidad, 0));//preguntar no tiene cantidad de caracteres 
			}
		}
	}
	
	//Test #15
	@Test
	public void TS125413_CRM_Interfaz_LCRM_ProductoAdquiridoEquipos() throws ParseException, IOException {
		String sName = "_PRODUCTOADQUIRIDOEQUIPOS_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sPTVTA = lsAux.get(0); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sPTVTA, 0));//preguntar no tiene cantidad de caracteres 
				 
				String sPTVTADescripcionSeparadosPorUnEspacio = lsAux.get(1); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sPTVTADescripcionSeparadosPorUnEspacio, 0));//preguntar no tiene cantidad de caracteres 
				 
				String sVENDEDOR = lsAux.get(2); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sVENDEDOR, 18)); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sVENDEDOR, 242)); 
				 
				String sNombreDelVvendedor = lsAux.get(3); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNombreDelVvendedor, 18)); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNombreDelVvendedor, 242)); 
				 
				String sMERCADO = lsAux.get(4); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sMERCADO, 255)); 
				 
				String sFECHA_OPER = lsAux.get(5); 
				if (!sFECHA_OPER.isEmpty()) { 
					sdfDateFormat = new SimpleDateFormat(sDateFormat); 
					sdfDateFormat.parse(sFECHA_OPER);
				}
				
				String sFECHA_MODIF = lsAux.get(6); 
				if (!sFECHA_MODIF.isEmpty()) { 
					sdfDateFormat = new SimpleDateFormat(sDateFormat); 
					sdfDateFormat.parse(sFECHA_MODIF); 
				} 
				 
				String sIMEI = lsAux.get(7); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIMEI, 15)); 
				 
				String sNMU = lsAux.get(8); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNMU, 256)); 
				 
				String sNMU_DESCRIPCION = lsAux.get(9); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNMU_DESCRIPCION, 200)); 
				 
				String sDNNUM = lsAux.get(10); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sDNNUM, 20)); 
				 
				String sTIPO_VENTA = lsAux.get(11); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTIPO_VENTA, 255)); 
				 
				String sNumeroDeOrden = lsAux.get(12); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroDeOrden, 30)); 
				 
				String sEstadoDeLaOrden = lsAux.get(13); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEstadoDeLaOrden, 255)); 
				 
				String sTipoDeDocumentoDeLaCuentaDeFacturacion = lsAux.get(14); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoDeDocumentoDeLaCuentaDeFacturacion, 30)); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoDeDocumentoDeLaCuentaDeFacturacion, 255)); 
				 
				String sNumeroDeDocumentoDeLaCuentaDeFacturacion = lsAux.get(15); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroDeDocumentoDeLaCuentaDeFacturacion, 30)); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroDeDocumentoDeLaCuentaDeFacturacion, 255)); 
				 
				String sID_PROMO = lsAux.get(16); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sID_PROMO, 18)); 
				 
				String sDescripcionDeLaPromo = lsAux.get(17); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sDescripcionDeLaPromo, 80)); 
				 
				String sProductoAdquirido_asset = lsAux.get(18); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sProductoAdquirido_asset, 18)); 
				 
				String sProdcuto = lsAux.get(19); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sProdcuto, 255)); 
				 
				String sDescripcionDelProducto = lsAux.get(20); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sDescripcionDelProducto, 255)); 
				 
				String sIdInternoDeAccount = lsAux.get(21); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdInternoDeAccount, 18)); 
				 
				String sTipoDeDocumentoDelContacto = lsAux.get(22); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoDeDocumentoDelContacto, 255)); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoDeDocumentoDelContacto, 30)); 
				 
				String sNumeroDeDocumentoDelContacto = lsAux.get(23); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroDeDocumentoDelContacto, 255)); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroDeDocumentoDelContacto, 30)); 
				 
				String sPRECIO_LISTA = lsAux.get(24); 
				if (!sPRECIO_LISTA.isEmpty()) { 
					Double.parseDouble(sPRECIO_LISTA); 
				} 
				 
				String sPRECIO_VENTA = lsAux.get(25); 
				if (!sPRECIO_VENTA.isEmpty()) { 
					Double.parseDouble(sPRECIO_VENTA); 
				} 
				 
				String sSUBSIDIO = lsAux.get(26); 
				if (!sSUBSIDIO.isEmpty()) { 
					Double.parseDouble(sSUBSIDIO); 
				} 
				 
				String sIMPUESTO = lsAux.get(27); 
				if (!sIMPUESTO.isEmpty()) { 
					Double.parseDouble(sIMPUESTO); 
				} 
				 
				String sCOD_TIPIFICACION = lsAux.get(28); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCOD_TIPIFICACION, 0));//preguntar no tiene cantidad de caracteres 
				 
				String sGAMA = lsAux.get(29); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sGAMA, 0));//preguntar no tiene cantidad de caracteres 
				 
				String sBODEGA = lsAux.get(30); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sBODEGA, 0));//preguntar no tiene cantidad de caracteres 
				 
				String sIMSI = lsAux.get(31); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIMSI, 256)); 
				 
				String sDESCR_CANAL = lsAux.get(32); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sDESCR_CANAL, 0));//preguntar no tiene cantidad de caracteres 
				 
				String sREF_PREF = lsAux.get(33); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sREF_PREF, 30)); 
				 
				String sDESC_PROCEDENCIA = lsAux.get(34); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sDESC_PROCEDENCIA, 3)); 
				 
				String sSEGMENTO = lsAux.get(35); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sSEGMENTO, 255)); 
				 
				String sBONIFICACION = lsAux.get(36); 
				if (!sBONIFICACION.isEmpty()) { 
					Double.parseDouble(sBONIFICACION); 
				} 
				 
				String sSIN_IMPUESTO = lsAux.get(37); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sSIN_IMPUESTO, 0));//preguntar no tiene cantidad de caracteres 
				 
				String sId_Procedencia = lsAux.get(38); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sId_Procedencia, 0));//preguntar no tiene cantidad de caracteres 
				 
				 
				String sCanal = lsAux.get(39); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCanal, 510)); 
				 
				String sCanal2 = lsAux.get(40); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCanal2, 510)); 
				 
				String sCanal3 = lsAux.get(41); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCanal3, 510));
			}
		}
	}
	
	//Test #16
	@Test
	public void TS125414_CRM_Interfaz_LCRM_Ajuste() throws ParseException, IOException {
		String sName = "_AJUSTE_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIDAjuste = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDAjuste, 18));
				
				String sNombreCasoAjuste = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNombreCasoAjuste, 80));
				
				String sFechaCreacion = lsAux.get(2);
				if (!sFechaCreacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreacion);
				}
				
				String sCodUsuarioCrea = lsAux.get(3);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioCrea, 18));
				
				String sFechaMod = lsAux.get(4);
				if (!sFechaMod.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaMod);
				}
				
				String sCodUsuarioMod = lsAux.get(5);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 18));
				
				String sIDCuentaFacturacion = lsAux.get(6);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDCuentaFacturacion, 18));
				
				String sMonto = lsAux.get(7);
				Double.parseDouble(sMonto);
				
				String sIDMetodoDePago = lsAux.get(8);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDMetodoDePago, 18));
				
				String sEstado = lsAux.get(9);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEstado, 255));
				
				String sTipoAjuste = lsAux.get(10);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoAjuste, 255));
				
				String sIDOrden = lsAux.get(11);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDOrden, 18));
				
				String sFechaCreaAudit = lsAux.get(12);
				if (!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);
				}
				
				String sFechaModAudit = lsAux.get(13);
				if (!sFechaModAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModAudit);
				}
				
				String sIDCaso = lsAux.get(14);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDCaso, 18));
			}
		}
	}
	
	//Test #17
	@Test
	public void TS125415_CRM_Interfaz_LCRM_ItemProducto2() throws ParseException, IOException {
		String sName = "_ITEMPRODUCTO2_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIDRaiz = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDRaiz, 18));
				
				String sNivel = lsAux.get(1);
				Integer.parseInt(sNivel);
				
				String sIDProducto = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDProducto, 18));
				
				String sIDPadre =  lsAux.get(3);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDPadre, 18));
				
				String sNombreProducto = lsAux.get(4);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNombreProducto, 255));
			}
		}
	}
	
	//Test #18
	@Test
	public void TS125416_CRM_Interfaz_LCRM_Producto() throws ParseException, IOException {
		String sName = "_PRODUCTO_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIdProducto = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdProducto, 18));
				Assert.assertFalse(sIdProducto.isEmpty());
				
				String sCodUsuarioAlta = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioAlta, 18));
				
				String sFechaCreacion = lsAux.get(2);
				if (!sFechaCreacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreacion);
				}
				
				String sDescripcion = lsAux.get(3);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sDescripcion, 255));
				
				String sFamiliaProducto = lsAux.get(4);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sFamiliaProducto, 40));
				
				String sMarcaBorrar = lsAux.get(5);
				Integer.parseInt(sMarcaBorrar);
				Assert.assertTrue(sMarcaBorrar.equals("1") || sMarcaBorrar.equals("0"));
				
				String sCodUsuarioMod = lsAux.get(6);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 18));
				
				String sFechaMod = lsAux.get(7);
				if (!sFechaMod.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaMod);
				}
				
				String sNombreProducto = lsAux.get(8);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNombreProducto, 255));
				
				String sCodProducto = lsAux.get(9);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodProducto, 255));
				
				String sTipoSim = lsAux.get(10);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoSim, 255));
				
				String sMarcaNoAssetisable = lsAux.get(11);
				Integer.parseInt(sMarcaNoAssetisable);
				Assert.assertTrue(sMarcaNoAssetisable.equals("1") || sMarcaNoAssetisable.equals("0"));
				
				String sMarcaOrderable = lsAux.get(12);
				Integer.parseInt(sMarcaOrderable);
				Assert.assertTrue(sMarcaOrderable.equals("1") || sMarcaOrderable.equals("0"));
				
				String sTipoEspecificacion = lsAux.get(13);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoEspecificacion, 255));
				
				String sEstadoProducto = lsAux.get(14);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEstadoProducto, 255));
				
				String sSubtipoProducto = lsAux.get(15);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sSubtipoProducto, 255));
				
				String sTipoProducto = lsAux.get(16);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoProducto, 255));
				
				String sFechaCreaAudit = lsAux.get(17);
				if (!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);
				}
				
				String sFechaModAudit = lsAux.get(18);
				if (!sFechaModAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModAudit);
				}
				
				String sSistemaOrigen = lsAux.get(19);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sSistemaOrigen, 255));
			}
		}
	}
	
	//Test #19
	@Test
	public void TS125417_CRM_Interfaz_LCRM_ItemProducto() throws ParseException, IOException {
		String sName = "_ITEMPRODUCTO_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIdProductoItem = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdProductoItem, 18));
				Assert.assertFalse(sIdProductoItem.isEmpty());
				
				String sNombreProductoItem = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNombreProductoItem, 255));
				
				String sNumeroSecuenciaItem = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroSecuenciaItem, 255));
				
				String sCodProductoHijo = lsAux.get(3);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodProductoHijo, 18));
				
				String sMarcaEsPadre = lsAux.get(4);
				if (!sMarcaEsPadre.isEmpty()) {
					Integer.parseInt(sMarcaEsPadre);
					Assert.assertTrue(sMarcaEsPadre.equals("1") || sMarcaEsPadre.equals("0"));
				}
				
				String sIdproducto = lsAux.get(5);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdproducto, 18));
				Assert.assertFalse(sIdproducto.isEmpty());
				
				String sCantidadItemProducto = lsAux.get(6);
				Integer.parseInt(sCantidadItemProducto);
				
				String sFechaCreaAudit = lsAux.get(7);
				sdfDateFormat = new SimpleDateFormat(sDateFormat);
				sdfDateFormat.parse(sFechaCreaAudit);
				
				String sFechaModAudit = lsAux.get(8);
				sdfDateFormat = new SimpleDateFormat(sDateFormat);
				sdfDateFormat.parse(sFechaModAudit);
				
				String sFechaMod = lsAux.get(9);
				if (!sFechaMod.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaMod);
				}
				
				String sFechaCreacion = lsAux.get(10);
				if (!sFechaCreacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreacion);
				}
				
				String sCodUsuarioAlta = lsAux.get(11);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioAlta, 18));
				
				String sCodUsuarioMod = lsAux.get(12);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 18));
				
				String sMarcasobreescrito = lsAux.get(13);
				if (!sMarcasobreescrito.isEmpty()) {
					Integer.parseInt(sMarcasobreescrito);
					Assert.assertTrue(sMarcasobreescrito.equals("1") || sMarcasobreescrito.equals("0"));
				}
				
				String sCardinalidad = lsAux.get(14);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCardinalidad, 1300));
			}
		}
	}
	
	//Test #20
	@Test
	public void TS125418_CRM_Interfaz_LCRM_Caso() throws ParseException, IOException {
		String sName = "_CASO_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIDCaso = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDCaso, 18));
				
				String sCodCaso = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodCaso, 30));
				
				String sIDIndividuo = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDIndividuo, 18));
				
				String sIDMotivoContacto = lsAux.get(3);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDMotivoContacto, 18));
				
				String sIDOrden = lsAux.get(4);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDOrden, 18));
				
				String sIDCuentaFacturacion = lsAux.get(5);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDCuentaFacturacion, 18));
				
				String sIDCuentaCliente = lsAux.get(6);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDCuentaCliente, 18));
				
				String sIDProductoAdquirido = lsAux.get(7);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDProductoAdquirido, 18));
				
				String sIDProducto = lsAux.get(8);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDProducto, 18));
				
				String sTipoCaso = lsAux.get(9);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoCaso, 40));
				
				String sEstadoCaso = lsAux.get(10);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEstadoCaso, 40));
				
				String sMotivoCaso = lsAux.get(11);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sMotivoCaso, 40));
				
				String sPrioridadCaso = lsAux.get(12);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sPrioridadCaso, 40));
				
				String sMarcaCasoCerrada = lsAux.get(13);
				if (!sMarcaCasoCerrada.isEmpty()) {
					Integer.parseInt(sMarcaCasoCerrada);
					Assert.assertTrue(sMarcaCasoCerrada.equals("1") || sMarcaCasoCerrada.equals("0"));
				}
				
				String sFechaCierreCaso = lsAux.get(14);
				if (!sFechaCierreCaso.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCierreCaso);
				}
				
				String sMarcaCasoEscalado = lsAux.get(15);
				if (!sMarcaCasoEscalado.isEmpty()) {
					Integer.parseInt(sMarcaCasoEscalado);
					Assert.assertTrue(sMarcaCasoEscalado.equals("1") || sMarcaCasoEscalado.equals("0"));
				}
				
				String sFechaCreacion = lsAux.get(16);
				if (!sFechaCreacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreacion);
				}
				
				String scodUsuarioAlta = lsAux.get(17);
				Assert.assertTrue(rsePage.verifyTextMaxSize(scodUsuarioAlta, 18));
				
				String sFechaMod = lsAux.get(18);
				if (!sFechaMod.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaMod);
				}
				
				String sCodUsuarioMod = lsAux.get(19);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 18));
				
				String sNumeroTelefono = lsAux.get(20);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroTelefono, 40));
				
				String sNumeroTelefonoMovil = lsAux.get(21);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroTelefonoMovil, 40));
				
				String sDireccionEmail = lsAux.get(22);
				if (!sDireccionEmail.isEmpty()) sDireccionEmail.contains("@");
				Assert.assertTrue(rsePage.verifyTextMaxSize(sDireccionEmail, 80));
				
				String sFaxContacto = lsAux.get(23);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sFaxContacto, 40));
				
				String sCasoNumeroEstado = lsAux.get(24);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCasoNumeroEstado, 1300));
				
				String sCanalCierre = lsAux.get(25);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCanalCierre, 255));
				
				String sFechaEstimadaCierreCaso = lsAux.get(26);
				if (!sFechaEstimadaCierreCaso.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaEstimadaCierreCaso);
				}
				
				String sImpactoCaso = lsAux.get(27);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sImpactoCaso, 255));
				
				String sMarcaRellamado = lsAux.get(28);
				if (!sMarcaRellamado.isEmpty()) {
					Integer.parseInt(sMarcaRellamado);
					Assert.assertTrue(sMarcaRellamado.equals("1") || sMarcaRellamado.equals("0"));
				}
				
				String sPreferenciaContacto = lsAux.get(29);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sPreferenciaContacto, 255));
				
				String sCantidadAjuste = lsAux.get(30);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCantidadAjuste, 8));
				
				String sFechaAjusteDesde = lsAux.get(31);
				if (!sFechaAjusteDesde.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaAjusteDesde);
				}
				
				String sFechaAjusteHasta = lsAux.get(32);
				if (!sFechaAjusteHasta.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaAjusteHasta);
				}
				
				String sMarcaExcepcion = lsAux.get(33);
				if (!sMarcaExcepcion.isEmpty()) {
					Integer.parseInt(sMarcaExcepcion);
					Assert.assertTrue(sMarcaExcepcion.equals("1") || sMarcaExcepcion.equals("0"));
				}
				
				String sMotivoAjuste = lsAux.get(34);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sMotivoAjuste, 50));
				
				String sMarcaEnGarantia = lsAux.get(35);
				if (!sMarcaEnGarantia.isEmpty()) {
					Integer.parseInt(sMarcaEnGarantia);
					Assert.assertTrue(sMarcaEnGarantia.equals("1") || sMarcaEnGarantia.equals("0"));
				}
				
				String sTipoAjuste = lsAux.get(36);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoAjuste, 50));
				
				String sCanalCaso = lsAux.get(37);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCanalCaso, 40));
				
				String sCodusuarioaAsignado = lsAux.get(38);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodusuarioaAsignado, 18));
				
				String sSubareaAtencion = lsAux.get(39);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sSubareaAtencion, 255));
				
				String sCategoriaCaso = lsAux.get(40);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCategoriaCaso, 255));
				
				String sSubcategoriaCaso = lsAux.get(41);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sSubcategoriaCaso, 255));
				
				String sCodGrupoTrabajo = lsAux.get(42);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodGrupoTrabajo, 255));
				
				String sSubtipoIncidente = lsAux.get(43);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sSubtipoIncidente, 255));
				
				String sSubtipoCaso = lsAux.get(44);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sSubtipoCaso, 255));
				
				String sCodEquipoPropietario = lsAux.get(45);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodEquipoPropietario, 18));
				
				String sFechaDesdeAcuerdo = lsAux.get(46);
				if (!sFechaDesdeAcuerdo.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaDesdeAcuerdo);
				}
				
				String sFechaHastaAcuerdo = lsAux.get(47);
				if (!sFechaHastaAcuerdo.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaHastaAcuerdo);
				}
				
				String sEstadoHito = lsAux.get(48);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEstadoHito, 30));
				
				String sIDCasoPadre = lsAux.get(49);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDCasoPadre, 18));
				
				String sTelefonoOrigenCaso = lsAux.get(50);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTelefonoOrigenCaso, 40));
				
				String sEmailOrigenCaso = lsAux.get(51);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEmailOrigenCaso, 80));
				
				String sIDTipoRegistroCaso = lsAux.get(52);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDTipoRegistroCaso, 18));
				
				String sTemaCaso = lsAux.get(53);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTemaCaso, 255));
				
				String sMarcaNaceCerrado = lsAux.get(54);
				if (!sMarcaNaceCerrado.isEmpty()) {
					Integer.parseInt(sMarcaNaceCerrado);
					Assert.assertTrue(sMarcaNaceCerrado.equals("1") || sMarcaNaceCerrado.equals("0"));
				}
				
				String sMontoAjuste = lsAux.get(55);
				if (!sMontoAjuste.isEmpty()) {
					Double.parseDouble(sMontoAjuste);
				}
				
				String sCodServicio = lsAux.get(56);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodServicio, 18));
				
				String sAreaAtencion = lsAux.get(57);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sAreaAtencion, 255));
				
				String sfechaVencimientoCaso = lsAux.get(58);
				if (!sfechaVencimientoCaso.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sfechaVencimientoCaso);
				}
				
				String sIdCasoExterno = lsAux.get(59);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdCasoExterno, 40));
				
				String sFechaModificacionCaso = lsAux.get(60);
				if (!sFechaModificacionCaso.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModificacionCaso);
				}
				
				String sTipoConcepto = lsAux.get(61);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoConcepto, 255));
				
				String sTipoItem = lsAux.get(62);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoItem, 255));
				
				String sTipoCargo = lsAux.get(63);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoCargo, 255));
				
				String sCodUnidadDeMedida = lsAux.get(64);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUnidadDeMedida, 255));
				
				String sFechaCreaAudit = lsAux.get(65);
				if (!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);
				}
				
				String sFechaModAudit = lsAux.get(66);
				if (!sFechaModAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModAudit);
				}
				
				String sIDAcuerdoServicio = lsAux.get(67);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDAcuerdoServicio, 18));
				
				String sResolucionIncidente = lsAux.get(68);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sResolucionIncidente, 1300));
				
				String sDescripcionIncidente = lsAux.get(69);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sDescripcionIncidente, 1300));
				
				String sCantidadRellamados = lsAux.get(70);
				if (!sCantidadRellamados.isEmpty()) {
					Integer.parseInt(sCantidadRellamados);
				}
				
				String sNumeroLinea = lsAux.get(71);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroLinea, 40));
				
				String sNumeroDocumentoPortacion = lsAux.get(72);
				if (!sNumeroDocumentoPortacion.isEmpty()) {
					Integer.parseInt(sNumeroDocumentoPortacion);
				}
				
				String sTipoDocumentoPortacion = lsAux.get(73);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoDocumentoPortacion, 510));
				
				String sCodMercadoDonantePortacion = lsAux.get(74);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodMercadoDonantePortacion, 20));
				
				String sFechaVentanaPortacion = lsAux.get(75);
				if (!sFechaVentanaPortacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaVentanaPortacion);
				}
				
				String sCantidadLineasPortacion = lsAux.get(76);
				if (!sCantidadLineasPortacion.isEmpty()) {
					Integer.parseInt(sCantidadLineasPortacion);
				}
				
				String sMercadoDonantePortacion = lsAux.get(77);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sMercadoDonantePortacion, 510));
				
				String sSubtipoMovimientoPortacion = lsAux.get(78);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sSubtipoMovimientoPortacion, 10));
				
				String sTipoMovimientoPortacion = lsAux.get(79);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoMovimientoPortacion, 8));
				
				String sRedPortacion = lsAux.get(80);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sRedPortacion, 510));
				
				String sCodNRNPortacion = lsAux.get(81);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodNRNPortacion, 12));
				
				String sCodIDOoperadoraReceptora = lsAux.get(82);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodIDOoperadoraReceptora, 20));
				
				String sCodCausaRechazoPortacion = lsAux.get(83);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodCausaRechazoPortacion, 20));
				
				String sMotivoRechazoPortacion = lsAux.get(84);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sMotivoRechazoPortacion, 510));
				
				String sCantidadCasosAfectados = lsAux.get(85);
				if (!sCantidadCasosAfectados.isEmpty()) {
					Integer.parseInt(sCantidadCasosAfectados);
				}
				
				String sMarcaEquipoReparado = lsAux.get(86);
				if (!sMarcaEquipoReparado.isEmpty()) {
					Integer.parseInt(sMarcaEquipoReparado);
				}
				
				String sNumeroComprobante = lsAux.get(87);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroComprobante, 40));
				
				String sMarcaReclamo = lsAux.get(88);
				if (!sMarcaReclamo.isEmpty()) {
					Integer.parseInt(sMarcaReclamo);
					Assert.assertTrue(sMarcaReclamo.equals("1") || sMarcaReclamo.equals("0"));
				}
				
				String sPINTarjetaPrepago = lsAux.get(89);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sPINTarjetaPrepago, 32));
				
				String sNumeroLote = lsAux.get(90);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroLote, 24));
			}
		}
	}
	
	//Test #21
	@Test
	public void TS125419_CRM_Interfaz_LCRM_OrdenItemHistoria() throws ParseException, IOException {
		String sName = "_ORDENITEMHISTORIA_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIDOrdenItemHistoria = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDOrdenItemHistoria, 18));
				
				String sIDOrdenItem = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDOrdenItem, 18));
				
				String sCodUsuarioMod = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 18));
				
				String sFechaCreacion = lsAux.get(3);
				if (!sFechaCreacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreacion);
				}
				
				String sCampoOrdenItem = lsAux.get(4);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCampoOrdenItem, 255));
				
				String sAntiguoValor = lsAux.get(5);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sAntiguoValor, 255));
				
				String sNuevoValor = lsAux.get(6);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNuevoValor, 255));
				
				String sFechaCreaAudit = lsAux.get(7);
				if (!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);
				}
				
				String sFechaModAudit = lsAux.get(8);
				if (!sFechaModAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModAudit);
				}
			}
		}
	}
	
	//Test #22
	@Test
	public void TS125420_CRM_Interfaz_LCRM_MedioDePago() throws ParseException, IOException {
		String sName = "_MEDIODEPAGO_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIDMedioDePago = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDMedioDePago, 18));
				
				String sIDCuentaFacturacion = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDCuentaFacturacion, 18));
				
				String sNombreEntidadTC = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNombreEntidadTC, 80));
				
				String sAlias = lsAux.get(3);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sAlias, 55));
				
				String sAnioVtoTarjeta = lsAux.get(4);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sAnioVtoTarjeta, 4));
				if (!sAnioVtoTarjeta.isEmpty()) {
					int iYear = Calendar.getInstance().get(Calendar.YEAR) - 1;
					Assert.assertTrue(Integer.parseInt(sAnioVtoTarjeta) >= iYear);
				}
				
				String sUltimosDigitosTarjeta = lsAux.get(5);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sUltimosDigitosTarjeta, 20));
				
				String sCodEntidadFinanciera = lsAux.get(6);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodEntidadFinanciera, 16));
				
				String sNumeroDocumento = lsAux.get(7);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroDocumento, 8));
				
				String sNroCuentaBancaria = lsAux.get(8);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNroCuentaBancaria, 31));
				
				String sCBU = lsAux.get(9);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCBU, 22));
				
				String sNroTarjeta = lsAux.get(10);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNroTarjeta, 16));
				
				String sTipoDocumento = lsAux.get(11);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoDocumento, 9));
				if (!sTipoDocumento.isEmpty()) {
					Assert.assertTrue(rsePage.documentType(sTipoDocumento));
				}
				
				String sNombreBanco = lsAux.get(12);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNombreBanco, 255));
				
				String sTipoCtaBancaria = lsAux.get(13);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoCtaBancaria, 255));
				
				String sMesVtoTarjeta = lsAux.get(14);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sMesVtoTarjeta, 2));
				
				String sCodUsuarioMod = lsAux.get(15);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 18));
				
				String sCodUsuarioAlta = lsAux.get(16);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioAlta, 18));
				
				String sMarcaMedioDePagoPpal = lsAux.get(17);
				if (!sMarcaMedioDePagoPpal.isEmpty()) {
					Integer.parseInt(sMarcaMedioDePagoPpal);
					Assert.assertTrue(sMarcaMedioDePagoPpal.equals("1") || sMarcaMedioDePagoPpal.equals("0"));
				}
				
				String sTipoMedioDePago = lsAux.get(18);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoMedioDePago, 255));
				
				String sTipoTarjeta = lsAux.get(19);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoTarjeta, 255));
				
				String sCodMedioDePago = lsAux.get(20);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodMedioDePago, 80));
				
				String sFechaCrea = lsAux.get(21);
				if (!sFechaCrea.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCrea);
				}
				
				String sFechaMod = lsAux.get(22);
				if (!sFechaMod.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaMod);
				}
				
				String sFechaCreaAudit = lsAux.get(23);
				if (!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);
				}
				
				String sFechaModAudit = lsAux.get(24);
				if (!sFechaModAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModAudit);
				}
			}
		}
	}
	
	//Test #23
	@Test
	public void TS125421_CRM_Interfaz_LCRM_Usuario() throws ParseException, IOException {
		String sName = "_USUARIO_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIdUsuario = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdUsuario, 18));
				
				String sMarcaActivoUsuario = lsAux.get(1);
				if (!sMarcaActivoUsuario.isEmpty()) {
					Integer.parseInt(sMarcaActivoUsuario);
					Assert.assertTrue(sMarcaActivoUsuario.equals("1") || sMarcaActivoUsuario.equals("0"));
				}
				
				String sAliasUsuario = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sAliasUsuario, 8));
				
				String sNumeroTelefonoMovil = lsAux.get(3);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroTelefonoMovil, 40));
				
				String sEmpresa = lsAux.get(4);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEmpresa, 80));
				
				String sIDIndividuo = lsAux.get(5);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDIndividuo, 18));
				
				String sGrupoNotificacion = lsAux.get(6);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sGrupoNotificacion, 40));
				
				String sCodUsuarioAprobador = lsAux.get(7);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioAprobador, 18));
				
				String sAreaOrganizacion = lsAux.get(8);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sAreaOrganizacion, 80));
				
				String sDivisionOrganizacion = lsAux.get(9);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sDivisionOrganizacion, 80));
				
				String sDireccionEmail = lsAux.get(10);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sDireccionEmail, 80));
				
				String sCodEmpleado = lsAux.get(11);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodEmpleado, 20));
				
				String sInterno = lsAux.get(12);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sInterno, 40));
				
				String sNumeroFax = lsAux.get(13);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroFax, 40));
				
				String sMarcaRecibeMail = lsAux.get(14);
				if (!sMarcaRecibeMail.isEmpty()) {
					Integer.parseInt(sMarcaRecibeMail);
					Assert.assertTrue(sMarcaRecibeMail.equals("1") || sMarcaRecibeMail.equals("0"));
				}
				
				String sCodUsuarioAdministrador = lsAux.get(15);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioAdministrador, 18));
				
				String sNombre = lsAux.get(16);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNombre, 121));
				
				String sCodUsuarioComunidad = lsAux.get(17);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioComunidad, 40));
				
				String sNumeroTelefono = lsAux.get(18);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroTelefono, 40));
				
				String sIDUsuarioRol = lsAux.get(19);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDUsuarioRol, 18));
				
				String sCodAccesoUsuario = lsAux.get(20);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodAccesoUsuario, 80));
				
				String sFechaCreaAudit = lsAux.get(21);
				Assert.assertFalse(sFechaCreaAudit.isEmpty());
				sdfDateFormat = new SimpleDateFormat(sDateFormat);
				sdfDateFormat.parse(sFechaCreaAudit);
				
				String sFechaModAudit = lsAux.get(22);
				Assert.assertFalse(sFechaModAudit.isEmpty());
				sdfDateFormat = new SimpleDateFormat(sDateFormat);
				sdfDateFormat.parse(sFechaModAudit);
				
				String sFechaCreacion = lsAux.get(23);
				if (!sFechaCreacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreacion);
				}
				
				String sFechaMod = lsAux.get(24);
				if (!sFechaMod.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaMod);
				}
				
				String sIDPerfil = lsAux.get(25);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDPerfil, 18));
				
				String sLegajoUsuario = lsAux.get(26);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sLegajoUsuario, 1024));
			}
		}
	}
	
	//Test #24
	@Test
	public void TS125422_CRM_Interfaz_LCRM_OrdenHistoria() throws ParseException, IOException {
		String sName = "_ORDENHISTORIA_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIDOrdenHistoria = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDOrdenHistoria, 18));
				
				String sIDOrden = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDOrden, 18));
				
				String sCodUsuarioMod = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 18));
				
				String sFechaCreacion = lsAux.get(3);
				if (!sFechaCreacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreacion);
				}
				
				String sCampoOrden = lsAux.get(4);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCampoOrden, 255));
				
				String sAntiguoValor = lsAux.get(5);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sAntiguoValor, 255));
				
				String sNuevoValor = lsAux.get(6);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNuevoValor, 255));
				
				String sFechaCreaAudit = lsAux.get(7);
				if (!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);
				}
				
				String sFechaModAudit = lsAux.get(8);
				if (!sFechaModAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModAudit);
				}
			}
		}
	}
	
	//Test #25
	@Test
	public void TS125423_CRM_Interfaz_LCRM_ProductoCaracteristica() throws ParseException, IOException {
		String sName = "_PRODUCTOCARACTERISTICA_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIDProducto = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDProducto, 36));
				
				String sIDCaracteristica = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDCaracteristica, 36));
				
				String sCodUsuarioCrea = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioCrea, 36));
				
				String sCodUsuarioMod = lsAux.get(3);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 36));
				
				String sFechaCrea = lsAux.get(4);
				if (!sFechaCrea.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCrea);
				}
				
				String sFechaMod = lsAux.get(5);
				if (!sFechaMod.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaMod);
				}
				
				String sFechaCreaAudit = lsAux.get(6);
				if (!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);
				}
				
				String sFechaModAudit = lsAux.get(7);
				if (!sFechaModAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModAudit);
				}
				
				String sTipoValor = lsAux.get(8);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoValor, 255));
				
				String sValor = lsAux.get(9);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sValor, 255));
				
				String sMarcaConfigurable = lsAux.get(10);
				if (!sMarcaConfigurable.isEmpty()) {
					Integer.parseInt(sMarcaConfigurable);
					Assert.assertTrue(sMarcaConfigurable.equals("1") || sMarcaConfigurable.equals("0"));
				}
			}
		}
	}
	
	//Test #26
	@Test
	public void TS125424_CRM_Interfaz_LCRM_GrupoDeTrabajo() throws ParseException, IOException {
		String sName = "_GRUPODETRABAJO_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIdGrupoDeTrabajo = lsAux.get(0);
				System.out.println(sIdGrupoDeTrabajo);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdGrupoDeTrabajo, 18));
				
				String sCodPropietario = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodPropietario, 18));
				
				String sFechaCreacion = lsAux.get(2);
				if (!sFechaCreacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreacion);
				}
				
				String sCodUsuarioAlta = lsAux.get(3);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioAlta, 18));
				
				String sFechaMod = lsAux.get(4);
				if (!sFechaMod.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaMod);
				}
				
				String sCodUsuarioMod = lsAux.get(5);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 18));
				
				String sMarcaActivo = lsAux.get(6);
				if (!sMarcaActivo.isEmpty()) {
					Integer.parseInt(sMarcaActivo);
					Assert.assertTrue(sMarcaActivo.equals("1") || sMarcaActivo.equals("0"));
				}
				
				String sPerfilAdministrador = lsAux.get(7);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sPerfilAdministrador, 1300));
				
				String sCodAdministrador = lsAux.get(8);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodAdministrador, 18));
				
				String sNombreGrupoDeTrabajo = lsAux.get(9);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNombreGrupoDeTrabajo, 255));
				
				String sIDPuntoDeVenta = lsAux.get(10);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDPuntoDeVenta, 18));
				
				String sNumeroGrupoDeTrabajo = lsAux.get(11);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroGrupoDeTrabajo, 30));
				
				String sFechaCreaAudit = lsAux.get(12);
				if (!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);
				}
				
				String sFechaModAudit = lsAux.get(13);
				if (!sFechaModAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModAudit);
				}
				
				String sIDGrupoDeTrabajoPadre = lsAux.get(14);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDGrupoDeTrabajoPadre, 18));
			}
		}
	}
	
	//Test #27
	@Test
	public void TS125425_CRM_Interfaz_LCRM_Legacy_Actions() throws ParseException, IOException {
		String sName = "_LEGACY_ACTIONS_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sFuncion = lsAux.get(0); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sFuncion, 32)); 
				 
				String sDescripcionDeFuncion = lsAux.get(1); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sDescripcionDeFuncion, 255)); 
				 
				String sIdioma = lsAux.get(2); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdioma, 2));
			}
		}
	}
	
	//Test #28
	@Test
	public void TS125426_CRM_Interfaz_LCRM_ExportEpcEcomm() throws ParseException, IOException {
		String sName = "_EXPORTEPCECOMM_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIdPricebookEntry = lsAux.get(0); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdPricebookEntry, 18)); 
				 
				String sNMU = lsAux.get(1); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNMU, 200)); 
				 
				String sSHDESPlan = lsAux.get(2); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sSHDESPlan, 5));
			}
		}
	}
			
	//Test #29
	@Test
	public void TS125427_CRM_Interfaz_LCRM_Direccion() throws ParseException, IOException {
		String sName = "_DIRECCION_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIDDireccion = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDDireccion, 16));
				
				String sIDCuentaCliente = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDCuentaCliente, 18));
				
				String sCiudadNombre = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCiudadNombre, 40));
				
				String sPais = lsAux.get(3);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sPais, 80));
				
				String sCodPais = lsAux.get(4);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodPais, 10));
				
				String sLatitud = lsAux.get(5);
				if (!sLatitud.isEmpty()) {
					Double.parseDouble(sLatitud);
				}
				
				String sLongitud = lsAux.get(6);
				if (!sLongitud.isEmpty()) {
					Double.parseDouble(sLongitud);
				}
				
				String sCodigoPostal = lsAux.get(7);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodigoPostal, 20));
				
				String sProvincia = lsAux.get(8);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sProvincia, 80));
				
				String sCodigoProvincia = lsAux.get(9);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodigoProvincia, 10));
				
				String sCalleNombre = lsAux.get(10);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCalleNombre, 255));
				
				String sFechaCreaAudit = lsAux.get(11);
				if (!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);
				}
				
				String sFechaModAudit = lsAux.get(12);
				if (!sFechaModAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModAudit);
				}
			}
		}
	}
	
	//Test #30
	@Test
	public void TS125428_CRM_Interfaz_LCRM_LegacyDefaultUser() throws ParseException, IOException {
		String sName = "_LEGACY_DEFAULT_USER_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIDDeUsuario = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDDeUsuario, 50));
				
				String sNombre = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNombre, 50));
				
				String sApellido = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sApellido, 50));
				
				String sEmail =lsAux.get(3);
				if (!sEmail.isEmpty()) {
					sEmail.contains("@");
					Assert.assertTrue(rsePage.verifyTextMaxSize(sEmail, 241));
				}
				
				String sTelefono =lsAux.get(4);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTelefono, 20));
				
				String sDepartamento = lsAux.get(5);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sDepartamento, 100));
				
				String sGrupoDeUsuario = lsAux.get(6);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sGrupoDeUsuario, 50));
				
				String sGerenteACargoDelusuario = lsAux.get(7);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sGerenteACargoDelusuario, 50));
				
				String sMarcaDeBorrado = lsAux.get(8);
				if (!sMarcaDeBorrado.isEmpty()) {
					Integer.parseInt(sMarcaDeBorrado);
					Assert.assertTrue(sMarcaDeBorrado.equals("1") || sMarcaDeBorrado.equals("0"));
				}
				
				String sMarcaDeInactivo = lsAux.get(9);
				if (!sMarcaDeInactivo.isEmpty()) {
					Integer.parseInt(sMarcaDeInactivo);
					Assert.assertTrue(sMarcaDeInactivo.equals("1") || sMarcaDeInactivo.equals("0"));
				}
				
				String sTipoDeUsuario = lsAux.get(10);
				Integer.parseInt(sTipoDeUsuario);
				
				String sValidoDesde = lsAux.get(11);
				if (!sValidoDesde.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormatYMD);
					sdfDateFormat.parse(sValidoDesde);
				}
				
				String sValidoHasta = lsAux.get(12);
				if (!sValidoHasta.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormatYMD);
					sdfDateFormat.parse(sValidoHasta);
				}
			}
		}
	}
	
	//Test #31
	@Test
	public void TS125429_CRM_Interfaz_LCRM_PrecioVariable() throws ParseException, IOException {
		String sName = "_PRECIOVARIABLE_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIDPrecioVariable = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDPrecioVariable, 36));
				
				String sTipoAjuste = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoAjuste, 510));
				
				String sTipoMoneda = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoMoneda, 510));
				
				String sFechaCreacion = lsAux.get(3);
				if (!sFechaCreacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreacion);
				}
				
				String sFechaMod = lsAux.get(4);
				if (!sFechaMod.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaMod);
				}
				
				String sFechaCreaAudit = lsAux.get(5);
				if (!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);
				}
				
				String sFechaModAudit = lsAux.get(6);
				if (!sFechaModAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModAudit);
				}
			}
		}
	}
	
	//Test #32
	@Test
	public void TS125430_CRM_Interfaz_LCRM_Categoria() throws ParseException, IOException {
		String sName = "_CATEGORIA_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIDCategoria = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDCategoria, 200));
				
				String sCodCategoria = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodCategoria, 200));
				
				String sNombreCategoria = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNombreCategoria, 200));
				
				String sFechaCreaAudit = lsAux.get(3);
				if (!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);
				}
				
				String sFechaModAudit = lsAux.get(4);
				if (!sFechaModAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModAudit);
				}
				
				String sFechaCrea = lsAux.get(5);
				if (!sFechaCrea.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCrea);
				}
				
				String sFechaMod = lsAux.get(6);
				if (!sFechaMod.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaMod);
				}
				
				String sCodUsuarioMod = lsAux.get(7);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 18));
				
				String sCodUsuarioAlta = lsAux.get(8);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioAlta, 18));
			}
		}
	}
	
	//Test #33
	@Test
	public void TS125431_CRM_Interfaz_LCRM_CaracteristicaValor() throws ParseException, IOException {
		String sName = "_CARACTERISTICAVALOR_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIDCaracteristicaValor = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDCaracteristicaValor, 36));
				
				String sIDCaracteristica = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDCaracteristica, 36));
				
				String sTipoValor = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoValor, 510));
				
				String sCodValor = lsAux.get(3);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodValor, 1300));
				
				String sValor = lsAux.get(4);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sValor, 160));
				
				String sFechaCreacion = lsAux.get(5);
				if (!sFechaCreacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreacion);
				}
				
				String sFechaMod = lsAux.get(6);
				if (!sFechaMod.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaMod);
				}
				
				String sFechaCreaAudit = lsAux.get(7);
				if (!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);
				}
				
				String sFechaModAudit = lsAux.get(8);
				if (!sFechaModAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModAudit);
				}
			}
		}
	}
	
	//Test #34
	@Test
	public void TS125432_CRM_Interfaz_LCRM_Caracteristica() throws ParseException, IOException {
		String sName = "_CARACTERISTICA_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIDCaracteristica = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDCaracteristica, 36));
				
				String sNombreCaracteristica = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNombreCaracteristica, 160));
				
				String sCodigoCaracteristica = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodigoCaracteristica, 160));
				
				String sIDCategoria = lsAux.get(3);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDCategoria, 36));
				
				String sFechaCrea = lsAux.get(4);
				if (!sFechaCrea.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCrea);
				}
				
				String sFechaMod = lsAux.get(5);
				if (!sFechaMod.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaMod);
				}
				
				String sFechaCreaAudit = lsAux.get(6);
				if (!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);
				}
				
				String sFechaModAudit = lsAux.get(7);
				if (!sFechaModAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModAudit);
				}
				
				String sTipoDato = lsAux.get(8);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoDato, 510));
			}
		}
	}
	
	//Test #35
	@Test
	public void TS125433_CRM_Interfaz_LCRM_MotivoContacto() throws ParseException, IOException {
		String sName = "_MOTIVOCONTACTO_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIDMotivoContacto = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDMotivoContacto, 18));
				
				String sIDPropietario = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDPropietario, 18));
				
				String sMotivoContacto = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sMotivoContacto, 80));
				
				String sIDTipoMotivoContacto = lsAux.get(3);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDTipoMotivoContacto, 18));
				
				String sCodUsuarioAlta = lsAux.get(4);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioAlta, 18));
				
				String sCodUsuarioMod = lsAux.get(5);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 18));
				
				String sFechaMod = lsAux.get(6);
				if (!sFechaMod.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaMod);
				}
				
				String sFechaCreacion = lsAux.get(7);
				if (!sFechaCreacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreacion);
				}
				
				String sDescripcion = lsAux.get(8);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sDescripcion, 255));
				
				String sConfiguracion = lsAux.get(9);
				if (!sConfiguracion.isEmpty()) {
					Integer.parseInt(sConfiguracion);
				}
				
				String sMarcaCobertura = lsAux.get(10);
				if (!sMarcaCobertura.isEmpty()) {
					Integer.parseInt(sMarcaCobertura);
					Assert.assertTrue(sMarcaCobertura.equals("1") || sMarcaCobertura.equals("0"));
				}
				
				String sFechaCreaAudit = lsAux.get(11);
				if (!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);
				}
				
				String sFechaModAudit = lsAux.get(12);
				if (!sFechaModAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModAudit);
				}
			}
		}
	}
	
	//Test #36
	@Test
	public void TS125434_CRM_Interfaz_LCRM_VENDEDORSP() throws ParseException, IOException {
		String sName = "_VENDEDORSP_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sSMDE_ICC_ID = lsAux.get(0);
				Assert.assertFalse(sSMDE_ICC_ID.isEmpty());
				Assert.assertTrue(rsePage.verifyTextMaxSize(sSMDE_ICC_ID, 50));
				
				String sSMDE_IMSI = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sSMDE_IMSI, 50));
				
				String sSMDE_KI = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sSMDE_KI, 50));
				
				String sSMDE_NUMERO_LINEA = lsAux.get(3);
				Assert.assertFalse(sSMDE_NUMERO_LINEA.isEmpty());
				Assert.assertTrue(rsePage.verifyTextMaxSize(sSMDE_NUMERO_LINEA, 50));
				
				String sSMDE_PLAN_REPRO = lsAux.get(4);
				Assert.assertFalse(sSMDE_PLAN_REPRO.isEmpty());
				Assert.assertTrue(rsePage.verifyTextMaxSize(sSMDE_PLAN_REPRO, 20));
				
				String sSMDE_NMU = lsAux.get(5);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sSMDE_NMU, 20));
				
				String sSMCA_FECHA_CARGA = lsAux.get(6);
				sdfDateFormat = new SimpleDateFormat(sDateFormat);
				sdfDateFormat.parse(sSMCA_FECHA_CARGA);
				
				String sSMCA_FECHA_PROCESADO = lsAux.get(7);
				sdfDateFormat = new SimpleDateFormat(sDateFormat);
				sdfDateFormat.parse(sSMCA_FECHA_PROCESADO);
				
				String sAGEN_VENDEDOR = lsAux.get(8);
				Assert.assertFalse(sAGEN_VENDEDOR.isEmpty());
				Assert.assertTrue(rsePage.verifyTextMaxSize(sAGEN_VENDEDOR, 50));
				
				String sAGEN_ACCOUNT_ID = lsAux.get(9);
				Assert.assertFalse(sAGEN_ACCOUNT_ID.isEmpty());
				Assert.assertTrue(rsePage.verifyTextMaxSize(sAGEN_ACCOUNT_ID, 50));
				
				String sAGEN_CODIGO_PUNTO_VENTA = lsAux.get(10);
				if(!sAGEN_CODIGO_PUNTO_VENTA.isEmpty()) {
					Integer.parseInt(sAGEN_CODIGO_PUNTO_VENTA);
				}
				
				String sAGEN_DESCRIPCION_PUNTO_VENTA = lsAux.get(11);
				Assert.assertFalse(sAGEN_DESCRIPCION_PUNTO_VENTA.isEmpty());
				Assert.assertTrue(rsePage.verifyTextMaxSize(sAGEN_DESCRIPCION_PUNTO_VENTA, 20));
				
				String sAGEN_CODIGO_CANAL = lsAux.get(12);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sAGEN_CODIGO_CANAL, 10));
				
				String sAGEN_CANAL = lsAux.get(13);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sAGEN_CANAL, 50));
				
				String sAGEN_RAZON_SOCIAL = lsAux.get(14);
				Assert.assertFalse(sAGEN_RAZON_SOCIAL.isEmpty());
				Assert.assertTrue(rsePage.verifyTextMaxSize(sAGEN_RAZON_SOCIAL, 50));
				
				String sAGEN_TIPO_PUNTO_VENTA = lsAux.get(15);
				Assert.assertFalse(sAGEN_TIPO_PUNTO_VENTA.isEmpty());
				Assert.assertTrue(rsePage.verifyTextMaxSize(sAGEN_TIPO_PUNTO_VENTA, 50));
				
				String sAGEN_CODIGO_DEPOSITO = lsAux.get(16);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sAGEN_CODIGO_DEPOSITO, 50));
				
				String sAGEN_DOMICILIO_LOCALIDAD = lsAux.get(17);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sAGEN_DOMICILIO_LOCALIDAD, 50));
				
				String sAGEN_DOMICILIO_PROVINCIA = lsAux.get(18);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sAGEN_DOMICILIO_PROVINCIA, 50));
				
				String sFECHA_ACTUALIZACION = lsAux.get(19);
				if (!sFECHA_ACTUALIZACION.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFECHA_ACTUALIZACION);
				}
			}
		}
	}
	
	
	//Test #37
	@Test
	public void TS125435_CRM_Interfaz_LCRM_CuentaFacturacionHistoria() throws ParseException, IOException {
		String sName = "_CUENTAFACTURACIONHISTORIA_";
						
		rsePage.checkName(sName);
						
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {					
			for (List<String> lsAux : sList) { 
				String sIDCuentaFacturacionHistoria = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDCuentaFacturacionHistoria, 18));
				
				String sIDCuentaFacturacion = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIDCuentaFacturacion, 18));
				
				String sCodUsuarioMod = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 18));
				
				String sFechaCreacion = lsAux.get(3);
				if (!sFechaCreacion.isEmpty()) { 
					sdfDateFormat = new SimpleDateFormat(sDateFormat); 
					sdfDateFormat.parse(sFechaCreacion); 
				}
				
				String sCampoCuentaFacturacion = lsAux.get(4);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCampoCuentaFacturacion, 255));
				
				String sAntiguoValor = lsAux.get(5);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sAntiguoValor, 255));
				
				String sNuevoValor = lsAux.get(6);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNuevoValor, 255));
				
				String sFechaCreaAudit = lsAux.get(7);
				if (!sFechaCreaAudit.isEmpty()) { 
					sdfDateFormat = new SimpleDateFormat(sDateFormat); 
					sdfDateFormat.parse(sFechaCreaAudit); 
				}
				
				String sFechaModAudit = lsAux.get(8);
				if (!sFechaModAudit.isEmpty()) { 
					sdfDateFormat = new SimpleDateFormat(sDateFormat); 
					sdfDateFormat.parse(sFechaModAudit); 
				}
			}
		}
	}
	
	//Test #38
	@Test
	public void TS125436_CRM_Interfaz_LCRM_LEGACY_ROLE_PERMISSION() throws ParseException, IOException {
		String sName = "_LEGACY_ROLE_PERMISSION_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sPerfil = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sPerfil, 100));
				
				String sPermisos = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sPermisos, 100));
				
				String sGrupoDePermisos = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sGrupoDePermisos, 20));
				
				String sValorDesde = lsAux.get(3);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sValorDesde, 50));
				
				String sValorHasta = lsAux.get(4);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sValorHasta, 50));
			}
		}
	}
	
	//Test #39
	@Test	
	public void TS125437_CRM_Interfaz_LCRM_IndividuoHistoria() throws ParseException, IOException {
		String sName = "_INDIVIDUOHISTORIA_";
									
		rsePage.checkName(sName);
									
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {								
			for (List<String> lsAux : sList) { 
				String sIdIndividuoHistoria = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdIndividuoHistoria, 18));
					
				String sIdIndividuo = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdIndividuo, 18));
						
				String sCodUsuarioMod = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 18));
						
				String sFechaCreacion = lsAux.get(3); 
				if (!sFechaCreacion.isEmpty()) { 
					sdfDateFormat = new SimpleDateFormat(sDateFormat); 
					sdfDateFormat.parse(sFechaCreacion); 
				}
						
				String sCampoContacto = lsAux.get(4);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCampoContacto, 255));
						
				String sAntiguoValor = lsAux.get(5);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sAntiguoValor, 255));
						
				String sNuevoValor = lsAux.get(6);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNuevoValor, 255));
						
				String sFechaCreaAudit = lsAux.get(7); 
				if (!sFechaCreaAudit.isEmpty()) { 
					sdfDateFormat = new SimpleDateFormat(sDateFormat); 
					sdfDateFormat.parse(sFechaCreaAudit); 
				}
				
				String sFechaModAudit = lsAux.get(8); 
				if (!sFechaModAudit.isEmpty()) { 
					sdfDateFormat = new SimpleDateFormat(sDateFormat); 
					sdfDateFormat.parse(sFechaModAudit); 
				}
			}
		}
	}
		
	//Test #40
	@Test	
	public void TS125438_CRM_Interfaz_LCRM_ProductoHistoria() throws ParseException, IOException {
		String sName = "_PRODUCTOHISTORIA_";
								
		rsePage.checkName(sName);
								
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {							
			for (List<String> lsAux : sList) { 
				String sIdProductoHistoria = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdProductoHistoria, 18));
				
				String sIdProducto = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdProducto, 18));
					
				String sCodUsuarioMod = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 18));
					
				String sFechaCreacion = lsAux.get(3); 
				if (!sFechaCreacion.isEmpty()) { 
					sdfDateFormat = new SimpleDateFormat(sDateFormat); 
					sdfDateFormat.parse(sFechaCreacion); 
				}
					
				String sCampoProducto = lsAux.get(4);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCampoProducto, 255));
					
				String sAntiguoValor = lsAux.get(5);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sAntiguoValor, 255));
					
				String sNuevoValor = lsAux.get(6);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNuevoValor, 255));
					
				String sFechaCreaAudit = lsAux.get(7); 
				if (!sFechaCreaAudit.isEmpty()) { 
					sdfDateFormat = new SimpleDateFormat(sDateFormat); 
					sdfDateFormat.parse(sFechaCreaAudit); 
				}
					
				String sFechaModAudit = lsAux.get(8); 
				if (!sFechaModAudit.isEmpty()) { 
					sdfDateFormat = new SimpleDateFormat(sDateFormat); 
					sdfDateFormat.parse(sFechaModAudit); 
			   }
			}
		}
	}
		
	//Test #41
	@Test
	public void TS125439_CRM_Interfaz_LCRM_Legacy_Role() throws ParseException, IOException {
		String sName = "_Q_LEGACY_ROLE_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sPerfil = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sPerfil, 100));
				
				String sDescripcionDelPerfil = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sDescripcionDelPerfil, 132));
				
				String sIdioma = lsAux.get(2);
				System.out.println("sIdioma: " + sIdioma);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdioma, 2));
				
				String sMarcaDeBorrado = lsAux.get(3);
				if (!sMarcaDeBorrado.isEmpty()) {
					Integer.parseInt(sMarcaDeBorrado);
					Assert.assertTrue(sMarcaDeBorrado.equals("1") || sMarcaDeBorrado.equals("0"));
				}
			}
		}
	}
	
	//Test #42
	@Test	
	public void TS125440_CRM_Interfaz_LCRM_CasoHistoria() throws ParseException, IOException {
		String sName = "_CASOHISTORIA_";
							
		rsePage.checkName(sName);
							
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) { 
				String sIdCasoHistoria = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdCasoHistoria, 18));
				
				String sIdCaso = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdCaso, 18));
				
				String sCodUsuarioMod = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 18));
				
				String sFechaCreacion = lsAux.get(3); 
				if (!sFechaCreacion.isEmpty()) { 
					sdfDateFormat = new SimpleDateFormat(sDateFormat); 
					sdfDateFormat.parse(sFechaCreacion); 
				}
				
				String sCampoCaso = lsAux.get(4);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCampoCaso, 255));
				
				String sAntiguoValor = lsAux.get(5);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sAntiguoValor, 255));
				
				String sNuevoValor = lsAux.get(6);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNuevoValor, 255));
				
				String sFechaCreaAudit = lsAux.get(7); 
				if (!sFechaCreaAudit.isEmpty()) { 
					sdfDateFormat = new SimpleDateFormat(sDateFormat); 
					sdfDateFormat.parse(sFechaCreaAudit); 
				}
				
				String sFechaModAudit = lsAux.get(8); 
				if (!sFechaModAudit.isEmpty()) { 
					sdfDateFormat = new SimpleDateFormat(sDateFormat); 
					sdfDateFormat.parse(sFechaModAudit); 
				}
			}
		}
	}
		
	//Test #43
	@Test
	public void TS125441_CRM_Interfaz_LCRM_CuentaPartner() throws ParseException, IOException {
		String sName = "_CUENTAPARTNER_";
						
		rsePage.checkName(sName);
						
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) { 
				String sIdCuentaPartner = lsAux.get(0); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdCuentaPartner, 18)); 
				Assert.assertFalse(sIdCuentaPartner.isEmpty());
			
				
				String sCodcuenta = lsAux.get(1); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodcuenta, 255)); 
				Assert.assertFalse(sCodcuenta.isEmpty());
			
			
				String sFechaFundacion = lsAux.get(2); 
				if (!sFechaFundacion.isEmpty()) { 
					sdfDateFormat = new SimpleDateFormat(sDateFormat); 
					sdfDateFormat.parse(sFechaFundacion); 
				}
				
				String sNumeroFax = lsAux.get(3); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroFax, 40));
				
				String sRazonSocial = lsAux.get(4); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sRazonSocial, 0)); 
				Assert.assertFalse(sRazonSocial.isEmpty());
				
				String sNumeroTelefonoPpal = lsAux.get(5); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroTelefonoPpal, 40)); 
				
				String sNumeroTelefonoAlternativo = lsAux.get(6); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNumeroTelefonoAlternativo, 40));
				
				String sEstado = lsAux.get(7); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEstado, 255));
				
				String sCUIT = lsAux.get(8); 
				Integer.parseInt(sCUIT); 
				
				String sFechaModEmail = lsAux.get(9); 
				if (!sFechaModEmail.isEmpty()) { 
					sdfDateFormat = new SimpleDateFormat(sDateFormat); 
					sdfDateFormat.parse(sFechaModEmail); 
				}
				
				String sIndustria = lsAux.get(10); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIndustria, 40));
				
				String sEmail = lsAux.get(11);
				if (!sEmail.isEmpty()) sEmail.contains("@");
					Assert.assertTrue(rsePage.verifyTextMaxSize(sEmail, 255));
						
				String sCodPersonaContacto = lsAux.get(12); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodPersonaContacto, 18));
				
				String sCodCuentaOrigen = lsAux.get(13); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodCuentaOrigen, 18));
				
				String sFechaCreaAudit = lsAux.get(14);
				Assert.assertFalse(sFechaCreaAudit.isEmpty());
				sdfDateFormat = new SimpleDateFormat(sDateFormat);
				sdfDateFormat.parse(sFechaCreaAudit);
				
				String sCodUsuarioAlta = lsAux.get(15); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioAlta, 18));
				
				String sFechaModAudit = lsAux.get(16);
				Assert.assertFalse(sFechaModAudit.isEmpty());
				sdfDateFormat = new SimpleDateFormat(sDateFormat);
				sdfDateFormat.parse(sFechaModAudit);
				
				String sCodCuentaPadre = lsAux.get(17); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodCuentaPadre, 18));
				
				String sCodCliente = lsAux.get(18);
				Integer.parseInt(sCodCliente);
				
				String sFechaMod = lsAux.get(19); 
				if (!sFechaMod.isEmpty()) { 
					sdfDateFormat = new SimpleDateFormat(sDateFormat); 
					sdfDateFormat.parse(sFechaMod); 
				}
				
				String sCodUsuarioMod = lsAux.get(20); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 18));
				
				String sFechaCreacion = lsAux.get(21); 
				if (!sFechaCreacion.isEmpty()) { 
					sdfDateFormat = new SimpleDateFormat(sDateFormat); 
					sdfDateFormat.parse(sFechaCreacion); 
				}
				
				String sTipoCuenta = lsAux.get(22); 
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoCuenta, 160));
			
			}
		}
	}
		
	//Test #44
	@Test
	public void TS125442_CRM_Interfaz_LCRM_CasoHito() throws ParseException, IOException {
		String sName = "_CASOHITO_";
					
		rsePage.checkName(sName);
					
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIdCasoHito = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdCasoHito, 18));
				
				String sIdCaso = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdCaso, 18));
				
				String sFechaInicio = lsAux.get(2);
				if(!sFechaInicio.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaInicio);		
				}
				
				String sTipoCasoHito = lsAux.get(3);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoCasoHito, 18));
				
				String sMarcaCompletado = lsAux.get(4);
				if(!sMarcaCompletado.isEmpty()) {
					Integer.parseInt(sMarcaCompletado);
					Assert.assertTrue(sMarcaCompletado.equals("1") || sMarcaCompletado.equals("0"));
				}
				
				String sFechaCreacion = lsAux.get(5);
				if(!sFechaCreacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreacion);		
				}
				
				String sCodUsuarioAlta = lsAux.get(6);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioAlta, 18));
				
				String sFechaMod = lsAux.get(7);
				if (!sFechaMod.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaMod);
				}
				
				String sCodUsuarioMod = lsAux.get(8);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 18));
				
				String sFechaCreaAudit = lsAux.get(9);
				if (!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);
				}
				
				String sFechaModAudit = lsAux.get(10);
				if (!sFechaModAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModAudit);
				}
				
				String sFechaObjetivo = lsAux.get(11);
				if (!sFechaObjetivo.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaObjetivo);
				}
				
				String sTiempoObjetivoHoras = lsAux.get(12);
				if(!sTiempoObjetivoHoras.isEmpty()) {
					Double.parseDouble(sTiempoObjetivoHoras);
				}
				
				String sTiempoObjetivoDias = lsAux.get(13);
				if(!sTiempoObjetivoDias.isEmpty()) {
					Double.parseDouble(sTiempoObjetivoDias);
				}
				
				String sTiempoObjetivoMinutos = lsAux.get(14);
				if(!sTiempoObjetivoMinutos.isEmpty()) {
					Integer.parseInt(sTiempoObjetivoMinutos);
				}
				
				String sTiempoRestanteMinutos = lsAux.get(15);
				if(!sTiempoRestanteMinutos.isEmpty()) {
					Integer.parseInt(sTiempoRestanteMinutos);
				}
				
				String sTiempoRestanteHoras = lsAux.get(16);
				if(!sTiempoRestanteHoras.isEmpty()) {
					Double.parseDouble(sTiempoRestanteHoras);
				}
				
				String sTiempoRestanteDias = lsAux.get(17);
				if(!sTiempoRestanteDias.isEmpty()) {
					Double.parseDouble(sTiempoRestanteDias);
				}
				
				String sTiempoExcedidoMinutos = lsAux.get(18);
				if(!sTiempoExcedidoMinutos.isEmpty()) {
					Assert.assertTrue(rsePage.verifyTextMaxSize(sTiempoExcedidoMinutos, 10));
				}
				
				String sFechaHitoCumplido = lsAux.get(19);
				if (!sFechaHitoCumplido.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaHitoCumplido);
				}
				
				String sMarcaHitoNoCumplido = lsAux.get(20);
				if(!sMarcaHitoNoCumplido.isEmpty()) {
					Integer.parseInt(sMarcaHitoNoCumplido);
				}
			}
		}
	}
		
	//Test #45
	@Test
	public void TS125443_CRM_Interfaz_LCRM_UsuarioGrupoDeTrabajoHistoria() throws ParseException, IOException {
		String sName = "_USUARIOGRUPODETRABAJOHISTORIA_";
				
		rsePage.checkName(sName);
				
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIdUsuarioGrupoDeTrabajoHistoria = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdUsuarioGrupoDeTrabajoHistoria, 18));
				
				String sIdUsuarioGrupoDeTrabajo = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdUsuarioGrupoDeTrabajo, 18));
				
				String sCodUsuarioMod = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 18));
				
				String sFechaCreacion = lsAux.get(3);
				if(!sFechaCreacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreacion);		
				}
				
				String sCampoUsuarioGrupoDeTrabajo = lsAux.get(4);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCampoUsuarioGrupoDeTrabajo, 255));
				
				String sAntiguoValor = lsAux.get(5);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sAntiguoValor, 255));
				
				String sNuevoValor = lsAux.get(6);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNuevoValor, 255));
				
				String sFechaCreaAudit = lsAux.get(7);
				if(!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);		
				}
				
				String sFechaModAudit = lsAux.get(8);
				if(!sFechaModAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModAudit);		
				}
			}
		}
	}
		
	//Test #46
	@Test
	public void TS125444_CRM_Interfaz_LCRM_UsuarioRol() throws ParseException, IOException {
		String sName = "_USUARIOROL_";
				
		rsePage.checkName(sName);
				
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIdUsuarioRol = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdUsuarioRol, 18));
				
				String sNombreRol = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNombreRol, 80));
				
				String sRolPariente = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sRolPariente, 18));
				
				String sFechaCreaAudit = lsAux.get(3);
				if(!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);
				}
				
				String sFechaModAudit = lsAux.get(4);
				if(!sFechaModAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModAudit);
				}
				
				String sCodUsuarioMod = lsAux.get(5);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 18));
				
				String sFechaMod = lsAux.get(6);
				if(!sFechaMod.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaMod);			
				}
			}
		}
	}
		
	//Test #47
	@Test
	public void TS125445_CRM_Interfaz_LCRM_Tarea() throws ParseException, IOException {
		String sName = "_TAREA_";
			
		rsePage.checkName(sName);
			
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sIdTarea = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdTarea, 18));
				
				String sIdcaso = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdcaso, 18));
				
				String stipoRegistroTarea = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(stipoRegistroTarea, 18));
				
				String sTema = lsAux.get(3);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTema, 255));
				
				String sfechaTareaPactada = lsAux.get(4);
				if (!sfechaTareaPactada.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sfechaTareaPactada);
				}
				
				String sEstadoTarea = lsAux.get(5);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEstadoTarea, 40));
				
				String sPrioridadTarea = lsAux.get(6);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sPrioridadTarea, 40));
				
				String sMarcaPrioridadAlta = lsAux.get(7);
				Integer.parseInt(sMarcaPrioridadAlta);
				Assert.assertTrue(sMarcaPrioridadAlta.equals("1") || sMarcaPrioridadAlta.equals("0"));
				
				String sIdPropietario = lsAux.get(8);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdPropietario, 18));
				
				String sDescripcionTarea = lsAux.get(9);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sDescripcionTarea, 1300));
				
				String sTipoTarea = lsAux.get(10);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sTipoTarea, 40));
				
				String sIdCuentaCliente = lsAux.get(11);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdCuentaCliente, 18));
				
				String sMarcaTareaCerrada = lsAux.get(12);
				Integer.parseInt(sMarcaTareaCerrada);
				Assert.assertTrue(sMarcaTareaCerrada.equals("1") || sMarcaTareaCerrada.equals("0"));
				
				String sFechaCreacion = lsAux.get(13);
				if(!sFechaCreacion.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreacion);
				}
				
				String sCodUsuarioAlta = lsAux.get(14);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioAlta, 18));
				
				String sFechaMod = lsAux.get(15);
				if(!sFechaMod.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaMod);
				}
				
				String sCodUsuarioMod = lsAux.get(16);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioMod, 18));
				
				String sSubtipoTarea = lsAux.get(17);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sSubtipoTarea, 40));
				
				String sFechaCierreTarea = lsAux.get(18);
				if(!sFechaCierreTarea.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCierreTarea);
				}
				
				String sDuracionTarea = lsAux.get(19);
				if(!sDuracionTarea.isEmpty()) {
					Integer.parseInt(sDuracionTarea);
					Assert.assertTrue(sDuracionTarea.equals("1") || sDuracionTarea.equals("0"));
				}
				String sEstadoEvento = lsAux.get(20);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEstadoEvento, 255));
				
				String sCodUsuarioSeguimiento = lsAux.get(21);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCodUsuarioSeguimiento, 255));
				
				String sNivelAlarma = lsAux.get(22);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sNivelAlarma, 255));
				
				String sFechaCreaAudit = lsAux.get(23);
				if(!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);
				}
				
				String sFechaModAudit = lsAux.get(24);
				if(!sFechaModAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaModAudit);
				}
			}
		}
	}
	
	//Test #48
	@Test
	public void TS125446_CRM_Interfaz_LCRM_LegacyPermissions() throws ParseException, IOException {
		String sName = "_LEGACY_PERMISSIONS_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sObjetoDeAutorizacion = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sObjetoDeAutorizacion, 50));
				
				String sDescripcionDeObjeto = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sDescripcionDeObjeto, 50));
				
				String sIdioma = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sIdioma, 2));
			}
		}
	}
	
	//Test #49
	@Test
	public void TS125447_CRM_Interfaz_LCRM_ReferenciaEntidadValor() throws ParseException, IOException {
		String sName = "_REFERENCIAENTIDADVALOR_";
		
		rsePage.checkName(sName);
		
		List<List<List<String>>> sFilesContent = new ArrayList<List<List<String>>>();
		
		List<String> sFiles = rsePage.findFiles(sName);
		
		for (String sAux : sFiles) {
			sFilesContent.add(rsePage.readTxt(sAux));
		}
		
		for (List<List<String>> sList : sFilesContent) {
			for (List<String> lsAux : sList) {
				String sEntidadLCRM = lsAux.get(0);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sEntidadLCRM, 255));
				Assert.assertFalse(sEntidadLCRM.isEmpty());
				
				String sCampoLCRM = lsAux.get(1);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sCampoLCRM, 255));
				Assert.assertFalse(sCampoLCRM.isEmpty());
				
				String sValor = lsAux.get(2);
				Assert.assertTrue(rsePage.verifyTextMaxSize(sValor, 70));
				
				String sFechaCreaAudit = lsAux.get(3);
				if(!sFechaCreaAudit.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaAudit);
				}
				
				String sFechaCreaMod  = lsAux.get(4);
				if(!sFechaCreaMod.isEmpty()) {
					sdfDateFormat = new SimpleDateFormat(sDateFormat);
					sdfDateFormat.parse(sFechaCreaMod);
				}
			}
		}
	}
	
}