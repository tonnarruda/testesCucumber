package com.fortes.rh.test.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.business.desenvolvimento.AproveitamentoAvaliacaoCursoManager;
import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.desenvolvimento.AproveitamentoAvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.FiltroPlanoTreinamento;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.desenvolvimento.relatorio.ColaboradorCertificacaoRelatorio;
import com.fortes.rh.model.desenvolvimento.relatorio.MatrizTreinamento;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.util.mockObjects.MockLongUtil;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockStringUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.desenvolvimento.ColaboradorTurmaListAction;
import com.fortes.web.tags.CheckBox;


public class ColaboradorTurmaListActionTest extends MockObjectTestCase
{
	private ColaboradorTurmaListAction action;
	private Mock colaboradorTurmaManager;
	private Mock turmaManager;
	private Mock colaboradorQuestionarioManager;
	private Mock areaOrganizacionalManager;
	private Mock cargoManager;
	private Mock grupoOcupacionalManager;
	private Mock cursoManager;
	private Mock estabelecimentoManager;
	private Mock certificacaoManager;
	private Mock colaboradorManager;
	private Mock aproveitamentoAvaliacaoCursoManager;
	private Mock empresaManager;
	private Mock parametrosDoSistemaManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new ColaboradorTurmaListAction();

        colaboradorTurmaManager = new Mock(ColaboradorTurmaManager.class);
        action.setColaboradorTurmaManager((ColaboradorTurmaManager) colaboradorTurmaManager.proxy());
        
        parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);
        action.setParametrosDoSistemaManager((ParametrosDoSistemaManager) parametrosDoSistemaManager.proxy());

        turmaManager = new Mock(TurmaManager.class);
        action.setTurmaManager((TurmaManager) turmaManager.proxy());

        colaboradorQuestionarioManager = new Mock(ColaboradorQuestionarioManager.class);
        action.setColaboradorQuestionarioManager((ColaboradorQuestionarioManager) colaboradorQuestionarioManager.proxy());

        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());

        cargoManager = new Mock(CargoManager.class);
        action.setCargoManager((CargoManager) cargoManager.proxy());

        grupoOcupacionalManager = new Mock(GrupoOcupacionalManager.class);
        action.setGrupoOcupacionalManager((GrupoOcupacionalManager) grupoOcupacionalManager.proxy());

        cursoManager = new Mock(CursoManager.class);
        action.setCursoManager((CursoManager) cursoManager.proxy());

        estabelecimentoManager = new Mock(EstabelecimentoManager.class);
        action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());

        certificacaoManager = new Mock(CertificacaoManager.class);
        action.setCertificacaoManager((CertificacaoManager) certificacaoManager.proxy());

        colaboradorManager = new Mock(ColaboradorManager.class);
        action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
        
        empresaManager = new Mock(EmpresaManager.class);
        action.setEmpresaManager((EmpresaManager) empresaManager.proxy());
        
        aproveitamentoAvaliacaoCursoManager = new Mock(AproveitamentoAvaliacaoCursoManager.class);
        action.setAproveitamentoAvaliacaoCursoManager((AproveitamentoAvaliacaoCursoManager) aproveitamentoAvaliacaoCursoManager.proxy());
        
        Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
        Mockit.redefineMethods(LongUtil.class, MockLongUtil.class);
        Mockit.redefineMethods(StringUtil.class, MockStringUtil.class);
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
    }

    protected void tearDown() throws Exception
    {
        action = null;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }

    public void testList() throws Exception
    {
    	action.setMsgAlert("msgAlert");

    	Turma turma = TurmaFactory.getEntity(1L);
    	action.setTurma(turma);
    	turmaManager.expects(once()).method("findById").with(eq(turma.getId())).will(returnValue(turma));

    	Colaborador colaborador = ColaboradorFactory.getEntity();
    	colaborador.setNome("colabTurmaAprovado");
    	
    	ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
    	colaboradorTurma.setColaborador(colaborador);
    	colaboradorTurma.setTurma(turma);
    	Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
    	colaboradorTurmas.add(colaboradorTurma);
    	
    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
    	colaboradorQuestionario.setColaborador(colaborador);
    	Collection<ColaboradorQuestionario> colaboradorQuestionarioCollection = new ArrayList<ColaboradorQuestionario>();
    	colaboradorQuestionarioCollection.add(colaboradorQuestionario);

    	empresaManager.expects(atLeastOnce()).method("ajustaCombo").with(ANYTHING, ANYTHING).will(returnValue(null));
    	
    	colaboradorTurmaManager.expects(once()).method("getCount").with(eq(turma.getId()), eq(null)).will(returnValue(colaboradorTurmas.size()));
    	colaboradorTurmaManager.expects(once()).method("findByTurma").with(eq(turma.getId()), eq(null), eq(1), eq(15)).will(returnValue(colaboradorTurmas));
    	colaboradorTurmaManager.expects(once()).method("setFamiliaAreas").with(ANYTHING, ANYTHING).will(returnValue(colaboradorTurmas));
    	colaboradorQuestionarioManager.expects(once()).method("findRespondidasByColaboradorETurma").with(eq(null), eq(turma.getId()), ANYTHING).will(returnValue(colaboradorQuestionarioCollection));
    	
		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
    	
    	assertEquals("success", action.list());
    	assertEquals(colaboradorTurmas, action.getColaboradorTurmas());
    }

    public void testPrepareFiltroHistoricoTreinamentos() throws Exception
    {
    	areaOrganizacionalManager.expects(once()).method("findAllSelectOrderDescricao").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<AreaOrganizacional>()));
    	cargoManager.expects(once()).method("findAllSelect").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<Cargo>()));
    	grupoOcupacionalManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(new ArrayList<GrupoOcupacional>()));
    	
    	assertEquals("success", action.prepareFiltroHistoricoTreinamentos());
    }

    public void testFiltroHistoricoTreinamentos() throws Exception
    {
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	colaborador.setNome("Emanuela");
    	colaborador.setMatricula("123456");
    	action.setColaborador(colaborador);
    	
    	Collection<Colaborador> colabCollection = new ArrayList<Colaborador>();
    	colabCollection.add(ColaboradorFactory.getEntity(1L));
    	
    	colaboradorManager.expects(once()).method("findColaboradoresByArea").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(colabCollection));
    	areaOrganizacionalManager.expects(once()).method("findAllSelectOrderDescricao").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<AreaOrganizacional>()));
    	cargoManager.expects(once()).method("findAllSelect").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<Cargo>()));
    	grupoOcupacionalManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(new ArrayList<GrupoOcupacional>()));
    	
    	assertEquals("success", action.filtroHistoricoTreinamentos());
    }

    public void testFiltroHistoricoTreinamentosException() throws Exception
    {
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	colaborador.setNome("Emanuela");
    	colaborador.setMatricula("123456");
    	action.setColaborador(colaborador);
    	
    	Collection<Colaborador> colabCollectionVazia = new ArrayList<Colaborador>();
    	
    	colaboradorManager.expects(once()).method("findColaboradoresByArea").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(colabCollectionVazia));
    	areaOrganizacionalManager.expects(once()).method("findAllSelectOrderDescricao").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<AreaOrganizacional>()));
    	cargoManager.expects(once()).method("findAllSelect").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<Cargo>()));
    	grupoOcupacionalManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(new ArrayList<GrupoOcupacional>()));
    	
    	assertEquals("input", action.filtroHistoricoTreinamentos());
    }

    public void testRelatorioColaboradorSemTreinamento() throws Exception
    {
    	Curso curso = CursoFactory.getEntity(1L);
    	curso.setNome("Como Raparigar");
    	action.setCurso(curso);

    	empresaManager.expects(atLeastOnce()).method("ajustaCombo").with(ANYTHING, ANYTHING).will(returnValue(null));
    	colaboradorTurmaManager.expects(once()).method("findRelatorioSemTreinamento").with(ANYTHING, eq(curso), ANYTHING, ANYTHING).will(returnValue(new ArrayList<ColaboradorTurma>()));
    	
    	assertEquals("success", action.relatorioColaboradorSemTreinamento());
    }
    
    public void testRelatorioColaboradorSemTreinamentoException() throws Exception
    {
    	empresaManager.expects(atLeastOnce()).method("ajustaCombo").with(ANYTHING, ANYTHING).will(returnValue(null));
    	colaboradorTurmaManager.expects(once()).method("findRelatorioSemTreinamento").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<ColaboradorTurma>()));
    	cursoManager.expects(once()).method("findToList").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<ColaboradorTurma>()));
    	
		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
    	
    	assertEquals("input", action.relatorioColaboradorSemTreinamento());
    }

    public void testRelatorioColaboradorSemTreinamentoExceptionCllectioVazia() throws Exception
    {
    	Curso curso = CursoFactory.getEntity(1L);
    	curso.setNome("Como Raparigar");
    	action.setCurso(curso);
    	
		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
    	
    	colaboradorTurmaManager.expects(once()).method("findRelatorioSemTreinamento").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(throwException(new ColecaoVaziaException("Não existem treinamentos para o colaborador informado.")));
    	cursoManager.expects(once()).method("findToList").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<ColaboradorTurma>()));
    	empresaManager.expects(atLeastOnce()).method("ajustaCombo").with(ANYTHING, ANYTHING).will(returnValue(null));
    	
    	assertEquals("input", action.relatorioColaboradorSemTreinamento());
    }

