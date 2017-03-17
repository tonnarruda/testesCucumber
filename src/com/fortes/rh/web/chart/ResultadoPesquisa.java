package com.fortes.rh.web.chart;

import java.awt.Color;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupport;

public class ResultadoPesquisa extends MyActionSupport
{
	private static final long serialVersionUID = 1L;

	@Autowired private ColaboradorRespostaManager colaboradorRespostaManager;

	private String idPergunta;
	private String idPesquisa;
	private JFreeChart chart;
	private Collection<Resposta> respostas;
	private int qtdeComentario = 0;
	private Pergunta pergunta;
	private String areasIds;
	private String estabelecimentosIds;

	private Date periodoIni;
	private Date periodoFim;
	private Long turmaId;

	public JFreeChart getChart()
	{
		return chart;
	}

	public String execute() throws Exception
	{
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String graficoObjetiva() throws Exception
	{
		DefaultPieDataset dataSet = new DefaultPieDataset();

		String[] areas = null;
		String[] estabelecimentos = null;
		if(areasIds != null && areasIds.length() > 0)
			areas = areasIds.split(",");
		if(estabelecimentosIds != null && estabelecimentosIds.length() > 0)
			estabelecimentos = estabelecimentosIds.split(",");

		List<Integer[]> qtdRespostas = colaboradorRespostaManager.countRespostas(pergunta.getId(), LongUtil.arrayStringToArrayLong(estabelecimentos), LongUtil.arrayStringToArrayLong(areas), periodoIni, periodoFim, turmaId);

		for (int i = 0; i < qtdRespostas.size(); i++)
		{
			Object[] qtdResposta = (Object[])qtdRespostas.get(i);
			if(qtdResposta[0] != null && qtdResposta[1] != null)
				dataSet.setValue(StringUtil.letraByNumero((Integer)qtdResposta[0]), (Integer)qtdResposta[1]);
		}

		return this.geraGrafico(dataSet);
	}

	public String geraGrafico(DefaultPieDataset dataSet) throws Exception
	{
		//primeiro boolean = legendas
		chart = ChartFactory.createPieChart3D("",dataSet,false,false,false);

        final PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setBackgroundPaint(new Color(255, 255, 255));
        plot.setCircular(true);
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        plot.setNoDataMessage("Sem dados para gerar o gráfico.");
        plot.setIgnoreNullValues(true);
        plot.setIgnoreZeroValues(true);
//        plot.setLabelGenerator(new StandardPieItemLabelGenerator("Item {0}\n{2} ({1})", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()));
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("Item {0}\n{2} ({1})", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()));

		return SUCCESS;
	}

	public void setIdPergunta(String idPergunta)
	{
		this.idPergunta = idPergunta;
	}

	public String getIdPergunta()
	{
		return idPergunta;
	}

	public Collection<Resposta> getRespostas()
	{
		return respostas;
	}

	public int getQtdeComentario()
	{
		return qtdeComentario;
	}

	public void setQtdeComentario(int qtdeComentario)
	{
		this.qtdeComentario = qtdeComentario;
	}

	public String getIdPesquisa()
	{
		return idPesquisa;
	}

	public void setIdPesquisa(String idPesquisa)
	{
		this.idPesquisa = idPesquisa;
	}

	public Pergunta getPergunta()
	{
		return pergunta;
	}

	public void setPergunta(Pergunta pergunta)
	{
		this.pergunta = pergunta;
	}

	public void setAreasIds(String areasIds)
	{
		this.areasIds = areasIds;
	}

	public void setPeriodoFim(Date periodoFim)
	{
		this.periodoFim = periodoFim;
	}

	public void setPeriodoIni(Date periodoIni)
	{
		this.periodoIni = periodoIni;
	}

	public void setRespostas(Collection<Resposta> respostas)
	{
		this.respostas = respostas;
	}

	public Long getTurmaId()
	{
		return turmaId;
	}

	public void setTurmaId(Long turmaId)
	{
		this.turmaId = turmaId;
	}

	public void setEstabelecimentosIds(String estabelecimentosIds)
	{
		this.estabelecimentosIds = estabelecimentosIds;
	}
}