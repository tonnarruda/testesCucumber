package com.fortes.rh.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.jfree.chart.JFreeChart;

import com.fortes.rh.business.desenvolvimento.ColaboradorPresencaManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.TurmaTipoDespesaManager;
import com.fortes.rh.model.desenvolvimento.IndicadorTreinamento;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.TipoDespesa;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.webwork.ServletActionContext;

public class IndicadorTreinamentosListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private CursoManager cursoManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private ColaboradorPresencaManager colaboradorPresencaManager;
	private TurmaManager turmaManager;
	private TurmaTipoDespesaManager turmaTipoDespesaManager;
	private EmpresaManager empresaManager;

	private JFreeChart chart;
	private IndicadorTreinamento indicadorTreinamento = new IndicadorTreinamento();

	//dados dos graficos
	private Integer qtdAprovados;
	private Integer qtdReprovados;
	private Integer qtdTreinamentosRealizados;
	private Integer qtdTreinamentosNaoRealizados;
	private Integer qtdTotalInscritosTurmas = 0 ;
	private Integer qtdParticipantesPrevistos= 0 ;
	private Map<String, Object> parametros;
	private Collection<String> colecao = new ArrayList<String>();

	private String grfTreinamento="";
	private String grfFrequenciaParticipantes="";
	private String grfFrequenciaInscritos="";
	private String grfDesempenho="";
	private String grfCusto="";

	private Long[] empresasCheck;
	private Collection<CheckBox> empresasCheckList = new ArrayList<CheckBox>();

	public String list() throws Exception
	{
		prepareDatas();
		
		Collection<Empresa> empresas = empresaManager.findEmpresasPermitidas(true , null, getUsuarioLogado().getId(), "ROLE_T&D_REL");
   		empresasCheckList =  CheckListBoxUtil.populaCheckListBox(empresas, "getId", "getNome");

   		if (ArrayUtils.isEmpty(empresasCheck)) {
   			CollectionUtil<Empresa> cUtil = new CollectionUtil<Empresa>();
			empresasCheck = cUtil.convertCollectionToArrayIds(empresas);
		}
   		
		indicadorTreinamento = cursoManager.montaIndicadoresTreinamentos(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), empresasCheck);

		prepareGraficoFrequencia();
		prepareGraficoCumprimentoPlanoTreinamento();
		prepareGraficoDesempenho();
		prepareGraficoCustoCursoPorTipoDespesa();

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

	private void prepareGraficoDesempenho()
	{
		HashMap<String, Integer> resultados = colaboradorTurmaManager.getResultado(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), empresasCheck);
		qtdAprovados = resultados.get("qtdAprovados");
		qtdReprovados = resultados.get("qtdReprovados");

		Collection<DataGrafico> graficoDesempenho = new ArrayList<DataGrafico>();
		graficoDesempenho.add(new DataGrafico(null, "Aprovados", qtdAprovados, ""));
		graficoDesempenho.add(new DataGrafico(null, "Reprovados", qtdReprovados, ""));
		grfDesempenho = StringUtil.toJSON(graficoDesempenho, null);
	}

	private void prepareGraficoFrequencia()
	{
		this.qtdTotalInscritosTurmas = cursoManager.findQtdColaboradoresInscritosTreinamentos(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), empresasCheck);
		Object[] inscritos = new Object[]{1, qtdTotalInscritosTurmas};
		Collection<Object[]>  graficoInscritos = new ArrayList<Object[]>();
		graficoInscritos.add(inscritos);
		grfFrequenciaInscritos = StringUtil.toJSON(graficoInscritos, null);
		
		this.qtdParticipantesPrevistos = turmaManager.quantidadeParticipantesPrevistos(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), empresasCheck);
		Object[] participantes = new Object[]{2, qtdParticipantesPrevistos};
		Collection<Object[]>  graficoParticipantes = new ArrayList<Object[]>();
		graficoParticipantes.add(participantes);
		grfFrequenciaParticipantes = StringUtil.toJSON(graficoParticipantes, null);
	}

	private void prepareGraficoCumprimentoPlanoTreinamento()
	{
		this.qtdTreinamentosRealizados = cursoManager.countTreinamentos(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), empresasCheck, true);
		this.qtdTreinamentosNaoRealizados = cursoManager.countTreinamentos(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), empresasCheck, false);

		Collection<DataGrafico> grfCumprimentoPlanoTreinamento = new ArrayList<DataGrafico>();
		grfCumprimentoPlanoTreinamento.add(new DataGrafico(null, "Realizados", this.qtdTreinamentosRealizados, ""));
		grfCumprimentoPlanoTreinamento.add(new DataGrafico(null, "Não Realizados", this.qtdTreinamentosNaoRealizados, ""));
		grfTreinamento = StringUtil.toJSON(grfCumprimentoPlanoTreinamento, null);
	}

	private void prepareGraficoCustoCursoPorTipoDespesa()
	{
		Collection<DataGrafico> grfCustoTipoDespesa = new ArrayList<DataGrafico>();

		Double custosNaoDetalhados = turmaManager.somaCustosNaoDetalhados(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), empresasCheck);
		grfCustoTipoDespesa.add(new DataGrafico(null, "Não detalhado", custosNaoDetalhados, ""));
		
		Collection<TipoDespesa> tipoDespesas = turmaTipoDespesaManager.somaDespesasPorTipo(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), empresasCheck);
		for (TipoDespesa tipoDespesa : tipoDespesas)
			grfCustoTipoDespesa.add(new DataGrafico(null, tipoDespesa.getDescricao(), tipoDespesa.getTotalDespesas(), ""));
			
		grfCusto = StringUtil.toJSON(grfCustoTipoDespesa, null);
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
		return cursoManager.findQtdColaboradoresInscritosTreinamentos(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), empresasCheck);
	}

	public void setQtdTotalInscritosTurmas(Integer qtdTotalInscritosTurmas) {
		this.qtdTotalInscritosTurmas = qtdTotalInscritosTurmas;
	}

	public Integer getQtdParticipantesPrevistos() {
		return turmaManager.quantidadeParticipantesPrevistos(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), empresasCheck);
	}
	
	public void setQtdParticipantesPrevistos(Integer qtdParticipantesPrevistos) {
		this.qtdParticipantesPrevistos = qtdParticipantesPrevistos;
	}

	public void setColaboradorPresencaManager(ColaboradorPresencaManager colaboradorPresencaManager) {
		this.colaboradorPresencaManager = colaboradorPresencaManager;
	}

	public ColaboradorPresencaManager getColaboradorPresencaManager() {
		return colaboradorPresencaManager;
	}

	public String getGrfTreinamento() {
		return grfTreinamento;
	}

	public String getGrfDesempenho() {
		return grfDesempenho;
	}

	public String getGrfCusto() {
		return grfCusto;
	}

	public void setTurmaTipoDespesaManager(TurmaTipoDespesaManager turmaTipoDespesaManager) {
		this.turmaTipoDespesaManager = turmaTipoDespesaManager;
	}

	public String getGrfFrequenciaParticipantes()
	{
		return grfFrequenciaParticipantes;
	}
	
	public String getGrfFrequenciaInscritos()
	{
		return grfFrequenciaInscritos;
	}

	public void setEmpresasCheck(Long[] empresasCheck)
	{
		this.empresasCheck = empresasCheck;
	}

	public Collection<CheckBox> getEmpresasCheckList()
	{
		return empresasCheckList;
	}
	
	public void setEmpresaManager(EmpresaManager empresaManager)
	{
		this.empresaManager = empresaManager;
	}
}
