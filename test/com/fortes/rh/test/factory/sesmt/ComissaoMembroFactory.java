package com.fortes.rh.test.factory.sesmt;

import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.ComissaoPeriodo;

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
	
	public static ComissaoMembro getEntity(ComissaoPeriodo comissaoPeriodo, Colaborador colaborador, String funcaoComissao, String tipoMembroComissao)
	{
		ComissaoMembro comissaoMembro = new ComissaoMembro();
		comissaoMembro.setComissaoPeriodo(comissaoPeriodo);
		comissaoMembro.setColaborador(colaborador);
		comissaoMembro.setFuncao(funcaoComissao);
		comissaoMembro.setTipo(tipoMembroComissao);
		return comissaoMembro;
	}
}
