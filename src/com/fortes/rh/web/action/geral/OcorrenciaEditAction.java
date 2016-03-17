package com.fortes.rh.web.action.geral;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorOcorrenciaManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.OcorrenciaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.TipoRelatorio;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.geral.relatorio.ColaboradorOcorrenciaRelatorio;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings({"serial","unchecked"})
public class OcorrenciaEditAction extends MyActionSupportEdit
{
	private OcorrenciaManager ocorrenciaManager;
	private ColaboradorOcorrenciaManager colaboradorOcorrenciaManager;
	private EmpresaManager empresaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private UsuarioEmpresaManager usuarioEmpresaManager;

	private Ocorrencia ocorrencia;
	@SuppressWarnings("unused")
	private List emptyDataSource = new ArrayList();

	private Collection<ColaboradorOcorrencia> dataSource;
	private Collection<Colaborador> colaboradores;
	private Collection<ColaboradorOcorrencia> colaboradoresOcorrencias;

	private Collection<ColaboradorOcorrenciaRelatorio> colaboradorOcorrenciaRelatorios;

	private Map parametros =  new HashMap();
	private String colaborador;
	private Date dataIni;
	private Date dataFim;
	private Long ocorrenciaId;

	private Collection<CheckBox> grupoCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> areaCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> cargoCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> ocorrenciaCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> colaboradorCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> estabelecimentoCheckList = new ArrayList<CheckBox>();

	private String[] grupoCheck;
	private String[] areaCheck;
	private String[] cargoCheck;
	private String[] ocorrenciaCheck;
	private String[] colaboradorCheck;
	private String[] estabelecimentoCheck;

	private boolean detalhamento = true;
	private boolean empresaIntegradaComAC;
	
	private Empresa empresa;
	private Long[] empresaIds;//repassado para o DWR
	private Collection<Empresa> empresas;
	private Boolean compartilharColaboradores;
	private Boolean exibirProvidencia;
	private boolean agruparPorColaborador = true;
	
	private Map situacaos = new SituacaoColaborador();
	
	private String reportTitle;
	private String reportFilter;
	private Character tipo = TipoRelatorio.PDF;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(ocorrencia != null && ocorrencia.getId() != null)
			ocorrencia = (Ocorrencia) ocorrenciaManager.findById(ocorrencia.getId());
		
		empresaIntegradaComAC = getEmpresaSistema().isAcIntegra();
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		
		if(ocorrencia != null && ocorrencia.getId() != null && empresaIntegradaComAC)
			ocorrencia.setIntegraAC(true);
		
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String insertOrUpdate() throws Exception
	{
		try {
			ocorrencia.setEmpresa(getEmpresaSistema());
			ocorrenciaManager.saveOrUpdate(ocorrencia, getEmpresaSistema());
		} catch (Exception e)
		{
			prepareInsert();
			e.printStackTrace();
			
			if (e instanceof InvocationTargetException && ((InvocationTargetException)e).getTargetException() instanceof IntegraACException) 
				addActionError("Cadastro não pôde ser realizado no Fortes Pessoal.");
			else 
				addActionError("Cadastro não pôde ser realizado.");

			return Action.INPUT;
		}

		return Action.SUCCESS;
	}

	public String prepareRelatorioOcorrencia() throws Exception
	{
		compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
		empresas = empresaManager.findEmpresasPermitidas(compartilharColaboradores , getEmpresaSistema().getId(), SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), "ROLE_REL_OCORRENCIA");
		
		CollectionUtil<Empresa> clu = new CollectionUtil<Empresa>();
		empresaIds = clu.convertCollectionToArrayIds(empresas);
		
//		colaborador = getUsuarioLogado().getColaborador().getId().toString();
		
		empresa = getEmpresaSistema();
		
