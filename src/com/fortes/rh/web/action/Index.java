
package com.fortes.rh.web.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.NoticiaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.geral.UsuarioMensagemManager;
import com.fortes.rh.business.geral.UsuarioNoticiaManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.dicionario.TipoMensagem;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.geral.CaixaMensagem;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ConfiguracaoCaixasMensagens;
import com.fortes.rh.model.geral.Noticia;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.geral.PendenciaAC;
import com.fortes.rh.model.geral.Video;
import com.fortes.rh.model.geral.relatorio.MensagemVO;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Pesquisa;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.security.MyDaoAuthenticationProvider;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.StringUtil;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;


@SuppressWarnings("unchecked")
public class Index extends MyActionSupport
{
	private static final long serialVersionUID = 1L;
	private ColaboradorManager colaboradorManager = null;
	private UsuarioMensagemManager usuarioMensagemManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private FaixaSalarialHistoricoManager faixaSalarialHistoricoManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private AvaliacaoManager avaliacaoManager;
	private EmpresaManager empresaManager;
	private NoticiaManager noticiaManager;
	private UsuarioNoticiaManager usuarioNoticiaManager;

	private Collection<Pesquisa> pesquisasAtrasadas;

	private Collection<Pesquisa> pesquisasColaborador = new ArrayList<Pesquisa>();
	private Collection<Questionario> questionarios;
	private Collection<ColaboradorQuestionario> colaboradorQuestionariosTeD;
	private Map<Character, CaixaMensagem> caixasMensagens;
	private Collection<PendenciaAC> pendenciaACs = new ArrayList<PendenciaAC>();
	private Collection<ColaboradorQuestionario> avaliacoesDesempenhoPendentes = new ArrayList<ColaboradorQuestionario>();
	private Collection<Avaliacao> avaliacaos;

	private Long usuarioId;
	private Usuario usuario;
	private Colaborador colaborador;
	private ParametrosDoSistema parametrosDoSistema;
	private Long empresaId;
	private Long colaboradorId;
	private Long historicoColaboradorId;
	private Long faixaSalarialHistoricoId;
    private String actionMsg;

	private boolean pgInicial = true;
	private boolean primeiraExecucao;
	private boolean idiomaIncorreto;
	private boolean bancoConsistente;
	
	private boolean integradoAC;
	private Collection<Solicitacao> solicitacaos = new ArrayList<Solicitacao>();
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager; 
	private Collection<CandidatoSolicitacao> candidatoSolicitacaos = new ArrayList<CandidatoSolicitacao>();
	private MyDaoAuthenticationProvider authenticationProvider;
	private Collection<Video> listaVideos = new ArrayList<Video>();
	
	private ConfiguracaoCaixasMensagens configuracaoCaixasMensagens;
	private Character tipo;
	
	private String modulo;
	private String contexto;
	private String[] pendenciasAcsSerRemovida = {};
	
	private Noticia noticia;
	
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
		
		//bancoConsistente = parametrosDoSistema.isBancoConsistente();

		try
		{
			usuario = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession());
			
			if (empresaId != null)
			{
				SecurityUtil.removeSessaoFiltrosConfiguracoes();
				SecurityUtil.setEmpresaSession(ActionContext.getContext().getSession(), empresaManager.findById(empresaId));
				((MyDaoAuthenticationProvider)authenticationProvider).configuraPapeis(SecurityUtil.getUserDetails(ActionContext.getContext().getSession()), empresaId);
			}
			
			configuracaoCaixasMensagens = (ConfiguracaoCaixasMensagens) StringUtil.simpleJSONObjecttoArrayJava(usuario.getCaixasMensagens(), ConfiguracaoCaixasMensagens.class);
			
			usuarioId = usuario.getId();
			empresaId = SecurityUtil.getEmpresaSession(ActionContext.getContext().getSession()).getId();

			colaborador = colaboradorManager.findByUsuario(usuario, empresaId);

