package com.fortes.rh.test.business.desenvolvimento;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.fortes.rh.business.avaliacao.AvaliacaoPraticaManager;
import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorAvaliacaoPraticaManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManagerImpl;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.dao.desenvolvimento.ColaboradorCertificacaoDao;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoPraticaFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorAvaliacaoPraticaFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorCertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.util.mockObjects.MockHibernateTemplate;
import com.fortes.rh.test.util.mockObjects.MockSpringUtilJUnit4;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.web.tags.CheckBox;

public class ColaboradorCertificacaoManagerTest
{
	private ColaboradorCertificacaoManagerImpl colaboradorCertificacaoManager = new ColaboradorCertificacaoManagerImpl();
	private ColaboradorCertificacaoDao colaboradorCertificacaoDao;
	private ColaboradorAvaliacaoPraticaManager colaboradorAvaliacaoPraticaManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private CertificacaoManager certificacaoManager;
	private AvaliacaoPraticaManager avaliacaoPraticaManager;
	private ColaboradorManager colaboradorManager;
	
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
        
        colaboradorManager = mock(ColaboradorManager.class);
        MockSpringUtilJUnit4.mocks.put("colaboradorManager", colaboradorManager);
        
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
	public void testMontaRelatorioColaboradoresNasCertificacoes() throws CloneNotSupportedException{
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L); 
		
		Certificacao certificacao1 = CertificacaoFactory.getEntity(1L);
		certificacao1.setNome("cert. admin");
		certificacao1.setPeriodicidade(2);
		Certificacao certificacao2 = CertificacaoFactory.getEntity(1L);
		certificacao2.setNome("cert. Dev");
		Collection<Certificacao> certificacoes = new ArrayList<Certificacao>();
		certificacoes.add(certificacao1);
		
		Colaborador colab1 = ColaboradorFactory.getEntity(1L);
		colab1.setNome("Astrogildis");
		Colaborador colab2 = ColaboradorFactory.getEntity(2L);
		colab2.setNome("Margnoris");
		Colaborador colabVencido = ColaboradorFactory.getEntity(3L);
		colabVencido.setNome("Vencido");
		Colaborador colabFora = ColaboradorFactory.getEntity(4L);
		colabFora.setNome("Fora a adicionar");
		
		Curso curso = CursoFactory.getEntity(1L);
		Curso curso2 = CursoFactory.getEntity(2L);
		Collection<Curso> cursos = new ArrayList<Curso>();
		cursos.add(curso);
		cursos.add(curso2);
		
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
		
		ColaboradorTurma colaboradorTurmaVencido = ColaboradorTurmaFactory.getEntity(3L);
		colaboradorTurma2.setCurso(curso);
		colaboradorTurma2.setTurma(turma);
		colaboradorTurma2.setColaborador(colabVencido);
		colaboradorTurma2.setAprovado(true);
		
		ColaboradorCertificacao colaboradorCertificacao1 = ColaboradorCertificacaoFactory.getEntity(1L);
		colaboradorCertificacao1.setColaborador(colab1);
		colaboradorCertificacao1.setData(DateUtil.criarDataMesAno(1, 1, 2016));
		colaboradorCertificacao1.setColaboradorTurma(colaboradorTurma1);
		colaboradorCertificacao1.setCertificacao(certificacao1);
		
		ColaboradorCertificacao colaboradorCertificacao2 = ColaboradorCertificacaoFactory.getEntity(2L);
		colaboradorCertificacao2.setColaborador(colab2);
		colaboradorCertificacao2.setData(DateUtil.criarDataMesAno(1, 1, 2016));
		colaboradorCertificacao2.setColaboradorTurma(colaboradorTurma2);
		colaboradorCertificacao2.setCertificacao(certificacao2);
		
		ColaboradorCertificacao colaboradorCertificacaoVencido = ColaboradorCertificacaoFactory.getEntity(3L);
		colaboradorCertificacaoVencido.setColaborador(colabVencido);
		colaboradorCertificacaoVencido.setData(DateUtil.criarDataMesAno(1, 4, 2015));
		colaboradorCertificacaoVencido.setColaboradorTurma(colaboradorTurmaVencido);
		colaboradorCertificacaoVencido.setCertificacao(certificacao2);
		
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = new ArrayList<ColaboradorCertificacao>();
		colaboradorCertificacaos.add(colaboradorCertificacao1);
		colaboradorCertificacaos.add(colaboradorCertificacao2);
		
