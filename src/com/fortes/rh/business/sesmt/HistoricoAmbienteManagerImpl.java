package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.HistoricoAmbienteDao;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.RiscoAmbiente;
import com.fortes.rh.model.sesmt.relatorio.DadosAmbienteOuFuncaoRisco;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.ModelUtil;
import com.fortes.rh.util.SpringUtil;

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
	
	public void saveOrUpdate(HistoricoAmbiente historicoAmbiente, String[] riscoChecks, Collection<RiscoAmbiente> riscosAmbientes, String[] epcCheck) throws FortesException, Exception 
	{
		if (this.findByData(historicoAmbiente.getData(), historicoAmbiente.getId(), historicoAmbiente.getAmbiente().getId()) != null)
			throw new FortesException("Já existe um histórico para a data informada");	
		
		Long[] riscosMarcados = LongUtil.arrayStringToArrayLong(riscoChecks);
		Collection<Epc> epcs = new CollectionUtil<Epc>().convertArrayStringToCollection(Epc.class, epcCheck);
		
		if (historicoAmbiente.getId() != null)
			riscoAmbienteManager.removeByHistoricoAmbiente(historicoAmbiente.getId());
		
		Collection<RiscoAmbiente> riscoAmbientesSelecionados = new ArrayList<RiscoAmbiente>();
		
		for (Long riscoId : riscosMarcados)
		{
			for (RiscoAmbiente riscoAmbiente : riscosAmbientes)
			{
				if (riscoAmbiente != null && riscoAmbiente.getRisco() != null && riscoId.equals(riscoAmbiente.getRisco().getId()))
				{
					riscoAmbiente.setHistoricoAmbiente(historicoAmbiente);
					riscoAmbientesSelecionados.add(riscoAmbiente);
				}
			}
		}
		
		historicoAmbiente.setRiscoAmbientes(riscoAmbientesSelecionados);
		historicoAmbiente.setEpcs(epcs);
		if(ModelUtil.hasNull("getEstabelecimento().getId()", historicoAmbiente))
			historicoAmbiente.setEstabelecimento(null);
		saveOrUpdate(historicoAmbiente);
		
		AmbienteManager ambienteManager = (AmbienteManager) SpringUtil.getBean("ambienteManager");
		ambienteManager.atualizaDadosParaUltimoHistorico(historicoAmbiente.getAmbiente().getId());
	}
	
	public HistoricoAmbiente findByData(Date data, Long historicoAmbienteId, Long ambienteId) 
	{
		return getDao().findByData(data, historicoAmbienteId, ambienteId);
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
		AmbienteManager ambienteManager = (AmbienteManager) SpringUtil.getBean("ambienteManager");
		ambienteManager.atualizaDadosParaUltimoHistorico(historicoAmbiente.getAmbiente().getId());
	}
	
	public Collection<HistoricoAmbiente> findByAmbiente(Long ambienteId) 
	{
		return getDao().findByAmbiente(ambienteId);
	}

	public HistoricoAmbiente findUltimoHistorico(Long ambienteId) 
	{
		return getDao().findUltimoHistorico(ambienteId);
	}

	public List<DadosAmbienteOuFuncaoRisco> findDadosNoPeriodo(Long ambienteId, Date dataIni, Date dataFim) 
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

	public boolean existeHistoricoAmbienteByData(Long estabelecimentoId, Long ambienteId, Date data) {
		return getDao().existeHistoricoAmbienteByData(estabelecimentoId, ambienteId, data);
	}

	public void deleteByEstabelecimentos(Long[] estabelecimentoIds) throws Exception {
		getDao().deleteByEstabelecimentos(estabelecimentoIds);
	}

	public void setRiscoAmbienteManager(RiscoAmbienteManager riscoAmbienteManager) {
		this.riscoAmbienteManager = riscoAmbienteManager;
	}
}