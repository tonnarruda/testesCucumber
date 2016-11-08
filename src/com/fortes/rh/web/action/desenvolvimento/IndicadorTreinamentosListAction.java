package com.fortes.rh.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.jfree.chart.JFreeChart;

import com.fortes.rh.business.desenvolvimento.ColaboradorPresencaManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.TurmaTipoDespesaManager;
import com.fortes.rh.model.desenvolvimento.Curso;
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
import com.opensymphony.xwork.Action;

public class IndicadorTreinamentosListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private CursoManager cursoManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private ColaboradorPresencaManager colaboradorPresencaManager;
	private TurmaManager turmaManager;
	private TurmaTipoDespesaManager turmaTipoDespesaManager;
	private EmpresaManager empresaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EstabelecimentoManager estabelecimentoManager;

	private JFreeChart chart;
	private IndicadorTreinamento indicadorTreinamento = new IndicadorTreinamento();
	
	private Long cursoId;
	private Date dataIni;
	private Date dataFim;
	
	//dados dos graficos
	private Integer qtdAprovados;
	private Integer qtdReprovados;
	private Integer qtdTreinamentosRealizados;
	private Integer qtdTreinamentosNaoRealizados;
	private Integer qtdParticipantesPresentes= 0;
	private Map<String, Object> parametros;
	private Collection<String> colecao = new ArrayList<String>();

	private String grfTreinamento="";
	private String grfFrequenciaParticipantes="";
	private String grfFrequenciaPresentes="";
	private String grfFrequenciaInscritos="";
	private String grfDesempenho="";
	private String grfCusto="";
	private String grfCustoPorCurso="";
	private String json="";

	private Long[] empresasCheck;
	private Collection<CheckBox> empresasCheckList = new ArrayList<CheckBox>();
	private Long[] areasCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private Long[] cursosCheck;
	private Collection<CheckBox> cursosCheckList = new ArrayList<CheckBox>();
	private Long[] estabelecimentosCheck;
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private boolean considerarDiaTurmaCompreendidoNoPeriodo;

	public String list() throws Exception
	{
		prepareDatas();
		
		Collection<Empresa> empresas = empresaManager.findEmpresasPermitidas(true , null, getUsuarioLogado().getId(), "ROLE_TED_PAINEL_IND");
   		empresasCheckList =  CheckListBoxUtil.populaCheckListBox(empresas, "getId", "getNome");
   		empresasCheckList =  CheckListBoxUtil.marcaCheckListBox(empresasCheckList, StringUtil.LongToString(empresasCheck));
   		
   		if (ArrayUtils.isEmpty(empresasCheck)) 
   		{
   			CollectionUtil<Empresa> cUtil = new CollectionUtil<Empresa>();
			empresasCheck = cUtil.convertCollectionToArrayIds(empresas);
		}
   		
   		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(empresasCheck);
   		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, StringUtil.LongToString(areasCheck));
   		
   		Collection<Curso> cursos = cursoManager.findAllByEmpresasParticipantes(empresasCheck);
		cursosCheckList =  CheckListBoxUtil.populaCheckListBox(cursos, "getId", "getNome");
		cursosCheckList =  CheckListBoxUtil.marcaCheckListBox(cursosCheckList, StringUtil.LongToString(cursosCheck));
		
		estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(empresasCheck);
		estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, StringUtil.LongToString(estabelecimentosCheck));
   		
		indicadorTreinamento = cursoManager.montaIndicadoresTreinamentos(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), empresasCheck, areasCheck, cursosCheck, estabelecimentosCheck, considerarDiaTurmaCompreendidoNoPeriodo);

		prepareGraficoFrequencia();
		prepareGraficoCumprimentoPlanoTreinamento();
		prepareGraficoDesempenho();
		prepareGraficoCustoCursoPorTipoDespesa();
		prepareGraficoCustoCursoPorCurso();

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
		HashMap<String, Integer> resultados = colaboradorTurmaManager.getResultado(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), empresasCheck, areasCheck, cursosCheck, estabelecimentosCheck);
		qtdAprovados = resultados.get("qtdAprovados");
		qtdReprovados = resultados.get("qtdReprovados");

		Collection<DataGrafico> graficoDesempenho = new ArrayList<DataGrafico>();
		graficoDesempenho.add(new DataGrafico(null, "Aprovados", qtdAprovados, ""));
		graficoDesempenho.add(new DataGrafico(null, "Reprovados", qtdReprovados, ""));
		grfDesempenho = StringUtil.toJSON(graficoDesempenho, null);
	}

	private void prepareGraficoFrequencia()
	{
		Object[] participantes = new Object[]{1, indicadorTreinamento.getQtdColaboradoresPrevistos()};
		
		Collection<Object[]>  graficoParticipantes = new ArrayList<Object[]>();
		graficoParticipantes.add(participantes);
		grfFrequenciaParticipantes = StringUtil.toJSON(graficoParticipantes, null);
		
		Object[] inscritos = new Object[]{2, indicadorTreinamento.getQtdColaboradoresFiltrados()};
		
		Collection<Object[]>  graficoInscritos = new ArrayList<Object[]>();
		graficoInscritos.add(inscritos);
		grfFrequenciaInscritos = StringUtil.toJSON(graficoInscritos, null);
		
		this.qtdParticipantesPresentes = turmaManager.quantidadeParticipantesPresentes(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), empresasCheck, areasCheck, cursosCheck, estabelecimentosCheck);
		Object[] presentes = new Object[]{3, qtdParticipantesPresentes};
		
		Collection<Object[]>  graficoPresentes = new ArrayList<Object[]>();
		graficoPresentes.add(presentes);
		grfFrequenciaPresentes = StringUtil.toJSON(graficoPresentes, null);
	}

	private void prepareGraficoCumprimentoPlanoTreinamento()
	{
		this.qtdTreinamentosRealizados = cursoManager.countTreinamentos(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), empresasCheck, cursosCheck, true);
		this.qtdTreinamentosNaoRealizados = cursoManager.countTreinamentos(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), empresasCheck, cursosCheck, false);

		Collection<DataGrafico> grfCumprimentoPlanoTreinamento = new ArrayList<DataGrafico>();
		grfCumprimentoPlanoTreinamento.add(new DataGrafico(null, "Realizados", this.qtdTreinamentosRealizados, ""));
		grfCumprimentoPlanoTreinamento.add(new DataGrafico(null, "Não Realizados", this.qtdTreinamentosNaoRealizados, ""));
		grfTreinamento = StringUtil.toJSON(grfCumprimentoPlanoTreinamento, null);
	}

	private void prepareGraficoCustoCursoPorTipoDespesa()
	{
		Collection<DataGrafico> grfCustoTipoDespesa = new ArrayList<DataGrafico>();

		Double custosNaoDetalhados = turmaManager.somaCustosNaoDetalhados(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), empresasCheck, cursosCheck);
		grfCustoTipoDespesa.add(new DataGrafico(null, "Não detalhado", custosNaoDetalhados, ""));
		
		Collection<TipoDespesa> tipoDespesas = turmaTipoDespesaManager.somaDespesasPorTipo(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), empresasCheck, cursosCheck);
		for (TipoDespesa tipoDespesa : tipoDespesas)
			grfCustoTipoDespesa.add(new DataGrafico(null, tipoDespesa.getDescricao(), tipoDespesa.getTotalDespesas(), ""));
			
		grfCusto = StringUtil.toJSON(grfCustoTipoDespesa, null);
	}
	
	private void prepareGraficoCustoCursoPorCurso()
	{
		Collection<DataGrafico> grfCustoCurso = new ArrayList<DataGrafico>();
		
		Collection<Curso> cursos = cursoManager.somaDespesasPorCurso(indicadorTreinamento.getDataIni(), indicadorTreinamento.getDataFim(), empresasCheck, cursosCheck);
		for (Curso curso : cursos) {
			grfCustoCurso.add(new DataGrafico(curso.getId(), curso.getNome(), curso.getTotalDespesas() , ""));
		}
		
		grfCustoPorCurso = StringUtil.toJSON(grfCustoCurso, null);
	}
	
	public String grfTipoDespesaPorCurso() throws Exception
	{
		Collection<DataGrafico> grfCustoTipoDespesa = new ArrayList<DataGrafico>();
		Curso curso = cursoManager.findByIdProjection(cursoId);

		Double custosNaoDetalhados = turmaManager.somaCustosNaoDetalhados(dataIni, dataFim, empresasCheck, new Long[]{cursoId});
		
		if(custosNaoDetalhados != null & custosNaoDetalhados != 0)
			grfCustoTipoDespesa.add(new DataGrafico(null, "Não detalhado", custosNaoDetalhados, curso.getNome()));
		
		Collection<TipoDespesa> tipoDespesas = turmaTipoDespesaManager.somaDespesasPorTipo(dataIni, dataFim, empresasCheck, new Long[]{cursoId});
		for (TipoDespesa tipoDespesa : tipoDespesas)
			grfCustoTipoDespesa.add(new DataGrafico(tipoDespesa.getId(), tipoDespesa.getDescricao(), tipoDespesa.getTotalDespesas(), curso.getNome()));
			
		json = StringUtil.toJSON(grfCustoTipoDespesa, null);
		
		return Action.SUCCESS;
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
	
	public String getGrfCustoPorCurso() {
		return grfCustoPorCurso;
	}
	
	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
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

	public Integer getQtdParticipantesPresentes() {
		return qtdParticipantesPresentes;
	}

	public String getGrfFrequenciaPresentes() {
		return grfFrequenciaPresentes;
	}

	public Collection<CheckBox> getCursosCheckList() {
		return cursosCheckList;
	}

	public void setCursosCheck(Long[] cursosCheck) {
		this.cursosCheck = cursosCheck;
	}

	public Collection<CheckBox> getAreasCheckList() {
		return areasCheckList;
	}

	public void setAreasCheck(Long[] areasCheck) {
		this.areasCheck = areasCheck;
	}

	public void setAreaOrganizacionalManager(
			AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}
	
	public Collection<CheckBox> getEstabelecimentosCheckList() {
		return estabelecimentosCheckList;
	}

	public void setEstabelecimentosCheckList(
			Collection<CheckBox> estabelecimentosCheckList) {
		this.estabelecimentosCheckList = estabelecimentosCheckList;
	}

	public EstabelecimentoManager getEstabelecimentoManager() {
		return estabelecimentoManager;
	}

	public void setEstabelecimentoManager(
			EstabelecimentoManager estabelecimentoManager) {
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public Long[] getEstabelecimentosCheck() {
		return estabelecimentosCheck;
	}

	public void setEstabelecimentosCheck(Long[] estabelecimentosCheck) {
		this.estabelecimentosCheck = estabelecimentosCheck;
	}

	public void setCursoId(Long cursoId) {
		this.cursoId = cursoId;
	}

	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public void setConsiderarDiaTurmaCompreendidoNoPeriodo(boolean considerarDiaTurmaCompreendidoNoPeriodo) {
		this.considerarDiaTurmaCompreendidoNoPeriodo = considerarDiaTurmaCompreendidoNoPeriodo;
	}

	public boolean isConsiderarDiaTurmaCompreendidoNoPeriodo() {
		return considerarDiaTurmaCompreendidoNoPeriodo;
	}
}
