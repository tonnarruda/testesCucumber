package com.fortes.rh.test.util.mockObjects;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MockSpringUtilJUnit4
{
	public static Map<String, Object> mocks = new HashMap<String, Object>();

	public static Object getBeanOld(String bean)
	{
		return mocks.get(bean);
	}

	public static Object getBean(String bean)
	{
		return mocks.get(bean);
	}

	public static ClassPathXmlApplicationContext getInstance()
	{
		return null;
	}

	private static String[] contexts = {"applicationContext.xml",
		"applicationContext-security.xml",
		"applicationContext-datasource.xml",

		"com/fortes/rh/dao/acesso/applicationContext-dao.xml",
		"com/fortes/rh/dao/cargosalario/applicationContext-dao.xml",
		"com/fortes/rh/dao/captacao/applicationContext-dao.xml",
		"com/fortes/rh/dao/desenvolvimento/applicationContext-dao.xml",
		"com/fortes/rh/dao/geral/applicationContext-dao.xml",
		"com/fortes/rh/dao/pesquisa/applicationContext-dao.xml",
		"com/fortes/rh/dao/security/applicationContext-dao.xml",
		"com/fortes/rh/dao/sesmt/applicationContext-dao.xml",

		"com/fortes/rh/dao/hibernate/acesso/applicationContext-dao-hibernate.xml",
		"com/fortes/rh/dao/hibernate/cargosalario/applicationContext-dao-hibernate.xml",
		"com/fortes/rh/dao/hibernate/captacao/applicationContext-dao-hibernate.xml",
		"com/fortes/rh/dao/hibernate/desenvolvimento/applicationContext-dao-hibernate.xml",
		"com/fortes/rh/dao/hibernate/geral/applicationContext-dao-hibernate.xml",
		"com/fortes/rh/dao/hibernate/pesquisa/applicationContext-dao-hibernate.xml",
		"com/fortes/rh/dao/hibernate/security/applicationContext-dao-hibernate.xml",
		"com/fortes/rh/dao/hibernate/sesmt/applicationContext-dao-hibernate.xml",

		"com/fortes/rh/business/acesso/applicationContext-business.xml",
		"com/fortes/rh/business/cargosalario/applicationContext-business.xml",
		"com/fortes/rh/business/captacao/applicationContext-business.xml",
		"com/fortes/rh/business/desenvolvimento/applicationContext-business.xml",
		"com/fortes/rh/business/geral/applicationContext-business.xml",
		"com/fortes/rh/business/pesquisa/applicationContext-business.xml",
		"com/fortes/rh/business/sesmt/applicationContext-business.xml",
		"com/fortes/rh/business/security/applicationContext-business.xml"};
private static ClassPathXmlApplicationContext appContext = null;
}