		Collection<ColaboradorCertificacao> colaboradorCertificacaosVencidos = new ArrayList<ColaboradorCertificacao>();
		colaboradorCertificacaosVencidos.add(colaboradorCertificacaoVencido);
		
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity(1L);
		avaliacaoPratica.setNotaMinima(90.0);
		avaliacaoPratica.setTitulo("Avaliação Prática Z");
		
		Collection<AvaliacaoPratica> avaliacoesPraticas = new ArrayList<AvaliacaoPratica>();
		avaliacoesPraticas.add(avaliacaoPratica);
		
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica1 = ColaboradorAvaliacaoPraticaFactory.getEntity(1L);
		colaboradorAvaliacaoPratica1.setColaborador(colab1);
		colaboradorAvaliacaoPratica1.setAvaliacaoPratica(avaliacaoPratica);
		colaboradorAvaliacaoPratica1.setNota(90.0);
		
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica2 = ColaboradorAvaliacaoPraticaFactory.getEntity(2L);
		colaboradorAvaliacaoPratica2.setColaborador(colab2);
		colaboradorAvaliacaoPratica2.setAvaliacaoPratica(avaliacaoPratica);
		colaboradorAvaliacaoPratica2.setNota(90.0);
		
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
		
		Long[] certificacoesIds = new Long[]{certificacao1.getId()};
		Long[] colaboradoresIds = new Long[]{colab1.getId(), colab2.getId()};
		Date dataIni = DateUtil.criarDataMesAno(1, 1, 2015);
		Date dataFim = DateUtil.criarDataMesAno(1, 1, 2016);
		
		Long[] colabIds = new Long[]{colab1.getId(), colab2.getId()};
		Set<Long> setColabsIds = new HashSet<Long>();
		setColabsIds.add(colab1.getId());
		setColabsIds.add(colab2.getId());
		
		Long[] filtroColaboradoresIds = new Long[]{colab1.getId()};
		
		when(certificacaoManager.findCollectionByIdProjection(certificacoesIds)).thenReturn(certificacoes);
		when(avaliacaoPraticaManager.findMapByCertificacaoId(certificacoesIds)).thenReturn(mapAvaliacoesPraticas);
		when(colaboradorCertificacaoDao.findColaboradoresCertificados(dataIni, dataFim, null, new Long[]{certificacao1.getId()}, null,  null, filtroColaboradoresIds, null, false)).thenReturn(colaboradorCertificacaos);
		when(colaboradorCertificacaoDao.findColaboradoresCertificados(dataIni, dataFim, null, new Long[]{certificacao1.getId()}, null,  null, filtroColaboradoresIds, null, true)).thenReturn(colaboradorCertificacaosVencidos);
		when(colaboradorCertificacaoDao.findColaboradoresQueParticipamDaCertificacao(new Long[]{certificacao1.getId()}, null, null, new Long[]{colab1.getId()}, null, colabIds)).thenReturn(colaboradorCertificacaos);
		when(certificacaoManager.findCursosByCertificacaoId(certificacao1.getId())).thenReturn(cursos);
		when(colaboradorAvaliacaoPraticaManager.findMapByCertificacaoIdAndColaboradoresIds(certificacao1.getId(), colaboradoresIds)).thenReturn(mapColaboradorAvaliacoesPraticas);
		when(colaboradorManager.findDadosBasicosNotIds(setColabsIds, filtroColaboradoresIds, null, null, null, empresa.getId())).thenReturn(Arrays.asList(colabFora));

		Boolean certificado = null;
		Collection<ColaboradorCertificacao> colaboradoresNasCertificacoes = colaboradorCertificacaoManager.montaRelatorioColaboradoresNasCertificacoes(dataIni, dataFim, null, certificado, null, null, certificacoesIds, filtroColaboradoresIds, null, empresa.getId());
		assertEquals(9, colaboradoresNasCertificacoes.size());
		assertEquals("zzzzzzAvaliação Prática: Avaliação Prática Z", ((ColaboradorCertificacao) colaboradoresNasCertificacoes.toArray()[2]).getNomeCurso());
		
