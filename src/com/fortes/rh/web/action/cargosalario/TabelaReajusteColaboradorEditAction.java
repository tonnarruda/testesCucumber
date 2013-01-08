package com.fortes.rh.web.action.cargosalario;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.cargosalario.ReajusteColaboradorManager;
import com.fortes.rh.business.cargosalario.ReajusteFaixaSalarialManager;
import com.fortes.rh.business.cargosalario.TabelaReajusteColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.exception.LimiteColaboradorExceditoException;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.cargosalario.ReajusteFaixaSalarial;
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

	private String[] areaOrganizacionalsCheck;
	private Collection<CheckBox> areaOrganizacionalsCheckList = new ArrayList<CheckBox>();

	private String[] grupoOcupacionalsCheck;
	private Collection<CheckBox> grupoOcupacionalsCheckList = new ArrayList<CheckBox>();

	private TipoAplicacaoIndice tipoAplicacaoIndice = new TipoAplicacaoIndice();
	private String filtro = "0";
	private Double valorTotalFolha;
	
	private Map tipoReajustes = new TipoReajuste();

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
		areaOrganizacionals = areaOrganizacionalManager.findAllSelectOrderDescricao(getEmpresaSistema().getId(), AreaOrganizacional.TODAS, null);//busca area somente da empresa de sessaod

		Collection<Long> areaOrganizacionalIds = LongUtil.arrayStringToCollectionLong(areaOrganizacionalsCheck);
		Collection<Long> grupoOcupacionalIds = LongUtil.arrayStringToCollectionLong(grupoOcupacionalsCheck);

		areaOrganizacionalsCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		grupoOcupacionalsCheckList = CheckListBoxUtil.populaCheckListBox(grupoOcupacionals, "getId", "getNome");

		reajustes = reajusteColaboradorManager.findByIdEstabelecimentoAreaGrupo(tabelaReajusteColaborador.getId(), null, areaOrganizacionalIds, grupoOcupacionalIds, Integer.parseInt(filtro));
		if(reajustes == null || reajustes.isEmpty())
		{
			addActionMessage("Não existem Promoções e Reajustes a serem visualizadas!");
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
			
			addActionMessage("Reajuste aplicado com sucesso");
			
			return Action.SUCCESS;
		}
		catch (ColecaoVaziaException e)
		{
			e.printStackTrace();
			visualizar();
			addActionMessage(e.getMessage());
			return Action.INPUT;
		}
		catch (LimiteColaboradorExceditoException e)
		{
			e.printStackTrace();
			visualizar();
			addActionMessage(e.getMessage());
			return Action.INPUT;
		}
		catch (IntegraACException e)
		{
			e.printStackTrace();
			visualizar();
			addActionError(e.getMensagemDetalhada());
			return Action.INPUT;
		}
		catch (InvocationTargetException e)
		{
			String msg = "Inserção do Planejamento de Realinhamento falhou.";
			
			if (e.getCause() != null)
			{
				e.getCause().printStackTrace();
				msg += "<br />" + e.getCause().getMessage();
			}
			addActionError(msg);

			visualizar();
				
			return Action.INPUT;
		}
		catch (Exception e)
		{
			e.printStackTrace();

			String msg = "Inserção do Planejamento de Realinhamento falhou.";
			
			if (e.getMessage() != null)
				msg += "<br />" + e.getMessage();
	
			addActionError(msg);
			
			visualizar();

			return Action.INPUT;
		}
	}
	
	public String aplicarPorFaixaSalarial() throws Exception
	{
		try 
		{
			tabelaReajusteColaboradorManager.aplicarPorFaixaSalarial(tabelaReajusteColaborador.getId(), getEmpresaSistema());
			
			addActionMessage("Reajuste aplicado com sucesso");
			
			return Action.SUCCESS;
		} 
		catch (ColecaoVaziaException e) 
		{
			addActionError(e.getMessage());
			e.printStackTrace();
			
			visualizar();
			
			return Action.INPUT;
		}
		catch (Exception e) 
		{
			addActionError("Ocorreu um erro ao aplicar os reajustes");
			e.printStackTrace();
			
			visualizar();

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

	public Map getTipoReajustes() {
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
}