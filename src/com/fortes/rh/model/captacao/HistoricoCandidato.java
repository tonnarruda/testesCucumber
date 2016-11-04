package com.fortes.rh.model.captacao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.Apto;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="historicocandidato_sequence", allocationSize=1)
public class HistoricoCandidato extends AbstractModel implements Serializable, Cloneable
{
	@Temporal(TemporalType.DATE)
	private Date data;
	@ManyToOne
	private EtapaSeletiva etapaSeletiva;
	@ManyToOne
	private CandidatoSolicitacao candidatoSolicitacao;
	@Column(length=100)
	private String responsavel;
	@Lob
	private String observacao;

	private Character apto = Apto.INDIFERENTE;
	@Transient
	private Double qtdHistoricos;//usado no relatorio Processo Seletivo
	@Transient
	private Integer historicoMes;//usado no relatorio Processo Seletivo
	@Column(length=5)
	private String horaIni;
	@Column(length=5)
	private String horaFim;
	
	private boolean exibirNaAgenda = true;
	
	public HistoricoCandidato()
	{

	}
	
	public HistoricoCandidato(Long etapaId, String etapaNome, Integer qtdHistoricos, Integer historicoMes, Character apto)
	{
		if(this.etapaSeletiva == null)
			this.etapaSeletiva = new EtapaSeletiva();
		this.etapaSeletiva.setId(etapaId);
		this.etapaSeletiva.setNome(etapaNome);
		this.qtdHistoricos = qtdHistoricos.doubleValue();
		this.historicoMes = historicoMes;
		this.apto = apto;
	}

	public HistoricoCandidato(Date hcData, String hcResponsavel, String hcObservacao, Character hcApto, Long esId, String esNome, Long caId,String caNome, boolean caContratado, String caEmail, Long csId, Long sId, Long colaboradorId)
	{
		this.setData(hcData);
		this.setResponsavel(hcResponsavel);
		this.setObservacao(hcObservacao);
		this.setApto(hcApto);

		this.setCandidatoId(caId);
		this.setCandidatoNome(caNome);
		this.setProjectionCandidatoContratado(caContratado);
		this.setProjectionCandidatoEmail(caEmail);

		this.setEtapaSeletivaId(esId);
		this.setEtapaSeletivaNome(esNome);

		this.setCandidatoSolicitacaoId(csId);
		this.setProjectionCandidatoSolicitacaoColaboradorId(colaboradorId);

		this.setSolicitacaoId(sId);
	}

	public void setProjectionCandidatoSolicitacaoColaboradorId(Long projectionCandidatoSolicitacaoColaboradorId)
	{
		if (candidatoSolicitacao == null)
			candidatoSolicitacao = new CandidatoSolicitacao();

		candidatoSolicitacao.setColaboradorId(projectionCandidatoSolicitacaoColaboradorId);
	}

	//utilizado no daoHibernate
	public void setEtapaSeletivaId(Long etapaSeletivaId)
	{
		if (etapaSeletiva == null)
			etapaSeletiva = new EtapaSeletiva();

		etapaSeletiva.setId(etapaSeletivaId);
	}
	public void setEtapaSeletivaNome(String etapaSeletivaNome)
	{
		if (etapaSeletiva == null)
			etapaSeletiva = new EtapaSeletiva();

		etapaSeletiva.setNome(etapaSeletivaNome);
	}

	public void setEtapaSeletivaOrdem(int etapaSeletivaOrdem)
	{
		if (etapaSeletiva == null)
			etapaSeletiva = new EtapaSeletiva();

		etapaSeletiva.setOrdem(etapaSeletivaOrdem);
	}
	public void setCandidatoSolicitacaoId(Long candidatoSolicitacaoId)
	{
		if (candidatoSolicitacao == null)
			candidatoSolicitacao = new CandidatoSolicitacao();

		candidatoSolicitacao.setId(candidatoSolicitacaoId);
	}
	public void setCandidatoSolicitacaoApto(Character candidatoSolicitacaoApto)
	{
		if (candidatoSolicitacao == null)
			candidatoSolicitacao = new CandidatoSolicitacao();

		candidatoSolicitacao.setApto(candidatoSolicitacaoApto);
	}
	public void setSolicitacaoId(Long solicitacaoId)
	{
		inicializaSolicitacao();

		candidatoSolicitacao.getSolicitacao().setId(solicitacaoId);
	}