			if (SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VISUALIZAR_MSG"}) )
			{
				caixasMensagens = usuarioMensagemManager.listaMensagens(usuarioId, empresaId, colaborador.getId());
			}
			
			if (parametrosDoSistemaManager.isIdiomaCorreto())
			{
				idiomaIncorreto = true;
			}
			
			if (SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_CAD_PERIODOEXPERIENCIA"}) )
				avaliacaos = avaliacaoManager.findPeriodoExperienciaIsNull(TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA, empresaId);
			
			validaIntegracaoAC();
			
			String msgAvisoQtdColaborador = null;
			if(parametrosDoSistema.verificaRemprot())
				msgAvisoQtdColaborador = colaboradorManager.avisoQtdCadastros();
			
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
	
	public String mensagens()
	{
		pgInicial = false;
		
		try {
			contexto = parametrosDoSistemaManager.getContexto();
			usuario = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession());
			usuarioId = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession()).getId();
			empresaId = SecurityUtil.getEmpresaSession(ActionContext.getContext().getSession()).getId();
			colaborador = colaboradorManager.findByUsuario(usuario, empresaId);
			
			if (SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VISUALIZAR_MSG"}) )
			{
				caixasMensagens = usuarioMensagemManager.listaMensagens(usuarioId, empresaId, colaborador.getId());
			}
		
		} catch (Exception e) 
		{
			e.printStackTrace();
			addActionError("Ocorreu um erro ao carregar a lista de mensagens");
			index();
			return Action.INPUT;
		}

		return Action.SUCCESS;
	}
	
	public Collection<MensagemVO> getMensagens(char tipoMensagem)
	{
		return caixasMensagens.get(tipoMensagem).getMensagens();
	}

	public int getTotalNaoLidas(char tipoMensagem)
	{
		return caixasMensagens.get(tipoMensagem).getNaoLidas();
	}
	
	public String getDescricaoTipo(char tipoMensagem)
	{
		return new TipoMensagem(true).get(tipoMensagem);
	}

	public String videoteca() throws Exception
	{
		pgInicial = false;
		
		try {
			InputStream is = new URL("http://www.fortesinformatica.com.br/portaldocliente2/portal_videoteca_rh.php?hash="+getCalculoHash()).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			
			String jsonText = readAll(rd);
			
			listaVideos = (Collection<Video>) StringUtil.simpleJSONtoArrayJava(jsonText, Video.class);
			listaVideos = new CollectionUtil<Video>().sortCollectionStringIgnoreCase(listaVideos, "titulo");
			
			if (!StringUtil.isBlank(modulo))
			{
				CollectionUtils.filter(listaVideos, new Predicate() {
														public boolean evaluate(Object obj) {
															return (((Video)obj).getTitulo().startsWith(modulo));
														}
													});
			}
		
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
			if(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"RECEBE_ALERTA_ERRO_CONEXAO_ACPESSOAL"}))
			{
				String versaoWebServiceAC;

				try
				{
					if(! parametrosDoSistemaManager.isACIntegrado(SecurityUtil.getEmpresaSession(ActionContext.getContext().getSession())))
						addActionError("Atenção: A integração está ativa no RH mas não no Fortes Pessoal. Por favor, comunique ao administrador do sistema.");
					versaoWebServiceAC = parametrosDoSistemaManager.getVersaoWebServiceAC(SecurityUtil.getEmpresaSession(ActionContext.getContext().getSession()));
				}
				catch (Exception e)
				{
					e.printStackTrace();
					String msg = (e.getCause() instanceof java.net.ConnectException) ? "Verifique se o mesmo está funcionando corretamente." : e.getMessage();
					throw new Exception("Erro ao verificar conexão com o webservice do Fortes Pessoal. " + msg);
				}

				parametrosDoSistema = parametrosDoSistemaManager.findByIdProjection(1L);

				String versaoMinimaWebServicxeCompativel = parametrosDoSistema.getAcVersaoWebServiceCompativel();

				boolean rhCompativelComAC  = parametrosDoSistemaManager.verificaCompatibilidadeComWebServiceAC(versaoWebServiceAC, versaoMinimaWebServicxeCompativel);

				if(!rhCompativelComAC)
					addActionError("A versão do webservice do Fortes Pessoal é incompatível com essa versão do RH. Atualize a versão do webservice do Fortes Pessoal para que a integração funcione corretamente.");
			}

			if(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VISUALIZAR_PENDENCIA_AC"}))
			{
				pendenciaACs.addAll(historicoColaboradorManager.findPendenciasByHistoricoColaborador(empresaId));
				pendenciaACs.addAll(faixaSalarialHistoricoManager.findPendenciasByFaixaSalarialHistorico(empresaId));
				pendenciaACs.addAll(colaboradorManager.findPendenciasSolicitacaoDesligamento(empresaId));
			}
		}
	}
	
	public String removerMultiplasPendenciasAC()
	{
		for (String pendenciaAc : pendenciasAcsSerRemovida) 
		{
			Collection<String> ids = StringUtil.getSomenteNumero(pendenciaAc);

			if(pendenciaAc.contains("removePendenciaACHistoricoColaborador") && ids.size() == 2){
				historicoColaboradorId = new Long ((String) ids.toArray()[0]);
				colaboradorId = new Long ((String) ids.toArray()[1]);
				removePendenciaACHistoricoColaborador();
			} else if(pendenciaAc.contains("removePendenciaACColaborador") && ids.size() == 1){
				colaboradorId = new Long ((String) ids.toArray()[0]);
				removePendenciaACColaborador();
			} else if(pendenciaAc.contains("removePendenciaACHistoricoFaixaSalarial") && ids.size() == 1){
				faixaSalarialHistoricoId = new Long ((String) ids.toArray()[0]);
				removePendenciaACHistoricoFaixaSalarial();
			} else if(pendenciaAc.contains("removePendenciaACSolicitacaoDesligamento") && ids.size() == 1){
				colaboradorId = new Long ((String) ids.toArray()[0]);
				removePendenciaACSolicitacaoDesligamento();
			}
		}
		
		getActionSuccess().clear();
		if(getActionMessages().size() == 0){
			addActionSuccess("Pendências excluídas com sucesso.");
		}else{
			getActionMessages().clear();
			addActionMessage("Não foi possível excluir todas as pendências selecionadas.");
		}
		
		pendenciaACs.clear();
		index();
		return Action.SUCCESS;
	}
	
