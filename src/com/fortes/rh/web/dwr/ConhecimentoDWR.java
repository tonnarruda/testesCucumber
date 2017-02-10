package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.captacao.ConhecimentoManager;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;

@Component
@RemoteProxy(name="ConhecimentoDWR")
@SuppressWarnings("rawtypes")
public class ConhecimentoDWR
{
	@Autowired private ConhecimentoManager conhecimentoManager;

	@RemoteMethod
	public Map getConhecimentos(String[] areaOrganizacionalIds, Long empresaId)
	{
		Collection<Conhecimento> conhecimentos;

		if (areaOrganizacionalIds != null && areaOrganizacionalIds.length > 0)
			conhecimentos =  conhecimentoManager.findByAreasOrganizacionalIds(LongUtil.arrayStringToArrayLong(areaOrganizacionalIds),empresaId);
		else
			conhecimentos =  conhecimentoManager.findAllSelect(empresaId);

		return new CollectionUtil<Conhecimento>().convertCollectionToMap(conhecimentos,"getId","getNome");
	}

	@RemoteMethod
	public Map getByEmpresa(Long empresaId, Long[] empresaIds)
	{
		Collection<Conhecimento> conhecimentos = new ArrayList<Conhecimento>();
		
		if(empresaId == null || empresaId == -1)//Caso a empresa passada seja -1, vai trazer todos os conhecimentos dando distinct pelo nome
			conhecimentos =  conhecimentoManager.findAllSelectDistinctNome(empresaIds);
		else
			conhecimentos =  conhecimentoManager.findAllSelect(empresaId);			
				
		return new CollectionUtil<Conhecimento>().convertCollectionToMap(conhecimentos,"getId","getNome");
	}
}