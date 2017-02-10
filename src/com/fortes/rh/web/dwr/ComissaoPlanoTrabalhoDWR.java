package com.fortes.rh.web.dwr;

import java.util.HashMap;
import java.util.Map;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.sesmt.ComissaoPlanoTrabalhoManager;
import com.fortes.rh.model.sesmt.ComissaoPlanoTrabalho;

@Component
@RemoteProxy(name="ComissaoPlanoTrabalhoDWR")
public class ComissaoPlanoTrabalhoDWR
{
	@Autowired private ComissaoPlanoTrabalhoManager comissaoPlanoTrabalhoManager;

	@RemoteMethod
	public Map<String,Object> prepareDados(Long id) throws Exception
	{
		Map<String,Object> retorno = new HashMap<String, Object>();
		ComissaoPlanoTrabalho comissaoPlanoTrabalho = null;
		try
		{
			comissaoPlanoTrabalho = comissaoPlanoTrabalhoManager.findByIdProjection(id);

			if (comissaoPlanoTrabalho == null)
				throw new Exception();

			retorno.put("comissaoPlanoTrabalhoId", comissaoPlanoTrabalho.getId());
			retorno.put("prazo", comissaoPlanoTrabalho.getPrazoFormatado());
			retorno.put("descricao", comissaoPlanoTrabalho.getDescricao());
			retorno.put("prioridade", comissaoPlanoTrabalho.getPrioridade());
			retorno.put("parecer", comissaoPlanoTrabalho.getParecer());
			retorno.put("detalhes", comissaoPlanoTrabalho.getDetalhes());
			retorno.put("situacao", comissaoPlanoTrabalho.getSituacao());
			retorno.put("responsavelId", comissaoPlanoTrabalho.getResponsavel().getId());
			retorno.put("corresponsavelId", comissaoPlanoTrabalho.getCorresponsavel().getId());

		} catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

		return retorno;
	}
}