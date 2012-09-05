
package com.fortes.rh.web.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManager;
import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.geral.UsuarioMensagemManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.geral.PendenciaAC;
import com.fortes.rh.model.geral.UsuarioMensagem;
import com.fortes.rh.model.geral.Video;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Pesquisa;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.security.MyDaoAuthenticationProvider;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.StringUtil;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionSupport;


@SuppressWarnings("unchecked")
public class Index extends ActionSupport
{
	private static final long serialVersionUID = 1L;
	private ColaboradorManager colaboradorManager = null;
	private QuestionarioManager questionarioManager;
	private UsuarioMensagemManager usuarioMensagemManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private FaixaSalarialHistoricoManager faixaSalarialHistoricoManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private AvaliacaoDesempenhoManager avaliacaoDesempenhoManager;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private AvaliacaoManager avaliacaoManager;
	private EmpresaManager empresaManager;

	private Collection<Pesquisa> pesquisasAtrasadas;

	private Collection<Pesquisa> pesquisasColaborador = new ArrayList<Pesquisa>();
	private Collection<Questionario> questionarios;
	private Collection<ColaboradorQuestionario> colaboradorQuestionariosTeD;
	private Collection<UsuarioMensagem> mensagems;
	private Collection<PendenciaAC> pendenciaACs = new ArrayList<PendenciaAC>();
	private Collection<ColaboradorQuestionario> avaliacoesDesempenhoPendentes = new ArrayList<ColaboradorQuestionario>();
	private Collection<Avaliacao> avaliacaos;

	private Long usuarioId;
	private Colaborador colaborador;
	private ParametrosDoSistema parametrosDoSistema;
	private Long empresaId;
    private String actionMsg;

	private boolean pgInicial = true;
	private boolean possuiMensagem = true;
	private boolean primeiraExecucao;
	private boolean idiomaIncorreto;
	
	private boolean integradoAC;
	private SolicitacaoManager solicitacaoManager;
	private Collection<Solicitacao> solicitacaos = new ArrayList<Solicitacao>();
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager; 
	private Collection<CandidatoSolicitacao> candidatoSolicitacaos = new ArrayList<CandidatoSolicitacao>();
	private MyDaoAuthenticationProvider authenticationProvider;
	private Collection<Video> listaVideos = new ArrayList<Video>();
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	

	public String index()
	{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findById(1L);
		ServletActionContext.getRequest().getSession().setMaxInactiveInterval(parametrosDoSistema.getSessionTimeout());
		
		if(ActionContext.getContext().getSession().get("primeiraExecucao") == null)
		{
			ActionContext.getContext().getSession().put("primeiraExecucao", "sim");
			primeiraExecucao = true;
		}

		try
		{
			usuarioId = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession()).getId();
			if (empresaId != null)
			{
				SecurityUtil.setEmpresaSession(ActionContext.getContext().getSession(), empresaManager.findById(empresaId));
				((MyDaoAuthenticationProvider)authenticationProvider).configuraPapeis(SecurityUtil.getUserDetails(ActionContext.getContext().getSession()), empresaId);
				//SecurityUtil.setMenuFormatadoSession(ActionContext.getContext().getSession(), "Marlus");
			}
			empresaId = SecurityUtil.getEmpresaSession(ActionContext.getContext().getSession()).getId();

			questionarios = questionarioManager.findQuestionarioPorUsuario(usuarioId);
			
			colaboradorQuestionariosTeD = colaboradorQuestionarioManager.findQuestionarioByTurmaLiberadaPorUsuario(usuarioId);
			
			colaborador = colaboradorManager.findByUsuario(SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession()), empresaId);

