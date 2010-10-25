package com.fortes.rh.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class ConverterUtil
{

	public static String convertDoubleToString(Double number)
	{
		NumberFormat formatter = NumberFormat.getInstance(new Locale("pt", "BR"));
		formatter.setGroupingUsed(true);
		formatter.setMinimumFractionDigits(2);
		formatter.setMaximumFractionDigits(2);
		return formatter.format(number.doubleValue());
	}

	public static Double convertStringToDouble(String string) throws ParseException
	{
		NumberFormat formatter = NumberFormat.getInstance(new Locale("pt", "BR"));
		formatter.setGroupingUsed(true);
		formatter.setMinimumFractionDigits(2);
		Number object = formatter.parse(string);
		return new Double(object.doubleValue());
	}
}
