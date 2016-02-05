package com.fortes.rh.web.chart;

import java.awt.Color;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.CategoryDataset;

public class BarChartCustomizer  extends JRAbstractChartCustomizer {
	 
    public void customize(JFreeChart jFreeChart, JRChart jrChart) {
    	CategoryPlot catPlot = jFreeChart.getCategoryPlot();
        
//        Object j = getParameterValue("REPORT_DATA_SOURCE");
        
        CustomBarRenderer renderer = new CustomBarRenderer();
        jFreeChart.getCategoryPlot().setRenderer(renderer);
        
    }
}
