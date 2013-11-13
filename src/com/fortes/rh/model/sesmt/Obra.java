package com.fortes.rh.model.sesmt;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.geral.Estado;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="obra_sequence", allocationSize=1)
public class Obra extends AbstractModel implements Serializable
{
	private String nome;
	private String tipoObra;
	@Embedded
	private Endereco endereco;
	@ManyToOne
	private Empresa empresa;
	
	public String getNome() 
	{
		return nome;
	}
	
	public void setNome(String nome) 
	{
		this.nome = nome;
	}

	public Empresa getEmpresa() 
	{
		return empresa;
	}

	public void setEmpresa(Empresa empresa) 
	{
		this.empresa = empresa;
	}

	public String getTipoObra() 
	{
		return tipoObra;
	}

	public void setTipoObra(String tipoObra) 
	{
		this.tipoObra = tipoObra;
	}

	public Endereco getEndereco() 
	{
		return endereco;
	}

	public void setEndereco(Endereco endereco) 
	{
		this.endereco = endereco;
	}

	private void inicializaEndereco()
	{
		if (this.endereco == null)
			this.endereco = new Endereco();
	}

	public void setEnderecoLogradouro(String logradouro)
	{
		inicializaEndereco();
		this.endereco.setLogradouro(logradouro);
	}

	public void setEnderecoNumero(String numero)
	{
		inicializaEndereco();
		this.endereco.setNumero(numero);
	}

	public void setEnderecoCep(String cep)
	{
		inicializaEndereco();
		this.endereco.setCep(cep);
	}

	public void setEnderecoBairro(String bairro)
	{
		inicializaEndereco();
		this.endereco.setBairro(bairro);
	}

	private void inicializaCidade() 
	{
		inicializaEndereco();
		if (this.endereco.getCidade() == null)
			this.endereco.setCidade(new Cidade());
	}
	
	public void setEnderecoCidadeNome(String nome)
	{
		inicializaCidade();
		this.endereco.getCidade().setNome(nome);
	}


	public void setEnderecoCidadeId(Long cidadeId)
	{
		inicializaCidade();
		this.endereco.getCidade().setId(cidadeId);
	}

	private void inicializaEstado() 
	{
		inicializaEndereco();
		if (this.endereco.getUf() == null)
			this.endereco.setUf(new Estado());
	}
	
	public void setEnderecoUfSigla(String sigla)
	{
		inicializaEstado();
		this.endereco.getUf().setSigla(sigla);
	}

	public void setEnderecoUfId(Long enderecoUfId)
	{
		inicializaEstado();
		this.endereco.getUf().setId(enderecoUfId);
	}
}
