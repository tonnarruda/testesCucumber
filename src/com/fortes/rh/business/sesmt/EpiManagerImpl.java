package com.fortes.rh.business.sesmt;

import java.io.File;
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
import com.fortes.rh.model.dicionario.OpcaoImportacao;
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
import com.fortes.rh.util.importacao.ImportacaoCSVUtil;
import com.fortes.web.tags.CheckBox;

public class EpiManagerImpl extends GenericManagerImpl<Epi, EpiDao> implements EpiManager
{
	private EpiHistoricoManager epiHistoricoManager;
	private TipoEPIManager tipoEPIManager;
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

	public Collection<CheckBox> populaCheckToEpi(Long empresaId, Boolean epiAtivo)
	{
		Collection<CheckBox> checks = new ArrayList<CheckBox>();
		try
		{
			Collection<Epi> epis = findEpis(0, 0, empresaId, null, epiAtivo);
			checks = CheckListBoxUtil.populaCheckListBox(epis, "getId", "getNomeInativo", null);
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
		if (epiHistoricoManager.getCount(new String[] {"epi.id"}, new Object[]{epi.getId()}) > 1)
			throw new RemoveCascadeException("Não é possível excluir o Epi, pois este possui mais de um histórico.");
		
		epiHistoricoManager.removeByEpi(epi.getId());
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

		Collection<AreaOrganizacional> areas = areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.TODAS, null, empresaSistema.getId());
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
	
	public void sincronizar(Long empresaOrigemId, Long empresaDestinoId, Map<Long, Long> epiIds)
	{
		Map<Long, Long> tipoEPIIds = clonarTipoEPI(empresaOrigemId, empresaDestinoId);
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

	public Collection<Epi> findByRiscoAmbienteOuFuncao(Long riscoId, Long ambienteId, Date data, boolean controlaRiscoPorAmbiente) 
	{
		return getDao().findByRiscoAmbienteOuFuncao(riscoId, ambienteId, data, controlaRiscoPorAmbiente);
	}

	public Collection<Epi> findByRisco(Long riscoId) 
	{
		return getDao().findByRisco(riscoId);
	}

	public Collection<Epi> findByHistoricoFuncao(Long historicoFuncaoId)
	{
		return getDao().findByHistoricoFuncao(historicoFuncaoId);
	}

	public Collection<Epi> findPriorizandoEpiRelacionado(Long empresaId, Long colaboradorId, boolean somenteAtivos) 
	{
		return getDao().findPriorizandoEpiRelacionado(empresaId, colaboradorId, somenteAtivos);
	}
	
	public void importarArquivo(File arquivo, Long empresaId) throws Exception 
	{
		ImportacaoCSVUtil importacaoCSVUtil = new ImportacaoCSVUtil();
		importacaoCSVUtil.setDelimitador("|#|");
		importacaoCSVUtil.importarCSV(arquivo, OpcaoImportacao.EPIS, true);

		Collection<Epi> epis = importacaoCSVUtil.getEpis();
		TipoEPI tipoEpi;
		Epi epi;
		
		for (Epi epiImportado : epis) 
		{
			tipoEpi = saveTipoEPI(epiImportado.getTipoEPI(), empresaId);
			epi = saveEpi(epiImportado, tipoEpi, empresaId);
			saveEpiHistoricos(epi, epiImportado);
		}
	}
	
	private TipoEPI saveTipoEPI(TipoEPI tipoEpiImportado, Long empresaId) 
	{
		TipoEPI tipoEpi = tipoEPIManager.findFirst(new String[] {"empresa.id", "codigo"}, new Object[] { empresaId, tipoEpiImportado.getCodigo() }, new String[] {"empresa"});
		
		if (tipoEpi == null)
		{
			tipoEpiImportado.setEmpresaId(empresaId);
			tipoEpi = tipoEPIManager.save(tipoEpiImportado);
		}
		else
		{
			tipoEpi.setNome(tipoEpiImportado.getNome());
			tipoEPIManager.update(tipoEpi);
		}
		
		return tipoEpi;
	}

	private Epi saveEpi(Epi epiImportado, TipoEPI tipoEpi, Long empresaId) 
	{
		Epi epi = findFirst(new String[] {"empresa.id", "codigo"}, new Object[] { empresaId, epiImportado.getCodigo() }, new String[] {"empresa","tipoEPI"});
		
		if (epi == null)
		{
			epiImportado.setTipoEPI(tipoEpi);
			epiImportado.setEmpresaIdProjection(empresaId);
			epi = save(epiImportado);
		}
		else
		{
			epi.setNome(epiImportado.getNome());
			epi.setFabricante(epiImportado.getFabricante());
			epi.setAtivo(new Boolean(epiImportado.isAtivo()));
			epi.setFardamento(new Boolean(epiImportado.getFardamento()));
			epi.setTipoEPI(tipoEpi);
			update(epi);
		}
		
		return epi;
	}
	
	private void saveEpiHistoricos(Epi epi, Epi epiImportado) 
	{
		EpiHistorico epiHistorico;
		
		for (EpiHistorico epiHistoricoImportado : epiImportado.getEpiHistoricos()) 
		{
			epiHistorico = epiHistoricoManager.findFirst(new String[] {"epi.id", "data", "CA"}, new Object[] { epi.getId(), epiHistoricoImportado.getData(), epiHistoricoImportado.getCA() }, new String[] {"epi"});
			if (epiHistorico == null)
			{
				epiHistoricoImportado.setEpi(epi);
				epiHistoricoManager.save(epiHistoricoImportado);
			}
			else
			{
				epiHistorico.setData(epiHistoricoImportado.getData());
				epiHistorico.setVencimentoCA(epiHistoricoImportado.getVencimentoCA());
				epiHistorico.setCA(epiHistoricoImportado.getCA());
				epiHistorico.setAtenuacao(epiHistoricoImportado.getAtenuacao());
				epiHistorico.setValidadeUso(new Integer(epiHistoricoImportado.getValidadeUso()));
				epiHistoricoManager.update(epiHistorico);
			}
		}
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}
	
	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setTipoEPIManager(TipoEPIManager tipoEPIManager) 
	{
		this.tipoEPIManager = tipoEPIManager;
	}
}