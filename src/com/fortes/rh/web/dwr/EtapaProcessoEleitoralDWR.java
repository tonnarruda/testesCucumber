package com.fortes.rh.web.dwr;

import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.sesmt.EtapaProcessoEleitoralManager;
import com.fortes.rh.model.sesmt.EtapaProcessoEleitoral;
import com.fortes.rh.util.DateUtil;

public class EtapaProcessoEleitoralDWR
{
	private EtapaProcessoEleitoralManager etapaProcessoEleitoralManager;

	public Map<String,Object> prepareDadosEtapa(Long id) throws Exception
	{
		Map<String,Object> retorno = new HashMap<String, Object>();
		EtapaProcessoEleitoral etapaProcessoEleitoral = null;
		try
		{
			etapaProcessoEleitoral = etapaProcessoEleitoralManager.findById(id);

			if (etapaProcessoEleitoral == null)
				throw new Exception();

			retorno.put("etapaId", etapaProcessoEleitoral.getId());
			retorno.put("etapaNome", etapaProcessoEleitoral.getNome());
			retorno.put("etapaPrazoLegal", etapaProcessoEleitoral.getPrazoLegal());
			retorno.put("etapaPrazo", DateUtil.formataDiaMesAno(etapaProcessoEleitoral.getData()));
			retorno.put("empresaId", etapaProcessoEleitoral.getEmpresa().getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

		return retorno;
	}

	public void setEtapaProcessoEleitoralManager(EtapaProcessoEleitoralManager etapaProcessoEleitoralManager)
	{
		this.etapaProcessoEleitoralManager = etapaProcessoEleitoralManager;
	}
}
