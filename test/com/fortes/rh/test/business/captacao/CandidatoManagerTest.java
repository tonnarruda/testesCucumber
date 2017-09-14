package com.fortes.rh.test.business.captacao;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.core.Constraint;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.model.type.File;
import com.fortes.rh.business.captacao.AnuncioManager;
import com.fortes.rh.business.captacao.CandidatoCurriculoManager;
import com.fortes.rh.business.captacao.CandidatoIdiomaManager;
import com.fortes.rh.business.captacao.CandidatoManagerImpl;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.ExperienciaManager;
import com.fortes.rh.business.captacao.FormacaoManager;
import com.fortes.rh.business.captacao.HistoricoCandidatoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.geral.BairroManager;
import com.fortes.rh.business.geral.CamposExtrasManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.sesmt.SolicitacaoExameManager;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoCurriculo;
import com.fortes.rh.model.captacao.CandidatoIdioma;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.Ctps;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.Idioma;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.Apto;
import com.fortes.rh.model.dicionario.CampoExtra;
import com.fortes.rh.model.dicionario.OrigemCandidato;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorIdioma;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.geral.SocioEconomica;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.test.business.MockObjectTestCaseManager;
import com.fortes.rh.test.business.TesteAutomaticoManager;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.geral.ConfiguracaoCampoExtraFactory;
import com.fortes.rh.test.util.mockObjects.MockArquivoUtil;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.util.Zip;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class CandidatoManagerTest extends MockObjectTestCaseManager<CandidatoManagerImpl> implements TesteAutomaticoManager
{
	private Mock candidatoSolicitacaoManager = null;
	private Mock candidatoDao = null;
	private Mock mail = null;
	private Mock anuncioManager = null;
	private Mock experienciaManager = null;
	private Mock formacaoManager = null;
	private Mock candidatoIdiomaManager = null;
	private Mock transactionManager = null;
	private Mock solicitacaoManager = null;
	private Mock parametrosDoSistemaManager = null;
	private Mock bairroManager = null;
	private Mock candidatoCurriculoManager = null;
	private Mock empresaManager;
	private Mock colaboradorQuestionarioManager;
	private Mock colaboradorManager;
	private Mock solicitacaoExameManager;
	private Mock configuracaoNivelCompetenciaManager;
	private Mock gerenciadorComunicacaoManager;
	private Mock cidadeManager;
	private Mock configuracaoCampoExtraManager;
	private Mock camposExtrasManager;
	private Mock historicoCandidatoManager;
	
    protected void setUp() throws Exception
    {
        super.setUp();
        manager = new CandidatoManagerImpl();
        
        candidatoSolicitacaoManager = new Mock(CandidatoSolicitacaoManager.class);
        candidatoDao = new Mock(CandidatoDao.class);
        mail = mock(Mail.class);
        anuncioManager = new Mock(AnuncioManager.class);
        experienciaManager = new Mock(ExperienciaManager.class);
        formacaoManager = new Mock(FormacaoManager.class);
        candidatoIdiomaManager= new Mock(CandidatoIdiomaManager.class);
        transactionManager = new Mock(PlatformTransactionManager.class);
        solicitacaoManager = new Mock(SolicitacaoManager.class);
        parametrosDoSistemaManager= new Mock(ParametrosDoSistemaManager.class);
        bairroManager= new Mock(BairroManager.class);
        candidatoCurriculoManager = new Mock(CandidatoCurriculoManager.class);
        empresaManager = new Mock(EmpresaManager.class); 
        solicitacaoExameManager = new Mock(SolicitacaoExameManager.class); 
        configuracaoNivelCompetenciaManager = new Mock(ConfiguracaoNivelCompetenciaManager.class); 
        gerenciadorComunicacaoManager = new Mock(GerenciadorComunicacaoManager.class); 
        cidadeManager = new Mock(CidadeManager.class); 
        configuracaoCampoExtraManager = new Mock(ConfiguracaoCampoExtraManager.class);
        camposExtrasManager = new Mock(CamposExtrasManager.class);
        historicoCandidatoManager = new Mock(HistoricoCandidatoManager.class);
        
        manager.setCandidatoSolicitacaoManager((CandidatoSolicitacaoManager) candidatoSolicitacaoManager.proxy());
        manager.setDao((CandidatoDao) candidatoDao.proxy());
        manager.setMail((Mail) mail.proxy());
        manager.setAnuncioManager((AnuncioManager) anuncioManager.proxy());
        manager.setExperienciaManager((ExperienciaManager) experienciaManager.proxy());
        manager.setFormacaoManager((FormacaoManager) formacaoManager.proxy());
        manager.setCandidatoIdiomaManager((CandidatoIdiomaManager) candidatoIdiomaManager.proxy());
        manager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());
        manager.setSolicitacaoManager((SolicitacaoManager) solicitacaoManager.proxy());
        manager.setParametrosDoSistemaManager((ParametrosDoSistemaManager) parametrosDoSistemaManager.proxy());
        manager.setBairroManager((BairroManager) bairroManager.proxy());
        manager.setCandidatoCurriculoManager((CandidatoCurriculoManager) candidatoCurriculoManager.proxy());
        manager.setSolicitacaoExameManager((SolicitacaoExameManager) solicitacaoExameManager.proxy());
        manager.setConfiguracaoNivelCompetenciaManager((ConfiguracaoNivelCompetenciaManager) configuracaoNivelCompetenciaManager.proxy());
        manager.setGerenciadorComunicacaoManager((GerenciadorComunicacaoManager) gerenciadorComunicacaoManager.proxy());
        manager.setCidadeManager((CidadeManager) cidadeManager.proxy());
        manager.setConfiguracaoCampoExtraManager((ConfiguracaoCampoExtraManager) configuracaoCampoExtraManager.proxy());
        manager.setCamposExtrasManager((CamposExtrasManager) camposExtrasManager.proxy());
        manager.setHistoricoCandidatoManager((HistoricoCandidatoManager) historicoCandidatoManager.proxy());
		
        colaboradorQuestionarioManager = new Mock(ColaboradorQuestionarioManager.class);
        colaboradorManager = new Mock(ColaboradorManager.class);
        
        Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
        Mockit.redefineMethods(ArquivoUtil.class, MockArquivoUtil.class);
    }

    public void testSetBlackList() throws Exception
    {
    	boolean blackList = true;
    	HistoricoCandidato historicoCandidato = new HistoricoCandidato();
    	historicoCandidato.setApto(Apto.NAO);
    	historicoCandidato.setObservacao("observacao");

    	Candidato candidato = new Candidato();
    	candidato.setId(2L);

    	CandidatoSolicitacao candidatoSolicitacao = new CandidatoSolicitacao();
    	candidatoSolicitacao.setId(1L);
    	candidatoSolicitacao.setCandidato(candidato);

    	candidatoSolicitacaoManager.expects(once()).method("findCandidatoSolicitacaoById").with(eq(candidatoSolicitacao.getId())).will(returnValue(candidatoSolicitacao));
    	candidatoDao.expects(once()).method("updateBlackList").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING});

    	manager.setBlackList(historicoCandidato, candidatoSolicitacao.getId(), blackList);
    }

    public void testSetBlackListVariosCandidatos() throws Exception
    {
    	boolean blackList = true;
    	HistoricoCandidato historicoCandidato = new HistoricoCandidato();
    	historicoCandidato.setApto(Apto.NAO);
    	historicoCandidato.setObservacao("observacao");

    	Candidato candidato = new Candidato();
    	candidato.setId(2L);

    	Candidato candidato2 = new Candidato();
    	candidato2.setId(3L);

    	CandidatoSolicitacao candidatoSolicitacao = new CandidatoSolicitacao();
    	candidatoSolicitacao.setId(1L);
    	candidatoSolicitacao.setCandidato(candidato);

    	CandidatoSolicitacao candidatoSolicitacao2 = new CandidatoSolicitacao();
    	candidatoSolicitacao2.setId(2L);
    	candidatoSolicitacao2.setCandidato(candidato2);

    	Collection<CandidatoSolicitacao> candidatoSolicitacaos = new ArrayList<CandidatoSolicitacao>();
    	candidatoSolicitacaos.add(candidatoSolicitacao);
    	candidatoSolicitacaos.add(candidatoSolicitacao2);

    	String [] idsCandidatos = new String[2];
    	idsCandidatos[0] = candidatoSolicitacao.getId().toString();
    	idsCandidatos[1] = candidatoSolicitacao2.getId().toString();

    	Long [] idsCandidato = new Long[2];
    	idsCandidato[0] = candidatoSolicitacao.getId();
    	idsCandidato[1] = candidatoSolicitacao2.getId();

    	candidatoSolicitacaoManager.expects(once()).method("findCandidatoSolicitacaoById").with(eq(idsCandidato)).will(returnValue(candidatoSolicitacaos));
    	candidatoDao.expects(once()).method("updateBlackList").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING});

    	manager.setBlackList(historicoCandidato, idsCandidatos, blackList);
    }
    
    public void testEnviaEmailQtdCurriculosCadastrados() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	Collection<Empresa> empresas = new ArrayList<Empresa>();
    	empresas.add(empresa);
    	
    	Candidato candidato1 = new Candidato("Casa Pio", OrigemCandidato.CADASTRADO, 10);
    	Candidato candidato2 = new Candidato("Casa Pio", OrigemCandidato.F2RH, 5);
    	Candidato candidato3 = new Candidato("Havaianas", OrigemCandidato.F2RH, 9);
    	
    	candidato1.setEmpresa(empresa);
    	candidato2.setEmpresa(empresa);
    	candidato3.setEmpresa(empresa);
    	
    	Collection<Candidato> candidatos = new ArrayList<Candidato>();
    	candidatos.add(candidato1);
    	candidatos.add(candidato2);
    	candidatos.add(candidato3);
    	
    	candidatoDao.expects(once()).method("findQtdCadastradosByOrigem").with(new Constraint[]{ANYTHING,ANYTHING}).will(returnValue(candidatos));
    	gerenciadorComunicacaoManager.expects(once()).method("enviaEmailQtdCurriculosCadastrados").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING}).isVoid();
    	
    	Exception exc = null;
    	try {
    		manager.enviaEmailQtdCurriculosCadastrados(empresas);
			
		} catch (Exception e) {
			exc = e;
		}
    	
		assertNull(exc);
    }

    public void testFindCandidatosById() throws Exception
    {
    	Long[] ids = new Long[1];
    	ids[0] = 1L;

    	Candidato candidato = new Candidato();
    	candidato.setId(2L);

    	Collection<Candidato> candidatos = new ArrayList<Candidato>();
    	candidatos.add(candidato);

    	candidatoDao.expects(once()).method("findCandidatosById").with(eq(ids)).will(returnValue(candidatos));

    	Collection<Candidato> retorno = manager.findCandidatosById(ids);

    	assertEquals(candidatos.size(), retorno.size());
    }

    public void testFindByCPF() throws Exception
    {
    	Long[] ids = new Long[1];
    	ids[0] = 1L;

    	Candidato candidato = new Candidato();
    	candidato.setId(2L);
    	candidato.setCpf("cpf");
    	
    	Collection<Candidato> candidatos = new ArrayList<Candidato>();
    	candidatos.add(candidato);

    	candidatoDao.expects(once()).method("findByCPF").with(new Constraint[] { eq("cpf"), ANYTHING, ANYTHING, ANYTHING } ).will(returnValue(candidatos));

    	Candidato retorno = manager.findByCPF("cpf", null);

    	assertEquals(candidato.getId(), retorno.getId());
    }

    public void testFindByCandidatoId() throws Exception
    {
    	Long[] ids = new Long[1];
    	ids[0] = 1L;

    	Candidato candidato = new Candidato();
    	candidato.setId(2L);

    	candidatoDao.expects(once()).method("findByCandidatoId").with(eq(candidato.getId())).will(returnValue(candidato));

    	Candidato retorno = manager.findByCandidatoId(candidato.getId());

    	assertEquals(candidato.getId(), retorno.getId());
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
	public void testBusca() throws Exception
    {
    	Cargo cargo = new Cargo();
    	cargo.setId(1L);

    	Cargo cargo2 = new Cargo();
    	cargo2.setId(2L);

    	Experiencia experiencia = new Experiencia();
    	experiencia.setId(1L);
    	experiencia.setDataAdmissao(DateUtil.criarAnoMesDia(01, 01, 01));
    	experiencia.setDataDesligamento(DateUtil.criarAnoMesDia(03, 01, 01));
    	experiencia.setCargo(cargo);

    	Experiencia experiencia2 = new Experiencia();
    	experiencia2.setId(2L);
    	experiencia2.setDataAdmissao(DateUtil.criarAnoMesDia(01, 01, 01));
    	experiencia2.setDataDesligamento(DateUtil.criarAnoMesDia(01, 06, 01));
    	experiencia2.setCargo(cargo);

    	Experiencia experiencia3 = new Experiencia();
    	experiencia3.setId(3L);
    	experiencia3.setDataAdmissao(DateUtil.criarAnoMesDia(01, 01, 01));
    	experiencia3.setDataDesligamento(DateUtil.criarAnoMesDia(02, 06, 01));
    	experiencia3.setCargo(cargo2);

    	Collection<Experiencia> experiencias = new ArrayList<Experiencia>();
    	experiencias.add(experiencia);

    	Collection<Experiencia> experiencias2 = new ArrayList<Experiencia>();
    	experiencias.add(experiencia2);

    	Collection<Experiencia> experiencias3 = new ArrayList<Experiencia>();
    	experiencias.add(experiencia3);

    	Candidato candidato = new Candidato();
    	candidato.setId(2L);
    	candidato.setExperiencias(experiencias);

    	Candidato candidato2 = new Candidato();
    	candidato2.setId(3L);
    	candidato2.setExperiencias(experiencias2);

    	Candidato candidato3 = new Candidato();
    	candidato3.setId(4L);
    	candidato3.setExperiencias(experiencias3);

    	Collection<Candidato> candidatos = new ArrayList<Candidato>();
    	candidatos.add(candidato);
    	candidatos.add(candidato2);
    	candidatos.add(candidato3);

		long empresa = 1L;
		Map parametros = new HashMap();

		parametros.put("experiencias", new Long[]{cargo.getId(),cargo2.getId(),876587L});
		parametros.put("tempoExperiencia", "6");

		String[] cargosIds = new String[2];
		cargosIds[0] = cargo.getId().toString();
		cargosIds[1] = "6546";

		parametros.put("cargosIds", cargosIds);
		parametros.put("bairrosIds", new Long[]{1L});

		Collection<Candidato> retorno = new ArrayList<Candidato>();

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		bairroManager.expects(once()).method("getBairrosByIds").with(ANYTHING).will(returnValue(new ArrayList<Bairro>()));
		candidatoDao.expects(once()).method("getCandidatosByExperiencia").with(ANYTHING,ANYTHING).will(returnValue(candidatos));

		candidatoSolicitacaoManager.expects(once()).method("getCandidatosBySolicitacao").with(ANYTHING).will(returnValue(idsCandidatos));

		candidatoDao.expects(once()).method("findBusca").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,eq(false),ANYTHING,ANYTHING}).will(returnValue(candidatos));

		retorno = manager.busca(parametros, 1L, false, null, null, empresa);

    	assertEquals(3, retorno.size());

    	candidatoSolicitacaoManager.expects(once()).method("getCandidatosBySolicitacao").with(ANYTHING).will(returnValue(idsCandidatos));
    	bairroManager.expects(once()).method("getBairrosByIds").with(ANYTHING).will(returnValue(new ArrayList<Bairro>()));
    	candidatoDao.expects(once()).method("getCandidatosByExperiencia").with(ANYTHING,ANYTHING).will(returnValue(candidatos));
    	candidatoDao.expects(once()).method("findBusca").withAnyArguments();

    	parametros.put("experiencias", new Long[]{876587L});

    	retorno = manager.busca(parametros, 1L, false, null, null, empresa);

    	assertNull(retorno);
    }

    public void testList() throws Exception
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(654654L);

    	Candidato candidato = new Candidato();
    	candidato.setId(2L);
    	candidato.setEmpresa(empresa);

    	Candidato candidato2 = new Candidato();
    	candidato2.setId(3L);
    	candidato2.setEmpresa(empresa);

    	Candidato candidato3 = new Candidato();
    	candidato3.setId(4L);
    	candidato3.setEmpresa(empresa);

    	Collection<Candidato> candidatos = new ArrayList<Candidato>();
    	candidatos.add(candidato);
    	candidatos.add(candidato2);
    	candidatos.add(candidato3);

    	candidatoDao.expects(once()).method("find").withAnyArguments().will(returnValue(candidatos));

    	int page = 1;
		int pagingSize = 1;
		String nomeBusca = "";
		String cpfBusca = "";
		Long empresaId = empresa.getId();

		Collection<Candidato> retorno = manager.list(page, pagingSize, null, empresaId);

    	assertEquals(3, retorno.size());
    }

    public void testGetCount() throws Exception
    {
    	candidatoDao.expects(once()).method("getCount").withAnyArguments().will(returnValue(3));

    	String nomeBusca = "";
    	String cpfBusca = "";
    	Long empresaId = 1L;

    	Integer retorno = manager.getCount(null, empresaId);

    	assertTrue(retorno == 3);
    }

	public void testGetFoto() throws Exception
	{
		Long id = 1L;
		File foto = new File();
		foto.setName("foto");

		candidatoDao.expects(once()).method("getFile").with(eq("foto"),eq(id)).will(returnValue(foto));

		File fotoRetorno = manager.getFoto(id);

		assertEquals(foto.getName(), fotoRetorno.getName());
	}

	public void testRemoveCandidato() throws Exception
	{
		Candidato candidato = CandidatoFactory.getCandidato();

		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao.setCandidato(candidato);

		MockSpringUtil.mocks.put("colaboradorQuestionarioManager", colaboradorQuestionarioManager);
		MockSpringUtil.mocks.put("colaboradorManager", colaboradorManager);
		
		candidatoSolicitacaoManager.expects(once()).method("removeCandidato").with(eq(candidato.getId())).isVoid();
		colaboradorManager.expects(once()).method("setCandidatoNull").with(eq(candidato.getId())).isVoid();
		colaboradorQuestionarioManager.expects(once()).method("removeByCandidato").with(eq(candidato.getId())).isVoid();
		solicitacaoExameManager.expects(once()).method("removeByCandidato").with(eq(candidato.getId())).isVoid();
		configuracaoNivelCompetenciaManager.expects(once()).method("removeByCandidato").with(eq(candidato.getId())).isVoid();
		formacaoManager.expects(once()).method("removeCandidato").with(eq(candidato));
		experienciaManager.expects(once()).method("removeCandidato").with(eq(candidato));
		candidatoIdiomaManager.expects(once()).method("removeCandidato").with(eq(candidato));
		candidatoCurriculoManager.expects(once()).method("removeCandidato").with(eq(candidato));
		candidatoDao.expects(once()).method("removeAreaInteresseConhecimentoCargo").with(eq(candidato.getId()));
		candidatoDao.expects(once()).method("remove").with(eq(candidato.getId()));

		Exception exception = null;

		try
		{
			manager.removeCandidato(candidato);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);

	}

	public void testRemoveCandidatoException() throws Exception
	{
		Candidato candidato = CandidatoFactory.getCandidato();

		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao.setCandidato(candidato);

		MockSpringUtil.mocks.put("colaboradorQuestionarioManager", colaboradorQuestionarioManager);
		MockSpringUtil.mocks.put("colaboradorManager", colaboradorManager);
		
		candidatoSolicitacaoManager.expects(once()).method("removeCandidato").with(eq(candidato.getId())).isVoid();
		colaboradorManager.expects(once()).method("setCandidatoNull").with(eq(candidato.getId())).isVoid();
		colaboradorQuestionarioManager.expects(once()).method("removeByCandidato").with(eq(candidato.getId())).isVoid();
		solicitacaoExameManager.expects(once()).method("removeByCandidato").with(eq(candidato.getId())).isVoid();
		configuracaoNivelCompetenciaManager.expects(once()).method("removeByCandidato").with(eq(candidato.getId())).isVoid();
		formacaoManager.expects(once()).method("removeCandidato").with(eq(candidato));
		experienciaManager.expects(once()).method("removeCandidato").with(eq(candidato));
		candidatoIdiomaManager.expects(once()).method("removeCandidato").with(eq(candidato));
		candidatoCurriculoManager.expects(once()).method("removeCandidato").with(eq(candidato)).will(throwException(new Exception("erro")));

		Exception exception = null;

		try
		{
			manager.removeCandidato(candidato);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);

	}

	public void testUpdateSenha() throws Exception
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		candidato.setId(1L);
		candidato.setSenha("senha");
		candidato.setNovaSenha("novaSenha");
		candidato.setConfNovaSenha("novaSenha");

		candidatoDao.expects(once()).method("updateSenha").with(eq(candidato.getId()), eq(StringUtil.encodeString(candidato.getSenha())), eq(StringUtil.encodeString(candidato.getNovaSenha())));

		manager.updateSenha(candidato);
	}

	public void testExportaCandidatosBDS() throws Throwable
	{
		Empresa empresa = new Empresa();
		Candidato candidato = CandidatoFactory.getCandidato();
		Collection<Candidato> candidatos = new ArrayList<Candidato>();
		candidatos.add(candidato);
		String[] empresasCheck = new String[1];
		empresasCheck[0] = "1";
		String emailAvulso = "email";
		String assunto = "assunto";

		mail.expects(once()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		anuncioManager.expects(once()).method("montaEmails").with(new Constraint[]{ANYTHING,ANYTHING});
		boolean exporta = manager.exportaCandidatosBDS(empresa, candidatos, empresasCheck, emailAvulso, assunto);

		assertTrue(exporta);

		anuncioManager = null;

		Throwable exp = null;

		try{
			exporta = manager.exportaCandidatosBDS(empresa, candidatos, empresasCheck, emailAvulso, assunto);
		}catch(Throwable e){
			exp = e;
		}

		assertNotNull(exp);
	}

	public void testPopulaCandidatos() throws Exception
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		Candidato c1 = (Candidato) candidato.clone();
		c1.setId(1L);
		c1.getEndereco().setUf(new Estado());
		c1.getEndereco().setCidade(new Cidade());

		Candidato c2 = (Candidato) candidato.clone();
		c2.setId(2L);

		Candidato c3 = (Candidato) candidato.clone();
		c3.setId(3L);

		Collection<Candidato> candidatos = new ArrayList<Candidato>();
		candidatos.add(c1);
		candidatos.add(c2);
		candidatos.add(c3);

		Cargo cargo1 = new Cargo();
		cargo1.setNomeMercado("cargo");

		Cargo cargo2 = new Cargo();
		cargo2.setNomeMercado("cargo2");

		Cargo cargo3 = new Cargo();
		cargo3.setNomeMercado("cargo3");

		Experiencia e1 = new Experiencia();
		e1.setId(1L);
		e1.setCandidato(c1);
		e1.setCargo(cargo1);

		Experiencia e2 = new Experiencia();
		e2.setId(2L);

		Experiencia e3 = new Experiencia();
		e3.setId(3L);
		e3.setCandidato(c3);
		e3.setCargo(cargo3);

		Collection<Experiencia> experiencias = new ArrayList<Experiencia>();
		experiencias.add(e1);
		experiencias.add(e2);
		experiencias.add(e3);

		Formacao f1 = new Formacao();
		f1.setId(1L);
		f1.setCandidato(c1);

		Formacao f2 = new Formacao();
		f2.setId(2L);
		f2.setCandidato(c2);

		Formacao f3 = new Formacao();
		f3.setId(3L);
		f3.setCandidato(c3);

		Collection<Formacao> formacaos = new ArrayList<Formacao>();
		formacaos.add(f1);
		formacaos.add(f2);
		formacaos.add(f3);

		CandidatoIdioma ci1 = new CandidatoIdioma();
		ci1.setId(1L);
		ci1.setCandidato(c1);

		CandidatoIdioma ci2 = new CandidatoIdioma();
		ci2.setId(2L);
		ci2.setCandidato(c2);

		CandidatoIdioma ci3 = new CandidatoIdioma();
		ci3.setId(3L);
		ci3.setCandidato(c3);

		Collection<CandidatoIdioma> candidatoIdiomas = new ArrayList<CandidatoIdioma>();
		candidatoIdiomas.add(ci1);
		candidatoIdiomas.add(ci2);
		candidatoIdiomas.add(ci3);

		List<String> conhecimentos = new ArrayList<String>();
		conhecimentos.add("conhecimento1");
		conhecimentos.add("conhecimento2");
		conhecimentos.add("conhecimento3");

		experienciaManager.expects(once()).method("findInCandidatos").with(new Constraint[]{ANYTHING}).will(returnValue(experiencias));
		formacaoManager.expects(once()).method("findInCandidatos").with(new Constraint[]{ANYTHING}).will(returnValue(formacaos));
		candidatoIdiomaManager.expects(once()).method("findInCandidatos").with(new Constraint[]{ANYTHING}).will(returnValue(candidatoIdiomas));

		candidatoDao.expects(atLeastOnce()).method("getConhecimentosByCandidatoId").with(new Constraint[]{ANYTHING}).will(returnValue(conhecimentos));

		Collection<Candidato> retorno = manager.populaCandidatos(candidatos);

		assertEquals(candidatos.size(), retorno.size());

		Collection<Candidato> candidatosNull = new ArrayList<Candidato>();

		retorno = manager.populaCandidatos(candidatosNull);

		assertEquals(0, retorno.size());
	}

	public void testImportaBDS() throws Exception
	{
		java.io.File xmlFile = null;
		java.io.File zipFile = null;

		Empresa empresa = new Empresa();
		empresa.setId(1L);

		Candidato candidato = CandidatoFactory.getCandidato();
		candidato.setId(1L);
		candidato.setEmpresa(empresa);
		candidato.setDataAtualizacao(DateUtil.criarDataMesAno(01, 01, 2007));

		Collection<Candidato> candidatos = new ArrayList<Candidato>();
		candidatos.add(candidato);

		Candidato candidato2 = CandidatoFactory.getCandidato();
		candidato2.setId(2L);
		candidato2.setEmpresa(empresa);
		candidato2.setDataAtualizacao(DateUtil.criarDataMesAno(01, 01, 2008));

		Collection<Candidato> candidatos2 = new ArrayList<Candidato>();
		candidatos2.add(candidato2);

		Cargo cargo = CargoFactory.getEntity();

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();

		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setId(1L);
		solicitacao.setAreaOrganizacional(areaOrganizacional);
		solicitacao.setFaixaSalarial(faixaSalarial);
		solicitacao.setEmpresa(empresa);

		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao.setCandidato(candidato);
		candidatoSolicitacao.setSolicitacao(solicitacao);

		Collection<CandidatoSolicitacao> candidatoSolicitacaos = new ArrayList<CandidatoSolicitacao>();
		candidatoSolicitacaos.add(candidatoSolicitacao);

		String fileName = "candidato" + Calendar.getInstance().getTimeInMillis() + ".xml";
		xmlFile = new java.io.File(fileName);
		FileOutputStream outputStream = new FileOutputStream(xmlFile);
		String encoding = "UTF-8";
		XStream stream = new XStream(new DomDriver(encoding));

		outputStream.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n".getBytes());
		stream.toXML(candidatos, outputStream);
		outputStream.flush();
		outputStream.close();

		ZipOutputStream zipOutputStream = new Zip().compress(new java.io.File[] { xmlFile }, fileName, ".fortesrh", true);// cria o arquivo candidatos.zip
		zipOutputStream.close();

		zipFile = new java.io.File(fileName + ".fortesrh");


		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		candidatoDao.expects(once()).method("getCandidatosByCpf").with(new Constraint[]{ANYTHING,ANYTHING}).will(returnValue(candidatos));
		candidatoSolicitacaoManager.expects(once()).method("getCandidatosBySolicitacao").with(new Constraint[]{ANYTHING,ANYTHING}).will(returnValue(candidatoSolicitacaos));
		candidatoDao.expects(once()).method("findById").with(new Constraint[]{ANYTHING}).will(returnValue(candidato));
		transactionManager.expects(once()).method("commit").with(ANYTHING);
		solicitacaoManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(solicitacao));

		manager.importaBDS(zipFile, solicitacao);


		Exception exp = null;
		try
		{
			manager.importaBDS(zipFile, solicitacao);
		}catch (Exception e) {
			exp = e;
		}

		assertNotNull(exp);
	}

	public void testImportaBDSCandidatoSemId() throws Exception
	{
		java.io.File xmlFile = null;
		java.io.File zipFile = null;

		Empresa empresa = new Empresa();
		empresa.setId(1L);

		Candidato candidato = CandidatoFactory.getCandidato();
		candidato.setId(null);
		candidato.setEmpresa(empresa);
		candidato.setDataAtualizacao(DateUtil.criarDataMesAno(01, 01, 2007));
		candidato.getPessoal().setCpf("15826543487");
		Collection<Candidato> candidatos = new ArrayList<Candidato>();
		candidatos.add(candidato);

		Cargo cargo = CargoFactory.getEntity();

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();

		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setId(1L);
		solicitacao.setAreaOrganizacional(areaOrganizacional);
		solicitacao.setFaixaSalarial(faixaSalarial);
		solicitacao.setEmpresa(empresa);

		String fileName = "candidato" + Calendar.getInstance().getTimeInMillis() + ".xml";
		xmlFile = new java.io.File(fileName);
		FileOutputStream outputStream = new FileOutputStream(xmlFile);
		String encoding = "UTF-8";
		XStream stream = new XStream(new DomDriver(encoding));

		outputStream.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n".getBytes());
		stream.toXML(candidatos, outputStream);
		outputStream.flush();
		outputStream.close();

		ZipOutputStream zipOutputStream = new Zip().compress(new java.io.File[] { xmlFile }, fileName, ".fortesrh", true);// cria o arquivo candidatos.zip
		zipOutputStream.close();

		zipFile = new java.io.File(fileName + ".fortesrh");

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		candidatoDao.expects(once()).method("getCandidatosByCpf").with(new Constraint[]{ANYTHING,ANYTHING}).will(returnValue(new ArrayList<Candidato>()));
		candidatoSolicitacaoManager.expects(once()).method("getCandidatosBySolicitacao").with(new Constraint[]{ANYTHING,ANYTHING}).will(returnValue(new ArrayList<Candidato>()));
		transactionManager.expects(once()).method("commit").with(ANYTHING);
		solicitacaoManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(solicitacao));
		candidatoDao.expects(once()).method("save").with(new Constraint[]{ANYTHING}).will(returnValue(null));
		candidatoIdiomaManager.expects(once()).method("montaIdiomasBDS").with(new Constraint[]{ANYTHING,ANYTHING});
		formacaoManager.expects(once()).method("montaFormacaosBDS").with(new Constraint[]{ANYTHING,ANYTHING});
		experienciaManager.expects(once()).method("montaExperienciasBDS").with(new Constraint[]{ANYTHING,ANYTHING});
		candidatoSolicitacaoManager.expects(once()).method("save").with(new Constraint[]{ANYTHING});

		manager.importaBDS(zipFile, solicitacao);
	}

	public void testImportaBDSDadosCandidatoRecente() throws Exception
	{

		java.io.File xmlFile = null;
		java.io.File zipFile = null;

		Empresa empresa = new Empresa();
		empresa.setId(1L);

		Candidato candidato = CandidatoFactory.getCandidato();
		candidato.setId(1L);
		candidato.setEmpresa(empresa);
		candidato.setDataAtualizacao(DateUtil.criarDataMesAno(01, 01, 2007));

		Collection<Candidato> candidatos = new ArrayList<Candidato>();
		candidatos.add(candidato);

		Candidato candidato2 = CandidatoFactory.getCandidato();
		candidato2.setEmpresa(empresa);
		candidato2.setDataAtualizacao(DateUtil.criarDataMesAno(01, 01, 2008));
		candidato2.setId(323L);

		Collection<Candidato> candidatos2 = new ArrayList<Candidato>();
		candidatos2.add(candidato2);

		Cargo cargo = new Cargo();
		cargo.setId(1L);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();

		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setId(1L);
		solicitacao.setAreaOrganizacional(areaOrganizacional);
		solicitacao.setFaixaSalarial(faixaSalarial);
		solicitacao.setEmpresa(empresa);


		solicitacao.getFaixaSalarial().setCargo(cargo);

		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao.setCandidato(candidato2);
		candidatoSolicitacao.setSolicitacao(solicitacao);

		Collection<CandidatoSolicitacao> candidatoSolicitacaos = new ArrayList<CandidatoSolicitacao>();
		candidatoSolicitacaos.add(candidatoSolicitacao);

		String fileName = "candidato" + Calendar.getInstance().getTimeInMillis() + ".xml";
		xmlFile = new java.io.File(fileName);
		FileOutputStream outputStream = new FileOutputStream(xmlFile);
		String encoding = "UTF-8";
		XStream stream = new XStream(new DomDriver(encoding));

		outputStream.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n".getBytes());
		stream.toXML(candidatos2, outputStream);
		outputStream.flush();
		outputStream.close();

		ZipOutputStream zipOutputStream = new Zip().compress(new java.io.File[] { xmlFile }, fileName, ".fortesrh", true);// cria o arquivo candidatos.zip
		zipOutputStream.close();

		zipFile = new java.io.File(fileName + ".fortesrh");

		candidatoDao.expects(once()).method("getCandidatosByCpf").with(new Constraint[]{ANYTHING,ANYTHING}).will(returnValue(candidatos2));
		candidatoSolicitacaoManager.expects(once()).method("getCandidatosBySolicitacao").with(new Constraint[]{ANYTHING,ANYTHING}).will(returnValue(candidatoSolicitacaos));
		candidatoDao.expects(once()).method("findById").with(new Constraint[]{ANYTHING}).will(returnValue(candidato));
		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		transactionManager.expects(once()).method("commit").with(ANYTHING);
		solicitacaoManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(solicitacao));

		manager.importaBDS(zipFile, solicitacao);
	}

	public void testImportaBDSUpdateCandidato() throws Exception
	{
		java.io.File xmlFile = null;
		java.io.File zipFile = null;

		Empresa empresa = new Empresa();
		empresa.setId(1L);

		Candidato candidato = CandidatoFactory.getCandidato();
		candidato.setEmpresa(empresa);
		candidato.setDataAtualizacao(DateUtil.criarDataMesAno(01, 01, 2007));
		candidato.setCpf("5468465");

		Collection<Candidato> candidatos = new ArrayList<Candidato>();
		candidatos.add(candidato);

		Candidato candidato2 = CandidatoFactory.getCandidato();
		candidato2.setEmpresa(empresa);
		candidato2.setDataAtualizacao(DateUtil.criarDataMesAno(01, 01, 2008));
		candidato2.setId(323L);
		candidato2.setCpf("4546546542");

		Candidato candidato3 = CandidatoFactory.getCandidato();
		candidato3.setEmpresa(empresa);
		candidato3.setDataAtualizacao(DateUtil.criarDataMesAno(01, 01, 2008));
		candidato3.setId(65465L);

		Collection<Candidato> candidatos2 = new ArrayList<Candidato>();
		candidatos2.add(candidato2);

		Cargo cargo = CargoFactory.getEntity();

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();

		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setId(1L);
		solicitacao.setAreaOrganizacional(areaOrganizacional);
		solicitacao.setFaixaSalarial(faixaSalarial);
		solicitacao.setEmpresa(empresa);

		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao.setCandidato(candidato3);
		candidatoSolicitacao.setSolicitacao(solicitacao);

		Collection<CandidatoSolicitacao> candidatoSolicitacaos = new ArrayList<CandidatoSolicitacao>();
		candidatoSolicitacaos.add(candidatoSolicitacao);

		String fileName = "candidato" + Calendar.getInstance().getTimeInMillis() + ".xml";
		xmlFile = new java.io.File(fileName);
		FileOutputStream outputStream = new FileOutputStream(xmlFile);
		String encoding = "UTF-8";
		XStream stream = new XStream(new DomDriver(encoding));

		outputStream.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n".getBytes());
		stream.toXML(candidatos2, outputStream);
		outputStream.flush();
		outputStream.close();

		ZipOutputStream zipOutputStream = new Zip().compress(new java.io.File[] { xmlFile }, fileName, ".fortesrh", true);// cria o arquivo candidatos.zip
		zipOutputStream.close();

		zipFile = new java.io.File(fileName + ".fortesrh");

		candidatoDao.expects(once()).method("getCandidatosByCpf").with(new Constraint[]{ANYTHING,ANYTHING}).will(returnValue(candidatos));
		candidatoSolicitacaoManager.expects(once()).method("getCandidatosBySolicitacao").with(new Constraint[]{ANYTHING,ANYTHING}).will(returnValue(candidatoSolicitacaos));
		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		transactionManager.expects(once()).method("commit").with(ANYTHING);
		solicitacaoManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(solicitacao));
		candidatoDao.expects(once()).method("update").with(new Constraint[]{ANYTHING});
		candidatoIdiomaManager.expects(once()).method("montaIdiomasBDS").with(new Constraint[]{ANYTHING,ANYTHING});
		formacaoManager.expects(once()).method("montaFormacaosBDS").with(new Constraint[]{ANYTHING,ANYTHING});
		experienciaManager.expects(once()).method("montaExperienciasBDS").with(new Constraint[]{ANYTHING,ANYTHING});
		candidatoSolicitacaoManager.expects(once()).method("save").with(new Constraint[]{ANYTHING});

		manager.importaBDS(zipFile, solicitacao);
	}

	public void testImportaBDSRollbackAoSalvar() throws Exception
	{
		java.io.File xmlFile = null;
		java.io.File zipFile = null;

		Empresa empresa = new Empresa();
		empresa.setId(1L);

		Candidato candidato = CandidatoFactory.getCandidato();
		candidato.setEmpresa(empresa);
		candidato.setDataAtualizacao(DateUtil.criarDataMesAno(01, 01, 2007));

		Collection<Candidato> candidatos = new ArrayList<Candidato>();
		candidatos.add(candidato);

		Candidato candidato2 = CandidatoFactory.getCandidato();
		candidato2.setEmpresa(empresa);
		candidato2.setDataAtualizacao(DateUtil.criarDataMesAno(01, 01, 2008));
		candidato2.setId(323L);

		Collection<Candidato> candidatos2 = new ArrayList<Candidato>();
		candidatos2.add(candidato2);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setId(1L);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();

		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setId(1L);
		solicitacao.setAreaOrganizacional(areaOrganizacional);
		solicitacao.setFaixaSalarial(faixaSalarial);
		solicitacao.setEmpresa(empresa);

		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao.setCandidato(candidato2);
		candidatoSolicitacao.setSolicitacao(solicitacao);

		Collection<CandidatoSolicitacao> candidatoSolicitacaos = new ArrayList<CandidatoSolicitacao>();
		candidatoSolicitacaos.add(candidatoSolicitacao);

		String fileName = "candidato" + Calendar.getInstance().getTimeInMillis() + ".xml";
		xmlFile = new java.io.File(fileName);
		FileOutputStream outputStream = new FileOutputStream(xmlFile);
		String encoding = "UTF-8";
		XStream stream = new XStream(new DomDriver(encoding));

		outputStream.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n".getBytes());
		stream.toXML(candidatos2, outputStream);
		outputStream.flush();
		outputStream.close();

		ZipOutputStream zipOutputStream = new Zip().compress(new java.io.File[] { xmlFile }, fileName, ".fortesrh", true);// cria o arquivo candidatos.zip
		zipOutputStream.close();

		zipFile = new java.io.File(fileName + ".fortesrh");

		Exception exception = null;
		try
		{
			candidatoDao.expects(once()).method("getCandidatosByCpf").with(new Constraint[]{ANYTHING,ANYTHING}).will(returnValue(candidatos2));
			candidatoSolicitacaoManager.expects(once()).method("getCandidatosBySolicitacao").with(new Constraint[]{ANYTHING,ANYTHING}).will(returnValue(candidatoSolicitacaos));
			candidatoDao.expects(once()).method("findById").with(new Constraint[]{ANYTHING}).will(returnValue(candidato));
			transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
			transactionManager.expects(once()).method("rollback").with(ANYTHING);
			manager.setSolicitacaoManager(null);

			manager.importaBDS(zipFile, solicitacao);
		}catch (Exception e) {
			exception = e;
		}
		assertNotNull(exception);
	}

    public void testSaveOrUpdateCandidatoByColaborador()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	
    	Idioma i = new Idioma();
    	i.setId(1L);

    	ColaboradorIdioma ci = new ColaboradorIdioma();
    	ci.setId(1L);
    	ci.setIdioma(i);

    	Collection<ColaboradorIdioma> colaboradorIdiomas = new ArrayList<ColaboradorIdioma>();
    	colaboradorIdiomas.add(ci);

    	CandidatoIdioma candIdioma = new CandidatoIdioma();
    	candIdioma.setId(1L);
    	candIdioma.setIdioma(i);

    	Collection<CandidatoIdioma> candIdiomas = new ArrayList<CandidatoIdioma>();
    	candIdiomas.add(candIdioma);

    	Contato contato = new Contato();
    	contato.setDdd("85");
    	contato.setFoneFixo("22222222");
    	contato.setEmail("mail@grupofortes.com.br");

    	Endereco e = new Endereco();
    	e.setBairro("Bairro");
    	e.setCep("123456789");

    	Experiencia ex = new Experiencia();
    	ex.setId(1L);
    	ex.setCargo(new Cargo());

    	Collection<Experiencia> experiencias = new ArrayList<Experiencia>();
    	experiencias.add(ex);

    	Formacao formacao = new Formacao();
    	formacao.setId(1L);

    	Collection<Formacao> formacoes = new ArrayList<Formacao>();
    	formacoes.add(formacao);

    	ConfiguracaoCampoExtra configuracaoCampoExtraCampoDeTexto1 = ConfiguracaoCampoExtraFactory.getEntity(1L, CampoExtra.CAMPO_DE_TEXTO_1.getDescricao(), true, true);
    	
    	ConfiguracaoCampoExtra configuracaoCampoExtraCampoDeData1 = ConfiguracaoCampoExtraFactory.getEntity(9L, CampoExtra.CAMPO_DE_DATA_1.getDescricao(), true, false);
    	ConfiguracaoCampoExtra configuracaoCampoExtraCampoDeData2 = ConfiguracaoCampoExtraFactory.getEntity(10L, CampoExtra.CAMPO_DE_DATA_2.getDescricao(), false, true);
    	
    	
    	Collection<ConfiguracaoCampoExtra> configuracaoCamposExtras = Arrays.asList(configuracaoCampoExtraCampoDeData1, configuracaoCampoExtraCampoDeData2, configuracaoCampoExtraCampoDeTexto1); 
    	CamposExtras camposExtrasColaborador = new CamposExtras();
    	camposExtrasColaborador.setTexto1("Texto 1");
    	camposExtrasColaborador.setData1(DateUtil.criarAnoMesDia(2015, 01, 01));
    	
    	Pessoal pessoal = new Pessoal();
    	pessoal.setCpf("123456789");

    	Colaborador c = new Colaborador();
    	c.setId(1L);
    	c.setColaboradorIdiomas(colaboradorIdiomas);
    	c.setContato(contato);
    	c.setCursos("cursos");
    	c.setEndereco(e);
    	c.setExperiencias(experiencias);
    	c.setFormacao(formacoes);
    	c.setNome("nome");
    	c.setPessoal(pessoal);
    	c.setCamposExtras(camposExtrasColaborador);
    	c.setEmpresa(empresa);
	
		Candidato candidato = new Candidato();
		candidato.setId(1L);
	
		candidato.setPessoal(c.getPessoal());
	
		candidato.setContato(contato);
	
	   	SocioEconomica socioEconomica = new SocioEconomica();
	   	candidato.setSocioEconomica(socioEconomica);
	
	   	candidato.setDataAtualizacao(new Date());
	
		candidato.setCursos(c.getCursos());
		candidato.setEndereco(c.getEndereco());
		candidato.setExperiencias(c.getExperiencias());
		candidato.setFormacao(c.getFormacao());
		candidato.setNome(c.getNome());
    	
    	candidatoDao.expects(once()).method("save").with(ANYTHING).will(returnValue(candidato));
    	candidatoDao.expects(once()).method("update").with(ANYTHING);
    	candidatoIdiomaManager.expects(once()).method("montaCandidatoIdiomaByColaboradorIdioma").with(ANYTHING, ANYTHING).will(returnValue(candIdiomas));
    	formacaoManager.expects(once()).method("saveOrUpdate").with(eq(c.getFormacao())).isVoid();
    	configuracaoCampoExtraManager.expects(once()).method("find").with(eq(new String[]{"ativoCandidato","ativoColaborador", "empresa.id"}),eq(new Object[]{true, true, empresa.getId()}), eq(new String[]{"ordem"})).will(returnValue(configuracaoCamposExtras));
    	camposExtrasManager.expects(once()).method("saveOrUpdate").with(ANYTHING).isVoid();

    	candidato = manager.saveOrUpdateCandidatoByColaborador(c);
		
    	formacaoManager.expects(once()).method("saveOrUpdate").with(eq(c.getFormacao())).isVoid();
    	candidatoDao.expects(once()).method("save").with(ANYTHING).will(returnValue(candidato));
    	candidatoDao.expects(once()).method("update").with(ANYTHING);
    	candidatoIdiomaManager.expects(once()).method("montaCandidatoIdiomaByColaboradorIdioma").with(ANYTHING, ANYTHING).will(returnValue(candIdiomas));
    	configuracaoCampoExtraManager.expects(once()).method("find").with(eq(new String[]{"ativoCandidato","ativoColaborador", "empresa.id"}),eq(new Object[]{true, true, empresa.getId()}), eq(new String[]{"ordem"})).will(returnValue(configuracaoCamposExtras));
    	camposExtrasManager.expects(once()).method("saveOrUpdate").with(ANYTHING).isVoid();

    	candidato = manager.saveOrUpdateCandidatoByColaborador(c);

    	assertTrue("Teste 1", candidatoTemIdioma(i, candidato.getCandidatoIdiomas()));
    	assertEquals("Teste 2", "85", candidato.getContato().getDdd());
    	assertEquals("Teste 3", "mail@grupofortes.com.br", candidato.getContato().getEmail());

    	assertEquals("Teste 4", "cursos", candidato.getCursos());
    	assertEquals("Teste 5", e, candidato.getEndereco());
    	assertEquals("Teste 6", experiencias, candidato.getExperiencias());
    	assertEquals("Teste 7", formacoes, candidato.getFormacao());
    	assertEquals("Teste 8", "nome", candidato.getNome());
    	assertEquals("Teste 9", pessoal, candidato.getPessoal());

    	c.setCandidato(candidato);

    	candidatoDao.expects(once()).method("update").with(ANYTHING);
    	candidatoIdiomaManager.expects(once()).method("montaCandidatoIdiomaByColaboradorIdioma").with(ANYTHING, ANYTHING).will(returnValue(candIdiomas));
    	formacaoManager.expects(once()).method("saveOrUpdate").with(eq(c.getFormacao())).isVoid();
    	configuracaoCampoExtraManager.expects(once()).method("find").with(eq(new String[]{"ativoCandidato","ativoColaborador", "empresa.id"}),eq(new Object[]{true, true, empresa.getId()}), eq(new String[]{"ordem"})).will(returnValue(configuracaoCamposExtras));
    	camposExtrasManager.expects(once()).method("saveOrUpdate").with(ANYTHING).isVoid();

    	candidato = manager.saveOrUpdateCandidatoByColaborador(c);
    	
    	assertEquals("Teste 10", c.getCandidato().getId(), candidato.getId());    	
    }

    //método auxiliar
	private boolean candidatoTemIdioma(Idioma i, Collection<CandidatoIdioma> candidatoIdiomas)
	{
		for(CandidatoIdioma ci : candidatoIdiomas)
		{
			if(ci.getIdioma().getId().equals(i.getId()))
			{
				return true;
			}
		}
		return false;
	}

	public void testUpdateSetContratado() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		Candidato candidato = CandidatoFactory.getCandidato();
		candidato.setId(1L);
		candidato.setContratado(true);

		candidatoDao.expects(once()).method("updateSetContratado").with(eq(candidato.getId()), eq(empresa.getId()));

		manager.updateSetContratado(candidato.getId(), empresa.getId());

		assertTrue(candidato.isContratado());
	}


	public void tearDown() throws Exception
	{
		super.tearDown();
	}

	public void testFindByIdProjection() throws Exception
	{
		Candidato candidato = new Candidato();
		candidato.setId(1L);

		candidatoDao.expects(once()).method("findByIdProjection").with(eq(candidato.getId())).will(returnValue(candidato));

		assertEquals(candidato.getId(), (manager.findByIdProjection(candidato.getId())).getId());
	}

	@SuppressWarnings("unchecked")
	public void testFindConhecimentosByCandidatoId()
	{
		Candidato candidato = new Candidato();
		candidato.setId(1L);

		Object[][] arrayConhecimentos = new Object[][]{{1L,"conhecimento1"}, {2L,"conhecimento2"}};
		List retornoBanco = new ArrayList();

		retornoBanco.add(arrayConhecimentos[0]);
		retornoBanco.add(arrayConhecimentos[1]);

		candidatoDao.expects(once()).method("findConhecimentosByCandidatoId").with(eq(candidato.getId())).will(returnValue(retornoBanco));

		Collection<Conhecimento> conhecimentos = manager.findConhecimentosByCandidatoId(candidato.getId());

		Conhecimento conhecimento = (Conhecimento) conhecimentos.toArray()[0];

		assertEquals(2, conhecimentos.size());
		assertEquals(arrayConhecimentos[0][0], conhecimento.getId());
		assertEquals(arrayConhecimentos[0][1], conhecimento.getNome());
	}

	@SuppressWarnings("unchecked")
	public void testFindCargosByCandidatoId()
	{
		Candidato candidato = new Candidato();
		candidato.setId(1L);

		Object[][] arrayCargos = new Object[][]{{1L,"cargo1"}, {2L,"cargo2"}};
		List retornoBanco = new ArrayList();

		retornoBanco.add(arrayCargos[0]);
		retornoBanco.add(arrayCargos[1]);

		candidatoDao.expects(once()).method("findCargosByCandidatoId").with(eq(candidato.getId())).will(returnValue(retornoBanco));

		Collection<Cargo> cargos = manager.findCargosByCandidatoId(candidato.getId());

		Cargo cargo = (Cargo) cargos.toArray()[0];

		assertEquals(2, cargos.size());
		assertEquals(arrayCargos[0][0], cargo.getId());
		assertEquals(arrayCargos[0][1], cargo.getNomeMercado());
	}

	@SuppressWarnings("unchecked")
	public void testFindAreasInteressesByCandidatoId()
	{
		Candidato candidato = new Candidato();
		candidato.setId(1L);

		Object[][] arrayAreaInteresses = new Object[][]{{1L,"areaInteresse1"}, {2L,"areaInteresse2"}};
		List retornoBanco = new ArrayList();

		retornoBanco.add(arrayAreaInteresses[0]);
		retornoBanco.add(arrayAreaInteresses[1]);

		candidatoDao.expects(once()).method("findAreaInteressesByCandidatoId").with(eq(candidato.getId())).will(returnValue(retornoBanco));

		Collection<AreaInteresse> areaInteresses = manager.findAreaInteressesByCandidatoId(candidato.getId());

		AreaInteresse areaInteresse = (AreaInteresse) areaInteresses.toArray()[0];

		assertEquals(2, areaInteresses.size());
		assertEquals(arrayAreaInteresses[0][0], areaInteresse.getId());
		assertEquals(arrayAreaInteresses[0][1], areaInteresse.getNome());
	}

	public void testGetOcrTextoById()
	{
		Candidato candidato = new Candidato();
		candidato.setId(1L);
		candidato.setOcrTexto("textoocr");

		Collection<Candidato> candidatos = new ArrayList<Candidato>();
		candidatos.add(candidato);

		candidatoDao.expects(once()).method("findToList").with(ANYTHING,ANYTHING,ANYTHING,ANYTHING).will(returnValue(candidatos));

		String retorno = manager.getOcrTextoById(candidato.getId());

		assertEquals("textoocr", retorno);

	}

	public void testAtualizaTextoOcr()
	{
		Candidato candidato = new Candidato();
		candidato.setId(1L);
		candidato.setOcrTexto("textoocr");

		candidatoDao.expects(once()).method("atualizaTextoOcr").with(eq(candidato));

		manager.atualizaTextoOcr(candidato);
	}

	public void testGetCandidatosByNome()
	{
		Collection<Candidato> candidatos = new ArrayList<Candidato>();
		candidatos.add(new Candidato());
		candidatos.add(new Candidato());

		candidatoDao.expects(once()).method("getCandidatosByNome").with(eq("nome")).will(returnValue(candidatos));

		assertEquals(2, manager.getCandidatosByNome("nome").toArray().length);
	}

    public void testMigrarBairro()
    {
    	candidatoDao.expects(once()).method("migrarBairro").with(eq("bairro"), eq("bairroDestino")).isVoid();

    	manager.migrarBairro("bairro", "bairroDestino");
    }

	public void testSaveCandidatoCurriculoException() throws Exception
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		Candidato candidatoComId = CandidatoFactory.getCandidato(1L);
		File[] imagemEscaneada = new File[]{new File()};
		File ocrTexto = null;

		candidatoDao.expects(once()).method("save").with(ANYTHING).will(returnValue(candidatoComId));
		candidatoCurriculoManager.expects(once()).method("findToList").with(ANYTHING,ANYTHING,ANYTHING,ANYTHING).will(returnValue(new ArrayList<CandidatoCurriculo>()));
		candidatoCurriculoManager.expects(once()).method("save").with(ANYTHING).will(returnValue(throwException(new Exception())));

		Exception exception = null;
		try
		{
			manager.saveCandidatoCurriculo(candidato, imagemEscaneada, ocrTexto);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testSaveCandidatoCurriculo() throws Exception
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		File[] imagemEscaneada = new File[]{new File()};
		File ocrTexto = new File();
		ocrTexto.setContentType("text");
		ocrTexto.setName("curriculo.txt");
		ocrTexto.setBytes(new byte[]{111,111,111});

		CandidatoCurriculo cc = new CandidatoCurriculo();
		cc.setId(1L);
		cc.setCandidato(candidato);

		Collection<CandidatoCurriculo> ccCol = new ArrayList<CandidatoCurriculo>();
		ccCol.add(cc);

		Candidato candidatoComId = CandidatoFactory.getCandidato(1L);
		
		candidatoDao.expects(once()).method("save").with(eq(candidato)).will(returnValue(candidatoComId));
		candidatoCurriculoManager.expects(once()).method("findToList").with(ANYTHING,ANYTHING,ANYTHING,ANYTHING).will(returnValue(ccCol));
		candidatoCurriculoManager.expects(once()).method("remove").with(ANYTHING);
		candidatoCurriculoManager.expects(once()).method("save").with(ANYTHING);
		manager.saveCandidatoCurriculo(candidato, imagemEscaneada, ocrTexto);
	}

	public void testUpdate()
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		candidato.setId(1L);
		candidato.getPessoal().setRgUf(new Estado());
		candidato.getPessoal().setCtps(new Ctps());
		candidato.getPessoal().getCtps().setCtpsUf(new Estado());

		candidatoDao.expects(once()).method("update").with(ANYTHING);

		manager.update(candidato);
	}

	public void testBuscaSimplesDaSolicitacaoTodasEmpresasPermitidas()
	{
		Long[] empresaIds = {1L, 2L};
		String[] cargosCheck = new String[]{"cargo"};
		String[] conhecimentosCheck = new String[]{"conhecimento"};
		boolean somenteSemSolicitacao = false;
		String escolaridade = null;

		candidatoSolicitacaoManager.expects(once()).method("getCandidatosBySolicitacao").with(ANYTHING).will(returnValue(null));
		candidatoDao.expects(once()).method("findCandidatosForSolicitacao").will(returnValue(new ArrayList<Candidato>()));
		Collection<Candidato> candidatos = manager.buscaSimplesDaSolicitacao("indicadoPor", "nome", "cpf", escolaridade, null , null, cargosCheck, conhecimentosCheck, null, somenteSemSolicitacao, 100, null, false, empresaIds);
		assertTrue(candidatos.isEmpty());
	}

	public void testBuscaSimplesDaSolicitacaoEmpresaUnica()
	{
		Long empresaId = 2L;
		String[] cargosCheck = new String[]{"1"};
		String[] conhecimentosCheck = new String[]{"1"};
		boolean somenteSemSolicitacao = false;
		String escolaridade = null;

		candidatoSolicitacaoManager.expects(once()).method("getCandidatosBySolicitacao").with(ANYTHING).will(returnValue(null));
		candidatoDao.expects(once()).method("findCandidatosForSolicitacao").will(returnValue(new ArrayList<Candidato>()));
		Collection<Candidato> candidatos = manager.buscaSimplesDaSolicitacao("indicadoPor", "nome", "cpf", escolaridade, null, null, cargosCheck, conhecimentosCheck, null, somenteSemSolicitacao, 100, null, true, empresaId);
		assertTrue(candidatos.isEmpty());
	}
	
	public void testFindByNomeCpf()
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		candidato.setCpf("63033783387");
		candidato.setNome("Carlos Chagas");
		candidatoDao.expects(once()).method("findByNomeCpf").with(eq(candidato),eq(1L)).will(returnValue(new ArrayList<Candidato>()));
		assertNotNull(manager.findByNomeCpf(candidato, 1L));
	}
	public void testFindByNomeCpfAllEmpresas()
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		candidato.setCpf("63033783387");
		candidato.setNome("Carlos Chagas");
		candidatoDao.expects(once()).method("findByNomeCpf").with(eq(candidato),eq(null)).will(returnValue(new ArrayList<Candidato>()));
		assertNotNull(manager.findByNomeCpfAllEmpresas(candidato));
	}

	public void testGetsSets()
	{
		manager.getTotalSize();
	}
	
	public void testEnviaAvisoDeCadastroCandidato() throws Exception
	{
		String nomeCandidato = "teste";
		
		Empresa empresa = new Empresa();
		empresa.setId(1L);
		empresa.setNome("empresa");
		empresa.setEmailRespRH("x@x.com");

		gerenciadorComunicacaoManager.expects(once()).method("enviaAvisoDeCadastroCandidato").with(ANYTHING, ANYTHING).isVoid();
		manager.enviaAvisoDeCadastroCandidato(nomeCandidato, empresa.getId());
	}
	
	public void testCountComoFicouSabendoVagas(){
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Date dataDe = DateUtil.criarDataMesAno(1, 11, 2010);
		Date dataAte = DateUtil.criarDataMesAno(1, 11, 2011);
		
		Collection<DataGrafico> vagas = new ArrayList<DataGrafico>();
		
		candidatoDao.expects(once()).method("countComoFicouSabendoVagas").with(eq(empresa.getId()), eq(dataDe), eq(dataAte)).will(returnValue(vagas));
		
		assertEquals(vagas, manager.countComoFicouSabendoVagas(empresa.getId(), dataDe, dataAte));
	}

	public void testfindQtdAtendidos() 
	{
		historicoCandidatoManager.expects(once()).method("findQtdAtendidos").withAnyArguments().will(returnValue(0));
		
		assertEquals(0, manager.findQtdAtendidos(null, null, null, null, null, null));
	}
	
	public void testExecutaTesteAutomaticoDoManager() 
	{
		testeAutomatico(candidatoDao);
	}
	
	//TODO remprot
//    public void testValidaQtdCadastros()
//    {
//    	ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
//    	parametrosDoSistemaManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(parametrosDoSistema));
//    	candidatoDao.expects(once()).method("getCount").will(returnValue(10));
//    	
//    	Exception exception = null;
//    	
//    	try
//		{
//			candidatoManager.validaQtdCadastros();
//		} catch (Exception e)
//		{
//			exception = e;
//		}
//		
//		assertNotNull(exception);
//    }
}
