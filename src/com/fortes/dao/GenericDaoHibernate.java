package com.fortes.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;

import com.fortes.model.type.File;

/**
 * Spring/Hibernate implementation for the {@link com.fortes.dao.GenericDao}
 * interface.
 *
 * @param <T>
 *            The Entity class.
 * @param <I>
 *            The Entity Id class.
 */
@SuppressWarnings("unchecked")
public class GenericDaoHibernate<T> implements GenericDao<T>
{
	private Class<T> entityClass;
	
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public GenericDaoHibernate()
	{
		this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public Class<T> getEntityClass()
	{
		return entityClass;
	}

	public ProjectionList geraProjectionDosAtributosSimples(String alias)
	{
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property(alias + ".id"), "id");
		
		StringBuilder tipo = null;
		for (Field f : getEntityClass().getDeclaredFields())
		{
			tipo = new StringBuilder(f.getGenericType().toString());
			if( 
				!f.getName().toString().equals("serialVersionUID") && (
				tipo.toString().equals("class java.util.Date") ||
				tipo.toString().equals("class java.lang.Boolean") ||
				tipo.toString().equals("class java.lang.Character") ||
				tipo.toString().equals("class java.lang.Integer") ||
				tipo.toString().equals("class java.lang.Long") ||
				tipo.toString().equals("class java.lang.Float") ||
				tipo.toString().equals("class java.lang.Double") ||
				tipo.toString().equals("class java.lang.Short") ||
				tipo.toString().equals("class java.lang.String") ||
				tipo.toString().equals("boolean") ||
				tipo.toString().equals("char") ||
				tipo.toString().equals("int") ||
				tipo.toString().equals("long") ||
				tipo.toString().equals("float") ||
				tipo.toString().equals("double") ||
				tipo.toString().equals("short"))
				)
				p.add(Projections.property(alias + "." + f.getName()), f.getName());				
		}

		tipo = null;
		return p;
	}
	
	public T save(T entity)
	{
		Serializable id = getSession().save(entity);
//		getSessionFactory().getClassMetadata(getEntityClass()).setIdentifier(entity, id, EntityMode.getS);
		return entity;
	}

	public void update(T entity)
	{
		//Utilizado merge e não update para o objeto informado não ser colocado no first level cache
		//evitando a exception:
		//NonUniqueObjectException - a different object with the same identifier value was already associated with the session
		getSession().merge(entity);
	}
	
	public void saveOrUpdate(T entity)
	{
		getSession().saveOrUpdate(entity);
	}

	public void saveOrUpdate(Collection<T> entities)
	{
		getSession().saveOrUpdate(entities);
	}

	public String[] findDependentTables(Long id)
	{
		ClassMetadata cm = getSessionFactory().getClassMetadata(getEntityClass());
		AbstractEntityPersister aep = (AbstractEntityPersister) cm;
		String table = aep.getTableName();
		
		Query query = getSession().createSQLQuery("SELECT * FROM dependent_tables(:table, :id)");
		query.setString("table", table.toLowerCase());
		query.setLong("id", id);
		
		Object[] result = query.list().toArray();
		String[] retorno = Arrays.copyOf(result, result.length, String[].class);
		
		return retorno;
	}
	
	public void remove(Long[] ids) throws DataAccessException
	{
		for (Long id : ids)
			remove(id);
	}

	public void remove(Long id) throws DataAccessException
	{
		try
		{
			T entidade = (T) (Class.forName(getEntityClass().getName()).newInstance());
//			getSessionFactory().getClassMetadata(getEntityClass()).setIdentifier(entidade, id, EntityMode.POJO);
			remove(entidade);
		}
		catch (DataAccessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new DataAccessResourceFailureException("Erro ao criar entidade para remover registro do banco de dados.");
		}
	}

	public void remove(T entity) throws DataAccessException
	{
		//Sincroniza o estado dos objetos na sessão
		getSession().flush();
		//Remove completamente todos os objetos do cache da sessão
		getSession().clear();
		getSession().delete(entity);
	}
	
