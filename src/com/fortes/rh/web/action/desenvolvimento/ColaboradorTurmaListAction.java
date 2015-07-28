package com.fortes.rh.web.action.desenvolvimento;

import static com.fortes.rh.util.CheckListBoxUtil.populaCheckListBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.business.desenvolvimento.AproveitamentoAvaliacaoCursoManager;
import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.exception.ColecaoVaziaException;
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
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
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
	private CursoManager cursoManager;
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
	private Long areaFiltroId;

	private boolean gestor;
	private String msgAlert;
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
	
	//relatório de Colaboradores x Certificações
	private Collection<ColaboradorCertificacaoRelatorio> colaboradoresCertificacoes;
	private Certificacao certificacao;
	private Collection<Certificacao> certificacoes;
	private Boolean compartilharColaboradores;
	
	private Map<String,String> situacoes = new SituacaoColaborador();
	private String situacao;
	
	private boolean exibeCargo;

	public String getMsgAlert()
	{
		return msgAlert;
	}

	public void setMsgAlert(String msgAlert)
	{
		this.msgAlert = msgAlert;
	}

	public String list() throws Exception
	{
		if(msgAlert != null && !msgAlert.equals(""))
			addActionMessage(msgAlert);

		empresaId = empresaManager.ajustaCombo(empresaId, getEmpresaSistema().getId());
		populaEmpresa(new String[]{"ROLE_MOV_TURMA","ROLE_MOV_PLANO_TREINAMENTO"});	
		if(empresaId != null){
			estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(empresaId);
		}else{
			estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(new CollectionUtil<Empresa>().convertCollectionToArrayIds(empresas));
		}
		
		estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);
		turma = turmaManager.findById(turma.getId()); // precisa da colecao de avaliacaoTurmas
		
		setTotalSize(colaboradorTurmaManager.getCount(turma.getId(), empresaId, nomeBusca, LongUtil.arrayStringToArrayLong(estabelecimentosCheck), null));
		colaboradorTurmas = colaboradorTurmaManager.findByTurmaColaborador(turma.getId(), empresaId, nomeBusca, LongUtil.arrayStringToArrayLong(estabelecimentosCheck), null, getPage(), getPagingSize());
		colaboradorTurmas = colaboradorTurmaManager.setFamiliaAreas(colaboradorTurmas, empresaId);
		
		if(aprovado == 'S' || aprovado == 'N')
		{
			colaboradorTurmas = colaboradorTurmaManager.filtraAprovadoReprovado(colaboradorTurmas, aprovado);
			setTotalSize((colaboradorTurmaManager.filtraAprovadoReprovado(colaboradorTurmaManager.findByTurmaColaborador(turma.getId(), empresaId, nomeBusca, LongUtil.arrayStringToArrayLong(estabelecimentosCheck), null, null, null), aprovado)).size());
		}
		
		colaboradorQuestionarios = colaboradorQuestionarioManager.findRespondidasByColaboradorETurma(null, turma.getId(), empresaId);
		

		return Action.SUCCESS;
	}

	private void populaEmpresa(String... roles)
	{
		compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
		empresas = empresaManager.findEmpresasPermitidas(compartilharColaboradores , getEmpresaSistema().getId(), SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), roles);
	}

	public String prepareFiltroHistoricoTreinamentos() throws Exception
	{
		empresaId = getEmpresaSistema().getId();
				
		Collection<AreaOrganizacional> areaOrganizacionalsTmp = areaOrganizacionalManager.findAllSelectOrderDescricao(getEmpresaSistema().getId(), AreaOrganizacional.TODAS, null);
		areasCheckList = populaCheckListBox(areaOrganizacionalsTmp, "getId", "getDescricaoStatusAtivo");
		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);

		cargosCheckList = populaCheckListBox(cargoManager.findAllSelect(getEmpresaSistema().getId(), "nome", null, Cargo.TODOS), "getId", "getNome");
		cargosCheckList = CheckListBoxUtil.marcaCheckListBox(cargosCheckList, cargosCheck);
		gruposCheckList = populaCheckListBox(grupoOcupacionalManager.findAllSelect(getEmpresaSistema().getId()), "getId", "getNome");
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
		colaboradoresCheckList = populaCheckListBox(colaboradors, "getId", "getNome");

		
		return prepareFiltroHistoricoTreinamentos();
	}

	public String relatorioHistoricoTreinamentos()
	{
		try
		{
			colaboradorTurmas = colaboradorTurmaManager.findRelatorioHistoricoTreinamentos(getEmpresaSistema().getId(), colaboradoresCheck, dataIni, dataFim);
			parametros = RelatorioUtil.getParametrosRelatorio("Histórico de Treinamentos", getEmpresaSistema(), null);
			
			String[] faixaSalarialId;
			if(colaboradorTurmas != null && !colaboradorTurmas.isEmpty()){
				for (ColaboradorTurma colaboradorTurma : colaboradorTurmas) {
					
					faixaSalarialId = new String[]{colaboradorTurma.getColaborador().getFaixaSalarial().getId().toString()};
					
					Collection<MatrizTreinamento> matrizTreinamentos = certificacaoManager.montaMatriz(imprimirMatriz, faixaSalarialId, colaboradorTurmas);
					
					colaboradorTurma.setMatrizTreinamentos(matrizTreinamentos);
				}
			} else {
				
				for (Long colaboradorId : colaboradoresCheck) {

					colaborador = colaboradorManager.findByIdComHistorico(colaboradorId);
					colaborador.setFaixaSalarial(colaborador.getHistoricoColaborador().getFaixaSalarial());
					
					ColaboradorTurma colaboradorTurma = new ColaboradorTurma();
					colaboradorTurma.setColaborador(colaborador);
					colaboradorTurma.setMatrizTreinamentos(null);
					colaboradorTurmas.add(colaboradorTurma);
				}
			}
			parametros.put("IMPRIMIR_MATRIZ", imprimirMatriz);

			return SUCCESS;
		}
		catch (ColecaoVaziaException e)
		{
			addActionMessage(e.getMessage());
			prepareRelatorioColaborador();
			areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
			estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);
			cargosCheckList = CheckListBoxUtil.marcaCheckListBox(cargosCheckList, cargosCheck);

			return INPUT;
		}
		catch (Exception e)
		{
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
		}
		catch (Exception e)
		{
			addActionError("Não é possível remover o colaborador da turma, pois este já possui presença(s) registrada(s).");
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
		try
		{
			empresaId = empresaManager.ajustaCombo(empresaId, getEmpresaSistema().getId());
			
			colaboradorTurmas = colaboradorTurmaManager.findRelatorioSemTreinamento(empresaId, LongUtil.arrayStringToArrayLong(cursosCheck), LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), qtdMesesSemCurso);
			
			parametros = RelatorioUtil.getParametrosRelatorio("Colaboradores que não fizeram o treinamento", getEmpresaSistema(), "");

			return Action.SUCCESS;
		}
		catch (ColecaoVaziaException e)
		{
			addActionMessage(e.getMessage());
			prepareRelatorioColaborador();

			return Action.INPUT;
		}
		catch (Exception e)
		{
			addActionError("Erro ao gerar relatório");
			e.printStackTrace();
			prepareRelatorioColaborador();

			return Action.INPUT;
		}
	}

	public String relatorioColaboradorComTreinamento()
	{
		try
		{
			empresaId = empresaManager.ajustaCombo(empresaId, getEmpresaSistema().getId());
			
			colaboradorTurmas = colaboradorTurmaManager.findRelatorioComTreinamento(empresaId, LongUtil.arrayStringToArrayLong(cursosCheck), LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), dataIni, dataFim, aprovado, situacao);
			parametros = RelatorioUtil.getParametrosRelatorio("Colaboradores que fizeram o treinamento", getEmpresaSistema(), "");
			
			reportTitle = "Colaboradores que fizeram um treinamento ";

			String retorno = Action.SUCCESS;
			if (exibeCargo) {
				retorno += "ExibirCargos";
			}
			
			return retorno;
		}
		catch (ColecaoVaziaException e)
		{
			addActionMessage(e.getMessage());
			prepareRelatorioColaborador();

			return Action.INPUT;
		}
		catch (Exception e)
		{
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

		try
		{
			colaboradorTurmas = new ArrayList<ColaboradorTurma>();
			colaboradorTurmas = colaboradorTurmaManager.findRelatorioSemIndicacaoTreinamento(getEmpresaSistema().getId(), areaIds, estabelecimentoIds, qtdMesesSemCurso);
			parametros = RelatorioUtil.getParametrosRelatorio("Colaboradores Sem Indicação de Treinamento", getEmpresaSistema(), "Não fazem turmas/treinamentos há mais de " + qtdMesesSemCurso + " mês(es)");

			return Action.SUCCESS;

		} catch (ColecaoVaziaException e)
		{
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
			String periodo = "Período: " + DateUtil.formataDiaMesAno(dataIni) + " até " + DateUtil.formataDiaMesAno(dataFim);
			reportTitle = "Colaboradores x Certificações";
			reportFilter = "Emitido em: " + DateUtil.formataDiaMesAno(new Date()) + "\n" + periodo;
			
			colaboradoresCertificacoes = colaboradorTurmaManager.montaRelatorioColaboradorCertificacao(getEmpresaSistema().getId(), certificacao, LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), dataIni, dataFim);
			parametros = RelatorioUtil.getParametrosRelatorio(reportTitle, getEmpresaSistema(), "Certificação: " + certificacao.getNome() + "\n" + periodo);
			
			return SUCCESS;
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

	public void setCursoManager(CursoManager cursoManager)
	{
		this.cursoManager = cursoManager;
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
}