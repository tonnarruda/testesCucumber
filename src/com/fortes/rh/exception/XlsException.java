package com.fortes.rh.exception;

@SuppressWarnings("serial")
public class XlsException extends Exception
{
	private static Integer NumLinhasMaxXLS = 65535;
	
	public XlsException()
	{
		super("Não é possível gerar relatório XLS. Número de linhas é excedente ao que o documento XLS suporta.<br>Favor, altere os filtros para diminuir o número de linhas.");
	}


	public static void checaNumLinhasMaxXLS(Integer numLinhas) throws XlsException 
	{
		if(numLinhas > NumLinhasMaxXLS)
			throw new XlsException();
	}
}
