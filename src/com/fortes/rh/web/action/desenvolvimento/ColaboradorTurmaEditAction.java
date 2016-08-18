package com.fortes.rh.web.action.desenvolvimento;

import static com.fortes.rh.util.CheckListBoxUtil.populaCheckListBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.desenvolvimento.AvaliacaoCursoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.desenvolvimento.DNTManager;
import com.fortes.rh.business.desenvolvimento.PrioridadeTreinamentoManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.DNT;
import com.fortes.rh.model.desenvolvimento.FiltroPlanoTreinamento;
import com.fortes.rh.model.desenvolvimento.PrioridadeTreinamento;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ModelDriven;

public class ColaboradorTurmaEditAction extends MyActionSupportEdit implements ModelDriven
{
	private static final long serialVersionUID = 1L;
	
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private ColaboradorManager colaboradorManager;
	private DNTManager dNTManager;
	private PrioridadeTreinamentoManager prioridadeTreinamentoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private CargoManager cargoManager;
	private TurmaManager turmaManager;
	private CursoManager cursoManager;
	private AvaliacaoCursoManager avaliacaoCursoManager;
	private EmpresaManager empresaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private EstabelecimentoManager estabelecimentoManager;

	private Collection<ColaboradorTurma> colaboradorTurmas;
	private Collection<PrioridadeTreinamento> prioridadeTreinamentos = null;
	private Collection<Curso> cursos;
	private Collection<Colaborador> colaboradors;
	private Collection<Turma> turmas = null;
	private Collection<Empresa> empresas = new ArrayList<Empresa>();
	private Collection<AvaliacaoCurso> avaliacaoCursos;

	private ColaboradorTurma colaboradorTurma;
	private Turma turma;
	private Curso curso;
	private Colaborador colaborador;
	private Long empresaId;
	private AvaliacaoTurma avaliacaoTurma;
	private Questionario questionario;

	private String[] areasCheck;
	private String[] gruposCheck;
	private String[] estabelecimentosCheck;
	private String[] cargosCheck;
	private String[] colaboradoresCursosCheck;
	
	private String[] selectPrioridades;
	private String[] colaboradorTurmaHidden;
	private String[] notas = new String[]{};
	
	private Long[] colaboradoresId;
	private Long[] colaboradoresTurmaId;
	private Long[] avaliacaoCursoIds;
	
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> gruposCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> cargosCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> colaboradoresCursosCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	

	private Long areaFiltroId;

	//area  = 1
	//grupo = 2
	//cargo = 3
	private int filtrarPor;

	private int page = 1;
	private static final int pagingSize = 10000;
	private Integer totalSize;

	private String msgAlert = "";
	private char filtro;

	private boolean gestor;
	private boolean exibeFiltro;
	
	private Date dataAdmissaoIni;
	private Date dataAdmissaoFim;

	// Indica se a requisição veio do plano de treinamento
	private boolean planoTreinamento;
	private FiltroPlanoTreinamento filtroPlanoTreinamento;
	private Boolean compartilharColaboradores;

	public void prepare() throws Exception
	{
		if(colaboradorTurma != null && colaboradorTurma.getId() != null) {
			colaboradorTurma = (ColaboradorTurma) colaboradorTurmaManager.findById(colaboradorTurma.getId());
			turma = colaboradorTurma.getTurma();
		}
	}
	
	public String prepareInsertNota() throws Exception
	{
		empresaId = empresaManager.ajustaCombo(empresaId, getEmpresaSistema().getId());
		populaEmpresas();
		
		avaliacaoCursos = avaliacaoCursoManager.findByCurso(turma.getCurso().getId());
		return SUCCESS;
	}
	
