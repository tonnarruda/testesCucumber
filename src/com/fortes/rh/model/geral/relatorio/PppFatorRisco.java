package com.fortes.rh.model.geral.relatorio;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.util.DateUtil;

public class PppFatorRisco implements Serializable, Cloneable
{
	private static final long serialVersionUID = 1L;
	
	private Date dataInicio;
	private Date dataFim;
	private Date dataDesligamento;
	private Risco risco;
	private String intensidade;
	private String tecnicaUtilizada;
	private Boolean epcEficaz;
	private String caEpi="";
	private Long medicaoId;
	private boolean flagRemover=false;
	private boolean dataFinalJaSetada=false;
	private Date dataHistoricoAmbienteOuFuncao;
	
	public PppFatorRisco() {
	}
	
	
	public PppFatorRisco(Date dataInicio, Long riscoId, Long medicaoId, String intensidade) {
		this.dataInicio = dataInicio;
		this.risco = new Risco();
		this.risco.setId(riscoId);
		this.medicaoId = medicaoId;
		this.intensidade = intensidade;
	}
	
	public PppFatorRisco(Date dataInicio, Date dataFim, Long riscoId, String riscoTipo, String riscoDescricao, Long medicaoId, String intensidade, String tecnicaUtilizada, Boolean epcEficaz, 
			Collection<Epi> epis, Date dataHistoricoAmbienteOuFuncao, Date dataDesligamento) {
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.risco = new Risco();
		this.risco.setId(riscoId);
		this.risco.setDescricao(riscoDescricao);
		this.risco.setGrupoRisco(riscoTipo);
		this.medicaoId = medicaoId;
		this.intensidade = intensidade;
		this.tecnicaUtilizada = tecnicaUtilizada;
		this.epcEficaz = epcEficaz;
		this.dataHistoricoAmbienteOuFuncao = dataHistoricoAmbienteOuFuncao;
		this.dataDesligamento = dataDesligamento;
		
		formataCaEpis(epis);
	}

	private void formataCaEpis(Collection<Epi> epis) 
	{
		for (Epi epi : epis) 
		{
			if (epi.getEpiHistorico() != null && StringUtils.isNotBlank(epi.getEpiHistorico().getCA()))
			{
				if (StringUtils.isNotBlank(caEpi))
					caEpi += ", ";
				
				caEpi += epi.getEpiHistorico().getCA();
			}
		}
	}

	public Boolean isEpiEficaz()
	{
		return (StringUtils.isNotBlank(caEpi));
	}
	
	public boolean equalsMedicao(PppFatorRisco pppFatorRisco) 
	{
		return this.medicaoId.equals(pppFatorRisco.getMedicaoId()) || (this.intensidade.equals(pppFatorRisco.getIntensidade()) && this.tecnicaUtilizada.equals(pppFatorRisco.getTecnicaUtilizada()));
	}

	public String getCaEpi()
	{
		if (this.caEpi == "") {
			return "NA";
		} else {
			return caEpi;
		}
	}

	public void setCaEpi(String caEpi)
	{
		this.caEpi = caEpi;
	}

	public Boolean getEpcEficaz()
	{
		return epcEficaz;
	}

	public void setEpcEficaz(Boolean epcEficaz)
	{
		this.epcEficaz = epcEficaz;
	}

	public Risco getRisco()
	{
		return risco;
	}

	public void setRisco(Risco risco)
	{
		this.risco = risco;
	}

	public Date getDataFim()
	{
		return dataFim;
	}

	public void setDataFim(Date dataFim)
	{
		this.dataFim = dataFim;
	}

	public Date getDataInicio()
	{
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio)
	{
		this.dataInicio = dataInicio;
	}
	
	public String getPeriodo()
	{
		String dataFim = "__/__/___";
		if(dataDesligamento != null)
			dataFim = DateUtil.formataDiaMesAno(dataDesligamento);
		
		return (this.dataInicio != null ? DateUtil.formataDiaMesAno(this.dataInicio) : "__/__/___") + " a " + (this.dataFim != null ? DateUtil.formataDiaMesAno(this.dataFim) : dataFim);
	}

	public String getIntensidade() {
		return intensidade;
	}
	
	public void setIntensidade(String intensidade) {
		this.intensidade = intensidade;
	}

	@Override
	public Object clone()
	{
		try
		{
			return super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new Error("Ocorreu um erro interno no sistema. Não foi possível clonar o objeto.");
		}
	}

	public Long getMedicaoId() {
		return medicaoId;
	}

	public void setMedicaoId(Long medicaoId) {
		this.medicaoId = medicaoId;
	}

	public String getTecnicaUtilizada() {
		return tecnicaUtilizada;
	}

	public void setTecnicaUtilizada(String tecnicaUtilizada) {
		this.tecnicaUtilizada = tecnicaUtilizada;
	}

	public boolean comparaMedicao(PppFatorRisco pppFatorRisco) 
	{
		return this.dataInicio.compareTo(pppFatorRisco.getDataInicio()) == 0 && this.risco.equals(pppFatorRisco.getRisco()) && this.medicaoId.equals(pppFatorRisco.getMedicaoId());
	}

	public Date getDataHistoricoAmbienteOuFuncao() {
		return dataHistoricoAmbienteOuFuncao;
	}

	public void setDataHistoricoAmbienteOuFuncao(Date dataHistoricoAmbienteOuFuncao) {
		this.dataHistoricoAmbienteOuFuncao = dataHistoricoAmbienteOuFuncao;
	}

	public boolean isFlagRemover() {
		return flagRemover;
	}

	public void setFlagRemover(boolean flagRemover) {
		this.flagRemover = flagRemover;
	}

	public boolean isDataFinalJaSetada() {
		return dataFinalJaSetada;
	}

	public void setDataFinalJaSetada(boolean dataFinalJaSetada) {
		this.dataFinalJaSetada = dataFinalJaSetada;
	}


	public void setDataDesligamento(Date dataDesligamento) {
		this.dataDesligamento = dataDesligamento;
	}
}
