package com.fortes.displaytag;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

public class DateFormater implements DisplaytagColumnDecorator {

	private DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat mDateFor = new SimpleDateFormat("yyyy-MM-dd");
	
	public Object decorate(Object pColumnValue, PageContext arg1, MediaTypeEnum arg2) throws DecoratorException 
	{
		Date lDate = null;

		try 
		{
			lDate = (Date) pColumnValue;
		} 
		catch (Exception e) 
		{
			try 
			{
				lDate = mDateFor.parse(pColumnValue.toString().trim());
			} 
			catch (ParseException pe) 
			{
				pe.printStackTrace();
			}
			System.out.println("WARNING: Date parsed from String.");
		}
		return mDateFormat.format(lDate);
	}
	
	/*
	public final String decorate(Object pColumnValue) {
		Date lDate = null;

		try {
			lDate = (Date) pColumnValue;
		} catch (Exception e) {
			try {
				lDate = mDateFor.parse(pColumnValue.toString().trim());
			} catch (ParseException pe) {
				pe.printStackTrace();
			}
			System.out.println("WARNING: Date parsed from String.");
		}

		return mDateFormat.format(lDate);
	}*/
}
