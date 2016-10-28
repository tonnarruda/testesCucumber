package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.sesmt.CandidatoEleicaoManager;
import com.fortes.rh.business.sesmt.ComissaoEleicaoManager;
import com.fortes.rh.business.sesmt.ComissaoManager;
import com.fortes.rh.business.sesmt.EleicaoManagerImpl;
import com.fortes.rh.business.sesmt.EtapaProcessoEleitoralManager;
import com.fortes.rh.dao.sesmt.EleicaoDao;
import com.fortes.rh.exception.RemoveCascadeException;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.relatorio.ParticipacaoColaboradorCipa;
import com.fortes.rh.model.sesmt.CandidatoEleicao;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.model.sesmt.EtapaProcessoEleitoral;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.CandidatoEleicaoFactory;
import com.fortes.rh.test.factory.sesmt.EleicaoFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;

public class EleicaoManagerTest extends MockObjectTestCase
{
	private EleicaoManagerImpl eleicaoManager = new EleicaoManagerImpl();
	private Mock eleicaoDao = null;
	private Mock transactionManager;
	private Mock comissaoEleicaoManager;
	private Mock comissaoManager;
	private Mock candidatoEleicaoManager;
	private Mock etapaProcessoEleitoralManager;

	protected void setUp() throws Exception
    {
        super.setUp();
        eleicaoDao = new Mock(EleicaoDao.class);
        eleicaoManager.setDao((EleicaoDao) eleicaoDao.proxy());

        transactionManager = new Mock(PlatformTransactionManager.class);
        eleicaoManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());

        comissaoEleicaoManager = new Mock(ComissaoEleicaoManager.class);
        eleicaoManager.setComissaoEleicaoManager((ComissaoEleicaoManager) comissaoEleicaoManager.proxy());

        etapaProcessoEleitoralManager = new Mock(EtapaProcessoEleitoralManager.class);
        eleicaoManager.setEtapaProcessoEleitoralManager((EtapaProcessoEleitoralManager) etapaProcessoEleitoralManager.proxy());

        candidatoEleicaoManager = new Mock(CandidatoEleicaoManager.class);
		MockSpringUtil.mocks.put("candidatoEleicaoManager", candidatoEleicaoManager);

