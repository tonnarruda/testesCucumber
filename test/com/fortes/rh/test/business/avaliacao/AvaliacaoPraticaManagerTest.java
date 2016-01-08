package com.fortes.rh.test.business.avaliacao;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.avaliacao.AvaliacaoPraticaManagerImpl;
import com.fortes.rh.dao.avaliacao.AvaliacaoPraticaDao;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoPraticaFactory;

public class AvaliacaoPraticaManagerTest extends MockObjectTestCase
{
	private AvaliacaoPraticaManagerImpl avaliacaoPraticaManager = new AvaliacaoPraticaManagerImpl();
	private Mock avaliacaoPraticaDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        avaliacaoPraticaDao = new Mock(AvaliacaoPraticaDao.class);
        avaliacaoPraticaManager.setDao((AvaliacaoPraticaDao) avaliacaoPraticaDao.proxy());
    }

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<AvaliacaoPratica> avaliacaoPraticas = AvaliacaoPraticaFactory.getCollection(1L);

		avaliacaoPraticaDao.expects(once()).method("find").withAnyArguments().will(returnValue(avaliacaoPraticas));
		
		assertEquals(avaliacaoPraticas, avaliacaoPraticaManager.find(1, 15, new String[] {"empresa.id"}, new Object[] { empresaId }, new String[] { "titulo" }));
	}
	
	public void testFindByCertificacaoId(){
		Long certificacaoId = 1L;
		
		Collection<AvaliacaoPratica> avaliacaoPraticas = AvaliacaoPraticaFactory.getCollection(1L);
		avaliacaoPraticaDao.expects(once()).method("findByCertificacaoId").with(eq(certificacaoId)).will(returnValue(avaliacaoPraticas));
		
		assertEquals(1, avaliacaoPraticaManager.findByCertificacaoId(certificacaoId).size());
	}
}
