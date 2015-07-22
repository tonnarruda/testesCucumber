package com.fortes.rh.model.avaliacao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.MathUtil;
import com.fortes.security.auditoria.ChaveDaAuditoria;

@Entity
@SequenceGenerator(name="sequence", sequenceName="questionario_sequence", allocationSize=1)//TODO utilizamos a sequence do questionario para não alterar as consultas, Francisco
public class Avaliacao extends AbstractModel implements Serializable, Cloneable
{
	@Transient private static final long serialVersionUID = 1L;

	@Column(length=100)
	@ChaveDaAuditoria
    private String titulo;
	
	@Lob
	private String cabecalho;
	
	private Double percentualAprovacao;
	
	private boolean ativo;

	private boolean exibeResultadoAutoavaliacao;
	
	private char tipoModeloAvaliacao = 'D';
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Empresa empresa;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private PeriodoExperiencia periodoExperiencia;

	private boolean avaliarCompetenciasCargo;
	
	@Transient
	private int totalColab;
	
	public Avaliacao(){
		super();
	}

	public Avaliacao(Long id){
		this.setId(id);
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
	
	public void setProjectionEmpresaNome(String empresaNome)
	{
		if (empresa == null)
			empresa = new Empresa();
		
		empresa.setNome(empresaNome);
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCabecalho() {
		return cabecalho;
	}

	public void setCabecalho(String cabecalho) {
		this.cabecalho = cabecalho;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public int getTotalColab() {
		return totalColab;
	}

	public void setTotalColab(int totalColab) {
		this.totalColab = totalColab;
	}
	
	public char getTipoModeloAvaliacao() {
		return tipoModeloAvaliacao;
	}

	public void setTipoModeloAvaliacao(char tipoModeloAvaliacao) {
		this.tipoModeloAvaliacao = tipoModeloAvaliacao;
	}

	public PeriodoExperiencia getPeriodoExperiencia() {
		return periodoExperiencia;
	}

	public void setPeriodoExperiencia(PeriodoExperiencia periodoExperiencia) {
		this.periodoExperiencia = periodoExperiencia;
	}

	public boolean isExibeResultadoAutoavaliacao() {
		return exibeResultadoAutoavaliacao;
	}

	public void setExibeResultadoAutoavaliacao(boolean exibeResultadoAutoavaliacao) {
		this.exibeResultadoAutoavaliacao = exibeResultadoAutoavaliacao;
	}

	public boolean isAvaliarCompetenciasCargo() {
		return avaliarCompetenciasCargo;
	}

	public void setAvaliarCompetenciasCargo(boolean avaliarCompetenciasCargo) {
		this.avaliarCompetenciasCargo = avaliarCompetenciasCargo;
	}

	public Double getPercentualAprovacao() {
		return percentualAprovacao;
	}
	
	public String getPercentualAprovacaoFormatado() {
		String percentualFmt = "";
		if (percentualAprovacao != null)
			percentualFmt = MathUtil.formataValor(percentualAprovacao) + "%";

		return percentualFmt;
	}

	public void setPercentualAprovacao(Double percentualAprovacao) {
		this.percentualAprovacao = percentualAprovacao;
	}

	public String getTipoModeloAvaliacaoDescricao() 
	{
		return new TipoModeloAvaliacao().get(this.tipoModeloAvaliacao); 	
	}
}
