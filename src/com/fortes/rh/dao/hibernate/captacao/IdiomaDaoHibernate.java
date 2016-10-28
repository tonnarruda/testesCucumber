package com.fortes.rh.dao.hibernate.captacao;

import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.IdiomaDao;
import com.fortes.rh.model.captacao.Idioma;

/**
 * @author Moesio Medeiros
 * data: 16/06/2006
 * Requisito: RFA029 - Cadastro de curriculum
 */
@Component
public class IdiomaDaoHibernate extends GenericDaoHibernate<Idioma> implements IdiomaDao
{
    /** add more methods here **/
}