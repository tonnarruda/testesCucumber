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
import com.fortes.rh.model.dicionario.MotivoExtintorManutencao;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="extintormanutencao_sequence", allocationSize=1)
public class ExtintorManutencao extends AbstractModel implements Serializable
{
	@Temporal(DATE)
	private Date saida;

	@Temporal(DATE)
	private Date retorno;

	@Column(length=1)
	private String motivo;

	@Column(length=50)
	private String outroMotivo;
	
	@Lob
	private String observacao;

	@OneToOne(fetch=LAZY)
	private Extintor extintor;

	@ManyToMany(fetch=LAZY, targetEntity=ExtintorManutencaoServico.class)
	private Collection<ExtintorManutencaoServico> servicos;
	
	@Transient
	private Date vencimento;
	
	@Transient
	private String servicosRelatorio;
	
	public ExtintorManutencao()
	{
		super();
	}

	public ExtintorManutencao(Long id, Date saida, Integer periodoMax, String localizacao, Integer numeroCilindro, String tipo)
	{
		super();
		this.setId(id);
		this.saida = saida;
		if(periodoMax != null)
			this.vencimento = DateUtil.incrementaMes(saida, periodoMax);
		
		if (this.extintor == null)
			this.extintor = new Extintor();
		
		if (this.extintor.getUltimoHistorico() == null)
			this.extintor.setHistoricoExtintores(Arrays.asList(new HistoricoExtintor()));

		this.extintor.getUltimoHistorico().setLocalizacao(localizacao);
		this.extintor.setNumeroCilindro(numeroCilindro);
		this.extintor.setTipo(tipo);
	}

	public ExtintorManutencao(ExtintorManutencao extintorManutencao, HistoricoExtintor historicoExtintor)
	{
		this.setId(extintorManutencao.getId());
		this.setSaida(extintorManutencao.getSaida());
		this.setRetorno(extintorManutencao.getRetorno());
		this.setMotivo(extintorManutencao.getMotivo());
		this.setVencimento(extintorManutencao.getVencimento());
		this.setOutroMotivo(extintorManutencao.getOutroMotivo());
		this.setObservacao(extintorManutencao.getObservacao());
		this.setExtintor(extintorManutencao.getExtintor());
		this.setServicos(extintorManutencao.getServicos());
		
		this.getExtintor().setUltimoHistorico(historicoExtintor);
	}
	
	public String getMotivoDic()
	{
		if (motivo == null)
			return "";

		return (String)new MotivoExtintorManutencao().get(motivo);
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
	
	public Extintor getExtintor()
	{
		return extintor;
	}

	public void setExtintor(Extintor extintor)
	{
		this.extintor = extintor;
	}

	public String getMotivo()
	{
		return motivo;
	}

	public void setMotivo(String motivo)
	{
		this.motivo = motivo;
	}

	public String getObservacao()
	{
		return observacao;
	}

	public void setObservacao(String observacao)
	{
		this.observacao = observacao;
	}

	public Date getRetorno()
	{
		return retorno;
	}

	public void setRetorno(Date retorno)
	{
		this.retorno = retorno;
	}

	public Date getSaida()
	{
		return saida;
	}

	public void setSaida(Date saida)
	{
		this.saida = saida;
	}

	public Collection<ExtintorManutencaoServico> getServicos()
	{
		return servicos;
	}

	public void setServicos(Collection<ExtintorManutencaoServico> servicos)
	{
		this.servicos = servicos;
	}

	public String getOutroMotivo()
	{
		return outroMotivo;
	}

	public void setOutroMotivo(String outroMotivo)
	{
		this.outroMotivo = outroMotivo;
	}

	public Date getVencimento()
	{
		return vencimento;
	}

	public void setVencimento(Date vencimento)
	{
		this.vencimento = vencimento;
	}

	public String getServicosRelatorio() {
		return servicosRelatorio;
	}

	public void setServicosRelatorio(String servicosRelatorio) {
		this.servicosRelatorio = servicosRelatorio;
	}
}