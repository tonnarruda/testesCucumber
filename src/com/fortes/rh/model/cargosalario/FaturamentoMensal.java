package com.fortes.rh.model.cargosalario;

import java.io.Serializable;
import java.util.Date;

import com.fortes.rh.model.cargosalario.FaturamentoMensal;
import com.fortes.rh.model.geral.Empresa;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="faturamentoMensal_sequence", allocationSize=1)
public class FaturamentoMensal extends AbstractModel implements Serializable
{
	@Temporal(TemporalType.DATE)
	private Date mesAno;
	private Double valor;
	@ManyToOne
	private Empresa empresa;
	
	public Date getMesAno() {
		return mesAno;
	}
	public void setMesAno(Date mesAno) {
		this.mesAno = mesAno;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public void setProjectionEmpresaId(Long empresaId) 
	{
		if(this.empresa == null)
			this.empresa = new Empresa();
		this.empresa.setId(empresaId);
	}
}
