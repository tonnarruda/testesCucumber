package com.fortes.rh.util;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class MesComparator implements Comparator<String>
{
	private SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy", new Locale("pt", "BR"));
	public int compare(String mes1, String mes2)
	{
		int retorno = 0;
		
		try
		{
			Date data1 = sdf.parse(mes1);
			Date data2 = sdf.parse(mes2);
			
			retorno = data1.compareTo(data2);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return retorno;
	}

}
