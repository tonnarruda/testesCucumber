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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="curso_sequence", allocationSize=1)
public class Curso extends AbstractModel implements Serializable, Cloneable
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

	public Object clone()
	{
	   try
	   {
	      return super.clone();
	   }
	   catch (CloneNotSupportedException e)
	   {
		   e.printStackTrace();
	      throw new Error("Ocorreu um erro interno no sistema. Não foi possível clonar o objeto, curso.");
	   }
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
	
	public void setEmpresaId(Long empresaId)
	{
		if (this.empresa == null)
			this.empresa = new Empresa();
		
		this.empresa.setId(empresaId);
	}

	public Long getEmpresaId()
	{
		return this.empresa.getId();
	}
	
	public static String formataCargaHorariaMinutos(Integer cargaHoraria, String caracterAntecessorHora) {
		
		if(cargaHoraria == null)
			return "";
		
		Integer hora = cargaHoraria/60;
		Integer minutos = cargaHoraria%60;
		
		return (StringUtils.leftPad(hora.toString(), 4, caracterAntecessorHora) + ":" +StringUtils.leftPad(minutos.toString(), 2, "0")).trim();		
	}
	
	public String getCargaHorariaMinutos() {
		return formataCargaHorariaMinutos(cargaHoraria, "");		
	}

	public String getCargaHorariaMinutosFormatado() {
		return formataCargaHorariaMinutos(cargaHoraria, "0");		
	}
	
	public void setCargaHorariaMinutos(String cargaHorariaMinutos) {
		if(!cargaHorariaMinutos.equals("    :  ") && !cargaHorariaMinutos.equals("0000:00"))
		{
			Integer hora = Integer.parseInt(cargaHorariaMinutos.substring(0, (cargaHorariaMinutos.length()-3)));
			Integer minutos = Integer.parseInt(cargaHorariaMinutos.substring(cargaHorariaMinutos.length()-2, cargaHorariaMinutos.length()));
			
			setCargaHoraria(hora*60 + minutos);
		}
	}
}