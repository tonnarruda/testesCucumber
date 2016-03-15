package com.fortes.rh.test.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.desenvolvimento.AvaliacaoCursoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.desenvolvimento.PrioridadeTreinamentoManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.PrioridadeTreinamento;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.AvaliacaoCursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.PrioridadeTreinamentoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.desenvolvimento.ColaboradorTurmaEditAction;

public class ColaboradorTurmaEditActionTest extends MockObjectTestCase
{
	private ColaboradorTurmaEditAction action;
	private Mock manager;
	private Mock empresaManager;
	private Mock avaliacaoCursoManager;
	private Mock parametrosDoSistemaManager;
	private Mock turmaManager; 
	private Mock cursoManager; 
	private Mock areaOrganizacionalManager;
	private Mock prioridadeTreinamentoManager;
	private Mock colaboradorManager;
	private Mock estabelecimentoManager;
	private Mock cargoManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new ColaboradorTurmaEditAction();
        manager = new Mock(ColaboradorTurmaManager.class);
        empresaManager = new Mock(EmpresaManager.class);
        avaliacaoCursoManager = new Mock(AvaliacaoCursoManager.class);
        parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);
        turmaManager = new Mock(TurmaManager.class);
        cursoManager = new Mock(CursoManager.class);
        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        prioridadeTreinamentoManager = new Mock(PrioridadeTreinamentoManager.class);
        colaboradorManager = new Mock(ColaboradorManager.class);
        estabelecimentoManager = new Mock(EstabelecimentoManager.class);
        cargoManager = new Mock(CargoManager.class);

        action.setColaboradorTurmaManager((ColaboradorTurmaManager) manager.proxy());
        action.setEmpresaManager((EmpresaManager) empresaManager.proxy());
        action.setAvaliacaoCursoManager((AvaliacaoCursoManager) avaliacaoCursoManager.proxy());
        action.setParametrosDoSistemaManager((ParametrosDoSistemaManager) parametrosDoSistemaManager.proxy());
        action.setTurmaManager((TurmaManager) turmaManager.proxy());
        action.setCursoManager((CursoManager) cursoManager.proxy());
        action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
        action.setPrioridadeTreinamentoManager((PrioridadeTreinamentoManager) prioridadeTreinamentoManager.proxy());
        action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
        action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
        action.setCargoManager((CargoManager) cargoManager.proxy());

        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
    }

    protected void tearDown() throws Exception
    {
        manager = null;
        action = null;
        MockSecurityUtil.verifyRole = false;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }

    public void testUpdate() throws Exception
    {
    	action.setColaboradoresTurmaId(new Long[]{1L});
    	action.setColaboradorTurmaHidden(new String[]{"1","2"});

    	manager.expects(atLeastOnce()).method("saveUpdate").withAnyArguments();
    	assertEquals("success", action.update());
    }

    public void testUpdateException() throws Exception
    {
    	action.setColaboradoresTurmaId(new Long[]{1L});
    	action.setColaboradorTurmaHidden(new String[]{"1"});

    	manager.expects(once()).method("saveUpdate").withAnyArguments().will(throwException(new Exception()));
    	
    	
    	
    	manager.expects(once()).method("saveUpdate").withAnyArguments();
    	assertEquals("input", action.update());
    }

    public void testInsertColaboradorNota() throws Exception
    {
    	ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity();
    	parametrosDoSistema.setCompartilharColaboradores(true);
    	
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setId(1L);
    	action.setEmpresaSistema(empresa);
    	action.setEmpresaId(empresa.getId());
    	    	
    	Curso curso = CursoFactory.getEntity();
    	curso.setId(1L);
    	
    	Turma turma = TurmaFactory.getEntity();
    	turma.setCurso(curso);
    	action.setTurma(turma);
    	
    	manager.expects(once()).method("saveColaboradorTurmaNota").with(eq(turma),ANYTHING,ANYTHING,ANYTHING);
    	empresaManager.expects(once()).method("ajustaCombo").with(eq(empresa.getId()),ANYTHING).will(returnValue(empresa.getId()));
    	empresaManager.expects(once()).method("findEmpresasPermitidas").will(returnValue(Arrays.asList(empresa)));
    	parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
    	avaliacaoCursoManager.expects(once()).method("findByCurso").with(eq(turma.getCurso().getId())).will(returnValue(Arrays.asList(AvaliacaoCursoFactory.getEntity())));

    	assertEquals("success", action.insertColaboradorNota());
    }
    
    public void testInsertColaboradorNotaException() throws Exception
    {
    	ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity();
    	parametrosDoSistema.setCompartilharColaboradores(true);
    	
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setId(1L);
    	action.setEmpresaSistema(empresa);
    	action.setEmpresaId(empresa.getId());
    	
    	Curso curso = CursoFactory.getEntity();
    	curso.setId(1L);
    	
    	Turma turma = TurmaFactory.getEntity();
    	turma.setCurso(curso);
    	action.setTurma(turma);
    	
    	manager.expects(once()).method("saveColaboradorTurmaNota").will(throwException(new Exception()));
    	empresaManager.expects(once()).method("ajustaCombo").with(eq(empresa.getId()),ANYTHING).will(returnValue(empresa.getId()));
    	empresaManager.expects(once()).method("findEmpresasPermitidas").will(returnValue(Arrays.asList(empresa)));
    	parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
    	avaliacaoCursoManager.expects(once()).method("findByCurso").with(eq(turma.getCurso().getId())).will(returnValue(Arrays.asList(AvaliacaoCursoFactory.getEntity())));
    	
    	action.insertColaboradorNota();
    	assertEquals("Ocorreu um erro ao inserir o colaborador e as notas.", action.getActionErrors().toArray()[0]);
    }
    
    public void testPrepareInsert() throws Exception
    {
    	prepareInsert();
    	
    	assertEquals("success", action.prepareInsert());
    }

	private void prepareInsert() {
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity();
    	parametrosDoSistema.setCompartilharColaboradores(true);
    	
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setId(1L);
    	action.setEmpresaSistema(empresa);
    	action.setEmpresaId(empresa.getId());
    	
    	
    	Curso curso = CursoFactory.getEntity();
    	curso.setId(1L);
    	
    	Turma turma = TurmaFactory.getEntity();
    	turma.setCurso(curso);
    	action.setTurma(turma);

    	Colaborador colaborador = ColaboradorFactory.getEntity();
    	colaborador.setId(1L);
    	colaborador.setNome("José");
    	
    	ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
    	colaboradorTurma.setId(1L);
    	colaboradorTurma.setTurma(turma);
    	colaboradorTurma.setColaborador(colaborador);
    	action.setColaboradorTurma(colaboradorTurma);
    	
    	String[] areasCheck = {"1"};
    	action.setAreasCheck(areasCheck);

    	String[] colaboradoresCursosCheck = {"1"};
    	action.setColaboradoresCursosCheck(colaboradoresCursosCheck);
    	
    	String[] estabelecimentosCheck = {"1"};
    	action.setEstabelecimentosCheck(estabelecimentosCheck);
    	
    	Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
    	colaboradorTurmas.add(colaboradorTurma);
    	
    	Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
    	colaboradors.add(colaborador);
    	
    	manager.expects(once()).method("findById").with(eq(colaboradorTurma.getId())).will(returnValue(colaboradorTurma));
    	turmaManager.expects(once()).method("findByIdProjection").with(eq(turma.getId())).will(returnValue(turma));
    	empresaManager.expects(once()).method("ajustaCombo").with(eq(empresa.getId()),ANYTHING).will(returnValue(empresa.getId()));
    	empresaManager.expects(once()).method("findEmpresasPermitidas").will(returnValue(Arrays.asList(empresa)));
    	estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(Arrays.asList(empresa)));
    	parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
    	areaOrganizacionalManager.expects(once()).method("findAllSelectOrderDescricao").with(eq(empresa.getId()),ANYTHING,ANYTHING,ANYTHING).will(returnValue(Arrays.asList(new AreaOrganizacional(1L, "area", true))));
    	cargoManager.expects(once()).method("populaCheckBox").with(eq(true), ANYTHING).will(returnValue(Arrays.asList(new Cargo(1L, "cargo", true))));
    	manager.expects(once()).method("findColaboradoresByCursoTurmaIsNull").with(eq(turma.getCurso().getId())).will(returnValue(colaboradorTurmas));
    	manager.expects(once()).method("getListaColaboradores").with(eq(colaboradorTurmas)).will(returnValue(colaboradors));
	}
    
    public void testListFiltro() throws Exception 
    {
    	ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
    	Collection<ColaboradorTurma> colaboradorTurmas = Arrays.asList(colaboradorTurma);
    	empresaManager.expects(once()).method("ajustaCombo").with(eq(1L), ANYTHING).will(returnValue(1L));
    	manager.expects(once()).method("filtrarColaboradores").will(returnValue(colaboradorTurmas));
    	manager.expects(once()).method("setFamiliaAreas").with(eq(colaboradorTurmas), ANYTHING).will(returnValue(colaboradorTurmas));
    	
    	prepareInsert();
    	assertEquals("success", action.listFiltro());
	}
    
    public void testPrepareUpdate() throws Exception 
    {
    	PrioridadeTreinamento prioridadeTreinamento = PrioridadeTreinamentoFactory.getEntity();
    	Collection<PrioridadeTreinamento> prioridadeTreinamentos = Arrays.asList(prioridadeTreinamento);
    	action.setPrioridadeTreinamentos(prioridadeTreinamentos);
    	
    	Turma turma = TurmaFactory.getEntity();
    	action.setTurma(turma);
    	
    	ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
    	colaboradorTurma.setId(1L);
    	colaboradorTurma.setTurma(turma);
    	action.setColaboradorTurma(colaboradorTurma);
    	
    	manager.expects(once()).method("findById").with(eq(colaboradorTurma.getId())).will(returnValue(colaboradorTurma));
    	turmaManager.expects(once()).method("findByIdProjection").with(eq(turma.getId())).will(returnValue(turma));
    	
    	assertEquals("success", action.prepareUpdate());
    	
	}
    
    public void testPrepareUpdateSemPrioridadeTreinamento() throws Exception 
    {
    	Collection<PrioridadeTreinamento> prioridadeTreinamentos = new ArrayList<PrioridadeTreinamento>();
    	action.setPrioridadeTreinamentos(prioridadeTreinamentos);
    	
    	Turma turma = TurmaFactory.getEntity();
    	action.setTurma(turma);
    	
    	ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
    	colaboradorTurma.setId(1L);
    	colaboradorTurma.setTurma(turma);
    	action.setColaboradorTurma(colaboradorTurma);
    	
    	manager.expects(once()).method("findById").with(eq(colaboradorTurma.getId())).will(returnValue(colaboradorTurma));
    	turmaManager.expects(once()).method("findByIdProjection").with(eq(turma.getId())).will(returnValue(turma));
    	
    	assertEquals("list", action.prepareUpdate());
    	
    }
    
    public void testPrepareUpdateDnt() throws Exception 
    {
    	action.setAreaFiltroId(1L);
    	
    	Curso curso = CursoFactory.getEntity();
    	curso.setId(1L);
    	
    	ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
    	colaboradorTurma.setId(1L);
    	colaboradorTurma.setCurso(curso);
    	action.setColaboradorTurma(colaboradorTurma);
    	
    	manager.expects(once()).method("findById").with(eq(colaboradorTurma.getId())).will(returnValue(colaboradorTurma));
    	colaboradorManager.expects(once()).method("findByArea");
    	turmaManager.expects(once()).method("findAllSelect");
    	cursoManager.expects(once()).method("find");
    	prioridadeTreinamentoManager.expects(once()).method("findAll");
    	
    	assertEquals("success", action.prepareUpdateDnt());
	}
    
    public void testGets() throws Exception
    {
		action.getColaboradorTurma();
    }
}