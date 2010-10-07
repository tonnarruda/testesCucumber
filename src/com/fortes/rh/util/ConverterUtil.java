package com.fortes.rh.util;

import java.text.NumberFormat;
import java.text.ParseException;

public class ConverterUtil
{
	private static NumberFormat formatter = NumberFormat.getInstance();

	public static String convertDoubleToString(Double number)
	{
		formatter.setGroupingUsed(true);
		formatter.setMinimumFractionDigits(2);
		formatter.setMaximumFractionDigits(2);
		return formatter.format(number.doubleValue());
	}

	public static Double convertStringToDouble(String string) throws ParseException
	{
		formatter.setGroupingUsed(true);
		formatter.setMinimumFractionDigits(2);
		Number object = formatter.parse(string);
		return new Double(object.doubleValue());
	}
}
