package com.fortes.rh.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

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
import com.fortes.rh.util.EmpresaUtil;
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

	@Autowired private AreaOrganizacionalManager areaOrganizacionalManager;
	@Autowired private ColaboradorManager colaboradorManager;
	@Autowired private ConfiguracaoCampoExtraManager configuracaoCampoExtraManager;
	@Autowired private ParametrosDoSistemaManager parametrosDoSistemaManager;
	@Autowired private EmpresaManager empresaManager;

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
	private Long[] empresasPermitidas;
	private boolean exibirSalario;
	private boolean exibirSalarioVariavel = false;
	
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
	
	private boolean exibirSalarioVariavel()
	{
		boolean exibirSalarioVariavel = false;
		
		if (empresa.getId() != null)
		{
			empresa = empresaManager.findByIdProjection(empresa.getId());
			exibirSalarioVariavel = empresa.isAcIntegra();
		}
		else
			exibirSalarioVariavel = empresaManager.checkEmpresaIntegradaAc();
		
		return exibirSalarioVariavel;
	}
	

	public String gerarRelatorioXLS() throws Exception
	{
		String retorno = gerarRelatorio();
		
		if(exibirSalario && !exibirSalarioVariavel)
			return "successRemuneracaoVariavelRH"; 
		else return retorno;
	}
	
	public String gerarRelatorio() throws Exception
	{
		String retorno = null;
		String msg = null;
		
		if(exibirSalario)
			exibirSalarioVariavel = exibirSalarioVariavel();
		
		try
		{
			Collection<Long> estabelecimentos = LongUtil.arrayStringToCollectionLong(estabelecimentosCheck);
			Collection<Long> areas = LongUtil.arrayStringToCollectionLong(areasCheck);
			Collection<Long> cargos = LongUtil.arrayStringToCollectionLong(cargosCheck);
				
			dataSource = colaboradorManager.findAreaOrganizacionalByAreas(habilitaCampoExtra, estabelecimentos, areas, cargos, camposExtras, null, null, null, null, null, null, null, SituacaoColaborador.ATIVO, null, EmpresaUtil.empresasSelecionadas(empresa.getId(),empresasPermitidas));
			if(dataSource == null || dataSource.isEmpty())
			{
				ResourceBundle bundle = ResourceBundle.getBundle("application");
				msg = bundle.getString("error.relatorio.vazio");
				throw new Exception(msg);
			}

			reportTitle = "Relatório de Colaboradores por Área Organizacional";

			dataSource = colaboradorManager.ordenaPorEstabelecimentoArea(dataSource, EmpresaUtil.empresasSelecionadas(empresa.getId(),empresasPermitidas));

			if ( exibirSalarioVariavel )
				colaboradorManager.getRemuneracaoVariavelFromAcPessoalByColaboradores(dataSource);
			
			parametros = areaOrganizacionalManager.getParametrosRelatorio("Relatório de Áreas Organizacionais", getEmpresaSistema(), null);
			parametros.put("EXIBIRSALARIO", exibirSalario);
			parametros.put("EXIBIRSALARIOVARIAVEL", exibirSalarioVariavel);
			
			retorno = Action.SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();

			if(msg != null)
				addActionMessage(msg);
			else
				addActionMessage("Não foi possível gerar o relatório");

			formFiltro();
			retorno = Action.INPUT;
		}
		if(Action.INPUT.equals(retorno))
			return Action.INPUT;
		else if (exibirSalarioVariavel)
			return "successRemuneracaoVariavel"; 
		else return retorno;
	}

	public Collection<AreaOrganizacional> getAreaOrganizacionals()
	{
		return areaOrganizacionals;
	}

	public void setAreaOrganizacionals(Collection<AreaOrganizacional> areaOrganizacionals)
	{
		this.areaOrganizacionals = areaOrganizacionals;
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

	public Collection<ConfiguracaoCampoExtra> getConfiguracaoCampoExtras()
	{
		return configuracaoCampoExtras;
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
	
	public void setEmpresasPermitidas(Long[] empresasPermitidas) {
		this.empresasPermitidas = empresasPermitidas;
	}
	
	public boolean isExibirSalario() 
	{
		return exibirSalario;
	}

	public void setExibirSalario(boolean exibirSalario) 
	{
		this.exibirSalario = exibirSalario;
	}
}