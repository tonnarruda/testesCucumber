package com.fortes.rh.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanComparator;

/**
 * @author Igo Coelho
 * @since 29/08/2006
 *
 * Classe de utilidades para trabalho com o framework collections */
@SuppressWarnings({"unchecked", "rawtypes"})
public final class CollectionUtil<T>
{
	/** Responsável por converter um Collection informado em um Map */
	public Map convertCollectionToMap(Collection<T> coll, String methodKey, String methodValue)
	{
		if(coll != null)
		{
			Map map = new LinkedHashMap();
			Method mKey   = null;
			Method mValue = null;

			try
			{
				for(T clazz: coll)
				{
					mKey   = clazz.getClass().getMethod(methodKey);
					mValue = clazz.getClass().getMethod(methodValue);

					map.put(mKey.invoke(clazz,new Object[]{}), mValue.invoke(clazz,new Object[]{}));
				}

				return map;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	public static Map convertCollectionToMap(Collection coll, String methodKey, String methodValue, Class type)
	{
		Map map = new LinkedHashMap();
		Method mKey   = null;
		Method mValue = null;

		try
		{
			for(Object clazz: coll)
			{
				mKey   = clazz.getClass().getMethod(methodKey);
				mValue = clazz.getClass().getMethod(methodValue);

				map.put(mKey.invoke(clazz,new Object[]{}), mValue.invoke(clazz,new Object[]{}));
			}

			return map;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/** Responsável por converter um Collection informado em array de Longs com os id's*/
	public Long[] convertCollectionToArrayIds(Collection<T> coll)
	{
		return convertCollectionToArrayIds(coll, "getId");
	}
	
	public Long[] convertCollectionToArrayIds(Collection<T> coll, String metodoGet)
	{
		if(coll != null)
		{
			try
			{
				Method mKey = null;
				Long[] ids = new Long[coll.size()];

				int count = 0;
				for(T clazz: coll)
				{
					mKey   = clazz.getClass().getMethod(metodoGet);
					ids[count] = (Long) mKey.invoke(clazz,new Object[]{});
					count++;
				}

				return ids;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
		}

		return null;
	}

	/** Responsável por converter um Collection informado em array de Longs com os id's convertendo para array de String*/
	public String[] convertCollectionToArrayIdsString(Collection<T> coll)
	{
		if(coll != null)
		{
			try
			{
				Method mKey = null;
				String[] ids = new String[coll.size()];
				
				int count = 0;
				for(T clazz: coll)
				{
					mKey   = clazz.getClass().getMethod("getId");
					ids[count] = ((Long) mKey.invoke(clazz,new Object[]{})).toString();
					count++;
				}
				
				return ids;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
		}
		
		return null;
	}

	/** Responsável por converter um Collection informado em array de String*/
	public String[] convertCollectionToArrayString(Collection<T> coll, String metodoGet)
	{
		if(coll != null && metodoGet != null)
		{
			try
			{
				Method mKey = null;
				String[] str = new String[coll.size()];

				int count = 0;
				for(T clazz: coll)
				{
					mKey   = clazz.getClass().getMethod(metodoGet);
					str[count] = (String) mKey.invoke(clazz,new Object[]{});
					count++;
				}

				return str;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
		}

		return null;
	}

	public static Map<Object, Object> convertCollectionToMap(Collection coll, String methodKey)
	{
		Map map = new LinkedHashMap();
		Method mKey   = null;

		try
		{
			for(Object clazz: coll)
			{
				mKey   = clazz.getClass().getMethod(methodKey);

				map.put(mKey.invoke(clazz,new Object[]{}), clazz);
			}

			return map;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public List convertCollectionToList(Collection coll)
	{
		List listTmp = new ArrayList(coll.size());
		for (Object obj : coll)
		{
			listTmp.add(obj);
		}

		return listTmp;
	}

	public Collection<T> distinctCollection(Collection<T> elementos)
	{
		Collection<T> elementosDistinct = new ArrayList<T>();
		for (T t : (Collection<T>)(new HashSet(elementos)))
		{
			elementosDistinct.add(t);
		}

		return elementosDistinct;
	}

	public Collection<T> sortCollection(Collection<T> col, String ordenarPor)
	{
		Comparator comp = new BeanComparator(ordenarPor, new Comparator() {
			public int compare(Object o1, Object o2)
			{
				return ((Integer)o1).compareTo((Integer)o2);
			}
		});

		Collections.sort((List) col, comp);

		return col;
	}

	public Collection<T> sortCollectionDesc(Collection<T> col, String ordenarPor)
	{
		Comparator comp = new BeanComparator(ordenarPor, new Comparator() {
			public int compare(Object o1, Object o2)
			{
				return ((Integer)o2).compareTo((Integer)o1);
			}
		});

		Collections.sort((List) col, comp);

		return col;
	}

	public Collection<T> sortCollectionStringIgnoreCase(Collection<T> col, String ordenarPor)
	{
		Comparator comp = new BeanComparator(ordenarPor, new Comparator() {
			public int compare(Object o1, Object o2)
			{
				return ((String)o1.toString().toLowerCase()).compareTo((String)o2.toString().toLowerCase());
			}
		});

		try
		{
			Collections.sort((List) col, comp);			
		} catch (Exception e)
		{
		}

		return col;
	}

	public Collection<T> sortCollectionDate(Collection<T> col, String ordenarPor)
	{
		Comparator comp = new BeanComparator(ordenarPor, new Comparator() {
			public int compare(Object o1, Object o2)
			{
				return ((Date)o2).compareTo((Date)o1);
			}
		});

		Collections.sort((List) col, comp);

		return col;
	}
	
	public Collection<T> sortCollectionDouble(Collection<T> col, String ordenarPor)
	{
		Comparator comp = new BeanComparator(ordenarPor, new Comparator() {
			public int compare(Object o1, Object o2)
			{
				return ((Double)o2).compareTo((Double)o1);
			}
		});
		
		Collections.sort((List) col, comp);
		
		return col;
	}
	
	public Collection<T> sortCollectionBoolean(Collection<T> col, String ordenarPor, final String ascOrDesc)
	{
		Comparator comp = new BeanComparator(ordenarPor, new Comparator() {
			public int compare(Object o1, Object o2)
			{
				if(ascOrDesc.equals("asc"))
					return ((Boolean)o1).compareTo((Boolean)o2);
				else
					return ((Boolean)o2).compareTo((Boolean)o1);
			}
		});
		
		Collections.sort((List) col, comp);
		
		return col;
	}

	public Collection<T> sortCollectionDate(Collection<T> col, String ordenarPor, final String ascOrDesc)
	{
		Comparator comp = new BeanComparator(ordenarPor, new Comparator() {
			public int compare(Object o1, Object o2)
			{
				if(ascOrDesc.equals("asc"))
					return ((Date)o1).compareTo((Date)o2);
				else
					return ((Date)o2).compareTo((Date)o1);
			}
		});

		Collections.sort((List) col, comp);

		return col;
	}

	public Collection<T> convertArrayToCollection(T[] ts)
	{
		if (ts == null) return null;
		
		Collection<T> col = new ArrayList<T>();

		for (int i = 0; i < ts.length; i++)
		{
			col.add(ts[i]);
		}

		return col;
	}
	
	public Collection<T> convertArrayStringToCollection(Class<T> type, String[] ids) throws Exception
	{
		Collection<T> col = new ArrayList<T>();
		
		if (ids != null)
		{
			for (String id : ids)
			{
				T entidade = type.newInstance();
				type.getMethod("setId", Long.class).invoke(entidade, Long.parseLong(id));
				col.add(entidade);
			}
		}
		
		return col;
	}
	
	public Collection<T> convertArrayLongToCollection(Class<T> type, Long[] ids) throws Exception
	{
		Collection<T> col = new ArrayList<T>();
		
		if (ids != null)
		{
			for (Long id : ids)
			{
				T entidade = type.newInstance();
				type.getMethod("setId", Long.class).invoke(entidade, id);
				col.add(entidade);
			}
		}
		
		return col;
	}

	public Collection<T> sortDistinctCollection(Collection<T> col, String ordenarPor)
	{
		return this.sortCollection(this.distinctCollection(col), ordenarPor);
	}

	public String[] convertCollectionToArrayString(Collection<T> col) {
		int count = 0;
		Collection<T> retornoCollection = col;
		if(retornoCollection.size() > 0){
			String[] retorno = new String[retornoCollection.size()];
			for (T t : retornoCollection) 
				retorno[count++] = t.toString();
			return retorno;
		}
	
		return new String[]{};
	}
	
	public Long[] convertCollectionToArrayLong(Collection<T> col) {
		int count = 0;
		Collection<T> retornoCollection = col;
		if(retornoCollection.size() > 0){
			Long[] retorno = new Long[retornoCollection.size()];
			for (T t : retornoCollection) 
				retorno[count++] = new Long(t.toString());
			return retorno;
		}
		
		return new Long[]{};
	}
	
	public <K, V extends Comparable<? super V>> Map<K, V> sortMapByValue( Map<K, V> map )
	{
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>( map.entrySet() );
		Collections.sort( list, new Comparator<Map.Entry<K, V>>()
		{
			public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
			{
				return (o1.getValue()).compareTo( o2.getValue() );
			}
		} );

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list)
		{
			result.put( entry.getKey(), entry.getValue() );
		}
		return result;
	}
	
	public static Object[] collectionToArrayAttribute(Collection<?> modelos, String attributeName) {
		Object[] array = new Object[modelos.size()];
		try {
			if(modelos != null && modelos.size() > 0) {
				int i = 0;
				for (Object model : modelos)
				{
					Class<?> c = model.getClass();
					
					Field f = c.getDeclaredField(attributeName);
					f.setAccessible(true);
					
					array[i] = (Object) f.get(model);
					i++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return array;
	}
}