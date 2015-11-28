package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.SituacaoSolicitacaoEpi;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="solicitacaoepi_sequence", allocationSize=1)
public class SolicitacaoEpi extends AbstractModel implements Serializable
{
	@Temporal(TemporalType.DATE)
	private Date data;

	@ManyToOne
	private Colaborador colaborador;

	@ManyToOne
	private Cargo cargo;

	@ManyToOne
	private Estabelecimento estabelecimento;
	
	@ManyToOne
	private Empresa empresa;
	
	@OneToMany(mappedBy="solicitacaoEpi")
	private Collection<SolicitacaoEpiItem> solicitacaoEpiItems;

	//Usados por relatório
	@Transient
	private EpiHistorico epiHistorico;
	@Transient
	private Epi epi;
	@Transient
	private Integer qtdEpiSolicitado = 0;
	@Transient
	private Integer qtdEpiEntregue = 0;
	@Transient
	private Date dataEpiEntrega;
	@Transient
	private Date vencimentoCA;
	@Transient
	private String informativoEntrega;
	@Transient
	private String informativoDevolucao;
	@Transient
	private TamanhoEPI tamanhoEPI;
	@Transient
	private Integer qtdEpiDevolvido = 0;
	
	public SolicitacaoEpi()
	{
		if (this.data == null)
			this.data = new Date();
	}

	public SolicitacaoEpi(Long epiId, Long colaboradorId, String epiNome, Boolean epiAtivo, String colaboradorNome, String cargoNome, Date data, Integer validadeUso, Date dataEntrega, Integer qtdEpiEntregue, Date vencimentoCA)
	{
		epi = new Epi();
		epi.setId(epiId);
		epi.setNome(epiNome);
		epi.setAtivo(epiAtivo);
		setColaboradorNome(colaboradorNome);
		colaborador.setId(colaboradorId);
		setCargoNome(cargoNome);
		epiHistorico = new EpiHistorico();
		epiHistorico.setValidadeUso(validadeUso);
		this.data = data;
		this.qtdEpiEntregue = qtdEpiEntregue;
		this.dataEpiEntrega = dataEntrega;
		this.vencimentoCA = vencimentoCA;
	}

	public String getSituacaoDescricaoEntrega()
	{
		return SituacaoSolicitacaoEpi.getSituacaoDescricaoEntrega(qtdEpiEntregue, qtdEpiSolicitado);
	}

	public String getSituacaoEntrega()
	{
		return SituacaoSolicitacaoEpi.getSituacaoEntrega(qtdEpiEntregue, qtdEpiSolicitado);
	}
	
	public String getSituacaoDescricaoDevolucao()
	{
		return SituacaoSolicitacaoEpi.getSituacaoDescricaoDevolucao(qtdEpiDevolvido, qtdEpiEntregue);
	}

	public String getSituacaoDevolucao()
	{
		return SituacaoSolicitacaoEpi.getSituacaoDevolucao(qtdEpiDevolvido, qtdEpiEntregue);
	}
	
	public Date getDataVencimentoEpi()
	{
		Date dataVencimento = null;
		if (epiHistorico != null && epiHistorico.getValidadeUso() != null && dataEpiEntrega != null)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dataEpiEntrega);
			calendar.add(Calendar.DAY_OF_YEAR, +epiHistorico.getValidadeUso());
			dataVencimento = calendar.getTime();
		}

		return dataVencimento;
	}

	public void setCargoNome(String cargoNome)
	{
		if (this.cargo == null)
			this.cargo = new Cargo();

		this.cargo.setNome(cargoNome);

	}

	public void setColaboradorNome(String colaboradorNome)
	{
		if (this.colaborador == null)
			this.colaborador = new Colaborador();

		this.colaborador.setNome(colaboradorNome);
	}
	
	public void setColaboradorDesligado(boolean desligado)
	{
		if (this.colaborador == null)
			this.colaborador = new Colaborador();
		
		this.colaborador.setDesligado(desligado);
	}
	
	public void setColaboradorStatus(int colaboradorStatus)
	{
		if (this.colaborador == null)
			this.colaborador = new Colaborador();
		
		if (this.colaborador.getHistoricoColaborador() == null)
			this.colaborador.setHistoricoColaborador(new HistoricoColaborador());
		
		this.colaborador.getHistoricoColaborador().setStatus(colaboradorStatus);
	}
	
	public void setColaboradorMotivoHistorico(String colaboradorMotivoHistorico)
	{
		if (this.colaborador == null)
			this.colaborador = new Colaborador();
		
		if (this.colaborador.getHistoricoColaborador() == null)
			this.colaborador.setHistoricoColaborador(new HistoricoColaborador());
		
		this.colaborador.getHistoricoColaborador().setMotivo(colaboradorMotivoHistorico);
	}

	public Cargo getCargo()
	{
		return cargo;
	}

	public void setCargo(Cargo cargo)
	{
		this.cargo = cargo;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public Date getData()
	{
		return data;
	}
	
	public String getDataFormatada()
	{
		return DateUtil.formataDiaMesAno(data);
	}

	public void setData(Date data)
	{
		this.data = data;
	}

	public Empresa getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public void setEpiHistorico(EpiHistorico epiHistorico)
	{
		this.epiHistorico = epiHistorico;
	}

	public Epi getEpi()
	{
		return epi;
	}

	public Integer getQtdEpiEntregue() {
		return qtdEpiEntregue;
	}

	public EpiHistorico getEpiHistorico() {
		return epiHistorico;
	}

	public Date getVencimentoCA() {
		return vencimentoCA;
	}

	public Date getDataEpiEntrega() {
		return dataEpiEntrega;
	}

	public Integer getQtdEpiSolicitado() {
		return qtdEpiSolicitado;
	}

	public void setQtdEpiSolicitado(Integer qtdEpiSolicitado) {
		this.qtdEpiSolicitado = qtdEpiSolicitado;
	}

	public Collection<SolicitacaoEpiItem> getSolicitacaoEpiItems() {
		return solicitacaoEpiItems;
	}

	public void setSolicitacaoEpiItems(Collection<SolicitacaoEpiItem> solicitacaoEpiItems) {
		this.solicitacaoEpiItems = solicitacaoEpiItems;
	}

	public void setQtdEpiEntregue(Integer qtdEpiEntregue) {
		this.qtdEpiEntregue = qtdEpiEntregue;
	}

	public String getInformativoEntrega() {
		return informativoEntrega;
	}

	public void setInformativoEntrega(String informativoEntrega) {
		this.informativoEntrega = informativoEntrega;
	}
	
	public String getInformativoDevolucao() {
		return informativoDevolucao;
	}

	public void setInformativoDevolucao(String informativoDevolucao) {
		this.informativoDevolucao = informativoDevolucao;
	}

	public Estabelecimento getEstabelecimento()
	{
		return estabelecimento;
	}
	
	public void setEstabelecimento(Estabelecimento estabelecimento)
	{
		this.estabelecimento = estabelecimento;
	}

	public TamanhoEPI getTamanhoEPI() {
		return tamanhoEPI;
	}

	public void setTamanhoEPI(TamanhoEPI tamanhoEPI) {
		this.tamanhoEPI = tamanhoEPI;
	}

	public Integer getQtdEpiDevolvido() {
		return qtdEpiDevolvido;
	}

	public void setQtdEpiDevolvido(Integer qtdEpiDevolvido) {
		this.qtdEpiDevolvido = qtdEpiDevolvido;
	}
}