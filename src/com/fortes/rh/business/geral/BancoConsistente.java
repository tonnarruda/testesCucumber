package com.fortes.rh.business.geral;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BancoConsistente
{
	@Autowired private ParametrosDoSistemaManager parametrosDoSistemaManager;
	
	public void execute()
	{
		parametrosDoSistemaManager.verificaBancoConsistente();
	}
}