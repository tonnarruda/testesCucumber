package com.fortes.rh.test.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.util.ModelUtil;

public class ModelUtilTest extends TestCase
{
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    protected void setUp()
    {

    }

    public void testGetValor()
    {
		Colaborador colaborador = ColaboradorFactory.getEntity();
    	colaborador.getPessoal().getCtps().setCtpsNumero("1234");
    	colaborador.getPessoal().getCtps().setCtpsDataExpedicao(new Date());

    	assertEquals(colaborador.getPessoal().getCtps().getCtpsNumero(), ModelUtil.getValor(colaborador, "getPessoal().getCtps().getCtpsNumero()", true));
    	assertEquals(colaborador.getPessoal().getCtps().getCtpsNumero(), ModelUtil.getValor(colaborador, "getPessoal().getCtps().getCtpsNumero()", false));
    	assertEquals(sdf.format(colaborador.getPessoal().getCtps().getCtpsDataExpedicao()), ModelUtil.getValor(colaborador, "getPessoal().getCtps().getCtpsDataExpedicao()", true));
    	assertEquals(sdf.format(colaborador.getPessoal().getCtps().getCtpsDataExpedicao()), ModelUtil.getValor(colaborador, "getPessoal().getCtps().getCtpsDataExpedicao()", false));
    }
    
    public void testGetValorConsiderandoBrancoComoNulo()
    {
    	Colaborador colaborador = ColaboradorFactory.getEntity();
    	colaborador.getPessoal().getCtps().setCtpsNumero("");
    	
    	assertNull(ModelUtil.getValor(colaborador, "getPessoal().getCtps().getCtpsNumero()", true));
    	assertNotNull(ModelUtil.getValor(colaborador, "getPessoal().getCtps().getCtpsNumero()", false));
    	assertEquals(colaborador.getPessoal().getCtps().getCtpsNumero(), ModelUtil.getValor(colaborador, "getPessoal().getCtps().getCtpsNumero()", false));
    }
    
    public void testGetValorComPrimeiroNohNulo()
    {
    	Colaborador colaborador = ColaboradorFactory.getEntity();
    	colaborador.getPessoal().getCtps().setCtpsNumero(null);
    	
    	assertNull(ModelUtil.getValor(colaborador, "getPessoal().getCtps().getCtpsNumero()", true));
    	assertNull(ModelUtil.getValor(colaborador, "getPessoal().getCtps().getCtpsNumero()", false));
    }
    
    public void testGetValorComSegundoNohNulo()
    {
    	Colaborador colaborador = ColaboradorFactory.getEntity();
    	colaborador.getPessoal().setCtps(null);
    	
    	assertNull(ModelUtil.getValor(colaborador, "getPessoal().getCtps().getCtpsNumero()", true));
    	assertNull(ModelUtil.getValor(colaborador, "getPessoal().getCtps().getCtpsNumero()", false));
    }
    
    public void testGetValorComTerceiroNohNulo()
    {
    	Colaborador colaborador = ColaboradorFactory.getEntity();
    	colaborador.getPessoal().getCtps().setCtpsNumero(null);
    	colaborador.getPessoal().getCtps().setCtpsDataExpedicao(null);
    	
    	assertNull(ModelUtil.getValor(colaborador, "getPessoal().getCtps().getCtpsNumero()", true));
    	assertNull(ModelUtil.getValor(colaborador, "getPessoal().getCtps().getCtpsNumero()", false));
    	assertNull(ModelUtil.getValor(colaborador, "getPessoal().getCtps().getCtpsDataExpedicao()", true));
    	assertNull(ModelUtil.getValor(colaborador, "getPessoal().getCtps().getCtpsDataExpedicao()", false));
    }
    
    public void testHasNull()
    {
		Estado uf = new Estado();
		uf.setSigla("RN");

    	Colaborador colaborador = ColaboradorFactory.getEntity();
    	colaborador.getPessoal().setCertificadoMilitar(null);
    	colaborador.getPessoal().getCtps().setCtpsNumero(null);
    	colaborador.getPessoal().getCtps().setCtpsDataExpedicao(null);
    	colaborador.getPessoal().getCtps().setCtpsUf(uf);
    	
    	assertTrue(ModelUtil.hasNull("getPessoal().getCertificadoMilitar()", colaborador));
    	assertTrue(ModelUtil.hasNull("getPessoal().getCertificadoMilitar().getCertMilNumero()", colaborador));
    	assertFalse(ModelUtil.hasNull("getPessoal().getCtps()", colaborador));
    	assertTrue(ModelUtil.hasNull("getPessoal().getCtps().getCtpsNumero()", colaborador));
    	assertTrue(ModelUtil.hasNull("getPessoal().getCtps().getCtpsDataExpedicao()", colaborador));
    	assertFalse(ModelUtil.hasNull("getPessoal().getCtps().getCtpsUf()", colaborador));
    }
    
