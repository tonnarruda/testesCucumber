package com.fortes.rh.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.desenvolvimento.AvaliacaoCursoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.geral.TipoDespesaManager;
import com.fortes.rh.business.pesquisa.AvaliacaoTurmaManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.FiltroPlanoTreinamento;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.FiltroAgrupamentoCursoColaborador;
import com.fortes.rh.model.dicionario.FiltroSituacaoCurso;
import com.fortes.rh.model.dicionario.StatusAprovacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.TipoDespesa;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.BooleanUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings("serial")
public class TurmaListAction extends MyActionSupportList
{
	private TurmaManager turmaManager;
	private CursoManager cursoManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private AvaliacaoCursoManager avaliacaoCursoManager;
	private AvaliacaoTurmaManager avaliacaoTurmaManager;
	private EmpresaManager empresaManager;
	private EstabelecimentoManager estabelecimentoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	private TipoDespesaManager tipoDespesaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;

	private Turma turma;
	private Curso curso;
	private String msgAlert = "";

	private Collection<Turma> turmas;
	private Collection<ColaboradorTurma> dataSource;
	private Collection<Curso> cursos;
	private Collection<AvaliacaoCurso> avaliacaoCursos;
	private Collection<AvaliacaoTurma> avaliacaoTurmas;
	private Collection<String> custos;
	private Map<String, Object> parametros = new HashMap<String, Object>();
	
	private String[] colaboradoresCursos;
	private Map<Curso, Set<Colaborador>> cursosColaboradores;

	// Indica se a requisição veio do plano de treinamento
	private boolean planoTreinamento;
	private FiltroPlanoTreinamento filtroPlanoTreinamento = new FiltroPlanoTreinamento();

