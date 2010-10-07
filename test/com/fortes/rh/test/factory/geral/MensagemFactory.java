package com.fortes.rh.test.factory.geral;

import com.fortes.rh.model.geral.Mensagem;
import com.fortes.rh.util.DateUtil;

public class MensagemFactory
{

	public static Mensagem getEntity()
	{
		Mensagem mensagem =  new Mensagem();

		mensagem.setRemetente("Fortes RH");
		mensagem.setData(DateUtil.criarDataMesAno(05, 01, 2009));
		mensagem.setTexto("Teste do sistema de caixa de mensagem.");

		return mensagem;
	}

	public static Mensagem getEntity(Long id)
	{
		Mensagem mensagem = getEntity();
		mensagem.setId(id);

		return mensagem;
	}

}
