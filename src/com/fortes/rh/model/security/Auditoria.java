package com.fortes.rh.model.security;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.StringUtil;

@Entity
@SequenceGenerator(name = "sequence", sequenceName = "auditoria_sequence", allocationSize = 1)
public class Auditoria extends AbstractModel implements Serializable
{
	@ManyToOne
	private Usuario usuario;
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	@Column(length=20)
	private String operacao;
	@Column(length=50)
	private String entidade;
	private String dados;
	@Column(length=255)
	private String chave;

	@ManyToOne
	private Empresa empresa;

	public void setUsuarioNome(String usuarioNome)
	{
		if (usuario == null)
			usuario = new Usuario();

		usuario.setNome(usuarioNome);
	}

	public Date getData()
	{
		return data;
	}

	public void setData(Date data)
	{
		this.data = data;
	}

	public String getEntidade()
	{
		return StringUtil.camelCaseToSnakeCase(entidade);
	}

	public void setEntidade(String entidade)
	{
		this.entidade = entidade;
	}

	public String getOperacao()
	{
		return operacao;
	}

	public void setOperacao(String operacao)
	{
		this.operacao = operacao;
	}

	public Usuario getUsuario()
	{
		return usuario;
	}

	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}

	public String getDados()
	{
		return dados;
	}

	public void setDados(String dados)
	{
		this.dados = dados;
	}

	public Empresa getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public String getChave() {
		return this.chave == null ? "" : this.chave;
	}
	public void setChave(String chave) {
		this.chave = chave;
	}

	public void audita(Usuario usuario, Empresa empresa, String recurso, String operacao, String chave, String dados) {
		this.usuario = usuario;
		this.empresa = empresa;
		this.entidade = recurso;
		this.operacao = operacao;
		this.chave = chave;
		this.dados = dados;
		this.data = new Date();
	}

}