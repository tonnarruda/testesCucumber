package com.fortes.rh.model.avaliacao;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="periodoExperiencia_sequence", allocationSize=1)
public class PeriodoExperiencia extends AbstractModel implements Serializable
{
	private Integer dias;
	
	@ManyToOne
	private Empresa empresa;
	
	public PeriodoExperiencia() {
	}
	
	public PeriodoExperiencia(Long id, Integer dias, Long empresaId)
	{
		this.setId(id);
		this.dias = dias;
		this.empresa = new Empresa();
		this.empresa.setId(empresaId);
	}
	
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
}
