package com.fortes.report;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.util.HashMap;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

public class BarChartCustomizer extends JRAbstractChartCustomizer
{
   @SuppressWarnings("serial")
   public void customize(JFreeChart chart, JRChart jasperChart)
   {
	    //chart.setTitle("Qtd. Colaboradores x Respostas");
//		JRFillChartDataset fill = (JRFillChartDataset)jasperChart.getDataset();	   
		
		//Integer totalColab = (Integer)fill.getInputDataset().getFieldValue("totalColab");
		
		Integer totalColab = (Integer)(((HashMap)getParameterValue("REPORT_PARAMETERS_MAP")).get("TOTAL_COLAB_RESP"));
		if (totalColab == null)
			totalColab = 0;
		
 	    final BarRenderer oldRenderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
		final BarRenderer renderer = new BarRenderer() 
		{
		     public Paint getItemPaint(final int row, final int column) {
		         return oldRenderer.getItemPaint(column, column);
		     }
		};
		renderer.setBaseItemLabelGenerator(oldRenderer.getBaseItemLabelGenerator());
	    renderer.setBaseItemLabelFont(new Font("SansSerif",Font.PLAIN,6));
	    renderer.setBaseItemLabelPaint(Color.black);
		renderer.setBaseItemLabelsVisible(true);
		renderer.setBarPainter(new StandardBarPainter());
		
		chart.getCategoryPlot().setRenderer(renderer);

		// Configuracoes do eixo da Qtd. Colaboradores
		CategoryPlot plot = chart.getCategoryPlot();       
		//plot.getRangeAxis().setUpperMargin(0.8);
		plot.getRangeAxis().setLabel("Qtd. Colaboradores");
		//plot.getRangeAxis().setMinorTickMarkOutsideLength(0.7f);
		//plot.getRangeAxis().setMinorTickMarksVisible(true);
		plot.getRangeAxis().setTickMarksVisible(true);
		plot.getRangeAxis().setTickMarkOutsideLength(0.7f);
		plot.getDomainAxis().setLabel("Resposta");
		TickUnitSource tu = NumberAxis.createIntegerTickUnits();
		plot.getRangeAxis().setUpperBound(tu.getCeilingTickUnit(totalColab).getSize() +  tu.getCeilingTickUnit(totalColab).getMinorTickCount());
		plot.getRangeAxis().setStandardTickUnits(tu);
		plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
       
		// O marcador do total de colaboradores
		// não é usado na Avaliação de Desempenho, pois lá temos vários totais.
		if (totalColab != 0)
		{
			final ValueMarker target = new ValueMarker(totalColab);
			target.setLabel("Total: " + totalColab);
			target.setLabelFont(new Font("SansSerif", Font.PLAIN, 5));
			target.setLabelAnchor(RectangleAnchor.LEFT);
			target.setLabelTextAnchor(TextAnchor.BOTTOM_LEFT);
			target.setLabelPaint(new Color(0, 143, 255));
			target.setPaint(new Color(164, 235, 255));
			target.setStroke(new BasicStroke(0.7f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, new float[] {3.0f, 3.0f}, 0.0f));
			plot.addRangeMarker(target, Layer.BACKGROUND);
		}

		// Caso se precise customizar as cores
		//	final BarRenderer renderer = new BarRenderer() 
		//	{
		//	     private Paint[] colors = new Paint[] {Color.red, Color.blue, Color.green,
		//	             Color.yellow, Color.orange, Color.cyan,
		//	             Color.magenta, Color.blue};
		//	
		//	     public Paint getItemPaint(final int row, final int column) {
		//	         return this.colors[column % this.colors.length];
		//	     }
		//	};
	

		// Para mudar a posicao dos labels
		//  final ItemLabelPosition itemLabelPosition = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE11, 
		//		   															 TextAnchor.CENTER, 
		//		   															 TextAnchor.CENTER, 
		//		   															 -Math.PI / 2);
		//  
		//  renderer.setBasePositiveItemLabelPosition(itemLabelPosition);
		
		// Outra maneira
		//renderer.setPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER));
		//renderer.setNegativeItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER));       
		//renderer.setPositiveItemLabelPositionFallback(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE9, TextAnchor.CENTER));
		//renderer.setNegativeItemLabelPositionFallback(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE9, TextAnchor.CENTER));

		// Outros testes
		//renderer.setItemMargin(0.50);
		//plot.getRangeAxis().setLowerMargin(0.0);
		//plot.getDomainAxis().setLowerMargin(0.1);
		//plot.getDomainAxis().setUpperMargin(0.1);
   }
}
