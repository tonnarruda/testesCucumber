package com.fortes.rh.web.dwr;

import java.util.TreeMap;

import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;

public class GerenciadorComunicacaoDWR
{
	public  TreeMap<Integer,String> getMeioComunicacao(Integer operacaoId)
	{
		return Operacao.getMeioComunicacaosById(operacaoId);
	}

	public  TreeMap<Integer,String> getEnviarPara(Integer meioComunicacaoId)
	{
		MeioComunicacao meiocomunicacao = MeioComunicacao.getMeioComunicacaoById(meioComunicacaoId);
		return meiocomunicacao.getListEnviarPara();
	}
}
