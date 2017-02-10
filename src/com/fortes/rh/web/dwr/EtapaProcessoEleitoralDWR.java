package com.fortes.rh.web.dwr;

import java.util.HashMap;
import java.util.Map;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.sesmt.EtapaProcessoEleitoralManager;
import com.fortes.rh.model.sesmt.EtapaProcessoEleitoral;
import com.fortes.rh.util.DateUtil;

@Component
@RemoteProxy(name="EtapaProcessoEleitoralDWR")
public class EtapaProcessoEleitoralDWR
{
	@Autowired private EtapaProcessoEleitoralManager etapaProcessoEleitoralManager;

	@RemoteMethod
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
}