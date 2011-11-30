package com.fortes.rh.model.sesmt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.security.auditoria.ChaveDaAuditoria;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="afastamento_sequence", allocationSize=1)
public class Afastamento extends AbstractModel implements Serializable
{
	@Column(length=100)
	private String descricao;

	private boolean inss;
	
	@Transient
	private int qtd;

	public String getInssFmt()
	{
		return inss ? "Sim" : "Não";
	}

	@ChaveDaAuditoria
	public String getDescricao()
	{
		return descricao;
	}
	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}
	public boolean isInss()
	{
		return inss;
	}
	public void setInss(boolean inss)
	{
		this.inss = inss;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
}