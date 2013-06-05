package com.fortes.webwork.interceptor;

import java.sql.BatchUpdateException;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.ExceptionUtils;
import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;
import org.springframework.dao.DataIntegrityViolationException;

import com.fortes.rh.web.action.MyActionSupport;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.Interceptor;

public class ValidationExceptionInterceptor implements Interceptor
{

	private Logger logger = Logger.getLogger(ValidationExceptionInterceptor.class);
	
	public void destroy()
	{
	}

	public void init()
	{
	}
	
	public String intercept(ActionInvocation invocation) throws Exception
	{		
		String result = null;
		
		final Object action = invocation.getAction();
		MyActionSupport actionSuport = null;

		if (action instanceof MyActionSupport)
		{
			actionSuport = (MyActionSupport) action;
		}
		
		try
		{
			result = invocation.invoke();
		}
		catch (DataIntegrityViolationException e)
		{
			BatchUpdateException b = (BatchUpdateException) e.getCause();
			//^ERROR:.*\"(.*)\".*\"(.*)\".*\"(.*)\".*\"(.*)\".*$
			//Pattern p = Pattern.compile("^ERROR:.*\"(.*)\".*\"(.*)\".*\"(.*)\"(.*|\\s)");
			//Matcher m = p.matcher(b.getNextException().getMessage().trim());
			//m.find();
			//String entity = m.group(1);
			//String dep = m.group(3);

			Object[] erros = getPalavrasEntreAspas(b.getNextException().getMessage().trim());
			
			String entity = "atual";//erros[1];
			String dep = "outra entidade";//erros[3];

			if(erros.length > 2){
				entity = erros[0].toString().replace("\"","").toUpperCase();
				dep = erros[2].toString().replace("\"","").toUpperCase();
			}
			
			if (actionSuport != null) {
				String errorMessage = "Entidade \"" + entity + "\" possui dependências em \"" + dep + "\".";
				actionSuport.addActionWarning(errorMessage);
				logger.error(errorMessage);
			}
			this.logaErros(e);
			return Action.ERROR;
		}		
		catch (InvalidStateException e)
		{
			if (actionSuport != null)
			{
				InvalidValue[] invalidValues = e.getInvalidValues();
				for (InvalidValue invalidValue : invalidValues)
				{
					actionSuport.addFieldError(invalidValue.getPropertyName(), invalidValue.getMessage());
				}
			}
			this.logaErros(e);
			return Action.INPUT;
		}
		catch (ConstraintViolationException e)
		{
			if (actionSuport != null) {
				actionSuport.addActionWarning("Violação de integridade no banco de dados.");
				logger.error("Violação de integridade no banco de dados.");
			}
			this.logaErros(e);
			return Action.ERROR;			
		}
		catch (Exception e)
		{
			if (e.getCause() instanceof ConstraintViolationException) {
				
				ConstraintViolationException esception = (ConstraintViolationException) e.getCause();

				Object[] erros = getPalavrasEntreAspas(esception.getSQLException().getMessage().trim());
				
				String entity = "atual";//erros[1];
				String dep = "outra entidade";//erros[3];

				if(erros.length > 2){
					entity = erros[0].toString().replace("\"","").toUpperCase();
					dep = erros[2].toString().replace("\"","").toUpperCase();
				} 
				
				if (actionSuport != null) {
					String errorMessage = "Entidade \"" + entity + "\" possui dependências em \"" + dep + "\".";
					actionSuport.addActionWarning(errorMessage);
					logger.error(errorMessage);
				}
			} else if (actionSuport != null){
				actionSuport.addActionError(e.getMessage());
				e.printStackTrace();
			}
			
			this.logaErros(e);
			return Action.ERROR;			
		}
		
		return result;
	}

	private void logaErros(Exception e)
	{
		logger.error(e.getMessage());
		String stacktrace = ExceptionUtils.getFullStackTrace(e);
		logger.error(stacktrace);
	}

	private Object[] getPalavrasEntreAspas(String frase) {
		
		Vector<String> palavrasEntreAspas = new Vector<String>();
		//pega a sequencia de caracteres da palavra
		char[] caracteres = frase.toCharArray();
		//variavel que indica se leu a primeira aspas da palavra 
		boolean inicioAspas = false;
		//variavel que receberá os caracteres no loop
		String palavraTemp = "";
		
		//loop pelos caracteres
		for(int i=0; i<caracteres.length; i++){
			//se o caractere é uma aspas OU inicioAspas for true
			if(caracteres[i] == '\"' || inicioAspas){
			//concatene a palavaTemp
			palavraTemp += caracteres[i];
				//se inicioAspas for false
				if(!inicioAspas){
					//faça inicioAspas = true
					inicioAspas = true;
				}
				//se inicioAspas for true e esse caractere é a aspa que fecha
				else if(caracteres[i] == '\"')
				{
					//faça inicioAspas = false
					inicioAspas = false;
					//adicione a palavraTemp ao Vector de palavrasEntreAspas
					palavrasEntreAspas.add(palavraTemp);
					//faça palavraTemp = "";
					palavraTemp = "";
				}
			}
				
		}
	
		return palavrasEntreAspas.toArray();
	}
}