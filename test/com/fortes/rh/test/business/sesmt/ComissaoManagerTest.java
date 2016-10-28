package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.persistence.PersistenceException;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.sesmt.ComissaoManagerImpl;
import com.fortes.rh.business.sesmt.ComissaoMembroManager;
import com.fortes.rh.business.sesmt.ComissaoPeriodoManager;
import com.fortes.rh.business.sesmt.ComissaoPlanoTrabalhoManager;
import com.fortes.rh.business.sesmt.ComissaoReuniaoManager;
import com.fortes.rh.business.sesmt.EleicaoManager;
import com.fortes.rh.dao.sesmt.ComissaoDao;
import com.fortes.rh.model.dicionario.FuncaoComissao;
import com.fortes.rh.model.dicionario.TipoMembroComissao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.relatorio.ParticipacaoColaboradorCipa;
import com.fortes.rh.model.sesmt.CandidatoEleicao;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.ComissaoPeriodo;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.CandidatoEleicaoFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoMembroFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoPeriodoFactory;
import com.fortes.rh.test.factory.sesmt.EleicaoFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;

public class ComissaoManagerTest extends MockObjectTestCase
{
	private ComissaoManagerImpl comissaoManager = new ComissaoManagerImpl();
	private Mock comissaoDao;
	private Mock comissaoPeriodoManager;
	private Mock comissaoReuniaoManager;
	private Mock comissaoPlanoTrabalhoManager;
	private Mock comissaoMembroManager;
	private Mock eleicaoManager;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        comissaoDao = new Mock(ComissaoDao.class);
        comissaoManager.setDao((ComissaoDao) comissaoDao.proxy());

        comissaoPeriodoManager = new Mock(ComissaoPeriodoManager.class);
        comissaoManager.setComissaoPeriodoManager((ComissaoPeriodoManager)comissaoPeriodoManager.proxy());
        
        comissaoReuniaoManager = new Mock(ComissaoReuniaoManager.class);
        comissaoManager.setComissaoReuniaoManager((ComissaoReuniaoManager) comissaoReuniaoManager.proxy());
        
        comissaoPlanoTrabalhoManager = new Mock(ComissaoPlanoTrabalhoManager.class);
        comissaoManager.setComissaoPlanoTrabalhoManager((ComissaoPlanoTrabalhoManager) comissaoPlanoTrabalhoManager.proxy());
        
        comissaoMembroManager = mock(ComissaoMembroManager.class);
        comissaoManager.setComissaoMembroManager((ComissaoMembroManager) comissaoMembroManager.proxy());
        
