package com.fortes.rh.test.web.action.desenvolvimento;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorPresencaManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.desenvolvimento.DiaTurmaManager;
import com.fortes.rh.business.desenvolvimento.TurmaAvaliacaoTurmaManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.DocumentoAnexoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.TipoDespesaManager;
import com.fortes.rh.business.geral.TurmaTipoDespesaManager;
import com.fortes.rh.business.pesquisa.AvaliacaoTurmaManager;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.FiltroControleVencimentoCertificacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.DocumentoAnexo;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.TipoDespesa;
import com.fortes.rh.model.geral.TurmaTipoDespesa;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.test.dao.hibernate.pesquisa.AvaliacaoTurmaFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.web.action.desenvolvimento.TurmaEditAction;
import com.fortes.web.tags.CheckBox;

public class TurmaEditActionTest_Junit4
{
	private TurmaEditAction action;
	private TurmaManager turmaManager;
	private CursoManager cursoManager;
	private EmpresaManager empresaManager;
	private ColaboradorManager colaboradorManager;
	private CertificacaoManager certificacaoManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private AvaliacaoTurmaManager avaliacaoTurmaManager;
	private TurmaAvaliacaoTurmaManager turmaAvaliacaoTurmaManager;
	private DiaTurmaManager diaTurmaManager;
	private ColaboradorPresencaManager colaboradorPresencaManager;
	private TurmaTipoDespesaManager turmaTipoDespesaManager;
	private DocumentoAnexoManager documentoAnexoManager;
	private TipoDespesaManager tipoDespesaManager;
	private ColaboradorCertificacaoManager colaboradorCertificacaoManager;
	
	@Before
    public void setUp() throws Exception
    {
        action = new TurmaEditAction();

        turmaManager = mock(TurmaManager.class);
        action.setTurmaManager(turmaManager);

        colaboradorTurmaManager = mock(ColaboradorTurmaManager.class);
        action.setColaboradorTurmaManager(colaboradorTurmaManager);

        cursoManager = mock(CursoManager.class);
        action.setCursoManager(cursoManager);

        certificacaoManager = mock(CertificacaoManager.class);
        action.setCertificacaoManager(certificacaoManager);
        
        empresaManager = mock(EmpresaManager.class);
        action.setEmpresaManager(empresaManager);
        
        colaboradorManager = mock(ColaboradorManager.class);
        action.setColaboradorManager(colaboradorManager);
        
        avaliacaoTurmaManager = mock(AvaliacaoTurmaManager.class);
        action.setAvaliacaoTurmaManager(avaliacaoTurmaManager);
        
        turmaAvaliacaoTurmaManager = mock(TurmaAvaliacaoTurmaManager.class);
        action.setTurmaAvaliacaoTurmaManager(turmaAvaliacaoTurmaManager);
        
        diaTurmaManager = mock(DiaTurmaManager.class);
        action.setDiaTurmaManager(diaTurmaManager);
        
        colaboradorPresencaManager = mock(ColaboradorPresencaManager.class);
        action.setColaboradorPresencaManager(colaboradorPresencaManager);
        
        turmaTipoDespesaManager = mock(TurmaTipoDespesaManager.class);
        action.setTurmaTipoDespesaManager(turmaTipoDespesaManager);
        
        documentoAnexoManager = mock(DocumentoAnexoManager.class);
        action.setDocumentoAnexoManager(documentoAnexoManager);
        
        tipoDespesaManager = mock(TipoDespesaManager.class);
        action.setTipoDespesaManager(tipoDespesaManager);
        
        colaboradorCertificacaoManager = mock(ColaboradorCertificacaoManager.class);
        action.setColaboradorCertificacaoManager(colaboradorCertificacaoManager);
        
        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    }

