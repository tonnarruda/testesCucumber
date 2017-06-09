package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.avaliacao.PeriodoExperienciaManager;
import com.fortes.rh.business.captacao.CandidatoIdiomaManager;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.ExperienciaManager;
import com.fortes.rh.business.captacao.FormacaoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.CamposExtrasManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.ColaboradorIdiomaManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorOcorrenciaManager;
import com.fortes.rh.business.geral.ColaboradorPeriodoExperienciaAvaliacaoManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
import com.fortes.rh.business.geral.ConfiguracaoPerformanceManager;
import com.fortes.rh.business.geral.DocumentoAnexoManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.geral.QuantidadeLimiteColaboradoresPorCargoManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.sesmt.CatManager;
import com.fortes.rh.business.sesmt.ColaboradorAfastamentoManager;
import com.fortes.rh.business.sesmt.ComissaoManager;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.CamposExtrasFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.geral.ColaboradorEditAction;
import com.fortes.rh.web.ws.AcPessoalClientSistema;
import com.opensymphony.xwork.ActionContext;

public class ColaboradorEditActionTest extends MockObjectTestCase
{
	private ColaboradorEditAction action;
	private Mock colaboradorManager;
	private Mock historicoColaboradorManager;
	private Mock colaboradorIdiomaManager;
	private Mock formacaoManager;
	private Mock colaboradorTurmaManager;
	private Mock colaboradorOcorrenciaManager;
	private Mock documentoAnexoManager;
	private Mock areaOrganizacionalManager;
	private Mock cidadeManager;
	private Mock estadoManager;
	private Mock candidatoIdiomaManager;
	private Mock experienciaManager;
	private Mock colaboradorQuestionarioManager;
	private Mock colaboradorAfastamentoManager;
	private Mock catManager;
	private Mock comissaoManager;
	private Mock configuracaoCampoExtraManager;
	private Mock camposExtrasManager;
	private Mock configuracaoPerformanceManager;
	private Mock transactionManager;
	private Mock candidatoManager;
	private Mock quantidadeLimiteColaboradoresPorCargoManager;
	private Mock colaboradorPeriodoExperienciaAvaliacaoManager;
	private Mock acPessoalClientSistema;
	private Mock faixaSalarialManager;
	private Mock estabelecimentoManager;
	private Mock periodoExperienciaManager;
	private Mock avaliacaoManager;
	private Mock indiceManager;
	private Mock parametrosDoSistemaManager;
	private Mock usuarioManager;
	private Mock usuarioEmpresaManager;
	private Mock gerenciadorComunicacaoManager;

	protected void setUp () throws Exception
	{
		action = new ColaboradorEditAction();

		configuracaoPerformanceManager = new Mock(ConfiguracaoPerformanceManager.class);
		colaboradorManager = new Mock(ColaboradorManager.class);
		historicoColaboradorManager = new Mock(HistoricoColaboradorManager.class);
		colaboradorIdiomaManager = new Mock(ColaboradorIdiomaManager.class);
		formacaoManager = new Mock(FormacaoManager.class);
		colaboradorTurmaManager = new Mock(ColaboradorTurmaManager.class);
		colaboradorOcorrenciaManager = new Mock(ColaboradorOcorrenciaManager.class);
		documentoAnexoManager = new Mock(DocumentoAnexoManager.class);
		areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
		cidadeManager = new Mock(CidadeManager.class);
		estadoManager = new Mock(EstadoManager.class);
		candidatoIdiomaManager = new Mock(CandidatoIdiomaManager.class);
		experienciaManager = new Mock(ExperienciaManager.class);
		colaboradorQuestionarioManager = new Mock(ColaboradorQuestionarioManager.class);
		colaboradorAfastamentoManager = mock(ColaboradorAfastamentoManager.class);
		catManager = mock(CatManager.class);
		comissaoManager = mock(ComissaoManager.class);
		configuracaoCampoExtraManager = mock(ConfiguracaoCampoExtraManager.class);
		camposExtrasManager = new Mock(CamposExtrasManager.class);
		transactionManager = new Mock(PlatformTransactionManager.class);
		candidatoManager = new Mock(CandidatoManager.class);
		quantidadeLimiteColaboradoresPorCargoManager = new Mock(QuantidadeLimiteColaboradoresPorCargoManager.class);
		colaboradorPeriodoExperienciaAvaliacaoManager = new Mock(ColaboradorPeriodoExperienciaAvaliacaoManager.class);
		acPessoalClientSistema = new Mock(AcPessoalClientSistema.class);
		faixaSalarialManager = new Mock(FaixaSalarialManager.class);
		estabelecimentoManager = new Mock(EstabelecimentoManager.class);
		periodoExperienciaManager = new Mock(PeriodoExperienciaManager.class);
		avaliacaoManager = new Mock(AvaliacaoManager.class);
		indiceManager = new Mock(IndiceManager.class);
		parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);
		usuarioManager = new Mock(UsuarioManager.class);
		usuarioEmpresaManager = new Mock(UsuarioEmpresaManager.class);
		gerenciadorComunicacaoManager = new Mock(GerenciadorComunicacaoManager.class);
		
