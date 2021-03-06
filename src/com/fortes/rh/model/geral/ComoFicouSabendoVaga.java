package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.security.auditoria.ChaveDaAuditoria;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="comoFicouSabendoVaga_sequence", allocationSize=1)
public class ComoFicouSabendoVaga extends AbstractModel implements Serializable
{
	@ChaveDaAuditoria
	private String nome;
	@Transient
	private Integer qtd;
	@Transient
	private double percentual;

	public ComoFicouSabendoVaga() 
	{
	}

	public ComoFicouSabendoVaga(Long id, String nome) 
	{
		setId(id);
		this.nome = nome;
	}
	
	public ComoFicouSabendoVaga(String nome, double percentual)
	{
		this.nome = nome;
		this.percentual = percentual;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPercentual() {
		return percentual;
	}

	public void setPercentual(double percentual) {
		this.percentual = percentual;
	}

	public Integer getQtd() {
		return qtd;
	}

	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}
}