        eleicaoManager = mock(EleicaoManager.class);
        MockSpringUtil.mocks.put("eleicaoManager", eleicaoManager);
        
        Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
    }

	public void testFindByEleicao()
	{
		Eleicao eleicao = EleicaoFactory.getEntity(1L);
		comissaoDao.expects(once()).method("findByEleicao").with(eq(eleicao.getId())).will(returnValue(new ArrayList<Comissao>()));

		comissaoManager.findByEleicao(eleicao.getId());
	}

	public void testSave()
	{
		Eleicao eleicao = EleicaoFactory.getEntity(2L);
		Comissao comissao = ComissaoFactory.getEntity(1L);
		comissao.setEleicao(eleicao);

		comissaoDao.expects(once()).method("save").with(eq(comissao)).will(returnValue(comissao));
		comissaoPeriodoManager.expects(once()).method("save").with(eq(comissao.getId()), eq(comissao.getEleicao().getId()), ANYTHING).isVoid();

		comissaoManager.save(comissao);
	}

	public void testFindByIdProjection()
	{
		comissaoDao.expects(once()).method("findByIdProjection").with(eq(1L)).will(returnValue(new Comissao()));
		comissaoManager.findByIdProjection(1L);
	}

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		comissaoDao.expects(once()).method("findAllSelect").with(eq(1L)).will(returnValue(new ArrayList<Comissao>()));
		comissaoManager.findAllSelect(empresaId);
	}
	
	public void testRemove()
	{
		Long comissaoId = 12L;
		
		comissaoPeriodoManager.expects(once()).method("removeByComissao");
		comissaoReuniaoManager.expects(once()).method("removeByComissao");
		comissaoPlanoTrabalhoManager.expects(once()).method("removeByComissao");
		
		comissaoDao.expects(once()).method("remove").with(eq(comissaoId));

		Exception e = null;
		
		try
		{
			comissaoManager.remove(comissaoId);
		} catch(PersistenceException pe)
		{
			e = pe;
		}
		
		assertNull(e);

	}
	
	public void testRemoveException()
	{
		Long comissaoId = 12L;
		
		comissaoPeriodoManager.expects(once()).method("removeByComissao");
		comissaoReuniaoManager.expects(once()).method("removeByComissao");
		comissaoPlanoTrabalhoManager.expects(once()).method("removeByComissao");
		
		comissaoDao.expects(once()).method("remove").with(eq(comissaoId)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		
		Exception e = null;
		
		try
		{
			comissaoManager.remove(comissaoId);
		} catch(Exception pe)
		{
			e = pe;
		}
		
		assertNotNull(e);
	}
	
	public void testRemoveByEleicao()
	{
		Collection<Comissao> comissaos = new ArrayList<Comissao>();
		comissaos.add(ComissaoFactory.getEntity(2L));
		comissaoDao.expects(once()).method("findByEleicao").with(eq(1L)).will(returnValue(comissaos));
		
		comissaoPeriodoManager.expects(once()).method("removeByComissao");
		comissaoReuniaoManager.expects(once()).method("removeByComissao");
		comissaoPlanoTrabalhoManager.expects(once()).method("removeByComissao");
		comissaoDao.expects(once()).method("remove").with(eq(2L));
		
		comissaoManager.removeByEleicao(1L);
	}
	
	public void testValidaData()
	{
		Date data = new Date();
		
		Comissao comissao = ComissaoFactory.getEntity(1L);
		comissao.setDataIni(data);
		comissao.setDataFim(data);
		comissaoDao.expects(once()).method("findByIdProjection").will(returnValue(comissao));
		
		assertTrue(comissaoManager.validaData(data, 1L));
	}
	
	public void testUpdateTextosComunicados()
	{
		Comissao comissao = ComissaoFactory.getEntity(1L);
		comissao.setAtaPosseTexto1("Texto da Ata ...");
		comissaoDao.expects(once()).method("updateTextosComunicados").will(returnValue(true));

		assertTrue(comissaoManager.updateTextosComunicados(comissao));
	}
	
	public void testUpdateTextosComunicadosExceptionNull()
	{
		Comissao comissao = null;
		assertFalse(comissaoManager.updateTextosComunicados(comissao));
	}
	
	public void testGetParticipacoesDeColaboradorNaCipa()
	{
		Long colaboradorId = 1L;
		
		Colaborador colaborador = ColaboradorFactory.getEntity(colaboradorId);
		
		Date dataInscricaoCandidato = DateUtil.criarDataMesAno(7,6,2010); 
		Date dataMembroCipa = DateUtil.criarDataMesAno(8,6,2010); 
		
		// Participação como candidato
		CandidatoEleicao candidatoEleicao = CandidatoEleicaoFactory.getEntity(23L);
		candidatoEleicao.setProjectionEleicaoInscricaoCandidatoIni(dataInscricaoCandidato);
		candidatoEleicao.setCandidato(colaborador);
		//Encapsula a participação
		ParticipacaoColaboradorCipa participacaoEleicao = new ParticipacaoColaboradorCipa(candidatoEleicao);
		Collection<ParticipacaoColaboradorCipa> participacaoEmEleicoes = new ArrayList<ParticipacaoColaboradorCipa>();  
		participacaoEmEleicoes.add(participacaoEleicao);
		
		// Participação como membro CIPA
		ComissaoMembro comissaoMembro = ComissaoMembroFactory.getEntity(22L);
		comissaoMembro.setColaborador(colaborador);
		comissaoMembro.setFuncao(FuncaoComissao.PRESIDENTE);
		comissaoMembro.setProjectionComissaoPeriodoAPartirDe(dataMembroCipa);
		Collection<ComissaoMembro> comissaoMembros = Arrays.asList(comissaoMembro);
		//Encapsula a participação
		ParticipacaoColaboradorCipa participacaoComissao = new ParticipacaoColaboradorCipa(comissaoMembro);
		
		// Resultado - As duas participações juntas
		Collection<ParticipacaoColaboradorCipa> participacoes = new ArrayList<ParticipacaoColaboradorCipa>();
		participacoes.add(participacaoEleicao);
		participacoes.add(participacaoComissao);
		
		eleicaoManager.expects(once()).method("getParticipacoesDeColaboradorEmEleicoes").with(eq(1L)).will(returnValue(participacaoEmEleicoes));
		comissaoMembroManager.expects(once()).method("findByColaborador").with(eq(1L)).will(returnValue(comissaoMembros));
		
		comissaoManager.getParticipacoesDeColaboradorNaCipa(colaboradorId);
	}
	
	
	public void testMontaAtaPosse()
	{
		Colaborador colaborador = new Colaborador();
		colaborador.setId(1L);
		
		Comissao comissao = ComissaoFactory.getEntity(1L);
		comissao.setAtaPosseTexto1("teste1");
		comissao.setAtaPosseTexto2("teste2");

		ComissaoPeriodo comissaoPeriodo1 = ComissaoPeriodoFactory.getEntity(1L);
		comissaoPeriodo1.setComissao(comissao);

		ComissaoMembro comissaoMembro = ComissaoMembroFactory.getEntity(1L);
		comissaoMembro.setComissaoPeriodo(comissaoPeriodo1);
		comissaoMembro.setColaborador(colaborador);
		
		Collection<ComissaoMembro> membrosPresentes = new ArrayList<ComissaoMembro>();
		membrosPresentes.add(comissaoMembro);
		Collection<ComissaoMembro> membrosEleitos = new ArrayList<ComissaoMembro>();
		membrosEleitos.add(comissaoMembro);
		Collection<ComissaoMembro> membrosIndicados = new ArrayList<ComissaoMembro>();
		membrosIndicados.add(comissaoMembro);
		
		comissaoMembroManager.expects(once()).method("findByComissao").with(eq(comissao.getId()),eq(null)).will(returnValue(membrosPresentes));
		comissaoMembroManager.expects(once()).method("findByComissao").with(eq(1L),eq(TipoMembroComissao.ELEITO)).will(returnValue(membrosEleitos));
		comissaoMembroManager.expects(once()).method("findByComissao").with(eq(1L),eq(TipoMembroComissao.INDICADO_EMPRESA)).will(returnValue(membrosIndicados));
		
		assertNotNull(comissaoManager.montaAtaPosse(comissao));
		
	}
}