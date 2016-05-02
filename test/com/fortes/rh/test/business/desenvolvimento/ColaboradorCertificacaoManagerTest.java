package com.fortes.rh.test.business.desenvolvimento;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateTemplate;

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
import com.fortes.rh.test.util.mockObjects.MockHibernateTemplate;
import com.fortes.rh.test.util.mockObjects.MockSpringUtilJUnit4;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;

public class ColaboradorCertificacaoManagerTest
{
	private ColaboradorCertificacaoManagerImpl colaboradorCertificacaoManager = new ColaboradorCertificacaoManagerImpl();
	private ColaboradorCertificacaoDao colaboradorCertificacaoDao;
	private ColaboradorAvaliacaoPraticaManager colaboradorAvaliacaoPraticaManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private CertificacaoManager certificacaoManager;
	private AvaliacaoPraticaManager avaliacaoPraticaManager;
	
	@Before
	public void setUp() throws Exception
    {
        colaboradorCertificacaoDao = mock(ColaboradorCertificacaoDao.class);
        colaboradorCertificacaoManager.setDao(colaboradorCertificacaoDao);
        
        colaboradorAvaliacaoPraticaManager = mock(ColaboradorAvaliacaoPraticaManager.class);
        colaboradorCertificacaoManager.setColaboradorAvaliacaoPraticaManager(colaboradorAvaliacaoPraticaManager);
        
        avaliacaoPraticaManager = mock(AvaliacaoPraticaManager.class);
        colaboradorCertificacaoManager.setAvaliacaoPraticaManager(avaliacaoPraticaManager);
        
        colaboradorTurmaManager = mock(ColaboradorTurmaManager.class);
        MockSpringUtilJUnit4.mocks.put("colaboradorTurmaManager", colaboradorTurmaManager);

        certificacaoManager = mock(CertificacaoManager.class);
        MockSpringUtilJUnit4.mocks.put("certificacaoManager", certificacaoManager);
        
        Mockit.redefineMethods(SpringUtil.class, MockSpringUtilJUnit4.class);
        Mockit.redefineMethods(HibernateTemplate.class, MockHibernateTemplate.class);
    }

	@Test
	public void testFindAllSelect()
	{
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = ColaboradorCertificacaoFactory.getCollection(1L);

		when(colaboradorCertificacaoDao.findAll()).thenReturn(colaboradorCertificacaos);
		assertEquals(colaboradorCertificacaos, colaboradorCertificacaoManager.findAll());
	}

