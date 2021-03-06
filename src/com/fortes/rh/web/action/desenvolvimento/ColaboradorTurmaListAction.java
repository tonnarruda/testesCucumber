package com.fortes.rh.web.action.desenvolvimento;

import static com.fortes.rh.util.CheckListBoxUtil.populaCheckListBox;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.business.desenvolvimento.AproveitamentoAvaliacaoCursoManager;
import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.FiltroPlanoTreinamento;
import com.fortes.rh.model.desenvolvimento.PrioridadeTreinamento;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.desenvolvimento.relatorio.ColaboradorCertificacaoRelatorio;
import com.fortes.rh.model.desenvolvimento.relatorio.MatrizTreinamento;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.StatusAprovacao;
import com.fortes.rh.model.dicionario.StatusTreinamento;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.EmpresaUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class ColaboradorTurmaListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private EmpresaManager empresaManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EstabelecimentoManager estabelecimentoManager;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private CargoManager cargoManager;
	private GrupoOcupacionalManager grupoOcupacionalManager;
	private TurmaManager turmaManager;
	private CertificacaoManager certificacaoManager;
	private ColaboradorManager colaboradorManager;
	private AproveitamentoAvaliacaoCursoManager aproveitamentoAvaliacaoCursoManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;

	private Collection<ColaboradorTurma> colaboradorTurmas;
	private Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
	private Collection<ColaboradorQuestionario> colaboradorQuestionarios; 
	private Turma turma;
	private ColaboradorTurma colaboradorTurma;
	private Curso curso;
	private Colaborador colaborador;
	private Long colaboradorId;
	private Long cursoId;
	private String json = "";

	private int qtdMesesSemCurso = 0;

	private Long dntId;
	private Long lntId;
	private Long areaFiltroId;

	private boolean gestor;
	private Estabelecimento estabelecimento = new Estabelecimento();

	private Collection<PrioridadeTreinamento> prioridadeTreinamentos;
	private FiltroPlanoTreinamento filtroPlanoTreinamento;

	private String[] cursosCheck;
	private Collection<CheckBox> cursosCheckList = new ArrayList<CheckBox>();
	private String[] areasCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] estabelecimentosCheck;
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private Long[] colaboradoresCheck;
	private Collection<CheckBox> colaboradoresCheckList = new ArrayList<CheckBox>();
	

	private Collection<CheckBox> gruposCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> cargosCheckList = new ArrayList<CheckBox>();
	private String[] gruposCheck;
	private String[] cargosCheck;
	
	// Indica se a requisição veio do plano de treinamento
	private boolean planoTreinamento;

	// 1 - áreas, 2 - grupos/cargos
	private int filtrarPor; // Relatório de Histórico de Treinamentos..
	private char filtro;
	private boolean imprimirMatriz = true;
	private Date dataIni;
	private Date dataFim;

	private char aprovado = 'T'; // T - Todos, S - Sim , N - Não
	//flag para a tela do relatório de colaboradores com treinamento e sem treinamento
	private boolean comTreinamento;
	private boolean exibeFiltro;
	private Map<String,Object> parametros = new HashMap<String, Object>();
	private Long empresaId;
	private String nomeBusca = "";
	private Collection<Empresa> empresas;
	
	private String reportFilter;
	private String reportTitle;
	private String dinamicColumns;
	private String dinamicProperties;
	private String dinamicPropertiesGroup;
	
	//relatório de Colaboradores x Certificações
	private Collection<ColaboradorCertificacaoRelatorio> colaboradoresCertificacoes;
	private Certificacao certificacao;
	private Collection<Certificacao> certificacoes;
	private Boolean compartilharColaboradores;
	
	private Map<String,String> situacoes = new SituacaoColaborador();
	private String situacao = null;
	
	private boolean exibeCargo;
	private boolean exibeCargaHorariaEfetiva;
	private boolean exibeTotalCargaHorariaEfetiva;
	
	private char filtroAgrupamento;
	private String status;
	private Long[] empresasPermitidas;
	
	public String list() throws Exception
	{
		if(msgAlert != null && !msgAlert.equals(""))
			addActionMessage(msgAlert);

		if(empresaId != null && empresaId.equals(-1L))
			empresaId =  null;
		
		populaEmpresa(new String[]{"ROLE_MOV_TURMA","ROLE_MOV_PLANO_TREINAMENTO"});	
		if(empresaId != null){
			estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(empresaId);
		}else{
			estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(new CollectionUtil<Empresa>().convertCollectionToArrayIds(empresas));
		}
		
		estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);
		turma = turmaManager.findById(turma.getId()); // precisa da colecao de avaliacaoTurmas
		
		setTotalSize(colaboradorTurmaManager.getCount(turma.getId(), empresaId, nomeBusca, LongUtil.arrayStringToArrayLong(estabelecimentosCheck), null, getStatusAprovado()));
		colaboradorTurmas = colaboradorTurmaManager.findByTurmaColaborador(turma.getId(), empresaId, nomeBusca, LongUtil.arrayStringToArrayLong(estabelecimentosCheck), null, getPage(), getPagingSize(), getStatusAprovado());
		colaboradorTurmas = colaboradorTurmaManager.setFamiliaAreas(colaboradorTurmas, empresaId);
		
		colaboradorQuestionarios = colaboradorQuestionarioManager.findRespondidasByColaboradorETurma(null, turma.getId(), empresaId);

		return Action.SUCCESS;
	}

	private Boolean getStatusAprovado(){
		if(aprovado == 'S')
			return true;
		else if(aprovado == 'N')
			return false;
		return null;
	}
	
	private void populaEmpresa(String... roles)
	{
		compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
		empresas = empresaManager.findEmpresasPermitidas(compartilharColaboradores , getEmpresaSistema().getId(), SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), roles);
	}

	public String prepareFiltroHistoricoTreinamentos() throws Exception
	{
		empresaId = getEmpresaSistema().getId();
				
		Collection<AreaOrganizacional> areaOrganizacionalsTmp = areaOrganizacionalManager.findAllSelectOrderDescricao(getEmpresaSistema().getId(), AreaOrganizacional.TODAS, null, false);
		areasCheckList = populaCheckListBox(areaOrganizacionalsTmp, "getId", "getDescricaoStatusAtivo", null);
		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);

		cargosCheckList = populaCheckListBox(cargoManager.findAllSelect("nome", null, Cargo.TODOS, getEmpresaSistema().getId()), "getId", "getNome", null);
		cargosCheckList = CheckListBoxUtil.marcaCheckListBox(cargosCheckList, cargosCheck);
		gruposCheckList = populaCheckListBox(grupoOcupacionalManager.findAllSelect(getEmpresaSistema().getId()), "getId", "getNome", null);
		gruposCheckList = CheckListBoxUtil.marcaCheckListBox(gruposCheckList, gruposCheck);
		
		return SUCCESS;
	}

	public String filtroHistoricoTreinamentos() throws Exception
	{
		colaboradors = colaboradorManager.findColaboradoresByArea(LongUtil.arrayStringToArrayLong(areasCheck), colaborador.getNome(), colaborador.getMatricula(), getEmpresaSistema().getId(), colaborador.getNomeComercial());
		
		if (colaboradors == null || colaboradors.isEmpty())
		{
			addActionMessage("Não existem colaboradores para o filtro informado.");
			prepareFiltroHistoricoTreinamentos();
			return Action.INPUT;
		}
		colaboradoresCheckList = populaCheckListBox(colaboradors, "getId", "getNome", null);

		
		return prepareFiltroHistoricoTreinamentos();
	}

	public String relatorioHistoricoTreinamentos() {
		try {
			colaboradorTurmas = colaboradorTurmaManager.findHistoricoTreinamentosByColaborador(getEmpresaSistema().getId(), dataIni, dataFim, colaboradoresCheck);
			parametros = RelatorioUtil.getParametrosRelatorio("Histórico de Treinamentos", getEmpresaSistema(), null);
				String[] faixaSalarialId;
				Long colaboradorIdTemp = null;
				Collection<MatrizTreinamento> matrizTreinamentos = new ArrayList<MatrizTreinamento>();
				if(colaboradorTurmas != null && !colaboradorTurmas.isEmpty()){
					if(imprimirMatriz){
						for (ColaboradorTurma colaboradorTurma : colaboradorTurmas) {
							faixaSalarialId = new String[]{colaboradorTurma.getColaborador().getFaixaSalarial().getId().toString()};
							if(!colaboradorTurma.getColaborador().getId().equals(colaboradorIdTemp)){
								matrizTreinamentos = null;
								matrizTreinamentos = certificacaoManager.montaMatriz(imprimirMatriz, faixaSalarialId, colaboradorTurmas);
							}
							colaboradorTurma.setMatrizTreinamentos(matrizTreinamentos);
							colaboradorIdTemp = colaboradorTurma.getColaborador().getId();
						}
					}
					matrizTreinamentos = null;
				} else {
					colaboradorTurmas.addAll(colaboradorTurmaManager.findByColaboradoresForMatrizHistoricoTreinamento(colaboradoresCheck));
				}
			parametros.put("IMPRIMIR_MATRIZ", imprimirMatriz);

			return SUCCESS;
		} catch (ColecaoVaziaException e) {
			addActionMessage(e.getMessage());
			prepareRelatorioColaborador();
			areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
			estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);
			cargosCheckList = CheckListBoxUtil.marcaCheckListBox(cargosCheckList, cargosCheck);

			return INPUT;
		} catch (Exception e) {
			addActionError("Erro ao gerar relatório");
			e.printStackTrace();
			prepareRelatorioColaborador();

			return INPUT;
		}
	}

	public String delete() throws Exception
	{
		try
		{
			colaboradorTurma.setTurma(turma);
			colaboradorTurmaManager.remove(colaboradorTurma);
			addActionSuccess("Colaborador retirado da turma com sucesso.");
		}
		catch (Exception e)
		{
			addActionWarning("Não é possível remover o colaborador da turma, pois este já possui presença(s) registrada(s).");
			list();
			return Action.INPUT;
		}

		if (dntId != null && areaFiltroId != null)
		{
			msgAlert = "Previsão de curso para colaborador excluída da DNT com sucesso";
			return "successDnt";
		}

		return planoTreinamento ? "successFiltroPlanoTreinamento" : Action.SUCCESS;
	}

	public String prepareRelatorioColaborador()
	{
		empresaId = empresaManager.ajustaCombo(empresaId, getEmpresaSistema().getId());
		populaEmpresa(new String[]{"ROLE_REL_COLABORADOR_SEM_TREINAMENTO", "ROLE_REL_COLABORADOR_COM_TREINAMENTO"});	
		empresaId = getEmpresaSistema().getId();

		return Action.SUCCESS;
	}

	public String relatorioColaboradorSemTreinamento()
	{
		try{
			empresaId = empresaManager.ajustaCombo(empresaId, getEmpresaSistema().getId());
			parametros = RelatorioUtil.getParametrosRelatorio("Colaboradores que não fizeram o treinamento", getEmpresaSistema(), "");
			
			if(status.equals(StatusTreinamento.NUNCA_REALIZOU_NENHUM_TREINAMENTO)){
				colaboradors = colaboradorManager.findColaboradoresQueNuncaRealizaramTreinamento(EmpresaUtil.empresasSelecionadas(empresaId, empresasPermitidas), LongUtil.arrayStringToArrayLong(cursosCheck), LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(estabelecimentosCheck));
				return "nunca_realizou_nenhum_treinamento";
			} else if(status.equals(StatusTreinamento.SEM_TREINAMENTO)){
				colaboradorTurmas = colaboradorTurmaManager.findRelatorioColaboradoresQueNuncaRealizaramOsCursosSelecioandos(EmpresaUtil.empresasSelecionadas(empresaId, empresasPermitidas), LongUtil.arrayStringToArrayLong(cursosCheck), LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), situacao);
				parametros.put("SEM_TREINAMENTO", true);
			} else{
				colaboradorTurmas = colaboradorTurmaManager.findRelatorioSemTreinamentoAprovadosOrReprovados(EmpresaUtil.empresasSelecionadas(empresaId, empresasPermitidas), LongUtil.arrayStringToArrayLong(cursosCheck), LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), qtdMesesSemCurso, situacao, status);
				parametros.put("SEM_TREINAMENTO", false);
			}

			return  Action.SUCCESS;
		}catch (FortesException e){
			addActionWarning(e.getMessage());
			prepareRelatorioColaborador();

			return Action.INPUT;
		}catch (ColecaoVaziaException e){
			addActionMessage(e.getMessage());
			prepareRelatorioColaborador();
			
			return Action.INPUT;
		}catch (Exception e){
			addActionError("Erro ao gerar relatório");
			e.printStackTrace();
			prepareRelatorioColaborador();

			return Action.INPUT;
		}
	}

	public String relatorioColaboradorComTreinamentoXls(){
		
		if(relatorioColaboradorComTreinamento().equals(Action.INPUT))
			return Action.INPUT;	
		
		reportTitle = "Colaboradores que fizeram um treinamento ";
		reportFilter = "Emitido em: " + DateUtil.formataDiaMesAno(new Date()) + "\n";
		if(exibeTotalCargaHorariaEfetiva){
		    reportFilter += "Total Geral de Horas Efetivas:  " +calcularAndFormatarTotalCargaHorariaEfetiva() + "\n";
		    
		}

		dinamicColumns = "Curso,Carga Horária do Curso,Empresa,Estabelecimento,Área Organizacional,Colaborador,Matrícula,";
		dinamicPropertiesGroup = "curso.nome,curso.cargaHorariaEmHora,colaborador.empresa.nome,colaborador.estabelecimento.nome,colaborador.areaOrganizacional.descricao";
		dinamicProperties = "curso.nome,curso.cargaHorariaEmHora,colaborador.empresa.nome,colaborador.estabelecimento.nome,colaborador.areaOrganizacional.descricao,colaborador.nome,colaborador.matricula,";
							 
		if(exibeCargo){
			dinamicColumns += "Cargo,";
			dinamicProperties  += "colaborador.cargoFaixa,";
		}
							
		dinamicColumns += "Turma,Período,";
		dinamicProperties  += "turma.descricao,turma.periodoFormatado,";

		if(exibeCargaHorariaEfetiva){
			dinamicColumns += "Carga Horária Efetiva,";
			dinamicProperties  += "cargaHorariaEfetiva,";
		}
		
		dinamicColumns += "Aprovado";
		dinamicProperties  += "aprovadoMaisNota";
		
		return Action.SUCCESS;
	}
	
	private String calcularAndFormatarTotalCargaHorariaEfetiva() {
        Date dataBase = DateUtil.criarDataMesAno(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataBase);

        DateTime cargaHorariaEfetiva;

        for (ColaboradorTurma colaboradorTurma : colaboradorTurmas) {
            if (colaboradorTurma.getCargaHorariaEfetivaTime() != null) {
                cargaHorariaEfetiva = new DateTime(colaboradorTurma.getCargaHorariaEfetivaTime().getTime());
                calendar.add(Calendar.HOUR, cargaHorariaEfetiva.getHourOfDay());
                calendar.add(Calendar.MINUTE, cargaHorariaEfetiva.getMinuteOfHour());
                calendar.add(Calendar.SECOND, cargaHorariaEfetiva.getSecondOfMinute());
            }
        }

        Long dataFim = calendar.getTime().getTime();
        
        Period periodo= new Period(dataBase.getTime(), dataFim, PeriodType.yearMonthDayTime());
        int horas = new Period(dataBase.getTime(), dataFim, PeriodType.hours()).getHours();
        
        return (horas<10 ? "0" + horas: horas)  + ":" + (periodo.getMinutes()<10 ? "0" + periodo.getMinutes(): periodo.getMinutes()) + ":" + (periodo.getSeconds() < 10 ? "0" + periodo.getSeconds() : periodo.getSeconds());
    }

    public String relatorioColaboradorComTreinamento(){
		try{
			empresaId = empresaManager.ajustaCombo(empresaId, getEmpresaSistema().getId());
			
			colaboradorTurmas = colaboradorTurmaManager.findRelatorioComTreinamento(empresaId, LongUtil.arrayStringToArrayLong(cursosCheck), LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), dataIni, dataFim, aprovado, situacao);
			if (colaboradorTurmas == null || colaboradorTurmas.isEmpty())
				throw new ColecaoVaziaException();

			parametros = RelatorioUtil.getParametrosRelatorio("Colaboradores que fizeram o treinamento", getEmpresaSistema(), "");

			String retorno = Action.SUCCESS;
			
			if(exibeTotalCargaHorariaEfetiva){ 
                parametros.put("EXIBIR_CARGO", exibeCargo);
                retorno += "ExibirTotalCargaHorariaEfetiva";
            }else{			
    			if(exibeCargaHorariaEfetiva)
    				retorno += "ExibirHoraEfetiva";
    
    			if (exibeCargo) 
    				retorno += "ExibirCargos";
            }
			
			
			return retorno;
		}catch (ColecaoVaziaException e){
			addActionMessage(e.getMessage());
			prepareRelatorioColaborador();

			return Action.INPUT;
		}catch (Exception e){
			addActionError("Erro ao gerar relatório.");
			e.printStackTrace();
			prepareRelatorioColaborador();

			return Action.INPUT;
		}
	}

	public String relatorioColaboradorSemIndicacaoTreinamento()
	{
		Long[] areaIds={}, estabelecimentoIds={};

		if (areasCheck != null)
			areaIds = LongUtil.arrayStringToArrayLong(areasCheck);

		if (estabelecimentosCheck != null)
			estabelecimentoIds = LongUtil.arrayStringToArrayLong(estabelecimentosCheck);

		try{
			colaboradorTurmas = new ArrayList<ColaboradorTurma>();
			colaboradorTurmas = colaboradorTurmaManager.findRelatorioSemIndicacaoTreinamento(getEmpresaSistema().getId(), areaIds, estabelecimentoIds, qtdMesesSemCurso);
			parametros = RelatorioUtil.getParametrosRelatorio("Colaboradores Sem Indicação de Treinamento", getEmpresaSistema(), "Não fazem turmas/treinamentos há mais de " + qtdMesesSemCurso + " mês(es)");

			return Action.SUCCESS;

		} catch (ColecaoVaziaException e) {
			addActionMessage(e.getMessage());
			prepareRelatorioColaboradorSemIndicacao();

			return Action.INPUT;
		}
	}

	public String prepareRelatorioColaboradorSemIndicacao()
	{
		prepareRelatorioColaborador();
		
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(getEmpresaSistema().getId());
		
		return Action.SUCCESS;
	}
	
	public String prepareRelatorioColaboradorCertificacao()
	{
		certificacoes = certificacaoManager.findAllSelect(getEmpresaSistema().getId());
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(getEmpresaSistema().getId());
		return prepareRelatorioColaborador();
	}
	
	public String relatorioColaboradorCertificacao()
	{
		try
		{
			String retorno = SUCCESS;
			if (filtroAgrupamento == '1') {
				retorno = "successAgrupadoPorCurso";
			}
			
			String periodo = "Período: " + DateUtil.formataDiaMesAno(dataIni) + " até " + DateUtil.formataDiaMesAno(dataFim);
			reportTitle = "Colaboradores x Certificações";
			reportFilter = "Emitido em: " + DateUtil.formataDiaMesAno(new Date()) + "\n" + periodo;
			
			colaboradoresCertificacoes = colaboradorTurmaManager.montaRelatorioColaboradorCertificacao(getEmpresaSistema().getId(), certificacao, LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), dataIni, dataFim, filtroAgrupamento);
			parametros = RelatorioUtil.getParametrosRelatorio(reportTitle, getEmpresaSistema(), "Certificação: " + certificacao.getNome() + "\n" + periodo);
			
			return retorno;
		}
		catch (ColecaoVaziaException e)
		{
			addActionMessage(e.getMessage());
			prepareRelatorioColaboradorCertificacao();
			
			return Action.INPUT;
		}
		catch (Exception e)
		{
			addActionError("Erro ao gerar relatório.");
			e.printStackTrace();
			prepareRelatorioColaboradorCertificacao();

			return Action.INPUT;
		}

	}
	
	public String findNotas()
	{
		json = StringUtil.toJSON(aproveitamentoAvaliacaoCursoManager.findByColaboradorCurso(colaboradorId, cursoId), null);
		return SUCCESS;
	}
	
	public String InserirColaboradorNaLnt(){
		try {
			colaboradorTurmaManager.updateCursoLnt(colaboradorTurma.getCurso().getId(), colaboradorTurma.getId(), lntId);
			list();
			addActionSuccess("Colaborador vinculado a LNT com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Não foi possível o colaborador a LNT.");
		}
		return Action.SUCCESS;
	}

	public Collection<ColaboradorTurma> getColaboradorTurmas()
	{
		return colaboradorTurmas;
	}

	public ColaboradorTurma getColaboradorTurma()
	{
		if(colaboradorTurma == null)
			colaboradorTurma = new ColaboradorTurma();
		return colaboradorTurma;
	}

	public void setColaboradorTurma(ColaboradorTurma colaboradorTurma)
	{
		this.colaboradorTurma=colaboradorTurma;
	}

	public void setColaboradorTurmaManager(ColaboradorTurmaManager colaboradorTurmaManager)
	{
		this.colaboradorTurmaManager=colaboradorTurmaManager;
	}

	public Turma getTurma()
	{
		return turma;
	}

	public void setTurma(Turma turma)
	{
		this.turma = turma;
	}

	public Curso getCurso()
	{
		return curso;
	}

	public void setCurso(Curso curso)
	{
		this.curso = curso;
	}

	public Long getAreaFiltroId()
	{
		return areaFiltroId;
	}

	public void setAreaFiltroId(Long areaFiltroId)
	{
		this.areaFiltroId = areaFiltroId;
	}

	public Long getDntId()
	{
		return dntId;
	}

	public void setDntId(Long dntId)
	{
		this.dntId = dntId;
	}

	public boolean isGestor()
	{
		return gestor;
	}

	public void setGestor(boolean gestor)
	{
		this.gestor = gestor;
	}

	public Estabelecimento getEstabelecimento()
	{
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento)
	{
		this.estabelecimento = estabelecimento;
	}

	public Collection<PrioridadeTreinamento> getPrioridadeTreinamentos()
	{
		return prioridadeTreinamentos;
	}

	public void setPrioridadeTreinamentos(Collection<PrioridadeTreinamento> prioridadeTreinamentos)
	{
		this.prioridadeTreinamentos = prioridadeTreinamentos;
	}

	public void setTurmaManager(TurmaManager turmaManager)
	{
		this.turmaManager = turmaManager;
	}

	public FiltroPlanoTreinamento getFiltroPlanoTreinamento()
	{
		return filtroPlanoTreinamento;
	}

	public void setFiltroPlanoTreinamento(FiltroPlanoTreinamento filtroPlanoTreinamento)
	{
		this.filtroPlanoTreinamento = filtroPlanoTreinamento;
	}

	public char getAprovado()
	{
		return aprovado;
	}

	public void setAprovado(char aprovado)
	{
		this.aprovado = aprovado;
	}
	
	public String[] getCursosCheck() {
		return cursosCheck;
	}

	public void setCursosCheck(String[] cursosCheck) {
		this.cursosCheck = cursosCheck;
	}

	public String[] getAreasCheck()
	{
		return areasCheck;
	}

	public void setAreasCheck(String[] areasCheck)
	{
		this.areasCheck = areasCheck;
	}

	public boolean isComTreinamento()
	{
		return comTreinamento;
	}

	public void setComTreinamento(boolean comTreinamento)
	{
		this.comTreinamento = comTreinamento;
	}

	public String[] getEstabelecimentosCheck()
	{
		return estabelecimentosCheck;
	}

	public void setEstabelecimentosCheck(String[] estabelecimentosCheck)
	{
		this.estabelecimentosCheck = estabelecimentosCheck;
	}

	public Collection<CheckBox> getAreasCheckList()
	{
		return areasCheckList;
	}

	public Collection<CheckBox> getCursosCheckList()
	{
		return cursosCheckList;
	}

	public Collection<CheckBox> getEstabelecimentosCheckList()
	{
		return estabelecimentosCheckList;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public void setQtdMesesSemCurso(int qtdMesesSemCurso)
	{
		this.qtdMesesSemCurso = qtdMesesSemCurso;
	}

	public int getQtdMesesSemCurso()
	{
		return qtdMesesSemCurso;
	}

	public boolean isExibeFiltro()
	{
		return exibeFiltro;
	}

	public void setExibeFiltro(boolean exibeFiltro)
	{
		this.exibeFiltro = exibeFiltro;
	}

	public void setColaboradorQuestionarioManager(ColaboradorQuestionarioManager colaboradorQuestionarioManager)
	{
		this.colaboradorQuestionarioManager = colaboradorQuestionarioManager;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public int getFiltrarPor()
	{
		return filtrarPor;
	}

	public void setFiltrarPor(int filtrarPor)
	{
		this.filtrarPor = filtrarPor;
	}

	public String[] getCargosCheck()
	{
		return cargosCheck;
	}

	public void setCargosCheck(String[] cargosCheck)
	{
		this.cargosCheck = cargosCheck;
	}

	public String[] getGruposCheck()
	{
		return gruposCheck;
	}

	public void setGruposCheck(String[] gruposCheck)
	{
		this.gruposCheck = gruposCheck;
	}

	public Long getEmpresaId()
	{
		return empresaId;
	}

	public void setCargoManager(CargoManager cargoManager)
	{
		this.cargoManager = cargoManager;
	}

	public void setGrupoOcupacionalManager(GrupoOcupacionalManager grupoOcupacionalManager)
	{
		this.grupoOcupacionalManager = grupoOcupacionalManager;
	}

	public void setEmpresaId(Long empresaId)
	{
		this.empresaId = empresaId;
	}

	public boolean isImprimirMatriz()
	{
		return imprimirMatriz;
	}

	public void setImprimirMatriz(boolean imprimirMatriz)
	{
		this.imprimirMatriz = imprimirMatriz;
	}

	public Collection<CheckBox> getCargosCheckList()
	{
		return cargosCheckList;
	}

	public Collection<CheckBox> getGruposCheckList()
	{
		return gruposCheckList;
	}

	public void setCertificacaoManager(CertificacaoManager certificacaoManager)
	{
		this.certificacaoManager = certificacaoManager;
	}

	public char getFiltro()
	{
		return filtro;
	}

	public void setFiltro(char filtro)
	{
		this.filtro = filtro;
	}

	public Date getDataFim()
	{
		return dataFim;
	}

	public void setDataFim(Date dataFim)
	{
		this.dataFim = dataFim;
	}

	public Date getDataIni()
	{
		return dataIni;
	}

	public void setDataIni(Date dataIni)
	{
		this.dataIni = dataIni;
	}

	public Certificacao getCertificacao() {
		return certificacao;
	}

	public void setCertificacao(Certificacao certificacao) {
		this.certificacao = certificacao;
	}

	public Collection<ColaboradorCertificacaoRelatorio> getColaboradoresCertificacoes() {
		return colaboradoresCertificacoes;
	}

	public Collection<Certificacao> getCertificacoes() {
		return certificacoes;
	}

	public boolean isPlanoTreinamento() {
		return planoTreinamento;
	}

	public void setPlanoTreinamento(boolean planoTreinamento) {
		this.planoTreinamento = planoTreinamento;
	}

	public Collection<Colaborador> getColaboradors() {
		return colaboradors;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public void setColaboradorId(Long colaboradorId) {
		this.colaboradorId = colaboradorId;
	}

	public void setCursoId(Long cursoId) {
		this.cursoId = cursoId;
	}

	public String getJson() {
		return json;
	}

	public void setAproveitamentoAvaliacaoCursoManager(AproveitamentoAvaliacaoCursoManager aproveitamentoAvaliacaoCursoManager) {
		this.aproveitamentoAvaliacaoCursoManager = aproveitamentoAvaliacaoCursoManager;
	}

	public Collection<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public Boolean getCompartilharColaboradores() {
		return compartilharColaboradores;
	}

	public Collection<ColaboradorQuestionario> getColaboradorQuestionarios() {
		return colaboradorQuestionarios;
	}

	public String getNomeBusca() {
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca) {
		this.nomeBusca = nomeBusca;
	}

	public Long[] getColaboradoresCheck()
	{
		return colaboradoresCheck;
	}

	public void setColaboradoresCheck(Long[] colaboradoresCheck)
	{
		this.colaboradoresCheck = colaboradoresCheck;
	}

	public Collection<CheckBox> getColaboradoresCheckList()
	{
		return colaboradoresCheckList;
	}

	public String getReportTitle() {
		return reportTitle;
	}

	public Map<String, String> getSituacoes() {
		return situacoes;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getReportFilter() {
		return reportFilter;
	}

	public void setReportFilter(String reportFilter) {
		this.reportFilter = reportFilter;
	}

	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}

	public boolean isExibeCargo() {
		return exibeCargo;
	}

	public void setExibeCargo(boolean exibeCargo) {
		this.exibeCargo = exibeCargo;
	}

	public char getFiltroAgrupamento() {
		return filtroAgrupamento;
	}

	public void setFiltroAgrupamento(char filtroAgrupamento) {
		this.filtroAgrupamento = filtroAgrupamento;
	}
	
	public LinkedHashMap<Character, String> getStatusAprovacao(){
		return new StatusAprovacao();
	}

	public LinkedHashMap<String, String> getStatusTreinamento(){
		return new StatusTreinamento();
	}

	public String getDinamicColumns() {
		return dinamicColumns;
	}

	public String getDinamicProperties() {
		return dinamicProperties;
	}

	public String getDinamicPropertiesGroup() {
		return dinamicPropertiesGroup;
	}

	public boolean isExibeCargaHorariaEfetiva() {
		return exibeCargaHorariaEfetiva;
	}

	public void setExibeCargaHorariaEfetiva(boolean exibeCargaHorariaEfetiva) {
		this.exibeCargaHorariaEfetiva = exibeCargaHorariaEfetiva;
	}
	
	public boolean isExibeTotalCargaHorariaEfetiva() {
		return exibeTotalCargaHorariaEfetiva;
	}

	public void setExibeTotalCargaHorariaEfetiva(boolean exibeTotalCargaHorariaEfetiva) {
		this.exibeTotalCargaHorariaEfetiva = exibeTotalCargaHorariaEfetiva;
	}

	public Long getLntId() {
		return lntId;
	}

	public void setLntId(Long lntId) {
		this.lntId = lntId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setEmpresasPermitidas(Long[] empresasPermitidas) {
		this.empresasPermitidas = empresasPermitidas;
	}
}