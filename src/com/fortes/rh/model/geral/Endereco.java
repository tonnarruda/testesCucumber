/* Autor: Igo Coelho
 * Data: 26/05/2006 */
package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.rh.util.StringUtil;

@SuppressWarnings("serial")
public class Endereco implements Serializable
{
	@Column(length=40)
	private String logradouro;
	@Column(length=10)
	private String numero;
	@Column(length=20)
	private String complemento;
	@Column(length=20)
 	private String bairro;
	@ManyToOne(fetch=FetchType.LAZY)
	private Estado uf;
	@ManyToOne(fetch=FetchType.LAZY)
	private Cidade cidade;
	@Column(length=10)
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
	
	public String getCepFormatado()
	{
		return StringUtil.formataCep(cep);
	}
	
	public String getEnderecoFormatado()
	{
		String endereco = logradouro + ", " + numero;
		
		if (StringUtils.isNotBlank(complemento))
			endereco += " - " + complemento;
		
		return endereco;
	}

	public void setCep(String cep)
	{
		this.cep = cep;
	}

	public Cidade getCidade()
	{
		return cidade;
	}

	public void setCidade(Cidade cidade)
	{
		this.cidade = cidade;
	}

	public String getLogradouro()
	{
		return logradouro;
	}

	public void setLogradouro(String logradouro)
	{
		this.logradouro = logradouro;
	}

	public Estado getUf()
	{
		return uf;
	}

	public void setUf(Estado uf)
	{
		this.uf = uf;
	}

	public String getComplemento()
	{
		return complemento;
	}

	public void setComplemento(String complemento)
	{
		this.complemento = complemento;
	}

	public String getNumero()
	{
		return numero;
	}

	public void setNumero(String numero)
	{
		this.numero = numero;
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("numero", this.numero).append("cidade", this.cidade)
				.append("complemento", this.complemento)
				.append("cep", this.cep).append("bairro", this.bairro).append(
						"uf", this.uf).append("logradouro", this.logradouro)
				.toString();
	}
}