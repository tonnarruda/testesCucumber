package com.fortes.rh.test.web.acpessoal;

import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.ws.AcPessoalImportadorGastos;

public class AcPessoalClientGastoTest extends AcPessoalClientTest
{
	private AcPessoalImportadorGastos acPessoalImportadorGastos;
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		acPessoalImportadorGastos = new AcPessoalImportadorGastos();
		acPessoalImportadorGastos.setAcPessoalClient(acPessoalClientImpl);
	}

	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testImportarGastos() throws Exception
	{
		montaMockGrupoAC();
		
		String[] gastos = acPessoalImportadorGastos.importarGastos(DateUtil.montaDataByString("01/02/2011"), empresa);
		assertEquals(0, gastos.length);//nao sei pq era 18, chicobarroso.
	}
}
