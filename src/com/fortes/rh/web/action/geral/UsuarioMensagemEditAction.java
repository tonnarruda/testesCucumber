package com.fortes.rh.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.geral.MensagemManager;
import com.fortes.rh.business.geral.UsuarioMensagemManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.dicionario.TipoMensagem;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Mensagem;
import com.fortes.rh.model.geral.UsuarioMensagem;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings({"serial"})
public class UsuarioMensagemEditAction extends MyActionSupportEdit implements ModelDriven
{
	@Autowired private UsuarioManager usuarioManager;
	@Autowired private MensagemManager mensagemManager;
	@Autowired private UsuarioMensagem usuarioMensagem;
	@Autowired private UsuarioMensagemManager usuarioMensagemManager;

	private Long usuarioMensagemProximoId;
	private Long usuarioMensagemAnteriorId;
	private String navegacao;
	private boolean fromDelete; // indica que veio da remoção
	private Integer qtdTotal;
	private boolean noMessages;


	private Usuario usuarioRem;
	private Empresa empresaEmp;

	private String mensagemStatus = "";

	Mensagem mensagemNova;

	private String[] usuariosCheck;
	private Collection<CheckBox> usuariosCheckList = new ArrayList<CheckBox>();
	
	private Character tipo;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(usuarioMensagem != null && usuarioMensagem.getId() != null)
			usuarioMensagem = (UsuarioMensagem) usuarioMensagemManager.findById(usuarioMensagem.getId());

