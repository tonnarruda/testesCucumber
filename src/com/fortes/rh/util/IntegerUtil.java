package com.fortes.rh.util;


public class IntegerUtil
{
	public static Integer[] arrayStringToArrayInteger(String[] array)
	{
		if(array == null || array.length == 0 || (array.length == 1 && array[0].equals("[]")))
			return new Integer[0];

		Integer result[] = new Integer[array.length];
		int cont = 0;

		for (String str : array)
		{
			try
			{
				result[cont] = Integer.parseInt(str.trim());
			} catch (Exception e)
			{
				result[cont] = null;
			}
			
			cont++;
		}

		return result;
	}
}
