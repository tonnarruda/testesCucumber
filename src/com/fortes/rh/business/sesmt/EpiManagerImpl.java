package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.dao.sesmt.EpiDao;
import com.fortes.rh.exception.RemoveCascadeException;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.EpiHistorico;
import com.fortes.rh.model.sesmt.TipoEPI;
import com.fortes.rh.model.sesmt.relatorio.FichaEpiRelatorio;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.web.tags.CheckBox;

public class EpiManagerImpl extends GenericManagerImpl<Epi, EpiDao> implements EpiManager
{
	private EpiHistoricoManager epiHistoricoManager;
	private PlatformTransactionManager transactionManager;
	private ColaboradorManager colaboradorManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	
	public Integer getCount(Long empresaId, String epiNome, Boolean ativo) 
	{
		return getDao().getCount(empresaId, epiNome, ativo);
	}
	
	public Collection<Epi> findEpis(int page, int pagingSize, Long empresaId, String epiNome, Boolean ativo) 
	{
		return getDao().findEpis(page, pagingSize, empresaId, epiNome, ativo);
	}

	public Collection<CheckBox> populaCheckToEpi(Long empresaId)
	{
		Collection<CheckBox> checks = new ArrayList<CheckBox>();
		try
		{
			Collection<Epi> epis = find(new String[]{"empresa.id"}, new Object[]{empresaId}, new String[]{"nome"});
			checks = CheckListBoxUtil.populaCheckListBox(epis, "getId", "getNome");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return checks;
	}

	public Collection<Epi> populaEpi(String[] episCheck)
	{
		Collection<Epi> epis = new ArrayList<Epi>();

		if(episCheck != null && episCheck.length > 0)
		{
			Long episIds[] = LongUtil.arrayStringToArrayLong(episCheck);

			Epi epi;
			for (Long epiId: episIds)
			{
				epi = new Epi();
				epi.setId(epiId);

				epis.add(epi);
			}
		}
		return epis;
	}

	public Epi findByIdProjection(Long epiId)
	{
		return getDao().findByIdProjection(epiId);
	}

	public Collection<Epi> findAllSelect(Long empresaId)
	{
		return getDao().findAllSelect(empresaId);
	}

	public void saveEpi(Epi epi, EpiHistorico epiHistorico) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			epi = save(epi);

			epiHistorico.setEpi(epi);

			epiHistoricoManager.save(epiHistorico);

			transactionManager.commit(status);

		}
		catch (Exception e)
		{
			transactionManager.rollback(status);
			throw e;
		}

	}
	
	public void removeEpi(Epi epi) throws Exception
	{
		if (epiHistoricoManager.getCount(new String[] {"epi.id"}, new Object[]{epi.getId()}) >= 1)
		{
			throw new RemoveCascadeException("Não é possível excluir o Epi, pois este possui um ou mais históricos.");
		}
		
		remove(epi);
	}
	
	public void setEpiHistoricoManager(EpiHistoricoManager epiHistoricoManager)
	{
		this.epiHistoricoManager = epiHistoricoManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	public FichaEpiRelatorio findImprimirFicha(Empresa empresaSistema, Colaborador colaborador)
	{
		colaborador = colaboradorManager.findByIdDadosBasicos(colaborador.getId(), StatusRetornoAC.CONFIRMADO);
		FichaEpiRelatorio fichaEpiRelatorio = new FichaEpiRelatorio(colaborador, empresaSistema);

		Collection<AreaOrganizacional> areas = areaOrganizacionalManager.findAllListAndInativas(empresaSistema.getId(), AreaOrganizacional.TODAS, null);
		try
		{
			areas = areaOrganizacionalManager.montaFamilia(areas);
			for (AreaOrganizacional areaOrganizacional : areas)
			{
				if (areaOrganizacional.getId().equals(fichaEpiRelatorio.getColaborador().getAreaOrganizacional().getId()))
				{
					fichaEpiRelatorio.getColaborador().setAreaOrganizacional(areaOrganizacional);
					break;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return fichaEpiRelatorio;
	}
	
	public void sincronizar(Long empresaOrigemId, Long empresaDestinoId)
	{
		Map<Long, Long> tipoEPIIds = clonarTipoEPI(empresaOrigemId, empresaDestinoId);
		
		Map<Long, Long> epiIds = new HashMap<Long, Long>();
		
		Collection<Epi> epis = getDao().findSincronizarEpiInteresse(empresaOrigemId);
		
		for (Epi epi : epis) {
			
			Long epiOrigemId = epi.getId();
			
			clonar(epi, empresaDestinoId);
			epiIds.put(epiOrigemId, epi.getId());
			
			Collection<EpiHistorico> epiHistoricos = popularEpiHistoricoComIds(epiOrigemId, epi.getId());
			epi.setEpiHistoricos(epiHistoricos);
			
			TipoEPIManager tipoEPIManager = (TipoEPIManager) SpringUtil.getBean("tipoEPIManager");
			Long tipoEPIIdAtual = tipoEPIManager.findTipoEPIId(epi.getTipoEPI().getId());
			Long tipoEPIIdNovo = tipoEPIIds.get(tipoEPIIdAtual);
			TipoEPI tipoEPI = tipoEPIManager.findTipoEPI(tipoEPIIdNovo);
			epi.setTipoEPI(tipoEPI);
			
			getDao().update(epi);
		}
	}
	
	private Map<Long, Long> clonarTipoEPI(Long empresaOrigemId, Long empresaDestinoId) 
	{
		TipoEPIManager tipoEPIManager = (TipoEPIManager) SpringUtil.getBean("tipoEPIManager");
		Collection<TipoEPI> tipoEPIs = tipoEPIManager.findCollectionTipoEPI(empresaOrigemId);
		Map<Long, Long> tipoEPIIds = new HashMap<Long, Long>();
		
		for (TipoEPI tipoEPI : tipoEPIs)
		{
			Long TipoEPIOrigemId = tipoEPI.getId();
			
			tipoEPIManager.clonar(tipoEPI, empresaDestinoId);
			tipoEPIIds.put(TipoEPIOrigemId, tipoEPI.getId());
		}
		return tipoEPIIds;
	}

	
	private Collection<EpiHistorico> popularEpiHistoricoComIds(Long epiOrigemId, Long epiDestinoId) {
		
		Collection<EpiHistorico> epiHistoricos = epiHistoricoManager.findByEpi(epiOrigemId);
		
		for (EpiHistorico epiHistorico : epiHistoricos)
		{
			epiHistoricoManager.clonar(epiHistorico, epiDestinoId);
		}
		
		return epiHistoricos;
	}
	
	private void clonar(Epi epiInteresse, Long empresaDestinoId) 
	{
		epiInteresse.setId(null);
		epiInteresse.setEmpresaIdProjection(empresaDestinoId);
		
		getDao().save(epiInteresse);
	}

	public String findFabricantesDistinctByEmpresa(Long empresaId) 
	{
		Collection<String> fabricantes = getDao().findFabricantesDistinctByEmpresa(empresaId);
		if(fabricantes == null || fabricantes.isEmpty())
			return "";
		else
			return StringUtil.converteCollectionToStringComAspas(fabricantes);
	}
	
	public Collection<Epi> findByVencimentoCa(Date data, Long empresaId, String[] tipoEPICheck)
	{
		return getDao().findByVencimentoCa(data, empresaId,  LongUtil.arrayStringToArrayLong(tipoEPICheck));
	}

	public Collection<Epi> findEpisDoAmbiente(Long ambienteId, Date data)
	{
		return getDao().findEpisDoAmbiente(ambienteId, data);
	}

	public Collection<Epi> findByRiscoAmbiente(Long riscoId, Long ambienteId, Date data) 
	{
		return getDao().findByRiscoAmbiente(riscoId, ambienteId, data);
	}

	public Collection<Epi> findByRisco(Long riscoId) 
	{
		return getDao().findByRisco(riscoId);
	}
	
	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public Collection<Epi> findByHistoricoFuncao(Long historicoFuncaoId)
	{
		return getDao().findByHistoricoFuncao(historicoFuncaoId);
	}

	public Collection<Epi> findPriorizandoEpiRelacionado(Long empresaId, Long colaboradorId, boolean somenteAtivos) 
	{
		return getDao().findPriorizandoEpiRelacionado(empresaId, colaboradorId, somenteAtivos);
	}
}