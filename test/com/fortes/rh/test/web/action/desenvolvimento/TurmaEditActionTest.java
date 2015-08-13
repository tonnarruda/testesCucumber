package com.fortes.rh.test.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorPresencaManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.desenvolvimento.DNTManager;
import com.fortes.rh.business.desenvolvimento.DiaTurmaManager;
import com.fortes.rh.business.desenvolvimento.TurmaAvaliacaoTurmaManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.DocumentoAnexoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.TipoDespesaManager;
import com.fortes.rh.business.geral.TurmaTipoDespesaManager;
import com.fortes.rh.business.pesquisa.AvaliacaoTurmaManager;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.Certificado;
import com.fortes.rh.model.desenvolvimento.ColaboradorPresenca;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.DNT;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.model.desenvolvimento.FiltroPlanoTreinamento;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.DocumentoAnexo;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.TurmaTipoDespesa;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.util.mockObjects.MockArquivoUtil;
import com.fortes.rh.test.util.mockObjects.MockCheckListBoxUtil;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.desenvolvimento.TurmaEditAction;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.webwork.ServletActionContext;

public class TurmaEditActionTest extends MockObjectTestCase
{
	private TurmaEditAction action;
	private Mock diaTurmaManager;
	private Mock turmaManager;
	private Mock colaboradorTurmaManager;
	private Mock colaboradorPresencaManager;
	private Mock areaOrganizacionalManager;
	private Mock cursoManager;
	private Mock dNTManager;
	private Mock estabelecimentoManager;
	private Mock avaliacaoTurmaManager;
	private Mock certificacaoManager;
	private Mock empresaManager;
	private Mock colaboradorManager;
	private Mock tipoDespesaManager;
	private Mock turmaTipoDespesaManager;
	private Mock turmaAvaliacaoTurmaManager;
	private Mock documentoAnexoManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new TurmaEditAction();
        diaTurmaManager = new Mock(DiaTurmaManager.class);
        action.setDiaTurmaManager((DiaTurmaManager) diaTurmaManager.proxy());

        turmaManager = new Mock(TurmaManager.class);
        action.setTurmaManager((TurmaManager) turmaManager.proxy());

        colaboradorTurmaManager = new Mock(ColaboradorTurmaManager.class);
        action.setColaboradorTurmaManager((ColaboradorTurmaManager) colaboradorTurmaManager.proxy());