		comissaoManager = new Mock(ComissaoManager.class);
		eleicaoManager.setComissaoManager((ComissaoManager) comissaoManager.proxy());

		Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
    }

	protected void tearDown() throws Exception
	{
		Mockit.restoreAllOriginalDefinitions();

		super.tearDown();
	}

	public void testFindAllSelect() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Eleicao eleicao = EleicaoFactory.getEntity(1L);
		eleicao.setEmpresa(empresa);

		Collection<Eleicao> eleicaos = new ArrayList<Eleicao>();
		eleicaos.add(eleicao);

		eleicaoDao.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(eleicaos));

		assertEquals(1, eleicaoManager.findAllSelect(empresa.getId()).size());
	}

	public void testFindByIdProjection() throws Exception
	{
		Eleicao eleicao = EleicaoFactory.getEntity(1L);
		eleicaoDao.expects(once()).method("findByIdProjection").with(eq(eleicao.getId())).will(returnValue(eleicao));
		assertEquals(eleicao, eleicaoManager.findByIdProjection(1L));
	}
	
	public void testRemoveCascade()
	{
		Eleicao eleicao = EleicaoFactory.getEntity(1L);

		comissaoManager.expects(once()).method("getCount").with(ANYTHING,ANYTHING).will(returnValue(0));
		candidatoEleicaoManager.expects(once()).method("getCount").with(ANYTHING,ANYTHING).will(returnValue(0));

        transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
        //candidatoEleicaoManager.expects(once()).method("removeByEleicao").with(eq(eleicao.getId())).isVoid();
        comissaoEleicaoManager.expects(once()).method("removeByEleicao").with(eq(eleicao.getId())).isVoid();
        etapaProcessoEleitoralManager.expects(once()).method("removeByEleicao").with(eq(eleicao.getId())).isVoid();
        eleicaoDao.expects(once()).method("remove").with(eq(eleicao.getId())).isVoid();
        transactionManager.expects(once()).method("commit").with(ANYTHING);

        Exception exception = null;
        try
        {
            eleicaoManager.removeCascade(eleicao.getId());
        }
        catch (Exception e)
        {
            exception = e;
        }

        assertNull(exception);
	}

	public void testRemoveCascadeException()
	{
		Eleicao eleicao = EleicaoFactory.getEntity(1L);

		comissaoManager.expects(once()).method("getCount").with(ANYTHING,ANYTHING).will(returnValue(0));
		candidatoEleicaoManager.expects(once()).method("getCount").with(ANYTHING,ANYTHING).will(returnValue(0));

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
		//candidatoEleicaoManager.expects(once()).method("removeByEleicao").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));
		comissaoEleicaoManager.expects(once()).method("removeByEleicao").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));
		transactionManager.expects(once()).method("rollback").with(ANYTHING);

		Exception exception = null;
		try
		{
			eleicaoManager.removeCascade(eleicao.getId());
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);

		// Exceção: existe comissão associada
		comissaoManager.expects(once()).method("getCount").with(ANYTHING,ANYTHING).will(returnValue(1));
		exception = null;
		try
		{
			eleicaoManager.removeCascade(eleicao.getId());
		}
		catch (RemoveCascadeException e)
		{
			exception = e;
		}
		catch(Exception e) {}

		assertNotNull(exception);

		// Exceção: existe candidato
		comissaoManager.expects(once()).method("getCount").with(ANYTHING,ANYTHING).will(returnValue(0));
		candidatoEleicaoManager.expects(once()).method("getCount").with(ANYTHING,ANYTHING).will(returnValue(1));
		exception = null;
		try
		{
			eleicaoManager.removeCascade(eleicao.getId());
		}
		catch (RemoveCascadeException e)
		{
			exception = e;
		}
		catch(Exception e) {}

		assertNotNull(exception);
	}

	public void testUpdateVotos()
	{
		Eleicao eleicao = new Eleicao();
		eleicao.setQtdVotoBranco(null);
		eleicao.setQtdVotoNulo(null);
		eleicaoDao.expects(once()).method("updateVotos").with(eq(eleicao)).isVoid();
		eleicaoManager.updateVotos(eleicao);
	}
	
	public void testUpdate()
	{
		Date data = new Date();
		Eleicao eleicao = EleicaoFactory.getEntity(1L);
		eleicao.setPosse(data);
		
		Eleicao eleicaoTemp = EleicaoFactory.getEntity(2L);
		eleicaoTemp.setPosse(data);
		
		eleicaoDao.expects(once()).method("findByIdProjection").with(eq(eleicao.getId())).will(returnValue(eleicaoTemp));
		eleicaoDao.expects(once()).method("update").with(eq(eleicao)).isVoid();
		
		eleicaoManager.update(eleicao);
	}
	
	public void testUpdatePosseDiferente()
	{
		Eleicao eleicao = EleicaoFactory.getEntity(1L);
		eleicao.setPosse(new Date());
		
		Eleicao eleicaoTemp = EleicaoFactory.getEntity(2L);
		eleicaoTemp.setPosse(DateUtil.criarDataMesAno(01, 02, 2000));
		
		eleicaoDao.expects(once()).method("findByIdProjection").with(eq(eleicao.getId())).will(returnValue(eleicaoTemp));
		
		etapaProcessoEleitoralManager.expects(once()).method("findAllSelect").with(eq (null), eq(eleicao.getId())).will(returnValue(new ArrayList<EtapaProcessoEleitoral>()));
		etapaProcessoEleitoralManager.expects(once()).method("updatePrazos").with(ANYTHING, eq(eleicao.getPosse())).isVoid();
		
		eleicaoDao.expects(once()).method("update").with(eq(eleicao)).isVoid();
		
		eleicaoManager.update(eleicao);
	}

	
	public void testGetParticipacoesDeColaboradorEmEleicoes()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		
		Eleicao eleicao = EleicaoFactory.getEntity(2L);
		eleicao.setInscricaoCandidatoIni(new Date());
		
		CandidatoEleicao candidatoEleicao = CandidatoEleicaoFactory.getEntity(21L);
		candidatoEleicao.setEleicao(eleicao);
		candidatoEleicao.setCandidato(colaborador);
		
		CandidatoEleicao candidatoEleicao2 = CandidatoEleicaoFactory.getEntity(22L);
		candidatoEleicao2.setEleicao(eleicao);
		candidatoEleicao2.setCandidato(colaborador);
		
		Collection<CandidatoEleicao> candidatoEleicaos = Arrays.asList(candidatoEleicao, candidatoEleicao2);
		
		candidatoEleicaoManager.expects(once()).method("findByColaborador").will(returnValue(candidatoEleicaos));
		
		Collection<ParticipacaoColaboradorCipa> participacoes = eleicaoManager.getParticipacoesDeColaboradorEmEleicoes(colaborador.getId());
		
		ParticipacaoColaboradorCipa p1 = (ParticipacaoColaboradorCipa) participacoes.toArray()[0];
		assertEquals("Inscrito como candidato", p1.getDescricao());
		assertEquals("Candidato", p1.getFuncao());
		assertEquals(colaborador, p1.getColaborador());
		assertEquals(eleicao.getInscricaoCandidatoIni(), p1.getData());
	}
	
	public void testSetCandidatosGrafico()
	{
		Eleicao eleicao = EleicaoFactory.getEntity(2L);
		eleicao.setSomaVotos(100);
		
		CandidatoEleicao candidatoEleicao1 = CandidatoEleicaoFactory.getEntity(1L);
		candidatoEleicao1.setQtdVoto(4);
		candidatoEleicao1.setPercentualVoto(4.0);
		CandidatoEleicao candidatoEleicao2 = CandidatoEleicaoFactory.getEntity(2L);
		candidatoEleicao2.setQtdVoto(5);
		candidatoEleicao2.setPercentualVoto(12.0);
		CandidatoEleicao candidatoEleicao3 = CandidatoEleicaoFactory.getEntity(2L);
		candidatoEleicao3.setQtdVoto(2);
		candidatoEleicao3.setPercentualVoto(5.0);
		
		Collection<CandidatoEleicao> candidatoEleicaos = new ArrayList<CandidatoEleicao>();
		candidatoEleicaos.add(candidatoEleicao1);
		candidatoEleicaos.add(candidatoEleicao2);
		candidatoEleicaos.add(candidatoEleicao3);
		
		eleicao.setCandidatoEleicaos(candidatoEleicaos);
		
		assertEquals(4, eleicaoManager.setCandidatosGrafico(eleicao).size());
	
	}
}