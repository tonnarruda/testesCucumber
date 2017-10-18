package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.util.CnpjUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="estabelecimento_sequence", allocationSize=1)
public class Estabelecimento extends AbstractModel implements Serializable
{
	@Column(length=30)
	private String nome;
	@Embedded
	private Endereco endereco = new Endereco();
	@Column(length=10)
	private String complementoCnpj;
	@ManyToOne(fetch=FetchType.LAZY)
	private Empresa empresa;
	@Column(length=12)
	private String codigoAC;
	@Transient
	private EstabelecimentoManager estabelecimentoManager;
	
	public String getEnderecoFormatado()
	{
		String enderecoFormatado = "";
		if (endereco != null)
		{
			if (StringUtils.isNotBlank(endereco.getLogradouro()))
				enderecoFormatado += endereco.getLogradouro();
			if (StringUtils.isNotBlank(endereco.getNumero()))
				enderecoFormatado += " " + endereco.getNumero();
			if (StringUtils.isNotBlank(endereco.getBairro()))
				enderecoFormatado += ", " + endereco.getBairro();
		}

		return enderecoFormatado;
	}

	//projection
	public void setProjectionEmpresaNome(String projectionEmpresaNome)
	{
		if(this.empresa == null)
			this.empresa = new Empresa();
		
		this.empresa.setNome(projectionEmpresaNome);
	}
	
	public String getDescricaoComEmpresa()
	{
		if (empresa != null)
			return this.empresa.getNome() + " - " + this.nome;
		return "";
	}

	public String getComplementoCnpj()
	{
		return complementoCnpj;
	}
	public void setComplementoCnpj(String complementoCnpj)
	{
		this.complementoCnpj = complementoCnpj;
	}
	public Empresa getEmpresa()
	{
		return empresa;
	}
	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}
	public Endereco getEndereco()
	{
		return endereco;
	}
	public void setEndereco(Endereco endereco)
	{
		this.endereco = endereco;
	}
	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	public String getCodigoAC()
	{
		return codigoAC;
	}
	public void setCodigoAC(String codigoAC)
	{
		this.codigoAC = codigoAC;
	}

	public String getCnpj()
	{
		if (empresa != null && empresa.getCnpj() != null)
			return empresa.getCnpj() + complementoCnpj;
		else
			return "";
	}

	public String getCnpjFormatado(){
		String cnpjFormatado = CnpjUtil.formata(getCnpj());

		if(cnpjFormatado == null)
			return "";
		return cnpjFormatado;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
		.append("id", this.getId())
		.append("nome", this.nome)
		.append("endereco", this.endereco)
		.append("complementoCnpj", this.complementoCnpj)
		.append("codigoAC", this.codigoAC).toString();
	}
	public EstabelecimentoManager getEstabelecimentoManager()
	{
		return estabelecimentoManager;
	}

	public void setLogradouro(String logradouro) 
	{
		endereco.setLogradouro(logradouro);
	}
	
	public void setNumero(String numero) 
	{
		endereco.setNumero(numero);
	}

	public void setComplemento(String complemento) 
	{
		endereco.setComplemento(complemento);
	}

	public void setBairro(String bairro) 
	{
		endereco.setBairro(bairro);
	}
	
	public void setCep(String cep) 
	{
		endereco.setCep(cep);
	}
	
	public void setUfSigla(String ufSigla)
	{
		if(endereco.getUf() == null)
			endereco.setUf(new Estado());
		
		endereco.getUf().setSigla(ufSigla);
	}

	public void setCidadeNome(String cidadeNome)
	{
		if(endereco.getCidade() == null)
			endereco.setCidade(new Cidade());
		
		endereco.getCidade().setNome(cidadeNome);
	}
}