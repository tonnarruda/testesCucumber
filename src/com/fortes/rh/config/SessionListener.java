package com.fortes.rh.config;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fortes.rh.security.SecurityUtil;

public class SessionListener implements HttpSessionListener {  
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		ServletContext sc = se.getSession().getServletContext();
		if (sc != null) {
			WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
			if (context != null) { 		// SessionManager é um singleton do spring que controla as sessões ativas
				SessionManager sessionManager = (SessionManager) context.getBean("sessionManager");
				if (sessionManager != null) {
					sessionManager.registerLogin(se.getSession().getId(),null); 
				}
			}
		}
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		ServletContext sc = se.getSession().getServletContext();
		if (sc != null) {
			WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
			if (context != null) {
				SecurityUtil.registraLogout(context, se.getSession().getId());
			}
		}
	}
}  