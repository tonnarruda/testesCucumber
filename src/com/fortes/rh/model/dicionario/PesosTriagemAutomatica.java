package com.fortes.rh.model.dicionario;

import java.util.HashMap;


@SuppressWarnings("serial")
public class PesosTriagemAutomatica extends HashMap<String, Integer>
{
	public PesosTriagemAutomatica()
	{
		put("escolaridade", 2);
		put("cidade", 1);
		put("sexo", 1);
		put("idade", 1);
		put("cargo", 3);
		put("tempoExperiencia", 2);
		put("pretensaoSalarial", 2);
	}
}