package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.sesmt.CandidatoEleicaoManagerImpl;
import com.fortes.rh.business.sesmt.EleicaoManager;
import com.fortes.rh.dao.sesmt.CandidatoEleicaoDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.relatorio.LinhaCedulaEleitoralRelatorio;
import com.fortes.rh.model.sesmt.CandidatoEleicao;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.test.business.MockObjectTestCaseManager;
import com.fortes.rh.test.business.TesteAutomaticoManager;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.sesmt.CandidatoEleicaoFactory;
import com.fortes.rh.test.factory.sesmt.EleicaoFactory;

public class CandidatoEleicaoManagerTest extends MockObjectTestCaseManager<CandidatoEleicaoManagerImpl> implements TesteAutomaticoManager
{
    private Mock transactionManager;
    private Mock candidatoEleicaoDao;
    private Mock eleicaoManager;
    private Mock colaboradorManager;
    private Mock areaOrganizacionalManager;

    protected void setUp() throws Exception
    {
    	manager = new CandidatoEleicaoManagerImpl();
        super.setUp();
        candidatoEleicaoDao = new Mock(CandidatoEleicaoDao.class);
        manager.setDao((CandidatoEleicaoDao) candidatoEleicaoDao.proxy());

        eleicaoManager = new Mock(EleicaoManager.class);
        manager.setEleicaoManager((EleicaoManager) eleicaoManager.proxy());

        colaboradorManager = new Mock(ColaboradorManager.class);
        manager.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());

        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        manager.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());

        transactionManager = new Mock(PlatformTransactionManager.class);
        manager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());
    }

    public void testFindByEleicao()
    {
        candidatoEleicaoDao.expects(once()).method("findByEleicao").with(ANYTHING, eq(false), ANYTHING).will(returnValue(new ArrayList<CandidatoEleicao>()));
        assertNotNull(manager.findByEleicao(null));
    }

    public void testGetColaboradoresByEleicao() throws Exception
    {
    	Colaborador candidato = ColaboradorFactory.getEntity(1L);

    	CandidatoEleicao candidatoEleicao = CandidatoEleicaoFactory.getEntity(1L);
        candidatoEleicao.setCandidato(candidato);

        Collection<CandidatoEleicao> candidatoEleicaos = new ArrayList<CandidatoEleicao>();
        candidatoEleicaos.add(candidatoEleicao);

        Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
        AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
        candidato.setAreaOrganizacional(areaOrganizacional);
        colaboradors.add(candidato);

        Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();

        candidatoEleicaoDao.expects(once()).method("findByEleicao").with(ANYTHING,eq(false), ANYTHING).will(returnValue(candidatoEleicaos));
        colaboradorManager.expects(once()).method("findByIdHistoricoAtual").with(ANYTHING).will(returnValue(colaboradors));
        areaOrganizacionalManager.expects(once()).method("findAllListAndInativas").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(areas));
        areaOrganizacionalManager.expects(once()).method("montaFamilia").with(ANYTHING).will(returnValue(areas));
        areaOrganizacionalManager.expects(atLeastOnce()).method("getAreaOrganizacional").with(ANYTHING, ANYTHING).will(returnValue(null));

        assertEquals(1, manager.getColaboradoresByEleicao(null, null).size());
    }

    public void testSaveVotosEleicao()
    {
        String[] eleitosIds = new String[]{"1"};
        String[] qtdVotos = new String[]{"100", "", "10"};
        String[] idCandidatoEleicaos = new String[]{"1", "2", "3"};//id dos candidatos, o 1 teve 100 votos e ta eleito.

        Eleicao eleicao = EleicaoFactory.getEntity(1L);

        transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
        candidatoEleicaoDao.expects(atLeastOnce()).method("setQtdVotos").with(ANYTHING, ANYTHING).isVoid();
        candidatoEleicaoDao.expects(atLeastOnce()).method("setEleito").with(ANYTHING, ANYTHING).isVoid();
        eleicaoManager.expects(once()).method("updateVotos").with(eq(eleicao)).isVoid();
        transactionManager.expects(once()).method("commit").with(ANYTHING);

        Exception exception = null;
        try
        {
            manager.saveVotosEleicao(eleitosIds, qtdVotos, idCandidatoEleicaos, eleicao);
        }
        catch (Exception e)
        {
            exception = e;
        }

        assertNull(exception);
    }

    public void testSaveVotosEleicaoException()
    {
        String[] eleitosIds = new String[]{"1"};
        String[] qtdVotos = new String[]{"100", "", "10"};
        String[] idCandidatoEleicaos = new String[]{"1", "2", "3"};//id dos candidatos, o 1 teve 100 votos e ta eleito.

        Eleicao eleicao = EleicaoFactory.getEntity(1L);

        transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
        candidatoEleicaoDao.expects(atLeastOnce()).method("setEleito").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));
        transactionManager.expects(once()).method("rollback").with(ANYTHING);

        Exception exception = null;
        try
        {
            manager.saveVotosEleicao(eleitosIds, qtdVotos, idCandidatoEleicaos, eleicao);
        }
        catch (Exception e)
        {
            exception = e;
        }

        assertNotNull(exception);
    }

    public void testSave()
    {
        String[] candidatosCheck = new String[]{"1"};
        Eleicao eleicao = EleicaoFactory.getEntity(1L);

        transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
        candidatoEleicaoDao.expects(once()).method("findByEleicao").with(ANYTHING,eq(false), ANYTHING).will(returnValue(new ArrayList<CandidatoEleicao>()));
        candidatoEleicaoDao.expects(once()).method("save").with(ANYTHING).isVoid();
        transactionManager.expects(once()).method("commit").with(ANYTHING);

        Exception exception = null;
        try
        {
            manager.save(candidatosCheck, eleicao);
        }
        catch (Exception e)
        {
            exception = e;
        }

        assertNull(exception);
    }

    public void testSaveRepetido()
    {
        String[] candidatosCheck = new String[]{"1"};
        Eleicao eleicao = EleicaoFactory.getEntity(1L);

        Eleicao eleicao1 = EleicaoFactory.getEntity(1L);
        Colaborador candidato1 = ColaboradorFactory.getEntity(1L);

        CandidatoEleicao candidatoEleicao1 = CandidatoEleicaoFactory.getEntity(1L);
        candidatoEleicao1.setEleicao(eleicao1);
        candidatoEleicao1.setCandidato(candidato1);

        Collection<CandidatoEleicao> candidatoEleicaos = new ArrayList<CandidatoEleicao>();
        candidatoEleicaos.add(candidatoEleicao1);

        transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
        candidatoEleicaoDao.expects(once()).method("findByEleicao").with(ANYTHING,eq(false), ANYTHING).will(returnValue(candidatoEleicaos));
        transactionManager.expects(once()).method("commit").with(ANYTHING);

        Exception exception = null;
        try
        {
            manager.save(candidatosCheck, eleicao);
        }
        catch (Exception e)
        {
            exception = e;
        }

        assertNull(exception);
    }

    public void testSaveException()
    {
        String[] candidatosCheck = new String[]{"1"};
        Eleicao eleicao = EleicaoFactory.getEntity(1L);

        transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
        candidatoEleicaoDao.expects(once()).method("findByEleicao").with(ANYTHING,eq(false), ANYTHING).will(returnValue(new ArrayList<CandidatoEleicao>()));
        candidatoEleicaoDao.expects(once()).method("save").with(ANYTHING).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));
        transactionManager.expects(once()).method("rollback").with(ANYTHING);

        Exception exception = null;
        try
        {
            manager.save(candidatosCheck, eleicao);
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

    	candidatoEleicaoDao.expects(once()).method("removeByEleicao").with(eq(eleicao.getId())).isVoid();

    	manager.removeByEleicao(eleicao.getId());
    }

    public void testMontaCedulas() throws Exception
    {
    	Cargo cargo1 = CargoFactory.getEntity(1L);
    	cargo1.setNome("cargo1");
    	
    	Cargo cargo2 = CargoFactory.getEntity(2L);
    	cargo2.setNome("cargo2");
    	
    	FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity(1L);
    	faixaSalarial1.setCargo(cargo1);
    	    	    	
    	FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity(2L);
    	faixaSalarial2.setCargo(cargo2);
    	
    	Colaborador candidato1 = ColaboradorFactory.getEntity(1L);
    	candidato1.setNome("nome");
    	candidato1.setNomeComercial("nome");
    	candidato1.setFaixaSalarial(faixaSalarial1);
    	
    	Colaborador candidato2 = ColaboradorFactory.getEntity(2L);
    	candidato2.setNome("nome2");
    	candidato2.setNomeComercial("nomeComercial2");
    	candidato2.setFaixaSalarial(faixaSalarial2);

    	Collection<CandidatoEleicao> candidatoEleicaos = new ArrayList<CandidatoEleicao>();
    	
    	CandidatoEleicao candidatoEleicao1 = CandidatoEleicaoFactory.getEntity(1L);
    	candidatoEleicao1.setCandidato(candidato1);
    	
    	CandidatoEleicao candidatoEleicao2 = CandidatoEleicaoFactory.getEntity(2L);
    	candidatoEleicao2.setCandidato(candidato2);
    	
    	candidatoEleicaos.add(candidatoEleicao1);
    	candidatoEleicaos.add(candidatoEleicao2);

    	Collection<LinhaCedulaEleitoralRelatorio> cedulas = manager.montaCedulas(candidatoEleicaos);
    	assertEquals(10, cedulas.size());
    	assertEquals("(  )  1 - nome - cargo1\n(  )  2 - nome2 (nomeComercial2) - cargo2\n", ((LinhaCedulaEleitoralRelatorio)cedulas.toArray()[0]).getLinhas());
    }
    
    public void testMontaCedulas_comGetCountNUll() throws Exception
    {
    	Cargo cargo1 = CargoFactory.getEntity(1L);
    	cargo1.setNome("cargo1");
    	
    	
    	FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity(1L);
    	faixaSalarial1.setCargo(cargo1);
    	
    	
    	Colaborador candidato1 = ColaboradorFactory.getEntity(1L);
    	candidato1.setNome("nome");
    	candidato1.setNomeComercial("nomeComercial");
    	candidato1.setFaixaSalarial(faixaSalarial1);
    	
    	Colaborador candidato2 = ColaboradorFactory.getEntity(2L);
    	candidato2.setNome("nome2");
    	candidato2.setNomeComercial("nomeComercial2");
    	
    	Collection<CandidatoEleicao> candidatoEleicaos = new ArrayList<CandidatoEleicao>();
    	
    	CandidatoEleicao candidatoEleicao1 = CandidatoEleicaoFactory.getEntity(1L);
    	candidatoEleicao1.setCandidato(candidato1);
    	
    	CandidatoEleicao candidatoEleicao2 = CandidatoEleicaoFactory.getEntity(2L);
    	candidatoEleicao2.setCandidato(candidato2);
    	
    	candidatoEleicaos.add(candidatoEleicao1);
    	candidatoEleicaos.add(candidatoEleicao2);
    	
    	Collection<LinhaCedulaEleitoralRelatorio> cedulas = manager.montaCedulas(candidatoEleicaos);
    	assertEquals(10, cedulas.size());
    	assertEquals("(  )  1 - nome (nomeComercial) - cargo1\n(  )  2 - nome2 (nomeComercial2) - \n", ((LinhaCedulaEleitoralRelatorio)cedulas.toArray()[0]).getLinhas());
    }
    
    
    
    public void testMontaCedulasException() throws Exception
    {
    	Collection<CandidatoEleicao> candidatoEleicaos = new ArrayList<CandidatoEleicao>();

    	Exception exception = null;
    	try
		{
    		manager.montaCedulas(candidatoEleicaos);
		}
		catch (Exception e)
		{
			exception = e;
        }
        assertNotNull(exception);
    }
    
    public void testFindByColaborador()
    {
    	Collection<CandidatoEleicao> candidatoEleicaos = Arrays.asList(CandidatoEleicaoFactory.getEntity(11L));
    	
    	candidatoEleicaoDao.expects(once()).method("findByColaborador").with(eq(1L)).will(returnValue(candidatoEleicaos));
    	
    	assertEquals(1, manager.findByColaborador(1L).size());
    }

	public void testExecutaTesteAutomaticoDoManager() {
		testeAutomatico(candidatoEleicaoDao);
	}
}