package com.fortes.business;

import java.util.Collection;

import org.springframework.beans.factory.InitializingBean;

import com.fortes.dao.GenericDao;

public class GenericManagerImpl<T, D extends GenericDao<T>> implements GenericManager<T>, InitializingBean
{
	private D dao;

	public D getDao()
	{
		return dao;
	}

	public void setDao(D dao)
	{
		this.dao = dao;
	}

	public T save(T entity)
	{
		return (T) dao.save(entity);
	}

	public void update(T entity)
	{
		dao.update(entity);
	}
	
	public void saveOrUpdate(T entity)
	{
		dao.saveOrUpdate(entity);
	}

	public void saveOrUpdate(Collection<T> entities)
	{
		dao.saveOrUpdate(entities);
	}

	public String[] findDependentTables(Long id)
	{
		return getDao().findDependentTables(id);
	}
	
	public void remove(Long[] ids)
	{
		dao.remove(ids);
	}

	public void remove(Long id)
	{
		dao.remove(id);
	}

	public void remove(T entity)
	{
		dao.remove(entity);
	}

	public T findById(Long id)
	{
		return (T) dao.findById(id);
	}

	public T findById(Long id, String[] fetch)
	{
		return (T) getDao().findById(id, fetch);
	}

	public Collection<T> findById(String[] ids)
	{
		return dao.findById(ids);
	}

	public Collection<T> findById(Long[] ids)
	{
		return dao.findById(ids);
	}

	public Collection<T> find(T filter)
	{
		return dao.find(filter);
	}

	public Collection<T> find(T filter, String[] orderBy)
	{
		return dao.find(filter, orderBy);
	}

	public Collection<T> find(T filter, boolean strictSearch, String[] orderBy)
	{
		return dao.find(filter, strictSearch, orderBy);
	}

	public Collection<T> find(String[] key, Object[] value)
	{
		return dao.find(key, value);
	}

	public Collection<T> find(String[] key, Object[] value, String[] orderBy)
	{
		return dao.find(key, value, orderBy);
	}
	
	public T findFirst(String[] key, Object[] value, String[] fetchLazy)
	{
		return findFirst(key, value, null, fetchLazy);
	}

	@SuppressWarnings("unchecked")
	public T findFirst(String[] key, Object[] value, String[] orderBy, String[] fetchLazy)
	{
		Collection<T> result = dao.find(0, 0,key, value, orderBy, fetchLazy);
		
		if (result.isEmpty())
			return null;
		
		return (T) result.toArray()[0];
	}

	public Collection<T> find(int pagina, int qtdMax, String[] orderBy)
	{
		return dao.find(pagina, qtdMax, orderBy);
	}

	public Collection<T> find(int pagina, int qtdMax, String[] key, Object[] value, String[] orderBy)
	{
		return dao.find(pagina, qtdMax, key, value, orderBy, null);
	}

	public Collection<T> findAll()
	{
		return dao.findAll();
	}

	public Collection<T> findAll(String[] orderBy)
	{
		return dao.findAll(orderBy);
	}

	public Collection<T> findToList(String[] properties, String[] sets, String[] key, Object[] value, int page, int pagingSize, String[] orderBy)
	{
		return getDao().findToList(properties, sets, key, value, page, pagingSize, orderBy);
	}

	public Collection<T> findToList(String[] properties, String[] sets, String[] key, Object[] value, String[] orderBy)
	{
		return getDao().findToList(properties, sets, key, value, orderBy);
	}

	public Collection<T> findToList(String[] properties, String[] sets, String[] key, Object[] value)
	{
		return getDao().findToList(properties, sets, key, value);
	}

	public Collection<T> findToList(String[] properties, String[] sets, int page, int pagingSize, String[] orderBy)
	{
		return getDao().findToList(properties, sets, page, pagingSize, orderBy);
	}

	public Collection<T> findToList(String[] properties, String[] sets, String[] orderBy)
	{
		return getDao().findToList(properties, sets, orderBy);
	}

	public void afterPropertiesSet() throws Exception
	{
        if (dao == null)
            throw new IllegalArgumentException("Property \"dao\" is required");
	}

	public Integer getCount()
	{
		return getDao().getCount();
	}

	public Integer getCount(String[] key, Object[] value)
	{
		return getDao().getCount(key, value);
	}

	public T findRandom(String[] properties)
	{
		return (T) getDao().findRandom(properties);
	}

	public T findRandom(String[] properties, String[] key, Object[] value)
	{
		return (T) getDao().findRandom(properties, key, value);
	}

	public boolean verifyExists(String[] properties, Object[] value)
	{
		return getDao().verifyExists(properties, value);
	}

	public boolean verificaEmpresa(Long entidadeId, Long empresaId)
	{
		return getDao().verificaEmpresa(entidadeId, empresaId);
	}
	
	public T findEntidadeComAtributosSimplesById(Long id) {
		return (T) getDao().findEntidadeComAtributosSimplesById(id);
	}
}