	@Test
	public void testMontaRelatorioColaboradoresNasCertificacoes() throws CloneNotSupportedException
	{
		Certificacao certificacao1 = CertificacaoFactory.getEntity(1L);
		certificacao1.setNome("cert. admin");
		Certificacao certificacao2 = CertificacaoFactory.getEntity(1L);
		certificacao2.setNome("cert. Dev");
		Collection<Certificacao> certificacoes = new ArrayList<Certificacao>();
		certificacoes.add(certificacao1);
		
		Colaborador colab1 = ColaboradorFactory.getEntity(1L);
		colab1.setNome("Astrogildis");
		Colaborador colab2 = ColaboradorFactory.getEntity(2L);
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
		Collection<Curso> cursos = new ArrayList<Curso>();
		cursos.add(curso);
		
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

		Map<Long, Collection<AvaliacaoPratica>> mapAvaliacoesPraticas = new HashMap<Long, Collection<AvaliacaoPratica>>();
		mapAvaliacoesPraticas.put(certificacao1.getId(), avaliacoesPraticas);
		mapAvaliacoesPraticas.put(certificacao2.getId(), avaliacoesPraticas);

		Map<Long, Collection<ColaboradorTurma>> mapColaboradoresTurmas = new HashMap<Long, Collection<ColaboradorTurma>>();
		mapColaboradoresTurmas.put(colab1.getId(),colaboradorTurmas2);
		mapColaboradoresTurmas.put(colab2.getId(),colaboradorTurmas2);
		
		Map<Long, Collection<ColaboradorAvaliacaoPratica>> mapColaboradorAvaliacoesPraticas = new HashMap<Long, Collection<ColaboradorAvaliacaoPratica>>();
		mapColaboradorAvaliacoesPraticas.put(colab1.getId(), avaliacoesPraticasDoColaboradorRealizadas);
		
		Long[] certificacaoesIds = new Long[]{certificacao1.getId()};
		Long[] colaboradoresIds = new Long[]{colab1.getId()};
		Date dataIni = DateUtil.criarDataMesAno(1, 1, 2015);
		Date dataFim = DateUtil.criarDataMesAno(1, 1, 2016);
		
		when(avaliacaoPraticaManager.findMapByCertificacaoId(certificacaoesIds)).thenReturn(mapAvaliacoesPraticas);
		when(certificacaoManager.findCollectionByIdProjection(certificacaoesIds)).thenReturn(certificacoes);
		when(certificacaoManager.findCursosByCertificacaoId(certificacao1.getId())).thenReturn(cursos);
		when(colaboradorCertificacaoDao.colaboradoresQueParticipamDaCertificacao(dataIni, dataFim, null, certificacaoesIds, null, null, false)).thenReturn(colaboradorCertificacaos);
		when(colaboradorTurmaManager.findMapByColaboradorIdAndCertificacaoIdAndColabCertificacaoId(certificacao1.getId(), new Long[]{colab1.getId(), colab2.getId()})).thenReturn(mapColaboradoresTurmas);
		when(colaboradorAvaliacaoPraticaManager.findMapByCertificacaoIdAndColaboradoresIds(certificacao1.getId(), colaboradoresIds)).thenReturn(mapColaboradorAvaliacoesPraticas);

		Collection<ColaboradorCertificacao> colaboradoresNasCertificacoes = colaboradorCertificacaoManager.montaRelatorioColaboradoresNasCertificacoes(dataIni, dataFim, null, true, true, null, null, certificacaoesIds, new Long[]{colaboradorCertificacao1.getId()});
		assertEquals(4, colaboradoresNasCertificacoes.size());
		
		ColaboradorCertificacao result2 = (ColaboradorCertificacao) colaboradoresNasCertificacoes.toArray()[1];
		assertEquals("Avaliação Prática: Beber Cachaça", result2.getNomeCurso());
	}
	
	@Test
	public void testFindColaboradorCertificadoInfomandoSeEUltimaCertificacao() {
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		when(colaboradorCertificacaoDao.findColaboradorCertificadoInfomandoSeEUltimaCertificacao(colaboradorCertificacao.getId(), colaborador.getId(), certificacao.getId())).thenReturn(colaboradorCertificacao);
		assertEquals(colaboradorCertificacao, colaboradorCertificacaoManager.findColaboradorCertificadoInfomandoSeEUltimaCertificacao(colaboradorCertificacao.getId(), colaborador.getId(), certificacao.getId()));
	}

	@Test
	public void testExisteColaboradorCertificadoEmUmaTurmaPosterior() {
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity();
		Collection<ColaboradorCertificacao> colaboradoresCertificacoes = new ArrayList<ColaboradorCertificacao>();
		colaboradoresCertificacoes.add(colaboradorCertificacao);
		when(colaboradorCertificacaoDao.findColaboradorCertificadoEmUmaTurmaPosterior(1L, 1L)).thenReturn(colaboradoresCertificacoes);
		assertTrue(colaboradorCertificacaoManager.existeColaboradorCertificadoEmUmaTurmaPosterior(1L, 1L));
	}
	
