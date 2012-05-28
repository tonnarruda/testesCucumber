package com.fortes.xwork.converters;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Map;

import ognl.DefaultTypeConverter;

import com.opensymphony.xwork.XworkException;

/** 
 * @author Marlus Saraiva
 */
public class DoubleConverter extends DefaultTypeConverter
{
	/** Number formatter. */
	private NumberFormat formatter = NumberFormat.getInstance();

	/**
	 * Converts values from String to Double and vice-versa.
	 * 
	 * @param context
	 *            OGNL context.
	 * @param value
	 *            Value to be converted.
	 * @param toType
	 *            Class to which to convert.
	 * @return Converted value.
	 */
	public Object convertValue(Map context, Object value, Class toType)
	{
		Object result = null;

		// Double to String.
		if ((toType == String.class) && (value instanceof Double))
		{
			result = convertDoubleToString((Double) value);
		}

		// String to Double.
		else if ((toType == Double.class) && (value instanceof String))
		{
			result = convertStringToDouble((String) value);
		}

		// String[] to Double.
		else if (value instanceof Object[])
		{
			Object[] array = (Object[]) value;
			result = (array.length > 0) ? convertStringToDouble((String) array[0]) : null;
		}

		// Returns the result, or null if no appropriate conversion found.
		return result;
	}

	/**
	 * Converts a string to a Double.
	 * 
	 * @param string
	 *            The string to convert.
	 * @return The converted Double object.
	 */
	private Double convertStringToDouble(String string)
	{
		// Parses the string according to the number formatter.
		try
		{
			//if (string != null && (string.indexOf('.') > 0))
			//	throw new XworkException("Could not parse number. Use ',' instead of '.'");
			formatter.setGroupingUsed(true);
			formatter.setMinimumFractionDigits(2);			
			Number object = formatter.parse(string);
			return new Double(object.doubleValue());
		}
		catch (ParseException e)
		{
			throw new XworkException("Could not parse number", e);
		}
		catch (Exception e)
		{
			throw new XworkException("Error converting number", e);
		}
	}

	/**
	 * Converts a Double to a string.
	 * 
	 * @param number
	 *            The Double to convert.
	 * @return The converted String object.
	 */
	private String convertDoubleToString(Double number)
	{		
		formatter.setGroupingUsed(true);
		formatter.setMinimumFractionDigits(2);
		// Parses the string according to the number formatter.
		return formatter.format(number.doubleValue());
	}
}