        colaboradorPresencaManager = new Mock(ColaboradorPresencaManager.class);
        action.setColaboradorPresencaManager((ColaboradorPresencaManager) colaboradorPresencaManager.proxy());

        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());

        cursoManager = new Mock(CursoManager.class);
        action.setCursoManager((CursoManager) cursoManager.proxy());

        dNTManager = new Mock(DNTManager.class);
        action.setDNTManager((DNTManager) dNTManager.proxy());
        
        turmaTipoDespesaManager = new Mock(TurmaTipoDespesaManager.class);
        action.setTurmaTipoDespesaManager((TurmaTipoDespesaManager) turmaTipoDespesaManager.proxy());

        estabelecimentoManager = new Mock(EstabelecimentoManager.class);
        action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());

        avaliacaoTurmaManager = new Mock(AvaliacaoTurmaManager.class);
        action.setAvaliacaoTurmaManager((AvaliacaoTurmaManager) avaliacaoTurmaManager.proxy());

        certificacaoManager = new Mock(CertificacaoManager.class);
        action.setCertificacaoManager((CertificacaoManager) certificacaoManager.proxy());
        
        empresaManager = new Mock(EmpresaManager.class);
        action.setEmpresaManager((EmpresaManager) empresaManager.proxy());
        
        colaboradorManager = new Mock(ColaboradorManager.class);
        action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
        
        tipoDespesaManager = new Mock(TipoDespesaManager.class);
        action.setTipoDespesaManager((TipoDespesaManager) tipoDespesaManager.proxy());

        turmaAvaliacaoTurmaManager = new Mock(TurmaAvaliacaoTurmaManager.class);
        action.setTurmaAvaliacaoTurmaManager((TurmaAvaliacaoTurmaManager) turmaAvaliacaoTurmaManager.proxy());
        
        documentoAnexoManager = new Mock(DocumentoAnexoManager.class);
        action.setDocumentoAnexoManager((DocumentoAnexoManager) documentoAnexoManager.proxy());
        
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
        Mockit.redefineMethods(CheckListBoxUtil.class, MockCheckListBoxUtil.class);
        Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
        Mockit.redefineMethods(ArquivoUtil.class, MockArquivoUtil.class);
        Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
        
        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    }

    protected void tearDown() throws Exception
    {
        action = null;
        super.tearDown();
    }

    public void testPrepareInsert() throws Exception
    {
    	action.setCurso(new Curso());
    	action.setTurma(new Turma());
    	FiltroPlanoTreinamento filtroPlanoTreinamento = new FiltroPlanoTreinamento();
    	action.setFiltroPlanoTreinamento(filtroPlanoTreinamento);

    	Collection<Curso> cursos = new ArrayList<Curso>();
    	Collection<AvaliacaoTurma> avaliacaoTurmas = new ArrayList<AvaliacaoTurma>();
    	Collection<DocumentoAnexo> documentoAnexos = new ArrayList<DocumentoAnexo>();

    	cursoManager.expects(once()).method("findAllByEmpresasParticipantes").with(ANYTHING).will(returnValue(cursos));
    	avaliacaoTurmaManager.expects(once()).method("findAllSelect").with(eq(true),ANYTHING).will(returnValue(avaliacaoTurmas));
    	documentoAnexoManager.expects(once()).method("getDocumentoAnexoByOrigemId").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(documentoAnexos));
    	tipoDespesaManager.expects(once()).method("find");

    	assertEquals("success", action.prepareInsert());
    	assertEquals(cursos, action.getCursos());
    }

    public void testPrepareUpdate() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Curso curso = CursoFactory.getEntity(1L);
    	action.setCurso(curso);
    	Turma turma = TurmaFactory.getEntity(1L);
    	turma.setEmpresa(empresa);
    	turma.setDataPrevIni(new Date());
    	action.setTurma(turma);

    	Collection<DiaTurma> diaTurmas = new ArrayList<DiaTurma>();
    	Collection<Curso> cursos = new ArrayList<Curso>();
    	Collection<AvaliacaoTurma> avaliacaoTurmas = new ArrayList<AvaliacaoTurma>();
    	Collection<DocumentoAnexo> documentoAnexos = new ArrayList<DocumentoAnexo>();

    	turmaManager.expects(once()).method("findByIdProjection").with(eq(turma.getId())).will(returnValue(turma));