	private void inicializaSolicitacao() {
		if (candidatoSolicitacao == null)
			candidatoSolicitacao = new CandidatoSolicitacao();

		if(candidatoSolicitacao.getSolicitacao() == null)
			candidatoSolicitacao.setSolicitacao(new Solicitacao());
	}
	 
	public void setSolicitacaoQuantidade(int solicitacaoQuantidade)
	{
		inicializaSolicitacao();
		
		candidatoSolicitacao.getSolicitacao().setQuantidade(solicitacaoQuantidade);
	}
	public void setSolicitacaoDescricao(String solicitacaoDescricao)
	{
		inicializaSolicitacao();
		
		candidatoSolicitacao.getSolicitacao().setDescricao(solicitacaoDescricao);
	}
	public void setSolicitacaoSolicitanteNome(String solicitacaoSolicitanteNome)
	{
		inicializaSolicitacao();
	
		if(candidatoSolicitacao.getSolicitacao().getSolicitante() == null)
			candidatoSolicitacao.getSolicitacao().setSolicitante(new Usuario());
		
		candidatoSolicitacao.getSolicitacao().getSolicitante().setNome(solicitacaoSolicitanteNome);
	}
	public void setSolicitacaoAreaId(Long solicitacaoAreaId)
	{
		inicializaSolicitacao();
		
		if(candidatoSolicitacao.getSolicitacao().getAreaOrganizacional() == null)
			candidatoSolicitacao.getSolicitacao().setAreaOrganizacional(new AreaOrganizacional());
		
		candidatoSolicitacao.getSolicitacao().getAreaOrganizacional().setId(solicitacaoAreaId);
	}
	public void setSolicitacaoAreaNome(String solicitacaoAreaNome)
	{
		inicializaSolicitacao();
		
		if(candidatoSolicitacao.getSolicitacao().getAreaOrganizacional() == null)
			candidatoSolicitacao.getSolicitacao().setAreaOrganizacional(new AreaOrganizacional());
		
		candidatoSolicitacao.getSolicitacao().getAreaOrganizacional().setNome(solicitacaoAreaNome);
	}
	public void setCandidatoId(Long candidatoId)
	{
		if(candidatoSolicitacao == null)
			candidatoSolicitacao = new CandidatoSolicitacao();
		if (candidatoSolicitacao.getCandidato() == null)
			candidatoSolicitacao.setCandidato(new Candidato());

		candidatoSolicitacao.getCandidato().setId(candidatoId);
	}

	public void setCandidatoNome(String candidatoNome)
	{
		if(candidatoSolicitacao == null)
			candidatoSolicitacao = new CandidatoSolicitacao();
		if (candidatoSolicitacao.getCandidato() == null)
			candidatoSolicitacao.setCandidato(new Candidato());

		candidatoSolicitacao.getCandidato().setNome(candidatoNome);
	}

	public void setProjectionCandidatoContratado(boolean projectionCandidatoContratado)
	{
		if(candidatoSolicitacao == null)
			candidatoSolicitacao = new CandidatoSolicitacao();
		if (candidatoSolicitacao.getCandidato() == null)
			candidatoSolicitacao.setCandidato(new Candidato());

		candidatoSolicitacao.getCandidato().setContratado(projectionCandidatoContratado);
	}

	public void setProjectionCandidatoEmail(String projectionCandidatoEmail)
	{
		if(candidatoSolicitacao == null)
			candidatoSolicitacao = new CandidatoSolicitacao();
		if (candidatoSolicitacao.getCandidato() == null)
			candidatoSolicitacao.setCandidato(new Candidato());
		if (candidatoSolicitacao.getCandidato().getContato() == null)
			candidatoSolicitacao.getCandidato().setContato(new Contato());

		candidatoSolicitacao.getCandidato().getContato().setEmail(projectionCandidatoEmail);
	}

