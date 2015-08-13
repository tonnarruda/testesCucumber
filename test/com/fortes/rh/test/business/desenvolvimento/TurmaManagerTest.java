package com.fortes.rh.test.business.desenvolvimento;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.cargosalario.FaturamentoMensalManager;
import com.fortes.rh.business.desenvolvimento.AproveitamentoAvaliacaoCursoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorPresencaManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.desenvolvimento.DiaTurmaManager;
import com.fortes.rh.business.desenvolvimento.TurmaAvaliacaoTurmaManager;
import com.fortes.rh.business.desenvolvimento.TurmaDocumentoAnexoManager;
import com.fortes.rh.business.desenvolvimento.TurmaManagerImpl;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.geral.TurmaTipoDespesaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.relatorio.Cabecalho;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionContext;

public class TurmaManagerTest extends MockObjectTestCase
{
	TurmaManagerImpl turmaManager = new TurmaManagerImpl();
	Mock colaboradorTurmaManager = null;
	Mock turmaDao = null;
	Mock parametrosDoSistemaManager;
	Mock transactionManager;
	Mock diaTurmaManager;
	Mock colaboradorPresencaManager;
	Mock colaboradorQuestionarioManager;
	Mock aproveitamentoAvaliacaoCursoManager;
	Mock cursoManager;
	Mock turmaTipoDespesaManager;
	Mock faturamentoMensalManager;
	Mock turmaAvaliacaoTurmaManager;
	Mock turmaDocumentoAnexoManager;

	protected void setUp() throws Exception
	{
		turmaDao = new Mock(TurmaDao.class);
		turmaManager.setDao((TurmaDao) turmaDao.proxy());
		colaboradorTurmaManager = new Mock(ColaboradorTurmaManager.class);
		turmaManager.setColaboradorTurmaManager((ColaboradorTurmaManager) colaboradorTurmaManager.proxy());
		parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);
		MockSpringUtil.mocks.put("parametrosDoSistemaManager", parametrosDoSistemaManager);

