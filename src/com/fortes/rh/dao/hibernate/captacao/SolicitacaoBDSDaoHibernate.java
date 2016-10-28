/* Autor: Robertson Freitas
 * Data: 04/07/2006 
 * Requisito: RFA019
 * Requisito: RFA021
 * Requisito: RFA022
*/
package com.fortes.rh.dao.hibernate.captacao;

import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.SolicitacaoBDSDao;
import com.fortes.rh.model.captacao.SolicitacaoBDS;

@Component
public class SolicitacaoBDSDaoHibernate extends GenericDaoHibernate<SolicitacaoBDS> implements SolicitacaoBDSDao
{
    /** add more methods here **/
}