package com.fortes.rh.web.dwr;

import java.util.HashMap;

import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;

public class GerenciadorComunicacaoDWR
{
	public  HashMap<Integer,String> getMeioComunicacao(Integer operacaoId)
	{
		return Operacao.getMeioComunicacaoById(operacaoId);
	}

	public  HashMap<Integer,String> getEnviarPara(Integer operacaoId, Integer meioComunicacaoId)
	{
		MeioComunicacao meiocomunicacao = MeioComunicacao.getMeioComunicacaoById(meioComunicacaoId);
		return meiocomunicacao.getListEnviarPara();
	}
}