	public T findRandom(String[] properties)
	{
		int indice = (int) Math.round((Math.random() * (getCount())));//getCount() retorna quantidade de registros no banco
		if(indice > 0)
			indice--;

		Criteria criteria = getSession().createCriteria(getEntityClass());

		ProjectionList p = Projections.projectionList().create();
		for (int i = 0; i < properties.length; i++)
			p.add(Projections.property(properties[i]),properties[i]);

		criteria.setProjection(p);

		criteria.setFirstResult(indice);
		criteria.setMaxResults(1);//so queremos um elemento
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		List l = criteria.list();
		if (l.isEmpty())
			return null;
		else
			return (T) l.get(0);
	}

	public T findRandom(String[] properties, String[] key, Object[] value)
	{
		int indice = (int) Math.round((Math.random() * (getCount(key, value))));//getCount() retorna quantidade de registros no banco
		if(indice > 0)
			indice--;

		Criteria criteria = getSession().createCriteria(getEntityClass());

		ProjectionList p = Projections.projectionList().create();
		for (int i = 0; i < properties.length; i++)
			p.add(Projections.property(properties[i]),properties[i]);

		criteria.setProjection(p);

		for(int i = 0; i < key.length; i++)
			criteria.add(Expression.eq(key[i], value[i]));

		criteria.setFirstResult(indice);
		criteria.setMaxResults(1);//so queremos um elemento

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		List l = criteria.list();
		if (l.isEmpty())
			return null;
		else
			return (T) l.get(0);
	}

	public T findById(Long id)
	{
		return (T) getSession().get(getEntityClass(), id);
	}

