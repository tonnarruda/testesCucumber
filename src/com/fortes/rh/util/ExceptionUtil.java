package com.fortes.rh.util;

import java.lang.reflect.InvocationTargetException;
import java.sql.BatchUpdateException;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import com.fortes.rh.exception.FortesException;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.dicionario.Entidade;
import com.fortes.rh.web.action.MyActionSupport;

public class ExceptionUtil
{
	private static Logger logger = Logger.getLogger(ExceptionUtil.class);
	
	public static void traduzirMensagem(MyActionSupport action, Exception exception, String mensagemDefault)
	{
		try {
			
			if (exception.getCause() instanceof ConstraintViolationException) {
				if(exception.getCause().getCause() instanceof BatchUpdateException){
					addMessageForAction(action, exception.getCause()); 
				} else {
					ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception.getCause();
					action.addActionWarning(verificaEntidades(constraintViolationException.getSQLException().getMessage().trim())); 
				}
			} else if (exception instanceof DataIntegrityViolationException) {
				addMessageForAction(action, exception);
			} else if (exception instanceof FortesException || exception instanceof IntegraACException){
				action.addActionWarning(exception.getMessage()); 
			} else if (exception instanceof InvocationTargetException) {
				if (exception.getCause() instanceof DataIntegrityViolationException){
					addMessageForAction(action, exception.getCause()); 
				} else if (exception.getCause() instanceof FortesException || exception.getCause() instanceof IntegraACException){
					action.addActionWarning(exception.getCause().getMessage()); 
				}
			}
			
			if(mensagemDefault != null && action.getActionWarnings().size() == 0)
				action.addActionError(mensagemDefault); 
			
		} catch (Exception e) {
			logger.error("Houve um erro ao tentar traduzir a mensagem de erro.");
			e.printStackTrace();
			action.addActionError(e.getMessage()); 
		}
	}

	private static void addMessageForAction(MyActionSupport action, Throwable exception) {
		BatchUpdateException batchUpdateException = (BatchUpdateException) exception.getCause();
		action.addActionWarning(verificaEntidades(batchUpdateException.getNextException().getMessage().trim()));
	}

	private static String verificaEntidades(String mensagem)
	{
		Object[] erros = getPalavrasEntreAspas(mensagem);
		
		String entity = "atual";//erros[1];
		String dep = "outra entidade";//erros[3];

		if(erros.length > 2){
			entity = erros[0].toString().replace("\"","").toLowerCase();
			dep = erros[2].toString().replace("\"","").toLowerCase();
		}
		
		return "Entidade \"" + Entidade.getDescricao(entity) + "\" possui dependências em \"" + Entidade.getDescricao(dep) + "\".";
	}
		
	private static Object[] getPalavrasEntreAspas(String frase) 
	{
		Vector<String> palavrasEntreAspas = new Vector<String>();
		char[] caracteres = frase.toCharArray();
		boolean inicioAspas = false;
		String palavraTemp = "";

		for(int i=0; i<caracteres.length; i++){

			if(caracteres[i] == '\"' || inicioAspas){
				
				palavraTemp += caracteres[i];
				if(!inicioAspas){
					inicioAspas = true;
				}
				else if(caracteres[i] == '\"')
				{
					inicioAspas = false;
					palavrasEntreAspas.add(palavraTemp);
					palavraTemp = "";
				}
			}

		}

		return palavrasEntreAspas.toArray();
	}
	
	public static String getMensagem(Exception exception, String mensagemDefault) 
	{
		if(exception.getMessage() != null)
			mensagemDefault = exception.getMessage();
		else if(exception.getCause() != null && exception.getCause().getLocalizedMessage() != null)
			mensagemDefault = exception.getCause().getLocalizedMessage();
		
		return mensagemDefault;
	}

}