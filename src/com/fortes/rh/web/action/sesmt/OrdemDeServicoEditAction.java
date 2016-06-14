package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
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
	
	private OrdemDeServico ordemDeServico;
	private Colaborador colaborador = new Colaborador();
	
	private Map situacoes = new SituacaoColaborador();
	private Map filtrosOrdemDeServico = new FiltroOrdemDeServico();
	private String situacao = SituacaoColaborador.ATIVO;
	private String filtroOrdemDeServico = FiltroOrdemDeServico.TODOS;

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
		ordensDeServico = ordemDeServicoManager.find(getPage(), getPagingSize(), new String[]{"colaborador.id"}, new Long[]{colaborador.getId()}, new String[]{"data"});
		return Action.SUCCESS;
	}
	
//
//	public String delete() throws Exception
//	{
//		try
//		{
//			ordemDeServicoManager.remove(ordemDeServico.getId());
//			addActionMessage("OrdemDeServico excluído com sucesso.");verificar msg
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			addActionError("Não foi possível excluir este ordemDeServico.");verificar msg
//		}
//
//		return list();
//	}
	
	private void prepare() throws Exception
	{
		if(ordemDeServico != null && ordemDeServico.getId() != null)
			ordemDeServico = (OrdemDeServico) ordemDeServicoManager.findById(ordemDeServico.getId());

	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		ordemDeServicoManager.save(ordemDeServico);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		ordemDeServicoManager.update(ordemDeServico);
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

	public Collection<OrdemDeServico> getOrdemDeServicos()
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
