package com.fortes.rh.test.factory.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;

public class ConfiguracaoCampoExtraFactory
{
	public static ConfiguracaoCampoExtra getEntity()
	{
		ConfiguracaoCampoExtra configuracaoCampoExtra = new ConfiguracaoCampoExtra();
		configuracaoCampoExtra.setId(null);
		return configuracaoCampoExtra;
	}

	public static ConfiguracaoCampoExtra getEntity(Long id)
	{
		ConfiguracaoCampoExtra configuracaoCampoExtra = getEntity();
		configuracaoCampoExtra.setId(id);

		return configuracaoCampoExtra;
	}

	public static Collection<ConfiguracaoCampoExtra> getCollection()
	{
		Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras = new ArrayList<ConfiguracaoCampoExtra>();
		configuracaoCampoExtras.add(getEntity());

		return configuracaoCampoExtras;
	}
	
	public static Collection<ConfiguracaoCampoExtra> getCollection(Long id)
	{
		Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras = new ArrayList<ConfiguracaoCampoExtra>();
		configuracaoCampoExtras.add(getEntity(id));
		
		return configuracaoCampoExtras;
	}

	public static ConfiguracaoCampoExtra getEntity(String nome)
	{
		ConfiguracaoCampoExtra configuracaoCampoExtra = getEntity();
		configuracaoCampoExtra.setNome(nome);
		
		return configuracaoCampoExtra;
	}
	
	public static ConfiguracaoCampoExtra getEntity(Long id, String descricao, boolean ativoColaborador, boolean ativoCandidato )
	{
		ConfiguracaoCampoExtra configuracaoCampoExtra = getEntity();
		configuracaoCampoExtra.setId(id);
		configuracaoCampoExtra.setDescricao(descricao);
		configuracaoCampoExtra.setAtivoColaborador(ativoColaborador);
		configuracaoCampoExtra.setAtivoCandidato(ativoCandidato);
		return configuracaoCampoExtra;
	}
}
