package com.fortes.rh.model.desenvolvimento;

import java.util.Date;

import com.fortes.rh.util.MathUtil;

public class IndicadorTreinamento
{
	private Date dataIni;
	private Date dataFim;

	private Double custoTotal;
	private Double custoMedioHora;
	private Double custoPerCapita;
	private Double horasPerCapita;

	private Double somaCustos;
	private Integer somaHoras;
	private Integer qtdColaboradoresInscritos;
	private Integer qtdColaboradoresPrevistos;

	private Integer graficoQtdTreinamentosRealizados = 0;
	private Integer graficoQtdTreinamentosNaoRealizados = 0;
	private Integer graficoQtdVagasOciosas = 0;
	private Integer graficoQtdParticipantes = 0;
	private Integer graficoQtdAprovados = 0;
	private Integer graficoQtdReprovados = 0;

	private String alertMsg = "";

	public IndicadorTreinamento()
	{
	}

	public IndicadorTreinamento(Double somaCustos, Integer somaHoras)
	{
		this.somaCustos = (somaCustos != null ? somaCustos : 0);
		this.somaHoras = (somaHoras != null ? somaHoras : 0);
	}

	public String getCustoTotalFmt()
	{
		String retorno = "-";
		if (custoTotal != null && custoTotal > 0)
			retorno = MathUtil.formataValor(custoTotal);

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
	public Integer getSomaHoras()
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

	public String getAlertMsg()
	{
		return alertMsg;
	}
	public void setAlertMsg(String alertMsg)
	{
		this.alertMsg = alertMsg;
	}

	public Double getCustoTotal()
	{
		return custoTotal;
	}

	public void setCustoTotal(Double custoTotal)
	{
		this.custoTotal = custoTotal;
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

	public void setSomaHoras(Integer somaHoras)
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

}
