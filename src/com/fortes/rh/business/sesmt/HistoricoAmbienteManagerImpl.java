package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.HistoricoAmbienteDao;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.RiscoAmbiente;
import com.fortes.rh.model.sesmt.relatorio.DadosAmbienteRisco;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;

public class HistoricoAmbienteManagerImpl extends GenericManagerImpl<HistoricoAmbiente, HistoricoAmbienteDao> implements HistoricoAmbienteManager
{
	private RiscoAmbienteManager riscoAmbienteManager; 
	
	@Override
	public HistoricoAmbiente findById(Long id) 
	{
		HistoricoAmbiente historicoAmbiente = getDao().findById(id);
		Collection<RiscoAmbiente> riscoAmbientes = new CollectionUtil<RiscoAmbiente>().distinctCollection(historicoAmbiente.getRiscoAmbientes());
		historicoAmbiente.setRiscoAmbientes(riscoAmbientes);
		
		return historicoAmbiente;
	}
	
	public void save(HistoricoAmbiente historicoAmbiente, String[] riscoChecks, Collection<RiscoAmbiente> riscosAmbientes, String[] epcCheck, char controlaRiscoPor) throws Exception 
	{
		Long[] riscosMarcados = LongUtil.arrayStringToArrayLong(riscoChecks);
		Collection<Epc> epcs = new CollectionUtil<Epc>().convertArrayStringToCollection(Epc.class, epcCheck);
		
		if (controlaRiscoPor == 'F'){
			Collection<RiscoAmbiente> riscosMarcadosAux = riscoAmbienteManager.findToList(new String[] {"id"},new String[] {"id"}, new String[]{"historicoAmbiente.id"}, new Object[]{historicoAmbiente.getId()});
			
			CollectionUtil<RiscoAmbiente> cut = new CollectionUtil<RiscoAmbiente>();
			riscosMarcados = cut.convertCollectionToArrayIds(riscosMarcadosAux);
			
		} else {
			riscoAmbienteManager.removeByHistoricoAmbiente(historicoAmbiente.getId());
		}
		
		Collection<RiscoAmbiente> riscoAmbientesSelecionados = new ArrayList<RiscoAmbiente>();
		
		for (Long riscoId : riscosMarcados)
		{
			for (RiscoAmbiente riscoAmbiente : riscosAmbientes)
			{
				if (riscoAmbiente.getRisco() != null && riscoId.equals(riscoAmbiente.getRisco().getId()))
				{
					riscoAmbiente.setHistoricoAmbiente(historicoAmbiente);
					riscoAmbientesSelecionados.add(riscoAmbiente);
				}
			}
		}
		
		historicoAmbiente.setRiscoAmbientes(riscoAmbientesSelecionados);
		historicoAmbiente.setEpcs(epcs);
		
		if (historicoAmbiente.getId() == null)
			save(historicoAmbiente);
		else
			update(historicoAmbiente);
	}
	
	public boolean removeByAmbiente(Long ambienteId) 
	{
		return getDao().removeByAmbiente(ambienteId);
	}
	
	public void removeCascade(Long id)
	{
		// Deixa o hibernate gerenciar as remoções dos relacionamentos 
		HistoricoAmbiente historicoAmbiente = getDao().findById(id); // não tirar essa linha.
		getDao().remove(historicoAmbiente);
	}
	
	public void setRiscoAmbienteManager(RiscoAmbienteManager riscoAmbienteManager) {
		this.riscoAmbienteManager = riscoAmbienteManager;
	}

	public Collection<HistoricoAmbiente> findByAmbiente(Long ambienteId) 
	{
		return getDao().findByAmbiente(ambienteId);
	}

	public HistoricoAmbiente findUltimoHistorico(Long ambienteId) 
	{
		return getDao().findUltimoHistorico(ambienteId);
	}

	public List<DadosAmbienteRisco> findDadosNoPeriodo(Long ambienteId, Date dataIni, Date dataFim) 
	{
		return getDao().findDadosNoPeriodo(ambienteId, dataIni, dataFim);
	}

	public HistoricoAmbiente findUltimoHistoricoAteData(Long ambienteId, Date dataMaxima)
	{
		return getDao().findUltimoHistoricoAteData(ambienteId, dataMaxima);
	}

	public Collection<HistoricoAmbiente> findRiscosAmbientes(Collection<Long> ambienteIds, Date data) 
	{
		return getDao().findRiscosAmbientes(ambienteIds, data);
	}
}