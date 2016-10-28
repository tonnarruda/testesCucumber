package com.fortes.rh.business.geral;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.ClienteDao;
import com.fortes.rh.model.geral.Cliente;

@Component
public class ClienteManagerImpl extends GenericManagerImpl<Cliente, ClienteDao> implements ClienteManager
{
	@Autowired
	ClienteManagerImpl(ClienteDao dao) {
		setDao(dao);
	}
}