	@Test
	public void testGetColaboradoresByFiltro() throws Exception
	{
		Curso curso = CursoFactory.getEntity(1L);
		Turma turma = TurmaFactory.getEntity(10L);
		turma.setCurso(curso);
		action.setCurso(curso);
		action.setTurma(turma);
		action.setCertificadoDe('C');

		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		action.setCertificacao(certificacao);

		when(cursoManager.findByCertificacao(eq(1L))).thenReturn(new ArrayList<Curso>());
		when(colaboradorTurmaManager.findAprovadosByCertificacao(any(Certificacao.class), eq(1), eq(false))).thenReturn(new ArrayList<Colaborador>());
		when(certificacaoManager.findByIdProjection(eq(1L))).thenReturn(certificacao);
		when(empresaManager.findCidade(eq(1L))).thenReturn("Fortaleza");
		when(cursoManager.findAllByEmpresasParticipantes(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Curso>());
		when(certificacaoManager.findAllSelect(eq(1L))).thenReturn(new ArrayList<Certificacao>());
		when(turmaManager.findAllSelect(eq(1L))).thenReturn(new ArrayList<Turma>());

		assertEquals("success", action.getColaboradoresByFiltro());
	}

	@Test    
	public void testGetColaboradoresByFiltroPorTurma() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Curso curso = CursoFactory.getEntity(1L);
		Turma turma = TurmaFactory.getEntity(10L);
		turma.setCurso(curso);
		turma.setEmpresa(empresa);
		action.setCurso(curso);
		action.setTurma(turma);
		action.setCertificadoDe('T');

		Collection<Long> turmaIds = new ArrayList<Long>();
		turmaIds.add(turma.getId());

		Colaborador colaborador = ColaboradorFactory.getEntity(33L);
		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador);

		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(33L);
		colaboradorTurma.setColaborador(colaborador);
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		colaboradorTurmas.add(colaboradorTurma);

		when(colaboradorTurmaManager.montaExibicaoAprovadosReprovados(eq(turma.getId()))).thenReturn(colaboradors);
		when(turmaManager.findByIdProjection(turma.getId())).thenReturn(turma);
		when(empresaManager.findCidade(eq(1L))).thenReturn("Fortaleza");

		when(cursoManager.findAllByEmpresasParticipantes(1L)).thenReturn(new ArrayList<Curso>());
		when(certificacaoManager.findAllSelect(eq(1L))).thenReturn(new ArrayList<Certificacao>());