	@Test
	public void testverificaCertificacaoByColaboradorTurmaIdNaoCertificado()
	{
		Certificacao certificacaoBasica = CertificacaoFactory.getEntity(1L);
		certificacaoBasica.setNome("cert. basica");
		
		Certificacao certificacaoDev = CertificacaoFactory.getEntity(2L);
		certificacaoDev.setCertificacaoPreRequisito(certificacaoBasica);
		certificacaoDev.setNome("cert. Dev");

		Colaborador colabZeRuela = ColaboradorFactory.getEntity(2L);
		colabZeRuela.setNome("Zéruela");
		
		ColaboradorCertificacao colaboradorCertificacao2 = ColaboradorCertificacaoFactory.getEntity(1L);
		colaboradorCertificacao2.setColaborador(colabZeRuela);
		colaboradorCertificacao2.setData(DateUtil.criarDataMesAno(1, 1, 2016));
		colaboradorCertificacao2.setCertificacao(certificacaoDev);
		
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = new ArrayList<ColaboradorCertificacao>();
		colaboradorCertificacaos.add(colaboradorCertificacao2);
		
		when(colaboradorCertificacaoDao.colaboradoresCertificadosByColaboradorTurmaId(1L)).thenReturn(colaboradorCertificacaos);
		when(colaboradorCertificacaoDao.findUltimaCertificacaoByColaboradorIdAndCertificacaoId(null, null)).thenReturn(null);
			
		assertEquals(0, colaboradorCertificacaoManager.certificaColaborador(1L, null, null, null).size());
	}

	@Test
	public void testverificaCertificacaoByColaboradorTurmaIdCertificado()
	{
		Certificacao certificacaoBasica = CertificacaoFactory.getEntity(1L);
		certificacaoBasica.setNome("cert. basica");
		
		Certificacao certificacaoDev = CertificacaoFactory.getEntity(2L);
		certificacaoDev.setCertificacaoPreRequisito(certificacaoBasica);
		certificacaoDev.setNome("cert. Dev");
		
		Colaborador colabZeRuela = ColaboradorFactory.getEntity(2L);
		colabZeRuela.setNome("Zéruela");
		
		ColaboradorCertificacao colaboradorCertificacao1 = ColaboradorCertificacaoFactory.getEntity(1L);
		colaboradorCertificacao1.setColaborador(colabZeRuela);
		colaboradorCertificacao1.setData(new Date());
		colaboradorCertificacao1.setCertificacao(certificacaoBasica);
		
		ColaboradorCertificacao colaboradorCertificacao2 = ColaboradorCertificacaoFactory.getEntity(1L);
		colaboradorCertificacao2.setColaborador(colabZeRuela);
		colaboradorCertificacao2.setData(new Date());
		colaboradorCertificacao2.setCertificacao(certificacaoDev);
		
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = new ArrayList<ColaboradorCertificacao>();
		colaboradorCertificacaos.add(colaboradorCertificacao2);
		
		Curso curso = CursoFactory.getEntity(1L);
		
		Turma turma = TurmaFactory.getEntity(1L);
		turma.setDataPrevIni(DateUtil.criarDataMesAno(1, 3, 2015));
		turma.setDataPrevFim(DateUtil.criarDataMesAno(1, 4, 2015));
		
		ColaboradorTurma colaboradorTurma1 = ColaboradorTurmaFactory.getEntity(1L);
		colaboradorTurma1.setCurso(curso);
		colaboradorTurma1.setTurma(turma);
		colaboradorTurma1.setColaborador(colabZeRuela);
		colaboradorTurma1.setAprovado(true);
		
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmas.add(colaboradorTurma1);
		
		when(colaboradorCertificacaoDao.colaboradoresCertificadosByColaboradorTurmaId(1L)).thenReturn(colaboradorCertificacaos);
		when(colaboradorCertificacaoDao.colaboradoresTurmaCertificados(colabZeRuela.getId(), certificacaoDev.getId())).thenReturn(colaboradorTurmas);
		when(colaboradorAvaliacaoPraticaManager.findByColaboradorIdAndCertificacaoId(colabZeRuela.getId(), certificacaoDev.getId(), null, null, true, true)).thenReturn(new ArrayList<ColaboradorAvaliacaoPratica>());
		when(colaboradorCertificacaoDao.getHibernateTemplateByGenericDao()).thenReturn(new HibernateTemplate());
		when(certificacaoManager.findDependentes(certificacaoDev.getId())).thenReturn(new ArrayList<Certificacao>());
		when(colaboradorCertificacaoDao.findUltimaCertificacaoByColaboradorIdAndCertificacaoId(colabZeRuela.getId(), certificacaoBasica.getId())).thenReturn(colaboradorCertificacao1);
		
		assertEquals(1, colaboradorCertificacaoManager.certificaColaborador(1L, null, null, certificacaoManager).size());
	}
	
