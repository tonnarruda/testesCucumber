package com.fortes.rh.test.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.desenvolvimento.AproveitamentoAvaliacaoCursoManager;
import com.fortes.rh.business.desenvolvimento.AvaliacaoCursoManager;
import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorPresencaManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManagerImpl;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.desenvolvimento.DiaTurmaManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.dao.desenvolvimento.ColaboradorTurmaDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.desenvolvimento.AproveitamentoAvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorPresenca;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.DNT;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.model.desenvolvimento.PrioridadeTreinamento;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.desenvolvimento.relatorio.ColaboradorCursoMatriz;
import com.fortes.rh.model.desenvolvimento.relatorio.CursoPontuacaoMatriz;
import com.fortes.rh.model.desenvolvimento.relatorio.SomatorioCursoMatriz;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.AvaliacaoCursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.DiaTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.DntFactory;
import com.fortes.rh.test.factory.desenvolvimento.PrioridadeTreinamentoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.SpringUtil;

public class ColaboradorTurmaManagerTest extends MockObjectTestCase
{
	private ColaboradorTurmaManagerImpl colaboradorTurmaManager = new ColaboradorTurmaManagerImpl();
	private Mock colaboradorTurmaDao;
	private Mock colaboradorManager;
	private Mock historicoColaboradorManager;
	private Mock areaOrganizacionalManager;
	private Mock avaliacaoCursoManager;
	private Mock aproveitamentoAvaliacaoCursoManager;
	private Mock cursoManager;
	private Mock diaTurmaManager;
	private Mock certificacaoManager;
	Mock turmaManager;
	Mock colaboradorPresencaManager;

	protected void setUp() throws Exception
	{
		colaboradorTurmaDao = new Mock(ColaboradorTurmaDao.class);
		colaboradorTurmaManager.setDao((ColaboradorTurmaDao) colaboradorTurmaDao.proxy());

		colaboradorManager = new Mock(ColaboradorManager.class);
		colaboradorTurmaManager.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());

		historicoColaboradorManager = new Mock(HistoricoColaboradorManager.class);
		colaboradorTurmaManager.setHistoricoColaboradorManager((HistoricoColaboradorManager) historicoColaboradorManager.proxy());

		areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
		colaboradorTurmaManager.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());

		avaliacaoCursoManager = new Mock(AvaliacaoCursoManager.class);
		colaboradorTurmaManager.setAvaliacaoCursoManager((AvaliacaoCursoManager) avaliacaoCursoManager.proxy());

		aproveitamentoAvaliacaoCursoManager = new Mock(AproveitamentoAvaliacaoCursoManager.class);
		colaboradorTurmaManager.setAproveitamentoAvaliacaoCursoManager((AproveitamentoAvaliacaoCursoManager) aproveitamentoAvaliacaoCursoManager.proxy());

		cursoManager = new Mock(CursoManager.class);
		colaboradorTurmaManager.setCursoManager((CursoManager) cursoManager.proxy());
		
		certificacaoManager = mock(CertificacaoManager.class);
		colaboradorTurmaManager.setCertificacaoManager((CertificacaoManager) certificacaoManager.proxy());

		diaTurmaManager = new Mock(DiaTurmaManager.class);
		colaboradorTurmaManager.setDiaTurmaManager((DiaTurmaManager) diaTurmaManager.proxy());

		turmaManager = new Mock(TurmaManager.class);

		colaboradorPresencaManager = new Mock(ColaboradorPresencaManager.class);
		
		Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
		
	}

	public void testGetSomaPontuacao()
	{
		Collection<ColaboradorCursoMatriz> colaboradorCursoMatrizs = new ArrayList<ColaboradorCursoMatriz>();
		Collection<CursoPontuacaoMatriz> cursoPontuacaos1 = new ArrayList<CursoPontuacaoMatriz>();
		Collection<CursoPontuacaoMatriz> cursoPontuacaos2 = new ArrayList<CursoPontuacaoMatriz>();

		Curso c1 = new Curso();
		c1.setId(1L);
		Curso c2 = new Curso();
		c2.setId(2L);

		CursoPontuacaoMatriz cpm22 = new CursoPontuacaoMatriz();
		cpm22.setCurso(c1);
		cpm22.setPontuacao(5);
		CursoPontuacaoMatriz cpm33 = new CursoPontuacaoMatriz();
		cpm33.setCurso(c1);
		cpm33.setPontuacao(3);
		CursoPontuacaoMatriz cpm44 = new CursoPontuacaoMatriz();
		cpm44.setCurso(c2);
		cpm44.setPontuacao(1);

		cursoPontuacaos1.add(cpm22);
		cursoPontuacaos2.add(cpm33);
		cursoPontuacaos1.add(cpm44);

		ColaboradorCursoMatriz ccm1 = new ColaboradorCursoMatriz();
		ccm1.setCursoPontuacaos(cursoPontuacaos1);

		ColaboradorCursoMatriz ccm2 = new ColaboradorCursoMatriz();
		ccm2.setCursoPontuacaos(cursoPontuacaos2);

		colaboradorCursoMatrizs.add(ccm1);
		colaboradorCursoMatrizs.add(ccm2);

		Collection<SomatorioCursoMatriz> scm = colaboradorTurmaManager.getSomaPontuacao(colaboradorCursoMatrizs);

		assertFalse(scm.isEmpty());

		for (SomatorioCursoMatriz matriz : scm)
		{
			if (matriz.getCursoId() == 1L)
			{
				assertEquals(8, matriz.getSoma());
			}
			if (matriz.getCursoId() == 2L)
			{
				assertEquals(1, matriz.getSoma());
			}
		}
	}

	public void testFindByTurma()
	{
		Turma turma = TurmaFactory.getEntity();
		turma.setId(1L);

		ColaboradorTurma colaboradorTurma = new ColaboradorTurma();
		colaboradorTurma.setId(1L);
		colaboradorTurma.setTurma(turma);

		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmas.add(colaboradorTurma);

		colaboradorTurmaDao.expects(once()).method("findByTurma").with(eq(turma.getId())).will(returnValue(colaboradorTurmas));

		Collection<ColaboradorTurma> retornos = colaboradorTurmaManager.findByTurma(turma.getId());

		assertEquals(1, retornos.size());
	}

	public void testGetColaboradoresAprovadoByTurmaSemAvaliacao()
	{
		Turma turma = new Turma();
		turma.setId(1L);
		
		Collection<Long> turmaIds = new ArrayList<Long>();
		turmaIds.add(turma.getId());

		avaliacaoCursoManager.expects(once()).method("countAvaliacoes").with(eq(turma.getId()), eq("T")).will(returnValue(0));
		colaboradorTurmaDao.expects(once()).method("getColaboradoresByTurma").with(eq(turmaIds)).will(returnValue(new ArrayList<ColaboradorTurma>()));
		assertNotNull(colaboradorTurmaManager.getColaboradoresAprovadoByTurma(turma.getId()));
	}

	public void testGetColaboradoresAprovadoByTurmaComAvaliacao()
	{
		Turma turma = new Turma();
		turma.setId(1L);
		Integer qtdAvaliacoes = new Integer(1);
		avaliacaoCursoManager.expects(once()).method("countAvaliacoes").with(eq(turma.getId()), eq("T")).will(returnValue(qtdAvaliacoes));

		Collection<Long> aprovadosIds = new ArrayList<Long>();
		aprovadosIds.add(1L);
		aproveitamentoAvaliacaoCursoManager.expects(once()).method("find").with(eq(turma.getId()), eq(qtdAvaliacoes), eq("T"), eq(true)).will(returnValue(aprovadosIds));

		colaboradorTurmaDao.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(new ArrayList<ColaboradorTurma>()));
		assertNotNull(colaboradorTurmaManager.getColaboradoresAprovadoByTurma(turma.getId()));
	}

	public void testGetDadosTurma() throws Exception
	{
		Date data = new Date();
		Curso curso = new Curso();
		curso.setId(1L);
		curso.setNome("hibernate");
		curso.setConteudoProgramatico("criteria");

		Turma turma = new Turma();
		turma.setId(1L);
		turma.setCurso(curso);
		turma.setDescricao("basico do hibernate");
		turma.setDataPrevIni(data);
		turma.setDataPrevFim(data);

		turma.setInstrutor("jubiliano");

		Colaborador colaborador = new Colaborador();
		colaborador.setId(1L);

		ColaboradorTurma colaboradorTurma = new ColaboradorTurma();
		colaboradorTurma.setId(1L);
		colaboradorTurma.setTurma(turma);
		colaboradorTurma.setCurso(curso);
		colaboradorTurma.setColaborador(colaborador);

		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmas.add(colaboradorTurma);

		Map<String, Object> parametros = colaboradorTurmaManager.getDadosTurma(colaboradorTurmas, new HashMap<String, Object>());
		assertEquals(curso.getNome(), parametros.get("CURSO_NOME"));
	}

	public void testSaveUpdate() throws Exception
	{
		Collection<Long> colaboradoresTurmaId = new HashSet<Long>();
		colaboradoresTurmaId.add(1L);
		colaboradoresTurmaId.add(2L);

		colaboradorTurmaDao.expects(atLeastOnce()).method("updateColaboradorTurmaSetAprovacao").with(ANYTHING, ANYTHING);

		Exception exception = null;
		try
		{
			colaboradorTurmaManager.saveUpdate(colaboradoresTurmaId, true);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}

	public void testSaveUpdateException() throws Exception
	{
		Collection<Long> colaboradoresTurmaId = new HashSet<Long>();
		colaboradoresTurmaId.add(1L);
		colaboradoresTurmaId.add(2L);

		colaboradorTurmaDao.expects(atLeastOnce()).method("updateColaboradorTurmaSetAprovacao").with(ANYTHING, ANYTHING).will(throwException(new Exception()));

		Exception exception = null;
		try
		{
			colaboradorTurmaManager.saveUpdate(colaboradoresTurmaId, true);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testSetCustoRateado() throws Exception
	{
		Turma turma = new Turma();
		turma.setId(1L);

		ColaboradorTurma colaboradorTurma1 = new ColaboradorTurma();
		colaboradorTurma1.setTurma(turma);
		colaboradorTurma1.setId(1L);

		ColaboradorTurma colaboradorTurma2 = new ColaboradorTurma();
		colaboradorTurma2.setTurma(turma);
		colaboradorTurma2.setId(2L);

		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmas.add(colaboradorTurma1);
		colaboradorTurmas.add(colaboradorTurma2);

		Object[] custoRateado1 = new Object[] { 1L, 100.0 };

		List<Object> custosRateados = new ArrayList<Object>();
		custosRateados.add(custoRateado1);

		colaboradorTurmaDao.expects(once()).method("findCustoRateado").will(returnValue(custosRateados));

		Collection<ColaboradorTurma> retorno = colaboradorTurmaManager.setCustoRateado(colaboradorTurmas);

		for (ColaboradorTurma colaboradorTurma : retorno)
		{
			assertEquals(100.0, colaboradorTurma.getCustoRateado());
		}
	}

	public void testFiltrarColaboradoresFiltrarPorArea() throws Exception
	{
		int page = 1;
		int pagingSize = 10;
		String[] areasCheck = new String[] { "1" };
		String[] cargosCheck = null;
		String[] gruposCheck = null;
		String[] colaboradoresCursosCheck = new String[] { "" };
		Turma turma = TurmaFactory.getEntity(1L);
		Long empresaId = 1L;

		int filtrarPor = 1;

		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaboradores.add(colaborador);

		colaboradorManager.expects(once()).method("findByAreasOrganizacionalIds").with(eq(page), eq(pagingSize), ANYTHING, ANYTHING).will(returnValue(colaboradores));
		assertEquals(1, colaboradorTurmaManager.filtrarColaboradores(page, pagingSize, areasCheck, cargosCheck, gruposCheck, colaboradoresCursosCheck,
				filtrarPor, turma, null, empresaId).size());
	}

	public void testFiltrarColaboradoresFiltrarPorCargo() throws Exception
	{
		int page = 1;
		int pagingSize = 10;
		String[] areasCheck = null;
		String[] cargosCheck = new String[] { "1" };
		String[] gruposCheck = null;
		String[] colaboradoresCursosCheck = new String[] { "" };
		Turma turma = TurmaFactory.getEntity(1L);
		Long empresaId = 1L;

		int filtrarPor = 3;

		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaboradores.add(colaborador);

		historicoColaboradorManager.expects(once()).method("findByCargosIds").with(new Constraint[]{eq(page), eq(pagingSize), ANYTHING, eq(empresaId), ANYTHING}).will(
				returnValue(colaboradores));
		assertEquals(1, colaboradorTurmaManager.filtrarColaboradores(page, pagingSize, areasCheck, cargosCheck, gruposCheck, colaboradoresCursosCheck,
				filtrarPor, turma, null, empresaId).size());
	}

	public void testFiltrarColaboradoresFiltrar() throws Exception
	{
		int page = 1;
		int pagingSize = 10;
		String[] areasCheck = null;
		String[] cargosCheck = null;
		String[] gruposCheck = null;
		String[] colaboradoresCursosCheck = new String[] { "1" };
		Long empresaId = 1L;

		Curso curso = CursoFactory.getEntity(1L);
		Turma turma = TurmaFactory.getEntity(1L);
		turma.setCurso(curso);

		int filtrarPor = 4;

		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();

		colaboradorTurmaDao.expects(once()).method("findByColaboradorAndTurma").with(new Constraint[]{eq(page), eq(pagingSize), ANYTHING, eq(turma.getCurso().getId()), ANYTHING}).will(
				returnValue(colaboradorTurmas));
		assertEquals(colaboradorTurmas, colaboradorTurmaManager.filtrarColaboradores(page, pagingSize, areasCheck, cargosCheck, gruposCheck,
				colaboradoresCursosCheck, filtrarPor, turma, null, empresaId));
	}

	public void testFiltroRelatorioMatriz()
	{
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmaDao.expects(once()).method("filtroRelatorioMatriz").with(ANYTHING).will(returnValue(colaboradorTurmas));
		colaboradorTurmaManager.filtroRelatorioMatriz(null);
	}

	public void testFiltroRelatorioPlanoTrei()
	{
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmaDao.expects(once()).method("filtroRelatorioPlanoTrei").with(ANYTHING).will(returnValue(colaboradorTurmas));
		colaboradorTurmaManager.filtroRelatorioPlanoTrei(null);
	}

	public void testGetListaColaboradores()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
		colaboradorTurma.setColaborador(colaborador);

		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmas.add(colaboradorTurma);

		assertEquals(1, colaboradorTurmaManager.getListaColaboradores(colaboradorTurmas).size());
	}

	public void testSaveUpdateSobrecarregado()
	{
		String[] colaboradorTurma = new String[] { "1" };
		String[] selectPrioridades = new String[] { "1" };

		colaboradorTurmaDao.expects(once()).method("updateColaboradorTurmaSetPrioridade").with(ANYTHING, ANYTHING).isVoid();

		Exception ex = null;
		try
		{
			colaboradorTurmaManager.saveUpdate(colaboradorTurma, selectPrioridades);
		}
		catch (Exception e)
		{
			ex = e;
		}

		assertNull(ex);
	}

	public void testSaveUpdateSobrecarregadoException()
	{
		String[] colaboradorTurma = new String[] { "1" };
		String[] selectPrioridades = new String[] { "1" };

		colaboradorTurmaDao.expects(once()).method("updateColaboradorTurmaSetPrioridade").with(ANYTHING, ANYTHING).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null, ""))));

		Exception ex = null;
		try
		{
			colaboradorTurmaManager.saveUpdate(colaboradorTurma, selectPrioridades);
		}
		catch (Exception e)
		{
			ex = e;
		}

		assertNotNull(ex);
	}

	public void testGetListaCursos()
	{
		Curso curso = CursoFactory.getEntity(1L);
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
		colaboradorTurma.setCurso(curso);

		Collection<ColaboradorTurma> colaboradorTurmasLista = new ArrayList<ColaboradorTurma>();
		colaboradorTurmasLista.add(colaboradorTurma);

		assertEquals(1, colaboradorTurmaManager.getListaCursos(colaboradorTurmasLista).size());
	}

	public void testMontaMatriz()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		PrioridadeTreinamento prioridadeTreinamento = PrioridadeTreinamentoFactory.getEntity(1L);
		prioridadeTreinamento.setNumero(1);
		prioridadeTreinamento.setSigla("S");

		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
		colaboradorTurma.setColaborador(colaborador);
		colaboradorTurma.setPrioridadeTreinamento(prioridadeTreinamento);

		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmas.add(colaboradorTurma);

		assertEquals(1, colaboradorTurmaManager.montaMatriz(colaboradorTurmas).size());
	}
	
	public void testFindHistoricoTreinamentosByColaborador() throws Exception
	{
		colaboradorTurmaDao.expects(once()).method("findHistoricoTreinamentosByColaborador").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<ColaboradorTurma>()));
		assertNotNull(colaboradorTurmaManager.findHistoricoTreinamentosByColaborador(1L, 100L, null, null));
	}
