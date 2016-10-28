package com.fortes.dao;

import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
//import org.springframework.orm.hibernate4.HibernateTemplate;

import com.fortes.model.type.File;

/**
 * Defines the basic methods of a data access objet (DAO) that should be
 * implemented in order to create a basic Entity DAO.
 *
 * @author MARLUS SARAIVA
 *
 * @param <T>
 *            The Entity class.
 */
public interface GenericDao<T>
{
	/**
	 * <p>
	 * Creates a new &lt;T&gt; instance from <strong>all</strong> attributes
	 * and adds it to the persistent store.
	 * </p>
	 */
	public T save(T entity);

	/**
	 * <p>
	 * Cria projections para atributos simples da classe
	 * Não cria para tipos complexos
	 * </p>
	 */
	public ProjectionList geraProjectionDosAtributosSimples(String alias);
	/**
	 * <p>
	 * Updates the entity in the persistent store.
	 * </p>
	 */
	public void update(T entity);

	/**
	 * <p>
	 * Updates the entity in the persistent store.
	 * </p>
	 */
	public void saveOrUpdate(T entity);
	
	/**
	 * <p>
	 * Updates the entities in the persistent store.
	 * </p>
	 */
	public void saveOrUpdate(Collection<T> entities);

	/**
	 * <p>
	 * Retorna a lista de tabelas que dependem do registro.
	 * </p>
	 */
	public String[] findDependentTables(Long id);
	
	/**
	 * <p>
	 * Removes from the persistent store all entities based on the ids.
	 * </p>
	 */
	public void remove(Long[] ids);

	/**
	 * <p>
	 * Removes the entity from the persistent store based on the id.
	 * </p>
	 */
	public void remove(Long id);

	/**
	 * <p>
	 * Removes the entity from the persistent store.
	 * </p>
	 */
	public void remove(T entity);

	public T findRandom(String[] properties);

	public T findRandom(String[] properties, String[] key, Object[] value);

	/**
	 * <p>
	 * Loads an instance of &lt;T&gt; from the persistent store.
	 * </p>
	 */
	public T findById(Long id);

	// TODO: Comentar
	public T findById(Long id, String[] fetch);

	public Collection<T> findById(String[] id);

	public Collection<T> findById(Long[] ids);

	/**
	 * <p>
	 * Loads any instances of &lt;T&gt; from the persistent store based on the
	 * filter param.
	 * </p>
	 *
	 * @param filter
	 *            An &lt;T&gt; instance.
	 * @return A list of the intances found.
	 */
	public Collection<T> find(T filter);

	/**
	 * <p>
	 * Loads any instances of T from the persistent store based on the filter
	 * param. The order of the list can be set based on the properties names
	 * defined in the orderBy param.
	 * </p>
	 *
	 * @param filter
	 *            a filter instance.
	 * @param orderBy
	 *            a String array containing de names of de properties to order
	 *            by.
	 * @return the list of the intances found.
	 */
	public Collection<T> find(T filter, String[] orderBy);

	/**
	 * <p>
	 * Loads any instances of T from the persistent store based on the filter
	 * param. The order of the list can be set based on the properties names
	 * defined in the orderBy param.
	 * </p>
	 *
	 * @param filter
	 *            a filter instance.
	 * @param strictSearch
	 *            defines if the internal query should use the 'like' operator.
	 * @param orderBy
	 *            a String array containing de names of de properties to order
	 *            by.
	 * @return the list of the intances found.
	 */
	public Collection<T> find(T filter, boolean strictSearch, String[] orderBy);

	//TODO: Comentar
	public Collection<T> find(String[] key, Object[] value);

	//TODO: Comentar
	public Collection<T> find(String[] key, Object[] value, String[] orderBy);

	//TODO: Comentar
	public Collection<T> find(int pagina, int qtdMax, String[] orderBy);

	//TODO: Comentar
	public Collection<T> find(int pagina, int qtdMax, String[] key, Object[] value, String[] orderBy, String[] fetchLazy);

	/**
	 * <p>
	 * Loads all instances of T from the persistent store.
	 * </p>
	 *
	 * @return the list of the intances found.
	 */
	public Collection<T> findAll();

	/**
	 * <p>
	 * Loads all instances of T from the persistent store. The order of the list
	 * can be set based on the properties names defined in the orderBy param.
	 * </p>
	 *
	 * @param orderBy
	 *            a String array containing de names of de properties to order
	 *            by.
	 * @return the list of the intances found.
	 */
	public Collection<T> findAll(String[] orderBy);

	public Collection<T> findToList(String[] properties, String[] sets, String[] key, Object[] value, int page, int pagingSize, String[] orderBy);
	public Collection<T> findToList(String[] properties, String[] sets, String[] key, Object[] value, String[] orderBy);
	public Collection<T> findToList(String[] properties, String[] sets, String[] key, Object[] value);
	public Collection<T> findToList(String[] properties, String[] sets, int page, int pagingSize, String[] orderBy);
	public Collection<T> findToList(String[] properties, String[] sets, String[] orderBy);

	//TODO: Comentar
	public File getFile(String property, Long id) throws Exception;

	public Integer getCount();

	public Integer getCount(String[] key, Object[] value);

	/**
	 * @return HibernateTemplate para dar maior poder aos DAOs, permitindo controlar o flush por exemplo.
	 */
	public Session getHibernateTemplateByGenericDao();

	/**
	 * Verifica se existe registro com esses dados no banco.
	 */
	public boolean verifyExists(String[] properties, Object[] value);

	public boolean verificaEmpresa(Long entidadeId, Long empresaId);
	
	public T findEntidadeComAtributosSimplesById(Long id);
	
}