	@Test
	public void testverificaCertificacaoByColaboradorTurmaIdTresNiveisNaoCertificadoNaBasica()
	{
		Certificacao certificacaoBasica = CertificacaoFactory.getEntity(1L);
		certificacaoBasica.setNome("cert. basica");
		
		Certificacao certificacaoDev = CertificacaoFactory.getEntity(2L);
		certificacaoDev.setCertificacaoPreRequisito(certificacaoBasica);
		certificacaoDev.setNome("cert. Dev");

		Certificacao certificacaoSuperDev = CertificacaoFactory.getEntity(3L);
		certificacaoSuperDev.setCertificacaoPreRequisito(certificacaoDev);
		certificacaoSuperDev.setNome("cert. Dev");
		
		Colaborador colabZeRuela = ColaboradorFactory.getEntity(1L);
		colabZeRuela.setNome("Zéruela");
		
		ColaboradorCertificacao colaboradorCertificacaoDev = ColaboradorCertificacaoFactory.getEntity(1L);
		colaboradorCertificacaoDev.setColaborador(colabZeRuela);
		colaboradorCertificacaoDev.setData(new Date());
		colaboradorCertificacaoDev.setCertificacao(certificacaoDev);
		
		ColaboradorCertificacao colaboradorCertificacaoSuperDev = ColaboradorCertificacaoFactory.getEntity(1L);
		colaboradorCertificacaoSuperDev.setColaborador(colabZeRuela);
		colaboradorCertificacaoSuperDev.setData(new Date());
		colaboradorCertificacaoSuperDev.setCertificacao(certificacaoSuperDev);
		
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = new ArrayList<ColaboradorCertificacao>();
		colaboradorCertificacaos.add(colaboradorCertificacaoSuperDev);
		
		when(colaboradorCertificacaoDao.colaboradoresCertificadosByColaboradorTurmaId(1L)).thenReturn(colaboradorCertificacaos);
		when(colaboradorCertificacaoDao.findUltimaCertificacaoByColaboradorIdAndCertificacaoId(colabZeRuela.getId(), certificacaoDev.getId())).thenReturn(colaboradorCertificacaoSuperDev);
		when(colaboradorCertificacaoDao.findUltimaCertificacaoByColaboradorIdAndCertificacaoId(colabZeRuela.getId(), certificacaoDev.getId())).thenReturn(null);
		
		assertEquals(0, colaboradorCertificacaoManager.certificaColaborador(1L, null, null, null).size());
	}
	
