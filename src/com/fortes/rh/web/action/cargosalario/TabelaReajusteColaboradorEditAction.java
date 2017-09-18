package com.fortes.rh.web.action.cargosalario;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.cargosalario.ReajusteColaboradorManager;
import com.fortes.rh.business.cargosalario.ReajusteFaixaSalarialManager;
import com.fortes.rh.business.cargosalario.ReajusteIndiceManager;
import com.fortes.rh.business.cargosalario.TabelaReajusteColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.exception.LimiteColaboradorExcedidoException;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.cargosalario.ReajusteFaixaSalarial;
import com.fortes.rh.model.cargosalario.ReajusteIndice;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.dicionario.TipoReajuste;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class TabelaReajusteColaboradorEditAction extends MyActionSupportEdit
{
	
	private static final long serialVersionUID = 1L;
	
	private TabelaReajusteColaboradorManager tabelaReajusteColaboradorManager;
	private ReajusteColaboradorManager reajusteColaboradorManager;
	private ReajusteFaixaSalarialManager reajusteFaixaSalarialManager;
	private ReajusteIndiceManager reajusteIndiceManager;
	private GrupoOcupacionalManager grupoOcupacionalManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private HistoricoColaboradorManager historicoColaboradorManager;

	private TabelaReajusteColaborador tabelaReajusteColaborador;
	private GrupoOcupacional grupoOcupacional;
	private AreaOrganizacional areaOrganizacional;

	private Collection<GrupoOcupacional> grupoOcupacionals;
	private Collection<AreaOrganizacional> areaOrganizacionals;
	private Collection<ReajusteColaborador> reajustes;
	private Collection<ReajusteFaixaSalarial> reajustesFaixaSalarial;
	private Collection<ReajusteIndice> reajustesIndice;

	private String[] areaOrganizacionalsCheck;
	private Collection<CheckBox> areaOrganizacionalsCheckList = new ArrayList<CheckBox>();

	private String[] grupoOcupacionalsCheck;
	private Collection<CheckBox> grupoOcupacionalsCheckList = new ArrayList<CheckBox>();

	private TipoAplicacaoIndice tipoAplicacaoIndice = new TipoAplicacaoIndice();
	private String filtro = "0";
	private Double valorTotalFolha;
	
	private Map<Character, String> tipoReajustes = new TipoReajuste();

	private void prepare() throws Exception
	{
		if(tabelaReajusteColaborador != null && tabelaReajusteColaborador.getId() != null)
		{
			tabelaReajusteColaborador = tabelaReajusteColaboradorManager.findByIdProjection(tabelaReajusteColaborador.getId());
		}
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String visualizar() throws Exception
	{
		prepare();
		
		try {
			if (tabelaReajusteColaborador.getTipoReajuste().equals(TipoReajuste.COLABORADOR))
				return visualizarPorColaborador();
			
			else if (tabelaReajusteColaborador.getTipoReajuste().equals(TipoReajuste.FAIXA_SALARIAL))
				return visualizarPorFaixaSalarial();
			
			else if (tabelaReajusteColaborador.getTipoReajuste().equals(TipoReajuste.INDICE))
				return visualizarPorIndice();
			
			else
				throw new FortesException("Tipo de reajuste não identificado");
		
		} 
		catch (FortesException e)
		{
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			addActionError("Ocorreu um erro ao carregar os dados do reajuste");
		}

		return Action.SUCCESS;
	}

	private String visualizarPorColaborador() throws Exception
	{
		grupoOcupacionals = grupoOcupacionalManager.findAllSelect(getEmpresaSistema().getId());
		areaOrganizacionals = areaOrganizacionalManager.findAllSelectOrderDescricao(getEmpresaSistema().getId(), AreaOrganizacional.TODAS, null, false);//busca area somente da empresa de sessaod

		Collection<Long> areaOrganizacionalIds = LongUtil.arrayStringToCollectionLong(areaOrganizacionalsCheck);
		Collection<Long> grupoOcupacionalIds = LongUtil.arrayStringToCollectionLong(grupoOcupacionalsCheck);

		areaOrganizacionalsCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		grupoOcupacionalsCheckList = CheckListBoxUtil.populaCheckListBox(grupoOcupacionals, "getId", "getNome", null);

		reajustes = reajusteColaboradorManager.findByIdEstabelecimentoAreaGrupo(tabelaReajusteColaborador.getId(), null, areaOrganizacionalIds, grupoOcupacionalIds, Integer.parseInt(filtro));
		if(reajustes == null || reajustes.isEmpty())
		{
			addActionMessage("Não existem promoções e reajustes a serem visualizados");
			return Action.SUCCESS;
		}

		for (ReajusteColaborador reajusteTmp : reajustes)
		{
			reajusteTmp.setAreaOrganizacionalProposta(areaOrganizacionalManager.getAreaOrganizacional(areaOrganizacionals, reajusteTmp.getAreaOrganizacionalProposta().getId()));
		}

		CollectionUtil<ReajusteColaborador> reajusteColaboradorUtil = new CollectionUtil<ReajusteColaborador>();
		reajustes = reajusteColaboradorUtil.sortCollectionStringIgnoreCase(reajustes, "areaOrganizacionalProposta.descricao");

		valorTotalFolha = historicoColaboradorManager.getValorTotalFolha(getEmpresaSistema().getId(), tabelaReajusteColaborador.getData());
		
		return Action.SUCCESS;
	}
	
	private String visualizarPorFaixaSalarial() throws Exception
	{
		reajustesFaixaSalarial = reajusteFaixaSalarialManager.findByTabelaReajusteColaboradorId(tabelaReajusteColaborador.getId());
		
		if (reajustesFaixaSalarial == null || reajustesFaixaSalarial.isEmpty())
			addActionMessage("Não existem promoções e reajustes a serem visualizados");
		
		return Action.SUCCESS;
	}
	
	private String visualizarPorIndice() throws Exception
	{
		reajustesIndice = reajusteIndiceManager.findByTabelaReajusteColaboradorId(tabelaReajusteColaborador.getId());
		
		if (reajustesIndice == null || reajustesIndice.isEmpty())
			addActionMessage("Não existem promoções e reajustes a serem visualizados");
		
		return Action.SUCCESS;
	}
	
	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		tabelaReajusteColaborador.setAprovada(false);

		tabelaReajusteColaborador.setEmpresa(getEmpresaSistema());

		tabelaReajusteColaboradorManager.save(tabelaReajusteColaborador);

		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		try
		{
			tabelaReajusteColaboradorManager.verificaDataHistoricoColaborador(tabelaReajusteColaborador.getId(), tabelaReajusteColaborador.getData());			
		} catch (Exception e)
		{
			addActionMessage(e.getMessage());
			prepareUpdate();
			return Action.INPUT;
		}
		
		tabelaReajusteColaborador.setEmpresa(getEmpresaSistema());
		tabelaReajusteColaboradorManager.update(tabelaReajusteColaborador);
		return Action.SUCCESS;
	}

	public String aplicarPorColaborador() throws Exception
	{
		try
		{
			reajustes = reajusteColaboradorManager.findByIdEstabelecimentoAreaGrupo(tabelaReajusteColaborador.getId(), null, null, null, 0);
			tabelaReajusteColaboradorManager.verificaDataHistoricoColaborador(tabelaReajusteColaborador.getId(), tabelaReajusteColaborador.getData());
			tabelaReajusteColaboradorManager.aplicarPorColaborador(tabelaReajusteColaborador, getEmpresaSistema(), reajustes);

			addActionSuccess("Reajuste aplicado com sucesso.");

			return Action.SUCCESS;
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			visualizar();

			if (exception instanceof FortesException)
				addActionWarning(exception.getMessage());
			else if (exception instanceof ColecaoVaziaException)
				addActionMessage(exception.getMessage());
			else if (exception instanceof LimiteColaboradorExcedidoException)
				addActionMessage(exception.getMessage());
			else if (exception instanceof IntegraACException)
				addActionError(((IntegraACException)exception).getMensagemDetalhada());
			else if (exception instanceof InvocationTargetException)
			{
				String msg = "Inserção do planejamento de realinhamento falhou.";

				if (exception.getCause() != null)
				{
					exception.getCause().printStackTrace();
					msg += "<br />" + exception.getCause().getMessage();
				}
				addActionError(msg);

			}
			else
				addActionError("Não foi possível aplicar o reajuste.");

			return Action.INPUT;
		}
	}
	
	public String aplicarPorFaixaSalarial() throws Exception
	{
		try 
		{
			tabelaReajusteColaboradorManager.verificaDataHistoricoFaixaSalarial(tabelaReajusteColaborador.getId(), tabelaReajusteColaborador.getData());
			tabelaReajusteColaboradorManager.aplicarPorFaixaSalarial(tabelaReajusteColaborador.getId(), getEmpresaSistema());
			
			addActionSuccess("Reajuste aplicado com sucesso.");
			
			return Action.SUCCESS;
		} 
		catch (Exception exception)
		{
			exception.printStackTrace();
			visualizar();

			if (exception instanceof FortesException)
				addActionWarning(exception.getMessage());
			else if (exception instanceof ColecaoVaziaException)
				addActionMessage(exception.getMessage());
			else if (exception instanceof IntegraACException)
				addActionError(((IntegraACException)exception).getMensagemDetalhada());
			else
				addActionError("Não foi possível aplicar o reajuste.");

			return Action.INPUT;
		}
	}

	public String aplicarPorIndice() throws Exception
	{
		try 
		{
			tabelaReajusteColaboradorManager.verificaDataHistoricoIndice(tabelaReajusteColaborador.getId(), tabelaReajusteColaborador.getData());
			tabelaReajusteColaboradorManager.aplicarPorIndice(tabelaReajusteColaborador.getId(), getEmpresaSistema());
			
			addActionSuccess("Reajuste aplicado com sucesso");
			
			return Action.SUCCESS;
		} 
		catch (Exception exception)
		{
			exception.printStackTrace();
			visualizar();

			if (exception instanceof FortesException)
				addActionWarning(exception.getMessage());
			else if (exception instanceof ColecaoVaziaException)
				addActionMessage(exception.getMessage());
			else
				addActionError("Não foi possível aplicar o reajuste.");

			return Action.INPUT;
		}
	}

	public TabelaReajusteColaborador getTabelaReajusteColaborador()
	{
		if(tabelaReajusteColaborador == null)
		{
			tabelaReajusteColaborador = new TabelaReajusteColaborador();
		}
		return tabelaReajusteColaborador;
	}

	public void setTabelaReajusteColaborador(TabelaReajusteColaborador tabelaReajusteColaborador)
	{
		this.tabelaReajusteColaborador=tabelaReajusteColaborador;
	}

	public void setTabelaReajusteColaboradorManager(TabelaReajusteColaboradorManager tabelaReajusteColaboradorManager)
	{
		this.tabelaReajusteColaboradorManager = tabelaReajusteColaboradorManager;
	}

	public void setReajusteColaboradorManager(ReajusteColaboradorManager reajusteColaboradorManager)
	{
		this.reajusteColaboradorManager = reajusteColaboradorManager;
	}

	public Collection<AreaOrganizacional> getAreaOrganizacionals()
	{
		return areaOrganizacionals;
	}

	public void setAreaOrganizacionals(
			Collection<AreaOrganizacional> areaOrganizacionals)
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

	public void setAreaOrganizacionalManager(
			AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setGrupoOcupacionalManager(
			GrupoOcupacionalManager grupoOcupacionalManager)
	{
		this.grupoOcupacionalManager = grupoOcupacionalManager;
	}
	public AreaOrganizacional getAreaOrganizacional()
	{
		return areaOrganizacional;
	}

	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional)
	{
		this.areaOrganizacional = areaOrganizacional;
	}

	public GrupoOcupacional getGrupoOcupacional()
	{
		return grupoOcupacional;
	}

	public void setGrupoOcupacional(GrupoOcupacional grupoOcupacional)
	{
		this.grupoOcupacional = grupoOcupacional;
	}

	public Collection<ReajusteColaborador> getReajustes() {
		return reajustes;
	}

	public void setReajustes(Collection<ReajusteColaborador> reajustes) {
		this.reajustes = reajustes;
	}

	public TipoAplicacaoIndice getTipoAplicacaoIndice()
	{
		return tipoAplicacaoIndice;
	}

	public void setTipoAplicacaoIndice(TipoAplicacaoIndice tipoAplicacaoIndice)
	{
		this.tipoAplicacaoIndice = tipoAplicacaoIndice;
	}

	public Collection<CheckBox> getAreaOrganizacionalsCheckList()
	{
		return areaOrganizacionalsCheckList;
	}

	public void setAreaOrganizacionalsCheckList(Collection<CheckBox> areaOrganizacionalsCheckList)
	{
		this.areaOrganizacionalsCheckList = areaOrganizacionalsCheckList;
	}

	public Collection<CheckBox> getGrupoOcupacionalsCheckList()
	{
		return grupoOcupacionalsCheckList;
	}

	public void setGrupoOcupacionalsCheckList(Collection<CheckBox> grupoOcupacionalsCheckList)
	{
		this.grupoOcupacionalsCheckList = grupoOcupacionalsCheckList;
	}

	public String getFiltro()
	{
		return filtro;
	}

	public void setFiltro(String filtro)
	{
		this.filtro = filtro;
	}

	public String[] getAreaOrganizacionalsCheck()
	{
		return areaOrganizacionalsCheck;
	}

	public void setAreaOrganizacionalsCheck(String[] areaOrganizacionalsCheck)
	{
		this.areaOrganizacionalsCheck = areaOrganizacionalsCheck;
	}

	public String[] getGrupoOcupacionalsCheck()
	{
		return grupoOcupacionalsCheck;
	}

	public void setGrupoOcupacionalsCheck(String[] grupoOcupacionalsCheck)
	{
		this.grupoOcupacionalsCheck = grupoOcupacionalsCheck;
	}

	public void setHistoricoColaboradorManager(HistoricoColaboradorManager historicoColaboradorManager)
	{
		this.historicoColaboradorManager = historicoColaboradorManager;
	}

	public Double getValorTotalFolha()
	{
		return valorTotalFolha;
	}

	public Map<Character, String> getTipoReajustes() {
		return tipoReajustes;
	}

	public Collection<ReajusteFaixaSalarial> getReajustesFaixaSalarial() {
		return reajustesFaixaSalarial;
	}

	public void setReajustesFaixaSalarial(Collection<ReajusteFaixaSalarial> reajustesFaixaSalarial) {
		this.reajustesFaixaSalarial = reajustesFaixaSalarial;
	}

	public void setReajusteFaixaSalarialManager(ReajusteFaixaSalarialManager reajusteFaixaSalarialManager) {
		this.reajusteFaixaSalarialManager = reajusteFaixaSalarialManager;
	}

	public Collection<ReajusteIndice> getReajustesIndice() {
		return reajustesIndice;
	}

	public void setReajustesIndice(Collection<ReajusteIndice> reajustesIndice) {
		this.reajustesIndice = reajustesIndice;
	}

	public void setReajusteIndiceManager(ReajusteIndiceManager reajusteIndiceManager) {
		this.reajusteIndiceManager = reajusteIndiceManager;
	}
}