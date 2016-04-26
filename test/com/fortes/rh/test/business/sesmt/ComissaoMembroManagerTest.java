package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.ComissaoMembroManagerImpl;
import com.fortes.rh.dao.sesmt.ComissaoMembroDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.ComissaoPeriodo;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoMembroFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoPeriodoFactory;

public class ComissaoMembroManagerTest extends MockObjectTestCase
{
	private ComissaoMembroManagerImpl comissaoMembroManager = new ComissaoMembroManagerImpl();
	private Mock comissaoMembroDao;

	protected void setUp() throws Exception
    {
        super.setUp();
        comissaoMembroDao = new Mock(ComissaoMembroDao.class);
        comissaoMembroManager.setDao((ComissaoMembroDao) comissaoMembroDao.proxy());
    }

	public void testFindByComissaoPeriodo()
	{
		comissaoMembroDao.expects(once()).method("findByComissaoPeriodo").with(ANYTHING).will(returnValue(new ArrayList<ComissaoMembro>()));
		comissaoMembroManager.findByComissaoPeriodo(new Long[]{1L});

		comissaoMembroManager.findByComissaoPeriodo(new Long[0]);
	}

	public void testFindColaboradorByComissao()
	{
		comissaoMembroDao.expects(once()).method("findColaboradorByComissao").with(ANYTHING).will(returnValue(new ArrayList<Colaborador>()));
		comissaoMembroManager.findColaboradorByComissao(1L);
	}

	public void testRemoveByComissaoPeriodo()
	{
		comissaoMembroDao.expects(once()).method("removeByComissaoPeriodo").with(ANYTHING).isVoid();
		comissaoMembroManager.removeByComissaoPeriodo(new Long[]{1L});
	}
	
    public void testFindByColaborador()
    {
    	Collection<ComissaoMembro> comissaoMembros = Arrays.asList(ComissaoMembroFactory.getEntity(11L));
    	
    	comissaoMembroDao.expects(once()).method("findByColaborador").with(eq(1L)).will(returnValue(comissaoMembros));
    	
    	assertEquals(1, comissaoMembroManager.findByColaborador(1L).size());
    }

    public void testUpdateFuncaoETipo() throws Exception
    {
    	String[] comissaoMembroIds = {"1","2"};
    	String[] funcaoComissaos = {"1","2"};
    	String[] tipoComissaos = {"1","2"};
    	
    	comissaoMembroDao.expects(atLeastOnce()).method("updateFuncaoETipo").with(ANYTHING, ANYTHING, ANYTHING);
    	comissaoMembroManager.updateFuncaoETipo(comissaoMembroIds, funcaoComissaos, tipoComissaos);
    }

    public void testSave() throws Exception
    {
    	Colaborador joao = ColaboradorFactory.getEntity(1L);
    	Colaborador maria = ColaboradorFactory.getEntity(2L);

    	ComissaoPeriodo comissaoPeriodo = ComissaoPeriodoFactory.getEntity(1L);
    	
    	ComissaoMembro comissaoMembroJoao = ComissaoMembroFactory.getEntity();
    	comissaoMembroJoao.setColaborador(joao);
    	comissaoMembroJoao.setComissaoPeriodo(comissaoPeriodo);
    	
    	ComissaoMembro comissaoMembroMaria = ComissaoMembroFactory.getEntity();
    	comissaoMembroMaria.setColaborador(maria);
    	comissaoMembroMaria.setComissaoPeriodo(comissaoPeriodo);
    	
    	Collection<ComissaoMembro> comissaoMembros = new ArrayList<ComissaoMembro>();
    	comissaoMembros.add(comissaoMembroJoao);
    	comissaoMembros.add(comissaoMembroMaria);
    	
    	String[] colaboradorsCheck = {"1","2","33", "44"};

    	comissaoMembroDao.expects(once()).method("findByComissaoPeriodo").with(ANYTHING).will(returnValue(comissaoMembros));
    	comissaoMembroDao.expects(once()).method("save").with(ANYTHING);
    	comissaoMembroDao.expects(once()).method("save").with(ANYTHING);
    	
    	comissaoMembroManager.save(colaboradorsCheck, comissaoPeriodo);
    }

    public void testFindDistinctByComissaoPeriodo() throws Exception
    {
    	comissaoMembroDao.expects(once()).method("findDistinctByComissaoPeriodo").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<ComissaoMembro>()));
    	assertEquals(0, comissaoMembroManager.findDistinctByComissaoPeriodo(1L, null).size());
    }

    public void testFindByComissao() throws Exception
    {
    	comissaoMembroDao.expects(once()).method("findByComissao").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<ComissaoMembro>()));
    	assertEquals(0, comissaoMembroManager.findByComissao(1L, "teste").size());
    }

}