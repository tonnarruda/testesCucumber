package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.ComissaoReuniaoPresencaManagerImpl;
import com.fortes.rh.dao.sesmt.ComissaoReuniaoPresencaDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.ComissaoReuniaoPresenca;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;

public class ComissaoReuniaoPresencaManagerTest extends MockObjectTestCase
{
	private ComissaoReuniaoPresencaManagerImpl comissaoReuniaoPresencaManager = new ComissaoReuniaoPresencaManagerImpl();
	private Mock comissaoReuniaoPresencaDao;

	protected void setUp() throws Exception
    {
        super.setUp();
        comissaoReuniaoPresencaDao = new Mock(ComissaoReuniaoPresencaDao.class);
        comissaoReuniaoPresencaManager.setDao((ComissaoReuniaoPresencaDao) comissaoReuniaoPresencaDao.proxy());
    }

	public void testSaveOrUpdateByReuniao()
	{
		Long comissaoReuniaoId = 1L;
		Long comissaoId = 1L;
		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
		Collection<Colaborador> membros = new ArrayList<Colaborador>();
		membros.add(colaborador1);
		membros.add(colaborador2);

		Collection<ComissaoReuniaoPresenca> comissaoReuniaoPresencas = new ArrayList<ComissaoReuniaoPresenca>();
		ComissaoReuniaoPresenca presenca = new ComissaoReuniaoPresenca();
		presenca.setColaborador(colaborador1);
		comissaoReuniaoPresencas.add(presenca);

		String[] presencaChecks = {"1"};	// CheckBox de Presenças
		String[] presencaIds = {"1","2"};	// Todos os membros
		String[] justificativas = {"","Doente"}; // Justificando faltas

		comissaoReuniaoPresencaDao.expects(once()).method("findByReuniao").with(eq(comissaoReuniaoId)).will(returnValue(comissaoReuniaoPresencas));
		comissaoReuniaoPresencaDao.expects(atLeastOnce()).method("update").with(ANYTHING).isVoid();
		comissaoReuniaoPresencaDao.expects(atLeastOnce()).method("save").with(ANYTHING).will(returnValue(presenca));

		Exception exception = null;
		try
		{
			comissaoReuniaoPresencaManager.saveOrUpdateByReuniao(comissaoReuniaoId, comissaoId, presencaChecks, presencaIds, justificativas);
		}
		catch (Exception e)
		{
			exception = e;
			e.printStackTrace();
		}
		assertNull(exception);
	}

	public void testSaveOrUpdateByReuniaoSemPresencasRegistradas() throws Exception
	{
		Long comissaoReuniaoId = 1L;
		Long comissaoId = 1L;
		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
		Collection<Colaborador> membros = new ArrayList<Colaborador>();
		membros.add(colaborador1);
		membros.add(colaborador2);

		String[] presencaChecks = {};	// CheckBox de Presenças
		String[] presencaIds = {};	// Todos os membros
		String[] justificativas = {"","Doente"}; // Justificando faltas

		comissaoReuniaoPresencaDao.expects(once()).method("findByReuniao").with(eq(comissaoReuniaoId)).will(returnValue(new ArrayList<ComissaoReuniaoPresenca>()));
		comissaoReuniaoPresencaDao.expects(once()).method("saveOrUpdate");

		comissaoReuniaoPresencaManager.saveOrUpdateByReuniao(comissaoReuniaoId, comissaoId, presencaChecks, presencaIds, justificativas);
	}

	public void testRemoveByReuniao()
	{
		comissaoReuniaoPresencaDao.expects(once()).method("removeByReuniao").with(ANYTHING).isVoid();
		comissaoReuniaoPresencaManager.removeByReuniao(1L);
	}
	public void testFindByReuniao()
	{
		comissaoReuniaoPresencaDao.expects(once()).method("findByReuniao").with(ANYTHING).will(returnValue(new ArrayList<ComissaoReuniaoPresenca>()));
		comissaoReuniaoPresencaManager.findByReuniao(1L);
	}
	public void testFindByComissao()
	{
		comissaoReuniaoPresencaDao.expects(once()).method("findByComissao").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<ComissaoReuniaoPresenca>()));
		comissaoReuniaoPresencaManager.findByComissao(1L, false);
	}


}