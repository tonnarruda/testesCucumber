package com.fortes.rh.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.MotivoDemissaoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.MotivoDemissao;
import com.fortes.rh.model.geral.relatorio.MotivoDemissaoQuantidade;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;


public class MotivoDemissaoListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private MotivoDemissaoManager motivoDemissaoManager;
	private ColaboradorManager colaboradorManager;
	private EmpresaManager empresaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;

	private Collection<MotivoDemissao> motivoDemissaos = null;
	private Collection<Colaborador> colaboradores;
	private Collection<MotivoDemissaoQuantidade> motivoDemissaoQuantidades;

	private MotivoDemissao motivoDemissao;

	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> cargosCheckList = new ArrayList<CheckBox>();
	private String[] estabelecimentosCheck;
	private String[] areasCheck;
	private String[] cargosCheck;

	private Date dataIni;
	private Date dataFim;

	private boolean listaColaboradores;
	
	private boolean agruparPorMotivo = true;
	private boolean exibirObservacao = false;
	
	private Empresa empresa;
	private Collection<Empresa> empresas;
	private Long[] empresaIds;//repassado para o DWR

	private Map<String, Object> parametros = new HashMap<String, Object>();
	private Boolean compartilharColaboradores;
	
	
	public String list() throws Exception
	{
		String[] keys = new String[]{"empresa.id"};
		Object[] values = new Object[]{getEmpresaSistema().getId()};
		String[] orders = new String[]{"motivo asc"};

		setTotalSize(motivoDemissaoManager.getCount(keys, values));
		motivoDemissaos = motivoDemissaoManager.find(getPage(), getPagingSize(), keys, values, orders);

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		motivoDemissaoManager.remove(motivoDemissao.getId());
		addActionMessage("Motivo excluído com sucesso.");

		return Action.SUCCESS;
	}

	public String prepareRelatorioMotivoDemissao() throws Exception
	{
		compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
		empresas = empresaManager.findEmpresasPermitidas(compartilharColaboradores , getEmpresaSistema().getId(),SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), "ROLE_REL_MOTIVO_DEMISSAO");
		
		CollectionUtil<Empresa> clu = new CollectionUtil<Empresa>();
		empresaIds = clu.convertCollectionToArrayIds(empresas);
		
		empresa = getEmpresaSistema();
		return Action.SUCCESS;
	}

	public String imprimeRelatorioMotivoDemissao() throws Exception
	{
		try
		{
			colaboradores = colaboradorManager.findColaboradoresMotivoDemissao(LongUtil.arrayStringToArrayLong(estabelecimentosCheck), LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(cargosCheck), dataIni, dataFim);
		}
		catch (Exception e)
		{
			addActionMessage(e.getMessage());
			e.printStackTrace();
			prepareRelatorioMotivoDemissao();

			return Action.INPUT;
		}
		
		if (agruparPorMotivo)
		{
			if(exibirObservacao)
				return "successComObservacao";
			else
				return Action.SUCCESS;			
		}
		else
		{
			colaboradores = new CollectionUtil<Colaborador>().sortCollectionStringIgnoreCase(colaboradores, "nome");
			if(exibirObservacao)
				return "successSemAgruparComObservacao";
			else
				return "successSemAgrupar";
		}
	}

	public String imprimeRelatorioMotivoDemissaoBasico() throws Exception
	{
		try
		{
			motivoDemissaoQuantidades = colaboradorManager.findColaboradoresMotivoDemissaoQuantidade(LongUtil.arrayStringToArrayLong(estabelecimentosCheck), LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(cargosCheck), dataIni, dataFim);
		}
		catch (Exception e)
		{
			addActionMessage(e.getMessage());
			e.printStackTrace();
			prepareRelatorioMotivoDemissao();

			return Action.INPUT;
		}

		return "successBasico";
	}

	public String relatorioMotivoDemissao() throws Exception
	{
		parametros = motivoDemissaoManager.getParametrosRelatorio(getEmpresaSistema(), dataIni, dataFim, parametros);
		parametros.put("EXIBIR_OBS", exibirObservacao);

		if(listaColaboradores)
			return imprimeRelatorioMotivoDemissao();
		else
			return imprimeRelatorioMotivoDemissaoBasico();
	}

	public Collection<MotivoDemissao> getMotivoDemissaos() {
		return motivoDemissaos;
	}


	public MotivoDemissao getMotivoDemissao(){
		if(motivoDemissao == null){
			motivoDemissao = new MotivoDemissao();
		}
		return motivoDemissao;
	}

	public void setMotivoDemissao(MotivoDemissao motivoDemissao){
		this.motivoDemissao=motivoDemissao;
	}

	public void setMotivoDemissaoManager(MotivoDemissaoManager motivoDemissaoManager){
		this.motivoDemissaoManager=motivoDemissaoManager;
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

	public boolean isListaColaboradores()
	{
		return listaColaboradores;
	}

	public void setListaColaboradores(boolean listaColaboradores)
	{
		this.listaColaboradores = listaColaboradores;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public Collection<Colaborador> getColaboradores()
	{
		return colaboradores;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public void setAreasCheckList(Collection<CheckBox> areasCheckList)
	{
		this.areasCheckList = areasCheckList;
	}

	public void setCargosCheckList(Collection<CheckBox> cargosCheckList)
	{
		this.cargosCheckList = cargosCheckList;
	}

	public void setEstabelecimentosCheckList(Collection<CheckBox> estabelecimentosCheckList)
	{
		this.estabelecimentosCheckList = estabelecimentosCheckList;
	}

	public Collection<MotivoDemissaoQuantidade> getMotivoDemissaoQuantidades()
	{
		return motivoDemissaoQuantidades;
	}

	public boolean isAgruparPorMotivo() {
		return agruparPorMotivo;
	}

	public void setAgruparPorMotivo(boolean agruparPorMotivo) {
		this.agruparPorMotivo = agruparPorMotivo;
	}

	public boolean isExibirObservacao()
	{
		return exibirObservacao;
	}

	public void setExibirObservacao(boolean exibirObservacao)
	{
		this.exibirObservacao = exibirObservacao;
	}

	public void setEmpresaManager(EmpresaManager empresaManager)
	{
		this.empresaManager = empresaManager;
	}

	public Empresa getEmpresa()
	{
		return empresa;
	}

	public Collection<Empresa> getEmpresas()
	{
		return empresas;
	}

	public Long[] getEmpresaIds()
	{
		return empresaIds;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public Boolean getCompartilharColaboradores() {
		return compartilharColaboradores;
	}

}