package com.fortes.rh.test.business.geral;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.FileBoxManager;
import com.fortes.rh.business.geral.MorroManagerImpl;

public class MorroManagerTest extends MockObjectTestCase
{
	MorroManagerImpl morroManager = null;
	Mock fileBoxManager = null;

	protected void setUp() throws Exception
	{
		super.setUp();
		morroManager = new MorroManagerImpl();

		fileBoxManager = new Mock(FileBoxManager.class);
		morroManager.setFileBoxManager((FileBoxManager) fileBoxManager.proxy());
	}

	public void testEnviar() throws Exception
	{
		fileBoxManager.expects(once()).method("enviar").withAnyArguments().isVoid();

		morroManager.enviar("mensagem", "classeExcecao", "stackTrace", "url", "browser", "versao", "clienteCnpj", "clienteNome", "usuario");
	}
}
