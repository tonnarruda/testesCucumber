package com.fortes.rh.dao.hibernate.security;

import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.security.TokenDao;
import com.fortes.rh.model.ws.Token;

@Component
public class TokenDaoHibernate extends GenericDaoHibernate<Token> implements TokenDao
{
	
}