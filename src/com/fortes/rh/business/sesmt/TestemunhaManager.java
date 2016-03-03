package com.fortes.rh.business.sesmt;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.Testemunha;

public interface TestemunhaManager extends GenericManager<Testemunha>
{
	public void removeTestemunha(Long idTestemunha, String nomeAtributo);
	
}