package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="curso_sequence", allocationSize=1)
public class Curso extends AbstractModel implements Serializable
{
    @ManyToOne
    private Empresa empresa;
    @Column(length=100)
    private String nome;
    @Column(length=20)
    private Integer cargaHoraria;
    @Lob
    private String conteudoProgramatico;
    @Lob
    private String criterioAvaliacao;
    @ManyToMany(fetch=FetchType.LAZY)
    private Collection<AvaliacaoCurso> avaliacaoCursos;
    @Column(length=20)
    private Double percentualMinimoFrequencia;

	@ManyToMany(fetch=FetchType.LAZY, mappedBy="cursos")
	private Collection<Certificacao> certificacaos;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="curso")
	private Collection<Turma> turmas;

	public Collection<Turma> getTurmas()
	{
		return turmas;
	}
	public void setTurmas(Collection<Turma> turmas)
	{
		this.turmas = turmas;
	}
	public Curso()
	{

	}
	public Curso(Long id, String nome, Integer cargaHoraria, String conteudoProgramatico, String criterioAvaliacao, Double percentualMinimoFrequencia)
	{
		this.setId(id);
		this.nome = nome;
		this.cargaHoraria = cargaHoraria;
		this.conteudoProgramatico = conteudoProgramatico;
		this.criterioAvaliacao = criterioAvaliacao;
		this.percentualMinimoFrequencia = percentualMinimoFrequencia;
	}

	public void setProjectionEmpresaId(Long empresaId)
	{
		if (this.empresa == null)
			this.empresa = new Empresa();

		empresa.setId(empresaId);
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
	public String getConteudoProgramatico()
	{
		return conteudoProgramatico;
	}
	public void setConteudoProgramatico(String conteudoProgramatico)
	{
		this.conteudoProgramatico = conteudoProgramatico;
	}
	public Integer getCargaHoraria()
	{
		return cargaHoraria;
	}
	public void setCargaHoraria(Integer cargaHoraria)
	{
		this.cargaHoraria = cargaHoraria;
	}
	public String getCriterioAvaliacao()
	{
		return criterioAvaliacao;
	}
	public void setCriterioAvaliacao(String criterioAvaliacao)
	{
		this.criterioAvaliacao = criterioAvaliacao;
	}
	public Collection<AvaliacaoCurso> getAvaliacaoCursos()
	{
		return avaliacaoCursos;
	}
	public void setAvaliacaoCursos(Collection<AvaliacaoCurso> avaliacaoCursos)
	{
		this.avaliacaoCursos = avaliacaoCursos;
	}
	public Collection<Certificacao> getCertificacaos()
	{
		return certificacaos;
	}
	public void setCertificacaos(Collection<Certificacao> certificacaos)
	{
		this.certificacaos = certificacaos;
	}
	
	@Override
	public String toString() {
		ToStringBuilder string = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
		string.append("id", this.getId());
		string.append(this.nome);
		string.append(this.cargaHoraria);
		string.append(this.conteudoProgramatico);
		return string.toString();
	}
	public Double getPercentualMinimoFrequencia() {
		return percentualMinimoFrequencia;
	}
	
	public void setPercentualMinimoFrequencia(Double percentualMinimoFrequencia) {
		this.percentualMinimoFrequencia = percentualMinimoFrequencia;
	}

}