package com.fortes.rh.web.action.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.cargosalario.ReajusteColaboradorManager;
import com.fortes.rh.business.cargosalario.ReajusteFaixaSalarialManager;
import com.fortes.rh.business.cargosalario.ReajusteIndiceManager;
import com.fortes.rh.business.cargosalario.TabelaReajusteColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.cargosalario.ReajusteFaixaSalarial;
import com.fortes.rh.model.cargosalario.ReajusteIndice;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.FiltrosRelatorio;
import com.fortes.rh.model.dicionario.TipoReajuste;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.web.action.MyActionSupport;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings( { "unchecked", "serial" })
public class ReajusteRelatorioAction extends MyActionSupport
{
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private GrupoOcupacionalManager grupoOcupacionalManager;
	private EstabelecimentoManager estabelecimentoManager;
	private ReajusteColaboradorManager reajusteColaboradorManager;
	private ReajusteIndiceManager reajusteIndiceManager;
	private ReajusteFaixaSalarialManager reajusteFaixaSalarialManager;
	private TabelaReajusteColaboradorManager tabelaReajusteColaboradorManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private CargoManager cargoManager;

	private Map<String, Object> parametros = new HashMap<String, Object>();
	private LinkedHashMap filtrarPor;
	private boolean imprimirSomenteTotais;
	private TabelaReajusteColaborador tabelaReajusteColaborador;
	private String filtro;
	private String total;
	private Boolean exibirObservacao;
	private Boolean exibirAreaEstabelecimento;

	private Collection<ReajusteColaborador> dataSource;
	private Collection<ReajusteIndice> dataSourceIndice;
	private Collection<ReajusteFaixaSalarial> dataSourceFaixaSalarial;
	private Collection<TabelaReajusteColaborador> tabelaReajusteColaboradors;
	private Collection<AreaOrganizacional> areaOrganizacionals;
	private Collection<GrupoOcupacional> grupoOcupacionals;

	private String[] estabelecimentosCheck;
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();

	private String[] areaOrganizacionalsCheck;
	private Collection<CheckBox> areaOrganizacionalsCheckList = new ArrayList<CheckBox>();

	private String[] indicesCheck;
	private Collection<CheckBox> indicesCheckList = new ArrayList<CheckBox>();
	
	private String[] cargosCheck;
	private Collection<CheckBox> cargosCheckList = new ArrayList<CheckBox>();
	
	private String[] faixaSalarialsCheck;
	private Collection<CheckBox> faixaSalarialsCheckList = new ArrayList<CheckBox>();

	private String[] grupoOcupacionalsCheck;
	private Collection<CheckBox> grupoOcupacionalsCheckList = new ArrayList<CheckBox>();
	private Double valorTotalFolha;

	private String reportTitle;
	private boolean verTodasAreas;
	
	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String formFiltro() throws Exception
	{
		estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(getEmpresaSistema().getId());
		tabelaReajusteColaboradors = tabelaReajusteColaboradorManager.findAllSelect(getEmpresaSistema().getId(), null, null);
		grupoOcupacionals = grupoOcupacionalManager.findAllSelect(getEmpresaSistema().getId());
		grupoOcupacionalsCheckList = CheckListBoxUtil.populaCheckListBox(grupoOcupacionals, "getId", "getNome", null);
		cargosCheckList = cargoManager.populaCheckBox(false, getEmpresaSistema().getId());
		
		Long[] areaIds = null;
		Usuario usuarioLogado = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession());
		
		verTodasAreas = SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VER_AREAS"});
		
		if(verTodasAreas) {
			areaOrganizacionalsCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		} else {
			areaIds = areaOrganizacionalManager.findIdsAreasDoResponsavelCoResponsavel(usuarioLogado, getEmpresaSistema().getId());
			
			if(areaIds.length == 0)
				areaIds = new Long[]{-1L};
			
			areaOrganizacionalsCheckList = areaOrganizacionalManager.populaCheckByAreasOrderDescricao(areaIds);
			if(areaOrganizacionalsCheckList.isEmpty())
			{
				addActionMessage("Não é possível gerar o relatório, pois você não é responsável por nenhuma área organizacional.");
				tabelaReajusteColaboradors = null;
			}
		}

		filtrarPor = new FiltrosRelatorio();

		if (tabelaReajusteColaborador != null && tabelaReajusteColaborador.getId() != null)
			tabelaReajusteColaborador = tabelaReajusteColaboradorManager.findByIdProjection(tabelaReajusteColaborador.getId());

