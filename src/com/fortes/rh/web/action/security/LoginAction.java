package com.fortes.rh.web.action.security;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.config.SetupListener;
import com.fortes.rh.config.backup.RunAntScript;
import com.fortes.rh.exception.NotConectAutenticationException;
import com.fortes.rh.exception.NotRegistredException;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.Autenticador;
import com.fortes.rh.web.action.MyActionSupport;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings("serial")
public class LoginAction extends MyActionSupport
{
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private Collection<Empresa> empresas = new ArrayList<Empresa>();
	private String msgRemprot = "";
	private String senhaBD;
	private String versao = "";
	private Boolean demonstracao = false;
	private String servidorRemprot;

	@SuppressWarnings("unchecked")
	public String login() throws Exception
	{
		try {
			if (SecurityUtil.hasLoggedUser())
				return "index";

			if(!verificaCompatibilidadeDoJava())
				return "javasCompativeis";

			if(demonstracao){
				ActionContext.getContext().getSession().put("REG_MSG", Autenticador.getMsgPadrao());
				Autenticador.setDemo(true);
				return Action.SUCCESS;
			}

			ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findByIdProjection(1L);
			servidorRemprot = parametrosDoSistema.getServidorRemprot();
			
			if(servidorRemprot == null || "".equals(servidorRemprot)) 
				return "not_conect";
			
			Autenticador.verificaCopia(servidorRemprot, parametrosDoSistema.verificaRemprot(), parametrosDoSistema.getModulosPermitidosSomatorio());
			
		} catch (NotRegistredException e) {
			msgRemprot = e.getMessage();
			addActionWarning(msgRemprot);
			Autenticador.setDemo(false);
			return "not_registered";
		} catch (NotConectAutenticationException e) {
			msgRemprot = e.getMessage();
			addActionWarning(msgRemprot);
			Autenticador.setDemo(false);
			return "not_conect"; 
		} catch (Exception e) {
			addActionError("Erro ao iniciar o RH (Entre em contato com o suporte)<br>" + e.getMessage());
			e.printStackTrace();
			return Action.INPUT;
		}			

		return Action.SUCCESS;
	}
	
	public String configAutenticador() throws Exception
	{
		try {
			parametrosDoSistemaManager.updateServidorRemprot(servidorRemprot);
		} catch (Exception e) {
			addActionError("Erro ao configurar servidor de autenticação." + e.getMessage());
			e.printStackTrace();
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}

	public String updateConf() throws Exception
	{
		try	{
			SetupListener setupListener = new SetupListener();
			versao = setupListener.updateConf(senhaBD);
			
			addActionMessage("Senha salva com sucesso, reinicie a aplicação RH.");
		} catch (Exception e) {
			addActionError("Erro ao iniciar o RH (Entre em contato com o suporte<br>" + e.getMessage());
			e.printStackTrace();
		}
		
		return Action.SUCCESS;
	}
	
	public String gerarBD() throws Exception
	{
		try	{
			String dbName = ArquivoUtil.getSystemConf().getProperty("db.name");
			if(dbName == null || dbName.equals(""))
				dbName = "fortesrh";

			RunAntScript runAntScript = new RunAntScript(null, "create-bd");
			runAntScript.addProperty("db_name", dbName.trim());
			runAntScript.launch();
			addActionMessage("Banco gerado com sucesso, reinicie a aplicação RH.");
		} catch (Exception e) {
			addActionError(e.getMessage());
			e.printStackTrace();
		}
		
		return Action.SUCCESS;
	}
	
	public String javasCompativeis()
	{
		return Action.SUCCESS;
	}
	
	public boolean verificaCompatibilidadeDoJava()
	{
		String versaoJava = System.getProperty("java.version");
		return versaoJava.contains("1.6") || versaoJava.contains("1.7");
	}
	
	public Collection<Empresa> getEmpresas()
	{
		return empresas;
	}

	public void setEmpresas(Collection<Empresa> empresas)
	{
		this.empresas = empresas;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager)
	{
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public String getMsgRemprot()
	{
		return msgRemprot;
	}

	public void setSenhaBD(String senhaBD)
	{
		this.senhaBD = senhaBD;
	}

	public String getVersao()
	{
		return versao;
	}

	public Boolean getDemonstracao() {
		return demonstracao;
	}

	public void setDemonstracao(Boolean demonstracao) {
		this.demonstracao = demonstracao;
	}

	public String getServidorRemprot() {
		return servidorRemprot;
	}

	public void setServidorRemprot(String servidorRemprot) {
		this.servidorRemprot = servidorRemprot;
	}
}
