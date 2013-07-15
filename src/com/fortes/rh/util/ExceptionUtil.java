package com.fortes.rh.util;

import java.sql.BatchUpdateException;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import com.fortes.rh.web.action.MyActionSupport;

public class ExceptionUtil
{
	private static Logger logger = Logger.getLogger(ExceptionUtil.class);
	
	public static void traduzirMensagem(MyActionSupport action, Exception exception)
	{
		try {
			
			if (exception.getCause() instanceof ConstraintViolationException) {
				ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception.getCause();
				action.addActionWarning(verificaEntidades(constraintViolationException.getSQLException().getMessage().trim())); 
			} else if (exception instanceof DataIntegrityViolationException) {
				BatchUpdateException batchUpdateException = (BatchUpdateException) exception.getCause();
				action.addActionWarning(verificaEntidades(batchUpdateException.getNextException().getMessage().trim())); 
			}
			
		} catch (Exception e) {
			logger.error("Houve um erro ao tentar traduzir a mensagem de erro.");
			e.printStackTrace();
			action.addActionError(e.getMessage()); 
		}
	}

	private static String verificaEntidades(String mensagem)
	{
		Object[] erros = getPalavrasEntreAspas(mensagem);
		
		String entity = "atual";//erros[1];
		String dep = "outra entidade";//erros[3];

		if(erros.length > 2){
			entity = erros[0].toString().replace("\"","").toUpperCase();
			dep = erros[2].toString().replace("\"","").toUpperCase();
		}
		
		return "Entidade \"" + entity + "\" possui dependências em \"" + dep + "\".";
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

}