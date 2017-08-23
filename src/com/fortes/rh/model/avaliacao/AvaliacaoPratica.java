package com.fortes.rh.model.avaliacao;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="avaliacaoPratica_sequence", allocationSize=1)
public class AvaliacaoPratica extends AbstractModel implements Serializable
{
	//Usado para posicionar a Avaliação Prática na última posição.
	public final static String SUFIXO_ORDENACAO_ULTIMA_POSICAO = "zzzzzz"; 
	
	private String titulo;
	private Double notaMinima;
	@ManyToOne
	private Empresa empresa;
	
	@Transient
	private Long certificacaoId;
	
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
	public Long getCertificacaoId() {
		return certificacaoId;
	}
	public void setCertificacaoId(Long certificacaoId) {
		this.certificacaoId = certificacaoId;
	}
}
