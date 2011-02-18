package com.fortes.rh.web.action.desenvolvimento;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.fortes.rh.business.desenvolvimento.ColaboradorPresencaManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.IndicadorTreinamento;
import com.fortes.rh.model.desenvolvimento.relatorio.GraficoIndicadorTreinamento;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.MathUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;

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

	public String list() throws Exception
	{
		prepareDatas();

		cursoManager.findCustoMedioHora(indicadorTreinamento, indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId());
		cursoManager.findCustoPerCapita(indicadorTreinamento, indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId());
		cursoManager.findHorasPerCapita(indicadorTreinamento, indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId());

		prepareGraficoFrequencia();
		prepareGraficoCumprimentoPlanoTreinamento();
		prepareGraficoDesempenho();

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

//	private void prepareGraficoFrequencia()
//	{
//		qtdParticipantes = cursoManager.findQtdColaboradoresInscritosTreinamentos(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId());
//		qtdPrevistos = cursoManager.findSomaColaboradoresPrevistosTreinamentos(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId());
//		qtdVagasOciosas = qtdPrevistos - qtdParticipantes;
//
//		if (qtdVagasOciosas > 0)
//		{
//			indicadorTreinamento.setGraficoQtdParticipantes(qtdParticipantes);
//			indicadorTreinamento.setGraficoQtdVagasOciosas(qtdVagasOciosas);
//		}
//	}
//
//	public String graficoFrequencia() throws Exception
//	{
//		prepareGraficoFrequencia();
//
//		DefaultPieDataset dataSet = new DefaultPieDataset();
//
//		qtdParticipantes = cursoManager.findQtdColaboradoresInscritosTreinamentos(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId());
//		qtdPrevistos = cursoManager.findSomaColaboradoresPrevistosTreinamentos(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId());
//		qtdVagasOciosas = qtdPrevistos - qtdParticipantes;
//
//		if (qtdVagasOciosas > 0)
//		{
//			dataSet.setValue("Participantes", qtdParticipantes);
//			dataSet.setValue("Vagas ociosas", qtdVagasOciosas);
//		}
//
//		return this.geraGrafico(dataSet);
//	}

	public String graficoCumprimentoPlanoTreinamento() throws Exception
	{
		DefaultPieDataset dataSet = new DefaultPieDataset();

		prepareGraficoCumprimentoPlanoTreinamento();
		dataSet.setValue("Treinamentos\n Realizados", qtdTreinamentosRealizados);
		dataSet.setValue("Treinamentos\n não Realizados", qtdTreinamentosNaoRealizados);

		return this.geraGrafico(dataSet);
	}

	public String graficoDesempenho() throws Exception
	{
		DefaultPieDataset dataSet = new DefaultPieDataset();

		prepareGraficoDesempenho();
		dataSet.setValue("Alunos\n Aprovados", qtdAprovados);
		dataSet.setValue("Alunos\n Reprovados", qtdReprovados);

		return this.geraGrafico(dataSet);
	}

	public String graficoVagasPorInscrito() throws Exception
	{
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

		prepareGraficoFrequencia();
		dataSet.addValue(qtdParticipantesPrevistos, "Vagas",  "");
		dataSet.addValue(qtdTotalInscritosTurmas,"Inscritos", "");
		
		return this.geraGraficoBarras(dataSet);
	}
	
	private void prepareGraficoDesempenho()
	{
		Collection<ColaboradorTurma> colaboradorTurmas = colaboradorTurmaManager.findAprovadosReprovados(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim());
		
		qtdAprovados = colaboradorTurmaManager.countAprovados(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId(), true);
		qtdReprovados = colaboradorTurmaManager.countAprovados(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId(), false);

		indicadorTreinamento.setGraficoQtdAprovados(qtdAprovados);
		indicadorTreinamento.setGraficoQtdReprovados(qtdReprovados);
	}

	private void prepareGraficoFrequencia()
	{
		this.qtdTotalInscritosTurmas = colaboradorTurmaManager.findQuantidade(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId());
		this.qtdParticipantesPrevistos = turmaManager.quantidadeParticipantesPrevistos(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId());
		
		indicadorTreinamento.setGraficoQtdAprovados(qtdParticipantesPrevistos);
		indicadorTreinamento.setGraficoQtdReprovados(qtdTotalInscritosTurmas);
	}

	private void prepareGraficoCumprimentoPlanoTreinamento()
	{
		this.qtdTreinamentosRealizados = cursoManager.countTreinamentos(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId(), true);
		this.qtdTreinamentosNaoRealizados = cursoManager.countTreinamentos(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId(), false);

		indicadorTreinamento.setGraficoQtdTreinamentosRealizados(qtdTreinamentosRealizados);
		indicadorTreinamento.setGraficoQtdTreinamentosNaoRealizados(qtdTreinamentosNaoRealizados);
	}
	
	private String geraGrafico(DefaultPieDataset dataSet) throws Exception
	{
		//primeiro boolean = legendas
		chart = ChartFactory.createPieChart3D("",dataSet,false,false,false);
		
        final PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setBackgroundPaint(new Color(255, 255, 255));
        plot.setCircular(false);
        
        plot.setForegroundAlpha(0.5f);
        plot.setNoDataMessage("Sem dados para gerar o gráfico.");
        plot.setIgnoreNullValues(true);
        plot.setIgnoreZeroValues(true);
        NumberFormat nf = new DecimalFormat("###,##%0.00");
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{2}", NumberFormat.getNumberInstance(), nf));
		return SUCCESS;
	}
	
	private String geraGraficoBarras(DefaultCategoryDataset dataset) throws Exception
	{
		chart = ChartFactory.createBarChart3D("", "", "", dataset, PlotOrientation.VERTICAL, true, true, true);
		chart.setBackgroundPaint(new Color(255, 255, 255));
		CategoryPlot p = chart.getCategoryPlot();
		p.setBackgroundPaint(Color.white);
		p.setRangeGridlinePaint(Color.GRAY);
		
		return SUCCESS;
	}

	public String imprimir() throws Exception
	{
		parametros = RelatorioUtil.getParametrosRelatorio("Painel de Indicadores de T&D", getEmpresaSistema(), "Período: "+ DateUtil.formataDiaMesAno(indicadorTreinamento.getDataIni()) + " a " + DateUtil.formataDiaMesAno(indicadorTreinamento.getDataFim()));
		colecao.add("");//para o ireport exibir as bands

		cursoManager.findCustoMedioHora(indicadorTreinamento, indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId());
		cursoManager.findCustoPerCapita(indicadorTreinamento, indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId());
		cursoManager.findHorasPerCapita(indicadorTreinamento, indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), getEmpresaSistema().getId());
		
		prepareGraficoFrequencia();
		prepareGraficoCumprimentoPlanoTreinamento();
		prepareGraficoDesempenho();

		Collection<GraficoIndicadorTreinamento> graficoFrequencia = new ArrayList<GraficoIndicadorTreinamento>();
		Collection<GraficoIndicadorTreinamento> graficoCumprimento = new ArrayList<GraficoIndicadorTreinamento>();
		Collection<GraficoIndicadorTreinamento> graficoDesempenho = new ArrayList<GraficoIndicadorTreinamento>();
		
		graficoFrequencia.add(new GraficoIndicadorTreinamento("Participantes", indicadorTreinamento.getGraficoQtdParticipantes(), MathUtil.calculaPorcentagem(indicadorTreinamento.getGraficoQtdParticipantes(), indicadorTreinamento.getGraficoQtdVagasOciosas())));
		graficoFrequencia.add(new GraficoIndicadorTreinamento("Vagas ociosas", indicadorTreinamento.getGraficoQtdVagasOciosas(), MathUtil.calculaPorcentagem(indicadorTreinamento.getGraficoQtdVagasOciosas(), indicadorTreinamento.getGraficoQtdParticipantes())));
		
		graficoCumprimento.add(new GraficoIndicadorTreinamento("Realizados", indicadorTreinamento.getGraficoQtdTreinamentosRealizados(), MathUtil.calculaPorcentagem(indicadorTreinamento.getGraficoQtdTreinamentosRealizados(), indicadorTreinamento.getGraficoQtdTreinamentosNaoRealizados())));
		graficoCumprimento.add(new GraficoIndicadorTreinamento("Não realizados", indicadorTreinamento.getGraficoQtdTreinamentosNaoRealizados(), MathUtil.calculaPorcentagem(indicadorTreinamento.getGraficoQtdTreinamentosNaoRealizados(), indicadorTreinamento.getGraficoQtdTreinamentosRealizados())));
		
		graficoDesempenho.add(new GraficoIndicadorTreinamento("Aprovados", indicadorTreinamento.getGraficoQtdAprovados(), MathUtil.calculaPorcentagem(indicadorTreinamento.getGraficoQtdAprovados(),indicadorTreinamento.getGraficoQtdReprovados())));
		graficoDesempenho.add(new GraficoIndicadorTreinamento("Reprovados", indicadorTreinamento.getGraficoQtdReprovados(), MathUtil.calculaPorcentagem(indicadorTreinamento.getGraficoQtdReprovados(), indicadorTreinamento.getGraficoQtdAprovados())));
		
		parametros.put("GRAFICO_FREQUENCIA", graficoFrequencia);
		parametros.put("GRAFICO_CUMPRIMENTO", graficoCumprimento);
		parametros.put("GRAFICO_DESEMPENHO", graficoDesempenho);
		parametros.put("CUSTO_MEDIO_HORA", indicadorTreinamento.getCustoMedioHoraFmt());
		parametros.put("CUSTO_PER_CAPITA", indicadorTreinamento.getCustoPerCapitaFmt());
		parametros.put("HORAS_PER_CAPITA", indicadorTreinamento.getHorasPerCapitaFmt());
		parametros.put("CUSTO_TOTAL", indicadorTreinamento.getCustoTotalFmt());
		
		return Action.SUCCESS;
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

}
