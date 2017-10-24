package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.apache.commons.lang.StringUtils;
import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.junit.Ignore;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.avaliacao.PeriodoExperienciaManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.MotivoSolicitacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManagerImpl;
import com.fortes.rh.business.geral.MensagemManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.geral.ProvidenciaManager;
import com.fortes.rh.business.geral.UsuarioMensagemManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.business.sesmt.ComissaoMembroManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.dao.geral.GerenciadorComunicacaoDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.captacao.Habilitacao;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.dicionario.EnviarPara;
import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.GerenciadorComunicacao;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.MotivoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.GerenciadorComunicacaoFactory;
import com.fortes.rh.test.util.mockObjects.MockArquivoUtil;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.SpringUtil;

public class GerenciadorComunicacaoManagerTest extends MockObjectTestCase
{
	private GerenciadorComunicacaoManagerImpl gerenciadorComunicacaoManager = new GerenciadorComunicacaoManagerImpl();
	private Mock gerenciadorComunicacaoDao;
	private Mock candidatoSolicitacaoManager;
	private Mock historicoColaboradorManager;
	private Mock parametrosDoSistemaManager;
	private Mock periodoExperienciaManager;
	private Mock areaOrganizacionalManager;
	private Mock motivoSolicitacaoManager;
	private Mock colaboradorTurmaManager;
	private Mock usuarioMensagemManager;
	private Mock estabelecimentoManager;
	private Mock usuarioEmpresaManager;
	private Mock comissaoMembroManager;
	private Mock questionarioManager;
	private Mock colaboradorManager;
	private Mock solicitacaoManager;
	private Mock providenciaManager;
	private Mock mensagemManager;
	private Mock empresaManager;
	private Mock usuarioManager;
	private Mock exameManager;
	private Mock cargoManager;
	private Mock mail;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        gerenciadorComunicacaoDao = new Mock(GerenciadorComunicacaoDao.class);
        gerenciadorComunicacaoManager.setDao((GerenciadorComunicacaoDao) gerenciadorComunicacaoDao.proxy());
        
        candidatoSolicitacaoManager = new Mock(CandidatoSolicitacaoManager.class);
        gerenciadorComunicacaoManager.setCandidatoSolicitacaoManager((CandidatoSolicitacaoManager) candidatoSolicitacaoManager.proxy());
        
        parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);
        gerenciadorComunicacaoManager.setParametrosDoSistemaManager((ParametrosDoSistemaManager) parametrosDoSistemaManager.proxy());
        
        periodoExperienciaManager = new Mock(PeriodoExperienciaManager.class);
        gerenciadorComunicacaoManager.setPeriodoExperienciaManager((PeriodoExperienciaManager) periodoExperienciaManager.proxy());
        
        empresaManager = new Mock(EmpresaManager.class);
        gerenciadorComunicacaoManager.setEmpresaManager((EmpresaManager) empresaManager.proxy());
        
        usuarioMensagemManager = new Mock(UsuarioMensagemManager.class);
        gerenciadorComunicacaoManager.setUsuarioMensagemManager((UsuarioMensagemManager) usuarioMensagemManager.proxy());

        usuarioEmpresaManager = new Mock(UsuarioEmpresaManager.class);
        gerenciadorComunicacaoManager.setUsuarioEmpresaManager((UsuarioEmpresaManager) usuarioEmpresaManager.proxy());

        mensagemManager = new Mock(MensagemManager.class);
        gerenciadorComunicacaoManager.setMensagemManager((MensagemManager) mensagemManager.proxy());
        
		comissaoMembroManager = new Mock(ComissaoMembroManager.class);
		gerenciadorComunicacaoManager.setComissaoMembroManager((ComissaoMembroManager) comissaoMembroManager.proxy());
        
        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        gerenciadorComunicacaoManager.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());

        motivoSolicitacaoManager = new Mock(MotivoSolicitacaoManager.class);
        gerenciadorComunicacaoManager.setMotivoSolicitacaoManager((MotivoSolicitacaoManager) motivoSolicitacaoManager.proxy());
        
        colaboradorTurmaManager = new Mock(ColaboradorTurmaManager.class);
        MockSpringUtil.mocks.put("colaboradorTurmaManager", colaboradorTurmaManager);
        
        cargoManager = new Mock(CargoManager.class);
        gerenciadorComunicacaoManager.setCargoManager((CargoManager) cargoManager.proxy());

        mail = mock(Mail.class);
        gerenciadorComunicacaoManager.setMail((Mail) mail.proxy());

        solicitacaoManager = new Mock(SolicitacaoManager.class);
		MockSpringUtil.mocks.put("solicitacaoManager", solicitacaoManager);

		providenciaManager = new Mock(ProvidenciaManager.class);
		gerenciadorComunicacaoManager.setProvidenciaManager((ProvidenciaManager) providenciaManager.proxy());
		
		colaboradorManager = new Mock(ColaboradorManager.class);
		MockSpringUtil.mocks.put("colaboradorManager", colaboradorManager);
		gerenciadorComunicacaoManager.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
		
		historicoColaboradorManager = new Mock(HistoricoColaboradorManager.class);
		MockSpringUtil.mocks.put("historicoColaboradorManager", historicoColaboradorManager);
		
		usuarioManager = new Mock(UsuarioManager.class);
		MockSpringUtil.mocks.put("usuarioManager", usuarioManager);
		
		questionarioManager = new Mock(QuestionarioManager.class);
		MockSpringUtil.mocks.put("questionarioManager", questionarioManager);
		
		exameManager = new Mock(ExameManager.class);
		MockSpringUtil.mocks.put("exameManager", exameManager);
		
		estabelecimentoManager = new Mock(EstabelecimentoManager.class);
		MockSpringUtil.mocks.put("estabelecimentoManager", estabelecimentoManager);

        Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
        Mockit.redefineMethods(ArquivoUtil.class, MockArquivoUtil.class);
    }

	public void testEnviaMensagemCadastroSituacaoAC()
	{
		Empresa empresa = criaEmpresa();
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L, "Colaborador", null, null, null, null, null);
		
		TSituacao situacao = new TSituacao();
		situacao.setEmpresaCodigoAC("001");
		situacao.setGrupoAC("002");
		situacao.setLotacaoCodigoAC("003");
		situacao.setData("05/02/2013");
		
		GerenciadorComunicacao gerenciadorComunicacao1 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.CADASTRAR_SITUACAO_AC, MeioComunicacao.EMAIL, EnviarPara.GESTOR_AREA);
		GerenciadorComunicacao gerenciadorComunicacao2 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.CADASTRAR_SITUACAO_AC, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
		
		Usuario usuario = UsuarioFactory.getEntity();
		Collection<Usuario> usuarios = Arrays.asList(usuario);

		GerenciadorComunicacao gerenciadorComunicacao3 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.CADASTRAR_SITUACAO_AC, MeioComunicacao.EMAIL, EnviarPara.USUARIOS);
		gerenciadorComunicacao3.setUsuarios(usuarios);
		
		GerenciadorComunicacao gerenciadorComunicacao4 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.CADASTRAR_SITUACAO_AC, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.GESTOR_AREA);
		
		GerenciadorComunicacao gerenciadorComunicacao5 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.CADASTRAR_SITUACAO_AC, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		gerenciadorComunicacao5.setUsuarios(usuarios);
		
		GerenciadorComunicacao gerenciadorComunicacao6 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.CADASTRAR_SITUACAO_AC, MeioComunicacao.EMAIL, EnviarPara.COGESTOR_AREA);
		
		GerenciadorComunicacao gerenciadorComunicacao7 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.CADASTRAR_SITUACAO_AC, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.COGESTOR_AREA);
		
		String[] emails = new String[] {"email1@teste.com"};
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = new ArrayList<GerenciadorComunicacao>();
		gerenciadorComunicacaos.add(gerenciadorComunicacao1);
		gerenciadorComunicacaos.add(gerenciadorComunicacao2);
		gerenciadorComunicacaos.add(gerenciadorComunicacao3);
		gerenciadorComunicacaos.add(gerenciadorComunicacao4);
		gerenciadorComunicacaos.add(gerenciadorComunicacao5);
		gerenciadorComunicacaos.add(gerenciadorComunicacao6);
		gerenciadorComunicacaos.add(gerenciadorComunicacao7);

		gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.CADASTRAR_SITUACAO_AC.getId()),eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		
		empresaManager.expects(once()).method("findByCodigoAC").with(eq(situacao.getEmpresaCodigoAC()),eq(situacao.getGrupoAC())).will(returnValue(empresa));
		
		areaOrganizacionalManager.expects(once()).method("findAreaOrganizacionalByCodigoAc").with(eq(situacao.getLotacaoCodigoAC()), eq(situacao.getEmpresaCodigoAC()), eq(situacao.getGrupoAC())).will(returnValue(areaOrganizacional));
		areaOrganizacionalManager.expects(once()).method("getEmailsResponsaveis").with(new Constraint[]{eq(areaOrganizacional.getId()), eq(empresa.getId()), eq(AreaOrganizacional.RESPONSAVEL), ANYTHING}).will(returnValue(emails));
		mail.expects(once()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		
		mail.expects(once()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		
		CollectionUtil<Usuario> collUtil = new CollectionUtil<Usuario>();
		Long[] usuariosIds = collUtil.convertCollectionToArrayIds(gerenciadorComunicacao3.getUsuarios());
		usuarioManager.expects(once()).method("findEmailsByUsuario").with(eq(usuariosIds), ANYTHING).will(returnValue(emails));
		mail.expects(once()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		
		areaOrganizacionalManager.expects(once()).method("findAreaOrganizacionalByCodigoAc").with(eq(situacao.getLotacaoCodigoAC()), eq(situacao.getEmpresaCodigoAC()), eq(situacao.getGrupoAC())).will(returnValue(areaOrganizacional));
		usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagemRespAreaOrganizacional").withAnyArguments().isVoid();		
		
		usuarioEmpresaManager.expects(once()).method("findUsuariosAtivo").with(eq(LongUtil.collectionToCollectionLong(gerenciadorComunicacao5.getUsuarios())), eq(gerenciadorComunicacao5.getEmpresa().getId()));
		usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagem").withAnyArguments().isVoid();
		
		areaOrganizacionalManager.expects(once()).method("findAreaOrganizacionalByCodigoAc").with(eq(situacao.getLotacaoCodigoAC()), eq(situacao.getEmpresaCodigoAC()), eq(situacao.getGrupoAC())).will(returnValue(areaOrganizacional));
		areaOrganizacionalManager.expects(once()).method("getEmailsResponsaveis").with(new Constraint[]{eq(areaOrganizacional.getId()), eq(empresa.getId()), eq(AreaOrganizacional.CORRESPONSAVEL), ANYTHING}).will(returnValue(emails));
		mail.expects(once()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});

		areaOrganizacionalManager.expects(once()).method("findAreaOrganizacionalByCodigoAc").with(eq(situacao.getLotacaoCodigoAC()), eq(situacao.getEmpresaCodigoAC()), eq(situacao.getGrupoAC())).will(returnValue(areaOrganizacional));
		usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagemCoRespAreaOrganizacional").withAnyArguments().isVoid();		
		
		gerenciadorComunicacaoManager.enviaMensagemCadastroSituacaoAC(colaborador.getNome(), situacao);
	}
	@Ignore
	public void testEnviaMensagemHabilitacaoAVencer()
	{
		Empresa empresa = criaEmpresa();
		
		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity(1L);
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity(2L);
		AreaOrganizacional areaOrganizacional3 = AreaOrganizacionalFactory.getEntity(3L);
		
		Collection<AreaOrganizacional> areas = Arrays.asList(areaOrganizacional1, areaOrganizacional2, areaOrganizacional3);
		
		Habilitacao habilitacaoComVencimento1DiaDepois = new Habilitacao();
		habilitacaoComVencimento1DiaDepois.setVencimento(DateUtil.incrementaDias(new Date(), 1));
		
		Habilitacao habilitacaoComVencimento2DiasDepois = new Habilitacao();
		habilitacaoComVencimento2DiasDepois.setVencimento(DateUtil.incrementaDias(new Date(), 2));
		
		Habilitacao habilitacaoComVencimento3DiasDepois = new Habilitacao();
		habilitacaoComVencimento3DiasDepois.setVencimento(DateUtil.incrementaDias(new Date(), 3));
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L, "Colaborador 1", "Nome Comercial 1", null, null, null, null);
		colaborador1.setHabilitacao(habilitacaoComVencimento1DiaDepois);
		colaborador1.setAreaOrganizacional(areaOrganizacional1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L, "Colaborador 2", "Nome Comercial 2", null, null, null, null);
		colaborador2.setHabilitacao(habilitacaoComVencimento1DiaDepois);
		colaborador2.setAreaOrganizacional(areaOrganizacional1);
		
		Colaborador colaborador3 = ColaboradorFactory.getEntity(3L, "Colaborador 3", "Nome Comercial 3", null, null, null, null);
		colaborador3.setHabilitacao(habilitacaoComVencimento2DiasDepois);
		colaborador3.setAreaOrganizacional(areaOrganizacional2);
		
		Colaborador colaborador4 = ColaboradorFactory.getEntity(4L, "Colaborador 4", "Nome Comercial 4", null, null, null, null);
		colaborador4.setHabilitacao(habilitacaoComVencimento3DiasDepois);
		colaborador4.setAreaOrganizacional(areaOrganizacional3);
		
		GerenciadorComunicacao gerenciadorComunicacao1 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.HABILITACAO_A_VENCER, MeioComunicacao.EMAIL, EnviarPara.GESTOR_AREA);
		gerenciadorComunicacao1.setQtdDiasLembrete("1&2");
		
		GerenciadorComunicacao gerenciadorComunicacao2 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.HABILITACAO_A_VENCER, MeioComunicacao.EMAIL, EnviarPara.COGESTOR_AREA);
		gerenciadorComunicacao2.setQtdDiasLembrete("1&2");
		
		GerenciadorComunicacao gerenciadorComunicacao3 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.HABILITACAO_A_VENCER, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
		gerenciadorComunicacao3.setQtdDiasLembrete("1&2");
		
		Collection<Integer> diasLembrete = Arrays.asList(1, 2);
		String[] emails = new String[] {"email1@teste.com", "email2@teste.com"};
		
		Usuario usuario = UsuarioFactory.getEntity();
		Collection<Usuario> usuarios = Arrays.asList(usuario);
		
		GerenciadorComunicacao gerenciadorComunicacao4 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.HABILITACAO_A_VENCER, MeioComunicacao.EMAIL, EnviarPara.USUARIOS);
		gerenciadorComunicacao4.setUsuarios(usuarios);
		gerenciadorComunicacao4.setQtdDiasLembrete("1&2");
		
		CollectionUtil<Usuario> collUtil = new CollectionUtil<Usuario>();
		Long[] usuariosIds = collUtil.convertCollectionToArrayIds(gerenciadorComunicacao4.getUsuarios());
		
		GerenciadorComunicacao gerenciadorComunicacao5 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.HABILITACAO_A_VENCER, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.GESTOR_AREA);
		gerenciadorComunicacao5.setQtdDiasLembrete("1&2");
		
		GerenciadorComunicacao gerenciadorComunicacao6 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.HABILITACAO_A_VENCER, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.COGESTOR_AREA);
		gerenciadorComunicacao6.setQtdDiasLembrete("1&2");
		
		GerenciadorComunicacao gerenciadorComunicacao7 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.HABILITACAO_A_VENCER, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		gerenciadorComunicacao7.setUsuarios(usuarios);
		gerenciadorComunicacao7.setQtdDiasLembrete("1&2");
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = new ArrayList<GerenciadorComunicacao>();
		gerenciadorComunicacaos.add(gerenciadorComunicacao1);
		gerenciadorComunicacaos.add(gerenciadorComunicacao2);
		gerenciadorComunicacaos.add(gerenciadorComunicacao3);
		gerenciadorComunicacaos.add(gerenciadorComunicacao4);
		gerenciadorComunicacaos.add(gerenciadorComunicacao5);
		gerenciadorComunicacaos.add(gerenciadorComunicacao6);
		gerenciadorComunicacaos.add(gerenciadorComunicacao7);
		
		gerenciadorComunicacaoDao.expects(atLeastOnce()).method("findByOperacaoId").with(eq(Operacao.HABILITACAO_A_VENCER.getId()),eq(null)).will(returnValue(gerenciadorComunicacaos));
		areaOrganizacionalManager.expects(atLeastOnce()).method("findAllListAndInativas").with(eq(true), eq(null), eq(new Long[]{gerenciadorComunicacao1.getEmpresa().getId()})).will(returnValue(areas));
		colaboradorManager.expects(atLeastOnce()).method("findHabilitacaAVencer").with(eq(diasLembrete), eq(gerenciadorComunicacao1.getEmpresa().getId())).will(returnValue(Arrays.asList(colaborador1, colaborador2, colaborador3)));
		
		// Email para gestor
		areaOrganizacionalManager.expects(atLeastOnce()).method("getEmailsResponsaveis").with(new Constraint[]{(ANYTHING), eq(areas), eq(AreaOrganizacional.RESPONSAVEL)}).will(returnValue(emails));
		mail.expects(atLeastOnce()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		
		// Email para cogestor
		areaOrganizacionalManager.expects(atLeastOnce()).method("getEmailsResponsaveis").with(new Constraint[]{(ANYTHING), eq(areas), eq(AreaOrganizacional.CORRESPONSAVEL)}).will(returnValue(emails));
		mail.expects(once()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		
		// Email para responsavel do rh
		mail.expects(once()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		
		// Email para usuarios
		usuarioManager.expects(once()).method("findEmailsByUsuario").with(eq(usuariosIds), ANYTHING).will(returnValue(emails));
		mail.expects(once()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		
		// Caixa de mensagem para gestor
		areaOrganizacionalManager.expects(atLeastOnce()).method("getMatriarca").withAnyArguments().will(returnValue(areaOrganizacional1));
		usuarioMensagemManager.expects(atLeastOnce()).method("saveMensagemAndUsuarioMensagemRespAreaOrganizacional").withAnyArguments().isVoid();
				
		// Caixa de mensagem para cogestor
		areaOrganizacionalManager.expects(once()).method("getMatriarca").withAnyArguments().will(returnValue(areaOrganizacional1));
		usuarioMensagemManager.expects(atLeastOnce()).method("saveMensagemAndUsuarioMensagemCoRespAreaOrganizacional").withAnyArguments().isVoid();
		
		// Caixa de mensagem para usuarios
		usuarioEmpresaManager.expects(once()).method("findUsuariosAtivo").with(eq(LongUtil.collectionToCollectionLong(gerenciadorComunicacao7.getUsuarios())), eq(gerenciadorComunicacao7.getEmpresa().getId()));
		usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagem").withAnyArguments().isVoid();
		
		gerenciadorComunicacaoManager.enviaMensagemHabilitacaoAVencer();
	}
	
	public void testEnviarEmailParaResponsaveis() throws Exception
	{
		ParametrosDoSistema parametroSistema = new ParametrosDoSistema();
		parametroSistema.setAppUrl("url");
		parametroSistema.setEmailDoSuporteTecnico("t@t.com.br");
		
		Empresa empresa = criaEmpresa();
		
		Usuario solicitante = UsuarioFactory.getEntity(1L);
		
		Colaborador colabSolicitante = ColaboradorFactory.getEntity(1L);
		colabSolicitante.setNome("nome");
		colabSolicitante.setNomeComercial("nomeComercial");
		colabSolicitante.setUsuario(solicitante);
		
		MotivoSolicitacao motivoSolicitacao = MotivoSolicitacaoFactory.getEntity();
		motivoSolicitacao.setId(1L);
		motivoSolicitacao.setDescricao("Motivo");
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		estabelecimento.setNome("Estabelecimento");
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setDescricao("descricao");
		solicitacao.setSolicitante(solicitante);
		solicitacao.setMotivoSolicitacao(motivoSolicitacao);
		solicitacao.setData(DateUtil.criarDataMesAno(20, 8, 2013));
		solicitacao.setObservacaoLiberador("observacaoLiberador");
		solicitacao.setEstabelecimento(estabelecimento);
		solicitacao.setStatus(StatusAprovacaoSolicitacao.ANALISE);
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
		
		GerenciadorComunicacao gerenciadorComunicacao2 = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.APROVAR_REPROVAR_SOLICITACAO_PESSOAL);
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao, gerenciadorComunicacao2);
		
		String[] emailsByUsuario = new String[]{empresa.getEmailRespRH()};
		String[] emailsmMrcados = new String[]{"marcado@gmail.com"};

		parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(parametroSistema));
		colaboradorManager.expects(atLeastOnce()).method("findByUsuarioProjection").with(eq(solicitacao.getSolicitante().getId()), eq(true)).will(returnValue(colabSolicitante));
		motivoSolicitacaoManager.expects(atLeastOnce()).method("findById").with(eq(solicitacao.getMotivoSolicitacao().getId())).will(returnValue(motivoSolicitacao));
		estabelecimentoManager.expects(atLeastOnce()).method("findById").with(eq(solicitacao.getEstabelecimento().getId())).will(returnValue(estabelecimento));
		mail.expects(once()).method("send").with(new Constraint[]{eq(empresa),eq(parametroSistema),ANYTHING,ANYTHING,eq(true),ANYTHING});
		gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.CADASTRAR_SOLICITACAO.getId()),eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		mail.expects(once()).method("send").with(new Constraint[]{eq(empresa),eq(parametroSistema),ANYTHING,ANYTHING,eq(true),ANYTHING});
		usuarioManager.expects(once()).method("findEmailsByPerfil").with(eq("ROLE_LIBERA_SOLICITACAO"),eq(empresa.getId())).will(returnValue(emailsByUsuario));
		mail.expects(once()).method("send").with(new Constraint[]{eq(empresa),eq(parametroSistema),ANYTHING,ANYTHING,eq(true),ANYTHING});
			
		gerenciadorComunicacaoManager.enviarEmailParaResponsaveisSolicitacaoPessoal(solicitacao, empresa, emailsmMrcados);
	}
	
	public void testEnviarEmailParaUsuarioComPermiAprovarOrReprovarAndGestor () throws Exception
	{
		ParametrosDoSistema parametroSistema = new ParametrosDoSistema();
		parametroSistema.setAppUrl("url");
		parametroSistema.setEmailDoSuporteTecnico("t@t.com.br");
		
		Empresa empresa = criaEmpresa();
		
		Usuario solicitante = UsuarioFactory.getEntity(1L);
		
		Colaborador colabSolicitante = ColaboradorFactory.getEntity(1L);
		colabSolicitante.setNome("nome");
		colabSolicitante.setNomeComercial("nomeComercial");
		colabSolicitante.setUsuario(solicitante);
		
		Colaborador gestor = ColaboradorFactory.getEntity(1L);

		AreaOrganizacional areaOrganizacionalMae = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacionalMae.setNome("Area Mãe");
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(2L);
		areaOrganizacional.setResponsavel(gestor);
		areaOrganizacional.setAreaMae(areaOrganizacionalMae);
		
		MotivoSolicitacao motivoSolicitacao = MotivoSolicitacaoFactory.getEntity();
		motivoSolicitacao.setId(1L);
		motivoSolicitacao.setDescricao("Motivo");
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		estabelecimento.setNome("Estabelecimento");
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setDescricao("descricao");
		solicitacao.setSolicitante(solicitante);
		solicitacao.setMotivoSolicitacao(motivoSolicitacao);
		solicitacao.setData(DateUtil.criarDataMesAno(20, 8, 2013));
		solicitacao.setObservacaoLiberador("observacaoLiberador");
		solicitacao.setEstabelecimento(estabelecimento);
		solicitacao.setAreaOrganizacional(areaOrganizacional);
		solicitacao.setStatus(StatusAprovacaoSolicitacao.ANALISE);
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.APROVAR_REPROVAR_SOLICITACAO_PESSOAL_AND_GESTOR);
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		String[] emailsByUsuario = new String[]{empresa.getEmailRespRH()};
		String[] emailsmMrcados = new String[]{"marcado@gmail.com"};
		
		gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.CADASTRAR_SOLICITACAO.getId()),eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(parametroSistema));
		colaboradorManager.expects(atLeastOnce()).method("findByUsuarioProjection").with(eq(solicitacao.getSolicitante().getId()), eq(true)).will(returnValue(colabSolicitante));
		motivoSolicitacaoManager.expects(atLeastOnce()).method("findById").with(eq(solicitacao.getMotivoSolicitacao().getId())).will(returnValue(motivoSolicitacao));
		estabelecimentoManager.expects(atLeastOnce()).method("findById").with(eq(solicitacao.getEstabelecimento().getId())).will(returnValue(estabelecimento));
		usuarioManager.expects(once()).method("findEmailByPerfilAndGestor").with(new Constraint[]{eq("ROLE_LIBERA_SOLICITACAO"), eq(empresa.getId()), eq(solicitacao.getAreaOrganizacional().getId()), eq(false), eq(null)}).will(returnValue(emailsByUsuario));
		mail.expects(atLeastOnce()).method("send").withAnyArguments();
			
		gerenciadorComunicacaoManager.enviarEmailParaResponsaveisSolicitacaoPessoal(solicitacao, empresa, emailsmMrcados);
	}
	
	public void testEnviarEmailParaUsuarioComPermiAprovarOrReprovarAndGestorSolicitacaoInviselParaGestor () throws Exception
	{
		ParametrosDoSistema parametroSistema = new ParametrosDoSistema();
		parametroSistema.setAppUrl("url");
		parametroSistema.setEmailDoSuporteTecnico("t@t.com.br");
		
		Empresa empresa = criaEmpresa();
		
		Usuario solicitante = UsuarioFactory.getEntity(1L);
		
		Colaborador colabSolicitante = ColaboradorFactory.getEntity(1L);
		colabSolicitante.setNome("nome");
		colabSolicitante.setNomeComercial("nomeComercial");
		colabSolicitante.setUsuario(solicitante);
		
		Colaborador gestor = ColaboradorFactory.getEntity(1L);

		AreaOrganizacional areaOrganizacionalMae = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacionalMae.setNome("Area Mãe");
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(2L);
		areaOrganizacional.setResponsavel(gestor);
		areaOrganizacional.setAreaMae(areaOrganizacionalMae);
		
		MotivoSolicitacao motivoSolicitacao = MotivoSolicitacaoFactory.getEntity();
		motivoSolicitacao.setId(1L);
		motivoSolicitacao.setDescricao("Motivo");
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		estabelecimento.setNome("Estabelecimento");
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setDescricao("descricao");
		solicitacao.setSolicitante(solicitante);
		solicitacao.setInvisivelParaGestor(true);
		solicitacao.setMotivoSolicitacao(motivoSolicitacao);
		solicitacao.setData(DateUtil.criarDataMesAno(20, 8, 2013));
		solicitacao.setObservacaoLiberador("observacaoLiberador");
		solicitacao.setEstabelecimento(estabelecimento);
		solicitacao.setAreaOrganizacional(areaOrganizacional);
		solicitacao.setStatus(StatusAprovacaoSolicitacao.ANALISE);
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.APROVAR_REPROVAR_SOLICITACAO_PESSOAL_AND_GESTOR);
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		String emailGestor = "gestor@gmail.com";
		
		gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.CADASTRAR_SOLICITACAO.getId()),eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(parametroSistema));
		colaboradorManager.expects(atLeastOnce()).method("findByUsuarioProjection").with(eq(solicitacao.getSolicitante().getId()), eq(true)).will(returnValue(colabSolicitante));
		motivoSolicitacaoManager.expects(atLeastOnce()).method("findById").with(eq(solicitacao.getMotivoSolicitacao().getId())).will(returnValue(motivoSolicitacao));
		estabelecimentoManager.expects(atLeastOnce()).method("findById").with(eq(solicitacao.getEstabelecimento().getId())).will(returnValue(estabelecimento));
		usuarioManager.expects(once()).method("findEmailByPerfilAndGestor").with(new Constraint[]{eq("ROLE_LIBERA_SOLICITACAO"), eq(empresa.getId()), eq(solicitacao.getAreaOrganizacional().getId()), eq(false), eq(emailGestor)}).will(returnValue(new String[]{}));
		areaOrganizacionalManager.expects(once()).method("getEmailResponsavel").with(eq(solicitacao.getAreaOrganizacional().getId())).will(returnValue(emailGestor));
		
		gerenciadorComunicacaoManager.enviarEmailParaResponsaveisSolicitacaoPessoal(solicitacao, empresa,  new String[]{});
	}
	
	public void testEnviaComunicadoAoCadastrarSolicitacaoRealinhamentoColaborador() throws Exception
	{
		Empresa empresa = criaEmpresa();
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setNome("Colaborador");
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		String subject = "[RH] - Cadastro de solicitação de realinhamento para colaborador";
		String body = "Foi realizado um cadastro de solicitação de realinhamento para o colaborador " + colaborador.getNome() + ".";
		String[] emails = StringUtils.split(empresa.getEmailRespRH(), ";");
		
		gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.CADASTRAR_SOLICITACAO_REALINHAMENTO_COLABORADOR.getId()),eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		empresaManager.expects(once()).method("findByIdProjection").with(eq(empresa.getId())).will(returnValue(empresa));
		mail.expects(once()).method("send").with(new Constraint[]{eq(empresa), eq(subject), eq(body), eq(null), eq(emails)});
		
		gerenciadorComunicacaoManager.enviaAvisoAoCadastrarSolicitacaoRealinhamentoColaborador(empresa.getId(), colaborador, null);
	}
	
	public void testEnviaAvisoSolicitacaoDesligamento() throws Exception
	{
		Empresa empresa = criaEmpresa();
		String[] emailsByUsuario = new String[]{empresa.getEmailRespRH()};
		
		Usuario usuario = UsuarioFactory.getEntity(1L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L, "Colaborador", "colab@gmail.com", DateUtil.criarDataMesAno(1, 1, 2016), "pq sim", "obs: demissão", null, AreaOrganizacionalFactory.getEntity(1L));
		
		ParametrosDoSistema parametroSistema = new ParametrosDoSistema();
		parametroSistema.setAppUrl("url");
		parametroSistema.setEmailDoSuporteTecnico("t@t.com.br");
		
		GerenciadorComunicacao gerenciadorComunicacao1 = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
		GerenciadorComunicacao gerenciadorComunicacao2 = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.APROVAR_REPROVAR_SOLICITACAO_DESLIGAMENTO);
		GerenciadorComunicacao gerenciadorComunicacao3 = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.USUARIOS);
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao1, gerenciadorComunicacao2, gerenciadorComunicacao3);
		
		String subject = "[RH] - Solicitação de desligamento de colaborador";
		String body = "Existe uma solicitação de desligamento para o colaborador <b>Colaborador</b> da empresa <b>Empresa I</b> pendente. Para aprovar ou reprovar essa solicitação, acesse o sistema <a href='url/geral/colaborador/visualizarSolicitacaoDesligamento.action?colaborador.id=1'>RH</a>.<br/><br /><b>Usuário solicitante:</b><br />nome do usuario<br /><br /><b>Data da Solicitação:</b><br />01/01/2016<br /><br /><b>Motivo:</b><br />pq sim<br /><br /><b>Observação:</b><br />obs: demissão";
		
		colaboradorManager.expects(atLeastOnce()).method("findByIdComHistorico").withAnyArguments().will(returnValue(colaborador));
		empresaManager.expects(once()).method("findByIdProjection").with(eq(empresa.getId())).will(returnValue(empresa));
		usuarioManager.expects(once()).method("findByIdProjection").with(eq(usuario.getId())).will(returnValue(usuario));
		parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(parametroSistema));
		usuarioManager.expects(once()).method("findEmailByPerfilAndGestor").withAnyArguments().will(returnValue(emailsByUsuario));
		usuarioManager.expects(once()).method("findEmailsByUsuario").withAnyArguments().will(returnValue(emailsByUsuario));
		gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.SOLICITAR_DESLIGAMENTO.getId()),eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		mail.expects(atLeastOnce()).method("send").with(new Constraint[]{eq(empresa),eq(parametroSistema), eq(subject), eq(body), eq(true), ANYTHING});
		
		Exception e = null;
		try {
			gerenciadorComunicacaoManager.enviaAvisoSolicitacaoDesligamento(colaborador.getId(), usuario.getId(), empresa.getId());
		} catch (Exception ex) {
			e = ex;
		}
		assertNull(e);
	}
	
	public void testEnviarEmailAoCriarAcessoSistemaSemEmail()
	{
		Empresa empresa = criaEmpresa();

		try{
			gerenciadorComunicacaoManager.enviarEmailAoCriarAcessoSistema("login", "senha", null, empresa);
			gerenciadorComunicacaoManager.enviarEmailAoCriarAcessoSistema("login", "senha", "", empresa);
			gerenciadorComunicacaoManager.enviarEmailAoCriarAcessoSistema("login", "senha", " ", empresa);
		} catch (Exception e) {
			assertFalse(true);
		}
		assertTrue(true);
	}
	
	public void testEenviarEmailAoCriarAcessoSistemaComEmail()
	{
		Empresa empresa = criaEmpresa();
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.COLABORADOR);
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(new ParametrosDoSistema()));
		gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.CRIAR_ACESSO_SISTEMA.getId()),eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		mail.expects(once()).method("send").withAnyArguments().isVoid();
		
		gerenciadorComunicacaoManager.enviarEmailAoCriarAcessoSistema("login", "senha", "email@email.com", empresa);
	}
	
	public void testEenviarEmailAoCriarAcessoSistemaSemGerenciadorConfigurado()
	{
		Empresa empresa = criaEmpresa();
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.AVULSO);
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(new ParametrosDoSistema()));
		gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.CRIAR_ACESSO_SISTEMA.getId()),eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		
		gerenciadorComunicacaoManager.enviarEmailAoCriarAcessoSistema("login", "senha", "email@email.com", empresa);
	}

	private Empresa criaEmpresa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setNome("Empresa I");
		empresa.setEmailRespRH("teste1@gmail.com;teste2@gmail.com;");
		empresa.setEmailRemetente("teste1@gmail.com");
		
		return empresa;
	}

	public void testEnviaAvisoAprovacaoSolicitacaoDesligamento() throws Exception
	{
		Empresa empresa = criaEmpresa();

		Colaborador solicitanteDemissao = ColaboradorFactory.getEntity(1L);
		solicitanteDemissao.setEmailColaborador("email@gmail.com");

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setNome("Colaborador");

		ParametrosDoSistema parametroSistema = new ParametrosDoSistema();
		parametroSistema.setAppUrl("url");
		parametroSistema.setEmailDoSuporteTecnico("t@t.com.br");

		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.SOLICITANTE_DESLIGAMENTO);
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);

		String subject = "[RH] - Solicitação de desligamento de colaborador aprovada";
		String body = "<br />Sua solicitação de desligamento para o(a) colaborador(a) <b>Colaborador</b> da empresa <b>Empresa I</b> foi aprovada.<br /><br />";

		parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(parametroSistema));
		gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.APROVAR_SOLICITACAO_DESLIGAMENTO.getId()),eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		colaboradorManager.expects(once()).method("findColaboradorByIdProjection").with(eq(solicitanteDemissao.getId())).will(returnValue(solicitanteDemissao)); 
		mail.expects(once()).method("send").with(new Constraint[]{eq(empresa),eq(parametroSistema), eq(subject), eq(body), eq(true), ANYTHING}).isVoid();

		Exception exception = null;
		try {
			gerenciadorComunicacaoManager.enviaAvisoAprovacaoSolicitacaoDesligamento(5L, colaborador.getNome(), solicitanteDemissao.getId(), empresa, true);
		} catch (Exception e) {
			exception = e;
		}

		assertNull(exception);
	}
	
	public void testEnviaAvisoAprovacaoSolicitacaoDesligamentoException()
	{
		Empresa empresa = criaEmpresa();

		Colaborador solicitanteDemissao = ColaboradorFactory.getEntity(1L);
		solicitanteDemissao.setEmailColaborador("email@gmail.com");

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setNome("Colaborador");

		ParametrosDoSistema parametroSistema = new ParametrosDoSistema();
		parametroSistema.setAppUrl("url");
		parametroSistema.setEmailDoSuporteTecnico("t@t.com.br");

		parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));

		Exception exception = null;
		try {
			gerenciadorComunicacaoManager.enviaAvisoAprovacaoSolicitacaoDesligamento(5L, colaborador.getNome(), solicitanteDemissao.getId(), empresa, true);
		} catch (Exception e) {
			exception = e;
		}

		assertNull(exception);
	}

	public void testEnviaAvisoAprovacaoSolicitacaoDesligamentoSolicitanteIgualDestinatario() 
	{
		Empresa empresa = criaEmpresa();

		Colaborador solicitanteDemissao = ColaboradorFactory.getEntity(1L);
		solicitanteDemissao.setEmailColaborador("email@gmail.com");

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setNome("Colaborador");

		Exception exception = null;
		try {
			gerenciadorComunicacaoManager.enviaAvisoAprovacaoSolicitacaoDesligamento(solicitanteDemissao.getId(), colaborador.getNome(), solicitanteDemissao.getId(), empresa, true);
		} catch (Exception e) {
			exception = e;
		}

		assertNull(exception);
	}
}
