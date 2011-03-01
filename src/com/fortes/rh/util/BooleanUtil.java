package com.fortes.rh.util;


public class BooleanUtil
{
	public static Boolean getValueCombo(char respondida) 
	{
		Boolean value = null;
		if (respondida == 'S' || respondida == 's' )
			value = true;
		if (respondida == 'N' || respondida == 'n')
			value = false;
		
		return value;
	}

	public static char setValueCombo(Boolean apto) 
	{
		if (apto == null)
			return 'T';
		
		if (apto)
			return 'S';
		else
			return 'N';
	}

}
