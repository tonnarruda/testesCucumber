package com.fortes.rh.model.sesmt;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.sesmt.eSocialTabelas.DescricaoNaturezaLesao;

public class Atestado  {

	@Column(length=7)
	private String codigoCNES; 
	@Temporal(TemporalType.DATE)
	private Date dataAtendimento;
	@Column(length=5)
	private String horaAtendimento;
	@Column(length=4)
	private String duracaoTratamentoEmDias;
	@OneToOne (fetch=FetchType.LAZY)
	private DescricaoNaturezaLesao descricaoNaturezaLesao;
	@Column(length=200)
	private String descricaoComplementarLesao;
	@Column(length=100)
	private String diagnosticoProvavel;
	@Column(length=4)
	private String codCID;
	@Column(length=255)
	private String observacaoAtestado;

	private boolean possuiAtestado;
	private Boolean indicativoInternacao;
	private Boolean indicativoAfastamento;

	@Transient
	private String indicativoInternacaoString;
	@Transient
	private String indicativoAfastamentoString;
	
	//Emitente
	@Column(length=70)
	private String medicoNome;
	private Long orgaoDeClasse;
	@Column(length=14)
	private String numericoInscricao;
    @ManyToOne(fetch=FetchType.LAZY)
	private Estado ufAtestado;
	
	public String getCodigoCNES() {
		return codigoCNES;
	}
	public void setCodigoCNES(String codigoCNES) {
		this.codigoCNES = codigoCNES;
	}
	public String getHoraAtendimento() {
		return horaAtendimento;
	}
	public void setHoraAtendimento(String horaAtendimento) {
		this.horaAtendimento = horaAtendimento;
	}
	public String getDuracaoTratamentoEmDias() {
		return duracaoTratamentoEmDias;
	}
	public void setDuracaoTratamentoEmDias(String duracaoTratamentoEmDias) {
		this.duracaoTratamentoEmDias = duracaoTratamentoEmDias;
	}
	public String getDescricaoComplementarLesao() {
		return descricaoComplementarLesao;
	}
	public void setDescricaoComplementarLesao(String descricaoComplementarLesao) {
		this.descricaoComplementarLesao = descricaoComplementarLesao;
	}
	public String getDiagnosticoProvavel() {
		return diagnosticoProvavel;
	}
	public void setDiagnosticoProvavel(String diagnosticoProvavel) {
		this.diagnosticoProvavel = diagnosticoProvavel;
	}
	public String getCodCID() {
		return codCID;
	}
	public void setCodCID(String codCID) {
		this.codCID = codCID;
	}
	public String getObservacaoAtestado() {
		return observacaoAtestado;
	}
	public void setObservacaoAtestado(String observacaoAtestado) {
		this.observacaoAtestado = observacaoAtestado;
	}
	public String getMedicoNome() {
		return medicoNome;
	}
	public void setMedicoNome(String medicoNome) {
		this.medicoNome = medicoNome;
	}
	public Long getOrgaoDeClasse() {
		return orgaoDeClasse;
	}
	public void setOrgaoDeClasse(Long orgaoDeClasse) {
		this.orgaoDeClasse = orgaoDeClasse;
	}
	public String getNumericoInscricao() {
		return numericoInscricao;
	}
	public void setNumericoInscricao(String numericoInscricao) {
		this.numericoInscricao = numericoInscricao;
	}
	public Boolean getIndicativoInternacao() {
		return indicativoInternacao;
	}
	public void setIndicativoInternacao(Boolean indicativoInternacao) {
		this.indicativoInternacao = indicativoInternacao;
	}
	public Boolean getIndicativoAfastamento() {
		return indicativoAfastamento;
	}
	public void setIndicativoAfastamento(Boolean indicativoAfastamento) {
		this.indicativoAfastamento = indicativoAfastamento;
	}
	public DescricaoNaturezaLesao getDescricaoNaturezaLesao() {
		return descricaoNaturezaLesao;
	}
	public void setDescricaoNaturezaLesao(DescricaoNaturezaLesao descricaoNaturezaLesao) {
		this.descricaoNaturezaLesao = descricaoNaturezaLesao;
	}
	public Estado getUfAtestado() {
		return ufAtestado;
	}
	public void setUfAtestado(Estado ufAtestado) {
		this.ufAtestado = ufAtestado;
	}
	public Date getDataAtendimento() {
		return dataAtendimento;
	}
	public void setDataAtendimento(Date dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
	}
	public void setIndicativoInternacaoString(String indicativoInternacaoString) {
		if(indicativoInternacaoString.equals("true"))
			this.indicativoInternacao = true;
		else if(indicativoInternacaoString.equals("false"))
			this.indicativoInternacao = false;
		else
			this.indicativoInternacao = null;
	}
	public void setIndicativoAfastamentoString(String indicativoAfastamentoString) {
		if(indicativoAfastamentoString.equals("true"))
			this.indicativoAfastamento = true;
		else if(indicativoAfastamentoString.equals("false"))
			this.indicativoAfastamento = false;
		else
			this.indicativoAfastamento = null;
	}
	public String getIndicativoInternacaoString() {
		if(indicativoInternacao != null)
			return indicativoInternacao.toString();
		else 
			return null;
	}
	public String getIndicativoAfastamentoString() {
		if(indicativoAfastamento != null)
			return indicativoAfastamento.toString();
		else 
			return null;
	}
	public boolean isPossuiAtestado() {
		return possuiAtestado;
	}
	public void setPossuiAtestado(boolean possuiAtestado) {
		this.possuiAtestado = possuiAtestado;
	}
}