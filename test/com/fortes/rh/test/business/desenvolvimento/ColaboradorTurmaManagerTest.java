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
import org.jmock.cglib.MockObjectTestCase;
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
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.dao.desenvolvimento.ColaboradorTurmaDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.Certificado;
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
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorPresencaFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.DiaTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.DntFactory;
import com.fortes.rh.test.factory.desenvolvimento.PrioridadeTreinamentoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.Mail;
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
	private Mock empresaManager;
	private Mock diaTurmaManager;
	private Mock certificacaoManager;
	Mock turmaManager;
	Mock colaboradorPresencaManager;
	private Mock mail;

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

		empresaManager = new Mock(EmpresaManager.class);
		colaboradorTurmaManager.setEmpresaManager((EmpresaManager) empresaManager.proxy());

		turmaManager = new Mock(TurmaManager.class);

		colaboradorPresencaManager = new Mock(ColaboradorPresencaManager.class);
		
        mail = mock(Mail.class);
        colaboradorTurmaManager.setMail((Mail) mail.proxy());
        
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
	
	public void testCarregaResultados()
	{
		Collection<ColaboradorTurma> colaboradorTurmas = montaAprovadosReprovados();
		
		colaboradorTurmaManager.carregaResultados(colaboradorTurmas);
		
		assertEquals(true, getColaboradorTurma(1L, colaboradorTurmas).isAprovado());
		assertEquals(true, getColaboradorTurma(2L, colaboradorTurmas).isAprovado());
		assertEquals(true, getColaboradorTurma(3L, colaboradorTurmas).isAprovado());
		assertEquals(false, getColaboradorTurma(4L, colaboradorTurmas).isAprovado());
		
		ColaboradorTurma aprovNota = getColaboradorTurma(5L, colaboradorTurmas);
		assertEquals(true, aprovNota.isAprovado());
		assertEquals(9.0, aprovNota.getNota());
		
		ColaboradorTurma reprovNota = getColaboradorTurma(6L, colaboradorTurmas);
		assertEquals(false, reprovNota.isAprovado());
		assertEquals(null, reprovNota.getNota());
	}

	private Collection<ColaboradorTurma> montaAprovadosReprovados() 
	{
		Curso curso = CursoFactory.getEntity();
		curso.setPercentualMinimoFrequencia(50.00);

		Curso cursoSemMinimoFrequencia = CursoFactory.getEntity();
		cursoSemMinimoFrequencia.setPercentualMinimoFrequencia(null);
		
		ColaboradorTurma aprovadoFrequencia = ColaboradorTurmaFactory.getEntity(1L);
		aprovadoFrequencia.setCurso(curso);
		aprovadoFrequencia.setQtdPresenca(6);
		aprovadoFrequencia.setTotalDias(10);
		
		ColaboradorTurma aprovadoSemChamada = ColaboradorTurmaFactory.getEntity(2L);
		aprovadoSemChamada.setCurso(curso);
		aprovadoSemChamada.setTotalDias(null);
		
		ColaboradorTurma aprovadoCursoSemMinimoFrequencia = ColaboradorTurmaFactory.getEntity(3L);
		aprovadoCursoSemMinimoFrequencia.setCurso(cursoSemMinimoFrequencia);
		
		ColaboradorTurma reprovadoFrequencia = ColaboradorTurmaFactory.getEntity(4L);
		reprovadoFrequencia.setCurso(curso);
		reprovadoFrequencia.setQtdPresenca(3);
		reprovadoFrequencia.setTotalDias(10);
		
		ColaboradorTurma aprovadoNota = ColaboradorTurmaFactory.getEntity(5L);
		aprovadoNota.setQtdAvaliacoesCurso(1);
		aprovadoNota.setQtdAvaliacoesAprovadasPorNota(1);
		aprovadoNota.setNota(9.0);
		aprovadoNota.setCurso(curso);
		aprovadoNota.setQtdPresenca(6);
		aprovadoNota.setTotalDias(10);
		
		ColaboradorTurma reprovadoNota = ColaboradorTurmaFactory.getEntity(6L);
		reprovadoNota.setQtdAvaliacoesCurso(2);
		reprovadoNota.setQtdAvaliacoesAprovadasPorNota(1);
		reprovadoNota.setNota(5.0);
		reprovadoNota.setCurso(curso);
		reprovadoNota.setQtdPresenca(6);
		reprovadoNota.setTotalDias(10);
		
		ColaboradorTurma reprovadoNotaFrequencia = ColaboradorTurmaFactory.getEntity(7L);
		reprovadoNotaFrequencia.setQtdAvaliacoesCurso(2);
		reprovadoNotaFrequencia.setQtdAvaliacoesAprovadasPorNota(1);
		reprovadoNotaFrequencia.setNota(5.0);
		reprovadoNotaFrequencia.setCurso(curso);
		reprovadoNotaFrequencia.setQtdPresenca(2);
		reprovadoNotaFrequencia.setTotalDias(10);
		
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmas.add(aprovadoFrequencia);
		colaboradorTurmas.add(aprovadoSemChamada);
		colaboradorTurmas.add(aprovadoCursoSemMinimoFrequencia);
		colaboradorTurmas.add(reprovadoFrequencia);
		colaboradorTurmas.add(aprovadoNota);
		colaboradorTurmas.add(reprovadoNota);
		colaboradorTurmas.add(reprovadoNotaFrequencia);
		
		return colaboradorTurmas;
	}

	private ColaboradorTurma getColaboradorTurma(Long id, Collection<ColaboradorTurma> colaboradorTurmas) 
	{
		for (ColaboradorTurma colaboradorTurma : colaboradorTurmas) 
		{
			if(colaboradorTurma.getId().equals(id))
				return colaboradorTurma;
		}
		
		return null;
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

		colaboradorTurmaDao.expects(once()).method("findByTurma").with(new Constraint[]{ eq(turma.getId()), eq(null), ANYTHING, ANYTHING, ANYTHING }).will(returnValue(colaboradorTurmas));

		Collection<ColaboradorTurma> retornos = colaboradorTurmaManager.findByTurma(turma.getId(), null, null, null);

		assertEquals(1, retornos.size());
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

		colaboradorManager.expects(once()).method("findByAreasOrganizacionalIds").with(new Constraint[]{eq(page), eq(pagingSize), ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(colaboradores));
		
		
		assertEquals(1, colaboradorTurmaManager.filtrarColaboradores(page, pagingSize, areasCheck, cargosCheck, gruposCheck, colaboradoresCursosCheck, filtrarPor, turma, null, null, null, empresaId).size());
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

		historicoColaboradorManager.expects(once()).method("findByCargosIds").with(new Constraint[]{eq(page), eq(pagingSize), ANYTHING, ANYTHING, eq(empresaId
				)}).will(
				returnValue(colaboradores));
		assertEquals(1, colaboradorTurmaManager.filtrarColaboradores(page, pagingSize, areasCheck, cargosCheck, gruposCheck, colaboradoresCursosCheck,
				filtrarPor, turma, null, null, null, empresaId).size());
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
				colaboradoresCursosCheck, filtrarPor, turma, null, null, null, empresaId));
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
		assertNotNull(colaboradorTurmaManager.findHistoricoTreinamentosByColaborador(1L, null, null, 100L));
	}

	public void testFindRelatorioHistoricoTreinamentos() throws Exception
	{
		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador1);
		colaboradors.add(colaborador2);
		
		Curso curso = CursoFactory.getEntity(1L);
		curso.setPercentualMinimoFrequencia(0.0);
		
		Turma turma = TurmaFactory.getEntity(1L);
		turma.setCurso(curso);
		
		ColaboradorTurma colaboradorTurma1 = ColaboradorTurmaFactory.getEntity(1L);
		colaboradorTurma1.setTurma(turma);
		colaboradorTurma1.setColaborador(colaborador1);
		colaboradorTurma1.setColaborador(colaborador2);
		colaboradorTurma1.setValorAvaliacao(85.0);
		colaboradorTurma1.setAprovado(true);
		
		ColaboradorPresenca colaboradorPresenca = ColaboradorPresencaFactory.getEntity(1L);
		colaboradorPresenca.setColaboradorTurma(colaboradorTurma1);
		
		Collection<ColaboradorPresenca> colaboradorPresencas = new ArrayList<ColaboradorPresenca>();
		colaboradorPresencas.add(new ColaboradorPresenca(colaborador1.getId(), turma.getId(), 20));
		colaboradorPresencas.add(new ColaboradorPresenca(2L, turma.getId(), 10));
		
		Collection<Turma> turmas = new ArrayList<Turma>();
		turmas.add(new Turma(turma.getId(), 11.1));
		
		Collection<Long> colaboradorIds = new ArrayList<Long>();
		colaboradorIds.add(colaborador1.getId());
		
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		colaboradores.add(colaborador1);
		
		DiaTurma diaTurma1 = DiaTurmaFactory.getEntity(1L);
		diaTurma1.setTurma(turma);

		DiaTurma diaTurma2 = DiaTurmaFactory.getEntity(2L);
		diaTurma2.setTurma(turma);
		
		Collection<ColaboradorTurma> colaboradorTurmasAprovados = new ArrayList<ColaboradorTurma>();
		colaboradorTurmasAprovados.add(colaboradorTurma1);
		
		int qrdAvaliacoes = 1;

		colaboradorTurmaDao.expects(once()).method("findHistoricoTreinamentosByColaborador").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(colaboradorTurmasAprovados));
		avaliacaoCursoManager.expects(atLeastOnce()).method("countAvaliacoes").with(eq(turma.getId()), eq("T")).will(returnValue(qrdAvaliacoes));
		aproveitamentoAvaliacaoCursoManager.expects(atLeastOnce()).method("findColaboradorTurma").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(colaboradorTurmasAprovados));

		MockSpringUtil.mocks.put("colaboradorPresencaManager", colaboradorPresencaManager);
		MockSpringUtil.mocks.put("turmaManager", turmaManager);
		
		colaboradorTurmaDao.expects(once()).method("findAprovadosReprovados").withAnyArguments().will(returnValue(colaboradorTurmasAprovados));
		colaboradorManager.expects(once()).method("findAllSelect").with(ANYTHING, eq(false)).will(returnValue(colaboradors));
		

		Collection<ColaboradorTurma> colaboradorTurmasResposta = new ArrayList<ColaboradorTurma>();		
		colaboradorTurmasResposta = colaboradorTurmaManager.findHistoricoTreinamentosByColaborador(1L, null, null, 1L);
		
		assertEquals(1, colaboradorTurmasResposta.size());
	}
	
	public void testMontaExibicaoAprovadosReprovados() throws Exception
	{
		Collection<ColaboradorTurma> colaboradorTurmas = montaAprovadosReprovados(); 
		
		long id = 1;
		for (ColaboradorTurma colaboradorTurma : colaboradorTurmas) 
			colaboradorTurma.setColaborador(ColaboradorFactory.getEntity(id++));
		
		colaboradorTurmaDao.expects(once()).method("findAprovadosReprovados").withAnyArguments().will(returnValue(colaboradorTurmas));

		Collection<Colaborador> colaboradores = colaboradorTurmaManager.montaExibicaoAprovadosReprovados(null, null);

		assertEquals(7, colaboradores.size());
		
		String nomeColaboradorAprovado = "nome colaborador";
		assertEquals(nomeColaboradorAprovado, buscaNomeColaborador(1L, colaboradores));
		assertEquals(nomeColaboradorAprovado, buscaNomeColaborador(2L, colaboradores));
		assertEquals(nomeColaboradorAprovado, buscaNomeColaborador(3L, colaboradores));
		assertEquals(nomeColaboradorAprovado, buscaNomeColaborador(5L, colaboradores));
		
		String span = "<span style='color: red;'>nome colaborador "; 
		String _span = "</span>";
		
		assertEquals(span + "(reprovado por falta)" + _span, buscaNomeColaborador(4L, colaboradores));
		assertEquals(span + "(reprovado por Nota)" + _span, buscaNomeColaborador(6L, colaboradores));
		assertEquals(span + "(reprovado por nota e falta)" + _span, buscaNomeColaborador(7L, colaboradores));
	}

	private String buscaNomeColaborador(Long id, Collection<Colaborador> colaboradores) 
	{
		for (Colaborador colaborador : colaboradores) 
		{
			if(colaborador.getId().equals(id))
				return colaborador.getNome();
		}
		
		return "";
	}

	public void testFindRelatorioHistoricoTreinamentosSemAvaliacoes() throws Exception
	{
		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador1);
		colaboradors.add(colaborador2);
		
		Curso curso = CursoFactory.getEntity(1L);
		curso.setPercentualMinimoFrequencia(0.0);
		
		Turma turma = TurmaFactory.getEntity(1L);
		turma.setCurso(curso);
		
		ColaboradorTurma colaboradorTurma1 = ColaboradorTurmaFactory.getEntity(1L);
		colaboradorTurma1.setTurma(turma);
		colaboradorTurma1.setColaborador(colaborador1);
		colaboradorTurma1.setColaborador(colaborador2);
		colaboradorTurma1.setValorAvaliacao(85.0);
		colaboradorTurma1.setAprovado(true);
		
		ColaboradorPresenca colaboradorPresenca = ColaboradorPresencaFactory.getEntity(1L);
		colaboradorPresenca.setColaboradorTurma(colaboradorTurma1);
		
		Collection<ColaboradorPresenca> colaboradorPresencas = new ArrayList<ColaboradorPresenca>();
		colaboradorPresencas.add(new ColaboradorPresenca(colaborador1.getId(), turma.getId(), 20));
		colaboradorPresencas.add(new ColaboradorPresenca(2L, turma.getId(), 10));
		
		Collection<Turma> turmas = new ArrayList<Turma>();
		turmas.add(new Turma(turma.getId(), 11.1));
		
		Collection<Long> colaboradorIds = new ArrayList<Long>();
		colaboradorIds.add(colaborador1.getId());
		
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		colaboradores.add(colaborador1);
		
		DiaTurma diaTurma1 = DiaTurmaFactory.getEntity(1L);
		diaTurma1.setTurma(turma);
		
		DiaTurma diaTurma2 = DiaTurmaFactory.getEntity(2L);
		diaTurma2.setTurma(turma);
		
		Collection<ColaboradorTurma> colaboradorTurmasAprovados = new ArrayList<ColaboradorTurma>();
		colaboradorTurmasAprovados.add(colaboradorTurma1);
		
		int qrdAvaliacoes = 0;
		
		colaboradorTurmaDao.expects(once()).method("findHistoricoTreinamentosByColaborador").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(colaboradorTurmasAprovados));
		avaliacaoCursoManager.expects(atLeastOnce()).method("countAvaliacoes").with(eq(turma.getId()), eq("T")).will(returnValue(qrdAvaliacoes));
		//aproveitamentoAvaliacaoCursoManager.expects(atLeastOnce()).method("findColaboradorTurma").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(colaboradorTurmasAprovados));
		
		MockSpringUtil.mocks.put("colaboradorPresencaManager", colaboradorPresencaManager);
		MockSpringUtil.mocks.put("turmaManager", turmaManager);
		
		colaboradorTurmaDao.expects(once()).method("findAprovadosReprovados").withAnyArguments().will(returnValue(colaboradorTurmasAprovados));
		colaboradorManager.expects(once()).method("findAllSelect").with(ANYTHING, eq(false)).will(returnValue(colaboradors));
		
		Collection<ColaboradorTurma> colaboradorTurmasResposta = new ArrayList<ColaboradorTurma>();		
		colaboradorTurmasResposta = colaboradorTurmaManager.findHistoricoTreinamentosByColaborador(1L, null, null, 1L);
		
		assertEquals(1, colaboradorTurmasResposta.size());
	}

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

		areaOrganizacionalManager.expects(once()).method("findAllListAndInativas").with(eq(empresa.getId()), ANYTHING, ANYTHING).will(returnValue(areas));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").with(eq(areas)).will(returnValue(areas));
		areaOrganizacionalManager.expects(once()).method("getAreaOrganizacional").with(ANYTHING, ANYTHING).will(returnValue(new AreaOrganizacional()));

		assertEquals(1, colaboradorTurmaManager.setFamiliaAreas(colaboradorTurmas, empresa.getId()).size());
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
		
		colaboradorTurmaDao.expects(once()).method("findByTurmaCurso").with(eq(turma.getCurso().getId())).will(returnValue(new ArrayList<ColaboradorTurma>()));
		colaboradorTurmaDao.expects(once()).method("findByColaboradorAndTurma").with(eq(turma.getId()), eq(colaborador.getId())).will(returnValue(null));
		colaboradorTurmaDao.expects(once()).method("save").with(ANYTHING).isVoid();
		aproveitamentoAvaliacaoCursoManager.expects(once()).method("saveNotas").with(ANYTHING, eq(notas), eq(avaliacaoCursoIds)).isVoid();
		colaboradorTurmaManager.saveColaboradorTurmaNota(turma, colaborador, avaliacaoCursoIds, notas);
	}

	public void testSaveColaboradorOutraTurmaNotaMesmoCurso() throws Exception
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		Turma turma = TurmaFactory.getEntity(1L);
		Turma turma2 = TurmaFactory.getEntity(2L);
		
		turma.setCurso(CursoFactory.getEntity(32L));
		turma2.setCurso(CursoFactory.getEntity(32L));
		
		AvaliacaoCurso avaliacaoCurso1 = AvaliacaoCursoFactory.getEntity(1L);
		AvaliacaoCurso avaliacaoCurso2 = AvaliacaoCursoFactory.getEntity(2L);
		AvaliacaoCurso avaliacaoCurso3 = AvaliacaoCursoFactory.getEntity(3L);
		
		Long[] avaliacaoCursoIds = new Long[]{avaliacaoCurso1.getId(), avaliacaoCurso2.getId(), avaliacaoCurso3.getId()};
		
		String[] notas = new String[]{"","3.1","5,5"};
		
		ColaboradorTurma colaboradorTurma = new ColaboradorTurma(1L);
		colaboradorTurma.setColaborador(colaborador);
		colaboradorTurma.setTurma(turma2);
		
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmas.add(colaboradorTurma);
		
		colaboradorTurmaDao.expects(once()).method("findByTurmaCurso").with(eq(turma.getCurso().getId())).will(returnValue(colaboradorTurmas));
		
		FortesException fortesException = null;
		
		try
		{
			colaboradorTurmaManager.saveColaboradorTurmaNota(turma, colaborador, avaliacaoCursoIds, notas);
		}
		catch (FortesException e) 
		{
			fortesException = e;
		}
		
		assertEquals("Colaborador já inscrito em outra turma deste curso", fortesException.getMessage());
	}

	public void testFindAprovadosByCertificacao()
	{
		Curso cursoA = CursoFactory.getEntity();
		cursoA.setPercentualMinimoFrequencia(100.0);

		Curso cursoB = CursoFactory.getEntity();
		cursoB.setPercentualMinimoFrequencia(100.0);
		
		Turma turma = TurmaFactory.getEntity(1L);
		
		Colaborador francisco = ColaboradorFactory.getEntity(1L);
		francisco.setNome("Francisco");
		
		Colaborador maria = ColaboradorFactory.getEntity(2L);
		maria.setNome("Maria");

		Colaborador pedro = ColaboradorFactory.getEntity(3L);
		pedro.setNome("Pedro");

		Colaborador jose = ColaboradorFactory.getEntity(4L);
		jose.setNome("Jose");
		
		Colaborador joao = ColaboradorFactory.getEntity(5L);
		joao.setNome("Joao");
		
		Collection<ColaboradorTurma> colabTurmas =  new ArrayList<ColaboradorTurma>();

		ColaboradorTurma pedroAprovadoCursoA = ColaboradorTurmaFactory.getEntity();
		pedroAprovadoCursoA.setColaborador(pedro);
		pedroAprovadoCursoA.setQtdAvaliacoesAprovadasPorNota(1);
		pedroAprovadoCursoA.setQtdAvaliacoesCurso(1);
		pedroAprovadoCursoA.setTotalDias(2);
		pedroAprovadoCursoA.setQtdPresenca(2);
		pedroAprovadoCursoA.setTurma(turma);
		pedroAprovadoCursoA.setCurso(cursoA);
		colabTurmas.add(pedroAprovadoCursoA);
		
		ColaboradorTurma pedroAprovadoCursoB = ColaboradorTurmaFactory.getEntity();
		pedroAprovadoCursoB.setColaborador(pedro);
		pedroAprovadoCursoB.setQtdAvaliacoesAprovadasPorNota(1);
		pedroAprovadoCursoB.setQtdAvaliacoesCurso(1);
		pedroAprovadoCursoB.setTotalDias(2);
		pedroAprovadoCursoB.setQtdPresenca(2);
		pedroAprovadoCursoB.setTurma(turma);
		pedroAprovadoCursoB.setCurso(cursoB);
		colabTurmas.add(pedroAprovadoCursoB);
		
		ColaboradorTurma franciscoAprovadoSoNOCursoA = ColaboradorTurmaFactory.getEntity();
		franciscoAprovadoSoNOCursoA.setColaborador(francisco);
		franciscoAprovadoSoNOCursoA.setQtdAvaliacoesAprovadasPorNota(1);
		franciscoAprovadoSoNOCursoA.setQtdAvaliacoesCurso(1);
		franciscoAprovadoSoNOCursoA.setTotalDias(2);
		franciscoAprovadoSoNOCursoA.setQtdPresenca(2);
		franciscoAprovadoSoNOCursoA.setTurma(turma);
		franciscoAprovadoSoNOCursoA.setCurso(cursoA);
		colabTurmas.add(franciscoAprovadoSoNOCursoA);

		ColaboradorTurma joaoReprovadoNotaSoNoCursoA = ColaboradorTurmaFactory.getEntity();
		joaoReprovadoNotaSoNoCursoA.setColaborador(joao);
		joaoReprovadoNotaSoNoCursoA.setQtdAvaliacoesAprovadasPorNota(0);
		joaoReprovadoNotaSoNoCursoA.setQtdAvaliacoesCurso(1);
		joaoReprovadoNotaSoNoCursoA.setTotalDias(2);
		joaoReprovadoNotaSoNoCursoA.setQtdPresenca(2);
		joaoReprovadoNotaSoNoCursoA.setTurma(turma);
		joaoReprovadoNotaSoNoCursoA.setCurso(cursoA);
		colabTurmas.add(joaoReprovadoNotaSoNoCursoA);

		ColaboradorTurma mariaReprovadoFaltaSoNoCursoA = ColaboradorTurmaFactory.getEntity();
		mariaReprovadoFaltaSoNoCursoA.setColaborador(maria);
		mariaReprovadoFaltaSoNoCursoA.setQtdAvaliacoesAprovadasPorNota(1);
		mariaReprovadoFaltaSoNoCursoA.setQtdAvaliacoesCurso(1);
		mariaReprovadoFaltaSoNoCursoA.setTotalDias(2);
		mariaReprovadoFaltaSoNoCursoA.setQtdPresenca(1);
		mariaReprovadoFaltaSoNoCursoA.setTurma(turma);
		mariaReprovadoFaltaSoNoCursoA.setCurso(cursoA);
		colabTurmas.add(mariaReprovadoFaltaSoNoCursoA);
		
		ColaboradorTurma joseAprovadoCursoA = ColaboradorTurmaFactory.getEntity();
		joseAprovadoCursoA.setColaborador(jose);
		joseAprovadoCursoA.setQtdAvaliacoesAprovadasPorNota(1);
		joseAprovadoCursoA.setQtdAvaliacoesCurso(1);
		joseAprovadoCursoA.setTotalDias(2);
		joseAprovadoCursoA.setQtdPresenca(2);
		joseAprovadoCursoA.setTurma(turma);
		joseAprovadoCursoA.setCurso(cursoA);
		colabTurmas.add(joseAprovadoCursoA);

		ColaboradorTurma joseReprovadoCursoB = ColaboradorTurmaFactory.getEntity();
		joseReprovadoCursoB.setColaborador(jose);
		joseReprovadoCursoB.setQtdAvaliacoesAprovadasPorNota(0);
		joseReprovadoCursoB.setQtdAvaliacoesCurso(1);
		joseReprovadoCursoB.setTotalDias(2);
		joseReprovadoCursoB.setQtdPresenca(2);
		joseReprovadoCursoB.setTurma(turma);
		joseReprovadoCursoB.setCurso(cursoB);
		colabTurmas.add(joseReprovadoCursoB);
		
		colaboradorTurmaDao.expects(once()).method("findAprovadosReprovados").withAnyArguments().will(returnValue(colabTurmas));

		
		Collection<Colaborador> colaboradores = colaboradorTurmaManager.findAprovadosByCertificacao(null, 2);
		assertEquals(5, colaboradores.size());
		
		Colaborador[] colabs = colaboradores.toArray(new Colaborador[]{});

		assertEquals("Pedro", colabs[0].getNome());
		assertEquals("<span style='color: red;'>Francisco (Não certificado)</span>", colabs[1].getNome());
		assertEquals("<span style='color: red;'>Joao (Não certificado)</span>", colabs[2].getNome());
		assertEquals("<span style='color: red;'>Jose (Não certificado)</span>", colabs[3].getNome());
		assertEquals("<span style='color: red;'>Maria (Não certificado)</span>", colabs[4].getNome());
	}

	public void testMontaRelatorioColaboradorCertificacaoColecaoVazia() throws Exception
	{
		Certificacao certificacao = CertificacaoFactory.getEntity(5L);

		certificacaoManager.expects(atLeastOnce()).method("findById").with(eq(5L)).will(returnValue(certificacao));
		cursoManager.expects(atLeastOnce()).method("findByCertificacao").will(returnValue(new ArrayList<Curso>()));
		colaboradorTurmaDao.expects(once()).method("findAprovadosReprovados").will(returnValue(new ArrayList<ColaboradorTurma>()));
		colaboradorTurmaDao.expects(once()).method("findAprovadosReprovados").will(returnValue(null));
		
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
		Collection<ColaboradorTurma> colaboradorTurmas = montaAprovadosReprovados();
		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
		
		colaboradorTurmaDao.expects(atLeastOnce()).method("findAprovadosReprovados").withAnyArguments().will(returnValue(colaboradorTurmas));
		areaOrganizacionalManager.expects(atLeastOnce()).method("findAllListAndInativas").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(areas));
		areaOrganizacionalManager.expects(atLeastOnce()).method("montaFamilia").with(ANYTHING).will(returnValue(areas));
		
		assertEquals(7, colaboradorTurmaManager.findRelatorioComTreinamento(null, CursoFactory.getEntity(1L), null, null, 'T').size());		
		assertEquals(4, colaboradorTurmaManager.findRelatorioComTreinamento(null, CursoFactory.getEntity(1L), null, null, 'S').size());		
		assertEquals(3, colaboradorTurmaManager.findRelatorioComTreinamento(null, CursoFactory.getEntity(1L), null, null, 'N').size());		
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
		areaOrganizacionalManager.expects(once()).method("findAllListAndInativas").with(eq(empresaId), ANYTHING, ANYTHING).will(returnValue(areas));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").with(eq(areas)).will(returnValue(areas));
		areaOrganizacionalManager.expects(once()).method("getAreaOrganizacional").with(ANYTHING, ANYTHING).will(returnValue(new AreaOrganizacional()));
		cursoManager.expects(atLeastOnce()).method("findByIdProjection").will(returnValue(curso));
	}
	
	public void testFindByTurmaSemPresenca() {
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmaDao.expects(once()).method("findByTurmaSemPresenca").will(returnValue(colaboradorTurmas));
		colaboradorTurmaManager.findByTurmaSemPresenca(1L, 10L);
	}

	public void testFindAprovadosByTurmas() 
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		Turma turma1 = TurmaFactory.getEntity(1L);
				
		Collection<Long> turmaIds = new ArrayList<Long>();
		turmaIds.add(turma1.getId());
		
		Collection<ColaboradorTurma> colabTurmas = new ArrayList<ColaboradorTurma>();
		
		ColaboradorTurma colaboradorTurma1 = new ColaboradorTurma();
		colaboradorTurma1.setColaboradorId(colaborador.getId());
		
		ColaboradorTurma colaboradorTurma2 = new ColaboradorTurma();
		colaboradorTurma2.setColaboradorId(colaborador.getId());
		
		colabTurmas.add(colaboradorTurma1);
		colabTurmas.add(colaboradorTurma2);
		
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		colaboradores.add(colaborador);
		
		colaboradorTurmaDao.expects(once()).method("findAprovadosReprovados").withAnyArguments().will(returnValue(colabTurmas));
		colaboradorManager.expects(once()).method("findAllSelect").with(ANYTHING, eq(false)).will(returnValue(colaboradores));
		
		Collection<Colaborador> retorno = colaboradorTurmaManager.findAprovadosByTurma(turmaIds);
		assertEquals(1, retorno.size());
		assertEquals(colaborador.getId(), ((Colaborador)retorno.toArray()[0]).getId());
	}
	
	public void testFindAprovadosByTurma() 
	{
		Curso curso = CursoFactory.getEntity();
		curso.setPercentualMinimoFrequencia(100.0);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		Turma turma = TurmaFactory.getEntity(1L);
		
		ColaboradorTurma aprovado = ColaboradorTurmaFactory.getEntity();
		aprovado.setColaborador(colaborador);
		aprovado.setQtdAvaliacoesAprovadasPorNota(1);
		aprovado.setQtdAvaliacoesCurso(1);
		aprovado.setTotalDias(2);
		aprovado.setQtdPresenca(2);
		aprovado.setTurma(turma);
		aprovado.setCurso(curso);

		ColaboradorTurma reprovadoNota = ColaboradorTurmaFactory.getEntity();
		reprovadoNota.setColaborador(colaborador);
		reprovadoNota.setQtdAvaliacoesAprovadasPorNota(0);
		reprovadoNota.setQtdAvaliacoesCurso(1);
		reprovadoNota.setTotalDias(2);
		reprovadoNota.setQtdPresenca(2);
		reprovadoNota.setTurma(turma);
		reprovadoNota.setCurso(curso);

		ColaboradorTurma reprovadoFalta = ColaboradorTurmaFactory.getEntity();
		reprovadoFalta.setColaborador(colaborador);
		reprovadoFalta.setQtdAvaliacoesAprovadasPorNota(1);
		reprovadoFalta.setQtdAvaliacoesCurso(1);
		reprovadoFalta.setTotalDias(2);
		reprovadoFalta.setQtdPresenca(1);
		reprovadoFalta.setTurma(turma);
		reprovadoFalta.setCurso(curso);
		
		Collection<ColaboradorTurma> colabTurmas =  new ArrayList<ColaboradorTurma>();
		colabTurmas.add(aprovado);
		colabTurmas.add(reprovadoFalta);
		colabTurmas.add(reprovadoNota);
		
		colaboradorTurmaDao.expects(once()).method("findAprovadosReprovados").withAnyArguments().will(returnValue(colabTurmas));
		
		assertEquals(1, colaboradorTurmaManager.findAprovadosByTurma(turma.getId()).size() );
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
	
	public void testMontaCertificados() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		
		Colaborador marlus = ColaboradorFactory.getEntity(1L);
		marlus.setNome("Marlus");
		
		Certificado certificadoA = new Certificado();
		certificadoA.setConteudo("mamae quer #NOMECOLABORADOR#");
		certificadoA.setNomeColaborador(marlus.getNome());
		
		Certificado certificadoB = new Certificado();
		
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		colaboradores.add(marlus);
		
		empresaManager.expects(once()).method("findById").with(eq(empresa.getId())).will(returnValue(empresa));
		
		Collection<Certificado>  certificadosResults = colaboradorTurmaManager.montaCertificados(colaboradores, certificadoA, null);
		
		Certificado certificadoClonado = (Certificado) certificadosResults.toArray()[0];
		
		assertEquals("mamae quer Marlus", certificadoClonado.getConteudo());
	}

}