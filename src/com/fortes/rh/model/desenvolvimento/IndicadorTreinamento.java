package com.fortes.rh.model.desenvolvimento;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.util.MathUtil;

public class IndicadorTreinamento
{
	private Long cursoId;
	private Long turmaId;

	private Date dataIni;
	private Date dataFim;

	private Double custoMedioHora;
	private Double custoPerCapita;
	private Double horasPerCapita;

	private Double somaCustos;
	private Double somaHoras;
	private Double somaHorasRatiada;
	private Integer qtdColaboradoresInscritos;
	private Integer qtdColaboradoresFiltrados;
	private Integer qtdColaboradoresPrevistos;
	private Integer totalHorasTreinamento;

	private Integer graficoQtdTreinamentosRealizados = 0;
	private Integer graficoQtdTreinamentosNaoRealizados = 0;
	private Integer graficoQtdVagasOciosas = 0;
	private Integer graficoQtdParticipantes = 0;
	private Integer graficoQtdAprovados = 0;
	private Integer graficoQtdReprovados = 0;

	private Double percentualFrequencia;
	private Double percentualInvestimento;

	private String alertMsg = "";

	public IndicadorTreinamento()
	{
	}

	public IndicadorTreinamento(Long cursoId, Double somaHoras)
	{
		this.cursoId = cursoId;
		this.somaHoras = (somaHoras != null ? somaHoras : 0);
	}
	
	public IndicadorTreinamento(Integer qtdColaboradoresPrevistos, Integer qtdColaboradoresFiltrados, Integer qtdColaboradoresInscritos, Double somaHoras, Double somaHorasRatiada, Double somaCustos) 
	{
		this.qtdColaboradoresPrevistos = qtdColaboradoresPrevistos;
		this.qtdColaboradoresFiltrados = qtdColaboradoresFiltrados;
		this.qtdColaboradoresInscritos = qtdColaboradoresInscritos;
		this.somaHoras = somaHoras;
		this.somaHorasRatiada = somaHorasRatiada;
		this.somaCustos = somaCustos;
	}

	public String getCustoTotalFmt()
	{
		String retorno = "-";
		
		if (somaCustos != null && somaCustos > 0)
			retorno = MathUtil.formataValor(somaCustos);

		return retorno;
	}

	public String getCustoMedioHoraFmt()
	{
		String retorno = "-";
		if (custoMedioHora != null && custoMedioHora > 0)
			retorno = MathUtil.formataValor(custoMedioHora);

		return retorno;
	}
	public String getCustoPerCapitaFmt()
	{
		String retorno = "-";
		if (custoPerCapita != null && custoPerCapita > 0)
			retorno = MathUtil.formataValor(custoPerCapita);

		return retorno;
	}
	public String getHorasPerCapitaFmt()
	{
		String retorno = "-";
		if (horasPerCapita != null && horasPerCapita > 0)
			retorno = MathUtil.formataValor(horasPerCapita);

		return retorno;
	}

	public Double getSomaCustos()
	{
		return somaCustos;
	}
	public Double getSomaHoras()
	{
		return somaHoras;
	}
	public Double getCustoMedioHora()
	{
		return custoMedioHora;
	}
	public void setCustoMedioHora(Double custoMedioHora)
	{
		this.custoMedioHora = custoMedioHora;
	}
	public Double getCustoPerCapita()
	{
		return custoPerCapita;
	}
	public void setCustoPerCapita(Double custoPerCapita)
	{
		this.custoPerCapita = custoPerCapita;
	}
	public Double getHorasPerCapita()
	{
		return horasPerCapita;
	}
	public void setHorasPerCapita(Double horasPerCapita)
	{
		this.horasPerCapita = horasPerCapita;
	}

	public Integer getQtdColaboradoresInscritos()
	{
		return qtdColaboradoresInscritos;
	}

	public Integer getQtdColaboradoresPrevistos()
	{
		return qtdColaboradoresPrevistos;
	}

	public String getTotalHorasTreinamento() {
		
		if(totalHorasTreinamento == null || totalHorasTreinamento.equals(0))
			return "-";
		
		Integer hora = totalHorasTreinamento/60;
		Integer minutos = totalHorasTreinamento%60;
		
		return (StringUtils.leftPad(hora.toString(), 4, "") + ":" +StringUtils.leftPad(minutos.toString(), 2, "0")).trim();	
	}

