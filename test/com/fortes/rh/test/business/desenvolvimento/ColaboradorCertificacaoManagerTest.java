package com.fortes.rh.test.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.avaliacao.AvaliacaoPraticaManager;
import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorAvaliacaoPraticaManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManagerImpl;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.dao.desenvolvimento.ColaboradorCertificacaoDao;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoPraticaFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorAvaliacaoPraticaFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorCertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;

public class ColaboradorCertificacaoManagerTest extends MockObjectTestCase
{
	private ColaboradorCertificacaoManagerImpl colaboradorCertificacaoManager = new ColaboradorCertificacaoManagerImpl();
	private Mock colaboradorCertificacaoDao;
	private Mock colaboradorAvaliacaoPraticaManager;
	private Mock colaboradorTurmaManager;
	private Mock certificacaoManager;
	private Mock avaliacaoPraticaManager;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        colaboradorCertificacaoDao = new Mock(ColaboradorCertificacaoDao.class);
        colaboradorCertificacaoManager.setDao((ColaboradorCertificacaoDao) colaboradorCertificacaoDao.proxy());
        
        colaboradorAvaliacaoPraticaManager = new Mock(ColaboradorAvaliacaoPraticaManager.class);
        colaboradorCertificacaoManager.setColaboradorAvaliacaoPraticaManager((ColaboradorAvaliacaoPraticaManager) colaboradorAvaliacaoPraticaManager.proxy());
        
        avaliacaoPraticaManager = new Mock(AvaliacaoPraticaManager.class);
        colaboradorCertificacaoManager.setAvaliacaoPraticaManager((AvaliacaoPraticaManager) avaliacaoPraticaManager.proxy());
        
