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
        CategoryDataset catDS = catPlot.getDataset();
        Color seriesColor = null;
        
//        Object j = getParameterValue("REPORT_DATA_SOURCE");
        
//        Collection<TaxaDemissao> taxas = (Collection<TaxaDemissao>) parameter.getValue();
 
        for (int i = 0; i < catDS.getColumnCount(); i++) {
            if (catDS.getColumnKey(i).toString().equalsIgnoreCase("02/2015")) {
                seriesColor = Color.blue;
            } else if (catDS.getColumnKey(i).toString().equalsIgnoreCase("Warning")) {
                seriesColor = Color.green;
            } else if (catDS.getColumnKey(i).toString().equalsIgnoreCase("Minor")) {
                seriesColor = Color.red;
            } else {
            	seriesColor = Color.gray;
            }
            //catPlot.getRendererForDataset(catDS). Paint(seriesColor); // (catDS.getColumnIndex(catDS.getColumnKey(i)), seriesColor);
            
//            jrChart.get getXYPlot().setQuadrantPaint(i, seriesColor);
        }
        
//        System.out.println("called customizer");
//        CategoryPlot plot = jFreeChart.getCategoryPlot();
//        BarRenderer renderer = (BarRenderer) new BarRenderer();
//        renderer.setSeriesPaint(0, Color.green);
//        renderer.setSeriesPaint(1, Color.red);
//        plot.setRenderer(renderer);
        
        CustomBarRenderer renderer = new CustomBarRenderer();
        jFreeChart.getCategoryPlot().setRenderer(renderer);
        
//        catPlot.getRendererForDataset(catDS).getSeriesPaint(0);
//        catPlot.getRendererForDataset(catDS).getSeriesPaint(1);
//        catPlot.getRendererForDataset(catDS).getSeriesPaint(5);
    }
}
