package com.fortes.rh.dao.hibernate.geral;

import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.TipoDespesaDao;
import com.fortes.rh.model.geral.TipoDespesa;

@Component
public class TipoDespesaDaoHibernate extends GenericDaoHibernate<TipoDespesa> implements TipoDespesaDao
{
}
