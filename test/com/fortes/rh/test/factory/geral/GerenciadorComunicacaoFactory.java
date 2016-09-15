package com.fortes.rh.test.factory.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.dicionario.EnviarPara;
import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GerenciadorComunicacao;

public class GerenciadorComunicacaoFactory
{
	public static GerenciadorComunicacao getEntity(Empresa empresa, Operacao operacao, MeioComunicacao meioComunicacao, EnviarPara enviarPara)
	{
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity();
		gerenciadorComunicacao.setEmpresa(empresa);
		gerenciadorComunicacao.setOperacao(operacao.getId());
		gerenciadorComunicacao.setMeioComunicacao(meioComunicacao.getId());
		gerenciadorComunicacao.setEnviarPara(enviarPara.getId());
		
		return gerenciadorComunicacao;
	}

	public static GerenciadorComunicacao getEntity(Long id, Empresa empresa, MeioComunicacao meioComunicacao, EnviarPara enviarPara)
	{
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(id);
		gerenciadorComunicacao.setEmpresa(empresa);
		gerenciadorComunicacao.setMeioComunicacao(meioComunicacao.getId());
		gerenciadorComunicacao.setEnviarPara(enviarPara.getId());
		
		return gerenciadorComunicacao;
	}
	
	public static GerenciadorComunicacao getEntity()
	{
		GerenciadorComunicacao gerenciadorComunicacao = new GerenciadorComunicacao();
		gerenciadorComunicacao.setId(null);
		return gerenciadorComunicacao;
	}
	
	public static GerenciadorComunicacao getEntity(Long id)
	{
		GerenciadorComunicacao gerenciadorComunicacao = getEntity();
		gerenciadorComunicacao.setId(id);

		return gerenciadorComunicacao;
	}

	public static Collection<GerenciadorComunicacao> getCollection()
	{
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = new ArrayList<GerenciadorComunicacao>();
		gerenciadorComunicacaos.add(getEntity());

		return gerenciadorComunicacaos;
	}
	
	public static Collection<GerenciadorComunicacao> getCollection(Long id)
	{
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = new ArrayList<GerenciadorComunicacao>();
		gerenciadorComunicacaos.add(getEntity(id));
		
		return gerenciadorComunicacaos;
	}
}