	private String[] turmasCheck;
	private Collection<CheckBox> turmasCheckList = new ArrayList<CheckBox>(); 
	private String[] cursosCheck;
	private Collection<CheckBox> cursosCheckList = new ArrayList<CheckBox>();
	private String[] areasCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] estabelecimentosCheck;
	private String[] empresasCheck;
	private Collection<CheckBox> empresasCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private Collection<String[]> avaliacaoTurmasCheck;
	private Collection<CheckBox> avaliacaoTurmasCheckList = new ArrayList<CheckBox>();
	private Collection<String[]> diasTurmasCheck;
	private Collection<String[]> horariosIni;
	private Collection<String[]> horariosFim;
	
	private Date dataIni;
	private Date dataFim;
	private char realizada;
	private String totalCargaHoraria;
	private boolean exibirCustoDetalhado;

	private Long empresaId;
	private Long[] empresaIds;
	private Collection<Empresa> empresas;
	private Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias;
	private Collection<TipoDespesa> tipoDespesas;
	
	private char colaboradoresAvaliados;
	private char agruparPor;
	private boolean compartilharColaboradores;
	
	private Date dataReferencia;
	
	private Empresa empresa;
	private char filtroAgrupamento;
	private char filtroSituacao;
	private char filtroAprovado;

	public String filtroPlanoTreinamento() throws Exception
	{
		prepareDatas();

		cursos = cursoManager.findAllSelect(empresaId);

		if(getShowFilter()) {
			turmas = turmaManager.findPlanosDeTreinamento(0, 0, filtroPlanoTreinamento.getCursoId(), filtroPlanoTreinamento.getDataIni(), filtroPlanoTreinamento.getDataFim(), filtroPlanoTreinamento.getRealizada(), empresaId);
			totalCargaHoraria = cursoManager.somaCargaHoraria(turmas);
			
			if(turmas.size() == 0)
				addActionMessage("Não existem cursos/tumas a serem listadas para o filtro informado.");
		}
		else
			addActionMessage("Utilize o filtro para buscar as Turmas.");
		
		compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
		empresas = empresaManager.findEmpresasPermitidas(compartilharColaboradores, getEmpresaSistema().getId(), getUsuarioLogado().getId(), null);

		return "successFiltroPlanoTreinamento";
	}

	public String imprimirPlanoTreinamento() throws Exception
	{
		turmas = turmaManager.findPlanosDeTreinamento(0, 0, filtroPlanoTreinamento.getCursoId(), filtroPlanoTreinamento.getDataIni(), filtroPlanoTreinamento.getDataFim(), filtroPlanoTreinamento.getRealizada(), empresaId);
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

	public String preparePdi()
	{
		prepareEmpresas(false, "ROLE_MOV_PLANO_DESENVOLVIMENTO_INDIVIDUAL");
		
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(empresaIds);
		estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(empresaIds);
		
		return SUCCESS;
	}
	
	public String pdi()
	{
		prepareEmpresas(false, "ROLE_MOV_PLANO_DESENVOLVIMENTO_INDIVIDUAL");
		
		if (empresaId != null)
		{
			areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(empresaId);
			estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(empresaId);
		}
		else
		{
			areasCheckList = areaOrganizacionalManager. populaCheckOrderDescricao(empresaIds);
			estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(empresaIds);
		}
		
		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
		estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);
		
		configuracaoNivelCompetencias = configuracaoNivelCompetenciaManager.findColaboradoresCompetenciasAbaixoDoNivel(empresaId, LongUtil.arrayStringToArrayLong(estabelecimentosCheck), LongUtil.arrayStringToArrayLong(areasCheck), BooleanUtil.getValueCombo(colaboradoresAvaliados), agruparPor);
		
		if(configuracaoNivelCompetencias.isEmpty())
			addActionMessage("Não existem colaboradores para o filtro informado.");
		
		return SUCCESS;
	}
	
	public String imprimirPdi()
	{
		configuracaoNivelCompetencias = configuracaoNivelCompetenciaManager.findColaboradoresCompetenciasAbaixoDoNivel(empresaId, LongUtil.arrayStringToArrayLong(estabelecimentosCheck), LongUtil.arrayStringToArrayLong(areasCheck), BooleanUtil.getValueCombo(colaboradoresAvaliados), agruparPor);
		
		parametros = RelatorioUtil.getParametrosRelatorio("Plano de Treinamento Individual", getEmpresaSistema(), "");
		parametros.put("AGRUPAR_POR", String.valueOf(agruparPor));
		
		return SUCCESS;
	}
	
	public String prepareAplicarPdi() throws Exception
	{
		if(getEmpresaByfiltro() != null)
			empresaIds = new Long[]{ empresaId };
		else
			prepareEmpresas(false, "ROLE_MOV_PLANO_DESENVOLVIMENTO_INDIVIDUAL");

		tipoDespesas = tipoDespesaManager.find(new String[]{"empresa.id"}, empresaIds, new String[]{"descricao"});
		avaliacaoTurmas = avaliacaoTurmaManager.findAllSelect(true, empresaIds);
		avaliacaoTurmasCheckList = CheckListBoxUtil.populaCheckListBox(avaliacaoTurmas, "getId", "getQuestionarioTitulo");
		
		cursosColaboradores = new HashMap<Curso, Set<Colaborador>>();
		String[] dados = null;
		Curso curso = null;
		Collection<Turma> turmas = null;
		Colaborador colaborador = null;
		
		for (int i = 0; i < colaboradoresCursos.length; i++)
		{
			dados = colaboradoresCursos[i].split(",");
			
			colaborador = new Colaborador(dados[1], Long.parseLong(dados[0]));
			curso = new Curso(Long.parseLong(dados[2]), dados[3]);
			
			turmas = turmaManager.findByFiltro(null, null, 'N', empresaIds, new Long[] { curso.getId() });
			curso.setTurmas(turmas);
			
			if (!cursosColaboradores.containsKey(curso))
				cursosColaboradores.put(curso, new HashSet<Colaborador>());
			
			cursosColaboradores.get(curso).add(colaborador);
		}
		
		return SUCCESS;
	}
	
	private Long getEmpresaByfiltro() 
	{
		if(empresaId != null && empresaId <= 0L)
			empresaId = null;
		
		return empresaId;
	}

	public String aplicarPdi() throws Exception
	{
		int i = 0;
		String[] diasCheck = null;
		String custosTurma = null;
		
		try 
		{
			if (turmas == null || turmas.isEmpty())
				throw new ColecaoVaziaException("O PDI não foi concluído por não haver turmas configuradas.");
			
			for (Turma turma : turmas) 
			{
				if (turma != null && turma.getColaboradorTurmas() != null) 
				{
					if (turma.getId() != null)
					{
						Long[] colaboradoresIds = new Long[ turma.getColaboradorTurmas().size() ];
						int j = 0;
						
						for (ColaboradorTurma cTurma : turma.getColaboradorTurmas())
							colaboradoresIds[j++] = cTurma.getColaborador().getId();
						
						Collection<ColaboradorTurma> colaboradoresTurmas = colaboradorTurmaManager.findByTurma(turma.getId(), null, true, null, null);
						colaboradorTurmaManager.insereColaboradorTurmas(colaboradoresIds, colaboradoresTurmas, turma, null, 0, null);
					}
					else
					{
						turma.setEmpresa(getEmpresaSistema());
						diasCheck = (String[]) diasTurmasCheck.toArray()[i];
						custosTurma = (String) custos.toArray()[i];
	
						Long[] avaliacoesCheck = null;
						if (avaliacaoTurmasCheck != null && avaliacaoTurmasCheck.size() > i)
							avaliacoesCheck = LongUtil.arrayStringToArrayLong((String[]) avaliacaoTurmasCheck.toArray()[i]);
	
						turmaManager.salvarTurmaDiasCustosColaboradoresAvaliacoes(turma, diasCheck, custosTurma, turma.getColaboradorTurmas(), avaliacoesCheck, (String[]) horariosIni.toArray()[i], (String[]) horariosFim.toArray()[i]);
					}
				}
				
				i++;
			}
			
			addActionSuccess("Turmas criadas com sucesso");
		
		} 
		catch (ColecaoVaziaException e) 
		{
			e.printStackTrace();
			addActionWarning(e.getMessage());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			addActionError("Ocorreu um erro ao criar as turmas");
		}

		preparePdi();
		
		return SUCCESS;
	}

	private void prepareEmpresas(Boolean compartilhado, String role)
	{
		empresas = empresaManager.findEmpresasPermitidas(compartilhado , getEmpresaSistema().getId(), SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), role);
		CollectionUtil<Empresa> clu = new CollectionUtil<Empresa>();
		empresaIds = clu.convertCollectionToArrayIds(empresas);
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
		addActionSuccess("Turma excluída com sucesso.");

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
	
	public String popUpTurmaAvaliacaoAcao() throws Exception
	{
		avaliacaoTurmas = avaliacaoTurmaManager.findByTurma(turma.getId());
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
		cursos = cursoManager.findAllByEmpresasParticipantes(getEmpresaSistema().getId());
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
	
	public String prepareImprimirCursosVencidosAVencer() throws Exception
	{
		prepareEmpresas(true, "ROLE_REL_CURSOS_VENCIDOS_A_VENCER");
		empresasCheckList = CheckListBoxUtil.populaCheckListBox(empresas, "getId", "getNome");
		CheckListBoxUtil.marcaCheckListBox(empresasCheckList, empresasCheck);
		
		setMsgHelp("Para que o relatório seja gerado, o curso tem que ter uma periodicidade e possuir colaboradores.");
		
		return Action.SUCCESS;
	}
	
	public String imprimirCursosVencidosAVencer() throws Exception 
	{
		try {
			dataSource = colaboradorTurmaManager.findCursosVencidosAVencer(LongUtil.arrayStringToArrayLong(empresasCheck), LongUtil.arrayStringToArrayLong(cursosCheck), dataReferencia, filtroAgrupamento, filtroSituacao, filtroAprovado);
		
			if (dataSource.isEmpty())
				throw new ColecaoVaziaException();
			
			String retorno;
			String tituloRelatorio;
			String tituloSituacao = FiltroSituacaoCurso.VENCIDOS.getDescricao() + " e " + FiltroSituacaoCurso.A_VENCER.getDescricao();
			
			if (filtroAgrupamento == FiltroAgrupamentoCursoColaborador.CURSOS.getOpcao()) {
				tituloRelatorio = "Relatorio de Cursos " + tituloSituacao + " agrupado por Cursos";
				retorno = "successAgrupadoPorCurso";
			} else{
				tituloRelatorio = "Relatorio de Cursos " + tituloSituacao + " agrupado por Colaboradores";
				retorno = "successAgrupadoPorColaborador";
			}
			
			parametros = RelatorioUtil.getParametrosRelatorio(tituloRelatorio, getEmpresaSistema(),  getDataReferenciaFormatada());

			return retorno;
		} catch (ColecaoVaziaException e) {
			addActionMessage("Não existem dados para o filtro informado.");
			prepareImprimirCursosVencidosAVencer();
			return Action.INPUT;
		
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Ocorreu um erro ao gerar o relatório.");
			prepareImprimirCursosVencidosAVencer();
			return Action.INPUT;
		}
	}
	
	private String getPeriodoFormatado()
	{
		String periodoFormatado = "-";
		if (dataIni != null && dataFim != null)
			periodoFormatado = "Período: " + DateUtil.formataDiaMesAno(dataIni) + " - " + DateUtil.formataDiaMesAno(dataFim);

		return periodoFormatado;
	}
	
	private String getDataReferenciaFormatada(){
		String dataReferenciaFormatada = "-";
		if (dataReferencia != null)
			dataReferenciaFormatada = "Data de Referência: " + DateUtil.formataDiaMesAno(dataReferencia);

		return dataReferenciaFormatada;
	}
	
	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public void setParametros(Map<String, Object> parametros)
	{
		this.parametros = parametros;
	}
	
	public void setTurmas(Collection<Turma> turmas) 
	{
		this.turmas = turmas;
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

	public Collection<AvaliacaoTurma> getAvaliacaoTurmas() {
		return avaliacaoTurmas;
	}

	public void setAvaliacaoTurmaManager(AvaliacaoTurmaManager avaliacaoTurmaManager) {
		this.avaliacaoTurmaManager = avaliacaoTurmaManager;
	}

	
	public Collection<Empresa> getEmpresas()
	{
		return empresas;
	}

	
	public void setEmpresaManager(EmpresaManager empresaManager)
	{
		this.empresaManager = empresaManager;
	}

	
	public Long[] getEmpresaIds()
	{
		return empresaIds;
	}

	
	public Collection<CheckBox> getAreasCheckList()
	{
		return areasCheckList;
	}

	
	public Collection<CheckBox> getEstabelecimentosCheckList()
	{
		return estabelecimentosCheckList;
	}

	
	public void setAreasCheck(String[] areasCheck)
	{
		this.areasCheck = areasCheck;
	}

	
	public void setEstabelecimentosCheck(String[] estabelecimentosCheck)
	{
		this.estabelecimentosCheck = estabelecimentosCheck;
	}

	public Collection<ConfiguracaoNivelCompetencia> getConfiguracaoNivelCompetencias() {
		return configuracaoNivelCompetencias;
	}

	public void setConfiguracaoNivelCompetenciaManager(
			ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager) {
		this.configuracaoNivelCompetenciaManager = configuracaoNivelCompetenciaManager;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public char getAgruparPor() {
		return agruparPor;
	}

	public void setAgruparPor(char agruparPor) {
		this.agruparPor = agruparPor;
	}

	public String[] getColaboradoresCursos() {
		return colaboradoresCursos;
	}

	public void setColaboradoresCursos(String[] colaboradoresCursos) {
		this.colaboradoresCursos = colaboradoresCursos;
	}

	public Collection<Colaborador> getColaboradoresCurso(Curso curso) {
		return cursosColaboradores.get(curso);
	}

	public Collection<String[]> getDiasTurmasCheck() {
		return diasTurmasCheck;
	}

	public void setDiasTurmasCheck(Collection<String[]> diasTurmasCheck) {
		this.diasTurmasCheck = diasTurmasCheck;
	}

	public void setEstabelecimentoManager(	EstabelecimentoManager estabelecimentoManager) {
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public char getColaboradoresAvaliados() {
		return colaboradoresAvaliados;
	}

	public void setColaboradoresAvaliados(char colaboradoresAvaliados) {
		this.colaboradoresAvaliados = colaboradoresAvaliados;
	}

	public Collection<TipoDespesa> getTipoDespesas() {
		return tipoDespesas;
	}

	public void setTipoDespesas(Collection<TipoDespesa> tipoDespesas) {
		this.tipoDespesas = tipoDespesas;
	}

	public void setTipoDespesaManager(TipoDespesaManager tipoDespesaManager) {
		this.tipoDespesaManager = tipoDespesaManager;
	}

	public Collection<String> getCustos() {
		return custos;
	}

	public void setCustos(Collection<String> custos) {
		this.custos = custos;
	}

	public Collection<String[]> getAvaliacaoTurmasCheck() {
		return avaliacaoTurmasCheck;
	}

	public void setAvaliacaoTurmasCheck(Collection<String[]> avaliacaoTurmasCheck) {
		this.avaliacaoTurmasCheck = avaliacaoTurmasCheck;
	}

	public Collection<CheckBox> getAvaliacaoTurmasCheckList() {
		return avaliacaoTurmasCheckList;
	}

	public Map<Curso, Set<Colaborador>> getCursosColaboradores() {
		return cursosColaboradores;
	}

	public void setCursosColaboradores(Map<Curso, Set<Colaborador>> cursosColaboradores) {
		this.cursosColaboradores = cursosColaboradores;
	}

	public boolean isCompartilharColaboradores() {
		return compartilharColaboradores;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public void setHorariosIni(Collection<String[]> horariosIni) {
		this.horariosIni = horariosIni;
	}

	public void setHorariosFim(Collection<String[]> horariosFim) {
		this.horariosFim = horariosFim;
	}

	public Collection<String[]> getHorariosIni() {
		return horariosIni;
	}

	public Collection<String[]> getHorariosFim() {
		return horariosFim;
	}

	public Date getDataReferencia() {
		return dataReferencia;
	}

	public void setDataReferencia(Date dataReferencia) {
		this.dataReferencia = dataReferencia;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public char getFiltroAgrupamento() {
		return filtroAgrupamento;
	}

	public void setFiltroAgrupamento(char filtroAgrupamento) {
		this.filtroAgrupamento = filtroAgrupamento;
	}

	public char getFiltroSituacao() {
		return filtroSituacao;
	}

	public void setFiltroSituacao(char filtroSituacao) {
		this.filtroSituacao = filtroSituacao;
	}

	public void setEmpresasCheck(String[] empresasCheck) {
		this.empresasCheck = empresasCheck;
	}

	public Collection<CheckBox> getEmpresasCheckList() {
		return empresasCheckList;
	}
	
	public LinkedHashMap<Character, String> getStatusAprovacao(){
		return new StatusAprovacao();
	}

	public void setFiltroAprovado(char filtroAprovado) {
		this.filtroAprovado = filtroAprovado;
	}
}