		transactionManager = new Mock(PlatformTransactionManager.class);
		turmaManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());

		diaTurmaManager = new Mock(DiaTurmaManager.class);
		turmaManager.setDiaTurmaManager((DiaTurmaManager) diaTurmaManager.proxy());

		colaboradorPresencaManager = new Mock(ColaboradorPresencaManager.class);
		turmaManager.setColaboradorPresencaManager((ColaboradorPresencaManager) colaboradorPresencaManager.proxy());

		colaboradorQuestionarioManager = new Mock(ColaboradorQuestionarioManager.class);
		turmaManager.setColaboradorQuestionarioManager((ColaboradorQuestionarioManager) colaboradorQuestionarioManager.proxy());

		aproveitamentoAvaliacaoCursoManager = new Mock(AproveitamentoAvaliacaoCursoManager.class);
		turmaManager.setAproveitamentoAvaliacaoCursoManager((AproveitamentoAvaliacaoCursoManager) aproveitamentoAvaliacaoCursoManager.proxy());
		
		cursoManager = new Mock(CursoManager.class);
		turmaManager.setCursoManager((CursoManager) cursoManager.proxy());

		turmaTipoDespesaManager = new Mock(TurmaTipoDespesaManager.class);
		turmaManager.setTurmaTipoDespesaManager((TurmaTipoDespesaManager) turmaTipoDespesaManager.proxy());

		faturamentoMensalManager = new Mock(FaturamentoMensalManager.class);
		turmaManager.setFaturamentoMensalManager((FaturamentoMensalManager) faturamentoMensalManager.proxy());
		
		turmaAvaliacaoTurmaManager = new Mock(TurmaAvaliacaoTurmaManager.class);
		turmaDocumentoAnexoManager = new Mock(TurmaDocumentoAnexoManager.class);
		
		Mockit.redefineMethods(ActionContext.class, MockActionContext.class);
		Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
		
		MockSpringUtil.mocks.put("turmaAvaliacaoTurmaManager", turmaAvaliacaoTurmaManager);
		MockSpringUtil.mocks.put("turmaDocumentoAnexoManager", turmaDocumentoAnexoManager);
	}

    protected void tearDown() throws Exception
    {
    	super.tearDown();

    	Mockit.restoreAllOriginalDefinitions();
    }

	public void testGetTurmaFinalizadas(){

		Collection<Turma> turma = new ArrayList<Turma>();

		turmaDao.expects(once()).method("getTurmaFinalizadas").with(eq(1L)).will(returnValue(turma));
		Collection<Turma> turmasRetorno = turmaManager.getTurmaFinalizadas(1L);

		assertEquals(turma, turmasRetorno);
	}

	@SuppressWarnings("unchecked")
	public void testFiltroRelatorioByAreas()
	{
		LinkedHashMap parametros = new LinkedHashMap();
		parametros.put("colaborador", null);

		Object[] t1 = new Object[5];
		t1[0] = "A1";
		t1[1] = "Curso1";
		t1[2] = DateUtil.criarDataMesAno(01, 02, 2008);
		t1[3] = 400D;
		t1[4] = 2;

		List treinamentos = new LinkedList();
		treinamentos.add(t1);

		turmaDao.expects(once()).method("filtroRelatorioByAreas").with(new Constraint[]{ANYTHING}).will(returnValue(treinamentos));

		List retorno = turmaManager.filtroRelatorioByAreas(parametros);

		assertEquals(treinamentos.size(), retorno.size());
	}

	public void testGetParametrosRelatorio()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setNome("teste");
		empresa.setLogoUrl("logo.jpg");

		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
		parametrosDoSistema.setAppVersao("1.1.1");

		parametrosDoSistemaManager.expects(once()).method("findByIdProjection").with(eq(parametrosDoSistema.getId())).will(returnValue(parametrosDoSistema));

		Map<String, Object> parametros = turmaManager.getParametrosRelatorio(empresa, new HashMap<String, Object>());

		Cabecalho cabecalho = (Cabecalho) parametros.get("CABECALHO");

		assertEquals("teste", cabecalho.getNomeEmpresa());
		assertEquals("", cabecalho.getLogoUrl()); //Arquivo nao existe
		assertEquals("1.1.1", cabecalho.getVersaoSistema());
		assertEquals("xxx" + File.separatorChar, parametros.get("SUBREPORT_DIR"));
	}

	@SuppressWarnings("unchecked")
	public void testFiltroRelatorioByColaborador()
	{
		Colaborador colaborador = new Colaborador();
		colaborador.setId(1L);

		LinkedHashMap parametros = new LinkedHashMap();
		parametros.put("colaborador", colaborador);

		Object[] t1 = new Object[5];
		t1[0] = "A1";
		t1[1] = "Curso1";
		t1[2] = DateUtil.criarDataMesAno(01, 02, 2008);
		t1[3] = 400D;
		t1[4] = 2;

		List treinamentos = new LinkedList();
		treinamentos.add(t1);

		turmaDao.expects(once()).method("filtroRelatorioByColaborador").with(new Constraint[]{ANYTHING}).will(returnValue(treinamentos));

		List retorno = turmaManager.filtroRelatorioByColaborador(parametros);

		assertEquals(treinamentos.size(), retorno.size());

	}

	public void testFindPlanosDeTreinamento()
	{
		Collection<Turma> turmas = new ArrayList<Turma>();
		turmaDao.expects(once()).method("findPlanosDeTreinamento").withAnyArguments().will(returnValue(turmas));
		assertEquals(turmas, turmaManager.findPlanosDeTreinamento(0, 15, null, null, null, 't', null));
	}

	public void testCountPlanosDeTreinamento()
	{
		Integer count = 1;
		turmaDao.expects(once()).method("countPlanosDeTreinamento").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(count));
		assertEquals(count, turmaManager.countPlanosDeTreinamento(null, null, null, 't'));
	}

	public void testUpdateRealizada() throws Exception
	{
		Turma turma = TurmaFactory.getEntity(1L);

		turmaDao.expects(once()).method("updateRealizada").with(eq(turma.getId()), eq(false)).isVoid();
		turmaManager.updateRealizada(turma.getId(), false);
	}

	public void testFindByFiltro() throws Exception
	{
		turmaDao.expects(once()).method("findByFiltro").withAnyArguments().will(returnValue(new ArrayList<Turma>()));
		turmaManager.findByFiltro(null, null, 'T', null, null);
	}

	public void testFindByIdProjectionArray() throws Exception
	{
		turmaDao.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(new ArrayList<Turma>()));
		turmaManager.findByIdProjection(new Long[]{1L});
	}

	public void testRealizadaValue() throws Exception
	{
		assertNull(turmaManager.realizadaValue('T'));
		assertTrue(turmaManager.realizadaValue('S'));
		assertFalse(turmaManager.realizadaValue('N'));
	}

	public void testRemoveCascade()
	{
		Turma turma = TurmaFactory.getEntity(1L);
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmas.add(colaboradorTurma);

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		colaboradorTurmaManager.expects(once()).method("find").with(ANYTHING, ANYTHING).will(returnValue(colaboradorTurmas));
		colaboradorQuestionarioManager.expects(once()).method("removeByColaboradorETurma").with(eq(null), eq(turma.getId())).isVoid();
		aproveitamentoAvaliacaoCursoManager.expects(once()).method("removeByTurma").with(ANYTHING).isVoid();
		turmaTipoDespesaManager.expects(once()).method("removeByTurma").with(ANYTHING).isVoid();
		colaboradorPresencaManager.expects(once()).method("removeByColaboradorTurma").with(ANYTHING).isVoid();
		colaboradorTurmaManager.expects(once()).method("remove").with(ANYTHING).isVoid();

		diaTurmaManager.expects(once()).method("deleteDiasTurma").isVoid();
		turmaAvaliacaoTurmaManager.expects(once()).method("removeByTurma").with(eq(turma.getId())).isVoid();
		turmaDao.expects(once()).method("remove").with(eq(turma.getId())).isVoid();
		transactionManager.expects(once()).method("commit").with(ANYTHING);

		Exception ex = null;
		try
		{
			turmaManager.removeCascade(turma.getId());
		}
		catch (Exception e)
		{
			ex = e;
		}

		assertNull(ex);
	}

	public void testRemoveCascadeException()
	{
		Turma turma = TurmaFactory.getEntity(1L);
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmas.add(colaboradorTurma);

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		//gera a exception
		turmaManager.setColaboradorTurmaManager(null);

		transactionManager.expects(once()).method("rollback").with(ANYTHING);

		Exception ex = null;
		try
		{
			turmaManager.removeCascade(turma.getId());
		}
		catch (Exception e)
		{
			ex = e;
		}

		assertNotNull(ex);
	}

	public void testInserir()
	{
		Turma turma = TurmaFactory.getEntity(1L);
		String[] diasCheck = new String[]{"1"};
		String despesa = "200,00";
		Long[] avaliacaoTurmaIds = {1L};
		Long[] documentoAnexosIds = {1L};

		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmas.add(colaboradorTurma);

		turmaDao.expects(once()).method("save").with(eq(turma)).isVoid();
		diaTurmaManager.expects(once()).method("saveDiasTurma").with(eq(turma), eq(diasCheck), ANYTHING, ANYTHING).isVoid();
		turmaTipoDespesaManager.expects(once()).method("save").with(eq(despesa),eq(turma.getId())).isVoid();
		turmaAvaliacaoTurmaManager.expects(once()).method("salvarAvaliacaoTurmas").with(eq(turma.getId()),eq(avaliacaoTurmaIds)).isVoid();
		turmaDocumentoAnexoManager.expects(once()).method("salvarDocumentoAnexos").with(eq(turma.getId()),eq(documentoAnexosIds)).isVoid();
		
		Exception ex = null;
		try
		{
			turmaManager.inserir(turma, diasCheck, despesa, avaliacaoTurmaIds, documentoAnexosIds, null, null);
		}
		catch (Exception e)
		{
			ex = e;
		}
		
		assertNull(ex);
		
	}
	
	public void testAtualizar()
	{
		Turma turma = TurmaFactory.getEntity(1L);
		String[] diasCheck = new String[]{"1"};
		String[] selectPrioridades = null;
		Long[] avaliacaoTurmaIds = {1L};
		Long[] documentoAnexosIds = {1L};
		
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
		String[] colaboradorTurmas = {colaboradorTurma.getId().toString()};
		
		colaboradorTurmaManager.expects(once()).method("saveUpdate").with(eq(colaboradorTurmas),eq(selectPrioridades)).isVoid();
		
		turmaDao.expects(once()).method("update").with(eq(turma)).isVoid();
		colaboradorPresencaManager.expects(once()).method("existPresencaByTurma").will(returnValue(false));
		diaTurmaManager.expects(once()).method("saveDiasTurma").with(eq(turma), eq(diasCheck), ANYTHING, ANYTHING).isVoid();
		
		turmaAvaliacaoTurmaManager.expects(once()).method("salvarAvaliacaoTurmas").with(eq(turma.getId()),eq(avaliacaoTurmaIds)).isVoid();
		turmaDocumentoAnexoManager.expects(once()).method("salvarDocumentoAnexos").with(eq(turma.getId()),eq(documentoAnexosIds)).isVoid();
		
		Exception ex = null;
		try
		{
			turmaManager.atualizar(turma, diasCheck, null, null, colaboradorTurmas, selectPrioridades, avaliacaoTurmaIds, documentoAnexosIds, true);
		}
		catch (Exception e)
		{
			ex = e;
		}
		
		assertNull(ex);
		
	}
	
	public void testSalvarTudo()
	{
		Turma turma = TurmaFactory.getEntity(1L);
		String[] diasCheck = new String[]{"1"};

		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmas.add(colaboradorTurma);

		turmaDao.expects(once()).method("save").with(eq(turma)).isVoid();
		diaTurmaManager.expects(once()).method("saveDiasTurma").with(eq(turma), eq(diasCheck), ANYTHING, ANYTHING).isVoid();

		Exception ex = null;
		try
		{
			turmaManager.salvarTurmaDiasCusto(turma, diasCheck, null, null, null);
		}
		catch (Exception e)
		{
			ex = e;
		}

		assertNull(ex);
	}

	public void testSalvarTudoException()
	{
		Turma turma = TurmaFactory.getEntity(1L);
		String[] diasCheck = new String[]{"1"};

		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmas.add(colaboradorTurma);

		turmaDao.expects(once()).method("save").with(eq(turma)).isVoid();
		// gera exception
		turmaManager.setDiaTurmaManager(null);

		Exception ex = null;
		try
		{
			turmaManager.salvarTurmaDiasCusto(turma, diasCheck, null, null, null);
		}
		catch (Exception e)
		{
			ex = e;
		}

		assertNotNull(ex);
	}

	public void testUpdateTurmaDias()
	{
		Turma turma = TurmaFactory.getEntity(1L);
		String[] diasCheck = new String[]{"1"};

		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmas.add(colaboradorTurma);

		turmaDao.expects(once()).method("update").with(eq(turma)).isVoid();
		colaboradorPresencaManager.expects(once()).method("existPresencaByTurma").will(returnValue(false));
		diaTurmaManager.expects(once()).method("saveDiasTurma").with(eq(turma), eq(diasCheck), ANYTHING, ANYTHING).isVoid();
		
		Exception ex = null;
		try
		{
			turmaManager.updateTurmaDias(turma, diasCheck, null, null);
		}
		catch (Exception e)
		{
			ex = e;
		}

		assertNull(ex);
	}

	public void testUpdateTurmaDiasException()
	{
		Turma turma = TurmaFactory.getEntity(1L);
		String[] diasCheck = new String[]{"1"};

		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmas.add(colaboradorTurma);

		turmaDao.expects(once()).method("update").with(eq(turma)).isVoid();
		//gera exception
		turmaManager.setColaboradorPresencaManager(null);
		
		Exception ex = null;
		try
		{
			turmaManager.updateTurmaDias(turma, diasCheck, null, null);
		}
		catch (Exception e)
		{
			ex = e;
		}

		assertNotNull(ex);
	}

	public void testFindByIdProjection()
	{
		Turma turma = TurmaFactory.getEntity(1L);
		turmaDao.expects(once()).method("findByIdProjection").with(eq(turma.getId())).will(returnValue(turma));

		assertEquals(turma, turmaManager.findByIdProjection(turma.getId()));
	}

	public void testFindAllSelect()
	{
		Curso curso = CursoFactory.getEntity(1L);
		Collection<Turma> turmas = new ArrayList<Turma>();
		turmaDao.expects(once()).method("findAllSelect").with(eq(curso.getId())).will(returnValue(turmas));

		assertEquals(turmas, turmaManager.findAllSelect(curso.getId()));
	}
	
	public void testQuantidadeParticipantesPrevistos() 
	{
		Empresa empresa = new Empresa();
		empresa.setId(1021L);
		
		Calendar dataDoisMesesAtras = Calendar.getInstance();
    	dataDoisMesesAtras.add(Calendar.MONTH, -2);
    	Calendar dataTresMesesAtras = Calendar.getInstance();
    	dataTresMesesAtras.add(Calendar.MONTH, -3);
		
		Turma turma = TurmaFactory.getEntity(1L);
		turma.setDataPrevIni(dataTresMesesAtras.getTime());
		turma.setDataPrevFim(dataDoisMesesAtras.getTime());
		turma.setEmpresa(empresa);
		
		Collection<Turma> turmas = new ArrayList<Turma>();
		turmas.add(turma);
		
		turmaDao.expects(once()).method("quantidadeParticipantesPrevistos").with(eq(dataTresMesesAtras.getTime()), eq(dataDoisMesesAtras.getTime()),eq(new Long[]{empresa.getId()}), eq(null)).will(returnValue(new Integer (1)));

		assertEquals(new Integer(1), turmaManager.quantidadeParticipantesPrevistos(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(),new Long[]{empresa.getId()}, null));
	}
	
	public void testClonarTurmaAndCursoEntreEmpresas() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Empresa empresaDestino = EmpresaFactory.getEmpresa(2L);

		Curso java = CursoFactory.getEntity(2L);
		java.setEmpresa(empresa);
		
		Turma turmaA_Java = TurmaFactory.getEntity(3L);
		turmaA_Java.setCurso(java);
		turmaA_Java.setEmpresa(empresa);
		
		Curso delphi = CursoFactory.getEntity(4L);
		delphi.setEmpresa(empresa);

		Turma turmaC_Delphi = TurmaFactory.getEntity(5L);
		turmaC_Delphi.setCurso(delphi);
		turmaC_Delphi.setEmpresa(empresa);
		
		Turma turmaB_Java = TurmaFactory.getEntity(6L);
		turmaB_Java.setCurso(java);
		turmaB_Java.setEmpresa(empresa);
		
		Collection<Turma> turmas = new ArrayList<Turma>();
		turmas.add(turmaA_Java);
		turmas.add(turmaB_Java);
		turmas.add(turmaC_Delphi);
		
		turmaDao.expects(once()).method("findByEmpresaOrderByCurso").with(eq(empresa.getId())).will(returnValue(turmas));

		cursoManager.expects(once()).method("saveClone").with(ANYTHING, eq(empresaDestino.getId()));
		turmaDao.expects(once()).method("save").with(ANYTHING).will(returnValue(new Turma()));
		turmaDao.expects(once()).method("save").with(ANYTHING).will(returnValue(new Turma()));

		cursoManager.expects(once()).method("saveClone").with(ANYTHING, eq(empresaDestino.getId()));
		turmaDao.expects(once()).method("save").with(ANYTHING).will(returnValue(new Turma()));

		diaTurmaManager.expects(once()).method("clonarDiaTurmasDeTurma").with(ANYTHING, ANYTHING);
		diaTurmaManager.expects(once()).method("clonarDiaTurmasDeTurma").with(ANYTHING, ANYTHING);
		diaTurmaManager.expects(once()).method("clonarDiaTurmasDeTurma").with(ANYTHING, ANYTHING);
		
		//clonar Curso sem Turma
		Curso php = CursoFactory.getEntity(77L);
		php.setEmpresa(empresa);

		Curso ruby = CursoFactory.getEntity(44L);
		ruby.setEmpresa(empresa);
		
		Collection<Curso> cursosSemTurma = new ArrayList<Curso>();
		cursosSemTurma.add(php);
		cursosSemTurma.add(delphi);
		
		cursoManager.expects(once()).method("findCursosSemTurma").with(eq(empresa.getId())).will(returnValue(cursosSemTurma));
		cursoManager.expects(once()).method("saveClone").with(ANYTHING, eq(empresaDestino.getId()));
		cursoManager.expects(once()).method("saveClone").with(ANYTHING, eq(empresaDestino.getId()));
		
		turmaManager.sincronizar(empresa.getId(), empresaDestino.getId());
	}
	
	public void testGetPercentualInvestimento() {
		
		Date dataIni = DateUtil.criarDataMesAno(1, 1, 2012);
		Date dataFim = DateUtil.criarDataMesAno(1, 3, 2012);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		faturamentoMensalManager.expects(once()).method("somaByPeriodo").with(eq(dataIni), eq(dataFim), eq(new Long[]{empresa.getId()})).will(returnValue(100.0));
		
		assertEquals(10.0, turmaManager.getPercentualInvestimento(10.0, dataIni, dataFim, new Long[]{empresa.getId()}));
		
		faturamentoMensalManager.expects(once()).method("somaByPeriodo").with(eq(dataIni), eq(dataFim), eq(new Long[]{empresa.getId()})).will(returnValue(0.0));
		
		assertEquals(0.0, turmaManager.getPercentualInvestimento(10.0, dataIni, dataFim, new Long[]{empresa.getId()}));
		
	}
}