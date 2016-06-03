package com.fortes.webwork.interceptor;

import com.fortes.rh.security.SecurityUtil;
import com.opensymphony.webwork.interceptor.TokenInterceptor;
import com.opensymphony.xwork.ActionInvocation;

public class MyTokenInterceptor extends TokenInterceptor
{
	private static final long serialVersionUID = -6003499432798881081L;

	protected String doIntercept(ActionInvocation invocation) throws Exception
	{
		String token = null;
		if (invocation.getInvocationContext().getParameters().get("internalToken") != null)
			token = ((String[]) invocation.getInvocationContext().getParameters().get("internalToken"))[0];

		if (SecurityUtil.isTokenValido(token))
			return handleValidToken(invocation);
		else
			return handleInvalidToken(invocation);
	}
}