	public String insertColaboradorNota() throws Exception
	{
		try
		{
			colaboradorTurmaManager.saveColaboradorTurmaNota(turma, colaborador, avaliacaoCursoIds, notas, getEmpresaSistema().isControlarVencimentoPorCertificacao());
			addActionSuccess("Colaborador e notas inseridos com sucesso.");
		}
		catch (FortesException e)
		{
			e.printStackTrace();
			addActionWarning(e.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Ocorreu um erro ao inserir o colaborador e as notas.");
		}
		
		prepareInsertNota();
		return SUCCESS;
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		turma = turmaManager.findByIdProjection(turma.getId());
		colaboradorTurma.setTurma(turma);
		empresaId = empresaManager.ajustaCombo(empresaId, getEmpresaSistema().getId());
		
		populaEmpresas();
		
		Collection<Estabelecimento> estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		estabelecimentosCheckList = CheckListBoxUtil.populaCheckListBox(estabelecimentos, "getId", "getNome");
		estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);

		Collection<AreaOrganizacional> areaOrganizacionalsTmp = areaOrganizacionalManager.findAllSelectOrderDescricao(empresaId, AreaOrganizacional.TODAS, null, false);
		areasCheckList = populaCheckListBox(areaOrganizacionalsTmp, "getId", "getDescricaoStatusAtivo");
		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
		
		cargosCheckList = cargoManager.populaCheckBox(true, empresaId);
		cargosCheckList = CheckListBoxUtil.marcaCheckListBox(cargosCheckList, cargosCheck);

		Collection<ColaboradorTurma> colaboradorTurmas = colaboradorTurmaManager.findColaboradoresByCursoTurmaIsNull(turma.getCurso().getId());
		colaboradoresCursosCheckList = populaCheckListBox(colaboradorTurmaManager.getListaColaboradores(colaboradorTurmas), "getId", "getNome");
		colaboradoresCursosCheckList = CheckListBoxUtil.marcaCheckListBox(colaboradoresCursosCheckList, colaboradoresCursosCheck);

		return SUCCESS;
	}

