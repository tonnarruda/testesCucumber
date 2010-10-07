package com.fortes.rh.test.factory.sesmt;

import com.fortes.rh.model.sesmt.ComissaoPlanoTrabalho;

public class ComissaoPlanoTrabalhoFactory
{
	public static ComissaoPlanoTrabalho getEntity()
	{
		return new ComissaoPlanoTrabalho();
	}

	public static ComissaoPlanoTrabalho getEntity(Long id)
	{
		ComissaoPlanoTrabalho comissaoPlanoTrabalho = getEntity();
		comissaoPlanoTrabalho.setId(id);
		return comissaoPlanoTrabalho;
	}
}