//    public void testRelatorioColaboradorComTreinamento() throws Exception
//    {
//    	Curso curso = CursoFactory.getEntity(1L);
//    	curso.setNome("Como Raparigar");
//    	action.setCurso(curso);
//    	
//    	empresaManager.expects(atLeastOnce()).method("ajustaCombo").with(ANYTHING, ANYTHING).will(returnValue(null));    	
//    	colaboradorTurmaManager.expects(once()).method("findRelatorioComTreinamento").withAnyArguments().will(returnValue(new ArrayList<ColaboradorTurma>()));
//    	cursoManager.expects(once()).method("findByIdProjection").withAnyArguments().will(returnValue(curso));
//    	
//    	assertEquals("success", action.relatorioColaboradorComTreinamento());
//    }
    
    public void testRelatorioColaboradorComTreinamentoException() throws Exception
    {
		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
    	
    	colaboradorTurmaManager.expects(once()).method("findRelatorioComTreinamento").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(new ArrayList<ColaboradorTurma>()));
    	empresaManager.expects(atLeastOnce()).method("ajustaCombo").with(ANYTHING, ANYTHING).will(returnValue(null));
    	cursoManager.expects(once()).method("findToList").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<ColaboradorTurma>()));
    	
    	assertEquals("input", action.relatorioColaboradorComTreinamento());
    }
    
    public void testRelatorioColaboradorComTreinamentoExceptionCllectioVazia() throws Exception
    {
    	Curso curso = CursoFactory.getEntity(1L);
    	curso.setNome("Como Raparigar");
    	action.setCurso(curso);

		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
    	
    	empresaManager.expects(atLeastOnce()).method("ajustaCombo").with(ANYTHING, ANYTHING).will(returnValue(null));
    	colaboradorTurmaManager.expects(once()).method("findRelatorioComTreinamento").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(throwException(new ColecaoVaziaException("Não existem treinamentos para o colaborador informado.")));
    	cursoManager.expects(once()).method("findToList").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<ColaboradorTurma>()));
    	
    	assertEquals("input", action.relatorioColaboradorComTreinamento());
    }

    public void testRelatorioColaboradorSemIndicacaoTreinamento() throws Exception
    {
    	String[] areasCheck = new String[]{"1"};
    	action.setAreasCheck(areasCheck);
    	
    	String[] estabelecimentosCheck = new String[]{"1"};
    	action.setEstabelecimentosCheck(estabelecimentosCheck);
    	
    	colaboradorTurmaManager.expects(once()).method("findRelatorioSemIndicacaoTreinamento").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<ColaboradorTurma>()));
    	
    	assertEquals("success", action.relatorioColaboradorSemIndicacaoTreinamento());
    }

    public void testRelatorioColaboradorSemIndicacaoTreinamentoCollectionVazia() throws Exception
    {
    	String[] areasCheck = new String[]{"1"};
    	action.setAreasCheck(areasCheck);
    	
    	String[] estabelecimentosCheck = new String[]{"1"};
    	action.setEstabelecimentosCheck(estabelecimentosCheck);

		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");

    	empresaManager.expects(atLeastOnce()).method("ajustaCombo").with(ANYTHING, ANYTHING).will(returnValue(null));
    	colaboradorTurmaManager.expects(once()).method("findRelatorioSemIndicacaoTreinamento").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(throwException(new ColecaoVaziaException("Não existem treinamentos para o colaborador informado.")));
    	cursoManager.expects(once()).method("findToList").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<ColaboradorTurma>()));
    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));
    	estabelecimentoManager.expects(once()).method("populaCheckBox").will(returnValue(new ArrayList<CheckBox>()));
    	
    	assertEquals("input", action.relatorioColaboradorSemIndicacaoTreinamento());
    }

    public void testPrepareRelatorioColaboradorSemIndicacao() throws Exception
    {
		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
    	
    	empresaManager.expects(atLeastOnce()).method("ajustaCombo").with(ANYTHING, ANYTHING).will(returnValue(null));
    	cursoManager.expects(once()).method("findToList").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<ColaboradorTurma>()));
    	
    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));
    	estabelecimentoManager.expects(once()).method("populaCheckBox").will(returnValue(new ArrayList<CheckBox>()));
    	
    	assertEquals("success", action.prepareRelatorioColaboradorSemIndicacao());
    }

    public void testPrepareRelatorioColaboradorCertificacao() throws Exception
    {
		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
		
    	empresaManager.expects(atLeastOnce()).method("ajustaCombo").with(ANYTHING, ANYTHING).will(returnValue(null));
    	certificacaoManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(new ArrayList<Certificacao>()));
    	cursoManager.expects(once()).method("findToList").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<ColaboradorTurma>()));

    	assertEquals("success", action.prepareRelatorioColaboradorCertificacao());
    }

    public void testRelatorioColaboradorCertificacao() throws Exception
    {
    	Certificacao certificacao = CertificacaoFactory.getEntity(1L);  
    	certificacao.setNome("Cerificação");
    	action.setCertificacao(certificacao);
    	colaboradorTurmaManager.expects(once()).method("montaRelatorioColaboradorCertificacao").with(ANYTHING, eq(certificacao), ANYTHING, ANYTHING).will(returnValue(new ArrayList<ColaboradorCertificacaoRelatorio>()));
    	
    	assertEquals("success", action.relatorioColaboradorCertificacao());
    }

    public void testRelatorioColaboradorCertificacaoException() throws Exception
    {
    	ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
    	
    	empresaManager.expects(atLeastOnce()).method("ajustaCombo").with(ANYTHING, ANYTHING).will(returnValue(null));
    	colaboradorTurmaManager.expects(once()).method("montaRelatorioColaboradorCertificacao").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<ColaboradorCertificacaoRelatorio>()));
    	certificacaoManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(new ArrayList<Certificacao>()));
    	cursoManager.expects(once()).method("findToList").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<ColaboradorTurma>()));
    	
    	assertEquals("input", action.relatorioColaboradorCertificacao());
    }

    public void testFindNotas() throws Exception
    {
    	aproveitamentoAvaliacaoCursoManager.expects(once()).method("findByColaboradorCurso").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<AproveitamentoAvaliacaoCurso>()));
    	assertEquals("success", action.findNotas());
    }

    public void testRelatorioColaboradorCertificacaoCollectioVazia() throws Exception
    {
    	Certificacao certificacao = CertificacaoFactory.getEntity(1L);    	
    	certificacao.setNome("Cerificação");
    	action.setCertificacao(certificacao);

    	ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
    	
    	empresaManager.expects(atLeastOnce()).method("ajustaCombo").with(ANYTHING, ANYTHING).will(returnValue(null));
    	colaboradorTurmaManager.expects(once()).method("montaRelatorioColaboradorCertificacao").with(ANYTHING, eq(certificacao), ANYTHING, ANYTHING).will(throwException(new ColecaoVaziaException("Não existem treinamentos para o colaborador informado.")));
    	certificacaoManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(new ArrayList<Certificacao>()));
    	cursoManager.expects(once()).method("findToList").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<ColaboradorTurma>()));
    	
    	assertEquals("input", action.relatorioColaboradorCertificacao());
    }

    public void testRelatorioHistoricoTreinamentos() throws Exception
    {
    	Cargo cargo = CargoFactory.getEntity(1L);
    	cargo.setNome("Rapariga");
    	
    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
    	faixaSalarial.setCargo(cargo);
    	faixaSalarial.setDescricao("faixaSalarial");
    	
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	colaborador.setNome("Karina");
    	colaborador.setFaixaSalarial(faixaSalarial);
    	
    	ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
    	colaboradorTurma.setColaborador(colaborador);
    	
    	Collection<ColaboradorTurma> colabTurmaCollection = new ArrayList<ColaboradorTurma>();
    	colabTurmaCollection.add(colaboradorTurma);
    	
    	action.setColaborador(colaborador);
    	action.setDataIni(DateUtil.criarAnoMesDia(2010, 01, 01));
    	action.setDataFim(DateUtil.criarAnoMesDia(2010, 01, 01));
    	
    	colaboradorTurmaManager.expects(once()).method("findRelatorioHistoricoTreinamentos").with(ANYTHING, eq(colaborador.getId()), eq(DateUtil.criarAnoMesDia(2010, 01, 01)), eq(DateUtil.criarAnoMesDia(2010, 01, 01))).will(returnValue(colabTurmaCollection));
    	certificacaoManager.expects(once()).method("montaMatriz").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<MatrizTreinamento>()));
    	
    	assertEquals("success", action.relatorioHistoricoTreinamentos());
    }

    public void testRelatorioHistoricoTreinamentosException() throws Exception
    {
    	Cargo cargo = CargoFactory.getEntity(1L);
    	cargo.setNome("Rapariga");
    	
    	Collection<Curso> cursoCollection = new ArrayList<Curso>();
    	cursoCollection.add(new Curso());
    	
    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
    	faixaSalarial.setDescricao("faixaSalarial");
    	
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	colaborador.setNome("Karina");
    	colaborador.setFaixaSalarial(faixaSalarial);
    	
    	ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
    	colaboradorTurma.setColaborador(colaborador);
    	
    	Collection<ColaboradorTurma> colabTurmaCollection = new ArrayList<ColaboradorTurma>();
    	colabTurmaCollection.add(colaboradorTurma);
    	
    	ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
    	parametrosDoSistema.setUpperCase(true);
    	parametrosDoSistema.setCompartilharCandidatos(true);
    	
    	action.setColaborador(colaborador);
    	action.setDataIni(DateUtil.criarAnoMesDia(2010, 01, 01));
    	action.setDataFim(DateUtil.criarAnoMesDia(2010, 01, 01));

		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
    	empresaManager.expects(atLeastOnce()).method("ajustaCombo").with(ANYTHING, ANYTHING).will(returnValue(null));
    	colaboradorTurmaManager.expects(once()).method("findRelatorioHistoricoTreinamentos").with(ANYTHING, eq(colaborador.getId()), eq(DateUtil.criarAnoMesDia(2010, 01, 01)), eq(DateUtil.criarAnoMesDia(2010, 01, 01))).will(returnValue(colabTurmaCollection));
    	cursoManager.expects(once()).method("findToList").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(colabTurmaCollection));
    	
    	assertEquals("input", action.relatorioHistoricoTreinamentos());
    }

    public void testRelatorioHistoricoTreinamentosExceptionCollectionVazia() throws Exception
    {
    	Cargo cargo = CargoFactory.getEntity(1L);
    	cargo.setNome("Rapariga");
    	
    	Collection<Curso> cursoCollection = new ArrayList<Curso>();
    	cursoCollection.add(new Curso());
    	
    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
    	faixaSalarial.setDescricao("faixaSalarial");
    	
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	colaborador.setNome("Karina");
    	colaborador.setFaixaSalarial(faixaSalarial);
    	
    	action.setColaborador(colaborador);
    	action.setDataIni(DateUtil.criarAnoMesDia(2010, 01, 01));
    	action.setDataFim(DateUtil.criarAnoMesDia(2010, 01, 01));

       	ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
    	
    	empresaManager.expects(atLeastOnce()).method("ajustaCombo").with(ANYTHING, ANYTHING).will(returnValue(null));
    	colaboradorTurmaManager.expects(once()).method("findRelatorioHistoricoTreinamentos").with(ANYTHING, eq(colaborador.getId()), eq(DateUtil.criarAnoMesDia(2010, 01, 01)), eq(DateUtil.criarAnoMesDia(2010, 01, 01))).will(throwException(new ColecaoVaziaException("Não existem treinamentos para o colaborador informado.")));
    	cursoManager.expects(once()).method("findToList").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<ColaboradorTurma>()));
    	
    	assertEquals("input", action.relatorioHistoricoTreinamentos());
    }

    public void testDeleteComDnt() throws Exception
    {
    	ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
    	action.setColaboradorTurma(colaboradorTurma);

    	colaboradorTurmaManager.expects(once()).method("remove").with(eq(colaboradorTurma));

    	Long idFiltro = new Long(1L);
    	action.setDntId(idFiltro);
    	action.setAreaFiltroId(idFiltro);

    	assertEquals("successDnt", action.delete());
    	assertNotNull(action.getMsgAlert());
    	assertEquals(idFiltro, action.getDntId());
    	assertEquals(idFiltro, action.getAreaFiltroId());
    }

    public void testDeleteException() throws Exception
    {
    	ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
    	action.setColaboradorTurma(colaboradorTurma);

    	colaboradorTurmaManager.expects(once()).method("remove").with(eq(colaboradorTurma)).will(throwException(new org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException(new ObjectNotFoundException(colaboradorTurma.getId(),""))));;

    	//list
    	Turma turma = TurmaFactory.getEntity(1L);
    	action.setTurma(turma);
    	turmaManager.expects(once()).method("findById").with(eq(turma.getId())).will(returnValue(turma));

    	empresaManager.expects(atLeastOnce()).method("ajustaCombo").with(ANYTHING, ANYTHING).will(returnValue(null));

		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
    	
    	Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
    	colaboradorTurmaManager.expects(once()).method("getCount").with(eq(turma.getId()), eq(null)).will(returnValue(colaboradorTurmas.size()));
    	colaboradorTurmaManager.expects(once()).method("findByTurma").with(eq(turma.getId()), eq(null), eq(1), eq(15)).will(returnValue(colaboradorTurmas));
    	colaboradorTurmaManager.expects(once()).method("setFamiliaAreas").with(ANYTHING, ANYTHING).will(returnValue(colaboradorTurmas));
    	colaboradorQuestionarioManager.expects(once()).method("findRespondidasByColaboradorETurma").with(eq(null), eq(turma.getId()), ANYTHING).will(returnValue(new ArrayList<ColaboradorQuestionario>()));

    	assertEquals("input", action.delete());
    }

    public void testGets() throws Exception
    {
    	action.setColaboradorTurma(null);
    	assertNotNull(action.getColaboradorTurma());

    	Turma turma = TurmaFactory.getEntity(1L);
    	action.setTurma(turma);
    	assertEquals(turma, action.getTurma());

    	Curso curso = CursoFactory.getEntity(1L);
    	action.setCurso(curso);
    	assertEquals(curso, action.getCurso());

    	action.setGestor(true);
    	assertEquals(true, action.isGestor());

    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
    	action.setEstabelecimento(estabelecimento);
    	assertEquals(estabelecimento, action.getEstabelecimento());

    	FiltroPlanoTreinamento filtroPlanoTreinamento = new FiltroPlanoTreinamento();
    	action.setFiltroPlanoTreinamento(filtroPlanoTreinamento);
    	assertEquals(filtroPlanoTreinamento, action.getFiltroPlanoTreinamento());
    	
    	char aprovado = 'a';
    	action.setAprovado(aprovado);
    	assertEquals(aprovado, action.getAprovado());

    	boolean comTreinamento = true;
    	action.setComTreinamento(comTreinamento);
    	assertEquals(comTreinamento, action.isComTreinamento());

    	String[] estabelecimentosCheck = new String[]{};
    	action.setEstabelecimentosCheck(estabelecimentosCheck);
    	assertEquals(estabelecimentosCheck, action.getEstabelecimentosCheck());
    	
    	
    	action.setPrioridadeTreinamentos(null);
    }

}