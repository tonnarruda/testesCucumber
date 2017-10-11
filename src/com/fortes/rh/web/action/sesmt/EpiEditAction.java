package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.sesmt.EpiHistoricoManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.SolicitacaoEpiItemEntregaManager;
import com.fortes.rh.business.sesmt.SolicitacaoEpiItemManager;
import com.fortes.rh.business.sesmt.TipoEPIManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.EpiHistorico;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;
import com.fortes.rh.model.sesmt.TipoEPI;
import com.fortes.rh.model.sesmt.relatorio.FichaEpiRelatorio;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;

public class EpiEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;
	
	private EpiManager epiManager;
	private TipoEPIManager tipoEPIManager;
	private EpiHistoricoManager epiHistoricoManager;
	private ColaboradorManager colaboradorManager;
	private SolicitacaoEpiItemManager solicitacaoEpiItemManager;
	private SolicitacaoEpiItemEntregaManager solicitacaoEpiItemEntregaManager;

	private Epi epi;
	private EpiHistorico epiHistorico;

	private Collection<TipoEPI> tipos;
	private Collection<EpiHistorico> epiHistoricos;
	private Collection<SolicitacaoEpiItemEntrega> dataSourceFichaEpi;
	private String fabricantes = "";
	
	//Impressão de ficha de EPI
	private Colaborador colaborador = new Colaborador();
	private Collection<Colaborador> colaboradors;
	private Map<String,Object> parametros = new HashMap<String, Object>();
	private FichaEpiRelatorio fichaEpiRelatorio;
	private boolean imprimirVerso;
	private Long solicitacoesEpiCheck [];

	public Date getDataHoje()
	{
		return new Date();
	}

	public Collection<TipoEPI> getTipos()
	{
		return tipos;
	}

	public void setTipos(Collection<TipoEPI> tipos)
	{
		this.tipos = tipos;
	}

	private void prepare() throws Exception
	{
		if(epi != null && epi.getId() != null)
			epi = (Epi) epiManager.findById(epi.getId());

		Long empresaId = getEmpresaSistema().getId();
		
		tipos = tipoEPIManager.find(new String[]{"empresa.id"},new Object[]{ empresaId });
		fabricantes = epiManager.findFabricantesDistinctByEmpresa(empresaId);
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();

		if(epi != null && epi.getId() != null)
			epiHistoricos = epiHistoricoManager.find(new String[]{"epi.id"},new Object[]{epi.getId()});

		if(! getEmpresaSistema().getId().equals(epi.getEmpresa().getId()))
		{
			msgAlert = "O EPI solicitado não existe na empresa " + getEmpresaSistema().getNome() +".";
			return Action.ERROR;
		}

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		epi.setEmpresa(getEmpresaSistema());

		try
		{
			epiManager.saveEpi(epi, epiHistorico);

			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			addActionError("O EPI não pôde ser cadastrado.");
			prepareInsert();
			return Action.INPUT;
		}
	}

	public String update() throws Exception
	{
		Epi epiTmp = epiManager.findByIdProjection(epi.getId());

		if(epiTmp == null || !getEmpresaSistema().getId().equals(epiTmp.getEmpresa().getId()))
		{
			msgAlert = "O EPI solicitado não existe na empresa " + getEmpresaSistema().getNome() +".";
			return Action.ERROR;
		}
		epi.setEmpresa(getEmpresaSistema());

		epiManager.update(epi);
		return Action.SUCCESS;
	}

	public String prepareImprimirFicha()
	{
		return SUCCESS;
	}

	public String filtroImprimirFicha()
	{
		colaborador.setPessoalCpf(StringUtil.removeMascara(colaborador.getPessoal().getCpf()));
		colaboradors = colaboradorManager.findByNomeCpfMatricula(colaborador, false, null, null, getEmpresaSistema().getId());
		if (colaboradors == null || colaboradors.isEmpty())
			addActionMessage("Nenhum colaborador para o filtro informado.");

		return SUCCESS;
	}

	public String imprimirFicha()
	{
		parametros = RelatorioUtil.getParametrosRelatorio("FICHA DE CONTROLE DE EPI", getEmpresaSistema(), "");
		fichaEpiRelatorio = epiManager.findImprimirFicha(getEmpresaSistema(), colaborador);
		fichaEpiRelatorio.setImprimirVerso(imprimirVerso);
		
		montaRelatorioFichaEpi();
		parametros.put("FICHA", fichaEpiRelatorio);
		
		return SUCCESS;
	}

	private void montaRelatorioFichaEpi() 
	{
		int baseNumDeLinhas = (imprimirVerso ? 34 : 10);

		dataSourceFichaEpi = new ArrayList<SolicitacaoEpiItemEntrega>();
		int numLinhasAdicionaisParaRelatorio  = baseNumDeLinhas;
		
		
		if(ArrayUtils.isNotEmpty(solicitacoesEpiCheck)){
			for(Long solicitacaoId : solicitacoesEpiCheck){
				
				Collection<SolicitacaoEpiItem> solicitacaoEpiItems = solicitacaoEpiItemManager.findBySolicitacaoEpi(solicitacaoId); 
				
				for (SolicitacaoEpiItem solicitacaoEpiItem : solicitacaoEpiItems) 
					dataSourceFichaEpi.addAll(solicitacaoEpiItemEntregaManager.findBySolicitacaoEpiItem(solicitacaoEpiItem.getId()));
				
				numLinhasAdicionaisParaRelatorio = baseNumDeLinhas - (dataSourceFichaEpi.size() % baseNumDeLinhas);
			}
		}

		for(int i=1; i <= numLinhasAdicionaisParaRelatorio; i++)
			dataSourceFichaEpi.add(new SolicitacaoEpiItemEntrega());
	}

	public Epi getEpi()
	{
		if(epi == null)
			epi = new Epi();
		return epi;
	}

	public void setEpi(Epi epi)
	{
		this.epi = epi;
	}

	public void setEpiManager(EpiManager epiManager)
	{
		this.epiManager = epiManager;
	}

	public void setTipoEPIManager(TipoEPIManager tipoEPIManager)
	{
		this.tipoEPIManager = tipoEPIManager;
	}

	public Collection<EpiHistorico> getEpiHistoricos()
	{
		return epiHistoricos;
	}

	public void setEpiHistoricos(Collection<EpiHistorico> epiHistoricos)
	{
		this.epiHistoricos = epiHistoricos;
	}

	public void setEpiHistoricoManager(EpiHistoricoManager epiHistoricoManager)
	{
		this.epiHistoricoManager = epiHistoricoManager;
	}

	public EpiHistorico getEpiHistorico()
	{
		return epiHistorico;
	}

	public void setEpiHistorico(EpiHistorico epiHistorico)
	{
		this.epiHistorico = epiHistorico;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public Collection<Colaborador> getColaboradors()
	{
		return colaboradors;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public FichaEpiRelatorio getFichaEpiRelatorio()
	{
		return fichaEpiRelatorio;
	}

	public boolean isImprimirVerso() {
		return imprimirVerso;
	}

	public void setImprimirVerso(boolean imprimirVerso) {
		this.imprimirVerso = imprimirVerso;
	}

	public String getFabricantes() {
		return fabricantes;
	}

	public Collection<SolicitacaoEpiItemEntrega> getDataSourceFichaEpi() {
		return dataSourceFichaEpi;
	}

	public void setSolicitacaoEpiItemManager(SolicitacaoEpiItemManager solicitacaoEpiItemManager) {
		this.solicitacaoEpiItemManager = solicitacaoEpiItemManager;
	}

	public void setSolicitacaoEpiItemEntregaManager(SolicitacaoEpiItemEntregaManager solicitacaoEpiItemEntregaManager) {
		this.solicitacaoEpiItemEntregaManager = solicitacaoEpiItemEntregaManager;
	}

	public Long[] getSolicitacoesEpiCheck() {
		return solicitacoesEpiCheck;
	}

	public void setSolicitacoesEpiCheck(Long solicitacoesEpiCheck[]) {
		this.solicitacoesEpiCheck = solicitacoesEpiCheck;
	}
}