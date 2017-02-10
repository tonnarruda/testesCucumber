package com.fortes.rh.web.dwr;

import java.util.TreeMap;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Component;

import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;

@Component
@RemoteProxy(name="GerenciadorComunicacaoDWR")
public class GerenciadorComunicacaoDWR
{
	@RemoteMethod
	public  TreeMap<Integer,String> getMeioComunicacao(Integer operacaoId)
	{
		return Operacao.getMeioComunicacaosById(operacaoId);
	}

	@RemoteMethod
	public  TreeMap<Integer,String> getEnviarPara(Integer meioComunicacaoId)
	{
		MeioComunicacao meiocomunicacao = MeioComunicacao.getMeioComunicacaoById(meioComunicacaoId);
		return meiocomunicacao.getListEnviarPara();
	}
}
