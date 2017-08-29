package com.fortes.rh.model.relatorio.customizers;

import java.awt.Font;
import java.util.Locale;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;

import com.ibm.icu.text.DecimalFormat;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

public class AnaliseDesempenhoOrganizacaoGraphCustomizer extends JRAbstractChartCustomizer{

	@Override
	public void customize(JFreeChart jFreeChart, JRChart jrChart) {
		
		CategoryPlot categoryPlot = jFreeChart.getCategoryPlot();
		// Posiciona os percentuais no parte inferior do gráfico(Disposição Horizontal).
		categoryPlot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		
		CategoryAxis categoryAxis = categoryPlot.getDomainAxis();
		categoryAxis.setMaximumCategoryLabelLines(2);
		categoryAxis.setMaximumCategoryLabelWidthRatio(1f);
		categoryAxis.setLowerMargin(.01);
		categoryAxis.setUpperMargin(.01);
		
		//Configura espaço entre barras
		BarRenderer barRenderer = (BarRenderer) categoryPlot.getRenderer();
		barRenderer.setItemMargin(0.03);
		barRenderer.setMaximumBarWidth(0.2d);
		barRenderer.setBaseItemLabelGenerator(new CategoryItemLabelGenerator() {
			
			@Override
			public String generateRowLabel(final CategoryDataset categoryDataset, final int linha) {
				return null;
			}
			
			@Override
			public String generateLabel(final CategoryDataset categoryDataset, final int linha, final int coluna) {
				Number performance = categoryDataset.getValue(linha, coluna);
				
				DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(new Locale("pt","BR"));
				df.setMaximumFractionDigits(2);
				df.setMinimumFractionDigits(2);
				df.setPositiveSuffix("%");
				
				return df.format(performance.doubleValue());
			}
			
			@Override
			public String generateColumnLabel(final CategoryDataset categoryDataset, final int coluna) {
				return null;
			}
		});
		
		if(categoryPlot.getDataset().getRowCount() * categoryPlot.getDataset().getColumnCount() > 100)
			barRenderer.setBaseItemLabelsVisible(false);
		else if(categoryPlot.getDataset().getRowCount() * categoryPlot.getDataset().getColumnCount() > 70)
			barRenderer.setBaseItemLabelFont(new Font("SansSerif", Font.PLAIN, 4));

		//Configura espaço entre topo da barra e o label apresentado
		//barRenderer.setItemLabelAnchorOffset(10.0d);
	}
}
