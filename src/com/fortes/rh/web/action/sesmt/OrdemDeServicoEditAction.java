package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.OrdemDeServicoManager;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.FiltroOrdemDeServico;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.OrdemDeServico;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("rawtypes")
public class OrdemDeServicoEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EstabelecimentoManager estabelecimentoManager;
	private OrdemDeServicoManager ordemDeServicoManager;
	private UsuarioEmpresaManager usuarioEmpresaManager;
	private ColaboradorManager colaboradorManager;
	private CargoManager cargoManager;

	private Collection<Estabelecimento> estabelecimentosList = new ArrayList<Estabelecimento>();
	private Collection<AreaOrganizacional> areasList = new ArrayList<AreaOrganizacional>();
	private Collection<OrdemDeServico> ordensDeServico = new ArrayList<OrdemDeServico>();
	private HistoricoColaborador historicoColaborador = new HistoricoColaborador();
	private Collection<Cargo> cargosList = new ArrayList<Cargo>();
	private Collection<Colaborador> colaboradores;
	private Map<String, Object> parametros;
	
	private OrdemDeServico ordemDeServico;
	private Colaborador colaborador = new Colaborador();
	
	private Map situacoes = new SituacaoColaborador();
	private Map filtrosOrdemDeServico = new FiltroOrdemDeServico();
	private String situacao = SituacaoColaborador.ATIVO;
	private String filtroOrdemDeServico = FiltroOrdemDeServico.TODOS;
	private OrdemDeServico ordemDeServicoAtual;

	public String listGerenciamentoOS() throws Exception
	{
		setAreasPermitidas();
		estabelecimentosList = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		cargosList = cargoManager.findAllSelect("nomeMercado", null, Cargo.TODOS, getEmpresaSistema().getId());
		
		colaborador.setEmpresa(getEmpresaSistema());
		setTotalSize(colaboradorManager.getCountColaboradorComESemOrdemDeServico(colaborador, historicoColaborador, LongUtil.collectionToArrayLong(areasList), situacao, filtroOrdemDeServico));
		colaboradores = colaboradorManager.findColaboradorComESemOrdemDeServico(colaborador, historicoColaborador, LongUtil.collectionToArrayLong(areasList), situacao, filtroOrdemDeServico, getPage(), getPagingSize());
		
		if (colaboradores == null || colaboradores.size() == 0)
			addActionMessage("Não existem colaboradores a serem listados para os filtros informados.");
		
		return Action.SUCCESS;
	}
	
	
	private void setAreasPermitidas() throws Exception {
		Collection<AreaOrganizacional> areaOrganizacionalsTmp = new ArrayList<AreaOrganizacional>();
		if(getUsuarioLogado().getId().equals(1L) || usuarioEmpresaManager.containsRole(getUsuarioLogado().getId(), getEmpresaSistema().getId(), "ROLE_VER_AREAS") ) 
			areaOrganizacionalsTmp.addAll(areaOrganizacionalManager.findByEmpresa(getEmpresaSistema().getId()));
		else 
			areaOrganizacionalsTmp.addAll(areaOrganizacionalManager.findAreasByUsuarioResponsavel(getUsuarioLogado(),  getEmpresaSistema().getId()));
		
		areasList = areaOrganizacionalManager.montaFamilia(areaOrganizacionalsTmp);
		CollectionUtil<AreaOrganizacional> cu1 = new CollectionUtil<AreaOrganizacional>();
		areasList = cu1.sortCollectionStringIgnoreCase(areasList, "descricao");
	}
	
	
	public String list() throws Exception
	{
		setTotalSize(ordemDeServicoManager.getCount(new String[]{"colaborador.id"}, new Long[]{colaborador.getId()}));
		colaborador = colaboradorManager.findComDadosBasicosParaOrdemDeServico(colaborador, new Date());
		ordensDeServico = ordemDeServicoManager.find(getPage(), getPagingSize(), new String[]{"colaborador.id"}, new Long[]{colaborador.getId()}, new String[]{"data"});
		ordemDeServicoAtual = ordemDeServicoManager.findUltimaOrdemDeServico(colaborador.getId());
		return Action.SUCCESS;
	}
	
	public String delete() throws Exception
	{
		try
		{
			ordemDeServicoManager.remove(ordemDeServico.getId());
			addActionSuccess("Ordem de serviço excluída com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Ocorreu um erro ao excluir a ordem de serviço.");
		}

		return list();
	}
	
	public String prepareInsert() throws Exception
	{
		ordemDeServico = ordemDeServicoManager.montaOrdemDeServico(ordemDeServico, colaborador, getEmpresaSistema(), new Date());
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		ordemDeServico = ordemDeServicoManager.findOrdemServicoProjection(ordemDeServico.getId());
		ordemDeServico = ordemDeServicoManager.montaOrdemDeServico(ordemDeServico, colaborador, getEmpresaSistema(), ordemDeServico.getData());
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		try {
			ordemDeServicoManager.save(ordemDeServico);
			addActionSuccess("Ordem de serviço gravada com sucesso.");
			return Action.SUCCESS;
		} catch (Exception e) {
			addActionError("Ocorreu um erro ao gravar a ordem de serviço.");
			prepareInsert();
			return Action.INPUT;
		}
	}

	public String update() throws Exception
	{
		try {
			ordemDeServicoManager.update(ordemDeServico);
			addActionSuccess("Ordem de serviço atualizada com sucesso.");
			return Action.SUCCESS;
		} catch (Exception e) {
			addActionError("Ocorreu um erro ao atualizar a ordem de serviço.");
			prepareUpdate();
			return Action.INPUT;
		}
	}
	
	public String imprimir(){
		ordemDeServico = ordemDeServicoManager.findOrdemServicoProjection(ordemDeServico.getId());
		ordensDeServico.add(ordemDeServico);
		
		if(!ordemDeServico.isImpressa()){
			ordemDeServico.setImpressa(true);
			ordemDeServicoManager.update(ordemDeServico);
		}
		
   	   	parametros = new HashMap<String, Object>();
    	parametros.put("LOGO", getEmpresaSistema().getLogoUrl());
    	parametros.put("IMPRIMIR_INFO_ADICIONAIS", true);
//    	parametros.put("IMPRIMIR_INFO_ADICIONAIS", ordemDeServico.isImprimirInfoAdicionais());
		return Action.SUCCESS;
	}
	
	public String visualizar(){
		ordemDeServico = ordemDeServicoManager.findOrdemServicoProjection(ordemDeServico.getId());
		return Action.SUCCESS;
	}
	
	public OrdemDeServico getOrdemDeServico()
	{
		if(ordemDeServico == null)
			ordemDeServico = new OrdemDeServico();
		return ordemDeServico;
	}

	public void setOrdemDeServico(OrdemDeServico ordemDeServico)
	{
		this.ordemDeServico = ordemDeServico;
	}

	public Collection<OrdemDeServico> getOrdensDeServico()
	{
		return ordensDeServico;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public HistoricoColaborador getHistoricoColaborador() {
		return historicoColaborador;
	}

	public void setHistoricoColaborador(HistoricoColaborador historicoColaborador) {
		this.historicoColaborador = historicoColaborador;
	}

	public Collection<Estabelecimento> getEstabelecimentosList() {
		return estabelecimentosList;
	}

	public void setEstabelecimentosList( Collection<Estabelecimento> estabelecimentosList) {
		this.estabelecimentosList = estabelecimentosList;
	}

	public Collection<AreaOrganizacional> getAreasList() {
		return areasList;
	}

	public void setAreasList(Collection<AreaOrganizacional> areasList) {
		this.areasList = areasList;
	}

	public Collection<Cargo> getCargosList() {
		return cargosList;
	}

	public void setCargosList(Collection<Cargo> cargosList) {
		this.cargosList = cargosList;
	}
	
	public Map<String, Object> getParametros() {
		return parametros;
	}
	
	public Map getSituacoes()
	{
		return situacoes;
	}
	
	public Map getFiltrosOrdemDeServico() {
		return filtrosOrdemDeServico;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	public String getSituacao() {
		return situacao;
	}

	public String getFiltroOrdemDeServico() {
		return filtroOrdemDeServico;
	}
	
	public void setFiltroOrdemDeServico(String filtropossuiOrdemDeServico) {
		this.filtroOrdemDeServico = filtropossuiOrdemDeServico;
	}
	
	public OrdemDeServico getOrdemDeServicoAtual() {
		return ordemDeServicoAtual;
	}
	
	public String getDataDoDia() 
	{
		return DateUtil.formataDiaMesAno(new Date());
	}
	
	public void setOrdemDeServicoManager(OrdemDeServicoManager ordemDeServicoManager)
	{
		this.ordemDeServicoManager = ordemDeServicoManager;
	}
	
	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public Collection<Colaborador> getColaboradores() {
		return colaboradores;
	}

	public void setColaboradores(Collection<Colaborador> colaboradores) {
		this.colaboradores = colaboradores;
	}

	public void setAreaOrganizacionalManager( AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setEstabelecimentoManager( EstabelecimentoManager estabelecimentoManager) {
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public void setCargoManager(CargoManager cargoManager) {
		this.cargoManager = cargoManager;
	}


	public void setUsuarioEmpresaManager(UsuarioEmpresaManager usuarioEmpresaManager) {
		this.usuarioEmpresaManager = usuarioEmpresaManager;
	}
}