		return Action.SUCCESS;
	}

	public String buscaOcorrencia() throws Exception
	{
		try
		{
			Collection<Long> empresaIds = new ArrayList<Long>();
			if(empresa == null || empresa.getId() == null)
			{
				compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
				empresas = empresaManager.findEmpresasPermitidas(compartilharColaboradores , getEmpresaSistema().getId(), SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), "ROLE_REL_OCORRENCIA");
				empresaIds = LongUtil.collectionToCollectionLong(empresas);
			}else
			{
				empresaIds.add(empresa.getId());
			}
			
			Collection<Long> ocorrenciaIds = LongUtil.arrayStringToCollectionLong(ocorrenciaCheck);
			Collection<Long> colaboradorIds = LongUtil.arrayStringToCollectionLong(colaboradorCheck);
			Collection<Long> estabelecimentoIds = LongUtil.arrayStringToCollectionLong(estabelecimentoCheck);

			Collection<Long> areaIds = LongUtil.arrayStringToCollectionLong(areaCheck);
			if ( areaIds.size() == 0 ) {
				Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
				for (Long empresaId : empresaIds) {
					if ( usuarioEmpresaManager.containsRole(getUsuarioLogado().getId(), empresaId, "ROLE_VER_AREAS") ) {
						areas.addAll(areaOrganizacionalManager.findByEmpresa(empresaId));
					} else {
						areas.addAll(areaOrganizacionalManager.findAreasByUsuarioResponsavel(getUsuarioLogado(), empresaId)); 
					}
				}
				areaIds = LongUtil.collectionToCollectionLong(areas);
			}
			colaboradoresOcorrencias = colaboradorOcorrenciaManager.filtrarOcorrencias(empresaIds, dataIni, dataFim, ocorrenciaIds, areaIds, estabelecimentoIds, colaboradorIds, detalhamento, agruparPorColaborador);

			if(colaboradoresOcorrencias == null || colaboradoresOcorrencias.isEmpty())
				throw new ColecaoVaziaException("Não existem dados para o filtro informado.");

			String acao, extensao;
			
			if (tipo.equals(TipoRelatorio.XLS))
			{
				extensao = "XLS";
				reportFilter = "Período: " + DateUtil.formataDiaMesAno(dataIni) + " à " + DateUtil.formataDiaMesAno(dataFim);
				reportTitle = "Ranking de Ocorrências";
			}
			else
			{
				extensao = "PDF";
				parametros.put("dataIni", dataIni);
				parametros.put("dataFim", dataFim);
				
				String filtro = "Período: " + DateUtil.formataDiaMesAno(dataIni) + " à " + DateUtil.formataDiaMesAno(dataFim);
				parametros = RelatorioUtil.getParametrosRelatorio("Ranking de Ocorrências", getEmpresaSistema(), filtro);
			}
			
			if(detalhamento)
			{
				if (agruparPorColaborador)
				{
					if(exibirProvidencia)
						acao = "comProvidencia";
					else
						acao = "semProvidencia";
				}
				else
					acao = "providenciaAgrupada";
			}
			else
				acao = "relatorioSemDetalhe";
			
			return acao + extensao;
		}
		catch (ColecaoVaziaException cE)
		{
			addActionMessage(cE.getMessage());
			prepareRelatorioOcorrencia();
			return Action.INPUT;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível gerar o relatório");
			prepareRelatorioOcorrencia();
			return Action.INPUT;
		}

	}
	
	public Object getModel()
	{
		return getOcorrencia();
	}

	public Ocorrencia getOcorrencia()
	{
		if(ocorrencia == null)
			ocorrencia = new Ocorrencia();
		return ocorrencia;
	}

	public void setOcorrencia(Ocorrencia ocorrencia)
	{
		this.ocorrencia = ocorrencia;
	}

	public void setOcorrenciaManager(OcorrenciaManager ocorrenciaManager)
	{
		this.ocorrenciaManager = ocorrenciaManager;
	}

	public Map getParametros()
	{
		return parametros;
	}

	public void setParametros(Map parametros)
	{
		this.parametros = parametros;
	}

	public String getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(String colaborador)
	{
		this.colaborador = colaborador;
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

	public Long getOcorrenciaId()
	{
		return ocorrenciaId;
	}

	public void setOcorrenciaId(Long ocorrenciaId)
	{
		this.ocorrenciaId = ocorrenciaId;
	}

	public String[] getAreaCheck()
	{
		return areaCheck;
	}

	public void setAreaCheck(String[] areaCheck)
	{
		this.areaCheck = areaCheck;
	}

	public Collection<CheckBox> getAreaCheckList()
	{
		return areaCheckList;
	}

	public void setAreaCheckList(Collection<CheckBox> areaCheckList)
	{
		this.areaCheckList = areaCheckList;
	}

	public String[] getCargoCheck()
	{
		return cargoCheck;
	}

	public void setCargoCheck(String[] cargoCheck)
	{
		this.cargoCheck = cargoCheck;
	}

	public Collection<CheckBox> getCargoCheckList()
	{
		return cargoCheckList;
	}

	public void setCargoCheckList(Collection<CheckBox> cargoCheckList)
	{
		this.cargoCheckList = cargoCheckList;
	}

	public String[] getGrupoCheck()
	{
		return grupoCheck;
	}

	public void setGrupoCheck(String[] grupoCheck)
	{
		this.grupoCheck = grupoCheck;
	}

	public Collection<CheckBox> getGrupoCheckList()
	{
		return grupoCheckList;
	}

	public void setGrupoCheckList(Collection<CheckBox> grupoCheckList)
	{
		this.grupoCheckList = grupoCheckList;
	}

	public String[] getOcorrenciaCheck()
	{
		return ocorrenciaCheck;
	}

	public void setOcorrenciaCheck(String[] ocorrenciaCheck)
	{
		this.ocorrenciaCheck = ocorrenciaCheck;
	}

	public Collection<CheckBox> getOcorrenciaCheckList()
	{
		return ocorrenciaCheckList;
	}

	public void setOcorrenciaCheckList(Collection<CheckBox> ocorrenciaCheckList)
	{
		this.ocorrenciaCheckList = ocorrenciaCheckList;
	}

	public Collection<CheckBox> getColaboradorCheckList()
	{
		return colaboradorCheckList;
	}

	public void setColaboradorCheckList(Collection<CheckBox> colaboradorCheckList)
	{
		this.colaboradorCheckList = colaboradorCheckList;
	}

	public String[] getColaboradorCheck()
	{
		return colaboradorCheck;
	}

	public void setColaboradorCheck(String[] colaboradorCheck)
	{
		this.colaboradorCheck = colaboradorCheck;
	}

	public Collection<ColaboradorOcorrencia> getDataSource()
	{
		return dataSource;
	}

	public void setDataSource(Collection<ColaboradorOcorrencia> dataSource)
	{
		this.dataSource = dataSource;
	}

	public boolean isDetalhamento()
	{
		return detalhamento;
	}

	public void setDetalhamento(boolean detalhamento)
	{
		this.detalhamento = detalhamento;
	}

	public Collection<Colaborador> getColaboradores()
	{
		return colaboradores;
	}

	public void setColaboradores(Collection<Colaborador> colaboradores)
	{
		this.colaboradores = colaboradores;
	}

	public Collection<ColaboradorOcorrencia> getColaboradoresOcorrencias()
	{
		return colaboradoresOcorrencias;
	}

	public void setColaboradoresOcorrencias(Collection<ColaboradorOcorrencia> colaboradoresOcorrencias)
	{
		this.colaboradoresOcorrencias = colaboradoresOcorrencias;
	}

	public void setColaboradorOcorrenciaManager(ColaboradorOcorrenciaManager colaboradorOcorrenciaManager)
	{
		this.colaboradorOcorrenciaManager = colaboradorOcorrenciaManager;
	}

	public List getEmptyDataSource()
	{
		return new CollectionUtil().convertCollectionToList(colaboradorOcorrenciaRelatorios);
	}

	public void setEmptyDataSource(List emptyDataSource)
	{
		this.emptyDataSource = emptyDataSource;
	}

	public Collection<CheckBox> getEstabelecimentoCheckList()
	{
		return estabelecimentoCheckList;
	}

	public void setEstabelecimentoCheckList(Collection<CheckBox> estabelecimentoCheckList)
	{
		this.estabelecimentoCheckList = estabelecimentoCheckList;
	}

	public String[] getEstabelecimentoCheck()
	{
		return estabelecimentoCheck;
	}

	public void setEstabelecimentoCheck(String[] estabelecimentoCheck)
	{
		this.estabelecimentoCheck = estabelecimentoCheck;
	}

	public boolean isEmpresaIntegradaComAC()
	{
		return empresaIntegradaComAC;
	}

	public Empresa getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public Long[] getEmpresaIds()
	{
		return empresaIds;
	}

	public Collection<Empresa> getEmpresas()
	{
		return empresas;
	}

	public void setEmpresaManager(EmpresaManager empresaManager)
	{
		this.empresaManager = empresaManager;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager)
	{
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public void setAreaOrganizacionalManager(
			AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}
	
	public void setUsuarioEmpresaManager(UsuarioEmpresaManager usuarioEmpresaManager) {
		this.usuarioEmpresaManager = usuarioEmpresaManager;
	}

	public Boolean getCompartilharColaboradores()
	{
		return compartilharColaboradores;
	}

	public Boolean getExibirProvidencia()
	{
		return exibirProvidencia;
	}

	public void setExibirProvidencia(Boolean exibirProvidencia)
	{
		this.exibirProvidencia = exibirProvidencia;
	}

	public void setEmpresas(Collection<Empresa> empresas)
	{
		this.empresas = empresas;
	}

	public boolean isAgruparPorColaborador()
	{
		return agruparPorColaborador;
	}

	public void setAgruparPorColaborador(boolean agruparPorColaborador)
	{
		this.agruparPorColaborador = agruparPorColaborador;
	}

	public Map getSituacaos()
	{
		return situacaos;
	}

	public String getReportTitle() {
		return reportTitle;
	}

	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}

	public String getReportFilter() {
		return reportFilter;
	}

	public void setReportFilter(String reportFilter) {
		this.reportFilter = reportFilter;
	}

	public Character getTipo() {
		return tipo;
	}

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}
}