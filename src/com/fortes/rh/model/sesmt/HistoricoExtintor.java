package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="historicoextintor_sequence", allocationSize=1)
public class HistoricoExtintor extends AbstractModel implements Serializable
{
	@ManyToOne
	private Extintor extintor;

	@ManyToOne
	private Estabelecimento estabelecimento;
	
	@Column(length=50)
	private String localizacao;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;

	@Transient
	private String hora;

	public Extintor getExtintor() {
		return extintor;
	}
	
	public void setProjectionEstabelecimentoId(Long estabelecimentoId) {
		if (this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();
		
		this.estabelecimento.setId(estabelecimentoId);
	}

	public void setProjectionEstabelecimentoNome(String estabelecimentoNome) {
		if (this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();
		
		this.estabelecimento.setNome(estabelecimentoNome);
	}

	public void setExtintor(Extintor extintor) {
		this.extintor = extintor;
	}

	private void inicializaExtintor() {
		if(this.extintor == null)
			this.extintor = new Extintor();
	}

	public void setProjectionExtintorId(Long extintorId){
		inicializaExtintor();
		this.extintor.setId(extintorId);
	}
	
	public void setProjectionExtintorTipo(String extintorTipo){
		inicializaExtintor();
		this.extintor.setTipo(extintorTipo);
	}
	
	public void setProjectionExtintorNumeroCilindro(Integer extintorNumeroCilindro){
		inicializaExtintor();
		this.extintor.setNumeroCilindro(extintorNumeroCilindro);
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getHora() {
		return DateUtil.getHora(getData());
	}

	public String getHoraString() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}
}