	private void populaEmpresas() 
	{
		compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
		empresas = empresaManager.findEmpresasPermitidas(compartilharColaboradores , getEmpresaSistema().getId(), SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), new String[]{"ROLE_MOV_TURMA","ROLE_MOV_PLANO_TREINAMENTO"});
	}

	public String listFiltro() throws Exception
	{
		empresaId = empresaManager.ajustaCombo(empresaId, getEmpresaSistema().getId());
		totalSize = 10000;
		colaboradorTurmas = colaboradorTurmaManager.filtrarColaboradores(page, pagingSize, areasCheck, cargosCheck, estabelecimentosCheck, gruposCheck, colaboradoresCursosCheck, turma, colaborador, dataAdmissaoIni, dataAdmissaoFim, empresaId);
		colaboradorTurmas = colaboradorTurmaManager.setFamiliaAreas(colaboradorTurmas, empresaId);

		return prepareInsert();
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		turma = turmaManager.findByIdProjection(turma.getId());
		if(prioridadeTreinamentos == null || prioridadeTreinamentos.isEmpty()){
			addActionMessage("Para continuar é necessário cadastrar Prioridades de Treinamento para adicionar colaboradores.");
			return "list";
		}
		return Action.SUCCESS;
	}

	private void prepareDnt()
	{
		AreaOrganizacional areaOrganizacional = new AreaOrganizacional();
		areaOrganizacional.setId(areaFiltroId);
		colaboradors = colaboradorManager.findByArea(areaOrganizacional);

		if(colaboradorTurma != null)
		{
			if (colaboradorTurma.getId() != null)
				colaboradorTurma = colaboradorTurmaManager.findById(colaboradorTurma.getId());
			if (colaboradorTurma.getCurso() != null && colaboradorTurma.getCurso().getId() != null)
				turmas = turmaManager.findAllSelect(colaboradorTurma.getCurso().getId());
		}
		cursos = cursoManager.find(new String[]{"empresa.id"}, new Object[]{getEmpresaSistema().getId()}, new String[]{"nome"});
		prioridadeTreinamentos = prioridadeTreinamentoManager.findAll();
	}

	public String prepareUpdateDnt() throws Exception
	{
		prepareDnt();
		return Action.SUCCESS;
	}

	public String prepareInsertDnt() throws Exception
	{
		prepareDnt();
		colaboradorTurma.setOrigemDnt(true);
		return Action.SUCCESS;
	}

	public String insert() throws Exception{
		if(colaboradoresId != null && colaboradoresId.length > 0){
			turma = turmaManager.findByIdProjection(turma.getId());

			Collection<ColaboradorTurma> colaboradoresTurmas = colaboradorTurmaManager.findByTurmaCurso(turma.getCurso().getId());
			DNT dnt = dNTManager.getUltimaDNT(getEmpresaSistema().getId());

			try{
				msgAlert = colaboradorTurmaManager.insereColaboradorTurmas(colaboradoresId, colaboradoresTurmas, turma, dnt, filtrarPor, selectPrioridades, getEmpresaSistema().isControlarVencimentoPorCertificacao());
			}catch (Exception e){
				msgAlert = "Erro ao inserir colaborador.";
			}
		}

		if(filtroPlanoTreinamento != null && filtroPlanoTreinamento.getDataIni() != null)
			return "successFiltroPlanoTreinamento";

		return Action.SUCCESS;
	}

	public String update() throws Exception{
		try{
			if(colaboradorTurmaHidden != null && colaboradorTurmaHidden.length > 0)
				colaboradorTurmaManager.saveUpdate(colaboradorTurmaHidden, selectPrioridades, getEmpresaSistema().isControlarVencimentoPorCertificacao());

			Collection<Long> aprovados = new HashSet<Long>();
			Collection<Long> reprovados = new HashSet<Long>();
			Collection<String> geral = new HashSet<String>();
			Collection<Long> marcados = new HashSet<Long>();

			if(colaboradorTurmaHidden!= null && colaboradorTurmaHidden.length > 0)
				geral = new CollectionUtil<String>().convertArrayToCollection(colaboradorTurmaHidden);
			if(colaboradoresTurmaId!= null && colaboradoresTurmaId.length > 0)
				marcados = new CollectionUtil<Long>().convertArrayToCollection(colaboradoresTurmaId);

				for (String idTmp : geral){
					if(marcados.contains(Long.parseLong(idTmp))){
						aprovados.add(Long.parseLong(idTmp));
						continue;
					}
					reprovados.add(Long.parseLong(idTmp));
				}

			if(aprovados.size() > 0)
				colaboradorTurmaManager.saveUpdate(aprovados,true);

			if(reprovados.size() > 0)
				colaboradorTurmaManager.saveUpdate(reprovados,false);

			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			msgAlert = "Não foi possível atualizar os colaboradores desta turma.";
			return Action.INPUT;
		}

	}

	public String updateDNT() throws Exception
	{
		colaboradorTurmaManager.update(colaboradorTurma);
		return Action.SUCCESS;
	}

	public String insertDNT() throws Exception
	{
		if (colaboradorTurmaManager.verifcaExisteNoCurso(colaborador, colaboradorTurma.getCurso(), colaboradorTurma.getDnt()))
		{
			addActionError("O Colaborador já está inscrito neste curso.");
			prepareDnt();
			return Action.INPUT;
		}

		colaboradorTurma.setColaborador(colaborador);
		colaboradorTurma.setOrigemDnt(true);
		colaboradorTurmaManager.save(colaboradorTurma);

		Long id = colaboradorTurma.getDnt().getId();
		colaboradorTurma = new ColaboradorTurma();
		colaboradorTurma.setDnt(new DNT());
		colaboradorTurma.getDnt().setId(id);

		addActionMessage("Colaborador incluido no Curso com sucesso!");
		prepareInsertDnt();
		return Action.SUCCESS;
	}
	
	public void setEstabelecimentoManager(
			EstabelecimentoManager estabelecimentoManager) {
		this.estabelecimentoManager = estabelecimentoManager;
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

	public Object getModel()
	{
		return getColaboradorTurma();
	}

	public void setColaboradorTurmaManager(ColaboradorTurmaManager colaboradorTurmaManager)
	{
		this.colaboradorTurmaManager=colaboradorTurmaManager;
	}

	public void setPrioridadeTreinamentoManager(PrioridadeTreinamentoManager prioridadeTreinamentoManager)
	{
		this.prioridadeTreinamentoManager = prioridadeTreinamentoManager;
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

	public Collection<Turma> getTurmas()
	{
		return turmas;
	}

	public void setTurmas(Collection<Turma> turmas)
	{
		this.turmas = turmas;
	}

	public String[] getAreasCheck()
	{
		return areasCheck;
	}

	public void setAreasCheck(String[] areasCheck)
	{
		this.areasCheck = areasCheck;
	}

	public Collection<CheckBox> getAreasCheckList()
	{
		return areasCheckList;
	}

	public void setAreasCheckList(Collection<CheckBox> areasCheckList)
	{
		this.areasCheckList = areasCheckList;
	}

	public String[] getCargosCheck()
	{
		return cargosCheck;
	}

	public void setCargosCheck(String[] cargosCheck)
	{
		this.cargosCheck = cargosCheck;
	}

	public Collection<CheckBox> getCargosCheckList()
	{
		return cargosCheckList;
	}

	public void setCargosCheckList(Collection<CheckBox> cargosCheckList)
	{
		this.cargosCheckList = cargosCheckList;
	}

	public String[] getGruposCheck()
	{
		return gruposCheck;
	}

	public void setGruposCheck(String[] gruposCheck)
	{
		this.gruposCheck = gruposCheck;
	}

	public Collection<CheckBox> getGruposCheckList()
	{
		return gruposCheckList;
	}

	public void setGruposCheckList(Collection<CheckBox> gruposCheckList)
	{
		this.gruposCheckList = gruposCheckList;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setCargoManager(CargoManager cargoManager)
	{
		this.cargoManager = cargoManager;
	}

	public Collection<ColaboradorTurma> getColaboradorTurmas()
	{
		return colaboradorTurmas;
	}

	public void setColaboradorTurmas(Collection<ColaboradorTurma> colaboradorTurmas)
	{
		this.colaboradorTurmas = colaboradorTurmas;
	}

	public String[] getColaboradorTurmaHidden()
	{
		return colaboradorTurmaHidden;
	}

	public void setColaboradorTurmaHidden(String[] colaboradorTurmaHidden)
	{
		this.colaboradorTurmaHidden = colaboradorTurmaHidden;
	}

	public Long[] getColaboradoresId()
	{
		return colaboradoresId;
	}

	public void setColaboradoresId(Long[] colaboradoresId)
	{
		this.colaboradoresId = colaboradoresId;
	}

	public String[] getSelectPrioridades()
	{
		return selectPrioridades;
	}

	public void setSelectPrioridades(String[] selectPrioridades)
	{
		this.selectPrioridades = selectPrioridades;
	}

	public int getFiltrarPor()
	{
		return filtrarPor;
	}

	public void setFiltrarPor(int filtrarPor)
	{
		this.filtrarPor = filtrarPor;
	}

	public Turma getTurma()
	{
		return turma;
	}

	public void setTurma(Turma turma)
	{
		this.turma = turma;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPagingSize() {
		return pagingSize;
	}

	public Integer getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}

	public Collection<Empresa> getEmpresas()
	{
		return empresas;
	}

	public void setEmpresas(Collection<Empresa> empresas)
	{
		this.empresas = empresas;
	}

	public Collection<CheckBox> getColaboradoresCursosCheckList()
	{
		return colaboradoresCursosCheckList;
	}

	public void setColaboradoresCursosCheckList(Collection<CheckBox> colaboradoresCursosCheckList)
	{
		this.colaboradoresCursosCheckList = colaboradoresCursosCheckList;
	}

	public String[] getColaboradoresCursosCheck()
	{
		return colaboradoresCursosCheck;
	}

	public void setColaboradoresCursosCheck(String[] colaboradoresCursosCheck)
	{
		this.colaboradoresCursosCheck = colaboradoresCursosCheck;
	}

	public String getMsgAlert()
	{
		return msgAlert;
	}

	public void setMsgAlert(String msgAlert)
	{
		this.msgAlert = msgAlert;
	}

	public void setDNTManager(DNTManager manager)
	{
		dNTManager = manager;
	}

	public Long getAreaFiltroId()
	{
		return areaFiltroId;
	}

	public void setAreaFiltroId(Long areaFiltroId)
	{
		this.areaFiltroId = areaFiltroId;
	}

	public Collection<Colaborador> getColaboradors()
	{
		return colaboradors;
	}

	public void setColaboradors(Collection<Colaborador> colaboradors)
	{
		this.colaboradors = colaboradors;
	}

	public Collection<Curso> getCursos()
	{
		return cursos;
	}

	public void setCursos(Collection<Curso> cursos)
	{
		this.cursos = cursos;
	}

	public void setCursoManager(CursoManager cursoManager)
	{
		this.cursoManager = cursoManager;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public boolean isGestor()
	{
		return gestor;
	}

	public void setGestor(boolean gestor)
	{
		this.gestor = gestor;
	}

	public Curso getCurso()
	{
		return curso;
	}

	public void setCurso(Curso curso)
	{
		this.curso = curso;
	}

	public Long[] getColaboradoresTurmaId()
	{
		return colaboradoresTurmaId;
	}

	public void setColaboradoresTurmaId(Long[] colaboradoresTurmaId)
	{
		this.colaboradoresTurmaId = colaboradoresTurmaId;
	}

	public Long getEmpresaId()
	{
		return empresaId;
	}

	public void setEmpresaId(Long empresaId)
	{
		this.empresaId = empresaId;
	}

	public char getFiltro()
	{
		return filtro;
	}

	public void setFiltro(char filtro)
	{
		this.filtro = filtro;
	}

	public FiltroPlanoTreinamento getFiltroPlanoTreinamento()
	{
		return filtroPlanoTreinamento;
	}

	public void setFiltroPlanoTreinamento(FiltroPlanoTreinamento filtroPlanoTreinamento)
	{
		this.filtroPlanoTreinamento = filtroPlanoTreinamento;
	}

	public boolean isExibeFiltro()
	{
		return exibeFiltro;
	}

	public void setExibeFiltro(boolean exibeFiltro)
	{
		this.exibeFiltro = exibeFiltro;
	}

	public Questionario getQuestionario()
	{
		return questionario;
	}

	public void setQuestionario(Questionario questionario)
	{
		this.questionario = questionario;
	}

	public AvaliacaoTurma getAvaliacaoTurma()
	{
		return avaliacaoTurma;
	}

	public void setAvaliacaoTurma(AvaliacaoTurma avaliacaoTurma)
	{
		this.avaliacaoTurma = avaliacaoTurma;
	}
	
	public void setAvaliacaoCursoManager(AvaliacaoCursoManager avaliacaoCursoManager)
	{
		this.avaliacaoCursoManager = avaliacaoCursoManager;
	}

	public Collection<AvaliacaoCurso> getAvaliacaoCursos()
	{
		return avaliacaoCursos;
	}

	public Long[] getAvaliacaoCursoIds()
	{
		return avaliacaoCursoIds;
	}

	public void setAvaliacaoCursoIds(Long[] avaliacaoCursoIds)
	{
		this.avaliacaoCursoIds = avaliacaoCursoIds;
	}

	public String[] getNotas()
	{
		return notas;
	}

	public void setNotas(String[] notas)
	{
		this.notas = notas;
	}

	public boolean isPlanoTreinamento() {
		return planoTreinamento;
	}

	public void setPlanoTreinamento(boolean planoTreinamento) {
		this.planoTreinamento = planoTreinamento;
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

	public Date getDataAdmissaoIni() {
		return dataAdmissaoIni;
	}

	public void setDataAdmissaoIni(Date dataAdmissaoIni) {
		this.dataAdmissaoIni = dataAdmissaoIni;
	}

	public Date getDataAdmissaoFim() {
		return dataAdmissaoFim;
	}

	public void setDataAdmissaoFim(Date dataAdmissaoFim) {
		this.dataAdmissaoFim = dataAdmissaoFim;
	}

	public void setEstabelecimentosCheck(String[] estabelecimentosCheck) {
		this.estabelecimentosCheck = estabelecimentosCheck;
	}

	public void setEstabelecimentosCheckList(
			Collection<CheckBox> estabelecimentosCheckList) {
		this.estabelecimentosCheckList = estabelecimentosCheckList;
	}

	public EstabelecimentoManager getEstabelecimentoManager() {
		return estabelecimentoManager;
	}

	public String[] getEstabelecimentosCheck() {
		return estabelecimentosCheck;
	}

	public Collection<CheckBox> getEstabelecimentosCheckList() {
		return estabelecimentosCheckList;
	}
}