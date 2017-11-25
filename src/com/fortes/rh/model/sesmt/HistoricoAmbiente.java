package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.util.CnpjUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="historicoambiente_sequence", allocationSize=1)
public class HistoricoAmbiente extends AbstractModel implements Serializable
{
	@ManyToOne
	private Ambiente ambiente;
	@Column(length=100)
	private String nomeAmbiente;
	@ManyToOne
	private Estabelecimento estabelecimento; 
	@Lob
	private String descricao;
	@Temporal(TemporalType.DATE)
	private Date data;
	@Temporal(TemporalType.DATE)
	private Date dataInativo;
	
	@Column(length=40)
	private String tempoExposicao;
	
	private Integer localAmbiente;
	
	@Column(length=14)
    private String cnpjEstabelecimentoDeTerceiros;
	
	@ManyToMany(fetch=FetchType.EAGER)
    private Collection<Epc> epcs;
	
	@OneToMany(mappedBy="historicoAmbiente", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Collection<RiscoAmbiente> riscoAmbientes;
	
	@Transient
	private Date dataProximoHistorico;

	@Transient
	private Risco risco;
	@Transient
	private Collection<Risco> riscos;

	public HistoricoAmbiente()
	{	}

	public HistoricoAmbiente(String descricao, Date data, Date dataInativo, String tempoExposicao) 
	{ 
		this.descricao = descricao;
		this.data = data;
		this.dataInativo = dataInativo;
		this.tempoExposicao = tempoExposicao;
	}

	public HistoricoAmbiente(String descricao, String ambienteNome)
	{
		this.descricao = descricao;
		this.ambiente = new Ambiente();
		this.ambiente.setNome(ambienteNome);
	}
	
	public HistoricoAmbiente(Long id, Date data, Ambiente ambiente)
	{
		setId(id);
		this.data = data;
		this.ambiente = ambiente;
	}
   
	public HistoricoAmbiente(Long ambienteId, String ambienteNome, Long riscoId, String riscoDescricao)
    {
    	if(this.ambiente == null)
    		this.ambiente = new Ambiente();
    	
    	this.getAmbiente().setId(ambienteId);
    	this.getAmbiente().setNome(ambienteNome);
    	
    	if(this.risco == null)
    		this.risco = new Risco();
    	
    	this.risco.setId(riscoId);
    	this.risco.setDescricao(riscoDescricao);
    }

	public Date getData()
	{
		return data;
	}
	public void setData(Date data)
	{
		this.data = data;
	}
	public String getDescricao()
	{
		return descricao;
	}
	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}
	public Ambiente getAmbiente()
	{
		return ambiente;
	}
	public void setAmbiente(Ambiente ambiente)
	{
		this.ambiente = ambiente;
	}
	public Date getDataInativo()
	{
		return dataInativo;
	}
	public void setDataInativo(Date dataInativo)
	{
		this.dataInativo = dataInativo;
	}

	public Collection<RiscoAmbiente> getRiscoAmbientes() {
		return riscoAmbientes;
	}

	public void setRiscoAmbientes(Collection<RiscoAmbiente> riscoAmbientes) {
		this.riscoAmbientes = riscoAmbientes;
	}

	public Collection<Epc> getEpcs() {
		return epcs;
	}

	public void setEpcs(Collection<Epc> epcs) {
		this.epcs = epcs;
	}

	public String getTempoExposicao() {
		return tempoExposicao;
	}

	public void setTempoExposicao(String tempoExposicao) {
		this.tempoExposicao = tempoExposicao;
	}

	/**
	 * variável <b>Transiente</b>.
	 */
	public Date getDataProximoHistorico() {
		return dataProximoHistorico;
	}

	public void setDataProximoHistorico(Date dataProximoHistorico) {
		this.dataProximoHistorico = dataProximoHistorico;
	}

	public Risco getRisco() {
		return risco;
	}

	public void setRiscos(Collection<Risco> riscos){
		this.riscos = riscos;
	}

	public Collection<Risco> getRiscos() {
		return riscos;
	}
	
	public String getDescricaoRiscos()
	{
		StringBuilder descricao = new StringBuilder("");
		int cont = 1;
		if(this.riscos != null && !this.riscos.isEmpty())
		{
			for (Risco risco : this.riscos)
			{
				descricao.append("- " + risco.getDescricao());
				if(cont != riscos.size())
					descricao.append("\n");
				
				cont++;
			}
		}
		
		return descricao.toString();
	}
	
	public void setAmbienteId(Long ambienteId){
		if(this.ambiente == null)
			this.ambiente = new Ambiente();
		
		this.ambiente.setId(ambienteId);
	}
	
	public void setRicoAmbienteId(Long riscoAmbienteId){
		if(this.riscoAmbientes == null)
			this.riscoAmbientes = new ArrayList<RiscoAmbiente>();
		
		this.riscoAmbientes.add(new RiscoAmbiente(riscoAmbienteId));
	}

	public String getNomeAmbiente() {
		return nomeAmbiente;
	}

	public void setNomeAmbiente(String nomeAmbiente) {
		this.nomeAmbiente = nomeAmbiente;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}
	
	public void setEstabelecimentoId(Long estabelecimentoId) {
		if(this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();
		
		this.estabelecimento.setId(estabelecimentoId);
	}

	public Integer getLocalAmbiente() {
		return localAmbiente;
	}

	public void setLocalAmbiente(Integer localAmbiente) {
		this.localAmbiente = localAmbiente;
	}

	public void setEstabelecimentoNome(String estabelecimentoNome) {
		if(this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();
		
		this.estabelecimento.setNome(estabelecimentoNome);
		
	}

	public String getCnpjEstabelecimentoDeTerceiros() {
		return cnpjEstabelecimentoDeTerceiros;
	}

	public void setCnpjEstabelecimentoDeTerceiros(
			String cnpjEstabelecimentoDeTerceiros) {
		this.cnpjEstabelecimentoDeTerceiros = cnpjEstabelecimentoDeTerceiros;
	}

	public String getCnpjEstabelecimentoDeTerceirosFormatado() {
		String cnpjFormatado = CnpjUtil.formata(getCnpjEstabelecimentoDeTerceiros(), Boolean.TRUE);

		return StringUtils.defaultString(cnpjFormatado, StringUtils.EMPTY);
	}
}