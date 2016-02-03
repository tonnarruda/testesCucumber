package com.fortes.rh.web.chart;

import java.awt.Color;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;

public class MyChartCustomizer extends JRAbstractChartCustomizer
{

    public void customize(JFreeChart chart, JRChart jasperChart) {
          
     //Chart is a bar chart
     if(jasperChart.getChartType() == JRChart.CHART_TYPE_BAR) {
      
      BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
     
      //Removes gray line around the bar
      renderer.setDrawBarOutline(false); 
          
      //Removes the label displayed at the top of the bar
         renderer.setBaseItemLabelGenerator(null);
         
//      //Set maximum bar width
//         renderer.setMaximumBarWidth(0.10);
//                  
//         //Create no data message
         CategoryPlot categoryplot = (CategoryPlot) chart.getCategoryPlot();
//         categoryplot.setNoDataMessage("No data available");
//         categoryplot.setNoDataMessageFont(new Font("SansSerif",Font.BOLD,14));
//         categoryplot.setNoDataMessagePaint(Color.WHITE);
//         
//         //Set background as transparent
//         categoryplot.setBackgroundPaint(null);
//         
            CategoryDataset cd = (CategoryDataset) categoryplot.getDataset();
//         
//   //Get values assigned to bar chart
         if(cd != null) {
//          System.out.println("Bar Row " + cd.getRowCount());
//          System.out.println("Column Row " + cd.getColumnCount());
//          
          for (int row = 0; row < cd.getRowCount(); row++) {
           for (int col = 0; col < cd.getColumnCount(); col++) {
            String l_rowKey = (String)String.valueOf(cd.getRowKey(row));
            String l_colKey = (String)cd.getColumnKey(col);
            double l_value  = cd.getValue(cd.getRowKey(row), l_colKey).doubleValue();
            double s_value = cd.getValue(row,col).doubleValue();
//
            System.out.println("l_rowKey " + l_rowKey + " l_colKey  " + l_colKey + " l_value " + l_value + " s_value " + s_value);
            if (l_colKey.equals("02/2015"))
            	categoryplot.setRangeGridlinePaint(Color.green);
            	renderer.getItemPaint(row, col);
//          
           }
          }
//         
         }         
//                   
//         //Set space for labels so it does not truncate
////         categoryAxis.setMaximumCategoryLabelWidthRatio(25.0f);
////         categoryAxis.setMaximumCategoryLabelLines(2);
//         
         //set background grid color
         
                    
     }         
    }
}