//BACALHAU REFATORAR
//	public void testFindRelatorioHistoricoTreinamentos() throws Exception
//	{
//		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
//		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
//		
//		Curso curso = CursoFactory.getEntity(1L);
//		curso.setPercentualMinimoFrequencia(0.0);
//		
//		Turma turma = TurmaFactory.getEntity(1L);
//		turma.setCurso(curso);
//		
//		ColaboradorTurma colaboradorTurma1 = ColaboradorTurmaFactory.getEntity(1L);
//		colaboradorTurma1.setTurma(turma);
//		colaboradorTurma1.setColaborador(colaborador1);
//		colaboradorTurma1.setColaborador(colaborador2);
//		colaboradorTurma1.setValorAvaliacao(85.0);
//		colaboradorTurma1.setAprovado(true);
//		
//		ColaboradorPresenca colaboradorPresenca = ColaboradorPresencaFactory.getEntity(1L);
//		colaboradorPresenca.setColaboradorTurma(colaboradorTurma1);
//		
//		Collection<ColaboradorPresenca> colaboradorPresencas = new ArrayList<ColaboradorPresenca>();
//		colaboradorPresencas.add(new ColaboradorPresenca(colaborador1.getId(), turma.getId(), 20));
//		colaboradorPresencas.add(new ColaboradorPresenca(2L, turma.getId(), 10));
//		
//		Collection<Turma> turmas = new ArrayList<Turma>();
//		turmas.add(new Turma(turma.getId(), 11.1));
//		
//		Collection<Long> colaboradorIds = new ArrayList<Long>();
//		colaboradorIds.add(colaborador1.getId());
//		
//		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
//		colaboradores.add(colaborador1);
//		
//		DiaTurma diaTurma1 = DiaTurmaFactory.getEntity(1L);
//		diaTurma1.setTurma(turma);
//
//		DiaTurma diaTurma2 = DiaTurmaFactory.getEntity(2L);
//		diaTurma2.setTurma(turma);
//		
//		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
//		colaboradorTurmas.add(colaboradorTurma1);
//
//		colaboradorTurmaDao.expects(once()).method("findHistoricoTreinamentosByColaborador").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(colaboradorTurmas));
//		avaliacaoCursoManager.expects(atLeastOnce()).method("countAvaliacoes").with(eq(turma.getId()), eq("T")).will(returnValue(1));
//		colaboradorTurmaDao.expects(once()).method("findColaboradorByTurma").with(eq(turma.getId())).will(returnValue(colaboradorTurmas));
//		
//		MockSpringUtil.mocks.put("colaboradorPresencaManager", colaboradorPresencaManager);
//		MockSpringUtil.mocks.put("turmaManager", turmaManager);
//		
//		colaboradorPresencaManager.expects(once()).method("findColaboradorAprovadosAvaliacao").with(eq(turma.getId())).will(returnValue(colaboradorPresencas));
//		turmaManager.expects(once()).method("findTurmaPresencaMinima").with(eq(turma.getId())).will(returnValue(turmas));
//		colaboradorManager.expects(once()).method("findAllSelect").with(eq(colaboradorIds)).will(returnValue(colaboradores));
//		
//		
//		MockSpringUtil.mocks.put("colaboradorPresencaManager", colaboradorPresencaManager);
//		colaboradorPresencaManager.expects(atLeastOnce()).method("calculaFrequencia").with(eq(colaboradorTurma1.getId()), eq(2)).will(returnValue("2"));
//		
//		Collection<ColaboradorTurma> colabTurmasReprovados = new ArrayList<ColaboradorTurma>();
//		ColaboradorTurma colaboradorTurmaReprovado = ColaboradorTurmaFactory.getEntity(2L);
//		colabTurmasReprovados.add(colaboradorTurmaReprovado);
//		
//		//aprovados + nota
//		aproveitamentoAvaliacaoCursoManager.expects(once()).method("findColaboradorTurma").with(eq(turma.getId()), eq(1), eq("T"), eq(true)).will(returnValue(colaboradorTurmas));
//		//reprovados + nota
//		aproveitamentoAvaliacaoCursoManager.expects(once()).method("findColaboradorTurma").with(eq(turma.getId()), eq(1), eq("T"), eq(false)).will(returnValue(colabTurmasReprovados));
//		
//		colaboradorTurmas = colaboradorTurmaManager.findRelatorioHistoricoTreinamentos(1L, 1L, null, null);
//		assertTrue(((ColaboradorTurma)colaboradorTurmas.toArray()[0]).isAprovado());
//		assertEquals(85.0, ((ColaboradorTurma)colaboradorTurmas.toArray()[0]).getValorAvaliacao());
//
//		turmaManager.expects(once()).method("findTurmaPresencaMinima").with(eq(turma.getId())).will(returnValue(turmas));
//
//		aproveitamentoAvaliacaoCursoManager.expects(once()).method("findColaboradorTurma").with(eq(turma.getId()), eq(1), eq("T"), eq(true)).will(returnValue(colaboradorTurmas));
//		aproveitamentoAvaliacaoCursoManager.expects(once()).method("findColaboradorTurma").with(eq(turma.getId()), eq(1), eq("T"), eq(false)).will(returnValue(colabTurmasReprovados));
//		
//		colaboradorTurmaDao.expects(once()).method("findHistoricoTreinamentosByColaborador").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(colaboradorTurmas));
//		colaboradorTurmaDao.expects(once()).method("findColaboradorByTurma").with(eq(turma.getId())).will(returnValue(colaboradorTurmas));
//		//colaboradorTurmas = colaboradorTurmaManager.findHistoricoTreinamentosByColaborador(1L, 1L, null, null);
//		
//		assertTrue(((ColaboradorTurma)colaboradorTurmas.toArray()[0]).isAprovado());
//	}

	public void testFindColaboradoresByCursoTurmaIsNull()
	{
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmaDao.expects(once()).method("findColaboradoresByCursoTurmaIsNull").with(ANYTHING).will(returnValue(colaboradorTurmas));
		assertEquals(colaboradorTurmas, colaboradorTurmaManager.findColaboradoresByCursoTurmaIsNull(null));
	}

	public void testUpdateTurmaEPrioridade()
	{
		colaboradorTurmaDao.expects(once()).method("updateTurmaEPrioridade").with(ANYTHING, ANYTHING, ANYTHING).isVoid();
		colaboradorTurmaManager.updateTurmaEPrioridade(null, null, null);
	}

	public void testFindByTurmaCurso()
	{
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmaDao.expects(once()).method("findByTurmaCurso").with(ANYTHING).will(returnValue(colaboradorTurmas));
		assertEquals(colaboradorTurmas, colaboradorTurmaManager.findByTurmaCurso(null));
	}

	public void testFindByDNTColaboradores()
	{
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmaDao.expects(once()).method("findByDNTColaboradores").with(ANYTHING, ANYTHING).will(returnValue(colaboradorTurmas));
		assertEquals(colaboradorTurmas, colaboradorTurmaManager.findByDNTColaboradores(null, null));
	}

	public void testComparaEmpresa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);

		colaboradorTurmaDao.expects(once()).method("findEmpresaDoColaborador").with(eq(colaboradorTurma)).will(returnValue(empresa));
		assertEquals(true, colaboradorTurmaManager.comparaEmpresa(colaboradorTurma, empresa));
	}

	public void testComparaEmpresaInvalida()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Empresa empresa2 = EmpresaFactory.getEmpresa(2L);
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);

		colaboradorTurmaDao.expects(once()).method("findEmpresaDoColaborador").with(eq(colaboradorTurma)).will(returnValue(empresa2));
		assertEquals(false, colaboradorTurmaManager.comparaEmpresa(colaboradorTurma, empresa));
	}

	public void testVerifcaExisteNoCurso()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		Curso curso = CursoFactory.getEntity(1L);
		DNT dnt = DntFactory.getEntity(1L);

		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmaDao.expects(once()).method("findToList").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(colaboradorTurmas));
		assertEquals(false, colaboradorTurmaManager.verifcaExisteNoCurso(colaborador, curso, dnt));
	}

	public void testSetFamiliaAreas() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setAreaOrganizacional(areaOrganizacional);
		colaboradorTurma.setColaborador(colaborador);

		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmas.add(colaboradorTurma);

		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();

		areaOrganizacionalManager.expects(once()).method("findAllList").with(eq(empresa.getId()), ANYTHING).will(returnValue(areas));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").with(eq(areas)).will(returnValue(areas));
		areaOrganizacionalManager.expects(once()).method("getAreaOrganizacional").with(ANYTHING, ANYTHING).will(returnValue(new AreaOrganizacional()));

		assertEquals(1, colaboradorTurmaManager.setFamiliaAreas(colaboradorTurmas, empresa.getId()).size());
	}

	public void testCountAprovados()
	{
		Long empresaId = 1L;
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		Turma turma1 = TurmaFactory.getEntity(1L);
				
		Collection<Long> turmaIds = new ArrayList<Long>();
		turmaIds.add(turma1.getId());
		
		Collection<ColaboradorPresenca> colaboradorPresencas = new ArrayList<ColaboradorPresenca>();
		colaboradorPresencas.add(new ColaboradorPresenca(colaborador.getId(), turma1.getId(), 20));
		colaboradorPresencas.add(new ColaboradorPresenca(2L, turma1.getId(), 10));
		
		Collection<Turma> turmas = new ArrayList<Turma>();
		turmas.add(new Turma(turma1.getId(), 11.1));
		
		Collection<Long> colaboradorIds = new ArrayList<Long>();
		colaboradorIds.add(colaborador.getId());
		
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		colaboradores.add(colaborador);
		
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
		colaboradorTurma.setColaborador(colaborador);
		colaboradorTurma.setTurma(turma1);

		MockSpringUtil.mocks.put("colaboradorPresencaManager", colaboradorPresencaManager);
		MockSpringUtil.mocks.put("turmaManager", turmaManager);
		
		Date hoje = new Date();
		turmaManager.expects(once()).method("findByFiltro").with(eq(hoje), eq(hoje), eq('T'), eq(empresaId)).will(returnValue(turmas));
		
		colaboradorPresencaManager.expects(once()).method("findColabPresencaAprovOuRepAvaliacao").with(eq(turmaIds), eq(true)).will(returnValue(colaboradorPresencas));
		turmaManager.expects(once()).method("findTurmaPresencaMinima").with(eq(turmaIds)).will(returnValue(turmas));
		colaboradorManager.expects(once()).method("findAllSelect").with(eq(colaboradorIds)).will(returnValue(colaboradores));
	
		// aprovado
		assertEquals(1, colaboradorTurmaManager.countAprovados(hoje, hoje, empresaId, true).intValue());

		// reprovado
		turmaManager.expects(once()).method("findByFiltro").with(eq(hoje), eq(hoje), eq('T'), eq(empresaId)).will(returnValue(turmas));
		colaboradorPresencaManager.expects(once()).method("findColabPresencaAprovOuRepAvaliacao").with(eq(turmaIds), eq(true)).will(returnValue(colaboradorPresencas));
		turmaManager.expects(once()).method("findTurmaPresencaMinima").with(eq(turmaIds)).will(returnValue(turmas));
		colaboradorManager.expects(once()).method("findAllSelect").with(eq(colaboradorIds)).will(returnValue(colaboradores));
		colaboradorManager.expects(once()).method("qtdColaboradoresByTurmas").with(eq(turmaIds)).will(returnValue(colaboradores.size()));
		
		assertEquals(0, colaboradorTurmaManager.countAprovados(hoje, hoje, empresaId, false).intValue());
		
	}
	
	public void testSaveColaboradorTurmaNota() throws Exception
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		Turma turma = TurmaFactory.getEntity(1L);
		turma.setCurso(CursoFactory.getEntity(32L));
		
		AvaliacaoCurso avaliacaoCurso1 = AvaliacaoCursoFactory.getEntity(1L);
		AvaliacaoCurso avaliacaoCurso2 = AvaliacaoCursoFactory.getEntity(2L);
		AvaliacaoCurso avaliacaoCurso3 = AvaliacaoCursoFactory.getEntity(3L);

		Long[] avaliacaoCursoIds = new Long[]{avaliacaoCurso1.getId(), avaliacaoCurso2.getId(), avaliacaoCurso3.getId()};
		
		String[] notas = new String[]{"","3.1","5,5"};
		
		colaboradorTurmaDao.expects(once()).method("findByColaboradorAndTurma").with(eq(turma.getId()), eq(colaborador.getId())).will(returnValue(null));
		colaboradorTurmaDao.expects(once()).method("save").with(ANYTHING).isVoid();
		aproveitamentoAvaliacaoCursoManager.expects(once()).method("saveNotas").with(ANYTHING, eq(notas), eq(avaliacaoCursoIds)).isVoid();
		colaboradorTurmaManager.saveColaboradorTurmaNota(turma, colaborador, avaliacaoCursoIds, notas);
	}

	//TODO BACALHAU, refatorar metodo
