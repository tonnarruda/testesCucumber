package com.fortes.rh.web.action.security;

import java.util.Collection;

import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.config.SetupListener;
import com.fortes.rh.config.backup.RunAntScript;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.Autenticador;
import com.fortes.rh.web.action.MyActionSupport;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class LoginAction extends MyActionSupport
{
	private EmpresaManager empresaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private Collection<Empresa> empresas;
	private String msgRemprot = "";
	private String senhaBD;
	private String versao;
//	private Boolean atualizadoSucesso = true;
	private Boolean demonstracao = false;

	public String login() throws Exception
	{
		try
		{
			if (SecurityUtil.hasLoggedUser())
				return "index";
			
			ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findByIdProjection(1L);
//			if(parametrosDoSistema.getAtualizadoSucesso() != null)
//				atualizadoSucesso = parametrosDoSistema.getAtualizadoSucesso();
			
			String msgAutenticacao = "";
			boolean rhRegistrado = false;
			if(demonstracao)
			{
				msgAutenticacao = Autenticador.getMsgPadrao();
			}
			else
			{
				rhRegistrado = Autenticador.verificaCopia(parametrosDoSistema.getServidorRemprot());
				if (!rhRegistrado)
				{
					msgRemprot = "Este sistema não está licenciado para uso ou ocorreu algum erro com a comunicação do servidor.";
					msgAutenticacao = Autenticador.getMsgPadrao();
					return "not_registered";
				}				
			}
			
			ActionContext.getContext().getSession().put("REG_MSG", msgAutenticacao);
			
			empresas = empresaManager.findToList(new String[]{"id","nome"}, new String[]{"id","nome"}, new String[]{"nome"});
			return Action.SUCCESS;

		} catch (Exception e)
		{
			addActionError("Erro ao iniciar o Fortes RH (Entre em contato com o suporte)<br>" + e.getMessage());
			e.printStackTrace();
			return Action.INPUT;
		}
	}

	public String updateConf() throws Exception
	{
		try
		{
			SetupListener setupListener = new SetupListener();
			versao = setupListener.updateConf(senhaBD);
			
			addActionMessage("Senha salva com sucesso, reinicie a aplicação Fortes RH.");
		} catch (Exception e)
		{
			addActionError("Erro ao iniciar o Fortes RH (Entre em contato com o suporte<br>" + e.getMessage());
			e.printStackTrace();
		}
		
		return Action.SUCCESS;
	}
	
	public String gerarBD() throws Exception
	{
		try
		{
			RunAntScript runAntScript = new RunAntScript(null, "create-bd");
			runAntScript.launch();
			addActionMessage("Banco gerado com sucesso, reinicie a aplicação Fortes RH.");
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

//	public Boolean getAtualizadoSucesso()
//	{
//		return atualizadoSucesso;
//	}

}
