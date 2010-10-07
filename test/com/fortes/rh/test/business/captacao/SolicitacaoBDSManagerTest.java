package com.fortes.rh.test.business.captacao;

import org.jmock.MockObjectTestCase;

import com.fortes.model.type.File;
import com.fortes.rh.business.captacao.SolicitacaoBDSManagerImpl;

public class SolicitacaoBDSManagerTest extends MockObjectTestCase
{
	SolicitacaoBDSManagerImpl solicitacaoBDSManager = null;

	protected void setUp() throws Exception{
		super.setUp();

		solicitacaoBDSManager = new SolicitacaoBDSManagerImpl();
	}

	protected void tearDown() throws Exception{
		super.tearDown();
	}

	public void testValidaArquivoBDS(){

		File file = new File();
		file.setName("c:/teste.fortesrh");

		assertTrue("Test 1", solicitacaoBDSManager.validaArquivoBDS(file));

		file.setName("c:/teste.txt");

		assertFalse("Test 2", solicitacaoBDSManager.validaArquivoBDS(file));

	}
}