			if(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VISUALIZAR_MSG"}) )
			{
				mensagems = usuarioMensagemManager.listaUsuarioMensagem(usuarioId, empresaId);
				possuiMensagem = usuarioMensagemManager.possuiMensagemNaoLida(usuarioId, empresaId);
			}
			
			if (parametrosDoSistemaManager.isIdiomaCorreto())
				idiomaIncorreto = true;
			
			
			validaAvaliacoesDesempenho();
			
			if(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VISUALIZAR_SOLICITACAO_PESSOAL"}) )
			{
				solicitacaos = solicitacaoManager.findSolicitacaoList(empresaId, false, StatusAprovacaoSolicitacao.ANALISE, null);
				
				if (gerenciadorComunicacaoManager.existeConfiguracaoParaCandidatosModuloExterno(getEmpresaId()))
					candidatoSolicitacaos = candidatoSolicitacaoManager.findByFiltroSolicitacaoTriagem(true);
			}
			
			if(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_CAD_PERIODOEXPERIENCIA"}) )
				avaliacaos = avaliacaoManager.findPeriodoExperienciaIsNull(TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA, empresaId);
			
			validaIntegracaoAC();
			
			String msgAvisoQtdColaborador = colaboradorManager.avisoQtdCadastros();
			if (msgAvisoQtdColaborador != null)
				addActionMessage(msgAvisoQtdColaborador);
			
			if (StringUtils.isNotBlank(actionMsg))
				addActionMessage(actionMsg);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError(e.getMessage());
		}
        
		return Action.SUCCESS;
	}

	private void validaAvaliacoesDesempenho()
	{
		Collection<AvaliacaoDesempenho> avaliacaoDesempenhos = avaliacaoDesempenhoManager.findAllSelect(null, true, null);
		
		for (AvaliacaoDesempenho avaliacaoDesempenho : avaliacaoDesempenhos)
		{
			Collection<ColaboradorQuestionario> avaliadosComAvaliacaoPendente = colaboradorQuestionarioManager.findAvaliadosByAvaliador(avaliacaoDesempenho.getId(), colaborador.getId(), false, true);
			if (avaliadosComAvaliacaoPendente != null && !avaliadosComAvaliacaoPendente.isEmpty())
			{
				for (ColaboradorQuestionario colabQuestionarioAvaliado : avaliadosComAvaliacaoPendente)
				{
					avaliacoesDesempenhoPendentes.add(colabQuestionarioAvaliado);
				}
			}
		}
	}
	
	
	public String videoteca() throws Exception
	{
		pgInicial = false;
		
		try {
			InputStream is = new URL("http://www.fortesinformatica.com.br/portal_videoteca_rh.php?hash="+getCalculoHash()).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			
			String jsonText = readAll(rd);
			
			listaVideos = (Collection<Video>) StringUtil.simpleJSONtoArrayJava(jsonText, Video.class);
			
		
		} catch (Exception e) 
		{
			addActionError("A lista de vídeos não pôde ser carregada. Verifique a conexão do servidor com a internet.");
		}
		
		return Action.SUCCESS;
	}
	
	public String contatos() throws Exception
	{
		pgInicial = false;
		return Action.SUCCESS;
	}
	
	public String getCalculoHash()
	{
		String data = new SimpleDateFormat("ddMMyyyy").format(new Date());
		
		Integer calculoHash  = (Integer.parseInt(data) * 2) / 64;
		
		return StringUtil.encodeString(calculoHash.toString());
	}
	
	private static String readAll(Reader rd) throws IOException 
	{
		StringBuilder sb = new StringBuilder();
		int cp;
		
		while ((cp = rd.read()) != -1)
			sb.append((char) cp);
		
		return sb.toString();
	}
	
	private void validaIntegracaoAC() throws Exception 
	{
		integradoAC = SecurityUtil.getEmpresaSession(ActionContext.getContext().getSession()).isAcIntegra();
		
		if(integradoAC)
		{
			if(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"RECEBE_ALERTA_SETORPESSOAL"}))
			{
				String versaoWebServiceAC;

				try
				{
					if(! parametrosDoSistemaManager.isACIntegrado(SecurityUtil.getEmpresaSession(ActionContext.getContext().getSession())))
						addActionError("Atenção: A integração está ativa no Fortes RH mas não no AC Pessoal. Por favor, comunique ao administrador do sistema.");
					versaoWebServiceAC = parametrosDoSistemaManager.getVersaoWebServiceAC(SecurityUtil.getEmpresaSession(ActionContext.getContext().getSession()));
				}
				catch (Exception e)
				{
					e.printStackTrace();
					String msg = (e.getCause() instanceof java.net.ConnectException) ? "Verifique se o mesmo está funcionando corretamente." : e.getMessage();
					throw new Exception("Erro ao verificar conexão com o WebService. " + msg);
				}

				parametrosDoSistema = parametrosDoSistemaManager.findByIdProjection(1L);

				String versaoMinimaWebServicxeCompativel = parametrosDoSistema.getAcVersaoWebServiceCompativel();

				boolean rhCompativelComAC  = parametrosDoSistemaManager.verificaCompatibilidadeComWebServiceAC(versaoWebServiceAC, versaoMinimaWebServicxeCompativel);

				if(!rhCompativelComAC)
					addActionError("A versão do WebService do AC Pessoal ("+versaoWebServiceAC+") está diferente da versão ("+versaoMinimaWebServicxeCompativel+") exigida pelo RH.");
			}

			if(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VISUALIZAR_PENDENCIA_AC"}))
			{
				pendenciaACs.addAll(historicoColaboradorManager.findPendenciasByHistoricoColaborador(empresaId));
				pendenciaACs.addAll(faixaSalarialHistoricoManager.findPendenciasByFaixaSalarialHistorico(empresaId));
				pendenciaACs.addAll(colaboradorManager.findPendencias(empresaId));
			}
		}
	}
	
	
	
	
	public String browsersCompativeis()
	{
		return Action.SUCCESS;
	}
	
