package com.fortes.rh.test.business.desenvolvimento;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.desenvolvimento.TurmaDocumentoAnexoManagerImpl;
import com.fortes.rh.dao.desenvolvimento.TurmaDocumentoAnexoDao;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;

public class TurmaDocumentoAnexoManagerTest extends MockObjectTestCase
{
	private TurmaDocumentoAnexoManagerImpl turmaDocumentoAnexoManager  = new TurmaDocumentoAnexoManagerImpl();
	private Mock turmaDocumentoAnexoDao = null;

	protected void setUp() throws Exception
	{
		turmaDocumentoAnexoDao = new Mock(TurmaDocumentoAnexoDao.class);
		turmaDocumentoAnexoManager.setDao((TurmaDocumentoAnexoDao) turmaDocumentoAnexoDao.proxy());
	}

	public void testFindAllSelect()
	{
		Turma turma = TurmaFactory.getEntity(1L);
		turmaDocumentoAnexoDao.expects(once()).method("removeByTurma").with(eq(1L)).isVoid();
		turmaDocumentoAnexoDao.expects(once()).method("save").with(ANYTHING).isVoid();

		Exception ex = null;
		try
		{
			turmaDocumentoAnexoManager.salvarDocumentoAnexos(turma.getId(), new Long[]{1L});
		}
		catch (Exception e)
		{
			ex = e;
		}
		
		assertNull(ex);
	}
}
