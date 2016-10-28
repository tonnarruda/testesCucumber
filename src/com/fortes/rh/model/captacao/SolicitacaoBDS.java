/* Autor: Robertson Freitas
 * Data: 04/07/2006
 * Requisito: RFA019 - Solicitar Banco de Dados Solidário */
package com.fortes.rh.model.captacao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaOrganizacional;

@Entity
@SequenceGenerator(name="sequence", sequenceName="solicitacaobds_sequence", allocationSize=1)
public class SolicitacaoBDS extends AbstractModel implements Serializable
{
    @Temporal(TemporalType.DATE)
    private Date data;
    private char tipo;
    private char escolaridade;
    private String experiencia;
    private char sexo;
    private int idadeMinima;
    private int idadeMaxima;
    private String observacao;

    @ManyToMany
    private Collection<EmpresaBds> empresasBDSs;

    @ManyToOne
    private Cargo cargo;
    @ManyToOne
    private AreaOrganizacional areaOrganizacional;

	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public char getEscolaridade() {
		return escolaridade;
	}
	public void setEscolaridade(char escolaridade) {
		this.escolaridade = escolaridade;
	}
	public String getExperiencia() {
		return experiencia;
	}
	public void setExperiencia(String experiencia) {
		this.experiencia = experiencia;
	}
	public int getIdadeMaxima() {
		return idadeMaxima;
	}
	public void setIdadeMaxima(int idadeMaxima) {
		this.idadeMaxima = idadeMaxima;
	}
	public int getIdadeMinima() {
		return idadeMinima;
	}
	public void setIdadeMinima(int idadeMinima) {
		this.idadeMinima = idadeMinima;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public char getSexo() {
		return sexo;
	}
	public void setSexo(char sexo) {
		this.sexo = sexo;
	}
	public char getTipo() {
		return tipo;
	}
	public void setTipo(char tipo) {
		this.tipo = tipo;
	}
	public AreaOrganizacional getAreaOrganizacional() {
		return areaOrganizacional;
	}
	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional) {
		this.areaOrganizacional = areaOrganizacional;
	}
	public Cargo getCargo() {
		return cargo;
	}
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	public Collection<EmpresaBds> getEmpresasBDSs() {
		return empresasBDSs;
	}
	public void setEmpresasBDSs(Collection<EmpresaBds> empresasBDSs) {
		this.empresasBDSs = empresasBDSs;
	}

	public String toString()
	{
		ToStringBuilder string = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
		string.append("id", this.getId());
		string.append("cargo", this.cargo);
		string.append("areaOrganizacional", this.areaOrganizacional);
		string.append("tipo", this.tipo);
		string.append("data", this.data);
		string.append("idadeMinima", this.idadeMinima);
		string.append("idadeMaxima", this.idadeMaxima);
		string.append("sexo", this.sexo);
		string.append("escolaridade", this.escolaridade);
		string.append("experiencia", this.experiencia);
		string.append("observacao", this.observacao);

		return string.toString();
	}
}