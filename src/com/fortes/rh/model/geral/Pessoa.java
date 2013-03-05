package com.fortes.rh.model.geral;

import java.io.Serializable;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.dicionario.TipoPessoa;

@SuppressWarnings("serial")
public class Pessoa extends AbstractModel implements Serializable
{
    private String nome;
    private TipoPessoa tipoPessoa;

	public Pessoa()
	{
		
	}

	public Pessoa(Long id, String nome, TipoPessoa tipoPessoa)
	{
		this.setId(id);
		this.nome = nome;
		this.tipoPessoa = tipoPessoa;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public TipoPessoa getTipoPessoa() 
	{
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) 
	{
		this.tipoPessoa = tipoPessoa;
	}
}