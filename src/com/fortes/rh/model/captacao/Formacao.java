package com.fortes.rh.model.captacao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.AreaFormacao;
import com.fortes.rh.model.geral.Colaborador;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="formacao_sequence", allocationSize=1)
public class Formacao extends AbstractModel implements Serializable
{
	@ManyToOne
	private Candidato candidato;
	@ManyToOne
	private Colaborador colaborador;
	private char situacao; /* C - completo, A - em andamento, I-incompleto */
	private char tipo; /* T - técnico, G - graduação, M - mestrado, E - especialização, D - doutorado, P - pós-doutorado */
	@Column(length=100)
	private String curso;
	@Column(length=100)
	private String local;
	@Column(length=30)
	private String conclusao;
	@ManyToOne
	private AreaFormacao areaFormacao;

	//projection
	
	public void setAreaFormacaoId(Long areaFormacaoId)
	{
		if (this.areaFormacao == null)
			this.areaFormacao = new AreaFormacao();
		
		this.areaFormacao.setId(areaFormacaoId);
	}
	
	public void setAreaFormacaoNome(String areaFormacaoNome)
	{
		if (this.areaFormacao == null)
			this.areaFormacao = new AreaFormacao();
		
		this.areaFormacao.setNome(areaFormacaoNome);
	}

	public void setCandidatoId(Long candidatoId)
	{
		if (candidato == null)
			candidato = new Candidato();

		candidato.setId(candidatoId);
	}

	public String getConclusao() {
		return conclusao;
	}

	public void setConclusao(String conclusao) {
		this.conclusao = conclusao;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public char getSituacao() {
		return situacao;
	}

	public String getSituacaoDescricao()
	{
		switch (situacao)
		{
			case 'C':
				return "Completo";

			case 'A':
				return "Andamento";

			case 'I':
				return "Incompleto";

			default:
				return " ";

		}

	}

	public String getTipoDescricao()
	{
		switch (tipo)
		{
			case 'T':
				return "Técnico";

			case 'G':
				return "Graduação";

			case 'M':
				return "Mestrado";

			case 'E':
				return "Especialização";

			case 'D':
				return "Doutorado";

			case 'P':
				return "Pós Graduação";

			default:
				return " ";

		}

	}

	public void setSituacao(char situacao) {
		this.situacao = situacao;
	}

	public char getTipo() {
		return tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
	}

	public Candidato getCandidato() {
		return candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

	public AreaFormacao getAreaFormacao()
	{
		return areaFormacao;
	}

	public void setAreaFormacao(AreaFormacao areaFormacao)
	{
		this.areaFormacao = areaFormacao;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public void setColaboradorId(Long colaboradorId)
	{
		if(colaboradorId != null){
			if (colaborador == null)
				colaborador = new Colaborador();
	
			colaborador.setId(colaboradorId);
		}
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("id", this.getId()).append("colaborador", this.colaborador)
				.append("tipo", this.tipo).append("situacao", this.situacao)
				.append("local", this.local).append("curso", this.curso)
				.append("areaFormacao", this.areaFormacao).append("candidato",
						this.candidato).append("conclusao", this.conclusao)
				.toString();
	}

	public void atualizaColaboradorECandidato(Colaborador colaborador) {
		this.colaborador = colaborador;
		if (colaborador.jaFoiUmCandidato())
			this.candidato = colaborador.getCandidato();
		else
			this.candidato = null;
	}
}