//	public void testFindAprovadosByCertificacao()
//	{
//		Long colaboradorId = 1L;
//		Collection<Curso> cursos = new ArrayList<Curso>();
//		Curso curso1 = new Curso(); curso1.setId(1L); cursos.add(curso1);
//		Curso curso2 = new Curso(); curso2.setId(2L); cursos.add(curso2);
//		Curso curso3 = new Curso(); curso3.setId(3L); cursos.add(curso3);
//
//		Collection<ColaboradorTurma> cursosInscrito = new ArrayList<ColaboradorTurma>();
//		ColaboradorTurma colaboradorTurma1 = new ColaboradorTurma();
//		colaboradorTurma1.setColaboradorId(colaboradorId);
//		colaboradorTurma1.setCursoId(1L);
//		ColaboradorTurma colaboradorTurma2 = new ColaboradorTurma();
//		colaboradorTurma2.setColaboradorId(colaboradorId);
//		colaboradorTurma2.setCursoId(2L);
//		ColaboradorTurma colaboradorTurma3 = new ColaboradorTurma();
//		colaboradorTurma3.setColaboradorId(colaboradorId);
//		colaboradorTurma3.setCursoId(3L);
//		cursosInscrito.add(colaboradorTurma1);
//		cursosInscrito.add(colaboradorTurma2);
//		cursosInscrito.add(colaboradorTurma3);
//
//		Collection<Long> colaboradorIdsAprovados = new ArrayList<Long>();
//		colaboradorIdsAprovados.add(colaboradorId);
//
//		Colaborador colaborador = new Colaborador();
//		colaborador.setId(colaboradorId);
//		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
//		colaboradores.add(colaborador);
//
//		avaliacaoCursoManager.expects(once()).method("countAvaliacoes").with(ANYTHING).will(returnValue(3));
//		aproveitamentoAvaliacaoCursoManager.expects(once()).method("find").with(ANYTHING, eq(3), eq(true)).will(returnValue(colaboradorIdsAprovados));
//
//		colaboradorTurmaDao.expects(once()).method("findHistoricoTreinamentosByColaborador").with(ANYTHING, eq(colaboradorId), eq(null), eq(null)).will(returnValue(cursosInscrito));
//		colaboradorManager.expects(once()).method("findAllSelect").with(eq(colaboradorIdsAprovados)).will(returnValue(colaboradores));
//
//		Collection<Colaborador> resultado = colaboradorTurmaManager.findAprovadosByCertificacao(1L, cursos);
//		assertEquals(1, resultado.size());
//	}
//
//	public void testFindAprovadosByCertificacaoReprovadoNaoInscrito()
//	{
//		// Existem 3 cursos, e o colaborador está inscrito em apenas 2.
//		Empresa empresa = EmpresaFactory.getEmpresa(1L);
//
//		Long colaboradorId = 1L;
//		Collection<Curso> cursos = new ArrayList<Curso>();
//		Curso curso1 = new Curso(); curso1.setId(1L); curso1.setEmpresa(empresa); cursos.add(curso1);
//		Curso curso2 = new Curso(); curso2.setId(2L); curso1.setEmpresa(empresa); cursos.add(curso2);
//		Curso curso3 = new Curso(); curso3.setId(3L); curso1.setEmpresa(empresa); cursos.add(curso3);
//		
//		CollectionUtil<Curso> util = new CollectionUtil<Curso>();
//		Long[] cursoIds = util.convertCollectionToArrayIds(cursos);
//		
//		Collection<ColaboradorTurma> cursosInscrito = new ArrayList<ColaboradorTurma>();
//		ColaboradorTurma colaboradorTurma1 = new ColaboradorTurma();
//		colaboradorTurma1.setColaboradorId(colaboradorId);
//		colaboradorTurma1.setCursoId(1L);
//		ColaboradorTurma colaboradorTurma2 = new ColaboradorTurma();
//		colaboradorTurma2.setColaboradorId(colaboradorId);
//		colaboradorTurma2.setCursoId(2L);
//		ColaboradorTurma colaboradorTurma3 = new ColaboradorTurma();
//		colaboradorTurma3.setColaboradorId(colaboradorId);
//		colaboradorTurma3.setCursoId(3L);
//		cursosInscrito.add(colaboradorTurma1);
//		cursosInscrito.add(colaboradorTurma2);
//
//		Turma turma1 = TurmaFactory.getEntity(1L);
//		turma1.setCurso(curso1);
//		Turma turma2 = TurmaFactory.getEntity(2L);
//		turma2.setCurso(curso2);
//		Turma turma3 = TurmaFactory.getEntity(3L);
//		turma3.setCurso(curso3);
//		
//		Collection<Turma> turmas = new ArrayList<Turma>();
//		turmas.add(turma1);
//		turmas.add(turma2);
//		turmas.add(turma3);
//		
//		CollectionUtil<Turma> turmaUtil = new CollectionUtil<Turma>();
//		Long[] turmaIds = turmaUtil.convertCollectionToArrayIds(turmas);
//
//		Collection<Long> colaboradorIdsAprovados = new ArrayList<Long>();
//		colaboradorIdsAprovados.add(colaboradorId);
//
//		Colaborador colaborador = new Colaborador();
//		colaborador.setId(colaboradorId);
//		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
//		colaboradores.add(colaborador);
//
//		avaliacaoCursoManager.expects(once()).method("countAvaliacoes").with(ANYTHING).will(returnValue(3));
//		aproveitamentoAvaliacaoCursoManager.expects(once()).method("find").with(ANYTHING, eq(3), eq(true)).will(returnValue(colaboradorIdsAprovados));
//
//		colaboradorTurmaDao.expects(once()).method("findHistoricoTreinamentosByColaborador").with(ANYTHING, eq(colaboradorId), eq(null), eq(null)).will(returnValue(cursosInscrito));
//
//		cursoManager.expects(once()).method("findTurmas").with(ANYTHING, ANYTHING).will(returnValue(turmaIds));
//		
//		colaboradorTurmaManager.colaboradorIdAprovadoFrequencias(empresa.getId(),cursoIds);
//		
//		Collection<Colaborador> resultado = colaboradorTurmaManager.findAprovadosByCertificacao(1L, cursos);
//		assertEquals(0, resultado.size());
//	}
//	
//	public void testMontaRelatorioColaboradorCertificacao() throws Exception
//	{
//		Long empresaId=1L;
//		Long[] areaIds = new Long[]{1L};
//		Long[] estabelecimentoIds = new Long[]{1L,2L};
//		
//		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity(100L);
//		avaliacaoCurso.setTipo(TipoAvaliacaoCurso.NOTA);
//		avaliacaoCurso.setMinimoAprovacao(7d);
//		Collection<AvaliacaoCurso> avaliacaoCursos = new ArrayList<AvaliacaoCurso>();
//		avaliacaoCursos.add(avaliacaoCurso);
//		
//		Curso curso = CursoFactory.getEntity(10L);
//		curso.setAvaliacaoCursos(avaliacaoCursos);
//		Curso cursoSemAvaliacao = CursoFactory.getEntity(10L);
//		cursoSemAvaliacao.setAvaliacaoCursos(null);
//		Collection<Curso> cursos = new ArrayList<Curso>();
//		cursos.add(curso);
//		cursos.add(cursoSemAvaliacao);
//		
//		Certificacao certificacao = CertificacaoFactory.getEntity(5L);
//		certificacao.setNome("Habilidades Interpessoais");
//		certificacao.setCursos(cursos);
////		
////		Collection<Certificacao> certificacaos = new ArrayList<Certificacao>();
////		certificacaos.add(certificacao);
////		
////		Colaborador colaborador = ColaboradorFactory.getEntity(1000L);
////		colaborador.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity(1L));
////		colaborador.setEstabelecimento(EstabelecimentoFactory.getEntity(1L));
////		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(10L);
////		faixaSalarial.setCertificacaos(certificacaos);
////		colaborador.setFaixaSalarial(faixaSalarial);
////		
////		Colaborador colaborador2 = ColaboradorFactory.getEntity(1001L);
////		colaborador2.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity(1L));
////		colaborador2.setEstabelecimento(EstabelecimentoFactory.getEntity(2L));
////		colaborador.setFaixaSalarial(faixaSalarial);
////		
////		Turma turma = TurmaFactory.getEntity(31L);
////		turma.setCurso(curso);
////		turma.setDescricao("Turma A");
////		
////		Turma turmaDoCursoSemAvaliacao = TurmaFactory.getEntity(31L);
////		turmaDoCursoSemAvaliacao.setCurso(curso);
////		turmaDoCursoSemAvaliacao.setDescricao("Turma Zé Moleza");
////		
////		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(50L);
////		colaboradorTurma.setCurso(curso);
////		colaboradorTurma.setTurma(turma);
////		colaboradorTurma.setColaborador(colaborador);
////		
////		ColaboradorTurma colaboradorTurmaCursoSemAvaliacao = ColaboradorTurmaFactory.getEntity(55L);
////		colaboradorTurmaCursoSemAvaliacao.setCurso(cursoSemAvaliacao);
////		colaboradorTurmaCursoSemAvaliacao.setTurma(turmaDoCursoSemAvaliacao);
////		colaboradorTurmaCursoSemAvaliacao.setColaborador(colaborador2);
////		
////		Collection<ColaboradorTurma> colaboradorTurmas  = new ArrayList<ColaboradorTurma>();
////		colaboradorTurmas.add(colaboradorTurma);
////		colaboradorTurmas.add(colaboradorTurmaCursoSemAvaliacao);
////		
////		Collection<ColaboradorTurma> colabTurmasAprovados = new ArrayList<ColaboradorTurma>();
////		colabTurmasAprovados.add(colaboradorTurma);
////		
////		
////		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
////		areaOrganizacionalManager.expects(once()).method("findAllList").with(eq(empresaId), ANYTHING).will(returnValue(areas));
////		areaOrganizacionalManager.expects(once()).method("montaFamilia").with(eq(areas)).will(returnValue(areas));
////		areaOrganizacionalManager.expects(atLeastOnce()).method("getAreaOrganizacional").with(ANYTHING, ANYTHING).will(returnValue(new AreaOrganizacional()));
////		
////		// curso sem avaliação
////		avaliacaoCursoManager.expects(once()).method("countAvaliacoes").with(eq(colaboradorTurmaCursoSemAvaliacao.getTurma().getId()),eq("T")).will(returnValue(0));
////		
////		// curso com avaliação, colaboradorTurma aprovado
////		avaliacaoCursoManager.expects(once()).method("countAvaliacoes").with(eq(colaboradorTurma.getTurma().getId()),eq("T")).will(returnValue(1));
////		//aprovados + nota
////		aproveitamentoAvaliacaoCursoManager.expects(once()).method("findColaboradorTurma").with(eq(turma.getId()), eq(1), eq("T"), eq(true)).will(returnValue(colabTurmasAprovados));
////		//reprovados + nota
////		aproveitamentoAvaliacaoCursoManager.expects(once()).method("findColaboradorTurma").with(eq(turma.getId()), eq(1), eq("T"), eq(false)).will(returnValue(new ArrayList<ColaboradorTurma>()));
//
////		colaboradorTurmaDao.expects(once()).method("findHistoricoTreinamentosByColaborador").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(colaboradorTurmas));
////		avaliacaoCursoManager.expects(atLeastOnce()).method("countAvaliacoes").with(eq(turma.getId()), eq("T")).will(returnValue(1));
////		colaboradorTurmaDao.expects(once()).method("findColaboradorByTurma").with(eq(turma.getId())).will(returnValue(colaboradorTurmas));
//
//		certificacaoManager.expects(once()).method("findById").with(eq(5L)).will(returnValue(certificacao));
//		cursoManager.expects(once()).method("findByCertificacao").will(returnValue(cursos));
//		colaboradorTurmaDao.expects(once()).method("findColaboradoresCertificacoes").with(eq(empresaId),eq(certificacao),eq(areaIds),eq(estabelecimentoIds)).will(returnValue(colaboradorTurmas));
//
//		Collection<ColaboradorCertificacaoRelatorio> resultado = colaboradorTurmaManager.montaRelatorioColaboradorCertificacao(empresaId, certificacao, areaIds, estabelecimentoIds);
//		
//		// 2 Colaboradores X 2 Cursos da Certificação 
//		assertEquals(4, resultado.size());
//	}
	
	public void testMontaRelatorioColaboradorCertificacaoColecaoVazia() throws Exception
	{
		Certificacao certificacao = CertificacaoFactory.getEntity(5L);

		certificacaoManager.expects(atLeastOnce()).method("findById").with(eq(5L)).will(returnValue(certificacao));
		cursoManager.expects(atLeastOnce()).method("findByCertificacao").will(returnValue(new ArrayList<Curso>()));
		colaboradorTurmaDao.expects(once()).method("findColaboradoresCertificacoes").will(returnValue(new ArrayList<ColaboradorTurma>()));
		colaboradorTurmaDao.expects(once()).method("findColaboradoresCertificacoes").will(returnValue(null));
		
		Exception ex=null;
		try {
			colaboradorTurmaManager.montaRelatorioColaboradorCertificacao(1L, certificacao, new Long[]{1L}, new Long[]{1L});
		}
		catch (ColecaoVaziaException e) { ex = e; }
		assertNotNull(ex);
		
		ex=null;
		try {
			colaboradorTurmaManager.montaRelatorioColaboradorCertificacao(1L, certificacao, new Long[]{1L}, new Long[]{1L});
		}
		catch (ColecaoVaziaException e) { ex = e; }
		assertNotNull(ex);
	}
	
	public void testFindRelatorioComTreinamento() throws Exception
	{
		Curso curso = CursoFactory.getEntity(10L);
		Long empresaId=1L;
		Long[] areaIds=null;
		Long[] estabelecimentoIds=null;
		char aprovadoFiltro='T';
		
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(100L);
		colaboradorTurmas.add(colaboradorTurma);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1000L);
		colaborador.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity(1L));
		colaboradorTurma.setColaborador(colaborador);
		
		// Sem avaliação
		avaliacaoCursoManager.expects(once()).method("countAvaliacoes").with(eq(curso.getId()),eq("C")).will(returnValue(0));
		colaboradorTurmaDao.expects(once()).method("findRelatorioComTreinamento").will(returnValue(colaboradorTurmas));
		mockValidaRelatorio(curso, empresaId);
		
		Collection<ColaboradorTurma> resultadoSemAvaliacao = colaboradorTurmaManager.findRelatorioComTreinamento(empresaId, curso, areaIds, estabelecimentoIds, aprovadoFiltro);
		
		assertEquals(1,resultadoSemAvaliacao.size());
		
		ColaboradorTurma colabTurma = (ColaboradorTurma) resultadoSemAvaliacao.toArray()[0];
		assertEquals(true,colabTurma.isAprovado());
		
		// Com avaliação
		
		ColaboradorTurma colaboradorTurma2 = ColaboradorTurmaFactory.getEntity(101L);
		colaboradorTurmas.add(colaboradorTurma2);
		colaborador.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity(1L));
		colaboradorTurma2.setColaborador(colaborador);
		
		Collection<ColaboradorTurma> colaboradorTurmasReprovados = new ArrayList<ColaboradorTurma>();
		colaboradorTurma2.setAprovado(false);
		colaboradorTurma2.setValorAvaliacao(5.0);
		colaboradorTurmasReprovados.add(colaboradorTurma2);
		
		avaliacaoCursoManager.expects(once()).method("countAvaliacoes").with(eq(curso.getId()),eq("C"))
				.will(returnValue(1));
		colaboradorTurmaDao.expects(once()).method("findRelatorioComTreinamento").will(returnValue(colaboradorTurmas));
		Collection<Long> colabTurmasAprovados = new ArrayList<Long>();
		colabTurmasAprovados.add(100L);
		aproveitamentoAvaliacaoCursoManager.expects(once()).method("find").with(eq(curso.getId()),eq(1),eq("C"),eq(true)).will(returnValue(colabTurmasAprovados));
		aproveitamentoAvaliacaoCursoManager.expects(once()).method("findColaboradorTurma").with(eq(curso.getId()),eq(1),eq("C"),eq(false)).will(returnValue(colaboradorTurmasReprovados));
		mockValidaRelatorio(curso, empresaId);
		
		Collection<ColaboradorTurma> resultadoComAvaliacao = colaboradorTurmaManager.findRelatorioComTreinamento(empresaId, curso, areaIds, estabelecimentoIds, aprovadoFiltro);
		
		assertEquals(2, resultadoComAvaliacao.size());
	}
	
	public void testFindRelatorioComTreinamentoApenasAprovadosOuReprovados() throws Exception
	{
		Curso curso = CursoFactory.getEntity(10L);
		Long empresaId=1L;
		Long[] areaIds=null;
		Long[] estabelecimentoIds=null;
		char aprovadoFiltro='S';
		
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(100L);
		colaboradorTurmas.add(colaboradorTurma);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1000L);
		colaborador.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity(1L));
		colaboradorTurma.setColaborador(colaborador);
		
		Collection<Long> colabTurmasIds = new ArrayList<Long>();
		colabTurmasIds.add(100L);
		
		avaliacaoCursoManager.expects(once()).method("countAvaliacoes").with(eq(curso.getId()),eq("C")).will(returnValue(1));
		aproveitamentoAvaliacaoCursoManager.expects(once()).method("find").with(eq(curso.getId()),eq(1),eq("C"),eq(true)).will(returnValue(colabTurmasIds));
		colaboradorTurmaDao.expects(once()).method("findRelatorioComTreinamento").will(returnValue(colaboradorTurmas));
		mockValidaRelatorio(curso, empresaId);
		
		Collection<ColaboradorTurma> resultadoAprovados = colaboradorTurmaManager.findRelatorioComTreinamento(empresaId, curso, areaIds, estabelecimentoIds, aprovadoFiltro);
		
		assertEquals(1, resultadoAprovados.size());
		
		ColaboradorTurma colabTurma = (ColaboradorTurma) resultadoAprovados.toArray()[0];
		assertEquals(true,colabTurma.isAprovado());
		
		// Reprovados
		colaborador.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity(1L));
		aprovadoFiltro = 'N';
		
		avaliacaoCursoManager.expects(once()).method("countAvaliacoes").with(eq(curso.getId()),eq("C")).will(returnValue(1));
		aproveitamentoAvaliacaoCursoManager.expects(once()).method("find").with(eq(curso.getId()),eq(1),eq("C"),eq(false)).will(returnValue(colabTurmasIds));
		colaboradorTurmaDao.expects(once()).method("findRelatorioComTreinamento").will(returnValue(colaboradorTurmas));
		aproveitamentoAvaliacaoCursoManager.expects(once()).method("findColaboradorTurma").with(eq(curso.getId()),eq(1),eq("C"),eq(false)).will(returnValue(colaboradorTurmas));
		mockValidaRelatorio(curso, empresaId);
		
		Collection<ColaboradorTurma> resultadoReprovados = colaboradorTurmaManager.findRelatorioComTreinamento(empresaId, curso, areaIds, estabelecimentoIds, aprovadoFiltro);
		ColaboradorTurma reprovado = (ColaboradorTurma) resultadoReprovados.toArray()[0];
		assertEquals(false,reprovado.isAprovado());
	}
	
	public void testFindRelatorioComTreinamentoColecaoVaziaException()
	{
		Curso curso = CursoFactory.getEntity(10L);
		Long empresaId=1L;
		Long[] areaIds=null;
		Long[] estabelecimentoIds=null;
		char aprovadoFiltro='N';
		
		avaliacaoCursoManager.expects(once()).method("countAvaliacoes").with(eq(curso.getId()),eq("C")).will(returnValue(0));
		aproveitamentoAvaliacaoCursoManager.expects(once()).method("find").with(eq(curso.getId()),eq(0),eq("C"),eq(false)).will(returnValue(new ArrayList<Long>()));
		
		Exception exception = null;
		try
		{
			colaboradorTurmaManager.findRelatorioComTreinamento(empresaId, curso, areaIds, estabelecimentoIds, aprovadoFiltro);
		}
		catch (Exception e) { exception = e; }
		
		assertTrue(exception instanceof ColecaoVaziaException);
	}
	
	public void testFindRelatorioSemTreinamento() throws Exception
	{
		Curso curso = CursoFactory.getEntity(10L);
		Long empresaId=1L;
		Long[] areaIds=null;
		Long[] estabelecimentoIds=null;
		
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(100L);
		colaboradorTurmas.add(colaboradorTurma);
		Colaborador colaborador = ColaboradorFactory.getEntity(1000L);
		colaborador.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity(1L));
		colaboradorTurma.setColaborador(colaborador);
		
		colaboradorTurmaDao.expects(once()).method("findRelatorioSemTreinamento").will(returnValue(colaboradorTurmas));
		mockValidaRelatorio(curso, empresaId);
		
		Collection<ColaboradorTurma> resultado = colaboradorTurmaManager.findRelatorioSemTreinamento(empresaId, curso, areaIds, estabelecimentoIds);
		
		assertEquals(1, resultado.size());
	}
	
	public void testFindRelatorioSemTreinamentoColecaoVazia() throws Exception
	{
		Curso curso = CursoFactory.getEntity(10L);
		Long empresaId=1L;
		Long[] areaIds=null;
		Long[] estabelecimentoIds=null;
		
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		
		colaboradorTurmaDao.expects(once()).method("findRelatorioSemTreinamento").will(returnValue(colaboradorTurmas));
		
		Exception exception = null;
		try
		{
			colaboradorTurmaManager.findRelatorioSemTreinamento(empresaId, curso, areaIds, estabelecimentoIds);
		}
		catch (Exception e) { exception = e; }
		
		assertTrue(exception instanceof ColecaoVaziaException);
	}

	private void mockValidaRelatorio(Curso curso, Long empresaId) {
		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
		areaOrganizacionalManager.expects(once()).method("findAllList").with(eq(empresaId), ANYTHING).will(returnValue(areas));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").with(eq(areas)).will(returnValue(areas));
		areaOrganizacionalManager.expects(once()).method("getAreaOrganizacional").with(ANYTHING, ANYTHING).will(returnValue(new AreaOrganizacional()));
		cursoManager.expects(atLeastOnce()).method("findByIdProjection").will(returnValue(curso));
	}
	
	public void testFindByTurmaSemPresenca() {
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmaDao.expects(once()).method("findByTurmaSemPresenca").will(returnValue(colaboradorTurmas));
		colaboradorTurmaManager.findByTurmaSemPresenca(1L, 10L);
	}
	public void testFindByIdProjectionComArray() {
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmas.add(ColaboradorTurmaFactory.getEntity(1L));
		colaboradorTurmas.add(ColaboradorTurmaFactory.getEntity(2L));
		Long[] ids = new Long[]{1L,2L};
		
		colaboradorTurmaDao.expects(once()).method("findByIdProjection").will(returnValue(colaboradorTurmas));
		assertEquals(2, colaboradorTurmaManager.findByIdProjection(ids).size());
	}
	
	public void testFindAprovadosByTurma() 
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		Turma turma1 = TurmaFactory.getEntity(1L);
				
		Collection<Long> turmaIds = new ArrayList<Long>();
		turmaIds.add(turma1.getId());
		
		Collection<ColaboradorPresenca> colaboradorPresencas = new ArrayList<ColaboradorPresenca>();
		colaboradorPresencas.add(new ColaboradorPresenca(colaborador.getId(), turma1.getId(), 20));
		colaboradorPresencas.add(new ColaboradorPresenca(2L, turma1.getId(), 10));
		
		Collection<Turma> turmas = new ArrayList<Turma>();
		turmas.add(new Turma(turma1.getId(), 11.1));
		
		Collection<Long> colaboradorIds = new ArrayList<Long>();
		colaboradorIds.add(colaborador.getId());
		
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		colaboradores.add(colaborador);
		
		MockSpringUtil.mocks.put("colaboradorPresencaManager", colaboradorPresencaManager);
		MockSpringUtil.mocks.put("turmaManager", turmaManager);
		
		colaboradorPresencaManager.expects(once()).method("findColabPresencaAprovOuRepAvaliacao").with(eq(turmaIds), eq(true)).will(returnValue(colaboradorPresencas));
		turmaManager.expects(once()).method("findTurmaPresencaMinima").with(eq(turmaIds)).will(returnValue(turmas));
		colaboradorManager.expects(once()).method("findAllSelect").with(eq(colaboradorIds)).will(returnValue(colaboradores));
		
		Collection<Colaborador> retorno = colaboradorTurmaManager.findAprovadosByTurma(turmaIds);
		assertEquals(1, retorno.size());
		assertEquals(colaborador.getId(), ((Colaborador)retorno.toArray()[0]).getId());
	}

	public void testPercentualFrequencia() 
	{
		Empresa empresa = new Empresa();
		empresa.setId(1L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		
		Turma turma = TurmaFactory.getEntity(1L);
		
		Collection<Long> turmaIds = new ArrayList<Long>();
		turmaIds.add(turma.getId());
		
		Collection<Turma> turmas = new ArrayList<Turma>();
		turmas.add(new Turma(turma.getId(), 11.1));
		
		DiaTurma diaTurma1 = DiaTurmaFactory.getEntity(1L);
		diaTurma1.setDia(new Date());
		diaTurma1.setTurma(turma);
		
		DiaTurma diaTurma2 = DiaTurmaFactory.getEntity(2L);
		diaTurma2.setDia(new Date());
		diaTurma2.setTurma(turma);

		Collection<ColaboradorPresenca> colaboradorPresencas = new ArrayList<ColaboradorPresenca>();
		colaboradorPresencas.add(new ColaboradorPresenca(colaborador.getId(), turma.getId(), 20));
		
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
		colaboradorTurma.setColaborador(colaborador);
		colaboradorTurma.setTurma(turma);
		
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		colaboradores.add(colaborador);
		
		MockSpringUtil.mocks.put("colaboradorPresencaManager", colaboradorPresencaManager);
		MockSpringUtil.mocks.put("turmaManager", turmaManager);
		MockSpringUtil.mocks.put("diaTurmaManager", diaTurmaManager);

		turmaManager.expects(once()).method("findByFiltro").with(ANYTHING, ANYTHING, eq('T'), eq(empresa.getId())).will(returnValue(turmas));
		colaboradorManager.expects(atLeastOnce()).method("qtdColaboradoresByTurmas").with(eq(turmaIds)).will(returnValue(colaboradores.size()));
		diaTurmaManager.expects(atLeastOnce()).method("qtdDiasDasTurmas").with(eq(turma.getId())).will(returnValue(new Integer(2)));
		colaboradorPresencaManager.expects(once()).method("qtdDiaPresentesTurma").with(eq(turma.getId())).will(returnValue(new Integer(1)));
		
		assertEquals(50.0, colaboradorTurmaManager.percentualFrequencia(null, null, empresa.getId()));

		// quando colaboradores retornar 0 ( divisão por zero)
		turmaManager.expects(once()).method("findByFiltro").with(ANYTHING, ANYTHING, eq('T'), eq(empresa.getId())).will(returnValue(turmas));
		colaboradorManager.expects(atLeastOnce()).method("qtdColaboradoresByTurmas").with(eq(turmaIds)).will(returnValue(new Integer(0)));
		diaTurmaManager.expects(atLeastOnce()).method("qtdDiasDasTurmas").with(eq(turma.getId())).will(returnValue(new Integer(2)));
		colaboradorPresencaManager.expects(once()).method("qtdDiaPresentesTurma").with(eq(turma.getId())).will(returnValue(new Integer(1)));
		
		assertEquals(100.0, colaboradorTurmaManager.percentualFrequencia(null, null, empresa.getId()));
	}
	
// refatorar Samuel
//	public void testfindAprovadosByCertificacao() 
//	{
//		Colaborador colaboradorAprovado1 = ColaboradorFactory.getEntity(1L);
//		Colaborador colaboradorAprovado2 = ColaboradorFactory.getEntity(2L);
//		Colaborador colaboradorReprovado1 = ColaboradorFactory.getEntity(3L);
//		Colaborador colaboradorReprovado2 = ColaboradorFactory.getEntity(4L);
//		
//		AvaliacaoCurso avaliacaoCursoAprovados = AvaliacaoCursoFactory.getEntity(1L);
//		avaliacaoCursoAprovados.setMinimoAprovacao(2.0);
//		AvaliacaoCurso avaliacaoCursoReprovados = AvaliacaoCursoFactory.getEntity(2L);
//		avaliacaoCursoReprovados.setMinimoAprovacao(8.0);
//		
//		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCursoAprovado = new AproveitamentoAvaliacaoCurso();
//		aproveitamentoAvaliacaoCursoAprovado.setAvaliacaoCurso(avaliacaoCursoAprovados);
//		aproveitamentoAvaliacaoCursoAprovado.setValor(7.0);
//		AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCursoReprovado = new AproveitamentoAvaliacaoCurso();
//		aproveitamentoAvaliacaoCursoReprovado.setAvaliacaoCurso(avaliacaoCursoReprovados);
//		aproveitamentoAvaliacaoCursoReprovado.setValor(3.0);
//		
//		Collection<AproveitamentoAvaliacaoCurso> aproveitamentoAvaliacaoCursoAprovados = new ArrayList<AproveitamentoAvaliacaoCurso>();
//		aproveitamentoAvaliacaoCursoAprovados.add(aproveitamentoAvaliacaoCursoAprovado);
//		Collection<AproveitamentoAvaliacaoCurso> aproveitamentoAvaliacaoCursoReprovados = new ArrayList<AproveitamentoAvaliacaoCurso>();
//		aproveitamentoAvaliacaoCursoReprovados.add(aproveitamentoAvaliacaoCursoReprovado);
//
//		Curso curso = CursoFactory.getEntity(1L);
//		Turma turma = TurmaFactory.getEntity(1L);
//		turma.setRealizada(true);
//		turma.setDiasEstimadosParaAprovacao(3.0);
//		turma.setCurso(curso);
//		
//		ColaboradorTurma colaboradorTurmaAprovado = new ColaboradorTurma();
//		colaboradorTurmaAprovado.setId(1L);
//		colaboradorTurmaAprovado.setAproveitamentoAvaliacaoCursos(aproveitamentoAvaliacaoCursoAprovados);
//		colaboradorTurmaAprovado.setColaborador(colaboradorAprovado1);
//		colaboradorTurmaAprovado.setColaborador(colaboradorAprovado2);
//		colaboradorTurmaAprovado.setTurma(turma);
//		
//		ColaboradorTurma colaboradorTurmaReprovado = new ColaboradorTurma();
//		colaboradorTurmaReprovado.setId(2L);
//		colaboradorTurmaReprovado.setAproveitamentoAvaliacaoCursos(aproveitamentoAvaliacaoCursoReprovados);
//		colaboradorTurmaReprovado.setColaborador(colaboradorReprovado1);
//		colaboradorTurmaReprovado.setColaborador(colaboradorReprovado2);
//		colaboradorTurmaReprovado.setTurma(turma);
//		
//		ColaboradorPresenca colaboradorPresencaAprovado = new ColaboradorPresenca(colaboradorAprovado1.getId(), turma.getId(), 20);
//		colaboradorPresencaAprovado.setDiasPresente(20);
//		colaboradorPresencaAprovado.setColaboradorTurma(colaboradorTurmaAprovado);
//		
//		ColaboradorPresenca colaboradorPresencaReprovado = new ColaboradorPresenca(colaboradorAprovado1.getId(), turma.getId(), 2);
//		colaboradorPresencaReprovado.setColaboradorTurma(colaboradorTurmaReprovado);
//		colaboradorPresencaReprovado.setDiasPresente(1);
//		
//		Collection<Long> turmaIds = new ArrayList<Long>();
//		turmaIds.add(turma.getId());
//		Collection<Turma> turmas = new ArrayList<Turma>();
//		turmas.add(turma);
//		
//		Collection<Curso> cursos = new ArrayList<Curso>();
//		cursos.add(curso);
//
//		Collection<Colaborador> colaboradorAprovados = new ArrayList<Colaborador>();
//		colaboradorAprovados.add(colaboradorAprovado1);
//		colaboradorAprovados.add(colaboradorAprovado2);
//
//		Collection<Colaborador> colaboradorReprovados = new ArrayList<Colaborador>();
//		colaboradorReprovados.add(colaboradorReprovado1);
//		colaboradorReprovados.add(colaboradorReprovado2);
//
//		Collection<Long> colaboradorAprovadoIds = LongUtil.collectionToCollectionLong(colaboradorAprovados);
//		Collection<Long> colaboradorReprovadoIds = LongUtil.collectionToCollectionLong(colaboradorReprovados);
//		
//		MockSpringUtil.mocks.put("colaboradorPresencaManager", colaboradorPresencaManager);
//		MockSpringUtil.mocks.put("turmaManager", turmaManager);
//		
//		turmaManager.expects(atLeastOnce()).method("getTurmaFinalizadas").with(eq(curso.getId())).will(returnValue(turmas));
//		colaboradorPresencaManager.expects(atLeastOnce()).method("findColabPresencaAprovOuRepAvaliacao").with(eq(turmaIds), ANYTHING).will(returnValue(colaboradorAprovados));
//		turmaManager.expects(atLeastOnce()).method("findTurmaPresencaMinima").with(eq(turmaIds)).will(returnValue(turmas));
//		
//		colaboradorPresencaManager.expects(once()).method("findColabPresencaAprovOuRepAvaliacao").with(eq(turmaIds), eq(true)).will(returnValue(colaboradorAprovados));
//		colaboradorManager.expects(once()).method("findAllSelect").with(eq(colaboradorAprovadoIds)).will(returnValue(colaboradorReprovados));
//		
//		colaboradorPresencaManager.expects(once()).method("findColabPresencaAprovOuRepAvaliacao").with(eq(turmaIds), eq(false)).will(returnValue(colaboradorReprovados));
//		colaboradorManager.expects(once()).method("findAllSelect").with(eq(colaboradorReprovadoIds)).will(returnValue(colaboradorReprovados));
//		
//		assertEquals(4, colaboradorTurmaManager.findAprovadosByCertificacao(cursos).size());
//	}
//	

}