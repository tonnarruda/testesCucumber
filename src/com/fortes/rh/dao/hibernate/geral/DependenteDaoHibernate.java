/* Autor: Bruno Bachiega
 * Data: 16/06/2006 
 * Requisito: RFA006 */
package com.fortes.rh.dao.hibernate.geral;

import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.DependenteDao;
import com.fortes.rh.model.geral.Dependente;

@Component
public class DependenteDaoHibernate extends GenericDaoHibernate<Dependente> implements DependenteDao
{

}