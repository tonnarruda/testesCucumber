package com.fortes.rh.model.captacao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.MathUtil;

@Entity
public class Competencia
{
	@Transient
	public static final Character CONHECIMENTO = 'C';
	@Transient
	public static final Character HABILIDADE = 'H';
	@Transient
	public static final Character ATITUDE = 'A';
	
	@Id
	private Long id;
	@Column(length=100)
	private String nome;
	@ManyToOne
	private Empresa empresa;
	@Lob
	private String observacao;
	private Character tipo;

	@Transient
	private NivelCompetencia nivelCompetencia;
	@Transient
	private Double performance;
	@Transient
	private boolean competenciaAbaixoDoNivelExigido; 
	@Transient
	private Integer pesoCompetencia;
	@Transient
	private String descricaoNivelCompetenciaExigido;
	@Transient
	private String descricaoNivelCompetencia;
	
	public Competencia(){
		super();
	}
	
	public Competencia(String nome, Double performance, Integer pesoCompetencia,String descricaoNivelCompetenciaExigido) 
	{
		this.nome = nome;
		this.performance = performance;
		this.pesoCompetencia = pesoCompetencia;
		this.descricaoNivelCompetenciaExigido = descricaoNivelCompetenciaExigido;
	}

	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}
	
	public String getNome() 
	{
		return nome;
	}
	
	public void setNome(String nome) 
	{
		this.nome = nome;
	}
	
	public Empresa getEmpresa() 
	{
		return empresa;
	}
	
	public void setEmpresa(Empresa empresa) 
	{
		this.empresa = empresa;
	}
	
	public String getObservacao() 
	{
		return observacao;
	}
	
	public void setObservacao(String observacao) 
	{
		this.observacao = observacao;
	}
	
	public Character getTipo() 
	{
		return tipo;
	}
	
	public void setTipo(Character tipo) 
	{
		this.tipo = tipo;
	}

	public NivelCompetencia getNivelCompetencia()
	{
		return nivelCompetencia;
	}
	
	public void setNivelCompetencia(NivelCompetencia nivelCompetencia)
	{
		this.nivelCompetencia = nivelCompetencia;
	}
	
	@Override
	public boolean equals(Object object)
	{
    	if(object == null)
    		return false;

    	if(object == this)
    		return true;

    	if (!object.getClass().getName().equals(this.getClass().getName()))
    		return false;

    	if(this.getId() == null)
    		return false;

    	return this.getId().equals(((Competencia)object).getId()) && this.getTipo().equals(((Competencia)object).getTipo());
	}

	public Double getPerformance() {
		return MathUtil.round(performance, 2);
	}

	public void setPerformance(Double performance) {
		this.performance = performance;
	}

	public boolean isCompetenciaAbaixoDoNivelExigido() {
		return competenciaAbaixoDoNivelExigido;
	}

	public void setCompetenciaAbaixoDoNivelExigido(boolean competenciaAbaixoDoNivelExigido) {
		this.competenciaAbaixoDoNivelExigido = competenciaAbaixoDoNivelExigido;
	}

	public Integer getPesoCompetencia() {
		return pesoCompetencia;
	}

	public void setPesoCompetencia(Integer pesoCompetencia) {
		this.pesoCompetencia = pesoCompetencia;
	}

	public String getDescricaoNivelCompetenciaExigido() {
		return descricaoNivelCompetenciaExigido;
	}

	public void setDescricaoNivelCompetenciaExigido(String descricaoNivelCompetenciaExigido) {
		this.descricaoNivelCompetenciaExigido = descricaoNivelCompetenciaExigido;
	}

	public String getDescricaoNivelCompetencia() {
		return descricaoNivelCompetencia;
	}

	public void setDescricaoNivelCompetencia(String descricaoNivelCompetencia) {
		this.descricaoNivelCompetencia = descricaoNivelCompetencia;
	}
}