	public String removePendenciaACHistoricoColaborador()
	{
		try {
			historicoColaboradorManager.removeHistoricoAndReajuste(historicoColaboradorId, colaboradorId, getEmpresaSistema(), false);
			addActionSuccess("Novo histórico do colaborador excluído com sucesso.");
		}catch (Exception e)
		{
			String message = "Não foi possível excluir o novo histórico do colaborador.";

			if(e.getMessage() != null)
				message = e.getMessage();
			else if(e.getCause() != null && e.getCause().getLocalizedMessage() != null)
				message = e.getCause().getLocalizedMessage();

			addActionMessage(message);
		}
		index();
		return Action.SUCCESS;
	}
	
	public String removePendenciaACColaborador()
	{
		try {
			colaboradorManager.removeComDependencias(colaboradorId);
			addActionSuccess("Contratação do colaborador excluído com sucesso.");
		}catch (Exception e)
		{
			String message = "Não foi possível excluir a contratação do colaborador.";

			if(e.getMessage() != null)
				message = e.getMessage();
			else if(e.getCause() != null && e.getCause().getLocalizedMessage() != null)
				message = e.getCause().getLocalizedMessage();

			addActionMessage(message);
		}
		
		index();
		return Action.SUCCESS;
	}
	
	public String removePendenciaACHistoricoFaixaSalarial()
	{
		try {
			faixaSalarialHistoricoManager.remove(faixaSalarialHistoricoId, getEmpresaSistema(), false);
			addActionSuccess("Novo histórico da faixa salarial excluído com sucesso.");
		}catch (Exception e)
		{
			String message = "Não foi possível excluir o novo histórico da faixa salarial.";

			if(e.getMessage() != null)
				message = e.getMessage();
			else if(e.getCause() != null && e.getCause().getLocalizedMessage() != null)
				message = e.getCause().getLocalizedMessage();

			addActionMessage(message);
		}
		index();
		return Action.SUCCESS;
	}
	
	public String removePendenciaACSolicitacaoDesligamento()
	{
		try {
			colaboradorManager.religaColaborador(colaboradorId);
			addActionSuccess("Solicitação de desligamento removida com sucesso.");
		}catch (Exception e)
		{
			String message = "Não foi possível remover a solicitação de desligamento.";

			if(e.getMessage() != null)
				message = e.getMessage();
			else if(e.getCause() != null && e.getCause().getLocalizedMessage() != null)
				message = e.getCause().getLocalizedMessage();

			addActionMessage(message);
		}
		index();
		return Action.SUCCESS;
	}
	
	public String browsersCompativeis()
	{
		return Action.SUCCESS;
	}
	
	public String detalheNoticia()
	{
		try {
			usuarioNoticiaManager.marcarLida(getUsuarioLogado().getId(), noticia.getId());
			noticiaManager.carregarUltimasNoticias(getUsuarioLogado().getId());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
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

	public boolean isPgInicial()
	{
		return pgInicial;
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
	
	public void setPrimeiraExecucao(boolean primeiraExecucao) 
	{
		this.primeiraExecucao = primeiraExecucao;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager)
	{
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public ParametrosDoSistema getParametrosDoSistema()
	{
		return parametrosDoSistema;
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

	public Collection<ColaboradorQuestionario> getAvaliacoesDesempenhoPendentes() {
		return avaliacoesDesempenhoPendentes;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public ConfiguracaoCaixasMensagens getConfiguracaoCaixasMensagens() {
		return configuracaoCaixasMensagens;
	}

	public Map<Character, CaixaMensagem> getCaixasMensagens() {
		return caixasMensagens;
	}

	public Character getTipo() {
		return tipo;
	}

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public String getContexto() {
		return contexto;
	}
	
	public Noticia getNoticia() {
		return noticia;
	}

	public void setNoticia(Noticia noticia) {
		this.noticia = noticia;
	}
	
	public void setNoticiaManager(NoticiaManager noticiaManager) {
		this.noticiaManager = noticiaManager;
	}

	public void setUsuarioNoticiaManager(UsuarioNoticiaManager usuarioNoticiaManager) {
		this.usuarioNoticiaManager = usuarioNoticiaManager;
	}

	public void setColaboradorId(Long colaboradorId) {
		this.colaboradorId = colaboradorId;
	}

	public void setHistoricoColaboradorId(Long historicoColaboradorId) {
		this.historicoColaboradorId = historicoColaboradorId;
	}

	public void setFaixaSalarialHistoricoId(Long faixaSalarialHistoricoId) {
		this.faixaSalarialHistoricoId = faixaSalarialHistoricoId;
	}

	public void setPendenciasAcsSerRemovida(String[] pendenciasAcsSerRemovida) {
		this.pendenciasAcsSerRemovida = pendenciasAcsSerRemovida;
	}
	
	public boolean isBancoConsistente(){
		return this.bancoConsistente;
	}
}
