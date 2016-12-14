/* Autor: Bruno Bachiega
 * Data: 7/06/2006
 * Requisito: RFA005 */
package com.fortes.rh.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.business.cargosalario.TabelaReajusteColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.TipoReajuste;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupport;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class ColaboradorReportAction extends MyActionSupport
{
	private List emptyDataSource = new ArrayList();

	private Map<String, Object> parametros = new HashMap<String, Object>();
	private String filtro;
	private Date data;

	private Collection<Colaborador> dataSource;

	private AreaOrganizacionalManager areaOrganizacionalManager = null;
	private GrupoOcupacionalManager grupoOcupacionalManager = null;
	private EstabelecimentoManager estabelecimentoManager;
	private ColaboradorManager colaboradorManager;
	private CargoManager cargoManager;
	private TabelaReajusteColaboradorManager tabelaReajusteColaboradorManager;

	private Collection<TabelaReajusteColaborador> tabelaReajusteColaboradors;
	private Collection<AreaOrganizacional> areaOrganizacionals;
	private Collection<GrupoOcupacional> grupoOcupacionals;

	private TabelaReajusteColaborador tabelaReajusteColaborador;

	private String[] estabelecimentosCheck;
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();

	private String[] areasCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();

	private String[] gruposCheck;
	private Collection<CheckBox> gruposCheckList = new ArrayList<CheckBox>();

	private String[] cargosCheck;
	private Collection<CheckBox> cargosCheckList = new ArrayList<CheckBox>();

	private String reportFilter;
	private String reportTitle;
	
	@SuppressWarnings("unchecked")
	public String execute()
	{
		return SUCCESS;
	}

	public String prepareProjecaoSalarialFiltro() throws Exception
	{
		estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(getEmpresaSistema().getId());

        areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());

        tabelaReajusteColaboradors = tabelaReajusteColaboradorManager.findAllSelectByNaoAprovada(getEmpresaSistema().getId(), TipoReajuste.COLABORADOR);

		grupoOcupacionals = grupoOcupacionalManager.findAllSelect(getEmpresaSistema().getId());
		gruposCheckList = CheckListBoxUtil.populaCheckListBox(grupoOcupacionals, "getId", "getNome", null);

		cargosCheckList = cargoManager.populaCheckBox(false, getEmpresaSistema().getId());

		return Action.SUCCESS;
	}

	public String gerarRelatorioProjecaoSalarial() throws Exception
	{
		String msg = null;
		try
		{
			Collection<Long> estabelecimentoIds = LongUtil.arrayStringToCollectionLong(estabelecimentosCheck);
			Collection<Long> areaIds = LongUtil.arrayStringToCollectionLong(areasCheck);
			Collection<Long> grupoIds = LongUtil.arrayStringToCollectionLong(gruposCheck);
			Collection<Long> cargoIds = LongUtil.arrayStringToCollectionLong(cargosCheck);

			if (tabelaReajusteColaborador.getId() != null)
				tabelaReajusteColaborador = tabelaReajusteColaboradorManager.findByIdProjection(tabelaReajusteColaborador.getId());

			dataSource = colaboradorManager.findProjecaoSalarial(tabelaReajusteColaborador.getId(), data, estabelecimentoIds, areaIds, grupoIds, cargoIds, filtro, getEmpresaSistema().getId());

			if(dataSource == null || dataSource.isEmpty())
			{
				ResourceBundle bundle = ResourceBundle.getBundle("application");
				msg = bundle.getString("error.relatorio.vazio");
				throw new Exception(msg);
			}

			parametros = RelatorioUtil.getParametrosRelatorio("Relatório de Projeção Salarial", getEmpresaSistema(), "Data da Projeção: " + DateUtil.formataDiaMesAno(data));			
			parametros.put("FILTRAR_POR", filtro);
			parametros.put("TABELA_REAJUSTE_NOME", tabelaReajusteColaborador.getNome());
			parametros.put("INTEGRA_AC", getEmpresaSistema().isAcIntegra());

			return Action.SUCCESS;

		}
		catch (Exception e)
		{
			e.printStackTrace();

			if(msg != null)
				addActionMessage(msg);
			else
				addActionMessage("Não foi possível gerar o relatório");

			prepareProjecaoSalarialFiltro();
 			return Action.INPUT;
		}


	}


	public Collection<AreaOrganizacional> getAreaOrganizacionals()
	{
		return areaOrganizacionals;
	}

	public void setAreaOrganizacionals(Collection<AreaOrganizacional> areaOrganizacionals)
	{
		this.areaOrganizacionals = areaOrganizacionals;
	}

	public ColaboradorManager getColaboradorManager()
	{
		return colaboradorManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public Collection<Colaborador> getDataSource()
	{
		return dataSource;
	}

	public void setDataSource(Collection<Colaborador> dataSource)
	{
		this.dataSource = dataSource;
	}

	public String[] getEstabelecimentosCheck()
	{
		return estabelecimentosCheck;
	}

	public void setEstabelecimentosCheck(String[] estabelecimentosCheck)
	{
		this.estabelecimentosCheck = estabelecimentosCheck;
	}

	public Collection<CheckBox> getEstabelecimentosCheckList()
	{
		return estabelecimentosCheckList;
	}

	public void setEstabelecimentosCheckList(Collection<CheckBox> estabelecimentosCheckList)
	{
		this.estabelecimentosCheckList = estabelecimentosCheckList;
	}

	public String getFiltro()
	{
		return filtro;
	}

	public void setFiltro(String filtro)
	{
		this.filtro = filtro;
	}

	public Collection<GrupoOcupacional> getGrupoOcupacionals()
	{
		return grupoOcupacionals;
	}

	public void setGrupoOcupacionals(Collection<GrupoOcupacional> grupoOcupacionals)
	{
		this.grupoOcupacionals = grupoOcupacionals;
	}

	public Map getParametros()
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

	public void setGrupoOcupacionalManager(GrupoOcupacionalManager grupoOcupacionalManager)
	{
		this.grupoOcupacionalManager = grupoOcupacionalManager;
	}
	public List getEmptyDataSource()
	{
		return emptyDataSource;
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

	public String[] getCargosCheck()
	{
		return cargosCheck;
	}

	public void setCargosCheck(String[] cargosCheck)
	{
		this.cargosCheck = cargosCheck;
	}

	public Date getData()
	{
		return data;
	}

	public void setData(Date data)
	{
		this.data = data;
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

	public Collection<TabelaReajusteColaborador> getTabelaReajusteColaboradors()
	{
		return tabelaReajusteColaboradors;
	}

	public void setTabelaReajusteColaboradors(Collection<TabelaReajusteColaborador> tabelaReajusteColaboradors)
	{
		this.tabelaReajusteColaboradors = tabelaReajusteColaboradors;
	}

	public void setTabelaReajusteColaboradorManager(TabelaReajusteColaboradorManager tabelaReajusteColaboradorManager)
	{
		this.tabelaReajusteColaboradorManager = tabelaReajusteColaboradorManager;
	}

	public TabelaReajusteColaborador getTabelaReajusteColaborador()
	{
		return tabelaReajusteColaborador;
	}

	public void setTabelaReajusteColaborador(TabelaReajusteColaborador tabelaReajusteColaborador)
	{
		this.tabelaReajusteColaborador = tabelaReajusteColaborador;
	}

	public String getReportFilter() {
		return reportFilter;
	}
	
	public String getReportTitle() {
		return reportTitle;
	}

}