package com.fortes.rh.web.dwr;

import java.util.HashMap;

import com.fortes.rh.model.dicionario.Operacao;

public class GerenciadorComunicacaoDWR
{
	public  HashMap<Integer,String> getMeioComunicacao(int operacaoId)
	{
		return Operacao.getMeioComunicacaoById(operacaoId);
	}

	public  HashMap<Integer,String> getEnviarPara(int operacaoId)
	{
		return Operacao.getEnviarParaById(operacaoId);
	}
}
