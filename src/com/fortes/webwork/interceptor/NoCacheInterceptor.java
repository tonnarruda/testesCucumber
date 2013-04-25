package com.fortes.webwork.interceptor;

import javax.servlet.http.HttpServletResponse;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.Interceptor;

public class NoCacheInterceptor implements Interceptor
{
	public void destroy() { }

	public void init() { }

	public String intercept(ActionInvocation invocation) throws Exception
	{
        /*invocation.addPreResultListener (
        	new PreResultListener()
        	{
        		public void beforeResult(ActionInvocation invocation, String result)
        		{
        			HttpServletResponse response = ServletActionContext.getResponse();
        	        response.addHeader("Expires", "0");
        	        response.addHeader("Pragma", "no-cache, no-store");
        	        response.addHeader("Cache-Control", "no-cache, no-store");
        		}
        	}
        );*/

		HttpServletResponse response = ServletActionContext.getResponse();
        response.addHeader("Expires", "0");
        response.addHeader("Pragma", "no-cache, no-store");
        response.addHeader("Cache-Control", "no-cache, no-store");

        return invocation.invoke();
	}
}