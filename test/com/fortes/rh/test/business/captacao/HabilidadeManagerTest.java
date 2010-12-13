package com.fortes.rh.test.business.captacao;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.HabilidadeManagerImpl;
import com.fortes.rh.dao.captacao.HabilidadeDao;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;

public class HabilidadeManagerTest extends MockObjectTestCase
{
	private HabilidadeManagerImpl habilidadeManager = new HabilidadeManagerImpl();
	private Mock habilidadeDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        habilidadeDao = new Mock(HabilidadeDao.class);
        habilidadeManager.setDao((HabilidadeDao) habilidadeDao.proxy());
    }

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<Habilidade> habilidades = HabilidadeFactory.getCollection(1L);

		habilidadeDao.expects(once()).method("findAllSelect").with(eq(empresaId)).will(returnValue(habilidades));
		//assertEquals(habilidades, habilidadeManager.findAllSelect(empresaId));
	}
}
