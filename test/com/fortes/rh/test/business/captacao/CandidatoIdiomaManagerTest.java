package com.fortes.rh.test.business.captacao;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.CandidatoIdiomaManagerImpl;
import com.fortes.rh.business.geral.ColaboradorIdiomaManager;
import com.fortes.rh.dao.captacao.CandidatoIdiomaDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoIdioma;
import com.fortes.rh.model.captacao.Idioma;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorIdioma;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;

public class CandidatoIdiomaManagerTest extends MockObjectTestCase
{
	private CandidatoIdiomaManagerImpl candidatoIdiomaManager = new CandidatoIdiomaManagerImpl();
	private Mock colaboradorIdiomaManager;
	private Mock candidatoIdiomaDao;

    protected void setUp() throws Exception
    {
        super.setUp();
        
        colaboradorIdiomaManager = new Mock(ColaboradorIdiomaManager.class);
        candidatoIdiomaManager.setColaboradorIdiomaManager((ColaboradorIdiomaManager) colaboradorIdiomaManager.proxy());
        
        candidatoIdiomaDao = new Mock(CandidatoIdiomaDao.class);
        candidatoIdiomaManager.setDao((CandidatoIdiomaDao) candidatoIdiomaDao.proxy());
    }


    public void testMontaCandidatoIdiomaByColaboradorIdioma()
    {
    	Idioma i1 = new Idioma();
    	i1.setId(1L);

    	Idioma i2 = new Idioma();
    	i2.setId(2L);

    	ColaboradorIdioma ci1 = new ColaboradorIdioma();
    	ci1.setId(1L);
    	ci1.setIdioma(i1);

    	ColaboradorIdioma ci2 = new ColaboradorIdioma();
    	ci2.setId(2L);
    	ci2.setIdioma(i2);

    	Collection<ColaboradorIdioma> colaboradorIdiomas = new ArrayList<ColaboradorIdioma>();
    	colaboradorIdiomas.add(ci1);
    	colaboradorIdiomas.add(ci2);

    	Candidato candidato = new Candidato();
    	candidato.setId(1L);

    	candidatoIdiomaDao.expects(atLeastOnce()).method("save").with(ANYTHING).will(returnValue(ci1));

    	Collection<CandidatoIdioma> candidatoIdiomas = candidatoIdiomaManager.montaCandidatoIdiomaByColaboradorIdioma(colaboradorIdiomas, candidato);

    	assertEquals(2, candidatoIdiomas.size());
    }
    
    public void testMontaListCandidatoIdioma()
    {
    	Idioma i1 = new Idioma();
    	i1.setId(1L);
    	
    	ColaboradorIdioma ci1 = new ColaboradorIdioma();
    	ci1.setId(1L);
    	ci1.setIdioma(i1);
    	
    	Collection<ColaboradorIdioma> colaboradorIdiomas = new ArrayList<ColaboradorIdioma>();
    	colaboradorIdiomas.add(ci1);
    	
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	
    	colaboradorIdiomaManager.expects(once()).method("findByColaborador").with(eq(colaborador.getId())).will(returnValue(colaboradorIdiomas));
    	
    	Collection<CandidatoIdioma> candidatoIdiomas = candidatoIdiomaManager.montaListCandidatoIdioma(colaborador.getId());
    	
    	assertEquals(1, candidatoIdiomas.size());
    }

    public void testRemoveCandidato() throws Exception
    {
    	Candidato candidato = new Candidato();
    	candidato.setId(1L);

    	candidatoIdiomaDao.expects(once()).method("removeCandidato").with(ANYTHING);

    	Exception exception = null;

    	try
		{
    		candidatoIdiomaManager.removeCandidato(candidato);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
    }

    public void testFindInCandidatos()
    {
    	Long[] ids = new Long[1];
    	ids[0] = 1L;

    	CandidatoIdioma ci = new CandidatoIdioma();
    	ci.setId(1L);

    	Collection<CandidatoIdioma> col = new ArrayList<CandidatoIdioma>();
    	col.add(ci);

    	Collection<CandidatoIdioma> colRetorno;

    	candidatoIdiomaDao.expects(once()).method("findInCandidatos").with(eq(ids)).will(returnValue(col));

    	colRetorno = candidatoIdiomaManager.findInCandidatos(ids);

    	assertEquals(col.size(), colRetorno.size());
    }

    public void testMontaIdiomasBDS()
    {
    	Candidato candidato = new Candidato();

    	CandidatoIdioma ci = new CandidatoIdioma();
    	ci.setId(1L);

		Collection<CandidatoIdioma> candidatoIdiomas = new ArrayList<CandidatoIdioma>();
		candidatoIdiomas.add(ci);

		candidatoIdiomaDao.expects(atLeastOnce()).method("save").with(ANYTHING).will(returnValue(ci));

		candidatoIdiomaManager.montaIdiomasBDS(candidatoIdiomas, candidato);
    }

    public void testFindByCandidato()
    {
    	Candidato candidato = new Candidato();
    	candidato.setId(1L);

    	Collection<CandidatoIdioma> candidatoIdiomas = new ArrayList<CandidatoIdioma>();

    	candidatoIdiomaDao.expects(once()).method("findByCandidato").with(eq(candidato.getId())).will(returnValue(candidatoIdiomas));

    	Collection<CandidatoIdioma> retorno = candidatoIdiomaManager.findByCandidato(candidato.getId());

    	assertEquals(candidatoIdiomas, retorno);
    }
}