		certificado = true;
		colaboradoresNasCertificacoes = colaboradorCertificacaoManager.montaRelatorioColaboradoresNasCertificacoes(dataIni, dataFim, null, certificado, null, null, certificacoesIds, filtroColaboradoresIds, null, empresa.getId());
		assertEquals(6, colaboradoresNasCertificacoes.size());
		
		certificado = false;
		colaboradoresNasCertificacoes = colaboradorCertificacaoManager.montaRelatorioColaboradoresNasCertificacoes(dataIni, dataFim, null, certificado, null, null, certificacoesIds, filtroColaboradoresIds, null, empresa.getId());
		assertEquals(9, colaboradoresNasCertificacoes.size());
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
	
	@Test
	public void TestPossuemAvaliacoesPraticasRealizadas()
	{
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity(colaborador, certificacao, DateUtil.criarDataMesAno(1, 1, 2016));
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L, colaborador);
		
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = new ArrayList<ColaboradorCertificacao>();
		colaboradorCertificacaos.add(colaboradorCertificacao);
		
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity(1L);
		avaliacaoPratica.setNotaMinima(90.0);
		avaliacaoPratica.setTitulo("Avaliação Prática Z");
		
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica1 = ColaboradorAvaliacaoPraticaFactory.getEntity(colaboradorCertificacao, colaborador, certificacao, avaliacaoPratica, 90.0);
		colaboradorAvaliacaoPratica1.setData(DateUtil.criarDataMesAno(1, 1, 2016));
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica2 = ColaboradorAvaliacaoPraticaFactory.getEntity(colaboradorCertificacao, colaborador, certificacao, avaliacaoPratica, 90.0);
		colaboradorAvaliacaoPratica2.setData(DateUtil.criarDataMesAno(1, 2, 2016));
		
		Collection<ColaboradorAvaliacaoPratica> avaliacoesPraticasDoColaboradorRealizadas = new ArrayList<ColaboradorAvaliacaoPratica>();
		avaliacoesPraticasDoColaboradorRealizadas.add(colaboradorAvaliacaoPratica1);
		avaliacoesPraticasDoColaboradorRealizadas.add(colaboradorAvaliacaoPratica2);
		
		Map<Long, Collection<ColaboradorAvaliacaoPratica>> mapColaboradorAvaliacoesPraticas = new HashMap<Long, Collection<ColaboradorAvaliacaoPratica>>();
		mapColaboradorAvaliacoesPraticas.put(colaborador.getId(), avaliacoesPraticasDoColaboradorRealizadas);
		
		Long[] colaboradoresIds = new Long[]{colaborador.getId()};

		when(colaboradorCertificacaoDao.colaboradoresAprovadosEmTodosOsCursosDaCertificacao(certificacao.getId())).thenReturn(colaboradorCertificacaos);
		when(colaboradorAvaliacaoPraticaManager.findMapByCertificacaoIdAndColaboradoresIds(certificacao.getId(), colaboradoresIds)).thenReturn(mapColaboradorAvaliacoesPraticas);
		when(colaboradorTurmaManager.findByColaboradorIdAndCertificacaoIdAndColabCertificacaoId(certificacao.getId(), null, colaborador.getId())).
		thenReturn(Arrays.asList(colaboradorTurma));
									
		Collection<ColaboradorCertificacao> colaboradorescertificacoes = colaboradorCertificacaoManager.possuemAvaliacoesPraticasRealizadas(certificacao.getId(), colaboradorTurmaManager);
		ColaboradorCertificacao colabCertificacao = (ColaboradorCertificacao) colaboradorescertificacoes.toArray()[0];

