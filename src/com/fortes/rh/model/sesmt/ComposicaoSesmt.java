package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="composicaoSesmt_sequence", allocationSize=1)
public class ComposicaoSesmt extends AbstractModel implements Serializable
{
	@ManyToOne
	Empresa empresa;
	@Temporal(TemporalType.DATE)
	Date data;
	Integer qtdTecnicosSeguranca;
	Integer qtdEngenheirosSeguranca;
	Integer qtdAuxiliaresEnfermagem;
	Integer qtdEnfermeiros;
	Integer qtdMedicos;
	
	public void setProjectionEmpresaId(Long projectionEmpresaId) {
		if (empresa == null)
			empresa = new Empresa();
			
		empresa.setId(projectionEmpresaId);
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public Integer getQtdTecnicosSeguranca() {
		return qtdTecnicosSeguranca;
	}
	public void setQtdTecnicosSeguranca(Integer qtdTecnicosSeguranca) {
		this.qtdTecnicosSeguranca = qtdTecnicosSeguranca;
	}
	public Integer getQtdEngenheirosSeguranca() {
		return qtdEngenheirosSeguranca;
	}
	public void setQtdEngenheirosSeguranca(Integer qtdEngenheirosSeguranca) {
		this.qtdEngenheirosSeguranca = qtdEngenheirosSeguranca;
	}
	public Integer getQtdAuxiliaresEnfermagem() {
		return qtdAuxiliaresEnfermagem;
	}
	public void setQtdAuxiliaresEnfermagem(Integer qtdAuxiliaresEnfermagem) {
		this.qtdAuxiliaresEnfermagem = qtdAuxiliaresEnfermagem;
	}
	public Integer getQtdEnfermeiros() {
		return qtdEnfermeiros;
	}
	public void setQtdEnfermeiros(Integer qtdEnfermeiros) {
		this.qtdEnfermeiros = qtdEnfermeiros;
	}
	public Integer getQtdMedicos() {
		return qtdMedicos;
	}
	public void setQtdMedicos(Integer qtdMedicos) {
		this.qtdMedicos = qtdMedicos;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
}
