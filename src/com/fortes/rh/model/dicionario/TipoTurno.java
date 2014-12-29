package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings("serial")
public class TipoTurno extends LinkedHashMap<Character, String>
{
	public static final char DIA = 'D';
	public static final char MANHA = 'M';
	public static final char TARDE = 'T';
	public static final char NOITE = 'N';

	public TipoTurno()
	{
		put(DIA, null);
		put(MANHA, "manhã");
		put(TARDE, "tarde");
		put(NOITE, "noite");
	}
	
	public static String getDescricao(Character statusSolicitacao)
	{
		TipoTurno tipoTurno = new TipoTurno();
		return tipoTurno.get(statusSolicitacao);
	}
	
	public static int getQtdTurnos()
	{
		TipoTurno tipoTurno = new TipoTurno();
		return tipoTurno.size() - 1;
	}

	public static char getTipoTurnoByStringConteins(String descricao)
	{
		if(StringUtils.contains(descricao, TipoTurno.getDescricao(TipoTurno.MANHA)))
			return TipoTurno.MANHA;
		if(StringUtils.contains(descricao, TipoTurno.getDescricao(TipoTurno.TARDE)))
			return TipoTurno.TARDE;
		if(StringUtils.contains(descricao, TipoTurno.getDescricao(TipoTurno.NOITE)))
			return TipoTurno.NOITE;
		
		return TipoTurno.DIA;
	}
}
