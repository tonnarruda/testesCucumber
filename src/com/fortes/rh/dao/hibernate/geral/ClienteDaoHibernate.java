package com.fortes.rh.dao.hibernate.geral;

import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.ClienteDao;
import com.fortes.rh.model.geral.Cliente;

@Component
public class ClienteDaoHibernate extends GenericDaoHibernate<Cliente> implements ClienteDao
{
}
