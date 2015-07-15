package com.fortes.rh.web.dwr;

import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.sesmt.ComissaoPlanoTrabalhoManager;
import com.fortes.rh.model.sesmt.ComissaoPlanoTrabalho;

public class ComissaoPlanoTrabalhoDWR
{
	private ComissaoPlanoTrabalhoManager comissaoPlanoTrabalhoManager;

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
			retorno.put("coresponsavelId", comissaoPlanoTrabalho.getCoresponsavel().getId());

		} catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

		return retorno;
	}

	public void setComissaoPlanoTrabalhoManager(ComissaoPlanoTrabalhoManager comissaoPlanoTrabalhoManager)
	{
		this.comissaoPlanoTrabalhoManager = comissaoPlanoTrabalhoManager;
	}
}