		action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
		action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
		action.setHistoricoColaboradorManager((HistoricoColaboradorManager) historicoColaboradorManager.proxy());
		action.setColaboradorIdiomaManager((ColaboradorIdiomaManager) colaboradorIdiomaManager.proxy());
		action.setFormacaoManager((FormacaoManager) formacaoManager.proxy());
		action.setColaboradorTurmaManager((ColaboradorTurmaManager) colaboradorTurmaManager.proxy());
		action.setColaboradorOcorrenciaManager((ColaboradorOcorrenciaManager) colaboradorOcorrenciaManager.proxy());
		action.setDocumentoAnexoManager((DocumentoAnexoManager) documentoAnexoManager.proxy());
		action.setCidadeManager((CidadeManager) cidadeManager.proxy());
		action.setEstadoManager((EstadoManager) estadoManager.proxy());
		action.setCandidatoIdiomaManager((CandidatoIdiomaManager) candidatoIdiomaManager.proxy());
		action.setExperienciaManager((ExperienciaManager) experienciaManager.proxy());
		action.setColaboradorQuestionarioManager((ColaboradorQuestionarioManager) colaboradorQuestionarioManager.proxy());
		action.setColaboradorAfastamentoManager((ColaboradorAfastamentoManager) colaboradorAfastamentoManager.proxy());
		action.setCatManager((CatManager) catManager.proxy());
		action.setComissaoManager((ComissaoManager) comissaoManager.proxy());
		action.setConfiguracaoCampoExtraManager((ConfiguracaoCampoExtraManager) configuracaoCampoExtraManager.proxy());
		action.setCamposExtrasManager((CamposExtrasManager) camposExtrasManager.proxy());
		action.setConfiguracaoPerformanceManager((ConfiguracaoPerformanceManager) configuracaoPerformanceManager.proxy());
		action.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());
		action.setCandidatoManager((CandidatoManager) candidatoManager.proxy());
		action.setQuantidadeLimiteColaboradoresPorCargoManager((QuantidadeLimiteColaboradoresPorCargoManager) quantidadeLimiteColaboradoresPorCargoManager.proxy());
		action.setColaboradorPeriodoExperienciaAvaliacaoManager((ColaboradorPeriodoExperienciaAvaliacaoManager) colaboradorPeriodoExperienciaAvaliacaoManager.proxy());
		action.setAcPessoalClientSistema((AcPessoalClientSistema) acPessoalClientSistema.proxy());
		action.setFaixaSalarialManager((FaixaSalarialManager) faixaSalarialManager.proxy());
		action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
		action.setPeriodoExperienciaManager((PeriodoExperienciaManager) periodoExperienciaManager.proxy());
		action.setAvaliacaoManager((AvaliacaoManager) avaliacaoManager.proxy());
		action.setIndiceManager((IndiceManager) indiceManager.proxy());
		action.setParametrosDoSistemaManager((ParametrosDoSistemaManager) parametrosDoSistemaManager.proxy());
		action.setUsuarioManager((UsuarioManager) usuarioManager.proxy());
		action.setUsuarioEmpresaManager((UsuarioEmpresaManager)usuarioEmpresaManager.proxy());
		action.setGerenciadorComunicacaoManager((GerenciadorComunicacaoManager) gerenciadorComunicacaoManager.proxy());
		
		Mockit.redefineMethods(ActionContext.class, MockActionContext.class);
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}

	protected void tearDown() throws Exception
    {
        colaboradorManager = null;
        action = null;
        MockSecurityUtil.verifyRole = false;
        Mockit.restoreAllOriginalDefinitions();
        super.tearDown();
    }

	public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }
	
	public void testPrepareUpdateInfoPessoais() throws Exception
	{
		ConfiguracaoCampoExtra configuracaoCampoExtra = new ConfiguracaoCampoExtra();
		configuracaoCampoExtra.setId(1L);
		configuracaoCampoExtra.setAtivoColaborador(true);
		Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras = new ArrayList<ConfiguracaoCampoExtra>();
		configuracaoCampoExtras.add(configuracaoCampoExtra);
	
		Empresa empresa = EmpresaFactory.getEmpresa(2L);
		empresa.setCampoExtraColaborador(true);
		action.setEmpresaSistema(empresa);
		
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity();
		
		CamposExtras camposExtras = CamposExtrasFactory.getEntity(1L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresa);
		colaborador.getEndereco().setUf(EstadoFactory.getEntity(1L));
		colaborador.setCamposExtras(camposExtras);
		
		parametrosDoSistemaManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(parametrosDoSistema));
		colaboradorManager.expects(once()).method("findColaboradorById").with(ANYTHING).will(returnValue(colaborador));
		
		cidadeManager.expects(once()).method("find").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<Cidade>()));
		estadoManager.expects(once()).method("findAll").with(ANYTHING).will(returnValue(new ArrayList<Estado>()));
		
		candidatoIdiomaManager.expects(once()).method("montaListCandidatoIdioma").with(ANYTHING);
		experienciaManager.expects(once()).method("findByColaborador").with(ANYTHING);
		formacaoManager.expects(once()).method("findByColaborador").with(ANYTHING);

		assertEquals("success", action.prepareUpdateInfoPessoais());
	}
	
	public void testPrepareUpdateInfoPessoaisEmpresaErrada() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(2L);
		action.setEmpresaSistema(empresa);
		
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity();
		
		Empresa empresaColab = EmpresaFactory.getEmpresa(44L);
		empresaColab.setNome("babau");
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresaColab);
		
		colaboradorManager.expects(once()).method("findColaboradorById").with(ANYTHING).will(returnValue(colaborador));
		parametrosDoSistemaManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(parametrosDoSistema));
		
		assertEquals("success", action.prepareUpdateInfoPessoais());
		assertTrue(((String)action.getActionWarnings().toArray()[0]).equals("Só é possível editar dados pessoais para empresa na qual você foi contratado(a). Acesse a empresa babau para alterar suas informações."));
	}
	
	public void testPrepareUpdateInfoPessoaisEmpresaSemColaborador() throws Exception
	{
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity();
		colaboradorManager.expects(once()).method("findColaboradorById").with(ANYTHING).will(returnValue(null));
		parametrosDoSistemaManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(parametrosDoSistema));
		assertEquals("success", action.prepareUpdateInfoPessoais());
		assertTrue(((String)action.getActionWarnings().toArray()[0]).equals("Sua conta de usuário não está vinculada à nenhum colaborador"));
	}
	
	public void testPrepareColaboradorSolicitacaoTendoOutroColaboradorComMesmoCpf() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Candidato candidato = CandidatoFactory.getCandidato(1L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresa);
		action.setColaborador(colaborador);
		
		Colaborador colaboradorMesmoCpf = ColaboradorFactory.getEntity(2L);
		colaboradorMesmoCpf.setCandidato(candidato);
		colaboradorMesmoCpf.setEmpresa(empresa);
		colaboradorMesmoCpf.setDesligado(true);
		
		colaboradorManager.expects(once()).method("findByIdComHistoricoConfirmados").with(eq(colaborador.getId())).will(returnValue(colaborador));
		transactionManager.expects(once()).method("getTransaction").withAnyArguments().will(returnValue(null));
		candidatoManager.expects(once()).method("findByCPF").with(eq(colaborador.getPessoal().getCpf()), eq(colaborador.getEmpresa().getId())).will(returnValue(candidato));
		colaboradorManager.expects(once()).method("findByCandidato").with(eq(candidato.getId()), eq(colaborador.getEmpresa().getId())).will(returnValue(colaboradorMesmoCpf));
		transactionManager.expects(once()).method("rollback").withAnyArguments().isVoid();
		
		assertEquals("mesmo_cpf", action.prepareColaboradorSolicitacao());
	}

	public void testPrepareColaboradorSolicitacaoVincularComCandidato() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Candidato candidato = CandidatoFactory.getCandidato(1L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresa);
		action.setColaborador(colaborador);
		
		colaboradorManager.expects(once()).method("findByIdComHistoricoConfirmados").with(eq(colaborador.getId())).will(returnValue(colaborador));
		transactionManager.expects(once()).method("getTransaction").withAnyArguments().will(returnValue(null));
		candidatoManager.expects(once()).method("findByCPF").with(eq(colaborador.getPessoal().getCpf()), eq(colaborador.getEmpresa().getId())).will(returnValue(candidato));
		colaboradorManager.expects(once()).method("findByCandidato").with(eq(candidato.getId()), eq(colaborador.getEmpresa().getId())).will(returnValue(null));
		transactionManager.expects(once()).method("rollback").withAnyArguments().isVoid();
		
		assertEquals("mesmo_cpf", action.prepareColaboradorSolicitacao());
	}
	
	public void testPrepareColaboradorSolicitacaoCriandoNovoCandidato() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Candidato candidato = CandidatoFactory.getCandidato(1L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresa);
		action.setColaborador(colaborador);
		
		colaboradorManager.expects(once()).method("findByIdComHistoricoConfirmados").with(eq(colaborador.getId())).will(returnValue(colaborador));
		transactionManager.expects(once()).method("getTransaction").withAnyArguments().will(returnValue(null));
		candidatoManager.expects(once()).method("findByCPF").with(eq(colaborador.getPessoal().getCpf()), eq(colaborador.getEmpresa().getId())).will(returnValue(null));
		colaboradorIdiomaManager.expects(once()).method("find").will(returnValue(null));	
		experienciaManager.expects(once()).method("findByColaborador").will(returnValue(null));	
		formacaoManager.expects(once()).method("findByColaborador").will(returnValue(null));	
		candidatoManager.expects(once()).method("saveOrUpdateCandidatoByColaborador").with(eq(colaborador)).will(returnValue(candidato));
		colaboradorManager.expects(once()).method("update").with(eq(colaborador)).isVoid();
		candidatoManager.expects(once()).method("updateDisponivelAndContratadoByColaborador").with(eq(false), eq(!colaborador.isDesligado()), eq(new Long[]{colaborador.getId()})).isVoid();
		transactionManager.expects(once()).method("commit").withAnyArguments().isVoid();
		
		assertEquals("success", action.prepareColaboradorSolicitacao());
	}
	
	public void testPrepareColaboradorSolicitacaoColaboradorComCandidato() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Candidato candidato = CandidatoFactory.getCandidato(1L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setCandidato(candidato);
		colaborador.setEmpresa(empresa);
		action.setColaborador(colaborador);
		
		colaboradorManager.expects(once()).method("findByIdComHistoricoConfirmados").with(eq(colaborador.getId())).will(returnValue(colaborador));
		transactionManager.expects(once()).method("getTransaction").withAnyArguments().will(returnValue(null));
		candidatoManager.expects(once()).method("update").with(eq(colaborador.getCandidato())).isVoid();
		transactionManager.expects(once()).method("commit").withAnyArguments().isVoid();
		
		assertEquals("success", action.prepareColaboradorSolicitacao());
	}
}