		empresaEmp = SecurityUtil.getEmpresaSession(ActionContext.getContext().getSession());
		usuarioRem = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession());
		usuariosCheckList = usuarioManager.populaCheckOrderNome(empresaEmp.getId());
	}

	public String prepareInsert() throws Exception
	{
		prepare();

		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	//TODO Mover lógica para o Manager e verificar a transação
	public String insert() throws Exception
	{
		try
		{
			mensagemNova = mensagemManager.save(usuarioMensagem.getMensagem());
			mensagemNova.setTipo(TipoMensagem.UTILITARIOS);

			usuarioMensagemManager.salvaMensagem(usuarioMensagem.getEmpresa(), mensagemNova, usuariosCheck);

			addActionSuccess("Mensagem enviada com sucesso");
			
			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			addActionError("Ocorreu um erro ao enviar a mensagem.");

			return Action.INPUT;
		}

	}

	public String update() throws Exception
	{
		usuarioMensagemManager.update(usuarioMensagem);
		return Action.SUCCESS;
	}

	public String leituraUsuarioMensagemPopup() throws Exception
	{
		Empresa empresa = getEmpresaSistema();
		Usuario usuario = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession());
		qtdTotal = usuarioMensagemManager.countMensagens(empresa.getId(), usuario.getId(), tipo);

		if (qtdTotal > 0)
		{
			realizaNavegacao(usuario, empresa);
			usuarioMensagem = usuarioMensagemManager.findByIdProjection(usuarioMensagem.getId(), empresa.getId());
		}
		else
		{
			noMessages = true;
			addActionMessage("Nenhuma mensagem.");
		}

		return Action.SUCCESS;
	}

	private void realizaNavegacao(Usuario usuario, Empresa empresa)
	{

		if (StringUtils.isNotBlank(navegacao))
		{
			if (navegacao.equals("proximo"))
			{
				usuarioMensagemAnteriorId = usuarioMensagem.getId();
				usuarioMensagem.setId(usuarioMensagemProximoId);
				usuarioMensagemProximoId = usuarioMensagemManager.getAnteriorOuProximo(usuarioMensagem.getId(), usuario.getId(), empresa.getId(), 'P', tipo);
			}
			else if (navegacao.equals("anterior"))
			{
				usuarioMensagemProximoId = usuarioMensagem.getId();
				usuarioMensagem.setId(usuarioMensagemAnteriorId);
				usuarioMensagemAnteriorId = usuarioMensagemManager.getAnteriorOuProximo(usuarioMensagem.getId(), usuario.getId(), empresa.getId(), 'A', tipo);
			}
			else if (navegacao.equals("delete"))
			{
				if (usuarioMensagemProximoId != null)
				{
					usuarioMensagem.setId(usuarioMensagemProximoId);
					usuarioMensagemProximoId = usuarioMensagemManager.getAnteriorOuProximo(usuarioMensagem.getId(), usuario.getId(), empresa.getId(), 'P', tipo);
				}
				else if (usuarioMensagemAnteriorId != null)
				{
					usuarioMensagem.setId(usuarioMensagemAnteriorId);
					usuarioMensagemAnteriorId = usuarioMensagemManager.getAnteriorOuProximo(usuarioMensagem.getId(), usuario.getId(), empresa.getId(), 'A', tipo);
				}
				else
				{
					noMessages = true;
					addActionMessage("Nenhuma mensagem.");
				}
			}
		}
		else
		{
			usuarioMensagemAnteriorId = usuarioMensagemManager.getAnteriorOuProximo(usuarioMensagem.getId(), usuario.getId(), empresa.getId(), 'A', tipo);
			usuarioMensagemProximoId = usuarioMensagemManager.getAnteriorOuProximo(usuarioMensagem.getId(), usuario.getId(), empresa.getId(), 'P', tipo);
		}
	}

	public Object getModel()
	{
		return getUsuarioMensagem();
	}

	public UsuarioMensagem getUsuarioMensagem()
	{
		if(usuarioMensagem == null)
			usuarioMensagem = new UsuarioMensagem();
		return usuarioMensagem;
	}

	public String[] getUsuariosCheck()
	{
		return usuariosCheck;
	}

	public void setUsuariosCheck(String[] usuariosCheck)
	{
		this.usuariosCheck = usuariosCheck;
	}

	public Collection<CheckBox> getUsuariosCheckList()
	{
		return usuariosCheckList;
	}

	public void setUsuariosCheckList(Collection<CheckBox> usuariosCheckList)
	{
		this.usuariosCheckList = usuariosCheckList;
	}

	public Usuario getUsuarioRem()
	{
		return usuarioRem;
	}

	public void setUsuarioRem(Usuario usuarioRem)
	{
		this.usuarioRem = usuarioRem;
	}

	public Empresa getEmpresaEmp()
	{
		return empresaEmp;
	}

	public void setEmpresaEmp(Empresa empresaEmp)
	{
		this.empresaEmp = empresaEmp;
	}

	public Mensagem getMensagemNova()
	{
		return mensagemNova;
	}

	public void setMensagemNova(Mensagem mensagemNova)
	{
		this.mensagemNova = mensagemNova;
	}

	public String getMensagemStatus()
	{
		return mensagemStatus;
	}

	public void setMensagemStatus(String mensagemStatus)
	{
		this.mensagemStatus = mensagemStatus;
	}

	public Long getUsuarioMensagemAnteriorId()
	{
		return usuarioMensagemAnteriorId;
	}

	public void setUsuarioMensagemAnteriorId(Long usuarioMensagemAnteriorId)
	{
		this.usuarioMensagemAnteriorId = usuarioMensagemAnteriorId;
	}

	public Long getUsuarioMensagemProximoId()
	{
		return usuarioMensagemProximoId;
	}

	public void setUsuarioMensagemProximoId(Long usuarioMensagemProximoId)
	{
		this.usuarioMensagemProximoId = usuarioMensagemProximoId;
	}

	public void setNavegacao(String navegacao)
	{
		this.navegacao = navegacao;
	}

	public boolean isFromDelete()
	{
		return fromDelete;
	}

	public void setFromDelete(boolean fromDelete)
	{
		this.fromDelete = fromDelete;
	}

	public Integer getQtdTotal()
	{
		return qtdTotal;
	}

	public void setQtdTotal(Integer qtdTotal)
	{
		this.qtdTotal = qtdTotal;
	}

	public boolean getNoMessages()
	{
		return noMessages;
	}

	public void setNoMessages(boolean noMessages)
	{
		this.noMessages = noMessages;
	}

	public Character getTipo() {
		return tipo;
	}

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}
}