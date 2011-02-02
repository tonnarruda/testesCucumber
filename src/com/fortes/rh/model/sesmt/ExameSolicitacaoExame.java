package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="examesolicitacaoexame_sequence", allocationSize=1)
public class ExameSolicitacaoExame extends AbstractModel implements Serializable
{
    @ManyToOne
    private SolicitacaoExame solicitacaoExame;

    @ManyToOne
    private Exame exame;

	@ManyToOne
	private ClinicaAutorizada clinicaAutorizada;

	@OneToOne
	private RealizacaoExame realizacaoExame;
	
	private int periodicidade; //periodicidade para o exame na solicitação/atendimento
	
	@Transient
	private String colaboradorNome;
	@Transient
	private Long colaboradorId;
	@Transient
	private String candidatoNome;
	@Transient
	private Long candidatoId;

	public ExameSolicitacaoExame()
	{
	}

	public ExameSolicitacaoExame(Long id, Integer periodicidade, String exameNome, Date realizacaoData, String colaboradorNome, String candidatoNome)
	{
		this.setId(id);
		this.periodicidade = periodicidade;
		
		newExame();
		this.exame.setNome(exameNome);
		
		newRealizacaoExame();
		this.realizacaoExame.setData(realizacaoData);
		
		this.colaboradorNome = colaboradorNome;
		this.candidatoNome = candidatoNome;
	}

	public ExameSolicitacaoExame(Long exameId, Long clinicaAutorizadaId)
	{
		clinicaAutorizada = new ClinicaAutorizada();
		clinicaAutorizada.setId(clinicaAutorizadaId);
		
		exame = new Exame();
		exame.setId(exameId);
	}
	
	/**
	 * retorna o resultado, se houver.
	 * @see coluna "Exames" na listagem de Solicitações/Atendimentos (ftl). 
	 */
	public String getResultado()
	{
		if (realizacaoExame != null && StringUtils.isNotBlank(realizacaoExame.getResultado())) 
			return realizacaoExame.getResultado();
		else
			return "";
	}

	public void newSolicitacaoExame()
	{
		if(this.solicitacaoExame == null)
			this.solicitacaoExame = new SolicitacaoExame();
	}

	public void newExame()
	{
		if(this.exame == null)
			this.exame = new Exame();
	}
	//Projections
	public void setProjectionSolicitacaoExameId(Long solicitacaoExameId)
	{
		newSolicitacaoExame();
		this.solicitacaoExame.setId(solicitacaoExameId);
	}
	public void setProjectionSolicitacaoExameData(Date solicitacaoExameData)
	{
		newSolicitacaoExame();
		this.solicitacaoExame.setData(solicitacaoExameData);
	}

	public void setProjectionExameId(Long exameId)
	{
		newExame();
		this.exame.setId(exameId);
	}

	public void setProjectionExameNome(String exameNome)
	{
		newExame();
		this.exame.setNome(exameNome);
	}

	public void setProjectionRealizacaoExameId(Long realizacaoExameId)
	{
		newRealizacaoExame();
		this.realizacaoExame.setId(realizacaoExameId);
	}

	public void setProjectionRealizacaoExameData(Date realizacaoExameData)
	{
		newRealizacaoExame();
		this.realizacaoExame.setData(realizacaoExameData);
	}

	public void setProjectionRealizacaoExameResultado(String realizacaoExameResultado)
	{
		newRealizacaoExame();
		this.realizacaoExame.setResultado(realizacaoExameResultado);
	}

	public void setProjectionRealizacaoExameObservacao(String realizacaoExameObservacao)
	{
		newRealizacaoExame();
		this.realizacaoExame.setObservacao(realizacaoExameObservacao);
	}

	private void newRealizacaoExame()
	{
		if (this.realizacaoExame == null)
			this.realizacaoExame = new RealizacaoExame();
	}
	public void setProjectionClinicaId(Long clinicaId)
	{
		if (this.clinicaAutorizada == null)
			this.clinicaAutorizada = new ClinicaAutorizada();

		clinicaAutorizada.setId(clinicaId);
	}

	public ClinicaAutorizada getClinicaAutorizada()
	{
		return clinicaAutorizada;
	}
	public void setClinicaAutorizada(ClinicaAutorizada clinicaAutorizada)
	{
		this.clinicaAutorizada = clinicaAutorizada;
	}
	public Exame getExame()
	{
		return exame;
	}
	public void setExame(Exame exame)
	{
		this.exame = exame;
	}
	public SolicitacaoExame getSolicitacaoExame()
	{
		return solicitacaoExame;
	}
	public void setSolicitacaoExame(SolicitacaoExame solicitacaoExame)
	{
		this.solicitacaoExame = solicitacaoExame;
	}

	public RealizacaoExame getRealizacaoExame()
	{
		return realizacaoExame;
	}

	public void setRealizacaoExame(RealizacaoExame realizacaoExame)
	{
		this.realizacaoExame = realizacaoExame;
	}

	/**
	 * Periodicidade do exame especificada na Solicitação / Atendimento 
	 */
	public int getPeriodicidade() 
	{
		return periodicidade;
	}

	public void setPeriodicidade(int periodicidade) 
	{
		this.periodicidade = periodicidade;
	}

	public void setProjectionColaboradorNome(String colaboradorNome) 
	{
		this.colaboradorNome = colaboradorNome;
	}

	public void setProjectionColaboradorId(Long colaboradorId) 
	{
		this.colaboradorId = colaboradorId;
	}

	public void setProjectionCandidatoNome(String candidatoNome) 
	{
		this.candidatoNome = candidatoNome;
	}

	public void setProjectionCandidatoId(Long candidatoId) 
	{
		this.candidatoId = candidatoId;
	}

	public String getColaboradorNome() 
	{
		return colaboradorNome;
	}

	public String getCandidatoNome() 
	{
		return candidatoNome;
	}

	public Long getColaboradorId() {
		return colaboradorId;
	}

	public Long getCandidatoId() {
		return candidatoId;
	}
	
	
}