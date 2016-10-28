package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.geral.OcorrenciaManager;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.util.CollectionUtil;

@Component
public class OcorrenciaDWR
{
	@Autowired
	private OcorrenciaManager ocorrenciaManager;
	
	@SuppressWarnings("unchecked")
	public Map<Object, Object> getByEmpresas(Long empresaId, Long[] empresaIds) throws Exception
	{
		Collection<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();

		if(empresaId != null && empresaId != 0)
			empresaIds = new Long[]{empresaId};
		
		ocorrencias = ocorrenciaManager.findAllSelect(empresaIds);

		return new CollectionUtil<Ocorrencia>().convertCollectionToMap(ocorrencias, "getId", "getDescricaoComEmpresa");
	}

	@SuppressWarnings("unchecked")
	public Map<Object, Object> getByEmpresa(Long empresaId) throws Exception
	{
		Collection<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();
		ocorrencias = ocorrenciaManager.findAllSelect(new Long[]{empresaId});
		
		return new CollectionUtil<Ocorrencia>().convertCollectionToMap(ocorrencias, "getId", "getDescricao");
	}

	@SuppressWarnings("unchecked")
	public Map<Object, Object> getByEmpresaComCodigoAc(Long empresaId) throws Exception
	{
		Collection<Ocorrencia> ocorrencias  = ocorrenciaManager.findComCodigoAC(empresaId);
		return new CollectionUtil<Ocorrencia>().convertCollectionToMap(ocorrencias, "getCodigoAC", "getDescricao");
	}
	
	public void setOcorrenciaManager(OcorrenciaManager ocorrenciaManager)
	{
		this.ocorrenciaManager = ocorrenciaManager;
	}
}
