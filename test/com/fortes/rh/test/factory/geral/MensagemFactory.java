package com.fortes.rh.test.factory.geral;

import com.fortes.rh.model.dicionario.TipoMensagem;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Mensagem;
import com.fortes.rh.util.DateUtil;

public class MensagemFactory
{

	public static Mensagem getEntity()
	{
		Mensagem mensagem =  new Mensagem();

		mensagem.setRemetente("RH");
		mensagem.setData(DateUtil.criarDataMesAno(05, 01, 2009));
		mensagem.setTexto("Teste do sistema de caixa de mensagem.");
		mensagem.setTipo(TipoMensagem.AVALIACAO_DESEMPENHO);

		return mensagem;
	}

	public static Mensagem getEntity(Long id)
	{
		Mensagem mensagem = getEntity();
		mensagem.setId(id);

		return mensagem;
	}
	
	public static Mensagem getEntity(Colaborador colaborador, char tipoMensagem)
	{
		Mensagem mensagem = getEntity();
		mensagem.setColaborador(colaborador);
		mensagem.setTipo(tipoMensagem);
		
		return mensagem;
	}

}
