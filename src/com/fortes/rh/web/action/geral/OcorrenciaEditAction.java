package com.fortes.rh.web.action.geral;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.fortes.rh.business.geral.ColaboradorOcorrenciaManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.OcorrenciaManager;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.geral.relatorio.ColaboradorOcorrenciaRelatorio;
import com.fortes.rh.model.geral.relatorio.OcorrenciaRelatorio;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
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
	private boolean ponto = true;
	//variavel apenas para marcar o checkbox
	private boolean det = true;
	private boolean empresaIntegradaComAC;
	
	private Empresa empresa;
	private Long[] empresaIds;//repassado para o DWR
	private Collection<Empresa> empresas;

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
		}
		catch (IntegraACException e)
		{
			prepareInsert();
			e.printStackTrace();
			addActionError("Cadastro não pôde ser realizado no AC Pessoal.");
			return Action.INPUT;
		}
		catch (Exception e)
		{
			prepareInsert();
			e.printStackTrace();
			addActionError("Cadastro não pôde ser realizado.");
			return Action.INPUT;
		}

		return Action.SUCCESS;
	}

	public String prepareRelatorioOcorrencia() throws Exception
	{
		empresas = empresaManager.findByUsuarioPermissao(SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), "ROLE_REL_OCORRENCIA");
		CollectionUtil<Empresa> clu = new CollectionUtil<Empresa>();
		empresaIds = clu.convertCollectionToArrayIds(empresas);
		
		empresa = getEmpresaSistema();
		
		return Action.SUCCESS;
	}

	public String buscaOcorrencia() throws Exception
	{
		String msg = null;
		try
		{
			Long[] colaboradorCheckLong = StringUtil.stringToLong(colaboradorCheck);
			Long[] ocorrenciaCheckLong = StringUtil.stringToLong(ocorrenciaCheck);
			Long[] estabelecimentoCheckLong = StringUtil.stringToLong(estabelecimentoCheck);

			parametros.put("dataIni", dataIni);
			parametros.put("dataFim", dataFim);

			colaboradoresOcorrencias = colaboradorOcorrenciaManager.filtrar(ocorrenciaCheckLong, colaboradorCheckLong, estabelecimentoCheckLong, parametros);

			if(colaboradoresOcorrencias == null || colaboradoresOcorrencias.isEmpty())
				throw new Exception(ResourceBundle.getBundle("application").getString("error.relatorio.vazio"));

			colaboradorOcorrenciaRelatorios = montaRelatorio(colaboradoresOcorrencias);

			CollectionUtil<ColaboradorOcorrenciaRelatorio> cu = new CollectionUtil<ColaboradorOcorrenciaRelatorio>();
			colaboradorOcorrenciaRelatorios = cu.sortCollectionDesc(colaboradorOcorrenciaRelatorios, "qtdPontos");

			parametros.put("PONTUACAO", ponto);
			String filtro = "Período: " + DateUtil.formataDiaMesAno(dataIni) + " à " + DateUtil.formataDiaMesAno(dataFim);
			parametros = RelatorioUtil.getParametrosRelatorio("Ranking de Ocorrências", getEmpresaSistema(), filtro);

			if(detalhamento)
				return Action.SUCCESS;
			else
				return "relatorio_sem_detalhe";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(msg != null)
				addActionMessage(msg);
			else
				addActionMessage("Não foi possível gerar o relatório");

			prepareRelatorioOcorrencia();
			return Action.INPUT;
		}

	}

	public Collection<ColaboradorOcorrenciaRelatorio> montaRelatorio(Collection<ColaboradorOcorrencia> colaboradoresOcorrencias)
	{
		Collection<ColaboradorOcorrenciaRelatorio> colaboradorOcorrenciaRelatorios = new ArrayList<ColaboradorOcorrenciaRelatorio>();

		for(ColaboradorOcorrencia colaboradorOcorrencia : colaboradoresOcorrencias)
		{
			adicionaNaCollecao(colaboradorOcorrenciaRelatorios, colaboradorOcorrencia);
		}

		return colaboradorOcorrenciaRelatorios;
	}


	public void adicionaNaCollecao(Collection<ColaboradorOcorrenciaRelatorio> colaboradorOcorrenciaRelatorios, ColaboradorOcorrencia colaboradorOcorrencia)
	{
		boolean jaTem = false;

		for(ColaboradorOcorrenciaRelatorio colaboradorOcorrenciaRelatorio : colaboradorOcorrenciaRelatorios){
			if(colaboradorOcorrenciaRelatorio.getColaborador().getId().doubleValue() == colaboradorOcorrencia.getColaborador().getId().doubleValue()){

				OcorrenciaRelatorio ocorrenciaRelatorio = new OcorrenciaRelatorio();
				ocorrenciaRelatorio.setOcorrencia(colaboradorOcorrencia.getOcorrencia());
				ocorrenciaRelatorio.setDataFim(colaboradorOcorrencia.getDataFim());
				ocorrenciaRelatorio.setDataIni(colaboradorOcorrencia.getDataIni());
				ocorrenciaRelatorio.setObservacao(colaboradorOcorrencia.getObservacao());

				colaboradorOcorrenciaRelatorio.getOcorrencias().add(ocorrenciaRelatorio);
				colaboradorOcorrenciaRelatorio.setQtdPontos(colaboradorOcorrenciaRelatorio.getQtdPontos() + colaboradorOcorrencia.getOcorrencia().getPontuacao());
				jaTem = true;

				break;
			}
		}

		if(!jaTem){
			colaboradorOcorrenciaRelatorios.add(transformaOcorrenciaRelatorio(colaboradorOcorrencia));
		}
	}

	private ColaboradorOcorrenciaRelatorio transformaOcorrenciaRelatorio(ColaboradorOcorrencia colaboradorOcorrencia)
	{
		ColaboradorOcorrenciaRelatorio colaboradorOcorrenciaRelatorio = new ColaboradorOcorrenciaRelatorio();

		colaboradorOcorrenciaRelatorio.setColaborador(colaboradorOcorrencia.getColaborador());

		OcorrenciaRelatorio ocorrenciaRelatorio = new OcorrenciaRelatorio();
		ocorrenciaRelatorio.setOcorrencia(colaboradorOcorrencia.getOcorrencia());
		ocorrenciaRelatorio.setDataFim(colaboradorOcorrencia.getDataFim());
		ocorrenciaRelatorio.setDataIni(colaboradorOcorrencia.getDataIni());
		ocorrenciaRelatorio.setObservacao(colaboradorOcorrencia.getObservacao());

		Collection<OcorrenciaRelatorio> ocorrencias = new ArrayList<OcorrenciaRelatorio>();
		ocorrencias.add(ocorrenciaRelatorio);

		colaboradorOcorrenciaRelatorio.setOcorrencias(ocorrencias);
		colaboradorOcorrenciaRelatorio.setQtdPontos(colaboradorOcorrencia.getOcorrencia().getPontuacao());

		return colaboradorOcorrenciaRelatorio;
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

	public boolean isPonto()
	{
		return ponto;
	}

	public void setPonto(boolean ponto)
	{
		this.ponto = ponto;
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

	public boolean isDet()
	{
		return det;
	}

	public void setDet(boolean det)
	{
		this.det = det;
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


}