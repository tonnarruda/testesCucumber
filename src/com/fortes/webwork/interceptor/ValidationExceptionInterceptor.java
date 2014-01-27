package com.fortes.webwork.interceptor;

import java.sql.BatchUpdateException;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.ExceptionUtils;
import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;
import org.springframework.dao.DataIntegrityViolationException;

import com.fortes.rh.model.dicionario.Entidade;
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
			String warnMessage = montaMensagemDependencias(b.getNextException().getMessage().trim());
			
			if (actionSuport != null) 
			{
				actionSuport.addActionWarning(warnMessage);
				logger.error(warnMessage);
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
			String warnMessage = montaMensagemDependencias(e.getMessage());
			if (actionSuport != null) 
			{
				actionSuport.addActionWarning(warnMessage);
				logger.error(warnMessage);
			}

			this.logaErros(e);
			
			return Action.ERROR;			
		}
		catch (Exception e)
		{
			if (e.getCause() instanceof ConstraintViolationException) {
				
				ConstraintViolationException cvException = (ConstraintViolationException) e.getCause();
				String warnMessage = montaMensagemDependencias(cvException.getSQLException().getMessage().trim());
				
				if (actionSuport != null) 
				{
					actionSuport.addActionWarning(warnMessage);
					logger.error(warnMessage);
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
	
	private String montaMensagemDependencias(String errorMessage)
	{
		Object[] erros = getPalavrasEntreAspas(errorMessage);
		
		if (erros.length > 2)
		{
			String entity = erros[0].toString().replace("\"","");
			String dep = erros[2].toString().replace("\"","");
			
			return "Entidade <strong>" + Entidade.getDescricao(entity) + "</strong> possui dependências em <strong>" + Entidade.getDescricao(dep) + "</strong>.";
		}
		else
			return "Violação de integridade no banco de dados";
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