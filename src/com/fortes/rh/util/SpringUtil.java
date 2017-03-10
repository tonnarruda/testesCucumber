package com.fortes.rh.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.webwork.ServletActionContext;

/**
 * @author Igo Coelho
 * 29/08/2006
 *
 * Classe responsável por fornecer acesso aos recursos do Spring
 */
@Component
public class SpringUtil
{
	/** Lista de todos os arquivos de contexto do Spring */
	private static String[] contexts = {"applicationContext.xml",
			"applicationContext-security.xml",
			"applicationContext-datasource.xml",
			"applicationContext-xfire.xml",

			"com/fortes/rh/dao/acesso/applicationContext-dao.xml",
			"com/fortes/rh/dao/cargosalario/applicationContext-dao.xml",
			"com/fortes/rh/dao/captacao/applicationContext-dao.xml",
			"com/fortes/rh/dao/desenvolvimento/applicationContext-dao.xml",
			"com/fortes/rh/dao/geral/applicationContext-dao.xml",
			"com/fortes/rh/dao/pesquisa/applicationContext-dao.xml",
			"com/fortes/rh/dao/security/applicationContext-dao.xml",
			"com/fortes/rh/dao/sesmt/applicationContext-dao.xml",
			"com/fortes/rh/dao/avaliacao/applicationContext-dao.xml",

			"com/fortes/rh/dao/hibernate/acesso/applicationContext-dao-hibernate.xml",
			"com/fortes/rh/dao/hibernate/cargosalario/applicationContext-dao-hibernate.xml",
			"com/fortes/rh/dao/hibernate/captacao/applicationContext-dao-hibernate.xml",
			"com/fortes/rh/dao/hibernate/desenvolvimento/applicationContext-dao-hibernate.xml",
			"com/fortes/rh/dao/hibernate/geral/applicationContext-dao-hibernate.xml",
			"com/fortes/rh/dao/hibernate/pesquisa/applicationContext-dao-hibernate.xml",
			"com/fortes/rh/dao/hibernate/security/applicationContext-dao-hibernate.xml",
			"com/fortes/rh/dao/hibernate/sesmt/applicationContext-dao-hibernate.xml",
			"com/fortes/rh/dao/hibernate/avaliacao/applicationContext-dao-hibernate.xml",

			"com/fortes/rh/business/acesso/applicationContext-business.xml",
			"com/fortes/rh/business/cargosalario/applicationContext-business.xml",
			"com/fortes/rh/business/captacao/applicationContext-business.xml",
			"com/fortes/rh/business/desenvolvimento/applicationContext-business.xml",
			"com/fortes/rh/business/geral/applicationContext-business.xml",
			"com/fortes/rh/business/pesquisa/applicationContext-business.xml",
			"com/fortes/rh/business/sesmt/applicationContext-business.xml",
			"com/fortes/rh/business/avaliacao/applicationContext-business.xml",
			"com/fortes/rh/business/security/applicationContext-business.xml",
			"com/fortes/rh/config/backup/applicationContext-backup.xml"};
	private static ClassPathXmlApplicationContext appContext = null;

	/**
	 * Singleton responsavel em fornecer o objeto de acesso ao contexto do Spring
	 */
	public static ClassPathXmlApplicationContext getInstance()
	{
		if(appContext == null)
			appContext = new ClassPathXmlApplicationContext(contexts);
		return appContext;
	}

	/**
	 * Retorna o bean de acordo com o nome informado.
	 * Utilizado apenas nas classes do DWR e deve em breve ser removido pelo fato de levantar um novo
	 * contexto para o Spring.
	 */
	@Deprecated
	public static Object getBeanOld(String bean)
	{
		return getInstance().getBean(bean);
	}

	/**
	 *  Retorna o bean de acordo com o nome informado.
	 */
	public static Object getBean(String bean)
	{
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
		return ctx.getBean(bean);
	}
	

	private static SpringUtil instance;
	@Autowired
    private ApplicationContext applicationContext;
	
	 @PostConstruct
	 public void registerInstance() {
		 instance = this;
	 }
	 
	 public static <T> T getBean(Class<T> clazz) {
	     return instance.applicationContext.getBean(clazz);
	 }
}