	public void setProjectionCargoNome(String projectionCargoNome)
	{
		inicializaSolicitacao();
		if(candidatoSolicitacao.getSolicitacao().getFaixaSalarial() == null)
			candidatoSolicitacao.getSolicitacao().setFaixaSalarial(new FaixaSalarial());
		if(candidatoSolicitacao.getSolicitacao().getFaixaSalarial().getCargo() == null)
			candidatoSolicitacao.getSolicitacao().getFaixaSalarial().setCargo(new Cargo());

		candidatoSolicitacao.getSolicitacao().getFaixaSalarial().getCargo().setNome(projectionCargoNome);
	}
	
	public void setProjectionCargoNomeMercado(String projectionCargoNomeMercado)
	{
		inicializaSolicitacao();
		if(candidatoSolicitacao.getSolicitacao().getFaixaSalarial() == null)
			candidatoSolicitacao.getSolicitacao().setFaixaSalarial(new FaixaSalarial());
		if(candidatoSolicitacao.getSolicitacao().getFaixaSalarial().getCargo() == null)
			candidatoSolicitacao.getSolicitacao().getFaixaSalarial().setCargo(new Cargo());
		
		candidatoSolicitacao.getSolicitacao().getFaixaSalarial().getCargo().setNomeMercado(projectionCargoNomeMercado);
	}

	public Date getData()
	{
		return data;
	}
	
	public String getDataFormatada()
	{
		String dataFormatada = "";
		
		if (data != null)
			dataFormatada = DateUtil.formataDiaMesAno(data);

		return dataFormatada;
	}

	public void setData(Date data)
	{
		this.data = data;
	}
	public CandidatoSolicitacao getCandidatoSolicitacao()
	{
		return candidatoSolicitacao;
	}
	public void setCandidatoSolicitacao(CandidatoSolicitacao candidatoSolicitacao)
	{
		this.candidatoSolicitacao = candidatoSolicitacao;
	}
	public EtapaSeletiva getEtapaSeletiva()
	{
		return etapaSeletiva;
	}
	public void setEtapaSeletiva(EtapaSeletiva etapaSeletiva)
	{
		this.etapaSeletiva = etapaSeletiva;
	}
	public String getObservacao()
	{
		return observacao;
	}
	public void setObservacao(String observacao)
	{
		this.observacao = observacao;
	}
	public String getResponsavel()
	{
		return responsavel;
	}
	public void setResponsavel(String responsavel)
	{
		this.responsavel = responsavel;
	}
	public String getAptoFormatado() 
	{
		return Apto.getDescApto(apto);
	}
	@Override
	public boolean equals(Object object)
	{
		if (!(object instanceof HistoricoCandidato))
			return false;

		return ((HistoricoCandidato) object).getId().equals(this.getId());
	}

	@Override
	public int hashCode()
	{
		if (this.getId() == null)
			return 0;
	   return this.getId().hashCode();
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("etapaSeletiva", this.etapaSeletiva)
				.append("id",this.getId())
				.append("apto", this.apto)
				.append("responsavel", this.responsavel)
				.append("candidatoSolicitacao", this.candidatoSolicitacao)
				.append("observacao", this.observacao)
				.append("data", this.data).toString();
	}

	public Long getCandidatoSolicitacaoId()
	{
		if (this.candidatoSolicitacao != null)
			return this.candidatoSolicitacao.getId();
		return null;
	}

	public Object clone()
	{
		try
		{
			return super.clone();
		}
		catch(CloneNotSupportedException e)
		{
			throw new Error("This should not occur since we implement Cloneable");
		}
	}

	public Double getQtdHistoricos()
	{
		return qtdHistoricos;
	}

	public Integer getHistoricoMes()
	{
		return historicoMes;
	}

	public String getHoraIni() {
		return horaIni;
	}

	public void setHoraIni(String horaIni) {
		this.horaIni = horaIni;
	}

	public String getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim;
	}

	public Character getApto() {
		return apto;
	}

	public void setApto(Character apto) {
		this.apto = apto;
	}

	public boolean getAptoBoolean() 
	{
		if (this.apto == Apto.SIM)
			return true;
		
		return false;
	}

	public boolean isExibirNaAgenda() {
		return exibirNaAgenda;
	}

	public void setExibirNaAgenda(boolean exibirNaAgenda) {
		this.exibirNaAgenda = exibirNaAgenda;
	}
}