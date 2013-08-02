package com.fortes.rh.util;

import java.util.Arrays;


public final class ArrayUtil
{
	public static Object[] add(Object[] object, Object newObject) 
	{
		if(object == null)
			object = new Character[0];
		
		object = Arrays.copyOf(object, object.length +1);
		object[object.length-1] = newObject;
		return object;
	}

	public static Object[] addAll(Object[] object1, Object[] object2) 
	{
		if(object1 == null)
			object1 = new Character[0];
		
		if(object2 == null)
			object2 = new Character[0];
		
		Object[] objects = Arrays.copyOf(object1, object2.length + object1.length);
		System.arraycopy(object2, 0, objects, object1.length, object2.length);
		return objects;
	}

}