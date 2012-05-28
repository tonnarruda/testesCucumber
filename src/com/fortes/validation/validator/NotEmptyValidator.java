package com.fortes.validation.validator;

import java.io.Serializable;

import org.hibernate.validator.Validator;

@SuppressWarnings("serial")
public class NotEmptyValidator implements Validator<NotEmpty>, Serializable
{
	public boolean isValid(Object value)
	{		
		if ((value instanceof String) && value.equals(""))
			return false;
		return true;
	}

	public void initialize(NotEmpty parameters)
	{
	}
}