	@SuppressWarnings("deprecation")
	public T findById(Long id, String[] fetch)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass());
		criteria.add(Expression.eq("id", id));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		if (fetch != null)
		{
			for (int i = 0; i < fetch.length; i++)
				criteria.setFetchMode(fetch[i], FetchMode.EAGER);
		}

		List l = criteria.list();
		if (l.isEmpty())
			return null;
		else
			return (T) l.get(0);
	}

	//TODO: Remover e atualizar quem estiver usando
	public Collection<T> findById(String[] ids)
	{
		int i = 0;
		Long[] lista = new Long[ids.length];
		for (String s : ids)
			lista[i++] = Long.valueOf(s);

		return findById(lista);
	}

	public Collection<T> findById(Long[] ids)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass());
		criteria.add(Expression.in("id", ids));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return criteria.list();
	}

	public Collection<T> find(T filter)
	{
		return find(filter, new String[]{});
	}

	public Collection<T> find(T filter, String[] orderBy)
	{
		return find(filter, false, orderBy);
	}

	public Collection<T> find(T filter, boolean strictSearch, String[] orderBy)
	{
        Example example = Example.create(filter).excludeZeroes().ignoreCase();

        if (!strictSearch)
            example.enableLike(MatchMode.ANYWHERE);

        Criteria criteria = getSession().createCriteria(getEntityClass()).add(example);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        if (orderBy != null)
        {
            for (int i = 0; i < orderBy.length; i++)
            {
                String[] tmp = orderBy[i].split("\\s");
                if (tmp.length > 1 && tmp[1].equalsIgnoreCase("desc"))
                    criteria.addOrder(Order.desc(tmp[0]));
                else
                    criteria.addOrder(Order.asc(tmp[0]));
            }
        }
        return criteria.list();
	}

	public Collection<T> find(String[] key, Object[] value)
	{
		return find(key, value, null);
	}

	public Collection<T> find(String[] key, Object[] value, String[] orderBy)
	{
		return find(0, 0, key, value, orderBy, null);
	}

	public Collection<T> find(int pagina, int qtdMax, String[] orderBy)
	{
		return find(pagina, qtdMax, null, null, orderBy, null);
	}

	public Collection<T> find(int pagina, int qtdMax, String[] key, Object[] value, String[] orderBy, String[] fetchLazy)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass());
		if (key != null)
		{
			for(int i = 0; i < key.length; i++)
				criteria.add(Expression.eq(key[i], value[i]));
		}
		
		if (fetchLazy != null)
		{
			for (int i = 0; i < fetchLazy.length; i++)
				criteria.setFetchMode(fetchLazy[i], FetchMode.SELECT);
		}

        if (orderBy != null)
        {
            for (int i = 0; i < orderBy.length; i++)
            {
                String[] tmp = orderBy[i].split("\\s");
                if (tmp.length > 1 && tmp[1].equalsIgnoreCase("desc"))
                    criteria.addOrder(Order.desc(tmp[0]));
                else
                    criteria.addOrder(Order.asc(tmp[0]));
            }
        }

        if (qtdMax > 0)
        {
        	criteria.setFirstResult(((pagina - 1) * qtdMax));
        	criteria.setMaxResults(qtdMax);
        }

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Collection<T> findAll()
	{
		Criteria criteria = getSession().createCriteria(getEntityClass());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Collection<T> findAll(String[] orderBy)
	{
        Criteria criteria = getSession().createCriteria(getEntityClass());
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        if (orderBy != null)
        {
            for (int i = 0; i < orderBy.length; i++)
            {
                String[] tmp = orderBy[i].split("\\s");
                if (tmp.length > 1 && tmp[1].equalsIgnoreCase("desc"))
                    criteria.addOrder(Order.desc(tmp[0]));
                else
                    criteria.addOrder(Order.asc(tmp[0]));
            }
        }

        return criteria.list();
	}

	public Collection<T> findToList(String[] properties, String[] sets, String[] key, Object[] value, int page, int pagingSize, String[] orderBy)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass());

		ProjectionList p = Projections.projectionList().create();
		for (int i = 0; i < properties.length; i++)
			p.add(Projections.property(properties[i]),sets[i]);

		criteria.setProjection(p);

		if (key != null)
			for(int i = 0; i < key.length; i++){

				if(key[i].startsWith(">="))
					criteria.add(Expression.ge(key[i].replaceFirst(">=", ""), value[i]));
				else if(key[i].startsWith(">"))
					criteria.add(Expression.gt(key[i].replaceFirst(">", ""), value[i]));
				else if(key[i].startsWith("<="))
					criteria.add(Expression.le(key[i].replaceFirst("<=", ""), value[i]));
				else if(key[i].startsWith("<"))
					criteria.add(Expression.lt(key[i].replaceFirst("<", ""), value[i]));
				else if(key[i].startsWith("!="))
					criteria.add(Expression.ne(key[i].replaceFirst("!=", ""), value[i]));
				else
					criteria.add(Expression.eq(key[i], value[i]));
			}

        if (orderBy != null)
        {
            for (int i = 0; i < orderBy.length; i++)
            {
                String[] tmp = orderBy[i].split("\\s");
                if (tmp.length > 1 && tmp[1].equalsIgnoreCase("desc"))
                    criteria.addOrder(Order.desc(tmp[0]));
                else
                    criteria.addOrder(Order.asc(tmp[0]));
            }
        }

		if (pagingSize > 0)
		{
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
		}

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Collection<T> findToList(String[] properties, String[] sets, String[] key, Object[] value, String[] orderBy)
	{
		return findToList(properties, sets, key, value, 0, 0, orderBy);
	}

	public Collection<T> findToList(String[] properties, String[] sets, String[] key, Object[] value)
	{
		return findToList(properties, sets, key, value, 0, 0, null);
	}

	public Collection<T> findToList(String[] properties, String[] sets, int page, int pagingSize, String[] orderBy)
	{
		return findToList(properties, sets, page, pagingSize, orderBy);
	}

	public Collection<T> findToList(String[] properties, String[] sets, String[] orderBy)
	{
		return findToList(properties, sets, null, null, 0, 0, orderBy);
	}

	public File getFile(String property, Long id)
	{
			return getFileFromDatabase(property, id);
	}

	private File getFileFromDatabase(String property, Long id)
	{
		ClassMetadata meta = getSessionFactory().getClassMetadata(getEntityClass());
		String idPropName = meta.getIdentifierPropertyName();
		String className = getEntityClass().getName();
		String instanceName = getEntityClass().getSimpleName().toLowerCase();

		String query = "select " + instanceName + "." + property +
					   " from " + className + " " + instanceName +
					   " where " + instanceName + "." + idPropName + "=:id";
		File file = (File) getSession().createQuery(query)
						.setParameter("id", id)
						.uniqueResult();

		return file;
	}

	public Integer getCount()
	{
		return getCount(null, null);
	}

	public Integer getCount(String[] key, Object[] value)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass());
		criteria.setProjection(Projections.rowCount());

		if(key != null)
		{
			for(int i = 0; i < key.length; i++)
				criteria.add(Expression.eq(key[i], value[i]));
		}

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List result = criteria.list();
		return (Integer) result.get(0);
	}

	public Session getHibernateTemplateByGenericDao()
	{
		return getSession();
	}

	public boolean verifyExists(String[] key, Object[] value)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"entity");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("entity.id"),"id");
		criteria.setProjection(p);

		if (key != null)
			for(int i = 0; i < key.length; i++)
				criteria.add(Expression.eq("entity." + key[i], value[i]));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		List result = criteria.list();

		return result.size() > 0;
	}

	public boolean verificaEmpresa(Long entidadeId, Long empresaId)
	{
		String[] key = new String[]{"id","empresa.id"};
		Object[] values = new Object[]{entidadeId, empresaId};

		return verifyExists(key, values);
	}
	
	public ProjectionList geraProjectionDosAtributosSimplesNaoTransientes(String alias) {
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property(alias + ".id"), "id");
		
		for (Field f : getEntityClass().getDeclaredFields()) {
			
			boolean naoEhTransiente = (f.getAnnotation(Transient.class) == null);
			boolean ehAtributoSimples = this.ehAtributoSimples(f);
			
			if(ehAtributoSimples 
					&& naoEhTransiente) {
				p.add(Projections.property(alias + "." + f.getName()), f.getName());
			}
		}
		return p;
	}
	/**
	 * Verifica se é atributo simples.
	 */
	private boolean ehAtributoSimples(Field atributo) {
		String tipo = atributo.getGenericType().toString();
		boolean naoEhSerialVersionUID = !atributo.getName().equals("serialVersionUID");
		boolean ehAtributoPrimitivoOuWrapper = (
				tipo.equals("class java.util.Date") ||
				tipo.equals("class java.lang.Boolean") ||
				tipo.equals("class java.lang.Character") ||
				tipo.equals("class java.lang.Integer") ||
				tipo.equals("class java.lang.Long") ||
				tipo.equals("class java.lang.Float") ||
				tipo.equals("class java.lang.Double") ||
				tipo.equals("class java.lang.Short") ||
				tipo.equals("class java.lang.String") ||
				tipo.equals("boolean") ||
				tipo.equals("char") ||
				tipo.equals("int") ||
				tipo.equals("long") ||
				tipo.equals("float") ||
				tipo.equals("double") ||
				tipo.equals("short"));
		return (ehAtributoPrimitivoOuWrapper 
						&& naoEhSerialVersionUID);
	}
	
	public T findEntidadeComAtributosSimplesById(Long id) {
		
		Criteria criteria = getSession().createCriteria(getEntityClass(), "e");
		criteria.add(Expression.idEq(id));
		
		ProjectionList proj = this.geraProjectionDosAtributosSimplesNaoTransientes("e");
		criteria.setProjection(proj);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return (T) criteria.uniqueResult();
	}
	
}