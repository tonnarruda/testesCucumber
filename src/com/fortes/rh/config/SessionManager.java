package com.fortes.rh.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fortes.rh.security.UserDetailsImpl;

public class SessionManager {
	
	private Map<String, UserDetailsImpl> usersMap = new HashMap<String, UserDetailsImpl>();

	public synchronized void registerLogout(String sessionId)
	{
		usersMap.remove(sessionId);
	}

	public synchronized Integer getTotalDeSessoes()
	{
		return usersMap.size();
	}

	public synchronized Integer getNumeroDeUsuariosAutenticadosComAcessoNormal()
	{
		int number = 0;
		Set<Map.Entry<String, UserDetailsImpl>> entries = usersMap.entrySet();
		for (Map.Entry<String, UserDetailsImpl> entry : entries) {
			UserDetailsImpl userDetailsImpl = entry.getValue();
			
			if (null != userDetailsImpl && !userDetailsImpl.getHasAcessoRestrito()) {
				number++;
			}
		}
		return number;
	}

	public synchronized void registerLogin(String sessionId, UserDetailsImpl user)
	{
		usersMap.put(sessionId, user);
	}

	public synchronized boolean isUserLogged(String sessionId)
	{
		return usersMap.get(sessionId) != null;
	}
}