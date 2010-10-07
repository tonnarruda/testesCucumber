package com.fortes.rh.model.sesmt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.dicionario.FuncaoComissaoEleitoral;
import com.fortes.rh.model.geral.Colaborador;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="comissaoeleicao_sequence", allocationSize=1)
public class ComissaoEleicao extends AbstractModel implements Serializable
{
	@ManyToOne(fetch = FetchType.LAZY)
	private Eleicao eleicao;
	@OneToOne
	private Colaborador colaborador;
	@Column(length=1)
	private String funcao = FuncaoComissaoEleitoral.SECRETARIO;

	public ComissaoEleicao()
	{
		
	}
	
	public ComissaoEleicao(Eleicao eleicao, Colaborador colaborador)
	{
		super();
		this.eleicao = eleicao;
		this.colaborador = colaborador;
	}
	
	//Projection
	public void setProjectionEleicaoId(Long eleicaoId)
	{
		if(eleicao == null)
			eleicao = new Eleicao();
		eleicao.setId(eleicaoId);
	}	
	public void setProjectionColaboradorId(Long colaboradorId)
	{
		if(colaborador == null)
			colaborador = new Colaborador();
		colaborador.setId(colaboradorId);
	}	
	public void setProjectionColaboradorNome(String colaboradorNome)
	{
		if(colaborador == null)
			colaborador = new Colaborador();
		colaborador.setNome(colaboradorNome);
	}	
	
	public Eleicao getEleicao()
	{
		return eleicao;
	}
	public void setEleicao(Eleicao eleicao)
	{
		this.eleicao = eleicao;
	}
	public Colaborador getColaborador()
	{
		return colaborador;
	}
	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	@Override
	public boolean equals(Object object)
	{
		if (object == null || !(object instanceof ComissaoEleicao))
			return false;

		ComissaoEleicao comissaoEleicao = (ComissaoEleicao)object;
		if (comissaoEleicao.getColaborador() != null && comissaoEleicao.getEleicao() != null && this.getColaborador() != null && this.getEleicao() != null)
			return this.getColaborador().getId().equals(comissaoEleicao.getColaborador().getId()) && this.getEleicao().getId().equals(comissaoEleicao.getEleicao().getId());

		return false;
	}

	public String getFuncao()
	{
		return funcao;
	}

	public void setFuncao(String funcao)
	{
		this.funcao = funcao;
	}
}