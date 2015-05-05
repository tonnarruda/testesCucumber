package com.fortes.rh.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.context.SecurityContextImpl;

import com.fortes.rh.security.UserDetailsImpl;

public class UsuarioListener implements HttpSessionAttributeListener {  
  
    static List<SecurityContextImpl> sessoes = new ArrayList<SecurityContextImpl>();
    
    public void attributeAdded(HttpSessionBindingEvent evt) {
        if(evt.getName().equalsIgnoreCase("ACEGI_SECURITY_CONTEXT")) {
        	if ( getSessoes().size() >= 10 )
        		SecurityContextHolder.getContext().setAuthentication(null);
        	else 
	        	sessoes.add((SecurityContextImpl) evt.getValue());
        }
    }  
  
    public void attributeRemoved(HttpSessionBindingEvent evt) {  
        if(evt.getName().equalsIgnoreCase("ACEGI_SECURITY_CONTEXT")) {
        	sessoes.remove((SecurityContextImpl) evt.getValue());
        }
    }  
  
    public void attributeReplaced(HttpSessionBindingEvent arg0) {  
        // TODO Auto-generated method stub  
          
    }  
  
    public List<SecurityContextImpl> getSessoes(){ 
    	List<SecurityContextImpl> sessoesTemp = new ArrayList<SecurityContextImpl>();
    	sessoesTemp.addAll(sessoes);
    	
    	for (SecurityContextImpl sessao : sessoes) {
			UserDetailsImpl user = (UserDetailsImpl) sessao.getAuthentication().getPrincipal();
			if ( (user.getUltimoLogin().getTime() + 3600000) < new Date().getTime() )
				sessoesTemp.remove(sessao);
		}
    	
    	sessoes = sessoesTemp;
    	
        return sessoes;
    }  
}  