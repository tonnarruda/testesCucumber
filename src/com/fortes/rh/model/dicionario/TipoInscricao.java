package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class TipoInscricao extends LinkedHashMap<Integer, String>
{
	public static final int CNPJ = 1;
	public static final int CPF = 2;
	public static final int CAEPF = 3;
	public static final int CNO = 4;
	private static TipoInscricao tipoInscricao;

	public TipoInscricao(){
		put(CNPJ, "CNPJ");
		put(CPF, "CPF");
		put(CAEPF, "CAEPF (Cadastro de Atividade Econômica de Pessoa Física)");
		put(CNO, "CNO (Cadastro Nacional de Obra)");
	}
	
	public static TipoInscricao getInstance()
	{
		if (tipoInscricao == null)
			tipoInscricao = new TipoInscricao(); 

		return tipoInscricao;
	}
	
	public static TipoInscricao getInstanceCAT()
	{
		if (tipoInscricao == null)
			tipoInscricao = new TipoInscricao();
		
		tipoInscricao.remove(CAEPF);
		tipoInscricao.remove(CNO);

		return tipoInscricao;
	}
}
