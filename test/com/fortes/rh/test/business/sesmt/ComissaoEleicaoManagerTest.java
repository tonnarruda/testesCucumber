package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.sesmt.ComissaoEleicaoManagerImpl;
import com.fortes.rh.dao.sesmt.ComissaoEleicaoDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.ComissaoEleicao;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoEleicaoFactory;
import com.fortes.rh.test.factory.sesmt.EleicaoFactory;

public class ComissaoEleicaoManagerTest extends MockObjectTestCase
{
	private ComissaoEleicaoManagerImpl comissaoEleicaoManager = new ComissaoEleicaoManagerImpl();
	private Mock comissaoEleicaoDao;
	private Mock transactionManager;

	protected void setUp() throws Exception
    {
        super.setUp();
        comissaoEleicaoDao = new Mock(ComissaoEleicaoDao.class);
        comissaoEleicaoManager.setDao((ComissaoEleicaoDao) comissaoEleicaoDao.proxy());

        transactionManager = new Mock(PlatformTransactionManager.class);
        comissaoEleicaoManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());
    }
	
	public void testFindByEleicao()
	{		
		Eleicao eleicao = EleicaoFactory.getEntity(1L);
		comissaoEleicaoDao.expects(once()).method("findByEleicao").with(eq(eleicao.getId())).will(returnValue(new ArrayList<ComissaoEleicao>()));
	
		comissaoEleicaoManager.findByEleicao(eleicao.getId());
	}
	
	public void testSave()
	{
		String[] colaboradorsCheck = new String[]{"1"};
		Eleicao eleicao = EleicaoFactory.getEntity(1L);
		
		transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
		comissaoEleicaoDao.expects(once()).method("findByEleicao").with(eq(eleicao.getId())).will(returnValue(new ArrayList<ComissaoEleicao>()));
		comissaoEleicaoDao.expects(once()).method("save").with(ANYTHING).isVoid();
		transactionManager.expects(once()).method("commit").with(ANYTHING);
		
		Exception exception = null;
		try
		{
			comissaoEleicaoManager.save(colaboradorsCheck, eleicao);
		}
		catch (Exception e)
		{
			exception = e;
		}
		
		assertNull(exception);
	}
	
	public void testSaveException()
	{
		String[] colaboradorsCheck = new String[]{"1"};
		Eleicao eleicao = EleicaoFactory.getEntity(1L);
		
		transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
		comissaoEleicaoDao.expects(once()).method("findByEleicao").with(eq(eleicao.getId())).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));
		transactionManager.expects(once()).method("rollback").with(ANYTHING);
		
		Exception exception = null;
		try
		{
			comissaoEleicaoManager.save(colaboradorsCheck, eleicao);
		}
		catch (Exception e)
		{
			exception = e;
		}
		
		assertNotNull(exception);
	}
	
	public void testSaveRepetido()
	{
		Eleicao eleicao = EleicaoFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		String[] colaboradorsCheck = new String[]{"1"};
		
		ComissaoEleicao comissaoEleicao = ComissaoEleicaoFactory.getEntity(1L);
		comissaoEleicao.setEleicao(eleicao);
		comissaoEleicao.setColaborador(colaborador);
		
		Collection<ComissaoEleicao> comissaos = new ArrayList<ComissaoEleicao>();
		comissaos.add(comissaoEleicao);
		
		transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
		comissaoEleicaoDao.expects(once()).method("findByEleicao").with(eq(eleicao.getId())).will(returnValue(comissaos));
		//não salva pois ja existe o colaborador na comissao
		transactionManager.expects(once()).method("commit").with(ANYTHING);
		
		Exception exception = null;
		try
		{
			comissaoEleicaoManager.save(colaboradorsCheck, eleicao);
		}
		catch (Exception e)
		{
			exception = e;
		}
		
		assertNull(exception);
	}
	
	public void testUpdateFuncao()
	{
		String[] comissaoEleicaoIds = new String[]{"1"};
		String[] funcaoComissaos = new String[]{"12"};
		
        transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
        comissaoEleicaoDao.expects(once()).method("updateFuncao").with(eq(1L), eq(funcaoComissaos[0]));
        transactionManager.expects(once()).method("commit").with(ANYTHING);

        Exception exception = null;
        try
        {
        	comissaoEleicaoManager.updateFuncao(comissaoEleicaoIds, funcaoComissaos);
        }
        catch (Exception e)
        {
            exception = e;
        }

        assertNull(exception);
	}
	
	public void testUpdateFuncaoException()
	{
		String[] comissaoEleicaoIds = new String[]{"1"};
		String[] funcaoComissaos = new String[]{"12"};
		
		transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
		comissaoEleicaoDao.expects(once()).method("updateFuncao").with(eq(1L), eq(funcaoComissaos[0])).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));;
		transactionManager.expects(once()).method("rollback").with(ANYTHING);
		
		Exception exception = null;
		try
		{
			comissaoEleicaoManager.updateFuncao(comissaoEleicaoIds, funcaoComissaos);
		}
		catch (Exception e)
		{
			exception = e;
		}
		
		assertNotNull(exception);
	}
    
    public void testRemoveByEleicao()
    {
    	Eleicao eleicao = EleicaoFactory.getEntity(1L);
    	
    	comissaoEleicaoDao.expects(once()).method("removeByEleicao").with(eq(eleicao.getId())).isVoid();
    	
    	comissaoEleicaoManager.removeByEleicao(eleicao.getId());
    }
}