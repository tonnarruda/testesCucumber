package com.fortes.rh.model.ws;

import java.io.Serializable;

public class TEstabelecimento implements Serializable
{
	private String codigo;
	private String codigoEmpresa;
	private String nome;
	private String complementoCnpj;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String uf;
	private String codigoCidade;
	private String cep;

	public String getBairro()
	{
		return bairro;
	}
	public void setBairro(String bairro)
	{
		this.bairro = bairro;
	}
	public String getCep()
	{
		return cep;
	}
	public void setCep(String cep)
	{
		this.cep = cep;
	}
	public String getCodigo()
	{
		return codigo;
	}
	public void setCodigo(String codigo)
	{
		this.codigo = codigo;
	}
	public String getCodigoCidade()
	{
		return codigoCidade;
	}
	public void setCodigoCidade(String codigoCidade)
	{
		this.codigoCidade = codigoCidade;
	}
	public String getCodigoEmpresa()
	{
		return codigoEmpresa;
	}
	public void setCodigoEmpresa(String codigoEmpresa)
	{
		this.codigoEmpresa = codigoEmpresa;
	}
	public String getComplemento()
	{
		return complemento;
	}
	public void setComplemento(String complemento)
	{
		this.complemento = complemento;
	}
	public String getComplementoCnpj()
	{
		return complementoCnpj;
	}
	public void setComplementoCnpj(String complementoCnpj)
	{
		this.complementoCnpj = complementoCnpj;
	}
	public String getLogradouro()
	{
		return logradouro;
	}
	public void setLogradouro(String logradouro)
	{
		this.logradouro = logradouro;
	}
	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	public String getNumero()
	{
		return numero;
	}
	public void setNumero(String numero)
	{
		this.numero = numero;
	}
	public String getUf()
	{
		return uf;
	}
	public void setUf(String uf)
	{
		this.uf = uf;
	}
}