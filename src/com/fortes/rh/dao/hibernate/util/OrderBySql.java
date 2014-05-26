package com.fortes.rh.dao.hibernate.util;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Order;

@SuppressWarnings("serial")
public class OrderBySql extends Order {

	/**
	 * Extende {@link org.hibernate.criterion.Order} para permitir ordenação por
	 * uma SQL fórmula passada pelo usuário. Adiciona o <code>sqlFormula</code>
	 * passado pelo usuário para o resultado do SQL query, sem verificação.
	 * 
	 * @author Sorin Postelnicu
	 * @since Jun 10, 2008
	 */
	private String sql;

	/**
	 * Construtor de Order.
	 * 
	 * @param sql
	 *            uma SQL string que será adicionada ao SQL final.
	 */
	protected OrderBySql(String sql) {
		super(sql, true);
		this.sql = sql;
	}

	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException
	{
		return sql;
	}

	/**
	 * Customização da cláusula order
	 * 
	 * @param sql
	 *            uma SQL string que será adicionada ao SQL final.
	 * @return Order
	 */
	public static Order sql(String sql)
	{
		return new OrderBySql(sql);
	}
}