		assertEquals(1, colaboradorescertificacoes.size());
		assertEquals(2, colabCertificacao.getColaboradoresAvaliacoesPraticas().size());
		assertEquals(colaboradorAvaliacaoPratica2.getId(), colabCertificacao.getColaboradorAvaliacaoPraticaAtual().getId());
	}
	
	@Test
	public void colaboradoresParticipamCertificacao()
	{
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity(colaborador, certificacao, DateUtil.criarDataMesAno(1, 1, 2016));
		
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = new ArrayList<ColaboradorCertificacao>();
		colaboradorCertificacaos.add(colaboradorCertificacao);
		
		Long[] certificacoesIds = new Long[]{certificacao.getId()};
		Date dataIni = DateUtil.criarDataMesAno(1, 1, 2015);
		Date dataFim = DateUtil.criarDataMesAno(1, 1, 2016);

		when(colaboradorCertificacaoDao.findColaboradoresCertificados(dataIni, dataFim, null, certificacoesIds, null, null, null, null, false)).thenReturn(colaboradorCertificacaos);
		
		Collection<ColaboradorCertificacao> colaboradorescertificacoes = colaboradorCertificacaoManager.colaboradoresCertificados(dataIni, dataFim, null, certificacoesIds, null, null, null, null);
		assertEquals(1, colaboradorescertificacoes.size());
	}
	
	@Test
	public void existiColaboradorCertificadoByTurmaTrue()
	{
		Long turmaId = 1L;
		when(colaboradorCertificacaoDao.existiColaboradorCertificadoByTurma(turmaId)).thenReturn(true);
		assertTrue(colaboradorCertificacaoManager.existiColaboradorCertificadoByTurma(turmaId));
	}
	
	@Test
	public void existiColaboradorCertificadoByTurmaFalse()
	{
		Long turmaId = 1L;
		when(colaboradorCertificacaoDao.existiColaboradorCertificadoByTurma(turmaId)).thenReturn(false);
		assertFalse(colaboradorCertificacaoManager.existiColaboradorCertificadoByTurma(turmaId));
	}
	
	@Test
	public void testSetCertificacoesNomesInColaboradorTurmas(){
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
		
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmas.add(colaboradorTurma);
		
		colaboradorTurma.setCertificacoesNomes("certificacoesNomes");
		Map<Long, ColaboradorTurma> colaboradoresTurmaMap = new HashMap<Long, ColaboradorTurma>();
		colaboradoresTurmaMap.put(colaboradorTurma.getId(), colaboradorTurma);

		when(colaboradorCertificacaoDao.findCertificacoesNomesByColaboradoresTurmasIds(new CollectionUtil<ColaboradorTurma>().convertCollectionToArrayIds(colaboradorTurmas))).thenReturn(colaboradoresTurmaMap);
		colaboradorCertificacaoManager.setCertificacoesNomesInColaboradorTurmas( colaboradorTurmas);
		
		assertEquals(colaboradorTurma.getCertificacoesNomes(), ((ColaboradorTurma) colaboradorTurmas.toArray()[0]).getCertificacoesNomes());
	}
	
	@Test
	public void testIsCertificadoByColaboradorTurmaId(){
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
		
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmas.add(colaboradorTurma);
		
		Map<Long, ColaboradorTurma> colaboradoresTurmaMap = new HashMap<Long, ColaboradorTurma>();
		colaboradoresTurmaMap.put(colaboradorTurma.getId(), colaboradorTurma);

		when(colaboradorCertificacaoDao.findCertificacoesNomesByColaboradoresTurmasIds(colaboradorTurma.getId())).thenReturn(colaboradoresTurmaMap);
		assertTrue(colaboradorCertificacaoManager.isCertificadoByColaboradorTurmaId(colaboradorTurma.getId()));
	}
	
	@Test
	public void testColaboradoresSemCertificacaoDWR(){
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		certificacao.setNome("Cerificação");
		
		Curso curso1 = CursoFactory.getEntity(1L);
		Curso curso2 = CursoFactory.getEntity(2L);
		Collection<Curso> cursos = Arrays.asList(curso1, curso2);
		
		Colaborador colab1 = ColaboradorFactory.getEntity(1L);
		colab1.setNome("João");
		Colaborador colab2 = ColaboradorFactory.getEntity(2L);
		colab2.setNome("Maria");
		
		ColaboradorCertificacao colaboradorCertificacao1 = ColaboradorCertificacaoFactory.getEntity(colab1, certificacao, null);
		ColaboradorCertificacao colaboradorCertificacao2 = ColaboradorCertificacaoFactory.getEntity(colab2, certificacao, null);
		
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = Arrays.asList(colaboradorCertificacao1, colaboradorCertificacao2);
		
		Long[] certificacoesIds = new Long[]{1L, 2L};
		Long[] cursosIds = new Long[]{curso1.getId(), curso2.getId()};
		
		when(certificacaoManager.findCursosByCertificacaoId(1L)).thenReturn(cursos);
		when(colaboradorCertificacaoDao.findColaboradoresCertificacoesQueNaoParticipamDoCurso(1L, null, null, null, SituacaoColaborador.ATIVO, cursosIds)).thenReturn(colaboradorCertificacaos);

		Collection<CheckBox> checkBoxs = colaboradorCertificacaoManager.checkBoxColaboradoresSemCertificacaoDWR(1L, null, null, certificacoesIds, SituacaoColaborador.ATIVO);
		assertEquals(2, checkBoxs.size());
		assertEquals(colab1.getNome(), ((CheckBox) checkBoxs.toArray()[0]).getNome());
		assertEquals(colab2.getNome(), ((CheckBox) checkBoxs.toArray()[1]).getNome());
	}
	
	@Test
	public void testColaboradoresSemCertificacao(){
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		certificacao.setNome("Cerificação");
		Collection<Certificacao> certificacoes = Arrays.asList(certificacao);
		
		Colaborador colab1 = ColaboradorFactory.getEntity(1L);
		colab1.setNome("João");
		Colaborador colab2 = ColaboradorFactory.getEntity(2L);
		colab2.setNome("Maria");
		
		Curso curso1 = CursoFactory.getEntity(1L);
		Curso curso2 = CursoFactory.getEntity(2L);
		Collection<Curso> cursos = Arrays.asList(curso1, curso2);
		
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity(1L);
		avaliacaoPratica.setNotaMinima(90.0);
		avaliacaoPratica.setTitulo("Av Pratica");
		
		Map<Long, Collection<AvaliacaoPratica>> mapAvaliacoesPraticas = new HashMap<Long, Collection<AvaliacaoPratica>>();
		mapAvaliacoesPraticas.put(certificacao.getId(), Arrays.asList(avaliacaoPratica));
		
		ColaboradorCertificacao colaboradorCertificacao1 = ColaboradorCertificacaoFactory.getEntity(1L);
		colaboradorCertificacao1.setColaborador(colab1);
		colaboradorCertificacao1.setCertificacao(certificacao);
		
		ColaboradorCertificacao colaboradorCertificacao2 = ColaboradorCertificacaoFactory.getEntity(2L);
		colaboradorCertificacao2.setColaborador(colab2);
		colaboradorCertificacao2.setCertificacao(certificacao);
		
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = Arrays.asList(colaboradorCertificacao1, colaboradorCertificacao2);
		
		Long[] certificacoesIds = new Long[]{certificacao.getId()};
		Long[] colaboradoresIds = new Long[]{colab1.getId(), colab2.getId()};
		Long[] cursosIds = new Long[]{curso1.getId(), curso2.getId()};
		
		when(certificacaoManager.findCollectionByIdProjection(certificacoesIds)).thenReturn(certificacoes);
		when(avaliacaoPraticaManager.findMapByCertificacaoId(certificacoesIds)).thenReturn(mapAvaliacoesPraticas);
		when(certificacaoManager.findCursosByCertificacaoId(certificacao.getId())).thenReturn(cursos);
		when(colaboradorCertificacaoDao.findColaboradoresCertificacoesQueNaoParticipamDoCurso(1L, null, null, colaboradoresIds, SituacaoColaborador.ATIVO, cursosIds)).thenReturn(colaboradorCertificacaos);

		Collection<ColaboradorCertificacao> colaboradoresNasCertificacoes = colaboradorCertificacaoManager.colaboradoresSemCertificacao(1L, null, null, colaboradoresIds, certificacoesIds, SituacaoColaborador.ATIVO);
		assertEquals(6, colaboradoresNasCertificacoes.size());
		
		ColaboradorCertificacao result2 = (ColaboradorCertificacao) colaboradoresNasCertificacoes.toArray()[2];
		assertEquals("Avaliação Prática: Av Pratica", result2.getNomeCurso());
	}
}