	public void setCandidatoSolicitacaoManager(CandidatoSolicitacaoManager candidatoSolicitacaoManager) {
		this.candidatoSolicitacaoManager = candidatoSolicitacaoManager;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public Collection<Pesquisa> getPesquisasAtrasadas()
	{
		return pesquisasAtrasadas;
	}

	public void setPesquisasAtrasadas(Collection<Pesquisa> pesquisasAtrasadas)
	{
		this.pesquisasAtrasadas = pesquisasAtrasadas;
	}

	public Collection<Pesquisa> getPesquisasColaborador()
	{
		return pesquisasColaborador;
	}

	public void setPesquisasColaborador(Collection<Pesquisa> pesquisasColaborador)
	{
		this.pesquisasColaborador = pesquisasColaborador;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public void setQuestionarioManager(QuestionarioManager questionarioManager)
	{
		this.questionarioManager = questionarioManager;
	}

	public Long getUsuarioId()
	{
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId)
	{
		this.usuarioId = usuarioId;
	}

	public Long getEmpresaId()
	{
		return empresaId;
	}

	public void setEmpresaId(Long empresaId)
	{
		this.empresaId = empresaId;
	}

	public Collection<Questionario> getQuestionarios()
	{
		return questionarios;
	}

	public void setQuestionarios(Collection<Questionario> questionarios)
	{
		this.questionarios = questionarios;
	}

	public void setUsuarioMensagemManager(UsuarioMensagemManager usuarioMensagemManager)
	{
		this.usuarioMensagemManager = usuarioMensagemManager;
	}

	public void setMensagems(Collection<UsuarioMensagem> mensagems)
	{
		this.mensagems = mensagems;
	}

	public Collection<UsuarioMensagem> getMensagems()
	{
		return mensagems;
	}

	public boolean isPgInicial()
	{
		return pgInicial;
	}

	public boolean isPossuiMensagem()
	{
		return possuiMensagem;
	}

	public void setPossuiMensagem(boolean possuiMensagem)
	{
		this.possuiMensagem = possuiMensagem;
	}

	public void setActionMsg(String actionMsg)
	{
		this.actionMsg = actionMsg;
	}

	public void setFaixaSalarialHistoricoManager(FaixaSalarialHistoricoManager faixaSalarialHistoricoManager)
	{
		this.faixaSalarialHistoricoManager = faixaSalarialHistoricoManager;
	}

	public void setHistoricoColaboradorManager(HistoricoColaboradorManager historicoColaboradorManager)
	{
		this.historicoColaboradorManager = historicoColaboradorManager;
	}

	public Collection<PendenciaAC> getPendenciaACs()
	{
		return pendenciaACs;
	}

	public void setPendenciaACs(Collection<PendenciaAC> pendenciaACs)
	{
		this.pendenciaACs = pendenciaACs;
	}

	public boolean isIntegradoAC()
	{
		return integradoAC;
	}

	public void setIntegradoAC(boolean integradoAC)
	{
		this.integradoAC = integradoAC;
	}

	public boolean isPrimeiraExecucao()
	{
		return primeiraExecucao;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager)
	{
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public ParametrosDoSistema getParametrosDoSistema()
	{
		return parametrosDoSistema;
	}

	public void setSolicitacaoManager(SolicitacaoManager solicitacaoManager) {
		this.solicitacaoManager = solicitacaoManager;
	}

	public Collection<Solicitacao> getSolicitacaos() {
		return solicitacaos;
	}

	public CandidatoSolicitacaoManager getCandidatoSolicitacaoManager() {
		return candidatoSolicitacaoManager;
	}

	public Collection<CandidatoSolicitacao> getCandidatoSolicitacaos() {
		return candidatoSolicitacaos;
	}

	public void setAvaliacaoDesempenhoManager(AvaliacaoDesempenhoManager avaliacaoDesempenhoManager) {
		this.avaliacaoDesempenhoManager = avaliacaoDesempenhoManager;
	}

	public Collection<ColaboradorQuestionario> getAvaliacoesDesempenhoPendentes() {
		return avaliacoesDesempenhoPendentes;
	}

	public void setColaboradorQuestionarioManager(ColaboradorQuestionarioManager colaboradorQuestionarioManager) {
		this.colaboradorQuestionarioManager = colaboradorQuestionarioManager;
	}

	public boolean isIdiomaIncorreto() {
		return idiomaIncorreto;
	}

	public Collection<Avaliacao> getAvaliacaos() {
		return avaliacaos;
	}

	public void setAvaliacaoManager(AvaliacaoManager avaliacaoManager) {
		this.avaliacaoManager = avaliacaoManager;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public void setAuthenticationProvider(MyDaoAuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
	}

	public Collection<ColaboradorQuestionario> getColaboradorQuestionariosTeD() {
		return colaboradorQuestionariosTeD;
	}

	public Collection<Video> getListaVideos() {
		return listaVideos;
	}

	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}
}
