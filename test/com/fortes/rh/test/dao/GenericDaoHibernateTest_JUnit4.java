package com.fortes.rh.test.dao;

import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.dao.GenericDao;

public abstract class GenericDaoHibernateTest_JUnit4<T> extends DaoHibernateAnnotationTest
{
	protected static final Logger logger = Logger.getLogger(GenericDaoHibernateTest_JUnit4.class);
	
	public abstract GenericDao<T> getGenericDao();

	public abstract T getEntity();

	@Test
	public void testSave() throws Exception
	{
		T entity = getEntity();
		entity = getGenericDao().save(entity);
		Long id = getIdFrom(entity);
		Assert.assertNotNull(id);
	}

	private Long getIdFrom(T entity) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		return (Long) (entity.getClass().getMethod("getId",new Class[]{}).invoke(entity, new Object[]{}));
	}

	@Test
	public void testFindById() throws Exception
	{
		T entity = getEntity();
		entity = getGenericDao().save(entity);
		Long id = getIdFrom(entity);

		entity = null;
		entity = getGenericDao().findById(id);

		Assert.assertNotNull(entity);
	}

	@Test
	public void testUpdate() throws Exception
	{
		T entity = getEntity();
		entity = getGenericDao().save(entity);
		Long id = getIdFrom(entity);
		

		getGenericDao().update(entity);

		entity = getGenericDao().findById(id);

		Assert.assertNotNull(entity);
	}

	@Test
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

		Assert.assertNotNull(ex);
		Assert.assertTrue(ex instanceof HibernateObjectRetrievalFailureException);
	}
}
