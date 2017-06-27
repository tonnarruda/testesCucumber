package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@Table(name="solicitacaoepi_item")
@SequenceGenerator(name="sequence", sequenceName="solicitacaoepi_item_sequence", allocationSize=1)
public class SolicitacaoEpiItem extends AbstractModel implements Serializable
{
	@ManyToOne(fetch = FetchType.EAGER)
	private Epi epi;

	@ManyToOne(fetch = FetchType.EAGER)
	private SolicitacaoEpi solicitacaoEpi;

	@ManyToOne(fetch = FetchType.EAGER)
	private MotivoSolicitacaoEpi motivoSolicitacaoEpi;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private TamanhoEPI tamanhoEPI;
	
	private Integer qtdSolicitado=0;
	
	@OneToMany(mappedBy="solicitacaoEpiItem", cascade=CascadeType.ALL)
	private Collection<SolicitacaoEpiItemEntrega> solicitacaoEpiItemEntregas;
	
	@OneToMany(mappedBy="solicitacaoEpiItem", cascade=CascadeType.ALL)
	private Collection<SolicitacaoEpiItemDevolucao> solicitacaoEpiItemDevolucoes;

	@Transient
	private int totalEntregue=0;

	@Transient
	private int totalDevolvido=0;

	
	public void setProjectionSolicitacaoEpiId(Long id)
	{
		if (this.solicitacaoEpi == null)
			this.solicitacaoEpi = new SolicitacaoEpi();

		this.solicitacaoEpi.setId(id);
	}

	public void setProjectionSolicitacaoEpiData(Date data)
	{
		if (this.solicitacaoEpi == null)
			this.solicitacaoEpi = new SolicitacaoEpi();

		this.solicitacaoEpi.setData(data);
	}
	
	public void setIdTamanhoEPI(Long id){
		
		if (this.tamanhoEPI == null) {
			this.tamanhoEPI = new TamanhoEPI();
		}
		
		this.tamanhoEPI.setId(id);
	}
	
	public void setDescricaoTamanhoEPI(String descricao){
		
		if (this.tamanhoEPI == null) {
			this.tamanhoEPI = new TamanhoEPI();
		}
		
		this.tamanhoEPI.setDescricao(descricao);
	}

	private void inicializaEpi() {
		if (this.epi == null)
			this.epi = new Epi();
	}

	public void setProjectionEpiId(Long id)
	{
		inicializaEpi();
		this.epi.setId(id);
	}

	public void setProjectionEpiNome(String nome)
	{
		inicializaEpi();
		this.epi.setNome(nome);
	}
	
	public void setProjectionEpiFabricante(String fabricante)
	{
		inicializaEpi();
		this.epi.setFabricante(fabricante);
	}
	
	public void setProjectionEpiHistorico(EpiHistorico epiHistorico)
	{
		inicializaEpi();
		this.epi.setEpiHistorico(epiHistorico);
	}
	
	public void setProjectionEpiAtivo(boolean epiAtivo)
	{
		inicializaEpi();
		this.epi.setAtivo(epiAtivo);
	}

	public Epi getEpi()
	{
		return epi;
	}
	
	public void setEpi(Epi epi)
	{
		this.epi = epi;
	}
	
	public Integer getQtdSolicitado()
	{
		return qtdSolicitado;
	}
	
	public void setQtdSolicitado(Integer qtdSolicitado)
	{
		this.qtdSolicitado = qtdSolicitado;
	}
	
	public SolicitacaoEpi getSolicitacaoEpi()
	{
		return solicitacaoEpi;
	}
	
	public void setSolicitacaoEpi(SolicitacaoEpi solicitacaoEpi)
	{
		this.solicitacaoEpi = solicitacaoEpi;
	}

	public Collection<SolicitacaoEpiItemEntrega> getSolicitacaoEpiItemEntregas()
	{
		return solicitacaoEpiItemEntregas;
	}

	public void setSolicitacaoEpiItemEntregas(Collection<SolicitacaoEpiItemEntrega> solicitacaoEpiItemEntregas)
	{
		this.solicitacaoEpiItemEntregas = solicitacaoEpiItemEntregas;
	}

	public Collection<SolicitacaoEpiItemDevolucao> getSolicitacaoEpiItemDevolucoes() {
		return solicitacaoEpiItemDevolucoes;
	}

	public void setSolicitacaoEpiItemDevolucoes(
			Collection<SolicitacaoEpiItemDevolucao> solicitacaoEpiItemDevolucoes) {
		this.solicitacaoEpiItemDevolucoes = solicitacaoEpiItemDevolucoes;
	}

	public int getTotalEntregue()
	{
		return totalEntregue;
	}

	public void setTotalEntregue(Integer totalEntregue)
	{
		if (totalEntregue == null)
			totalEntregue = 0;
		this.totalEntregue = totalEntregue;
	}

	public int getTotalDevolvido()
	{
		return this.totalDevolvido;
	}

	public void setTotalDevolvido(Integer totalDevolvido)
	{
		if (totalDevolvido == null)
			totalDevolvido = 0;
		this.totalDevolvido = totalDevolvido;
	}
	
	public MotivoSolicitacaoEpi getMotivoSolicitacaoEpi()
	{
		return motivoSolicitacaoEpi;
	}
	
	public void setMotivoSolicitacaoEpi(MotivoSolicitacaoEpi motivoSolicitacaoEpi)
	{
		this.motivoSolicitacaoEpi = motivoSolicitacaoEpi;
	}

	public TamanhoEPI getTamanhoEPI() {
		return tamanhoEPI;
	}

	public void setTamanhoEPI(TamanhoEPI tamanhoEPI) {
		this.tamanhoEPI = tamanhoEPI;
	}
	
	public String getNomeEpiComTamanho(){
		if(tamanhoEPI != null &&  !tamanhoEPI.getDescricao().isEmpty())
			return epi.getNomeInativo() + " (" + tamanhoEPI.getDescricao() +")";
		else
			return epi.getNomeInativo();
	}
	
	public boolean getQtdDevolucaoMaiorOuIgualQtdEntrega(){
		return this.totalDevolvido >= this.totalEntregue ;
	}
}