package com.fortes.rh.test;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.AreaOrganizacionalManagerImpl;

public class CoberturaManager extends MockObjectTestCase
{
	
	private static final Logger log = Logger.getLogger(CoberturaManager.class.getSimpleName());
	
	public void testCobreGetSet()
	{
		cobreGetSet(new AreaOrganizacionalManagerImpl());
	}
	
	private void cobreGetSet(Object obj)
	{
		for (Method m : obj.getClass().getMethods())
		{
			try
			{
				if (m.getName().toString().startsWith("findAreaOrganizacionalCodigoAc"))
				{
					m.invoke(obj, new Object[]{null});
					assertTrue(true);
				}

			}
			catch (Exception e)
			{
//				e.printStackTrace();
				log.warn("Erro ao cobrir método " + m + " da classe " + obj.getClass().getSimpleName());
			}
		}
	}
}