        colaboradorTurmaManager = new Mock(ColaboradorTurmaManager.class);
        certificacaoManager = new Mock(CertificacaoManager.class);
        
        Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
    }

	public void testFindAllSelect()
	{
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = ColaboradorCertificacaoFactory.getCollection(1L);

		colaboradorCertificacaoDao.expects(once()).method("findAll").will(returnValue(colaboradorCertificacaos));
		assertEquals(colaboradorCertificacaos, colaboradorCertificacaoManager.findAll());
	}
	
	public void testMontaRelatorioColaboradoresNasCertificacoes()
	{
		MockSpringUtil.mocks.put("colaboradorTurmaManager", colaboradorTurmaManager);
		MockSpringUtil.mocks.put("certificacaoManager", certificacaoManager);
		
		Certificacao certificacao1 = CertificacaoFactory.getEntity(1L);
		certificacao1.setNome("cert. admin");
		
		Certificacao certificacao2 = CertificacaoFactory.getEntity(1L);
		certificacao2.setNome("cert. Dev");

		Collection<Certificacao> certificacoes = new ArrayList<Certificacao>();
		certificacoes.add(certificacao1);
		
		Colaborador colab1 = ColaboradorFactory.getEntity(1L);
		colab1.setNome("Astrogildis");
		
		Colaborador colab2 = ColaboradorFactory.getEntity(1L);
		colab2.setNome("Margnoris");
		
		ColaboradorCertificacao colaboradorCertificacao1 = ColaboradorCertificacaoFactory.getEntity(1L);
		colaboradorCertificacao1.setColaborador(colab1);
		colaboradorCertificacao1.setData(DateUtil.criarDataMesAno(1, 1, 2016));
		colaboradorCertificacao1.setCertificacao(certificacao1);
		
		ColaboradorCertificacao colaboradorCertificacao2 = ColaboradorCertificacaoFactory.getEntity(1L);
		colaboradorCertificacao2.setColaborador(colab2);
		colaboradorCertificacao2.setData(DateUtil.criarDataMesAno(1, 1, 2016));
		colaboradorCertificacao2.setCertificacao(certificacao2);
		
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = new ArrayList<ColaboradorCertificacao>();
		colaboradorCertificacaos.add(colaboradorCertificacao1);
		colaboradorCertificacaos.add(colaboradorCertificacao2);
		
		Curso curso = CursoFactory.getEntity(1L);
		
		Turma turma = TurmaFactory.getEntity(1L);
		turma.setDataPrevIni(DateUtil.criarDataMesAno(1, 3, 2015));
		turma.setDataPrevFim(DateUtil.criarDataMesAno(1, 4, 2015));
		
		ColaboradorTurma colaboradorTurma1 = ColaboradorTurmaFactory.getEntity(1L);
		colaboradorTurma1.setCurso(curso);
		colaboradorTurma1.setTurma(turma);
		colaboradorTurma1.setColaborador(colab1);
		colaboradorTurma1.setAprovado(true);
		
		Collection<ColaboradorTurma> colaboradorTurmas1 = new ArrayList<ColaboradorTurma>();
		colaboradorTurmas1.add(colaboradorTurma1);
		
		ColaboradorTurma colaboradorTurma2 = ColaboradorTurmaFactory.getEntity(2L);
		colaboradorTurma2.setCurso(curso);
		colaboradorTurma2.setTurma(turma);
		colaboradorTurma2.setColaborador(colab1);
		colaboradorTurma2.setAprovado(true);
		
		Collection<ColaboradorTurma> colaboradorTurmas2 = new ArrayList<ColaboradorTurma>();
		colaboradorTurmas2.add(colaboradorTurma2);
		
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity(1L);
		avaliacaoPratica.setNotaMinima(90.0);
		avaliacaoPratica.setTitulo("Beber Cachaça");
		
		Collection<AvaliacaoPratica> avaliacoesPraticas = new ArrayList<AvaliacaoPratica>();
		avaliacoesPraticas.add(avaliacaoPratica);
		
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica1 = ColaboradorAvaliacaoPraticaFactory.getEntity(1L);
		colaboradorAvaliacaoPratica1.setColaborador(colab1);
		colaboradorAvaliacaoPratica1.setAvaliacaoPratica(avaliacaoPratica);
		colaboradorAvaliacaoPratica1.setNota(90.0);
		
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica2 = ColaboradorAvaliacaoPraticaFactory.getEntity(2L);
		colaboradorAvaliacaoPratica2.setColaborador(colab2);
		colaboradorAvaliacaoPratica1.setAvaliacaoPratica(avaliacaoPratica);
		colaboradorAvaliacaoPratica1.setNota(90.0);
		
		Collection<ColaboradorAvaliacaoPratica> avaliacoesPraticasDoColaboradorRealizadas = new ArrayList<ColaboradorAvaliacaoPratica>();
		avaliacoesPraticasDoColaboradorRealizadas.add(colaboradorAvaliacaoPratica1);
		avaliacoesPraticasDoColaboradorRealizadas.add(colaboradorAvaliacaoPratica2);
		
		colaboradorCertificacaoDao.expects(once()).method("colaboradoresQueParticipaDoCertificado").will(returnValue(colaboradorCertificacaos));
		colaboradorTurmaManager.expects(once()).method("findByColaboradorIdAndCertificacaoIdAndColabCertificacaoId").will(returnValue(colaboradorTurmas1));
		colaboradorTurmaManager.expects(once()).method("findByColaboradorIdAndCertificacaoIdAndColabCertificacaoId").will(returnValue(colaboradorTurmas2));
		avaliacaoPraticaManager.expects(atLeastOnce()).method("findByCertificacaoId").will(returnValue(avaliacoesPraticas));
		colaboradorAvaliacaoPraticaManager.expects(atLeastOnce()).method("findByColaboradorIdAndCertificacaoId").will(returnValue(avaliacoesPraticasDoColaboradorRealizadas));
		certificacaoManager.expects(once()).method("findById").will(returnValue(certificacoes));
		
		Date dataIni = DateUtil.criarDataMesAno(1, 1, 2015);
		Date dataFim = DateUtil.criarDataMesAno(1, 1, 2016);
		
		Collection<ColaboradorCertificacao> colaboradoresNasCertificacoes = colaboradorCertificacaoManager.montaRelatorioColaboradoresNasCertificacoes(dataIni, dataFim, true, true, null, null, null, new Long[]{colaboradorCertificacao1.getId()}, null);
		
		assertEquals(4, colaboradoresNasCertificacoes.size());
		
		ColaboradorCertificacao result2 = (ColaboradorCertificacao) colaboradoresNasCertificacoes.toArray()[1];
		
		assertEquals("Avaliação Prática: Beber Cachaça", result2.getNomeCurso());
	}
	
	public void testFindColaboradorCertificadoInfomandoSeEUltimaCertificacao() {
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaboradorCertificacaoDao.expects(once()).method("findColaboradorCertificadoInfomandoSeEUltimaCertificacao").with(eq(colaboradorCertificacao.getId()), eq(colaborador.getId()), eq(certificacao.getId())).will(returnValue(colaboradorCertificacao));
		assertEquals(colaboradorCertificacao, colaboradorCertificacaoManager.findColaboradorCertificadoInfomandoSeEUltimaCertificacao(colaboradorCertificacao.getId(), colaborador.getId(), certificacao.getId()));
	}
}
