package com.fortes.rh.test.business.geral;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.GrupoACManagerImpl;
import com.fortes.rh.dao.geral.GrupoACDao;
import com.fortes.rh.model.geral.GrupoAC;

public class GrupoACManagerTest extends MockObjectTestCase
{
	GrupoACManagerImpl grupoACManager = null;
	Mock grupoACDao = null;

	protected void setUp() throws Exception
	{
		super.setUp();
		grupoACManager = new GrupoACManagerImpl();
		grupoACDao = new Mock(GrupoACDao.class);
		grupoACManager.setDao((GrupoACDao) grupoACDao.proxy());
	}
	
	public void testUpdateGrupoVazio() throws Exception 
	{
		GrupoAC grupoAC = new GrupoAC("XXX", "teste");
		grupoAC.setId(1L);
		grupoAC.setAcSenha("");

		GrupoAC grupoACTmp = new GrupoAC("XXX", "teste");
		grupoACTmp.setAcSenha("1234");
		
		grupoACDao.expects(once()).method("findById").with(eq(1L)).will(returnValue(grupoACTmp));
		grupoACDao.expects(once()).method("update").with(eq(grupoAC));
		
		GrupoAC retorno = grupoACManager.updateGrupo(grupoAC);
		
		assertEquals("1234", retorno.getAcSenha());
	}

	public void testUpdateGrupoNull() throws Exception 
	{
		GrupoAC grupoAC = new GrupoAC("XXX", "teste");
		grupoAC.setId(1L);
		grupoAC.setAcSenha(null);
		
		GrupoAC grupoACTmp = new GrupoAC("XXX", "teste");
		grupoACTmp.setAcSenha("1234");
		
		grupoACDao.expects(once()).method("findById").with(eq(1L)).will(returnValue(grupoACTmp));
		grupoACDao.expects(once()).method("update").with(eq(grupoAC));
		
		GrupoAC retorno = grupoACManager.updateGrupo(grupoAC);
		
		assertEquals("1234", retorno.getAcSenha());
	}
	
	public void testUpdateGrupo() throws Exception 
	{
		GrupoAC grupoAC = new GrupoAC("XXX", "teste");
		grupoAC.setId(1L);
		grupoAC.setAcSenha("564");
		
		grupoACDao.expects(once()).method("update").with(eq(grupoAC));
		
		GrupoAC retorno = grupoACManager.updateGrupo(grupoAC);
		
		assertEquals("564", retorno.getAcSenha());
	}
}