package com.fortes.rh.web.chart;

import java.awt.Color;
import java.text.NumberFormat;
import java.util.Collection;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.action.MyActionSupport;

public class ProgressaoColaborador extends MyActionSupport
{
	@Autowired private HistoricoColaboradorManager historicoColaboradorManager;
	private Long id;
	private JFreeChart chart;

	public void setId(Long id)
	{
		this.id = id;
	}

	public JFreeChart getChart()
	{
		return chart;
	}

	public String execute() throws Exception
	{
		Collection<HistoricoColaborador> lista = historicoColaboradorManager.progressaoColaborador(id, null);

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (HistoricoColaborador historicoColaborador : lista)
		{
			dataset.addValue(historicoColaborador.getSalarioCalculado(), historicoColaborador.getColaborador().getNomeComercial(), DateUtil.formataDiaMesAno(historicoColaborador.getData()));
		}

		// chart title, x axis label, y axis label, data, PlotOrientation, include legend, tooltips, urls
		chart = ChartFactory.createLineChart("", "Data", "Salário", dataset, PlotOrientation.VERTICAL, false, false, false);

		CategoryPlot plot = chart.getCategoryPlot();
		
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAutoRangeIncludesZero(false);
        
        NumberFormat formatter = NumberFormat.getInstance();
		formatter.setGroupingUsed(true);
		formatter.setMinimumFractionDigits(2);
        rangeAxis.setNumberFormatOverride(formatter);

        CategoryItemRenderer renderer = plot.getRenderer();
		renderer.setSeriesPaint(0, Color.blue);

		return SUCCESS;
	}
}