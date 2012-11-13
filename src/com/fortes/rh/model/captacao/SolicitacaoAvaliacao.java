package com.fortes.rh.model.captacao;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.avaliacao.Avaliacao;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name = "sequence", sequenceName = "solicitacaoavaliacao_sequence", allocationSize = 1)
public class SolicitacaoAvaliacao extends AbstractModel implements Serializable, Cloneable
{
	@ManyToOne
	private Solicitacao solicitacao;

	@ManyToOne
	private Avaliacao avaliacao;
	
	private boolean responderModuloExterno;
	
	public SolicitacaoAvaliacao() 
	{
		
	}
	
	public SolicitacaoAvaliacao(Long solicitacaoId, Long avaliacaoId, String avaliacaoTitulo) 
	{
		this.setProjectionSolicitacaoId(solicitacaoId);
		this.setProjectionAvaliacaoId(avaliacaoId);
		this.setProjectionAvaliacaoTitulo(avaliacaoTitulo);
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

	public boolean isResponderModuloExterno() {
		return responderModuloExterno;
	}

	public void setResponderModuloExterno(boolean responderModuloExterno) {
		this.responderModuloExterno = responderModuloExterno;
	}
	
	public Long getAvaliacaoId()
	{
		if (this.avaliacao == null)
			return null;
		
		return this.avaliacao.getId();
	}
	
	public String getAvaliacaoTitulo()
	{
		if (this.avaliacao == null)
			return null;
		
		return this.avaliacao.getTitulo();
	}
	
	public Long getSolicitacaoId()
	{
		if (this.solicitacao == null)
			return null;
		
		return this.solicitacao.getId();
	}

	public void setProjectionAvaliacaoId(Long avaliacaoId) 
	{
		if (this.avaliacao == null)
			this.avaliacao = new Avaliacao();
		
		this.avaliacao.setId(avaliacaoId);
	}
	
	public void setProjectionAvaliacaoTitulo(String avaliacaoTitulo) 
	{
		if (this.avaliacao == null)
			this.avaliacao = new Avaliacao();
		
		this.avaliacao.setTitulo(avaliacaoTitulo);
	}
	
	public void setProjectionSolicitacaoId(Long solicitacaoId) 
	{
		if (this.solicitacao == null)
			this.solicitacao = new Solicitacao();
		
		this.solicitacao.setId(solicitacaoId);
	}
}