		when(turmaManager.findAllSelect(eq(1L))).thenReturn(new ArrayList<Turma>());
		assertEquals("success", action.getColaboradoresByFiltro());
	}
	
	private void montaAvaliacaoTurmasCheck(Long empresaId) {
		Questionario questionario = QuestionarioFactory.getEntity(1L, "Questionario1");
		Questionario questionarioInativo = QuestionarioFactory.getEntity(2L, "Questionario2");
		
		AvaliacaoTurma avaliacaoTurma = AvaliacaoTurmaFactory.getEntity(1L);
		avaliacaoTurma.setQuestionario(questionario);
		
		AvaliacaoTurma avaliacaoTurmaMarcadoInativo = AvaliacaoTurmaFactory.getEntity(2L);
		avaliacaoTurmaMarcadoInativo.setAtiva(false);
		avaliacaoTurmaMarcadoInativo.setQuestionario(questionarioInativo);
		
		Collection<AvaliacaoTurma> avaliacaoTurmas = Arrays.asList(avaliacaoTurma, avaliacaoTurmaMarcadoInativo);
		
		Collection<AvaliacaoTurma> avaliacaoTurmasMarcados = Arrays.asList(avaliacaoTurma);
		
		when(avaliacaoTurmaManager.findAllSelect(null, empresaId)).thenReturn(avaliacaoTurmas);
		when(avaliacaoTurmaManager.findByTurma(1L)).thenReturn(avaliacaoTurmasMarcados);
	}
	
	private void assertMontaAvaliacaoTurmasCheck() {
		Collection<CheckBox> avaliacaoTurmasCheckList = action.getAvaliacaoTurmasCheckList();
    	assertEquals(1, avaliacaoTurmasCheckList.size());
    	assertEquals("Questionario1", ((CheckBox)avaliacaoTurmasCheckList.toArray()[0]).getNome());
	}
	
	@Test
    public void testPrepareUpdate() throws Exception{
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Curso curso = CursoFactory.getEntity(1L);
    	action.setCurso(curso);
    	Turma turma = TurmaFactory.getEntity(1L);
    	turma.setEmpresa(empresa);
    	turma.setDataPrevIni(new Date());
    	turma.setCurso(curso);
    	action.setTurma(turma);

    	Collection<DiaTurma> diaTurmas = new ArrayList<DiaTurma>();
    	Collection<Curso> cursos = new ArrayList<Curso>();
    	Collection<DocumentoAnexo> documentoAnexos = new ArrayList<DocumentoAnexo>();
    	montaAvaliacaoTurmasCheck(turma.getEmpresa().getId());

    	when(turmaManager.findByIdProjection(turma.getId())).thenReturn(turma);
    	when(turmaManager.verificaAvaliacaoDeTurmaRespondida(turma.getId())).thenReturn(false);
    	when(turmaAvaliacaoTurmaManager.verificaAvaliacaoliberada(turma.getId())).thenReturn(false);
    	when(diaTurmaManager.find(new String[] { "turma.id" }, new Object[] { turma.getId() })).thenReturn(diaTurmas);
    	when(colaboradorPresencaManager.existPresencaByTurma(turma.getId())).thenReturn(true);
    	when(tipoDespesaManager.find(new String[]{"empresa.id"}, new Object[]{1L}, new String[]{"descricao"})).thenReturn(new ArrayList<TipoDespesa>());
    	when(cursoManager.findAllByEmpresasParticipantes(1L)).thenReturn(cursos);
    	when(cursoManager.existeEmpresasNoCurso(empresa.getId(), curso.getId())).thenReturn(true);
    	when(turmaTipoDespesaManager.findTipoDespesaTurma(turma.getId())).thenReturn(new ArrayList<TurmaTipoDespesa>());
    	when(documentoAnexoManager.getDocumentoAnexoByOrigemId('U', 1L)).thenReturn(documentoAnexos);
    	when(documentoAnexoManager.findByTurma(turma.getId())).thenReturn(documentoAnexos);

    	assertEquals("success", action.prepareUpdate());
    	assertMontaAvaliacaoTurmasCheck();
    }
	
	@Test
    public void testPrepareUpdateSomenteLeitura() throws Exception{
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setControlarVencimentoCertificacaoPor(FiltroControleVencimentoCertificacao.CERTIFICACAO.getOpcao());
    	action.setEmpresaSistema(empresa);
    	
    	Curso curso = CursoFactory.getEntity(1L);
    	action.setCurso(curso);
    	
    	Turma turma = TurmaFactory.getEntity(1L);
    	turma.setEmpresa(empresa);
    	turma.setDataPrevIni(new Date());
    	turma.setCurso(curso);
    	turma.setRealizada(true);
    	action.setTurma(turma);
    	
    	Collection<DiaTurma> diaTurmas = new ArrayList<DiaTurma>();
    	Collection<Curso> cursos = new ArrayList<Curso>();
    	Collection<DocumentoAnexo> documentoAnexos = new ArrayList<DocumentoAnexo>();

    	montaAvaliacaoTurmasCheck(turma.getEmpresa().getId());
    	
    	when(turmaManager.findByIdProjection(turma.getId())).thenReturn(turma);
    	when(turmaManager.verificaAvaliacaoDeTurmaRespondida(turma.getId())).thenReturn(false);
    	when(turmaAvaliacaoTurmaManager.verificaAvaliacaoliberada(turma.getId())).thenReturn(false);
    	when(diaTurmaManager.find(new String[] { "turma.id" }, new Object[] { turma.getId() })).thenReturn(diaTurmas);
    	when(colaboradorPresencaManager.existPresencaByTurma(turma.getId())).thenReturn(true);
    	when(tipoDespesaManager.find(new String[]{"empresa.id"}, new Object[]{1L}, new String[]{"descricao"})).thenReturn(new ArrayList<TipoDespesa>());
    	when(cursoManager.findAllByEmpresasParticipantes(1L)).thenReturn(cursos);
    	when(cursoManager.existeEmpresasNoCurso(empresa.getId(), curso.getId())).thenReturn(true);
    	when(turmaTipoDespesaManager.findTipoDespesaTurma(turma.getId())).thenReturn(new ArrayList<TurmaTipoDespesa>());
    	when(documentoAnexoManager.getDocumentoAnexoByOrigemId('U', 1L)).thenReturn(documentoAnexos);
    	when(documentoAnexoManager.findByTurma(turma.getId())).thenReturn(documentoAnexos);
    	when(colaboradorCertificacaoManager.existeColaboradorCertificadoEmUmaTurmaPosterior(turma.getId(), null)).thenReturn(false);
    	
    	assertEquals("success", action.prepareUpdate());
    	assertMontaAvaliacaoTurmasCheck();
    }
	
	@Test
    public void testPrepareUpdateCursoNaoCompartilhado() throws Exception{
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Curso curso = CursoFactory.getEntity(1L);
    	action.setCurso(curso);
    	
    	Turma turma = TurmaFactory.getEntity(1L);
    	turma.setEmpresa(empresa);
    	turma.setDataPrevIni(new Date());
    	turma.setCurso(curso);
    	action.setTurma(turma);
    	
    	Collection<Curso> cursos = new ArrayList<Curso>();
    	Collection<DocumentoAnexo> documentoAnexos = new ArrayList<DocumentoAnexo>();
    	
    	when(turmaManager.findByIdProjection(turma.getId())).thenReturn(turma);
    	when(cursoManager.findAllByEmpresasParticipantes(1L)).thenReturn(cursos);
    	when(tipoDespesaManager.find(new String[]{"empresa.id"}, new Object[]{1L}, new String[]{"descricao"})).thenReturn(new ArrayList<TipoDespesa>());
    	when(documentoAnexoManager.getDocumentoAnexoByOrigemId('U', 1L)).thenReturn(documentoAnexos);
    	when(cursoManager.existeEmpresasNoCurso(empresa.getId(), curso.getId())).thenReturn(false);

    	assertEquals("error", action.prepareUpdate());
    }
	
	@Test
    public void testPrepareUpdateTurmaNaoPertenceAEmpresaLogada() throws Exception{
    	Empresa empresaLogada = EmpresaFactory.getEmpresa(1L);
    	action.setEmpresaSistema(empresaLogada);
    	
    	Empresa empresaTurma = EmpresaFactory.getEmpresa(2L);

    	Curso curso = CursoFactory.getEntity(1L);
    	curso.setEmpresa(empresaLogada);
    	action.setCurso(curso);
    	
    	Turma turma = TurmaFactory.getEntity(1L);
    	turma.setEmpresa(empresaTurma);
    	turma.setDataPrevIni(new Date());
    	turma.setCurso(curso);
    	action.setTurma(turma);
    	
    	Collection<DiaTurma> diaTurmas = new ArrayList<DiaTurma>();
    	Collection<Curso> cursos = new ArrayList<Curso>();
    	Collection<DocumentoAnexo> documentoAnexos = new ArrayList<DocumentoAnexo>();
    	Collection<TurmaTipoDespesa> tipoDespesas = Arrays.asList(new TurmaTipoDespesa());
    	
    	montaAvaliacaoTurmasCheck(turma.getEmpresa().getId());
    	
    	when(turmaManager.findByIdProjection(turma.getId())).thenReturn(turma);
    	when(turmaManager.verificaAvaliacaoDeTurmaRespondida(turma.getId())).thenReturn(false);
    	when(turmaAvaliacaoTurmaManager.verificaAvaliacaoliberada(turma.getId())).thenReturn(false);
    	when(diaTurmaManager.find(new String[] { "turma.id" }, new Object[] { turma.getId() })).thenReturn(diaTurmas);
    	when(colaboradorPresencaManager.existPresencaByTurma(turma.getId())).thenReturn(true);
    	when(tipoDespesaManager.find(new String[]{"empresa.id"}, new Object[]{1L}, new String[]{"descricao"})).thenReturn(new ArrayList<TipoDespesa>());
    	when(cursoManager.findAllByEmpresasParticipantes(1L)).thenReturn(cursos);
    	when(cursoManager.existeEmpresasNoCurso(empresaLogada.getId(), curso.getId())).thenReturn(true);
    	when(turmaTipoDespesaManager.findTipoDespesaTurma(turma.getId())).thenReturn(tipoDespesas);
    	when(documentoAnexoManager.getDocumentoAnexoByOrigemId('U', 1L)).thenReturn(documentoAnexos);
    	when(documentoAnexoManager.findByTurma(turma.getId())).thenReturn(documentoAnexos);
    	when(colaboradorCertificacaoManager.existeColaboradorCertificadoEmUmaTurmaPosterior(turma.getId(), null)).thenReturn(true);

    	assertEquals("success", action.prepareUpdate());
    	assertMontaAvaliacaoTurmasCheck();
    }
 }