	public void setTotalHorasTreinamento(Integer totalHorasTreinamento) {
		this.totalHorasTreinamento = totalHorasTreinamento;
		
	}

	public String getAlertMsg()
	{
		return alertMsg;
	}
	public void setAlertMsg(String alertMsg)
	{
		this.alertMsg = alertMsg;
	}

	public Date getDataFim()
	{
		return dataFim;
	}

	public void setDataFim(Date dataFim)
	{
		this.dataFim = dataFim;
	}

	public Date getDataIni()
	{
		return dataIni;
	}

	public void setDataIni(Date dataIni)
	{
		this.dataIni = dataIni;
	}

	public void setSomaHoras(Double somaHoras)
	{
		this.somaHoras = somaHoras;
	}

	public void setQtdColaboradoresInscritos(Integer qtdColaboradoresInscritos)
	{
		this.qtdColaboradoresInscritos = qtdColaboradoresInscritos;
	}

	public void setQtdColaboradoresPrevistos(Integer qtdColaboradoresPrevistos)
	{
		this.qtdColaboradoresPrevistos = qtdColaboradoresPrevistos;
	}

	public Integer getGraficoQtdAprovados()
	{
		return graficoQtdAprovados;
	}

	public void setGraficoQtdAprovados(Integer graficoQtdAprovados)
	{
		this.graficoQtdAprovados = graficoQtdAprovados;
	}

	public Integer getGraficoQtdParticipantes()
	{
		return graficoQtdParticipantes;
	}

	public void setGraficoQtdParticipantes(Integer graficoQtdParticipantes)
	{
		this.graficoQtdParticipantes = graficoQtdParticipantes;
	}

	public Integer getGraficoQtdReprovados()
	{
		return graficoQtdReprovados;
	}

	public void setGraficoQtdReprovados(Integer graficoQtdReprovados)
	{
		this.graficoQtdReprovados = graficoQtdReprovados;
	}

	public Integer getGraficoQtdTreinamentosNaoRealizados()
	{
		return graficoQtdTreinamentosNaoRealizados;
	}

	public void setGraficoQtdTreinamentosNaoRealizados(Integer graficoQtdTreinamentosNaoRealizados)
	{
		this.graficoQtdTreinamentosNaoRealizados = graficoQtdTreinamentosNaoRealizados;
	}

	public Integer getGraficoQtdTreinamentosRealizados()
	{
		return graficoQtdTreinamentosRealizados;
	}

	public void setGraficoQtdTreinamentosRealizados(Integer graficoQtdTreinamentosRealizados)
	{
		this.graficoQtdTreinamentosRealizados = graficoQtdTreinamentosRealizados;
	}

	public Integer getGraficoQtdVagasOciosas()
	{
		return graficoQtdVagasOciosas;
	}

	public void setGraficoQtdVagasOciosas(Integer graficoQtdVagasOciosas)
	{
		this.graficoQtdVagasOciosas = graficoQtdVagasOciosas;
	}

	public Long getCursoId() {
		return cursoId;
	}

	public void setCursoId(Long cursoId) {
		this.cursoId = cursoId;
	}

	public Double getPercentualFrequencia() {
		return percentualFrequencia;
	}

	public void setPercentualFrequencia(Double percentualFrequencia) {
		this.percentualFrequencia = percentualFrequencia;
	}

	public Double getPercentualInvestimento() {
		return percentualInvestimento;
	}

	public void setPercentualInvestimento(Double percentualInvestimento) {
		this.percentualInvestimento = percentualInvestimento;
	}

	public void setSomaCustos(Double somaCustos) {
		this.somaCustos = somaCustos;
	}

	public Long getTurmaId() {
		return turmaId;
	}

	public void setTurmaId(Long turmaId) {
		this.turmaId = turmaId;
	}

	public Integer getQtdColaboradoresFiltrados() {
		return qtdColaboradoresFiltrados;
	}

	public void setQtdColaboradoresFiltrados(Integer qtdColaboradoresFiltrados) {
		this.qtdColaboradoresFiltrados = qtdColaboradoresFiltrados;
	}

	public Double getSomaHorasRatiada() {
		return somaHorasRatiada;
	}

	public void setSomaHorasRatiada(Double somaHorasRatiada) {
		this.somaHorasRatiada = somaHorasRatiada;
	}

}
