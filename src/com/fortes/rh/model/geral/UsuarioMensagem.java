package com.fortes.rh.model.geral;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.acesso.Usuario;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name = "sequence", sequenceName = "usuarioMensagem_sequence", allocationSize = 1)
public class UsuarioMensagem extends AbstractModel implements Serializable
{
	@ManyToOne
	private Usuario usuario;

	@ManyToOne
	private Mensagem mensagem;

	@ManyToOne
	private Empresa empresa;

	private boolean lida;

	public Empresa getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public Mensagem getMensagem()
	{
		return mensagem;
	}

	public void setMensagem(Mensagem mensagem)
	{
		this.mensagem = mensagem;
	}

	public Usuario getUsuario()
	{
		return usuario;
	}

	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}

	public boolean isLida()
	{
		return lida;
	}

	public void setLida(boolean lida)
	{
		this.lida = lida;
	}

	public void setProjectionEmpresaId(Long projectionEmpresaId)
	{
		if(empresa == null)
			empresa = new Empresa();

		empresa.setId(projectionEmpresaId);
	}

	public void setProjectionUsuarioId(Long projectionUsuarioId)
	{
		if(usuario == null)
			usuario = new Usuario();

		usuario.setId(projectionUsuarioId);
	}

	public void setProjectUsuarioNome(String projectUsuarioNome)
	{
		if(usuario == null)
			usuario = new Usuario();

		usuario.setNome(projectUsuarioNome);

	}

	public void setProjectionMensagemId(Long projectionMensagemId)
	{
		if(mensagem == null)
			mensagem = new Mensagem();

		mensagem.setId(projectionMensagemId);
	}

	public void setProjectionMensagemRemetente(String projectionMensagemRemetente)
	{
		if(mensagem == null)
			mensagem = new Mensagem();

		mensagem.setRemetente(projectionMensagemRemetente);
	}

	public void setProjectionMensagemData(Date projectionMensagemData)
	{
		if(mensagem == null)
			mensagem = new Mensagem();

		mensagem.setData(projectionMensagemData);
	}

	public void setProjectionMensagemTexto(String projectionMensagemTexto)
	{
		if(mensagem == null)
			mensagem = new Mensagem();

		mensagem.setTexto(projectionMensagemTexto);
	}
	
	public void setProjectionMensagemLink(String projectionMensagemLink)
	{
		if(mensagem == null)
			mensagem = new Mensagem();
		
		mensagem.setLink(projectionMensagemLink);
	}

	public void setProjectionMensagemTipo(Character mensagemTipo)
	{
		if(mensagem == null)
			mensagem = new Mensagem();
		
		mensagem.setTipo(mensagemTipo);
	}
}