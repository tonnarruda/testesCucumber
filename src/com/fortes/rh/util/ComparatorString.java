package com.fortes.rh.util;

import java.util.Comparator;

public class ComparatorString implements Comparator
{
	//TODO verifica o1 != null para não lançar null pointer 
    public int compare(Object o1, Object o2)
    {
    	if(o1 != null)
    	{
    		try
    		{
    			return ((String)o1).compareTo((String)o2);
    		}
    		catch (NullPointerException e)
    		{
    			return -1;
    		}
    	}
    	return -1;
    }
}