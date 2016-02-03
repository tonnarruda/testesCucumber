package com.fortes.rh.web.chart;

import java.awt.Color;
import java.awt.Paint;

import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;

public class CustomBarRenderer extends BarRenderer
{
  public CustomBarRenderer() 
  {
    super();
  }

  public Paint getItemPaint(int row, int column)
  {
    CategoryDataset cd = getPlot().getDataset();
    if(cd != null) 
    {
      String l_rowKey = (String)cd.getRowKey(row);
      String l_colKey = (String)cd.getColumnKey(column);
      double l_value  = cd.getValue(l_rowKey, l_colKey).doubleValue();
      return l_value > 4
             ? Color.GREEN
             : (l_value > 2
                ? Color.decode("#4D4B93")
                : Color.RED);
    } else 
    	return getItemPaint(row, column);
  }
}