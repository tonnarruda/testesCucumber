package com.fortes.report;

import java.awt.Color;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.PieDataset;

public class RespostasPieChartCustomizer extends JRAbstractChartCustomizer 
{
	public void customize(JFreeChart chart, JRChart jasperChart) 
	{
		PiePlot plot = (PiePlot) chart.getPlot();
		PieDataset dataset = plot.getDataset();

		for (int i = 0; i < dataset.getItemCount(); i++) 
		{
			plot.setSectionPaint(dataset.getKey(i), Color.YELLOW);
		}
	}
}
