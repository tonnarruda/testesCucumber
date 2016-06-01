package com.fortes.webwork.interceptor;

import java.util.Map;

import com.opensymphony.webwork.interceptor.TokenInterceptor;
import com.opensymphony.webwork.util.TokenHelper;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;

public class MyTokenInterceptor extends TokenInterceptor
{
	private static final long serialVersionUID = -6003499432798881081L;

	protected String doIntercept(ActionInvocation invocation) throws Exception {

		Map session = ActionContext.getContext().getSession();
		System.out.println(session.get("webwork.token"));
		System.out.println(TokenHelper.getToken());

		synchronized (session) {
			if (!TokenHelper.validToken()) {
				return handleInvalidToken(invocation);
			}

			return handleValidToken(invocation);
		}
	}
	    
	protected String handleInvalidToken(ActionInvocation invocation) throws Exception 
	{
		invocation.invoke();

		return INVALID_TOKEN_CODE;
	}
	
	protected String handleValidToken(ActionInvocation invocation) throws Exception
	{
		return invocation.invoke();
	}
}
