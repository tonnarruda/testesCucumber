package com.fortes.rh.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.JFreeChart;

import com.fortes.rh.business.desenvolvimento.ColaboradorPresencaManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.model.desenvolvimento.IndicadorTreinamento;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.webwork.ServletActionContext;

public class IndicadorTreinamentosListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private CursoManager cursoManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private ColaboradorPresencaManager colaboradorPresencaManager;
	private TurmaManager turmaManager;

	private JFreeChart chart;
	private IndicadorTreinamento indicadorTreinamento = new IndicadorTreinamento();

	//dados dos graficos
	private Integer qtdAprovados;
	private Integer qtdReprovados;
	private Integer qtdTreinamentosRealizados;
	private Integer qtdTreinamentosNaoRealizados;
	private Integer qtdTotalInscritosTurmas = 0 ;
	private Integer qtdParticipantesPrevistos= 0 ;
	private Double percentualFrequencia = 0.0;
	private Map<String, Object> parametros;
	private Collection<String> colecao = new ArrayList<String>();

	private String grfTreinamento="";
	private String grfFrequencia="";
	private String grfDesempenho="";
	private String grfCusto="";

	public String list() throws Exception
	{
		prepareDatas();

		cursoManager.findCustoMedioHora(indicadorTreinamento, indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId());
		cursoManager.findCustoPerCapita(indicadorTreinamento, indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId());
		cursoManager.findHorasPerCapita(indicadorTreinamento, indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId());

		prepareGraficoFrequencia();
		prepareGraficoCumprimentoPlanoTreinamento();
		prepareGraficoDesempenho();
		prepareGraficoCustoCursoPorTipoDespesa();

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		return SUCCESS;
	}

	private void prepareDatas()
	{
		if (indicadorTreinamento.getDataIni() == null || indicadorTreinamento.getDataFim() == null)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.MONTH, 0);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			indicadorTreinamento.setDataIni(calendar.getTime());
			calendar.add(Calendar.YEAR, +1);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			indicadorTreinamento.setDataFim(calendar.getTime());
		}
	}

	private void prepareGraficoDesempenho()
	{
		HashMap<String, Integer> resultados = colaboradorTurmaManager.getResultado(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId());
		qtdAprovados = resultados.get("qtdAprovados");
		qtdReprovados = resultados.get("qtdReprovados");

		Collection<DataGrafico> graficoDesempenho = new ArrayList<DataGrafico>();
		graficoDesempenho.add(new DataGrafico(null, "Aprovados", qtdAprovados, ""));
		graficoDesempenho.add(new DataGrafico(null, "Reprovados", qtdReprovados, ""));
		grfDesempenho = StringUtil.toJSON(graficoDesempenho, null);
	}

	private void prepareGraficoFrequencia()
	{
		this.qtdTotalInscritosTurmas = colaboradorTurmaManager.findQuantidade(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId());
		this.qtdParticipantesPrevistos = turmaManager.quantidadeParticipantesPrevistos(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId());
		
		Collection<DataGrafico> graficoFrequencia = new ArrayList<DataGrafico>();
		graficoFrequencia.add(new DataGrafico(null, "Participantes", this.qtdParticipantesPrevistos, ""));
		graficoFrequencia.add(new DataGrafico(null, "Inscritos", this.qtdTotalInscritosTurmas, ""));
		grfFrequencia = StringUtil.toJSON(graficoFrequencia, null);
	}

	private void prepareGraficoCumprimentoPlanoTreinamento()
	{
		this.qtdTreinamentosRealizados = cursoManager.countTreinamentos(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId(), true);
		this.qtdTreinamentosNaoRealizados = cursoManager.countTreinamentos(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId(), false);

		Collection<DataGrafico> grfCumprimentoPlanoTreinamento = new ArrayList<DataGrafico>();
		grfCumprimentoPlanoTreinamento.add(new DataGrafico(null, "Realizados", this.qtdTreinamentosRealizados, ""));
		grfCumprimentoPlanoTreinamento.add(new DataGrafico(null, "Não Realizados", this.qtdTreinamentosNaoRealizados, ""));
		grfTreinamento = StringUtil.toJSON(grfCumprimentoPlanoTreinamento, null);
	}

	private void prepareGraficoCustoCursoPorTipoDespesa()
	{
		Double custoGeral = turmaManager.somaCustos(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId());
		
		Collection<DataGrafico> grfCustoTipoDespesa = new ArrayList<DataGrafico>();
		grfCustoTipoDespesa.add(new DataGrafico(null, "Não detalhado", 22220.00 - 20000.00, ""));
		
		grfCustoTipoDespesa.add(new DataGrafico(null, "Coffee", 750.00, ""));
		grfCustoTipoDespesa.add(new DataGrafico(null, "Sala", 1202.00, ""));
		grfCusto = StringUtil.toJSON(grfCustoTipoDespesa, null);
	}
	
	public void setCursoManager(CursoManager cursoManager)
	{
		this.cursoManager = cursoManager;
	}

	public IndicadorTreinamento getIndicadorTreinamento()
	{
		return indicadorTreinamento;
	}

	public void setIndicadorTreinamento(IndicadorTreinamento indicadorTreinamento)
	{
		this.indicadorTreinamento = indicadorTreinamento;
	}

	public JFreeChart getChart()
	{
		return chart;
	}

	public void setColaboradorTurmaManager(ColaboradorTurmaManager colaboradorTurmaManager)
	{
		this.colaboradorTurmaManager = colaboradorTurmaManager;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public Collection<String> getColecao()
	{
		return colecao;
	}
	
	public void setTurmaManager(TurmaManager turmaManager) {
		this.turmaManager = turmaManager;
	}
	
	public Integer getQtdTotalInscritosTurmas() {
		return colaboradorTurmaManager.findQuantidade(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId());
	}

	public void setQtdTotalInscritosTurmas(Integer qtdTotalInscritosTurmas) {
		this.qtdTotalInscritosTurmas = qtdTotalInscritosTurmas;
	}

	public Integer getQtdParticipantesPrevistos() {
		return turmaManager.quantidadeParticipantesPrevistos(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId());
	}
	public void setQtdParticipantesPrevistos(Integer qtdParticipantesPrevistos) {
		this.qtdParticipantesPrevistos = qtdParticipantesPrevistos;
	}

	public void setColaboradorPresencaManager(
			ColaboradorPresencaManager colaboradorPresencaManager) {
		this.colaboradorPresencaManager = colaboradorPresencaManager;
	}

	public ColaboradorPresencaManager getColaboradorPresencaManager() {
		return colaboradorPresencaManager;
	}

	public Double getPercentualFrequencia() 
	{
		return colaboradorTurmaManager.percentualFrequencia(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId());
	}

	public String getGrfTreinamento() {
		return grfTreinamento;
	}

	public String getGrfFrequencia() {
		return grfFrequencia;
	}

	public String getGrfDesempenho() {
		return grfDesempenho;
	}

	public String getGrfCusto() {
		return grfCusto;
	}

}
