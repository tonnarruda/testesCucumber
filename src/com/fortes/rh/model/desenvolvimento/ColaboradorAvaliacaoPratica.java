package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

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
	@ManyToOne
	private ColaboradorCertificacao colaboradorCertificacao;

	@Temporal(TemporalType.DATE)
	private Date data;
	private Double nota;
	
	@Transient
	private String dataString;//Usado no DWR
	
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
	public void setAvaliacaoPraticaId(Long id){
		iniciaAvaliacaoPratica();
		avaliacaoPratica.setId(id);
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
	public ColaboradorCertificacao getColaboradorCertificacao() {
		return colaboradorCertificacao;
	}
	public void setColaboradorCertificacao(ColaboradorCertificacao colaboradorCertificacao) {
		this.colaboradorCertificacao = colaboradorCertificacao;
	}
	public void setColaboradorId(Long colaboradorId){
		if(this.colaborador == null)
			this.colaborador = new Colaborador();
		
		colaborador.setId(colaboradorId);
	}
	
	public void setCertificacaoId(Long certificacaoId){
		if(this.certificacao == null)
			this.certificacao = new Certificacao();
		
		this.certificacao.setId(certificacaoId);
	}
	
	public void setColaboradorCertificacaoId(Long colaboradorCertificacaoId){
		if(colaboradorCertificacaoId != null){
			if(this.colaboradorCertificacao == null)
				this.colaboradorCertificacao = new ColaboradorCertificacao();
			
			colaboradorCertificacao.setId(colaboradorCertificacaoId);
		}
	}
	public String getDataString() {
		if(data != null)
			return DateUtil.formataDate(data, "dd/MM/yyyy");
		
		return "";
	}
	public void setDataString(String dataString) {
		this.dataString = dataString;
	}
}
