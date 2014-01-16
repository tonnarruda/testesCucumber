package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="ocorrencia_sequence", allocationSize=1)
public class Ocorrencia extends AbstractModel implements Serializable
{
	@Column(length=40)
    private String descricao;
    private int pontuacao;

	@ManyToOne
	private Empresa empresa;

	@Column(length=3)
	private String codigoAC;

	private boolean integraAC;
	private boolean absenteismo;
	private boolean performance = true;
	
	public String getDescricaoComEmpresa()
	{
		if (this.empresa != null)
			return this.empresa.getNome() + " - " + this.descricao;
			
		return "";	
	}
	
	public void setProjectionEmpresaId(Long empresaId)
	{
		if (empresa == null)
			empresa = new Empresa();
		empresa.setId(empresaId);
	}

	public void setProjectionEmpresaNome(String nome)
	{
		if (empresa == null)
			empresa = new Empresa();
		empresa.setNome(nome);
	}

	public String getDescricao()
	{
		return descricao;
	}
	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}
	public int getPontuacao()
	{
		return pontuacao;
	}
	public void setPontuacao(int pontuacao)
	{
		this.pontuacao = pontuacao;
	}

	public Empresa getEmpresa()
	{
		return empresa;
	}
	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}
	public String getCodigoAC()
	{
		return codigoAC;
	}
	public void setCodigoAC(String codigoAC)
	{
		this.codigoAC = codigoAC;
	}
	public boolean getIntegraAC()
	{
		return integraAC;
	}
	public void setIntegraAC(boolean integraAC)
	{
		this.integraAC = integraAC;
	}
	
	public void setEmpresaId(Long empresaId)
	{
		if (this.empresa == null)
			this.empresa = new Empresa();
		
		this.empresa.setId(empresaId);
	}

	public boolean isAbsenteismo() {
		return absenteismo;
	}

	public void setAbsenteismo(boolean absenteismo) {
		this.absenteismo = absenteismo;
	}

	public boolean isPerformance() {
		return performance;
	}

	public void setPerformance(boolean performance) {
		this.performance = performance;
	}
}