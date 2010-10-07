package com.fortes.rh.util;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.model.AbstractModel;

@SuppressWarnings("unchecked")
public class LongUtil
{
	public static Long[] collectionStringToArrayLong(Collection colecao){

		Long[] arrayDeLong = new Long[colecao.size()];

		int i = 0;
		for (Object obj : colecao)
		{
			arrayDeLong[i] = Long.valueOf(obj.toString());
			i++;
		}
		return arrayDeLong;
	}

	public static Collection<Long> arrayStringToCollectionLong(String[] colecao)
	{
		if (colecao == null || colecao.length == 0)
			return new ArrayList<Long>();

		Collection<Long> result = new ArrayList<Long>();

		for (int i = 0; i < colecao.length; i++)
		{
			result.add(Long.valueOf(colecao[i]));
		}

		return result;
	}

	public static Long[] arrayStringToArrayLong(String[] array)
	{
		if(array == null || (array.length == 1 && array[0].equals("[]")))
			return new Long[0];

		Long result[] = new Long[array.length];
		int cont = 0;

		for (String str : array)
		{
			result[cont] = Long.parseLong(str.trim());
			cont++;
		}

		return result;
	}

	public static boolean contains(Long id, Collection<? extends AbstractModel> modelos) {
		
		if (modelos == null)
			return false;
		
		for (AbstractModel model : modelos)
		{
			if (id.equals(model.getId()))
				return true;
		}
		
		return false;
	}

	public static Collection<Long> collectionToCollectionLong(Collection<? extends AbstractModel> modelos) 
	{
		Collection<Long> ids = new ArrayList<Long>();
		
		for (AbstractModel model : modelos) 
		{
			ids.add(model.getId());
		}
		
		return ids;
	}
}
