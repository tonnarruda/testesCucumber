package com.fortes.rh.exception;

import java.net.ConnectException;

import org.apache.axis.AxisFault;
import org.apache.commons.lang.StringUtils;

public class IntegraACException extends Exception
{
	private static final long serialVersionUID = 1958750031897346627L;
	private Exception cause;
	
	public IntegraACException()
	{
		super();
	}

	public IntegraACException(String msg)
	{
		super(msg);
	}
	
	public IntegraACException(Exception exception, String msg)
	{
		super(msg);
		this.cause = exception;
	}

	public Exception getCause() {
		return cause;
	}
	
	public String getMensagemDetalhada()
	{
		String mensagemDetalhada = getMessage() != null ? getMessage() : "";
		
		if (cause != null){
			
			if (cause instanceof AxisFault)
			{
				AxisFault axisFault = (AxisFault)cause;
				
				/*
				 * Por enquanto, tratando só essa mensagem, mas o AC deve sempre tratar a exceção e enviar 
				 * uma mensagem legível para o usuário para que possamos exibi-la. 
				 */
				if (StringUtils.isNotBlank(axisFault.getMessage()))
				{
					if (axisFault.getMessage().contains("Usuário Não Autenticado"))
						mensagemDetalhada += " - " + axisFault.getMessage();
				}
				 
//				mensagemDetalhada += " - " + axisFault.getMessage();
				
				if ( axisFault.detail instanceof ConnectException)
					mensagemDetalhada += " - Não foi possível conectar ao Fortes Pessoal.";
				
			}			
		}
		
		return mensagemDetalhada;
	}
}
