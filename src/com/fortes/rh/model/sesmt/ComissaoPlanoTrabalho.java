package com.fortes.rh.model.sesmt;

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
import com.fortes.rh.model.dicionario.ParecerEmpresa;
import com.fortes.rh.model.dicionario.Prioridade;
import com.fortes.rh.model.dicionario.Situacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="comissaoplanotrabalho_sequence", allocationSize=1)
public class ComissaoPlanoTrabalho extends AbstractModel implements Serializable
{
	@Temporal(TemporalType.DATE)
	private Date prazo;
	@Column(length=100)
	private String descricao;
	@Column(length=12)
	private String situacao = Situacao.NAO_INICIADO;
	@Column(length=1)
	private String parecer;
	@Column(length=1)
	private String prioridade;
	
	private String detalhes;

	@ManyToOne
	private Comissao comissao;
	@ManyToOne
	private Colaborador responsavel;
	@ManyToOne
	private Colaborador corresponsavel;

	public String getPrazoFormatado()
    {
        String dataFormatada = "";
        if (prazo != null)
            dataFormatada = DateUtil.formataDiaMesAno(prazo);

        return dataFormatada;
    }

	//Projection
	public void setProjectionComissaoId(Long comissaoId)
	{
		if (comissao == null)
			comissao = new Comissao();

		comissao.setId(comissaoId);
	}
	
	public void setProjectionResponsavelId(Long responsavelId)
	{
		iniciaResponsavel();
		responsavel.setId(responsavelId);
	}
	public void setProjectionResponsavelNome(String responsavelNome)
	{
		iniciaResponsavel();
		responsavel.setNome(responsavelNome);
	}

	private void iniciaResponsavel() 
	{
		if (responsavel == null)
			responsavel = new Colaborador();
	}
	
	private void iniciaCorresponsavel() 
	{
		if (corresponsavel == null)
			corresponsavel = new Colaborador();
	}
	
	public void setProjectionCorresponsavelId(Long coresponsavelId)
	{
		iniciaCorresponsavel();
		corresponsavel.setId(coresponsavelId);
	}

	public void setProjectionCorresponsavelNome(String coresponsavelNome)
	{
		iniciaCorresponsavel();
		corresponsavel.setNome(coresponsavelNome);
	}

	public String getSituacaoDic()
	{
		return Situacao.getInstance().get(this.situacao).toString();
	}

	public String getParecerDic()
	{
		Object parecer = ParecerEmpresa.getInstance().get(this.parecer);
		if (parecer != null)
			return parecer.toString();
		return "";
	}

	public String getPrioridadeDic()
	{
		Object prioridade = Prioridade.getInstance().get(this.prioridade);
		if (prioridade != null)
			return prioridade.toString();
		return "";
	}

	public Comissao getComissao()
	{
		return comissao;
	}

	public void setComissao(Comissao comissao)
	{
		this.comissao = comissao;
	}

	public String getDescricao()
	{
		return descricao;
	}

	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}

	public String getDetalhes()
	{
		return detalhes;
	}

	public void setDetalhes(String detalhes)
	{
		this.detalhes = detalhes;
	}

	public String getParecer()
	{
		return parecer;
	}

	public void setParecer(String parecer)
	{
		this.parecer = parecer;
	}

	public Date getPrazo()
	{
		return prazo;
	}

	public void setPrazo(Date prazo)
	{
		this.prazo = prazo;
	}

	public String getPrioridade()
	{
		return prioridade;
	}

	public void setPrioridade(String prioridade)
	{
		this.prioridade = prioridade;
	}

	public String getSituacao()
	{
		return situacao;
	}

	public void setSituacao(String situacao)
	{
		this.situacao = situacao;
	}

	public Colaborador getResponsavel()
	{
		return responsavel;
	}

	public void setResponsavel(Colaborador responsavel)
	{
		this.responsavel = responsavel;
	}

	public Colaborador getCorresponsavel() 
	{
		return corresponsavel;
	}

	public void setCorresponsavel(Colaborador corresponsavel) 
	{
		this.corresponsavel = corresponsavel;
	}
}