    public void testHasNullModelo1Null()
    {
    	Colaborador colaboradorNulo = null;
    	Colaborador colaborador1 = ColaboradorFactory.getEntity();
    	colaborador1.setPessoal(null);
    	Colaborador colaborador2 = ColaboradorFactory.getEntity();
    	colaborador2.getPessoal().getCtps().setCtpsNumero("1234");
    	Colaborador colaborador3 = ColaboradorFactory.getEntity();
    	colaborador3.getPessoal().getCtps().setCtpsNumero("");
    	
    	assertTrue(ModelUtil.hasNull("getPessoal().getCtps().getCtpsNumero()", colaborador1, colaborador2, colaborador3));
    	assertFalse(ModelUtil.hasNull("getPessoal().getCtps().getCtpsNumero()", colaborador2, colaborador3));
    	assertTrue(ModelUtil.hasNull("getPessoal().getCtps().getCtpsNumero()", colaboradorNulo));
    }
    
    public void testHasNullModelo2Null()
    {
    	Colaborador colaborador1 = ColaboradorFactory.getEntity();
    	colaborador1.getPessoal().getCtps().setCtpsNumero("1234");
    	Colaborador colaborador2 = ColaboradorFactory.getEntity();
    	colaborador2.setPessoal(null);
    	Colaborador colaborador3 = ColaboradorFactory.getEntity();
    	colaborador3.getPessoal().getCtps().setCtpsNumero("");
    	
    	assertTrue(ModelUtil.hasNull("getPessoal().getCtps().getCtpsNumero()", colaborador1, colaborador2, colaborador3));
    	assertFalse(ModelUtil.hasNull("getPessoal().getCtps().getCtpsNumero()", colaborador1, colaborador3));
    }
    
    public void testHasNullModelo3Null()
    {
    	Colaborador colaborador1 = ColaboradorFactory.getEntity();
    	colaborador1.getPessoal().getCtps().setCtpsNumero("1234");
    	Colaborador colaborador2 = ColaboradorFactory.getEntity();
    	colaborador2.getPessoal().getCtps().setCtpsNumero("");
    	Colaborador colaborador3 = ColaboradorFactory.getEntity();
    	colaborador3.setPessoal(null);
    	
    	assertTrue(ModelUtil.hasNull("getPessoal().getCtps().getCtpsNumero()", colaborador1, colaborador2, colaborador3));
    	assertFalse(ModelUtil.hasNull("getPessoal().getCtps().getCtpsNumero()", colaborador1, colaborador2));
    }
 
    public void testHasNotNull()
    {
		Estado uf = new Estado();
		uf.setSigla("RN");

    	Colaborador colaborador = ColaboradorFactory.getEntity();
    	colaborador.getPessoal().setCertificadoMilitar(null);
    	colaborador.getPessoal().getCtps().setCtpsNumero(null);
    	colaborador.getPessoal().getCtps().setCtpsDataExpedicao(null);
    	colaborador.getPessoal().getCtps().setCtpsUf(uf);
    	
    	assertFalse(ModelUtil.hasNotNull("getPessoal().getCertificadoMilitar()", colaborador));
    	assertFalse(ModelUtil.hasNotNull("getPessoal().getCertificadoMilitar().getCertMilNumero()", colaborador));
    	assertTrue(ModelUtil.hasNotNull("getPessoal().getCtps()", colaborador));
    	assertFalse(ModelUtil.hasNotNull("getPessoal().getCtps().getCtpsNumero()", colaborador));
    	assertFalse(ModelUtil.hasNotNull("getPessoal().getCtps().getCtpsDataExpedicao()", colaborador));
    	assertTrue(ModelUtil.hasNotNull("getPessoal().getCtps().getCtpsUf()", colaborador));
    }
    
    public void testHasNotNullModelo1Null()
    {
    	Colaborador colaboradorNulo = null;
    	Colaborador colaborador1 = ColaboradorFactory.getEntity();
    	colaborador1.setPessoal(null);
    	Colaborador colaborador2 = ColaboradorFactory.getEntity();
    	colaborador2.getPessoal().getCtps().setCtpsNumero("1234");
    	Colaborador colaborador3 = ColaboradorFactory.getEntity();
    	colaborador3.getPessoal().getCtps().setCtpsNumero("");
    	
    	assertFalse(ModelUtil.hasNotNull("getPessoal().getCtps().getCtpsNumero()", colaborador1, colaborador2, colaborador3));
    	assertTrue(ModelUtil.hasNotNull("getPessoal().getCtps().getCtpsNumero()", colaborador2, colaborador3));
    	assertFalse(ModelUtil.hasNotNull("getPessoal().getCtps().getCtpsNumero()", colaboradorNulo));
    }
    

}