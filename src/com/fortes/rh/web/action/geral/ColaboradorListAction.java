package com.fortes.rh.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.dicionario.Mes;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.ibm.icu.util.Calendar;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings("unchecked")
public class ColaboradorListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private ColaboradorManager colaboradorManager = null;
	private EstabelecimentoManager estabelecimentoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private CargoManager cargoManager;
	private EmpresaManager empresaManager;
	
	private Collection<Colaborador> colaboradors = null;
	private Colaborador colaborador;
	private String nomeBusca;
	private String cpfBusca;
	private String matriculaBusca;
	private Collection<Colaborador> colaboradores;
	private int pageAnt;
	private Collection<Estabelecimento> estabelecimentosList = new ArrayList<Estabelecimento>();
	private Collection<AreaOrganizacional> areasList = new ArrayList<AreaOrganizacional>();
	private Collection<Cargo> cargosList = new ArrayList<Cargo>();
	private Estabelecimento estabelecimento = new Estabelecimento();
	private AreaOrganizacional areaOrganizacional = new AreaOrganizacional();
	private Cargo cargo = new Cargo();
	private Empresa empresa;
	
	private Map situacaos = new SituacaoColaborador();
	private String situacao;

	private Map<String,Object> parametros = new HashMap<String, Object>();
	private String[] areasCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] estabelecimentosCheck;
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private Collection<Empresa> empresas;
	
	private boolean exibirNomeComercial;
	
	private char exibir = ' ';
	
	private int mes;
	private Map meses = new Mes();
	
	//relatorio de admitidos
	private Date dataIni;
	private Date dataFim;
	private boolean exibirSomenteAtivos;

	private Long[] empresaIds;//repassado para o DWR

	public String list() throws Exception
	{
		Collection<AreaOrganizacional> areaOrganizacionalsTmp = areaOrganizacionalManager.findAllList(getEmpresaSistema().getId(), AreaOrganizacional.TODAS);
		areasList = areaOrganizacionalManager.montaFamilia(areaOrganizacionalsTmp);
		CollectionUtil<AreaOrganizacional> cu1 = new CollectionUtil<AreaOrganizacional>();
		areasList = cu1.sortCollectionStringIgnoreCase(areasList, "descricao");

		estabelecimentosList = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		cargosList = cargoManager.findAllSelect(getEmpresaSistema().getId(), "nomeMercado");

		if(StringUtils.isNotBlank(cpfBusca))
			cpfBusca = StringUtil.removeMascara(cpfBusca);

		if(situacao == null)
			situacao = "A";

		Map parametros = new HashMap();
		parametros.put("nomeBusca", nomeBusca);
		parametros.put("cpfBusca", cpfBusca);
		parametros.put("matriculaBusca", matriculaBusca);
		parametros.put("empresaId", getEmpresaSistema().getId());
		parametros.put("areaId", areaOrganizacional.getId());
		parametros.put("estabelecimentoId", estabelecimento.getId());
		parametros.put("cargoId", cargo.getId());
		parametros.put("situacao", situacao);

		//BACALHAU, refatorar outra consulta que ta com HQL, essa é em SQL...ajustar size ta pegando o tamanho da lista
		setTotalSize(colaboradorManager.getCountComHistoricoFuturoSQL(parametros));
		colaboradors = colaboradorManager.findComHistoricoFuturoSQL(getPage(), getPagingSize(), parametros);

		if (colaboradors == null || colaboradors.size() == 0)
			addActionMessage("Não existem colaboradores a serem listados.");

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		colaboradorManager.remove(colaborador, getEmpresaSistema());
		addActionMessage("Colaborador excluído com sucesso.");

		return Action.SUCCESS;
	}

	public String prepareRelatorioAniversariantes()
	{
		prepareEmpresas("ROLE_REL_ANIVERSARIANTES");
		
		Calendar hoje = Calendar.getInstance();
		mes = hoje.get(Calendar.MONTH) + 1;
		
		return SUCCESS;
	}

	private void prepareEmpresas(String role)
	{
		empresas = empresaManager.findByUsuarioPermissao(SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), role);
		CollectionUtil<Empresa> clu = new CollectionUtil<Empresa>();
		empresaIds = clu.convertCollectionToArrayIds(empresas);
		
		empresa = getEmpresaSistema();
	}

	public String relatorioAniversariantes()
	{
		try
		{
			boolean exibirCargo = (exibir == 'C');
			boolean exibirArea = (exibir == 'A');
			
			empresaIds = empresaManager.selecionaEmpresa(empresa, SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), "ROLE_REL_ANIVERSARIANTES");
			colaboradors = colaboradorManager.findAniversariantes(empresaIds, mes, LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(estabelecimentosCheck));
			
			parametros = RelatorioUtil.getParametrosRelatorio("Aniversariantes do mês: " + meses.get(mes), getEmpresaSistema(), null);
			parametros.put("EXIBIR_NOME_COMERCIAL", exibirNomeComercial);
			parametros.put("EXIBIR_CARGO", exibirCargo);
			parametros.put("EXIBIR_AREA", exibirArea);
		}
		catch (ColecaoVaziaException e)
		{
			addActionMessage(e.getMessage());
			prepareRelatorioAniversariantes();
			areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
			estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);

			return Action.INPUT;
		}
		catch (Exception e)
		{
			addActionError("Erro ao gerar relatório");
			e.printStackTrace();
			prepareRelatorioAniversariantes();

			return Action.INPUT;
		}
		return SUCCESS;
	}

	public String prepareRelatorioAdmitidos()
	{
		prepareEmpresas("ROLE_REL_ADMITIDOS");
		return SUCCESS;
	}
	
	public String relatorioAdmitidos()
	{
		try
		{
			empresaIds = empresaManager.selecionaEmpresa(empresa, SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), "ROLE_REL_ADMITIDOS");
			colaboradors = colaboradorManager.findAdmitidos(empresaIds, dataIni, dataFim, LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), exibirSomenteAtivos);
			
			String filtro = "Período : " + DateUtil.formataDiaMesAno(dataIni) + " a " + DateUtil.formataDiaMesAno(dataFim);
			
			parametros = RelatorioUtil.getParametrosRelatorio("Relatório de Admitidos ", getEmpresaSistema(), filtro);
			parametros.put("SOMENTE_ATIVOS", exibirSomenteAtivos);
		}
		catch (ColecaoVaziaException e)
		{
			addActionMessage(e.getMessage());
			prepareRelatorioAdmitidos();

			return Action.INPUT;
		}
		catch (Exception e)
		{
			addActionError("Erro ao gerar relatório");
			e.printStackTrace();
			prepareRelatorioAdmitidos();

			return Action.INPUT;
		}
		return SUCCESS;
	}

	public Collection getColaboradors()
	{
		return colaboradors;
	}

	public Colaborador getColaborador()
	{
		if(colaborador == null)
			colaborador = new Colaborador();
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador=colaborador;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager=colaboradorManager;
	}

	public String getCpfBusca()
	{
		return cpfBusca;
	}

	public void setCpfBusca(String cpfBusca)
	{
		this.cpfBusca = cpfBusca;
	}

	public String getNomeBusca()
	{
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca)
	{
		this.nomeBusca = StringUtil.retiraAcento(nomeBusca);
	}

	public Collection<Colaborador> getColaboradores()
	{
		return colaboradores;
	}

	public void setColaboradores(Collection<Colaborador> colaboradores)
	{
		this.colaboradores = colaboradores;
	}

	public int getPageAnt()
	{
		return pageAnt;
	}

	public void setPageAnt(int pageAnt)
	{
		this.pageAnt = pageAnt;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public void setCargoManager(CargoManager cargoManager)
	{
		this.cargoManager = cargoManager;
	}

	public Map getSituacaos()
	{
		return situacaos;
	}

	public String getMatriculaBusca()
	{
		return matriculaBusca;
	}

	public void setMatriculaBusca(String matriculaBusca)
	{
		this.matriculaBusca = StringUtil.retiraAcento(matriculaBusca);
	}

	public String getSituacao()
	{
		return situacao;
	}

	public void setSituacao(String situacao)
	{
		this.situacao = situacao;
	}

	public AreaOrganizacional getAreaOrganizacional()
	{
		return areaOrganizacional;
	}

	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional)
	{
		this.areaOrganizacional = areaOrganizacional;
	}

	public Collection<AreaOrganizacional> getAreasList()
	{
		return areasList;
	}

	public void setAreasList(Collection<AreaOrganizacional> areasList)
	{
		this.areasList = areasList;
	}

	public Cargo getCargo()
	{
		return cargo;
	}

	public void setCargo(Cargo cargo)
	{
		this.cargo = cargo;
	}

	public Collection<Cargo> getCargosList()
	{
		return cargosList;
	}

	public void setCargosList(Collection<Cargo> cargosList)
	{
		this.cargosList = cargosList;
	}

	public Estabelecimento getEstabelecimento()
	{
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento)
	{
		this.estabelecimento = estabelecimento;
	}

	public Collection<Estabelecimento> getEstabelecimentosList()
	{
		return estabelecimentosList;
	}

	public void setEstabelecimentosList(Collection<Estabelecimento> estabelecimentosList)
	{
		this.estabelecimentosList = estabelecimentosList;
	}

	public String formPrint() throws Exception
	{
		return Action.SUCCESS;
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

	public boolean isExibirNomeComercial()
	{
		return exibirNomeComercial;
	}

	public void setExibirNomeComercial(boolean exibirNomeComercial)
	{
		this.exibirNomeComercial = exibirNomeComercial;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public int getMes()
	{
		return mes;
	}

	public void setMes(int mes)
	{
		this.mes = mes;
	}

	public Map getMeses()
	{
		return meses;
	}

	public char getExibir() {
		return exibir;
	}

	public void setExibir(char exibir) {
		this.exibir = exibir;
	}

	public Date getDataIni() {
		return dataIni;
	}

	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public boolean isExibirSomenteAtivos() {
		return exibirSomenteAtivos;
	}

	public void setExibirSomenteAtivos(boolean exibirSomenteAtivos) {
		this.exibirSomenteAtivos = exibirSomenteAtivos;
	}

	public void setEmpresaManager(EmpresaManager empresaManager)
	{
		this.empresaManager = empresaManager;
	}

	public Collection<Empresa> getEmpresas()
	{
		return empresas;
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
}