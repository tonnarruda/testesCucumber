package com.fortes.business;

import java.util.Collection;

import com.fortes.rh.security.spring.aop.callback.crud.InsertAuditorCallbackImpl;
import com.fortes.rh.security.spring.aop.callback.crud.RemoveAuditorCallbackImpl;
import com.fortes.rh.security.spring.aop.callback.crud.UpdateAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;

/**
 * Defines the basic methods of a Generic Manager.
 *
 * @author MARLUS SARAIVA
 *
 * @param <T>
 *            The Entity class.
 */
public interface GenericManager<T>
{
	/**
	 * <p>
	 * Create a new &lt;T&gt; entity instance.
	 * </p>
	 */
	@Audita(operacao="Inserção", auditor=InsertAuditorCallbackImpl.class)
	public T save(T entity);

	/**
	 * <p>
	 * Updates the entity instance.
	 * </p>
	 */
	@Audita(operacao="Atualização", auditor=UpdateAuditorCallbackImpl.class)
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
	 * Removes all entities based on the ids.
	 * </p>
	 */
	public void remove(Long[] ids);
	
	@Audita(operacao="Remoção", auditor=RemoveAuditorCallbackImpl.class)
	public void remove(Long id);
	
	public void remove(T entity);

	public T findRandom(String[] properties);

	public T findRandom(String[] properties, String[] key, Object[] value);

	/**
	 * <p>
	 * Loads an instance of &lt;T&gt; from the persistent store.
	 * </p>
	 */
	public T findById(Long id);

	public T findById(Long id, String[] fetch);

	public Collection<T> findById(String[] ids);

	public Collection<T> findById(Long[] ids);

	/**
	 * <p>
	 * Finds any instances of &lt;T&gt; based on the
	 * filter param.
	 * </p>
	 *
	 * @param filter
	 *            An &lt;T&gt; instance.
	 * @return A list of the intances found.
	 *
	 */
	public Collection<T> find(T filter);

	/**
	 * <p>
	 * Finds any instances of T based on the filter
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
	 * Finds any instances of T based on the filter
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
	public Collection<T> find(int pagina, int qtdMax, String[] key, Object[] value, String[] orderBy);
	
	//TODO: Comentar
	public T findFirst(String[] key, Object[] value, String[] fetchLazy);

	//TODO: Comentar
	public T findFirst(String[] key, Object[] value, String[] orderBy, String[] fetchLazy);

	/**
	 * <p>
	 * Finds all instances of T.
	 * </p>
	 *
	 * @return the list of the intances found.
	 */
	public Collection<T> findAll();

	public Collection<T> findToList(String[] properties, String[] sets, String[] key, Object[] value, int page, int pagingSize, String[] orderBy);
	public Collection<T> findToList(String[] properties, String[] sets, String[] key, Object[] value, String[] orderBy);
	public Collection<T> findToList(String[] properties, String[] sets, String[] key, Object[] value);
	public Collection<T> findToList(String[] properties, String[] sets, int page, int pagingSize, String[] orderBy);
	public Collection<T> findToList(String[] properties, String[] sets, String[] orderBy);

	/**
	 * <p>
	 * Finds all instances of T. The order of the list
	 * can be set based on the properties names defined in the orderBy param.
	 * </p>
	 *
	 * @param orderBy
	 *            a String array containing de names of de properties to order
	 *            by.
	 * @return the list of the intances found.
	 */
	public Collection<T> findAll(String[] orderBy);

	public Integer getCount();

	public Integer getCount(String[] key, Object[] value);

	/**
	 * Verifica se existe registro com esses dados no banco.
	 */
	public boolean verifyExists(String[] properties, Object[] value);

	public boolean verificaEmpresa(Long entidadeId, Long empresaId);
	
	public T findEntidadeComAtributosSimplesById(Long id);
	
}
