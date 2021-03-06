package com.fortes.rh.web.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.rh.business.geral.NoticiaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.dicionario.TelaAjudaESocial;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Noticia;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.util.StringUtil;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionSupport;

public abstract class MyActionSupport extends ActionSupport
{
	private static final long serialVersionUID = 2609579938880531255L;
	
	private Empresa empresaSistema = null;
	private Usuario usuarioSistema = null;
	private Colaborador colaboradorSistema = null;
	private Collection<Long> videosAjuda = null;
	private String actionErr = null;
	private String actionMsg = null;
	private String versao = null;
	private String msgHelp = null;
	private ParametrosDoSistema parametrosDoSistemaSession;
	private String telaAjuda;
	private boolean exibeDialogAJuda = false;
	
	public static final String MESSAGE = "message";
	
	private Collection<String> actionWarnings = new ArrayList<String>();
	private Collection<String> actionSuccess = new ArrayList<String>();
	
	public static String actionWarningsSessionKey = "__MessageStoreInterceptor_ActionWarnings_SessionKey";
    public static String actionSuccessSessionKey = "__MessageStoreInterceptor_ActionSuccess_SessionKey";
    
    public String msgAlert = "";

	public Empresa getEmpresaSistema(){
		if (empresaSistema == null)
			empresaSistema = SecurityUtil.getEmpresaSession(ActionContext.getContext().getSession());
		return empresaSistema;
	}

	public void setEmpresaSistema(Empresa empresaSistema)
	{
		this.empresaSistema = empresaSistema;
	}
	
	public ParametrosDoSistema getParametrosDoSistemaSession() {
		if(parametrosDoSistemaSession == null)
			parametrosDoSistemaSession = SecurityUtil.getParametrosDoSistemaSession(ActionContext.getContext().getSession());
		return parametrosDoSistemaSession;
	}
	
	public Usuario getUsuarioLogado()
	{
		if (usuarioSistema == null)
			usuarioSistema = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession());
		return usuarioSistema;
	}

	public void setUsuarioLogado(Usuario usuario)
	{
		this.usuarioSistema = usuario;
	}

	public String getActionMsg()
	{
		if(actionMsg != null)
			return actionMsg.replace(" ", "%20");
		else
			return null;
	}

	public void setActionMsg(String actionMsg)
	{
		this.actionMsg = actionMsg;

		if (actionMsg != null && !actionMsg.equals(""))
		{
			//TODO actionmessage duplicada, Francisco e Arnaldo 21/01/09. Duvidas ligue 190.  
			clearErrorsAndMessages();
			addActionMessage(this.actionMsg);
		}
	}

	public String getActionErr()
	{
		return actionErr;
	}

	public void setActionErr(String actionErr)
	{
		this.actionErr = actionErr;
	}
	
	public String updateFilter()
	{
		return Action.NONE;
	}
	
	public String getCalculoHash()
	{
		String data = new SimpleDateFormat("ddMMyyyy").format(new Date());
		
		Integer calculoHash  = (Integer.parseInt(data) * 2) / 64;
		
		return StringUtil.encodeString(calculoHash.toString());
	}

	public Collection<Long> getVideosAjuda() 
	{
		return videosAjuda;
	}

	public void setVideoAjuda(Long videoAjuda) 
	{
		if(videosAjuda == null)
			videosAjuda = new ArrayList<Long>();
		
		videosAjuda.add(videoAjuda);
	}

	public Collection<String> getActionWarnings() 
	{
		return actionWarnings;
	}

	public void setActionWarnings(Collection<String> actionWarnings) 
	{
		this.actionWarnings = actionWarnings;
	}
	
	public void addActionWarning(String actionWarning) 
	{
		this.actionWarnings.add(actionWarning);
	}
	
	public Collection<String> getActionSuccess() 
	{
		return actionSuccess;
	}

	public void setActionSuccess(Collection<String> actionSuccess) 
	{
		this.actionSuccess = actionSuccess;
	}
	
	public void addActionSuccess(String actionSuccess) 
	{
		this.actionSuccess.add(actionSuccess);
	}
	
	@SuppressWarnings("rawtypes")
	public String getUltimasNoticias()
	{
		Map session = ActionContext.getContext().getSession();
		
		if (session.get(Noticia.ULTIMAS_NOTICIAS) == null)
		{
			NoticiaManager noticiaManager = (NoticiaManager) SpringUtil.getBean("noticiaManager");
			noticiaManager.carregarUltimasNoticias(getUsuarioLogado().getId()); 
		}
			
		return (String) session.get(Noticia.ULTIMAS_NOTICIAS);
	}
	
	public String getVersao() {
		if (versao == null)
			versao = SecurityUtil.getVersao(ActionContext.getContext().getSession());
		return versao;
	}

	public String getMsgHelp() {
		return msgHelp;
	}

	public void setMsgHelp(String msgHelp) {
		this.msgHelp = msgHelp;
	}
	
	public String getMsgAlert()
	{
		if(msgAlert != null)
			return msgAlert.replace(" ", "%20");
		else
			return null;
	}

	public void setMsgAlert(String msgAlert)
	{
		this.msgAlert = msgAlert;
	}

	public Long getColaboradorSistemaId() {
		if (colaboradorSistema == null)
			colaboradorSistema = SecurityUtil.getColaboradorSession(ActionContext.getContext().getSession());
		
		if (colaboradorSistema != null)
			return colaboradorSistema.getId();
		
		return null;
	}
	
	public boolean isEmpresaIntegradaEAderiuAoESocial(){
		if(!getEmpresaSistema().isAcIntegra())
			return false;
	    ParametrosDoSistemaManager parametrosDoSistemaManager = (ParametrosDoSistemaManager) SpringUtil.getBean("parametrosDoSistemaManager");
	    boolean aderiuAoESocial = true;
	    try {
			aderiuAoESocial = parametrosDoSistemaManager.isAderiuAoESocial(getEmpresaSistema());
		} catch (Exception e) {
			e.printStackTrace();
			if(getEmpresaSistema().isAcIntegra())
				addActionWarning("Não foi possível verificar se a empresa aderiu ao eSocial. Por motivo de segurança consideramos que tenha aderido.");
		}
	    return aderiuAoESocial && getEmpresaSistema().isAcIntegra();
	}

	public String getTelaAjuda() {
		return telaAjuda;
	}

	public void setTelaAjuda(String telaAjuda) {
		this.telaAjuda = telaAjuda;
	}

	public boolean isExibeDialogAJuda() {
		return exibeDialogAJuda;
	}

	public void setExibeDialogAJuda(boolean exibeDialogAJuda) {
		this.exibeDialogAJuda = exibeDialogAJuda;
	}
}