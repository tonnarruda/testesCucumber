package com.fortes.rh.web.action.captacao.indicador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.relatorio.TurnOverCollection;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class IndicadorTurnOverListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private String dataDe;
	private String dataAte;
	private String[] areasCheck;
	private String[] estabelecimentosCheck;
	private String[] cargosCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> cargosCheckList = new ArrayList<CheckBox>();

	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EstabelecimentoManager estabelecimentoManager;
	private ColaboradorManager colaboradorManager;
	private CargoManager cargoManager;

	private Map<String, Object> parametros = new HashMap<String, Object>();
	private Collection<TurnOverCollection> dataSource;
	private int filtrarPor = 1;

	public String prepare() throws Exception
	{
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		cargosCheckList = cargoManager.populaCheckBox(getEmpresaSistema().getId());
		estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(getEmpresaSistema().getId());
		
		CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);
		CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
		CheckListBoxUtil.marcaCheckListBox(cargosCheckList, cargosCheck);

		return Action.SUCCESS;
	}

	public String list() throws Exception 
	{
		if(DateUtil.mesesEntreDatas(DateUtil.criarDataMesAno(dataDe), DateUtil.criarDataMesAno(dataAte)) > 12)
		{
			addActionMessage("Não é permitido um período maior do 12 meses para a geração deste relatório");
			prepare();
			return Action.INPUT;
		}

		Map<String, Object> parametrosConsulta = new HashMap<String, Object>();
		parametrosConsulta.put("areas", areasCheck);
		parametrosConsulta.put("cargos", cargosCheck);
		parametrosConsulta.put("estabelecimentos", estabelecimentosCheck);
		parametrosConsulta.put("filtrarPor", filtrarPor);
		parametrosConsulta.put("empresaId", getEmpresaSistema().getId());

		TurnOverCollection turnOverCollection = new TurnOverCollection();
		
		try 
		{
			turnOverCollection.setTurnOvers(colaboradorManager.getTurnOver(dataDe, dataAte, parametrosConsulta));
			dataSource = Arrays.asList(turnOverCollection);
			
			String filtro =  "Período: " + dataDe + " a " + dataAte;
			parametros = RelatorioUtil.getParametrosRelatorio("Turnover (rotatividade de colaboradores)", getEmpresaSistema(), filtro);
			
			return Action.SUCCESS;
		
		} catch (ColecaoVaziaException e) {

			addActionMessage(e.getMessage());
			prepare();
			return Action.INPUT;
		}
	}

	public String getDataAte()
	{
		return dataAte;
	}

	public void setDataAte(String dataAte)
	{
		this.dataAte = dataAte;
	}

	public String getDataDe()
	{
		return dataDe;
	}

	public void setDataDe(String dataDe)
	{
		this.dataDe = dataDe;
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

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	@SuppressWarnings("unchecked")
	public void setParametros(HashMap parametros)
	{
		this.parametros = parametros;
	}

	public Collection<CheckBox> getEstabelecimentosCheckList()
	{
		return estabelecimentosCheckList;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public String[] getEstabelecimentosCheck()
	{
		return estabelecimentosCheck;
	}

	public void setEstabelecimentosCheck(String[] estabelecimentosCheck)
	{
		this.estabelecimentosCheck = estabelecimentosCheck;
	}

	public void setEstabelecimentosCheckList(Collection<CheckBox> estabelecimentosCheckList)
	{
		this.estabelecimentosCheckList = estabelecimentosCheckList;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
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

	public void setCargoManager(CargoManager cargoManager)
	{
		this.cargoManager = cargoManager;
	}

	public Collection<TurnOverCollection> getDataSource()
	{
		return dataSource;
	}

	public void setDataSource(Collection<TurnOverCollection> dataSource)
	{
		this.dataSource = dataSource;
	}
}