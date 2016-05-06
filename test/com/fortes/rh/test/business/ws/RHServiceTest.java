package com.fortes.rh.test.business.ws;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.pesquisa.PesquisaManager;
import com.fortes.rh.business.security.TokenManager;
import com.fortes.rh.business.ws.RHServiceImpl;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.MovimentacaoAC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.ws.FeedbackWebService;
import com.fortes.rh.model.ws.TEmpregado;
import com.fortes.rh.model.ws.TEmpresa;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.model.ws.Token;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;

public class RHServiceTest extends MockObjectTestCase
{
	private RHServiceImpl rHServiceImpl = new RHServiceImpl();
	private Mock empresaManager;
	private Mock colaboradorManager;
	private Mock gerenciadorComunicacaoManager;
	private Mock areaOrganizacionalManager;
	private Mock estabelecimentoManager;
	private Mock faixaSalarialManager;
	private Mock transactionManager;
	private Mock pesquisaManager;
	private Mock historicoColaboradorManager;
	private Mock tokenManager;

	protected void setUp() throws Exception
	{
		super.setUp();

		empresaManager = new Mock(EmpresaManager.class);
		rHServiceImpl.setEmpresaManager((EmpresaManager) empresaManager.proxy());

		colaboradorManager = new Mock(ColaboradorManager.class);
		rHServiceImpl.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());

		gerenciadorComunicacaoManager = new Mock(GerenciadorComunicacaoManager.class);
		rHServiceImpl.setGerenciadorComunicacaoManager((GerenciadorComunicacaoManager) gerenciadorComunicacaoManager.proxy());
		
		areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
		rHServiceImpl.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());

		estabelecimentoManager = new Mock(EstabelecimentoManager.class);
		rHServiceImpl.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());

		faixaSalarialManager = new Mock(FaixaSalarialManager.class);
		rHServiceImpl.setFaixaSalarialManager((FaixaSalarialManager) faixaSalarialManager.proxy());
		
		transactionManager = new Mock(PlatformTransactionManager.class);
		rHServiceImpl.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());
		
		pesquisaManager = new Mock(PesquisaManager.class);
		rHServiceImpl.setPesquisaManager((PesquisaManager) pesquisaManager.proxy());
		
		historicoColaboradorManager = new Mock(HistoricoColaboradorManager.class);
		rHServiceImpl.setHistoricoColaboradorManager((HistoricoColaboradorManager) historicoColaboradorManager.proxy());
		
		tokenManager = new Mock(TokenManager.class);
		rHServiceImpl.setTokenManager((TokenManager) tokenManager.proxy());
	}
	
	public void testTransferirSemEmpresaIntegrada() throws Exception
	{
		TEmpresa tEmpresaOrigin = new TEmpresa();
		TEmpresa tEmpresaDestino = new TEmpresa();
		
		TEmpregado tEmpregado1 = new TEmpregado();
		TEmpregado[] tEmpregados = new TEmpregado[]{tEmpregado1};
		
		TSituacao tSituacao1 = new TSituacao();
		TSituacao[] tSituacoes = new TSituacao[]{tSituacao1};
		String dataDesligamento = "10/07/2014";
		
		tokenManager.expects(once()).method("findFirst").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new Token("TOKEN")));
		tokenManager.expects(once()).method("remove").with(ANYTHING).isVoid();
		FeedbackWebService feedbackWebService = rHServiceImpl.transferir("TOKEN", tEmpresaOrigin, tEmpresaDestino, tEmpregados, tSituacoes, dataDesligamento); 
		
		assertFalse(feedbackWebService.isSucesso());
		assertEquals("Nenhuma empresa esta integrada com o sistena RH.", feedbackWebService.getMensagem());
	}
	
	public void testTransferirSemEmpresaIntegradaErroEmpresaOrigem() throws Exception
	{
		String codigoACOrigim = "codigoACOrigim";
		String grupoACOrigim = "grupoACOrigim";
		
		TEmpresa tEmpresaOrigin = new TEmpresa();
		tEmpresaOrigin.setCodigoAC(codigoACOrigim);
		tEmpresaOrigin.setGrupoAC(grupoACOrigim);
		
		TEmpregado tEmpregado1 = new TEmpregado();
		TEmpregado[] tEmpregados = new TEmpregado[]{tEmpregado1};
		
		TSituacao tSituacao1 = new TSituacao();
		TSituacao[] tSituacoes = new TSituacao[]{tSituacao1};
		
		tokenManager.expects(once()).method("findFirst").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new Token("TOKEN")));
		tokenManager.expects(once()).method("remove").with(ANYTHING).isVoid();
		empresaManager.expects(once()).method("findByCodigoAC").with(eq(tEmpresaOrigin.getCodigoAC()), eq(tEmpresaOrigin.getGrupoAC())).will(returnValue(null));
		
		FeedbackWebService feedbackWebService = rHServiceImpl.transferir("TOKEN", tEmpresaOrigin, new TEmpresa(), tEmpregados, tSituacoes, "10/07/2014"); 
		
		assertFalse(feedbackWebService.isSucesso());
		assertEquals("Empresa origem não encontrada no sistema RH", feedbackWebService.getMensagem());
	}

	public void testTransferirSemEmpresaIntegradaErroEmpresaDestino() throws Exception
	{
		String codigoACDestino = "codigoACDestino";
		String grupoACDestino = "grupoACDestino";
		
		TEmpresa tEmpresaDestino = new TEmpresa();
		tEmpresaDestino.setCodigoAC(codigoACDestino);
		tEmpresaDestino.setGrupoAC(grupoACDestino);
		
		TEmpregado tEmpregado1 = new TEmpregado();
		TEmpregado[] tEmpregados = new TEmpregado[]{tEmpregado1};
		
		TSituacao tSituacao1 = new TSituacao();
		TSituacao[] tSituacoes = new TSituacao[]{tSituacao1};
		
		tokenManager.expects(once()).method("findFirst").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new Token("TOKEN")));
		tokenManager.expects(once()).method("remove").with(ANYTHING).isVoid();
		empresaManager.expects(once()).method("findByCodigoAC").with(eq(tEmpresaDestino.getCodigoAC()), eq(tEmpresaDestino.getGrupoAC())).will(returnValue(null));
		
		FeedbackWebService feedbackWebService = rHServiceImpl.transferir("TOKEN", new TEmpresa(), tEmpresaDestino, tEmpregados, tSituacoes, "10/07/2014"); 
		
		assertFalse(feedbackWebService.isSucesso());
		assertEquals("Empresa destino não encontrada no sistema RH", feedbackWebService.getMensagem());
	}
	
	public void testTransferirComEmpresaOrigemIntegrada() throws Exception
	{
		String codigoACOrigim = "codigoACOrigim";
		String grupoACOrigim = "grupoACOrigim";
		
		TEmpresa tEmpresaOrigin = new TEmpresa();
		tEmpresaOrigin.setCodigoAC(codigoACOrigim);
		tEmpresaOrigin.setGrupoAC(grupoACOrigim);

		Empresa empresaOrigem = EmpresaFactory.getEmpresa();
		empresaOrigem.setCodigoAC(codigoACOrigim);
		empresaOrigem.setGrupoAC(grupoACOrigim);
		
		TEmpregado tEmpregado1 = new TEmpregado();
		tEmpregado1.setCodigoAC("tEmp1");
		TEmpregado tEmpregado2 = new TEmpregado();
		tEmpregado2.setCodigoAC("tEmp2");
		TEmpregado tEmpregado3 = new TEmpregado();
		tEmpregado3.setCodigoAC("tEmp3");
		
		TEmpregado[] tEmpregados = new TEmpregado[]{tEmpregado1,tEmpregado2,tEmpregado3};
		String[] codigosAcDosColaboradores = tEmpregadoToArrayCodigoAC(tEmpregados);
		
		TSituacao tSituacao1 = new TSituacao();
		tSituacao1.setEmpregadoCodigoAC("tEmp1");
		TSituacao tSituacao2 = new TSituacao();
		tSituacao2.setEmpregadoCodigoAC("tEmp2");
		TSituacao tSituacao3 = new TSituacao();
		tSituacao3.setEmpregadoCodigoAC("tEmp3");
		TSituacao[] tSituacoes = new TSituacao[]{tSituacao1,tSituacao2,tSituacao3};
		
		tokenManager.expects(once()).method("findFirst").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new Token("TOKEN")));
		tokenManager.expects(once()).method("remove").with(ANYTHING).isVoid();
		empresaManager.expects(once()).method("findByCodigoAC").with(eq(tEmpresaOrigin.getCodigoAC()), eq(tEmpresaOrigin.getGrupoAC())).will(returnValue(empresaOrigem));
		empresaManager.expects(once()).method("findByCodigoAC").with(eq(empresaOrigem.getCodigoAC()), eq(empresaOrigem.getGrupoAC())).will(returnValue(empresaOrigem));
		colaboradorManager.expects(once()).method("desligaColaboradorAC").with(eq(empresaOrigem), ANYTHING, eq(codigosAcDosColaboradores)).will(returnValue(true));
		gerenciadorComunicacaoManager.expects(once()).method("enviaAvisoDesligamentoColaboradorAC").
														with(eq(empresaOrigem.getCodigoAC()), eq(empresaOrigem.getGrupoAC()), eq(empresaOrigem), eq(codigosAcDosColaboradores)).isVoid();
		FeedbackWebService feedbackWebService = rHServiceImpl.transferir("TOKEN", tEmpresaOrigin, new TEmpresa(), tEmpregados, tSituacoes, "10/07/2014");
		
		assertTrue(feedbackWebService.isSucesso());
	}

	public void testTransferirComEmpresaOrigemIntegradaComErroaAoDesligar() throws Exception
	{
		String codigoACOrigim = "codigoACOrigim";
		String grupoACOrigim = "grupoACOrigim";
		
		TEmpresa tEmpresaOrigin = new TEmpresa();
		tEmpresaOrigin.setCodigoAC(codigoACOrigim);
		tEmpresaOrigin.setGrupoAC(grupoACOrigim);
		
		Empresa empresaOrigem = EmpresaFactory.getEmpresa();
		empresaOrigem.setCodigoAC(codigoACOrigim);
		empresaOrigem.setGrupoAC(grupoACOrigim);
		
		TEmpregado tEmpregado1 = new TEmpregado();
		tEmpregado1.setCodigoAC("tEmp1");
		TEmpregado[] tEmpregados = new TEmpregado[]{tEmpregado1};
		
		TSituacao tSituacao1 = new TSituacao();
		tSituacao1.setEmpregadoCodigoAC("tEmp1");
		TSituacao[] tSituacoes = new TSituacao[]{tSituacao1};
		
		String[] codigosAcDosColaboradores = tEmpregadoToArrayCodigoAC(tEmpregados);
		
		tokenManager.expects(once()).method("findFirst").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new Token("TOKEN")));
		tokenManager.expects(once()).method("remove").with(ANYTHING).isVoid();
		empresaManager.expects(atLeastOnce()).method("findByCodigoAC").with(eq(tEmpresaOrigin.getCodigoAC()), eq(tEmpresaOrigin.getGrupoAC())).will(returnValue(empresaOrigem));
		colaboradorManager.expects(once()).method("desligaColaboradorAC").with(eq(empresaOrigem), ANYTHING, eq(codigosAcDosColaboradores)).will(returnValue(false));
		
		FeedbackWebService feedbackWebService = rHServiceImpl.transferir("TOKEN", tEmpresaOrigin, new TEmpresa(), tEmpregados, tSituacoes, "10/07/2014");
		
		assertFalse(feedbackWebService.isSucesso());
		assertEquals("Existem empregados que não foram encontrados no sistema RH", feedbackWebService.getMensagem());
	}
	
	public void testTransferirComEmpresaDestinoIntegrada() throws Exception
	{
		String codigoACDestino = "codigoACDestino";
		String grupoACDestino = "grupoACDestino";
		
		TEmpresa tEmpresaDestino = new TEmpresa();
		tEmpresaDestino.setCodigoAC(codigoACDestino);
		tEmpresaDestino.setGrupoAC(grupoACDestino);
		
		Empresa empresaDestino = EmpresaFactory.getEmpresa();
		empresaDestino.setCodigoAC(codigoACDestino);
		empresaDestino.setGrupoAC(grupoACDestino);
		
		TEmpregado tEmpregado1 = new TEmpregado();
		tEmpregado1.setCodigoAC("tEmp1");
		TEmpregado tEmpregado2 = new TEmpregado();
		tEmpregado2.setCodigoAC("tEmp2");
		TEmpregado tEmpregado3 = new TEmpregado();
		tEmpregado3.setCodigoAC("tEmp3");
		
		TEmpregado[] tEmpregados = new TEmpregado[]{tEmpregado1,tEmpregado2,tEmpregado3};
		
		TSituacao tSituacao = new TSituacao();
		tSituacao.setLotacaoCodigoAC("lotacaoCodigoAC");
		tSituacao.setCargoCodigoAC("cargoCodigoAC");
		tSituacao.setEstabelecimentoCodigoAC("estabelecimentoCodigoAC");
		
		TSituacao tSituacao1 = tSituacao;
		tSituacao1.setEmpregadoCodigoAC("tEmp1");
		TSituacao tSituacao2 = tSituacao;
		tSituacao2.setEmpregadoCodigoAC("tEmp2");
		TSituacao tSituacao3 = tSituacao;
		tSituacao3.setEmpregadoCodigoAC("tEmp3");
		TSituacao[] tSituacoes = new TSituacao[]{tSituacao1,tSituacao2,tSituacao3};
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setCodigoAC(tSituacao.getLotacaoCodigoAC());
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setCodigoAC(tSituacao.getLotacaoCodigoAC());
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCodigoAC(tSituacao.getCargoCodigoAC());
		
		tokenManager.expects(once()).method("findFirst").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new Token("TOKEN")));
		tokenManager.expects(once()).method("remove").with(ANYTHING).isVoid();
		empresaManager.expects(once()).method("findByCodigoAC").with(eq(tEmpresaDestino.getCodigoAC()), eq(tEmpresaDestino.getGrupoAC())).will(returnValue(empresaDestino));
		estabelecimentoManager.expects(once()).method("findEstabelecimentoByCodigoAc").with(eq(tSituacoes[0].getEstabelecimentoCodigoAC()),eq(tEmpresaDestino.getCodigoAC()), eq(tEmpresaDestino.getGrupoAC())).will(returnValue(estabelecimento));
		areaOrganizacionalManager.expects(once()).method("findAreaOrganizacionalByCodigoAc").with(eq(tSituacoes[0].getLotacaoCodigoAC()),eq(tEmpresaDestino.getCodigoAC()), eq(tEmpresaDestino.getGrupoAC())).will(returnValue(areaOrganizacional));
		faixaSalarialManager.expects(once()).method("findFaixaSalarialByCodigoAc").with(eq(tSituacoes[0].getCargoCodigoAC()),eq(tEmpresaDestino.getCodigoAC()), eq(tEmpresaDestino.getGrupoAC())).will(returnValue(faixaSalarial));
		colaboradorManager.expects(once()).method("saveEmpregadosESituacoes").with(eq(tEmpregados), eq(tSituacoes), eq(empresaDestino)).isVoid();
		
		FeedbackWebService feedbackWebService = rHServiceImpl.transferir("TOKEN", new TEmpresa(), tEmpresaDestino, tEmpregados, tSituacoes, "10/07/2014");
		
		assertTrue(feedbackWebService.isSucesso());
	}
	
	public void testTransferirComEmpresaDestinoIntegradaSemEstabelecimentoAreaCargoIntegradas() throws Exception
	{
		String codigoACDestino = "codigoACDestino";
		String grupoACDestino = "grupoACDestino";
		
		TEmpresa tEmpresaDestino = new TEmpresa();
		tEmpresaDestino.setCodigoAC(codigoACDestino);
		tEmpresaDestino.setGrupoAC(grupoACDestino);
		
		Empresa empresaDestino = EmpresaFactory.getEmpresa();
		empresaDestino.setCodigoAC(codigoACDestino);
		empresaDestino.setGrupoAC(grupoACDestino);
		
		TEmpregado tEmpregado1 = new TEmpregado();
		tEmpregado1.setCodigoAC("tEmp1");
		TEmpregado tEmpregado2 = new TEmpregado();
		tEmpregado2.setCodigoAC("tEmp2");
		TEmpregado tEmpregado3 = new TEmpregado();
		tEmpregado3.setCodigoAC("tEmp3");
		
		TEmpregado[] tEmpregados = new TEmpregado[]{tEmpregado1,tEmpregado2,tEmpregado3};
		
		TSituacao tSituacao = new TSituacao();
		tSituacao.setLotacaoCodigoAC("lotacaoCodigoAC");
		tSituacao.setCargoCodigoAC("cargoCodigoAC");
		tSituacao.setEstabelecimentoCodigoAC("estabelecimentoCodigoAC");
		
		TSituacao tSituacao1 = tSituacao;
		tSituacao1.setEmpregadoCodigoAC("tEmp1");
		TSituacao tSituacao2 = tSituacao;
		tSituacao2.setEmpregadoCodigoAC("tEmp2");
		TSituacao tSituacao3 = tSituacao;
		tSituacao3.setEmpregadoCodigoAC("tEmp3");
		TSituacao[] tSituacoes = new TSituacao[]{tSituacao1,tSituacao2,tSituacao3};
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setCodigoAC(tSituacao.getLotacaoCodigoAC());
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setCodigoAC(tSituacao.getLotacaoCodigoAC());
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCodigoAC(tSituacao.getCargoCodigoAC());
		
		tokenManager.expects(once()).method("findFirst").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new Token("TOKEN")));
		tokenManager.expects(once()).method("remove").with(ANYTHING).isVoid();
		empresaManager.expects(once()).method("findByCodigoAC").with(eq(tEmpresaDestino.getCodigoAC()), eq(tEmpresaDestino.getGrupoAC())).will(returnValue(empresaDestino));
		estabelecimentoManager.expects(once()).method("findEstabelecimentoByCodigoAc").with(eq(tSituacoes[0].getEstabelecimentoCodigoAC()),eq(tEmpresaDestino.getCodigoAC()), eq(tEmpresaDestino.getGrupoAC())).will(returnValue(null));
		areaOrganizacionalManager.expects(once()).method("findAreaOrganizacionalByCodigoAc").with(eq(tSituacoes[0].getLotacaoCodigoAC()),eq(tEmpresaDestino.getCodigoAC()), eq(tEmpresaDestino.getGrupoAC())).will(returnValue(null));
		faixaSalarialManager.expects(once()).method("findFaixaSalarialByCodigoAc").with(eq(tSituacoes[0].getCargoCodigoAC()),eq(tEmpresaDestino.getCodigoAC()), eq(tEmpresaDestino.getGrupoAC())).will(returnValue(null));
		
		FeedbackWebService feedbackWebService = rHServiceImpl.transferir("TOKEN", new TEmpresa(), tEmpresaDestino, tEmpregados, tSituacoes, "10/07/2014");
		
		assertFalse(feedbackWebService.isSucesso());
		assertEquals("Existem inconsistências de integração com o sistema RH na empresa destino.", feedbackWebService.getMensagem());
		
		String exceptionRetorno = feedbackWebService.getException();
		assertTrue(exceptionRetorno.contains("O estabelecimento destino não existe no sistema RH\nA área organizacional destino não existe no sistema RH\nO cargo destino não existe no sistema RH\n\n\n"));
	}

	public void testTransferirComEmpresaOrigemEDestinoIntegrada() throws Exception
	{
		String codigoACOrigim = "codigoACOrigim";
		String grupoACOrigim = "grupoACOrigim";
		
		String codigoACDestino = "codigoACDestino";
		String grupoACDestino = "grupoACDestino";
		
		TEmpresa tEmpresaOrigin = new TEmpresa();
		tEmpresaOrigin.setCodigoAC(codigoACOrigim);
		tEmpresaOrigin.setGrupoAC(grupoACOrigim);
		
		TEmpresa tEmpresaDestino = new TEmpresa();
		tEmpresaDestino.setCodigoAC(codigoACDestino);
		tEmpresaDestino.setGrupoAC(grupoACDestino);
		
		Empresa empresaOrigem = EmpresaFactory.getEmpresa();
		empresaOrigem.setCodigoAC(codigoACOrigim);
		empresaOrigem.setGrupoAC(grupoACOrigim);
		
		Empresa empresaDestino = EmpresaFactory.getEmpresa();
		empresaDestino.setCodigoAC(codigoACDestino);
		empresaDestino.setGrupoAC(grupoACDestino);
		
		TEmpregado tEmpregado1 = new TEmpregado();
		tEmpregado1.setCodigoAC("tEmp1");
		TEmpregado tEmpregado2 = new TEmpregado();
		tEmpregado2.setCodigoAC("tEmp2");
		TEmpregado tEmpregado3 = new TEmpregado();
		tEmpregado3.setCodigoAC("tEmp3");
		
		TEmpregado[] tEmpregados = new TEmpregado[]{tEmpregado1,tEmpregado2,tEmpregado3};
		
		TSituacao tSituacao = new TSituacao();
		tSituacao.setLotacaoCodigoAC("lotacaoCodigoAC");
		tSituacao.setCargoCodigoAC("cargoCodigoAC");
		tSituacao.setEstabelecimentoCodigoAC("estabelecimentoCodigoAC");
		
		TSituacao tSituacao1 = tSituacao;
		tSituacao1.setEmpregadoCodigoAC("tEmp1");
		TSituacao tSituacao2 = tSituacao;
		tSituacao2.setEmpregadoCodigoAC("tEmp2");
		TSituacao tSituacao3 = tSituacao;
		tSituacao3.setEmpregadoCodigoAC("tEmp3");
		TSituacao[] tSituacoes = new TSituacao[]{tSituacao1,tSituacao2,tSituacao3};
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setCodigoAC(tSituacao.getLotacaoCodigoAC());
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setCodigoAC(tSituacao.getLotacaoCodigoAC());
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCodigoAC(tSituacao.getCargoCodigoAC());
		
		String dataDesligamento = "10/07/2014";
		
		String[] codigosAcDosColaboradores = tEmpregadoToArrayCodigoAC(tEmpregados);
		
		tokenManager.expects(once()).method("findFirst").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new Token("TOKEN")));
		tokenManager.expects(once()).method("remove").with(ANYTHING).isVoid();
		empresaManager.expects(atLeastOnce()).method("findByCodigoAC").with(eq(tEmpresaOrigin.getCodigoAC()), eq(tEmpresaOrigin.getGrupoAC())).will(returnValue(empresaOrigem));
		empresaManager.expects(once()).method("findByCodigoAC").with(eq(tEmpresaDestino.getCodigoAC()), eq(tEmpresaDestino.getGrupoAC())).will(returnValue(empresaDestino));
		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		transactionManager.expects(once()).method("commit").with(ANYTHING);
		colaboradorManager.expects(once()).method("desligaColaboradorAC").with(eq(empresaOrigem), ANYTHING, eq(codigosAcDosColaboradores)).will(returnValue(true));
		gerenciadorComunicacaoManager.expects(once()).method("enviaAvisoDesligamentoColaboradorAC").with(eq(tEmpresaOrigin.getCodigoAC()),eq(tEmpresaOrigin.getGrupoAC()),eq(empresaOrigem), eq(codigosAcDosColaboradores)).isVoid();
		estabelecimentoManager.expects(once()).method("findEstabelecimentoByCodigoAc").with(eq(tSituacoes[0].getEstabelecimentoCodigoAC()),eq(tEmpresaDestino.getCodigoAC()), eq(tEmpresaDestino.getGrupoAC())).will(returnValue(estabelecimento));
		areaOrganizacionalManager.expects(once()).method("findAreaOrganizacionalByCodigoAc").with(eq(tSituacoes[0].getLotacaoCodigoAC()),eq(tEmpresaDestino.getCodigoAC()), eq(tEmpresaDestino.getGrupoAC())).will(returnValue(areaOrganizacional));
		faixaSalarialManager.expects(once()).method("findFaixaSalarialByCodigoAc").with(eq(tSituacoes[0].getCargoCodigoAC()),eq(tEmpresaDestino.getCodigoAC()), eq(tEmpresaDestino.getGrupoAC())).will(returnValue(faixaSalarial));
		colaboradorManager.expects(once()).method("saveEmpregadosESituacoes").with(eq(tEmpregados), eq(tSituacoes), eq(empresaDestino)).isVoid();
		
		FeedbackWebService feedbackWebService = rHServiceImpl.transferir("TOKEN", tEmpresaOrigin, tEmpresaDestino, tEmpregados, tSituacoes, dataDesligamento);
		
		assertTrue(feedbackWebService.isSucesso());
	}
	
	private String[] tEmpregadoToArrayCodigoAC(TEmpregado[] empregados) 
	{
		String[] codigosEmpregados = new String[empregados.length];
		
		for(int i = 0; i < empregados.length; i++)
			codigosEmpregados[i] = ((TEmpregado) empregados[i]).getCodigoAC();
		
		return codigosEmpregados;
	}
	
	public void testExistePesquisaParaSerRespondida()
	{
		String empregadoCodigoAC = "001";
		String empresaCodigoAC = "001";
		String grupoAC = "001";
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(1L);
		empresa.setCodigoAC(empresaCodigoAC);
		empresa.setGrupoAC(grupoAC);

		empresaManager.expects(once()).method("findByCodigoAC").with(eq(empresa.getCodigoAC()), eq(empresa.getGrupoAC())).will(returnValue(empresa));
		pesquisaManager.expects(once()).method("existePesquisaParaSerRespondida").with(eq(empregadoCodigoAC), eq(empresa.getId())).will(returnValue(true));
		
		assertTrue("Existe Pesquisa a ser Respondida", rHServiceImpl.existePesquisaParaSerRespondida(empregadoCodigoAC, empresaCodigoAC, grupoAC));
	}
	
	public void testExistePesquisaParaSerRespondidaComParametrosNulos()
	{
		String empregadoCodigoAC = null;
		String empresaCodigoAC = null;
		String grupoAC = null;
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(1L);
		empresa.setCodigoAC(empresaCodigoAC);
		empresa.setGrupoAC(grupoAC);
		
		empresaManager.expects(once()).method("findByCodigoAC").with(eq(empresa.getCodigoAC()), eq(empresa.getGrupoAC())).will(returnValue(empresa));
		
		assertFalse("Existe Pesquisa a ser Respondida", rHServiceImpl.existePesquisaParaSerRespondida(empregadoCodigoAC, empresaCodigoAC, grupoAC));
	}
	
	public void testExistePesquisaParaSerRespondidaException()
	{
		String empregadoCodigoAC = "001";
		String empresaCodigoAC = "001";
		String grupoAC = "001";

		rHServiceImpl.setEmpresaManager(null);
		
		assertFalse("Existe Pesquisa a ser Respondida", rHServiceImpl.existePesquisaParaSerRespondida(empregadoCodigoAC, empresaCodigoAC, grupoAC));
	}

	public void testAtualizarMovimentacaoEmLote()
	{
		String codPessoalEstabOuArea = "001";
		String[] empregadoCodigos = new String[]{"0025", "0026"};
		String empresaCodigoAC = null;
		String grupoAC = null;
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(1L);
		empresa.setCodigoAC(empresaCodigoAC);
		empresa.setGrupoAC(grupoAC);
		
		tokenManager.expects(once()).method("findFirst").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new Token("TOKEN")));
		tokenManager.expects(once()).method("remove").with(ANYTHING).isVoid();
		empresaManager.expects(once()).method("findByCodigoAC").with(eq(empresa.getCodigoAC()), eq(empresa.getGrupoAC())).will(returnValue(empresa));
		areaOrganizacionalManager.expects(once()).method("possuiAreaFilhasByCodigoAC").with(eq(codPessoalEstabOuArea),eq(empresa.getId())).will(returnValue(false));
		historicoColaboradorManager.expects(atLeastOnce()).method("updateSituacaoByMovimentacao").withAnyArguments();
		
		FeedbackWebService feedbackWebService = rHServiceImpl.atualizarMovimentacaoEmLote("TOKEN", empregadoCodigos, MovimentacaoAC.AREA, codPessoalEstabOuArea, false, empresa.getCodigoAC(), empresa.getGrupoAC());
		assertTrue(feedbackWebService.isSucesso());
	}
	
	public void testAtualizarMovimentacaoEmLoteEmpresaNula()
	{
		String codPessoalEstabOuArea = "001";
		String[] empregadoCodigos = new String[]{"0025", "0026"};
		String empresaCodigoAC = null;
		String grupoAC = null;
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(1L);
		empresa.setCodigoAC(empresaCodigoAC);
		empresa.setGrupoAC(grupoAC);
		
		tokenManager.expects(once()).method("findFirst").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new Token("TOKEN")));
		tokenManager.expects(once()).method("remove").with(ANYTHING).isVoid();
		empresaManager.expects(once()).method("findByCodigoAC").with(eq(empresa.getCodigoAC()), eq(empresa.getGrupoAC())).will(returnValue(null));
		
		FeedbackWebService feedbackWebService = rHServiceImpl.atualizarMovimentacaoEmLote("TOKEN", empregadoCodigos, MovimentacaoAC.AREA, codPessoalEstabOuArea, false, empresa.getCodigoAC(), empresa.getGrupoAC());
		assertEquals("Empresa não encontrada no sistema RH.", feedbackWebService.getMensagem());
	}
	
	public void testAtualizarMovimentacaoEmLoteMivimentacaoNaoEncontrada()
	{
		String codPessoalEstabOuArea = "001";
		String[] empregadoCodigos = new String[]{"0025", "0026"};
		String empresaCodigoAC = null;
		String grupoAC = null;
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(1L);
		empresa.setCodigoAC(empresaCodigoAC);
		empresa.setGrupoAC(grupoAC);
		
		tokenManager.expects(once()).method("findFirst").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new Token("TOKEN")));
		tokenManager.expects(once()).method("remove").with(ANYTHING).isVoid();
		empresaManager.expects(once()).method("findByCodigoAC").with(eq(empresa.getCodigoAC()), eq(empresa.getGrupoAC())).will(returnValue(empresa));
		
		FeedbackWebService feedbackWebService = rHServiceImpl.atualizarMovimentacaoEmLote("TOKEN", empregadoCodigos, "bla", codPessoalEstabOuArea, false, empresa.getCodigoAC(), empresa.getGrupoAC());
		assertEquals("Movimentação não encontrada no RH.", feedbackWebService.getMensagem());
	}
	
	public void testAtualizarMovimentacaoEmLotePossuiAreaFilha()
	{
		String codPessoalEstabOuArea = "001";
		String[] empregadoCodigos = new String[]{"0025", "0026"};
		String empresaCodigoAC = null;
		String grupoAC = null;
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(1L);
		empresa.setCodigoAC(empresaCodigoAC);
		empresa.setGrupoAC(grupoAC);
		
		tokenManager.expects(once()).method("findFirst").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new Token("TOKEN")));
		tokenManager.expects(once()).method("remove").with(ANYTHING).isVoid();
		empresaManager.expects(once()).method("findByCodigoAC").with(eq(empresa.getCodigoAC()), eq(empresa.getGrupoAC())).will(returnValue(empresa));
		areaOrganizacionalManager.expects(once()).method("possuiAreaFilhasByCodigoAC").with(eq(codPessoalEstabOuArea),eq(empresa.getId())).will(returnValue(true));
		
		FeedbackWebService feedbackWebService = rHServiceImpl.atualizarMovimentacaoEmLote("TOKEN", empregadoCodigos, MovimentacaoAC.AREA, codPessoalEstabOuArea, false, empresa.getCodigoAC(), empresa.getGrupoAC());
		assertEquals("Não foi possível realizar a atualização em lote. A lotação é mãe de outras lotações.", feedbackWebService.getMensagem());
	}
	
	public void testExisteColaboradorAtivoTrue()
	{
		String cpf = "01345678912";
		colaboradorManager.expects(once()).method("existeColaboradorAtivo").with(eq(cpf), ANYTHING).will(returnValue(true));
		assertTrue("Existe Colaborador Ativo", rHServiceImpl.existeColaboradorAtivo(cpf));
	}
	
	public void testExisteColaboradorAtivoFalse()
	{
		String cpf = "01345678912";
		colaboradorManager.expects(once()).method("existeColaboradorAtivo").with(eq(cpf), ANYTHING).will(returnValue(false));
		assertFalse("Não existe Colaborador Ativo", rHServiceImpl.existeColaboradorAtivo(cpf));
	}
}
