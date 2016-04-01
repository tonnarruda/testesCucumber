package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;

import com.fortes.rh.business.geral.ColaboradorIdiomaManagerImpl;
import com.fortes.rh.dao.geral.ColaboradorIdiomaDao;
import com.fortes.rh.model.captacao.Idioma;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorIdioma;
import com.fortes.rh.test.business.MockObjectTestCaseManager;
import com.fortes.rh.test.business.TesteAutomaticoManager;

public class ColaboradorIdiomaManagerTest extends MockObjectTestCaseManager<ColaboradorIdiomaManagerImpl> implements TesteAutomaticoManager
{
	Mock colaboradorIdiomaDao = null;

    protected void setUp() throws Exception
    {
        super.setUp();
        manager = new ColaboradorIdiomaManagerImpl();
        colaboradorIdiomaDao = new Mock(ColaboradorIdiomaDao.class);
		manager.setDao((ColaboradorIdiomaDao) colaboradorIdiomaDao.proxy());
    }

	public void testFindByColaborador() throws Exception
	{
		Colaborador colaborador = new Colaborador();
		colaborador.setId(1L);

		Idioma idioma = new Idioma();
		idioma.setId(1L);

		ColaboradorIdioma colaboradorIdioma1 = new ColaboradorIdioma();
		colaboradorIdioma1.setId(1L);
		colaboradorIdioma1.setColaborador(colaborador);
		colaboradorIdioma1.setIdioma(idioma);

		ColaboradorIdioma colaboradorIdioma2 = new ColaboradorIdioma();
		colaboradorIdioma2.setId(2L);
		colaboradorIdioma2.setColaborador(colaborador);
		colaboradorIdioma2.setIdioma(idioma);

		Collection<ColaboradorIdioma> colaboradorIdiomas = new ArrayList<ColaboradorIdioma>();
		colaboradorIdiomas.add(colaboradorIdioma1);
		colaboradorIdiomas.add(colaboradorIdioma2);

		colaboradorIdiomaDao.expects(once()).method("findByColaborador").with(eq(colaborador.getId())).will(returnValue(colaboradorIdiomas));

		Collection<ColaboradorIdioma> colaboradorIdiomasRetorno = manager.findByColaborador(colaborador.getId());

		assertEquals(2, colaboradorIdiomasRetorno.size());
	}

	public void testExecutaTesteAutomaticoDoManager() {
		testeAutomatico(colaboradorIdiomaDao);
	}
}