//    	diaTurmaManager.expects(once()).method("montaListaDias").with(eq(turma.getDataPrevIni()), ANYTHING, eq(false)).will(returnValue(new ArrayList<CheckBox>()));
    	turmaManager.expects(once()).method("verificaAvaliacaoDeTurmaRespondida").with(eq(turma.getId())).will(returnValue(false));
    	turmaAvaliacaoTurmaManager.expects(once()).method("verificaAvaliacaoliberada").with(eq(turma.getId())).will(returnValue(false));
    	Collection<CheckBox> diasCheckList = MockCheckListBoxUtil.populaCheckListBox(null, null, null);
    	diaTurmaManager.expects(once()).method("find").with(ANYTHING, ANYTHING).will(returnValue(diaTurmas));
    	colaboradorPresencaManager.expects(once()).method("existPresencaByTurma").with(eq(turma.getId())).will(returnValue(true));

    	cursoManager.expects(once()).method("findAllByEmpresasParticipantes").with(eq(new Long[]{1L})).will(returnValue(cursos));
    	cursoManager.expects(once()).method("existeEmpresasNoCurso").with(eq(empresa.getId()),eq(curso.getId())).will(returnValue(true));
    	avaliacaoTurmaManager.expects(once()).method("findByTurma").with(ANYTHING).will(returnValue(avaliacaoTurmas));
    	avaliacaoTurmaManager.expects(once()).method("findAllSelect").with(eq(true), ANYTHING).will(returnValue(avaliacaoTurmas));
    	tipoDespesaManager.expects(once()).method("find");
    	turmaTipoDespesaManager.expects(once()).method("findTipoDespesaTurma").will(returnValue(new ArrayList<TurmaTipoDespesa>()));
    	documentoAnexoManager.expects(once()).method("getDocumentoAnexoByOrigemId").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(documentoAnexos));
    	documentoAnexoManager.expects(once()).method("findByTurma").with(ANYTHING).will(returnValue(documentoAnexos));

    	assertEquals("success", action.prepareUpdate());
    	assertEquals(false, action.isContemCustosDetalhados());
    	assertEquals(diasCheckList, action.getDiasCheckList());
    }

    public void testInsert() throws Exception
    {
    	Turma turma = TurmaFactory.getEntity(1L);
    	
    	action.setAvaliacaoTurmasCheck(new String[] { "234" });
    	action.setTurma(turma);

    	turmaManager.expects(once()).method("inserir").withAnyArguments().isVoid();;
    	turmaManager.expects(once()).method("setAssinaturaDigital").withAnyArguments().isVoid();;
    	
    	assertEquals("success", action.insert());
    }

    public void testUpdate() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	Turma turma = TurmaFactory.getEntity(10L);
    	turma.setEmpresa(empresa);
    	
    	action.setAvaliacaoTurmasCheck(new String[] { "234" });
    	action.setTurma(turma);
    	
    	turmaManager.expects(once()).method("atualizar").withAnyArguments().isVoid();
    	turmaManager.expects(once()).method("setAssinaturaDigital").will(returnValue(turma));
    	
    	assertEquals("success", action.update());
    }
    
    public void testPreparePresenca() throws Exception
    {
    	Turma turma = TurmaFactory.getEntity(10L);
    	action.setTurma(turma);
    	Collection<Turma> turmas = new ArrayList<Turma>();
    	turmas.add(turma);
    	
    	diaTurmaManager.expects(once()).method("findByTurma").with(eq(10L)).will(returnValue(turmas));
    	colaboradorTurmaManager.expects(once()).method("findByTurma").with(new Constraint[] { eq(10L), ANYTHING, eq(true), ANYTHING, ANYTHING }).will(returnValue(new ArrayList<ColaboradorTurma>()));
    	turmaManager.expects(once()).method("findByIdProjection").with(eq(10L)).will(returnValue(turma));
    	colaboradorPresencaManager.expects(once()).method("findPresencaByTurma").with(eq(10L)).will(returnValue(new ArrayList<ColaboradorPresenca>()));
    	
    	assertEquals("success", action.preparePresenca());
    	assertTrue(action.getActionMessages().isEmpty());
    }
    public void testPreparePresencaVazio() throws Exception
    {
    	Turma turma = TurmaFactory.getEntity(10L);
    	action.setTurma(turma);
    	
    	diaTurmaManager.expects(once()).method("findByTurma").with(eq(turma.getId())).will(returnValue(new ArrayList<Turma>()));
    	colaboradorTurmaManager.expects(once()).method("findByTurma").with(new Constraint[] { eq(10L), ANYTHING, eq(true), ANYTHING, ANYTHING }).will(returnValue(new ArrayList<ColaboradorTurma>()));
    	turmaManager.expects(once()).method("findByIdProjection").with(eq(10L)).will(returnValue(turma));
    	colaboradorPresencaManager.expects(once()).method("findPresencaByTurma").with(eq(10L)).will(returnValue(new ArrayList<ColaboradorPresenca>()));
    	
    	assertEquals("success", action.preparePresenca());
    	assertEquals("Não existe previsão de dias para esta turma.",action.getActionMessages().toArray()[0]);
    }
    
    public void testPrepareFrequencia() throws Exception
    {
    	cursoManager.expects(once()).method("find").withAnyArguments().will(returnValue(new ArrayList<Curso>()));
    	assertEquals("success", action.prepareFrequencia());
    }
    
    public void testVerTurmasCurso() throws Exception
    {
    	Curso curso = CursoFactory.getEntity(12L);
    	action.setCurso(curso);
    	
    	Turma turma = TurmaFactory.getEntity(10L);
    	Collection<Turma> turmas = new ArrayList<Turma>();
    	turmas.add(turma);
    	
    	turmaManager.expects(once()).method("getCount").will(returnValue(1));
    	turmaManager.expects(once()).method("find").withAnyArguments().will(returnValue(turmas));
    	
    	cursoManager.expects(once()).method("find").withAnyArguments().will(returnValue(new ArrayList<Curso>()));
    	
    	assertEquals("success", action.verTurmasCurso());
    	assertTrue(action.getActionMessages().isEmpty());
    }
    public void testVerTurmasCursoVazio() throws Exception
    {
    	Curso curso = CursoFactory.getEntity(12L);
    	action.setCurso(curso);
    	
    	turmaManager.expects(once()).method("getCount").will(returnValue(1));
    	turmaManager.expects(once()).method("find").withAnyArguments().will(returnValue(new ArrayList<Turma>()));
    	
    	cursoManager.expects(once()).method("find").withAnyArguments().will(returnValue(new ArrayList<Curso>()));
    	
    	assertEquals("success", action.verTurmasCurso());
    	assertEquals("Não existe turma para o curso informado.<br>",action.getActionMessages().toArray()[0]);
    }
    
    public void testPrepareImprimirTurma() throws Exception
    {
    	dNTManager.expects(once()).method("findToList").withAnyArguments().will(returnValue(new ArrayList<DNT>()));
    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").will(returnValue(new ArrayList<CheckBox>()));
    	estabelecimentoManager.expects(once()).method("populaCheckBox").will(returnValue(new ArrayList<CheckBox>()));
    	
    	assertEquals("success",action.prepareImprimirTurma());
    }
    
    public void testInsertPresenca() throws Exception
    {
    	assertEquals("success",action.insertPresenca());
    }

    public void testPrepareRelatorio() throws Exception
    {
    	assertEquals("success", action.prepareRelatorio());
    }
    
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
    	
    	cursoManager.expects(once()).method("findByCertificacao").with(eq(1L)).will(returnValue(new ArrayList<Curso>()));
    	colaboradorTurmaManager.expects(once()).method("findAprovadosByCertificacao").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<Colaborador>()));
		certificacaoManager.expects(once()).method("findByIdProjection").with(eq(1L)).will(returnValue(certificacao));
    	empresaManager.expects(once()).method("findCidade").with(eq(1L)).will(returnValue("Fortaleza"));
    	
    	cursoManager.expects(once()).method("findAllSelect").with(eq(1L)).will(returnValue(new ArrayList<Curso>()));
    	certificacaoManager.expects(once()).method("findAllSelect").with(eq(1L)).will(returnValue(new ArrayList<Certificacao>()));
    	
    	turmaManager.expects(once()).method("findAllSelect").with(eq(1L)).will(returnValue(new ArrayList<Turma>()));
    	
    	assertEquals("success", action.getColaboradoresByFiltro());
    }
    
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

    	colaboradorTurmaManager.expects(once()).method("montaExibicaoAprovadosReprovados").with(ANYTHING, eq(turma.getId())).will(returnValue(colaboradors));
    	turmaManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(turma));
    	empresaManager.expects(once()).method("findCidade").with(eq(1L)).will(returnValue("Fortaleza"));
    	
    	cursoManager.expects(once()).method("findAllSelect").with(eq(1L)).will(returnValue(new ArrayList<Curso>()));
    	certificacaoManager.expects(once()).method("findAllSelect").with(eq(1L)).will(returnValue(new ArrayList<Certificacao>()));
    	
    	turmaManager.expects(once()).method("findAllSelect").with(eq(1L)).will(returnValue(new ArrayList<Turma>()));
    	
    	assertEquals("success", action.getColaboradoresByFiltro());
    }

    public void testPrepareImprimirCertificado() throws Exception
    {
    	Collection<Curso> cursos = new ArrayList<Curso>();
    	cursoManager.expects(once()).method("findAllByEmpresasParticipantes").with(ANYTHING).will(returnValue(cursos));
    	certificacaoManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(new ArrayList<Certificacao>()));
    	assertEquals("success", action.prepareImprimirCertificado());
    	assertEquals(cursos, action.getCursos());
    }
    
    public void testImprimirCertificado() throws Exception
    {
    	Certificado certificado = new Certificado();
    	certificado.setTamanho("1");
		action.setCertificado(certificado);
		String[] colaboradoresCheck = {"1000"};
		action.setColaboradoresCheck(colaboradoresCheck);
		action.setTurma(TurmaFactory.getEntity(2L));
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1000L);
    	Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
    	colaboradors.add(colaborador);
		
    	colaboradorManager.expects(once()).method("findComNotaDoCurso").with(ANYTHING, ANYTHING).will(returnValue(colaboradors));
    	colaboradorTurmaManager.expects(once()).method("montaCertificados").with(ANYTHING,ANYTHING,ANYTHING).will(returnValue(new ArrayList<Colaborador>()));
    	
    	assertEquals("successGrande", action.imprimirCertificado());
    	
    	certificado.setTamanho("0"); //pequeno
    	
    	colaboradorManager.expects(once()).method("findComNotaDoCurso").with(ANYTHING, ANYTHING).will(returnValue(colaboradors));
    	colaboradorTurmaManager.expects(once()).method("montaCertificados").with(ANYTHING,ANYTHING,ANYTHING).will(returnValue(new ArrayList<Colaborador>()));
    	
    	assertEquals("successPequeno", action.imprimirCertificado());
    }
    
    public void testImprimirCertificadoVazio() throws Exception
    {
    	action.setColaboradoresCheck(null);
    	cursoManager.expects(once()).method("findAllByEmpresasParticipantes").with(ANYTHING).will(returnValue(new ArrayList<Curso>()));
    	certificacaoManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(new ArrayList<Certificacao>()));
    	assertEquals("input",action.imprimirCertificado());
    }
    
    public void testImprimirCertificadoVerso() throws Exception
    {
    	Certificado certificado = new Certificado();
		String[] colaboradoresCheck = {"1000"};
		
		action.setCertificado(certificado);
		action.setColaboradoresCheck(colaboradoresCheck);
		action.setCurso(CursoFactory.getEntity(2L));
		action.setTurma(TurmaFactory.getEntity(2L));
		
		certificado.setTamanho("1");//grande

		Colaborador colaborador = ColaboradorFactory.getEntity(1000L);
    	Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
    	colaboradors.add(colaborador);
		
    	colaboradorManager.expects(once()).method("findComNotaDoCurso").with(ANYTHING, ANYTHING).will(returnValue(colaboradors));
    	cursoManager.expects(once()).method("getConteudoProgramatico").with(eq(2L)).will(returnValue("Conteúdo programático: bla bla bla"));
    	
    	assertEquals("successGrande", action.imprimirCertificadoVerso());
    	
    	certificado.setTamanho("0");//pequeno
    	
    	colaboradorManager.expects(once()).method("findComNotaDoCurso").with(ANYTHING, ANYTHING).will(returnValue(colaboradors));
    	cursoManager.expects(once()).method("getConteudoProgramatico").with(eq(2L)).will(returnValue("Conteúdo programático: bla bla bla"));
    	
    	assertEquals("successPequeno", action.imprimirCertificadoVerso());
    	
    }
    public void testImprimirCertificadoVersoVazio() throws Exception
    {
    	action.setColaboradoresCheck(null);
    	cursoManager.expects(once()).method("findAllByEmpresasParticipantes").with(ANYTHING).will(returnValue(new ArrayList<Curso>()));
    	certificacaoManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(new ArrayList<Certificacao>()));
    	assertEquals("input",action.imprimirCertificadoVerso());
    }

    public void testGets() throws Exception
    {
    	Date data = new Date();
    	action.setDataIni(data);
    	assertEquals(data, action.getDataIni());

    	action.setDataFim(data);
    	assertEquals(data, action.getDataFim());
    	
    	action.setAvaliacaoTurma(new AvaliacaoTurma());
    	action.getAvaliacaoTurma();
    	action.getAvaliacaoTurmas();
    	action.getAvaliacaoCursos();
    	action.setAvaliacaoCurso(null);
    	action.setNotas(new String[]{"10","9","8"});
    	action.setColaboradorTurmaIds(null);
    	action.getAproveitamentos();
    	action.getColaboradorTurmasIds();
    	action.isAvaliacaoRespondida();
    	action.getCertificacaos();
    	action.getCertificacao();
    	action.getCertificadoDe();
    	action.getCertificado();
    	action.getColaboradoresTurma();
    	action.getCertificacaoTreinamentos();
    	action.setPlanoTreinamento(true);
    	assertTrue(action.isPlanoTreinamento());
    }

}