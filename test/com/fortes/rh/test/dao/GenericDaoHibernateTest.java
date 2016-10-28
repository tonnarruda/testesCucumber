package com.fortes.rh.test.dao;

import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.dao.GenericDao;

public abstract class GenericDaoHibernateTest<T> extends BaseDaoHibernateTest
{
	
	protected static final Logger logger = Logger.getLogger(GenericDaoHibernateTest.class);
	
	public abstract GenericDao<T> getGenericDao();

	public abstract T getEntity();

	public void testSave() throws Exception
	{
		T entity = getEntity();
		entity = getGenericDao().save(entity);
		Long id = getIdFrom(entity);
		assertNotNull(id);
	}

	private Long getIdFrom(T entity) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		return (Long) (entity.getClass().getMethod("getId",new Class[]{}).invoke(entity, new Object[]{}));
	}

	public void testFindById() throws Exception
	{
		T entity = getEntity();
		entity = getGenericDao().save(entity);
		Long id = getIdFrom(entity);

		entity = null;
		entity = getGenericDao().findById(id);

		assertNotNull(entity);
	}

	public void testUpdate() throws Exception
	{
		T entity = getEntity();
		entity = getGenericDao().save(entity);
		Long id = getIdFrom(entity);
		

		getGenericDao().update(entity);

		entity = getGenericDao().findById(id);

		assertNotNull(entity);
	}

	public void testRemove() throws Exception
	{
		T entity = getEntity();
		entity = getGenericDao().save(entity);
		Long id = getIdFrom(entity);

		getGenericDao().remove(id);

		Exception ex = null;

		try {
			entity = getGenericDao().findById(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			ex = e;
		}

		assertNotNull(ex);
		assertTrue(ex instanceof HibernateObjectRetrievalFailureException);
	}
}
