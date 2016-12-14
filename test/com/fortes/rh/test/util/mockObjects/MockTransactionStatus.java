package com.fortes.rh.test.util.mockObjects;

import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;


/**
 * Classe criada para auxiliar a Mockagem estática de métodos de TransactionStatus
 */
public class MockTransactionStatus implements TransactionStatus
{

	public boolean hasSavepoint()
	{
		return false;
	}

	public boolean isCompleted()
	{
		return false;
	}

	public boolean isNewTransaction()
	{
		return false;
	}

	public boolean isRollbackOnly()
	{
		return false;
	}

	public void setRollbackOnly()
	{

	}

	public Object createSavepoint() throws TransactionException
	{
		return null;
	}

	public void releaseSavepoint(Object arg0) throws TransactionException
	{

	}

	public void rollbackToSavepoint(Object arg0) throws TransactionException
	{

	}

	@Override
	public void flush() {
	}
}