		return Action.SUCCESS;
	}
	public String gerarRelatorioXls() throws Exception
	{
		String retorno = gerarRelatorio();
		
		if(retorno.equals(Action.SUCCESS))
		{
			if(filtro.equals("2"))
				retorno = "succesGrupoOcupacional";
			else
				retorno = Action.SUCCESS;
			
			if(exibirObservacao)
				retorno += "Obs";
		}else if (retorno.equals("successExibirAreaEstabelecimento"))
		{
			retorno = "successExibirAreaEstabelecimento";
			
			if(filtro.equals("2"))
				retorno += "GrupoOcupacional";
			
			if(exibirObservacao)
				retorno += "Obs";
		}
		
		return retorno;
	}

	@SuppressWarnings("finally")
	public String gerarRelatorio() throws Exception
	{
		try
		{
			TabelaReajusteColaborador tabelaReajusteColaboradorAux = tabelaReajusteColaboradorManager.findByIdProjection(tabelaReajusteColaborador.getId());
			Collection<Long> estabelecimentos = LongUtil.arrayStringToCollectionLong(estabelecimentosCheck);
			Collection<Long> areaOrganizacionals = LongUtil.arrayStringToCollectionLong(areaOrganizacionalsCheck);
			Collection<Long> grupoOcupacionals = LongUtil.arrayStringToCollectionLong(grupoOcupacionalsCheck);
			Collection<Long> indices = LongUtil.arrayStringToCollectionLong(indicesCheck);
			Collection<Long> faixaSalarials = LongUtil.arrayStringToCollectionLong(faixaSalarialsCheck);
			Collection<Long> cargos = LongUtil.arrayStringToCollectionLong(cargosCheck);

			HashMap filtros = new HashMap();
			// parametros dos filtros
			filtros.put("tabela", tabelaReajusteColaborador.getId());
			filtros.put("estabelecimentos", estabelecimentos);
			filtros.put("filtrarPor", filtro);
			filtros.put("areas", areaOrganizacionals);
			filtros.put("grupos", grupoOcupacionals);
			filtros.put("indices", indices);
			filtros.put("faixaSalarials", faixaSalarials);
			filtros.put("cargos", cargos);
			filtros.put("total", total);

			String retorno;
			if(tabelaReajusteColaboradorAux.getTipoReajuste().equals(TipoReajuste.INDICE))
			{
				dataSourceIndice = reajusteIndiceManager.findByFiltros(filtros);
				
				if (dataSourceIndice == null || dataSourceIndice.isEmpty())
					throw new FortesException(" Não existem dados para o filtro informado.<br>");
				
				retorno = "successIndice";
			} else if(tabelaReajusteColaboradorAux.getTipoReajuste().equals(TipoReajuste.FAIXA_SALARIAL))
			{
				dataSourceFaixaSalarial = reajusteFaixaSalarialManager.findByFiltros(filtros);
				
				if (dataSourceFaixaSalarial == null || dataSourceFaixaSalarial.isEmpty())
					throw new FortesException(" Não existem dados para o filtro informado.<br>");

				retorno = "successFaixaSalarial";
			} else 
			{
				dataSource = reajusteColaboradorManager.findByGruposAreas(filtros);

				if (dataSource == null || dataSource.isEmpty())
					throw new FortesException(" Não existem dados para o filtro informado.<br>");

				dataSource = reajusteColaboradorManager.ordenaPorEstabelecimentoAreaOrGrupoOcupacional(getEmpresaSistema().getId(), dataSource, filtro);
				
				if (exibirAreaEstabelecimento && total.equals("false"))
					retorno = "successExibirAreaEstabelecimento";
				else 
					retorno = Action.SUCCESS;
			}

			valorTotalFolha = historicoColaboradorManager.getValorTotalFolha(getEmpresaSistema().getId(), tabelaReajusteColaboradorAux.getData());

			reportTitle= "Relatório de Simulação de Promoções e Reajustes de Salários"; 
			
			parametros = reajusteColaboradorManager.getParametrosRelatorio(reportTitle, getEmpresaSistema(), tabelaReajusteColaboradorAux.getNome());
			parametros.put("EXIBIR_TOTAL",total);
			parametros.put("EXIBIR_OBS",exibirObservacao);
			parametros.put("FILTRAR_POR", filtro);
			parametros.put("TOTAL_FOLHA", valorTotalFolha);
			
			return retorno;
		}
		catch (FortesException e)
		{
			e.printStackTrace();
			addActionMessage(e.getMessage());
			
			tabelaReajusteColaborador = null;
			formFiltro();
			return Action.INPUT;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionMessage("Não foi possível gerar o relatório");
			
			tabelaReajusteColaborador = null;
			formFiltro();
			return Action.INPUT;
		} 
	}

	public LinkedHashMap getFiltrarPor()
	{
		return filtrarPor;
	}

	public void setFiltrarPor(LinkedHashMap filtrarPor)
	{
		this.filtrarPor = filtrarPor;
	}

	public Collection<AreaOrganizacional> getAreaOrganizacionals()
	{
		return areaOrganizacionals;
	}

	public void setAreaOrganizacionals(Collection<AreaOrganizacional> areaOrganizacionals)
	{
		this.areaOrganizacionals = areaOrganizacionals;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public Collection<GrupoOcupacional> getGrupoOcupacionals()
	{
		return grupoOcupacionals;
	}

	public void setGrupoOcupacionals(Collection<GrupoOcupacional> grupoOcupacionals)
	{
		this.grupoOcupacionals = grupoOcupacionals;
	}

	public void setGrupoOcupacionalManager(GrupoOcupacionalManager grupoOcupacionalManager)
	{
		this.grupoOcupacionalManager = grupoOcupacionalManager;
	}

	public boolean isImprimirSomenteTotais()
	{
		return imprimirSomenteTotais;
	}

	public void setImprimirSomenteTotais(boolean imprimirSomenteTotais)
	{
		this.imprimirSomenteTotais = imprimirSomenteTotais;
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

	public String getFiltro()
	{
		return filtro;
	}

	public void setFiltro(String filtro)
	{
		this.filtro = filtro;
	}

	public String getTotal()
	{
		return total;
	}

	public void setTotal(String total)
	{
		this.total = total;
	}

	public Collection<ReajusteColaborador> getDataSource()
	{
		return dataSource;
	}

	public void setDataSource(Collection<ReajusteColaborador> dataSource)
	{
		this.dataSource = dataSource;
	}

	public void setReajusteColaboradorManager(ReajusteColaboradorManager reajusteColaboradorManager)
	{
		this.reajusteColaboradorManager = reajusteColaboradorManager;
	}

	public Map getParametros()
	{
		return parametros;
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

	public Collection<CheckBox> getEstabelecimentosCheckList()
	{
		return estabelecimentosCheckList;
	}

	public void setEstabelecimentosCheckList(Collection<CheckBox> estabelecimentosCheckList)
	{
		this.estabelecimentosCheckList = estabelecimentosCheckList;
	}

	public String[] getAreaOrganizacionalsCheck()
	{
		return areaOrganizacionalsCheck;
	}

	public void setAreaOrganizacionalsCheck(String[] areaOrganizacionalsCheck)
	{
		this.areaOrganizacionalsCheck = areaOrganizacionalsCheck;
	}

	public Collection<CheckBox> getAreaOrganizacionalsCheckList()
	{
		return areaOrganizacionalsCheckList;
	}

	public void setAreaOrganizacionalsCheckList(Collection<CheckBox> areaOrganizacionalsCheckList)
	{
		this.areaOrganizacionalsCheckList = areaOrganizacionalsCheckList;
	}

	public String[] getGrupoOcupacionalsCheck()
	{
		return grupoOcupacionalsCheck;
	}

	public void setGrupoOcupacionalsCheck(String[] grupoOcupacionalsCheck)
	{
		this.grupoOcupacionalsCheck = grupoOcupacionalsCheck;
	}

	public Collection<CheckBox> getGrupoOcupacionalsCheckList()
	{
		return grupoOcupacionalsCheckList;
	}

	public void setGrupoOcupacionalsCheckList(Collection<CheckBox> grupoOcupacionalsCheckList)
	{
		this.grupoOcupacionalsCheckList = grupoOcupacionalsCheckList;
	}

	public Boolean getExibirObservacao() {
		return exibirObservacao;
	}

	public void setExibirObservacao(Boolean exibirObservacao) {
		this.exibirObservacao = exibirObservacao;
	}

	public Collection<CheckBox> getIndicesCheckList() {
		return indicesCheckList;
	}

	public void setHistoricoColaboradorManager(	HistoricoColaboradorManager historicoColaboradorManager) {
		this.historicoColaboradorManager = historicoColaboradorManager;
	}

	public Collection<ReajusteFaixaSalarial> getDataSourceFaixaSalarial() {
		return dataSourceFaixaSalarial;
	}

	public Collection<ReajusteIndice> getDataSourceIndice() {
		return dataSourceIndice;
	}

	public void setReajusteFaixaSalarialManager(
			ReajusteFaixaSalarialManager reajusteFaixaSalarialManager) {
		this.reajusteFaixaSalarialManager = reajusteFaixaSalarialManager;
	}

	public void setReajusteIndiceManager(ReajusteIndiceManager reajusteIndiceManager) {
		this.reajusteIndiceManager = reajusteIndiceManager;
	}

	public void setFaixaSalarialsCheck(String[] faixaSalarialsCheck) {
		this.faixaSalarialsCheck = faixaSalarialsCheck;
	}

	public Collection<CheckBox> getFaixaSalarialsCheckList() {
		return faixaSalarialsCheckList;
	}

	public void setCargosCheck(String[] cargosCheck) {
		this.cargosCheck = cargosCheck;
	}

	public Collection<CheckBox> getCargosCheckList() {
		return cargosCheckList;
	}

	public void setCargoManager(CargoManager cargoManager) {
		this.cargoManager = cargoManager;
	}

	public void setIndicesCheck(String[] indicesCheck) {
		this.indicesCheck = indicesCheck;
	}

	public boolean isVerTodasAreas()
	{
		return verTodasAreas;
	}
	
	public void setExibirAreaEstabelecimento(Boolean exibirAreaEstabelecimento)
	{
		this.exibirAreaEstabelecimento = exibirAreaEstabelecimento;
	}

	public String getReportTitle() {
		return reportTitle;
	}
}