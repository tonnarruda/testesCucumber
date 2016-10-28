package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;

@SuppressWarnings("unchecked")
@Component
public class AmbienteDWR
{
	@Autowired
	private AmbienteManager ambienteManager;
	
	public Map<Object, Object> getAmbienteByEstabelecimento(Long estabelecimentoId)
	{
		Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
		Collection<Ambiente> ambientesAux = ambienteManager.findByEstabelecimento(estabelecimentoId); 
		
		Ambiente ambienteVazio = new Ambiente();
		ambienteVazio.setId(-1L);
		ambientes.add(ambienteVazio);
		ambientes.addAll(ambientesAux);
		
		if(ambientesAux.size() < 1){
		
			ambienteVazio.setNome(" Nenhum");
		}
		else{
			ambienteVazio.setNome(" Selecione...");
		}
		
		return new CollectionUtil<Ambiente>().convertCollectionToMap(ambientes,"getId","getNome");
	}
	
	public Map<Object, Object> getAmbienteChecks(Long estabelecimentoId, Date data)
	{
		Collection<Ambiente> ambientes = ambienteManager.findByEstabelecimento(estabelecimentoId);
		
		return  new CollectionUtil<Ambiente>().convertCollectionToMap(ambientes,"getId","getNome");
	}
	
	public Map<Object, Object> getAmbientesByEstabelecimentos(String[] estabelecimentosIds, Date data)
	{
		Collection<Ambiente> ambientes = ambienteManager.findByEstabelecimento(LongUtil.arrayStringToArrayLong(estabelecimentosIds));
		
		return  new CollectionUtil<Ambiente>().convertCollectionToMap(ambientes,"getId","getNomeComEstabelecimento");
	}

	public void setAmbienteManager(AmbienteManager ambienteManager) {
		this.ambienteManager = ambienteManager;
	}
	
}
