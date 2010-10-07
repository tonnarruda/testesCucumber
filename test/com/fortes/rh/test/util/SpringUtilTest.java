package com.fortes.rh.test.util;

import mockit.Mockit;

import org.jmock.MockObjectTestCase;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.business.ws.RHServiceImpl;
import com.fortes.rh.test.util.mockObjects.MockWebApplicationContext;
import com.fortes.rh.test.util.mockObjects.MockWebApplicationContextUtils;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.SpringUtil;

public class SpringUtilTest extends MockObjectTestCase
{
	@Override
	protected void setUp()
	{
		Mockit.redefineMethods(WebApplicationContextUtils.class, MockWebApplicationContextUtils.class);
	}

	@SuppressWarnings("deprecation")
	public void testGetBeanOld()
	{
		Object mail = SpringUtil.getBeanOld("mail");
		Object RHService = SpringUtil.getBeanOld("rhService");
		Object funcaoManager = SpringUtil.getBeanOld("funcaoManager");

		assertTrue(mail instanceof Mail);
		assertTrue(RHService instanceof RHServiceImpl);
		assertTrue(funcaoManager instanceof FuncaoManager);
	}

	public void testGetBean()
	{
		MockWebApplicationContext.mapa.put("mail", new Mail());

		Object mail = SpringUtil.getBean("mail");
		assertTrue(mail instanceof Mail);
	}

   protected void tearDown() throws Exception
    {
    	Mockit.restoreAllOriginalDefinitions();
    }
}