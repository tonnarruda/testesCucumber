package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.CandidatoIdiomaManager;
import com.fortes.rh.business.captacao.ExperienciaManager;
import com.fortes.rh.business.captacao.FormacaoManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.CamposExtrasManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.ColaboradorIdiomaManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorOcorrenciaManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
import com.fortes.rh.business.geral.ConfiguracaoPerformanceManager;
import com.fortes.rh.business.geral.DocumentoAnexoManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.sesmt.CatManager;
import com.fortes.rh.business.sesmt.ColaboradorAfastamentoManager;
import com.fortes.rh.business.sesmt.ComissaoManager;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.dicionario.OrigemAnexo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorIdioma;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.DocumentoAnexo;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.relatorio.ParticipacaoColaboradorCipa;
import com.fortes.rh.model.sesmt.Cat;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.FormacaoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.CamposExtrasFactory;
import com.fortes.rh.test.factory.geral.ColaboradorIdiomaFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;
import com.fortes.rh.test.factory.geral.OcorrenciaFactory;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.geral.ColaboradorEditAction;
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
	private Mock parametrosDoSistemaManager;
	private Mock configuracaoPerformanceManager;
	

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
		parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
		
		
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
		
		Mockit.redefineMethods(ActionContext.class, MockActionContext.class);
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}

	protected void tearDown() throws Exception
    {
        colaboradorManager = null;
        action = null;
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
		
		CamposExtras camposExtras = CamposExtrasFactory.getEntity(1L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresa);
		colaborador.getEndereco().setUf(EstadoFactory.getEntity(1L));
		colaborador.setCamposExtras(camposExtras);
		colaboradorManager.expects(once()).method("findColaboradorById").with(ANYTHING).will(returnValue(colaborador));
		
		cidadeManager.expects(once()).method("find").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<Cidade>()));
		estadoManager.expects(once()).method("findAll").with(ANYTHING).will(returnValue(new ArrayList<Estado>()));
		
		candidatoIdiomaManager.expects(once()).method("montaListCandidatoIdioma").with(ANYTHING);
		experienciaManager.expects(once()).method("findByColaborador").with(ANYTHING);
		formacaoManager.expects(once()).method("findByColaborador").with(ANYTHING);

		configuracaoCampoExtraManager.expects(once()).method("find").with(eq(new String[]{"ativoColaborador", "empresa.id"}),eq(new Object[]{true, empresa.getId()}), eq(new String[]{"ordem"})).will(returnValue(configuracaoCampoExtras));
		camposExtrasManager.expects(once()).method("findById").with(eq(colaborador.getCamposExtras().getId())).will(returnValue(camposExtras));
		
		assertEquals("success", action.prepareUpdateInfoPessoais());
	}
	
	public void testPrepareUpdateInfoPessoaisEmpresaErrada() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(2L);
		action.setEmpresaSistema(empresa);
		
		Empresa empresaColab = EmpresaFactory.getEmpresa(44L);
		empresaColab.setNome("babau");
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresaColab);
		colaboradorManager.expects(once()).method("findColaboradorById").with(ANYTHING).will(returnValue(colaborador));
		
		assertEquals("success", action.prepareUpdateInfoPessoais());
		assertTrue(((String)action.getActionWarnings().toArray()[0]).equals("Só é possível editar dados pessoais para empresa na qual você foi contratado(a). Acesse a empresa babau para alterar suas informações."));
	}
	
	public void testPrepareUpdateInfoPessoaisEmpresaSemColaborador() throws Exception
	{
		colaboradorManager.expects(once()).method("findColaboradorById").with(ANYTHING).will(returnValue(null));
		
		assertEquals("success", action.prepareUpdateInfoPessoais());
		assertTrue(((String)action.getActionWarnings().toArray()[0]).equals("Sua conta de usuário não está vinculada à nenhum colaborador"));
	}
	
	public void testPreparePerformanceFuncional()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCampoExtraColaborador(true);
		action.setEmpresaSistema(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresa);
		action.setColaborador(colaborador);

		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1.setId(1L);
		historicoColaborador1.setColaborador(colaborador);

		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setId(2L);
		historicoColaborador2.setColaborador(colaborador);

		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();

		ColaboradorIdioma colaboradorIdioma = ColaboradorIdiomaFactory.getEntity();
		colaboradorIdioma.setId(1L);
		colaboradorIdioma.setColaborador(colaborador);

		Collection<ColaboradorIdioma> colaboradorsIdioma = new ArrayList<ColaboradorIdioma>();
		colaboradorsIdioma.add(colaboradorIdioma);

		Formacao formacao = FormacaoFactory.getEntity();
		formacao.setId(1L);
		formacao.setColaborador(colaborador);

		Collection<Formacao> formacaos = new ArrayList<Formacao>();
		formacaos.add(formacao);

		ColaboradorTurma colaboradorTurma = new ColaboradorTurma();
		colaboradorTurma.setId(1L);
		colaboradorTurma.setColaborador(colaborador);

		Collection<ColaboradorTurma> cursosColaborador = new ArrayList<ColaboradorTurma>();
		cursosColaborador.add(colaboradorTurma);

		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity();
		ocorrencia.setDescricao("Abandono do local de trabalho");
		ocorrencia.setPontuacao(10);
		
		ColaboradorOcorrencia colaboradorOcorrencia = new ColaboradorOcorrencia();
		colaboradorOcorrencia.setId(1L);
		colaboradorOcorrencia.setColaborador(colaborador);
		colaboradorOcorrencia.setOcorrencia(ocorrencia);

		Collection<ColaboradorOcorrencia> ocorrenciasColaborador = new ArrayList<ColaboradorOcorrencia>();
		ocorrenciasColaborador.add(colaboradorOcorrencia);
		
		Collection<ColaboradorAfastamento> afastamentosColaborador = new ArrayList<ColaboradorAfastamento>();
		Collection<Experiencia> experiencias = new ArrayList<Experiencia>();

		DocumentoAnexo documentoAnexo = new DocumentoAnexo();
		documentoAnexo.setId(1L);
		documentoAnexo.setOrigemId(colaborador.getId());
		documentoAnexo.setOrigem(OrigemAnexo.AnexoCandidato);

		Collection<DocumentoAnexo> documentoAnexosColaborador = new ArrayList<DocumentoAnexo>();
		documentoAnexosColaborador.add(documentoAnexo);

		Collection<AreaOrganizacional> areas = AreaOrganizacionalFactory.getCollection();
		
		ConfiguracaoCampoExtra configuracaoCampoExtra = new ConfiguracaoCampoExtra();
		configuracaoCampoExtra.setId(1L);
		configuracaoCampoExtra.setAtivoColaborador(true);
		Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras = new ArrayList<ConfiguracaoCampoExtra>();
		configuracaoCampoExtras.add(configuracaoCampoExtra);
	
		action.setColaborador(colaborador);
		
		colaboradorManager.expects(once()).method("getFoto").with(eq(colaborador.getId())).will(returnValue(null));
		configuracaoCampoExtraManager.expects(once()).method("find").with(eq(new String[]{"ativoColaborador", "empresa.id"}),eq(new Object[]{true, empresa.getId()}), eq(new String[]{"ordem"})).will(returnValue(configuracaoCampoExtras));
		colaboradorManager.expects(once()).method("findColaboradorById").with(eq(colaborador.getId())).will(returnValue(colaborador));
		colaboradorQuestionarioManager.expects(once()).method("findAvaliacaoByColaborador").with(eq(colaborador.getId()), eq(false)).will(returnValue(new ArrayList<ColaboradorQuestionario>()));
		colaboradorQuestionarioManager.expects(once()).method("findAvaliacaoByColaborador").with(eq(colaborador.getId()), eq(true)).will(returnValue(new ArrayList<ColaboradorQuestionario>()));
		historicoColaboradorManager.expects(once()).method("progressaoColaborador").with(eq(colaborador.getId()), eq(empresa.getId())).will(returnValue(historicoColaboradors));
		historicoColaboradorManager.expects(once()).method("getHistoricoAtual").with(eq(colaborador.getId())).will(returnValue(historicoColaborador1));
		colaboradorIdiomaManager.expects(once()).method("findByColaborador").with(eq(colaborador.getId())).will(returnValue(colaboradorsIdioma));
		formacaoManager.expects(once()).method("findByColaborador").with(eq(colaborador.getId())).will(returnValue(formacaos));
		colaboradorTurmaManager.expects(once()).method("findHistoricoTreinamentosByColaborador").with(eq(empresa.getId()),ANYTHING,ANYTHING, ANYTHING).will(returnValue(cursosColaborador));
		colaboradorOcorrenciaManager.expects(once()).method("findByColaborador").with(eq(colaborador.getId())).will(returnValue(ocorrenciasColaborador));
		colaboradorAfastamentoManager.expects(once()).method("findByColaborador").with(eq(colaborador.getId())).will(returnValue(afastamentosColaborador));
		experienciaManager.expects(once()).method("findByColaborador").with(eq(colaborador.getId())).will(returnValue(experiencias));
		catManager.expects(once()).method("findByColaborador").with(eq(colaborador)).will(returnValue(new ArrayList<Cat>()));
		comissaoManager.expects(once()).method("getParticipacoesDeColaboradorNaCipa").with(eq(colaborador.getId())).will(returnValue(new ArrayList<ParticipacaoColaboradorCipa>()));
		colaboradorQuestionarioManager.expects(once()).method("findByColaborador").with(eq(colaborador.getId()));
		
		documentoAnexoManager.expects(once()).method("getDocumentoAnexoByOrigemId").with(ANYTHING, eq(colaborador.getId()), eq(null)).will(returnValue(documentoAnexosColaborador));

		areaOrganizacionalManager.expects(once()).method("findAllListAndInativas").with(eq(empresa.getId()),eq(AreaOrganizacional.TODAS), ANYTHING).will(returnValue(areas));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").with(eq(areas)).will(returnValue(areas));
		configuracaoPerformanceManager.expects(once()).method("findByUsuario").with(ANYTHING);
		colaboradorManager.expects(once()).method("findByCpf").with(eq(colaborador.getPessoal().getCpf()), eq(null), eq(null), eq(null)).will(returnValue(new ArrayList<Colaborador>()));

		String retorno = "";
		Exception excep = null;

		try
		{
			retorno = action.preparePerformanceFuncional();
		}
		catch (Exception e)
		{
			excep = e;
		}

		assertNull(excep);
		assertEquals(retorno, "success");
	}

	public void testPreparePerformanceFuncionalException()
	{
		action.setColaborador(null);
		String retorno = "";
		Exception excep = null;

		try
		{
			retorno = action.preparePerformanceFuncional();
		}
		catch (Exception e)
		{
			excep = e;
		}

		assertNull(excep);
		assertEquals(retorno, "input");
	}
}
