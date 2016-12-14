package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="cursoLnt_sequence", allocationSize=1)
public class CursoLnt extends AbstractModel implements Serializable
{
	private String nomeNovoCurso;
	@Column(length=20)
	private Integer cargaHoraria;
	private Double custo;
	@Lob
	private String conteudoProgramatico;
	@Lob
	private String justificativa;
	@ManyToOne
	private Lnt lnt;
	@ManyToOne
	private Curso curso;
	@OneToMany(fetch = FetchType.LAZY, mappedBy="cursoLnt", cascade=CascadeType.ALL)
	private Collection<ParticipanteCursoLnt> participanteCursoLnts;
	@OneToMany(fetch = FetchType.LAZY, mappedBy="cursoLnt", cascade=CascadeType.ALL)
	private Collection<ColaboradorTurma> colaboradorTurmas;

	@Transient
	private boolean existePerticipanteASerRelacionado;
	
	public CursoLnt() {
	}
	
	public CursoLnt(String nome) {
		this.nomeNovoCurso = nome;
	}
	
	public CursoLnt(Long id) {
		setId(id);
	}
	
	public CursoLnt(Long id, String nome, Long cursoId) {
		setId(id);
		this.nomeNovoCurso = nome;
		setCursoId(cursoId);
	}
	
	public String getNomeNovoCurso() {
		return nomeNovoCurso;
	}

	public void setNomeNovoCurso(String nomeNovoCurso) {
		this.nomeNovoCurso = nomeNovoCurso;
	}

	public Integer getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(Integer cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public void setCargaHorariaMinutos(String cargaHorariaMinutos) 
	{
		if (!cargaHorariaMinutos.equals("    :  ") && !cargaHorariaMinutos.equals("0000:00")) {
			Integer hora = Integer.parseInt(cargaHorariaMinutos.substring(0, (cargaHorariaMinutos.length() - 3)));
			Integer minutos = Integer.parseInt(cargaHorariaMinutos.substring(cargaHorariaMinutos.length() - 2, cargaHorariaMinutos.length()));
			setCargaHoraria(hora * 60 + minutos);
		}
	}
	
	public static String formataCargaHorariaMinutos(Integer cargaHoraria, String caracterAntecessorHora)
	{
		if(cargaHoraria == null)
			return "";
		
		Integer hora = cargaHoraria/60;
		Integer minutos = cargaHoraria%60;
		
		return (StringUtils.leftPad(hora.toString(), 4, caracterAntecessorHora) + ":" +StringUtils.leftPad(minutos.toString(), 2, "0")).trim();		
	}
	
	public String getCargaHorariaMinutos() {
		return formataCargaHorariaMinutos(cargaHoraria, "");		
	}
	
	public Double getCusto() {
		return custo;
	}

	public void setCusto(Double custo) {
		this.custo = custo;
	}

	public String getConteudoProgramatico() {
		return conteudoProgramatico;
	}

	public void setConteudoProgramatico(String conteudoProgramatico) {
		this.conteudoProgramatico = conteudoProgramatico;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public Lnt getLnt() {
		return lnt;
	}

	public void setLnt(Lnt lnt) {
		this.lnt = lnt;
	}

	public void setLntId(Long lntId) {
		if(this.lnt == null)
			this.lnt = new Lnt();
		
		this.lnt.setId(lntId);
	}
	
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
//		if(curso != null && (curso.getId() == null || curso.getId() == 0L) )
//			this.curso = null;
//		else
			this.curso = curso;
	}

	public Collection<ParticipanteCursoLnt> getParticipanteCursoLnts()
	{
		return participanteCursoLnts;
	}

	public void setParticipanteCursoLnts(Collection<ParticipanteCursoLnt> participanteCursoLnts)
	{
		this.participanteCursoLnts = participanteCursoLnts;
	}
	
	public Long getCursoId() {
		if (curso != null)
			return curso.getId();
		else
			return 0L;
	}
	
	public void setCursoId(Long cursoId) {
		if (cursoId != null && cursoId != 0L) {
			if(this.curso == null)
				this.curso = new Curso();
		
			curso.setId(cursoId);
		}
	}

	public Collection<ColaboradorTurma> getColaboradorTurmas() {
		return colaboradorTurmas;
	}

	public void setColaboradorTurmas(Collection<ColaboradorTurma> colaboradorTurmas) {
		this.colaboradorTurmas = colaboradorTurmas;
	}

	public boolean isExistePerticipanteASerRelacionado() {
		return existePerticipanteASerRelacionado;
	}

	public void setExistePerticipanteASerRelacionado(
			boolean existePerticipanteASerRelacionado) {
		this.existePerticipanteASerRelacionado = existePerticipanteASerRelacionado;
	}
}
