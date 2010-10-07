package com.fortes.rh.test.model.sesmt;

import junit.framework.TestCase;

import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.test.factory.sesmt.ComissaoFactory;
import com.fortes.rh.util.DateUtil;

public class ComissaoTest extends TestCase
{
	public void testGetPeriodoFormatado()
    {
    	Comissao comissao = ComissaoFactory.getEntity(1L);
    	comissao.setDataIni(DateUtil.criarDataMesAno(01, 02, 2004));
    	comissao.setDataFim(DateUtil.criarDataMesAno(01, 02, 2005));
    	
    	assertEquals("01/02/2004 a 01/02/2005", comissao.getPeriodoFormatado());
    }
	
	public void testGetPeriodoFormatadoNull()
	{
		Comissao comissao = ComissaoFactory.getEntity(1L);
    	comissao.setDataIni(null);
    	comissao.setDataFim(null);
    	
    	assertEquals("", comissao.getPeriodoFormatado());
	}
    
}
