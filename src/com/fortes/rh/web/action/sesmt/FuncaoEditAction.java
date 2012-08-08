package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.business.sesmt.HistoricoFuncaoManager;
import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.RiscoFuncao;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

@SuppressWarnings({"serial"})
public class FuncaoEditAction extends MyActionSupportEdit
{
	private FuncaoManager funcaoManager;
	private CargoManager cargoManager;
	private HistoricoFuncaoManager historicoFuncaoManager;
	private ExameManager exameManager;
	private EpiManager epiManager;
	private RiscoManager riscoManager;

	private Funcao funcao;
	private Cargo cargoTmp;
	private HistoricoFuncao historicoFuncao;

	private Collection<Cargo> cargos = new ArrayList<Cargo>();
	private Collection<HistoricoFuncao> historicoFuncaos = new ArrayList<HistoricoFuncao>();

    private Collection<Ambiente> ambientes;
    private AmbienteManager ambienteManager;

	private Collection<RiscoFuncao> riscosFuncoes;

	private String[] riscoChecks;

    
    private Colaborador colaborador;
    private ColaboradorManager colaboradorManager;
    private HistoricoColaborador historicoColaborador;
    private HistoricoColaboradorManager historicoColaboradorManager;
    private Collection<Funcao> funcaos = new ArrayList<Funcao>();

    private AreaOrganizacional areaBusca;
    private String nomeBusca;
    private int page;
    private boolean veioDoSESMT;

	private Long[] examesChecked;
	private Collection<CheckBox> examesCheckList = new HashSet<CheckBox>();
	private Long[] episChecked;
	private Collection<CheckBox> episCheckList = new HashSet<CheckBox>();

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(funcao != null && funcao.getId() != null)
			funcao = funcaoManager.findByIdProjection(funcao.getId());

		Collection<Exame> exames = exameManager.findAllSelect(getEmpresaSistema().getId());
		examesCheckList = CheckListBoxUtil.populaCheckListBox(exames, "getId", "getNome");
		episCheckList = epiManager.populaCheckToEpi(getEmpresaSistema().getId(), true);
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		
		cargoTmp = cargoManager.findByIdProjection(cargoTmp.getId());
		
		riscosFuncoes = riscoManager.findRiscosFuncoesByEmpresa(getEmpresaSistema().getId());

