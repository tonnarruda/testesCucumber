package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.OrdemDeServicoManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.OrdemDeServico;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class OrdemDeServicoEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private OrdemDeServicoManager ordemDeServicoManager;
	private OrdemDeServico ordemDeServico;
	private Collection<OrdemDeServico> ordemDeServicos = new ArrayList<OrdemDeServico>();
	
	private Collection<Colaborador> colaboradores;
	private ColaboradorManager colaboradorManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EstabelecimentoManager estabelecimentoManager;
	private CargoManager cargoManager;
	private Colaborador colaborador = new Colaborador();
	private HistoricoColaborador historicoColaborador = new HistoricoColaborador();
	
	private Collection<Estabelecimento> estabelecimentosList = new ArrayList<Estabelecimento>();
	private Collection<AreaOrganizacional> areasList = new ArrayList<AreaOrganizacional>();
	private Collection<Cargo> cargosList = new ArrayList<Cargo>();
	
	@SuppressWarnings("rawtypes")
	private Map situacaos = new SituacaoColaborador();
	private String situacao = SituacaoColaborador.getAtivo();
	Boolean possuiOrdemDeServico;
	

	public String listColaboradores() throws Exception
	{
		Collection<AreaOrganizacional> areaOrganizacionalsTmp = areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.TODAS, null, getEmpresaSistema().getId());
		areasList = areaOrganizacionalManager.montaFamilia(areaOrganizacionalsTmp);
		CollectionUtil<AreaOrganizacional> cu1 = new CollectionUtil<AreaOrganizacional>();
		areasList = cu1.sortCollectionStringIgnoreCase(areasList, "descricao");
		estabelecimentosList = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		cargosList = cargoManager.findAllSelect("nomeMercado", null, Cargo.TODOS, getEmpresaSistema().getId());
		
		colaborador.setEmpresa(getEmpresaSistema());
		setTotalSize(colaboradorManager.getCountColaboradorComESemOrdemDeServico(colaborador, historicoColaborador, getUsuarioLogado(), situacao, possuiOrdemDeServico));
		colaboradores = colaboradorManager.findColaboradorComESemOrdemDeServico(colaborador, historicoColaborador, getUsuarioLogado(), situacao, possuiOrdemDeServico, getPage(), getPagingSize());
		
		if (colaboradores == null || colaboradores.size() == 0)
			addActionMessage("Não existem colaboradores a serem listados.");
		
		return Action.SUCCESS;
	}
	
	public String list() throws Exception
	{
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
		return ordemDeServicos;
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
	
	public Map getSituacaos()
	{
		return situacaos;
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
}
