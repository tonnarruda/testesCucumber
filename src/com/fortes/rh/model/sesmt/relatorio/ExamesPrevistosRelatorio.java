package com.fortes.rh.model.sesmt.relatorio;

import java.util.Calendar;
import java.util.Date;

import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.util.DateUtil;

public class ExamesPrevistosRelatorio {

	private Long exameId;
	private Long colaboradorId;
	private String exameNome;
	private String colaboradorMatricula;
	private String colaboradorNome;
	private String colaboradorNomeComercial;
	private Integer examePeriodicidade;
	private String tempoVencido;
	private Date dataSolicitacaoExame;
	private Date dataRealizacaoExame;
	private Date dataProximoExame;
	private Estabelecimento estabelecimento;
	private AreaOrganizacional areaOrganizacional;
	private String cargoNome;
	private String motivoSolicitacaoExame;
	private boolean adicionar = true;

	public ExamesPrevistosRelatorio() {}

	public ExamesPrevistosRelatorio(Long colaboradorId, Long exameId, Long areaOrganizacionalId, String cargoNome, String colaboradorMatricula, String colaboradorNome, String colaboradorNomeComercial, String exameNome, Date dataSolicitacaoExame, Date dataRealizacaoExame, Integer solicitacaoExamePeriodicidade, String motivoSolicitacaoExame, Long estabelecimentoId, String estabelecimentoNome) {
		this.colaboradorId = colaboradorId;
		this.exameId = exameId;
		this.colaboradorMatricula = colaboradorMatricula;
		this.colaboradorNome = colaboradorNome;
		this.colaboradorNomeComercial = colaboradorNomeComercial;
		this.exameNome = exameNome;
		this.dataSolicitacaoExame = dataSolicitacaoExame;
		this.dataRealizacaoExame = dataRealizacaoExame;
		this.examePeriodicidade = solicitacaoExamePeriodicidade;
		this.areaOrganizacional = new AreaOrganizacional();
		this.areaOrganizacional.setId(areaOrganizacionalId);
		this.estabelecimento = new Estabelecimento();
		this.estabelecimento.setId(estabelecimentoId);
		this.estabelecimento.setNome(estabelecimentoNome);
		this.cargoNome = cargoNome;
		this.motivoSolicitacaoExame = motivoSolicitacaoExame;
		setDataProximoExame();
	}

	/**
	 * Calcula o próximo exame
	 */
	private void setDataProximoExame()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dataRealizacaoExame != null ? dataRealizacaoExame : dataSolicitacaoExame);
		calendar.add(Calendar.MONTH, +examePeriodicidade);
		this.dataProximoExame = calendar.getTime();
	}
	
	public void setDataProximoExame(Date dataProximoExame)
	{
		this.dataProximoExame = dataProximoExame;
	}

	public String getDataProximoExameFmt()
	{
		String dataProximoExameFmt = "-";
		dataProximoExameFmt = DateUtil.formataDiaMesAno(this.dataProximoExame);
		return dataProximoExameFmt;
	}

	public Date getDataProximoExame()
	{
		return this.dataProximoExame;
	}

	/**
	 * Formata e retorna data do último exame. Pode ser a data de realização, ou
	 * data da solicitação (se ainda não tiver sido realizado)
	 * 
	 * @return dataUltimoExameFmt
	 */
	public String getDataUltimoExame()
	{
		String dataUltimoExameFmt = "-";
		dataUltimoExameFmt = DateUtil.formataDiaMesAno(dataRealizacaoExame != null ? dataRealizacaoExame : dataSolicitacaoExame);
		return dataUltimoExameFmt;
	}

	public String getTempoVencido()
	{
		tempoVencido = DateUtil.calculaIntervaloAteHoje(this.dataProximoExame);
		return tempoVencido;
	}

	public String getColaboradorNome()
	{
		return colaboradorNome;
	}

	public void setColaboradorNome(String colaboradorNome)
	{
		this.colaboradorNome = colaboradorNome;
	}

	public String getExameNome()
	{
		return exameNome;
	}

	public Long getExameId()
	{
		return exameId;
	}

	public Long getColaboradorId()
	{
		return colaboradorId;
	}

	public Date getDataRealizacaoExame()
	{
		return dataRealizacaoExame != null ? dataRealizacaoExame : dataSolicitacaoExame;
	}

	// Usado na logica no manager
	public boolean getAdicionar()
	{
		return adicionar;
	}

	public void setAdicionar(boolean adicionar)
	{
		this.adicionar = adicionar;
	}

	public AreaOrganizacional getAreaOrganizacional()
	{
		return areaOrganizacional;
	}

	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional)
	{
		this.areaOrganizacional = areaOrganizacional;
	}

	public String getCargoNome()
	{
		return cargoNome;
	}

	public String getColaboradorNomeComercial()
	{
		return colaboradorNomeComercial;
	}

	public String getMotivoSolicitacaoExame()
	{
		return motivoSolicitacaoExame;
	}

	public Estabelecimento getEstabelecimento()
	{
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento)
	{
		this.estabelecimento = estabelecimento;
	}

	public String getColaboradorMatricula()
	{
		return colaboradorMatricula;
	}

	public void setColaboradorMatricula(String colaboradorMatricula)
	{
		this.colaboradorMatricula = colaboradorMatricula;
	}
	
	public void setColaboradorId(Long colaboradorId){
		this.colaboradorId = colaboradorId;
	}

	public void setExameId(Long exameId) {
		this.exameId = exameId;
	}

	public void setExameNome(String exameNome) {
		this.exameNome = exameNome;
	}

	public void setColaboradorNomeComercial(String colaboradorNomeComercial) {
		this.colaboradorNomeComercial = colaboradorNomeComercial;
	}

	public void setExamePeriodicidade(Integer examePeriodicidade) {
		this.examePeriodicidade = examePeriodicidade;
	}

	public void setDataSolicitacaoExame(Date dataSolicitacaoExame) {
		this.dataSolicitacaoExame = dataSolicitacaoExame;
	}

	public void setDataRealizacaoExame(Date dataRealizacaoExame) {
		this.dataRealizacaoExame = dataRealizacaoExame;
	}

	public void setCargoNome(String cargoNome) {
		this.cargoNome = cargoNome;
	}

	public void setMotivoSolicitacaoExame(String motivoSolicitacaoExame) {
		this.motivoSolicitacaoExame = motivoSolicitacaoExame;
	}
	
	public void setAreaOrganizacionalId(Long areaOrganizacionalId){
		if(this.areaOrganizacional == null)
			this.areaOrganizacional = new AreaOrganizacional();
		
		this.areaOrganizacional.setId(areaOrganizacionalId);
	}
	
	public void setAreaOrganizacionalNome(String areaOrganizacionalNome){
		if(this.areaOrganizacional == null)
			this.areaOrganizacional = new AreaOrganizacional();
		
		this.areaOrganizacional.setNome(areaOrganizacionalNome);
	}
	
	public void setEstabelecimentolId(Long estabelecimentoId){
		if(this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();
		
		this.estabelecimento.setId(estabelecimentoId);
	}
	
	public void setEstabelecimentolNome(String estabelecimentoNome){
		if(this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();
		
		this.estabelecimento.setNome(estabelecimentoNome);
	}
}
