package com.fortes.rh.web.action.sesmt;


import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.sesmt.EpiHistoricoManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.MotivoSolicitacaoEpiManager;
import com.fortes.rh.business.sesmt.SolicitacaoEpiItemDevolucaoManager;
import com.fortes.rh.business.sesmt.SolicitacaoEpiItemEntregaManager;
import com.fortes.rh.business.sesmt.SolicitacaoEpiItemManager;
import com.fortes.rh.business.sesmt.SolicitacaoEpiManager;
import com.fortes.rh.business.sesmt.TipoEPIManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.EpiHistorico;
import com.fortes.rh.model.sesmt.MotivoSolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemDevolucao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;
import com.fortes.rh.model.sesmt.TamanhoEPI;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;

@SuppressWarnings({"serial"})
public class SolicitacaoEpiEditAction extends MyActionSupportEdit
{
	@Autowired private SolicitacaoEpiManager solicitacaoEpiManager;
	@Autowired private ColaboradorManager colaboradorManager;
	@Autowired private EpiManager epiManager;
	@Autowired private EpiHistoricoManager epiHistoricoManager;
	@Autowired private SolicitacaoEpiItemManager solicitacaoEpiItemManager;
	@Autowired private SolicitacaoEpiItemEntregaManager solicitacaoEpiItemEntregaManager;
	@Autowired private SolicitacaoEpiItemDevolucaoManager solicitacaoEpiItemDevolucaoManager;
	@Autowired private MotivoSolicitacaoEpiManager motivoSolicitacaoEpiManager;
	@Autowired private TipoEPIManager tipoEPIManager;

	private Colaborador colaborador;
	private SolicitacaoEpi solicitacaoEpi;
	private SolicitacaoEpiItem solicitacaoEpiItem;
	private SolicitacaoEpiItemEntrega solicitacaoEpiItemEntrega;
	private SolicitacaoEpiItemDevolucao solicitacaoEpiItemDevolucao;

	private Collection<Colaborador> colaboradors;
	private Collection<SolicitacaoEpiItem> solicitacaoEpiItems;

	private Object[][] listaEpis;
	private String[] selectQtdSolicitado;
	private Date[] selectDataSolicitado;
	private String[] selectQtdEntregue;
	private String[] epiIds;
	private String[] selectMotivoSolicitacaoEpi;
	private String[] selectTamanhoEpi;

	private Date dataEntrega;
	private boolean entregue;
	private boolean insert;
	private Collection<EpiHistorico> epiHistoricos;
	private Collection<MotivoSolicitacaoEpi> motivoSolicitacaoEpis; 
	private Collection<TamanhoEPI> tamanhoEpis; 

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		boolean somenteAtivos = true;
		
		if(solicitacaoEpi != null && solicitacaoEpi.getId() != null)
		{
			somenteAtivos = false;
			solicitacaoEpi = (SolicitacaoEpi) solicitacaoEpiManager.findById(solicitacaoEpi.getId());
			colaborador = solicitacaoEpi.getColaborador(); 
		}

		motivoSolicitacaoEpis = motivoSolicitacaoEpiManager.findAll(new String[] {"descricao"});
		
