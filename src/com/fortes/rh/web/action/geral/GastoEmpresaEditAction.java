package com.fortes.rh.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.GastoEmpresaItemManager;
import com.fortes.rh.business.geral.GastoEmpresaManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.GastoEmpresa;
import com.fortes.rh.model.geral.GastoEmpresaItem;
import com.fortes.rh.model.geral.relatorio.GastoEmpresaTotal;
import com.fortes.rh.model.geral.relatorio.GastoRelatorio;
import com.fortes.rh.model.geral.relatorio.GastoRelatorioItem;
import com.fortes.rh.model.geral.relatorio.TotalGastoRelatorio;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings({"serial"})
public class GastoEmpresaEditAction extends MyActionSupportEdit implements ModelDriven
{
	private GastoEmpresaManager gastoEmpresaManager;
	private GastoEmpresaItemManager gastoEmpresaItemManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private ColaboradorManager colaboradorManager;
	private EstabelecimentoManager estabelecimentoManager;

	private GastoEmpresa gastoEmpresa;
	private Colaborador colaborador;

	private String dataIni;
	private String dataFim;
	//Recebe mes/ano para transformar em Date() format.01/mes/ano
	private String dataMesAno;
	private String dataClone;
	private String[] areasCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] estabelecimentosCheck;
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();

	private Collection<GastoEmpresaItem> itens;
	private Collection<Colaborador> colaboradors;
	private Collection<GastoRelatorio> gastoRelatorios = new ArrayList<GastoRelatorio>();
	private Collection<GastoRelatorioItem> gastoRelatorioItems = new ArrayList<GastoRelatorioItem>();
	private Collection<GastoEmpresaTotal> dataSource;

	private Collection<String> meses = new ArrayList<String>();

	private Map parametros = new HashMap();

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String prepareFiltroEstabelecimentoAreaOrganizacional() throws Exception
	{
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());

		String[] properties = new String[]{"id","nome"};
		String[] sets = new String[]{"id","nome"};

		Collection<Estabelecimento> estabelecimentosTmp = estabelecimentoManager.findToList(properties, sets, new String[]{"empresa.id"}, new Object[]{getEmpresaSistema().getId()}, new String[]{"nome"});
		estabelecimentosCheckList = CheckListBoxUtil.populaCheckListBox(estabelecimentosTmp, "getId", "getNome");

		return Action.SUCCESS;
	}

	public void prepare() throws Exception
	{
		if(gastoEmpresa != null && gastoEmpresa.getId() != null)
			gastoEmpresa = (GastoEmpresa) gastoEmpresaManager.findById(gastoEmpresa.getId());

		colaboradors = colaboradorManager.find(new String[]{"empresa.id"},new Object[]{getEmpresaSistema().getId()},new String[]{"nomeComercial"});
		prepareFiltroEstabelecimentoAreaOrganizacional();
	}

	@SuppressWarnings("unchecked")
	public String prepareInsert() throws Exception
	{
		prepare();

		Map session = ActionContext.getContext().getSession();
		session.put("SESSION_GASTO_ITENS", null);

		return Action.SUCCESS;
	}

	@SuppressWarnings({ "unchecked" })
	public String insert() throws Exception
	{
		Map session = ActionContext.getContext().getSession();
		itens = (Collection<GastoEmpresaItem>) session.get("SESSION_GASTO_ITENS");

		if(itens == null || itens.size() == 0)
		{
			addActionError("Nenhum item foi inserido.");
			prepareInsert();
			return Action.INPUT;
		}

		session.put("SESSION_GASTO_ITENS", null);

		gastoEmpresa.setEmpresa(getEmpresaSistema());
		gastoEmpresa.setMesAno(DateUtil.criarDataMesAno(dataMesAno));
		gastoEmpresa = gastoEmpresaManager.save(gastoEmpresa);

		try
		{
			saveDetalhes();
		}
		catch (Exception e)
		{
			addActionError("Ocorreu um erro ao inserir investimento.");
			return Action.INPUT;
		}

		return Action.SUCCESS;
	}

	private void saveDetalhes()
	{
		if (itens != null && !itens.isEmpty())
		{
			for (GastoEmpresaItem i : itens)
			{
				i.setId(null);
				i.setGastoEmpresa(gastoEmpresa);
				gastoEmpresaItemManager.save(i);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public String update() throws Exception
	{
		Map session = ActionContext.getContext().getSession();
		itens = (Collection<GastoEmpresaItem>) session.get("SESSION_GASTO_ITENS");

		if(itens == null || itens.size() == 0)
		{
			addActionError("Nenhum item foi inserido.");
			prepareUpdate();
			return Action.INPUT;
		}

		session.put("SESSION_GASTO_ITENS", null);

		gastoEmpresa.setEmpresa(getEmpresaSistema());

		gastoEmpresa.setMesAno(DateUtil.criarDataMesAno(dataMesAno));
		gastoEmpresaManager.update(gastoEmpresa);

		gastoEmpresaItemManager.removeGastos(gastoEmpresa);

		saveDetalhes();

		return Action.SUCCESS;
	}

	public String prepareImprimir() throws Exception
	{
		prepareFiltroEstabelecimentoAreaOrganizacional();

		return Action.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String gerarRelatorioInvestimentos() throws Exception
	{
		String msg = null;
		try
		{
			LinkedHashMap filtro = new LinkedHashMap();

			if(dataIni != null && !(dataIni).trim().equals("") && dataFim != null && !(dataFim).trim().equals(""))
			{
				Date dateIni = DateUtil.criarDataMesAno(dataIni);
				Date dateFim = DateUtil.getUltimoDiaMes(DateUtil.criarDataMesAno(dataFim));

				filtro.put("dataIni", dateIni);
				filtro.put("dataFim", dateFim);
			}

			if(colaborador != null && colaborador.getId() != null)
			{
				filtro.put("colaborador", colaborador);
				colaborador = colaboradorManager.findColaboradorById(colaborador.getId());
			}

			filtro.put("areas", LongUtil.arrayStringToCollectionLong(areasCheck));
			filtro.put("estabelecimentos", LongUtil.arrayStringToCollectionLong(estabelecimentosCheck));
			filtro.put("empresaId", getEmpresaSistema().getId());

			Collection<GastoRelatorio> gastoRelatorios = gastoEmpresaManager.filtroRelatorio(filtro);
			Collection<TotalGastoRelatorio> totais =  gastoEmpresaManager.getTotalInvestimentos(gastoRelatorios);

			GastoEmpresaTotal gastoEmpresaTotal = new GastoEmpresaTotal();
			gastoEmpresaTotal.setGastoRelatorios(gastoRelatorios);
			gastoEmpresaTotal.setTotais(totais);

			dataSource = new ArrayList<GastoEmpresaTotal>();
			dataSource.add(gastoEmpresaTotal);

			if(dataSource == null || dataSource.isEmpty())
			{
				ResourceBundle bundle = ResourceBundle.getBundle("application");
				msg = bundle.getString("error.relatorio.vazio");
				throw new Exception(msg);
			}

			String pathImg = ServletActionContext.getServletContext().getRealPath("/imgs/") + java.io.File.separatorChar;
			
			String filtroRelatorio = "Período: " + dataIni + " à " + dataFim;
			parametros = RelatorioUtil.getParametrosRelatorio("Relatório de Investimentos da Empresa", getEmpresaSistema(), filtroRelatorio);

			parametros.put("IMG_DIR", pathImg);
			parametros.put("IDCOLABORADOR", colaborador.getId());
			parametros.put("NOMECOLABORADOR", colaborador.getNomeComercial());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(msg != null)
				addActionMessage(msg);
			else
				addActionMessage("Não foi possível gerar o relatório");

			prepareFiltroEstabelecimentoAreaOrganizacional();
			areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
			estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);
			return Action.INPUT;
		}
		return Action.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String prepareUpdate() throws Exception
	{
		prepare();
		preparaData();

		Map session = ActionContext.getContext().getSession();
		session.put("SESSION_GASTO_ITENS", gastoEmpresaItemManager.find(new String[]{"gastoEmpresa.id"}, new Object[]{gastoEmpresa.getId()}));

		return Action.SUCCESS;
	}

	public String prepareClonar() throws Exception
	{
		prepare();
		preparaData();

		itens = gastoEmpresaItemManager.getGastosImportaveis(gastoEmpresa);
		return Action.SUCCESS;
	}

	@SuppressWarnings("deprecation")
	private void preparaData()
	{
		//formata mes ano para evitar problema com js do Date no ftl
		String mes = "";
		if(gastoEmpresa.getMesAno().getMonth() < 9)
			mes = "0" + (gastoEmpresa.getMesAno().getMonth() + 1);
		else
			mes = "" + (gastoEmpresa.getMesAno().getMonth() + 1);

		dataMesAno = mes +"/"+ (gastoEmpresa.getMesAno().getYear() + 1900);
	}

	public String clonar() throws Exception
	{
		gastoEmpresaManager.clonarGastosPorColaborador(dataClone, gastoEmpresa);

		return Action.SUCCESS;
	}

	public GastoEmpresa getGastoEmpresa()
	{
		if(gastoEmpresa == null)
			gastoEmpresa = new GastoEmpresa();
		return gastoEmpresa;
	}

	public void setGastoEmpresa(GastoEmpresa gastoEmpresa)
	{
		this.gastoEmpresa=gastoEmpresa;
	}

	public Object getModel()
	{
		return getGastoEmpresa();
	}

	public void setGastoEmpresaManager(GastoEmpresaManager gastoEmpresaManager)
	{
		this.gastoEmpresaManager=gastoEmpresaManager;
	}

	public Collection<GastoEmpresaItem> getItens()
	{
		return itens;
	}

	public void setItens(Collection<GastoEmpresaItem> itens)
	{
		this.itens = itens;
	}

	public void setGastoEmpresaItemManager(GastoEmpresaItemManager gastoEmpresaItemManager)
	{
		this.gastoEmpresaItemManager = gastoEmpresaItemManager;
	}

	public Collection<Colaborador> getColaboradors()
	{
		return colaboradors;
	}

	public void setColaboradors(Collection<Colaborador> colaboradors)
	{
		this.colaboradors = colaboradors;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
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

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public String getDataFim()
	{
		return dataFim;
	}

	public void setDataFim(String dataFim)
	{
		this.dataFim = dataFim;
	}

	public String getDataIni()
	{
		return dataIni;
	}

	public void setDataIni(String dataIni)
	{
		this.dataIni = dataIni;
	}

	public String getDataMesAno()
	{
		return dataMesAno;
	}

	public void setDataMesAno(String dataMesAno)
	{
		this.dataMesAno = dataMesAno;
	}

	public List getEmptyDataSource()
	{
		return new CollectionUtil().convertCollectionToList(gastoRelatorios);
	}

	public Collection<GastoRelatorio> getGastoRelatorios()
	{
		return gastoRelatorios;
	}

	public void setGastoRelatorios(Collection<GastoRelatorio> gastoRelatorios)
	{
		this.gastoRelatorios = gastoRelatorios;
	}

	public Collection<String> getMeses()
	{
		return meses;
	}

	public void setMeses(Collection<String> meses)
	{
		this.meses = meses;
	}

	public Collection<GastoRelatorioItem> getGastoRelatorioItems()
	{
		return gastoRelatorioItems;
	}

	public void setGastoRelatorioItems(Collection<GastoRelatorioItem> gastoRelatorioItems)
	{
		this.gastoRelatorioItems = gastoRelatorioItems;
	}

	public String getDataClone()
	{
		return dataClone;
	}

	public void setDataClone(String dataClone)
	{
		this.dataClone = dataClone;
	}

	public Map getParametros()
	{
		return parametros;
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

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public Collection<GastoEmpresaTotal> getDataSource()
	{
		return dataSource;
	}

	public void setDataSource(Collection<GastoEmpresaTotal> dataSource)
	{
		this.dataSource = dataSource;
	}

}