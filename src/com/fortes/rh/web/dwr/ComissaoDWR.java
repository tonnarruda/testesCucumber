package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.sesmt.ComissaoMembroManager;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.util.DateUtil;

@Component
public class ComissaoDWR
{
	@Autowired
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
		return new Date().before(dataEstabilidade) ? DateUtil.formataDiaMesAno(dataEstabilidade) : null;
	}

	public void setComissaoMembroManager(ComissaoMembroManager comissaoMembroManager) {
		this.comissaoMembroManager = comissaoMembroManager;
	}
}
