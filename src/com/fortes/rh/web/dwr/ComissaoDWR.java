package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.Date;

import com.fortes.rh.business.sesmt.ComissaoMembroManager;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.util.DateUtil;

public class ComissaoDWR
{
	private ComissaoMembroManager comissaoMembroManager;

	public String dataEstabilidade(Long colaboradorId)
	{
		Collection<Comissao> comissaos = comissaoMembroManager.findComissaoByColaborador(colaboradorId);
		
		if(comissaos.isEmpty())
			return null;
		
		Date dataEstabilidade = ((Comissao) (comissaos.toArray()[0])).getDataFim();
		for (Comissao comissao : comissaos) {
			if(comissao.getDataFim().getTime() > dataEstabilidade.getTime())
				dataEstabilidade = comissao.getDataFim(); 
		}
		
		dataEstabilidade = DateUtil.incrementaAno(dataEstabilidade, 1);
		return DateUtil.formataDiaMesAno(dataEstabilidade);
	}

	public void setComissaoMembroManager(ComissaoMembroManager comissaoMembroManager) {
		this.comissaoMembroManager = comissaoMembroManager;
	}
}
