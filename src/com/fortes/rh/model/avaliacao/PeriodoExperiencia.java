package com.fortes.rh.model.avaliacao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="periodoExperiencia_sequence", allocationSize=1)
public class PeriodoExperiencia extends AbstractModel implements Serializable, Cloneable
{
	private Integer dias;
	@Column(length=40)
	private String descricao;
	@ManyToOne
	private Empresa empresa;
	private boolean ativo;
	
	@Transient
	private Date dataFim;

	public Integer getDias() {
		return dias;
	}

	public void setDias(Integer dias) {
		this.dias = dias;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public void setEmpresaId(Long empresaId){
		if(this.empresa == null)
			this.empresa = new Empresa();
		
		this.getEmpresa().setId(empresaId);
	}
	
	public void setEmpresaNotificarSomentePeriodosConfigurados(boolean empresaNotificarSomentePeriodosConfigurados){
		if(this.empresa == null)
			this.empresa = new Empresa();
		
		this.getEmpresa().setNotificarSomentePeriodosConfigurados(empresaNotificarSomentePeriodosConfigurados);
	}

	public String getDescricao() {
		return descricao;
	}
	
	public String getDiasDescricao() {
		return dias.toString() + " dias" + (descricao.equals("")?"":" (" + descricao  + ")");
	}

	public String getDiasDescricaoComInativo() {
		if(ativo)
			return dias.toString() + " dias" + (descricao.equals("")?"":" (" + descricao  + ")");
		else
			return dias.toString() + " dias" + (descricao.equals("")?"":" (" + descricao  + ")") + " - (Inativo)";
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDiasComDescricao() 
	{
		if (descricao != null && !descricao.equals(""))
			return dias + " - " + descricao ;
		
		return dias.toString();
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
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
	
	
}
