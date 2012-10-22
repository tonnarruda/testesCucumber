package com.fortes.rh.util;


public class BooleanUtil
{
	public static Boolean getValueCombo(char valueCombo) 
	{
		Boolean value = null;
		if (valueCombo == 'S' || valueCombo == 's' )
			value = true;
		if (valueCombo == 'N' || valueCombo == 'n')
			value = false;
		
		return value;
	}

	public static char setValueCombo(Boolean valor) 
	{
		if (valor == null)
			return 'T';
		
		if (valor)
			return 'S';
		else
			return 'N';
	}

	public static String getDescricao(char ativa) 
	{
		if (ativa == 'S' || ativa == 's' )
			return "Sim";
		if (ativa == 'N' || ativa == 'n')
			return "Não";
		
		return "Todas";
	}

}
