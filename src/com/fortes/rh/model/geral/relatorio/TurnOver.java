package com.fortes.rh.model.geral.relatorio;

import java.text.DecimalFormat;
import java.util.Date;

import com.fortes.rh.util.DateUtil;

public class TurnOver
{
	private Date mesAno;
	private Double turnOver = 0.0;
	private Double qtdAdmitidos = 0.0;
	private Double qtdDemitidos = 0.0;
	private Double qtdAtivosInicioMes = 0.0;
	private Double qtdAtivosFinalMes = 0.0;
	
	private Integer qtdColaboradores;
	private Integer tempoServico;
	private Long idAreaOuCargo;
	
	public TurnOver() 
	{
		super();
	}
	
	public TurnOver(Integer qtdColaboradores, Integer tempoServico) 
	{
		super();
		this.setQtdColaboradores(qtdColaboradores);
		this.setTempoServico(tempoServico);
	}
	
	public TurnOver (Date mesAno, double qtdAtivosInicioMes, double qtdAtivosFinalMes, double qtdAdmitidos, double qtdDemitidos)
	{
		super();
		setMesAno(mesAno);
		setQtdAdmitidos(qtdAdmitidos);
		setQtdDemitidos(qtdDemitidos);
		setQtdAtivosInicioMes(qtdAtivosInicioMes);
		setQtdAtivosFinalMes(qtdAtivosFinalMes);
	}

	public void setMesAnoQtdDemitidos(Date mesAno, Double qtdDemitidos) 
	{
		this.setMesAno(mesAno);
		this.setQtdDemitidos(qtdDemitidos);
	}
	
	public void setMesAnoQtdAdmitidos(Date mesAno, Double qtdAdmitidos) 
	{
		this.setMesAno(mesAno);
		this.setQtdAdmitidos(qtdAdmitidos);
	}

	public void setMesAnoQtdAtivos(Date dataMesAno, double qtdAtivosInicioMes) 
	{
		this.setMesAno(mesAno);
		this.setQtdAtivosInicioMes(qtdAtivosInicioMes);
	}

	public String getMesAnoFormatado()
	{
		return DateUtil.formataDate(mesAno, "MM/yyyy");
	}

	public Date getMesAno()
	{
		return mesAno;
	}
	public void setMesAno(Date mesAno)
	{
		this.mesAno = mesAno;
	}
	public Double getTurnOver()
	{
		try {
			DecimalFormat df = new DecimalFormat("0.00");
			return Double.parseDouble(df.format(turnOver).replace(",", "."));
		} catch (Exception e) {
			return 0.0;
		}
	}
	public void setTurnOver(Double turnOver)
	{
		this.turnOver = turnOver;
	}

	public Double getQtdAdmitidos() {
		return qtdAdmitidos;
	}

	public void setQtdAdmitidos(Double qtdAdmitidos) {
		this.qtdAdmitidos = qtdAdmitidos;
	}

	public Double getQtdDemitidos() {
		return qtdDemitidos;
	}

	public void setQtdDemitidos(Double qtdDemitidos) {
		this.qtdDemitidos = qtdDemitidos;
	}

	public Integer getTempoServico() {
		return tempoServico;
	}

	public void setTempoServico(Integer tempoServico) {
		this.tempoServico = tempoServico;
	}

	public Integer getQtdColaboradores() {
		return qtdColaboradores;
	}

	public void setQtdColaboradores(Integer qtdColaboradores) {
		this.qtdColaboradores = qtdColaboradores;
	}

	public Double getQtdAtivosInicioMes() {
		return qtdAtivosInicioMes;
	}

	public void setQtdAtivosInicioMes(Double qtdAtivosInicioMes) {
		this.qtdAtivosInicioMes = qtdAtivosInicioMes;
	}

	public Double getQtdAtivosFinalMes() {
		return qtdAtivosFinalMes;
	}

	public void setQtdAtivosFinalMes(Double qtdAtivosFinalMes) {
		this.qtdAtivosFinalMes = qtdAtivosFinalMes;
	}

	public Long getIdAreaOuCargo() {
		return idAreaOuCargo;
	}

	public void setIdAreaOuCargo(Long idAreaOuCargo) {
		this.idAreaOuCargo = idAreaOuCargo;
	}
}
