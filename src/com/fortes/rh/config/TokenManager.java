package com.fortes.rh.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;

public class TokenManager {
	
	private Map<String, String> usersTokenMap = new HashMap<String, String>();

	public synchronized void remove(String sessionId)
	{
		usersTokenMap.remove(sessionId);
	}

	public synchronized void registra(String sessionId)
	{
		usersTokenMap.put(sessionId, RandomStringUtils.random(128, true, true));
	}

	public synchronized boolean isRegistrado(String sessionId)
	{
		return usersTokenMap.get(sessionId) != null;
	}
	
	public boolean isValido(String sessionId, String token)
	{
		return isRegistrado(sessionId) && token.equals(usersTokenMap.get(sessionId));
	}

	public String getToken(String sessionId)
	{
		return usersTokenMap.get(sessionId);
	}
}