package com.fortes.rh.test.factory.sesmt;

import com.fortes.rh.model.sesmt.ComissaoMembro;

public class ComissaoMembroFactory
{
	public static ComissaoMembro getEntity()
	{
		ComissaoMembro comissaoMembro = new ComissaoMembro();
		return comissaoMembro;
	}

	public static ComissaoMembro getEntity(Long id)
	{
		ComissaoMembro comissaoMembro = new ComissaoMembro();
		comissaoMembro.setId(id);
		return comissaoMembro;
	}
}
