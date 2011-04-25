package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="solicitacaoepi_sequence", allocationSize=1)
public class SolicitacaoEpi extends AbstractModel implements Serializable
{
	@Temporal(TemporalType.DATE)
	private Date data;
	private boolean entregue;

	@ManyToOne
	private Colaborador colaborador;

	@ManyToOne
	private Cargo cargo;

	@ManyToOne
	private Empresa empresa;

	//Usados por relatório
	@Transient
	private EpiHistorico epiHistorico;
	@Transient
	private Epi epi;
	@Transient
	private Integer qtdEpiEntregue = 0;
	@Transient
	private Date dataEpiEntrega;
	
	@Transient
	private Date vencimentoCA;

	public Date getDataVencimentoEpi()
	{
		Date dataVencimento = null;
		if (epiHistorico != null && epiHistorico.getValidadeUso() != null && dataEpiEntrega != null)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dataEpiEntrega);
			calendar.add(Calendar.DAY_OF_YEAR, +epiHistorico.getValidadeUso());
			dataVencimento = calendar.getTime();
		}
		
		return dataVencimento;
	}

	public SolicitacaoEpi()
	{
		if (this.data == null)
			this.data = new Date();
	}

	public SolicitacaoEpi(Long id, Date data, boolean entregue, String colaboradorNome, String cargoNome)
	{
		setId(id);
		this.data = data;
		this.entregue = entregue;
		setColaboradorNome(colaboradorNome);
		setCargoNome(cargoNome);
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

	public String getSituacao()
	{
		String situacao = entregue ? "Entregue" : "Aberta";
		return situacao;
	}

	private void setCargoNome(String cargoNome)
	{
		if (this.cargo == null)
			this.cargo = new Cargo();

		this.cargo.setNome(cargoNome);

	}

	private void setColaboradorNome(String colaboradorNome)
	{
		if (this.colaborador == null)
			this.colaborador = new Colaborador();

		this.colaborador.setNome(colaboradorNome);
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

	public boolean isEntregue()
	{
		return entregue;
	}

	public void setEntregue(boolean entregue)
	{
		this.entregue = entregue;
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

}