
package com.fortes.rh.web.action;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.geral.UsuarioMensagemManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.geral.PendenciaAC;
import com.fortes.rh.model.geral.UsuarioMensagem;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Pesquisa;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.security.SecurityUtil;
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

	private Collection<Pesquisa> pesquisasAtrasadas;

	private Collection<Pesquisa> pesquisasColaborador = new ArrayList<Pesquisa>();
	private Collection<Questionario> questionarios;
	private Collection<UsuarioMensagem> mensagems;
	private Collection<PendenciaAC> pendenciaACs = new ArrayList<PendenciaAC>();
	private Collection<ColaboradorQuestionario> avaliacoesDesempenhoPendentes = new ArrayList<ColaboradorQuestionario>();

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
	
	public String index()
	{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		if(ActionContext.getContext().getSession().get("primeiraExecucao") == null)
		{
			ActionContext.getContext().getSession().put("primeiraExecucao", "sim");
			primeiraExecucao = true;
		}

		try
		{
			usuarioId = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession()).getId();
			empresaId = SecurityUtil.getEmpresaSession(ActionContext.getContext().getSession()).getId();

			questionarios = questionarioManager.findQuestionarioPorUsuario(usuarioId);

			colaborador = colaboradorManager.findByUsuario(SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession()), empresaId);

			mensagems = usuarioMensagemManager.listaUsuarioMensagem(usuarioId, empresaId);

			possuiMensagem = usuarioMensagemManager.possuiMensagemNaoLida(usuarioId, empresaId);
			
			if (parametrosDoSistemaManager.isIdiomaCorreto())
				idiomaIncorreto = true;
			
			validaIntegracaoAC();
			
			validaAvaliacoesDesempenho();
			
			if(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_LIBERA_SOLICITACAO"}) )
			{
				solicitacaos = solicitacaoManager.findSolicitacaoList(empresaId, false, false, null);
				candidatoSolicitacaos = candidatoSolicitacaoManager.findByFiltroSolicitacaoTriagem(true);
			}
			
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
		Collection<AvaliacaoDesempenho> avaliacaoDesempenhos = avaliacaoDesempenhoManager.findAllSelect(empresaId, true);
		
		for (AvaliacaoDesempenho avaliacaoDesempenho : avaliacaoDesempenhos)
		{
			Collection<ColaboradorQuestionario> avaliadosComAvaliacaoPendente = colaboradorQuestionarioManager.findAvaliadosByAvaliador(avaliacaoDesempenho.getId(), colaborador.getId(), false);
			if (avaliadosComAvaliacaoPendente != null && !avaliadosComAvaliacaoPendente.isEmpty())
			{
				for (ColaboradorQuestionario colabQuestionarioAvaliado : avaliadosComAvaliacaoPendente)
				{
					avaliacoesDesempenhoPendentes.add(colabQuestionarioAvaliado);
				}
			}
		}
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
					throw new Exception("Erro ao verificar versão do WebService do AC Pessoal. Verifique se a conexão está funcionando corretamente.");
				}

				parametrosDoSistema = parametrosDoSistemaManager.findByIdProjection(1L);

				String versaoMinimaWebServicxeCompativel = parametrosDoSistema.getAcVersaoWebServiceCompativel();

				boolean rhCompativelComAC  = parametrosDoSistemaManager.verificaCompatibilidadeComWebServiceAC(versaoWebServiceAC, versaoMinimaWebServicxeCompativel);

				if(!rhCompativelComAC)
					addActionError("A versão do WebService do AC Pessoal ("+versaoWebServiceAC+") está diferente da versão ("+versaoMinimaWebServicxeCompativel+") exigida pelo FortesRH.");
			}

			pendenciaACs.addAll(historicoColaboradorManager.findPendenciasByHistoricoColaborador(empresaId));

			pendenciaACs.addAll(faixaSalarialHistoricoManager.findPendenciasByFaixaSalarialHistorico(empresaId));
		}
	}

	public void setCandidatoSolicitacaoManager(
			CandidatoSolicitacaoManager candidatoSolicitacaoManager) {
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

}
