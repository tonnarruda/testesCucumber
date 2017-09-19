package com.fortes.rh.model.cargosalario;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;

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
	@ManyToOne
	private Estabelecimento estabelecimento;
	
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
	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}
	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}
	public void setEstabelecimentoNome(String estabelecimentoNome){
		if(this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();
		
		this.estabelecimento.setNome(estabelecimentoNome);
	}
}
