package com.fortes.rh.dao.hibernate.geral;

import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.ProvidenciaDao;
import com.fortes.rh.model.geral.Providencia;

@Component
public class ProvidenciaDaoHibernate extends GenericDaoHibernate<Providencia> implements ProvidenciaDao
{
}
