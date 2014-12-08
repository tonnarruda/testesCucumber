package com.fortes.rh.test.dao;

import javax.sql.DataSource;

import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.test.AbstractTransactionalSpringContextTests;

public abstract class BaseDaoHibernateTest extends AbstractTransactionalSpringContextTests
{
	@Override
	protected String[] getConfigLocations()
	{
        setAutowireMode(AUTOWIRE_BY_NAME);
        return new String [] {
    			"applicationContext.xml",
    			"applicationContext-security.xml",
    			"applicationContext-datasource-test.xml",

    			"com/fortes/rh/dao/acesso/applicationContext-dao.xml",
    			"com/fortes/rh/dao/cargosalario/applicationContext-dao.xml",
    			"com/fortes/rh/dao/captacao/applicationContext-dao.xml",
    			"com/fortes/rh/dao/desenvolvimento/applicationContext-dao.xml",
    			"com/fortes/rh/dao/geral/applicationContext-dao.xml",
    			"com/fortes/rh/dao/pesquisa/applicationContext-dao.xml",
    			"com/fortes/rh/dao/security/applicationContext-dao.xml",
    			"com/fortes/rh/dao/sesmt/applicationContext-dao.xml",
    			"com/fortes/rh/dao/avaliacao/applicationContext-dao.xml",
    			"com/fortes/portalcolaborador/dao/applicationContext-dao.xml",

    			"com/fortes/rh/dao/hibernate/acesso/applicationContext-dao-hibernate.xml",
    			"com/fortes/rh/dao/hibernate/cargosalario/applicationContext-dao-hibernate.xml",
    			"com/fortes/rh/dao/hibernate/captacao/applicationContext-dao-hibernate.xml",
    			"com/fortes/rh/dao/hibernate/desenvolvimento/applicationContext-dao-hibernate.xml",
    			"com/fortes/rh/dao/hibernate/geral/applicationContext-dao-hibernate.xml",
    			"com/fortes/rh/dao/hibernate/pesquisa/applicationContext-dao-hibernate.xml",
    			"com/fortes/rh/dao/hibernate/security/applicationContext-dao-hibernate.xml",
    			"com/fortes/rh/dao/hibernate/sesmt/applicationContext-dao-hibernate.xml",
    			"com/fortes/rh/dao/hibernate/avaliacao/applicationContext-dao-hibernate.xml",
    			"com/fortes/portalcolaborador/dao/hibernate/applicationContext-dao-hibernate.xml",
    			
    			"com/fortes/rh/business/acesso/applicationContext-business.xml",
    			"com/fortes/rh/business/cargosalario/applicationContext-business.xml",
    			"com/fortes/rh/business/captacao/applicationContext-business.xml",
    			"com/fortes/rh/business/desenvolvimento/applicationContext-business.xml",
    			"com/fortes/rh/business/geral/applicationContext-business.xml",
    			"com/fortes/rh/business/pesquisa/applicationContext-business.xml",
    			"com/fortes/rh/business/security/applicationContext-business.xml",
    			"com/fortes/rh/business/sesmt/applicationContext-business.xml",
    			"com/fortes/rh/business/avaliacao/applicationContext-business.xml",
    			"com/fortes/portalcolaborador/business/applicationContext-business.xml",

    			"applicationContext-xfire.xml",
    			"applicationContext-dwr.xml",
    			"com/fortes/rh/config/backup/applicationContext-backup.xml"
    			};
	}
	
	public DataSource getDataSource() {
		HibernateTransactionManager tm = (HibernateTransactionManager) transactionManager;
		return tm.getDataSource();
	}
	
}