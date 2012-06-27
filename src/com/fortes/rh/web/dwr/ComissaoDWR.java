package com.fortes.rh.web.dwr;

import java.util.Date;

import com.fortes.rh.business.sesmt.ComissaoMembroManager;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.util.DateUtil;

public class ComissaoDWR
{
	private ComissaoMembroManager comissaoMembroManager;

	public String dataEstabilidade(Long colaboradorId)
	{
		Comissao comissao = comissaoMembroManager.findComissaoByColaborador(colaboradorId);
		
		if(comissao == null)
			return null;
		
		Date dataEstabilidade = DateUtil.incrementaAno(comissao.getDataFim(), 1);
		return DateUtil.formataDiaMesAno(dataEstabilidade);
	}

	public void setComissaoMembroManager(ComissaoMembroManager comissaoMembroManager) {
		this.comissaoMembroManager = comissaoMembroManager;
	}
}