		return Action.SUCCESS;
	}

	public String prepareInsertFiltro() throws Exception
	{
		veioDoSESMT = true;
		prepareInsert();
		
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		cargoTmp = cargoManager.findByIdProjection(cargoTmp.getId());
		historicoFuncaos = historicoFuncaoManager.findToList(new String[]{"id","descricao","data"}, new String[]{"id","descricao","data"}, new String[]{"funcao.id"}, new Object[]{funcao.getId()}, new String[]{"data desc"});

		return Action.SUCCESS;
	}

	public String prepareUpdateFiltro() throws Exception
	{
		veioDoSESMT = true;
		prepareUpdate();
		
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		funcao.setCargo(cargoTmp);
		historicoFuncaoManager.saveFuncaoHistorico(funcao, historicoFuncao, examesChecked, episChecked, riscoChecks, riscosFuncoes, getEmpresaSistema().getControlaRiscoPor());

		if(veioDoSESMT)
			return "SUCESSO_VEIO_SESMT";
		else
			return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		cargoTmp = cargoManager.findByIdProjection(cargoTmp.getId());
		funcao.setCargo(cargoTmp);
		funcaoManager.update(funcao);

		if(veioDoSESMT)
			return "SUCESSO_VEIO_SESMT";
		else
			return Action.SUCCESS;
	}

	public String prepareMudancaFuncao()
	{
		colaborador = colaboradorManager.findColaboradorById(colaborador.getId());

		if(!colaborador.getEmpresa().getId().equals(getEmpresaSistema().getId()))
		{
			addActionError("O colaborador solicitado não existe na empresa " + getEmpresaSistema().getNome());
			return Action.ERROR;
		}

		historicoColaborador = historicoColaboradorManager.getHistoricoAtual(colaborador.getId());
		historicoColaborador.setData(new Date());

		funcaos = funcaoManager.findByCargo(historicoColaborador.getFaixaSalarial().getCargo().getId());
		ambientes = ambienteManager.findAmbientes(getEmpresaSistema().getId());

		return SUCCESS;
	}

	public String mudaFuncao()
	{
		HistoricoColaborador historico = historicoColaboradorManager.getHistoricoAtual(colaborador.getId());

		historicoColaborador.setColaboradorId(colaborador.getId());
		if(historicoColaboradorManager.existeHistoricoData(historicoColaborador))
		{
			addActionError("Já existe histórico para este colaborador neste dia.");
			prepareMudancaFuncao();
			return Action.INPUT;
		}

		colaborador = historico.getColaborador();

		if(!colaborador.getEmpresa().getId().equals(getEmpresaSistema().getId()))
		{
			addActionError("O colaborador solicitado não existe na empresa " + getEmpresaSistema().getNome());
			prepareMudancaFuncao();
			return Action.INPUT;
		}

		HistoricoColaborador hist = (HistoricoColaborador) historico.clone();
		hist.setId(null);
		hist.setData(historicoColaborador.getData());
		hist.setHistoricoAnterior(historicoColaboradorManager.getHistoricoAnterior(hist));
		hist.setFuncao(historicoColaborador.getFuncao());
		hist.setGfip(historicoColaborador.getGfip());
		hist.setAmbiente(historicoColaborador.getAmbiente());
		hist.setMotivo(MotivoHistoricoColaborador.MUDANCA_FUNCAO);

		hist = historicoColaboradorManager.save(hist);
		historicoColaboradorManager.atualizaHistoricosImediatos(hist);

		return SUCCESS;
	}

	public Funcao getFuncao()
	{
		if(funcao == null)
			funcao = new Funcao();
		return funcao;
	}

	public void setFuncao(Funcao funcao)
	{
		this.funcao = funcao;
	}

	public void setFuncaoManager(FuncaoManager funcaoManager)
	{
		this.funcaoManager = funcaoManager;
	}

	public Collection<Cargo> getCargos()
	{
		return cargos;
	}

	public void setCargos(Collection<Cargo> cargos)
	{
		this.cargos = cargos;
	}

	public void setCargoManager(CargoManager cargoManager)
	{
		this.cargoManager = cargoManager;
	}

	public Cargo getCargoTmp()
	{
		return cargoTmp;
	}

	public void setCargoTmp(Cargo cargoTmp)
	{
		this.cargoTmp = cargoTmp;
	}

	public void setHistoricoFuncaoManager(HistoricoFuncaoManager historicoFuncaoManager)
	{
		this.historicoFuncaoManager = historicoFuncaoManager;
	}

	public HistoricoFuncao getHistoricoFuncao()
	{
		return historicoFuncao;
	}

	public void setHistoricoFuncao(HistoricoFuncao historicoFuncao)
	{
		this.historicoFuncao = historicoFuncao;
	}

	public Collection<HistoricoFuncao> getHistoricoFuncaos()
	{
		return historicoFuncaos;
	}

	public void setHistoricoFuncaos(Collection<HistoricoFuncao> historicoFuncaos)
	{
		this.historicoFuncaos = historicoFuncaos;
	}

	public void setAmbienteManager(AmbienteManager ambienteManager)
	{
		this.ambienteManager = ambienteManager;
	}

	public Collection<Ambiente> getAmbientes()
	{
		return ambientes;
	}

	public void setAmbientes(Collection<Ambiente> ambientes)
	{
		this.ambientes = ambientes;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public HistoricoColaborador getHistoricoColaborador()
	{
		return historicoColaborador;
	}

	public void setHistoricoColaborador(HistoricoColaborador historicoColaborador)
	{
		this.historicoColaborador = historicoColaborador;
	}

	public void setHistoricoColaboradorManager(HistoricoColaboradorManager historicoColaboradorManager)
	{
		this.historicoColaboradorManager = historicoColaboradorManager;
	}
	public Collection<Funcao> getFuncaos()
	{
		return funcaos;
	}

	public void setFuncaos(Collection<Funcao> funcaos)
	{
		this.funcaos = funcaos;
	}
	public int getPage()
	{
		return page;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public AreaOrganizacional getAreaBusca()
	{
		return areaBusca;
	}

	public void setAreaBusca(AreaOrganizacional areaBusca)
	{
		this.areaBusca = areaBusca;
	}

	public String getNomeBusca()
	{
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca)
	{
		this.nomeBusca = StringUtil.retiraAcento(nomeBusca);
	}

	public Long[] getExamesChecked()
	{
		return examesChecked;
	}

	public void setExamesChecked(Long[] examesChecked)
	{
		this.examesChecked = examesChecked;
	}

	public Collection<CheckBox> getExamesCheckList()
	{
		return examesCheckList;
	}

	public void setExameManager(ExameManager exameManager)
	{
		this.exameManager = exameManager;
	}

	public void setEpiManager(EpiManager epiManager)
	{
		this.epiManager = epiManager;
	}

	public Collection<CheckBox> getEpisCheckList()
	{
		return episCheckList;
	}

	public void setEpisChecked(Long[] episChecked)
	{
		this.episChecked = episChecked;
	}

	public boolean isVeioDoSESMT()
	{
		return veioDoSESMT;
	}

	public void setVeioDoSESMT(boolean veioDoSESMT)
	{
		this.veioDoSESMT = veioDoSESMT;
	}

	public String[] getRiscoChecks() {
		return riscoChecks;
	}

	public void setRiscoChecks(String[] riscoChecks) {
		this.riscoChecks = riscoChecks;
	}

	public Collection<RiscoFuncao> getRiscosFuncoes() {
		return riscosFuncoes;
	}

	public void setRiscosFuncoes(Collection<RiscoFuncao> riscosFuncoes) {
		this.riscosFuncoes = riscosFuncoes;
	}

	public void setRiscoManager(RiscoManager riscoManager) {
		this.riscoManager = riscoManager;
	}


}