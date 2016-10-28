package com.fortes.validation.aop.advice;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

public class ModelValidationAdvice implements MethodBeforeAdvice
{
	@SuppressWarnings("unchecked")
	public void before(Method m, Object[] args, Object target) throws Throwable
	{
		if ((args.length) > 0 && args[0] != null)			
		{
			Object model = args[0];
//			ClassValidator validator = new ClassValidator(model.getClass());
//			InvalidValue[] invalidValues = validator.getInvalidValues(model);
//			if (invalidValues.length > 0)
//				throw new InvalidStateException(invalidValues);
		}
	}
}