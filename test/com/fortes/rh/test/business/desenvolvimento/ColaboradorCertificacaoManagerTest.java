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
		
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		certificacao.setNome("cert. admin");
		Collection<Certificacao> certificacoes = new ArrayList<Certificacao>();
		certificacoes.add(certificacao);
		
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity(1L);
		colaboradorCertificacao.setColaborador(ColaboradorFactory.getEntity(1L));
		colaboradorCertificacao.setData(DateUtil.criarDataMesAno(1, 1, 2016));
		colaboradorCertificacao.setCertificacao(certificacao);
		
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = new ArrayList<ColaboradorCertificacao>();
		colaboradorCertificacaos.add(colaboradorCertificacao);
		
		Curso curso = CursoFactory.getEntity(1L);
		
		Turma turma = TurmaFactory.getEntity(1L);
		turma.setDataPrevIni(DateUtil.criarDataMesAno(1, 3, 2015));
		turma.setDataPrevFim(DateUtil.criarDataMesAno(1, 4, 2015));
		
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
		colaboradorTurma.setCurso(curso);
		colaboradorTurma.setTurma(turma);
		colaboradorTurma.setAprovado(true);
		
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmas.add(colaboradorTurma);
		
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity(1L);
		avaliacaoPratica.setNotaMinima(90.0);
		avaliacaoPratica.setTitulo("Beber Cachaça");
		
		Collection<AvaliacaoPratica> avaliacoesPraticas = new ArrayList<AvaliacaoPratica>();
		avaliacoesPraticas.add(avaliacaoPratica);
		
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = ColaboradorAvaliacaoPraticaFactory.getEntity(1L);
		colaboradorAvaliacaoPratica.setAvaliacaoPratica(avaliacaoPratica);
		colaboradorAvaliacaoPratica.setNota(90.0);
		
		Collection<ColaboradorAvaliacaoPratica> avaliacoesPraticasDoColaboradorRealizadas = new ArrayList<ColaboradorAvaliacaoPratica>();
		avaliacoesPraticasDoColaboradorRealizadas.add(colaboradorAvaliacaoPratica);
		
		colaboradorCertificacaoDao.expects(once()).method("colaboradoresQueParticipaDoCertificado").will(returnValue(colaboradorCertificacaos));
		colaboradorTurmaManager.expects(once()).method("findByColaboradorIdAndCertificacaoIdAndColabCertificacaoId").will(returnValue(colaboradorTurmas));
		avaliacaoPraticaManager.expects(once()).method("findByCertificacaoId").will(returnValue(avaliacoesPraticas));
		colaboradorAvaliacaoPraticaManager.expects(once()).method("findByColaboradorIdAndCertificacaoId").will(returnValue(avaliacoesPraticasDoColaboradorRealizadas));
		certificacaoManager.expects(once()).method("findById").will(returnValue(certificacoes));
		
		Date dataIni = DateUtil.criarDataMesAno(1, 1, 2015);
		Date dataFim = DateUtil.criarDataMesAno(1, 1, 2016);
		
		Collection<ColaboradorCertificacao> colaboradoresNasCertificacoes = colaboradorCertificacaoManager.montaRelatorioColaboradoresNasCertificacoes(dataIni, dataFim, true, true, null, null, null, new Long[]{colaboradorCertificacao.getId()}, null);
		
		assertEquals(2, colaboradoresNasCertificacoes.size());
		
		ColaboradorCertificacao result2 = (ColaboradorCertificacao) colaboradoresNasCertificacoes.toArray()[1];
		
		assertEquals("Avaliação Prática: Beber Cachaça", result2.getNomeCurso());
	}
}
