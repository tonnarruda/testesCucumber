package com.fortes.rh.web.action.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.geral.EmpresaManager;
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

public class LoginAction extends MyActionSupport
{
	private EmpresaManager empresaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private Collection<Empresa> empresas = new ArrayList<Empresa>();
	private String msgRemprot = "";
	private String senhaBD;
	private String versao;
//	private Boolean atualizadoSucesso = true;
	private Boolean demonstracao = false;
	private String servidorRemprot;

	@SuppressWarnings("unchecked")
	public String login() throws Exception
	{
		try
		{
			if (SecurityUtil.hasLoggedUser())
				return "index";
			
			Map<String, Object> session = ActionContext.getContext().getSession();
			ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findByIdProjection(1L);
			
			String msgAutenticacao = "";
			String linkSuporte = null;
			
			if(demonstracao)
			{
				msgAutenticacao = Autenticador.getMsgPadrao();
			}
			else
			{
				try {
					servidorRemprot = parametrosDoSistema.getServidorRemprot();
					Autenticador.verificaCopia(servidorRemprot, parametrosDoSistema.verificaRemprot());
				} catch (NotRegistredException e) {
					msgRemprot = e.getMessage();
					msgAutenticacao = Autenticador.getMsgPadrao();
					return "not_registered";
				} catch (NotConectAutenticationException e) {
					msgRemprot = e.getMessage();
					msgAutenticacao = Autenticador.getMsgPadrao();
					return "not_conect"; 
				} catch (Exception e) {
					e.printStackTrace();
				}					
			}
			
			session.put("REG_MSG", msgAutenticacao);
			
			if (parametrosDoSistema.isSuporteVeica())
				linkSuporte = "http://184.106.249.85";
			else if (!StringUtils.isEmpty(parametrosDoSistema.getCodEmpresaSuporte()) && !StringUtils.isEmpty(parametrosDoSistema.getCodClienteSuporte()))
				linkSuporte = "http://chatonline.grupofortes.com.br/cliente/MATRIZ/" + parametrosDoSistema.getCodClienteSuporte() + "/" + parametrosDoSistema.getCodEmpresaSuporte();
			
			session.put("LINK_SUPORTE", linkSuporte);
			
			return Action.SUCCESS;

		} catch (Exception e)
		{
			addActionError("Erro ao iniciar o RH (Entre em contato com o suporte)<br>" + e.getMessage());
			e.printStackTrace();
			return Action.INPUT;
		}
	}
	
	public String configAutenticador() throws Exception
	{
		try
		{
			parametrosDoSistemaManager.updateServidorRemprot(servidorRemprot);
		} catch (Exception e)
		{
			addActionError("Erro ao configurar servidor de autenticação." + e.getMessage());
			e.printStackTrace();
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}

	public String updateConf() throws Exception
	{
		try
		{
			SetupListener setupListener = new SetupListener();
			versao = setupListener.updateConf(senhaBD);
			
			addActionMessage("Senha salva com sucesso, reinicie a aplicação RH.");
		} catch (Exception e)
		{
			addActionError("Erro ao iniciar o RH (Entre em contato com o suporte<br>" + e.getMessage());
			e.printStackTrace();
		}
		
		return Action.SUCCESS;
	}
	
	public String gerarBD() throws Exception
	{
		try
		{
			String dbName = ArquivoUtil.getSystemConf().getProperty("db.name");
			if(dbName == null || dbName.equals(""))
				dbName = "fortesrh";

			RunAntScript runAntScript = new RunAntScript(null, "create-bd");
			runAntScript.addProperty("db_name", dbName);
			runAntScript.launch();
			addActionMessage("Banco gerado com sucesso, reinicie a aplicação RH.");
		} catch (Exception e)
		{
			addActionError(e.getMessage());
			e.printStackTrace();
		}
		
		return Action.SUCCESS;
	}
	
	public Collection<Empresa> getEmpresas()
	{
		return empresas;
	}

	public void setEmpresas(Collection<Empresa> empresas)
	{
		this.empresas = empresas;
	}

	public void setEmpresaManager(EmpresaManager empresaManager)
	{
		this.empresaManager = empresaManager;
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

//	public Boolean getAtualizadoSucesso()
//	{
//		return atualizadoSucesso;
//	}

}
