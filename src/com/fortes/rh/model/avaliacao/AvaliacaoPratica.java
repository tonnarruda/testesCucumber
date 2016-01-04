package com.fortes.rh.model.avaliacao;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="avaliacaoPratica_sequence", allocationSize=1)
public class AvaliacaoPratica extends AbstractModel implements Serializable
{
	private String titulo;
	
	private Double notaMinima;
	
	@ManyToOne
	private Empresa empresa;
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public Double getNotaMinima() {
		return notaMinima;
	}
	public void setNotaMinima(Double notaMinima) {
		this.notaMinima = notaMinima;
	}
}
