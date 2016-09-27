package com.fortes.rh.test.util.mockObjects;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork.ActionContext;

public class MockActionContext
{
	static Map context = null;

	public static ActionContext getContext()
	{
		if(context == null)
			context = new HashMap();

		return new ActionContext(context);
	}

	public Map getSession()
	{
		return context;
	}

}
