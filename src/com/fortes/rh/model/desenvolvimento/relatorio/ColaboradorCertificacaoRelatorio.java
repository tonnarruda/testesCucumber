package com.fortes.rh.model.desenvolvimento.relatorio;

import java.io.Serializable;

import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;

// Colaborador x Certificacao
public class ColaboradorCertificacaoRelatorio implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	String certificacaoNome;
	Estabelecimento estabelecimento;
	AreaOrganizacional areaOrganizacional;
	Long colaboradorId;
	String colaboradorNome;
	Curso curso;
	Turma turma;
	String periodo;
	String aprovado;
	Double valorAvaliacao;

	private Colaborador colaborador;
	
	public ColaboradorCertificacaoRelatorio(Colaborador colaborador, Certificacao certificacao, Curso curso) {
		this.colaborador = colaborador;
		this.colaboradorNome = colaborador.getNome();
		this.certificacaoNome = certificacao.getNome();
		this.curso = curso;
		this.turma = new Turma();
		turma.setDescricao("-");
		this.aprovado = "-";
	}

	public ColaboradorCertificacaoRelatorio() {
		// TODO Auto-generated constructor stub
	}

	public String getCertificacaoNome() {
		return certificacaoNome;
	}

	public void setCertificacaoNome(String certificacaoNome) {
		this.certificacaoNome = certificacaoNome;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public AreaOrganizacional getAreaOrganizacional() {
		return areaOrganizacional;
	}

	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional) {
		this.areaOrganizacional = areaOrganizacional;
	}

	public Long getColaboradorId() {
		return colaboradorId;
	}

	public void setColaboradorId(Long colaboradorId) {
		this.colaboradorId = colaboradorId;
	}

	public String getColaboradorNome() {
		return colaboradorNome;
	}

	public void setColaboradorNome(String colaboradorNome) {
		this.colaboradorNome = colaboradorNome;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getAprovado() {
		return aprovado;
	}

	public void setAprovado(String aprovado) {
		this.aprovado = aprovado;
	}

	public void setAprovado(Boolean aprovado) {
		if (aprovado != null)
			this.aprovado = aprovado ? "Sim" : "Não";
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public Double getValorAvaliacao()
	{
		return valorAvaliacao;
	}

	public void setValorAvaliacao(Double valorAvaliacao)
	{
		this.valorAvaliacao = valorAvaliacao;
	}

}
