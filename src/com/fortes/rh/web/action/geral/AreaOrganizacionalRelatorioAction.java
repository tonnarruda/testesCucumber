package com.fortes.rh.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.web.action.MyActionSupport;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings("serial")
public class AreaOrganizacionalRelatorioAction extends MyActionSupport
{
	private Map<String, Object> parametros = new HashMap<String, Object>();

	private Collection<Colaborador> dataSource;

	private AreaOrganizacionalManager areaOrganizacionalManager;
	private ColaboradorManager colaboradorManager;
	private ConfiguracaoCampoExtraManager configuracaoCampoExtraManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private EmpresaManager empresaManager;

	private Collection<AreaOrganizacional> areaOrganizacionals;
	private Collection<GrupoOcupacional> grupoOcupacionals;
	private Collection<Empresa> empresas;

	private String[] estabelecimentosCheck;
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();

	private String[] areasCheck;
	private Collection<CheckBox> areaOrganizacionalsCheckList = new ArrayList<CheckBox>();

	private String[] cargosCheck;
	private Collection<CheckBox> cargosCheckList = new ArrayList<CheckBox>();

	private String[] grupoOcupacionalsCheck;
	private Collection<CheckBox> grupoOcupacionalsCheckList = new ArrayList<CheckBox>();
	
	private Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras = new ArrayList<ConfiguracaoCampoExtra>();
	private boolean habilitaCampoExtra;
	private CamposExtras camposExtras;
	private Long[] empresaIds;//repassado para o DWR
	private Empresa empresa;
	private Boolean compartilharColaboradores;
	private String reportTitle;
	
	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String formFiltro() throws Exception
	{
		compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
		empresas = empresaManager.findEmpresasPermitidas(compartilharColaboradores , getEmpresaSistema().getId(),SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), "ROLE_REL_AREAORGANIZACIONAL");

		CollectionUtil<Empresa> clu = new CollectionUtil<Empresa>();
		empresaIds = clu.convertCollectionToArrayIds(empresas);//usado pelo DWR
		
		empresa = getEmpresaSistema();
		
		habilitaCampoExtra = empresa.isCampoExtraColaborador();
		if(habilitaCampoExtra)
			configuracaoCampoExtras = configuracaoCampoExtraManager.find(new String[]{"ativoColaborador", "empresa.id"}, new Object[]{true, empresa.getId()}, new String[]{"ordem"});
		
		return Action.SUCCESS;
	}

	public String gerarRelatorio() throws Exception
	{
		String msg = null;
		try
		{
			Collection<Long> estabelecimentos = LongUtil.arrayStringToCollectionLong(estabelecimentosCheck);
			Collection<Long> areas = LongUtil.arrayStringToCollectionLong(areasCheck);
			Collection<Long> cargos = LongUtil.arrayStringToCollectionLong(cargosCheck);
				
			dataSource = colaboradorManager.findAreaOrganizacionalByAreas(habilitaCampoExtra, estabelecimentos, areas, cargos, camposExtras, empresa.getId(), null, null, null, null, null, null, null, SituacaoColaborador.ATIVO, null);
			if(dataSource == null || dataSource.isEmpty())
			{
				ResourceBundle bundle = ResourceBundle.getBundle("application");
				msg = bundle.getString("error.relatorio.vazio");
				throw new Exception(msg);
			}

			reportTitle = "Relatório de Colaboradores por Área Organizacional";

			dataSource = colaboradorManager.ordenaPorEstabelecimentoArea(getEmpresaSistema().getId(), dataSource);

			parametros = areaOrganizacionalManager.getParametrosRelatorio("Relatório de Áreas Organizacionais", getEmpresaSistema(), null);

			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();

			if(msg != null)
				addActionMessage(msg);
			else
				addActionMessage("Não foi possível gerar o relatório");

			formFiltro();
 			return Action.INPUT;
		}
	}

	public Collection<AreaOrganizacional> getAreaOrganizacionals()
	{
		return areaOrganizacionals;
	}

	public void setAreaOrganizacionals(
			Collection<AreaOrganizacional> areaOrganizacionals)
	{
		this.areaOrganizacionals = areaOrganizacionals;
	}

	public void setAreaOrganizacionalManager(
			AreaOrganizacionalManager areaOrganizacionalManager)
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

	public Collection<Colaborador> getDataSource()
	{
		return dataSource;
	}

	public void setDataSource(Collection<Colaborador> dataSource)
	{
		this.dataSource = dataSource;
	}

	@SuppressWarnings("rawtypes")
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

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
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

	public void setConfiguracaoCampoExtraManager(ConfiguracaoCampoExtraManager configuracaoCampoExtraManager)
	{
		this.configuracaoCampoExtraManager = configuracaoCampoExtraManager;
	}

	public Collection<ConfiguracaoCampoExtra> getConfiguracaoCampoExtras()
	{
		return configuracaoCampoExtras;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager)
	{
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public boolean isHabilitaCampoExtra()
	{
		return habilitaCampoExtra;
	}

	public void setHabilitaCampoExtra(boolean habilitaCampoExtra)
	{
		this.habilitaCampoExtra = habilitaCampoExtra;
	}

	public CamposExtras getCamposExtras()
	{
		return camposExtras;
	}

	public void setCamposExtras(CamposExtras camposExtras)
	{
		this.camposExtras = camposExtras;
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

	public Empresa getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public Boolean getCompartilharColaboradores()
	{
		return compartilharColaboradores;
	}

	public void setCargosCheck(String[] cargosCheck)
	{
		this.cargosCheck = cargosCheck;
	}

	public void setCargosCheckList(Collection<CheckBox> cargosCheckList)
	{
		this.cargosCheckList = cargosCheckList;
	}
	
	public Collection<CheckBox> getCargosCheckList()
	{
		return cargosCheckList;
	}

	public String[] getAreasCheck()
	{
		return areasCheck;
	}

	public void setAreasCheck(String[] areasCheck)
	{
		this.areasCheck = areasCheck;
	}

	public String getReportTitle()
	{
		return reportTitle;
	}
}