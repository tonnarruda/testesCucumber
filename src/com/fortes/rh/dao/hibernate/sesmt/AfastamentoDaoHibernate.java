package com.fortes.rh.dao.hibernate.sesmt;

import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.AfastamentoDao;
import com.fortes.rh.model.sesmt.Afastamento;

@Component
public class AfastamentoDaoHibernate extends GenericDaoHibernate<Afastamento> implements AfastamentoDao
{
}