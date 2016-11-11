package com.fortes.rh.model.sesmt;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.TemporalType.DATE;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="extintorinspecao_sequence", allocationSize=1)
public class ExtintorInspecao extends AbstractModel implements Serializable
{
	@Temporal(DATE)
	private Date data;
	@Transient
	private Date vencimento;

	@Column(length=50)
	private String empresaResponsavel;

	@Column(length=50)
	private String outroMotivo;

	
	private String observacao;

	@OneToOne(fetch=LAZY)
	private Extintor extintor;

	@Transient
	private String tipoDeRegularidade = "Regular";
	@Transient
	private String itensRelatorio = "";
	

	@ManyToMany(fetch=LAZY, targetEntity=ExtintorInspecaoItem.class)
	private Collection<ExtintorInspecaoItem> itens;

	public ExtintorInspecao()
	{
		super();
	}
	
	public ExtintorInspecao(Long id, Date data, Integer periodoMax, String localizacao, Integer numeroCilindro, String tipo)
	{
		super();
		this.setId(id);
		this.data = data;
		if(periodoMax != null)
			this.vencimento = DateUtil.incrementaMes(data, periodoMax);
		
		if (this.extintor == null)
			this.extintor = new Extintor();
		
		if (this.extintor.getUltimoHistorico() == null)
			this.extintor.setHistoricoExtintores(Arrays.asList(new HistoricoExtintor()));

		this.extintor.getUltimoHistorico().setLocalizacao(localizacao);
		this.extintor.setNumeroCilindro(numeroCilindro);
		this.extintor.setTipo(tipo);
	}
	
	public ExtintorInspecao(ExtintorInspecao extintorInspecao, HistoricoExtintor historicoExtintor)
	{
		this.setId(extintorInspecao.getId());
		this.setData(extintorInspecao.getData());
		this.setVencimento(extintorInspecao.getVencimento());
		this.setEmpresaResponsavel(extintorInspecao.getEmpresaResponsavel());
		this.setOutroMotivo(extintorInspecao.getOutroMotivo());
		this.setObservacao(extintorInspecao.getObservacao());
		this.setExtintor(extintorInspecao.getExtintor());
		this.setItens(extintorInspecao.getItens());
		
		this.getExtintor().setUltimoHistorico(historicoExtintor);
	}

	public void setProjectionExtintorNumeroCilindro(Integer extintorNumeroCilindro)
	{
		if (extintor == null)
			extintor = new Extintor();
		extintor.setNumeroCilindro(extintorNumeroCilindro);
	}

	public void setProjectionExtintorTipo(String extintorTipo)
	{
		if (extintor == null)
			extintor = new Extintor();
		extintor.setTipo(extintorTipo);
	}

	public Date getData()
	{
		return data;
	}

	public void setData(Date data)
	{
		this.data = data;
	}

	public Extintor getExtintor()
	{
		return extintor;
	}

	public void setExtintor(Extintor extintor)
	{
		this.extintor = extintor;
	}

	public String getObservacao()
	{
		return observacao;
	}

	public void setObservacao(String observacao)
	{
		this.observacao = observacao;
	}

	public Collection<ExtintorInspecaoItem> getItens()
	{
		return itens;
	}

	public void setItens(Collection<ExtintorInspecaoItem> extintorInspecaoItems)
	{
		this.itens = extintorInspecaoItems;
	}

	public String getEmpresaResponsavel()
	{
		return empresaResponsavel;
	}

	public void setEmpresaResponsavel(String empresaResponsavel)
	{
		this.empresaResponsavel = empresaResponsavel;
	}

	public Date getVencimento()
	{
		return vencimento;
	}

	public void setVencimento(Date vencimento)
	{
		this.vencimento = vencimento;
	}

	public String getTipoDeRegularidade() {
		return tipoDeRegularidade;
	}

	public void setTipoDeRegularidade(String tipoDeRegularidade) {
		this.tipoDeRegularidade = tipoDeRegularidade;
	}

	public String getItensRelatorio() {
		return itensRelatorio;
	}

	public void setItensRelatorio(String itensRelatorio) {
		this.itensRelatorio = itensRelatorio;
	}

	public String getOutroMotivo() {
		return outroMotivo;
	}

	public void setOutroMotivo(String outroMotivo) {
		this.outroMotivo = outroMotivo;
	}
}