package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.SituacaoSolicitacaoEpi;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="solicitacaoepi_sequence", allocationSize=1)
public class SolicitacaoEpi extends AbstractModel implements Serializable
{
	@Temporal(TemporalType.DATE)
	private Date data;

	@ManyToOne
	private Colaborador colaborador;

	@ManyToOne
	private Cargo cargo;

	@ManyToOne
	private Empresa empresa;
	
	@OneToMany(mappedBy="solicitacaoEpi")
	private Collection<SolicitacaoEpiItem> solicitacaoEpiItems;

	//Usados por relatório
	@Transient
	private EpiHistorico epiHistorico;
	@Transient
	private Epi epi;
	@Transient
	private Integer qtdEpiSolicitado = 0;
	@Transient
	private Integer qtdEpiEntregue = 0;
	@Transient
	private Date dataEpiEntrega;
	@Transient
	private Date vencimentoCA;

	public SolicitacaoEpi()
	{
		if (this.data == null)
			this.data = new Date();
	}

	public SolicitacaoEpi(Long epiId, Long colaboradorId, String epiNome, String colaboradorNome, String cargoNome, Date data, Integer validadeUso, Date dataEntrega, Integer qtdEpiEntregue, Date vencimentoCA)
	{
		epi = new Epi();
		epi.setId(epiId);
		epi.setNome(epiNome);
		setColaboradorNome(colaboradorNome);
		colaborador.setId(colaboradorId);
		setCargoNome(cargoNome);
		epiHistorico = new EpiHistorico();
		epiHistorico.setValidadeUso(validadeUso);
		this.data = data;
		this.qtdEpiEntregue = qtdEpiEntregue;
		this.dataEpiEntrega = dataEntrega;
		this.vencimentoCA = vencimentoCA;
	}

	public String getSituacaoDescricao()
	{
		return SituacaoSolicitacaoEpi.getSituacaoDescricao(qtdEpiEntregue, qtdEpiSolicitado);
	}

	public char getSituacao()
	{
		return SituacaoSolicitacaoEpi.getSituacao(qtdEpiEntregue, qtdEpiSolicitado);
	}

	public void setCargoNome(String cargoNome)
	{
		if (this.cargo == null)
			this.cargo = new Cargo();

		this.cargo.setNome(cargoNome);

	}

	public void setColaboradorNome(String colaboradorNome)
	{
		if (this.colaborador == null)
			this.colaborador = new Colaborador();

		this.colaborador.setNome(colaboradorNome);
	}
	
	public void setColaboradorStatus(int colaboradorStatus)
	{
		if (this.colaborador == null)
			this.colaborador = new Colaborador();
		
		if (this.colaborador.getHistoricoColaborador() == null)
			this.colaborador.setHistoricoColaborador(new HistoricoColaborador());
		
		this.colaborador.getHistoricoColaborador().setStatus(colaboradorStatus);
	}

	public Cargo getCargo()
	{
		return cargo;
	}

	public void setCargo(Cargo cargo)
	{
		this.cargo = cargo;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public Date getData()
	{
		return data;
	}

	public void setData(Date data)
	{
		this.data = data;
	}

	public Empresa getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public void setEpiHistorico(EpiHistorico epiHistorico)
	{
		this.epiHistorico = epiHistorico;
	}

	public Epi getEpi()
	{
		return epi;
	}

	public Integer getQtdEpiEntregue() {
		return qtdEpiEntregue;
	}

	public EpiHistorico getEpiHistorico() {
		return epiHistorico;
	}

	public Date getVencimentoCA() {
		return vencimentoCA;
	}

	public Date getDataEpiEntrega() {
		return dataEpiEntrega;
	}

	public Integer getQtdEpiSolicitado() {
		return qtdEpiSolicitado;
	}

	public void setQtdEpiSolicitado(Integer qtdEpiSolicitado) {
		this.qtdEpiSolicitado = qtdEpiSolicitado;
	}

	public Collection<SolicitacaoEpiItem> getSolicitacaoEpiItems() {
		return solicitacaoEpiItems;
	}

	public void setSolicitacaoEpiItems(Collection<SolicitacaoEpiItem> solicitacaoEpiItems) {
		this.solicitacaoEpiItems = solicitacaoEpiItems;
	}

	public void setQtdEpiEntregue(Integer qtdEpiEntregue) {
		this.qtdEpiEntregue = qtdEpiEntregue;
	}
}