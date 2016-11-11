package com.fortes.rh.model.captacao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.MathUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="experiencia_sequence", allocationSize=1)
public class Experiencia extends AbstractModel implements Serializable
{
	@ManyToOne
	private Candidato candidato;
	@ManyToOne
	private Colaborador colaborador;
	@Column(length=100)
	private String empresa;
	@Temporal(TemporalType.DATE)
	private Date dataAdmissao;
	@Temporal(TemporalType.DATE)
	private Date dataDesligamento;
	
	private String observacao;
	@Column(length=100)
	private String nomeMercado;
	@ManyToOne
	private Cargo cargo;
	
	private Double salario;
	
	@Column(length=100)
	private String motivoSaida;
	@Column(length=15)
	private String contatoEmpresa;

	public Experiencia() {
		super();
	}
	
	public Experiencia(Long id, Date dataAdmissao, Date dataDesligamento, Long cargoId) {
		setId(id);
		setDataAdmissao(dataAdmissao);
		setDataDesligamento(dataDesligamento);
		setCargoId(cargoId);
	}

	public void setProjectionCandidatoId(Long candidatoId)
	{
		if (this.candidato == null)
			this.candidato = new Candidato();
		this.candidato.setId(candidatoId);
	}

	public void setProjectionCargoId(Long cargoId)
	{
		if (this.cargo == null)
			this.cargo = new Cargo();
		this.cargo.setId(cargoId);
	}

	public String getTempoExperiencia()
	{
		return DateUtil.getIntervalDateString(dataAdmissao, dataDesligamento);
	}

	public String getPeriodoFormatado()
	{
		String result = DateUtil.formataDate(dataAdmissao, "dd/MM/yyyy");
		if (dataDesligamento != null)
			result += " a " + DateUtil.formataDate(dataDesligamento, "dd/MM/yyyy");
		else
			result += " até o momento";

		return result + " " + DateUtil.getIntervalDateString(dataAdmissao, dataDesligamento);
	}

	public Date getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public Date getDataDesligamento() {
		return dataDesligamento;
	}

	public void setDataDesligamento(Date dataDesligamento) {
		this.dataDesligamento = dataDesligamento;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Candidato getCandidato() {
		return candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

	public void setProjectionCandiatoSolicitacaoId(Long id)	
	{
		candidato.setId(id);
	}
	
	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public Cargo getCargo()
	{
		return cargo;
	}

	public void setCargo(Cargo cargo)
	{
		this.cargo = cargo;
	}

	public String getNomeMercado()
	{
		return nomeMercado;
	}

	public void setNomeMercado(String nomeMercado)
	{
		this.nomeMercado = nomeMercado;
	}

	public String getNomeFuncao()
	{
		String nomeFuncao = "";

		if(cargo != null && cargo.getNomeMercado() != null && !cargo.getNomeMercado().equals(""))
			nomeFuncao = cargo.getNomeMercado();
		else if(nomeMercado != null && !nomeMercado.equals(""))
			nomeFuncao = nomeMercado;

		return nomeFuncao;
	}

	public void setCandidatoId(Long candidatoId)
	{
		if (candidato == null)
			candidato = new Candidato();

		candidato.setId(candidatoId);
	}

	public void setColaboradorId(Long colaboradorId)
	{
		if (colaborador == null)
			colaborador = new Colaborador();

		colaborador.setId(colaboradorId);
	}

	public void setCargoId(Long cargoId)
	{
		if(cargoId != null){
			if (cargo == null)
				cargo = new Cargo();

			cargo.setId(cargoId);
		}
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("id", this.getId())
				.append("candidato", this.candidato)
				.append("colaborador", this.colaborador)
				.append("empresa", this.empresa)
				.append("funcao", this.getNomeFuncao())
				.append("dataAdmissao", this.dataAdmissao)
				.append("dataDesligamento", this.dataDesligamento)
				.append("empresa", this.empresa)
				.append("observacao", this.observacao).toString();
	}

	public Double getSalario() {
		return salario;
	}
	public String getSalarioFormatado()
	{
		String salarioFmt = "";
		if (salario != null)
		salarioFmt = MathUtil.formataValor(salario);
		
		return salarioFmt;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public String getMotivoSaida() {
		return motivoSaida;
	}

	public void setMotivoSaida(String motivoSaida) {
		this.motivoSaida = motivoSaida;
	}

	public void atualizaColaboradorECandidato(Colaborador colaborador) {
		this.colaborador = colaborador;
		if (colaborador.jaFoiUmCandidato())
			this.candidato = colaborador.getCandidato();
		else
			this.candidato = null;
	}

	public boolean possuiCargo() {
		return !(cargo == null || cargo.getId() == null);
	}

	public String getContatoEmpresa() {
		return contatoEmpresa;
	}

	public void setContatoEmpresa(String contatoEmpresa) {
		this.contatoEmpresa = contatoEmpresa;
	}
}