package com.fortes.rh.config;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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
				// SessionManager é um singleton do spring que controla as sessões ativas
				SessionManager sessionManager = (SessionManager) context.getBean("sessionManager");
				if (sessionManager != null) {
					sessionManager.registerLogout(se.getSession().getId()); // decrementa o contador de sessões ativas
				}
			}
		}
	}

  
//    static List<SecurityContextImpl> sessoes = new ArrayList<SecurityContextImpl>();
//    
//    public void attributeAdded(HttpSessionBindingEvent evt) {
//        if(evt.getName().equalsIgnoreCase("ACEGI_SECURITY_CONTEXT")) {
//        	if ( getSessoes().size() >= 10 )
//        		SecurityContextHolder.getContext().setAuthentication(null);
//        	else 
//	        	sessoes.add((SecurityContextImpl) evt.getValue());
//        }
//    }  
//  
//    public void attributeRemoved(HttpSessionBindingEvent evt) {  
//        if(evt.getName().equalsIgnoreCase("ACEGI_SECURITY_CONTEXT")) {
//        	sessoes.remove((SecurityContextImpl) evt.getValue());
//        }
//    }  
//  
//    public void attributeReplaced(HttpSessionBindingEvent arg0) {  
//        // TODO Auto-generated method stub  
//          
//    }  
//  
//    public List<SecurityContextImpl> getSessoes(){ 
//    	List<SecurityContextImpl> sessoesTemp = new ArrayList<SecurityContextImpl>();
//    	sessoesTemp.addAll(sessoes);
//    	
//    	for (SecurityContextImpl sessao : sessoes) {
//			UserDetailsImpl user = (UserDetailsImpl) sessao.getAuthentication().getPrincipal();
//			if ( (user.getUltimoLogin().getTime() + 3600000) < new Date().getTime() )
//				sessoesTemp.remove(sessao);
//		}
//    	
//    	sessoes = sessoesTemp;
//    	
//        return sessoes;
//    }  
}  