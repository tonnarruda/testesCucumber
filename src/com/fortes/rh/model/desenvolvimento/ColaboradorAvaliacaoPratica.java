package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="colaboradorAvaliacaoPratica_sequence", allocationSize=1)
public class ColaboradorAvaliacaoPratica extends AbstractModel implements Serializable
{
	@ManyToOne(fetch=FetchType.LAZY)
	private AvaliacaoPratica avaliacaoPratica;
	@ManyToOne(fetch=FetchType.LAZY)
	private Certificacao certificacao;
	@ManyToOne(fetch=FetchType.LAZY)
	private Colaborador colaborador;
	@Temporal(TemporalType.DATE)
	private Date data;
	private Double nota;
	
	public Certificacao getCertificacao() {
		return certificacao;
	}
	public void setCertificacao(Certificacao certificacao) {
		this.certificacao = certificacao;
	}
	public Colaborador getColaborador() {
		return colaborador;
	}
	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getDataFormatada(){
		if(data != null)
			return DateUtil.formataDate(data, "dd/MM/yyyy");
		
		return "";
	}
	public Double getNota() {
		return nota;
	}
	public void setNota(Double nota) {
		this.nota = nota;
	}
	public AvaliacaoPratica getAvaliacaoPratica() {
		return avaliacaoPratica;
	}
	public void setAvaliacaoPratica(AvaliacaoPratica avaliacaoPratica) {
		this.avaliacaoPratica = avaliacaoPratica;
	}
	public void setAvaliacaoPraticaTitulo(String titulo){
		iniciaAvaliacaoPratica();
		avaliacaoPratica.setTitulo(titulo);
	}
	public void setAvaliacaoPraticaNotaMinima(Double notaMinima){
		iniciaAvaliacaoPratica();
		avaliacaoPratica.setNotaMinima(notaMinima);;
	}
	private void iniciaAvaliacaoPratica() {
		if(avaliacaoPratica == null)
			avaliacaoPratica = new AvaliacaoPratica();
	}
}
