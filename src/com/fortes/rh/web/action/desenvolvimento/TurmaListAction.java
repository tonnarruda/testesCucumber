package com.fortes.rh.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.desenvolvimento.AvaliacaoCursoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.FiltroPlanoTreinamento;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.util.BooleanUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class TurmaListAction extends MyActionSupportList
{
	private TurmaManager turmaManager;
	private CursoManager cursoManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private AvaliacaoCursoManager avaliacaoCursoManager;

	private Collection<Turma> turmas;

	private Turma turma;
	private Curso curso;
	private String msgAlert = "";

	private Map<String, Object> parametros = new HashMap<String, Object>();
	private Collection<ColaboradorTurma> dataSource;
	private Collection<Curso> cursos;
	private Collection<AvaliacaoCurso> avaliacaoCursos;

	// Indica se a requisição veio do plano de treinamento
	private boolean planoTreinamento;
	private FiltroPlanoTreinamento filtroPlanoTreinamento = new FiltroPlanoTreinamento();

	private String[] turmasCheck;
	private Collection<CheckBox> turmasCheckList = new ArrayList<CheckBox>();

	private String[] cursosCheck;
	private Collection<CheckBox> cursosCheckList = new ArrayList<CheckBox>();
	private Date dataIni;
	private Date dataFim;
	private char realizada;
	private String totalCargaHoraria;
	private boolean exibirCustoDetalhado;

	public String filtroPlanoTreinamento() throws Exception
	{
		prepareDatas();

		cursos = cursoManager.findToList(new String[]{"id", "nome"}, new String[]{"id", "nome"}, new String[]{"nome"});

		if(getShowFilter()) {
			turmas = turmaManager.findPlanosDeTreinamento(0, 0, filtroPlanoTreinamento.getCursoId(), filtroPlanoTreinamento.getDataIni(), filtroPlanoTreinamento.getDataFim(), filtroPlanoTreinamento.getRealizada());
			totalCargaHoraria = cursoManager.somaCargaHoraria(turmas);
		}
		else
			addActionMessage("Utilize o filtro para buscar as Turmas.");

		return "successFiltroPlanoTreinamento";
	}

	public String imprimirPlanoTreinamento() throws Exception
	{
		turmas = turmaManager.findPlanosDeTreinamento(0, 0, filtroPlanoTreinamento.getCursoId(), filtroPlanoTreinamento.getDataIni(), filtroPlanoTreinamento.getDataFim(), filtroPlanoTreinamento.getRealizada());
		parametros = RelatorioUtil.getParametrosRelatorio("Plano de Treinamento", getEmpresaSistema(), montaFiltro());

		return Action.SUCCESS;
	}

	private void prepareDatas()
	{
		if (filtroPlanoTreinamento.getDataIni() == null || filtroPlanoTreinamento.getDataFim() == null)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.MONTH, 0);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			filtroPlanoTreinamento.setDataIni(calendar.getTime());
			calendar.add(Calendar.YEAR, +1);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			filtroPlanoTreinamento.setDataFim(calendar.getTime());
		}
	}

	private String montaFiltro()
	{
		StringBuilder filtro = new StringBuilder("Curso: ");
		if(filtroPlanoTreinamento.getCursoId() == null)
			filtro.append("Todos\n");
		else
		{
			Curso cursoTmp = cursoManager.findByIdProjection(filtroPlanoTreinamento.getCursoId());
			filtro.append(cursoTmp.getNome() + "\n");
		}

		filtro.append("Período: " + DateUtil.formataDiaMesAno(filtroPlanoTreinamento.getDataIni()) + " a " + DateUtil.formataDiaMesAno(filtroPlanoTreinamento.getDataFim()) + "\n");

		filtro.append("Realizada: ");
		if(filtroPlanoTreinamento.getRealizada() == 'T')
			filtro.append("Todas");
		else if(filtroPlanoTreinamento.getRealizada() == 'S')
			filtro.append("Sim");
		else if(filtroPlanoTreinamento.getRealizada() == 'N')
			filtro.append("Não");

		return filtro.toString();
	}

	public String list() throws Exception
	{
		setTotalSize(turmaManager.getCount(new String[]{"curso.id"}, new Object[]{curso.getId()}));
		turmas = turmaManager.findTurmas(getPage(), getPagingSize(), curso.getId());

		// Se não houver resultados na página, refaz a consulta com a página anterior
		if (turmas == null || turmas.isEmpty())
		{
			if (getPage() > 1)
			{
				setPage(getPage()-1);
				turmas = turmaManager.findTurmas(getPage(), getPagingSize(), curso.getId());
			}
			else
				addActionMessage("Não existem turmas para o filtro informado.");
		}

		curso = cursoManager.findByIdProjection(curso.getId());
		avaliacaoCursos = avaliacaoCursoManager.findByCurso(curso.getId());

		if (!msgAlert.equals(""))
			addActionMessage("Colaborador(es) já inscritos no curso <br>" + msgAlert + "<br>");

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		turmaManager.removeCascade(turma.getId());
		addActionMessage("Turma excluída com sucesso.");

		if(planoTreinamento)
			return "successFiltroPlanoTreinamento";
		else
			return Action.SUCCESS;
	}

	public String imprimirConfirmacaoCertificado() throws Exception
	{
		try
		{
			dataSource = colaboradorTurmaManager.findAprovadosByTurma(turma.getId());
			parametros = colaboradorTurmaManager.getDadosTurma(dataSource, parametros);
			parametros = turmaManager.getParametrosRelatorio(getEmpresaSistema(), parametros);
		}
		catch (Exception e)
		{
			addActionMessage(e.getMessage());
			e.printStackTrace();
			list();
			return Action.INPUT;
		}

		return Action.SUCCESS;
	}
	
	public String filtroCronogramaTreinamento() throws Exception
	{
		return Action.SUCCESS;
	}

	public String imprimirCronogramaTreinamento() throws Exception
	{
		turmas = turmaManager.findByIdProjection(LongUtil.arrayStringToArrayLong(turmasCheck));
		parametros = RelatorioUtil.getParametrosRelatorio("Cronograma de Treinamentos", getEmpresaSistema(), null);

		return Action.SUCCESS;
	}

	public String relatorioInvestimento() throws Exception
	{
		cursos = cursoManager.findAllSelect(getEmpresaSistema().getId());
		cursosCheckList = CheckListBoxUtil.populaCheckListBox(cursos, "getId", "getNome");
		
		return Action.SUCCESS;
	}
	
	public String imprimirRelatorioInvestimento() throws Exception
	{
		turmas = turmaManager.findByTurmasPeriodo(LongUtil.arrayStringToArrayLong(turmasCheck),  dataIni, dataFim, BooleanUtil.getValueCombo(realizada));
		
		try {
			if (turmas.isEmpty())
				throw new ColecaoVaziaException();
			
			parametros = RelatorioUtil.getParametrosRelatorio("Relatorio de Investimento", getEmpresaSistema(),  getPeriodoFormatado());
			parametros.put("EXIBIR_CUSTO_DETALHADO", exibirCustoDetalhado);
		
		} catch (ColecaoVaziaException e) {
			addActionMessage("Não existem dados para o filtro informado.");
			relatorioInvestimento();
			return Action.INPUT;
		
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Ocorreu um erro ao gerar o relatório.");
			relatorioInvestimento();
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}
	
	private String getPeriodoFormatado()
	{
		String periodoFormatado = "-";
		if (dataIni != null && dataFim != null)
			periodoFormatado = "Período: " + DateUtil.formataDiaMesAno(dataIni) + " - " + DateUtil.formataDiaMesAno(dataFim);

		return periodoFormatado;
	}
	
	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public void setParametros(Map<String, Object> parametros)
	{
		this.parametros = parametros;
	}

	public Collection<Turma> getTurmas()
	{
		return turmas;
	}

	public Turma getTurma()
	{
		if (turma == null)
		{
			turma = new Turma();
		}
		return turma;
	}

	public void setTurma(Turma turma)
	{
		this.turma = turma;
	}

	public void setTurmaManager(TurmaManager turmaManager)
	{
		this.turmaManager = turmaManager;
	}

	public Curso getCurso()
	{
		return curso;
	}

	public void setCurso(Curso curso)
	{
		this.curso = curso;
	}

	public void setCursoManager(CursoManager cursoManager)
	{
		this.cursoManager = cursoManager;
	}

	public String getMsgAlert()
	{
		return msgAlert;
	}

	public void setMsgAlert(String msgAlert)
	{
		this.msgAlert = msgAlert;
	}

	public Collection<ColaboradorTurma> getDataSource()
	{
		return dataSource;
	}

	public void setDataSource(Collection<ColaboradorTurma> dataSource)
	{
		this.dataSource = dataSource;
	}

	public void setColaboradorTurmaManager(ColaboradorTurmaManager colaboradorTurmaManager)
	{
		this.colaboradorTurmaManager = colaboradorTurmaManager;
	}

	public Collection<Curso> getCursos()
	{
		return cursos;
	}

	public FiltroPlanoTreinamento getFiltroPlanoTreinamento()
	{
		return filtroPlanoTreinamento;
	}

	public void setFiltroPlanoTreinamento(FiltroPlanoTreinamento filtroPlanoTreinamento)
	{
		this.filtroPlanoTreinamento = filtroPlanoTreinamento;
	}

	public Collection<CheckBox> getTurmasCheckList()
	{
		return turmasCheckList;
	}

	public void setTurmasCheck(String[] turmasCheck)
	{
		this.turmasCheck = turmasCheck;
	}

	public boolean isPlanoTreinamento() {
		return planoTreinamento;
	}

	public void setPlanoTreinamento(boolean planoTreinamento) {
		this.planoTreinamento = planoTreinamento;
	}

	public Collection<CheckBox> getCursosCheckList() {
		return cursosCheckList;
	}

	public void setCursosCheck(String[] cursosCheck) {
		this.cursosCheck = cursosCheck;
	}

	public Date getDataIni() {
		return dataIni;
	}

	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public void setRealizada(char realizada) {
		this.realizada = realizada;
	}

	public char getRealizada() {
		return realizada;
	}

	public Collection<AvaliacaoCurso> getAvaliacaoCursos() {
		return avaliacaoCursos;
	}

	public void setAvaliacaoCursoManager(AvaliacaoCursoManager avaliacaoCursoManager) {
		this.avaliacaoCursoManager = avaliacaoCursoManager;
	}

	public String getTotalCargaHoraria() {
		return totalCargaHoraria;
	}

	public boolean isExibirCustoDetalhado() {
		return exibirCustoDetalhado;
	}

	public void setExibirCustoDetalhado(boolean exibirCustoDetalhado) {
		this.exibirCustoDetalhado = exibirCustoDetalhado;
	}
}