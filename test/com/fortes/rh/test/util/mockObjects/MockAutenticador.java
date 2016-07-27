package com.fortes.rh.test.util.mockObjects;

import java.util.ArrayList;
import java.util.Collection;

import remprot.RPClient;

import com.fortes.rh.exception.NotConectAutenticationException;

public class MockAutenticador
{
	private static boolean verificaLicensa;
	private static int RECRUT_SELECAO = 1;
	private static int CARGO_SALARIO = 2;
	private static int PESQUISA = 4;
	private static int TRE_DESENV = 8;
	private static int AVAL_DESEMP = 16;
	private static int SESMT = 32;
	
	public static final Collection<Long> modulos = new ArrayList<Long>(8);
	static {
		modulos.add(357L);
		modulos.add(361L);
		modulos.add(353L);
		modulos.add(365L);
		modulos.add(373L);
		modulos.add(382L);
		modulos.add(75L);
		modulos.add(37L);
	}
	
	public static Collection<Long> getModulosNaoConfigurados() throws NotConectAutenticationException
	{
		RPClient c = getRemprot();
			    // somatorio dos modulos do RH: 1  - Recrut. e Seleção ,2  - Cargos e Salários ,4  - Pesquisa ,
				//8  - Treina. e Desenvolvimento ,16 - Avaliação de Desempenho ,32 - SESMT
		return getModulosNaoConfigurados(c.getEnabledModules());

	}
	
	public static Collection<Long> getModulosNaoConfigurados(int chave) 
	{
		Collection<Long> modulosNaoConfigurados = new ArrayList<Long>();
		
		if(verificaLicensa) {
			modulosNaoConfigurados.addAll(modulos);

			if((chave & RECRUT_SELECAO) == RECRUT_SELECAO)
				modulosNaoConfigurados.remove(357L);
			if((chave & CARGO_SALARIO) == CARGO_SALARIO)
				modulosNaoConfigurados.remove(361L);
			if((chave & PESQUISA) == PESQUISA)
				modulosNaoConfigurados.remove(353L);
			if((chave & TRE_DESENV) == TRE_DESENV)
				modulosNaoConfigurados.remove(365L);
			if((chave & AVAL_DESEMP) == AVAL_DESEMP)
				modulosNaoConfigurados.remove(382L);
			if((chave & SESMT) == SESMT)
				modulosNaoConfigurados.remove(75L);
			
			// Estes módulos sempre aparecerão no menu.
			modulosNaoConfigurados.remove(373L);
			modulosNaoConfigurados.remove(37L);
		}
		
		return modulosNaoConfigurados;
	}
	
	public static boolean isDemo()
	{
		return false;
	}
	
	public static RPClient getRemprot()
	{
		return new RPClient(33, "RH");
	}
}