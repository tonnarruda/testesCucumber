package com.fortes.rh.web.dwr;

import java.util.HashMap;

import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;

public class GerenciadorComunicacaoDWR
{
	public  HashMap<Integer,String> getMeioComunicacao(int operacaoId)
	{
		return Operacao.getMeioComunicacaoById(operacaoId);
	}

	public  HashMap<Integer,String> getEnviarPara(int operacaoId, int meioComunicacaoId)
	{
		MeioComunicacao meiocomunicacao = MeioComunicacao.getMeioComunicacaoById(meioComunicacaoId);
		return meiocomunicacao.getEnviarPara(operacaoId);
	}
}
