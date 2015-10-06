package com.fortes.rh.util;

import java.lang.reflect.Method;

import javax.mail.MessagingException;

import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;

public class GerenciadorComunicacaoRunnable implements Runnable {

	Method method;
	Object[] params;
	
	public GerenciadorComunicacaoRunnable(){}
	
	public GerenciadorComunicacaoRunnable(Method method, Object[] params){
		this.method = method;
		this.params = params;
	}
	
	public void run() {
		try {
			executar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
    public void executar() throws Exception, MessagingException 
    {
		GerenciadorComunicacaoManager gerenciadorComunicacaoManager = (GerenciadorComunicacaoManager) SpringUtil.getBeanOld("gerenciadorComunicacaoManager");
    	method.invoke(gerenciadorComunicacaoManager, params);
    }
}