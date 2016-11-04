package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="prontuario_sequence", allocationSize=1)
public class Prontuario extends AbstractModel implements Serializable
{
	@ManyToOne(fetch = FetchType.LAZY)
	private Colaborador colaborador;
	@Temporal(TemporalType.DATE)
	private Date data;
	@Lob
	private String descricao;

	@ManyToOne
	private Usuario usuario;

	public String getDataFormatada()
	{
		String dataFormatada = "-";
		if (data != null)
		{
			dataFormatada = DateUtil.formataDiaMesAno(data);
		}
		return dataFormatada;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}
	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}
	public Date getData()
	{
		return data;
	}
	public void setData(Date data)
	{
		this.data = data;
	}
	public String getDescricao()
	{
		return descricao;
	}
	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}

	public Usuario getUsuario()
	{
		return usuario;
	}

	public void setUsuario(Usuario usuarioResponsavel)
	{
		this.usuario = usuarioResponsavel;
	}

	public void setProjectionUsuarioLogin(String usuarioLogin)
	{
		if (this.usuario == null)
			this.usuario = new Usuario();

		this.usuario.setLogin(usuarioLogin);
	}
}