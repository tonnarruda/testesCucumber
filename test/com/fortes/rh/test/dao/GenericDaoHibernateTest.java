package com.fortes.rh.test.dao;

import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.dao.GenericDao;

public abstract class GenericDaoHibernateTest<T> extends BaseDaoHibernateTest
{
	public abstract GenericDao<T> getGenericDao();

	public abstract T getEntity();

	public void testSave() throws Exception
	{
		T entity = getEntity();
		entity = getGenericDao().save(entity);
		Long id = (Long) (entity.getClass().getMethod("getId",new Class[]{}).invoke(entity, new Object[]{}));
		assertNotNull(id);
	}

	public void testFindById() throws Exception
	{
		T entity = getEntity();
		entity = getGenericDao().save(entity);
		Long id = (Long) (entity.getClass().getMethod("getId",new Class[]{}).invoke(entity, new Object[]{}));

		entity = null;
		entity = getGenericDao().findById(id);

		assertNotNull(entity);
	}

	/*public void testFind() throws Exception
	{
		T entity = getEntity();
		entity = getGenericDao().save(entity);

		Collection<T> entitys = getGenericDao().find(entity);

		assertFalse(entitys.isEmpty());
		//assertTrue(entitys.isEmpty());
	}*/

	/*public void testFindAll() throws Exception
	{
		T entity = getEntity();
		entity = getGenericDao().save(entity);

		Collection<T> entitys = getGenericDao().findAll();

		assertFalse(entitys.isEmpty());
	}*/

	public void testUpdate() throws Exception
	{
		T entity = getEntity();
		entity = getGenericDao().save(entity);
		Long id = (Long) (entity.getClass().getMethod("getId",new Class[]{}).invoke(entity, new Object[]{}));

		getGenericDao().update(entity);

		entity = getGenericDao().findById(id);

		assertNotNull(entity);
	}

	public void testRemove() throws Exception
	{
		T entity = getEntity();
		entity = getGenericDao().save(entity);
		Long id = (Long) (entity.getClass().getMethod("getId",new Class[]{}).invoke(entity, new Object[]{}));

		getGenericDao().remove(id);

		Exception ex = null;

		try {
			entity = getGenericDao().findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			ex = e;
		}

		assertNotNull(ex);
		assertTrue(ex instanceof HibernateObjectRetrievalFailureException);
	}
}
