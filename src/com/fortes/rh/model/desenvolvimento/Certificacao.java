package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Empresa;

@Entity
@SequenceGenerator(name="sequence", sequenceName="certificacao_sequence", allocationSize=1)
public class Certificacao extends AbstractModel implements Serializable, Cloneable
{
	@Transient private static final long serialVersionUID = 1L;
	
	private String nome;
	@ManyToMany(fetch=FetchType.LAZY)
	private Collection<Curso> cursos;
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="certificacaos")
	private Collection<FaixaSalarial> faixaSalarials;
	@ManyToOne(fetch=FetchType.LAZY)
	private Empresa empresa;
	@Column
	private Integer periodicidade;
	
	@ManyToMany(fetch=FetchType.LAZY)
	private Collection<AvaliacaoPratica> avaliacoesPraticas;
	
	@OneToMany(fetch=FetchType.LAZY,  mappedBy="certificacao")
	private Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacaoPraticas;
	
	@OneToOne
	private Certificacao certificacaoPreRequisito;
	
	@Transient
	private Boolean aprovadoNaTurma;
	
	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public Collection<Curso> getCursos()
	{
		return cursos;
	}

	public void setCursos(Collection<Curso> cursos)
	{
		this.cursos = cursos;
	}

	public Collection<FaixaSalarial> getFaixaSalarials()
	{
		return faixaSalarials;
	}

	public void setFaixaSalarials(Collection<FaixaSalarial> faixaSalarials)
	{
		this.faixaSalarials = faixaSalarials;
	}

	public Empresa getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public Integer getPeriodicidade() {
		return periodicidade;
	}

	public String getPeriodicidadeFormatada() 
	{
		if(periodicidade == null)
			return "Sem peridicidade";
		else if(periodicidade == 1)
			return periodicidade + " mês";
		else
			return periodicidade + " meses";
	}

	public void setPeriodicidade(Integer periodicidade) {
		this.periodicidade = periodicidade;
	}

	public Collection<AvaliacaoPratica> getAvaliacoesPraticas() {
		return avaliacoesPraticas;
	}

	public void setAvaliacoesPraticas(Collection<AvaliacaoPratica> avaliacoesPraticas) {
		this.avaliacoesPraticas = avaliacoesPraticas;
	}

	public Collection<ColaboradorAvaliacaoPratica> getColaboradorAvaliacaoPraticas() {
		return colaboradorAvaliacaoPraticas;
	}

	public void setColaboradorAvaliacaoPraticas(Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacaoPraticas) {
		this.colaboradorAvaliacaoPraticas = colaboradorAvaliacaoPraticas;
	}
	
	public Certificacao getCertificacaoPreRequisito() {
		return certificacaoPreRequisito;
	}

	public void setCertificacaoPreRequisito(Certificacao certificacaoPreRequisito) {
		this.certificacaoPreRequisito = certificacaoPreRequisito;
	}
	
	public void setCertificacaoPreRequisitoId(Long certificacaoPreRequisitoId){
		inicializarCertificacaoPreRequisito();
		this.certificacaoPreRequisito.setId(certificacaoPreRequisitoId);
	}
	
	public void setCertificacaoPreRequisitoNome(String certificacaoPreRequisitoNome){
		inicializarCertificacaoPreRequisito();
		this.certificacaoPreRequisito.setNome(certificacaoPreRequisitoNome);
	}
	
	private void inicializarCertificacaoPreRequisito(){
		if(this.certificacaoPreRequisito == null)
			this.certificacaoPreRequisito = new Certificacao();
	}
	
	public Boolean getAprovadoNaTurma() {
		return aprovadoNaTurma;
	}
	
	public String getAprovadoNaTurmaString() {
		if(aprovadoNaTurma != null && aprovadoNaTurma)
			return "Sim";
		
		return "Não";
	}

	public void setAprovadoNaTurma(Boolean aprovadoNaTurma) {
		this.aprovadoNaTurma = aprovadoNaTurma;
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
}