	@Test
	public void testverificaCertificacaoByColaboradorTurmaIdDependentes()
	{
		Certificacao certificacaoBasica = CertificacaoFactory.getEntity(1L);
		certificacaoBasica.setNome("cert. basica");
		
		Certificacao certificacaoDev = CertificacaoFactory.getEntity(2L);
		certificacaoDev.setCertificacaoPreRequisito(certificacaoBasica);
		certificacaoDev.setNome("cert. Dev");

		Certificacao certificacaoSuperDev = CertificacaoFactory.getEntity(3L);
		certificacaoSuperDev.setCertificacaoPreRequisito(certificacaoDev);
		certificacaoSuperDev.setNome("cert. Dev");
		
		Colaborador colabZeRuela = ColaboradorFactory.getEntity(1L);
		colabZeRuela.setNome("Zéruela");
		
		ColaboradorCertificacao colaboradorCertificacaoBasica = ColaboradorCertificacaoFactory.getEntity(1L);
		colaboradorCertificacaoBasica.setColaborador(colabZeRuela);
		colaboradorCertificacaoBasica.setData(new Date());
		colaboradorCertificacaoBasica.setCertificacao(certificacaoBasica);
		
		ColaboradorCertificacao colabCertificacaoRetornoDependenteCertificado = ColaboradorCertificacaoFactory.getEntity(1L);
		colabCertificacaoRetornoDependenteCertificado.setColaborador(colabZeRuela);
		colabCertificacaoRetornoDependenteCertificado.setData(new Date());
		colabCertificacaoRetornoDependenteCertificado.setCertificacao(certificacaoDev);
		
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = new ArrayList<ColaboradorCertificacao>();
		colaboradorCertificacaos.add(colaboradorCertificacaoBasica);
		
		Curso curso = CursoFactory.getEntity(1L);
		
		Turma turma = TurmaFactory.getEntity(1L);
		turma.setDataPrevIni(DateUtil.criarDataMesAno(1, 3, 2015));
		turma.setDataPrevFim(DateUtil.criarDataMesAno(1, 4, 2015));
		
		ColaboradorTurma colaboradorTurma1 = ColaboradorTurmaFactory.getEntity(1L);
		colaboradorTurma1.setCurso(curso);
		colaboradorTurma1.setTurma(turma);
		colaboradorTurma1.setColaborador(colabZeRuela);
		colaboradorTurma1.setAprovado(true);
		
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmas.add(colaboradorTurma1);
		
		Collection<Certificacao> certificacaoesDependentes = new ArrayList<Certificacao>();
		certificacaoesDependentes.add(certificacaoDev);
		
		when(colaboradorCertificacaoDao.colaboradoresCertificadosByColaboradorTurmaId(1L)).thenReturn(colaboradorCertificacaos);
		when(colaboradorCertificacaoDao.colaboradoresTurmaCertificados(colabZeRuela.getId(), certificacaoBasica.getId())).thenReturn(colaboradorTurmas);
		when(colaboradorAvaliacaoPraticaManager.findByColaboradorIdAndCertificacaoId(colabZeRuela.getId(), certificacaoBasica.getId(), null, null, true, true)).thenReturn(new ArrayList<ColaboradorAvaliacaoPratica>());
		when(colaboradorCertificacaoDao.getHibernateTemplateByGenericDao()).thenReturn(new HibernateTemplate());
		when(certificacaoManager.findDependentes(certificacaoBasica.getId())).thenReturn(certificacaoesDependentes);
		when(colaboradorCertificacaoDao.verificaCertificacao(colabZeRuela.getId(), certificacaoDev.getId())).thenReturn(colabCertificacaoRetornoDependenteCertificado);
		when(colaboradorCertificacaoDao.colaboradoresTurmaCertificados(colabZeRuela.getId(), certificacaoDev.getId())).thenReturn(colaboradorTurmas);
		when(colaboradorAvaliacaoPraticaManager.findByColaboradorIdAndCertificacaoId(colabZeRuela.getId(), certificacaoBasica.getId(), null, null, true, true)).thenReturn(new ArrayList<ColaboradorAvaliacaoPratica>());
		when(colaboradorCertificacaoDao.getHibernateTemplateByGenericDao()).thenReturn(new HibernateTemplate());
		when(certificacaoManager.findDependentes(certificacaoDev.getId())).thenReturn(new ArrayList<Certificacao>());
		
		assertEquals(1, colaboradorCertificacaoManager.certificaColaborador(1L, null, null, certificacaoManager).size());
	}
	
	@Test
	public void testDescertificarColaborador()
	{
		ColaboradorCertificacao colaboradorCertificacaoPreRequisito = ColaboradorCertificacaoFactory.getEntity(1L);
		
		Collection<ColaboradorCertificacao> colaboradorCertificacaosPreRequisitos = new ArrayList<ColaboradorCertificacao>();
		colaboradorCertificacaosPreRequisitos.add(colaboradorCertificacaoPreRequisito);
		
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity(2L);
		colaboradorCertificacao.setColaboradorCertificacaoPreRequisito(colaboradorCertificacaoPreRequisito);
		
		when(colaboradorCertificacaoDao.findColaboradorCertificacaoPreRequisito(colaboradorCertificacao.getId())).thenReturn(colaboradorCertificacaosPreRequisitos);
		when(colaboradorCertificacaoDao.findColaboradorCertificacaoPreRequisito(colaboradorCertificacaoPreRequisito.getId())).thenReturn(new ArrayList<ColaboradorCertificacao>());
		
		Exception ex = null;
		
		try {
			colaboradorCertificacaoManager.descertificarColaborador(colaboradorCertificacao.getId());
		} catch (Exception e) {
			ex = e;
		}
		
		assertNull(ex);
	}
}