		if (colaborador != null && colaborador.getId() != null)
		{
			Collection<Epi> epis = epiManager.findPriorizandoEpiRelacionado(getEmpresaSistema().getId(), colaborador.getId(), somenteAtivos);
	
			listaEpis = new Object[epis.size()][2];
			int i=0;
	
			for (Epi epi : epis)
			{
				epi.setTipoEPI(tipoEPIManager.findById(epi.getTipoEPIId()));
				listaEpis[i][0] = epi;
				listaEpis[i][1] = new SolicitacaoEpiItem();
				i++;
			}
		}
	}

	public String filtroColaboradores() throws Exception
	{
		prepare();
		colaborador.setPessoalCpf(StringUtil.removeMascara(colaborador.getPessoal().getCpf()));
		colaboradors = colaboradorManager.findByNomeCpfMatricula(colaborador, true, null, null, getEmpresaSistema().getId());
		if (colaboradors == null || colaboradors.isEmpty())
			addActionMessage("Nenhum colaborador para o filtro informado.");

		return SUCCESS;
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		solicitacaoEpiItems = solicitacaoEpiItemManager.findBySolicitacaoEpi(solicitacaoEpi.getId());
		epiIds = new String[solicitacaoEpiItems.size()];

		// Preparando os EPIs marcados e as quantidades solicitadas
		int j=0;
		for (int i=0; i<listaEpis.length; i++)
		{
			Epi epi = (Epi)listaEpis[i][0];
			for (SolicitacaoEpiItem item : solicitacaoEpiItems)
			{
				if (item.getEpi().getId().equals(epi.getId()))
				{
					epiIds[j] = epi.getId().toString();
					j++;
					listaEpis[i][1] = item;
				}
			}
		}

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		try
		{
			this.colaborador = colaboradorManager.findByIdDadosBasicos(colaborador.getId(), null);
			
			if(this.entregue && colaborador.getHistoricoColaborador().getStatus() != StatusRetornoAC.CONFIRMADO)
				throw new FortesException("Não é permitido entregar um EPI para um colaborador ainda não confirmado no Fortes Pessoal.");
			
			solicitacaoEpi.setColaborador(colaborador);
			solicitacaoEpi.setCargo(colaborador.getFaixaSalarial().getCargo());
			solicitacaoEpi.setEstabelecimento(colaborador.getEstabelecimento());
			solicitacaoEpi.setEmpresa(getEmpresaSistema());

			solicitacaoEpiManager.save(solicitacaoEpi, epiIds, selectQtdSolicitado, selectMotivoSolicitacaoEpi, dataEntrega, entregue, selectTamanhoEpi);
			
			addActionSuccess("Solicitação gravada com sucesso.");
			return SUCCESS;
		}
		catch (FortesException e)
		{
			addActionMessage(e.getMessage());
			e.printStackTrace();
			prepare();
			return INPUT;
		}
		catch (Exception e)
		{
			addActionError("Não foi possível gravar a solicitação.");
			e.printStackTrace();
			prepare();
			return INPUT;
		}
	}

	public String update() throws Exception
	{
		solicitacaoEpiManager.update(solicitacaoEpi, epiIds, selectQtdSolicitado, selectMotivoSolicitacaoEpi, selectTamanhoEpi);
		return SUCCESS;
	}
	
	public String prepareEntrega() throws Exception
	{
		if(solicitacaoEpi != null && solicitacaoEpi.getId() != null)
			solicitacaoEpi = (SolicitacaoEpi) solicitacaoEpiManager.findById(solicitacaoEpi.getId());

		solicitacaoEpiItems = solicitacaoEpiItemManager.findBySolicitacaoEpi(solicitacaoEpi.getId());

		return Action.SUCCESS;
	}

	public String prepareInsertEntrega() throws Exception
	{
		solicitacaoEpiItem = solicitacaoEpiItemManager.findByIdProjection(solicitacaoEpiItem.getId());
		epiHistoricos = epiHistoricoManager.findByEpi(solicitacaoEpiItem.getEpi().getId());
		return Action.SUCCESS;
	}
	
	public String prepareUpdateEntrega() throws Exception
	{
		solicitacaoEpiItemEntrega = solicitacaoEpiItemEntregaManager.findByIdProjection(solicitacaoEpiItemEntrega.getId());
		solicitacaoEpiItem = solicitacaoEpiItemManager.findByIdProjection(solicitacaoEpiItem.getId());
		epiHistoricos = epiHistoricoManager.findByEpi(solicitacaoEpiItem.getEpi().getId());
		return Action.SUCCESS;
	}

	public String insertEntrega() throws Exception
	{
		try
		{
			validaDatasEQtds(solicitacaoEpi.getId(), solicitacaoEpiItem.getId(), solicitacaoEpiItemEntrega);
			
			solicitacaoEpiItemEntrega.setSolicitacaoEpiItem(solicitacaoEpiItem);
			solicitacaoEpiItemEntregaManager.save(solicitacaoEpiItemEntrega);
		}
		catch (FortesException fE)
		{
			addActionWarning(fE.getMessage());
			fE.printStackTrace();
			prepareInsertEntrega();
			return INPUT;
		}
		catch (Exception e)
		{
			addActionError("Erro ao gravar entrega.");
			e.printStackTrace();
			prepareInsertEntrega();
			return INPUT;
		}

		return SUCCESS;
	}
	
	public String updateEntrega() throws Exception
	{
		try
		{
			validaDatasEQtds(solicitacaoEpi.getId(), solicitacaoEpiItem.getId(), solicitacaoEpiItemEntrega);
			
			solicitacaoEpiItemEntrega.setSolicitacaoEpiItem(solicitacaoEpiItem);
			solicitacaoEpiItemEntregaManager.update(solicitacaoEpiItemEntrega);
		}
		catch (FortesException fE)
		{
			addActionWarning(fE.getMessage());
			fE.printStackTrace();
			prepareInsertEntrega();
			return INPUT;
		}
		catch (Exception e)
		{
			addActionError("Erro ao editar entrega.");
			e.printStackTrace();
			prepareUpdateEntrega();
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	private void validaDatasEQtds(Long solicitacaoEpiId, Long solicitacaoEpiItemId, SolicitacaoEpiItemEntrega solicitacaoEpiItemEntrega) throws FortesException
	{
		SolicitacaoEpi solicitacaoEpi = solicitacaoEpiManager.findEntidadeComAtributosSimplesById(solicitacaoEpiId);
		if (solicitacaoEpiItemEntrega.getDataEntrega().before(solicitacaoEpi.getData()))
			throw new FortesException("A data de entrega não pode ser anterior à data de solicitação");
		
		SolicitacaoEpiItem solicitacaoEpiItem = solicitacaoEpiItemManager.findEntidadeComAtributosSimplesById(solicitacaoEpiItemId);
		int totalEntregue = solicitacaoEpiItemEntregaManager.getTotalEntregue(solicitacaoEpiItemId, solicitacaoEpiItemEntrega.getId());
		
		if (totalEntregue + solicitacaoEpiItemEntrega.getQtdEntregue() > solicitacaoEpiItem.getQtdSolicitado())
			throw new FortesException("O total de itens entregues não pode ser superior à quantidade solicitada");
	}
	
	public String deleteEntrega() throws Exception
	{
		try
		{
			solicitacaoEpiItem = solicitacaoEpiItemManager.populaEPIsEntreguesDevolvidos(solicitacaoEpiItem);
			solicitacaoEpiItemEntrega = solicitacaoEpiItemEntregaManager.findById(solicitacaoEpiItemEntrega.getId());
			
			if(solicitacaoEpiItem.getTotalDevolvido() != 0 && ((solicitacaoEpiItemEntrega.getQtdEntregue() <= solicitacaoEpiItem.getTotalEntregue() && solicitacaoEpiItem.getTotalEntregue() <= solicitacaoEpiItem.getTotalDevolvido())
				|| solicitacaoEpiItemEntrega.getQtdEntregue() > solicitacaoEpiItem.getTotalDevolvido()))
				addActionWarning("A exclusão da entrega não pode ser realizada, pois existem devoluções para o ítem entregue.");
			else {
				solicitacaoEpiItemEntregaManager.remove(solicitacaoEpiItemEntrega.getId());
				addActionSuccess("Entrega de EPI excluída com sucesso");
			}
		}
		catch (Exception e)
		{
			addActionError("Erro ao excluir entrega.");
			e.printStackTrace();
		}
		
		prepareEntrega();
		return SUCCESS;
	}
	
	public String prepareInsertDevolucao() throws Exception
	{
		solicitacaoEpiItem = solicitacaoEpiItemManager.findByIdProjection(solicitacaoEpiItem.getId());
		return Action.SUCCESS;
	}
	
	public String prepareUpdateDevolucao() throws Exception
	{
		solicitacaoEpiItemDevolucao = solicitacaoEpiItemDevolucaoManager.findById(solicitacaoEpiItemDevolucao.getId());
		solicitacaoEpiItem = solicitacaoEpiItemManager.findByIdProjection(solicitacaoEpiItem.getId());
		return Action.SUCCESS;
	}

	public String insertDevolucao() throws Exception
	{
		try
		{
			validaDatasEQtdsDevolvidas(solicitacaoEpi.getId(), solicitacaoEpiItem.getId(), solicitacaoEpiItemDevolucao);
			
			solicitacaoEpiItemDevolucao.setSolicitacaoEpiItem(solicitacaoEpiItem);
			solicitacaoEpiItemDevolucaoManager.save(solicitacaoEpiItemDevolucao);
		}
		catch (FortesException fE)
		{
			addActionWarning(fE.getMessage());
			fE.printStackTrace();
			prepareInsertDevolucao();
			return INPUT;
		}
		catch (Exception e)
		{
			addActionError("Erro ao gravar a devolução do EPI.");
			e.printStackTrace();
			prepareInsertDevolucao();
			return INPUT;
		}

		return SUCCESS;
	}
	
	public String updateDevolucao() throws Exception
	{
		try
		{
			validaDatasEQtdsDevolvidas(solicitacaoEpi.getId(), solicitacaoEpiItem.getId(), solicitacaoEpiItemDevolucao);
			
			solicitacaoEpiItemDevolucao.setSolicitacaoEpiItem(solicitacaoEpiItem);
			solicitacaoEpiItemDevolucaoManager.update(solicitacaoEpiItemDevolucao);
		}
		catch (FortesException fE)
		{
			addActionWarning(fE.getMessage());
			fE.printStackTrace();
			prepareUpdateDevolucao();
			return INPUT;
		}
		catch (Exception e)
		{
			addActionError("Erro ao editar a devolução.");
			e.printStackTrace();
			prepareUpdateDevolucao();
			return INPUT;
		}
		return SUCCESS;
	}
	
	public String deleteDevolucao() throws Exception
	{
		try
		{
			solicitacaoEpiItemDevolucaoManager.remove(solicitacaoEpiItemDevolucao.getId());
			addActionSuccess("Devolução do EPI excluída com sucesso");
		}
		catch (Exception e)
		{
			addActionError("Erro ao excluir devolução.");
			e.printStackTrace();
		}
		
		prepareEntrega();
		return SUCCESS;
	}
	
	private void validaDatasEQtdsDevolvidas(Long solicitacaoEpiId, Long solicitacaoEpiItemId, SolicitacaoEpiItemDevolucao solicitacaoEpiItemDevolucao) throws FortesException
	{
		SolicitacaoEpi solicitacaoEpi = solicitacaoEpiManager.findEntidadeComAtributosSimplesById(solicitacaoEpiId);
		if (solicitacaoEpiItemDevolucao.getDataDevolucao().before(solicitacaoEpi.getData()))
			throw new FortesException("A data de devolução não pode ser anterior à data de solicitação");
		
		int totalEntregue = solicitacaoEpiItemEntregaManager.getTotalEntregue(solicitacaoEpiItemId, null);
		int totalDevolvido = solicitacaoEpiItemDevolucaoManager.getTotalDevolvido(solicitacaoEpiItemId, solicitacaoEpiItemDevolucao.getId());
		
		if (totalDevolvido + solicitacaoEpiItemDevolucao.getQtdDevolvida() > totalEntregue)
			throw new FortesException("O total de itens devolvidos não pode ser superior à quantidade de itens entregues");
	}
	
	public SolicitacaoEpi getSolicitacaoEpi()
	{
		if(solicitacaoEpi == null)
			solicitacaoEpi = new SolicitacaoEpi();
		return solicitacaoEpi;
	}

	public void setSolicitacaoEpi(SolicitacaoEpi solicitacaoEpi)
	{
		this.solicitacaoEpi = solicitacaoEpi;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public Collection<Colaborador> getColaboradors()
	{
		return colaboradors;
	}

	public Object[][] getListaEpis()
	{
		return listaEpis;
	}

	public String[] getSelectQtdSolicitado()
	{
		return selectQtdSolicitado;
	}

	public void setSelectQtdSolicitado(String[] selectQtdSolicitado)
	{
		this.selectQtdSolicitado = selectQtdSolicitado;
	}

	public String[] getEpiIds()
	{
		return epiIds;
	}

	public void setEpiIds(String[] epiIds)
	{
		this.epiIds = epiIds;
	}

	public String[] getSelectQtdEntregue()
	{
		return selectQtdEntregue;
	}

	public void setSelectQtdEntregue(String[] selectQtdEntregue)
	{
		this.selectQtdEntregue = selectQtdEntregue;
	}

	public Collection<SolicitacaoEpiItem> getSolicitacaoEpiItems()
	{
		return solicitacaoEpiItems;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public Date[] getSelectDataSolicitado() {
		return selectDataSolicitado;
	}

	public void setSelectDataSolicitado(Date[] selectDataSolicitado) {
		this.selectDataSolicitado = selectDataSolicitado;
	}

	public boolean isEntregue() {
		return entregue;
	}

	public void setEntregue(boolean entregue) {
		this.entregue = entregue;
	}

	public boolean isInsert() {
		return insert;
	}

	public SolicitacaoEpiItem getSolicitacaoEpiItem() {
		return solicitacaoEpiItem;
	}

	public void setSolicitacaoEpiItem(SolicitacaoEpiItem solicitacaoEpiItem) {
		this.solicitacaoEpiItem = solicitacaoEpiItem;
	}

	public SolicitacaoEpiItemEntrega getSolicitacaoEpiItemEntrega() {
		return solicitacaoEpiItemEntrega;
	}

	public void setSolicitacaoEpiItemEntrega(SolicitacaoEpiItemEntrega solicitacaoEpiItemEntrega) {
		this.solicitacaoEpiItemEntrega = solicitacaoEpiItemEntrega;
	}

	public SolicitacaoEpiItemDevolucao getSolicitacaoEpiItemDevolucao() {
		return solicitacaoEpiItemDevolucao;
	}

	public void setSolicitacaoEpiItemDevolucao(
			SolicitacaoEpiItemDevolucao solicitacaoEpiItemDevolucao) {
		this.solicitacaoEpiItemDevolucao = solicitacaoEpiItemDevolucao;
	}

	public Collection<EpiHistorico> getEpiHistoricos() {
		return epiHistoricos;
	}

	public Collection<MotivoSolicitacaoEpi> getMotivoSolicitacaoEpis()
	{
		return motivoSolicitacaoEpis;
	}

	public String[] getSelectMotivoSolicitacaoEpi()
	{
		return selectMotivoSolicitacaoEpi;
	}

	public void setSelectMotivoSolicitacaoEpi(String[] selectMotivoSolicitacaoEpi)
	{
		this.selectMotivoSolicitacaoEpi = selectMotivoSolicitacaoEpi;
	}

	public String[] getSelectTamanhoEpi() {
		return selectTamanhoEpi;
	}

	public void setSelectTamanhoEpi(String[] selectTamanhoEpi) {
		this.selectTamanhoEpi = selectTamanhoEpi;
	}

	public Collection<TamanhoEPI> getTamanhoEpis() {
		return tamanhoEpis;
	}
}