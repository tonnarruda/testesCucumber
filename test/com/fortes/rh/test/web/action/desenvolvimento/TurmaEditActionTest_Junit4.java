package com.fortes.rh.test.web.action.desenvolvimento;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.web.action.desenvolvimento.TurmaEditAction;

public class TurmaEditActionTest_Junit4
{
	private TurmaEditAction action;
	private TurmaManager turmaManager;
	private CursoManager cursoManager;
	private EmpresaManager empresaManager;
	private ColaboradorManager colaboradorManager;
	private CertificacaoManager certificacaoManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	
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

		when(colaboradorTurmaManager.montaExibicaoAprovadosReprovados(anyLong(), eq(turma.getId()))).thenReturn(colaboradors);
		when(turmaManager.findByIdProjection(turma.getId())).thenReturn(turma);
		when(empresaManager.findCidade(eq(1L))).thenReturn("Fortaleza");

		when(cursoManager.findAllByEmpresasParticipantes(1L)).thenReturn(new ArrayList<Curso>());
		when(certificacaoManager.findAllSelect(eq(1L))).thenReturn(new ArrayList<Certificacao>());

		when(turmaManager.findAllSelect(eq(1L))).thenReturn(new ArrayList<Turma>());
		assertEquals("success", action.getColaboradoresByFiltro());
	}
 }