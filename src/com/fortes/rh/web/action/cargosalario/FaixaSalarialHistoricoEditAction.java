package com.fortes.rh.web.action.cargosalario;

import static com.fortes.rh.util.CheckListBoxUtil.populaCheckListBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupport;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class FaixaSalarialHistoricoEditAction extends MyActionSupport
{
	private static final long serialVersionUID = 1L;
	
	private FaixaSalarialHistoricoManager faixaSalarialHistoricoManager;
	private FaixaSalarialManager faixaSalarialManager;
	private IndiceManager indiceManager;
	private GrupoOcupacionalManager grupoOcupacionalManager;
	private CargoManager cargoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;

	private FaixaSalarial faixaSalarialAux;
	private FaixaSalarialHistorico faixaSalarialHistorico;
	private Map tipoAplicacaoIndices;
	private TipoAplicacaoIndice tipoAplicacaoIndice = new TipoAplicacaoIndice();

	private Collection<Indice> indices = new ArrayList<Indice>();
	private Collection<FaixaSalarialHistorico> faixaSalarialHistoricos;
	private Collection<CheckBox> grupoOcupacionalsCheckList = new ArrayList<CheckBox>();
	private Collection<Cargo> cargos = new ArrayList<Cargo>();
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> cargosCheckList = new ArrayList<CheckBox>();

	private String[] grupoOcupacionalsCheck;
	private String[] areasCheck;
	private String[] cargosCheck;
	private Character situacaoCargo; 
	
	private Long empresaId;

	private Date data;
	
	private Map<String, Object> parametros;
	
	private void prepare() throws Exception
	{
		if (faixaSalarialHistorico != null && faixaSalarialHistorico.getId() != null)
			faixaSalarialHistorico = (FaixaSalarialHistorico) faixaSalarialHistoricoManager.findByIdProjection(faixaSalarialHistorico.getId());

		faixaSalarialAux = faixaSalarialManager.findByFaixaSalarialId(faixaSalarialAux.getId());
		indices = indiceManager.findAll(getEmpresaSistema());

		tipoAplicacaoIndices = new TipoAplicacaoIndice();
		tipoAplicacaoIndices.remove(TipoAplicacaoIndice.CARGO);
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareRelatorioHistoricoFaixaSalarial() throws Exception
	{
		empresaId = getEmpresaSistema().getId();
		
		grupoOcupacionalsCheckList = grupoOcupacionalManager.populaCheckOrderNome(getEmpresaSistema().getId());
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		cargosCheckList = cargoManager.populaCheckBox(false, getEmpresaSistema().getId());
		
		grupoOcupacionalsCheckList = CheckListBoxUtil.marcaCheckListBox(grupoOcupacionalsCheckList, grupoOcupacionalsCheck);
		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
		cargosCheckList = CheckListBoxUtil.marcaCheckListBox(cargosCheckList, cargosCheck);
		
		return Action.SUCCESS;
	}

	public String relatorioHistoricoFaixaSalarial() throws Exception
	{
		faixaSalarialHistoricos = faixaSalarialHistoricoManager.findByGrupoCargoAreaData(grupoOcupacionalsCheck, cargosCheck, areasCheck, null, Boolean.FALSE, getEmpresaSistema().getId(), isCargoAtivo());
		parametros = RelatorioUtil.getParametrosRelatorio("Históricos das Faixas Salariais", getEmpresaSistema(), "");
		return Action.SUCCESS;
	}
	
	public String analiseTabelaSalarialFiltro() throws Exception
	{
		empresaId = getEmpresaSistema().getId();
		
		grupoOcupacionalsCheckList = grupoOcupacionalManager.populaCheckOrderNome(getEmpresaSistema().getId());
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		cargosCheckList = populaCheckListBox(cargoManager.findAllSelect(getEmpresaSistema().getId(), "nome", null, Cargo.TODOS), "getId", "getNomeMercadoComStatus");
		
		grupoOcupacionalsCheckList = CheckListBoxUtil.marcaCheckListBox(grupoOcupacionalsCheckList, grupoOcupacionalsCheck);
		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
		cargosCheckList = CheckListBoxUtil.marcaCheckListBox(cargosCheckList, cargosCheck);
		
		return Action.SUCCESS;
	}
	
	public String analiseTabelaSalarialList() throws Exception
	{
		try
		{
			faixaSalarialHistoricos = faixaSalarialHistoricoManager.findByGrupoCargoAreaData(grupoOcupacionalsCheck, cargosCheck, areasCheck, data, Boolean.TRUE, getEmpresaSistema().getId(), isCargoAtivo());
			cargos = cargoManager.getCargosFromFaixaSalarialHistoricos(faixaSalarialHistoricos);
		}
		catch (Exception e)
		{
			setActionMsg(e.getMessage());
			e.printStackTrace();
			
			analiseTabelaSalarialFiltro();

			return Action.INPUT;
		}

		return Action.SUCCESS;
	}

	public String relatorioAnaliseTabelaSalarial() throws Exception
	{
		analiseTabelaSalarialList();
		parametros = RelatorioUtil.getParametrosRelatorio("Análise de Tabela Salarial", getEmpresaSistema(), "Data: " + DateUtil.formataDiaMesAno(data));
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		if (faixaSalarialHistoricoManager.verifyData(faixaSalarialHistorico.getId(), faixaSalarialHistorico.getData(), faixaSalarialAux.getId()))
		{
			addActionMessage("Já existe um histórico com essa data.");
			prepareInsert();
			return Action.INPUT;
		}

		if (faixaSalarialHistorico.getTipo() == TipoAplicacaoIndice.getIndice())
		{
			if (!faixaSalarialHistoricoManager.verifyHistoricoIndiceNaData(faixaSalarialHistorico.getData(), faixaSalarialHistorico.getIndice().getId()))
			{
				addActionMessage("Não existe histórico para o índice selecionado nesta data.");
				prepareInsert();
				return Action.INPUT;
			}
		}

		try
		{
			faixaSalarialHistoricoManager.save(faixaSalarialHistorico, faixaSalarialAux, getEmpresaSistema(), true);			
		}
		catch (Exception e)
		{
			addActionError("O Histórico não pôde ser cadastrado.");
			faixaSalarialHistorico.setId(null);
			prepareInsert();
			return Action.INPUT;
		}

		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		if (faixaSalarialHistoricoManager.verifyData(faixaSalarialHistorico.getId(), faixaSalarialHistorico.getData(), faixaSalarialAux.getId()))
		{
			addActionMessage("Já existe um histórico com essa data.");
			prepareUpdate();
			return Action.INPUT;
		}

		if (faixaSalarialHistorico.getTipo() == TipoAplicacaoIndice.getIndice())
		{
			if (!faixaSalarialHistoricoManager.verifyHistoricoIndiceNaData(faixaSalarialHistorico.getData(), faixaSalarialHistorico.getIndice().getId()))
			{
				addActionMessage("Não existe histórico para o índice selecionado nesta data.");
				prepareInsert();
				return Action.INPUT;
			}
		}

		faixaSalarialHistoricoManager.update(faixaSalarialHistorico, faixaSalarialAux, getEmpresaSistema());

		return Action.SUCCESS;
	}

	private Boolean isCargoAtivo() 
	{
		Boolean cargoAtivo = null;
		if(situacaoCargo != null && situacaoCargo == 'A')
			cargoAtivo = Cargo.ATIVO;
		if(situacaoCargo != null && situacaoCargo == 'I')
			cargoAtivo = Cargo.INATIVO;
		return cargoAtivo;
	}
	
	public Object getModel()
	{
		return getFaixaSalarialHistorico();
	}

	public FaixaSalarialHistorico getFaixaSalarialHistorico()
	{
		if (faixaSalarialHistorico == null)
			faixaSalarialHistorico = new FaixaSalarialHistorico();
		return faixaSalarialHistorico;
	}

	public void setFaixaSalarialHistorico(FaixaSalarialHistorico faixaSalarialHistorico)
	{
		this.faixaSalarialHistorico = faixaSalarialHistorico;
	}

	public void setFaixaSalarialHistoricoManager(FaixaSalarialHistoricoManager faixaSalarialHistoricoManager)
	{
		this.faixaSalarialHistoricoManager = faixaSalarialHistoricoManager;
	}

	public void setFaixaSalarialManager(FaixaSalarialManager faixaSalarialManager)
	{
		this.faixaSalarialManager = faixaSalarialManager;
	}

	public Map getTipoAplicacaoIndices()
	{
		return tipoAplicacaoIndices;
	}

	public TipoAplicacaoIndice getTipoAplicacaoIndice()
	{
		return tipoAplicacaoIndice;
	}

	public FaixaSalarial getFaixaSalarialAux()
	{
		return faixaSalarialAux;
	}

	public void setFaixaSalarialAux(FaixaSalarial faixaSalarialAux)
	{
		this.faixaSalarialAux = faixaSalarialAux;
	}

	public void setIndiceManager(IndiceManager indiceManager)
	{
		this.indiceManager = indiceManager;
	}

	public Collection<Indice> getIndices()
	{
		return indices;
	}

	public Collection<CheckBox> getGrupoOcupacionalsCheckList()
	{
		return grupoOcupacionalsCheckList;
	}

	public void setGrupoOcupacionalsCheck(String[] grupoOcupacionalsCheck)
	{
		this.grupoOcupacionalsCheck = grupoOcupacionalsCheck;
	}

	public void setGrupoOcupacionalManager(GrupoOcupacionalManager grupoOcupacionalManager)
	{
		this.grupoOcupacionalManager = grupoOcupacionalManager;
	}

	public void setData(Date data)
	{
		this.data = data;
	}

	public Date getData()
	{
		return data;
	}

	public Collection<FaixaSalarialHistorico> getFaixaSalarialHistoricos()
	{
		return faixaSalarialHistoricos;
	}

	public Collection<Cargo> getCargos()
	{
		return cargos;
	}

	public void setCargoManager(CargoManager cargoManager)
	{
		this.cargoManager = cargoManager;
	}

	public Collection<CheckBox> getAreasCheckList() {
		return areasCheckList;
	}

	public String[] getAreasCheck() {
		return areasCheck;
	}

	public void setAreasCheck(String[] areasCheck) {
		this.areasCheck = areasCheck;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public String[] getCargosCheck() {
		return cargosCheck;
	}

	public void setCargosCheck(String[] cargosCheck) {
		this.cargosCheck = cargosCheck;
	}

	public Collection<CheckBox> getCargosCheckList() {
		return cargosCheckList;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public void setSituacaoCargo(Character situacaoCargo) {
		this.situacaoCargo = situacaoCargo;
	}
}