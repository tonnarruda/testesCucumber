package com.fortes.rh.business.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.security.TokenDao;
import com.fortes.rh.model.ws.Token;

@Component
public class TokenManagerImpl extends GenericManagerImpl<Token, TokenDao> implements TokenManager
{
	@Autowired
	public TokenManagerImpl(TokenDao tokenDao) {
		setDao(tokenDao);
	}
}